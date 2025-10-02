package com.soulware.platform.customerservice.cs.application.internal.services;

import com.soulware.platform.customerservice.cs.domain.model.valueobjects.DocumentType;
import com.soulware.platform.customerservice.cs.interfaces.soap.resources.CreatePatientResource;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Iterator;
import java.util.Locale;

/**
 * Parses the clinic Excel form (XLSX) into a CreatePatientResource.
 * Assumptions (based on the provided template):
 * - Labels are on the left (often col B), values to the right (often col D).
 * - "Documento de Identidad" row may contain masked placeholders like "XXXXX XXX".
 * - receiptType is a String (not an enum) and will be normalized to "INVOICE" or "BILL" when possible.
 */
@Component
public class ExcelPatientFormParser {

    /**
     * Parse the first sheet of the given Excel stream into a CreatePatientResource.
     * Throws IllegalArgumentException with a clear message when a required field is not found.
     */
    public CreatePatientResource parse(InputStream in) {
        try (Workbook wb = new XSSFWorkbook(in)) {
            Sheet sheet = wb.getSheetAt(0);

            // --- Names ---
            String paternal     = findByLabel(sheet, "Apellido Paterno");
            String maternal     = findByLabel(sheet, "Apellido Materno");
            String firstNames   = findByLabel(sheet, "Nombres Completos");

            // --- Phone (pick the first non-blank among common labels) ---
            String phone        = firstNonBlank(
                    findByLabel(sheet, "Fijo Casa/Celular"),
                    findByLabel(sheet, "Fono 1"),
                    findByLabel(sheet, "Celular")
            );

            // --- Birth date ---
            LocalDate birthDate = readDate(sheet, "Fecha de Nacimiento");

            // --- Document Type (map to domain enum) ---
            String docTypeStr   = firstNonBlank(
                    findByLabel(sheet, "Documento de Identidad"),
                    findByLabel(sheet, "Documento"),
                    findByLabel(sheet, "DNI"),
                    findByLabel(sheet, "D.N.I.")
            );
            DocumentType documentType = normalizeDocType(docTypeStr);

            // --- Document Number (robust extraction + normalization) ---
            // 1) From the same row as "Documento de Identidad", first cell to the right with >= 6 digits.
            String docNumberRaw = findNumberInRowOfLabel(sheet, "Documento de Identidad");

            // 2) If masked or empty, try scanning near tokens anywhere.
            if (isBlank(docNumberRaw) || looksMasked(docNumberRaw)) {

                docNumberRaw = findNumberNearLabelAnywhere(sheet, "DNI", "D.N.I.", "Documento");
            }

            // 3) As a last fallback, try a later row sometimes titled "RUC y/o DNI".
            if (isBlank(docNumberRaw) || looksMasked(docNumberRaw)) {

                String fromRucRow = findByLabel(sheet, "RUC y/o DNI");
                if (!isBlank(fromRucRow) && !looksMasked(fromRucRow)) {
                    docNumberRaw = fromRucRow;
                }
            }

            String documentNumber = digitsOnly(docNumberRaw);

            // If you REQUIRE a real document number, keep this validation:
            if (isBlank(documentNumber) || documentNumber.length() < 6) {
                throw new IllegalArgumentException("document number required");
            }

            // --- Receipt Type as String (normalize but keep free-form if unknown) ---
            String receiptRaw   = firstNonBlank(
                    findByLabel(sheet, "Tipo de Comprobante"),
                    findByLabel(sheet, "Comprobante")
            );
            String receiptType  = normalizeReceiptString(receiptRaw); // "INVOICE" / "BILL" / original

            // --- Validate minimal required fields (tune as needed) ---
            if (isBlank(firstNames))   throw new IllegalArgumentException("first names required");
            if (isBlank(paternal))     throw new IllegalArgumentException("paternal surname required");
            if (birthDate == null)     throw new IllegalArgumentException("birth date required");
            if (isBlank(phone))        phone = ""; // optional: allow empty phone

            return new CreatePatientResource(
                    firstNames,
                    paternal,
                    maternal,
                    documentType,
                    documentNumber,
                    phone,
                    birthDate,
                    receiptType
            );

        } catch (IllegalArgumentException iae) {
            // Bubble up clean business messages (Spring-WS will emit them as SOAP Faults)
            throw iae;
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Excel patient form", e);
        }
    }

    // ========================= Helpers =========================

    private String firstNonBlank(String... vals) {
        for (String v : vals) if (v != null && !v.isBlank()) return v.trim();
        return null;
    }

    private String digitsOnly(String s) {
        if (s == null) return null;
        String d = s.replaceAll("\\D+", ""); // keep only digits 0-9
        return d.isEmpty() ? null : d;
    }




    /**
     * Find a row by label text (search first 3 cells for the label), then return the first non-empty cell to the right.
     */
    private String findByLabel(Sheet sheet, String label) {
        String target = norm(label);
        for (Row row : sheet) {
            int lastCell = Math.max(0, row.getLastCellNum());
            for (int i = 0; i < Math.min(3, lastCell); i++) {
                Cell c = row.getCell(i);
                if (c == null) continue;
                String txt = asString(c);
                if (norm(txt).equals(target)) {
                    for (int col = i + 1; col < lastCell; col++) {
                        String val = asString(row.getCell(col));
                        if (val != null && !val.isBlank()) return val.trim();
                    }
                }
            }
        }
        return null;
    }

    /**
     * From the row containing the given label, return the first cell to the right that contains a value with >= 6 digits.
     */
    private String findNumberInRowOfLabel(Sheet sheet, String label) {
        String target = norm(label);
        for (Row row : sheet) {
            int lastCell = Math.max(0, row.getLastCellNum());
            for (int i = 0; i < Math.min(3, lastCell); i++) {
                Cell c = row.getCell(i);
                if (c == null) continue;
                String txt = asString(c);
                if (norm(txt).equals(target)) {
                    for (int col = i + 1; col < lastCell; col++) {
                        String val = asString(row.getCell(col));
                        if (val != null && val.replaceAll("\\D+", "").length() >= 6) return val.trim();
                    }
                }
            }
        }
        return null;
    }

    /**
     * Scan the entire sheet; when a cell contains any of the tokens, check to the right and one row below for a value with >= 6 digits.
     */
    private String findNumberNearLabelAnywhere(Sheet sheet, String... tokens) {
        for (Row row : sheet) {
            for (Cell cell : row) {
                String txt = asString(cell);
                if (txt == null) continue;
                String n = norm(txt);
                for (String t : tokens) {
                    if (n.contains(norm(t))) {
                        // same row, cells to the right
                        int lastCell = Math.max(0, row.getLastCellNum());
                        for (int col = cell.getColumnIndex() + 1; col < lastCell; col++) {
                            String val = asString(row.getCell(col));
                            if (val != null && val.replaceAll("\\D+", "").length() >= 6) return val.trim();
                        }
                        // one row below, same column
                        Row below = sheet.getRow(row.getRowNum() + 1);
                        if (below != null) {
                            String val = asString(below.getCell(cell.getColumnIndex()));
                            if (val != null && val.replaceAll("\\D+", "").length() >= 6) return val.trim();
                        }
                    }
                }
            }
        }
        return null;
    }

    private LocalDate readDate(Sheet sheet, String label) {
        String target = norm(label);
        for (Row row : sheet) {
            int lastCell = Math.max(0, row.getLastCellNum());
            for (int i = 0; i < Math.min(3, lastCell); i++) {
                Cell c = row.getCell(i);
                if (c == null) continue;
                String txt = asString(c);
                if (norm(txt).equals(target)) {
                    for (int col = i + 1; col < lastCell; col++) {
                        Cell v = row.getCell(col);
                        if (v == null) continue;
                        if (v.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(v)) {
                            return v.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        } else {
                            String s = asString(v);
                            if (s != null && !s.isBlank()) {
                                try { return LocalDate.parse(s.trim()); } catch (Exception ignored) {}
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    private String asString(Cell c) {
        if (c == null) return null;
        return switch (c.getCellType()) {
            case STRING  -> c.getStringCellValue();
            case NUMERIC -> DateUtil.isCellDateFormatted(c)
                    ? c.getLocalDateTimeCellValue().toLocalDate().toString()
                    : stringFromNumeric(c);
            case BOOLEAN -> String.valueOf(c.getBooleanCellValue());
            case FORMULA -> {
                // try cached result; if date, convert; otherwise numeric/string
                FormulaEvaluator evaluator = c.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
                CellValue cv = evaluator.evaluate(c);
                yield switch (cv.getCellType()) {
                    case STRING  -> cv.getStringValue();
                    case NUMERIC -> String.valueOf((long) cv.getNumberValue());
                    case BOOLEAN -> String.valueOf(cv.getBooleanValue());
                    default      -> null;
                };
            }
            default      -> null;
        };
    }

    private String stringFromNumeric(Cell c) {
        double d = c.getNumericCellValue();
        long asLong = (long) d;
        // For IDs/phones, integers are common; render without decimal point if applicable
        return (d == asLong) ? Long.toString(asLong) : Double.toString(d);
    }

    private String norm(String s) {
        if (s == null) return "";
        return s.toLowerCase(Locale.ROOT)
                .replace("á","a").replace("é","e").replace("í","i")
                .replace("ó","o").replace("ú","u").replace("ñ","n")
                .trim();
    }

    private boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }

    private boolean looksMasked(String s) {
        if (s == null) return false;
        String n = s.trim().toUpperCase(Locale.ROOT);
        // common placeholders in the provided template: "XXXXX XXX", strings of 'X', etc.
        if (n.contains("XXXXX")) return true;
        if (n.matches("X[ X\\-]*")) return true; // X, X X, XXXXX, X- X - X, etc.
        return false;
    }

    private DocumentType normalizeDocType(String raw) {
        String n = norm(raw);
        if (n.contains("dni"))      return DocumentType.DNI;
        if (n.contains("pasaport")) return DocumentType.PASSPORT;
        if (n.contains("ruc"))      return DocumentType.RUC;
        // default
        return DocumentType.DNI;
    }

    /**
     * Keep receipt as String. Normalize common Spanish terms to WSDL-friendly uppercase values
     * used by your SOAP schema ("INVOICE", "BILL"). If unknown, return the original string or a default.
     */
    private String normalizeReceiptString(String raw) {
        if (isBlank(raw)) return "BILL"; // sensible default
        String n = norm(raw);
        if (n.contains("factura")) return "INVOICE";
        if (n.contains("boleta") || n.contains("bill")) return "BILL";
        // keep original (uppercased) if it's some other code you want to allow
        return raw.trim().toUpperCase(Locale.ROOT);
    }
}
