package eu.trentorise.smartcampus.android.studyMate.utilities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.google.android.gms.internal.ac;

import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.android.studyMate.MyUniActivity;
import eu.trentorise.smartcampus.android.studyMate.R;
import eu.trentorise.smartcampus.android.studyMate.models.Notification;
import eu.trentorise.smartcampus.android.studyMate.notices.NoticesActivity;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;

public class NotificationHandler extends
		AsyncTask<Void, Void, List<Notification>> {

	public Context context;
	String body;
	TextView textViewTitleNotices;
	private ListView lvAllNotices;
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
	@SuppressWarnings("unused")
	private TextView noNot;
	private TextView voidNot;

	public NotificationHandler(Context applicationContext, ListView lvAllNotices,
			SherlockFragmentActivity act, long fromDate, String type,
			TextView noNot, TextView voidNot) {
		this.context = applicationContext;
		this.lvAllNotices = lvAllNotices;
		this.activity = act;
		this.fromDate = fromDate;
		this.type = type;
		this.noNot = noNot;
		this.voidNot = voidNot;
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
			// Toast.makeText(context, "test", Toast.LENGTH_SHORT).show();
		} else
			setListNotifications(notifies);
	}

	private void setListNotifications(final List<Notification> notifies) {
		

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
	}

	private void setVoidNotify() {
		lvAllNotices.setVisibility(View.GONE);
		voidNot.setText(context.getResources().getString(R.string.notification_void));
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
