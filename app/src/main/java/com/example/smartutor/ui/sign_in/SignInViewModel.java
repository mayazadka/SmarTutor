package com.example.smartutor.ui.sign_in;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Model;

@RequiresApi(api = Build.VERSION_CODES.O)
public class SignInViewModel extends ViewModel {
    private Model model = Model.getInstance();

    public SignInViewModel() {}

    public boolean checkDetailsStudent(String email, String password){ return model.checkDetailsStudent(email, password);}
    public boolean checkDetailsTutor(String email, String password){return model.checkDetailsTutor(email, password);}
}
