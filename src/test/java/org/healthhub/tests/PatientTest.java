package org.healthhub.tests;

import org.healthhub.model.MedicalRecord;
import org.healthhub.model.Patient;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import java.time.LocalDate;
import java.time.LocalDateTime;
import static org.testng.Assert.*;

public class PatientTest {

    private static int suiteCounter = 0;
    private static int testCounter = 0;
    private static int classCounter = 0;
    private static int methodCounter = 0;
    
    private Patient patient;
    private String testPatientId;
    
    @BeforeSuite
    public void beforeSuite() {
        suiteCounter++;
        System.out.println("PatientTest - BeforeSuite executed. Suite counter: " + suiteCounter);
    }
    
    @AfterSuite
    public void afterSuite() {
        System.out.println("PatientTest - AfterSuite executed. Suite counter: " + suiteCounter);
    }
    
    @BeforeTest
    public void beforeTest() {
        testCounter++;
        System.out.println("PatientTest - BeforeTest executed. Test counter: " + testCounter);
    }
    
    @AfterTest
    public void afterTest() {
        System.out.println("PatientTest - AfterTest executed. Test counter: " + testCounter);
    }
    
    @BeforeClass
    public void beforeClass() {
        classCounter++;
        System.out.println("PatientTest - BeforeClass executed. Class counter: " + classCounter);
    }
    
    @AfterClass
    public void afterClass() {
        System.out.println("PatientTest - AfterClass executed. Class counter: " + classCounter);
    }
    
    @BeforeGroups(groups = {"validation", "builder"})
    public void beforeGroups() {
        System.out.println("PatientTest - BeforeGroups executed for validation and builder groups");
    }
    
    @AfterGroups(groups = {"validation", "builder"})
    public void afterGroups() {
        System.out.println("PatientTest - AfterGroups executed for validation and builder groups");
    }
    
    @BeforeMethod
    public void beforeMethod() {
        methodCounter++;
        testPatientId = "PAT-" + System.currentTimeMillis();
        patient = new Patient(testPatientId, "John", "Doe", LocalDate.of(1990, 5, 15));
        System.out.println("PatientTest - BeforeMethod executed. Method counter: " + methodCounter);
    }
    
    @AfterMethod
    public void afterMethod() {
        patient = null;
        System.out.println("PatientTest - AfterMethod executed");
    }
    
    @Test(groups = "validation", priority = 1)
    public void testPatientCreation() {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(patient, "Patient should not be null");
        softAssert.assertEquals(patient.getPatientId(), testPatientId, "Patient ID should match");
        softAssert.assertEquals(patient.getFirstName(), "John", "First name should be John");
        softAssert.assertEquals(patient.getLastName(), "Doe", "Last name should be Doe");
        softAssert.assertEquals(patient.getDateOfBirth(), LocalDate.of(1990, 5, 15), "Date of birth should match");
        softAssert.assertAll();
    }
    
    @Test(groups = "validation", priority = 2)
    public void testPatientAgeCalculation() {
        SoftAssert softAssert = new SoftAssert();
        LocalDate birthDate = LocalDate.now().minusYears(30);
        Patient testPatient = new Patient("PAT-001", "Test", "User", birthDate);
        softAssert.assertEquals(testPatient.getAge(), 30, "Age should be 30");
        softAssert.assertAll();
    }
    
    @Test(groups = "validation", priority = 3)
    public void testEmailValidation() {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(Patient.PatientValidator.isValidEmail("john.doe@example.com"), "Valid email should pass");
        softAssert.assertFalse(Patient.PatientValidator.isValidEmail("invalid-email"), "Invalid email should fail");
        softAssert.assertFalse(Patient.PatientValidator.isValidEmail(null), "Null email should fail");
        softAssert.assertAll();
    }
    
    @Test(groups = "validation", priority = 4)
    public void testPhoneNumberValidation() {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(Patient.PatientValidator.isValidPhoneNumber("1234567890"), "10 digit phone should be valid");
        softAssert.assertTrue(Patient.PatientValidator.isValidPhoneNumber("123456789012345"), "15 digit phone should be valid");
        softAssert.assertFalse(Patient.PatientValidator.isValidPhoneNumber("123"), "Short phone should be invalid");
        softAssert.assertFalse(Patient.PatientValidator.isValidPhoneNumber(null), "Null phone should be invalid");
        softAssert.assertAll();
    }
    
    @Test(groups = "validation", priority = 5)
    public void testPatientIdValidation() {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(Patient.PatientValidator.isValidPatientId("PAT-001"), "Valid patient ID should pass");
        softAssert.assertFalse(Patient.PatientValidator.isValidPatientId(""), "Empty patient ID should fail");
        softAssert.assertFalse(Patient.PatientValidator.isValidPatientId(null), "Null patient ID should fail");
        softAssert.assertAll();
    }
    
    @Test(groups = "builder", priority = 6)
    public void testPatientBuilder() {
        SoftAssert softAssert = new SoftAssert();
        Patient builtPatient = new Patient.PatientBuilder()
                .setPatientId("PAT-002")
                .setFirstName("Jane")
                .setLastName("Smith")
                .setDateOfBirth(LocalDate.of(1985, 3, 20))
                .withEmail("jane.smith@example.com")
                .withPhoneNumber("9876543210")
                .build();
        
        softAssert.assertEquals(builtPatient.getFirstName(), "Jane", "First name should be Jane");
        softAssert.assertEquals(builtPatient.getEmail(), "jane.smith@example.com", "Email should match");
        softAssert.assertEquals(builtPatient.getPhoneNumber(), "9876543210", "Phone number should match");
        softAssert.assertAll();
    }
    
    @Test(priority = 7)
    public void testAddMedicalRecord() {
        SoftAssert softAssert = new SoftAssert();
        MedicalRecord record = new MedicalRecord();
        record.setRecordId("REC-001");
        record.setPatient(patient);
        record.setDoctor(null);
        record.setRecordDate(LocalDateTime.now());
        record.setStatus(MedicalRecord.RecordStatus.ACTIVE);
        patient.addMedicalRecord(record);
        
        softAssert.assertEquals(patient.getMedicalRecords().size(), 1, "Should have one medical record");
        softAssert.assertTrue(patient.getMedicalRecords().contains(record), "Should contain the added record");
        softAssert.assertAll();
    }
    
    @Test(priority = 8)
    public void testSetEmail() {
        SoftAssert softAssert = new SoftAssert();
        patient.setEmail("john.doe@example.com");
        softAssert.assertEquals(patient.getEmail(), "john.doe@example.com", "Email should be set correctly");
        softAssert.assertAll();
    }
    
    @Test(priority = 9)
    public void testSetPhoneNumber() {
        SoftAssert softAssert = new SoftAssert();
        patient.setPhoneNumber("5551234567");
        softAssert.assertEquals(patient.getPhoneNumber(), "5551234567", "Phone number should be set correctly");
        softAssert.assertAll();
    }
    
    @Test(priority = 10)
    public void testMultipleMedicalRecords() {
        SoftAssert softAssert = new SoftAssert();
        MedicalRecord record1 = new MedicalRecord();
        record1.setRecordId("REC-001");
        record1.setPatient(patient);
        record1.setDoctor(null);
        record1.setRecordDate(LocalDateTime.now());
        record1.setStatus(MedicalRecord.RecordStatus.ACTIVE);
        
        MedicalRecord record2 = new MedicalRecord();
        record2.setRecordId("REC-002");
        record2.setPatient(patient);
        record2.setDoctor(null);
        record2.setRecordDate(LocalDateTime.now());
        record2.setStatus(MedicalRecord.RecordStatus.ACTIVE);
        
        patient.addMedicalRecord(record1);
        patient.addMedicalRecord(record2);
        
        softAssert.assertEquals(patient.getMedicalRecords().size(), 2, "Should have two medical records");
        softAssert.assertTrue(patient.getMedicalRecords().contains(record1), "Should contain first record");
        softAssert.assertTrue(patient.getMedicalRecords().contains(record2), "Should contain second record");
        softAssert.assertAll();
    }
}
