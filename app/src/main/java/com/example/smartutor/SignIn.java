package com.example.smartutor;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

public class SignIn extends Fragment {

    //views
    private TextView signUp;
    private RadioButton isStudent;
    private RadioButton isTutor;
    private ImageButton showHide;
    private EditText password;
    private Button signIn;

    public SignIn() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        // views setup
        signUp = view.findViewById(R.id.signIn_signUp_tv);
        isStudent = view.findViewById(R.id.signIn_student_rb);
        isTutor = view.findViewById(R.id.signIn_tutor_rb);
        showHide = view.findViewById(R.id.signIn_showHide_imgbtn);
        password = view.findViewById(R.id.signIn_password_et);
        signIn = view.findViewById(R.id.signIn_signIn_btn);

        // events setup
        signUp.setOnClickListener(v -> {
            if(isStudent.isChecked()){Navigation.findNavController(view).navigate(R.id.action_global_studentSignUp);}
            else if(isTutor.isChecked()){Navigation.findNavController(view).navigate(R.id.action_global_tutorSignUp);}
        });
        showHide.setOnTouchListener((v, event) -> {
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
        signIn.setOnClickListener(v -> {
            Intent intent = null;
            if(isStudent.isChecked()){intent = new Intent(getActivity(), StudentMenuActivity.class);}
            else if(isTutor.isChecked()){intent = new Intent(getActivity(), TutorMenuActivity.class);}
            startActivity(intent);
        });


        return view;
    }
}