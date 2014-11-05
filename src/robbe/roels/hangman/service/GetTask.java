package robbe.roels.hangman.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import robbe.roels.hangman.SelectCategory;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class GetTask extends AsyncTask<String, Void, String> implements Subject{
	String error = null;
	String data = "";
	boolean fetched = false;
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	
	public GetTask() {

	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		Log.v("DataFetch", "PreExecute");
	}

	@Override
	protected String doInBackground(String... urls) {
		Log.v("DataFetch", "DoInBackground");
			BufferedReader reader = null;
			try {
				// Defined URL where to send data
				URL url = new URL(urls[0]);
	
				// Send POST data request
	
				URLConnection conn = url.openConnection();
				conn.setDoOutput(true);
	
				// Get the server response
	
				reader = new BufferedReader(new InputStreamReader(
						conn.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line = null;
	
				// Read Server Response
				while ((line = reader.readLine()) != null) {
					// Append server response in string
					sb.append(line + "");
				}
	
				// Append Server Response To Content String
				data = sb.toString();
			} catch (Exception ex) {
				error = ex.getMessage();
			} finally {
				try {
	
					reader.close();
				}
	
				catch (Exception ex) {
				}
			}

		return null;
	}

	@Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if(error != null){
        	fetched = false;
        	notifyObservers();
        	Log.v("DB error", error);
        }else{
        	Words words = Words.getInstance();
        		//Create JSONarray instantly because data is already an array
        		JSONArray json;
        		try {
					json = new JSONArray(data);
				
        		
        		for(int i = 0; i<json.length(); i++){
        			JSONObject row = json.getJSONObject(i);
        			
        			String type = "" + row.getString("type");
        			String guessword = "" + row.getString("guessword");
        			words.add(type, guessword);
        		}
        		fetched = true;
        		notifyObservers();
        	}
        	catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
        	}
        }
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
		return fetched;
	}
	
}