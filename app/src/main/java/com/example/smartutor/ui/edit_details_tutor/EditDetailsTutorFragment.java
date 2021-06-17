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

import com.example.smartutor.MultiSpinner;
import com.example.smartutor.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Arrays;
import java.util.Date;

public class EditDetailsTutorFragment extends Fragment {

    private EditDetailsTutorViewModel editDetailsTutorViewModel;
    //views
    private Spinner gender;
    private EditText password;
    private EditText confirm;
    private MultiSpinner profesions;
    private Button chooseDate;
    private EditText date;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // view model
        editDetailsTutorViewModel = new ViewModelProvider(this).get(EditDetailsTutorViewModel.class);

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_edit_details_tutor, container, false);

        // views setup
        gender = root.findViewById(R.id.editDetailsTutor_gender_spn);
        password = root.findViewById(R.id.editDetailsTutor_password_et);
        confirm = root.findViewById(R.id.editDetailsTutor_confirmPassword_et);
        profesions = root.findViewById(R.id.editDetailsTutor_professions_spn);
        chooseDate = root.findViewById(R.id.editDetailsTutor_birthdayDate_btn);
        date = root.findViewById(R.id.editDetailsTutor_birthdayDate_et);

        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.gender, R.layout.spinner_item);
        gender.setAdapter(genderAdapter);

        profesions.setItems(Arrays.asList(getResources().getStringArray(R.array.subject)), "Choose professions.", (MultiSpinner.MultiSpinnerListener) selected -> { });

        // events setup
        chooseDate.setOnClickListener(v -> {
            DatePicker picker = new DatePicker(getContext());
            picker.setCalendarViewShown(false);
            picker.setMaxDate(new Date().getTime());
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext(), R.style.AlertDialogStyle)
                    .setView(picker)
                    .setPositiveButton("OK", (dialog, which) -> dialog.cancel())
                    .setOnCancelListener(dialog -> date.setText(picker.getDayOfMonth()+"/"+ (picker.getMonth() + 1)+"/"+picker.getYear()));
            builder.show();
        });

        return root;
    }
}

//getActivity().getIntent().getStringExtra("EMAIL")
