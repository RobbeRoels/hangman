package robbe.roels.hangman.service;

import android.content.Context;

public abstract interface DbHandler extends Subject{
	
	abstract void fetchWords(Context context);

}
