package com.example.smartutor.ui.available_tutor;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.smartutor.R;
import com.example.smartutor.model.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AvailableTutorFragment extends Fragment {

    private AvailableTutorViewModel availableTutorViewModel;
    private TextView date;
    private TextView hour;
    private Button set;
    private LocalDateTime dateTime;
    private SwipeRefreshLayout swipeUp;

    public AvailableTutorFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LocalDateTime now = LocalDateTime.now();
        availableTutorViewModel = new ViewModelProvider(this).get(AvailableTutorViewModel.class);
        dateTime = LocalDate.now().atTime(AvailableTutorFragmentArgs.fromBundle(getArguments()).getHour(), 0).plusDays(AvailableTutorFragmentArgs.fromBundle(getArguments()).getDay() - ((now.getDayOfWeek().getValue() % 7) +1));
        availableTutorViewModel.initial(dateTime);
        View root = inflater.inflate(R.layout.fragment_available_tutor, container, false);

        date = root.findViewById(R.id.availableTutor_date_tv);
        hour = root.findViewById(R.id.availableTutor_hour_tv);
        set = root.findViewById(R.id.availableTutor_set_btn);
        swipeUp = root.findViewById(R.id.availableTutor_swipeUp);

        date.setText(dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        hour.setText(dateTime.format(DateTimeFormatter.ofPattern("HH:mm")));

        if(!AvailableTutorFragmentArgs.fromBundle(getArguments()).getAvailable()){
            set.setText("Set Unavailable");
            set.setOnClickListener(v -> {
                v.setEnabled(false);
                availableTutorViewModel.addEvent(dateTime, ()->Navigation.findNavController(root).navigate(R.id.action_global_nav_home_tutor));
            });
        }
        else{
            set.setText("Set Available");
            set.setOnClickListener(v -> {
                v.setEnabled(false);
                availableTutorViewModel.deleteEvent(()->Navigation.findNavController(root).navigate(R.id.action_global_nav_home_tutor));
            });
        }

        Model.getInstance().eventLoadingState.observe(getViewLifecycleOwner(), state->{
            if(state == Model.LoadingState.loaded){
                set.setEnabled(true);
                swipeUp.setRefreshing(false);
            }
            else{
                set.setEnabled(false);
                swipeUp.setRefreshing(true);
            }
        });

        swipeUp.setOnRefreshListener(()->{
            Model.getInstance().refreshEvents();
        });

        return root;
    }
}