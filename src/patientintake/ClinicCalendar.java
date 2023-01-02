package patientintake;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class ClinicCalendar {

   private final List<PatientAppointment> appointments;
   private final LocalDate today;

   public ClinicCalendar() {
      this.today = LocalDate.now();
      this.appointments = new ArrayList<>();
   }

   public ClinicCalendar(LocalDate today) {
      this.today = today;
      this.appointments = new ArrayList<>();
   }

   public void addAppointment(String patientFirstName, String patientLastName, String doctorKey,
                              String dateTime) {
      Doctor doc = Doctor.valueOf(doctorKey.toLowerCase());
      LocalDateTime localDateTime = DateTimeConverter.convertToDateFromString(dateTime, today);
      PatientAppointment appointment = new PatientAppointment(patientFirstName, patientLastName,
         localDateTime, doc);
      appointments.add(appointment);
   }

   public List<PatientAppointment> getAppointments() {
      return this.appointments;
   }

   public boolean hasAppointment(LocalDate date) {
      return appointments.stream()
              .anyMatch(appt -> appt.getAppointmentDateTime().toLocalDate().equals(date));
   }

   public List<PatientAppointment> getTodayAppointments() {
      return appointments.stream()
              .filter(appt -> LocalDate.from(appt.getAppointmentDateTime()).equals(today))
              .collect(Collectors.toList());
   }
}
