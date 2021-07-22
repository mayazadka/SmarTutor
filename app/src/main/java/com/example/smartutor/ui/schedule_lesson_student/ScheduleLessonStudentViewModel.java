package com.example.smartutor.ui.schedule_lesson_student;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Event;
import com.example.smartutor.model.Lesson;
import com.example.smartutor.model.Model;
import com.example.smartutor.model.Profession;
import com.example.smartutor.model.Tutor;

import java.time.LocalDateTime;

public class ScheduleLessonStudentViewModel extends ViewModel {
    private Model model = Model.getInstance();
    private LiveData<Tutor> tutor;

    public ScheduleLessonStudentViewModel() {}
    public void initial(String email){tutor = model.getTutor(email);}

    public void addLesson(LocalDateTime when, String tutorEmail, String studentEmail, Profession subject)   {model.addLesson(new Lesson(studentEmail, tutorEmail, when, subject));}
    public LiveData<Tutor> getTutor(){return tutor;}
}
