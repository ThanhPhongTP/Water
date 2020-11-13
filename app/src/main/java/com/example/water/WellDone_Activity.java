package com.example.water;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class WellDone_Activity extends AppCompatActivity {
    Button btnDone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_well_done);
        getSupportActionBar().hide();
        setControl();
        setEvent();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
    private void setEvent() {
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void setControl() {
        btnDone = findViewById(R.id.btnDone);
    }

}
