package eu.trentorise.smartcampus.android.studyMate.utilities;

import android.content.Context;
import android.os.AsyncTask;
import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.android.studyMate.MyUniActivity;
import eu.trentorise.smartcampus.android.studyMate.models.Evento;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;

public class PostEvent extends AsyncTask<Void, Void, Evento> {

	private ProtocolCarrier mProtocolCarrier;
	public Context context;
	String body;
	Evento evento;

	public PostEvent(Context applicationContext, Evento evento) {
		this.context = applicationContext;
		this.evento = evento;
	}

	@Override
	protected Evento doInBackground(Void... arg0) {

		mProtocolCarrier = new ProtocolCarrier(context,
				SmartUniDataWS.TOKEN_NAME);

		MessageRequest request = new MessageRequest(
				SmartUniDataWS.URL_WS_SMARTUNI, SmartUniDataWS.POST_NEW_EVENT);
		request.setMethod(Method.POST);

		MessageResponse response;
		try {

			String eventoJSON = Utils.convertToJSON(evento);

			request.setBody(eventoJSON);
			response = mProtocolCarrier.invokeSync(request,
					SmartUniDataWS.TOKEN_NAME, MyUniActivity.getAuthToken());

			if (response.getHttpStatus() == 200) {

				return evento;

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

		return null;
	}

}
