package com.world18.timanager;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DatePicker extends DialogFragment  implements DatePickerDialog.OnDateSetListener {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int y =c.get(Calendar.YEAR);
        int m =c.get(Calendar.MONTH);
        int d =c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),this,y,m,d);
        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
        return datePickerDialog;
    }
    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c= Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        String chosenDate = formatter.format(c.getTime());
        TextView textView=(TextView)getActivity().findViewById(R.id.chosenDate);
        textView.setText(chosenDate);
    }
}
