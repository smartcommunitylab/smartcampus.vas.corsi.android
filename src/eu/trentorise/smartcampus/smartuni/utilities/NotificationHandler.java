package eu.trentorise.smartcampus.smartuni.utilities;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.os.AsyncTask;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.smartuni.models.Notifications;
import eu.trentorise.smartcampus.storage.Utils;

public class NotificationHandler extends AsyncTask {

	private ProtocolCarrier mProtocolCarrier;
	HttpClient httpClient = new DefaultHttpClient();
	private static final String AUTH_TOKEN = "aee58a92-d42d-42e8-b55e-12e4289586fc";
	public Context context;
	public String appToken = "test smartcampus";

	public NotificationHandler(Context applicationContext) {
		this.context = applicationContext;
	}

	public Notifications getDataNotification() throws eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException {		
			mProtocolCarrier = new ProtocolCarrier(context, appToken);
			MessageRequest request = new MessageRequest(
					"http://smartcampus_vas_corsi_web.app.smartcampuslab.it", "gettime");
			request.setMethod(Method.GET);
			MessageResponse response;
			
			try {
				response = mProtocolCarrier
				.invokeSync(request, appToken, AUTH_TOKEN);
				
				if (response.getHttpStatus() == HttpStatus.SC_OK) {
					String body = response.getBody();
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
	protected Object doInBackground(Object... params) {
		// TODO Auto-generated method stub
		return null;
	}

}
