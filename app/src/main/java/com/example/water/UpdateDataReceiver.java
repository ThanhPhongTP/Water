package com.example.water;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateDataReceiver extends BroadcastReceiver {
    SharedPreferences sharedPreferences;
    String scup = "cups";
    String s = "";
    TextView tvCup;



    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "succsec", Toast.LENGTH_SHORT).show();

        sharedPreferences = context.getSharedPreferences("cups", Context.MODE_PRIVATE);
        s = sharedPreferences.getString(scup, "");

        Toast.makeText(context, s + "" , Toast.LENGTH_SHORT).show();

//        s = sharedPreferences.getString(scup, "");
        if (Integer.parseInt(s) == 8) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(scup, 0 + "");
                    editor.commit();
                }



    }
}
