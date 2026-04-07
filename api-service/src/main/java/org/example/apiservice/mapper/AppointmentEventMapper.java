package org.example.apiservice.mapper;

import org.example.apiservice.dto.AppointmentEvent;
import org.example.apiservice.dto.AppointmentRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AppointmentEventMapper {

    public AppointmentEvent toEvent(AppointmentRequest request) {
        AppointmentEvent event = new AppointmentEvent();
        event.setAppointmentId(UUID.randomUUID());
        event.setPatientName(request.getPatientName());
        event.setDoctorName(request.getDoctorName());
        event.setDoctorSpecialty(request.getDoctorSpecialty());
        event.setAppointmentDateTime(request.getAppointmentDateTime());
        event.setReason(request.getReason());
        return event;
    }
}
