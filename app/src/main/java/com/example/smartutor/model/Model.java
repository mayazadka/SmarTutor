package com.example.smartutor.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.smartutor.MyApplication;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Model {
    public enum LoadingState{loaded, loading, error}
    public interface OnCompleteListener{
        public void onComplete();
    }

    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private static Model model = null;

    public MutableLiveData<LoadingState> studentLoadingState = new MutableLiveData<>(LoadingState.loaded);
    public MutableLiveData<LoadingState> tutorLoadingState = new MutableLiveData<>(LoadingState.loaded);
    public MutableLiveData<LoadingState> lessonLoadingState = new MutableLiveData<>(LoadingState.loaded);
    public MutableLiveData<LoadingState> eventLoadingState = new MutableLiveData<>(LoadingState.loaded);

    private MutableLiveData<List<Student>> students = new MutableLiveData<>(new LinkedList<>());
    private MutableLiveData<List<Tutor>> tutors = new MutableLiveData<>(new LinkedList<>());
    private MutableLiveData<List<Lesson>> lessons = new MutableLiveData<>(new LinkedList<>());
    private MutableLiveData<List<Event>> events = new MutableLiveData<>(new LinkedList<>());

    private Model(){
        new ModelFireBase();
        students.observeForever(s -> studentLoadingState.setValue(LoadingState.loaded));
        tutors.observeForever(t -> tutorLoadingState.setValue(LoadingState.loaded));
        lessons.observeForever(l -> lessonLoadingState.setValue(LoadingState.loaded));
        events.observeForever(l -> eventLoadingState.setValue(LoadingState.loaded));
    }
    public static Model getInstance(){
        if(model == null){
            model = new Model();
        }
        return model;
    }

    public LiveData<List<Student>> getStudents(){
        ModelFireBase.getStudents(s -> students.setValue(s));
        return students;
    }
    public LiveData<Student> getStudent(String email){
        MutableLiveData<Student> student = new MutableLiveData<>();
        ModelFireBase.getStudents(s -> {
            if(s.contains(new Student(email, null, null, null, null, 0, null))){
                student.setValue(s.get(s.indexOf(new Student(email, null, null, null, null, 0, null))));
            }
            else{
                student.setValue(null);
            }
        });
        getStudents();
        return student;
    }
    public void addStudent(Student student, OnCompleteListener listener){
        studentLoadingState.setValue(LoadingState.loading);
        ModelFireBase.addStudent(student, listener);
        getStudents();
    }
    public void updateStudent(Student student, OnCompleteListener listener){
        studentLoadingState.setValue(LoadingState.loading);
        ModelFireBase.updateStudent(student, listener);
        getStudent(student.getEmail());
    }
    public void deleteStudent(Student student, OnCompleteListener listener){
        studentLoadingState.setValue(LoadingState.loading);
        ModelFireBase.deleteStudent(student, listener);
        getStudent(student.getEmail());
    }

    public LiveData<List<Tutor>> getTutors(){
        ModelFireBase.getTutors(t -> tutors.setValue(t));
        return tutors;
    }
    public LiveData<Tutor> getTutor(String email){
        MutableLiveData<Tutor> tutor = new MutableLiveData<>();
        ModelFireBase.getTutors(t -> {
            if(t.contains(new Tutor(email, null, null, null, null, null, null, null))){
                tutor.setValue(t.get(t.indexOf(new Tutor(email, null, null, null, null, null, null, null))));
            }
            else{
                tutor.setValue(null);
            }
        });
        getTutors();
        return tutor;
    }
    public void addTutor(Tutor tutor, OnCompleteListener listener){
        tutorLoadingState.setValue(LoadingState.loading);
        ModelFireBase.addTutor(tutor, listener);
        getTutors();
    }
    public void updateTutor(Tutor tutor, OnCompleteListener listener){
        tutorLoadingState.setValue(LoadingState.loading);
        ModelFireBase.updateTutor(tutor, listener);
        getTutor(tutor.getEmail());
    }
    public void deleteTutor(Tutor tutor, OnCompleteListener listener){
        tutorLoadingState.setValue(LoadingState.loading);
        ModelFireBase.deleteTutor(tutor, listener);
        getTutor(tutor.getEmail());
    }

    public LiveData<List<Lesson>> getLessons(){
        ModelFireBase.getLessons(l -> lessons.setValue(l));
        return lessons;
    }
    public LiveData<Lesson> getLessonByTutor(String tutorEmail, LocalDateTime date) {
        MutableLiveData<Lesson> lessonByTutor = new MutableLiveData<>();
        ModelFireBase.getLessons(l -> {
            for(Lesson lesson : l){
                if(lesson.getTutorEmail().equals(tutorEmail) && lesson.getDate().equals(date)){
                    lessonByTutor.setValue(lesson);
                    return;
                }
            }
            lessonByTutor.setValue(null);
        });
        getLessons();
        return lessonByTutor;
    }
    public LiveData<Lesson> getLessonByStudent(String studentEmail, LocalDateTime date) {
        MutableLiveData<Lesson> lessonByStudent = new MutableLiveData<>();
        ModelFireBase.getLessons(l -> {
            for(Lesson lesson : l){
                if(lesson.getStudentEmail().equals(studentEmail) && lesson.getDate().equals(date)){
                    lessonByStudent.setValue(lesson);
                    return;
                }
            }
            lessonByStudent.setValue(null);
        });
        getLessons();
        return lessonByStudent;
    }
    public void addLesson(Lesson lesson, OnCompleteListener listener){
        lessonLoadingState.setValue(LoadingState.loading);
        ModelFireBase.addLesson(lesson, listener);
        getLessons();
    }
    public void deleteLesson(Lesson lesson, OnCompleteListener listener){
        lessonLoadingState.setValue(LoadingState.loading);
        ModelFireBase.deleteLesson(lesson, listener);
        getLessonByStudent(lesson.getStudentEmail(), lesson.getDate());
        getLessonByTutor(lesson.getTutorEmail(), lesson.getDate());
    }

    public LiveData<List<Event>> getEvents() {
        ModelFireBase.getEvents(e -> events.setValue(e));
        return events;
    }
    public LiveData<Event> getEvent(String email, LocalDateTime date) {
        MutableLiveData<Event> event = new MutableLiveData<>();
        ModelFireBase.getEvents(events -> {
            for(Event e : events){
                if(e.getTutorEmail().equals(email) && e.getDate().equals(date)){
                    event.setValue(e);
                    return;
                }
            }
            event.setValue(null);
        });
        getEvents();
        return event;
    }
    public void addEvent(Event event, OnCompleteListener listener){
        eventLoadingState.setValue(LoadingState.loading);
        ModelFireBase.addEvent(event, listener);
        getEvents();
    }
    public void deleteEvent(Event event, OnCompleteListener listener){
        eventLoadingState.setValue(LoadingState.loading);
        ModelFireBase.deleteEvent(event, listener);
        getEvents();
    }

    public LiveData<List<Post>> getPosts()                          {return AppLocalDB.db.postDao().getPosts();}
    public void addPost(Post post)                                  {executorService.execute(()->AppLocalDB.db.postDao().insertPost(post));}
    public LiveData<List<Post>> getPostsByTutor(String email)       {return AppLocalDB.db.postDao().getPostsByTutor(email);}
    public LiveData<Post> getPostById(int id)                       {return AppLocalDB.db.postDao().getPostById(id);}
    public void updatePost(Post post)                               {
        Log.d("TAG", "description in model: " + post.getText());
        executorService.execute(()->AppLocalDB.db.postDao().updatePost(post));}
    public void deletePost(Post post)                               {executorService.execute(()->AppLocalDB.db.postDao().deletePost(post));}
    public void deletePostByTutor(String email)                     {executorService.execute(()->AppLocalDB.db.postDao().deleteByTutor(email));}
    public void deleteAllPosts()                                    {executorService.execute(()->AppLocalDB.db.postDao().deleteAll());}

}