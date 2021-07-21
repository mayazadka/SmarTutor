package com.example.smartutor.ui.available_tutor;

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

    public AvailableTutorViewModel() {}

    public void addEvent(LocalDateTime when, String email)   {model.addEvent(new Event(email, when));}
}
