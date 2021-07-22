package com.example.smartutor.model;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.time.LocalDate;
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
            model.deleteAllLessons();
            //model.deleteAllStudents();
            //model.deleteAllTutors();
            //model.deleteAllEvents();
            //model.deleteAllPosts();
            model.addLesson(new Lesson("a@gmail.com", "a@gmail.com", LocalDate.now().atTime(17, 0), Profession.MATH));
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
    public LiveData<Lesson> getLessonByTutor(String tutorEmail, LocalDateTime date) {return AppLocalDB.db.lessonDao().getLessonByTutor(tutorEmail, date);}
    public LiveData<Lesson> getLessonByStudent(String studentEmail, LocalDateTime date) {return AppLocalDB.db.lessonDao().getLessonByStudent(studentEmail, date);}
    public LiveData<List<Lesson>> getLessonsByStudent(String email) {return AppLocalDB.db.lessonDao().getLessonsByStudent(email);}
    public LiveData<List<Lesson>> getLessonsByTutor(String email)   {return AppLocalDB.db.lessonDao().getLessonsByTutor(email);}
    public void addLesson(Lesson lesson)                            {executorService.execute(()->AppLocalDB.db.lessonDao().insertLesson(lesson));}
    public void deleteLesson(Lesson lesson)                         {executorService.execute(()->AppLocalDB.db.lessonDao().deleteLesson(lesson));}
    public void deleteAllLessons()                                  {executorService.execute(()->AppLocalDB.db.lessonDao().deleteAll());}

    public LiveData<List<Event>> getEventsByTutor(String email)     {return AppLocalDB.db.eventDao().getEventsByTutor(email);}
    public void addEvent(Event event)                               {executorService.execute(()->AppLocalDB.db.eventDao().insertEvent(event));}
    public void deleteEvent(Event event)                            {executorService.execute(()->AppLocalDB.db.eventDao().deleteEvent(event));}
    public void deleteAllEvents()                                   {executorService.execute(()->AppLocalDB.db.eventDao().deleteAll());}

    public LiveData<List<Post>> getPosts()                          {return AppLocalDB.db.postDao().getPosts();}
    public void addPost(Post post)                                  {executorService.execute(()->AppLocalDB.db.postDao().insertPost(post));}
    public LiveData<List<Post>> getPostsByTutor(String email)       {return AppLocalDB.db.postDao().getPostsByTutor(email);}
    public LiveData<Post> getPostById(int id)                       {return AppLocalDB.db.postDao().getPostById(id);}
    public void updatePost(Post post)                               {executorService.execute(()->AppLocalDB.db.postDao().updatePost(post));}
    public void deletePost(Post post)                               {executorService.execute(()->AppLocalDB.db.postDao().deletePost(post));}
    public void deletePostByTutor(String email)                     {executorService.execute(()->AppLocalDB.db.postDao().deleteByTutor(email));}
    public void deleteAllPosts()                                    {executorService.execute(()->AppLocalDB.db.postDao().deleteAll());}

}