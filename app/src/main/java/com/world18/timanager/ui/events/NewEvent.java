package com.world18.timanager.ui.events;

import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import android.widget.TimePicker;

import com.world18.timanager.MainActivity;
import com.world18.timanager.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NewEvent extends Fragment{

    private NewEventModel newEventModel;
    private View add_event;
    private long add_event() throws Exception {
        /*VARS LIST*/
        String name,date,priority;
        int max_duration,start_h,start_m,finish_h,finish_m;
        EditText f_name,f_duration;
        TextView f_date,f_startTime,f_finishTime;
        /*VARS LIST END*/
        f_name = add_event.findViewById(R.id.ev_label_Name);
        name = f_name.getText().toString();
        if (name.length()<4 || name.length()>20) {
            throw new Exception(getString(R.string.event_u_name));
        }
        f_date=add_event.findViewById(R.id.chosenDate);
        date=f_date.getText().toString();
        f_startTime=add_event.findViewById(R.id.startTime);
        f_finishTime=add_event.findViewById(R.id.finishTime);
        start_h = Integer.parseInt(f_startTime.getText().toString().split(":")[0]);
        start_m = Integer.parseInt(f_startTime.getText().toString().split(":")[1]);
        finish_h = Integer.parseInt(f_finishTime.getText().toString().split(":")[0]);
        finish_m = Integer.parseInt(f_finishTime.getText().toString().split(":")[1]);
        max_duration=finish_h-start_h;
        if(max_duration<0){
            throw new Exception(getString(R.string.event_u_finish));
        }else if(max_duration==0){
            max_duration=finish_m-start_m;
            if(max_duration<=0){
                throw new Exception(getString(R.string.event_u_finish));
            }
        }else{
            if((finish_m-start_m)<0){
                max_duration-=1;
                finish_m+=60;
            }
            if(max_duration>0){
                max_duration=max_duration*60+(finish_m-start_m);
            }else{
                max_duration=finish_m-start_m;
            }
        }
        f_duration=add_event.findViewById(R.id.ev_label_duration);
        int duration = Integer.parseInt(f_duration.getText().toString());
        if(max_duration<duration){
            throw new Exception(getString(R.string.event_u_duration));
        }
        Spinner prioritys = add_event.findViewById(R.id.spinnerPriority);
        int valuePriority = prioritys.getSelectedItemPosition();
        SQLiteDatabase db = MainActivity.dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", name);
        values.put("time_start", f_startTime.getText().toString());
        values.put("time_end", f_finishTime.getText().toString());
        values.put("duration",duration);
        values.put("date",date);
        values.put("priority",valuePriority);
        long newRowId = db.insert("events", null, values);
        return newRowId;
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        newEventModel =
                ViewModelProviders.of(this).get(NewEventModel.class);
        View root = inflater.inflate(R.layout.new_event, container, false);
        Button setDay=(Button) root.findViewById(R.id.newEventDate);
        Calendar c= Calendar.getInstance();
        TextView textView=(TextView)root.findViewById(R.id.chosenDate);
        add_event=root;
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        textView.setText(formatter.format(c.getTime()));
        setDay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new com.world18.timanager.DatePicker();
                datePicker.show(getActivity().getSupportFragmentManager(),"Date Picker");
            }
        });
        Button setStartTime = (Button) root.findViewById(R.id.startTimeButton);
        setStartTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                TimePickerDialog picker = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                TextView startTime = add_event.findViewById(R.id.startTime);
                                if(sMinute<10){
                                    startTime.setText(sHour + ":0" + sMinute);
                                }else{
                                    startTime.setText(sHour + ":" + sMinute);
                                }
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });
        Button setFinishTime = (Button) root.findViewById(R.id.finishTimeButton);
        setFinishTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                TimePickerDialog picker = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                TextView startTime = add_event.findViewById(R.id.finishTime);
                                if(sMinute<10){
                                    startTime.setText(sHour + ":0" + sMinute);
                                }else{
                                    startTime.setText(sHour + ":" + sMinute);
                                }
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });
        Button addEvent = (Button) root.findViewById(R.id.addEvent);
        addEvent.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                TextView f_message = add_event.findViewById(R.id.f_message);
                try {
                    long id = add_event();
                    f_message.setText((getString(R.string.event_u_created)).replace("{number}",Long.toString(id)));
                }
                catch(NumberFormatException e){
                    f_message.setText(getString(R.string.event_u_duration_ne));
                }
                catch(Exception e){
                    f_message.setText(e.getMessage());
                }
            }
        });
        return root;
    }
}
