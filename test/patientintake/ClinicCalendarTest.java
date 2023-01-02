package patientintake;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClinicCalendarTest {

   private ClinicCalendar calendar;

   @BeforeEach
   public void init() {
      calendar = new ClinicCalendar(LocalDate.of(2023, 1, 2));
//      ClinicCalendar calendar = new ClinicCalendar(LocalDate.now());
   }

   @Test
   void allowEntryOfAnAppointment() {
      calendar.addAppointment("Jim", "Weaver", "avery",
         "09/01/2018 2:00 pm");
      List<PatientAppointment> appointments = calendar.getAppointments();

      assertNotNull(appointments);
      assertEquals(1, appointments.size());

      PatientAppointment enteredAppt = appointments.get(0);
      // several assertions but logicaly just one: the user is correct
      assertAll(
           () -> assertEquals("Jim", enteredAppt.getPatientFirstName()),
           () -> assertEquals("Weaver", enteredAppt.getPatientLastName()),
           () -> assertSame(Doctor.avery, enteredAppt.getDoctor()),
           () -> assertEquals("9/1/2018 02:00 PM", enteredAppt.getAppointmentDateTime()
              .format(DateTimeFormatter.ofPattern("M/d/yyyy hh:mm a", Locale.US)))
      );
   }

   @Test
   void returnTrueForHasAppointmentsIfThereAreAppointments() {
      calendar.addAppointment("Jim",
                      "Weaver", "avery",
                      "09/01/2018 2:00 pm");
      assertTrue(calendar.hasAppointment(LocalDate.of(2018, 9, 1)));
   }

   @Test
   void returnFalseForHasAppointmentsIfThereAreNoAppointments() {
      assertFalse(calendar.hasAppointment(LocalDate.of(2018, 9, 1)));
   }

   @Test
   void returnCurrentDaysAppointments() {
      calendar.addAppointment("Jim", "Weaver", "avery",
              "01/02/2023 2:00 pm");
      calendar.addAppointment("Jim", "Weaver", "avery",
              "01/02/2023 3:00 pm");
      calendar.addAppointment("Jim", "Weaver", "avery",
              "01/01/2023 2:00 pm");

      List<PatientAppointment> todayAppoints = calendar.getTodayAppointments();
      assertEquals(2, todayAppoints.size());
//      assertIterableEquals(calendar.getAppointments(), calendar.getTodayAppointments());
   }
}