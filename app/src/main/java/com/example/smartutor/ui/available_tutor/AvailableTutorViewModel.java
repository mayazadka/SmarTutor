package com.example.smartutor.ui.available_tutor;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Event;
import com.example.smartutor.model.Lesson;
import com.example.smartutor.model.Model;
import com.example.smartutor.model.Student;
import com.example.smartutor.model.Tutor;

import java.time.LocalDateTime;
import java.util.List;

public class AvailableTutorViewModel extends ViewModel {
    private Model model = Model.getInstance();
    private LiveData<Event> event;

    public AvailableTutorViewModel() {}
    public void initial(String email, LocalDateTime dateTime){
        event = model.getEvent(email, dateTime);
        event.observeForever(e-> {});
    }
    public void addEvent(LocalDateTime when, String email, Model.OnCompleteListener listener)   {model.addEvent(new Event(email, when), listener);}
    public void deleteEvent(Model.OnCompleteListener listener){model.deleteEvent(event.getValue(), listener);}

}
