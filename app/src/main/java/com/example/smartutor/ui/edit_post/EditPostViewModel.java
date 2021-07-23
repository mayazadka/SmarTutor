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

public class EditPostViewModel extends ViewModel {

    private Model model = Model.getInstance();
    private LiveData<Post> post;
    private LiveData<Tutor> tutor;

    public EditPostViewModel() { }
    public void initial(String email, Long id){
        tutor = model.getTutor(email);
        post = model.getPost(id);
        post.observeForever(p->{});
    }

    public LiveData<Post> getPost()                                                                             {return post;}
    public LiveData<Tutor> getTutor()                                                                           {return tutor;}
    public void updatePost(Long postId, Post post, Model.OnCompleteListener listener)                           { model.updatePost(postId, post, listener); }
    public void deletePost(Model.OnCompleteListener listener)                                                   {model.deletePost(post.getValue(), listener);}
    public void uploadImage(Bitmap imageBmp, String name, final AddPostViewModel.UploadImageListener listener)  { Model.getInstance().uploadImage(imageBmp, name, listener); }
}