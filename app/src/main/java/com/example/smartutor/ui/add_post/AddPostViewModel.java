package com.example.smartutor.ui.add_post;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartutor.model.Model;
import com.example.smartutor.model.Post;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class AddPostViewModel extends ViewModel {

    private Model model = Model.getInstance();

    public AddPostViewModel() { }

    public void addPost(Post post, Bitmap ImageBit,  Model.OnCompleteListener listener) {model.addPost(post, ImageBit, listener);}
}
