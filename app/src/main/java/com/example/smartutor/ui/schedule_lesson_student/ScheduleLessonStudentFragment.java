package com.example.smartutor.ui.schedule_lesson_student;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartutor.R;
import com.example.smartutor.model.Profession;
import com.example.smartutor.ui.available_tutor.AvailableTutorFragmentArgs;
import com.example.smartutor.ui.available_tutor.AvailableTutorViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScheduleLessonStudentFragment extends Fragment {

    TextView date;
    TextView hour;
    TextView tutorName;
    Button schedule;
    ImageView img;

    LocalDateTime dateTime;
    Profession subject;

    public ScheduleLessonStudentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ScheduleLessonStudentViewModel viewModel = new ViewModelProvider(this).get(ScheduleLessonStudentViewModel.class);
        viewModel.initial(ScheduleLessonStudentFragmentArgs.fromBundle(getArguments()).getEmail());
        View root = inflater.inflate(R.layout.fragment_schedule_lesson_student, container, false);

        date = root.findViewById(R.id.scheduleLessonStudent_date_tv);
        hour = root.findViewById(R.id.scheduleLessonStudent_hour_tv);
        schedule = root.findViewById(R.id.scheduleLessonStudent_schedule_btn);
        tutorName = root.findViewById(R.id.scheduleLessonStudent_tutor_tv);
        img = root.findViewById(R.id.scheduleLessonStudent_subject_img);

        subject = null;

        LocalDateTime now = LocalDateTime.now();

        dateTime = LocalDate.now().atTime(ScheduleLessonStudentFragmentArgs.fromBundle(getArguments()).getHour(), 0).plusDays(ScheduleLessonStudentFragmentArgs.fromBundle(getArguments()).getDay() - ((now.getDayOfWeek().getValue() % 7) +1));
        date.setText(dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        hour.setText(dateTime.format(DateTimeFormatter.ofPattern("HH:mm")));
        img.setImageResource(R.drawable.ic_baseline_block_24);

        viewModel.getTutor().observe(getViewLifecycleOwner(), t ->{
            if(t!=null){
                tutorName.setText(t.getFirstName() +" "+t.getLastName());
            }
        });

        schedule.setOnClickListener(v -> {
            if(subject == null){
                Snackbar.make(v, "choosw subject", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
            else{
                viewModel.addLesson(dateTime, ScheduleLessonStudentFragmentArgs.fromBundle(getArguments()).getEmail(),getActivity().getIntent().getStringExtra("EMAIL"), subject);
                Navigation.findNavController(root).navigate(R.id.action_global_nav_tutor_details_student);
            }
        });

        return root;
    }
}