package com.world18.timanager;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.filters.SmallTest;
import androidx.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class DataBaseTest {
    private DBHelper dbhelper;
    private SQLiteDatabase db;
    private String date;
    public void add_entris(){
        ContentValues values = new ContentValues();
        values.put("title", "check1");
        values.put("time_start", "8:00");
        values.put("time_end", "8:59");
        values.put("duration",50);
        values.put("date","15.06.2020");
        values.put("priority",1);
        long newRowId = db.insert("events", null, values);
        values = new ContentValues();
        values.put("title", "check2");
        values.put("time_start", "8:00");
        values.put("time_end", "10:00");
        values.put("duration",55);
        values.put("date","15.06.2020");
        values.put("priority",1);
        newRowId = db.insert("events", null, values);
        values = new ContentValues();
        values.put("title", "check3");
        values.put("time_start", "13:00");
        values.put("time_end", "15:00");
        values.put("duration",115);
        values.put("date","15.06.2020");
        values.put("priority",3);
        newRowId = db.insert("events", null, values);
        values = new ContentValues();
        values.put("title", "check4");
        values.put("time_start", "11:00");
        values.put("time_end", "14:00");
        values.put("duration",115);
        values.put("date","15.06.2020");
        values.put("priority",4);
        newRowId = db.insert("events", null, values);
        values = new ContentValues();
        values.put("title", "check5");
        values.put("time_start", "14:00");
        values.put("time_end", "17:00");
        values.put("duration",115);
        values.put("date","15.06.2020");
        values.put("priority",2);
        newRowId = db.insert("events", null, values);
        values = new ContentValues();
        values.put("title", "check6");
        values.put("time_start", "13:00");
        values.put("time_end", "22:00");
        values.put("duration",30);
        values.put("date","15.06.2020");
        values.put("priority",5);
        newRowId = db.insert("events", null, values);
        /*values = new ContentValues();
        values.put("title", "check7");
        values.put("time_start", "13:00");
        values.put("time_end", "24:00");
        values.put("duration",5);
        values.put("date","15.06.2020");
        values.put("priority",5);
        newRowId = db.insert("events", null, values);*/
    }
    @Before
    public void initDatabase(){
        dbhelper = new DBHelper(ApplicationProvider.getApplicationContext());
        db = dbhelper.getWritableDatabase();
        add_entris();
        date="15.06.2020";
    }
    @Test
    public void checkEntries(){
        db = dbhelper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM events", null);
        String entries="";
        while(c.moveToNext()){
            entries+=c.getString(c.getColumnIndex("id"))+"="+c.getString(c.getColumnIndex("title"))+"\n";
        }
        assertEquals("1=check1\n2=check2\n3=check3\n4=check4\n5=check5\n", entries);
    }
    @Test
    public void checkCreated(){
        Schedule sc = new Schedule();
        sc.setUpCreation(date,dbhelper);
        db = dbhelper.getWritableDatabase();
        Cursor c=db.rawQuery("SELECT * FROM program_schedule WHERE date=?",new String[] {this.date});
        String entries="";
        while(c.moveToNext()){
            entries+=c.getString(c.getColumnIndex("id"))+"="+c.getString(c.getColumnIndex("id_event"));
            entries+="="+c.getString(c.getColumnIndex("time_start"))+"="+c.getString(c.getColumnIndex("time_end"))+"\n";
        }
        assertEquals("1=2", entries);
    }
    public void checking(){
        db = dbhelper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM events", null);
        String entries="";
        c.move(0);
        entries+=c.getString(c.getColumnIndex("id"))+"="+c.getString(c.getColumnIndex("title"))+"\n";
        assertEquals("1=check1\n2=check2\n3=check3\n4=check4\n5=check5\n", entries);
    }
    @After
    public void closeDb(){
        db.close();
    }
}
