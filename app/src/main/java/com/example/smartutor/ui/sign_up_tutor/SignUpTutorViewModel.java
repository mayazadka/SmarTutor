package com.example.smartutor.ui.sign_up_tutor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Model;
import com.example.smartutor.model.Student;
import com.example.smartutor.model.Tutor;

import java.util.List;

public class SignUpTutorViewModel  extends ViewModel {

    private Model model = Model.getInstance(); ;

    private LiveData<List<Tutor>> tutors;
    private LiveData<List<Student>> students;

    public SignUpTutorViewModel(){
        tutors = model.getTutors();
        students = model.getStudents();

        tutors.observeForever(ts-> {});
        students.observeForever(ss-> {});
    }

    public void addTutor(Tutor tutor, String password, Model.OnCompleteListener OnSuccess, Model.OnCompleteListener OnFailure)   {
        createUserAccount("tutor", tutor.getEmail(), password, () -> {
            model.addTutor(tutor, OnSuccess);
        }, OnFailure);
    }

    public void createUserAccount(String type, String email, String password, Model.OnCompleteListener OnSuccess, Model.OnCompleteListener OnFailure) {
        model.createUserAccount(type, email, password, OnSuccess, OnFailure);
    }
    public String getCurrentUserEmail() { return model.getCurrentUserEmail(); }
}
