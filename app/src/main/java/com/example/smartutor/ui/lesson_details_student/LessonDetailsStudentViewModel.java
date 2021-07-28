package com.example.smartutor.ui.lesson_details_student;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Lesson;
import com.example.smartutor.model.Model;
import com.example.smartutor.model.Tutor;

import java.time.LocalDateTime;

public class LessonDetailsStudentViewModel extends ViewModel {
    private Model model = Model.getInstance();
    private LiveData<Lesson> lesson;

    public LessonDetailsStudentViewModel() {}
    public void initial(LocalDateTime dateTime){
        lesson = model.getLessonByStudent(getCurrentUserEmail(), dateTime);
        lesson.observeForever(l->{ });
    }

    public void deleteLesson(Model.OnCompleteListener listener) { model.deleteLesson(lesson.getValue(), listener); }
    public LiveData<Tutor> getTutor(String email)               { return model.getTutor(email); }
    public LiveData<Lesson> getLesson()                         { return lesson; }
    public String getCurrentUserEmail()                         { return model.getCurrentUserEmail(); }

}
