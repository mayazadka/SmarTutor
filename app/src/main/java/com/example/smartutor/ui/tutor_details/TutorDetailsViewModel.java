package com.example.smartutor.ui.tutor_details;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Model;
import com.example.smartutor.model.Tutor;

import java.util.List;

public class TutorDetailsViewModel extends ViewModel {
    private List<Tutor> tutorsList;

    public TutorDetailsViewModel() {
        tutorsList = Model.getInstance().getTutors();
    }

    public List<Tutor> getTutors(){
        return Model.getInstance().getTutors();
    }
    public Tutor getTutor(String email){
        return Model.getInstance().getTutor(email);
    }

    public List<Tutor> getData() { return tutorsList; }
}