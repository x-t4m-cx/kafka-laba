package org.example.dataservice.controller;

import org.example.dataservice.model.Appointment;
import org.example.dataservice.repository.AppointmentRepository;
import org.example.dataservice.service.AppointmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentsController {

    private final AppointmentService appointmentService;

    public AppointmentsController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/search")
    public List<Map<String, Object>> search(
            @RequestParam(required = false) String patientName,
            @RequestParam(required = false) String doctorName,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to
    ) {
        LocalDateTime fromDateTime = appointmentService.parseDateTimeOrNull(from);
        LocalDateTime toDateTime = appointmentService.parseDateTimeOrNull(to);

        List<Appointment> appointments = appointmentService.search(patientName, doctorName, fromDateTime, toDateTime);

        return appointments.stream()
                .map(a -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", a.getId());
                    map.put("patientName", a.getPatient() != null ? a.getPatient().getName() : null);
                    map.put("doctorName", a.getDoctor() != null ? a.getDoctor().getName() : null);
                    map.put("doctorSpecialty", a.getDoctor() != null ? a.getDoctor().getSpecialty() : null);
                    map.put("appointmentTime", a.getAppointmentTime());
                    map.put("reason", a.getReason());
                    return map;
                })
                .collect(Collectors.toList());
    }
}