package com.world18.timanager.ui.timetable;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.world18.timanager.MainActivity;
import com.world18.timanager.Schedule;

public class TimetableModel extends ViewModel {
    private Schedule create;
    private MutableLiveData<String> mText;
    public TimetableModel() {
        SQLiteDatabase database = MainActivity.dbhelper.getWritableDatabase();
        Cursor c = database.rawQuery("SELECT * FROM events", null);
        create= new Schedule();
        String entries="";
        while(c.moveToNext()){
            entries+=c.getString(c.getColumnIndex("id"))+"="+c.getString(c.getColumnIndex("title"))+"\n";
        }
        mText = new MutableLiveData<>();

        mText.setValue(entries);
    }

    public LiveData<String> getText() {
        return mText;
    }
}