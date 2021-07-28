package com.example.smartutor.ui.edit_post;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.example.smartutor.model.LoadingState;
import com.example.smartutor.model.Model;
import com.example.smartutor.model.Post;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDateTime;

public class EditPostFragment extends Fragment {
    // view model
    private EditPostViewModel editPostViewModel;
    private String tutorEmail;
    private String postId;

    // views
    private EditText description;
    private ImageView image;
    private Button saveBtn;
    private Button cancelBtn;
    private Button deleteBtn;
    private SwipeRefreshLayout swipeUp;

    private ImageButton editImage;
    private ImageButton galleryBtn;

    // images
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_LOAD_IMG = 2;

    private Bitmap imageBitmap;
    private View view;

    public EditPostFragment(){
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        // view model
        editPostViewModel = new ViewModelProvider(this).get(EditPostViewModel.class);
        tutorEmail = editPostViewModel.getCurrentUserEmail();
        postId = EditPostFragmentArgs.fromBundle(getArguments()).getIdPost();
        editPostViewModel.initial(postId);

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_edit_post, container, false);

        // views setup
        description = view.findViewById(R.id.editPost_description_etml);
        image = view.findViewById(R.id.editPost_image_img);
        saveBtn = view.findViewById(R.id.editPost_save_btn);
        cancelBtn = view.findViewById(R.id.editPost_cancel_btn);
        deleteBtn = view.findViewById(R.id.editPost_delete_btn);
        editImage = view.findViewById(R.id.editPost_camera_btn);
        galleryBtn = view.findViewById(R.id.editPost_gallery_btn);
        swipeUp = view.findViewById(R.id.editPost_swipeUp);

        editPostViewModel.getPost().observe(getViewLifecycleOwner(), post -> {
            if(post != null){
                description.setText(post.getText());
                image.setImageResource(R.drawable.ic_baseline_hourglass_empty_24);
                if(post.getPicture() != null && post.getPicture() != ""){
                    Picasso.get().load(post.getPicture()).placeholder(R.drawable.ic_baseline_hourglass_empty_24).error(R.drawable.ic_baseline_report_problem_24).into(image);
                }
            }
        });

        saveBtn.setOnClickListener(v -> {
            v.setEnabled(false);
            enableButtons(false);

            if(description.getText().toString().trim().equals("")){
                Snackbar.make(saveBtn, "you must enter description", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                enableButtons(true);
            }
            else if(imageBitmap==null){
                editPostViewModel.updatePost(postId, description.getText().toString(), ()->{
                    Navigation.findNavController(view).navigateUp();
                });
            }
            else{
                Post post = new Post(tutorEmail, description.getText().toString(), "", LocalDateTime.now());
                post.setId(postId);
                editPostViewModel.updatePost(post, imageBitmap,()->Navigation.findNavController(view).navigateUp());
            }
        });

        cancelBtn.setOnClickListener(v -> {
            v.setEnabled(false);
            enableButtons(false);
            Navigation.findNavController(view).navigateUp();
        });

        deleteBtn.setOnClickListener(v -> {
            v.setEnabled(false);
            enableButtons(false);
            editPostViewModel.deletePost(() -> Navigation.findNavController(view).navigateUp());
        });
        editImage.setOnClickListener(v -> takePicture());
        galleryBtn.setOnClickListener(v -> loadPictureFromGallery());

        swipeUp.setOnRefreshListener(()-> editPostViewModel.refresh());
        editPostViewModel.getPostLoadingState().observeForever(state->{
            boolean b = (state == LoadingState.loaded);
            enableButtons(b);
            swipeUp.setRefreshing(!b);
        });
        return view;
    }

    private void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }
    private void loadPictureFromGallery(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, REQUEST_LOAD_IMG);
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
        else if(requestCode == REQUEST_LOAD_IMG){
            if (resultCode == Activity.RESULT_OK) {
                try {
                    Uri imageUri = data.getData();
                    InputStream imageStream = getContext().getContentResolver().openInputStream(imageUri);
                    imageBitmap = BitmapFactory.decodeStream(imageStream);
                    image.setImageBitmap(imageBitmap);
                } catch (FileNotFoundException e) {
                }
            }else {
            }
        }
    }

    private void enableButtons(boolean state){
        saveBtn.setEnabled(state);
        cancelBtn.setEnabled(state);
        deleteBtn.setEnabled(state);
        galleryBtn.setEnabled(state);
        editImage.setEnabled(state);
    }
}