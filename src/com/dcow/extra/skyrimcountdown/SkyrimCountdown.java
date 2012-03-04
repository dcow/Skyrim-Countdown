package com.dcow.extra.skyrimcountdown;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

public class SkyrimCountdown extends Activity {
	
    /* Initialize the calendar to the date when Skyrim is released */
	public static final long RELEASEDATE =  
		(new GregorianCalendar(2011, Calendar.NOVEMBER, 11, 0, 0, 0)).getTimeInMillis();
	
	private long mCurrentTime;
	private SkyrimCounter mCounter;
	
	TextView mCountdownDisplay;
	TextView mDays, mHours, mMinutes, mSeconds, mMilli;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        /* Get the textViews so the time remaining can be updated */
        mCountdownDisplay = (TextView) findViewById(R.id.timeRemaining);
        mDays = (TextView) findViewById(R.id.textDays);
        mHours = (TextView) findViewById(R.id.textHours);
        mMinutes = (TextView) findViewById(R.id.textMinutes);
        mSeconds = (TextView) findViewById(R.id.textSeconds);
        mMilli = (TextView) findViewById(R.id.textMilli);

        /* Create a new SkyrimCounter that counts down from now until the release date */
        mCounter = new SkyrimCounter(RELEASEDATE - System.currentTimeMillis(), 1);
        mCounter.start();
    }
    
    public void updateDate() {
    	mCurrentTime = System.currentTimeMillis();
    	mCountdownDisplay.setText("Time: " + mCurrentTime);
    
    }
    
    
    /* Implement the abstract class CountDownTimer as a SkyrimCounter */
    public class SkyrimCounter extends CountDownTimer {
 
    	public SkyrimCounter(long msInFuture, long interval) {    	
    		super(msInFuture, interval);
    	}

		@Override
		public void onFinish() {
			onTick(0L);
			mCountdownDisplay.setText(R.string.finished);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			
			mDays.setText("D:  " + msToDaysRemaining(millisUntilFinished));
			mHours.setText("H:  " + msToHoursRemaining(millisUntilFinished));
			mMinutes.setText("M:  " + msToMinutesRemaining(millisUntilFinished));
			mSeconds.setText("S:  " + msToSecondsRemaining(millisUntilFinished));
			mMilli.setText("m:  " + msToMilliRemaining(millisUntilFinished));
			
		}
		
    }
    
    public static int msToDaysRemaining(long ms) {
		return (int) (ms / 86400000);
	}
    
    public static int msToHoursRemaining(long ms) {
		return (int) ((ms % 86400000L) / 3600000L);
	}
    
    public static int msToMinutesRemaining(long ms) {
		return (int) (((ms % 86400000L) % 3600000L) / 60000L);
	}
    
    public static int msToSecondsRemaining(long ms) {
		return (int) ((((ms % 86400000L) % 3600000L) % 60000L) / 1000L);
	}
    
    public static int msToMilliRemaining(long ms) {
		return (int) ((((ms % 86400000L) % 3600000L) % 60000L) % 1000L);
	}
    
}