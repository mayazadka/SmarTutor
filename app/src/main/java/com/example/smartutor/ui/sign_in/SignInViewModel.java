package com.example.smartutor.ui.sign_in;

import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Model;

public class SignInViewModel extends ViewModel {
    private Model model;

    public SignInViewModel() {
        model = Model.getInstance();
    }

    public boolean isCorrectStudentDetails(String email, String password){
        for(int i=0;i<model.getStudents().size();i++){
            if(model.getStudents().get(i).getEmail().equals(email)){
                return model.getStudents().get(i).getPassword().equals(password);
            }
        }
        return false;
    }

    public boolean isCorrectTutorDetails(String email, String password){
        for(int i=0;i<model.getTutors().size();i++){
            if(model.getTutors().get(i).getEmail().equals(email)){
                return model.getTutors().get(i).getPassword().equals(password);
            }
        }
        return false;
    }
}
