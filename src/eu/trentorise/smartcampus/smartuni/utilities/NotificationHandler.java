package eu.trentorise.smartcampus.smartuni.utilities;

import java.util.List;

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
import android.content.Context;
import android.os.AsyncTask;

public class NotificationHandler extends AsyncTask<Void,Void,List<Notice>> {

	
	private ProtocolCarrier mProtocolCarrier;
	public Context context;
	public String appToken = "test smartcampus";
	public String authToken = "aee58a92-d42d-42e8-b55e-12e4289586fc";
	String body;

	public NotificationHandler(Context applicationContext) {
		this.context = applicationContext;
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
	protected void onCancelled() {
		// TODO Auto-generated method stub
		super.onCancelled();
	}



	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}

	@Override
	protected List<Notice> doInBackground(Void... params) {
		// TODO Auto-generated method stub
		return getNotification();
	}
}
