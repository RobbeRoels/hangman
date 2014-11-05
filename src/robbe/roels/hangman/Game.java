package robbe.roels.hangman;



import robbe.roels.hangman.gameBase.controllers.Hangman;
import robbe.roels.hangman.gameBase.exceptions.ControllerException;
import robbe.roels.hangman.service.Words;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class Game extends Activity {
	private Words words = Words.getInstance();
	private Hangman hm;
	private ImageView image;
	private TextView word;
	private EditText letter;
	private TextView guessedLetters;
	private static final String STATE_WORD = "WORD";
	private static final String STATE_GUESSESTRING = "GUESSES";
	private String guessword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		Intent intent = getIntent();
		Log.v("Create","Creating new instance");
		
			if(guessword == null){
				guessword = words.getWord(intent.getStringExtra("category"));
			}
			gameSetUp();
			uiSetUp();
			

	}
	
	private void gameSetUp(){
		try {
			hm = new Hangman(8, guessword);
		} catch (ControllerException e) {
			Log.v("WordError",e.getMessage() + " ");
			e.printStackTrace();
		}
	}
	
	private void uiSetUp(){
		image = (ImageView) findViewById(R.id.hangmanImage);
		letter = (EditText) findViewById(R.id.letterInput);
		word = (TextView) findViewById(R.id.guessword);
		guessedLetters = (TextView) findViewById(R.id.guessedLetters);
		setBackground(0);
		word.setText(hm.getDisplayWord());	 
	}
	

	private void setBackground(int guesses) {
		int id;
		switch(guesses){
		case 0:
			id = R.drawable.hangman0;
			break;
		case 1:
			id = R.drawable.hangman1;
			break;
		case 2:
			id = R.drawable.hangman2;
			break;
		case 3:
			id = R.drawable.hangman3;
			break;
		case 4:
			id = R.drawable.hangman4;
			break;
		case 5:
			id = R.drawable.hangman5;
			break;
		case 6:
			id = R.drawable.hangman6;
			break;
		case 7:
			id = R.drawable.hangman7;
			break;
		case 8:
			id = R.drawable.hangman8;
			break;
		default:
			id = R.drawable.hangman0;
			break;
		}
		image.setImageDrawable(getResources().getDrawable(id));
		image.invalidate( );
	}
	
	public void guess(View view){
		if(letter.getText().length() !=1){
        	CharSequence text = "You have to input a letter!";
        	int duration = Toast.LENGTH_LONG;
        	Toast toast = Toast.makeText(getApplicationContext(), text, duration);
        	toast.show();
		}else{
			hm.checkLetter(letter.getText().charAt(0));
			word.setText(hm.getDisplayWord());	
			setBackground(hm.getGuesses());
			letter.setText("");
			guessedLetters.setText(hm.guessedWrongLetters());
		}
		if(!hm.alive()){
			gameOver(false);
		}
		if(hm.won()){
			gameOver(true);
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}
	
	public void reset() {
		Intent intent = new Intent(getApplicationContext(), SelectCategory.class);
		startActivity(intent);
	}
	
	
	public void gameOver(boolean win){
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        switch (which){
		        case DialogInterface.BUTTON_POSITIVE:
		            reset();
		            break;

		        case DialogInterface.BUTTON_NEGATIVE:
		        	Intent homeIntent = new Intent(Intent.ACTION_MAIN);
		            homeIntent.addCategory( Intent.CATEGORY_HOME );
		            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  
		            startActivity(homeIntent); 
		            break;
		        }
		    }
		};
		String message;
		if(win){
			message = "You won!! Want to play again?";
		}else{
			message = "You lost. Want to play again?";
		}
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message).setPositiveButton("Yes", dialogClickListener)
		    .setNegativeButton("No", dialogClickListener).show();
	}
	
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) { 
    	super.onSaveInstanceState(savedInstanceState);
    	
    	// Save the user's current game state 
    	savedInstanceState.putString(STATE_WORD , guessword);
    	savedInstanceState.putString(STATE_GUESSESTRING, hm.guessedLetters());
    	Log.v("Save", hm.guessedLetters());
    	// Always call the superclass so it can save the view hierarchy state 
    	super.onSaveInstanceState(savedInstanceState);
    }
    
    
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) { 
    	super.onRestoreInstanceState(savedInstanceState); 

    	Log.v("Restore","RestoringInstance");
    	
    	guessword = savedInstanceState.getString(STATE_WORD);
    	String stateGuessestring = savedInstanceState.getString(STATE_GUESSESTRING);
    	gameSetUp();
    	
    	if(stateGuessestring != ""){
    		if(stateGuessestring.length() > 1){
    			String[] seperateLetters = stateGuessestring.split(",");
    			for(int i = 0; i < seperateLetters.length; i++){
    				hm.checkLetter(seperateLetters[i].charAt(0));
    			}
    		}else{
    			hm.checkLetter(stateGuessestring.charAt(0));
    		}
    	}
		word.setText(hm.getDisplayWord());	
		setBackground(hm.getGuesses());
		letter.setText("");
		guessedLetters.setText(hm.guessedWrongLetters());
    }
	
	

}
