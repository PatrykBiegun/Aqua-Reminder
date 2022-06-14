package com.example.aquareminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;



public class ReminderBrodcast extends BroadcastReceiver {
    private String title = "Aqua reminder", note = "its WATER TIME!";

    @Override
    public void onReceive(Context context , Intent intent) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder ( context, "notifyLemubit" )
                .setContentText(title)
                .setSmallIcon(R.drawable.water_glass)
                .setContentTitle(note)
                .setPriority( NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from (context);

        notificationManager.notify ( 200, builder.build ());
    }
}
