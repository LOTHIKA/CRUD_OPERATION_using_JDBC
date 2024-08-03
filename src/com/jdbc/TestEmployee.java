package com.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.List;

public class TestEmployee {

    public static void main(String[] args) {
        EmployeeDAO employeeDAO = new EmployeeDAO();

        // Creating Date objects
        Date doj = Date.valueOf("2021-06-18");
        Date dob = Date.valueOf("2003-09-17");

        // Handling file input stream for photo
        InputStream photoStream = null;
        try {
            photoStream = TestEmployee.class.getClassLoader().getResourceAsStream("image/doraemon.jpeg");
            if (photoStream == null) {
                throw new IOException("Resource not found: doraemon.jpeg");
            }
        } catch (IOException e) {
            System.out.println("Photo file not found: " + e.getMessage());
            return; // Exit if photo file is not found
        }

        // Creating an Employee object
        Employee employee = new Employee(77, "Sameer", "Chennai", "CSE", "Marketing", doj, dob, 650000f, photoStream, "Yamuna Street");

        // Saving the employee
        employeeDAO.saveEmp(employee);

        // Retrieving and displaying employee with ID 77
        employeeDAO.getEmp(77);

        // Retrieving and displaying all employees
        employeeDAO.getAllEmp();

        // Updating the salary of the employee
        employee.setSalary(690000f);
        employeeDAO.updateSal(employee);

        // Retrieving and displaying employee with ID 77 after salary update
        employeeDAO.getEmp(77);

        // Deleting employee with ID 77
        employeeDAO.deleteEmp(77);

        // Retrieving and displaying all employees after deletion
        employeeDAO.getAllEmp();

        // Close photoStream if it was opened
        try {
            if (photoStream != null) {
                photoStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        int yearsOfExperience = 2; // Change this value as needed
        List<Employee> experiencedEmployees = employeeDAO.getEmployeesWithMoreThanNYearsExperience(yearsOfExperience);
        System.out.println("Employees with more than " + yearsOfExperience + " years of experience:");
        for (Employee emp : experiencedEmployees) {
            System.out.println(emp);
        }
    }
}