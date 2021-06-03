package com.example.smartutor.ui.search_tutors_student;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SearchTutorsStudentViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SearchTutorsStudentViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is search tutor fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}