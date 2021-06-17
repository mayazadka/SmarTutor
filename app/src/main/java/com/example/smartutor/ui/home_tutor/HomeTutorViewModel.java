package com.example.smartutor.ui.home_tutor;

import android.util.Log;

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

public class HomeTutorViewModel extends ViewModel {
    private Model model;
    private List<Student> tutorsList;

    public HomeTutorViewModel() {
        model = Model.getInstance();
    }

    public String getName(String email){
        List<Student> students = model.getStudents();
        for(int i=0;i<students.size();i++){
            if(students.get(i).getEmail().equals(email)){
                return students.get(i).getFirstName()+" "+students.get(i).getLastName();
            }
        }
        return null;
    }
    public int[] getLessonStatistic(String email){
        int thisWeek = 0;
        int remain = 0;
        int total = 0;

        List<Lesson> lessons = model.getLessons();
        for(int i =0;i<lessons.size(); i++){
            Lesson lesson = lessons.get(i);
            if(lesson.getTutor().getEmail().equals(email)){
                total++;
                Date lessonDate = lesson.getDate();
                int lessonWeak = new GregorianCalendar(lessonDate.getYear(), lessonDate.getMonth(), lessonDate.getDate()).get(Calendar.WEEK_OF_YEAR);
                int lessonYear = lessonDate.getYear();
                int currentWeek = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR)-1;
                int currentYear = new GregorianCalendar(lessonDate.getYear(), lessonDate.getMonth(), lessonDate.getDay()).get(Calendar.WEEK_OF_YEAR);
                Log.d("omer", new Integer(lessonWeak).toString()+" "+new Integer(currentWeek).toString());
                if(lessonWeak == currentWeek && lessonYear == currentYear){
                    thisWeek++;
                    int lessonDay = lessonDate.getDay();
                    int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                    if(lessonDay>currentDay){
                        remain++;
                    }
                    else if(currentDay==lessonDay){
                        int lessonHour = lesson.getHour();
                        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                    }
                }

            }
        }
        int[] arr = {thisWeek, remain, total};
        return arr;
    }

    public Tutor getTutor(String email){
        return model.getTutor(email);
    }
    public List<Lesson> getTutorLessons(String email){
        return model.getTutorLessons(email);
    }
}