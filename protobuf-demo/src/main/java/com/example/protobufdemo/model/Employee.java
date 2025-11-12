package com.example.protobufdemo.model;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Map;

public class Employee {

    private int id;
    private String name;
    private String email;
    private String department;
    private double salary;
    private Map<String, String> address;
    private Instant joinDate;
    private LocalDate dateOfBirth;

    public Employee() {
    }

    public Employee(int id, String name, String email, String department, double salary, Map<String, String> address, Instant joinDate, LocalDate dateOfBirth) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.department = department;
        this.salary = salary;
        this.address = address;
        this.joinDate = joinDate;
        this.dateOfBirth = dateOfBirth;
    }

    public Employee(String name, String email, String department, double salary, Map<String, String> address,
                    Instant joinDate, LocalDate dateOfBirth) {
        this.name = name;
        this.email = email;
        this.department = department;
        this.salary = salary;
        this.address = address;
        this.joinDate = joinDate;
        this.dateOfBirth = dateOfBirth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Map<String, String> getAddress() {
        return address;
    }

    public void setAddress(Map<String, String> address) {
        this.address = address;
    }

    public Instant getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Instant joinDate) {
        this.joinDate = joinDate;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
