package org.example.dataservice.kafka;

import org.example.dataservice.dto.AppointmentEvent;
import org.example.dataservice.service.AppointmentService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AppointmentsListener {

    private final AppointmentService appointmentService;

    public AppointmentsListener(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @KafkaListener(topics = "${appointments.topic-name}")
    public void onAppointment(AppointmentEvent event) {
        appointmentService.saveFromEvent(event);
    }
}

