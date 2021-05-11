package com.example.infocovid;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import static android.content.Context.MODE_PRIVATE;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // crea canal de notificaciones
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context.getApplicationContext(), "com.uc3m.it.infocovid.notify_001");

        SharedPreferences mPreferences = context.getSharedPreferences("com.uc3m.it.infocovid", MODE_PRIVATE);
        String comunidad = mPreferences.getString("comunidad", null);

        Intent intent2 = new Intent(context.getApplicationContext(), DisplayRestrictionsActivity.class);
        intent2.putExtra("province", comunidad);
        intent2.putExtra("activity", "MainActivity");
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent2, 0);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.drawable.ic_covid_notification);
        mBuilder.setContentTitle("¡Date prisa!");
        mBuilder.setContentText("En 10 minutos es el toque de queda, ¡vuelve a casa!");
        mBuilder.setChannelId("checkRestrictions");

        notificationManager.notify(0, mBuilder.build());



    }
}
