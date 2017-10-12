package com.example.cofrealarme;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import android.util.Log; 

public class ServiceTocandoRingtone extends Service {

	
	MediaPlayer alarme;
	boolean playing;
	
	


	
	@Override
	public IBinder onBind(Intent intent) {
		//
		return null;
	}
	

    @SuppressLint("NewApi")
	@Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        
    	//Log.i("LocalService", "Received start id " + startId + ": " + intent);
        

        
 
        
    	//fetch a extra string
    	Boolean state = intent.getExtras().getBoolean("extra");
    	
       	Log.e("Ringtone State" , String.valueOf(state));
    	
       	

       	
       	
       	
       	
       	
       	
       	
       	//if else statements
       	
       	//if sem musica && start_alarm
       	//musica começa a tocar
       	if (!playing && state) {
       		
       		Log.e("sem musica &&"," start_alarm");
       		// Criar uma instancia do MediaPlayer
            alarme = MediaPlayer.create(this, R.raw.alarme1);
            //Toca o ringtone
            alarme.setLooping(true);
            alarme.start();
            
            playing = true;
            
           	//Notificação
           	//Set up notificação service
           	NotificationManager notificacao = (NotificationManager) 
           			getSystemService(NOTIFICATION_SERVICE);
           	// Set up intent que vai para o MainActivity
           	Intent intent_notificacao = new Intent (this.getApplicationContext(), MainActivity.class); //Mudar para CofreActivity.class
           	
           	//Set up um pending intent
           	PendingIntent pendingIntent_notificacao = PendingIntent.getActivity(this, 0	,
           			intent_notificacao, 0);
           	
           	//Fazer os parametros de notificação
           	Notification notificacao_popup = new Notification.Builder(this)
           			.setContentTitle("Alarme Disparado !")
           			.setContentText("Clique aqui para destrancar.")
           			.setSmallIcon(R.drawable.ic_launcher)
           			.setContentIntent (pendingIntent_notificacao)
           			.setAutoCancel(true)       			
           			.build();
           	
           	
           	//Set up notificação start command
           	notificacao.notify(0, notificacao_popup);
           			
           /* 
           	//Notificacao2
           	Intent intent= new Intent(MainActivity.this, NotificacaoActivity.class);
           	intent.putExtra("mensagem", msg.getText().toString());
           	int id = (int) (Math.random()*1000);
           	PendingIntent pi= PendingIntent.getActivity(getBaseContext(), id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
           	
           	Notification notification= new Notification.Builder(getBaseContext())
           			.setContentTitle("De: Andrés Menéndez" )
           			.setContentText("Opa")
           			.setSmallIcon((R.ic_launcher-web)
           			.setContentIntent(pi).build();
           	NotificationManager notificationManager= 
           			(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
           	notification.flags|= Notification.FLAG_AUTO_CANCEL;
           	notificationManager.notify(id, notification);
           	
            */
            
       	}
       	
       	//if: com musica && end_alarm
       	//para a musica
       	else if (playing && !state) {
       		
       		Log.e("com musica && ","end_alarm");
       		//Para o ringtone
       		alarme.stop();
       		alarme.reset();
       		
       		playing = false;
       		
       	}
       	
       	//if: sem musica && end_alarm
       	else if (!playing && !state) {
       		Log.e("sem musica && ","end_alarm");
       		
       	}
       	//if: com musica && start_alarm
       	//fazer nada
       	else if (playing && state) {
       		Log.e("com musica && ","start_alarm");
       	}
       	
       	
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {

    	Log.e("Destruido", "Puff!");
    	
    	playing = false;
    	super.onDestroy(); // Buscar modo de manter apos fechar.

    }


	
}
