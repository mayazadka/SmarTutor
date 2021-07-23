package com.example.smartutor.ui.sign_up_tutor;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Gender;
import com.example.smartutor.model.Model;
import com.example.smartutor.model.Profession;
import com.example.smartutor.model.Student;
import com.example.smartutor.model.Tutor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class SignUpTutorViewModel  extends ViewModel {

    private Model model = Model.getInstance(); ;
    private LiveData<List<Tutor>> tutors;

    public SignUpTutorViewModel(){
        tutors = model.getTutors();

        tutors.observeForever(ts-> {});
    }

    public void addTutor(Tutor tutor, Model.OnCompleteListener listener)   {model.addTutor(tutor, listener);}

    public boolean isExistTutor(String email) throws Exception {
        if(tutors.getValue() == null){return false;}
        for (Tutor tutor : tutors.getValue()) {
            if (tutor.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

}
