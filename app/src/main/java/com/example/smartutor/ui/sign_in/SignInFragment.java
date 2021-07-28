package com.example.smartutor.ui.sign_in;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.example.smartutor.model.Model;
import com.example.smartutor.ui.student_menu_activity.StudentMenuActivity;
import com.example.smartutor.ui.tutor_menu_activity.TutorMenuActivity;
import com.google.android.material.snackbar.Snackbar;

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
    private SwipeRefreshLayout swipeUp;


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
        swipeUp = view.findViewById(R.id.signIn_swipeUp);

        enableButtons(true);

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
            v.setEnabled(false);
            enableButtons(false);
            try {
                if (isStudent.isChecked()) {
                    signInViewModel.signIn("student", email.getText().toString(), password.getText().toString(), () -> {
                        Intent intent = null;
                        intent = new Intent(getActivity(), StudentMenuActivity.class);
                        if (intent != null) {
                            startActivity(intent);
                            email.setText("");
                            password.setText("");
                        }
                    }, () -> {
                        email.setText("");
                        password.setText("");
                        Snackbar.make(signIn, "wrong details", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        enableButtons(true);
                    });
                } else if (isTutor.isChecked()) {
                    signInViewModel.signIn("tutor", email.getText().toString(), password.getText().toString(), () -> {
                        Intent intent = null;
                        intent = new Intent(getActivity(), TutorMenuActivity.class);
                        if (intent != null) {
                            startActivity(intent);
                            email.setText("");
                            password.setText("");
                        }
                        }, () -> {
                        email.setText("");
                        password.setText("");
                        Snackbar.make(signIn, "wrong details", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        enableButtons(true);
                    });
                }
            }
            catch (Exception e){
                Snackbar.make(signIn, "error", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                enableButtons(true);
            }
        });

        Model.getInstance().studentLoadingState.observe(getViewLifecycleOwner(), state -> handleLoading());
        Model.getInstance().tutorLoadingState.observe(getViewLifecycleOwner(), state -> handleLoading());
        swipeUp.setOnRefreshListener(()->{
            Model.getInstance().refreshStudents();
            Model.getInstance().refreshTutors();
        });

        return view;
    }

    private void enableButtons(boolean state){
        isStudent.setEnabled(state);
        isTutor.setEnabled(state);
        signIn.setEnabled(state);
        signUp.setEnabled(state);
        email.setEnabled(state);
        password.setEnabled(state);
    }
    private void handleLoading(){
        if(Model.getInstance().studentLoadingState.getValue() == Model.LoadingState.loaded && Model.getInstance().tutorLoadingState.getValue() == Model.LoadingState.loaded){
            enableButtons(true);
            swipeUp.setRefreshing(false);
        }
        else{
            enableButtons(false);
            swipeUp.setRefreshing(true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        signInViewModel.signOut();
    }
}