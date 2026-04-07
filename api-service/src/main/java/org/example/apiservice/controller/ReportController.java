package org.example.apiservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.apiservice.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/reports/appointments-per-day")
    public ResponseEntity<Object> getAppointmentsPerDay() {
        return reportService.getAppointmentsPerDay();
    }

    @GetMapping("/reports/top-patients")
    public ResponseEntity<Object> getTopPatients() {
        return reportService.getTopPatients();
    }

    @GetMapping("/reports/top-doctors")
    public ResponseEntity<Object> getTopDoctors() {
        return reportService.getTopDoctors();
    }
}
