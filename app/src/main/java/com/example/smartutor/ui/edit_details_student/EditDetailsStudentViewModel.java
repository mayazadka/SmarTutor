package com.example.smartutor.ui.edit_details_student;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EditDetailsStudentViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public EditDetailsStudentViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is edit details fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}