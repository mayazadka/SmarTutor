package com.example.smartutor.ui.sign_up_tutor;

import android.util.Log;

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

public class SignUpTutorViewModel  extends ViewModel {
    private Model model;

    public SignUpTutorViewModel(){model = Model.getInstance(); }

    public boolean signUp(String email, String lastName, String firstName, String genderString, String birthdayDateString, String professionsString, String aboutMe, String password, String confirm){
        if(!password.equals(confirm)){return false;}
        Gender gender = Gender.valueOf(genderString.toUpperCase());
        Date birthdayDate;
        try {birthdayDate = new SimpleDateFormat("dd/MM/yyyy").parse(birthdayDateString);}catch(Exception e){return false;}
        professionsString = professionsString.substring(1, professionsString.length()-1).replace(" ", "");
        if(professionsString.equals("")){return false;}
        String[] professionStrings = professionsString.split(",");
        List<Profession> professions = new ArrayList<>();
        for(int i=0;i<professionStrings.length;i++){
            professions.add(Profession.valueOf(professionStrings[i].toUpperCase()));
        }

        // check specific details

        Tutor tutor = new Tutor(email, lastName, firstName, gender, birthdayDate, professions, aboutMe, password);
        if(model.getTutors().contains(tutor)){return false;}
        else{model.addTutor(tutor);}
        return true;
    }
}
