package com.example.smartutor.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class StudentWithLessons {
    @Embedded
    private Student student;
    @Relation(
            parentColumn = "email",
            entityColumn = "studentEmail"
    )
    private List<Lesson> lessons;

    public StudentWithLessons(){}

    public Student getStudent() {
        return student;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }
}
