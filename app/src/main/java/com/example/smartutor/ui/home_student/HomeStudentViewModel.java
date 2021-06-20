package com.example.smartutor.ui.home_student;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModel;
import com.example.smartutor.model.Lesson;
import com.example.smartutor.model.Model;
import com.example.smartutor.model.Student;
import com.example.smartutor.model.Tutor;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class HomeStudentViewModel extends ViewModel {

    private Model  model = Model.getInstance();

    public HomeStudentViewModel() {}
    public Student getStudent(String email){
        return model.getStudent(email).getStudent();
    }
    public int getStudentLessons(String email){return model.getStudent(email).getLessons().size();}
    public int getRemainLessonsStudent(String email) {return model.getRemainLessonsStudent(email).size();}
    public Lesson getNextLessonStudent(String email){return model.getNextLessonStudent(email);}
    public int getThisWeekLessonsStudent(String email){return model.getThisWeekLessonsStudent(email).size();}
    public Tutor getTutor(String email){
        return model.getTutor(email).getTutor();
    }
}