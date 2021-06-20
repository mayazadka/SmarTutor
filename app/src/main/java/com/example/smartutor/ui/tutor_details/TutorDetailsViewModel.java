package com.example.smartutor.ui.tutor_details;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Model;
import com.example.smartutor.model.Tutor;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class TutorDetailsViewModel extends ViewModel {
    private Model model = Model.getInstance();

    public TutorDetailsViewModel() {}
    public Tutor getTutor(String email){
        return model.getTutor(email).getTutor();
    }
}