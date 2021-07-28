package com.example.smartutor.ui.student_feed;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.LoadingState;
import com.example.smartutor.model.Model;
import com.example.smartutor.model.Post;
import com.example.smartutor.model.Tutor;

import java.util.List;

public class StudentFeedViewModel extends ViewModel {
    private Model model = Model.getInstance();
    private LiveData<List<Post>> posts;
    private LiveData<List<Tutor>> tutors;

    public StudentFeedViewModel(){
        posts = model.getPosts();
        tutors = model.getTutors();
    }
    public LiveData<List<Post>> getPosts()          {return posts;}
    public LiveData<Tutor> getTutor(String email)   {return model.getTutor(email);}

    public void refresh() {
        Model.getInstance().refreshPosts();
        Model.getInstance().refreshTutors();
    }

    public LiveData<LoadingState> getTutorLoadingState() {return model.tutorLoadingState; }
    public LiveData<LoadingState> getPostLoadingState() {return model.postLoadingState; }
}