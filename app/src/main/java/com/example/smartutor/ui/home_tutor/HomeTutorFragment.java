package com.example.smartutor.ui.home_tutor;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.smartutor.R;
import com.example.smartutor.Utilities;
import com.example.smartutor.model.Event;
import com.example.smartutor.model.Lesson;
import com.example.smartutor.model.Model;
import com.example.smartutor.model.Student;
import com.example.smartutor.model.Tutor;
import com.example.smartutor.ui.search_tutors_student.SearchTutorsStudentFragmentDirections;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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
    private LinearLayout calendarLinearLayout;
    private SwipeRefreshLayout swipeUp;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeTutorViewModel = new ViewModelProvider(this).get(HomeTutorViewModel.class);
        homeTutorViewModel.initial(getActivity().getIntent().getStringExtra("EMAIL"));

        root = inflater.inflate(R.layout.fragment_home_tutor, container, false);

        helloTv = root.findViewById(R.id.homeTutor_hello_tv);
        thisWeek = root.findViewById(R.id.homeTutor_thisWeakNumber_tv);
        remain = root.findViewById(R.id.homeTutor_remainNumber_tv);
        total = root.findViewById(R.id.homeTutor_totalNumber_tv);
        nextLessonSubject = root.findViewById(R.id.homeTutor_subject_tv);
        nextLessonStudent = root.findViewById(R.id.homeTutor_student_tv);
        nextLessonDate = root.findViewById(R.id.homeTutor_date_tv);
        nextLessonSubjectImg = root.findViewById(R.id.homeTutor_subject_img);
        calendarLinearLayout = root.findViewById(R.id.homeTutor_calendar_ll);
        swipeUp = root.findViewById(R.id.homeTutor_swipeUp);

        setCalendar();

        homeTutorViewModel.getTutor().observe(getViewLifecycleOwner(), tutor -> {
            if(tutor != null){
                helloTv.setText("hello, " + tutor.getFirstName()+" " +tutor.getLastName());
            }
        });

        homeTutorViewModel.getLessonsByTutor().observe(getViewLifecycleOwner(), new Observer<List<Lesson>>() {
            private LiveData<Student> student = null;

            @Override
            public void onChanged(List<Lesson> lessons) {
                if(student!=null){student.removeObservers(getViewLifecycleOwner());}

                thisWeek.setText(String.valueOf(Utilities.getThisWeekLessons(lessons).size()));
                remain.setText(String.valueOf(Utilities.getRemainLessons(lessons).size()));
                total.setText(String.valueOf(lessons.size()));

                Lesson nextLesson = Utilities.getNextLesson(lessons);
                if(nextLesson == null){
                    nextLessonSubject.setText("");
                    nextLessonDate.setText("");
                    nextLessonStudent.setText("");
                    nextLessonSubjectImg.setImageResource(R.drawable.ic_baseline_block_24);
                }
                else {
                    nextLessonSubject.setText(nextLesson.getSubject().toString().replace("_", " ").toLowerCase());
                    nextLessonDate.setText(nextLesson.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm")));
                    student = homeTutorViewModel.getStudent(nextLesson.getStudentEmail());
                    student.observe(getViewLifecycleOwner(), s -> {
                        if (s != null)
                            nextLessonStudent.setText(s.getFirstName() + " " + s.getLastName());
                    });

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
                }

                setCalendar();
                setCalendarByLessons(lessons);
                LiveData<List<Event>> eventsLiveData = homeTutorViewModel.getEventsByTutor();
                if(eventsLiveData.getValue()!=null){
                    List<Event> events = eventsLiveData.getValue().stream().filter(e -> e.getTutorEmail().equals(getActivity().getIntent().getStringExtra("EMAIL"))).collect(Collectors.toList());
                    setCalendarByEvents(events);
                }
            }

        });

        homeTutorViewModel.getEventsByTutor().observe(getViewLifecycleOwner(), events ->{
            setCalendar();
            setCalendarByEvents(events);
            LiveData<List<Lesson>> lessonsLiveData = homeTutorViewModel.getLessonsByTutor();
            if(lessonsLiveData.getValue()!=null){
                List<Lesson> lessons = lessonsLiveData.getValue().stream().filter(e -> e.getTutorEmail().equals(getActivity().getIntent().getStringExtra("EMAIL"))).collect(Collectors.toList());
                setCalendarByLessons(lessons);
            }
        });

        Model.getInstance().tutorLoadingState.observe(getViewLifecycleOwner(), state-> handleLoading());
        Model.getInstance().lessonLoadingState.observe(getViewLifecycleOwner(), state-> handleLoading());
        Model.getInstance().eventLoadingState.observe(getViewLifecycleOwner(), state-> handleLoading());
        Model.getInstance().studentLoadingState.observe(getViewLifecycleOwner(), state-> handleLoading());

        swipeUp.setOnRefreshListener(()->{
            Model.getInstance().refreshTutors();
            Model.getInstance().refreshEvents();
            Model.getInstance().refreshLessons();
            Model.getInstance().refreshStudents();
        });

        return root;
    }

    public void enableCalendar(boolean state){
        for(int i=8;i<=20;i++){
            LinearLayout hourRow = (LinearLayout)calendarLinearLayout.getChildAt(i - 8);
            for(int j = 1;j<=7;j++){
                ImageView img = (ImageView)hourRow.getChildAt(j);
                img.setEnabled(state);

            }
        }
    }

    public void setCalendar(){
        for(int i=8;i<=20;i++){
            LinearLayout hourRow = (LinearLayout)calendarLinearLayout.getChildAt(i - 8);
            for(int j = 1;j<=7;j++){
                ImageView img = (ImageView)hourRow.getChildAt(j);
                int hour = i;
                int day = j;


                if((LocalDateTime.now().getDayOfWeek().getValue() % 7) + 1 > day){
                    img.setImageResource(R.drawable.ic_baseline_block_24);
                }
                else if((LocalDateTime.now().getDayOfWeek().getValue() % 7) + 1 == day && LocalDateTime.now().getHour() > hour){
                    img.setImageResource(R.drawable.ic_baseline_block_24);
                }
                else{
                    img.setImageResource(R.drawable.ic_baseline_add_24);
                    img.setOnClickListener(v -> {
                        HomeTutorFragmentDirections.ActionNavHomeTutorToAvailableTutorFragment action = HomeTutorFragmentDirections.actionNavHomeTutorToAvailableTutorFragment(hour, day, false);
                        Navigation.findNavController(root).navigate(action);
                    });
                }

            }
        }
    }

    public void setCalendarByEvents(List<Event> events){
        for(Event event : Utilities.getRemainEvents(events)){
            LocalDateTime date = event.getDate();
            LinearLayout hourRow = (LinearLayout)calendarLinearLayout.getChildAt(date.getHour() - 8);
            ImageView img = (ImageView)hourRow.getChildAt((date.getDayOfWeek().getValue() % 7) + 1);
            img.setImageResource(R.drawable.ic_baseline_info_24);
            img.setOnClickListener(v -> {
                HomeTutorFragmentDirections.ActionNavHomeTutorToAvailableTutorFragment action = HomeTutorFragmentDirections.actionNavHomeTutorToAvailableTutorFragment(date.getHour(), (date.getDayOfWeek().getValue() % 7) + 1, true);
                Navigation.findNavController(root).navigate(action);
            });
        }
    }

    public void setCalendarByLessons(List<Lesson> lessons){
        for(Lesson lesson : Utilities.getRemainLessons(lessons)){
            LocalDateTime date = lesson.getDate();
            LinearLayout hourRow = (LinearLayout)calendarLinearLayout.getChildAt(date.getHour() - 8);
            ImageView img = (ImageView)hourRow.getChildAt((date.getDayOfWeek().getValue() % 7) + 1);
            img.setImageResource(R.drawable.ic_baseline_info_24);
            img.setOnClickListener(v -> {
                HomeTutorFragmentDirections.ActionNavHomeTutorToLessonDetailsTutorFragment action = HomeTutorFragmentDirections.actionNavHomeTutorToLessonDetailsTutorFragment(date.getHour(), (date.getDayOfWeek().getValue() % 7) + 1);
                Navigation.findNavController(root).navigate(action);
            });
        }
    }

    public void handleLoading(){
        if(Model.getInstance().tutorLoadingState.getValue() == Model.LoadingState.loaded && Model.getInstance().lessonLoadingState.getValue() == Model.LoadingState.loaded && Model.getInstance().eventLoadingState.getValue() == Model.LoadingState.loaded && Model.getInstance().studentLoadingState.getValue() == Model.LoadingState.loaded){
            enableCalendar(true);
            swipeUp.setRefreshing(false);
        }
        else{
            enableCalendar(false);
            swipeUp.setRefreshing(true);
        }
    }
}