package com.example.water;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.water.SharedPreferences_Utils.SharedPreferences_Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Button btnDrink;
    ProgressBar progressBar;
    TextView tvCup;
    int ndem = 0;
    SharedPreferences sharedPreferences;
    String scup = "cups";
    String s = "";
    Calendar calendar;
    String saveDate = "saveDate";
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    SharedPreferences_Utils sharedPreferences_utils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        setControl();
        setEventWater();
        createNotificationWater();
        sharedPreferences_utils.updateDataPassOneDay(tvCup,progressBar);
    }

    private void timeSetCallNotification() {
        Intent intent = new Intent(this, NotificationReceiverWater.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long timeCurrent = System.currentTimeMillis();
//        long setTime = 1000 * 60 * 90;
        long setTime = 1000 * 1;
        alarmManager.set(AlarmManager.RTC_WAKEUP, timeCurrent + setTime, pendingIntent);

    }



//    private void updateDataPassOneDay(TextView textView, ProgressBar progressBar) {
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        Calendar calendar = Calendar.getInstance();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
//        String date = simpleDateFormat.format(calendar.getTime());
//        String sDate = sharedPreferences.getString(saveDate, date);
//        if (sharedPreferences.contains(saveDate)) {
//            if(!date.equals(sDate)){
//                editor.remove(scup);
//                textView.setText("0");
//                progressBar.setProgress(0);
//            }
//        }
//        editor.putString(saveDate, date);
//        editor.commit();
//
//    }

    private void setEventWater() {
        progressBar.setMax(8);
        s = sharedPreferences_utils.getWaterCounter();
        tvCup.setText(s);
        progressBar.setProgress(Integer.parseInt(s));

        btnDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(s) < 8) {
                    ndem = Integer.parseInt(s) + 1;
                    tvCup.setText(String.valueOf(ndem));
                    progressBar.setProgress(ndem);
                   sharedPreferences_utils.setWaterCounter(String.valueOf(ndem));

                    Intent intent = new Intent(getApplication(), Water_Tracker_Activity.class);
                    intent.putExtra("cup",ndem);
                    startActivity(intent);
                    finish();
                    // notification
                    timeSetCallNotification();
//                    updateProgress();
                } else {
                    Toast.makeText(getApplication(), R.string.notificaion, Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });
    }
//    private void saveCounter (String name){
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(scup, name);
//        editor.commit();
//    }
//    private String getCounter(){
//        String counter = sharedPreferences.getString(scup, 0 + "");
//        return counter;
//    }
    private void createNotificationWater() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("id_cup", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void setControl() {
        sharedPreferences_utils = new SharedPreferences_Utils(this);
//        sharedPreferences = getSharedPreferences(scup, Context.MODE_PRIVATE);

        btnDrink = findViewById(R.id.btnDrink);
        tvCup = findViewById(R.id.txtcup);
        progressBar = findViewById(R.id.progress_bar);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//        timePicker = findViewById(R.id.timepicker);
    }
}
