package com.soulware.platform.customerservice.cs.interfaces.rest.controllers;

import com.soulware.platform.customerservice.cs.domain.model.aggregates.LegalResponsibleProfile;
import com.soulware.platform.customerservice.cs.domain.model.commands.CreateLegalResponsibleCommand;
import com.soulware.platform.customerservice.cs.domain.model.queries.GetAllLegalResponsiblesQuery;
import com.soulware.platform.customerservice.cs.domain.model.queries.GetLegalResponsiblesByPatientQuery;
import com.soulware.platform.customerservice.cs.domain.services.LegalResponsibleCommandService;
import com.soulware.platform.customerservice.cs.domain.services.LegalResponsibleQueryService;
import com.soulware.platform.customerservice.cs.interfaces.rest.dto.CreateLegalResponsibleRequest;
import com.soulware.platform.customerservice.cs.interfaces.rest.dto.LegalResponsibleResponse;
import com.soulware.platform.customerservice.cs.interfaces.rest.mappers.LegalResponsibleMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/legal-responsibles")
@CrossOrigin(origins = "*")
@Tag(name = "Legal Responsible Management", description = "APIs for managing legal responsible information")
public class LegalResponsibleController {

    private final LegalResponsibleCommandService commandService;
    private final LegalResponsibleQueryService queryService;
    private final LegalResponsibleMapper mapper;

    public LegalResponsibleController(LegalResponsibleCommandService commandService, 
                                   LegalResponsibleQueryService queryService, 
                                   LegalResponsibleMapper mapper) {
        this.commandService = commandService;
        this.queryService = queryService;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Create a new legal responsible", description = "Creates a new legal responsible profile")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Legal responsible created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    public ResponseEntity<LegalResponsibleResponse> createLegalResponsible(
            @RequestBody CreateLegalResponsibleRequest request) {
        
        CreateLegalResponsibleCommand command = mapper.toCommand(request);
        Optional<LegalResponsibleProfile> result = commandService.handle(command);
        
        if (result.isPresent()) {
            LegalResponsibleResponse response = mapper.toResponse(result.get());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    @Operation(summary = "Get all legal responsibles", description = "Retrieves all legal responsible profiles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Legal responsibles retrieved successfully")
    })
    public ResponseEntity<List<LegalResponsibleResponse>> getAllLegalResponsibles() {
        List<LegalResponsibleProfile> legalResponsibles = queryService.handle(new GetAllLegalResponsiblesQuery());
        List<LegalResponsibleResponse> responses = legalResponsibles.stream()
                .map(mapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get legal responsibles by patient", description = "Retrieves all legal responsibles for a specific patient")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Legal responsibles retrieved successfully")
    })
    public ResponseEntity<List<LegalResponsibleResponse>> getLegalResponsiblesByPatient(
            @Parameter(description = "Patient ID") @PathVariable Long patientId) {
        
        List<LegalResponsibleProfile> legalResponsibles = queryService.handle(new GetLegalResponsiblesByPatientQuery(patientId));
        List<LegalResponsibleResponse> responses = legalResponsibles.stream()
                .map(mapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }
}



