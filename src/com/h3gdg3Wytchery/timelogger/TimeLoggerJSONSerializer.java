package com.h3gdg3Wytchery.timelogger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import android.content.Context;

public class TimeLoggerJSONSerializer {
	
	private Context mContext;
	private String mFileName;

	public TimeLoggerJSONSerializer(Context c, String f){
		mContext = c;
		mFileName = f;
	}
	
	public ArrayList<Hourglass> loadCrimes() throws IOException, JSONException {
		ArrayList<Hourglass> hourglasses= new ArrayList<Hourglass>();
		BufferedReader reader = null;
		try{
			//Open and read the File into a String Builder
			InputStream in = mContext.openFileInput(mFileName);
			reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder jsonString = new StringBuilder();
			String line = null;
			while((line = reader.readLine()) != null){
				//Line breaks are omitted and irrelevant
				jsonString.append(line);
			}
			// Parse the JSON using JSONTokener
			JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
			//Build the array of crimes from JSONObjects 
			for(int i = 0; i < array.length(); i++){
				hourglasses.add(new Hourglass(array.getJSONObject(i)));
				
			}
		}
		catch(FileNotFoundException e){
			//Ignore this, it happens when it starts fresh
		}finally{
			
			if(reader != null)
				reader.close();
			
			return hourglasses;
		}
		
	}
	public void saveHourglasses(ArrayList<Hourglass> hourglasses) throws JSONException, IOException {
		//Build an array in JSON
		JSONArray array = new JSONArray();
		for(int i = 0; i < hourglasses.size(); i++){
			array.put(hourglasses.get(i).toJSON());
		}
		//Write the file to disk
		Writer writer = null;
		try{
			OutputStream out = mContext.openFileOutput(mFileName, Context.MODE_PRIVATE);
			writer = new OutputStreamWriter(out);
			writer.write(array.toString());
		}finally{
			if(writer != null){
				writer.close();
			}
		}
	}
	
}
