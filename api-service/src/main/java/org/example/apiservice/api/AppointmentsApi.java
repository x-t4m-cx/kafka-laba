package org.example.apiservice.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.apiservice.dto.AppointmentRequest;
import org.springframework.http.ResponseEntity;

public interface AppointmentsApi {

    @Operation(summary = "Create a new appointment", description = "Sends the appointment request to Kafka")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Request accepted for processing"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    ResponseEntity<Void> createAppointment(
            @Parameter(description = "Appointment request data", required = true)
            AppointmentRequest request);

    @Operation(summary = "Search appointments", description = "Search appointments by optional parameters")
    ResponseEntity<Object> searchAppointments(
            @Parameter(description = "Patient's name", example = "Иван Иванов")
            String patientName,
            @Parameter(description = "Doctor's name", example = "Петр Петров")
            String doctorName,
            @Parameter(description = "Start date", example = "2026-04-20T9:30:00")
            String from,
            @Parameter(description = "End date", example = "2026-06-20T20:30:00")
            String to);
}
