package eu.trentorise.smartcampus.android.studyMate.utilities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.android.studyMate.start.MyUniActivity;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;

public class PostGDSresponseHandler extends AsyncTask<String[], Void, Boolean> {

	private ProtocolCarrier mProtocolCarrier;
	public Context context;
	String body;
	String[] ids;
	public SherlockFragmentActivity act;
	public static ProgressDialog pd;

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	public PostGDSresponseHandler(Context context, SherlockFragmentActivity act) {
		this.context = context;
		this.act = act;
	}

	@Override
	protected Boolean doInBackground(String[]... params) {
		ids = params[0];

		mProtocolCarrier = new ProtocolCarrier(context,
				SmartUniDataWS.TOKEN_NAME);

		MessageResponse response;

		MessageRequest request = new MessageRequest(
				SmartUniDataWS.URL_WS_SMARTUNI,
				SmartUniDataWS.POST_WS_MY_FEEDBACK);
		request.setMethod(Method.POST);

		Boolean resultPost = false;

		try {

			String eventoJSON = Utils.convertToJSON(ids);
			System.out.println(eventoJSON);
			request.setBody(eventoJSON);
			response = mProtocolCarrier.invokeSync(request,
					SmartUniDataWS.TOKEN_NAME, MyUniActivity.getAuthToken());

			if (response.getHttpStatus() == 200) {

				body = response.getBody();
				resultPost = Utils.convertJSONToObject(body, Boolean.class);

			}

		} catch (ConnectionException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (AACException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultPost;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);

		if (result.equals(true)) {
			Toast.makeText(context, "Ora fai parte del gruppo!", Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(
					context,
					"Ops! c'è stato un errore... Riprova più tardi",
					Toast.LENGTH_LONG).show();
		}
		//AddRatingFromCoursesPassed.pd.dismiss();
		act.finish();

	}

}
