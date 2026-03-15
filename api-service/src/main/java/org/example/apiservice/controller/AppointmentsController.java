package org.example.apiservice.controller;

import org.example.apiservice.dto.AppointmentRequest;
import org.example.apiservice.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AppointmentsController {

    private final AppointmentService appointmentService;

    public AppointmentsController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/appointments")
    public ResponseEntity<Void> createAppointment(@RequestBody @Validated AppointmentRequest request) {
        appointmentService.sendToKafka(request);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/appointments")
    public ResponseEntity<Object> searchAppointments(
            @RequestParam(required = false) String patientName,
            @RequestParam(required = false) String doctorName,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to
    ) {
        return appointmentService.searchAppointments(patientName, doctorName, from, to);
    }

    @GetMapping("/reports/appointments-per-day")
    public ResponseEntity<Object> getAppointmentsPerDay() {
        return appointmentService.getAppointmentsPerDay();
    }

    @GetMapping("/reports/top-patients")
    public ResponseEntity<Object> getTopPatients() {
        return appointmentService.getTopPatients();
    }

    @GetMapping("/reports/top-doctors")
    public ResponseEntity<Object> getTopDoctors() {
        return appointmentService.getTopDoctors();
    }
}