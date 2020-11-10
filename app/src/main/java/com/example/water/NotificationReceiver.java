package com.example.water;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationReceiver extends BroadcastReceiver {

    SharedPreferences sharedPreferences;
    String s;
    String scup = "cups";

    @Override
    public void onReceive(Context context, Intent intent) {
//        Toast.makeText(context, "abcccc", Toast.LENGTH_SHORT).show();

        sharedPreferences = context.getSharedPreferences("cups", Context.MODE_PRIVATE);
        s = sharedPreferences.getString(scup, "");
        intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "id_cup")
                .setSmallIcon(R.drawable.icon_nav_3)
                .setContentTitle("Water tracker")
                .setContentText("Bạn đã uống " + Integer.parseInt(s) + " cốc nước. Bạn cần uống thêm " + (8 - Integer.parseInt(s)) + " cốc nước nữa.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(0, mBuilder.build());

}}
