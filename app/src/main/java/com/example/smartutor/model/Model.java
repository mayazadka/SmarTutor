package com.example.smartutor.model;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.example.smartutor.MyApplication;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;


@RequiresApi(api = Build.VERSION_CODES.O)
public class Model {
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private static Model model = null;

    private Model(){}
    public static Model getInstance(){
        if(model == null){
            model = new Model();
        }
        return model;
    }

    public LiveData<List<Student>> getStudents()                    {return AppLocalDB.db.studentDao().getStudents();}
    public LiveData<List<Tutor>> getTutors()                        {return AppLocalDB.db.tutorDao().getTutors();}
    public LiveData<List<Lesson>> getLessons()                      {return AppLocalDB.db.lessonDao().getLessons();}
    public LiveData<List<Lesson>> getLessonsByStudent(String email) {return AppLocalDB.db.lessonDao().getLessonsByStudent(email);}
    public LiveData<List<Lesson>> getLessonsByTutor(String email)   {return AppLocalDB.db.lessonDao().getLessonsByTutor(email);}
    public void addStudent(Student student)                         {executorService.execute(()->AppLocalDB.db.studentDao().insertStudent(student));}
    public void addTutor(Tutor tutor)                               {executorService.execute(()->AppLocalDB.db.tutorDao().insertTutor(tutor));}
    public void addLesson(Lesson lesson)                            {executorService.execute(()->AppLocalDB.db.lessonDao().insertLesson(lesson));}
    public LiveData<Student> getStudent(String email)               {return AppLocalDB.db.studentDao().getStudent(email);}
    public LiveData<Tutor> getTutor(String email)                   {return AppLocalDB.db.tutorDao().getTutor(email);}
    public void updateStudent(Student student)                      {executorService.execute(()->AppLocalDB.db.studentDao().updateStudent(student));}
    public void updateTutor(Tutor tutor)                            {executorService.execute(()->AppLocalDB.db.tutorDao().updateTutor(tutor));}
    public void deleteStudent(Student student)                      {executorService.execute(()->AppLocalDB.db.studentDao().deleteStudent(student));}
    public void deleteTutor(Tutor tutor)                            {executorService.execute(()->AppLocalDB.db.tutorDao().deleteTutor(tutor));}

//    public Lesson getNextLessonStudent(String email) {
//        List<Lesson> remain = getRemainLessonsStudent(email);
//        if(remain.size() > 0)
//            return remain.get(0);
//        else
//            return null;
//    }
//    public List<Lesson> getThisWeekLessonsStudent(String email){
//        List<Lesson> studentLessons = getStudent(email).getLessons();
//        List<Lesson> thisWeekLessons = new LinkedList<>();
//        Calendar c = Calendar.getInstance();
//        c.setTime(new Date());
//        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
//        LocalDateTime today = LocalDateTime.now();
//        LocalDateTime thisSunday = today.plusDays(8 - dayOfWeek);
//        LocalDateTime lastSaturday = today.minusDays(dayOfWeek - 1);
//        for(int i = 0; i < studentLessons.size(); i++) {
//            if(studentLessons.get(i).getDate().isAfter(lastSaturday) && studentLessons.get(i).getDate().isBefore(thisSunday)) {
//                thisWeekLessons.add(studentLessons.get(i));
//            }
//        }
//        return thisWeekLessons;
//    }
//    public List<Lesson> getRemainLessonsTutor(String email) {
//        List<Lesson> TutorLessons = getTutor(email).getLessons();
//        List<Lesson> remainLessons = new LinkedList<Lesson>();
//        Collections.sort(TutorLessons, (l1, l2) -> l1.getDate().isBefore(l2.getDate())?1:0);
//        for(int i = 0; i < TutorLessons.size(); i++){
//            if(TutorLessons.get(i) != null) {
//                if(TutorLessons.get(i).getDate().isAfter(LocalDateTime.now()) ||
//                   TutorLessons.get(i).getDate().plusHours(1).isAfter(LocalDateTime.now()) ||
//                   TutorLessons.get(i).getDate().isAfter(LocalDateTime.now())) {
//                        remainLessons.add(TutorLessons.get(i));
//                }
//            }
//        }
//        return remainLessons;
//    }
//    public Lesson getNextLessonTutor(String email) {
//        List<Lesson> remain = getRemainLessonsTutor(email);
//        if(remain.size() > 0)
//            return remain.get(0);
//        else
//            return null;
//    }
//    public List<Lesson> getThisWeekLessonsTutor(String email){
//        List<Lesson> tutorLessons = getTutor(email).getLessons();
//        List<Lesson> thisWeekLessons = new LinkedList<>();
//        Calendar c = Calendar.getInstance();
//        c.setTime(new Date());
//        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
//        LocalDateTime today = LocalDateTime.now();
//        LocalDateTime thisSunday = today.plusDays(8 - dayOfWeek);
//        LocalDateTime lastSaturday = today.minusDays(dayOfWeek);
//        for(int i = 0; i < tutorLessons.size(); i++) {
//            if(tutorLessons.get(i).getDate().isAfter(lastSaturday) && tutorLessons.get(i).getDate().isBefore(thisSunday)) {
//                thisWeekLessons.add(tutorLessons.get(i));
//            }
//        }
//        return thisWeekLessons;
//    }

}