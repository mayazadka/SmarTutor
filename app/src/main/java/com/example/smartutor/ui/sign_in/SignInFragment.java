package com.example.smartutor.ui.sign_in;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.smartutor.R;
import com.example.smartutor.ui.StudentMenuActivity;
import com.example.smartutor.ui.TutorMenuActivity;
import com.example.smartutor.ui.delete_account_student.DeleteAccountStudentViewModel;
import com.google.android.material.snackbar.Snackbar;

@RequiresApi(api = Build.VERSION_CODES.O)
public class SignInFragment extends Fragment {
    //view model
    private SignInViewModel signInViewModel;

    //views
    private TextView signUp;
    private RadioButton isStudent;
    private RadioButton isTutor;
    private ImageButton showHide;
    private EditText password;
    private Button signIn;
    private EditText email;

    public SignInFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //view model
        signInViewModel = new ViewModelProvider(this).get(SignInViewModel.class);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        // views setup
        signUp = view.findViewById(R.id.signIn_signUp_tv);
        isStudent = view.findViewById(R.id.signIn_student_rb);
        isTutor = view.findViewById(R.id.signIn_tutor_rb);
        showHide = view.findViewById(R.id.signIn_showHide_imgbtn);
        password = view.findViewById(R.id.signIn_password_et);
        signIn = view.findViewById(R.id.signIn_signIn_btn);
        email = view.findViewById(R.id.signIn_email_et);

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
            if(isStudent.isChecked()){
                if(signInViewModel.checkDetailsStudent(email.getText().toString(), password.getText().toString())){
                    intent = new Intent(getActivity(), StudentMenuActivity.class);
                }
            }
            else if(isTutor.isChecked()){
                if(signInViewModel.checkDetailsTutor(email.getText().toString(), password.getText().toString())) {
                    intent = new Intent(getActivity(), TutorMenuActivity.class);
                }
            }
            if(intent != null){
                intent.putExtra("EMAIL", email.getText().toString());
                startActivity(intent);
            }
            else{
                Snackbar.make(signIn, "wrong details", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
        return view;
    }
}