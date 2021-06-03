package com.example.smartutor.ui.search_tutors_student;

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

public class SearchTutorsStudentFragment extends Fragment {

    private SearchTutorsStudentViewModel searchTutorsStudentViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        searchTutorsStudentViewModel =
                new ViewModelProvider(this).get(SearchTutorsStudentViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search_tutors_student, container, false);
        final TextView textView = root.findViewById(R.id.searchTutorsStudent_title_tv);
        searchTutorsStudentViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}