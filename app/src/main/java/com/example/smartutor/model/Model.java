package com.example.smartutor.model;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Model {
    public enum LoadingState{loaded, loading}
    public interface OnCompleteListener{
        void onComplete();
    }

    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private static Model model = null;

    public MutableLiveData<LoadingState> studentLoadingState = new MutableLiveData<>(LoadingState.loaded);
    public MutableLiveData<LoadingState> tutorLoadingState = new MutableLiveData<>(LoadingState.loaded);
    public MutableLiveData<LoadingState> lessonLoadingState = new MutableLiveData<>(LoadingState.loaded);
    public MutableLiveData<LoadingState> eventLoadingState = new MutableLiveData<>(LoadingState.loaded);
    public MutableLiveData<LoadingState> postLoadingState = new MutableLiveData<>(LoadingState.loaded);

    private Model(){
        Student.setLocalLatUpdateTime(Long.valueOf(0));
        Tutor.setLocalLatUpdateTime(Long.valueOf(0));
        Lesson.setLocalLatUpdateTime(Long.valueOf(0));
        Event.setLocalLatUpdateTime(Long.valueOf(0));
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
                if(le.getDeleted()){
                    AppLocalDB.db.lessonDao().deleteLesson(le);
                }
                else{
                    AppLocalDB.db.lessonDao().insertLesson(le);
                }
                if(lastUpdate < le.getLastUpdated()){
                    lastUpdate = le.getLastUpdated();
                }
            }
            Lesson.setLocalLatUpdateTime(lastUpdate);
            lessonLoadingState.postValue(LoadingState.loaded);
        }));
    }
    public LiveData<List<Lesson>> getLessonsByTutor(String email){
        refreshLessons();
        return AppLocalDB.db.lessonDao().getLessonsByTutor(email);
    }
    public LiveData<List<Lesson>> getLessonsByStudent(String email){
        refreshLessons();
        return AppLocalDB.db.lessonDao().getLessonsByStudent(email);
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

    public void refreshEvents(){
        eventLoadingState.setValue(LoadingState.loading);

        Long localLastTimeUpdate = Event.getLocalLatUpdateTime();

        ModelFireBase.getEvents(localLastTimeUpdate, e -> executorService.execute(()->{
            Long lastUpdate = Long.valueOf(0);
            for(Event ev: e){

                if(ev.getDeleted() == true){
                    AppLocalDB.db.eventDao().deleteEvent(ev);
                }
                else{
                    AppLocalDB.db.eventDao().insertEvent(ev);
                }
                if(lastUpdate < ev.getLastUpdated()){
                    lastUpdate = ev.getLastUpdated();
                }
            }
            Event.setLocalLatUpdateTime(lastUpdate);
            eventLoadingState.postValue(LoadingState.loaded);
        }));
    }
    public LiveData<List<Event>> getEventsByTutor(String email){
        refreshEvents();
        return AppLocalDB.db.eventDao().getEventsByTutor(email);
    }
    public LiveData<Event> getEvent(String email, LocalDateTime date) {
        refreshEvents();
        return AppLocalDB.db.eventDao().getEvent(email, date);
    }
    public void addEvent(Event event, OnCompleteListener listener){
        eventLoadingState.setValue(LoadingState.loading);
        ModelFireBase.addEvent(event, ()->{
            refreshEvents();
            listener.onComplete();
        });
    }
    public void deleteEvent(Event event, OnCompleteListener listener){
        eventLoadingState.setValue(LoadingState.loading);
        ModelFireBase.deleteEvent(event, ()->{
            refreshEvents();
            listener.onComplete();
        });
    }

    public void refreshPosts(){
        postLoadingState.setValue(LoadingState.loading);

        Long localLastTimeUpdate = Post.getLocalLatUpdateTime();

        ModelFireBase.getPosts(localLastTimeUpdate, p -> executorService.execute(()->{
            Long lastUpdate = Long.valueOf(0);
            for(Post po: p){
                if(po.getDeleted()){AppLocalDB.db.postDao().deletePost(po);}
                else{AppLocalDB.db.postDao().insertPost(po);}
                if(lastUpdate < po.getLastUpdated()){
                    lastUpdate = po.getLastUpdated();
                }
            }
            Post.setLocalLatUpdateTime(lastUpdate);
            postLoadingState.postValue(LoadingState.loaded);
        }));
    }
    public LiveData<List<Post>> getPosts() {
        refreshPosts();
        return AppLocalDB.db.postDao().getPosts();
    }
    public LiveData<List<Post>> getPostsByTutor(String email) {
        refreshPosts();
        return AppLocalDB.db.postDao().getPostsByTutor(email);
    }
    public LiveData<Post> getPost(String id) {
        refreshPosts();
        return AppLocalDB.db.postDao().getPostById(id);
    }
    public void addPost(Post post, Bitmap imageBmp, OnCompleteListener listener){
        postLoadingState.setValue(LoadingState.loading);
        ModelFireBase.addPost(post, id-> {
            post.setId(id);
            updatePost(post, imageBmp, ()->{
                refreshPosts();
                listener.onComplete();
            });
        });
    }
    public void updatePost(Post post, Bitmap bitmap, OnCompleteListener listener){
        postLoadingState.setValue(LoadingState.loading);
        ModelFireBase.deleteImage(post.getPicture(), ()-> ModelFireBase.uploadImage(bitmap, post.getId(), url ->{
            post.setPicture(url);
            ModelFireBase.updatePost(post, ()->{
                refreshPosts();
                listener.onComplete();
            });
        }));
    }
    public void deletePost(Post post, OnCompleteListener listener){
        postLoadingState.setValue(LoadingState.loading);
        ModelFireBase.deletePost(post, ()->{
            refreshPosts();
            listener.onComplete();
        });
    }

    public void createUserAccount(String type, String email, String password, Model.OnCompleteListener OnSuccess, Model.OnCompleteListener OnFailure) {
        ModelFireBase.createUserAccount(type, email, password, OnSuccess, OnFailure);
    }
    public String getCurrentUserEmail(){
        return ModelFireBase.getCurrentUserEmail();
    }
    public void signIn(String type, String email, String password, Model.OnCompleteListener OnSuccess, Model.OnCompleteListener OnFailure) {
        ModelFireBase.signIn(type, email, password, OnSuccess, OnFailure);
    }
    public void signOut() {ModelFireBase.signOut();}
}