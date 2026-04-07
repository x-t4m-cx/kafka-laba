package org.example.apiservice.service;

import lombok.RequiredArgsConstructor;
import org.example.apiservice.client.rest.AppointmentApiClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final AppointmentApiClient apiClient;

    public ResponseEntity<Object> getAppointmentsPerDay() {
        return apiClient.getAppointmentsPerDay();
    }

    public ResponseEntity<Object> getTopPatients() {
        return apiClient.getTopPatients();
    }

    public ResponseEntity<Object> getTopDoctors() {
        return apiClient.getTopDoctors();
    }
}
