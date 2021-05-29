package com.example.smartutor;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class StudentSignUp extends Fragment {

    //views
    private TextView signIn;
    private Spinner gender;
    private Spinner grade;
    private ImageButton passwordShowHide;
    private ImageButton confirmShowHide;
    private EditText password;
    private EditText confirm;

    public StudentSignUp() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_sign_up, container, false);

        // views setup
        signIn = view.findViewById(R.id.signUpStudent_signIn_tv);
        gender = view.findViewById(R.id.signUpStudent_gender_spn);
        grade = view.findViewById(R.id.signUpStudent_grade_spn);
        passwordShowHide = view.findViewById(R.id.signUpStudent_passwordShowHide_imgbtn);
        confirmShowHide = view.findViewById(R.id.signUpStudent_confirmPasswordShowHide_imgbtn);
        password = view.findViewById(R.id.signUpStudent_password_et);
        confirm = view.findViewById(R.id.signUpStudent_confirmPassword_et);

        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.gender, R.layout.spinner_item);
        gender.setAdapter(genderAdapter);
        ArrayAdapter<CharSequence> gradeAdapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.grade,R.layout.spinner_item);
        grade.setAdapter(gradeAdapter);


        // events setup
        signIn.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_global_signIn));
        passwordShowHide.setOnTouchListener((v, event) -> {
            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    password.setTransformationMethod(null);
                    return false; // if you want to handle the touch event
                case MotionEvent.ACTION_UP:
                    password.setTransformationMethod(new PasswordTransformationMethod());
                    return false; // if you want to handle the touch event
            }
            return false;
        });
        confirmShowHide.setOnTouchListener((v, event) -> {
            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    confirm.setTransformationMethod(null);
                    return false; // if you want to handle the touch event
                case MotionEvent.ACTION_UP:
                    confirm.setTransformationMethod(new PasswordTransformationMethod());
                    return false; // if you want to handle the touch event
            }
            return false;
        });

        return view;
    }


}