package com.example.smartutor.ui.add_post;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Model;
import com.example.smartutor.model.Post;

import java.util.LinkedList;
import java.util.List;

public class AddPostViewModel extends ViewModel {

    private Model model = Model.getInstance();
    MutableLiveData<List<Post>> Posts = new MutableLiveData<List<Post>>(new LinkedList<Post>());

    public AddPostViewModel() { }

    public LiveData<List<Post>> getPosts()                                                      { return model.getPosts(); }
    public void addPost(Post post, Model.OnCompleteListener listener) {
        model.addPost(post, listener);
    }
    public interface UploadImageListener                                                        { void onComplete(String url);}
    public void uploadImage(Bitmap imageBmp, String name, final UploadImageListener listener)   { Model.getInstance().uploadImage(imageBmp, name, listener); }
    public LiveData<Post> getPost(Long postId)                                                  {return model.getPost(postId);}
    public void updatePost(Long postId, Post post, Model.OnCompleteListener listener){
        post.setId(postId);
        model.updatePost(post, listener);
    }
}
