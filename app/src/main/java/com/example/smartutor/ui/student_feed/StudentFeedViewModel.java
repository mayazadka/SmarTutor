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
    // TODO: change this function
    public Tutor getTutor(String email)   {
        for(int i = 0; i < tutors.getValue().size(); i++) {
            if (tutors.getValue().get(i).getEmail().compareTo(email) == 0)
                return tutors.getValue().get(i);
        }
        return null;
    }
}