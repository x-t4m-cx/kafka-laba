package org.example.dataservice.service;

import org.example.dataservice.dto.AppointmentEvent;
import org.example.dataservice.model.Appointment;
import org.example.dataservice.model.Doctor;
import org.example.dataservice.model.Patient;
import org.example.dataservice.repository.AppointmentRepository;
import org.example.dataservice.repository.DoctorRepository;
import org.example.dataservice.repository.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AppointmentService {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;

    public AppointmentService(PatientRepository patientRepository,
                              DoctorRepository doctorRepository,
                              AppointmentRepository appointmentRepository) {
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Transactional
    public void saveFromEvent(AppointmentEvent event) {
        Patient patient = patientRepository
                .findByName(event.getPatientName())
                .orElseGet(() -> patientRepository.save(new Patient(event.getPatientName())));

        Doctor doctor = doctorRepository
                .findByName(event.getDoctorName())
                .orElseGet(() -> doctorRepository.save(new Doctor(event.getDoctorName(), event.getDoctorSpecialty())));

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentTime(event.getAppointmentDateTime());
        appointment.setReason(event.getReason());

        appointmentRepository.save(appointment);
    }

    @Transactional(readOnly = true)
    public List<Appointment> search(String patientName,
                                    String doctorName,
                                    LocalDateTime from,
                                    LocalDateTime to) {
        return appointmentRepository.search(
                emptyToNull(patientName),
                emptyToNull(doctorName),
                from,
                to
        );
    }

    @Transactional(readOnly = true)
    public List<AppointmentRepository.AppointmentsPerDayProjection> getAppointmentsPerDay() {
        return appointmentRepository.getAppointmentsPerDay();
    }

    @Transactional(readOnly = true)
    public List<AppointmentRepository.TopPatientsProjection> getTopPatients() {
        return appointmentRepository.getTopPatients();
    }

    @Transactional(readOnly = true)
    public List<AppointmentRepository.TopDoctorsProjection> getTopDoctors() {
        return appointmentRepository.getTopDoctors();
    }

    public LocalDateTime parseDateTimeOrNull(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return LocalDateTime.parse(value);
    }

    public LocalDate parseDateOrNull(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return LocalDate.parse(value, DateTimeFormatter.ISO_DATE);
    }

    private String emptyToNull(String value) {
        return (value == null || value.isBlank()) ? null : value;
    }
}

