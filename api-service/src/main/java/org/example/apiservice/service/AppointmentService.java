package org.example.apiservice.service;

import org.example.apiservice.dto.AppointmentRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class AppointmentService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final RestTemplate restTemplate;
    private final String topicName;
    private final String dataServiceBaseUrl;

    public AppointmentService(
            KafkaTemplate<String, Object> kafkaTemplate,
            RestTemplate restTemplate,
            @Value("${appointments.topic-name}") String topicName,
            @Value("${appointments.data-service-base-url}") String dataServiceBaseUrl
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.restTemplate = restTemplate;
        this.topicName = topicName;
        this.dataServiceBaseUrl = dataServiceBaseUrl;
    }

    public void sendToKafka(AppointmentRequest request) {
        kafkaTemplate.send(topicName, request);
    }

    public ResponseEntity<Object> searchAppointments(
            String patientName,
            String doctorName,
            String from,
            String to
    ) {
        String url = dataServiceBaseUrl + "/api/appointments/search";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);

        if (patientName != null && !patientName.isBlank()) {
            builder.queryParam("patientName", patientName);
        }
        if (doctorName != null && !doctorName.isBlank()) {
            builder.queryParam("doctorName", doctorName);
        }
        if (from != null && !from.isBlank()) {
            builder.queryParam("from", from);
        }
        if (to != null && !to.isBlank()) {
            builder.queryParam("to", to);
        }

        return restTemplate.getForEntity(builder.toUriString(), Object.class);
    }

    public ResponseEntity<Object> getAppointmentsPerDay() {
        String url = dataServiceBaseUrl + "/api/reports/appointments-per-day";
        return restTemplate.getForEntity(url, Object.class);
    }

    public ResponseEntity<Object> getTopPatients() {
        String url = dataServiceBaseUrl + "/api/reports/top-patients";
        return restTemplate.getForEntity(url, Object.class);
    }

    public ResponseEntity<Object> getTopDoctors() {
        String url = dataServiceBaseUrl + "/api/reports/top-doctors";
        return restTemplate.getForEntity(url, Object.class);
    }
}