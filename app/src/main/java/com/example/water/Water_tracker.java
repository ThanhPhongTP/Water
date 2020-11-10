package com.example.water;

import androidx.annotation.Dimension;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.wave.MultiWaveHeader;

public class Water_tracker extends AppCompatActivity {

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
        //get du lieu
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int value = 0;
        if (bundle != null) {
            value = bundle.getInt("cup", 0);
        }
        Toast.makeText(this, value + "", Toast.LENGTH_SHORT).show();
        tvCup.setText(value + "");
        //set percent
        switch (value){
            case 1:
                tvPervcent.setText("12.5%");
                break;
            case 2:
                tvPervcent.setText("25%");
                break;
            case 3:
                tvPervcent.setText("37.5%");
                break;
            case 4:
                tvPervcent.setText("50%");
                break;
            case 5:
                tvPervcent.setText("62.5%");
                break;
            case 6:
                tvPervcent.setText("75%");
                break;
            case 7:
                tvPervcent.setText("87.5%");
                break;
            default:
                tvPervcent.setText("100%");

        }

    }

    //Chuyen man hinh sau 3s
    private void sreenSwitch(){
        Thread bamgio = new Thread() {
            public void run() {
                try {
                    sleep(5000);
                } catch (Exception e) {
                } finally {
                    Intent intent1 = new Intent(getApplication(), WellDone.class);
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
//        waveview.setStartColor(Color.GREEN);
//        waveview.setCloseColor(Color.YELLOW);
        Display display = getWindowManager().getDefaultDisplay();
        int height = display.getHeight();// lay kt man hinh
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
