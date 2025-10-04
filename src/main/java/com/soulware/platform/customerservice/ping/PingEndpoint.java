package com.soulware.platform.customerservice.ping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Health Check", description = "Health check and system status endpoints")
public class PingEndpoint {

    @Operation(summary = "Health check", description = "Returns system status and echo message")
    @ApiResponse(responseCode = "200", description = "System is healthy")
    @GetMapping("/ping")
    public ResponseEntity<Map<String, Object>> ping(
            @Parameter(description = "Echo message to return") @RequestParam(required = false) String echo) {
        Map<String, Object> response = Map.of(
            "echo", echo != null ? echo : "",
            "serverTime", OffsetDateTime.now().toString(),
            "status", "OK"
        );
        return ResponseEntity.ok(response);
    }
}
