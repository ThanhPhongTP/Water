package com.example.water.SharedPreferences_Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SharedPreferences_Utils {
    private static SharedPreferences sharedPreferences;
    private final String MyPREFERENCES="cups";
    String scup = "cups";
    private String saveDate="saveDate";

    public SharedPreferences_Utils(Context context){
        sharedPreferences  = context.getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE);
    }

    public void setWaterCounter(String saveWater){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(scup, saveWater);
        editor.commit();
    }

    public String getWaterCounter(){
        String counter = sharedPreferences.getString(scup, 0 + "");
        return counter;
    }
    public void updateDataPassOneDay(TextView textView, ProgressBar progressBar) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String date = simpleDateFormat.format(calendar.getTime());
        String sDate = sharedPreferences.getString(saveDate, date);
        if (sharedPreferences.contains(saveDate)) {
            if(!date.equals(sDate)){
                editor.remove(scup);
                textView.setText("0");
                progressBar.setProgress(0);
            }
        }
        editor.putString(saveDate, date);
        editor.commit();

    }
}
