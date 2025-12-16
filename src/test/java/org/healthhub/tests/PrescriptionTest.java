package org.healthhub.tests;

import org.healthhub.model.Doctor;
import org.healthhub.model.Patient;
import org.healthhub.model.Prescription;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import java.time.LocalDate;
import static org.testng.Assert.*;

public class PrescriptionTest {

    private static int suiteCounter = 0;
    private static int testCounter = 0;
    private static int classCounter = 0;
    private static int methodCounter = 0;
    
    private Patient patient;
    private Doctor doctor;
    private Prescription prescription;
    
    @BeforeSuite
    public void beforeSuite() {
        suiteCounter++;
        System.out.println("PrescriptionTest - BeforeSuite executed. Suite counter: " + suiteCounter);
    }
    
    @AfterSuite
    public void afterSuite() {
        System.out.println("PrescriptionTest - AfterSuite executed. Suite counter: " + suiteCounter);
    }
    
    @BeforeTest
    public void beforeTest() {
        testCounter++;
        System.out.println("PrescriptionTest - BeforeTest executed. Test counter: " + testCounter);
    }
    
    @AfterTest
    public void afterTest() {
        System.out.println("PrescriptionTest - AfterTest executed. Test counter: " + testCounter);
    }
    
    @BeforeClass
    public void beforeClass() {
        classCounter++;
        System.out.println("PrescriptionTest - BeforeClass executed. Class counter: " + classCounter);
    }
    
    @AfterClass
    public void afterClass() {
        System.out.println("PrescriptionTest - AfterClass executed. Class counter: " + classCounter);
    }
    
    @BeforeGroups(groups = {"medication", "validation"})
    public void beforeGroups() {
        System.out.println("PrescriptionTest - BeforeGroups executed for medication and validation groups");
    }
    
    @AfterGroups(groups = {"medication", "validation"})
    public void afterGroups() {
        System.out.println("PrescriptionTest - AfterGroups executed for medication and validation groups");
    }
    
    @BeforeMethod
    public void beforeMethod() {
        methodCounter++;
        patient = new Patient("PAT-001", "John", "Doe", LocalDate.of(1990, 5, 15));
        doctor = new Doctor("DOC-001", "AB123456");
        doctor.setFirstName("Dr. Sarah");
        doctor.setLastName("Johnson");
        doctor.setSpecialization("Cardiology");
        prescription = new Prescription();
        prescription.setPrescriptionId("RX-001");
        prescription.setPatient(patient);
        prescription.setDoctor(doctor);
        prescription.setIssueDate(LocalDate.now());
        prescription.setExpiryDate(LocalDate.now().plusDays(30));
        prescription.setStatus(Prescription.PrescriptionStatus.ACTIVE);
        System.out.println("PrescriptionTest - BeforeMethod executed. Method counter: " + methodCounter);
    }
    
    @AfterMethod
    public void afterMethod() {
        prescription = null;
        patient = null;
        doctor = null;
        System.out.println("PrescriptionTest - AfterMethod executed");
    }
    
    @Test(groups = "validation", priority = 1)
    public void testPrescriptionCreation() {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(prescription, "Prescription should not be null");
        softAssert.assertEquals(prescription.getPrescriptionId(), "RX-001", "Prescription ID should match");
        softAssert.assertEquals(prescription.getPatient(), patient, "Patient should match");
        softAssert.assertEquals(prescription.getDoctor(), doctor, "Doctor should match");
        softAssert.assertEquals(prescription.getStatus(), Prescription.PrescriptionStatus.ACTIVE, "Status should be ACTIVE");
        softAssert.assertAll();
    }
    
    @Test(groups = "validation", priority = 2)
    public void testPrescriptionIdValidation() {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(Prescription.PrescriptionValidator.isValidPrescriptionId("RX-001"), "Valid prescription ID should pass");
        softAssert.assertTrue(Prescription.PrescriptionValidator.isValidPrescriptionId("RX-ABC123"), "Another valid prescription ID should pass");
        softAssert.assertFalse(Prescription.PrescriptionValidator.isValidPrescriptionId("001"), "Invalid prescription ID should fail");
        softAssert.assertFalse(Prescription.PrescriptionValidator.isValidPrescriptionId(null), "Null prescription ID should fail");
        softAssert.assertAll();
    }
    
    @Test(groups = "validation", priority = 3)
    public void testHasMedicationsValidation() {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertFalse(Prescription.PrescriptionValidator.hasMedications(prescription), "Empty prescription should have no medications");
        
        Prescription.Medication medication = new Prescription.Medication();
        medication.setName("Aspirin");
        medication.setDosage("100mg");
        medication.setQuantity(30);
        medication.setFrequency("Once daily");
        prescription.addMedication(medication);
        
        softAssert.assertTrue(Prescription.PrescriptionValidator.hasMedications(prescription), "Prescription with medication should pass");
        softAssert.assertAll();
    }
    
    @Test(groups = "validation", priority = 4)
    public void testExpiryDateValidation() {
        SoftAssert softAssert = new SoftAssert();
        LocalDate futureDate = LocalDate.now().plusDays(30);
        softAssert.assertTrue(Prescription.PrescriptionValidator.isValidExpiryDate(futureDate), "Future date should be valid");
        
        LocalDate pastDate = LocalDate.now().minusDays(1);
        softAssert.assertFalse(Prescription.PrescriptionValidator.isValidExpiryDate(pastDate), "Past date should be invalid");
        softAssert.assertAll();
    }
    
    @Test(groups = "validation", priority = 5)
    public void testPrescriptionStatusEnum() {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(Prescription.PrescriptionStatus.ACTIVE.getDisplayName(), "Active", "Active display name should match");
        softAssert.assertEquals(Prescription.PrescriptionStatus.EXPIRED.getDisplayName(), "Expired", "Expired display name should match");
        softAssert.assertEquals(Prescription.PrescriptionStatus.CANCELLED.getDisplayName(), "Cancelled", "Cancelled display name should match");
        softAssert.assertNotNull(Prescription.PrescriptionStatus.values(), "Status values should not be null");
        softAssert.assertAll();
    }
    
    @Test(groups = "medication", priority = 6)
    public void testAddMedication() {
        SoftAssert softAssert = new SoftAssert();
        Prescription.Medication medication = new Prescription.Medication();
        medication.setName("Aspirin");
        medication.setDosage("100mg");
        medication.setQuantity(30);
        medication.setFrequency("Once daily");
        prescription.addMedication(medication);
        
        softAssert.assertEquals(prescription.getMedications().size(), 1, "Should have one medication");
        softAssert.assertEquals(prescription.getMedications().get(0).getName(), "Aspirin", "Medication name should match");
        softAssert.assertAll();
    }
    
    @Test(groups = "medication", priority = 7)
    public void testMedicationProperties() {
        SoftAssert softAssert = new SoftAssert();
        Prescription.Medication medication = new Prescription.Medication();
        medication.setName("Ibuprofen");
        medication.setDosage("200mg");
        medication.setQuantity(60);
        medication.setFrequency("Twice daily");
        
        softAssert.assertEquals(medication.getName(), "Ibuprofen", "Name should match");
        softAssert.assertEquals(medication.getDosage(), "200mg", "Dosage should match");
        softAssert.assertEquals(medication.getQuantity(), 60, "Quantity should match");
        softAssert.assertEquals(medication.getFrequency(), "Twice daily", "Frequency should match");
        softAssert.assertAll();
    }
    
    @Test(groups = "medication", priority = 8)
    public void testMultipleMedications() {
        SoftAssert softAssert = new SoftAssert();
        Prescription.Medication med1 = new Prescription.Medication();
        med1.setName("Aspirin");
        med1.setDosage("100mg");
        med1.setQuantity(30);
        med1.setFrequency("Once daily");
        
        Prescription.Medication med2 = new Prescription.Medication();
        med2.setName("Vitamin D");
        med2.setDosage("1000IU");
        med2.setQuantity(90);
        med2.setFrequency("Once daily");
        
        prescription.addMedication(med1);
        prescription.addMedication(med2);
        
        softAssert.assertEquals(prescription.getMedications().size(), 2, "Should have two medications");
        softAssert.assertTrue(prescription.getMedications().contains(med1), "Should contain first medication");
        softAssert.assertTrue(prescription.getMedications().contains(med2), "Should contain second medication");
        softAssert.assertAll();
    }
    
    @Test(priority = 9)
    public void testPrescriptionExpiry() {
        SoftAssert softAssert = new SoftAssert();
        prescription.setExpiryDate(LocalDate.now().minusDays(1));
        softAssert.assertTrue(prescription.isExpired(), "Prescription should be expired");
        softAssert.assertAll();
    }
    
    @Test(priority = 10)
    public void testExpirePrescription() {
        SoftAssert softAssert = new SoftAssert();
        prescription.expire();
        softAssert.assertEquals(prescription.getStatus(), Prescription.PrescriptionStatus.EXPIRED, "Status should be EXPIRED");
        softAssert.assertAll();
    }
    
    @Test(priority = 11)
    public void testSetInstructions() {
        SoftAssert softAssert = new SoftAssert();
        prescription.setInstructions("Take with food");
        softAssert.assertEquals(prescription.getInstructions(), "Take with food", "Instructions should be set correctly");
        softAssert.assertAll();
    }
    
    @Test(priority = 12)
    public void testDefaultExpiryDate() {
        SoftAssert softAssert = new SoftAssert();
        LocalDate expectedExpiry = LocalDate.now().plusDays(30);
        softAssert.assertEquals(prescription.getExpiryDate(), expectedExpiry, "Default expiry date should be 30 days from now");
        softAssert.assertAll();
    }
}
