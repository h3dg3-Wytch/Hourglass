package com.h3gdg3Wytchery.timelogger;

import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;
import android.util.Log;

public class HourglassHoard {
	
	private static final String TAG = "HourglassHoard";
	private static final String FILENAME = "times.json";
	
	private static HourglassHoard sHourglassHoard;
	
	private Context mAppContext;
	
	private ArrayList<Hourglass> mHourglasses;
	private TimeLoggerJSONSerializer mSerializer;
	
	//This is a GIT test
	private HourglassHoard(Context appContext){
		
		mAppContext = appContext;
		mSerializer = new TimeLoggerJSONSerializer(mAppContext, FILENAME);
		try{
			mHourglasses = mSerializer.loadCrimes();
		}catch(Exception e){
			
			mHourglasses = new ArrayList<Hourglass>();
			Log.e(TAG, "ERROR loading crimes ", e);
			
		}
		
		
	}
	public static HourglassHoard get(Context c){
		if(sHourglassHoard == null){
			sHourglassHoard = new HourglassHoard(c.getApplicationContext());
		}
		return sHourglassHoard;
	}
	
	public ArrayList<Hourglass> getHourglasses(){
		return mHourglasses;
	}
	
	public void addHourglass(Hourglass h){
		
		mHourglasses.add(h);
		
	}
	
	public Hourglass getHourglasse(UUID id){
		
		for(Hourglass h : mHourglasses){
			
			if(h.getId().equals(id)){
				return h	;
			}
		}
		return null;
	}
	
	public boolean saveCrimes() {
		try{
			mSerializer.saveHourglasses(mHourglasses);
			Log.d(TAG, "Crimes saved to file");
			return true;
		}catch(Exception e){
			Log.e(TAG, "ERROR saving crimes: ",e);
			return false;
			
		}
	}

}
