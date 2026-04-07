package org.example.apiservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.apiservice.dto.AppointmentRequest;
import org.example.apiservice.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentsController {

    private final AppointmentService appointmentService;


    @PostMapping
    public ResponseEntity<Void> createAppointment(
            @RequestBody
            @Valid
            AppointmentRequest request) {
        appointmentService.sendToKafka(request);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchAppointments(
            @RequestParam(required = false) String patientName,
            @RequestParam(required = false) String doctorName,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to
    ) {
        return appointmentService.searchAppointments(patientName, doctorName, from, to);
    }
}