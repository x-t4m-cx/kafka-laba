package org.example.apiservice.service;

import lombok.RequiredArgsConstructor;
import org.example.apiservice.client.kafka.KafkaMessageProducer;
import org.example.apiservice.client.rest.AppointmentApiClient;
import org.example.apiservice.dto.AppointmentRequest;
import org.example.apiservice.mapper.AppointmentEventMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final KafkaMessageProducer kafkaProducer;
    private final AppointmentApiClient apiClient;
    private final AppointmentEventMapper eventMapper;

    public void sendToKafka(AppointmentRequest request) {
        var event = eventMapper.toEvent(request);
        String appointmentsKey = buildAppointmentKey(request);
        kafkaProducer.send(event, appointmentsKey);
    }

    public ResponseEntity<Object> searchAppointments(
            String patientName,
            String doctorName,
            String from,
            String to
    ) {
        return apiClient.searchAppointments(patientName, doctorName, from, to);
    }

    private String buildAppointmentKey(AppointmentRequest request) {
        String doctorName = normalize(request.getDoctorName());
        String doctorSpecialty = normalize(request.getDoctorSpecialty());
        return doctorName + "|" + doctorSpecialty;
    }

    private String normalize(String value) {
        return value == null ? "unknown" : value.trim().toLowerCase();
    }
}