package com.example.smartutor.ui.edit_details_tutor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Model;
import com.example.smartutor.model.Tutor;

public class EditDetailsTutorViewModel extends ViewModel {

    private Model model = Model.getInstance();
    private LiveData<Tutor> tutor;

    public EditDetailsTutorViewModel() {
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