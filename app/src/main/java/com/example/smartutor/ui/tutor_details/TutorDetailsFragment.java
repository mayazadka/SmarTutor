package com.example.smartutor.ui.tutor_details;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartutor.R;
import com.example.smartutor.Utilities;
import com.example.smartutor.model.Event;
import com.example.smartutor.model.Lesson;
import com.example.smartutor.model.Model;
import com.example.smartutor.model.Profession;

import java.time.LocalDateTime;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class TutorDetailsFragment extends Fragment {

    private TutorDetailsViewModel tutorDetailsViewModel;
    private String tutorEmail;

    private TextView emailTv;
    private TextView ageTv;
    private ImageView genderImg;
    private LinearLayout subjectsLL;
    private TextView aboutMeTv;
    private LinearLayout calendarLinearLayout;
    private Button myPostsBtn;
    private View view;
    private SwipeRefreshLayout swipeUp;

    public TutorDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        tutorDetailsViewModel = new ViewModelProvider(this).get(TutorDetailsViewModel.class);
        tutorEmail = TutorDetailsFragmentArgs.fromBundle(getArguments()).getEmail();
        tutorDetailsViewModel.initial(tutorEmail);

        view = inflater.inflate(R.layout.fragment_tutor_details, container, false);

        emailTv = view.findViewById(R.id.tutorDetails_email_tv);
        ageTv = view.findViewById(R.id.tutorDetails_age_tv);
        genderImg = view.findViewById(R.id.tutorDetails_gender_img);
        subjectsLL = view.findViewById(R.id.tutorDetails_subjects_linearLayout);
        aboutMeTv = view.findViewById(R.id.tutorDetails_aboutMe_tv);
        calendarLinearLayout = view.findViewById(R.id.tutorDetails_calendar_ll);
        myPostsBtn = view.findViewById(R.id.tutorDetails_my_posts_btn);
        swipeUp = view.findViewById(R.id.tutorDetails_swipeUp);

        setCalendar();

        tutorDetailsViewModel.getTutor().observe(getViewLifecycleOwner(), tutor -> {
            if(tutor != null) {
                emailTv.setText(tutor.getEmail());
                ageTv.setText(String.valueOf(Utilities.age(tutor.getBirthdayDate())));
            }

            int imageID = 0;
            switch (tutor.getGender()){
                case MALE:
                    imageID = R.drawable.ic_gender_male;
                    break;
                case FEMALE:
                    imageID = R.drawable.ic_gender_female;
                    break;
                case OTHER:
                    imageID = R.drawable.ic_gender_other;
                    break;
            }
            genderImg.setImageResource(imageID);

            subjectsLL.removeAllViews();
            if (tutor.getProfessions().contains(Profession.COMPUTER_SCIENCE)) {
                addImageToLL(view, R.drawable.ic_subject_computer_science);
            }
            if (tutor.getProfessions().contains(Profession.MATH)) {
                addImageToLL(view, R.drawable.ic_subject_math);
            }
            if (tutor.getProfessions().contains(Profession.HISTORY)) {
                addImageToLL(view, R.drawable.ic_subject_history);
            }
            if (tutor.getProfessions().contains(Profession.LANGUAGE)) {
                addImageToLL(view, R.drawable.ic_subject_english);
            }
            if (tutor.getProfessions().contains(Profession.LITERATURE)) {
                addImageToLL(view, R.drawable.ic_subject_literature);
            }
            if (tutor.getProfessions().contains(Profession.SCIENCE)) {
                addImageToLL(view, R.drawable.ic_subject_science);
            }
            aboutMeTv.setText(tutor.getAboutMe());
        });

        tutorDetailsViewModel.getLessonsByTutor().observe(getViewLifecycleOwner(), lessons -> {
            if(lessons != null){
                setCalendar();
                setCalendarByLessons(lessons);
                LiveData<List<Lesson>> listLiveData1 = tutorDetailsViewModel.getLessonsByStudent();
                if(listLiveData1.getValue()!= null){
                    List<Lesson> studentLessons = listLiveData1.getValue();
                    setCalendarByLessons(studentLessons);
                }
                LiveData<List<Event>> listLiveData2 = tutorDetailsViewModel.getEvents();
                if(listLiveData2.getValue()!= null){
                    List<Event> events = listLiveData2.getValue();
                    setCalendarByEvents(events);
                }
            }


        });
        tutorDetailsViewModel.getLessonsByStudent().observe(getViewLifecycleOwner(), lessons -> {
            if(lessons == null){
                setCalendar();
                setCalendarByLessons(lessons);
                LiveData<List<Lesson>> listLiveData = tutorDetailsViewModel.getLessonsByTutor();
                if(listLiveData.getValue()!= null){
                    List<Lesson> tutorLessons = listLiveData.getValue();
                    setCalendarByLessons(tutorLessons);
                }
                LiveData<List<Event>> listLiveData2 = tutorDetailsViewModel.getEvents();
                if(listLiveData2.getValue()!= null){
                    List<Event> events = listLiveData2.getValue();
                    setCalendarByEvents(events);
                }
            }

        });
        tutorDetailsViewModel.getEvents().observe(getViewLifecycleOwner(), events -> {
            if(events!=null){
                setCalendar();
                setCalendarByEvents(events);
                LiveData<List<Lesson>> listLiveData = tutorDetailsViewModel.getLessonsByTutor();
                if(listLiveData.getValue()!= null){
                    List<Lesson> tutorLessons = listLiveData.getValue();
                    setCalendarByLessons(tutorLessons);
                }
                LiveData<List<Lesson>> listLiveData2 = tutorDetailsViewModel.getLessonsByStudent();
                if(listLiveData2.getValue()!= null){
                    List<Lesson> studentLessons = listLiveData2.getValue();
                    setCalendarByLessons(studentLessons);
                }
            }

        });


        myPostsBtn.setOnClickListener(v -> {
            TutorDetailsFragmentDirections.ActionNavTutorDetailsStudentToTutorFeedStudentFragment action = TutorDetailsFragmentDirections.actionNavTutorDetailsStudentToTutorFeedStudentFragment(tutorEmail);
            Navigation.findNavController(view).navigate(action);
        });

        swipeUp.setOnRefreshListener(()->{
            Model.getInstance().refreshTutors();
            Model.getInstance().refreshLessons();
            Model.getInstance().refreshEvents();
        });

        Model.getInstance().tutorLoadingState.observe(getViewLifecycleOwner(), state->handleLoading());
        Model.getInstance().lessonLoadingState.observe(getViewLifecycleOwner(), state->handleLoading());
        Model.getInstance().eventLoadingState.observe(getViewLifecycleOwner(), state -> handleLoading());
        return view;
    }

    private void setCalendar(){
        for(int i=8;i<=20;i++){
            for(int j = 1;j<=7;j++){
                LinearLayout hourRow = (LinearLayout)calendarLinearLayout.getChildAt(i - 8);
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
                    img.setOnClickListener(v -> {
                        img.setImageResource(R.drawable.ic_baseline_add_24);
                        TutorDetailsFragmentDirections.ActionNavTutorDetailsStudentToScheduleLessonStudentFragment action = TutorDetailsFragmentDirections.actionNavTutorDetailsStudentToScheduleLessonStudentFragment(hour, day);
                        action.setEmail(TutorDetailsFragmentArgs.fromBundle(getArguments()).getEmail());
                        Navigation.findNavController(view).navigate(action);
                    });
                }
            }
        }
    }

    private void setCalendarByLessons(List<Lesson> lessons){
        for(Lesson lesson : Utilities.getRemainLessons(lessons)){
            LocalDateTime date = lesson.getDate();
            LinearLayout hourRow = (LinearLayout)calendarLinearLayout.getChildAt(date.getHour() - 8);
            ImageView img = (ImageView)hourRow.getChildAt((date.getDayOfWeek().getValue() % 7) + 1);
            img.setImageResource(R.drawable.ic_baseline_block_24);
            img.setOnClickListener(null);
        }
    }

    private void setCalendarByEvents(List<Event> events){
        for(Event event : Utilities.getRemainEvents(events)){
            LocalDateTime date = event.getDate();
            LinearLayout hourRow = (LinearLayout)calendarLinearLayout.getChildAt(date.getHour() - 8);
            ImageView img = (ImageView)hourRow.getChildAt((date.getDayOfWeek().getValue() % 7) + 1);
            img.setImageResource(R.drawable.ic_baseline_block_24);
            img.setOnClickListener(null);
        }
    }

    private void enableCalendar(boolean state){
        for(int i=8;i<=20;i++){
            LinearLayout hourRow = (LinearLayout)calendarLinearLayout.getChildAt(i - 8);
            for(int j = 1;j<=7;j++){
                ImageView img = (ImageView)hourRow.getChildAt(j);
                img.setEnabled(state);
            }
        }
    }

    private void handleLoading(){
        if(Model.getInstance().tutorLoadingState.getValue()== Model.LoadingState.loaded&&Model.getInstance().lessonLoadingState.getValue()== Model.LoadingState.loaded && Model.getInstance().eventLoadingState.getValue()== Model.LoadingState.loaded){
            enableCalendar(true);
            swipeUp.setRefreshing(false);
        }
        else{
            enableCalendar(false);
            swipeUp.setRefreshing(true);
        }
    }

    private void addImageToLL(View view, int resId){
        ImageView image;
        image = new ImageView(view.getContext());
        image.setImageResource(resId);
        subjectsLL.addView(image);
        image.getLayoutParams().height = 80;
        image.getLayoutParams().width = 80;
    }

}