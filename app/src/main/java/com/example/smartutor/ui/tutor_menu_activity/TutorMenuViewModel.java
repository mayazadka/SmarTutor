package com.example.smartutor.ui.tutor_menu_activity;

import androidx.lifecycle.ViewModel;
import com.example.smartutor.model.Model;

public class TutorMenuViewModel extends ViewModel {
    private Model model = Model.getInstance();

    public TutorMenuViewModel() {}
    public void signOut()       { model.signOut(); }
}
