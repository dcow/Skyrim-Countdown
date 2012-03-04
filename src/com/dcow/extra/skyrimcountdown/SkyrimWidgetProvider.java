package com.dcow.extra.skyrimcountdown;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class SkyrimWidgetProvider extends AppWidgetProvider {

	public static final String TAG = "SkyrimWidgetProvider";
	public static final String ACTION_SKYRIM_TICK = "dcow.extra.action.SKYRIM_TICK";

	private AlarmManager mAM;
	private PendingIntent mBroadcast;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		
		if ( ACTION_SKYRIM_TICK.equals(intent.getAction()) ) {
			AppWidgetManager aWM = AppWidgetManager.getInstance(context);
			onUpdate(context, aWM, 
					aWM.getAppWidgetIds(
							new ComponentName(context, SkyrimWidgetProvider.class)));
		}
	}

	@Override
	public void onEnabled(Context context) {
		
		Intent i = new Intent(ACTION_SKYRIM_TICK);
		mBroadcast = PendingIntent.getBroadcast(context, 0, i, 0);
		
		mAM = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		mAM.setRepeating(AlarmManager.RTC, System.currentTimeMillis() + 1000, 
				1000, mBroadcast);
		
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager awm, int[] awi) {

		/* Milliseconds remaining until release: */
		long ms = SkyrimCountdown.RELEASEDATE - System.currentTimeMillis();
		
		RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget);
		rv.setTextViewText(R.id.timeRemaining, 
				  "  D: " + SkyrimCountdown.msToDaysRemaining(ms)
				+ "  H: " + SkyrimCountdown.msToHoursRemaining(ms) 
				+ "  M: " + SkyrimCountdown.msToMinutesRemaining(ms)
				+ "  S: " + SkyrimCountdown.msToSecondsRemaining(ms) );
		
		Log.d(TAG, "mAppWidgetManager value: " + awm);
		if (awm != null)
			awm.updateAppWidget(awi, rv);
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
	
		if (mAM != null)
			mAM.cancel(mBroadcast);
		
		super.onDeleted(context, appWidgetIds);
	}
}
