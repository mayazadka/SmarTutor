package com.example.smartutor.ui.edit_post;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.smartutor.R;
import com.example.smartutor.model.Post;
import com.example.smartutor.ui.add_post.AddPostViewModel;
import com.example.smartutor.ui.my_feed.MyFeedFragmentDirections;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

public class EditPostFragment extends Fragment {
    // view model
    private EditPostViewModel editPostViewModel;
    private String tutorEmail;
    Long postId;

    // views
    private EditText description;
    private ImageView image;
    private Button saveBtn;
    private Button cancelBtn;
    private Button deleteBtn;

    private ImageButton editImage;

    // images
    static final int REQUEST_IMAGE_CAPTURE = 1;
    Bitmap imageBitmap;
    View view;

    public EditPostFragment(){
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        // view model
        editPostViewModel = new ViewModelProvider(this).get(EditPostViewModel.class);
        tutorEmail = getActivity().getIntent().getStringExtra("EMAIL");
        postId = EditPostFragmentArgs.fromBundle(getArguments()).getIdPost();
        Log.d("TAG", "post id: " + postId + " email: " + tutorEmail);
        editPostViewModel.initial(tutorEmail, postId);

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_edit_post, container, false);

        // views setup
        description = view.findViewById(R.id.editPost_description_etml);
        image = view.findViewById(R.id.editPost_image_img);
        saveBtn = view.findViewById(R.id.editPost_save_btn);
        cancelBtn = view.findViewById(R.id.editPost_cancel_btn);
        deleteBtn = view.findViewById(R.id.editPost_delete_btn);

        editPostViewModel.getPost().observe(getViewLifecycleOwner(), new Observer<Post>() {
            @Override
            public void onChanged(Post post) {
                if(post != null){
                    description.setText(post.getText());
                    image.setImageResource(R.drawable.ic_gender_male);
                    if(post.getPicture() != null && post.getPicture() != ""){
                        Picasso.get().load(post.getPicture()).placeholder(R.drawable.ic_gender_male).error(R.drawable.ic_gender_male).into(image);
                    }
                }
            }
        });

        saveBtn.setOnClickListener(v -> { savePicture(); });

        cancelBtn.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_editPostFragment_to_nav_my_feed_tutor);
        });
        deleteBtn.setOnClickListener(v -> {
            editPostViewModel.deletePost(() -> {Navigation.findNavController(view).navigate(R.id.action_editPostFragment_to_nav_my_feed_tutor);});
        });

        // camera
        editImage = view.findViewById(R.id.editPost_camera_btn);
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

    private void savePicture(){
        // progress bar visible
        saveBtn.setEnabled(false);
        editImage.setEnabled(false);
        cancelBtn.setEnabled(false);
        deleteBtn.setEnabled(false);

        Long postId = editPostViewModel.getPost().getValue().getId();

        if(imageBitmap != null){
            editPostViewModel.uploadImage(imageBitmap, String.valueOf(postId), new AddPostViewModel.UploadImageListener() {
                @Override
                public void onComplete(String url) {
                    savePost(url);
                }
            });
        } else{ savePost(null); }
    }
    private void savePost(String url) {
        Post post = new Post();
        post.setTutorEmail(editPostViewModel.getPost().getValue().getTutorEmail());
        post.setText(description.getText().toString());
        post.setId(editPostViewModel.getPost().getValue().getId());
        if (url == null) {
            post.setPicture(editPostViewModel.getPost().getValue().getPicture());
        }else{
            post.setPicture(url);
        }

        editPostViewModel.updatePost(post.getId(), post, ()->{
            Navigation.findNavController(view).navigate(R.id.action_editPostFragment_to_nav_my_feed_tutor);
        });
    }
}