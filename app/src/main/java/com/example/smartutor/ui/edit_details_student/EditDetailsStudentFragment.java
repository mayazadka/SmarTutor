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
    private String emailStudent;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // view model
        editDetailsStudentViewModel = new ViewModelProvider(this).get(EditDetailsStudentViewModel.class);

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

        emailStudent = getActivity().getIntent().getStringExtra("EMAIL");
        Student student = editDetailsStudentViewModel.getStudent(emailStudent);

        lastName.setText(student.getLastName());
        firstName.setText(student.getFirstName());
        switch (student.getGender()) {
            case MALE:
                gender.setSelection(0);
                break;
            case FEMALE:
                gender.setSelection(1);
                break;
            case OTHER:
                gender.setSelection(2);
                break;
        }

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        date.setText(dateFormat.format(student.getBirthdayDate()));

        grade.setSelection(student.getGrade() - 1);
        password.setText(student.getPassword());
        confirm.setText(student.getPassword());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student updatedStudent = checkDetails();
                if(updatedStudent != null){
                    editDetailsStudentViewModel.updateStudent(updatedStudent);
                    //TODO: navigate to home
                }
                else{
                    Snackbar.make(save, "wrong details", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }
        });

        return root;
    }

    private Student checkDetails(){
        if(!password.getText().toString().equals(confirm.getText().toString())){return null;}
        Gender gen = Gender.valueOf(gender.getSelectedItem().toString().toUpperCase());
        Date birthdayDate;
        try {birthdayDate = new SimpleDateFormat("dd/MM/yyyy").parse(date.getText().toString());}catch(Exception e){return null;}
        int gradeNum = grade.getSelectedItemPosition() + 1;
        Student student = new Student(emailStudent, lastName.getText().toString(), firstName.getText().toString(), gen, birthdayDate, gradeNum, password.getText().toString());
        // check specific details
        return student;
    }
}