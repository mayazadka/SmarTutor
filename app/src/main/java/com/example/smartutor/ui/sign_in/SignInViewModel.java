package com.example.smartutor.ui.sign_in;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.smartutor.model.Model;
import com.example.smartutor.model.ModelFireBase;
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

        students.observeForever(ss -> {
        });
        tutors.observeForever(ts -> {
        });
    }

    public void checkCurrentUser(Model.OnCompleteListener OnSuccess, Model.OnCompleteListener OnFailure){
        model.checkCurrentUser(OnSuccess, OnFailure);
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
    public void signOut() {model.signOut();}
}