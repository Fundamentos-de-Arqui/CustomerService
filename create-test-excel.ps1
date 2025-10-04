# Create a test Excel file for patient data upload
# This script creates an Excel file that matches the expected format

Write-Host "Creating test Excel file..." -ForegroundColor Green

# Create Excel file using PowerShell COM object
$excel = New-Object -ComObject Excel.Application
$excel.Visible = $false
$excel.DisplayAlerts = $false

# Create a new workbook
$workbook = $excel.Workbooks.Add()
$worksheet = $workbook.Worksheets.Item(1)
$worksheet.Name = "Patient Data"

# Set up the form structure based on the image
$row = 1

# Header
$worksheet.Cells.Item($row, 1) = "Nº Historia Clinica"
$worksheet.Cells.Item($row, 2) = ""
$row++

$worksheet.Cells.Item($row, 1) = "Fecha de Evaluación Inicial"
$worksheet.Cells.Item($row, 2) = "4/30/2025"
$row++

$worksheet.Cells.Item($row, 1) = "Fecha Actual"
$worksheet.Cells.Item($row, 2) = "4/30/2025"
$row += 2

# Section I - Patient Data
$worksheet.Cells.Item($row, 1) = "I.- PACIENTE"
$row++

$worksheet.Cells.Item($row, 1) = "Apellido Paterno"
$worksheet.Cells.Item($row, 2) = "Jose"
$row++

$worksheet.Cells.Item($row, 1) = "Apellido Materno"
$worksheet.Cells.Item($row, 2) = "Galvez"
$row++

$worksheet.Cells.Item($row, 1) = "Nombres Completos"
$worksheet.Cells.Item($row, 2) = "Test 4"
$row++

$worksheet.Cells.Item($row, 1) = "Lugar de Nacimiento"
$worksheet.Cells.Item($row, 2) = "Lima"
$row++

$worksheet.Cells.Item($row, 1) = "Fecha de Nacimiento"
$worksheet.Cells.Item($row, 2) = "1/16/2023"
$row++

$worksheet.Cells.Item($row, 1) = "Sexo"
$worksheet.Cells.Item($row, 2) = "Mujer"
$row++

$worksheet.Cells.Item($row, 1) = "Edad: 1era. Cita"
$worksheet.Cells.Item($row, 2) = "2 años, 3 meses y 14 días"
$row++

$worksheet.Cells.Item($row, 1) = "Edad: II Cita Actual"
$worksheet.Cells.Item($row, 2) = "2 años, 3 meses y 14 días"
$row++

$worksheet.Cells.Item($row, 1) = "Diagnóstico médico o problema identificado"
$worksheet.Cells.Item($row, 2) = "Retraso"
$row++

$worksheet.Cells.Item($row, 1) = "Domicilio Actual"
$worksheet.Cells.Item($row, 2) = "Jr. Tal Numero, Numero de casa. Distrito"
$row++

$worksheet.Cells.Item($row, 1) = "Distrito/Provincia/Región o Estado/País"
$worksheet.Cells.Item($row, 2) = "Distrito/Provincia/Departamento/Pais"
$row++

$worksheet.Cells.Item($row, 1) = "Documento de Identidad"
$worksheet.Cells.Item($row, 2) = "D.N.I. 88888888"
$row++

$worksheet.Cells.Item($row, 1) = "Fijo Casa/Celular"
$worksheet.Cells.Item($row, 2) = "Fono 1 888888888 Celular 845621547"
$row++

$worksheet.Cells.Item($row, 1) = "Correo electrónico"
$worksheet.Cells.Item($row, 2) = "hola@gmail.com"
$row++

$worksheet.Cells.Item($row, 1) = "Estado Civil"
$worksheet.Cells.Item($row, 2) = "soltero"
$row++

$worksheet.Cells.Item($row, 1) = "Grado de Instrucción"
$worksheet.Cells.Item($row, 2) = ""
$row++

$worksheet.Cells.Item($row, 1) = "Ocupación"
$worksheet.Cells.Item($row, 2) = "agricultor"
$row++

$worksheet.Cells.Item($row, 1) = "Institución Educativa Actual"
$worksheet.Cells.Item($row, 2) = ""
$row++

$worksheet.Cells.Item($row, 1) = "Religión"
$worksheet.Cells.Item($row, 2) = "Catolico"
$row++

$worksheet.Cells.Item($row, 1) = "Tipo de Comprobante"
$worksheet.Cells.Item($row, 2) = "Boleta"
$row++

$worksheet.Cells.Item($row, 1) = "Razón Social y/o Titular"
$worksheet.Cells.Item($row, 2) = "Rimac por ejemplo"
$row++

$worksheet.Cells.Item($row, 1) = "RUC y/o DNI"
$worksheet.Cells.Item($row, 2) = "55555555"
$row++

$worksheet.Cells.Item($row, 1) = "Dirección"
$worksheet.Cells.Item($row, 2) = "Av Victoria"
$row += 2

# Section II - Legal Responsibles
$worksheet.Cells.Item($row, 1) = "II.- RESPONSABLE/ES. U ACOMPAÑANTE/S."
$row++

$worksheet.Cells.Item($row, 1) = "R.1 Nombre/es. y Apellidos"
$worksheet.Cells.Item($row, 2) = "Jorge Alonso"
$row++

$worksheet.Cells.Item($row, 1) = "R.1 Documento de Identidad"
$worksheet.Cells.Item($row, 2) = "D.N.I. XXXXXXXX C.E."
$row++

$worksheet.Cells.Item($row, 1) = "R.1 Parentesco o Relación"
$worksheet.Cells.Item($row, 2) = "Parentezco por ejemplo Mamá"
$row++

$worksheet.Cells.Item($row, 1) = "R.1 Celular y/o Teléfono de Ofc."
$worksheet.Cells.Item($row, 2) = "Cel. +51 XXX XXX XXX Otro"
$row++

$worksheet.Cells.Item($row, 1) = "R.1 E-mail de Contacto"
$worksheet.Cells.Item($row, 2) = "correo@hotmail.com"
$row += 2

# Section III - Principal Physician
$worksheet.Cells.Item($row, 1) = "III.- MÉDICO TRATANTE PRINCIPAL"
$row++

$worksheet.Cells.Item($row, 1) = "Nombre y Apellidos"
$worksheet.Cells.Item($row, 2) = "Nombre Apellido / Nombre Apellido"
$row++

$worksheet.Cells.Item($row, 1) = "Especialidad"
$worksheet.Cells.Item($row, 2) = "Pediatra / Neuro-Pediatra"
$row++

$worksheet.Cells.Item($row, 1) = "Lugar de Atención"
$worksheet.Cells.Item($row, 2) = "Clínica San Pablo / Consultorio particular en Chacarilla"
$row++

$worksheet.Cells.Item($row, 1) = "Fono/os."
$worksheet.Cells.Item($row, 2) = ""
$row += 2

# Section IV - Reference
$worksheet.Cells.Item($row, 1) = "IV.- REFERENCIA: ¿Quién le ha referido?"
$row++

$worksheet.Cells.Item($row, 1) = "Nombre y Apellidos o Institución"
$worksheet.Cells.Item($row, 2) = "Dr(a) Nombre Apellido"
$row++

$worksheet.Cells.Item($row, 1) = "Relación con el Paciente"
$worksheet.Cells.Item($row, 2) = "Médico tratante"
$row++

$worksheet.Cells.Item($row, 1) = "Institución"
$worksheet.Cells.Item($row, 2) = ""
$row += 2

# Section V - Additional Data
$worksheet.Cells.Item($row, 1) = "V.- DATOS ADICIONALES"
$row++

$worksheet.Cells.Item($row, 1) = "Notas adicionales"
$worksheet.Cells.Item($row, 2) = "Antes se atendía en tal lugar pero ahora le queda lejos. No puede ser atendido en la mañana"

# Auto-fit columns
$worksheet.Columns.AutoFit() | Out-Null

# Save the file
$filePath = Join-Path (Get-Location) "test-patient.xlsx"
$workbook.SaveAs($filePath)

# Close Excel
$workbook.Close()
$excel.Quit()
[System.Runtime.Interopservices.Marshal]::ReleaseComObject($excel) | Out-Null

Write-Host "✅ Test Excel file created: $filePath" -ForegroundColor Green
Write-Host "You can now test the Excel upload functionality." -ForegroundColor Cyan