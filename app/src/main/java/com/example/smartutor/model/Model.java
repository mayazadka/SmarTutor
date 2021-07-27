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
    public interface OnCompleteSignInListener{
        public void onComplete(boolean signIn);
    }

    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private static Model model = null;

    public MutableLiveData<LoadingState> studentLoadingState = new MutableLiveData<>(LoadingState.loaded);
    public MutableLiveData<LoadingState> tutorLoadingState = new MutableLiveData<>(LoadingState.loaded);
    public MutableLiveData<LoadingState> lessonLoadingState = new MutableLiveData<>(LoadingState.loaded);
    public MutableLiveData<LoadingState> eventLoadingState = new MutableLiveData<>(LoadingState.loaded);
    public MutableLiveData<LoadingState> postLoadingState = new MutableLiveData<>(LoadingState.loaded);

    private MutableLiveData<List<Lesson>> lessons = new MutableLiveData<>(new LinkedList<>());
    private MutableLiveData<List<Event>> events = new MutableLiveData<>(new LinkedList<>());
    private MutableLiveData<List<Post>> posts = new MutableLiveData<>(new LinkedList<>());

    private Model(){
        new ModelFireBase();
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

    public void refreshStudents(){
        studentLoadingState.setValue(LoadingState.loading);
        Long localLastTimeUpdate = Student.getLocalLatUpdateTime();
        ModelFireBase.getStudents(localLastTimeUpdate, s -> executorService.execute(()->{
            Long lastUpdate = Long.valueOf(0);
            for(Student st: s){
                if(st.getDeleted()){AppLocalDB.db.studentDao().deleteStudent(st);}
                else{AppLocalDB.db.studentDao().insertStudent(st);}
                if(lastUpdate < st.getLastUpdated()){
                    lastUpdate = st.getLastUpdated();
                }
            }
            Student.setLocalLatUpdateTime(lastUpdate);
            studentLoadingState.postValue(LoadingState.loaded);
        }));
    }
    public LiveData<List<Student>> getStudents(){
        refreshStudents();
        return AppLocalDB.db.studentDao().getStudents();
    }
    public LiveData<Student> getStudent(String email){
        refreshStudents();
        return AppLocalDB.db.studentDao().getStudent(email);
    }
    public void addStudent(Student student, OnCompleteListener listener){
        studentLoadingState.setValue(LoadingState.loading);
        ModelFireBase.addStudent(student, ()->{
            refreshStudents();
            listener.onComplete();
        });
    }
    public void updateStudent(Student student, OnCompleteListener listener){
        studentLoadingState.setValue(LoadingState.loading);
        ModelFireBase.updateStudent(student, ()->{
            refreshStudents();
            listener.onComplete();
        });
    }
    public void deleteStudent(Student student, OnCompleteListener listener){
        studentLoadingState.setValue(LoadingState.loading);
        student.setDeleted(true);
        ModelFireBase.deleteStudent(student, ()->{
            refreshStudents();
            listener.onComplete();
        });
    }

    public void refreshTutors(){
        tutorLoadingState.setValue(LoadingState.loading);

        Long localLastTimeUpdate = Tutor.getLocalLatUpdateTime();

        ModelFireBase.getTutors(localLastTimeUpdate, t -> executorService.execute(()->{
            Long lastUpdate = Long.valueOf(0);
            for(Tutor tu: t){
                if(tu.getDeleted()){AppLocalDB.db.tutorDao().deleteTutor(tu);}
                else{AppLocalDB.db.tutorDao().insertTutor(tu);}
                if(lastUpdate < tu.getLastUpdated()){
                    lastUpdate = tu.getLastUpdated();
                }
            }
            Tutor.setLocalLatUpdateTime(lastUpdate);
            tutorLoadingState.postValue(LoadingState.loaded);
        }));
    }
    public LiveData<List<Tutor>> getTutors(){
        refreshTutors();
        return AppLocalDB.db.tutorDao().getTutors();
    }
    public LiveData<Tutor> getTutor(String email){
        refreshTutors();
        return AppLocalDB.db.tutorDao().getTutor(email);
    }
    public void addTutor(Tutor tutor, OnCompleteListener listener){
        tutorLoadingState.setValue(LoadingState.loading);
        ModelFireBase.addTutor(tutor, ()->{
            refreshTutors();
            listener.onComplete();
        });
    }
    public void updateTutor(Tutor tutor, OnCompleteListener listener){
        tutorLoadingState.setValue(LoadingState.loading);
        ModelFireBase.updateTutor(tutor, ()->{
            refreshTutors();
            listener.onComplete();
        });
    }
    public void deleteTutor(Tutor tutor, OnCompleteListener listener){
        tutorLoadingState.setValue(LoadingState.loading);
        tutor.setDeleted((true));
        ModelFireBase.deleteTutor(tutor, ()->{
            refreshTutors();
            listener.onComplete();
        });
    }

    public void refreshLessons(){
        lessonLoadingState.setValue(LoadingState.loading);

        Long localLastTimeUpdate = Lesson.getLocalLatUpdateTime();

        ModelFireBase.getLessons(localLastTimeUpdate, l -> executorService.execute(()->{
            Long lastUpdate = Long.valueOf(0);
            for(Lesson le: l){
                if(le.getDeleted()){AppLocalDB.db.lessonDao().deleteLesson(le);}
                else{AppLocalDB.db.lessonDao().insertLesson(le);}
                if(lastUpdate < le.getLastUpdated()){
                    lastUpdate = le.getLastUpdated();
                }
            }
            Lesson.setLocalLatUpdateTime(lastUpdate);
            lessonLoadingState.postValue(LoadingState.loaded);
        }));
    }
    public LiveData<List<Lesson>> getLessons(){
        refreshLessons();
        return AppLocalDB.db.lessonDao().getLessons();
    }
    public LiveData<Lesson> getLessonByTutor(String tutorEmail, LocalDateTime date) {
        refreshLessons();
        return AppLocalDB.db.lessonDao().getLessonByTutor(tutorEmail, date);
    }
    public LiveData<Lesson> getLessonByStudent(String studentEmail, LocalDateTime date) {
        refreshLessons();
        return AppLocalDB.db.lessonDao().getLessonByStudent(studentEmail, date);
    }
    public void addLesson(Lesson lesson, OnCompleteListener listener){
        lessonLoadingState.setValue(LoadingState.loading);
        ModelFireBase.addLesson(lesson, ()->{
            refreshLessons();
            listener.onComplete();
        });
    }
    public void deleteLesson(Lesson lesson, OnCompleteListener listener){
        lessonLoadingState.setValue(LoadingState.loading);
        ModelFireBase.deleteLesson(lesson, ()->{
            refreshLessons();
            listener.onComplete();
        });
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

    public boolean checkCurrentUser(){
        return ModelFireBase.checkCurrentUser();
    }
    public boolean createUserAccount(String email, String password) {
        return ModelFireBase.createUserAccount(email, password);
    }
    public void signIn(String email, String password, OnCompleteSignInListener listener) {
        ModelFireBase.signIn(email, password, listener);
    }
    public boolean sendEmailVerification() {
        return ModelFireBase.sendEmailVerification();
    }
}