package com.example.smartutor.ui.home_student;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.smartutor.R;

public class HomeStudentFragment extends Fragment {

    private HomeStudentViewModel homeStudentViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        homeStudentViewModel =
                new ViewModelProvider(this).get(HomeStudentViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home_student, container, false);
        final TextView textView = root.findViewById(R.id.homeStudent_title_tv);
        homeStudentViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}