package com.onlinefacultyapplication;

//import static org.mockito.ArgumentMatchers.nullable;
//import static org.mockito.Mockito.description;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.Properties;
import java.util.Scanner;

//import org.jboss.jandex.Main;

import com.onlinefacultyapplication.model.Department;
import com.onlinefacultyapplication.model.Teacher;
import com.onlinefacultyapplication.repository.DepartmentDAO;
import com.onlinefacultyapplication.repository.DepartmentDAOImpl;
import com.onlinefacultyapplication.repository.TeacherDAO;
import com.onlinefacultyapplication.repository.TeacherDAOImpl;

public class OnlineFacultyApplication {
	private static DepartmentDAO departmentDAO;
	private static TeacherDAO teacherDAO;
	private static Scanner scanner;

	public static void main(String[] args) {
		Properties properties = new Properties();

		try (InputStream inputStream = OnlineFacultyApplication.class.getClassLoader().getResourceAsStream("application.properties");) {
			properties.load(inputStream);

			String url = properties.getProperty("jdbc.url");
			String username = properties.getProperty("jdbc.username");
			String password = properties.getProperty("jdbc.password");

			createDatabaseIfNotExists(properties);
			createTablesIfNotExists(properties);

			departmentDAO = new DepartmentDAOImpl(url,username,password); 
			teacherDAO = new TeacherDAOImpl(url, username, password);

			scanner = new Scanner(System.in);

			int choice;
			do{
				printMenu();
				choice = getUserChoice();
				scanner.nextLine();

				switch(choice)
				{
					case 1:createDepartment(scanner);
					break;
					case 2:createTeacher(scanner);
					break;
					case 3:getDepartmentById(scanner);
					break;
					case 4:getTeacherById(scanner);
					break;
					case 5:getAllDepartments();
					break;
					case 6:getAllTeachers();
					break;
					case 7:updateDepartment(scanner);
					break;
					case 8:updateTeacher(scanner);
					break;
					case 9:deleteDepartment(scanner);
					break;
					case 10:deleteTeacher(scanner);
					break;
					case 11:searchTeachersByName(scanner);
					break;
					case 12:searchTeachersBySubject(scanner);
					break;
					case 13:searchDepartmentsByName(scanner);
					break;
					default:System.out.println("Invalid choice");
					break;
				}
			}while(choice!=14);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
	}

	private static int getUserChoice() {
		scanner = new Scanner(System.in);
		int choice = scanner.nextInt();
		return choice;
	}

	private static void printMenu() {

		System.out.println("Online Faculty Management");
		System.out.println("1) Create Department");
		System.out.println("2) Create Teacher");
		System.out.println("3) Get Department By Id");
		System.out.println("4) Get Teacher By Id");
		System.out.println("5) Get All Departments");
		System.out.println("6) Get All Teachers");
		System.out.println("7) Update Department");
		System.out.println("8) Update Teacher");
		System.out.println("9) Delete Department");
		System.out.println("10) Delete Teacher");
		System.out.println("11) Search Teachers By Name");
		System.out.println("12) Search Teachers By Subject");
		System.out.println("13) Search Department By Name");
		System.out.println("14) Exit");
		System.out.print("Choice: ");

	}

	public static void createDatabaseIfNotExists(Properties properties) {
		String url = properties.getProperty("jdbc.url");
		String username = properties.getProperty("jdbc.username");
		String password = properties.getProperty("jdbc.password");
		String databaseName = properties.getProperty("jdbc.database");

		try (Connection connection = DriverManager.getConnection(url, username, password);
				Statement statement = connection.createStatement()) {
			String createDatabaseSql = "CREATE DATABASE IF NOT EXISTS " + databaseName;
			statement.executeUpdate(createDatabaseSql);
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static void createTablesIfNotExists(Properties properties) {
		String url = properties.getProperty("jdbc.url");
		String username = properties.getProperty("jdbc.username");
		String password = properties.getProperty("jdbc.password");
		String databaseName = properties.getProperty("jdbc.database");

		String connectionString = url + "/" + databaseName;

		try (Connection connection = DriverManager.getConnection(url, username, password);
				Statement statement = connection.createStatement()) {

			String createDatabaseSql = "CREATE DATABASE IF NOT EXISTS " + databaseName;
			statement.executeUpdate(createDatabaseSql);

			String useDatabaseSql = "USE " + databaseName;
			statement.executeUpdate(useDatabaseSql);

			String createDepartmentTableSql = "CREATE TABLE IF NOT EXISTS department ("
					+ "id INT PRIMARY KEY AUTO_INCREMENT," + "name VARCHAR(10) NOT NULL)";

			statement.executeUpdate(createDepartmentTableSql);

			String createTeacherTableSql = "CREATE TABLE IF NOT EXISTS teacher (" + "id INT PRIMARY KEY AUTO_INCREMENT,"
					+ "name VARCHAR(10) NOT NULL," + "subject VARCHAR(255) NOT NULL,"
					+ "designation VARCHAR(20) NOT NULL," + "department_id INT NOT NULL,"
					+ "FOREIGN KEY (department_id) REFERENCES department(id) ON DELETE CASCADE)";

			statement.executeUpdate(createTeacherTableSql);
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	private static void createDepartment(Scanner sc) {
		System.out.print("Enter Department name: ");
		String name = sc.nextLine();

		Department dept = new Department();
		dept.setName(name);

		Department dept1 = departmentDAO.createDepartment(dept);

		if(dept1!=null)
		System.out.println("Department created successfully with ID: "+dept1.getId());
		else
		System.out.println("Failed to create department.");
	
	}

	private static void createTeacher(Scanner sc) {
		System.out.print("Enter Teacher name: ");
		String name = sc.nextLine();
		System.out.print("Enter subject: ");
		String sub = sc.nextLine();
		System.out.print("Enter designation: ");
		String desg = sc.nextLine();
		System.out.print("Enter Department Id: ");
		int did = sc.nextInt();

		Teacher t = new Teacher();
		t.setName(name);
		t.setSubject(sub);
		t.setDepartment(did);
		t.setDesignation(desg);

		Teacher t1 = teacherDAO.createTeacher(t);

		if(t1!=null)
		System.out.println("Teacher created successfully with ID: "+t1.getId());

		else
		System.out.println("Failed to create teacher.");
	}

	private static void getDepartmentById(Scanner sc) {
		System.out.print("Enter Department ID: ");
		int did = sc.nextInt();

		Department dept = departmentDAO.getDepartmentById(did);
		if(dept!=null)
		System.out.println("Retrieved Department:"+dept);
		else
		System.out.println("Department with ID "+did+" does not exist.");
	}

	private static void getTeacherById(Scanner sc) {
		System.out.print("Enter Teacher ID: ");
		int id = sc.nextInt();

		Teacher t = teacherDAO.getTeacherById(id);
		if(t!=null)
		System.out.println("Retrieved Teacher:"+t);
		else
		System.out.println("Teacher with ID "+id+" does not exist.");
	}

	private static void getAllDepartments() {
		List<Department> dept = departmentDAO.getAllDepartments();

		if(dept.isEmpty())
		{
			System.out.println("No Departments Found");
		}
		else{
			for(Department d: dept)
			{
				System.out.println(d);
			}
		}
	}

	private static void getAllTeachers() {
		List<Teacher> t = teacherDAO.getAllTeachers();

		if(t.isEmpty())
		{
			System.out.println("No Teachers Found");
		}
		else{
			for(Teacher d: t)
			{
				System.out.println(d);
			}
		}
	}

	private static void updateDepartment(Scanner sc) {
		System.out.print("Enter Department ID: ");
		int did = sc.nextInt();
		sc.nextLine();
		System.out.print("Enter new Department name: ");
		String name = sc.nextLine();
		Department d = departmentDAO.getDepartmentById(did);
		d.setName(name);

		departmentDAO.updateDepartment(d);
	}

	private static void updateTeacher(Scanner sc) {
		System.out.print("Enter Teacher ID: ");
		int id = sc.nextInt();
		sc.nextLine();
		System.out.print("Enter Teacher name: ");
		String name = sc.nextLine();
		System.out.print("Enter subject: ");
		String sub = sc.nextLine();
		System.out.print("Enter designation: ");
		String desg = sc.nextLine();
		System.out.print("Enter Department Id: ");
		int did = sc.nextInt();

		Teacher t = teacherDAO.getTeacherById(id);
		t.setName(name);
		t.setSubject(sub);
		t.setDepartment(did);
		t.setDesignation(desg);

		teacherDAO.updateTeacher(t);
	
	}

	private static void deleteDepartment(Scanner sc) {
		System.out.print("Enter Department ID: ");
		int did = sc.nextInt();

		if(departmentDAO.deleteDepartment(did))
		System.out.println("Department Deleted successfully");
		else
		System.out.println("Department not found");

	}

	private static void deleteTeacher(Scanner sc) {
		System.out.print("Enter Teacher ID: ");
		int id = sc.nextInt();

		if(teacherDAO.deleteTeacher(id))
		System.out.println("Teacher Deleted successfully");
		else
		System.out.println("Teacher not found");
	}

	private static List<Teacher> searchTeachersByName(Scanner sc) {
		System.out.print("Enter Teacher name: ");
		String name = sc.nextLine();
		List<Teacher> t = teacherDAO.searchTeachersByName(name);
		if(!t.isEmpty())
		{
			t.forEach(System.out::println);
		}
		else
		System.out.println("No Teachers found with given name.");
		return t;
	}

	private static List<Teacher> searchTeachersBySubject(Scanner sc) {
		System.out.print("Enter Teacher subject: ");
		String sub = sc.nextLine();
		List<Teacher> t = teacherDAO.searchTeachersBySubject(sub);
		if(!t.isEmpty())
		{
			t.forEach(System.out::println);
		}
		else
		System.out.println("No Teachers found with given subject.");
		
		
		return null;
	}

	private static List<Department> searchDepartmentsByName(Scanner sc) {
		System.out.print("Enter department name: ");
		String name = sc.nextLine();

		List<Department> departments = departmentDAO.searchDepartmentsByName(name);
		
		if (departments == null || departments.isEmpty()) {
	        System.out.println("No Departments found with given name.");
	    } else {
	        departments.forEach(System.out::println);
	    }

		return departments;
	}
}