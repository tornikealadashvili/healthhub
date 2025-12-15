package org.healthhub.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MedicalRecord {

    private String recordId;
    private Patient patient;
    private Doctor doctor;
    private LocalDateTime recordDate;
    private String diagnosis;
    private String symptoms;
    private String treatment;
    private List<String> testResults;
    private RecordType type;
    private RecordStatus status;
    
    public String getRecordId() {
        return recordId;
    }
    
    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }
    
    public Patient getPatient() {
        return patient;
    }
    
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    
    public Doctor getDoctor() {
        return doctor;
    }
    
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
    
    public LocalDateTime getRecordDate() {
        return recordDate;
    }
    
    public void setRecordDate(LocalDateTime recordDate) {
        this.recordDate = recordDate;
    }
    
    public String getDiagnosis() {
        return diagnosis;
    }
    
    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }
    
    public String getSymptoms() {
        return symptoms;
    }
    
    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }
    
    public String getTreatment() {
        return treatment;
    }
    
    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }
    
    public List<String> getTestResults() {
        return testResults;
    }
    
    public void addTestResult(String testResult) {
        if (this.testResults == null) {
            this.testResults = new ArrayList<>();
        }
        this.testResults.add(testResult);
    }
    
    public RecordType getType() {
        return type;
    }
    
    public void setType(RecordType type) {
        this.type = type;
    }
    
    public RecordStatus getStatus() {
        return status;
    }
    
    public void setStatus(RecordStatus status) {
        this.status = status;
    }
    
    public void archive() {
        this.status = RecordStatus.ARCHIVED;
    }
    
    public enum RecordType {

        CONSULTATION("Consultation"),
        LAB_RESULT("Lab Result"),
        IMAGING("Imaging"),
        SURGERY("Surgery"),
        EMERGENCY("Emergency"),
        FOLLOW_UP("Follow-up");
        
        private final String displayName;
        
        RecordType(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    public enum RecordStatus {

        ACTIVE("Active"),
        ARCHIVED("Archived"),
        DELETED("Deleted");
        
        private final String displayName;
        
        RecordStatus(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    public static class MedicalRecordManager {

        private static List<MedicalRecord> allRecords = new ArrayList<>();
        
        public static void addRecord(MedicalRecord record) {
            allRecords.add(record);
        }
        
        public static List<MedicalRecord> getRecordsForPatient(Patient patient) {
            return allRecords.stream()
                    .filter(record -> record.getPatient().equals(patient))
                    .toList();
        }
        
        public static List<MedicalRecord> getRecordsByType(RecordType type) {
            return allRecords.stream()
                    .filter(record -> record.getType() == type)
                    .toList();
        }
        
        public static void clearAllRecords() {
            allRecords.clear();
        }
        
        public static int getTotalRecordCount() {
            return allRecords.size();
        }
    }
}

