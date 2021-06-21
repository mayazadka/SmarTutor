package com.example.smartutor.ui.search_tutors_student;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Lesson;
import com.example.smartutor.model.Model;
import com.example.smartutor.model.Profession;
import com.example.smartutor.model.Student;
import com.example.smartutor.model.Tutor;

import java.util.LinkedList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class SearchTutorsStudentViewModel extends ViewModel {

    private Model model = Model.getInstance();
    private LiveData<List<Tutor>> tutors;

    public SearchTutorsStudentViewModel() {
        tutors = model.getTutors();
    }

    public LiveData<List<Tutor>> getTutors()    {return tutors;}
//    public List<Tutor> getTutorsByName(String name){
//        List<Tutor> filter = new LinkedList<>();
//        for(Tutor t : tutors){
//            if((t.getFirstName()+" "+t.getLastName()).contains(name) || (t.getLastName()+" "+t.getFirstName()).contains(name)){
//                filter.add(t);
//            }
//        }
//        return filter;
//    }
//    public List<Tutor> getTutorsByProfessions(List<Profession> professions){
//        List<Tutor> filter = new LinkedList<>();
//        for(Tutor t : tutors){
//            if(t.getProfessions().containsAll(professions)){
//                filter.add(t);
//            }
//        }
//        return filter;
//    }
}