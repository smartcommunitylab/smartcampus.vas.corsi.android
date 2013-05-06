package eu.trentorise.smartcampus.smartuni.utilities;

import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;

import eu.trentorise.smartcampus.smartuni.models.Notifications;
import android.content.Context;
import android.os.AsyncTask;

public class NotificationHandler extends AsyncTask {

	
	private ProtocolCarrier mProtocolCarrier;
	private static final String URL = "http://smartcampuswebifame.app.smartcampuslab.it/getsoldi";
	private static final String auth_token = "AUTH_TOKEN";
	private static final String token_value = "aee58a92-d42d-42e8-b55e-12e4289586fc";
	public Context context;
	public String appToken = "test smartcampus";
	public String authToken = "aee58a92-d42d-42e8-b55e-12e4289586fc";
	public Notifications notifies;
	

	public NotificationHandler(Context applicationContext) {
		this.context = applicationContext;
	}

	private Notifications getNotification() {
		
		mProtocolCarrier = new ProtocolCarrier(context, appToken);

		MessageRequest request = new MessageRequest(
				"http://smartcampuswebifame.app.smartcampuslab.it", "getsoldi");
		request.setMethod(Method.GET);

		MessageResponse response;
		try {
			response = mProtocolCarrier.invokeSync(request, appToken, authToken);

			if (response.getHttpStatus() == 200) {

				String body = response.getBody();
				body = "<notifications>" +
						"<notice> " +
						"<description> ciao </description>" +
						"<datetime> 14/04/2013 12:12:01 </datetime>" +
						"<user> utente1 </user>" +
						"</notice>" +
						"</notifications>";
				return Utils.convertJSONToObject(body, Notifications.class);

			} else {

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
		return null;
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
	protected Object doInBackground(Object... params) {
		// TODO Auto-generated method stub
		return null;
	}
}
