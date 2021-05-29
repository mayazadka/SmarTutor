package com.example.smartutor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class TutorSignUp extends Fragment {

    //views
    private TextView signIn;
    private Spinner gender;
    private ImageButton passwordShowHide;
    private ImageButton confirmShowHide;
    private EditText password;
    private EditText confirm;
    private MultiSpinner profesions;

    public TutorSignUp() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tutor_sign_up, container, false);

        // views setup
        signIn = view.findViewById(R.id.signUpTutor_signIn_tv);
        gender = view.findViewById(R.id.signUpTutor_gender_spn);
        passwordShowHide = view.findViewById(R.id.signUpTutor_passwordShowHide_imgbtn);
        confirmShowHide = view.findViewById(R.id.signUpTutor_confirmPasswordShowHide_imgbtn);
        password = view.findViewById(R.id.signUpTutor_password_et);
        confirm = view.findViewById(R.id.signUpTutor_confirmPassword_et);
        profesions = view.findViewById(R.id.signUpTutor_professions_spn);

        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.gender, R.layout.spinner_item);
        gender.setAdapter(genderAdapter);

        profesions.setItems(Arrays.asList(getResources().getStringArray(R.array.subject)), "Choose professions.", (MultiSpinner.MultiSpinnerListener) selected -> { });


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