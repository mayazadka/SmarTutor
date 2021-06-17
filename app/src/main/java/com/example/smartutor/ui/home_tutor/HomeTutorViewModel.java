package com.example.smartutor.ui.home_tutor;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Lesson;
import com.example.smartutor.model.Model;
import com.example.smartutor.model.Student;
import com.example.smartutor.model.Tutor;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class HomeTutorViewModel extends ViewModel {
    private Model model;
    private List<Student> tutorsList;

    public HomeTutorViewModel() {
        model = Model.getInstance();
    }


    public Tutor getTutor(String email){
        return model.getTutor(email);
    }
    public List<Lesson> getTutorLessons(String email){
        return model.getTutorLessons(email);
    }
}