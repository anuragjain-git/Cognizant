package com.onlinefacultyapplication.model;

import java.util.List;

public class Department {
    private int id;
    private String name;
    private List<Teacher> teachers;

    public Department() {}

    public Department(int id, String name, List<Teacher> teachers) {
        this.id = id;
        this.name = name;
        this.teachers = teachers;
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

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", teachers=" + teachers +
                '}';
    }
}
