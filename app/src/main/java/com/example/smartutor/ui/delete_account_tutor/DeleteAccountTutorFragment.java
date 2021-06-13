package com.example.smartutor.ui.delete_account_tutor;

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
import com.example.smartutor.ui.delete_account_student.DeleteAccountStudentViewModel;

public class DeleteAccountTutorFragment extends Fragment {

    private DeleteAccountTutorViewModel deleteAccountTutorViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        deleteAccountTutorViewModel = new ViewModelProvider(this).get(DeleteAccountTutorViewModel.class);
        View root = inflater.inflate(R.layout.fragment_delete_account_tutor, container, false);
        return root;
    }
}