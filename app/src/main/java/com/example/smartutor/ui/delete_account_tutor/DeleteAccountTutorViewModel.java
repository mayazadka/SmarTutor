package com.example.smartutor.ui.delete_account_tutor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Model;
import com.example.smartutor.model.Student;
import com.example.smartutor.model.Tutor;

import java.util.List;

public class DeleteAccountTutorViewModel extends ViewModel {

    private Model model;
    private List<Tutor> tutorsList;
    //private String email;


    public DeleteAccountTutorViewModel() {
        tutorsList = Model.getInstance().getTutors();
    }

    public Tutor getTutor(String email){
        return model.getTutor(email);
    }
    public int deleteTutor(String email){
        return model.deleteTutor(email);
    }
    //public List<Tutor> getData() { return tutorsList; }
}
