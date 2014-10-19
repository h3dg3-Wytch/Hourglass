package com.h3gdg3Wytchery.timelogger;

import java.util.UUID;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;

public class TimeFragment extends Fragment {
	
	public static final String EXTRA_TIME_ID = "com.h3gdg3Wytchery.timelogger.time";
	
	private Hourglass mHourglass;
	
	private EditText mTitleField;
	
	private Chronometer mTimer;
	
	private Button mStartButton;
	private Button mPauseButton;
	private Button mResetButton;
	
	private long timeWhenStopped;
	
	public static TimeFragment newInstance(UUID timeId){
		//Gets the Bundle, a blank mapping of string and pair. We put the timeId in this Bundle
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_TIME_ID, timeId);
		//We make a new Fragment and put this in here.
		TimeFragment fragment = new TimeFragment();
		fragment.setArguments(args);
		
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		//mHourglass = new Hourglass();
		UUID timeId = (UUID) getArguments().getSerializable(EXTRA_TIME_ID);
		mHourglass = HourglassHoard.get(getActivity()).getHourglasse(timeId);
		setHasOptionsMenu(true);
		
	}
	
	@TargetApi(11)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_time, parent, false );
		
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
			
			if(NavUtils.getParentActivityIntent(getActivity()) != null){
			
			getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
			
			}
		}
		
		mTitleField = (EditText) v.findViewById(R.id.hourglass_title);
		mTitleField.setText(mHourglass.getTitle());
		mTitleField.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				
				mHourglass.setTitle(s.toString());
				
			}
			
		});
		
		mTimer = (Chronometer) v.findViewById(R.id.hourglass);
		timeWhenStopped = mHourglass.getTime();
		mTimer.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
		mStartButton = (Button) v.findViewById(R.id.startButton);
		mStartButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
					mTimer.start();
			
			}
		});
		mPauseButton = (Button) v.findViewById(R.id.pauseButton);
		mPauseButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				timeWhenStopped = mTimer.getBase() - SystemClock.elapsedRealtime();
				mHourglass.setTime(timeWhenStopped);
				mTimer.stop();
				
			}
		});
		mResetButton = (Button) v.findViewById(R.id.resetButton);
		mResetButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				mTimer.stop();
				mTimer.setBase(SystemClock.elapsedRealtime());
				mHourglass.setTime(mTimer.getBase() - SystemClock.elapsedRealtime());
				timeWhenStopped = 0;
				
				
			}
		});
		return v;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case android.R.id.home:
			if(NavUtils.getParentActivityName(getActivity()) != null){
				NavUtils.navigateUpFromSameTask(getActivity());
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
			
		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
		HourglassHoard.get(getActivity()).saveCrimes();
	}
	

}
