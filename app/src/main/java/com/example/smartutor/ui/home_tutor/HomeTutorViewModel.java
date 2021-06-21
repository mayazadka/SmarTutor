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
import java.util.LinkedList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class HomeTutorViewModel extends ViewModel {

    private Model model = Model.getInstance();
    private LiveData<Tutor> tutor;
    private LiveData<List<Lesson>> lessons;

    public HomeTutorViewModel() {}
    public void initial(String email){
        tutor = model.getTutor(email);
        lessons = model.getLessonsByTutor(email);
    }

    public LiveData<Tutor> getTutor()                   {return tutor;}
    public LiveData<List<Lesson>> getLessons()          {return lessons;}
    public LiveData<Student> getStudent(String email)   {return model.getStudent(email);}
}