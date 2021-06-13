package com.example.smartutor.ui.edit_details_tutor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.smartutor.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Date;

public class EditDetailsTutorFragment extends Fragment {

    private EditDetailsTutorViewModel editDetailsTutorViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // view model
        editDetailsTutorViewModel = new ViewModelProvider(this).get(EditDetailsTutorViewModel.class);

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_edit_details_tutor, container, false);

        return root;
    }
}