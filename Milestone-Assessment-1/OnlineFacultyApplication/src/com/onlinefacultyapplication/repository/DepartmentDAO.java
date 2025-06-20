package com.onlinefacultyapplication.repository;

import com.onlinefacultyapplication.model.Department;
import java.util.List;

public interface DepartmentDAO {
    Department createDepartment(Department department);
    Department getDepartmentById(int id);
    List<Department> getAllDepartments();
    void updateDepartment(Department department);
    boolean deleteDepartment(int id);
    List<Department> searchDepartmentsByName(String name);
}
