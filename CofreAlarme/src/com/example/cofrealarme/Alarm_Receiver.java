package com.example.cofrealarme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log; 

public class Alarm_Receiver extends BroadcastReceiver {

	
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		Log.e("Reciver" , "Here");
		
		//Pega Boolean do Main
		Boolean getBoolean = intent.getExtras().getBoolean("extra");
		
		Log.e("Qual é a key?", String.valueOf(getBoolean));
		
		
		//Criar um intent para o ServiceRingtone
		Intent service_intent = new Intent (context, ServiceTocandoRingtone.class);
		
		//Passa a extra string da Main Activity para serviceRingtone
		service_intent.putExtra("extra", getBoolean);
		
		//Iniciar o ServiceRingtone
		context.startService(service_intent);
		

		
		

		
		
	}

}
