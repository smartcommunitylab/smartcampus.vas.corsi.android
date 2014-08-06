package eu.trentorise.smartcampus.android.studyMate.utilities;

import android.content.Context;
import eu.trentorise.smartcampus.pushservice.NotificationCenter;
import eu.trentorise.smartcampus.pushservice.NotificationDBHelper;

public class NotificationCenterGds extends NotificationCenter {
	
	private String message;
	private String gds;
	private Context mContext;
	private NotificationDBHelper mDB;

	public NotificationCenterGds(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}
	
//	@Override
//	public void publishNotification(Intent i, int pushId, Class resultActivity) {
//		message = i.getStringExtra("message");
//		insertNotification(message);
//		showNotification(pushId, message, resultActivity);
//	}
//	
//	@Override
//	public void showNotification(int pushId, String msg, Class resultActivity) {
//		NotificationCompat.Builder ncb = new NotificationCompat.Builder(
//				mContext);
//		ncb.setContentText(msg);
//		ncb.setSmallIcon(R.drawable.ic_launcher);
//		ncb.setContentTitle("title");
//		Intent resultIntent = new Intent(mContext, resultActivity);
//
//		TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
//		stackBuilder.addParentStack(resultActivity);
//		stackBuilder.addNextIntent(resultIntent);
//		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
//				PendingIntent.FLAG_UPDATE_CURRENT);
//		ncb.setContentIntent(resultPendingIntent);
//		ncb.setAutoCancel(true);
//		NotificationManager nm = (NotificationManager) mContext
//				.getSystemService(Context.NOTIFICATION_SERVICE);
//		nm.notify(pushId, ncb.build());
//	}

}
