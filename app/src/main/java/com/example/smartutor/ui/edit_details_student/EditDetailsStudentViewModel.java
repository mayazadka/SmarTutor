package com.example.smartutor.ui.edit_details_student;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Lesson;
import com.example.smartutor.model.Model;
import com.example.smartutor.model.Student;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class EditDetailsStudentViewModel extends ViewModel {

    private Model model = Model.getInstance();

    public EditDetailsStudentViewModel() { }
    public Student getStudent(String email){return model.getStudent(email).getStudent();}
    public void updateStudent(Student student){model.updateStudent(student);}
}