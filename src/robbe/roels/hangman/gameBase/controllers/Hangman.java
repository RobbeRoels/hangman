package robbe.roels.hangman.gameBase.controllers;

import java.util.ArrayList;

import robbe.roels.hangman.gameBase.domain.Word;
import robbe.roels.hangman.gameBase.exceptions.ControllerException;
import robbe.roels.hangman.gameBase.exceptions.DomainException;

public class Hangman {
	int guesses = 0;
	int maxGuesses;
	Word woord;
	ArrayList<String> guessedLetters = new ArrayList<String>();

	public Hangman() throws ControllerException {
		maxGuesses = 5;
		if (woord == null) {
			try {
				woord = new Word("appel");
			} catch (DomainException e) {
				throw new ControllerException(e.getMessage(), e);
			}
		}
	}

	public Hangman(int maxGuesses) throws ControllerException {
		this();
		this.maxGuesses = maxGuesses;

	}

	public Hangman(int maxGuesses, String word) throws ControllerException {
		this(maxGuesses);
		try {
			this.woord = new Word(word);

		} catch (DomainException e) {
			throw new ControllerException(e.getMessage(), e);
		}
	}

	public String checkLetter(char c) {
		if (guessedLetters.contains(c + "")) {
			return woord.getDisplayWord();
		}
		guessedLetters.add(c + "");
		if (!woord.addGuess(c)) {
			guesses++;
		}
		return woord.getDisplayWord();
	}

	public String getDisplayWord() {
		return woord.getDisplayWord();
	}

	public int getGuesses() {
		return guesses;
	}

	public int getMaxGuesses() {
		return maxGuesses;
	}

	public boolean alive() {
		return guesses < maxGuesses;
	}

	public boolean won() {
		return woord.won();
	}

	public String guessedWrongLetters() {
		String output = "";
		int i = 0;
		for (String s : guessedLetters) {
			if (!woord.contains(s)) {
				if (i == 0) {
					output += s;
				} else {
					output += ", " + s;
				}
				i++;
			}
			
		}
		return output;
	}
	
	public String guessedLetters() {
		String output = "";
		int i = 0;
		for (String s : guessedLetters) {
			if (i == 0) {
				output += s;
			} else {
				output += "," + s;
			}
			i++;
		}
		
		return output;
	}

}
