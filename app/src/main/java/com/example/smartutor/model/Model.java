package com.example.smartutor.model;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

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

// 0 = OK
// 1 = ERROR
@RequiresApi(api = Build.VERSION_CODES.O)
public class Model {
//    private List<Student> students;
//    private List<Tutor> tutors;
//    private List<Lesson> lessons;

    private static Model model = null;

    private Model(){
//        this.students = new ArrayList<>();
//        this.tutors = new ArrayList<>();
//        this.lessons = new ArrayList<>();
//
//        // test
//        Date birthdayDate = null;
//        try {birthdayDate = new SimpleDateFormat("dd/MM/yyyy").parse("04/01/2002");}catch(Exception e){}
//        Student s1 = new Student("omer5144@gmail.com", "Asher", "Omer", Gender.MALE, birthdayDate, 12, "OMEome0707");
//        this.students.add(s1);
//        try {birthdayDate = new SimpleDateFormat("dd/MM/yyyy").parse("18/02/2002");}catch(Exception e){}
//        Student s2 = new Student("mayazadka@gmail.com", "Zadka", "Maya", Gender.FEMALE, birthdayDate, 11, "maya");
//        this.students.add(s2);
//
//        List<Profession> professions = new ArrayList<>();
//        professions.add(Profession.MATH);
//        professions.add((Profession.COMPUTERSCIENCE));
//        try {birthdayDate = new SimpleDateFormat("dd/MM/yyyy").parse("04/01/2002");}catch(Exception e){}
//        Tutor t1 = new Tutor("omer5144@gmail.com", "Asher", "Omer", Gender.MALE, birthdayDate, professions, "I'm cool", "OMEome0707");
//        this.tutors.add(t1);
//        professions = new ArrayList<>();
//        professions.add(Profession.LANGUAGE);
//        professions.add((Profession.HISTORY));
//        try {birthdayDate = new SimpleDateFormat("dd/MM/yyyy").parse("18/02/2002");}catch(Exception e){}
//        Tutor t2 = new Tutor("mayazadka@gmail.com", "Zadka", "Maya", Gender.FEMALE, birthdayDate, professions, "Omer is cooler", "maya");
//        this.tutors.add(t2);
//
//        String sEmail1 = s1.getEmail();
//        String sEmail2 = s2.getEmail();
//        String tEmail1 = t1.getEmail();
//        String tEmail2  =t2.getEmail();
//
//        this.addLesson(new Lesson(sEmail1, tEmail1, LocalDateTime.of(2021, 6, 19, 12, 0), Profession.MATH));
//        this.addLesson(new Lesson(sEmail1, tEmail1, LocalDateTime.of(2021, 6, 12, 12, 0), Profession.COMPUTERSCIENCE));
//        this.addLesson(new Lesson(sEmail1, tEmail1, LocalDateTime.of(2021, 6, 14, 23, 0), Profession.MATH));
//
//        this.addLesson(new Lesson(sEmail2, tEmail2, LocalDateTime.of(2021, 6, 19, 12, 0), Profession.HISTORY));
//        this.addLesson(new Lesson(sEmail2, tEmail2, LocalDateTime.of(2021, 6, 12, 12, 0), Profession.HISTORY));
//        this.addLesson(new Lesson(sEmail2, tEmail2, LocalDateTime.of(2021, 6, 14, 23, 0), Profession.HISTORY));
//        this.addLesson(new Lesson(sEmail2, tEmail2, LocalDateTime.of(2021, 6, 19, 18, 30), Profession.HISTORY));
//        this.addLesson(new Lesson(sEmail2, tEmail2, LocalDateTime.of(2021, 6, 18, 18, 0), Profession.HISTORY));
//        this.addLesson(new Lesson(sEmail2, tEmail2, LocalDateTime.of(2021, 6, 14, 23, 0), Profession.LANGUAGE));
//        this.addLesson(new Lesson(sEmail2, tEmail2, LocalDateTime.of(2021, 6, 22, 15, 0), Profession.LANGUAGE));
//        this.addLesson(new Lesson(sEmail2, tEmail2, LocalDateTime.of(2021, 6, 25, 15, 0), Profession.LANGUAGE));
//        this.addLesson(new Lesson(sEmail2, tEmail2, LocalDateTime.of(2021, 6, 24, 15, 0), Profession.LANGUAGE));
    }

    public List<Student> getStudents()                              {return AppLocalDB.db.studentDao().getStudents();}
    public List<Tutor> getTutors()                                  {return AppLocalDB.db.tutorDao().getTutors();}
    public List<Lesson> getLessons()                                {return AppLocalDB.db.lessonDao().getLessons();}
    public void addStudent(Student student)                         {AppLocalDB.db.studentDao().insertStudent(student);}
    public void addTutor(Tutor tutor)                               {AppLocalDB.db.tutorDao().insertTutor(tutor);}
    public void addLesson(Lesson lesson)                            {AppLocalDB.db.lessonDao().insertLesson(lesson);}
    public StudentWithLessons getStudent(String email)              {return AppLocalDB.db.studentDao().getStudent(email);}
    public TutorWithLessons getTutor(String email)                  {return AppLocalDB.db.tutorDao().getTutor(email);}
    public void updateStudent(Student student)                      {AppLocalDB.db.studentDao().updateStudent(student);}
    public void updateTutor(Tutor tutor)                            {AppLocalDB.db.tutorDao().updateTutor(tutor);}
    public List<Tutor> getTutorsByName(String name)                 {return AppLocalDB.db.tutorDao().getTutorsByName(name);}
    public List<Tutor> getTutorsByProfessions(List<Profession> professions){
        List<Tutor> tutors = getTutors();
        List<Tutor> filterTutors = new LinkedList<>();
        for(Tutor tutor : tutors){
            for(Profession profession : professions){
                if(tutor.getProfessions().contains(profession)){
                    filterTutors.add(tutor);
                    break;
                }
            }
        }
        return filterTutors;
    }
    public void deleteStudent(Student student)                      {AppLocalDB.db.studentDao().deleteStudent(student);}
    public void deleteTutor(Tutor tutor)                            {AppLocalDB.db.tutorDao().deleteTutor(tutor);}
    public boolean checkDetailsTutor(String email, String password) {return AppLocalDB.db.tutorDao().countMatchTutor(email, password) != 0;}
    public boolean checkDetailsStudent(String email, String password) {return AppLocalDB.db.studentDao().countMatchStudent(email, password) != 0;}
    public List<Lesson> getRemainLessonsStudent(String email) {
        List<Lesson> studentLessons = getStudent(email).getLessons();
        List<Lesson> remainLessons = new LinkedList<>();
        Collections.sort(studentLessons, (l1, l2) -> l1.getDate().isBefore(l2.getDate())?1:0);
        for(int i = 0; i < studentLessons.size(); i++){
            if(studentLessons.get(i) != null) {
                if(studentLessons.get(i).getDate().isAfter(LocalDateTime.now()) ||
                   studentLessons.get(i).getDate().plusHours(1).isAfter(LocalDateTime.now()) ||
                   studentLessons.get(i).getDate().isAfter(LocalDateTime.now())){
                        remainLessons.add(studentLessons.get(i));
                }
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
        List<Lesson> studentLessons = getStudent(email).getLessons();
        List<Lesson> thisWeekLessons = new LinkedList<>();
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime thisSunday = today.plusDays(8 - dayOfWeek);
        LocalDateTime lastSaturday = today.minusDays(dayOfWeek - 1);
        for(int i = 0; i < studentLessons.size(); i++) {
            if(studentLessons.get(i).getDate().isAfter(lastSaturday) && studentLessons.get(i).getDate().isBefore(thisSunday)) {
                thisWeekLessons.add(studentLessons.get(i));
            }
        }
        return thisWeekLessons;
    }
    public List<Lesson> getRemainLessonsTutor(String email) {
        List<Lesson> TutorLessons = getTutor(email).getLessons();
        List<Lesson> remainLessons = new LinkedList<Lesson>();
        Collections.sort(TutorLessons, (l1, l2) -> l1.getDate().isBefore(l2.getDate())?1:0);
        for(int i = 0; i < TutorLessons.size(); i++){
            if(TutorLessons.get(i) != null) {
                if(TutorLessons.get(i).getDate().isAfter(LocalDateTime.now()) ||
                   TutorLessons.get(i).getDate().plusHours(1).isAfter(LocalDateTime.now()) ||
                   TutorLessons.get(i).getDate().isAfter(LocalDateTime.now())) {
                        remainLessons.add(TutorLessons.get(i));
                }
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
        List<Lesson> tutorLessons = getTutor(email).getLessons();
        List<Lesson> thisWeekLessons = new LinkedList<>();
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime thisSunday = today.plusDays(8 - dayOfWeek);
        LocalDateTime lastSaturday = today.minusDays(dayOfWeek);
        for(int i = 0; i < tutorLessons.size(); i++) {
            if(tutorLessons.get(i).getDate().isAfter(lastSaturday) && tutorLessons.get(i).getDate().isBefore(thisSunday)) {
                thisWeekLessons.add(tutorLessons.get(i));
            }
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