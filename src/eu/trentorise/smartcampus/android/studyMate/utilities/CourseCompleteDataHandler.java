package eu.trentorise.smartcampus.android.studyMate.utilities;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ExpandableListView;
import android.widget.RatingBar;
import android.widget.TextView;
import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.android.studyMate.models.AttivitaDidattica;
import eu.trentorise.smartcampus.android.studyMate.start.MyUniActivity;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;

public class CourseCompleteDataHandler extends AsyncTask<Void, Void, AttivitaDidattica> {

	private ProtocolCarrier mProtocolCarrier;
	public Context context;
	String body;
	long idCourse;
	TextView tvCourseName;
	RatingBar ratingAverage;
	TextView descriptionCourse;
	ExpandableListView listComments;

	public CourseCompleteDataHandler(Context applicationContext, long idCourse) {
		this.context = applicationContext;
		this.idCourse = idCourse;
	}

	private AttivitaDidattica getFullCourseById() {

		mProtocolCarrier = new ProtocolCarrier(context,
				SmartUniDataWS.TOKEN_NAME);

		MessageRequest request = new MessageRequest(
				SmartUniDataWS.URL_WS_SMARTUNI,
				SmartUniDataWS.GET_WS_COURSE_COMPLETE_DATA(String
						.valueOf(idCourse)));
		request.setMethod(Method.GET);

		MessageResponse response;
		try {
			response = mProtocolCarrier.invokeSync(request,
					SmartUniDataWS.TOKEN_NAME, MyUniActivity.getAuthToken());

			if (response.getHttpStatus() == 200) {

				body = response.getBody();

			} else {
				return null;
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

		return Utils.convertJSONToObject(body, AttivitaDidattica.class);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

	}

	@Override
	protected void onPostExecute(AttivitaDidattica course) {
		super.onPostExecute(course);
	}

	protected void loadDataLayout(AttivitaDidattica course) {
	}

	@Override
	protected AttivitaDidattica doInBackground(Void... params) {
		return getFullCourseById();
	}
}