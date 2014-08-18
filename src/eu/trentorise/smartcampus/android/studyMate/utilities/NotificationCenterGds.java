package eu.trentorise.smartcampus.android.studyMate.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import eu.trentorise.smartcampus.android.studyMate.models.PushNotificationGds;
import eu.trentorise.smartcampus.pushservice.NotificationCenter;
import eu.trentorise.smartcampus.pushservice.NotificationDBHelper;
import eu.trentorise.smartcampus.pushservice.PushNotification;
import eu.trentorise.smartcampus.pushservice.R;

public class NotificationCenterGds extends NotificationCenter {
	
	private String message;
	private String gds;
	private Context mContext;
	private NotificationDBGdsHelper mDB;
	private static final String MESSAGE_KEY = "message";
	private static final String GDS_ID_KEY = "gds";

	public NotificationCenterGds(Context ctx) {
		super(ctx);
		mContext = ctx.getApplicationContext();
		mDB = new NotificationDBGdsHelper(mContext);
	}
	
	@Override
	public void publishNotification(Intent i, int pushId, Class<? extends Activity> resultActivity) {
		PushNotificationGds notif = buildPushNotification(i);
		insertNotification(notif);
		showSystemNotification(pushId, notif, resultActivity);
	}
	

	public void insertNotification(PushNotificationGds notif) {
		SQLiteDatabase db = mDB.getWritableDatabase();
		try {
			db.beginTransaction();
			db.insert(NotificationDBGdsHelper.DB_TABLE_NOTIFICATION, null,
					notif.toContentValues());
			db.setTransactionSuccessful();
		} catch (Exception ex) {
			Log.e(this.getClass().getName(), ex.toString());
		} finally {
			db.endTransaction();
		}
		db.close();
	}
	

	
	public void showSystemNotification(int pushId, PushNotificationGds notif,
			Class<? extends Activity> resultActivity) {
		NotificationCompat.Builder ncb = new NotificationCompat.Builder(
				mContext);
		ncb.setSmallIcon(R.drawable.ic_launcher);
		int unread = getUnreadNotificationCount();
		if (unread > 1) {
			ncb.setContentText(mContext.getResources().getString(
					R.string.push_notification_msg));
			ncb.setContentTitle(mContext.getResources().getString(
					R.string.push_notification_msg));
			ncb.setContentInfo(unread + "");
		} else {
			ncb.setContentText(notif.getGdsId());
			ncb.setContentTitle(notif.getMessage());
		}

		Intent resultIntent = new Intent(mContext, resultActivity);

		TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
		stackBuilder.addParentStack(resultActivity);
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
				PendingIntent.FLAG_UPDATE_CURRENT);
		ncb.setContentIntent(resultPendingIntent);
		ncb.setAutoCancel(true);
		NotificationManager nm = (NotificationManager) mContext
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification n = ncb.build();
		n.defaults = Notification.DEFAULT_ALL;
		nm.notify(pushId, n);
	}

	
	private PushNotificationGds buildPushNotification(Intent i) {
		
		return new PushNotificationGds(i.getStringExtra(MESSAGE_KEY),i.getStringExtra(GDS_ID_KEY));
	}
	
	public String getJourneyId(String json) {
		return super.getJourneyId(json);
	}
}
