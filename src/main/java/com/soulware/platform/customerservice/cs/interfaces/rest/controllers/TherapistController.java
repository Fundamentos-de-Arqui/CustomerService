package com.soulware.platform.customerservice.cs.interfaces.rest.controllers;

import com.soulware.platform.customerservice.cs.domain.model.aggregates.TherapistProfile;
import com.soulware.platform.customerservice.cs.domain.model.commands.CreateTherapistCommand;
import com.soulware.platform.customerservice.cs.domain.model.queries.GetAllTherapistsQuery;
import com.soulware.platform.customerservice.cs.domain.model.queries.GetTherapistsByPatientQuery;
import com.soulware.platform.customerservice.cs.domain.services.TherapistCommandService;
import com.soulware.platform.customerservice.cs.domain.services.TherapistQueryService;
import com.soulware.platform.customerservice.cs.interfaces.rest.dto.CreateTherapistRequest;
import com.soulware.platform.customerservice.cs.interfaces.rest.dto.TherapistResponse;
import com.soulware.platform.customerservice.cs.interfaces.rest.mappers.TherapistMapper;
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
@RequestMapping("/api/v1/therapists")
@CrossOrigin(origins = "*")
@Tag(name = "Therapist Management", description = "APIs for managing therapist information")
public class TherapistController {

    private final TherapistCommandService commandService;
    private final TherapistQueryService queryService;
    private final TherapistMapper mapper;

    public TherapistController(TherapistCommandService commandService, 
                             TherapistQueryService queryService, 
                             TherapistMapper mapper) {
        this.commandService = commandService;
        this.queryService = queryService;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Create a new therapist", description = "Creates a new therapist profile")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Therapist created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<TherapistResponse> createTherapist(
            @RequestBody CreateTherapistRequest request) {
        
        CreateTherapistCommand command = mapper.toCommand(request);
        Optional<TherapistProfile> result = commandService.handle(command);
        
        if (result.isPresent()) {
            TherapistResponse response = mapper.toResponse(result.get());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    @Operation(summary = "Get all therapists", description = "Retrieves all therapist profiles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Therapists retrieved successfully")
    })
    public ResponseEntity<List<TherapistResponse>> getAllTherapists() {
        List<TherapistProfile> therapists = queryService.handle(new GetAllTherapistsQuery());
        List<TherapistResponse> responses = therapists.stream()
                .map(mapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get therapists by patient", description = "Retrieves all therapists assigned to a specific patient")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Therapists retrieved successfully")
    })
    public ResponseEntity<List<TherapistResponse>> getTherapistsByPatient(
            @Parameter(description = "Patient ID") @PathVariable Long patientId) {
        
        List<TherapistProfile> therapists = queryService.handle(new GetTherapistsByPatientQuery(patientId));
        List<TherapistResponse> responses = therapists.stream()
                .map(mapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }
}





