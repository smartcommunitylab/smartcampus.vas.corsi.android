package eu.trentorise.smartcampus.smartuni.utilities;


import smartcampus.android.template.standalone.FindHomeCourseActivity;
import smartcampus.android.template.standalone.HomeCourseDescriptionFragment;
import smartcampus.android.template.standalone.NoticesActivity;
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
import eu.trentorise.smartcampus.smartuni.models.Course;

public class CourseCompleteDataHandler extends AsyncTask<Void, Void, Course> {

	private ProtocolCarrier mProtocolCarrier;
	public Context context;
	public String appToken = "test smartcampus";
	public String authToken = "aee58a92-d42d-42e8-b55e-12e4289586fc";
	String body;
	String idCourse;

	public CourseCompleteDataHandler(Context applicationContext, String idCourse) {
		this.context = applicationContext;
		this.idCourse = idCourse;
	}

	private Course getFullCourseById() {

		mProtocolCarrier = new ProtocolCarrier(context,
				SmartUniDataWS.TOKEN_NAME);

		MessageRequest request = new MessageRequest(
				SmartUniDataWS.URL_WS_SMARTUNI,
				SmartUniDataWS.GET_WS_COURSE_COMPLETE_DATA(idCourse));
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

		return Utils.convertJSONToObject(body, Course.class);
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();

	}

	@Override
	protected void onPostExecute(Course course) {
		// TODO Auto-generated method stub
		super.onPostExecute(course);

		HomeCourseDescriptionFragment.pd.dismiss();

		if (course != null) {
			setDataCourse(course);
		}

	}

	private void setDataCourse(Course course) {

	}

	@Override
	protected Course doInBackground(Void... params) {
		// TODO Auto-generated method stub
		return getFullCourseById();
	}
}
