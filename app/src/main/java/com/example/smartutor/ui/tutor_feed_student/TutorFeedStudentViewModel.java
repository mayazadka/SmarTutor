package com.example.smartutor.ui.tutor_feed_student;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.LoadingState;
import com.example.smartutor.model.Model;
import com.example.smartutor.model.Post;
import com.example.smartutor.model.Tutor;

import java.util.List;

public class TutorFeedStudentViewModel extends ViewModel {
    private Model model = Model.getInstance();
    private LiveData<Tutor> tutor;

    public TutorFeedStudentViewModel() {}
    public void initial(String email){
        tutor = model.getTutor(email);
    }

    public LiveData<Tutor> getTutor()                           { return tutor; }
    public LiveData<List<Post>> getPostsByTutor(String email)   { return model.getPostsByTutor(email); }

    public void refresh() {
        Model.getInstance().refreshPosts();
        Model.getInstance().refreshTutors();
    }

    public LiveData<LoadingState> getTutorLoadingState() {return model.tutorLoadingState; }
    public LiveData<LoadingState> getPostLoadingState() {return model.postLoadingState; }
}
