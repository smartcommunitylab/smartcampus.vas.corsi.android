package eu.trentorise.smartcampus.studyMate.utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import smartcampus.android.studyMate.notices.NoticesActivity;
import smartcampus.android.studyMate.start.MyUniActivity;
import smartcampus.android.template.standalone.R;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.TextView;
import eu.trentorise.smartcampus.communicator.CommunicatorConnector;
import eu.trentorise.smartcampus.communicator.model.Notification;
import eu.trentorise.smartcampus.communicator.model.Notifications;

public class NotificationHandler extends
		AsyncTask<Void, Void, List<Notification>> {

	public Context context;
	String body;
	TextView textViewTitleNotices;
	private ListView lvAllNotices;
	private String dateString;
	// private ArrayList<Notice> notificationsList;
	private ArrayList<String> descriptionsList;
	@SuppressWarnings("unused")
	private ArrayList<String> datetimeList;
	@SuppressWarnings("unused")
	private ArrayList<String> usersList;

	// private static ProgressDialog pd;

	public NotificationHandler(Context applicationContext,
			TextView textViewTitleNotices, ListView lvAllNotices) {
		this.context = applicationContext;
		this.textViewTitleNotices = textViewTitleNotices;
		this.lvAllNotices = lvAllNotices;
	}

	private List<Notification> getNotification() throws Exception {

		CommunicatorConnector cc = new CommunicatorConnector(
				MyUniActivity.SERVER_URL, MyUniActivity.APP_ID);
		Notifications list = cc.getNotificationsByApp(0L, 0, -1,
				MyUniActivity.userAuthToken);
		list.getNotifications();
		List<Notification> not = list.getNotifications();

		return not;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();

	}

	@Override
	protected void onPostExecute(List<Notification> notifies) {
		// TODO Auto-generated method stub
		super.onPostExecute(notifies);

		NoticesActivity.pd.dismiss();

		if (notifies == null) {
			setVoidNotify();
		} else
			setListNotifications(notifies);
	}

	private void setListNotifications(List<Notification> notifies) {

		for (Notification n : notifies) {
			textViewTitleNotices.setText(n.getTitle());

		}

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss",
				Locale.ITALY);
		Date date = new Date();
		dateString = dateFormat.format(date);

		textViewTitleNotices.setText(R.string.notices_string_titlelistExist);
		textViewTitleNotices.setText(textViewTitleNotices.getText() + " "
				+ dateString);

		Iterator<Notification> i = notifies.iterator();

		datetimeList = new ArrayList<String>();
		usersList = new ArrayList<String>();
		descriptionsList = new ArrayList<String>();

		while (i.hasNext()) {
			Notification t = new Notification();
			t = (Notification) i.next();

			descriptionsList.add(t.getDescription());
			/*
			 * datetimeList.add(t.getDatetime()); usersList.add(t.getUser());
			 */
		}

		AdapterNoticesList adapterNotices = new AdapterNoticesList(context,
				R.id.listViewNotices, notifies);
		lvAllNotices.setAdapter(adapterNotices);

	}

	private void setVoidNotify() {
		textViewTitleNotices.setText(R.string.notices_string_titlelist);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss",
				Locale.ITALY);
		Date date = new Date();
		dateString = dateFormat.format(date);

		textViewTitleNotices.setText(R.string.notices_string_titlelistExist);
		textViewTitleNotices.setText(textViewTitleNotices.getText() + " "
				+ dateString);
	}

	@Override
	protected List<Notification> doInBackground(Void... params) {
		// TODO Auto-generated method stub
		try {
			return getNotification();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
