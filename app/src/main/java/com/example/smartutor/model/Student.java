package com.example.smartutor.model;


import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity
public class Student {
    @PrimaryKey
    @NonNull
    private String email;
    private String lastName;
    private String firstName;
    private Gender gender;
    private Date birthdayDate;
    private int grade;
    private String password;

    public Student(){}
    public Student(String email, String lastName, String firstName, Gender gender, Date birthdayDate, int grade, String password) {
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
        this.gender = gender;
        this.birthdayDate = birthdayDate;
        this.grade = grade;
        this.password = password;
    }
    public Student(Map<String, Object> json){
        email =             (String)json.get("email");
        lastName =          (String)json.get("lastName");
        firstName =         (String)json.get("firstName");
        gender =            Converters.fromStringToGender((String)json.get("gender"));
        birthdayDate =      Converters.fromStringToDate((String)json.get("birthdayDate"));
        grade =             ((Long)json.get("grade")).intValue();
        password =          (String)json.get("password");
    }

    public String getEmail()                            {return email;}
    public void setEmail(String email)                  {this.email = email;}
    public String getLastName()                         {return lastName;}
    public void setLastName(String lastName)            {this.lastName = lastName;}
    public String getFirstName()                        {return firstName;}
    public void setFirstName(String firstName)          {this.firstName = firstName;}
    public Gender getGender()                           {return gender;}
    public void setGender(Gender gender)                {this.gender = gender;}
    public Date getBirthdayDate()                       {return birthdayDate;}
    public void setBirthdayDate(Date birthdayDate)      {this.birthdayDate = birthdayDate;}
    public int getGrade()                               {return grade;}
    public void setGrade(int grade)                     {this.grade = grade;}
    public String getPassword()                         {return password;}
    public void setPassword(String password)            {this.password = password;}

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

    public Map<String, Object> toJson(){
        Map<String, Object> data = new HashMap<>();
        data.put("email", email);
        data.put("lastName", lastName);
        data.put("firstName",  firstName);
        data.put("gender", Converters.fromGenderToString(gender));
        data.put("birthdayDate",  Converters.fromDateToString(birthdayDate));
        data.put("grade",  grade);
        data.put("password",  password);
        return data;
    }
}
