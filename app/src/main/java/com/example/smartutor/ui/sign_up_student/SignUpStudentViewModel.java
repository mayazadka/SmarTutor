package com.example.smartutor.ui.sign_up_student;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Gender;
import com.example.smartutor.model.Model;
import com.example.smartutor.model.Student;
import com.example.smartutor.model.Tutor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class SignUpStudentViewModel  extends ViewModel {

    private Model model = Model.getInstance();
    private LiveData<List<Student>> students;

    public SignUpStudentViewModel() {
        students = model.getStudents();

        students.observeForever(students->{});
    }

    public void addStudent(Student student)     {model.addStudent(student);}

    public boolean isExistStudent(String email, String password) throws Exception {
        if(students.getValue() == null){throw new Exception();} //TODO exception
        for (Student student : students.getValue()) {
            if (student.getEmail().equals(email) && student.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

}
