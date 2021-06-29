package com.example.smartutor.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity(primaryKeys = {"studentEmail", "tutorEmail", "date"})
public class Lesson {
    @NonNull private String studentEmail;
    @NonNull private String tutorEmail;
    @NonNull private LocalDateTime date;
    private Profession subject;

    public Lesson(){}
    public Lesson(String studentEmail, String tutorEmail, LocalDateTime date, Profession subject) {
        this.studentEmail = studentEmail;
        this.tutorEmail = tutorEmail;
        this.date = date;
        this.subject = subject;
    }

    public String getStudentEmail()                     {return studentEmail;}
    public void setStudentEmail(String studentEmail)    {this.studentEmail = studentEmail;}
    public String getTutorEmail()                       {return tutorEmail;}
    public void setTutorEmail(String tutorEmail)        {this.tutorEmail = tutorEmail;}
    public LocalDateTime getDate()                      {return date;}
    public void setDate(LocalDateTime date)             {this.date = date;}
    public Profession getSubject()                      {return subject;}
    public void setSubject(Profession subject)          {this.subject = subject;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lesson lesson = (Lesson) o;
        return studentEmail.equals(lesson.studentEmail) &&
                tutorEmail.equals(lesson.tutorEmail) &&
                date.equals(lesson.date);
    }
    @Override
    public int hashCode() {
        return Objects.hash(studentEmail, tutorEmail, date);
    }
}
