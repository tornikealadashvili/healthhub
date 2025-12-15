package org.healthhub.tests;

import org.healthhub.model.Appointment;
import org.healthhub.model.Doctor;
import org.healthhub.model.Patient;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import java.time.LocalDate;
import java.time.LocalDateTime;
import static org.testng.Assert.*;

public class AppointmentTest {

    private static int suiteCounter = 0;
    private static int testCounter = 0;
    private static int classCounter = 0;
    private static int methodCounter = 0;
    
    private Patient patient;
    private Doctor doctor;
    private Appointment appointment;
    private SoftAssert softAssert;
    
    @BeforeSuite
    public void beforeSuite() {
        suiteCounter++;
        System.out.println("AppointmentTest - BeforeSuite executed. Suite counter: " + suiteCounter);
    }
    
    @AfterSuite
    public void afterSuite() {
        System.out.println("AppointmentTest - AfterSuite executed. Suite counter: " + suiteCounter);
    }
    
    @BeforeTest
    public void beforeTest() {
        testCounter++;
        System.out.println("AppointmentTest - BeforeTest executed. Test counter: " + testCounter);
    }
    
    @AfterTest
    public void afterTest() {
        System.out.println("AppointmentTest - AfterTest executed. Test counter: " + testCounter);
    }
    
    @BeforeClass
    public void beforeClass() {
        classCounter++;
        System.out.println("AppointmentTest - BeforeClass executed. Class counter: " + classCounter);
    }
    
    @AfterClass
    public void afterClass() {
        Appointment.AppointmentScheduler.clearAllAppointments();
        System.out.println("AppointmentTest - AfterClass executed. Class counter: " + classCounter);
    }
    
    @BeforeGroups(groups = {"scheduler", "status"})
    public void beforeGroups() {
        System.out.println("AppointmentTest - BeforeGroups executed for scheduler and status groups");
    }
    
    @AfterGroups(groups = {"scheduler", "status"})
    public void afterGroups() {
        System.out.println("AppointmentTest - AfterGroups executed for scheduler and status groups");
    }
    
    @BeforeMethod
    public void beforeMethod() {
        methodCounter++;
        patient = new Patient("PAT-001", "John", "Doe", LocalDate.of(1990, 5, 15));
        doctor = new Doctor("DOC-001", "AB123456");
        doctor.setFirstName("Dr. Sarah");
        doctor.setLastName("Johnson");
        doctor.setSpecialization("Cardiology");
        appointment = new Appointment();
        appointment.setAppointmentId("APT-001");
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDateTime(LocalDateTime.now().plusDays(1));
        appointment.setStatus(Appointment.AppointmentStatus.SCHEDULED);
        softAssert = new SoftAssert();
        System.out.println("AppointmentTest - BeforeMethod executed. Method counter: " + methodCounter);
    }
    
    @AfterMethod
    public void afterMethod() {
        if (softAssert != null) {
            softAssert.assertAll();
        }
        appointment = null;
        patient = null;
        doctor = null;
        System.out.println("AppointmentTest - AfterMethod executed");
    }
    
    @Test(groups = "status", priority = 1)
    public void testAppointmentCreation() {
        softAssert.assertNotNull(appointment, "Appointment should not be null");
        softAssert.assertEquals(appointment.getPatient(), patient, "Patient should match");
        softAssert.assertEquals(appointment.getDoctor(), doctor, "Doctor should match");
        softAssert.assertEquals(appointment.getStatus(), Appointment.AppointmentStatus.SCHEDULED, "Status should be SCHEDULED");
        softAssert.assertAll();
    }
    
    @Test(groups = "status", priority = 2)
    public void testAppointmentStatusEnum() {
        softAssert.assertEquals(Appointment.AppointmentStatus.SCHEDULED.getDisplayName(), "Scheduled", "Scheduled display name should match");
        softAssert.assertEquals(Appointment.AppointmentStatus.COMPLETED.getDisplayName(), "Completed", "Completed display name should match");
        softAssert.assertEquals(Appointment.AppointmentStatus.CANCELLED.getDisplayName(), "Cancelled", "Cancelled display name should match");
        softAssert.assertNotNull(Appointment.AppointmentStatus.values(), "Status values should not be null");
        softAssert.assertAll();
    }
    
    @Test(groups = "status", priority = 3)
    public void testCancelAppointment() {
        appointment.cancel();
        softAssert.assertEquals(appointment.getStatus(), Appointment.AppointmentStatus.CANCELLED, "Status should be CANCELLED");
        softAssert.assertAll();
    }
    
    @Test(groups = "status", priority = 4)
    public void testCompleteAppointment() {
        appointment.complete();
        softAssert.assertEquals(appointment.getStatus(), Appointment.AppointmentStatus.COMPLETED, "Status should be COMPLETED");
        softAssert.assertAll();
    }
    
    @Test(groups = "status", priority = 5)
    public void testSetAppointmentStatus() {
        appointment.setStatus(Appointment.AppointmentStatus.CONFIRMED);
        softAssert.assertEquals(appointment.getStatus(), Appointment.AppointmentStatus.CONFIRMED, "Status should be CONFIRMED");
        softAssert.assertAll();
    }
    
    @Test(groups = "scheduler", priority = 6)
    public void testScheduleAppointment() {
        LocalDateTime dateTime = LocalDateTime.now().plusDays(2);
        Appointment scheduled = Appointment.AppointmentScheduler.scheduleAppointment(patient, doctor, dateTime);
        
        softAssert.assertNotNull(scheduled, "Scheduled appointment should not be null");
        softAssert.assertEquals(scheduled.getPatient(), patient, "Patient should match");
        softAssert.assertEquals(scheduled.getDoctor(), doctor, "Doctor should match");
        softAssert.assertAll();
    }
    
    @Test(groups = "scheduler", priority = 7)
    public void testTimeSlotAvailability() {
        LocalDateTime dateTime = LocalDateTime.now().plusDays(3);
        softAssert.assertTrue(Appointment.AppointmentScheduler.isTimeSlotAvailable(doctor, dateTime), "Time slot should be available initially");
        
        Appointment.AppointmentScheduler.scheduleAppointment(patient, doctor, dateTime);
        softAssert.assertFalse(Appointment.AppointmentScheduler.isTimeSlotAvailable(doctor, dateTime), "Time slot should not be available after scheduling");
        softAssert.assertAll();
    }
    
    @Test(groups = "scheduler", priority = 8)
    public void testGetAppointmentsForDoctor() {
        LocalDateTime dateTime1 = LocalDateTime.now().plusDays(4);
        LocalDateTime dateTime2 = LocalDateTime.now().plusDays(5);
        
        Appointment.AppointmentScheduler.scheduleAppointment(patient, doctor, dateTime1);
        Appointment.AppointmentScheduler.scheduleAppointment(patient, doctor, dateTime2);
        
        softAssert.assertEquals(Appointment.AppointmentScheduler.getAppointmentsForDoctor(doctor).size(), 2, "Should have two appointments for doctor");
        softAssert.assertAll();
    }
    
    @Test(groups = "scheduler", priority = 9)
    public void testTimeSlotEnum() {
        softAssert.assertEquals(Appointment.AppointmentScheduler.TimeSlot.MORNING_9AM.getTime(), "09:00", "Morning 9AM time should match");
        softAssert.assertEquals(Appointment.AppointmentScheduler.TimeSlot.AFTERNOON_2PM.getPeriod(), "Afternoon", "Afternoon period should match");
        softAssert.assertNotNull(Appointment.AppointmentScheduler.TimeSlot.values(), "TimeSlot values should not be null");
        softAssert.assertAll();
    }
    
    @Test(priority = 10)
    public void testSetAppointmentReason() {
        appointment.setReason("Regular checkup");
        softAssert.assertEquals(appointment.getReason(), "Regular checkup", "Reason should be set correctly");
        softAssert.assertAll();
    }
    
    @Test(priority = 11)
    public void testSetAppointmentNotes() {
        appointment.setNotes("Patient requested follow-up");
        softAssert.assertEquals(appointment.getNotes(), "Patient requested follow-up", "Notes should be set correctly");
        softAssert.assertAll();
    }
    
    @Test(priority = 12)
    public void testGetAppointmentsForPatient() {
        LocalDateTime dateTime = LocalDateTime.now().plusDays(6);
        Appointment.AppointmentScheduler.scheduleAppointment(patient, doctor, dateTime);
        
        softAssert.assertEquals(Appointment.AppointmentScheduler.getAppointmentsForPatient(patient).size(), 1, "Should have one appointment for patient");
        softAssert.assertAll();
    }
}
