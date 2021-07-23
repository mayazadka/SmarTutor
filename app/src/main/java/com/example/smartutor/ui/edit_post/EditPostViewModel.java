package com.example.smartutor.ui.edit_post;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Model;
import com.example.smartutor.model.Post;
import com.example.smartutor.model.Tutor;

import java.util.List;

public class EditPostViewModel extends ViewModel {

    private Model model = Model.getInstance();
    private LiveData<List<Post>> posts;
    private LiveData<Tutor> tutor;

    public EditPostViewModel() { }
    public void initial(String email){
        tutor = model.getTutor(email);
        posts = model.getPostsByTutor(email);
    }

    public LiveData<List<Post>> getPosts()      {return posts;}
    public LiveData<Tutor> getTutor()           {return tutor;}
    public void updatePost(Post post) {
        Log.d("TAG", "description in update post: " + post.getText());
        model.updatePost(post);
    }
    public Post getPostByPosition(int position) { return posts.getValue().get(position); }
}