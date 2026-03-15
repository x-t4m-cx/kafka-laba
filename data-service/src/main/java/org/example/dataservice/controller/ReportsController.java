package org.example.dataservice.controller;

import org.example.dataservice.repository.AppointmentRepository;
import org.example.dataservice.service.AppointmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportsController {

    private final AppointmentService appointmentService;

    public ReportsController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/appointments-per-day")
    public List<Map<String, Object>> appointmentsPerDay() {
        return appointmentService.getAppointmentsPerDay().stream()
                .map(p -> Map.<String, Object>of(
                        "day", p.getDay(),
                        "count", p.getCount()
                ))
                .toList();
    }

    @GetMapping("/top-patients")
    public List<Map<String, Object>> topPatients() {
        return appointmentService.getTopPatients().stream()
                .map(p -> Map.<String, Object>of(
                        "patientName", p.getPatientName(),
                        "appointmentsCount", p.getAppointmentsCount()
                ))
                .toList();
    }

    @GetMapping("/top-doctors")
    public List<Map<String, Object>> topDoctors() {
        return appointmentService.getTopDoctors().stream()
                .map(p -> Map.<String, Object>of(
                        "doctorName", p.getDoctorName(),
                        "appointmentsCount", p.getAppointmentsCount()
                ))
                .toList();
    }
}

