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
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
    TimePicker timePicker;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        setControl();
        setEvent();
        updateProgress();
        createNotification();
        saveDay();
    }

    private void timeSetCallNotification() {
        Intent intent = new Intent(this, NotificationReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long timeCurrent = System.currentTimeMillis();
        long setTime = 1000 * 60 * 90;
        alarmManager.set(AlarmManager.RTC_WAKEUP, timeCurrent + setTime, pendingIntent);
    }

    //Lay ngay he thong luu va so sanh
    private void saveDay() {
        Intent intent = new Intent(this, UpdateDataReceiver.class);
        calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
//        String date = simpleDateFormat.format(calendar.getTime());
        int minute = calendar.getTime().getMinutes();
        int hour = calendar.getTime().getHours();
        int second = calendar.getTime().getSeconds();
        String time = simpleDateFormat.format(calendar.getTime());
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        if(hour == 23 && minute == 59 && second == 59){
            btnDrink.setEnabled(true);
            Toast.makeText(this, time + "", Toast.LENGTH_SHORT).show();
        }
    }




    private void setEvent() {
        s = sharedPreferences.getString(scup, "");
        tvCup.setText(s);
        if (!sharedPreferences.contains("cups")) {
            tvCup.setText("0");
        }
        if(Integer.parseInt(s) == 8)
            btnDrink.setEnabled(false);
        btnDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ndem < 8) {
                    s = sharedPreferences.getString(scup, "");
                    if (sharedPreferences.contains("cups") && Integer.parseInt(s) < 8) {
                        ndem = Integer.parseInt(s) + 1;
                    } else
                        ndem += 1;
                    tvCup.setText(ndem + "");
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(scup, ndem + "");
                    editor.commit();

                    if (ndem <= 7)
                        timeSetCallNotification();
                } else if (ndem >= 8) {
//                    Toast.makeText(getApplication(), "Bạn đã uống đủ số nước", Toast.LENGTH_SHORT).show();


                    return;
                }
                Intent intent = new Intent(getApplication(), Water_tracker.class);
                Bundle bundle = new Bundle();
                bundle.putInt("cup", ndem);
                intent.putExtras(bundle);
                startActivity(intent);
                updateProgress();
                MainActivity.super.onPause();
                finish();
            }
        });
    }

    //
    private void updateProgress() {
        progressBar.setMax(8);
        s = sharedPreferences.getString(scup, "");
        if (!sharedPreferences.contains("cups"))
            progressBar.setProgress(ndem);
        else
            progressBar.setProgress(Integer.parseInt((s)));
        if (tvCup.getText() == "0"){
            progressBar.setProgress(0);
        }
    }

    //
    private void createNotification() {
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
        sharedPreferences = getSharedPreferences("cups", Context.MODE_PRIVATE);
        btnDrink = findViewById(R.id.btnDrink);
        tvCup = findViewById(R.id.txtcup);
        progressBar = findViewById(R.id.progress_bar);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//        timePicker = findViewById(R.id.timepicker);
    }
}
