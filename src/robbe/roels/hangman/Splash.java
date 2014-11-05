package robbe.roels.hangman;


import robbe.roels.hangman.service.DBFacade;
import robbe.roels.hangman.service.Observer;
import android.app.Activity;

import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.widget.Toast;

public class Splash extends Activity implements Observer {
	DBFacade dbf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		dbf = new DBFacade();
		dbf.setHandler("Online");
		dbf.register(this);
		dbf.fetchWords(getApplicationContext());
	}

	@Override
	public void update() {
		if(!dbf.getUpdate()){
			CharSequence text = "Can't load words, using offline database";
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(getApplicationContext(), text, duration);
			toast.show();
			dbf.setHandler("Offline");
			dbf.fetchWords(getApplicationContext());
		}else{
          	Intent intent = new Intent(getApplicationContext(), SelectCategory.class);
          	startActivity(intent);
		}
				
	}


}
