package patientintake;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ClinicMain {

   private static ClinicCalendar calendar;

   public static void main(String[] args) throws Throwable {
      calendar = new ClinicCalendar();
      Scanner scanner = new Scanner(System.in);
      System.out.println("Welcome to the Patient Intake Computer System!\n\n");
      String lastOption = "";
      while (!lastOption.equalsIgnoreCase("x")) {
         lastOption = displayMenu(scanner);
      }
      System.out.println("\nExiting System...\n");
   }

   private static String displayMenu(Scanner scanner) throws Throwable {
      System.out.println("Please select an option:");
      System.out.println("1. Enter a Patient Appointment");
      System.out.println("2. View All Appointments");
      System.out.println("X.  Exit System.");
      System.out.print("Option: ");
      String option = scanner.next();
      switch (option) {
         case "1": performPatientEntry(scanner);
                 return option;
         case "2": performAllAppointments();
                 return option;
         default: System.out.println("Invalid option, please re-enter.");
                  return option;
      }
   }

   private static void performPatientEntry(Scanner scanner) {
      scanner.nextLine();
      System.out.println("\n\nPlease Enter Appointment Info:");
      System.out.print("  Patient Last Name: ");
      String lastName = scanner.nextLine();
      System.out.print("  Patient First Name: ");
      String firstName = scanner.nextLine();
      System.out.print("  Appointment Date (M/d/yyyy h:m a): ");
      String when = scanner.nextLine();
      System.out.print("  Doctor Last Name: ");
      String doc = scanner.nextLine();
      try {
         calendar.addAppointment(firstName, lastName, doc, when);
      } catch (Throwable t) {
         System.out.println("Error!  " + t.getMessage());
         return;
      }
      System.out.println("Patient entered successfully.\n\n");
   }

   private static void performAllAppointments() throws Throwable {
      System.out.println("\n\nAll Appointments in System:");
      for (PatientAppointment appointment : calendar.getAppointments()) {
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy hh:mm a");
         String apptTime = formatter.format(appointment.getAppointmentDateTime());
         System.out.println(String.format("%s:  %s, %s\t\tDoctor: %s", apptTime, appointment.getPatientLastName(),
            appointment.getPatientFirstName(), appointment.getDoctor().getName()));
      }
      System.out.println("\nPress any key to continue...");
      System.in.read();
      System.out.println("\n\n");
   }

   private static void performHeightWeight(Scanner scanner) {
      scanner.nextLine();
      System.out.println("\n\n Enter patient height and wiehgt for today appointment");
      System.out.println(" Patient last name");
      String lastName = scanner.nextLine();
      System.out.println("Patient First Name:");
      String firstName = scanner.nextLine();

      PatientAppointment appt = findPatientAppointment(lastName, firstName).orElse(null);
      if (appt != null) {
         System.out.println("Height in inches");
         int inches = scanner.nextInt();
         System.out.println("Weight in pounds");
         int pounds = scanner.nextInt();

         double roundedTwoPlaces = BMICalculator.calculateBMI(inches, pounds);
         appt.setBMI(roundedTwoPlaces);
         System.out.println("Set BMI to " + roundedTwoPlaces + "\n");
      } else {
         System.out.println("Patient not found\n\n");
      }
   }

   private static Optional<PatientAppointment> findPatientAppointment(String lastName, String firstName) {
      List<PatientAppointment> filteredPatient = calendar.getAppointments()
              .stream()
              .filter(appointment ->
                           appointment.getPatientLastName().equals(lastName)
                                   && appointment.getPatientFirstName().equals(firstName))
              .collect(Collectors.toList());

      if (filteredPatient.isEmpty()) {
         return Optional.empty();
      } else {
         return Optional.of(filteredPatient.get(0));
      }
   }
}
