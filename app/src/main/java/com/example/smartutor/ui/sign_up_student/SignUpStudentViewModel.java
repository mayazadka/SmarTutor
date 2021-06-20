package com.example.smartutor.ui.sign_up_student;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Gender;
import com.example.smartutor.model.Model;
import com.example.smartutor.model.Student;

import java.text.SimpleDateFormat;
import java.util.Date;

@RequiresApi(api = Build.VERSION_CODES.O)
public class SignUpStudentViewModel  extends ViewModel {
    private Model model = Model.getInstance();

    public SignUpStudentViewModel() {}
    public void addStudent(Student student){
        model.addStudent(student);
    }
}
