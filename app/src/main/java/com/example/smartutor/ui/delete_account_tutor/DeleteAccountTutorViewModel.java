package com.example.smartutor.ui.delete_account_tutor;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Model;
import com.example.smartutor.model.Tutor;

@RequiresApi(api = Build.VERSION_CODES.O)
public class DeleteAccountTutorViewModel extends ViewModel {

    private Model model = Model.getInstance();;
    private LiveData<Tutor> tutor;

    public DeleteAccountTutorViewModel() {}
    public void initial(){
        tutor = model.getTutor(getCurrentUserEmail());
        tutor.observeForever(tutor -> {});
    }

    public void deleteTutor(Model.OnCompleteListener listener)  { model.deleteTutor(tutor.getValue(), listener); }
    public String getCurrentUserEmail()                         { return model.getCurrentUserEmail(); }
}
