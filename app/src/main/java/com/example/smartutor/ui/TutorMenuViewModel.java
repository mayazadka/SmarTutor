package com.example.smartutor.ui;

import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Model;
import com.example.smartutor.model.Student;

import java.util.List;

public class TutorMenuViewModel extends ViewModel {
    private Model model;

    public TutorMenuViewModel() {
        model = Model.getInstance();
    }

    public String getName(String email){
        List<Student> students = model.getStudents();
        for(int i=0;i<students.size();i++){
            if(students.get(i).getEmail().equals(email)){
                return students.get(i).getFirstName()+" "+students.get(i).getLastName();
            }
        }
        return null;
    }
}
