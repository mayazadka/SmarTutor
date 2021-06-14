package com.example.smartutor.ui;

import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Model;
import com.example.smartutor.model.Student;

import java.util.List;

public class TutorMenuViewModel extends ViewModel {
    private String email;
    private Model model;

    public TutorMenuViewModel() {
        model = Model.getInstance();
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getName(){
        List<Student> students = model.getStudents();
        for(int i=0;i<students.size();i++){
            if(students.get(i).getEmail().equals(email)){
                return students.get(i).getFirstName()+" "+students.get(i).getLastName();
            }
        }
        return null;
    }

    public String getEmail(){
        return email;
    }


}
