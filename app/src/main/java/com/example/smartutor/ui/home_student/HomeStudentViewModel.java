package com.example.smartutor.ui.home_student;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Lesson;
import com.example.smartutor.model.LoadingState;
import com.example.smartutor.model.Model;
import com.example.smartutor.model.Student;
import com.example.smartutor.model.Tutor;

import java.util.List;

public class HomeStudentViewModel extends ViewModel {

    private Model model = Model.getInstance();
    private LiveData<Student> student;
    private LiveData<List<Lesson>> lessons;
    public HomeStudentViewModel() {
        String email = getCurrentUserEmail();
        student = model.getStudent(email);
        lessons = model.getLessonsByStudent(email);
    }

    public LiveData<Student> getStudent()                       { return student; }
    public LiveData<List<Lesson>> getLessonsByStudent()         { return lessons; }
    public LiveData<Tutor> getTutor(String email)               { return model.getTutor(email); }
    public String getCurrentUserEmail()                         { return model.getCurrentUserEmail(); }

    public void refresh() {
        model.refreshStudents();
        model.refreshTutors();
        model.refreshLessons();
    }

    public LiveData<LoadingState> getStudentLoadingState() {return model.studentLoadingState; }
    public LiveData<LoadingState> getTutorLoadingState() {return model.tutorLoadingState; }
    public LiveData<LoadingState> getLessonLoadingState() {return model.lessonLoadingState; }

}