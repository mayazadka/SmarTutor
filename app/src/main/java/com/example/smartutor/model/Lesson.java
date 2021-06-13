package com.example.smartutor.model;

import java.util.Date;
import java.util.Objects;

public class Lesson {
    private Student student;
    private Tutor tutor;
    private Date date;
    private int hour;

    public Lesson(Student student, Tutor tutor, Date date, int hour) {
        this.student = student;
        this.tutor = tutor;
        this.date = date;
        this.hour = hour;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lesson lesson = (Lesson) o;
        return hour == lesson.hour &&
                student.equals(lesson.student) &&
                tutor.equals(lesson.tutor) &&
                date.equals(lesson.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, tutor, date, hour);
    }
}
