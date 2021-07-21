package com.example.smartutor.ui.add_post;

import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Model;
import com.example.smartutor.model.Post;

public class AddPostViewModel extends ViewModel {

    private Model model = Model.getInstance();

    public AddPostViewModel() { }
    public void addPost(Post post)     {model.addPost(post);}
}
