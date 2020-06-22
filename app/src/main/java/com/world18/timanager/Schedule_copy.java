package com.world18.timanager;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Schedule_copy {
    private String date="No";
    private SQLiteDatabase database;
    private int idBackCheck=0;
    private int id=0;
    public Schedule_copy(){
    }
    public boolean addEvent(int position,String ts,String tf,int scheduled){
        ContentValues values = new ContentValues();
        values.put("id_event", position);
        values.put("time_start", ts);
        values.put("time_end", tf);
        values.put("date", date);
        values.put("scheduled", scheduled);
        if(database.insert("program_schedule", null, values)>0){
            return true;
        }else{
            return false;
        }
    }
    public void setUpCreation(String date){
        if(date!="No"){
            this.date=date;
        }
        database = MainActivity.dbhelper.getWritableDatabase();
        Cursor c=database.rawQuery("SELECT * FROM events WHERE date=?",new String[] {this.date});
        try {
            database.delete("program_schedule", "date=?",new String[] {this.date});
            c.moveToFirst();
            createSchedule(c,0,5);
        }
        catch (Exception e){

        }
        c.close();
    }
    //To delete
    String values;
    public void setUpCreation(String date,DBHelper dbh){
        if(date!="No"){
            this.date=date;
        }
        database = dbh.getWritableDatabase();
        Cursor c=database.rawQuery("SELECT * FROM events WHERE date=? ORDER BY priority ASC, time_start DESC, time_end DESC",new String[] {this.date});
        try {
            database.delete("program_schedule", "date=?",new String[] {this.date});
            c.moveToFirst();
            createSchedule(c,0,5);
        }
        catch (Exception e){
           values= e.toString();
        }
        c.close();
    }
    private int moveEvents(int idCurrent,int status,String timeStart,String timeFinish){
        status=0;
        while(true) {
            /*if(id==idBackCheck && status==1){
                status=1;
                break;
            }else if(status==3){
                status=0;
                break;
            }*/
            Cursor ftm = database.rawQuery("SELECT * FROM program_schedule WHERE id='"+idCurrent+"'", null);
            ftm.moveToFirst();
            timeStart = ftm.getString(ftm.getColumnIndex("time_start"));
            Cursor event=database.rawQuery("SELECT * FROM events WHERE id=?",new String[] {ftm.getString(ftm.getColumnIndex("id_event"))});
            event.moveToFirst();
            int duration = Integer.parseInt(event.getString(event.getColumnIndex("duration")));
            String timePossibleFinish = event.getString(event.getColumnIndex("time_end"));
            String actualFinish=  ConvertFunctions.addDurationToDate(timeFinish, duration);
            switch (status) {
                case 0:
                    //ftm - Field to move
                    if((ConvertFunctions.dateToTime(timePossibleFinish)-ConvertFunctions.dateToTime(actualFinish))<0){
                        return 3;
                    }
                    Cursor bef=database.rawQuery("SELECT * FROM program_schedule WHERE time_start BETWEEN '"+timeFinish+"' AND '"+timePossibleFinish+"'",null);
                    if(bef.moveToFirst()){
                        int newIdC=Integer.parseInt(bef.getString(bef.getColumnIndex("id")));
                        status = moveEvents(newIdC, 0,timeFinish,timePossibleFinish);
                    }else{
                        ContentValues cv = new ContentValues();
                        cv.put("time_start",timeFinish); //These Fields should be your String values of actual column names
                        cv.put("time_end",timePossibleFinish);
                        database.update("program_schedule", cv, "id=" + idCurrent, null);
                        return 1;
                    }
                    break;
                case 1:
                    ContentValues cv = new ContentValues();
                    cv.put("time_start",timeFinish); //These Fields should be your String values of actual column names
                    cv.put("time_end",timePossibleFinish);
                    if(database.update("program_schedule", cv, "id=" + idCurrent, null)>0)
                    {return 1;}
                    break;
            }
        }
        //return status;
    }
    private int createSchedule(Cursor c, int position,int status){
        c.moveToPosition(position);
        int count=c.getCount();
        if(position>=c.getCount()){
            return 4;
        }
        id=Integer.parseInt(c.getString(c.getColumnIndex("id")));
        while(true) {
            if(c.isBeforeFirst()){
                break;
            }
            if (status == 5) {
                String timeStart =c.getString(c.getColumnIndex("time_start"));
                int duration = Integer.parseInt(c.getString(c.getColumnIndex("duration")));
                addEvent(id,timeStart,ConvertFunctions.addDurationToDate(timeStart, duration),1);
                //return 1;
            } else {
                if (status == 4) {
                    return 4;
                } else if (status == 0) {
                    String timeStart = c.getString(c.getColumnIndex("time_start"));
                    int duration = Integer.parseInt(c.getString(c.getColumnIndex("duration")));
                    String timeFinish =  ConvertFunctions.addDurationToDate(timeStart, duration);
                    Cursor bef=database.rawQuery("SELECT * FROM program_schedule WHERE time_end BETWEEN '"+timeStart+"' AND '"+timeFinish+"' ORDER BY time_end DESC",null);
                    if(bef.moveToFirst()){
                        timeStart = bef.getString(bef.getColumnIndex("time_end"));
                        int timeF = ConvertFunctions.addDurationToTime(timeStart, duration);
                        if (timeF <= ConvertFunctions.dateToTime(c.getString(c.getColumnIndex("time_end")))) {
                            timeFinish=ConvertFunctions.timeToDate(timeF);
                            bef=database.rawQuery("SELECT * FROM program_schedule WHERE time_start BETWEEN '"+timeStart+"' AND '"+timeFinish+"' ORDER BY time_end DESC",null);
                            if(bef.moveToFirst()) {
                                this.idBackCheck=Integer.parseInt(bef.getString(bef.getColumnIndex("id")));
                                int stat = moveEvents(idBackCheck, 0,timeStart,timeFinish);
                                if(stat==1){
                                    addEvent(id, timeStart, timeFinish,1);
                                }else{
                                    addEvent(id, "00:00", "00:01",0);
                                }
                            }else{
                                addEvent(id, timeStart, timeFinish,1);
                            }
                        } else {
                            this.idBackCheck=Integer.parseInt(bef.getString(bef.getColumnIndex("id")));
                            int stat = moveEvents(idBackCheck, 0,timeStart,timeFinish);
                            if(stat==1){
                                addEvent(id, timeStart, timeFinish,1);
                            }else{
                                addEvent(id, "00:00", "00:02",0);
                            }
                           // return 2;
                        }
                    }else{
                        bef=database.rawQuery("SELECT * FROM program_schedule WHERE time_start BETWEEN '"+timeStart+"' AND '"+timeFinish+"' ORDER BY time_end DESC",null);
                        if(bef.moveToFirst()) {
                            this.idBackCheck=Integer.parseInt(bef.getString(bef.getColumnIndex("id")));
                            int stat = moveEvents(idBackCheck, 0,timeStart,timeFinish);
                            if(stat==1){
                                addEvent(id, timeStart, timeFinish,1);
                            }else{
                                addEvent(id, "00:00", "00:03",0);
                            }
                        }else{
                            addEvent(id, timeStart, timeFinish,1);
                        }
                    }
                }
            }
            status = createSchedule(c, position + 1, 0);
        }
        return status;
    }
}
