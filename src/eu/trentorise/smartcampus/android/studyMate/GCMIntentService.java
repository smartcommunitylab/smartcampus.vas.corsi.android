package eu.trentorise.smartcampus.android.studyMate;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;

import eu.trentorise.smartcampus.android.studyMate.gruppi_studio.Lista_GDS_activity;
import eu.trentorise.smartcampus.android.studyMate.utilities.NotificationCenterGds;

public class GCMIntentService extends GCMBaseIntentService {

	private static final String PROJECT_ID = "674612024423"; // to add project
																// id

	private static final String TAG = "GCMService";

	private static final int NOTIFICATION_ID = 7204;

	public GCMIntentService() {
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

		new NotificationCenterGds(ctx).publishNotification(intent,
				NOTIFICATION_ID, Lista_GDS_activity.class);

		String message = intent.getStringExtra("message");
		String name = intent.getStringExtra("name");
		String gds = intent.getStringExtra("gds");
		String gds_name = intent.getStringExtra("gds_name");
		String date = intent.getStringExtra("date");

		sendGCMIntent(ctx, message, name, gds, gds_name, date);

	}

	@Override
	protected void onRegistered(Context ctx, String regId) {
		// send regId to your server

	}

	@Override
	protected void onUnregistered(Context ctx, String regId) {
		// send notification to your server to remove that regId
	}

	private void sendGCMIntent(Context ctx, String message, String name,
			String gds, String gds_name, String date) {

		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction("GCM_RECEIVED_ACTION");

		broadcastIntent.putExtra("message", message);
		broadcastIntent.putExtra("name", name);
		broadcastIntent.putExtra("gds", gds);
		broadcastIntent.putExtra("gds_name", gds_name);
		broadcastIntent.putExtra("date", date);

		ctx.sendBroadcast(broadcastIntent);

	}

}
