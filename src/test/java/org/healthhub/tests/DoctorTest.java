package org.healthhub.tests;

import org.healthhub.model.Doctor;
import org.healthhub.model.Patient;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import java.time.LocalDate;
import static org.testng.Assert.*;

public class DoctorTest {

    private static int suiteCounter = 0;
    private static int testCounter = 0;
    private static int classCounter = 0;
    private static int methodCounter = 0;
    
    private Doctor doctor;
    private String testDoctorId;
    private SoftAssert softAssert;
    
    @BeforeSuite
    public void beforeSuite() {
        suiteCounter++;
        System.out.println("DoctorTest - BeforeSuite executed. Suite counter: " + suiteCounter);
    }
    
    @AfterSuite
    public void afterSuite() {
        System.out.println("DoctorTest - AfterSuite executed. Suite counter: " + suiteCounter);
    }
    
    @BeforeTest
    public void beforeTest() {
        testCounter++;
        System.out.println("DoctorTest - BeforeTest executed. Test counter: " + testCounter);
    }
    
    @AfterTest
    public void afterTest() {
        System.out.println("DoctorTest - AfterTest executed. Test counter: " + testCounter);
    }
    
    @BeforeClass
    public void beforeClass() {
        classCounter++;
        System.out.println("DoctorTest - BeforeClass executed. Class counter: " + classCounter);
    }
    
    @AfterClass
    public void afterClass() {
        System.out.println("DoctorTest - AfterClass executed. Class counter: " + classCounter);
    }
    
    @BeforeGroups(groups = {"specialization", "validation"})
    public void beforeGroups() {
        System.out.println("DoctorTest - BeforeGroups executed for specialization and validation groups");
    }
    
    @AfterGroups(groups = {"specialization", "validation"})
    public void afterGroups() {
        System.out.println("DoctorTest - AfterGroups executed for specialization and validation groups");
    }
    
    @BeforeMethod
    public void beforeMethod() {
        methodCounter++;
        testDoctorId = "DOC-" + System.currentTimeMillis();
        doctor = new Doctor(testDoctorId, "AB123456");
        doctor.setFirstName("Dr. Sarah");
        doctor.setLastName("Johnson");
        doctor.setSpecialization("Cardiology");
        softAssert = new SoftAssert();
        System.out.println("DoctorTest - BeforeMethod executed. Method counter: " + methodCounter);
    }
    
    @AfterMethod
    public void afterMethod() {
        if (softAssert != null) {
            softAssert.assertAll();
        }
        doctor = null;
        System.out.println("DoctorTest - AfterMethod executed");
    }
    
    @Test(groups = "validation", priority = 1)
    public void testDoctorCreation() {
        softAssert.assertNotNull(doctor, "Doctor should not be null");
        softAssert.assertEquals(doctor.getDoctorId(), testDoctorId, "Doctor ID should match");
        softAssert.assertEquals(doctor.getFirstName(), "Dr. Sarah", "First name should match");
        softAssert.assertEquals(doctor.getLastName(), "Johnson", "Last name should match");
        softAssert.assertEquals(doctor.getSpecialization(), "Cardiology", "Specialization should match");
        softAssert.assertAll();
    }
    
    @Test(groups = "validation", priority = 2)
    public void testLicenseNumberValidation() {
        softAssert.assertTrue(Doctor.DoctorValidator.isValidLicenseNumber("AB123456"), "Valid license should pass");
        softAssert.assertTrue(Doctor.DoctorValidator.isValidLicenseNumber("XY987654"), "Another valid license should pass");
        softAssert.assertFalse(Doctor.DoctorValidator.isValidLicenseNumber("12345678"), "Numeric only should fail");
        softAssert.assertFalse(Doctor.DoctorValidator.isValidLicenseNumber("AB12345"), "Short license should fail");
        softAssert.assertFalse(Doctor.DoctorValidator.isValidLicenseNumber(null), "Null license should fail");
        softAssert.assertAll();
    }
    
    @Test(groups = "validation", priority = 3)
    public void testSpecializationValidation() {
        softAssert.assertTrue(Doctor.DoctorValidator.isValidSpecialization("Cardiology"), "Cardiology should be valid");
        softAssert.assertTrue(Doctor.DoctorValidator.isValidSpecialization("Neurology"), "Neurology should be valid");
        softAssert.assertFalse(Doctor.DoctorValidator.isValidSpecialization(""), "Empty specialization should fail");
        softAssert.assertFalse(Doctor.DoctorValidator.isValidSpecialization(null), "Null specialization should fail");
        softAssert.assertAll();
    }
    
    @Test(groups = "specialization", priority = 4)
    public void testSpecializationEnum() {
        softAssert.assertEquals(Doctor.Specialization.CARDIOLOGY.getDisplayName(), "Cardiology", "Cardiology display name should match");
        softAssert.assertEquals(Doctor.Specialization.NEUROLOGY.getDisplayName(), "Neurology", "Neurology display name should match");
        softAssert.assertEquals(Doctor.Specialization.PEDIATRICS.getDisplayName(), "Pediatrics", "Pediatrics display name should match");
        softAssert.assertNotNull(Doctor.Specialization.values(), "Specialization values should not be null");
        softAssert.assertEquals(Doctor.Specialization.values().length, 6, "Should have 6 specializations");
        softAssert.assertAll();
    }
    
    @Test(groups = "specialization", priority = 5)
    public void testAllSpecializations() {
        Doctor.Specialization[] specializations = Doctor.Specialization.values();
        softAssert.assertTrue(specializations.length > 0, "Should have at least one specialization");
        for (Doctor.Specialization spec : specializations) {
            softAssert.assertNotNull(spec.getDisplayName(), "Display name should not be null for " + spec);
            softAssert.assertFalse(spec.getDisplayName().isEmpty(), "Display name should not be empty for " + spec);
        }
        softAssert.assertAll();
    }
    
    @Test(priority = 6)
    public void testAddPatient() {
        Patient patient = new Patient("PAT-001", "John", "Doe", LocalDate.of(1990, 5, 15));
        doctor.addPatient(patient);
        
        softAssert.assertEquals(doctor.getPatients().size(), 1, "Should have one patient");
        softAssert.assertTrue(doctor.getPatients().contains(patient), "Should contain the added patient");
        softAssert.assertAll();
    }
    
    @Test(priority = 7)
    public void testAddDuplicatePatient() {
        Patient patient = new Patient("PAT-001", "John", "Doe", LocalDate.of(1990, 5, 15));
        doctor.addPatient(patient);
        doctor.addPatient(patient);
        
        softAssert.assertEquals(doctor.getPatients().size(), 1, "Should still have only one patient");
        softAssert.assertAll();
    }
    
    @Test(priority = 8)
    public void testRemovePatient() {
        Patient patient = new Patient("PAT-001", "John", "Doe", LocalDate.of(1990, 5, 15));
        doctor.addPatient(patient);
        doctor.removePatient(patient);
        
        softAssert.assertEquals(doctor.getPatients().size(), 0, "Should have no patients");
        softAssert.assertFalse(doctor.getPatients().contains(patient), "Should not contain removed patient");
        softAssert.assertAll();
    }
    
    @Test(priority = 9)
    public void testSetLicenseNumber() {
        doctor.setLicenseNumber("AB123456");
        softAssert.assertEquals(doctor.getLicenseNumber(), "AB123456", "License number should be set correctly");
        softAssert.assertAll();
    }
    
    @Test(priority = 10)
    public void testMultiplePatients() {
        Patient patient1 = new Patient("PAT-001", "John", "Doe", LocalDate.of(1990, 5, 15));
        Patient patient2 = new Patient("PAT-002", "Jane", "Smith", LocalDate.of(1985, 3, 20));
        
        doctor.addPatient(patient1);
        doctor.addPatient(patient2);
        
        softAssert.assertEquals(doctor.getPatients().size(), 2, "Should have two patients");
        softAssert.assertTrue(doctor.getPatients().contains(patient1), "Should contain first patient");
        softAssert.assertTrue(doctor.getPatients().contains(patient2), "Should contain second patient");
        softAssert.assertAll();
    }
}
