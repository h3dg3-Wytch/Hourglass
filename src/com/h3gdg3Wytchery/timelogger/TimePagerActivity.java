package com.h3gdg3Wytchery.timelogger;

import java.util.ArrayList;
import java.util.UUID;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

public class TimePagerActivity extends FragmentActivity {
	
	private ViewPager mViewPager;
	private ArrayList<Hourglass> mHourglasses;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		mViewPager = new ViewPager(this);
		mViewPager.setId(R.id.viewPager);
		setContentView(mViewPager);
		
		mHourglasses = HourglassHoard.get(this).getHourglasses();
		FragmentManager fm =getSupportFragmentManager();
		mViewPager.setAdapter(new FragmentStatePagerAdapter(fm){

			@Override
			public Fragment getItem(int pos) {
				Hourglass hourglass = mHourglasses.get(pos);
				return TimeFragment.newInstance(hourglass.getId());
			}

			@Override
			public int getCount() {
				return mHourglasses.size();
			}
			
		});
		
		mViewPager.setOnPageChangeListener(new OnPageChangeListener(){

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPageSelected(int pos) {
			
				Hourglass hourglass = mHourglasses.get(pos);
				if(hourglass.getTitle() != null){
					setTitle(hourglass.getTitle());
				}
			}
			
		});
		
		UUID timeId = (UUID) getIntent().getSerializableExtra(TimeFragment.EXTRA_TIME_ID);
		for(int i = 0; i < mHourglasses.size(); i++){
			if(mHourglasses.get(i).getId().equals(timeId)){
				mViewPager.setCurrentItem(i);
				break;
			}
		}
		
	}

}
