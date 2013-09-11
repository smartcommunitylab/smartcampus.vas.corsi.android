package eu.trentorise.smartcampus.studyMate.utilities;

import java.util.Collections;
import java.util.List;

import smartcampus.android.studyMate.finder.FindHomeCourseActivity;
import android.app.Activity;
import android.app.ProgressDialog;
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
import eu.trentorise.smartcampus.studyMate.models.Corso;

public class FeedbackCourseHandler extends AsyncTask<Void, Void, List<Corso>> {

	private ProtocolCarrier mProtocolCarrier;
	public Context context;
	public String appToken = "test smartcampus";
	public String authToken = "aee58a92-d42d-42e8-b55e-12e4289586fc";
	String body;
	long idCourse;
	TextView tvCourseName;
	RatingBar ratingAverage;
	ExpandableListView listComments;
	public static List<Corso> feedbackInfoList;
	public Activity act;
	TextView descriptionCourse;
	public static ProgressDialog pd;

	public FeedbackCourseHandler(Context applicationContext, long idCourse,
			Activity act, RatingBar ratingAverage, TextView descriptionCourse) {
		this.context = applicationContext;
		this.idCourse = idCourse;
		this.act = act;
		this.ratingAverage = ratingAverage;
		this.descriptionCourse = descriptionCourse;
	}

	private List<Corso> getFullFeedbackById() {

		Context context = null;
		mProtocolCarrier = new ProtocolCarrier(context,
				SmartUniDataWS.TOKEN_NAME);

		MessageRequest request = new MessageRequest(
				SmartUniDataWS.URL_WS_SMARTUNI,
				SmartUniDataWS.GET_WS_FEEDBACK_OF_COURSE(idCourse));
		request.setMethod(Method.GET);

		MessageResponse response;
		String body = null;
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

		System.out.println(body);
		System.out.println(Utils.convertJSONToObjects(body, Corso.class));
		return Utils.convertJSONToObjects(body, Corso.class);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		new ProgressDialog(act);
		pd = ProgressDialog.show(act, "Informazioni del corso di "
				+ FindHomeCourseActivity.courseName, "Caricamento...");

	}

	@Override
	protected void onPostExecute(List<Corso> result) {
		super.onPostExecute(result);
		System.out.println("result: " + result);
		if (result.get(0) == null) {

			Toast.makeText(context, "Ops! C'e' stato un errore...",
					Toast.LENGTH_SHORT).show();

			pd.dismiss();
			// act.finish();
		} else {
			Corso c = result.get(0);
			System.out.println("CORSO: " + result.get(0).getNome());
			Collections.reverse(result);
			// feedbackInfoList = corso;
			// act.getActionBar().setTitle("Test");
			// corso.get(0).getNome());
			ratingAverage.setRating((float) c.getValutazione_media());

			// RatingBar ratingCont = (RatingBar) act
			// .findViewById(R.id.ratingBarRowContenuti);
			// ratingCont.setRating(feedbackInfoList.get(0).getRating_contenuto());
			//
			// RatingBar ratingCaricoStudio = (RatingBar) act
			// .findViewById(R.id.ratingBarRowCfu);
			// ratingCaricoStudio.setRating(feedbackInfoList.get(0)
			// .getRating_carico_studio());
			//
			// RatingBar ratingLezioni = (RatingBar) act
			// .findViewById(R.id.ratingBarRowLezioni);
			// ratingLezioni
			// .setRating(feedbackInfoList.get(0).getRating_lezioni());
			//
			// RatingBar ratingMateriali = (RatingBar) act
			// .findViewById(R.id.ratingBarRowMateriali);
			// ratingMateriali.setRating(feedbackInfoList.get(0)
			// .getRating_materiali());
			//
			// RatingBar ratingEsame = (RatingBar) act
			// .findViewById(R.id.ratingBarRowEsame);
			// ratingEsame.setRating(feedbackInfoList.get(0).getRating_esame());

			descriptionCourse.setText(c.getDescrizione());
			// System.out.println(feedbackInfoList.get(0).getDescrizione());
		}

		pd.dismiss();
		// loadDataLayout(course);

	}

	@Override
	protected List<Corso> doInBackground(Void... params) {
		// TODO Auto-generated method stub
		return getFullFeedbackById();
	}
}