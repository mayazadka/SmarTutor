package com.example.smartutor.ui.my_feed;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Model;
import com.example.smartutor.model.Post;
import com.example.smartutor.model.Tutor;

import java.util.List;

public class MyFeedViewModel extends ViewModel {
    private Model model = Model.getInstance();
    private LiveData<List<Post>> posts;
    private LiveData<Tutor> tutor;

    public MyFeedViewModel() {
        tutor = model.getTutor(getCurrentUserEmail());
        posts = model.getPostsByTutor(getCurrentUserEmail());
    }

    public LiveData<List<Post>> getPostsByTutor()   { return posts; }
    public LiveData<Tutor> getTutor()               { return tutor; }
    public String getCurrentUserEmail()             { return model.getCurrentUserEmail(); }
}
