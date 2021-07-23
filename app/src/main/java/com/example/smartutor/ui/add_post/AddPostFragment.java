package com.example.smartutor.ui.add_post;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.smartutor.R;
import com.example.smartutor.model.Model;
import com.example.smartutor.model.Post;

public class AddPostFragment extends Fragment {
    // view model
    private AddPostViewModel addPostViewModel;

    // views
    private EditText description;
    private Button addBtn;
    private ImageView image;
    private ImageButton editImage;

    // images
    static final int REQUEST_IMAGE_CAPTURE = 1;
    Bitmap imageBitmap;
    View view;

    public AddPostFragment(){
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // view model
        addPostViewModel = new ViewModelProvider(this).get(AddPostViewModel.class);

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_post, container, false);

        // views setup
        description = view.findViewById(R.id.addPost_description_etml);
        addBtn = view.findViewById(R.id.addPost_add_btn);
        image = view.findViewById(R.id.addPost_image_img);

        addBtn.setOnClickListener(v -> {
            savePost();
        });

        // camera
        editImage = view.findViewById(R.id.addPost_camera_btn);
        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });
        return view;
    }

    void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

        /*if(takePictureIntent.resolveActivity(requireActivity().getPackageManager()) != null){
        }*/
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_IMAGE_CAPTURE){
            if(resultCode == Activity.RESULT_OK){
                Bundle extras = data.getExtras();
                imageBitmap = (Bitmap) extras.get("data");
                image.setImageBitmap(imageBitmap);
            }
        }
    }
    private void savePicture(Long postId){
        LiveData<Post> post = addPostViewModel.getPost(postId);

        if(imageBitmap != null){
            addPostViewModel.uploadImage(imageBitmap, String.valueOf(postId), new AddPostViewModel.UploadImageListener() {
                @Override
                public void onComplete(String url) {
                    addPostViewModel.updatePost(postId, new Post(post.getValue().getTutorEmail(), post.getValue().getText(), url), ()->{
                        Navigation.findNavController(view).navigate(R.id.action_addPostFragment_to_nav_my_feed_tutor);
                    });
                }
            });
        } else{
            Navigation.findNavController(view).navigate(R.id.action_addPostFragment_to_nav_my_feed_tutor);
        }

    }
    private void savePost(){
        // progress bar visible
        addBtn.setEnabled(false);
        editImage.setEnabled(false);
        Post post = new Post();

        post.setTutorEmail(getActivity().getIntent().getStringExtra("EMAIL"));
        post.setText(description.getText().toString());
        post.setPicture("");

        addPostViewModel.addPost(post, ()->{
            savePicture(post.getId());
        });
    }
}