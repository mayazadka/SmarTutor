package com.example.smartutor.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.PrimitiveIterator;

public class Model {
    private List<Student> students;
    private List<Tutor> tutors;
    private List<Lesson> lessons;

    private static Model model = null;

    private Model(){
        this.students = new ArrayList<>();
        this.tutors = new ArrayList<>();
        this.lessons = new ArrayList<>();

        // test
        this.students.add(new Student("omer5144@gmail.com", "Asher", "Omer", Gender.MALE, new Date(2002, 1, 4), 12, "OMEome0707"));
        this.students.add(new Student("mayazadka@gmail.com", "Zadka", "Maya", Gender.FEMALE, new Date(2002, 2, 18), 1, "maya"));

        List<Profession> professions = new ArrayList<>();
        professions.add(Profession.MATH);
        professions.add((Profession.COMPUTER_SCIENCE));
        this.tutors.add(new Tutor("omer5144@gmail.com", "Asher", "Omer", Gender.MALE, new Date(2002, 1, 4), professions, "I'm cool", "OMEome0707"));
        professions = new ArrayList<>();
        professions.add(Profession.LANGUAGE);
        professions.add((Profession.HISTORY));
        this.tutors.add(new Tutor("mayazadka@gmail.com", "Zadka", "Maya", Gender.FEMALE, new Date(2002, 2, 18), professions, "Omer is cooler", "maya"));

    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Tutor> getTutors() {
        return tutors;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void addStudent(Student student){
        students.add(student);
    }

    public void addTutor(Tutor tutor){
        tutors.add(tutor);
    }

    public void addLesson(Lesson lesson){
        lessons.add(lesson);
    }

    public static Model getInstance(){
        if(model == null){
            model = new Model();
        }
        return model;
    }

}
