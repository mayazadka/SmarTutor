package com.example.smartutor.model;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Student {
    private String email;
    private String lastName;
    private String firstName;
    private Gender gender;
    private Date birthdayDate;
    private int grade;
    private String password;

    public Student(String email, String lastName, String firstName, Gender gender, Date birthdayDate, int grade, String password) {
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
        this.gender = gender;
        this.birthdayDate = birthdayDate;
        this.grade = grade;
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public Gender getGender() {
        return gender;
    }
    public void setGender(Gender gender) {
        this.gender = gender;
    }
    public Date getBirthdayDate() {
        return birthdayDate;
    }
    public void setBirthdayDate(Date birthdayDate) {
        this.birthdayDate = birthdayDate;
    }
    public int getGrade() {
        return grade;
    }
    public void setGrade(int grade) {
        this.grade = grade;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return email.equals(student.email);
    }
    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
