package com.example.smartutor.ui.sign_in;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Lesson;
import com.example.smartutor.model.Model;
import com.example.smartutor.model.Profession;
import com.example.smartutor.model.Student;
import com.example.smartutor.model.Tutor;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class SignInViewModel extends ViewModel {

    private Model model = Model.getInstance();
    private LiveData<List<Student>> students;
    private LiveData<List<Tutor>> tutors;

    public SignInViewModel() {
        students = model.getStudents();
        tutors = model.getTutors();

        students.observeForever(ss->{});
        tutors.observeForever(ts->{});

        //model.deleteAllTutors();
        //model.deleteAllStudents();
        //model.deleteAllLessons();

        model.addLesson(new Lesson("student@gmail.com", "tutor@gmail.com", LocalDateTime.of(2021, 6, 20, 1, 0, 0), Profession.COMPUTER_SCIENCE));
        model.addLesson(new Lesson("student@gmail.com", "tutor@gmail.com", LocalDateTime.of(2021, 6, 21, 1, 0, 0), Profession.COMPUTER_SCIENCE));
        model.addLesson(new Lesson("student@gmail.com", "tutor@gmail.com", LocalDateTime.of(2021, 6, 23, 1, 0, 0), Profession.COMPUTER_SCIENCE));
        model.addLesson(new Lesson("student@gmail.com", "tutor@gmail.com", LocalDateTime.of(2021, 6, 24, 1, 0, 0), Profession.COMPUTER_SCIENCE));
    }

    public boolean isExistStudent(String email, String password) throws Exception {
        if(students.getValue() == null){throw new Exception("try again");}
        for (Student student : students.getValue()) {
            if (student.getEmail().equals(email) && student.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
    public boolean isExistTutor(String email, String password) throws Exception {
        if(tutors.getValue() == null){throw new Exception("try again");}
        for (Tutor tutor : tutors.getValue()) {
            if (tutor.getEmail().equals(email) && tutor.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
}
