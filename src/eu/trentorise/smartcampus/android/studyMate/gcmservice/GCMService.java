package eu.trentorise.smartcampus.android.studyMate.gcmservice;

import android.content.Context;
import android.content.Intent;
import android.util.Log;


import com.google.android.gcm.GCMBaseIntentService;

public class GCMService extends GCMBaseIntentService {

	private static final String PROJECT_ID = ""; //to add project id
    
	private static final String TAG = "GCMIntentService";
	
	public GCMService()
	{
		super(PROJECT_ID);
		Log.d(TAG, "GCMIntentService init");
	}
	

	@Override
	protected void onError(Context ctx, String sError) {
		// TODO Auto-generated method stub
		Log.d(TAG, "Error: " + sError);
		
	}

	@Override
	protected void onMessage(Context ctx, Intent intent) {
		
		Log.d(TAG, "Message Received");
		
		String message = intent.getStringExtra("message");
		
		sendGCMIntent(ctx, message);
		
	}

	
	private void sendGCMIntent(Context ctx, String message) {
		
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction("GCM_RECEIVED_ACTION");
		
		broadcastIntent.putExtra("gcm", message);
		
		ctx.sendBroadcast(broadcastIntent);
		
	}
	
	
	@Override
	protected void onRegistered(Context ctx, String regId) {
		// TODO Auto-generated method stub
		// send regId to your server
		Log.d("Registered", regId);
		
	}

	@Override
	protected void onUnregistered(Context ctx, String regId) {
		// TODO Auto-generated method stub
		// send notification to your server to remove that regId
		Log.d("Unregistered", regId);
	}

}
