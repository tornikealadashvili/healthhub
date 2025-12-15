package org.healthhub.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Prescription {

    private String prescriptionId;
    private Patient patient;
    private Doctor doctor;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private List<Medication> medications;
    private String instructions;
    private PrescriptionStatus status;

    
    public String getPrescriptionId() {
        return prescriptionId;
    }
    
    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
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
    
    public LocalDate getIssueDate() {
        return issueDate;
    }
    
    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }
    
    public LocalDate getExpiryDate() {
        return expiryDate;
    }
    
    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }
    
    public List<Medication> getMedications() {
        return medications;
    }
    
    public void addMedication(Medication medication) {
        if (this.medications == null) {
            this.medications = new ArrayList<>();
        }
        this.medications.add(medication);
    }
    
    public String getInstructions() {
        return instructions;
    }
    
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
    
    public PrescriptionStatus getStatus() {
        return status;
    }
    
    public void setStatus(PrescriptionStatus status) {
        this.status = status;
    }
    
    public boolean isExpired() {
        return expiryDate != null && LocalDate.now().isAfter(expiryDate);
    }
    
    public void expire() {
        this.status = PrescriptionStatus.EXPIRED;
    }
    
    public enum PrescriptionStatus {
        ACTIVE("Active"),
        EXPIRED("Expired"),
        CANCELLED("Cancelled"),
        FULFILLED("Fulfilled");
        
        private final String displayName;
        
        PrescriptionStatus(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    public static class Medication {

        private String name;
        private String dosage;
        private int quantity;
        private String frequency;

        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public String getDosage() {
            return dosage;
        }
        
        public void setDosage(String dosage) {
            this.dosage = dosage;
        }
        
        public int getQuantity() {
            return quantity;
        }
        
        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
        
        public String getFrequency() {
            return frequency;
        }
        
        public void setFrequency(String frequency) {
            this.frequency = frequency;
        }
    }
    
    public static class PrescriptionValidator {
        public static boolean isValidPrescriptionId(String prescriptionId) {
            return prescriptionId != null && prescriptionId.startsWith("RX-");
        }
        
        public static boolean hasMedications(Prescription prescription) {
            return prescription != null && prescription.getMedications() != null && !prescription.getMedications().isEmpty();
        }
        
        public static boolean isValidExpiryDate(LocalDate expiryDate) {
            return expiryDate != null && expiryDate.isAfter(LocalDate.now());
        }
    }
}

