package com.example.smartutor.ui.delete_account_tutor;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Model;
import com.example.smartutor.model.Student;
import com.example.smartutor.model.Tutor;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class DeleteAccountTutorViewModel extends ViewModel {

    private Model model = Model.getInstance();;

    public DeleteAccountTutorViewModel() {}

    public void deleteTutor(String email){model.deleteTutor(model.getTutor(email).getValue());}
}
