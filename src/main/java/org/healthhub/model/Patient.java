package org.healthhub.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Patient {

    private String patientId;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String email;
    private String phoneNumber;
    private List<MedicalRecord> medicalRecords;
    
    public Patient(String patientId, String firstName, String lastName, LocalDate dateOfBirth) {
        this.patientId = patientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.medicalRecords = new ArrayList<>();
    }
    
    public String getPatientId() {
        return patientId;
    }
    
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public List<MedicalRecord> getMedicalRecords() {
        return medicalRecords;
    }
    
    public void addMedicalRecord(MedicalRecord record) {
        this.medicalRecords.add(record);
    }
    
    public int getAge() {
        return LocalDate.now().getYear() - dateOfBirth.getYear();
    }
    
    public static class PatientValidator {
        public static boolean isValidEmail(String email) {
            return email != null && email.contains("@") && email.contains(".");
        }
        
        public static boolean isValidPhoneNumber(String phoneNumber) {
            return phoneNumber != null && phoneNumber.matches("\\d{10,15}");
        }
        
        public static boolean isValidPatientId(String patientId) {
            return patientId != null && !patientId.trim().isEmpty();
        }
    }
    
    public static class PatientBuilder {

        private String patientId;
        private String firstName;
        private String lastName;
        private LocalDate dateOfBirth;
        private String email;
        private String phoneNumber;

        public PatientBuilder setPatientId(String patientId) {
            this.patientId = patientId;
            return this;
        }
        
        public PatientBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }
        
        public PatientBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }
        
        public PatientBuilder setDateOfBirth(LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }
        
        public PatientBuilder withEmail(String email) {
            this.email = email;
            return this;
        }
        
        public PatientBuilder withPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }
        
        public Patient build() {
            Patient patient = new Patient(patientId, firstName, lastName, dateOfBirth);
            patient.setEmail(email);
            patient.setPhoneNumber(phoneNumber);
            return patient;
        }
    }
}

