package com.example.smartutor.ui.delete_account_student;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Model;
import com.example.smartutor.model.Student;


@RequiresApi(api = Build.VERSION_CODES.O)
public class DeleteAccountStudentViewModel extends ViewModel {

    private Model model = Model.getInstance();;
    private LiveData<Student> student;

    public DeleteAccountStudentViewModel() {}
    public void initial(){
        student = model.getStudent(getCurrentUserEmail());
        student.observeForever(student -> {});
    }

    public void deleteStudent(Model.OnCompleteListener listener)    { model.deleteStudent(student.getValue(), listener); }
    public String getCurrentUserEmail()                             { return model.getCurrentUserEmail(); }
}
