package com.example.smartutor.ui.home_tutor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.smartutor.R;
import com.example.smartutor.model.Lesson;
import com.example.smartutor.model.Student;
import com.example.smartutor.model.Tutor;

import java.time.format.DateTimeFormatter;

public class HomeTutorFragment extends Fragment {

    private HomeTutorViewModel homeTutorViewModel;

    private TextView helloTv;
    private TextView thisWeek;
    private TextView remain;
    private TextView total;
    private TextView nextLessonSubject;
    private TextView nextLessonStudent;
    private TextView nextLessonDate;
    private ImageView nextLessonSubjectImg;

    private String email;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        email = getActivity().getIntent().getStringExtra("EMAIL");

        homeTutorViewModel = new ViewModelProvider(this).get(HomeTutorViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home_tutor, container, false);

        helloTv = root.findViewById(R.id.homeTutor_hello_tv);
        thisWeek = root.findViewById(R.id.homeTutor_thisWeakNumber_tv);
        remain = root.findViewById(R.id.homeTutor_remainNumber_tv);
        total = root.findViewById(R.id.homeTutor_totalNumber_tv);
        nextLessonSubject = root.findViewById(R.id.homeTutor_subject_tv);
        nextLessonStudent = root.findViewById(R.id.homeTutor_student_tv);
        nextLessonDate = root.findViewById(R.id.homeTutor_date_tv);
        nextLessonSubjectImg = root.findViewById(R.id.homeTutor_subject_img);

        String emailStudent = getActivity().getIntent().getStringExtra("EMAIL");
        Tutor tutor = homeTutorViewModel.getTutor(emailStudent);


        helloTv.setText("hello, " + tutor.getFirstName() + " " + tutor.getLastName());
        thisWeek.setText(String.valueOf(homeTutorViewModel.getThisWeekLessonsTutor(emailStudent)));
        remain.setText(String.valueOf(homeTutorViewModel.getRemainLessonsTutor(emailStudent)));
        total.setText(String.valueOf(homeTutorViewModel.getTutorLessons(emailStudent)));
        Lesson nextLesson = homeTutorViewModel.getNextLessonTutor(emailStudent);
        if(nextLesson != null) {
            nextLessonSubject.setText(nextLesson.getSubject().name());
            nextLessonStudent.setText(nextLesson.getStudent().getFirstName() + " " + nextLesson.getStudent().getLastName());
            nextLessonDate.setText(nextLesson.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm")));

            switch (nextLesson.getSubject()) {
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
        }
        else {
            nextLessonSubject.setText("");
            nextLessonStudent.setText("");
            nextLessonDate.setText("");
            nextLessonSubjectImg.setImageResource(R.drawable.ic_math);
        }

        return root;
    }
}