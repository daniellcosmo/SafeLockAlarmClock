package com.example.cofrealarme;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.util.FloatMath;
import android.util.Log;
import android.widget.Toast;

@SuppressLint("NewApi")
public class Alarm_Receiver extends BroadcastReceiver {

	private float[] coordenadas;
	private float mUltimaAceleracao;
	private float mAceleracaoAtual;
	private MediaPlayer mp;
	private boolean tocando = true;

	@Override
	public void onReceive(final Context context, Intent intent) {
		final Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		final NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

		SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		final Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

		sensorManager.registerListener(new SensorEventListener() {

			@Override
			public void onSensorChanged(SensorEvent event) {
				if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
					coordenadas = event.values.clone();

					float x = coordenadas[0];
					float y = coordenadas[1];
					float z = coordenadas[2];

					mUltimaAceleracao = mAceleracaoAtual;

					// formula da aceleracao baseada nos eixos
					// https://physics.stackexchange.com/questions/41653/how-do-i-get-the-total-acceleration-from-3-axes
					mAceleracaoAtual = FloatMath.sqrt(x*x + y*y + z*z);

					float delta = mAceleracaoAtual - mUltimaAceleracao;

					if(delta > 14){
						mp.stop();

						Notification notification = new Notification.Builder(context)
								.setContentTitle("Alarme")
								.setContentText("Desligado com sucesso!")
								.setSmallIcon(R.drawable.ic_launcher)
								.setAutoCancel(true)
								.build();

						notificationManager.notify(0, notification);
						vibrator.cancel();
					}

				}
			}

			@Override
			public void onAccuracyChanged(Sensor sensor, int i) {

			}
		}, sensor, SensorManager.SENSOR_DELAY_NORMAL);

		Notification notification = new Notification.Builder(context)
				.setContentTitle("Alarme Disparado!")
				.setContentText("Mova para destrancar")
				.setSmallIcon(R.drawable.ic_launcher)
				.setAutoCancel(true)
				.setOngoing(true)
				.build();

		//Set up notificação start command
		notificationManager.notify(0, notification);

		mp = MediaPlayer.create(context, R.raw.alarme2);
		mp.setLooping(true);
		mp.start();

		vibrator.vibrate(20000);

	}
}
