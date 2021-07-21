package com.example.smartutor.model;

import androidx.lifecycle.LiveData;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Model {
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private static Model model = null;

    private Model(){}
    public static Model getInstance(){
        if(model == null){
            model = new Model();
            model.addLesson(new Lesson("a@gmail.com", "b@gmail.com", LocalDateTime.now(), Profession.MATH));
        }
        return model;
    }

    public LiveData<List<Student>> getStudents()                    {return AppLocalDB.db.studentDao().getStudents();}
    public void addStudent(Student student)                         {executorService.execute(()->AppLocalDB.db.studentDao().insertStudent(student));}
    public LiveData<Student> getStudent(String email)               {return AppLocalDB.db.studentDao().getStudent(email);}
    public void updateStudent(Student student)                      {executorService.execute(()->AppLocalDB.db.studentDao().updateStudent(student));}
    public void deleteStudent(Student student)                      {executorService.execute(()->AppLocalDB.db.studentDao().deleteStudent(student));}
    public void deleteAllStudents()                                 {executorService.execute(()->AppLocalDB.db.studentDao().deleteAll());}

    public LiveData<List<Tutor>> getTutors()                        {return AppLocalDB.db.tutorDao().getTutors();}
    public void addTutor(Tutor tutor)                               {executorService.execute(()->AppLocalDB.db.tutorDao().insertTutor(tutor));}
    public LiveData<Tutor> getTutor(String email)                   {return AppLocalDB.db.tutorDao().getTutor(email);}
    public void updateTutor(Tutor tutor)                            {executorService.execute(()->AppLocalDB.db.tutorDao().updateTutor(tutor));}
    public void deleteTutor(Tutor tutor)                            {executorService.execute(()->AppLocalDB.db.tutorDao().deleteTutor(tutor));}
    public void deleteAllTutors()                                   {executorService.execute(()->AppLocalDB.db.tutorDao().deleteAll());}

    public LiveData<List<Lesson>> getLessons()                      {return AppLocalDB.db.lessonDao().getLessons();}
    public LiveData<List<Lesson>> getLessonsByStudent(String email) {return AppLocalDB.db.lessonDao().getLessonsByStudent(email);}
    public LiveData<List<Lesson>> getLessonsByTutor(String email)   {return AppLocalDB.db.lessonDao().getLessonsByTutor(email);}
    public void addLesson(Lesson lesson)                            {executorService.execute(()->AppLocalDB.db.lessonDao().insertLesson(lesson));}
    public void deleteLessonsByTutor(String email)                  {executorService.execute(()->AppLocalDB.db.lessonDao().deleteByTutor(email));}
    public void deleteLessonsByStudent(String email)                {executorService.execute(()->AppLocalDB.db.lessonDao().deleteByStudent(email));}
    public void deleteAllLessons()                                  {executorService.execute(()->AppLocalDB.db.lessonDao().deleteAll());}

    public LiveData<List<Event>> getEvents()                        {return AppLocalDB.db.eventDao().getEvents();}
    public LiveData<List<Event>> getEventsByTutor(String email)     {return AppLocalDB.db.eventDao().getEventsByTutor(email);}
    public void addEvent(Event event)                               {executorService.execute(()->AppLocalDB.db.eventDao().insertEvent(event));}
    public void deleteEventsByTutor(String email)                   {executorService.execute(()->AppLocalDB.db.eventDao().deleteByTutor(email));}
    public void deleteEvent(Event event)                            {executorService.execute(()->AppLocalDB.db.eventDao().deleteEvent(event));}
    public void deleteAllEvents()                                   {executorService.execute(()->AppLocalDB.db.eventDao().deleteAll());}

}