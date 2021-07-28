package com.example.smartutor.ui.delete_account_student;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.smartutor.R;

@RequiresApi(api = Build.VERSION_CODES.O)
public class DeleteAccountStudentFragment extends Fragment {

    private DeleteAccountStudentViewModel deleteAccountStudentViewModel;

    private Button yes;
    private Button no;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        deleteAccountStudentViewModel = new ViewModelProvider(this).get(DeleteAccountStudentViewModel.class);
        View root = inflater.inflate(R.layout.fragment_delete_account_student, container, false);

        yes = root.findViewById(R.id.deleteAccountStudent_yes_btn);
        no = root.findViewById(R.id.deleteAccountStudent_no_btn);

        yes.setOnClickListener(v -> {
            v.setEnabled(false);
            deleteAccountStudentViewModel.deleteStudent(()-> getActivity().finish());
        });

        no.setOnClickListener(v -> Navigation.findNavController(root).navigate(R.id.action_global_nav_home_student));

        return root;
    }
}