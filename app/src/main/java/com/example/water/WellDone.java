package com.example.water;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WellDone extends AppCompatActivity {
    Button btnDone;
    SharedPreferences sharedPreferences;
    String scup = "cups";
    String s = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_well_done);
        getSupportActionBar().hide();
        setControl();
        setEvent();
    }

    protected void onPause(){
        super.onPause();
        finish();
    }

    private void setEvent() {
        s = sharedPreferences.getString(scup, "");
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(s) == 8) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(scup, 0 + "");
                    editor.commit();
                }
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setControl() {
        btnDone = findViewById(R.id.btnDone);
        sharedPreferences = getSharedPreferences("cups", Context.MODE_PRIVATE);
    }

}
