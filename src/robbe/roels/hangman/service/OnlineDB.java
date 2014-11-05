package robbe.roels.hangman.service;

import java.util.ArrayList;

import robbe.roels.hangman.SelectCategory;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class OnlineDB implements DbHandler, Observer {
	private final String url = "https://u0047590.webontwerp.khleuven.be/php/fetchGuessWords.php";
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	private static OnlineDB instance;
	private GetTask task;
	private Context context;
	private boolean loaded;

	private OnlineDB() {

	}

	public static OnlineDB getInstance() {
		if (instance == null) {
			instance = new OnlineDB();
		}
		return instance;

	}

	@Override
	public void fetchWords(Context context) {
		this.context = context;
		load();
	}

	private void load() {
		task = new GetTask();
		task.execute(url);
		task.register(this);
	}

	// Obvious observer code
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

	// Return update to the Facade
	@Override
	public boolean getUpdate() {
		return loaded;
	}

	// Return update from the GetTask
	@Override
	public void update() {
		loaded = task.getUpdate();
		notifyObservers();
	}
}
