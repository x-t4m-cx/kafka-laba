package org.example.apiservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(description = "Appointment creation request")
public class AppointmentRequest {

    @NotBlank
    @Schema(description = "Patient's full name", example = "Иван Иванов")
    private String patientName;

    @NotBlank
    @Schema(description = "Doctor's full name", example = "Петр Петров")
    private String doctorName;

    @Schema(description = "Doctor's specialty", example = "Кардиолог")
    private String doctorSpecialty;

    @NotNull
    @Schema(description = "Appointment date and time", example = "2026-05-20T14:30:00")
    private LocalDateTime appointmentDateTime;

    @Schema(description = "Reason for the visit", example = "Боль в груди")
    private String reason;
}

