package com.world18.timanager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME="MiHalper";

    public static final String TABLE_EVENTS="events";
    public static final String TABLE_DAY_PLAN="day_plan";
    public static final String TABLE_MY_SCHEDULE="my_schedule";
    public static final String TABLE_PROGRAM_SCHEDULE="program_schedule";

    public DBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE `events` (\n" +
                "  `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "  `title` TEXT NOT NULL,\n" +
                "  `time_start` TEXT NOT NULL,\n" +
                "  `time_end` TEXT NOT NULL,\n" +
                "  `duration` INTEGER NOT NULL,\n" +
                "  `date` TEXT NOT NULL,\n" +
                "  `priority` INTEGER NOT NULL DEFAULT '5'\n" +
                ")");
        db.execSQL("CREATE TABLE `my_schedule` (\n" +
                "  `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "  `id_event` INTEGER NOT NULL,\n" +
                "  `time_start` TEXT NOT NULL,\n" +
                "  `time_end` TEXT NOT NULL,\n" +
                "  `date` TEXT NOT NULL\n" +
                ")");
        db.execSQL("CREATE TABLE `program_schedule` (\n" +
                "  `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "  `id_event` INTEGER NOT NULL,\n" +
                "  `time_start` TEXT NOT NULL,\n" +
                "  `time_end` TEXT NOT NULL,\n" +
                "  `date` TEXT NOT NULL,\n" +
                "  `scheduled` INTEGERL NOT NULL DEFAULT '1'\n" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
