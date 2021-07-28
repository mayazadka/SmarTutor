package com.example.smartutor.model;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.smartutor.MyApplication;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

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
    private Long lastUpdated;
    private Boolean isDeleted;

    //strings
    public static final String EMAIL = "email";
    public static final String LAST_NAME = "lastName";
    public static final String FIRST_NAME = "firstName";
    public static final String GENDER = "gender";
    public static final String BIRTHDAY_DATE = "birthdayDate";
    public static final String GRADE = "grade";
    public static final String LAST_UPDATED = "lastUpdated";
    public static final String IS_DELETED = "isDeleted";
    public static final String STUDENTS = "students";
    public static final String TAG = "TAG";
    public static final String STUDENT_LAST_UPDATE = "StudentLastUpdate";

    public Student(){}
    public Student(String email, String lastName, String firstName, Gender gender, Date birthdayDate, int grade) {
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
        this.gender = gender;
        this.birthdayDate = birthdayDate;
        this.grade = grade;
        this.isDeleted = false;
        this.lastUpdated = Long.valueOf(0);
    }
    public Student(Map<String, Object> json){
        email =             (String)json.get(EMAIL);
        lastName =          (String)json.get(LAST_NAME);
        firstName =         (String)json.get(FIRST_NAME);
        gender =            Converters.fromStringToGender((String)json.get(GENDER));
        birthdayDate =      Converters.fromStringToDate((String)json.get(BIRTHDAY_DATE));
        grade =             ((Long)json.get(GRADE)).intValue();
        Timestamp ts =      (Timestamp)json.get(LAST_UPDATED);
        if(ts!=null)        {lastUpdated =ts.getSeconds();}
        else                {lastUpdated = Long.valueOf(0);}
        isDeleted =         (Boolean)json.get(IS_DELETED);
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
    public Long getLastUpdated()                        {return lastUpdated;}
    public void setLastUpdated(Long lastUpdated)        {this.lastUpdated = lastUpdated;}
    public Boolean getDeleted()                         {return isDeleted;}
    public void setDeleted(Boolean deleted)             {isDeleted = deleted;}

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
        data.put(EMAIL, email);
        data.put(LAST_NAME, lastName);
        data.put(FIRST_NAME,  firstName);
        data.put(GENDER, Converters.fromGenderToString(gender));
        data.put(BIRTHDAY_DATE,  Converters.fromDateToString(birthdayDate));
        data.put(GRADE,  grade);
        data.put(LAST_UPDATED, FieldValue.serverTimestamp());
        data.put(IS_DELETED, isDeleted);
        return data;
    }

    public void update(){
        this.lastUpdated = Timestamp.now().getSeconds();
    }

    static public void setLocalLatUpdateTime(Long timeStamp){
        SharedPreferences.Editor editor = MyApplication.context.getSharedPreferences(TAG, Context.MODE_PRIVATE).edit();
        editor.putLong(STUDENT_LAST_UPDATE, timeStamp);
        editor.commit();
    }

    static public Long getLocalLatUpdateTime(){
        return MyApplication.context.getSharedPreferences(TAG, Context.MODE_PRIVATE).getLong(STUDENT_LAST_UPDATE, 0);
    }
}
