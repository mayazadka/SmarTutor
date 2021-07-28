package com.example.smartutor.ui.edit_details_student;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Model;
import com.example.smartutor.model.Student;


public class EditDetailsStudentViewModel extends ViewModel {

    private Model model = Model.getInstance();
    private LiveData<Student> student;

    public EditDetailsStudentViewModel() {
        student = model.getStudent(getCurrentUserEmail());
    }

    public LiveData<Student> getStudent()   { return student; }
    public String getCurrentUserEmail()     { return model.getCurrentUserEmail(); }
    public void updateStudent(Student student, Model.OnCompleteListener listener) {
        if(this.student.getValue() != null){
            student.setEmail(this.student.getValue().getEmail());
            model.updateStudent(student, listener);
        }
    }

}