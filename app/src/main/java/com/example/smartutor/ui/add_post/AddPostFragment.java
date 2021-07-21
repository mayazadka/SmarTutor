package com.example.smartutor.ui.add_post;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.smartutor.R;
import com.example.smartutor.model.Post;


public class AddPostFragment extends Fragment {
    // view model
    private AddPostViewModel addPostViewModel;

    // views
    private EditText description;
    private Button addBtn;
    private ImageView image;

    public AddPostFragment(){
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // view model
        addPostViewModel = new ViewModelProvider(this).get(AddPostViewModel.class);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_post, container, false);

        // views setup
        description = view.findViewById(R.id.addPost_description_etml);
        addBtn = view.findViewById(R.id.addPost_add_btn);
        image = view.findViewById(R.id.addPost_image_img);

        addBtn.setOnClickListener(v -> {
            Post post = new Post(getActivity().getIntent().getStringExtra("EMAIL"), description.getText().toString(), "123");
            addPostViewModel.addPost(post);
            /*Intent intent = new Intent(getActivity(), StudentMenuActivity.class);
            intent.putExtra("EMAIL", email.getText().toString());
            Navigation.findNavController(view).navigate(R.id.action_global_signIn);
            startActivity(intent);*/
        });

        return view;
    }
}