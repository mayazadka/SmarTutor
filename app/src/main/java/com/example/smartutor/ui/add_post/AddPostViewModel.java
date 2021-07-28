package com.example.smartutor.ui.add_post;

import android.graphics.Bitmap;
import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Model;
import com.example.smartutor.model.Post;

public class AddPostViewModel extends ViewModel {

    private Model model = Model.getInstance();

    public AddPostViewModel() { }

    public void addPost(Post post, Bitmap ImageBit,  Model.OnCompleteListener listener)     { model.addPost(post, ImageBit, listener); }
    public String getCurrentUserEmail()                                                     { return model.getCurrentUserEmail(); }
}
