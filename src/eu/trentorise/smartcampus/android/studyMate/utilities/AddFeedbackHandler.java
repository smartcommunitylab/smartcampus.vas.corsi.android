package eu.trentorise.smartcampus.android.studyMate.utilities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.android.studyMate.finder.FindHomeCourseActivity;
import eu.trentorise.smartcampus.android.studyMate.models.Commento;
import eu.trentorise.smartcampus.android.studyMate.models.Corso;
import eu.trentorise.smartcampus.android.studyMate.models.Studente;
import eu.trentorise.smartcampus.android.studyMate.rate.AddRatingFromCoursesPassed;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;

public class AddFeedbackHandler extends AsyncTask<Commento, Void, Boolean> {

	private ProtocolCarrier mProtocolCarrier;
	public Context context;
	String body;
	Commento commento;
	public SherlockFragmentActivity act;
	public static ProgressDialog pd;

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
//		new ProgressDialog(context);
//		pd = ProgressDialog.show(act, "Informazioni del corso di ", "Caricamento...");
	}
	
	public AddFeedbackHandler(Context context, SherlockFragmentActivity act) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.act = act;
	}

	@Override
	protected Boolean doInBackground(Commento... params) {
		commento = params[0];

		mProtocolCarrier = new ProtocolCarrier(context,
				SmartUniDataWS.TOKEN_NAME);

		MessageResponse response;

		MessageRequest request = new MessageRequest(SmartUniDataWS.URL_WS_SMARTUNI,
				SmartUniDataWS.POST_WS_MY_FEEDBACK);
		request.setMethod(Method.POST);
		
		Boolean resultPost = false;

		try {

			String eventoJSON = Utils.convertToJSON(commento);
			System.out.println(eventoJSON);
			request.setBody(eventoJSON);
			response = mProtocolCarrier.invokeSync(request,
					SmartUniDataWS.TOKEN_NAME, SmartUniDataWS.TOKEN);

			
			
			if (response.getHttpStatus() == 200) {

				body = response.getBody();
				resultPost = Utils.convertJSONToObject(body, Boolean.class);

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

		return resultPost;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
		if(result.equals(true)){
			Toast.makeText(context,
				"Voto Aggiunto!", Toast.LENGTH_LONG).show();
		}else{
			Toast.makeText(context,
					"Ops! c'è stato un errore. La tua valutazione non è stata salvata", Toast.LENGTH_LONG).show();
		}
		AddRatingFromCoursesPassed.pd.dismiss();
		act.finish();
		

	}

}
