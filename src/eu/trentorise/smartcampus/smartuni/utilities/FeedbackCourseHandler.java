package eu.trentorise.smartcampus.smartuni.utilities;

import java.util.Collections;
import java.util.List;

import smartcampus.android.template.standalone.HomeCourseDescriptionFragment;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ExpandableListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;
import eu.trentorise.smartcampus.smartuni.models.Commento;

public class FeedbackCourseHandler extends
		AsyncTask<Void, Void, List<Commento>> {

	private ProtocolCarrier mProtocolCarrier;
	public Context context;
	public String appToken = "test smartcampus";
	public String authToken = "aee58a92-d42d-42e8-b55e-12e4289586fc";
	String body;
	long idCourse;
	TextView tvCourseName;
	RatingBar ratingAverage;
	ExpandableListView listComments;
	public static List<Commento> feedbackInfoList;
	public Activity act;
	TextView descriptionCourse;

	public FeedbackCourseHandler(Context applicationContext, long idCourse,
			Activity act, RatingBar ratingAverage, TextView descriptionCourse) {
		this.context = applicationContext;
		this.idCourse = idCourse;
		this.act = act;
		this.ratingAverage = ratingAverage;
		this.descriptionCourse = descriptionCourse;
	}

	private List<Commento> getFullFeedbackById() {

		mProtocolCarrier = new ProtocolCarrier(context,
				SmartUniDataWS.TOKEN_NAME);

		MessageRequest request = new MessageRequest(
				SmartUniDataWS.URL_WS_SMARTUNI,
				SmartUniDataWS.GET_WS_FEEDBACK_OF_COURSE(idCourse));
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

		return Utils.convertJSONToObjects(body, Commento.class);
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();

	}

	@Override
	protected void onPostExecute(List<Commento> commenti) {
		// TODO Auto-generated method stub
		super.onPostExecute(commenti);

		Collections.reverse(commenti);
		
		
		feedbackInfoList = commenti;
		if (commenti == null) {

			Toast.makeText(context, "Ops! C'è stato un errore...",
					Toast.LENGTH_SHORT).show();
			act.finish();
		} else {
		act.getActionBar().setTitle(
				feedbackInfoList.get(0).getCorso().getNome());
		ratingAverage.setRating((float) feedbackInfoList.get(0).getCorso()
				.getValutazione_media());
		descriptionCourse.setText(feedbackInfoList.get(0).getCorso()
				.getDescrizione());

		HomeCourseDescriptionFragment.pd.dismiss();

		// loadDataLayout(course);
		}
	}

	@Override
	protected List<Commento> doInBackground(Void... params) {
		// TODO Auto-generated method stub
		return getFullFeedbackById();
	}
}