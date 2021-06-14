package com.example.smartutor.ui.home_tutor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Model;
import com.example.smartutor.model.Student;

import java.util.List;

public class HomeTutorViewModel extends ViewModel {
    private String email;
    private Model model;

    public HomeTutorViewModel() {
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


}