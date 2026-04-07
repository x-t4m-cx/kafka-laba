package org.example.apiservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class AppointmentEvent {
    private UUID appointmentId;
    private String patientName;
    private String doctorName;
    private String doctorSpecialty;
    private LocalDateTime appointmentDateTime;
    private String reason;
}
