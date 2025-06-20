package com.onlinefacultyapplication.repository;

import com.onlinefacultyapplication.model.Teacher;
import java.util.List;

public interface TeacherDAO {
    Teacher createTeacher(Teacher teacher);
    Teacher getTeacherById(int id);
    List<Teacher> getAllTeachers();
    List<Teacher> getTeachersByDepartment(int departmentId);
    void updateTeacher(Teacher teacher);
    boolean deleteTeacher(int id);
    List<Teacher> searchTeachersByName(String name);
    List<Teacher> searchTeachersBySubject(String subject);
}
