package com.example.smartutor.ui.sign_up_student;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Gender;
import com.example.smartutor.model.Model;
import com.example.smartutor.model.Student;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SignUpStudentViewModel  extends ViewModel {
    private Model model;

    public SignUpStudentViewModel() {
        model = Model.getInstance();
    }

   /* public boolean signUp(String email, String lastName, String firstName, String genderString, String birthdayDateString, String gradeString, String password, String confirm){

        return true;
    }*/
    public int addNewStudent(Student student){
        return model.addNewStudent(student);
    }
}
