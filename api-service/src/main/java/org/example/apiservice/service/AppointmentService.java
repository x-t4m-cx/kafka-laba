package org.example.apiservice.service;

import java.util.HashMap;
import java.util.UUID;
import java.util.stream.Stream;

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
        String appointmentsKey = UUID.randomUUID().toString();
        kafkaProducer.send(event, appointmentsKey);
    }

    public ResponseEntity<Object> searchAppointments(
            String patientName,
            String doctorName,
            String from,
            String to
    ) {
        HashMap<String, String> params = new HashMap<>();

        putIfNotNullOrBlank(params, "patientName", patientName);
        putIfNotNullOrBlank(params, "doctorName", doctorName);
        putIfNotNullOrBlank(params, "from", from);
        putIfNotNullOrBlank(params, "to", to);

        return apiClient.searchAppointments(params);
    }

    private void putIfNotNullOrBlank(HashMap<String, String> params, String parameter, String value) {
        if (value != null && !value.isBlank()) {
            params.put(parameter, value);
        }
    }
}
