package eu.trentorise.smartcampus.android.studyMate.utilities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.android.studyMate.MyUniActivity;
import eu.trentorise.smartcampus.android.studyMate.R;
import eu.trentorise.smartcampus.android.studyMate.models.Commento;
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
		super.onPreExecute();
	}

	public AddFeedbackHandler(Context context, SherlockFragmentActivity act) {
		this.context = context;
		this.act = act;
	}

	@Override
	protected Boolean doInBackground(Commento... params) {
		commento = params[0];

		mProtocolCarrier = new ProtocolCarrier(context,
				SmartUniDataWS.TOKEN_NAME);

		MessageResponse response;

		MessageRequest request = new MessageRequest(
				SmartUniDataWS.URL_WS_SMARTUNI,
				SmartUniDataWS.POST_WS_MY_FEEDBACK);
		request.setMethod(Method.POST);

		Boolean resultPost = false;

		try {

			String eventoJSON = Utils.convertToJSON(commento);
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
			e.printStackTrace();
		}

		return resultPost;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);

		if (result.equals(true)) {
			Toast.makeText(
					context,
					context.getResources()
							.getString(R.string.rate_review_added),
					Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(context,
					context.getResources().getString(R.string.dialog_error_old_course),
					Toast.LENGTH_LONG).show();
		}
		AddRatingFromCoursesPassed.pd.dismiss();
		act.finish();

	}

}
