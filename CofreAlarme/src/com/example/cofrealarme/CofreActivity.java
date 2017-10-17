package com.example.cofrealarme;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class CofreActivity extends Activity {

	Button button1;
	EditText editText1;
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cofre);
		
		button1 = (Button) findViewById (R.id.button1);
		editText1 = (EditText) findViewById (R.id.editText1);
		
		editText1.addTextChangedListener(MaskEditUtil.mask(editText1, MaskEditUtil.FORMATO_SENHA));
		
		button1.setVisibility(View.INVISIBLE);
		editText1.setVisibility(View.INVISIBLE);
		
		//Utilizar para capturar editText sem caracteres adicionais.
		//MaskEditUtil.unmask(editText1.getText().toString());
		
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                button1.setVisibility(View.VISIBLE);
                editText1.setVisibility(View.VISIBLE);
            }
        }, 60000); 	// 60 segundos
        
        
	
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cofre, menu);
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
