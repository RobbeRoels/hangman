package robbe.roels.hangman.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Words {
	private static Words instance = null;
	private HashMap<String, ArrayList<String>> words = new HashMap<String,ArrayList<String>>();

	protected Words() {
		// Exists only to defeat instantiation.
	}
	
	public static Words getInstance() {
		if (instance == null) {
			instance = new Words();
		}
		return instance;
	}
	
	public void setList(HashMap<String, ArrayList<String>> words){
		this.words = words;
	}
	
	public void add(String category, String word){
		if(words.containsKey(category)){
			words.get(category).add(word);
		}else{
			ArrayList<String> wordList = new ArrayList<String>();
			wordList.add(word);
			words.put(category, wordList);
		}
	}
	public ArrayList<String> getCategories(){
		return new ArrayList<String>(words.keySet());
	}

	public String getWord(String cat) {
		Random r = new Random();
		return words.get(cat).get(r.nextInt(words.get(cat).size()));
	}
}