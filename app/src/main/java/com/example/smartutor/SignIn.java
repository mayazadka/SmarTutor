package com.example.smartutor;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SignIn extends Fragment {

    private TextView signUp;

    public SignIn() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        signUp = view.findViewById(R.id.signIn_signUp_tv);
        signUp.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_global_studentSignUp));

        return view;
    }
}