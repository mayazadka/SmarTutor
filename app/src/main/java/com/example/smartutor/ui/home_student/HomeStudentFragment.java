package com.example.smartutor.ui.home_student;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.smartutor.R;
import com.example.smartutor.model.Lesson;
import com.example.smartutor.model.Student;

import java.time.format.DateTimeFormatter;

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
        View root = inflater.inflate(R.layout.fragment_home_student, container, false);
        helloTv = root.findViewById(R.id.homeStudent_hello_tv);
        lessonsThisWeek = root.findViewById(R.id.homeStudent_thisWeakNumber_tv);
        lessonsRemain = root.findViewById(R.id.homeStudent_remainNumber_tv);
        lessonsTotal = root.findViewById(R.id.homeStudent_totalNumber_tv);
        nextLessonSubject = root.findViewById(R.id.homeStudent_subject_tv);
        nextLessonTutor = root.findViewById(R.id.homeStudent_tutor_tv);
        nextLessonDate = root.findViewById(R.id.homeStudent_date_tv);
        nextLessonSubjectImg = root.findViewById(R.id.homeStudent_subject_img);

        String emailStudent = getActivity().getIntent().getStringExtra("EMAIL");
        Student student = homeStudentViewModel.getStudent(emailStudent);

        helloTv.setText("hello, " + student.getFirstName() + " " + student.getLastName());
        lessonsThisWeek.setText(String.valueOf(homeStudentViewModel.getThisWeekLessonsStudent(emailStudent)));
        lessonsRemain.setText(String.valueOf(homeStudentViewModel.getRemainLessonsStudent(emailStudent)));
        lessonsTotal.setText(String.valueOf(homeStudentViewModel.getStudentLessons(emailStudent)));
        Lesson nextLesson = homeStudentViewModel.getNextLessonStudent(emailStudent);
        nextLessonSubject.setText(nextLesson.getSubject().name());
        nextLessonTutor.setText(nextLesson.getTutor().getFirstName() + " " + nextLesson.getTutor().getLastName());
        nextLessonDate.setText(nextLesson.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm")));

        switch (nextLesson.getSubject()){
            case MATH:
                nextLessonSubjectImg.setImageResource(R.drawable.ic_math);
                break;
            case HISTORY:
                nextLessonSubjectImg.setImageResource(R.drawable.ic_history);
                break;
            case SCIENCE:
                nextLessonSubjectImg.setImageResource(R.drawable.ic_science);
                break;
            case LANGUAGE:
                nextLessonSubjectImg.setImageResource(R.drawable.ic_english);
                break;
            case LITERATURE:
                nextLessonSubjectImg.setImageResource(R.drawable.ic_literature);
                break;
            case COMPUTERSCIENCE:
                nextLessonSubjectImg.setImageResource(R.drawable.ic_computer_science);
                break;
        }
        return root;
    }
}