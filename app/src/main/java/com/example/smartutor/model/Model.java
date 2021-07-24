package com.example.smartutor.model;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.smartutor.MyApplication;
import com.example.smartutor.ui.add_post.AddPostViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
    public MutableLiveData<LoadingState> postLoadingState = new MutableLiveData<>(LoadingState.loaded);

    private MutableLiveData<List<Tutor>> tutors = new MutableLiveData<>(new LinkedList<>());
    private MutableLiveData<List<Lesson>> lessons = new MutableLiveData<>(new LinkedList<>());
    private MutableLiveData<List<Event>> events = new MutableLiveData<>(new LinkedList<>());
    private MutableLiveData<List<Post>> posts = new MutableLiveData<>(new LinkedList<>());

    private Model(){
        new ModelFireBase();
        tutors.observeForever(t -> tutorLoadingState.setValue(LoadingState.loaded));
        lessons.observeForever(l -> lessonLoadingState.setValue(LoadingState.loaded));
        events.observeForever(l -> eventLoadingState.setValue(LoadingState.loaded));
        posts.observeForever(p -> postLoadingState.setValue(LoadingState.loaded));
    }
    public static Model getInstance(){
        if(model == null){
            model = new Model();
        }
        return model;
    }

    public LiveData<List<Student>> getStudents(){
        studentLoadingState.setValue(LoadingState.loading);

        Long localLastTimeUpdate = Student.getLocalLatUpdateTime();

        ModelFireBase.getStudents(localLastTimeUpdate, s -> executorService.execute(()->{
            Long lastUpdate = Long.valueOf(0);
            for(Student st: s){
                AppLocalDB.db.studentDao().insertStudent(st);
                if(lastUpdate < st.getLastUpdated()){
                    lastUpdate = st.getLastUpdated();
                }
            }
            Student.setLocalLatUpdateTime(lastUpdate);
            studentLoadingState.postValue(LoadingState.loaded);
        }));
        return AppLocalDB.db.studentDao().getStudents();
    }
    public LiveData<Student> getStudent(String email){
        studentLoadingState.setValue(LoadingState.loading);

        Long localLastTimeUpdate = Student.getLocalLatUpdateTime();
        ModelFireBase.getStudent(localLastTimeUpdate, email, s -> executorService.execute(()->{
            Long lastUpdate = Long.valueOf(0);
            if(s != null){
                AppLocalDB.db.studentDao().insertStudent(s);
                if (lastUpdate < s.getLastUpdated()) {
                    lastUpdate = s.getLastUpdated();
                }
                Student.setLocalLatUpdateTime(lastUpdate);
            }
            studentLoadingState.postValue(LoadingState.loaded);
        }));
        return AppLocalDB.db.studentDao().getStudent(email);
    }
    public void addStudent(Student student, OnCompleteListener listener){
        studentLoadingState.setValue(LoadingState.loading);
        ModelFireBase.addStudent(student, ()->{
            getStudents();
            listener.onComplete();
        });
    }
    public void updateStudent(Student student, OnCompleteListener listener){
        ModelFireBase.updateStudent(student, ()->{
            getStudents();
            listener.onComplete();
        });
    }
    public void deleteStudent(Student student, OnCompleteListener listener){
        ModelFireBase.deleteStudent(student, ()->{
            getStudents();
            listener.onComplete();
        });
    }

    public LiveData<List<Tutor>> getTutors(){
        tutorLoadingState.setValue(LoadingState.loading);
        ModelFireBase.getTutors(t -> tutors.setValue(t));
        return tutors;
    }
    public LiveData<Tutor> getTutor(String email){
        tutorLoadingState.setValue(LoadingState.loading);
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
        lessonLoadingState.setValue(LoadingState.loading);
        ModelFireBase.getLessons(l -> lessons.setValue(l));
        return lessons;
    }
    public LiveData<Lesson> getLessonByTutor(String tutorEmail, LocalDateTime date) {
        lessonLoadingState.setValue(LoadingState.loading);
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
        lessonLoadingState.setValue(LoadingState.loading);
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
        eventLoadingState.setValue(LoadingState.loading);
        ModelFireBase.getEvents(e -> events.setValue(e));
        return events;
    }
    public LiveData<Event> getEvent(String email, LocalDateTime date) {
        eventLoadingState.setValue(LoadingState.loading);
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

    public LiveData<List<Post>> getPosts() {
        postLoadingState.setValue(LoadingState.loading);
        ModelFireBase.getPosts((p)-> posts.setValue(p));
        return posts;
    }
    public Long addPost(Post post, OnCompleteListener listener){
        postLoadingState.setValue(LoadingState.loading);
        Long id = ModelFireBase.addPost(post, listener);
        getPosts();
        return id;
    }
    public LiveData<Post> getPost(Long id) {
        postLoadingState.setValue(LoadingState.loading);
        MutableLiveData<Post> post = new MutableLiveData<>();
        ModelFireBase.getPosts(posts -> {
            for(Post p : posts){
                if(p.getId().equals(id)){
                    post.setValue(p);
                    return;
                }
            }
            post.setValue(null);
        });
        getPosts();
        return post;
    }
    public void updatePost(Long postId, Post post, OnCompleteListener listener){
        postLoadingState.setValue(LoadingState.loading);
        ModelFireBase.updatePost(postId, post, listener);
        getPost(post.getId());
    }
    public void deletePost(Post post, OnCompleteListener listener){
        postLoadingState.setValue(LoadingState.loading);
        ModelFireBase.deletePost(post, listener);
        getPosts();
    }

    public void uploadImage(Bitmap imageBmp, String name, final AddPostViewModel.UploadImageListener listener){
        ModelFireBase.uploadImage(imageBmp, name, listener);
    }
}