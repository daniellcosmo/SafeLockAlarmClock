package com.example.cofrealarme;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log; 

@SuppressLint("NewApi")
public class Alarm_Receiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

		Notification notification = new Notification.Builder(context)
				.setContentTitle("Alarme Disparado !")
				.setContentText("Clique aqui para destrancar.")
				.setSmallIcon(R.drawable.ic_launcher)
				.setAutoCancel(true)
				.build();

		//Set up notificação start command
		notificationManager.notify(0, notification);
	}
}
