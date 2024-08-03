package com.jdbc;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    Connection con;
    PreparedStatement pst;
    ResultSet rst;

    public EmployeeDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/practice1", "root", "Sista@1218");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveEmp(Employee e) {
        String sql = "INSERT INTO employee(id, e_name, city, dept, designation, doj, dob, salary, photo, address) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, e.getId());
            pst.setString(2, e.getE_name());
            pst.setString(3, e.getCity());
            pst.setString(4, e.getDept());
            pst.setString(5, e.getDesignation());
            pst.setDate(6, new java.sql.Date(e.getDoj().getTime()));
            pst.setDate(7, new java.sql.Date(e.getDob().getTime()));
            pst.setFloat(8, e.getSalary());

            // Handle photo (blob)
            if (e.getPhoto() != null) {
                pst.setBlob(9, e.getPhoto());
            } else {
                pst.setNull(9, Types.BLOB);
            }

            pst.setString(10, e.getAddress());
            pst.executeUpdate();
            System.out.println("Data inserted...");
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public void getEmp(int id) {
        String sql = "SELECT * FROM employee WHERE id = ?";
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            rst = pst.executeQuery();
            if (rst.next()) {
                System.out.println("ID: " + rst.getInt("id"));
                System.out.println("Name: " + rst.getString("e_name"));
                System.out.println("City: " + rst.getString("city"));
                System.out.println("Dept: " + rst.getString("dept"));
                System.out.println("Designation: " + rst.getString("designation"));
                System.out.println("Date of Joining: " + rst.getDate("doj"));
                System.out.println("Date of Birth: " + rst.getDate("dob"));
                System.out.println("Salary: " + rst.getFloat("salary"));
                System.out.println("Address: " + rst.getString("address"));

                // Retrieve and display the photo
                Blob photoBlob = rst.getBlob("photo");
                if (photoBlob != null) {
                    try (InputStream photoStream = photoBlob.getBinaryStream()) {
                        File photoFile = new File("retrieved_photo_" + id + ".jpeg");
                        try (FileOutputStream fos = new FileOutputStream(photoFile)) {
                            byte[] buffer = new byte[1024];
                            int bytesRead;
                            while ((bytesRead = photoStream.read(buffer)) != -1) {
                                fos.write(buffer, 0, bytesRead);
                            }
                        }
                        System.out.println("Photo saved to 'retrieved_photo_" + id + ".jpeg'");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("No photo found for this employee.");
                }
            } else {
                System.out.println("No employee found with ID " + id);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public void getAllEmp() {
        String str = "SELECT * FROM employee";
        try {
            pst = con.prepareStatement(str);
            rst = pst.executeQuery();
            System.out.println("ID\tNAME\tCITY\tDEPT\tDESIGNATION\tDATE_OF_JOINING\tDATE_OF_BIRTH\tSALARY\tADDRESS");
            while (rst.next()) {
                System.out.print(rst.getInt("id") + "\t");
                System.out.print(rst.getString("e_name") + "\t");
                System.out.print(rst.getString("city") + "\t");
                System.out.print(rst.getString("dept") + "\t");
                System.out.print(rst.getString("designation") + "\t");
                System.out.print(rst.getDate("doj") + "\t");
                System.out.print(rst.getDate("dob") + "\t");
                System.out.print(rst.getFloat("salary") + "\t");
                System.out.println(rst.getString("address"));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public void updateSal(Employee e) {
        String sql = "UPDATE employee SET salary = ? WHERE id = ?";
        try {
            pst = con.prepareStatement(sql);
            pst.setFloat(1, e.getSalary());
            pst.setInt(2, e.getId());
            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Salary updated for employee ID " + e.getId());
            } else {
                System.out.println("No employee found with ID " + e.getId());
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public void deleteEmp(int id) {
        String sql = "DELETE FROM employee WHERE id = ?";
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            int rowsDeleted = pst.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Employee deleted with ID " + id);
            } else {
                System.out.println("No employee found with ID " + id);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
    
    public List<Employee> getEmployeesWithMoreThanNYearsExperience(int years) {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employee WHERE DATEDIFF(CURDATE(), doj) / 365 > ?";
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, years);
            rst = pst.executeQuery();
            while (rst.next()) {
                int id = rst.getInt("id");
                String e_name = rst.getString("e_name");
                String city = rst.getString("city");
                String dept = rst.getString("dept");
                String designation = rst.getString("designation");
                Date doj = rst.getDate("doj");
                Date dob = rst.getDate("dob");
                float salary = rst.getFloat("salary");
                InputStream photo = rst.getBinaryStream("photo");
                String address = rst.getString("address");

                Employee employee = new Employee(id, e_name, city, dept, designation, doj, dob, salary, photo, address);
                employees.add(employee);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return employees;
    }
}