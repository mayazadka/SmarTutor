package com.example.smartutor.ui.schedule_lesson_student;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.smartutor.R;
import com.example.smartutor.model.Profession;
import com.example.smartutor.ui.available_tutor.AvailableTutorFragmentArgs;
import com.example.smartutor.ui.available_tutor.AvailableTutorViewModel;
import com.example.smartutor.ui.search_tutors_student.SearchTutorsStudentFragmentDirections;
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
    Spinner subjects;

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
        subjects = root.findViewById(R.id.scheduleLessonStudent_subject_spn);

        subject = Profession.MATH;

        LocalDateTime now = LocalDateTime.now();

        dateTime = LocalDate.now().atTime(ScheduleLessonStudentFragmentArgs.fromBundle(getArguments()).getHour(), 0).plusDays(ScheduleLessonStudentFragmentArgs.fromBundle(getArguments()).getDay() - ((now.getDayOfWeek().getValue() % 7) +1));
        date.setText(dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        hour.setText(dateTime.format(DateTimeFormatter.ofPattern("HH:mm")));
        img.setImageResource(R.drawable.ic_subject_math);

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
                ScheduleLessonStudentFragmentDirections.ActionScheduleLessonStudentFragmentToNavTutorDetailsStudent action = ScheduleLessonStudentFragmentDirections.actionScheduleLessonStudentFragmentToNavTutorDetailsStudent(ScheduleLessonStudentFragmentArgs.fromBundle(getArguments()).getEmail());
                Navigation.findNavController(root).navigate(action);
            }
        });

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, android.R.id.text1);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subjects.setAdapter(spinnerAdapter);
        spinnerAdapter.add("Math");
        spinnerAdapter.add("Language");
        spinnerAdapter.add("Science");
        spinnerAdapter.add("Computer Science");
        spinnerAdapter.add("Literature");
        spinnerAdapter.add("History");
        subjects.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selected = (String)subjects.getSelectedItem();
                selected = selected.replace(" ", "_");
                selected = selected.toUpperCase();
                subject = Profession.valueOf(selected);

                switch (subject) {
                    case MATH:
                        img.setImageResource(R.drawable.ic_subject_math);
                        break;
                    case HISTORY:
                        img.setImageResource(R.drawable.ic_subject_history);
                        break;
                    case SCIENCE:
                        img.setImageResource(R.drawable.ic_subject_science);
                        break;
                    case LANGUAGE:
                        img.setImageResource(R.drawable.ic_subject_english);
                        break;
                    case LITERATURE:
                        img.setImageResource(R.drawable.ic_subject_literature);
                        break;
                    case COMPUTER_SCIENCE:
                        img.setImageResource(R.drawable.ic_subject_computer_science);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        spinnerAdapter.notifyDataSetChanged();


        return root;
    }
}