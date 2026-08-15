package com.shubham.employeemanagementsystem.event;

import java.time.LocalDateTime;

public class EmployeeCreatedEvent {

    private Long employeeId;
    private String name;
    private String email;
    private LocalDateTime timestamp;

    public EmployeeCreatedEvent() {
    }

    public EmployeeCreatedEvent(Long employeeId, String name, String email, LocalDateTime timestamp) {
        this.employeeId = employeeId;
        this.name = name;
        this.email = email;
        this.timestamp = timestamp;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}