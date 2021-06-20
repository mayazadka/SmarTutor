package com.example.smartutor.ui.edit_details_tutor;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
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

@RequiresApi(api = Build.VERSION_CODES.O)
public class EditDetailsTutorViewModel extends ViewModel {
    private Model model = Model.getInstance();

    public EditDetailsTutorViewModel() { }
    public Tutor getTutor(String email){return model.getTutor(email).getTutor();}
    public void updateTutor(Tutor tutor){model.updateTutor(tutor);}
}