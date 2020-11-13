package com.example.water;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.scwang.wave.MultiWaveHeader;

public class Water_Tracker_Activity extends AppCompatActivity {

    TextView tvCup, tvPervcent;
    MultiWaveHeader waveview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_tracker);
        getSupportActionBar().hide();
        setControl();
        setEvent();
        sreenSwitch();
        setWaveview();
    }

    @SuppressLint("ResourceAsColor")
    private void setEvent() {
        int value = 0;
        Intent intent = getIntent();
        if (intent != null) {
            value = intent.getIntExtra("cup", 0);
        }
        tvCup.setText(value + "");
        //set percent
        String input ="";
        switch (value){
            case 1:
                input = "12.5%";
                break;
            case 2:
                input = "25%";
                break;
            case 3:
                input = "37.5%";
                break;
            case 4:
                input = "50%";
                break;
            case 5:
                input = "62.5%";
                break;
            case 6:
                input = "75%";
                break;
            case 7:
                input = "87.5%";
                break;
            default:
                input = "100%";

        }
        tvPervcent.setText(input);
    }

    //Chuyen man hinh sau 3s
    private void sreenSwitch(){
        Thread bamgio = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (Exception e) {
                } finally {
                    Intent intent1 = new Intent(getApplication(), WellDone_Activity.class);

                    startActivity(intent1);
                }
            }
        };
        bamgio.start();
    }

    protected void onPause(){
        super.onPause();
        finish();
    }
    private void setWaveview(){
        Display display = getWindowManager().getDefaultDisplay();
        int height = display.getHeight();// lay kt man hinh
        //set animation
        Animation animation = new TranslateAnimation(0,0,height / 8 - 1,0);
        animation.setDuration(2000);
        animation.setFillAfter(true);
        waveview.startAnimation(animation);
//        waveview.setVisibility(0);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int value = 0;
        if (bundle != null) {
            value = bundle.getInt("cup", 0);
        }
        waveview.setVelocity(1);
        waveview.setProgress(1);
        waveview.setGradientAngle(45);
        waveview.setWaveHeight(20);
        waveview.setStartColor(Color.CYAN);
        waveview.setCloseColor(Color.YELLOW);
        ViewGroup.LayoutParams multiWaveHeader = waveview.getLayoutParams();
        multiWaveHeader.height = height / 8 * value;
        waveview.setLayoutParams(multiWaveHeader);
    }
    private void setControl() {
        waveview = findViewById(R.id.wave);
        tvPervcent = findViewById(R.id.tvpercent);
        tvCup = findViewById(R.id.tvcup);
    }

}
