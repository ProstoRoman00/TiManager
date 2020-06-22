package com.world18.timanager;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConvertFunctionsTest {
    @Test
    public void dateToTime() {
        String date="03:25";
        int result = ConvertFunctions.dateToTime(date);
        assertEquals(205, result);
    }
    @Test
    public void timeToDate() {
        int time=952;
        String result = ConvertFunctions.timeToDate(time);
        assertEquals("15:52", result);
    }
    @Test
    public void addDurationToTime() {
        int time=35;
        String date="2:30";
        String result = ConvertFunctions.addDurationToDate(date,time);
        assertEquals("3:05", result);
    }
}