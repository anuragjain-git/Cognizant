package com.onlinefacultyapplication.repository;

import com.onlinefacultyapplication.model.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAOImpl implements TeacherDAO {
    private final String url;
    private final String username;
    private final String password;

    public TeacherDAOImpl(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    @Override
    public Teacher createTeacher(Teacher teacher) {
        String sql = "INSERT INTO teacher (name, subject, designation, department_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, teacher.getName());
            stmt.setString(2, teacher.getSubject());
            stmt.setString(3, teacher.getDesignation());
            stmt.setInt(4, teacher.getDepartmentId());

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                teacher.setId(rs.getInt(1));
                return teacher;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Teacher getTeacherById(int id) {
        String sql = "SELECT * FROM teacher WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Teacher(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("subject"),
                        rs.getString("designation"),
                        rs.getInt("department_id")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Teacher> getAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        String sql = "SELECT * FROM teacher";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                teachers.add(new Teacher(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("subject"),
                        rs.getString("designation"),
                        rs.getInt("department_id")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return teachers;
    }

    @Override
    public List<Teacher> getTeachersByDepartment(int departmentId) {
        List<Teacher> teachers = new ArrayList<>();
        String sql = "SELECT * FROM teacher WHERE department_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, departmentId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                teachers.add(new Teacher(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("subject"),
                        rs.getString("designation"),
                        rs.getInt("department_id")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return teachers;
    }

    @Override
    public void updateTeacher(Teacher teacher) {
        String sql = "UPDATE teacher SET name = ?, subject = ?, designation = ?, department_id = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, teacher.getName());
            stmt.setString(2, teacher.getSubject());
            stmt.setString(3, teacher.getDesignation());
            stmt.setInt(4, teacher.getDepartmentId());
            stmt.setInt(5, teacher.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean deleteTeacher(int id) {
        String sql = "DELETE FROM teacher WHERE id = ?";

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
    public List<Teacher> searchTeachersByName(String name) {
        List<Teacher> teachers = new ArrayList<>();
        String sql = "SELECT * FROM teacher WHERE name LIKE ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                teachers.add(new Teacher(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("subject"),
                        rs.getString("designation"),
                        rs.getInt("department_id")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return teachers;
    }

    @Override
    public List<Teacher> searchTeachersBySubject(String subject) {
        List<Teacher> teachers = new ArrayList<>();
        String sql = "SELECT * FROM teacher WHERE subject LIKE ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + subject + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                teachers.add(new Teacher(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("subject"),
                        rs.getString("designation"),
                        rs.getInt("department_id")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return teachers;
    }
}