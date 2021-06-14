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

    public boolean signUp(String email, String lastName, String firstName, String genderString, String birthdayDateString, String gradeString, String password, String confirm){
        if(!password.equals(confirm)){return false;}
        Gender gender = Gender.valueOf(genderString.toUpperCase());
        Date birthdayDate;
        try {birthdayDate = new SimpleDateFormat("dd/MM/yyyy").parse(birthdayDateString);}catch(Exception e){return false;}
        int grade = new Integer(gradeString.charAt(0));
        try{grade = grade*10+new Integer(gradeString.charAt(1));}catch (Exception e){}

        // check specific details

        Student student = new Student(email, lastName, firstName, gender, birthdayDate, grade, password);
        if(model.getStudents().contains(student)){return false;}
        else{model.addStudent(student);}
        return true;
    }
}
