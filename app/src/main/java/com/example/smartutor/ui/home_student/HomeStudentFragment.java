package com.example.smartutor.ui.home_student;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.smartutor.R;
import com.example.smartutor.Utilities;
import com.example.smartutor.model.Lesson;
import com.example.smartutor.model.Student;
import com.example.smartutor.model.Tutor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class HomeStudentFragment extends Fragment {

    private HomeStudentViewModel homeStudentViewModel;
    private TextView helloTv;
    private TextView lessonsThisWeek;
    private TextView lessonsRemain;
    private TextView lessonsTotal;
    private TextView nextLessonSubject;
    private TextView nextLessonTutor;
    private TextView nextLessonDate;
    private ImageView nextLessonSubjectImg;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeStudentViewModel = new ViewModelProvider(this).get(HomeStudentViewModel.class);
        homeStudentViewModel.initial(getActivity().getIntent().getStringExtra("EMAIL"));

        View root = inflater.inflate(R.layout.fragment_home_student, container, false);

        helloTv = root.findViewById(R.id.homeStudent_hello_tv);
        lessonsThisWeek = root.findViewById(R.id.homeStudent_thisWeakNumber_tv);
        lessonsRemain = root.findViewById(R.id.homeStudent_remainNumber_tv);
        lessonsTotal = root.findViewById(R.id.homeStudent_totalNumber_tv);
        nextLessonSubject = root.findViewById(R.id.homeStudent_subject_tv);
        nextLessonTutor = root.findViewById(R.id.homeStudent_tutor_tv);
        nextLessonDate = root.findViewById(R.id.homeStudent_date_tv);
        nextLessonSubjectImg = root.findViewById(R.id.homeStudent_subject_img);

        homeStudentViewModel.getStudent().observe(getViewLifecycleOwner(), student -> helloTv.setText("hello, " + student.getFirstName()+" " +student.getLastName()));

        homeStudentViewModel.getLessons().observe(getViewLifecycleOwner(), new Observer<List<Lesson>>() {
            private LiveData<Tutor> tutor = null;

            @Override
            public void onChanged(List<Lesson> lessons) {
                if(tutor!=null){tutor.removeObservers(getViewLifecycleOwner());}

                lessonsThisWeek.setText(String.valueOf(Utilities.getThisWeekLessons(lessons).size()));
                lessonsRemain.setText(String.valueOf(Utilities.getRemainLessons(lessons).size()));
                lessonsTotal.setText(String.valueOf(lessons.size()));

                Lesson nextLesson = Utilities.getNextLesson(lessons);
                if(nextLesson == null){return;}

                nextLessonSubject.setText(nextLesson.getSubject().toString().replace("_", " ").toLowerCase());
                nextLessonDate.setText(nextLesson.getDate().format(DateTimeFormatter.ISO_DATE)+" - "+nextLesson.getDate().getHour()+":00");
                tutor = homeStudentViewModel.getTutor(nextLesson.getTutorEmail());
                tutor.observe(getViewLifecycleOwner(), t -> {if(t!=null)nextLessonTutor.setText(t.getFirstName()+" "+t.getLastName());});
                switch (nextLesson.getSubject()) {
                    case MATH:
                        nextLessonSubjectImg.setImageResource(R.drawable.ic_subject_math);
                        break;
                    case HISTORY:
                        nextLessonSubjectImg.setImageResource(R.drawable.ic_subject_history);
                        break;
                    case SCIENCE:
                        nextLessonSubjectImg.setImageResource(R.drawable.ic_subject_science);
                        break;
                    case LANGUAGE:
                        nextLessonSubjectImg.setImageResource(R.drawable.ic_subject_english);
                        break;
                    case LITERATURE:
                        nextLessonSubjectImg.setImageResource(R.drawable.ic_subject_literature);
                        break;
                    case COMPUTER_SCIENCE:
                        nextLessonSubjectImg.setImageResource(R.drawable.ic_subject_computer_science);
                        break;
                }
                //TODO: calendar

            }

        });
        return root;
    }
}