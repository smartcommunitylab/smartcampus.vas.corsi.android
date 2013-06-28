package eu.trentorise.smartcampus.smartuni.utilities;

import java.util.List;

import smartcampus.android.template.standalone.OverviewFragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import eu.trentorise.smartcampus.ac.authenticator.AMSCAccessProvider;
import eu.trentorise.smartcampus.ac.model.UserData;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;
import eu.trentorise.smartcampus.smartuni.models.Event;
import eu.trentorise.smartcampus.smartuni.models.Evento;

public class EventsHandler extends AsyncTask<Void, Void, List<Evento>>{

	
	private ProtocolCarrier mProtocolCarrier;
	public Context context;
	String body;
	String id_course = null;
	private AMSCAccessProvider mAccessProvider;
	
	
	public EventsHandler(Context applicationContext, String id_course) {
		this.context = applicationContext;
		this.id_course = id_course; 
	}
	
	public EventsHandler(Context applicationContext) {
		this.context = applicationContext;
	}

	private List<Evento> getAllEventsOfCourse(String id_course) {
		
		mProtocolCarrier = new ProtocolCarrier(context, SmartUniDataWS.TOKEN_NAME);

		MessageRequest request = new MessageRequest(
				SmartUniDataWS.URL_WS_SMARTUNI, SmartUniDataWS.GET_WS_EVENTS_OF_COURSE(id_course));
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
		
		return Utils.convertJSONToObjects(body, Evento.class);
	}

	
	private List<Evento> getAllPersonalEvents() {
		
		
		mAccessProvider = new AMSCAccessProvider();
		UserData data = mAccessProvider.readUserData(context, null);
		data.getUserId();
		
		mProtocolCarrier = new ProtocolCarrier(context, SmartUniDataWS.TOKEN_NAME);

		MessageRequest request = new MessageRequest(
				SmartUniDataWS.URL_WS_SMARTUNI, SmartUniDataWS.GET_WS_EVENTS_OF_COURSE(id_course));
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
		
		return Utils.convertJSONToObjects(body, Evento.class);
	}

	
	
	@Override
	protected List<Evento> doInBackground(Void... params) {
		// TODO Auto-generated method stub
		//return getAllEventsOfCourse(id_course);
		//return getAllPersonalEvents();
		return getAllEventsOfCourse("1");
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();

	}
	@Override
	protected void onPostExecute(List<Evento> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
	}
}
