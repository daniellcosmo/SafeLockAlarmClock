package com.example.cofrealarme;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.util.Log; 

public class MainActivity extends Activity {

	//Fazer o alarmManager
	AlarmManager alarm_manager;
	TimePicker alarm_timePicker;
	TextView update_text;
	Context context;
	Button start_alarm;
	Button end_alarm;
	PendingIntent pending_intent;
		
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		this.context = this;
		
		// Inicializar nosso alarmManager
		alarm_manager = (AlarmManager) getSystemService (ALARM_SERVICE);
		
		//Inicializar nosso TimePicker
		alarm_timePicker = (TimePicker) findViewById (R.id.timePicker);
		
		// Inicializar nosso update_text
		update_text = (TextView) findViewById (R.id.update_text);
		
		//Criar uma instancia de calendário
		final Calendar calendario = Calendar.getInstance();
		
		//Inicializar Button start_alarm
		Button start_alarm = (Button) findViewById (R.id.start_alarm);
		
		
		//Criar um intent para a classe Alarm Reciver 
		final Intent alarm_intent = new Intent(MainActivity.this, Alarm_Receiver.class);
		
		
		
		//Criar um onClickListener para o stat_alarm		
		start_alarm.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
								
				//Setando a instancia calendario para horas e minutos
				calendario.set(Calendar.HOUR_OF_DAY, alarm_timePicker.getCurrentHour());
				calendario.set(Calendar.MINUTE, alarm_timePicker.getCurrentMinute() );
				
				// Pega o int das horas e minutos
				int hora = alarm_timePicker.getCurrentHour();
				int minuto = alarm_timePicker.getCurrentMinute();
				
				//Converte o int para String
				String hora_string = String.valueOf(hora);
				String minuto_string = String.valueOf(minuto);
				
				//Prestar atenção no AM e PM
				//10:8 >>>  10:08				
				if (minuto < 10) {
					minuto_string = "0" + String.valueOf(minuto);
				}
				
				if (hora < 10) {
					hora_string = "0" + String.valueOf(hora);
				}
				
				// Metodo para alterar o textView
				set_alarm_text("Alarme Ativado para:" + hora_string + ":" + minuto_string);
				
				
				//Colocar uma extra string no alarm_intent
				// Diz ao despertador que que o despertador foi ligado
				alarm_intent.putExtra("extra", true);
				
				
					
				//Criar um appending intent para entregar um intent
				// até que especifique o calendar time
				pending_intent = PendingIntent.getBroadcast(MainActivity.this, 0,
						alarm_intent, PendingIntent.FLAG_UPDATE_CURRENT);
				
				//Setar o alarm Manager
				alarm_manager.set(AlarmManager.RTC_WAKEUP, calendario.getTimeInMillis(),
						pending_intent);
				
				

				
			}
		});
		
			
		//Inicializar Button end_alarm
		Button end_alarm = (Button) findViewById (R.id.end_alarm);
		//Criar um onClickListener para o end_alarm
		
		end_alarm.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Metodo para alterar o textView
				set_alarm_text("Alarme Desativado");
				
				//Cancela o alarme.
				alarm_manager.cancel(pending_intent	);
				
				
				//Coloca uma extra String na intent
				alarm_intent.putExtra("extra", false);
				
				//Para o ringtone
				sendBroadcast(alarm_intent);
				
				
				
			}
		});
		
		
		
		
		
		
		
		
		
		
	}

	
	
	protected void set_alarm_text(String string) {
		update_text.setText(string);
		
	}


	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
