package com.jdbc;

import java.io.InputStream;
import java.sql.Date;

public class Employee {
    int id;
    String e_name, city, dept, designation;
    Date doj, dob;
    float salary;
    String address;
    InputStream photo;

    public Employee(int id, String e_name, String city, String dept, String designation, Date doj, Date dob,
                    float salary, InputStream photo, String address) {
        this.id = id;
        this.e_name = e_name;
        this.city = city;
        this.dept = dept;
        this.designation = designation;
        this.doj = doj;
        this.dob = dob;
        this.salary = salary;
        this.photo = photo;
        this.address = address;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getE_name() {
        return e_name;
    }

    public void setE_name(String e_name) {
        this.e_name = e_name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Date getDoj() {
        return doj;
    }

    public void setDoj(Date doj) {
        this.doj = doj;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public InputStream getPhoto() {
        return photo;
    }

    public void setPhoto(InputStream photo) {
        this.photo = photo;
    }
    
    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", e_name='" + e_name + '\'' +
                ", city='" + city + '\'' +
                ", dept='" + dept + '\'' +
                ", designation='" + designation + '\'' +
                ", doj=" + doj +
                ", dob=" + dob +
                ", salary=" + salary +
                ", address='" + address + '\'' +
                '}';
    }
}