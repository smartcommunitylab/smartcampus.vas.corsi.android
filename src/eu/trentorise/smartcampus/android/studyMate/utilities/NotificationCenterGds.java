package eu.trentorise.smartcampus.android.studyMate.utilities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
	private static final String GDS_NAME_KEY = "gds_name";
	public static final String DATE_KEY = "date";

	public NotificationCenterGds(Context ctx) {
		super(ctx);
		mContext = ctx.getApplicationContext();
		mDB = new NotificationDBGdsHelper(mContext);
	}

	@Override
	public void publishNotification(Intent i, int pushId,
			Class<? extends Activity> resultActivity) {
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

	public List<PushNotificationGds> getGdsNotifications() {
		SQLiteDatabase db = mDB.getReadableDatabase();
		String sql = " select * from "
				+ NotificationDBGdsHelper.DB_TABLE_NOTIFICATION + " order by "
				+ NotificationDBGdsHelper.DATE_KEY + " DESC";
		ArrayList<PushNotificationGds> notifs = new ArrayList<PushNotificationGds>();
		try {
			db.beginTransaction();
			Cursor cursor = db.rawQuery(sql, null);
			db.setTransactionSuccessful();

			while (cursor.moveToNext()) {
				PushNotificationGds notif = new PushNotificationGds(
						cursor.getString(cursor
								.getColumnIndex(NotificationDBGdsHelper.MESSAGE_KEY)),
						cursor.getLong(cursor
								.getColumnIndex(NotificationDBGdsHelper.GDS_KEY)),
						cursor.getString(cursor
								.getColumnIndex(NotificationDBGdsHelper.GDS_NAME)),
						cursor.getInt(cursor
								.getColumnIndex(NotificationDBGdsHelper.READ_KEY)) > 0 ? true
								: false,
						new Date(
								cursor.getLong(cursor
										.getColumnIndex(NotificationDBGdsHelper.DATE_KEY))));

				notifs.add(notif);
			}
		} catch (Exception ex) {
			Log.e(this.getClass().getName(), ex.toString());
		} finally {
			db.endTransaction();
		}

		return notifs;
	}
	
	
	public List<PushNotificationGds> getGdsNotificationsOfGds(long gdsId) {
		SQLiteDatabase db = mDB.getReadableDatabase();
		String sql = " select * from "
				+ NotificationDBGdsHelper.DB_TABLE_NOTIFICATION + " order by "
				+ NotificationDBGdsHelper.DATE_KEY + " DESC";
		ArrayList<PushNotificationGds> notifs = new ArrayList<PushNotificationGds>();
		try {
			db.beginTransaction();
			Cursor cursor = db.rawQuery(sql, null);
			db.setTransactionSuccessful();

			while (cursor.moveToNext()) {
				PushNotificationGds notif = new PushNotificationGds(
						cursor.getString(cursor
								.getColumnIndex(NotificationDBGdsHelper.MESSAGE_KEY)),
						cursor.getLong(cursor
								.getColumnIndex(NotificationDBGdsHelper.GDS_KEY)),
						cursor.getString(cursor
								.getColumnIndex(NotificationDBGdsHelper.GDS_NAME)),
						cursor.getInt(cursor
								.getColumnIndex(NotificationDBGdsHelper.READ_KEY)) > 0 ? true
								: false,
						new Date(
								cursor.getLong(cursor
										.getColumnIndex(NotificationDBGdsHelper.DATE_KEY))));

				notifs.add(notif);
			}
		} catch (Exception ex) {
			Log.e(this.getClass().getName(), ex.toString());
		} finally {
			db.endTransaction();
		}

		return notifs;
	}
	

	@Override
	public void deleteNotification(PushNotification notif) {
		super.deleteNotification(notif);
	}
	
	public void deleteNotificationGds(Long gdsId) {
		SQLiteDatabase db = mDB.getWritableDatabase();
		try {
			db.beginTransaction();
			db.delete(NotificationDBGdsHelper.DB_TABLE_NOTIFICATION,
					NotificationDBGdsHelper.GDS_KEY + "=?",
					new String[] { gdsId + "" });
			db.setTransactionSuccessful();
		} catch (Exception ex) {
			Log.e(this.getClass().getName(), ex.toString());
		} finally {
			db.endTransaction();
		}
		db.close();
	}
	
	@Override
	public void setNotificationRead(int id) {
		super.setNotificationRead(id);
	}

	@Override
	public void markNotificationAsRead(PushNotification notif) {
		super.markNotificationAsRead(notif);
	}

	@Override
	public void markAllNotificationAsRead() {
		super.markAllNotificationAsRead();
	}

	@Override
	public int getUnreadNotificationCount() {
		return super.getUnreadNotificationCount();
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
			ncb.setContentText(notif.getGdsName());
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

		return new PushNotificationGds(i.getStringExtra(MESSAGE_KEY),
				Long.valueOf(i.getStringExtra(GDS_ID_KEY)), i.getStringExtra(GDS_NAME_KEY), false, new Date(Long.parseLong(i.getStringExtra(
						DATE_KEY))));
	}

	public String getJourneyId(String json) {
		return super.getJourneyId(json);
	}
}
