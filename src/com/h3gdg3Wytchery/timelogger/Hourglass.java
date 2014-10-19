package com.h3gdg3Wytchery.timelogger;

import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

public class Hourglass {
	
	private static final String JSON_ID = "id";
	private static final String JSON_TITLE = "solved";
	private static final String JSON_TIME = "time";
	
	private UUID mId;
	private String mTitle;
	private long mTime;
	
	
	public JSONObject toJSON() throws JSONException{
		JSONObject json = new JSONObject();
		json.put(JSON_ID, mId.toString());
		json.put(JSON_TITLE, mTitle);
		json.put(JSON_TIME, mTime);
		return json;
	}
	
	
	public Hourglass(JSONObject json) throws JSONException{
		mId = UUID.fromString(json.getString(JSON_ID));
		if(json.has(JSON_TITLE)){
			mTitle = json.getString(JSON_TITLE);
		}
		mTime = json.getLong(JSON_TIME);
	}
	public long getTime() {
		return mTime;
	}

	public void setTime(long mTime) {
		this.mTime = mTime;
	}

	public Hourglass(){
		mId = UUID.randomUUID();
		mTime = 0;
	}

	public void setId(UUID mId) {
		this.mId = mId;
	}

	public void setTitle(String mTitle) {
		this.mTitle = mTitle;
	}

	public UUID getId() {
		return mId;
	}

	public String getTitle() {
		return mTitle;
	}
	@Override
	public String toString(){
		return mTitle;
	}

}
