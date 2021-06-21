package com.example.smartutor.ui.edit_details_student;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.smartutor.R;
import com.example.smartutor.model.Gender;
import com.example.smartutor.model.Student;
import com.example.smartutor.ui.StudentMenuActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RequiresApi(api = Build.VERSION_CODES.O)
public class EditDetailsStudentFragment extends Fragment {

    private EditDetailsStudentViewModel editDetailsStudentViewModel;
    private EditText lastName;
    private EditText firstName;
    private Spinner gender;
    private Spinner grade;
    private Button chooseDate;
    private EditText date;
    private EditText password;
    private EditText confirm;
    private Button save;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // view model
        editDetailsStudentViewModel = new ViewModelProvider(this).get(EditDetailsStudentViewModel.class);
        editDetailsStudentViewModel.initial(getActivity().getIntent().getStringExtra("EMAIL"));

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_edit_details_student, container, false);

        // views setup
        lastName = root.findViewById(R.id.editDetailsStudent_lastName_et);
        firstName = root.findViewById(R.id.editDetailsStudent_firstName_et);
        gender = root.findViewById(R.id.editDetailsStudent_gender_spn);
        grade = root.findViewById(R.id.editDetailsStudent_grade_spn);
        chooseDate = root.findViewById(R.id.editDetailsStudent_birthdayDate_btn);
        date = root.findViewById(R.id.editDetailsStudent_birthdayDate_et);
        password = root.findViewById(R.id.editDetailsStudent_password_et);
        confirm = root.findViewById(R.id.editDetailsStudent_confirmPassword_et);
        save = root.findViewById(R.id.editDetailsStudent_save_btn);

        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.gender, R.layout.spinner_item);
        gender.setAdapter(genderAdapter);
        ArrayAdapter<CharSequence> gradeAdapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.grade, R.layout.spinner_item);
        grade.setAdapter(gradeAdapter);

        // events setup
        chooseDate.setOnClickListener(v -> {
            DatePicker picker = new DatePicker(getContext());
            picker.setCalendarViewShown(false);
            picker.setMaxDate(new Date().getTime());
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext(), R.style.AlertDialogStyle)
                    .setView(picker)
                    .setPositiveButton("OK", (dialog, which) -> dialog.cancel())
                    .setOnCancelListener(dialog -> date.setText(picker.getDayOfMonth() + "/" + (picker.getMonth() + 1) + "/" + picker.getYear()));
            builder.show();
        });

//        lastName.setText(editDetailsStudentViewModel.getStudent().getLastName());
//        firstName.setText(editDetailsStudentViewModel.getStudent().getFirstName());
//        switch (editDetailsStudentViewModel.getStudent().getGender()) {
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
//        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//        dateFormat.setLenient(false);
//        date.setText(dateFormat.format(editDetailsStudentViewModel.getStudent().getBirthdayDate()));
//
//        grade.setSelection(editDetailsStudentViewModel.getStudent().getGrade() - 1);
//        password.setText(editDetailsStudentViewModel.getStudent().getPassword());
//        confirm.setText(editDetailsStudentViewModel.getStudent().getPassword());
//
//        save.setOnClickListener(v -> {
//            //TODO: validation
//            if(true){
//                Student s = new Student();
//                s.setFirstName(firstName.getText().toString());
//                s.setLastName(lastName.getText().toString());
//                s.setGender(Gender.valueOf(gender.getSelectedItem().toString().toUpperCase()));
//                //TODO: s.setBirthdayDate();
//                //TODO: s.setGrade();
//                s.setPassword(password.getText().toString());
//                editDetailsStudentViewModel.updateStudent(s);
//                //TODO: navigate to home
//            }
//            else{
//                Snackbar.make(save, "wrong details", Snackbar.LENGTH_LONG).setAction("Action", null).show();
//            }
//        });

        return root;
    }
}