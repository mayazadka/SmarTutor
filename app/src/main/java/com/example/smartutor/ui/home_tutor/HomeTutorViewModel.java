package com.example.smartutor.ui.home_tutor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Event;
import com.example.smartutor.model.Lesson;
import com.example.smartutor.model.LoadingState;
import com.example.smartutor.model.Model;
import com.example.smartutor.model.Student;
import com.example.smartutor.model.Tutor;

import java.util.List;

public class HomeTutorViewModel extends ViewModel {

    private Model model = Model.getInstance();
    private LiveData<Tutor> tutor;
    private LiveData<List<Lesson>> lessons;
    private LiveData<List<Event>> events;


    public HomeTutorViewModel() {
        String email = getCurrentUserEmail();
        tutor = model.getTutor(email);
        lessons = model.getLessonsByTutor(email);
        events = model.getEventsByTutor(email);
    }

    public LiveData<Tutor> getTutor()                   { return tutor; }
    public LiveData<List<Lesson>> getLessonsByTutor()   { return lessons; }
    public LiveData<List<Event>> getEventsByTutor()     { return events; }
    public LiveData<Student> getStudent(String email)   { return model.getStudent(email); }
    public String getCurrentUserEmail()                 { return model.getCurrentUserEmail(); }

    public void refresh() {
        model.refreshTutors();
        model.refreshEvents();
        model.refreshLessons();
        model.refreshStudents();
    }

    public LiveData<LoadingState> getTutorLoadingState() {return model.tutorLoadingState; }
    public LiveData<LoadingState> getStudentLoadingState() {return model.studentLoadingState; }
    public LiveData<LoadingState> getLessonLoadingState() {return model.lessonLoadingState; }
    public LiveData<LoadingState> getEventLoadingState() {return model.eventLoadingState; }

}