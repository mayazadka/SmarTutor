package com.example.smartutor.ui.search_tutors_student;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Lesson;
import com.example.smartutor.model.Model;
import com.example.smartutor.model.Student;
import com.example.smartutor.model.Tutor;

import java.util.List;

public class SearchTutorsStudentViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private List<Tutor> tutorsList;


    public SearchTutorsStudentViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is search tutor fragment");
        tutorsList = Model.getInstance().getTutors();
    }

    public LiveData<String> getText() {return mText;}
    public List<Tutor> getTutors(){
        return Model.getInstance().getTutors();
    }
    public List<Tutor> getTutorsByName(String name){
        return Model.getInstance().getTutorsByName(name);
    }
    public List<Tutor> getTutorsBySubject(String subject){
        return Model.getInstance().getTutorsBySubject(subject);
    }
    public List<Tutor> getData() { return tutorsList; }
}