package org.healthhub.model;

import java.util.ArrayList;
import java.util.List;

public class Doctor {

    private String doctorId;
    private String firstName;
    private String lastName;
    private String specialization;
    private String licenseNumber;
    private List<Patient> patients;
    
    public Doctor(String doctorId,String licenseNumber) {
        this.doctorId = doctorId;
        this.licenseNumber = licenseNumber;
        this.patients = new ArrayList<>();
    }
    
    public String getDoctorId() {
        return doctorId;
    }
    
    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
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
    
    public String getSpecialization() {
        return specialization;
    }
    
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
    
    public String getLicenseNumber() {
        return licenseNumber;
    }
    
    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
    
    public List<Patient> getPatients() {
        return patients;
    }
    
    public void addPatient(Patient patient) {
        if (!patients.contains(patient)) {
            this.patients.add(patient);
        }
    }
    
    public void removePatient(Patient patient) {
        this.patients.remove(patient);
    }
    
    public static class DoctorValidator {

        public static boolean isValidLicenseNumber(String licenseNumber) {
            return licenseNumber != null && licenseNumber.matches("[A-Z]{2}\\d{6}");
        }
        
        public static boolean isValidSpecialization(String specialization) {
            return specialization != null && !specialization.trim().isEmpty();
        }
    }
    
    public static enum Specialization {

        CARDIOLOGY("Cardiology"),
        NEUROLOGY("Neurology"),
        PEDIATRICS("Pediatrics"),
        ORTHOPEDICS("Orthopedics"),
        DERMATOLOGY("Dermatology"),
        GENERAL("General Practice");
        
        private final String displayName;
        
        Specialization(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
}

