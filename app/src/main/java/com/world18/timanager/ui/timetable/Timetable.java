package com.world18.timanager.ui.timetable;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.world18.timanager.R;

public class Timetable extends Fragment {

    private TimetableModel timetableModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        timetableModel =
                ViewModelProviders.of(this).get(TimetableModel.class);
        View root = inflater.inflate(R.layout.timetable, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        timetableModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
