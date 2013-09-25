package eu.trentorise.smartcampus.studyMate.utilities;

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
import eu.trentorise.smartcampus.studyMate.models.Commento;

public class AddFeedbackHandler extends AsyncTask<Commento, Void, Commento> {

	private ProtocolCarrier mProtocolCarrier;
	public Context context;
	String body;
	Commento commento;
	
	public AddFeedbackHandler(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	@Override
	protected Commento doInBackground(Commento... params) {
		commento = params[0];

		mProtocolCarrier = new ProtocolCarrier(context,
				SmartUniDataWS.TOKEN_NAME);

		MessageRequest request = new MessageRequest(
				SmartUniDataWS.URL_WS_SMARTUNI,
				SmartUniDataWS.POST_WS_MY_FEEDBACK);
		request.setMethod(Method.POST);

		MessageResponse response;
		try {

			String eventoJSON = Utils.convertToJSON(commento);

			request.setBody(eventoJSON);
			response = mProtocolCarrier.invokeSync(request,
					SmartUniDataWS.TOKEN_NAME, SmartUniDataWS.TOKEN);

			if (response.getHttpStatus() == 200) {

				return commento;

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
	protected void onPostExecute(Commento result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);

	}

}
