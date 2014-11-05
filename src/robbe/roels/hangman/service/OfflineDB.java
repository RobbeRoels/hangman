package robbe.roels.hangman.service;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;

public class OfflineDB implements DbHandler {
	private static OfflineDB instance;
	private HashMap<String, ArrayList<String>> words = new HashMap<String,ArrayList<String>>();
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	private boolean loaded;
	
	private OfflineDB(){
		
	}
	
	public static OfflineDB getInstance(){
		if(instance == null){
			instance = new OfflineDB();
		}
		return instance;
		
	}

	@Override
	public void fetchWords(Context context) {
		ArrayList<String> wordlist = new ArrayList<String>();
		wordlist.add("apple");
		wordlist.add("banana");
		wordlist.add("kiwi");
		wordlist.add("pineapple");
		wordlist.add("pear");
		wordlist.add("strawberry");
		wordlist.add("cherry");
		words.put("fruit", wordlist);
		ArrayList<String> wordlist2 = new ArrayList<String>();
		wordlist2.add("giraffe");
		wordlist2.add("horse");
		wordlist2.add("elephant");
		wordlist2.add("zebra");
		wordlist2.add("bird");
		wordlist2.add("shark");
		words.put("animal", wordlist2);
		Words wordsList = Words.getInstance();
		wordsList.setList(words);
		loaded = true;
		notifyObservers();
	}
	

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
		for(Observer o:observers){
			o.update();
		}
		
	}
	@Override
	public boolean getUpdate() {
		return loaded;
	}	

}
