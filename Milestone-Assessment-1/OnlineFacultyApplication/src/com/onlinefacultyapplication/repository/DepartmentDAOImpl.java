package com.onlinefacultyapplication.repository;

import com.onlinefacultyapplication.model.Department;
import com.onlinefacultyapplication.model.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAOImpl implements DepartmentDAO {
    private final String url;
    private final String username;
    private final String password;

    public DepartmentDAOImpl(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    @Override
    public Department createDepartment(Department department) {
        String sql = "INSERT INTO department (name) VALUES (?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, department.getName());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                department.setId(rs.getInt(1));
                return department;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Department getDepartmentById(int id) {
        String sql = "SELECT * FROM department WHERE id = ?";
        Department department = null;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                department = new Department();
                department.setId(rs.getInt("id"));
                department.setName(rs.getString("name"));
                department.setTeachers(getTeachersByDepartmentId(id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return department;
    }

    @Override
    public List<Department> getAllDepartments() {
        List<Department> departments = new ArrayList<>();
        String sql = "SELECT * FROM department";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                Department dept = new Department();
                dept.setId(id);
                dept.setName(rs.getString("name"));
                dept.setTeachers(getTeachersByDepartmentId(id));
                departments.add(dept);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return departments;
    }

    @Override
    public void updateDepartment(Department department) {
        String sql = "UPDATE department SET name = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, department.getName());
            stmt.setInt(2, department.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean deleteDepartment(int id) {
        String sql = "DELETE FROM department WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Department> searchDepartmentsByName(String name) {
        List<Department> departments = new ArrayList<>();
        String sql = "SELECT * FROM department WHERE name LIKE ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                Department dept = new Department();
                dept.setId(id);
                dept.setName(rs.getString("name"));
                dept.setTeachers(getTeachersByDepartmentId(id));
                departments.add(dept);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return departments;
    }

    private List<Teacher> getTeachersByDepartmentId(int departmentId) {
        List<Teacher> teachers = new ArrayList<>();
        String sql = "SELECT * FROM teacher WHERE department_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, departmentId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Teacher teacher = new Teacher();
                teacher.setId(rs.getInt("id"));
                teacher.setName(rs.getString("name"));
                teacher.setSubject(rs.getString("subject"));
                teacher.setDesignation(rs.getString("designation"));
                teacher.setDepartment(rs.getInt("department_id"));
                teachers.add(teacher);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return teachers;
    }
}