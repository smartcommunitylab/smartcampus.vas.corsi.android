package eu.trentorise.smartcampus.smartuni.utilities;

import smartcampus.android.template.standalone.AddRateActivity;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.RatingBar;
import android.widget.TextView;
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
import eu.trentorise.smartcampus.smartuni.models.Commento;
import eu.trentorise.smartcampus.smartuni.models.Corso;
import eu.trentorise.smartcampus.smartuni.models.Studente;

public class LoaderFeedbackData extends AsyncTask<Commento, Void, Commento>{

	private ProtocolCarrier mProtocolCarrier;
	public Context context;
	String body;
	
	public LoaderFeedbackData(Context applicationContext) {
		// TODO Auto-generated constructor stub
		this.context = applicationContext;
	}
	
	@Override
	protected Commento doInBackground(Commento... params) {
		mProtocolCarrier = new ProtocolCarrier(context,
				SmartUniDataWS.TOKEN_NAME);
		
		
		// prendo i dati dell'utente
		AMSCAccessProvider mAccessProvider = new AMSCAccessProvider();
		UserData profile = mAccessProvider.readUserData(context, null);
		
		
		
		MessageRequest request = new MessageRequest(
				SmartUniDataWS.URL_WS_SMARTUNI,
				SmartUniDataWS.GET_WS_FEEDBACK_OF_STUDENT(Long.parseLong(profile.getUserId()), CoursesHandler.corsoSelezionato.getId()));
		request.setMethod(Method.GET);

		MessageResponse response;
		try {
			response = mProtocolCarrier.invokeSync(request,
					SmartUniDataWS.TOKEN_NAME, SmartUniDataWS.TOKEN);

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
		
		return Utils.convertJSONToObject(body, Commento.class);
	}

}
