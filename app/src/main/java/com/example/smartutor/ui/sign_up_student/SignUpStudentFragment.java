package com.example.smartutor.ui.sign_up_student;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.smartutor.R;
import com.example.smartutor.Utilities;
import com.example.smartutor.model.Gender;
import com.example.smartutor.model.Student;
import com.example.smartutor.model.Tutor;
import com.example.smartutor.ui.StudentMenuActivity;
import com.example.smartutor.ui.sign_in.SignInViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;

@RequiresApi(api = Build.VERSION_CODES.O)
public class SignUpStudentFragment extends Fragment {
    //view model
    private SignUpStudentViewModel signUpStudentViewModel;

    //views
    private TextView signIn;
    private Spinner gender;
    private Spinner grade;
    private Button chooseDate;
    private EditText date;
    private Button signUp;
    private EditText email;
    private EditText lastName;
    private EditText firstName;
    private EditText password;
    private EditText confirm;

    public SignUpStudentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //view model
        signUpStudentViewModel = new ViewModelProvider(this).get(SignUpStudentViewModel.class);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_sign_up, container, false);

        // views setup
        signIn = view.findViewById(R.id.signUpStudent_signIn_tv);
        gender = view.findViewById(R.id.signUpStudent_gender_spn);
        grade = view.findViewById(R.id.signUpStudent_grade_spn);
        chooseDate = view.findViewById(R.id.signUpStudent_birthdayDate_btn);
        date = view.findViewById(R.id.signUpStudent_birthdayDate_et);
        signUp = view.findViewById(R.id.signUpStudent_signUp_btn);
        email = view.findViewById(R.id.signUpStudent_email_et);
        lastName = view.findViewById(R.id.signUpStudent_lastName_et);
        firstName = view.findViewById(R.id.signUpStudent_firstName_et);
        password = view.findViewById(R.id.signUpStudent_password_et);
        confirm = view.findViewById(R.id.signUpStudent_confirmPassword_et);

        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.gender, R.layout.spinner_item);
        gender.setAdapter(genderAdapter);
        ArrayAdapter<CharSequence> gradeAdapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.grade,R.layout.spinner_item);
        grade.setAdapter(gradeAdapter);


        // events setup
        signIn.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_global_signIn));
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
        signUp.setOnClickListener(v -> {
            try {
                Utilities.validateEmail(email.getText().toString());
                Utilities.validateLastName(lastName.getText().toString());
                Utilities.validateFirstName(firstName.getText().toString());
                Utilities.validateDate(date.getText().toString());
                Utilities.validatePassword(password.getText().toString(), confirm.getText().toString());
                if(signUpStudentViewModel.isExistStudent(email.getText().toString())){
                    Snackbar.make(signUp, "email in use", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
                else{
                    Student student = new Student(email.getText().toString(), lastName.getText().toString(), firstName.getText().toString(), Gender.valueOf(gender.getSelectedItem().toString().toUpperCase()), Utilities.convertToDate(date.getText().toString()), Utilities.convertToGrade(grade.getSelectedItem().toString()), password.getText().toString());
                    signUpStudentViewModel.addStudent(student);
                    Intent intent = new Intent(getActivity(), StudentMenuActivity.class);
                    intent.putExtra("EMAIL", email.getText().toString());
                    Navigation.findNavController(view).navigate(R.id.action_global_signIn);
                    startActivity(intent);
                }
            }
            catch (Exception e) {
                Snackbar.make(signUp, e.getMessage(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }

        });
        return view;
    }
}