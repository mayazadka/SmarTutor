package com.example.smartutor.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Lesson {
    private Student student;
    private Tutor tutor;
    private LocalDateTime date;
    private Profession subject;

    public Lesson(Student student, Tutor tutor, LocalDateTime date, Profession subject) {
        this.student = student;
        this.tutor = tutor;
        this.date = date;
        this.subject = subject;
    }
    public Student getStudent() {
        return student;
    }
    public void setStudent(Student student) {
        this.student = student;
    }
    public Tutor getTutor() {
        return tutor;
    }
    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public Profession getSubject() {
        return subject;
    }
    public void setSubject(Profession subject) {
        this.subject = subject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lesson lesson = (Lesson) o;
        return student.equals(lesson.student) &&
                tutor.equals(lesson.tutor) &&
                date.equals(lesson.date);
    }
    @Override
    public int hashCode() {
        return Objects.hash(student, tutor, date);
    }
}
