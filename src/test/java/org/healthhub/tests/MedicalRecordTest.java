package org.healthhub.tests;

import org.healthhub.model.Doctor;
import org.healthhub.model.MedicalRecord;
import org.healthhub.model.Patient;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import java.time.LocalDate;
import java.time.LocalDateTime;
import static org.testng.Assert.*;

public class MedicalRecordTest {

    private static int suiteCounter = 0;
    private static int testCounter = 0;
    private static int classCounter = 0;
    private static int methodCounter = 0;
    
    private Patient patient;
    private Doctor doctor;
    private MedicalRecord medicalRecord;
    private SoftAssert softAssert;
    
    @BeforeSuite
    public void beforeSuite() {
        suiteCounter++;
        System.out.println("MedicalRecordTest - BeforeSuite executed. Suite counter: " + suiteCounter);
    }
    
    @AfterSuite
    public void afterSuite() {
        System.out.println("MedicalRecordTest - AfterSuite executed. Suite counter: " + suiteCounter);
    }
    
    @BeforeTest
    public void beforeTest() {
        testCounter++;
        System.out.println("MedicalRecordTest - BeforeTest executed. Test counter: " + testCounter);
    }
    
    @AfterTest
    public void afterTest() {
        System.out.println("MedicalRecordTest - AfterTest executed. Test counter: " + testCounter);
    }
    
    @BeforeClass
    public void beforeClass() {
        classCounter++;
        System.out.println("MedicalRecordTest - BeforeClass executed. Class counter: " + classCounter);
    }
    
    @AfterClass
    public void afterClass() {
        MedicalRecord.MedicalRecordManager.clearAllRecords();
        System.out.println("MedicalRecordTest - AfterClass executed. Class counter: " + classCounter);
    }
    
    @BeforeGroups(groups = {"manager", "types"})
    public void beforeGroups() {
        System.out.println("MedicalRecordTest - BeforeGroups executed for manager and types groups");
    }
    
    @AfterGroups(groups = {"manager", "types"})
    public void afterGroups() {
        System.out.println("MedicalRecordTest - AfterGroups executed for manager and types groups");
    }
    
    @BeforeMethod
    public void beforeMethod() {
        methodCounter++;
        patient = new Patient("PAT-001", "John", "Doe", LocalDate.of(1990, 5, 15));
        doctor = new Doctor("DOC-001", "AB123456");
        doctor.setFirstName("Dr. Sarah");
        doctor.setLastName("Johnson");
        doctor.setSpecialization("Cardiology");
        medicalRecord = new MedicalRecord();
        medicalRecord.setRecordId("REC-001");
        medicalRecord.setPatient(patient);
        medicalRecord.setDoctor(doctor);
        medicalRecord.setRecordDate(LocalDateTime.now());
        medicalRecord.setStatus(MedicalRecord.RecordStatus.ACTIVE);
        softAssert = new SoftAssert();
        System.out.println("MedicalRecordTest - BeforeMethod executed. Method counter: " + methodCounter);
    }
    
    @AfterMethod
    public void afterMethod() {
        if (softAssert != null) {
            softAssert.assertAll();
        }
        medicalRecord = null;
        patient = null;
        doctor = null;
        System.out.println("MedicalRecordTest - AfterMethod executed");
    }
    
    @Test(groups = "types", priority = 1)
    public void testMedicalRecordCreation() {
        softAssert.assertNotNull(medicalRecord, "Medical record should not be null");
        softAssert.assertEquals(medicalRecord.getRecordId(), "REC-001", "Record ID should match");
        softAssert.assertEquals(medicalRecord.getPatient(), patient, "Patient should match");
        softAssert.assertEquals(medicalRecord.getDoctor(), doctor, "Doctor should match");
        softAssert.assertEquals(medicalRecord.getStatus(), MedicalRecord.RecordStatus.ACTIVE, "Status should be ACTIVE");
        softAssert.assertAll();
    }
    
    @Test(groups = "types", priority = 2)
    public void testRecordTypeEnum() {
        softAssert.assertEquals(MedicalRecord.RecordType.CONSULTATION.getDisplayName(), "Consultation", "Consultation display name should match");
        softAssert.assertEquals(MedicalRecord.RecordType.LAB_RESULT.getDisplayName(), "Lab Result", "Lab Result display name should match");
        softAssert.assertEquals(MedicalRecord.RecordType.EMERGENCY.getDisplayName(), "Emergency", "Emergency display name should match");
        softAssert.assertNotNull(MedicalRecord.RecordType.values(), "Record type values should not be null");
        softAssert.assertAll();
    }
    
    @Test(groups = "types", priority = 3)
    public void testRecordStatusEnum() {
        softAssert.assertEquals(MedicalRecord.RecordStatus.ACTIVE.getDisplayName(), "Active", "Active display name should match");
        softAssert.assertEquals(MedicalRecord.RecordStatus.ARCHIVED.getDisplayName(), "Archived", "Archived display name should match");
        softAssert.assertEquals(MedicalRecord.RecordStatus.DELETED.getDisplayName(), "Deleted", "Deleted display name should match");
        softAssert.assertNotNull(MedicalRecord.RecordStatus.values(), "Record status values should not be null");
        softAssert.assertAll();
    }
    
    @Test(groups = "types", priority = 4)
    public void testSetRecordType() {
        medicalRecord.setType(MedicalRecord.RecordType.CONSULTATION);
        softAssert.assertEquals(medicalRecord.getType(), MedicalRecord.RecordType.CONSULTATION, "Type should be CONSULTATION");
        
        medicalRecord.setType(MedicalRecord.RecordType.LAB_RESULT);
        softAssert.assertEquals(medicalRecord.getType(), MedicalRecord.RecordType.LAB_RESULT, "Type should be LAB_RESULT");
        softAssert.assertAll();
    }
    
    @Test(groups = "types", priority = 5)
    public void testArchiveRecord() {
        medicalRecord.archive();
        softAssert.assertEquals(medicalRecord.getStatus(), MedicalRecord.RecordStatus.ARCHIVED, "Status should be ARCHIVED");
        softAssert.assertAll();
    }
    
    @Test(groups = "manager", priority = 6)
    public void testAddRecordToManager() {
        MedicalRecord.MedicalRecordManager.addRecord(medicalRecord);
        softAssert.assertEquals(MedicalRecord.MedicalRecordManager.getTotalRecordCount(), 1, "Should have one record");
        softAssert.assertAll();
    }
    
    @Test(groups = "manager", priority = 7)
    public void testGetRecordsForPatient() {
        MedicalRecord.MedicalRecordManager.addRecord(medicalRecord);
        Patient patient2 = new Patient("PAT-002", "Jane", "Smith", LocalDate.of(1985, 3, 20));
        MedicalRecord record2 = new MedicalRecord();
        record2.setRecordId("REC-002");
        record2.setPatient(patient2);
        record2.setDoctor(doctor);
        record2.setRecordDate(LocalDateTime.now());
        record2.setStatus(MedicalRecord.RecordStatus.ACTIVE);
        MedicalRecord.MedicalRecordManager.addRecord(record2);
        
        softAssert.assertEquals(MedicalRecord.MedicalRecordManager.getRecordsForPatient(patient).size(), 1, "Should have one record for patient");
        softAssert.assertEquals(MedicalRecord.MedicalRecordManager.getRecordsForPatient(patient2).size(), 1, "Should have one record for patient2");
        softAssert.assertAll();
    }
    
    @Test(groups = "manager", priority = 8)
    public void testGetRecordsByType() {
        medicalRecord.setType(MedicalRecord.RecordType.CONSULTATION);
        MedicalRecord.MedicalRecordManager.addRecord(medicalRecord);
        
        MedicalRecord record2 = new MedicalRecord();
        record2.setRecordId("REC-002");
        record2.setPatient(patient);
        record2.setDoctor(doctor);
        record2.setRecordDate(LocalDateTime.now());
        record2.setStatus(MedicalRecord.RecordStatus.ACTIVE);
        record2.setType(MedicalRecord.RecordType.LAB_RESULT);
        MedicalRecord.MedicalRecordManager.addRecord(record2);
        
        softAssert.assertEquals(MedicalRecord.MedicalRecordManager.getRecordsByType(MedicalRecord.RecordType.CONSULTATION).size(), 1, "Should have one consultation record");
        softAssert.assertEquals(MedicalRecord.MedicalRecordManager.getRecordsByType(MedicalRecord.RecordType.LAB_RESULT).size(), 1, "Should have one lab result record");
        softAssert.assertAll();
    }
    
    @Test(priority = 9)
    public void testSetDiagnosis() {
        medicalRecord.setDiagnosis("Hypertension");
        softAssert.assertEquals(medicalRecord.getDiagnosis(), "Hypertension", "Diagnosis should be set correctly");
        softAssert.assertAll();
    }
    
    @Test(priority = 10)
    public void testSetSymptoms() {
        medicalRecord.setSymptoms("Headache, dizziness");
        softAssert.assertEquals(medicalRecord.getSymptoms(), "Headache, dizziness", "Symptoms should be set correctly");
        softAssert.assertAll();
    }
    
    @Test(priority = 11)
    public void testAddTestResult() {
        medicalRecord.addTestResult("Blood pressure: 140/90");
        medicalRecord.addTestResult("Heart rate: 72 bpm");
        
        softAssert.assertEquals(medicalRecord.getTestResults().size(), 2, "Should have two test results");
        softAssert.assertTrue(medicalRecord.getTestResults().contains("Blood pressure: 140/90"), "Should contain first test result");
        softAssert.assertTrue(medicalRecord.getTestResults().contains("Heart rate: 72 bpm"), "Should contain second test result");
        softAssert.assertAll();
    }
    
    @Test(priority = 12)
    public void testSetTreatment() {
        medicalRecord.setTreatment("Prescribed medication and lifestyle changes");
        softAssert.assertEquals(medicalRecord.getTreatment(), "Prescribed medication and lifestyle changes", "Treatment should be set correctly");
        softAssert.assertAll();
    }
}
