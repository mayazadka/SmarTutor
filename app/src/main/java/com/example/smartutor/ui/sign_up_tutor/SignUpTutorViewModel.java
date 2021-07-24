package com.example.smartutor.ui.sign_up_tutor;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Model;
import com.example.smartutor.model.Student;
import com.example.smartutor.model.Tutor;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
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

    public void addTutor(Tutor tutor, String password, Model.OnCompleteListener listener)   {
        createUserAccount(tutor.getEmail(), password);
        model.addTutor(tutor, listener);
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
    public boolean isExistStudent(String email) throws Exception {
        if(students.getValue() == null){return false;}
        for (Student student : students.getValue()) {
            if (student.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }
    public boolean createUserAccount(String email, String password) {
        return model.createUserAccount(email, password);
    }
}
