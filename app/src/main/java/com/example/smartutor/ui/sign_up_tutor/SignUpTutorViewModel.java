package com.example.smartutor.ui.sign_up_tutor;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Gender;
import com.example.smartutor.model.Model;
import com.example.smartutor.model.Profession;
import com.example.smartutor.model.Student;
import com.example.smartutor.model.Tutor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class SignUpTutorViewModel  extends ViewModel {
    private Model model = Model.getInstance(); ;

    public SignUpTutorViewModel(){}

    public void addTutor(Tutor tutor){model.addTutor(tutor);}
}
