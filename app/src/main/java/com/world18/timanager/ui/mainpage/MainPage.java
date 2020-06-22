package com.world18.timanager.ui.mainpage;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.world18.timanager.R;

public class MainPage extends Fragment {

    private MainPageModel myTimetableModel;
    public void createAlert(){
        Context c = getContext();
        new AlertDialog.Builder(c)
                .setTitle(getString(R.string.program_exit_title))
                .setMessage(getString(R.string.program_exit_about))
                .setPositiveButton(getString(R.string.program_exit), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                exitProgram();
            }
        })
    .setNegativeButton(getString(R.string.program_exit_cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // do nothing
            }
        })
    .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    public void exitProgram(){
        System.exit(0);
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myTimetableModel =
                ViewModelProviders.of(this).get(MainPageModel.class);
        View root = inflater.inflate(R.layout.main_page, container, false);
       // final TextView textView = root.findViewById(R.id.text_home);
        myTimetableModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        Button exitButton =(Button)root.findViewById(R.id.button_ex);
        exitButton.setOnClickListener((v)->{createAlert();});
        return root;
    }
}
