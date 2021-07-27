package com.example.smartutor.ui.student_menu_activity;

import androidx.lifecycle.ViewModel;
import com.example.smartutor.model.Model;

public class StudentMenuViewModel extends ViewModel {
    private Model model = Model.getInstance();

    public StudentMenuViewModel() {}
    public void signOut() {model.signOut();}
}
