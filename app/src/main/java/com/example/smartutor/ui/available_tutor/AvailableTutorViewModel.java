package com.example.smartutor.ui.available_tutor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Event;
import com.example.smartutor.model.Model;

import java.time.LocalDateTime;


public class AvailableTutorViewModel extends ViewModel {
    private Model model = Model.getInstance();
    private LiveData<Event> event;

    public AvailableTutorViewModel() {}
    public void initial(LocalDateTime dateTime){
        event = model.getEvent(getCurrentUserEmail(), dateTime);
        event.observeForever(e-> {});
    }
    public void addEvent(LocalDateTime when, Model.OnCompleteListener listener)     { model.addEvent(new Event(getCurrentUserEmail(), when), listener); }
    public void deleteEvent(Model.OnCompleteListener listener)                      { model.deleteEvent(event.getValue(), listener); }
    public String getCurrentUserEmail()                                             { return model.getCurrentUserEmail(); }
}
