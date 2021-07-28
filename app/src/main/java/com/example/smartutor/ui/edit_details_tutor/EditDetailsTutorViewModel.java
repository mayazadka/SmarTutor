package com.example.smartutor.ui.edit_details_tutor;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Lesson;
import com.example.smartutor.model.Model;
import com.example.smartutor.model.Student;
import com.example.smartutor.model.Tutor;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class EditDetailsTutorViewModel extends ViewModel {

    private Model model = Model.getInstance();
    private LiveData<Tutor> tutor;

    public EditDetailsTutorViewModel() { }
    public void initial(){
        tutor = model.getTutor(getCurrentUserEmail());
    }

    public LiveData<Tutor> getTutor()   { return tutor; }
    public String getCurrentUserEmail() { return model.getCurrentUserEmail(); }
    public void updateTutor(Tutor tutor, Model.OnCompleteListener listener){
        if(this.tutor.getValue()!=null){
            tutor.setEmail(this.tutor.getValue().getEmail());
            model.updateTutor(tutor, listener);
        }
    }
}