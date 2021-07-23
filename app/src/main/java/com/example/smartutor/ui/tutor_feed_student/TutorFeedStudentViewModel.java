package com.example.smartutor.ui.tutor_feed_student;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Model;
import com.example.smartutor.model.Post;
import com.example.smartutor.model.Tutor;

import java.util.List;

public class TutorFeedStudentViewModel extends ViewModel {
    private Model model = Model.getInstance();
    private LiveData<Tutor> tutor;
    private LiveData<List<Post>> posts;

    public TutorFeedStudentViewModel() {}
    public void initial(String email){
        tutor = model.getTutor(email);
        posts = model.getPosts();
    }

    public LiveData<Tutor> getTutor()       { return tutor; }
    public LiveData<List<Post>> getPosts()  { return posts; }
}
