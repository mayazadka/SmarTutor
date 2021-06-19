package com.example.smartutor.ui.home_student;

import androidx.lifecycle.ViewModel;
import com.example.smartutor.model.Lesson;
import com.example.smartutor.model.Model;
import com.example.smartutor.model.Student;

import java.util.List;

public class HomeStudentViewModel extends ViewModel {

    private List<Student> studentsList;
    //private String email;

    public HomeStudentViewModel() { studentsList = Model.getInstance().getStudents(); }
    public Student getStudent(String email){
        return Model.getInstance().getStudent(email);
    }
    public int getStudentLessons(String email){
        return Model.getInstance().getStudentLessons(email).size();
    }
    public int getRemainLessonsStudent(String email) {
        return Model.getInstance().getRemainLessonsStudent(email).size();
    }
    public Lesson getNextLessonStudent(String email){
        return Model.getInstance().getNextLessonStudent(email);
    }
    public int getThisWeekLessonsStudent(String email){
        return Model.getInstance().getThisWeekLessonsStudent(email).size();
    }
    //public List<Student> getData() { return studentsList; }
}