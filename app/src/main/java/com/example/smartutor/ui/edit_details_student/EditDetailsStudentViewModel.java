package com.example.smartutor.ui.edit_details_student;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Lesson;
import com.example.smartutor.model.Model;
import com.example.smartutor.model.Student;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class EditDetailsStudentViewModel extends ViewModel {

    private Model model = Model.getInstance();
    private LiveData<Student> student;

    public EditDetailsStudentViewModel() { }
    public void initial() {
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