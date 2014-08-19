package eu.trentorise.smartcampus.android.studyMate.utilities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NotificationDBGdsHelper extends SQLiteOpenHelper {

	static final String DB_NAME = "notificationsdb";

	static final int DB_VERSION = 1;

	public static final  String DB_TABLE_NOTIFICATION = "tb_notification";

	static final String ID_KEY = "not_id";

	public static final String MESSAGE_KEY = "message";
	public static final String GDS_KEY = "gds";


	// 0 for read
	// 1 for unread
	public static final String READ_KEY = "read";

	

	public NotificationDBGdsHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	private static final String CREATE_NOTIFICATION_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ DB_TABLE_NOTIFICATION
			+ " ("
			+ ID_KEY
			+ " integer PRIMARY_KEY, "
			+ MESSAGE_KEY
			+ " text not null, "
			+ GDS_KEY
			+ " text not null, "
			+ READ_KEY
			+ " integer DEFAULT 0) ";

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_NOTIFICATION_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO nothing for now.
	}

	

}

