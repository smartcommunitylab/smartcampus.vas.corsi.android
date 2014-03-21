package eu.trentorise.smartcampus.android.studyMate.utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.android.studyMate.models.Notification;
import eu.trentorise.smartcampus.android.studyMate.notices.NoticesActivity;
import eu.trentorise.smartcampus.android.studyMate.start.MyUniActivity;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;
import eu.trentorise.smartcampus.studymate.R;

public class NotificationHandler extends
		AsyncTask<Void, Void, List<Notification>> {

	public Context context;
	String body;
	TextView textViewTitleNotices;
	private ListView lvAllNotices;
	private String dateString;
	private ArrayList<String> descriptionsList;
	@SuppressWarnings("unused")
	private ArrayList<String> datetimeList;
	@SuppressWarnings("unused")
	private ArrayList<String> usersList;
	@SuppressWarnings("unused")
	private SherlockFragmentActivity activity;
	private ProtocolCarrier mProtocolCarrier;
	private long fromDate;
	private String type;
	private TextView noNot;
	public NotificationHandler(Context applicationContext,
			TextView textViewTitleNotices, ListView lvAllNotices,
			SherlockFragmentActivity act, long fromDate, String type,
			TextView noNot) {
		this.context = applicationContext;
		this.textViewTitleNotices = textViewTitleNotices;
		this.lvAllNotices = lvAllNotices;
		this.activity = act;
		this.fromDate = fromDate;
		this.type = type;
		this.noNot = noNot;
	}

	
	private List<Notification> getNotification() throws Exception {

		// CommunicatorConnector cc = new CommunicatorConnector(
		// MyUniActivity.SERVER_URL, MyUniActivity.APP_ID);
		// Notifications list = cc.getNotificationsByApp(0L, 0, -1,
		// MyUniActivity.getAuthToken());
		// list.getNotifications();
		// List<Notification> not = list.getNotifications();

		List<Notification> not = new ArrayList<Notification>();

		mProtocolCarrier = new ProtocolCarrier(context,
				SmartUniDataWS.TOKEN_NAME);

		MessageRequest request = new MessageRequest(
				SmartUniDataWS.URL_WS_SMARTUNI,
				SmartUniDataWS.GET_WS_NOTIFICATIONS(type, fromDate));
		request.setMethod(Method.GET);

		MessageResponse response;
		try {
			response = mProtocolCarrier.invokeSync(request,
					SmartUniDataWS.TOKEN_NAME, MyUniActivity.getAuthToken());

			if (response.getHttpStatus() == 200) {

				body = response.getBody();
			} else {
				return null;
			}
		} catch (ConnectionException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (AACException e) {
			e.printStackTrace();
		}

		not = Utils.convertJSONToObjects(body, Notification.class);

		return not;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		
	}

	@Override
	protected void onPostExecute(List<Notification> notifies) {
		super.onPostExecute(notifies);

		NoticesActivity.pd.dismiss();

		if (notifies == null) {
			setVoidNotify();
		} else
			setListNotifications(notifies);
	}

	private void setListNotifications(final List<Notification> notifies) {
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
		}

		AdapterNoticesList adapterNotices = new AdapterNoticesList(context,
				R.id.listViewNotices, notifies);
		lvAllNotices.setAdapter(adapterNotices);

		// lvAllNotices.setOnItemClickListener(new
		// ListView.OnItemClickListener() {
		// @Override
		// public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
		// long arg3) {
		// // if (notifies.get(arg2).getType().equals("gds")) {
		// AlertDialog.Builder mAlert = new AlertDialog.Builder(activity);
		// mAlert.setTitle(notifies.get(arg2).getTitle());
		// mAlert.setMessage(notifies.get(arg2).getDescription());
		// mAlert.setPositiveButton("OK",
		// new DialogInterface.OnClickListener() {
		// public void onClick(DialogInterface dialog,
		// int which) {
		// // Editable value = input.getText();
		// Toast.makeText(context, "OK...",
		// Toast.LENGTH_SHORT).show();
		// // e.printStackTrace();
		// }
		// });
		// mAlert.setNegativeButton("CANCEL",
		// new DialogInterface.OnClickListener() {
		// public void onClick(DialogInterface dialog,
		// int which) {
		// // Editable value = input.getText();
		// Toast.makeText(context, "CANCEL...",
		// Toast.LENGTH_SHORT).show();
		// // e.printStackTrace();
		// }
		// });
		// AlertDialog alert = mAlert.create();
		//
		// alert.show();
		// }
		// // }
		// });
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
		try {
			return getNotification();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
