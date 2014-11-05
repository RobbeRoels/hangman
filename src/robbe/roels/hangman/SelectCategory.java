package robbe.roels.hangman;

import robbe.roels.hangman.service.DBFacade;
import robbe.roels.hangman.service.DBFacadeInterface;
import robbe.roels.hangman.service.Words;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;

public class SelectCategory extends Activity {
	ListView listView;
	DBFacadeInterface dbFacade = new DBFacade();
	
	@Override
	public void onBackPressed(){
		Intent homeIntent = new Intent(Intent.ACTION_MAIN);
	    homeIntent.addCategory( Intent.CATEGORY_HOME );
	    homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  
	    startActivity(homeIntent); 
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_category);
		listView = (ListView) findViewById(R.id.categories);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	              android.R.layout.simple_list_item_1, android.R.id.text1, dbFacade.getCategories());
		 listView.setAdapter(adapter); 
		 setListViewListener();
		 
	}

	private void setListViewListener() {
		 listView.setOnItemClickListener(new OnItemClickListener() {			 
             @Override
             public void onItemClick(AdapterView<?> parent, View view,
                int position, long id) {              
              // ListView Clicked item value
            	String  itemValue    = (String) listView.getItemAtPosition(position);
              	Intent intent = new Intent(getApplicationContext(), Game.class);
              	intent.putExtra("category", itemValue);
              	startActivity(intent);
             }

        }); 
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.select_category, menu);

		return true;
	}
}
