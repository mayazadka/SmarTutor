package com.example.smartutor.ui.lesson_details_tutor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Event;
import com.example.smartutor.model.Lesson;
import com.example.smartutor.model.Model;
import com.example.smartutor.model.Student;

import java.time.LocalDateTime;

public class LessonDetailsTutorViewModel extends ViewModel {
    private Model model = Model.getInstance();
    private LiveData<Lesson> lesson;
    public LessonDetailsTutorViewModel() {}
    public void initial(String email, LocalDateTime dateTime){
        lesson = model.getLessonByTutor(email, dateTime);
        lesson.observeForever(l->{ });
    }

    public void deleteLesson(Model.OnCompleteListener listener)   {model.deleteLesson(lesson.getValue(), listener);}
    public LiveData<Student> getStudent(String email) {return model.getStudent(email);}
    public LiveData<Lesson> getLesson() {return lesson;}


}
