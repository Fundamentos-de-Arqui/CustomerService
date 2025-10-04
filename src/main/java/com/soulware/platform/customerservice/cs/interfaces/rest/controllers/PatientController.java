package com.soulware.platform.customerservice.cs.interfaces.rest.controllers;

import com.soulware.platform.customerservice.cs.application.internal.services.ExcelPatientFormParser;
import com.soulware.platform.customerservice.cs.application.internal.messaging.PatientMessageProducer;
import com.soulware.platform.customerservice.cs.domain.model.queries.GetAllPatientsQuery;
import com.soulware.platform.customerservice.cs.domain.services.PatientCommandService;
import com.soulware.platform.customerservice.cs.domain.services.PatientQueryService;
import com.soulware.platform.customerservice.cs.interfaces.rest.dto.*;
import com.soulware.platform.customerservice.cs.interfaces.rest.mappers.PatientMapper;
import com.soulware.platform.customerservice.cs.application.internal.services.CompleteExcelProcessor;
import org.springframework.jms.core.JmsTemplate;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.Connection;
import jakarta.jms.JMSException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST Controller for Patient operations
 */
@RestController
@RequestMapping("/api/v1/patients")
@CrossOrigin(origins = "*")
@Tag(name = "Patient Management", description = "APIs for managing patient information")
public class PatientController {

    private final PatientCommandService commandService;
    private final PatientQueryService queryService;
    private final ExcelPatientFormParser formParser;
    private final PatientMapper patientMapper;
    private final CompleteExcelProcessor completeExcelProcessor;
    private final PatientMessageProducer patientMessageProducer;
    private final JmsTemplate jmsTemplate;
    private final ConnectionFactory connectionFactory;

    public PatientController(PatientCommandService commandService, 
                           PatientQueryService queryService, 
                           ExcelPatientFormParser formParser,
                           PatientMapper patientMapper,
                           CompleteExcelProcessor completeExcelProcessor,
                           PatientMessageProducer patientMessageProducer,
                           JmsTemplate jmsTemplate,
                           ConnectionFactory connectionFactory) {
        this.commandService = commandService;
        this.queryService = queryService;
        this.formParser = formParser;
        this.patientMapper = patientMapper;
        this.completeExcelProcessor = completeExcelProcessor;
        this.patientMessageProducer = patientMessageProducer;
        this.jmsTemplate = jmsTemplate;
        this.connectionFactory = connectionFactory;
    }

    /**
     * Create a new patient
     */
    @Operation(summary = "Create a new patient", description = "Creates a new patient with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Patient created successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PatientResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PatientResponse> createPatient(@Valid @RequestBody CreatePatientRequest request) {
        var command = patientMapper.toCommand(request);
        var createdPatient = commandService.handle(command)
                .orElseThrow(() -> new RuntimeException("Failed to create patient"));
        
        var response = patientMapper.toResponse(createdPatient);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Get all patients
     */
    @Operation(summary = "Get all patients", description = "Retrieves a list of all patients")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved patients",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PatientResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PatientResponse>> getAllPatients() {
        var patients = queryService.handle(new GetAllPatientsQuery())
                .stream()
                .map(patientMapper::toResponse)
                .toList();
        
        return ResponseEntity.ok(patients);
    }

    /**
     * Get patient by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> getPatientById(@PathVariable Long id) {
        // This would need to be implemented in the query service
        return ResponseEntity.notFound().build();
    }

    /**
     * Upload patient form via Excel file
     */
    @Operation(summary = "Upload patient form", description = "Uploads an Excel file containing patient information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Patient created successfully from form",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PatientResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid file format or data"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> uploadPatientForm(
            @Parameter(description = "Name of the file") @RequestParam("fileName") String fileName,
            @Parameter(description = "Excel file containing patient data") @RequestParam("file") MultipartFile file) {
        
        try {
            // Parse Excel to get complete patient data including legal responsibles
            CompletePatientDataRequest patientData = formParser.parseComplete(file.getInputStream());
            
            Map<String, Object> response = new HashMap<>();
            
            try {
                // Try to send to queue first
                String messageId = patientMessageProducer.sendPatientForProcessing(patientData, fileName);
                
                response.put("messageId", messageId);
                response.put("status", "QUEUED");
                response.put("message", "Patient data queued for processing");
                response.put("legalResponsiblesCount", patientData.legalResponsibles() != null ? patientData.legalResponsibles().size() : 0);
                
                return ResponseEntity.accepted().body(response);
                
            } catch (Exception jmsException) {
                // Fallback to synchronous processing if JMS fails
                System.err.println("JMS processing failed, falling back to synchronous processing: " + jmsException.getMessage());
                
                try {
                    ExcelProcessingResult result = completeExcelProcessor.processCompleteData(patientData);
                    
                    response.put("status", "PROCESSED");
                    response.put("message", "Patient data processed synchronously");
                    response.put("success", result.success());
                    response.put("legalResponsiblesCount", patientData.legalResponsibles() != null ? patientData.legalResponsibles().size() : 0);
                    
                    if (result.success()) {
                        if (result.patient() != null) {
                            response.put("patientId", result.patient().id());
                        }
                        response.put("patient", result.patient());
                        response.put("legalResponsibles", result.legalResponsibles());
                        response.put("therapists", result.therapists());
                        response.put("details", result.message());
                        return ResponseEntity.ok(response);
                    } else {
                        response.put("error", result.message());
                        return ResponseEntity.badRequest().body(response);
                    }
                    
                } catch (Exception syncException) {
                    response.put("status", "ERROR");
                    response.put("message", "Failed to process patient data both asynchronously and synchronously");
                    response.put("jmsError", jmsException.getMessage());
                    response.put("syncError", syncException.getMessage());
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
                }
            }
            
        } catch (IOException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "ERROR");
            errorResponse.put("message", "Failed to parse Excel file");
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "ERROR");
            errorResponse.put("message", "Unexpected error processing file");
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    /**
     * Process complete patient data with all related entities
     */
    @Operation(summary = "Process complete patient data", description = "Creates a patient with all related legal responsibles and therapists")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Complete data processed successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExcelProcessingResult.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = "/complete", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExcelProcessingResult> processCompleteData(@Valid @RequestBody CompletePatientDataRequest request) {
        ExcelProcessingResult result = completeExcelProcessor.processCompleteData(request);
        
        if (result.success()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Patient service is running");
    }

    /**
     * Check broker health
     */
    @Operation(summary = "Check broker health", description = "Checks if the ActiveMQ broker is accessible and healthy")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Broker is healthy"),
        @ApiResponse(responseCode = "503", description = "Broker is not accessible")
    })
    @GetMapping("/broker/health")
    public ResponseEntity<Map<String, Object>> checkBrokerHealth() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Try to create a connection to test broker connectivity
            Connection connection = null;
            try {
                connection = connectionFactory.createConnection();
                connection.start();
                
                response.put("status", "HEALTHY");
                response.put("message", "ActiveMQ broker is accessible");
                response.put("brokerUrl", "tcp://localhost:61616");
                response.put("timestamp", java.time.Instant.now().toString());
                
                return ResponseEntity.ok(response);
                
            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (JMSException e) {
                        // Ignore close errors
                    }
                }
            }
            
        } catch (JMSException e) {
            response.put("status", "UNHEALTHY");
            response.put("message", "ActiveMQ broker is not accessible");
            response.put("error", e.getMessage());
            response.put("brokerUrl", "tcp://localhost:61616");
            response.put("timestamp", java.time.Instant.now().toString());
            
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
        }
    }

    /**
     * Send test message to broker
     */
    @Operation(summary = "Send test message", description = "Sends a test message to the patient processing queue to verify broker functionality")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Test message sent successfully"),
        @ApiResponse(responseCode = "500", description = "Failed to send test message")
    })
    @PostMapping("/broker/test")
    public ResponseEntity<Map<String, Object>> sendTestMessage() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String testMessage = "Test message from Customer Service API at " + java.time.Instant.now();
            String messageId = java.util.UUID.randomUUID().toString();
            
            jmsTemplate.convertAndSend("patient.processing.queue", testMessage, message -> {
                message.setStringProperty("messageId", messageId);
                message.setStringProperty("messageType", "TEST");
                message.setStringProperty("timestamp", java.time.Instant.now().toString());
                return message;
            });
            
            response.put("status", "SUCCESS");
            response.put("message", "Test message sent successfully");
            response.put("messageId", messageId);
            response.put("queueName", "patient.processing.queue");
            response.put("timestamp", java.time.Instant.now().toString());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("status", "ERROR");
            response.put("message", "Failed to send test message");
            response.put("error", e.getMessage());
            response.put("timestamp", java.time.Instant.now().toString());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
