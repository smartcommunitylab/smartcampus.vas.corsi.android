package eu.trentorise.smartcampus.android.studyMate;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;













import com.google.android.gcm.GCMBaseIntentService;

import eu.trentorise.smartcampus.pushservice.NotificationCenter;

public class GCMIntentService extends GCMBaseIntentService {

	private static final String PROJECT_ID = "674612024423"; //to add project id
    
	private static final String TAG = "GCMService";
	
	
	public GCMIntentService()
	{
		super(PROJECT_ID);
		Log.d(TAG, "GCMService init");
	}
	

	@Override
	protected void onError(Context ctx, String sError) {
		Log.d(TAG, "Error: " + sError);

	}

	@Override
	protected void onMessage(Context ctx, Intent intent) {

		Log.d(TAG, "Message Received");

//		new NotificationCenter(ctx).publishNotification(intent,
//				NOTIFICATION_ID, MyUniActivity.class);
//
//		
				
		String message = intent.getStringExtra("message");
		
		sendGCMIntent(ctx, message);
		
		
	}

	@Override
	protected void onRegistered(Context ctx, String regId) {
		// send regId to your server

	}

	@Override
	protected void onUnregistered(Context ctx, String regId) {
		// send notification to your server to remove that regId
	}

	
	private void sendGCMIntent(Context ctx, String message) {
		
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction("GCM_RECEIVED_ACTION");
		
		broadcastIntent.putExtra("gcm", message);
		
		ctx.sendBroadcast(broadcastIntent);
		
	}
	
}
