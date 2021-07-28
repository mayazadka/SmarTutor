package com.example.smartutor.ui.add_post;

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
import com.google.android.material.snackbar.Snackbar;

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
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_LOAD_IMG = 2;
    private Bitmap imageBitmap;
    private View view;

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
            v.setEnabled(false);
            enableButtons(false);

            if(description.getText().toString().trim().equals("")){
                Snackbar.make(addBtn, "you must enter description", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                enableButtons(true);
            }
            else if(imageBitmap==null){
                Snackbar.make(addBtn, "you must choose photo", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                enableButtons(true);
            }
            else {
                Post post = new Post(getActivity().getIntent().getStringExtra("EMAIL"), description.getText().toString(), "");
                addPostViewModel.addPost(post, imageBitmap, () -> Navigation.findNavController(view).navigateUp());
            }
        });

        editImage.setOnClickListener(v -> takePicture());
        galleryBtn.setOnClickListener(v -> loadPictureFromGallery());
        return view;
    }

    void enableButtons(boolean state){
        addBtn.setEnabled(state);
        editImage.setEnabled(state);
        galleryBtn.setEnabled(state);
    }
    void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
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
                    Log.d("omer", "Something went wrong");
                }
            }else {
                Log.d("omer", "You haven't picked Image");
            }
        }
    }
}