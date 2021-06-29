package com.example.smartutor.ui.home_tutor;

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
import java.util.LinkedList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
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
        homeTutorViewModel = new ViewModelProvider(this).get(HomeTutorViewModel.class);
        homeTutorViewModel.initial(getActivity().getIntent().getStringExtra("EMAIL"));

        View root = inflater.inflate(R.layout.fragment_home_tutor, container, false);

        helloTv = root.findViewById(R.id.homeTutor_hello_tv);
        thisWeek = root.findViewById(R.id.homeTutor_thisWeakNumber_tv);
        remain = root.findViewById(R.id.homeTutor_remainNumber_tv);
        total = root.findViewById(R.id.homeTutor_totalNumber_tv);
        nextLessonSubject = root.findViewById(R.id.homeTutor_subject_tv);
        nextLessonStudent = root.findViewById(R.id.homeTutor_student_tv);
        nextLessonDate = root.findViewById(R.id.homeTutor_date_tv);
        nextLessonSubjectImg = root.findViewById(R.id.homeTutor_subject_img);

        homeTutorViewModel.getTutor().observe(getViewLifecycleOwner(), tutor -> helloTv.setText("hello, " + tutor.getFirstName()+" " +tutor.getLastName()));

        homeTutorViewModel.getLessons().observe(getViewLifecycleOwner(), new Observer<List<Lesson>>() {
            private LiveData<Student> student = null;

            @Override
            public void onChanged(List<Lesson> lessons) {
                if(student!=null){student.removeObservers(getViewLifecycleOwner());}

                thisWeek.setText(String.valueOf(Utilities.getThisWeekLessons(lessons).size()));
                remain.setText(String.valueOf(Utilities.getRemainLessons(lessons).size()));
                total.setText(String.valueOf(lessons.size()));

                Lesson nextLesson = Utilities.getNextLesson(lessons);
                if(nextLesson == null){return;}

                nextLessonSubject.setText(nextLesson.getSubject().toString().replace("_", " ").toLowerCase());
                nextLessonDate.setText(nextLesson.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm")));
                student = homeTutorViewModel.getStudent(nextLesson.getStudentEmail());
                student.observe(getViewLifecycleOwner(), s -> {if(s!=null)nextLessonStudent.setText(s.getFirstName()+" "+s.getLastName());});

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
//
//        Lesson nextLesson = homeTutorViewModel.getNextLessonTutor(emailStudent);
//        if(nextLesson != null) {
//            Student student = homeTutorViewModel.getStudent(nextLesson.getStudentEmail());
//            nextLessonSubject.setText(nextLesson.getSubject().name());
//            nextLessonStudent.setText(student.getFirstName() + " " + student.getLastName());
//            nextLessonDate.setText(nextLesson.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm")));
//
//            switch (nextLesson.getSubject()) {
//                case MATH:
//                    nextLessonSubjectImg.setImageResource(R.drawable.ic_subject_math);
//                    break;
//                case HISTORY:
//                    nextLessonSubjectImg.setImageResource(R.drawable.ic_subject_history);
//                    break;
//                case SCIENCE:
//                    nextLessonSubjectImg.setImageResource(R.drawable.ic_subject_science);
//                    break;
//                case LANGUAGE:
//                    nextLessonSubjectImg.setImageResource(R.drawable.ic_subject_english);
//                    break;
//                case LITERATURE:
//                    nextLessonSubjectImg.setImageResource(R.drawable.ic_subject_literature);
//                    break;
//                case COMPUTERSCIENCE:
//                    nextLessonSubjectImg.setImageResource(R.drawable.ic_subject_computer_science);
//                    break;
//            }
//        }
//        else {
//            nextLessonSubject.setText("");
//            nextLessonStudent.setText("");
//            nextLessonDate.setText("");
//            nextLessonSubjectImg.setImageResource(R.drawable.ic_subject_math);
//        }

        return root;
    }
}