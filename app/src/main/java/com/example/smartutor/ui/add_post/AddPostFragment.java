package com.example.smartutor.ui.add_post;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
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

import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddPostFragment extends Fragment {
    // view model
    private AddPostViewModel addPostViewModel;

    // views
    private EditText description;
    private Button addBtn;
    private ImageView image;

    private ImageButton editImage;
    private ImageButton galleryBtn;

    // images
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_LOAD_IMG = 2;
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
        editImage = view.findViewById(R.id.addPost_camera_btn);
        galleryBtn = view.findViewById(R.id.addPost_gallery_btn);

        addBtn.setOnClickListener(v -> {
            savePost();
        });
        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });
        galleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { loadPictureFromGallery(); }
        });
        return view;
    }

    void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

        /*if(takePictureIntent.resolveActivity(requireActivity().getPackageManager()) != null){
        }*/
    }
    void loadPictureFromGallery(){
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
                    // TODO: display on the view
                    Log.d("TAG", "Something went wrong");
                }
            }else {
                Log.d("TAG", "You haven't picked Image");
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