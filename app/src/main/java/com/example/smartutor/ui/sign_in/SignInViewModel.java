package com.example.smartutor.ui.sign_in;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Model;
import com.example.smartutor.model.Student;
import com.example.smartutor.model.Tutor;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class SignInViewModel extends ViewModel {

    private Model model = Model.getInstance();
    private LiveData<List<Student>> students;
    private LiveData<List<Tutor>> tutors;

    public SignInViewModel() {
        students = model.getStudents();
        tutors = model.getTutors();

        students.observeForever(ss->{});
        tutors.observeForever(ts->{});
    }

    public void isExistStudent(String email, String password, Model.OnCompleteSignInListener listener) throws Exception {
        int flag = 0;
        if(students.getValue() == null){throw new Exception("try again");}
        for (Student student : students.getValue()) {
            if (student.getEmail().equals(email)) {
                flag = 1;
                signIn(email, password, listener);
                break;
            }
        }
        if(flag == 0)
            listener.onComplete(false);
    }
    public void isExistTutor(String email, String password, Model.OnCompleteSignInListener listener) throws Exception {
        int flag = 0;
        if(tutors.getValue() == null){throw new Exception("try again");}
        for (Tutor tutor : tutors.getValue()) {
            if (tutor.getEmail().equals(email)) {
                flag = 1;
                signIn(email, password, listener);
                break;
            }
        }
        if(flag == 0)
            listener.onComplete(false);
    }

    public boolean checkCurrentUser(){
        return model.checkCurrentUser();
    }
    public void signIn(String email, String password, Model.OnCompleteSignInListener listener) {
        model.signIn(email, password, listener);
    }
    public boolean sendEmailVerification() {
        return model.sendEmailVerification();
    }
}
