package com.example.smartutor.ui.student_feed;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Model;
import com.example.smartutor.model.Post;
import com.example.smartutor.model.Tutor;

import java.util.List;

public class StudentFeedViewModel extends ViewModel {
    private Model model = Model.getInstance();
    private LiveData<List<Post>> posts;
    private LiveData<List<Tutor>> tutors;

    public StudentFeedViewModel(){}
    public void initial(){
        posts = model.getPosts();
        tutors = model.getTutors();
    }

    public LiveData<List<Post>> getPosts()          {return posts;}
    public LiveData<Tutor> getTutor(String email)   {return model.getTutor(email);}
}