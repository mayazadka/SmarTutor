package com.example.smartutor.ui.sign_in;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Model;
import com.example.smartutor.model.Student;
import com.example.smartutor.model.Tutor;

import java.util.LinkedList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class SignInViewModel extends ViewModel {

    private Model model = Model.getInstance();
    private LiveData<List<Student>> students;
    private LiveData<List<Tutor>> tutors;

    public SignInViewModel() {
        students = model.getStudents();
        tutors = model.getTutors();

        students.observeForever(students->{});
        tutors.observeForever(students->{});
    }

    public LiveData<List<Student>> getStudents()    {return students;}
    public LiveData<List<Tutor>> getTutors()        {return tutors;}

    public boolean isExistStudent(String email, String password) throws Exception {
        if(students.getValue() == null){throw new Exception();} //TODO exception
        for (Student student : students.getValue()) {
            if (student.getEmail().equals(email) && student.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
    public boolean isExistTutor(String email, String password) throws Exception {
        if(tutors.getValue() == null){throw new Exception();} //TODO exception
        for (Tutor tutor : tutors.getValue()) {
            if (tutor.getEmail().equals(email) && tutor.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
}
