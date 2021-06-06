package com.example.smartutor.ui.delete_account_student;

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

public class DeleteAccountStudentFragment extends Fragment {

    private DeleteAccountStudentViewModel deleteAccountStudentViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        deleteAccountStudentViewModel =
                new ViewModelProvider(this).get(DeleteAccountStudentViewModel.class);
        View root = inflater.inflate(R.layout.fragment_delete_account_student, container, false);
        return root;
    }
}