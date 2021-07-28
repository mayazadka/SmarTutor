package com.example.smartutor.ui.sign_in;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Model;
import com.example.smartutor.model.Student;
import com.example.smartutor.model.Tutor;

import java.util.List;

public class SignInViewModel extends ViewModel {

    private Model model = Model.getInstance();
    private LiveData<List<Student>> students;
    private LiveData<List<Tutor>> tutors;

    public SignInViewModel() {
        students = model.getStudents();
        tutors = model.getTutors();

        students.observeForever(ss -> { });
        tutors.observeForever(ts -> { });
    }

    public String getCurrentUserEmail(){
        return model.getCurrentUserEmail();
    }
    public void signIn(String type, String email, String password, Model.OnCompleteListener OnSuccess, Model.OnCompleteListener OnFailure) {
        model.signIn(type, email, password, OnSuccess, OnFailure);
    }
    public void signOut() {model.signOut();}
}