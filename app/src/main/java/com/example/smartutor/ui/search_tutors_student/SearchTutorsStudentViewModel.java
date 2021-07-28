package com.example.smartutor.ui.search_tutors_student;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.LoadingState;
import com.example.smartutor.model.Model;
import com.example.smartutor.model.Tutor;

import java.util.List;

public class SearchTutorsStudentViewModel extends ViewModel {

    private Model model = Model.getInstance();
    private LiveData<List<Tutor>> tutors;

    public SearchTutorsStudentViewModel() {
        tutors = model.getTutors();
    }

    public LiveData<List<Tutor>> getTutors()    { return tutors; }
    public String getCurrentUserEmail()         { return model.getCurrentUserEmail(); }

    public void refresh() {model.refreshTutors(); }

    public LiveData<LoadingState> getTutorLoadingState() {return model.tutorLoadingState; }
}