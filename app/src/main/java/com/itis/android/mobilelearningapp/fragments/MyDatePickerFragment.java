package com.itis.android.mobilelearningapp.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;

import com.itis.android.mobilelearningapp.R;

import java.util.Calendar;

public class MyDatePickerFragment extends DialogFragment {

    public static MyDatePickerFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt("viewId", id);
        MyDatePickerFragment fragment = new MyDatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, dateSetListener, year, month, day);
    }

    @SuppressLint("SetTextI18n")
    private DatePickerDialog.OnDateSetListener dateSetListener =
            (view, year, month, day) -> {
                int id = getArguments().getInt("viewId");
                EditText edt = getActivity().findViewById(id);
                edt.setText(view.getYear() +
                        "/" + (view.getMonth() + 1) +
                        "/" + view.getDayOfMonth());
            };
}
