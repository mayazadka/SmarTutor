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
    public void deleteLessonsByTutor(String email)                  {executorService.execute(()->AppLocalDB.db.lessonDao().deleteByTutor(email));}
    public void deleteLessonsByStudent(String email)                {executorService.execute(()->AppLocalDB.db.lessonDao().deleteByStudent(email));}
    public void deleteAllStudents()                                 {executorService.execute(()->AppLocalDB.db.studentDao().deleteAll());}
    public void deleteAllTutors()                                   {executorService.execute(()->AppLocalDB.db.tutorDao().deleteAll());}
    public void deleteAllLessons()                                  {executorService.execute(()->AppLocalDB.db.lessonDao().deleteAll());}

}