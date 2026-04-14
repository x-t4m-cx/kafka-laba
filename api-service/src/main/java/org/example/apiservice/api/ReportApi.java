package org.example.apiservice.api;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;

public interface ReportApi {

    @Operation(summary = "Appointments per day", description = "Returns number of appointments grouped by day")
    ResponseEntity<Object> getAppointmentsPerDay();

    @Operation(summary = "Top patients", description = "Returns patients with the most appointments")
    ResponseEntity<Object> getTopPatients();

    @Operation(summary = "Top doctors", description = "Returns doctors with the most appointments")
    ResponseEntity<Object> getTopDoctors();
}
