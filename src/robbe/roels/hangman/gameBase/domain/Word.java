package robbe.roels.hangman.gameBase.domain;

import java.util.Arrays;

import robbe.roels.hangman.gameBase.exceptions.DomainException;

public class Word {
	String word;
	char[] wordArray;
	
	public Word(String word) throws DomainException{
		if(word == null){
			throw new DomainException("Invalid word");
		}
		this.word = word;
		wordArray = new char[word.length()];
		Arrays.fill(wordArray, '.');
	}
	
	public String getDisplayWord(){
		return new String(wordArray);
	}
	
	public boolean addGuess(char c){
		boolean found = false;
		for (int i = -1; (i = word.indexOf(c, i + 1)) != -1; ) {
		    wordArray[i] = c;
		    found = true;
		}
		return found;
	}
	
	public boolean won(){
		for(int i = 0; i < wordArray.length; i++){
			if(wordArray[i] == '.'){
				return false;
			}
		}
		return true;
	}
	
	public boolean contains(String letter){
		return word.contains(letter);
	}
	

}
