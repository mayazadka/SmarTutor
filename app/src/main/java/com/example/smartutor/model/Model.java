package com.example.smartutor.model;

import android.util.Log;

import com.example.smartutor.ui.LogIn;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

class LessonsComperator implements Comparator<Lesson>{
    @Override
    public int compare(Lesson o1, Lesson o2) {
        if(o1.getDate().isBefore(o2.getDate())){
            return 1;
        }
        return 0;
    }
}

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
        Date birthdayDate = null;
        try {birthdayDate = new SimpleDateFormat("dd/MM/yyyy").parse("04/01/2002");}catch(Exception e){}
        Student s1 = new Student("omer5144@gmail.com", "Asher", "Omer", Gender.MALE, birthdayDate, 12, "OMEome0707");
        this.students.add(s1);
        try {birthdayDate = new SimpleDateFormat("dd/MM/yyyy").parse("18/02/2002");}catch(Exception e){}
        Student s2 = new Student("mayazadka@gmail.com", "Zadka", "Maya", Gender.FEMALE, birthdayDate, 11, "maya");
        this.students.add(s2);

        List<Profession> professions = new ArrayList<>();
        professions.add(Profession.MATH);
        professions.add((Profession.COMPUTERSCIENCE));
        try {birthdayDate = new SimpleDateFormat("dd/MM/yyyy").parse("04/01/2002");}catch(Exception e){}
        Tutor t1 = new Tutor("omer5144@gmail.com", "Asher", "Omer", Gender.MALE, birthdayDate, professions, "I'm cool", "OMEome0707");
        this.tutors.add(t1);
        professions = new ArrayList<>();
        professions.add(Profession.LANGUAGE);
        professions.add((Profession.HISTORY));
        try {birthdayDate = new SimpleDateFormat("dd/MM/yyyy").parse("18/02/2002");}catch(Exception e){}
        Tutor t2 = new Tutor("mayazadka@gmail.com", "Zadka", "Maya", Gender.FEMALE, birthdayDate, professions, "Omer is cooler", "maya");
        this.tutors.add(t2);
        this.addLesson(new Lesson(s1, t1, LocalDateTime.of(2021, 6, 19, 12, 0), Profession.MATH));
        this.addLesson(new Lesson(s1, t1, LocalDateTime.of(2021, 6, 12, 12, 0), Profession.COMPUTERSCIENCE));
        this.addLesson(new Lesson(s1, t1, LocalDateTime.of(2021, 6, 14, 23, 0), Profession.MATH));

        this.addLesson(new Lesson(s2, t2, LocalDateTime.of(2021, 6, 19, 12, 0), Profession.HISTORY));
        this.addLesson(new Lesson(s2, t2, LocalDateTime.of(2021, 6, 12, 12, 0), Profession.HISTORY));
        this.addLesson(new Lesson(s2, t2, LocalDateTime.of(2021, 6, 14, 23, 0), Profession.HISTORY));
        this.addLesson(new Lesson(s2, t2, LocalDateTime.of(2021, 6, 19, 18, 30), Profession.HISTORY));
        this.addLesson(new Lesson(s2, t2, LocalDateTime.of(2021, 6, 18, 18, 0), Profession.HISTORY));
        this.addLesson(new Lesson(s2, t2, LocalDateTime.of(2021, 6, 14, 23, 0), Profession.LANGUAGE));
        this.addLesson(new Lesson(s2, t2, LocalDateTime.of(2021, 6, 22, 15, 0), Profession.LANGUAGE));
        this.addLesson(new Lesson(s2, t2, LocalDateTime.of(2021, 6, 25, 15, 0), Profession.LANGUAGE));
        this.addLesson(new Lesson(s2, t2, LocalDateTime.of(2021, 6, 24, 15, 0), Profession.LANGUAGE));
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
                students.get(i).setGrade(student.getGrade());
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
        String fullname;
        for(int i = 0; i < tutors.size(); i++){
            fullname = tutors.get(i).getFirstName().toLowerCase() + " " + tutors.get(i).getLastName().toLowerCase();
            if(fullname.contains(name.toLowerCase())){
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
    public Lesson getLessonByTutorAndStudent(String emailStudent, String emailTutor) {
        List<Lesson> tutorLessons = getTutorLessons(emailTutor);
        for(int i = 0; i < tutorLessons.size(); i++){
            if(tutorLessons.get(i).getStudent().getEmail().compareTo(emailStudent) == 0){
                return tutorLessons.get(i);
            }
        }
        return null;
    }
    public List<Lesson> getRemainLessonsStudent(String email) {
        List<Lesson> studentLessons = getStudentLessons(email);
        List<Lesson> remainLessons = new LinkedList<Lesson>();
        Collections.sort(studentLessons, new LessonsComperator());
        for(int i = 0; i < studentLessons.size(); i++){
            if(studentLessons.get(i) != null) {
                if(studentLessons.get(i).getDate().isAfter(LocalDateTime.now()) || studentLessons.get(i).getDate().plusHours(1).isAfter(LocalDateTime.now()))
                    remainLessons.add(studentLessons.get(i));
                else if (studentLessons.get(i).getDate().isAfter(LocalDateTime.now()))
                        remainLessons.add(studentLessons.get(i));
            }
        }
        return remainLessons;
    }
    public Lesson getNextLessonStudent(String email) {
        List<Lesson> remain = getRemainLessonsStudent(email);
        if(remain.size() > 0)
            return remain.get(0);
        else
            return null;
    }
    public List<Lesson> getThisWeekLessonsStudent(String email){
        List<Lesson> studentLessons = getStudentLessons(email);
        List<Lesson> thisWeekLessons = new LinkedList<Lesson>();
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime thisSunday = today.plusDays(8 - dayOfWeek);
        LocalDateTime lastSaturday = today.minusDays(dayOfWeek - 1);
        for(int i = 0; i < studentLessons.size(); i++) {
            if(studentLessons.get(i).getDate().isAfter(lastSaturday) && studentLessons.get(i).getDate().isBefore(thisSunday))
                thisWeekLessons.add(studentLessons.get(i));
        }
        return thisWeekLessons;
    }
    public List<Lesson> getRemainLessonsTutor(String email) {
        List<Lesson> TutorLessons = getTutorLessons(email);
        List<Lesson> remainLessons = new LinkedList<Lesson>();
        Collections.sort(TutorLessons, new LessonsComperator());
        for(int i = 0; i < TutorLessons.size(); i++){
            if(TutorLessons.get(i) != null) {
                if(TutorLessons.get(i).getDate().isAfter(LocalDateTime.now()) || TutorLessons.get(i).getDate().plusHours(1).isAfter(LocalDateTime.now()))
                    remainLessons.add(TutorLessons.get(i));
                else if (TutorLessons.get(i).getDate().isAfter(LocalDateTime.now()))
                    remainLessons.add(TutorLessons.get(i));
            }
        }
        return remainLessons;
    }
    public Lesson getNextLessonTutor(String email) {
        List<Lesson> remain = getRemainLessonsTutor(email);
        if(remain.size() > 0)
            return remain.get(0);
        else
            return null;
    }
    public List<Lesson> getThisWeekLessonsTutor(String email){
        List<Lesson> tutorLessons = getTutorLessons(email);
        List<Lesson> thisWeekLessons = new LinkedList<Lesson>();
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime thisSunday = today.plusDays(8 - dayOfWeek);
        LocalDateTime lastSaturday = today.minusDays(dayOfWeek);
        for(int i = 0; i < tutorLessons.size(); i++) {
            if(tutorLessons.get(i).getDate().isAfter(lastSaturday) && tutorLessons.get(i).getDate().isBefore(thisSunday))
                thisWeekLessons.add(tutorLessons.get(i));
        }
        return thisWeekLessons;
    }
    public static Model getInstance(){
        if(model == null){
            model = new Model();
        }
        return model;
    }
}