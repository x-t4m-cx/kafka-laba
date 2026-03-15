package org.example.dataservice.repository;

import org.example.dataservice.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("select a from Appointment a " +
           "where (cast(:patientName as string) is null or a.patient.name ilike %:patientName%) " +
           "and (cast(:doctorName as string) is null or a.doctor.name ilike %:doctorName%) " +
           "and (cast(:fromDateTime as timestamp) is null or a.appointmentTime >= :fromDateTime) " +
           "and (cast(:toDateTime as timestamp) is null or a.appointmentTime <= :toDateTime)")
    List<Appointment> search(
            @Param("patientName") String patientName,
            @Param("doctorName") String doctorName,
            @Param("fromDateTime") LocalDateTime fromDateTime,
            @Param("toDateTime") LocalDateTime toDateTime
    );

    interface AppointmentsPerDayProjection {
        String getDay();
        Long getCount();
    }

    interface TopPatientsProjection {
        String getPatientName();
        Long getAppointmentsCount();
    }

    interface TopDoctorsProjection {
        String getDoctorName();
        Long getAppointmentsCount();
    }

    @Query(
            value = """
                    select to_char(a.appointment_time, 'YYYY-MM-DD') as day,
                           count(*) as count
                    from appointments a
                    group by to_char(a.appointment_time, 'YYYY-MM-DD')
                    order by day
                    """,
            nativeQuery = true
    )
    List<AppointmentsPerDayProjection> getAppointmentsPerDay();

    @Query(
            value = """
                    select p.name as patientName,
                           count(*) as appointmentsCount
                    from appointments a
                             join patients p on a.patient_id = p.id
                    group by p.name
                    order by appointmentsCount desc
                    limit 10
                    """,
            nativeQuery = true
    )
    List<TopPatientsProjection> getTopPatients();

    @Query(
            value = """
                    select d.name as doctorName,
                           count(*) as appointmentsCount
                    from appointments a
                             join doctors d on a.doctor_id = d.id
                    group by d.name
                    order by appointmentsCount desc
                    limit 10
                    """,
            nativeQuery = true
    )
    List<TopDoctorsProjection> getTopDoctors();
}

