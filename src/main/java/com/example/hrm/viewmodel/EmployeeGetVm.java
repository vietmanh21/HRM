package com.example.hrm.viewmodel;

import com.example.hrm.model.Employee;

public class EmployeeGetVm {
    private String username;
    private String role;

    public EmployeeGetVm(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public static EmployeeGetVm fromModel(Employee employee) {
        return new EmployeeGetVm(employee.getUsername(), employee.getRole().toString());
    }
}
