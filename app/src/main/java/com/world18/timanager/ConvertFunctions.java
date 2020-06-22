package com.world18.timanager;

public class ConvertFunctions {
    public static int dateToTime(String date){
        int time=0;
        time=(Integer.parseInt(date.split(":")[0])*60)+Integer.parseInt(date.split(":")[1]);
        return time;
    }
    public static String timeToDate(int time){
        int m,h;
        String newDate="";
        h=time/60;
        m=time%60;
        if(m<10){
            newDate=h+":0"+m;
        }else {
            newDate = h + ":" + m;
        }
        return newDate;
    }
    public static String addDurationToDate(String date,int duration){
        String newDate="";
        int timeResult=dateToTime(date)+duration;
        newDate=timeToDate(timeResult);
        return newDate;
    }
    public static int addDurationToTime(String date,int duration){
        int timeResult=dateToTime(date)+duration;
        return timeResult;
    }
}
