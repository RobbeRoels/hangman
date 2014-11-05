package robbe.roels.hangman.service;

import java.util.ArrayList;
import java.util.HashMap;

import robbe.roels.hangman.gameBase.exceptions.DomainException;
import android.content.Context;

public abstract interface DBFacadeInterface {
	DbHandler handler = null;
	
	public abstract void setHandler(String handler);
	
	public abstract void fetchWords(Context context) throws DomainException;
	
	public abstract void setWords(HashMap<String,ArrayList<String>> wordlist);
	
	public abstract String getRandomWord(String cat);
	
	public abstract ArrayList<String> getCategories();	
	
}
