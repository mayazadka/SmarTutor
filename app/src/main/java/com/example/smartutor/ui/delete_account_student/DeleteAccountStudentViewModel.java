package com.example.smartutor.ui.delete_account_student;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Lesson;
import com.example.smartutor.model.Model;
import com.example.smartutor.model.Student;

import java.util.List;

public class DeleteAccountStudentViewModel extends ViewModel {

    private List<Student> studentsList;
    //private String email;

    public DeleteAccountStudentViewModel() { studentsList = Model.getInstance().getStudents(); }
    public int deleteStudent(String email){
        return Model.getInstance().deleteStudent(email);
    }
    //public List<Student> getData() { return studentsList; }
}
