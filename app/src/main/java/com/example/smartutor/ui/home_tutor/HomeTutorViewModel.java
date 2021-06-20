package com.example.smartutor.ui.home_tutor;

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
public class HomeTutorViewModel extends ViewModel {
    private Model  model = Model.getInstance();

    public HomeTutorViewModel() {}
    public Tutor getTutor(String email){
        return model.getTutor(email).getTutor();
    }
    public int getTutorLessons(String email){return model.getTutor(email).getLessons().size();}
    public int getRemainLessonsTutor(String email) {return model.getRemainLessonsTutor(email).size();}
    public Lesson getNextLessonTutor(String email){return model.getNextLessonTutor(email);}
    public int getThisWeekLessonsTutor(String email){return model.getThisWeekLessonsTutor(email).size();}
    public Student getStudent(String email){
        return model.getStudent(email).getStudent();
    }
}