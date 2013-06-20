package eu.trentorise.smartcampus.smartuni.utilities;

import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;
import eu.trentorise.smartcampus.smartuni.models.Event;

public class EventsHandler extends AsyncTask<Void, Void, List<Event>>{

	
	private ProtocolCarrier mProtocolCarrier;
	public Context context;
	String body;
	String id_course;

	
	
	public EventsHandler(Context applicationContext, String id_course) {
		this.context = applicationContext;
		this.id_course = id_course; 
	}

	private List<Event> getAllEventsOfCourse(String id_course) {
		
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
		
		return Utils.convertJSONToObjects(body, Event.class);
	}


	
	
	@Override
	protected List<Event> doInBackground(Void... params) {
		// TODO Auto-generated method stub
		return getAllEventsOfCourse(id_course);
	}
}
