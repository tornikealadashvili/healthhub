package org.healthhub.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Appointment {

    private String appointmentId;
    private Patient patient;
    private Doctor doctor;
    private LocalDateTime appointmentDateTime;
    private AppointmentStatus status;
    private String reason;
    private String notes;


    
    public String getAppointmentId() {
        return appointmentId;
    }
    
    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
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
    
    public LocalDateTime getAppointmentDateTime() {
        return appointmentDateTime;
    }
    
    public void setAppointmentDateTime(LocalDateTime appointmentDateTime) {
        this.appointmentDateTime = appointmentDateTime;
    }
    
    public AppointmentStatus getStatus() {
        return status;
    }
    
    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }
    
    public String getReason() {
        return reason;
    }
    
    public void setReason(String reason) {
        this.reason = reason;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public void cancel() {
        this.status = AppointmentStatus.CANCELLED;
    }
    
    public void complete() {
        this.status = AppointmentStatus.COMPLETED;
    }
    
    public enum AppointmentStatus {

        SCHEDULED("Scheduled"),
        CONFIRMED("Confirmed"),
        IN_PROGRESS("In Progress"),
        COMPLETED("Completed"),
        CANCELLED("Cancelled"),
        NO_SHOW("No Show");
        
        private final String displayName;
        
        AppointmentStatus(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    public static class AppointmentScheduler {

        private static List<Appointment> scheduledAppointments = new ArrayList<>();
        
        public static Appointment scheduleAppointment(Patient patient, Doctor doctor, LocalDateTime dateTime) {
            if (isTimeSlotAvailable(doctor, dateTime)) {
                String appointmentId = generateAppointmentId();
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(appointmentId);
                appointment.setPatient(patient);
                appointment.setDoctor(doctor);
                appointment.setAppointmentDateTime(dateTime);
                appointment.setStatus(AppointmentStatus.SCHEDULED);
                scheduledAppointments.add(appointment);
                return appointment;
            }
            throw new IllegalStateException("Time slot not available");
        }
        
        public static boolean isTimeSlotAvailable(Doctor doctor, LocalDateTime dateTime) {
            return scheduledAppointments.stream()
                    .noneMatch(apt -> apt.getDoctor().equals(doctor) 
                            && apt.getAppointmentDateTime().equals(dateTime)
                            && apt.getStatus() != AppointmentStatus.CANCELLED);
        }
        
        public static List<Appointment> getAppointmentsForDoctor(Doctor doctor) {
            return scheduledAppointments.stream()
                    .filter(apt -> apt.getDoctor().equals(doctor))
                    .toList();
        }
        
        public static List<Appointment> getAppointmentsForPatient(Patient patient) {
            return scheduledAppointments.stream()
                    .filter(apt -> apt.getPatient().equals(patient))
                    .toList();
        }
        
        public static void clearAllAppointments() {
            scheduledAppointments.clear();
        }
        
        private static String generateAppointmentId() {
            return "APT-" + System.currentTimeMillis();
        }
        
        public static enum TimeSlot {
            MORNING_9AM("09:00", "Morning"),
            MORNING_10AM("10:00", "Morning"),
            AFTERNOON_2PM("14:00", "Afternoon"),
            AFTERNOON_3PM("15:00", "Afternoon"),
            EVENING_5PM("17:00", "Evening");
            
            private final String time;
            private final String period;
            
            TimeSlot(String time, String period) {
                this.time = time;
                this.period = period;
            }
            
            public String getTime() {
                return time;
            }
            
            public String getPeriod() {
                return period;
            }
        }
    }
}

