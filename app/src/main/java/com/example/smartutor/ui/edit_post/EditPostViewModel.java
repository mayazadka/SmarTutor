package com.example.smartutor.ui.edit_post;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Lesson;
import com.example.smartutor.model.Model;
import com.example.smartutor.model.Post;
import com.example.smartutor.model.Tutor;
import com.example.smartutor.ui.add_post.AddPostViewModel;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class EditPostViewModel extends ViewModel {

    private Model model = Model.getInstance();
    private LiveData<Post> post;

    public EditPostViewModel() { }
    public void initial(String id){
        post = model.getPost(id);
        post.observeForever(p->{});
    }

    public LiveData<Post> getPost()                                                     { return post; }
    public void updatePost(Post post, Bitmap bitmap, Model.OnCompleteListener listener) { model.updatePost(post, bitmap, listener); }
    public void deletePost(Model.OnCompleteListener listener)                           { model.deletePost(post.getValue(), listener); }
    public String getCurrentUserEmail()                                                 { return model.getCurrentUserEmail(); }
}