package org.example.apiservice.client.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class AppointmentApiClient {

    private final RestClient restClient;

    public AppointmentApiClient(
            RestClient.Builder restClientBuilder,
            @Value("${appointments.data-service-base-url}") String baseUrl
    ) {
        this.restClient = restClientBuilder.baseUrl(baseUrl).build();
    }

    public ResponseEntity<Object> searchAppointments(
            String patientName,
            String doctorName,
            String from,
            String to
    ) {
        return restClient.get()
                .uri(uriBuilder -> {
                    uriBuilder.path("/api/appointments/search");
                    if (patientName != null && !patientName.isBlank()) {
                        uriBuilder.queryParam("patientName", patientName);
                    }
                    if (doctorName != null && !doctorName.isBlank()) {
                        uriBuilder.queryParam("doctorName", doctorName);
                    }
                    if (from != null && !from.isBlank()) {
                        uriBuilder.queryParam("from", from);
                    }
                    if (to != null && !to.isBlank()) {
                        uriBuilder.queryParam("to", to);
                    }
                    return uriBuilder.build();
                })
                .retrieve()
                .toEntity(Object.class);
    }

    public ResponseEntity<Object> getAppointmentsPerDay() {
        return restClient.get()
                .uri("/api/reports/appointments-per-day")
                .retrieve()
                .toEntity(Object.class);
    }

    public ResponseEntity<Object> getTopPatients() {
        return restClient.get()
                .uri("/api/reports/top-patients")
                .retrieve()
                .toEntity(Object.class);
    }

    public ResponseEntity<Object> getTopDoctors() {
        return restClient.get()
                .uri("/api/reports/top-doctors")
                .retrieve()
                .toEntity(Object.class);
    }
}