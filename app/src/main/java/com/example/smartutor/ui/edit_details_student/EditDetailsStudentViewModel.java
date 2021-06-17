package com.example.smartutor.ui.edit_details_student;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Lesson;
import com.example.smartutor.model.Model;
import com.example.smartutor.model.Student;

import java.util.List;

public class EditDetailsStudentViewModel extends ViewModel {

    private List<Student> studentsList;
    //private String email;

    public EditDetailsStudentViewModel() { studentsList = Model.getInstance().getStudents(); }
    public Student getStudent(String email){
        return Model.getInstance().getStudent(email);
    }
    public int updateStudent(Student student){
        return Model.getInstance().updateStudent(student);
    }

    //public List<Student> getData() { return studentsList; }
}