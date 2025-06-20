package com.onlinefacultyapplication.model;

public class Teacher {
    private int id;
    private String name;
    private String subject;
    private String designation;
    private int departmentId;

    public Teacher() {}

    public Teacher(int id, String name, String subject, String designation, int departmentId) {
        this.id = id;
        this.name = name;
        this.subject = subject;
        this.designation = designation;
        this.departmentId = departmentId;
    }

    // Getters and Setters
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartment(int departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", subject='" + subject + '\'' +
                ", designation='" + designation + '\'' +
                ", departmentId=" + departmentId +
                '}';
    }
}
