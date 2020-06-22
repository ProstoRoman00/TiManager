package com.world18.timanager.ui.mytimetable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyTimetableModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyTimetableModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}