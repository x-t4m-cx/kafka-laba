package org.example.apiservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.apiservice.api.ReportApi;
import org.example.apiservice.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reports")
public class ReportController implements ReportApi {
    private final ReportService reportService;

    @GetMapping("/appointments-per-day")
    public ResponseEntity<Object> getAppointmentsPerDay() {
        return reportService.getAppointmentsPerDay();
    }

    @GetMapping("/top-patients")
    public ResponseEntity<Object> getTopPatients() {
        return reportService.getTopPatients();
    }

    @GetMapping("/top-doctors")
    public ResponseEntity<Object> getTopDoctors() {
        return reportService.getTopDoctors();
    }
}
