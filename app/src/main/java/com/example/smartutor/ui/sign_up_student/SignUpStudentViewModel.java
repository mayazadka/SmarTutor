package com.example.smartutor.ui.sign_up_student;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Model;
import com.example.smartutor.model.Student;
import com.example.smartutor.model.Tutor;

import java.util.List;

public class SignUpStudentViewModel  extends ViewModel {

    private Model model = Model.getInstance();

    private LiveData<List<Tutor>> tutors;
    private LiveData<List<Student>> students;

    public SignUpStudentViewModel(){
        tutors = model.getTutors();
        students = model.getStudents();

        tutors.observeForever(ts-> {});
        students.observeForever(ss-> {});
    }

    public void addStudent(Student student, String password, Model.OnCompleteListener OnSuccess, Model.OnCompleteListener OnFailure)     {
        createUserAccount("student", student.getEmail(), password, () -> {
            model.addStudent(student, OnSuccess);
        }, OnFailure);
    }

    public void createUserAccount(String type, String email, String password, Model.OnCompleteListener OnSuccess, Model.OnCompleteListener OnFailure) {
        model.createUserAccount(type, email, password, OnSuccess, OnFailure);
    }
    public String getCurrentUserEmail(){
        return model.getCurrentUserEmail();
    }
    public void signIn(String type, String email, String password, Model.OnCompleteListener OnSuccess, Model.OnCompleteListener OnFailure) {
        model.signIn(type, email, password, OnSuccess, OnFailure);
    }
}
