package com.example.smartutor.ui.edit_details_student;

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

public class EditDetailsStudentFragment extends Fragment {

    private EditDetailsStudentViewModel editDetailsStudentViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        editDetailsStudentViewModel =
                new ViewModelProvider(this).get(EditDetailsStudentViewModel.class);
        View root = inflater.inflate(R.layout.fragment_edit_details_student, container, false);
        final TextView textView = root.findViewById(R.id.editDetailsStudent_title_tv);
        editDetailsStudentViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}