package com.example.smartutor.ui.home_tutor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.smartutor.R;

public class HomeTutorFragment extends Fragment {

    private HomeTutorViewModel homeTutorViewModel;

    private TextView hello;
    private TextView thisWeek;
    private TextView remain;
    private TextView total;

    private String email;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        email = getActivity().getIntent().getStringExtra("EMAIL");

        homeTutorViewModel = new ViewModelProvider(this).get(HomeTutorViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home_tutor, container, false);

        hello = root.findViewById(R.id.homeTutor_hello_tv);
        thisWeek = root.findViewById(R.id.homeTutor_thisWeakNumber_tv);
        remain = root.findViewById(R.id.homeTutor_remainNumber_tv);
        total = root.findViewById(R.id.homeTutor_totalNumber_tv);

        return root;
    }
}