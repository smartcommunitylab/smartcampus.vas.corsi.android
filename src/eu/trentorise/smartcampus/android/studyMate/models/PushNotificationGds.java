package eu.trentorise.smartcampus.android.studyMate.models;

import java.util.Date;

import android.content.ContentValues;
import eu.trentorise.smartcampus.android.studyMate.utilities.NotificationDBGdsHelper;
public class PushNotificationGds {

	private String message;
	private String gdsId;
	private boolean mRead = false;
	
	public PushNotificationGds(String msg, String gds_id, boolean read) {
		
		this.message = msg;
		this.gdsId = gds_id;
		this.mRead = read;
		
	}
	
	public ContentValues toContentValues() {
		ContentValues out = new ContentValues();

		if (message == null || gdsId == null)
			throw new RuntimeException("Notification's empy");

		out.put(NotificationDBGdsHelper.MESSAGE_KEY,message);
		out.put(NotificationDBGdsHelper.GDS_KEY,gdsId);
		out.put(NotificationDBGdsHelper.READ_KEY, mRead ? 1 : 0);
		
		return out;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getGdsId() {
		return gdsId;
	}

	public void setGdsId(String gdsId) {
		this.gdsId = gdsId;
	}

	public boolean ismRead() {
		return mRead;
	}

	public void setmRead(boolean mRead) {
		this.mRead = mRead;
	}
	
	
	

}
