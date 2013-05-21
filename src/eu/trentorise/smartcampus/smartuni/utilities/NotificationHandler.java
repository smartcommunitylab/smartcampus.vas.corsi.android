package eu.trentorise.smartcampus.smartuni.utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import smartcampus.android.template.standalone.NoticesActivity;
import smartcampus.android.template.standalone.R;

import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;

import eu.trentorise.smartcampus.smartuni.models.Notice;
import eu.trentorise.smartcampus.smartuni.models.Notifications;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.TextView;

public class NotificationHandler extends AsyncTask<Void,Void,List<Notice>> {

	
	private ProtocolCarrier mProtocolCarrier;
	public Context context;
	public String appToken = "test smartcampus";
	public String authToken = "aee58a92-d42d-42e8-b55e-12e4289586fc";
	String body;
	TextView textViewTitleNotices;
	private ListView lvAllNotices;
	private String dateString;
	private ArrayList<Notice> notificationsList;
	private ArrayList<String> descriptionsList;
	private ArrayList<String> datetimeList;
	private ArrayList<String> usersList;
	private static ProgressDialog pd;
	
	
	public NotificationHandler(Context applicationContext, TextView textViewTitleNotices, ListView lvAllNotices) {
		this.context = applicationContext;
		this.textViewTitleNotices = textViewTitleNotices;
		this.lvAllNotices = lvAllNotices;
	}

	private List<Notice> getNotification() {
		
		mProtocolCarrier = new ProtocolCarrier(context, SmartUniDataWS.TOKEN_NAME);

		MessageRequest request = new MessageRequest(
				SmartUniDataWS.URL_WS_SMARTUNI, SmartUniDataWS.GET_WS_NOTIFICATIONS);
		request.setMethod(Method.GET);

		MessageResponse response;
		try {
			response = mProtocolCarrier.invokeSync(request, SmartUniDataWS.TOKEN_NAME, SmartUniDataWS.TOKEN);

			if (response.getHttpStatus() == 200) {

				body = response.getBody();				

			} else {
				return null;
			}
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Utils.convertJSONToObjects(body, Notice.class);
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		
		
	}

	@Override
	protected void onPostExecute(List<Notice> notifies) {
		// TODO Auto-generated method stub
		super.onPostExecute(notifies);
		
		NoticesActivity.pd.dismiss();
		
		if (notifies == null) {
			setVoidNotify();
		} else
			setListNotifications(notifies);
	}

	
	private void setListNotifications(List<Notice> notifies) {

		textViewTitleNotices.setText(R.string.notices_string_titlelist);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss",
				Locale.ITALY);
		Date date = new Date();
		dateString = dateFormat.format(date);

		textViewTitleNotices.setText(R.string.notices_string_titlelistExist);
		textViewTitleNotices.setText(textViewTitleNotices.getText() + " "
				+ dateString);

		Iterator<Notice> i = notifies.iterator();

		datetimeList = new ArrayList<String>();
		usersList = new ArrayList<String>();
		descriptionsList = new ArrayList<String>();

		while (i.hasNext()) {
			Notice t = new Notice();
			t = (Notice) i.next();

			descriptionsList.add(t.getDescription());
			/*
			 * datetimeList.add(t.getDatetime()); usersList.add(t.getUser());
			 */
		}

		AdapterNoticesList adapterNotices = new AdapterNoticesList(
				context, R.id.listViewNotices, notifies);
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
	protected List<Notice> doInBackground(Void... params) {
		// TODO Auto-generated method stub
		return getNotification();
	}
}

