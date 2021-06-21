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
import com.example.smartutor.model.Lesson;
import com.example.smartutor.model.Student;
import com.example.smartutor.model.Tutor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
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
                List<Lesson> copy = new LinkedList<>(lessons);
                copy.removeIf(l -> l.getDate().isBefore(LocalDateTime.now()));
                Collections.sort(lessons, (l1, l2) -> l1.getDate().isBefore(l2.getDate())?1:0);
                if(copy.size() == 0){return;}
                if(tutor!=null){tutor.removeObservers(getViewLifecycleOwner());}

                nextLessonSubject.setText(copy.get(0).getSubject().toString().replace("_", " ").toLowerCase());
                nextLessonDate.setText(copy.get(0).getDate().format(DateTimeFormatter.ISO_DATE)+" - "+copy.get(0).getDate().getHour()+":00");
                tutor = homeStudentViewModel.getTutor(copy.get(0).getTutorEmail());
                tutor.observe(getViewLifecycleOwner(), t -> {if(t!=null)nextLessonTutor.setText(t.getFirstName()+" "+t.getLastName());});
                //TODO: image of next lesson
            }

        });

//        lessonsThisWeek.setText(String.valueOf(homeStudentViewModel.getThisWeekLessonsStudent(emailStudent)));
//        lessonsRemain.setText(String.valueOf(homeStudentViewModel.getRemainLessonsStudent(emailStudent)));
//        lessonsTotal.setText(String.valueOf(homeStudentViewModel.getStudentLessons(emailStudent)));
//        Lesson nextLesson = homeStudentViewModel.getNextLessonStudent(emailStudent);
//        if(nextLesson != null){
//            Tutor tutor = homeStudentViewModel.getTutor(nextLesson.getTutorEmail());
//            nextLessonSubject.setText(nextLesson.getSubject().name());
//            nextLessonTutor.setText(tutor.getFirstName() + " " + tutor.getLastName());
//            nextLessonDate.setText(nextLesson.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm")));
//
//            switch (nextLesson.getSubject()){
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
//            nextLessonTutor.setText("");
//            nextLessonDate.setText("");
//            nextLessonSubjectImg.setImageResource(R.drawable.ic_subject_math);
//        }

        return root;
    }
}