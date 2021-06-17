package com.example.smartutor.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.PrimitiveIterator;
// 0 = OK
// 1 = ERROR
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
        Student s1 = new Student("omer5144@gmail.com", "Asher", "Omer", Gender.MALE, new Date(2002, 1, 4), 12, "OMEome0707");
        this.students.add(s1);
        this.students.add(new Student("mayazadka@gmail.com", "Zadka", "Maya", Gender.FEMALE, new Date(2002, 2, 18), 1, "maya"));

        List<Profession> professions = new ArrayList<>();
        professions.add(Profession.MATH);
        professions.add((Profession.COMPUTERSCIENCE));
        Tutor t1 = new Tutor("omer5144@gmail.com", "Asher", "Omer", Gender.MALE, new Date(2002, 1, 4), professions, "I'm cool", "OMEome0707");
        this.tutors.add(t1);
        professions = new ArrayList<>();
        professions.add(Profession.LANGUAGE);
        professions.add((Profession.HISTORY));
        this.tutors.add(new Tutor("mayazadka@gmail.com", "Zadka", "Maya", Gender.FEMALE, new Date(2002, 2, 18), professions, "Omer is cooler", "maya"));

        this.addLesson(new Lesson(s1, t1, new Date(2021, 6, 19), 12));
        this.addLesson(new Lesson(s1, t1, new Date(2021, 6, 12), 12));
        this.addLesson(new Lesson(s1, t1, new Date(2021, 6, 14), 23));
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
    public Student getStudent(String email) {
        for(int i = 0; i < students.size(); i++){
            if(students.get(i).getEmail().compareTo(email) == 0){
                return students.get(i);
            }
        }
        return null;
    }
    public Tutor getTutor(String email){
        for(int i = 0; i < tutors.size(); i++){
            if(tutors.get(i).getEmail().compareTo(email) == 0){
                return tutors.get(i);
            }
        }
        return null;
    }
    public List<Lesson> getStudentLessons(String email){
        List<Lesson> studentLessons = new LinkedList<Lesson>();

        for(int i = 0; i < lessons.size(); i++){
            if(lessons.get(i).getStudent().getEmail().compareTo(email) == 0){
                studentLessons.add(lessons.get(i));
            }
        }
        return studentLessons;
    }
    public List<Lesson> getTutorLessons(String email){
        List<Lesson> tutorLessons = new LinkedList<Lesson>();
        for(int i = 0; i < lessons.size(); i++){
            if(lessons.get(i).getTutor().getEmail().compareTo(email) == 0){
                tutorLessons.add(lessons.get(i));
            }
        }
        return tutorLessons;
    }
    public int updateStudent(Student student){
        for(int i = 0; i < students.size(); i++){
            if(students.get(i).getEmail().compareTo(student.getEmail()) == 0){
                students.get(i).setLastName(student.getLastName());
                students.get(i).setFirstName(student.getFirstName());
                students.get(i).setGender(student.getGender());
                students.get(i).setBirthdayDate(student.getBirthdayDate());
                students.get(i).setPassword(student.getPassword());
                return 0;
            }
        }
        return 1;
    }
    public int updateTutor(Tutor tutor){
        for(int i = 0; i < tutors.size(); i++){
            if(tutors.get(i).getEmail().compareTo(tutor.getEmail()) == 0){
                tutors.get(i).setLastName(tutor.getLastName());
                tutors.get(i).setFirstName(tutor.getFirstName());
                tutors.get(i).setGender(tutor.getGender());
                tutors.get(i).setBirthdayDate(tutor.getBirthdayDate());
                tutors.get(i).setProfessions(tutor.getProfessions());
                tutors.get(i).setAboutMe(tutor.getAboutMe());
                tutors.get(i).setPassword(tutor.getPassword());
                return 0;
            }
        }
        return 1;
    }
    public List<Tutor> getTutorsByName(String name){
        List<Tutor> tutorsByName = new LinkedList<Tutor>();
        for(int i = 0; i < tutors.size(); i++){
            if(tutors.get(i).getFirstName().contains(name)){
                tutorsByName.add(tutors.get(i));
            }
        }
        return tutorsByName;
    }
    public List<Tutor> getTutorsBySubject(String subject){
        List<Tutor> tutorsBySubject = new LinkedList<Tutor>();
        for(int i = 0; i < tutors.size(); i++){
            if(tutors.get(i).getProfessions().contains(subject)){
                tutorsBySubject.add(tutors.get(i));
            }
        }
        return tutorsBySubject;
    }
    public int deleteStudent(String email){
        for(int i = 0; i < students.size(); i++){
            if(students.get(i).getEmail().compareTo(email) == 0) {
                students.remove(i);
                return 0;
            }
        }
        return 1;
    }
    public int deleteTutor(String email){
        for(int i = 0; i < tutors.size(); i++){
            if(tutors.get(i).getEmail().compareTo(email) == 0) {
                tutors.remove(i);
                return 0;
            }
        }
        return 1;
    }
    public int addNewStudent(Student student){
        students.add(student);
        return 0;
    }
    public int addNewTutor(Tutor tutor){
        tutors.add(tutor);
        return 0;
    }
    public int addNewLesson(Lesson lesson){
        lessons.add(lesson);
        return 0;
    }
    public boolean checkDetailsTutor(String email, String password){
        Tutor tutor = getTutor(email);
        if(tutor != null){
            if(tutor.getPassword().compareTo(password) == 0)
                return true;
        }
        return false;
    }
    public boolean checkDetailsStudent(String email, String password) {
        Student student = getStudent(email);
        if(student != null) {
            if(student.getPassword().compareTo(password) == 0)
                return true;
        }
        return false;
    }
    public static Model getInstance(){
        if(model == null){
            model = new Model();
        }
        return model;
    }
}