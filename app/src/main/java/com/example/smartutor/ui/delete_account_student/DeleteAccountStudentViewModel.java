package com.example.smartutor.ui.delete_account_student;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DeleteAccountStudentViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DeleteAccountStudentViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is delete account fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}
