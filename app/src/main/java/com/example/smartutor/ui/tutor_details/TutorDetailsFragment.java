package com.example.smartutor.ui.tutor_details;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartutor.R;
import com.example.smartutor.Utilities;
import com.example.smartutor.model.Lesson;
import com.example.smartutor.model.Profession;
import com.example.smartutor.model.Tutor;
import com.example.smartutor.ui.home_student.HomeStudentFragmentDirections;
import com.example.smartutor.ui.home_tutor.HomeTutorFragmentDirections;
import com.example.smartutor.ui.schedule_lesson_student.ScheduleLessonStudentFragmentDirections;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@RequiresApi(api = Build.VERSION_CODES.O)
public class TutorDetailsFragment extends Fragment {

    private TutorDetailsViewModel tutorDetailsViewModel;

    private TextView emailTv;
    private TextView ageTv;
    private ImageView genderImg;
    private LinearLayout subjectsLL;
    private TextView aboutMeTv;
    private LinearLayout calendarLinearLayout;

    public TutorDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        tutorDetailsViewModel = new ViewModelProvider(this).get(TutorDetailsViewModel.class);
        tutorDetailsViewModel.initial(TutorDetailsFragmentArgs.fromBundle(getArguments()).getEmail());

        View view = inflater.inflate(R.layout.fragment_tutor_details, container, false);

        emailTv = view.findViewById(R.id.tutorDetails_email_tv);
        ageTv = view.findViewById(R.id.tutorDetails_age_tv);
        genderImg = view.findViewById(R.id.tutorDetails_gender_img);
        subjectsLL = view.findViewById(R.id.tutorDetails_subjects_linearLayout);
        aboutMeTv = view.findViewById(R.id.tutorDetails_aboutMe_tv);
        calendarLinearLayout = view.findViewById(R.id.tutorDetails_calendar_ll);

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
                        TutorDetailsFragmentDirections.ActionNavTutorDetailsStudentToScheduleLessonStudentFragment action = TutorDetailsFragmentDirections.actionNavTutorDetailsStudentToScheduleLessonStudentFragment(hour, day);
                        action.setEmail(TutorDetailsFragmentArgs.fromBundle(getArguments()).getEmail());
                        Navigation.findNavController(view).navigate(action);
                    });
                }
            }
        }

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

        tutorDetailsViewModel.getLessons().observe(getViewLifecycleOwner(), lessons -> {
            for(Lesson lesson : Utilities.getRemainLessons(lessons)){
                LocalDateTime date = lesson.getDate();
                LinearLayout hourRow = (LinearLayout)calendarLinearLayout.getChildAt(date.getHour() - 8);
                ImageView img = (ImageView)hourRow.getChildAt((date.getDayOfWeek().getValue() % 7) + 1);
                img.setImageResource(R.drawable.ic_baseline_block_24);
                img.setOnClickListener(null);
            }
        });
        return view;
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