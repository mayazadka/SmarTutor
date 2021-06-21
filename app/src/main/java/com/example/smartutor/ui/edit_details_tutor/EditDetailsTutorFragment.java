package com.example.smartutor.ui.edit_details_tutor;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.smartutor.MultiSpinner;
import com.example.smartutor.R;
import com.example.smartutor.model.Gender;
import com.example.smartutor.model.Profession;
import com.example.smartutor.model.Student;
import com.example.smartutor.model.Tutor;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class EditDetailsTutorFragment extends Fragment {

    private EditDetailsTutorViewModel editDetailsTutorViewModel;
    //views
    private EditText lastName;
    private EditText firstName;
    private Spinner gender;
    private EditText date;
    private Button chooseDate;
    private MultiSpinner profesions;
    private EditText aboutMe;
    private EditText password;
    private EditText confirm;
    private Button save;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // view model
        editDetailsTutorViewModel = new ViewModelProvider(this).get(EditDetailsTutorViewModel.class);
        editDetailsTutorViewModel.initial(getActivity().getIntent().getStringExtra("EMAIL"));

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_edit_details_tutor, container, false);

        // views setup
        lastName = root.findViewById(R.id.editDetailsTutor_lastName_et);
        firstName = root.findViewById(R.id.editDetailsTutor_firstName_et);
        gender = root.findViewById(R.id.editDetailsTutor_gender_spn);
        date = root.findViewById(R.id.editDetailsTutor_birthdayDate_et);
        chooseDate = root.findViewById(R.id.editDetailsTutor_birthdayDate_btn);
        profesions = root.findViewById(R.id.editDetailsTutor_professions_spn);
        aboutMe = root.findViewById(R.id.signUpTutor_aboutMe_etml);
        password = root.findViewById(R.id.editDetailsTutor_password_et);
        confirm = root.findViewById(R.id.editDetailsTutor_confirmPassword_et);
        save = root.findViewById(R.id.editDetailsTutor_save_btn);

        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.gender, R.layout.spinner_item);
        gender.setAdapter(genderAdapter);

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

//        lastName.setText(editDetailsTutorViewModel.getTutor().getLastName());
//        firstName.setText(editDetailsTutorViewModel.getTutor().getFirstName());
//
//        switch (editDetailsTutorViewModel.getTutor().getGender()) {
//            case MALE:
//                gender.setSelection(0);
//                break;
//            case FEMALE:
//                gender.setSelection(1);
//                break;
//            case OTHER:
//                gender.setSelection(2);
//                break;
//        }
//
//        date.setText(new SimpleDateFormat("dd/MM/yyyy").format(editDetailsTutorViewModel.getTutor().getBirthdayDate()));
//        // TODO: add the names of the profession in the display
//        profesions.setItems(Arrays.asList(getResources().getStringArray(R.array.subject)), "Choose professions.", selected -> editDetailsTutorViewModel.getTutor().getProfessions());
//        aboutMe.setText(editDetailsTutorViewModel.getTutor().getAboutMe());
//        password.setText(editDetailsTutorViewModel.getTutor().getPassword());
//        confirm.setText(editDetailsTutorViewModel.getTutor().getPassword());
//
//        save.setOnClickListener(v -> {
//
//            //TODO: validation
//            if(true){
//                Tutor t = new Tutor();
//                t.setAboutMe(aboutMe.getText().toString());
//                //TODO: t.setBirthdayDate();
//                t.setFirstName(firstName.getText().toString());
//                //TODO: t.setGender();
//                t.setLastName(lastName.getText().toString());
//                t.setPassword(password.getText().toString());
//                //TODO: t.setProfessions();
//                editDetailsTutorViewModel.updateTutor(t);
//                //TODO: navigate to home
//            }
//            else{
//                Snackbar.make(save, "wrong details", Snackbar.LENGTH_LONG).setAction("Action", null).show();
//            }
//        });

        return root;
    }
}