package com.example.smartutor.ui.delete_account_student;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Model;
import com.example.smartutor.model.Student;


public class DeleteAccountStudentViewModel extends ViewModel {

    private Model model = Model.getInstance();;
    private LiveData<Student> student;

    public DeleteAccountStudentViewModel() {
        student = model.getStudent(getCurrentUserEmail());
        student.observeForever(student -> {});
    }

    public void deleteStudent(Model.OnCompleteListener listener)    { model.deleteStudent(student.getValue(), listener); }
    public String getCurrentUserEmail()                             { return model.getCurrentUserEmail(); }
}
