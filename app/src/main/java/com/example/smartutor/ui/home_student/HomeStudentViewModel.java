package com.example.smartutor.ui.home_student;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Lesson;
import com.example.smartutor.model.Model;
import com.example.smartutor.model.Student;

import java.util.List;

public class HomeStudentViewModel extends ViewModel {

    private List<Student> studentsList;
    //private String email;

    public HomeStudentViewModel() { studentsList = Model.getInstance().getStudents(); }
    public Student getStudent(String email){
        return Model.getInstance().getStudent(email);
    }
    public List<Lesson> getStudentLessons(String email){
        return Model.getInstance().getStudentLessons(email);
    }
    //public List<Student> getData() { return studentsList; }
}