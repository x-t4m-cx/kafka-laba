package org.example.apiservice.client.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.Map;

@Component
public class AppointmentApiClient {

    private final RestClient restClient;

    public AppointmentApiClient(
            RestClient.Builder restClientBuilder,
            @Value("${appointments.data-service-base-url}") String baseUrl
    ) {
        this.restClient = restClientBuilder.baseUrl(baseUrl).build();
    }

    public ResponseEntity<Object> searchAppointments(Map<String, String> params) {
        return restClient.get()
                .uri(uriBuilder -> buildUriWithParams(uriBuilder, params))
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

    private URI buildUriWithParams(UriBuilder uriBuilder, Map<String, String> params) {
        UriBuilder builder = uriBuilder.path("/api/appointments/search");
        for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }
        return builder.build();
    }
}