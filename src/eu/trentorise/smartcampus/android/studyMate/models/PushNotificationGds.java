package eu.trentorise.smartcampus.android.studyMate.models;

import java.util.Date;

import android.content.ContentValues;
import eu.trentorise.smartcampus.android.studyMate.utilities.NotificationDBGdsHelper;

public class PushNotificationGds {

	private String message;
	private Long gdsId;
	private String gdsName;
	private boolean mRead = false;
	private Date mDate;

	public PushNotificationGds(String msg, Long gds_id, String gds_name,
			boolean read, Date date) {

		this.message = msg;
		this.gdsId = gds_id;
		this.gdsName = gds_name;
		this.mRead = read;
		this.mDate = date;

	}

	public ContentValues toContentValues() {
		ContentValues out = new ContentValues();

		if (message == null || gdsId == null)
			throw new RuntimeException("Notification's empy");

		out.put(NotificationDBGdsHelper.MESSAGE_KEY, message);
		out.put(NotificationDBGdsHelper.GDS_KEY, gdsId);
		out.put(NotificationDBGdsHelper.GDS_NAME, gdsName);
		out.put(NotificationDBGdsHelper.READ_KEY, mRead ? 1 : 0);
		if (mDate != null)
			out.put(NotificationDBGdsHelper.DATE_KEY, mDate.getTime());
		else
			out.put(NotificationDBGdsHelper.DATE_KEY, new Date().getTime());

		return out;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getGdsId() {
		return gdsId;
	}

	public void setGdsId(Long gdsId) {
		this.gdsId = gdsId;
	}

	public boolean ismRead() {
		return mRead;
	}

	public void setmRead(boolean mRead) {
		this.mRead = mRead;
	}

	public String getGdsName() {
		return gdsName;
	}

	public void setGdsName(String gdsName) {
		this.gdsName = gdsName;
	}

	public Date getmDate() {
		return mDate;
	}

	public void setmDate(Date mDate) {
		this.mDate = mDate;
	}

}
