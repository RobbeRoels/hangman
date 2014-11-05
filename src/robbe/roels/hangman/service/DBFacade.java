package robbe.roels.hangman.service;

import java.util.ArrayList;
import java.util.HashMap;

import robbe.roels.hangman.SelectCategory;
import robbe.roels.hangman.gameBase.exceptions.DomainException;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class DBFacade implements DBFacadeInterface, Observer, Subject{
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	DbHandler handler;
	Words words = Words.getInstance();
	boolean fetched = false;
	
	@Override
	public void setHandler(String handler) {
		if(handler.equals("Online")){
			this.handler = OnlineDB.getInstance();
			this.handler.register(this);
		}else{
			this.handler.unregister(this);
			this.handler = OfflineDB.getInstance();
			this.handler.register(this);
		}
	}
	
	@Override
	public void fetchWords(Context context) {
		//Try online db first if that fails switch to offline
		handler.fetchWords(context);
	}

	@Override
	public void setWords(HashMap<String,ArrayList<String>> wordlist) {
		words.setList(wordlist);
	}

	@Override
	public String getRandomWord(String cat) {
		return words.getWord(cat);
		
	}
	@Override
	public ArrayList<String> getCategories() {
		return words.getCategories();
		
	}

	//Obvious observer code
	@Override
	public void register(Observer o) {
		observers.add(o);
	}

	@Override
	public void unregister(Observer o) {
		observers.remove(o);
	}

	@Override
	public void notifyObservers() {
		for (Observer o : observers) {
			o.update();
		}
	}

	//Return update to the activity
	@Override
	public boolean getUpdate() {
		return fetched;
	}

	//Return update from the handler
	@Override
	public void update() {
		fetched = handler.getUpdate();
		notifyObservers();
	}




}
