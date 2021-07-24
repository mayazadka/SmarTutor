package com.example.smartutor.ui.sign_up_student;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Model;
import com.example.smartutor.model.Student;
import com.example.smartutor.model.Tutor;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
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

    public void addStudent(Student student, String password, Model.OnCompleteListener listener)     {
        createUserAccount(student.getEmail(), password);
        model.addStudent(student, listener);
    }

    public boolean isExistStudent(String email) throws Exception {
        if(students.getValue() == null){return false;}
        for (Student student : students.getValue()) {
            if (student.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public boolean isExistTutor(String email) throws Exception {
        if(tutors.getValue() == null){return false;}
        for (Tutor tutor : tutors.getValue()) {
            if (tutor.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public boolean createUserAccount(String email, String password) {
        return model.createUserAccount(email, password);
    }
}
