package com.example.smartutor.ui.home_student;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.smartutor.model.Lesson;
import com.example.smartutor.model.Model;
import com.example.smartutor.model.Student;
import com.example.smartutor.model.StudentWithLessons;
import com.example.smartutor.model.Tutor;
import com.example.smartutor.model.TutorWithLessons;

import java.util.LinkedList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class HomeStudentViewModel extends ViewModel {

    private Model model = Model.getInstance();
    private LiveData<Student> student;
    private LiveData<List<Lesson>> lessons;

    public HomeStudentViewModel() {}
    public void initial(String email){
        student = model.getStudent(email);
        lessons = model.getLessonsByStudent(email);
    }

    public LiveData<Student> getStudent()               {return student;}
    public LiveData<List<Lesson>> getLessons()          {return lessons;}
    public LiveData<Tutor> getTutor(String email)       {return model.getTutor(email);}
}