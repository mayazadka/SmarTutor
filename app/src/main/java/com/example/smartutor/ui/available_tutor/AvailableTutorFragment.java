package com.example.smartutor.ui.available_tutor;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.smartutor.R;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AvailableTutorFragment extends Fragment {

    TextView date;
    TextView hour;
    Button set;
    LocalDateTime dateTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_available_tutor, container, false);

        date = root.findViewById(R.id.availableTutor_date_tv);
        hour = root.findViewById(R.id.availableTutor_hour_tv);
        set = root.findViewById(R.id.availableTutor_set_btn);

        LocalDateTime now = LocalDateTime.now();

        Log.d("aaa", Integer.toString(AvailableTutorFragmentArgs.fromBundle(getArguments()).getDay()));
        Log.d("aaa", Integer.toString((now.getDayOfWeek().getValue() % 7) +1));
        dateTime = LocalDate.now().atTime(AvailableTutorFragmentArgs.fromBundle(getArguments()).getHour(), 0).plusDays(AvailableTutorFragmentArgs.fromBundle(getArguments()).getDay() - ((now.getDayOfWeek().getValue() % 7) +1));
        date.setText(dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        hour.setText(dateTime.format(DateTimeFormatter.ofPattern("HH:mm")));

        set.setOnClickListener(v -> {

        });
        return root;
    }
}