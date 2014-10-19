package com.h3gdg3Wytchery.timelogger;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class TimeListFragment extends ListFragment {
	
	private ArrayList<Hourglass> mHourglasses;
	private boolean mSubtitleVisible;
	private Button mAddHourglassButton;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		getActivity().setTitle(R.string.hourglass_title);
		mHourglasses = HourglassHoard.get(getActivity()).getHourglasses();
		
		//ArrayAdapter<Hourglass> adapter = new ArrayAdapter<Hourglass>(getActivity(),android.R.layout.simple_list_item_1,mHourglasses);
		//setListAdapter(adapter);
		TimeAdapter adapter = new TimeAdapter(mHourglasses);
		setListAdapter(adapter);
		setRetainInstance(true);
		mSubtitleVisible=true;
		
	}
	
	@TargetApi(11)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.empty_list_item_time, parent, false);
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
			
			if(mSubtitleVisible){
				getActivity().getActionBar().setSubtitle(R.string.subtitle);
			}
			
		}
		mAddHourglassButton = (Button) v.findViewById(R.id.addHourglassButton);
		mAddHourglassButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Hourglass hourglass = new Hourglass();
				HourglassHoard.get(getActivity()).addHourglass(hourglass);
				Intent i = new Intent(getActivity(), TimePagerActivity.class);
				i.putExtra(TimeFragment.EXTRA_TIME_ID, hourglass.getId());
				startActivityForResult(i,0);
			}
		});
		return v;
	}
	

	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id){
		Hourglass h =  ((TimeAdapter)getListAdapter()).getItem(position);
		
		Intent i = new Intent(getActivity(), TimePagerActivity.class);
		i.putExtra(TimeFragment.EXTRA_TIME_ID, h.getId());
		startActivity(i);
	}
	
	
	private class TimeAdapter extends ArrayAdapter<Hourglass>{

		public TimeAdapter(ArrayList<Hourglass> hourglasses){
			super(getActivity(),0,hourglasses);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent){
			
			//If we weren't given a view, inflate one
			if(convertView == null){
				convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_time, null);
			}
			//Configure the View for this Hourglass
			Hourglass h = getItem(position);
			
			TextView titleTextView = (TextView)convertView.findViewById(R.id.hourglassTitleTextView);
			titleTextView.setText(h.getTitle());
			
			TextView timeTextView = (TextView) convertView.findViewById(R.id.hourglassTimeTextView);
			timeTextView.setText(formatTime(h.getTime()));
			
			return convertView;
			
		}
		
		

		private String formatTime(long time) {
			
			    long s = Math.abs(time) % 60;
			    long m = (Math.abs(time) / 60) % 60;
			    long h = (Math.abs(time) / (60 * 60)) % 24;
			    return String.format("%d:%02d:%02d", h,m,s);
			
		}
		
		
		
		
		
		
	}
	
	
	
	@Override
	public void onResume(){
		super.onResume();
		((TimeAdapter) getListAdapter()).notifyDataSetChanged();
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_hourglass_list, menu);
		MenuItem showSubtitle = menu.findItem(R.id.menu_item_show_subtitle);
		
		if( mSubtitleVisible && showSubtitle != null){
			showSubtitle.setTitle(R.string.hide_subtitle);
		}
		
		
	}
	@TargetApi(11)
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()) {
		
		
		case R.id.menu_item_new_hourglass:
			Hourglass hourglass = new Hourglass();
			HourglassHoard.get(getActivity()).addHourglass(hourglass);
			Intent i = new Intent(getActivity(), TimePagerActivity.class);
			i.putExtra(TimeFragment.EXTRA_TIME_ID, hourglass.getId());
			startActivityForResult(i, 0);
			return true;
			
		case R.id.menu_item_show_subtitle:
			
			if(getActivity().getActionBar().getSubtitle() == null){
				
				getActivity().getActionBar().setSubtitle(R.string.show_subtitle);
				item.setTitle(R.string.hide_subtitle);
				mSubtitleVisible = true;
			}else{
				getActivity().getActionBar().setSubtitle(null);
				item.setTitle(R.string.show_subtitle);
				mSubtitleVisible = false;
			}
			
			return true;
		default:
			return super.onOptionsItemSelected(item);
			
		}
		
	}
	
	
	
	
		
}
