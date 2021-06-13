package com.example.smartutor.ui.home_tutor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.smartutor.R;

public class HomeTutorFragment extends Fragment {

    private HomeTutorViewModel homeTutorViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeTutorViewModel = new ViewModelProvider(this).get(HomeTutorViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home_tutor, container, false);
        return root;
    }
}