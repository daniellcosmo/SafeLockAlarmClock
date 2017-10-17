package com.example.cofrealarme;

import java.util.Calendar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends Activity {

    //Fazer o alarmManager
    AlarmManager alarm_manager;
    TimePicker alarm_timePicker;
    TextView update_text;
    Context context;
    Button start_alarm;
    Button end_alarm;
    PendingIntent pending_intent;
    EditText inputSenha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.context = this;

        // Inicializar nosso alarmManager
        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        //Inicializar nosso TimePicker
        alarm_timePicker = (TimePicker) findViewById(R.id.timePicker);

        // Inicializar nosso update_text
        update_text = (TextView) findViewById(R.id.update_text);
        
        //Inicializar o Campo para Definição de Senha
        inputSenha = (EditText) findViewById (R.id.inputSenha);

        //Criar uma instancia de calendario
        final Calendar calendario = Calendar.getInstance();

        //Inicializar Button start_alarm
        Button start_alarm = (Button) findViewById(R.id.start_alarm);

        //Criar um intent para a classe Alarm Reciver
        final Intent alarm_intent = new Intent(MainActivity.this, Alarm_Receiver.class);
        
        //Utiliza uma máscara para o inputSenha utilizando a classe MaskEditUtil
        inputSenha.addTextChangedListener(MaskEditUtil.mask(inputSenha, MaskEditUtil.FORMATO_SENHA));
        //Para retornar o inputSenha sem os caracteres para dentro de um textView
        //textView.setText(String.valueOf(MaskEditUtil.unmask(inputSenha.getText().toString())));
        inputSenha.setText(null);

        //Criar um onClickListener para o stat_alarm
        start_alarm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Setando a instancia calendario para horas e minutos
                calendario.set(Calendar.HOUR_OF_DAY, alarm_timePicker.getCurrentHour());
                calendario.set(Calendar.MINUTE, alarm_timePicker.getCurrentMinute());
                calendario.set(Calendar.SECOND, 0);

                // Pega o int das horas e minutos
                int hora = alarm_timePicker.getCurrentHour();
                int minuto = alarm_timePicker.getCurrentMinute();

                //Converte o int para String
                String hora_string = String.valueOf(hora);
                String minuto_string = String.valueOf(minuto);

                //Prestar atenÃ§Ã£o no AM e PM
                //10:8 >>>  10:08
                if (minuto < 10) {
                    minuto_string = "0" + String.valueOf(minuto);
                }

                if (hora < 10) {
                    hora_string = "0" + String.valueOf(hora);
                }

                long timePicker = calendario.getTimeInMillis();
                long now = Calendar.getInstance().getTimeInMillis();

                if(timePicker <= now) {
                    set_alarm_text("Selecione um horário futuro.");
                    
                } else if (MaskEditUtil.unmask(inputSenha.getText().toString()).length() != 6) {
                	set_alarm_text("Insira uma senha de seis (6) digitos.");
                	
                } else {
                	
                	String timeAgo = DateUtils.getRelativeTimeSpanString(calendario.getTimeInMillis(), Calendar.getInstance().getTimeInMillis(), DateUtils.SECOND_IN_MILLIS).toString();

                    // Metodo para alterar o textView e Traduz Datas
                    set_alarm_text("Alarme ativado para daqui a " +
                		timeAgo.replaceAll("hours","horas").replaceAll("hour", "hora")
                    	.replaceAll("minuts", "minutos").replaceAll("minute", "minuto")
                		.replaceAll("second", "segundo").replaceAll("seconds", "segundos")
						.replaceAll("0 segundos", "menos de 1 minuto")
						.replaceAll("in ", ""));
                    
                    
                    //Colocar uma extra string no alarm_intent
                    // Diz ao despertador que que o despertador foi ligado
                    alarm_intent.putExtra("extra", true);


                    //Criar um pending intent para entregar um intent
                    // atÃ© que especifique o calendar time
                    pending_intent = PendingIntent.getBroadcast(MainActivity.this, 0,
                            alarm_intent, PendingIntent.FLAG_UPDATE_CURRENT);


                    //Setar o alarm Manager
                    alarm_manager.set(AlarmManager.RTC_WAKEUP, calendario.getTimeInMillis(),
                            pending_intent);

                }

            }
        });


        //Inicializar Button end_alarm
        Button end_alarm = (Button) findViewById(R.id.end_alarm);
        //Criar um onClickListener para o end_alarm

        end_alarm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Metodo para alterar o textView
                set_alarm_text("Alarme Desativado");

                //Cancela o alarme.
                alarm_manager.cancel(pending_intent);


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
