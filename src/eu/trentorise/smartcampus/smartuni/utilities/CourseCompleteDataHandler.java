package eu.trentorise.smartcampus.smartuni.utilities;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ExpandableListView;
import android.widget.RatingBar;
import android.widget.TextView;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;
import eu.trentorise.smartcampus.smartuni.models.Corso;

public class CourseCompleteDataHandler extends AsyncTask<Void, Void, Corso> {

	private ProtocolCarrier mProtocolCarrier;
	public Context context;
	public String appToken = "test smartcampus";
	public String authToken = "aee58a92-d42d-42e8-b55e-12e4289586fc";
	String body;
	long idCourse;
	TextView tvCourseName;
	RatingBar ratingAverage;
	TextView descriptionCourse;
	ExpandableListView listComments;

	public CourseCompleteDataHandler(Context applicationContext, long idCourse) {
		this.context = applicationContext;
		this.idCourse = idCourse;
		/*
		 * this.tvCourseName = courseName; this.ratingAverage = ratingAverage;
		 * this.descriptionCourse = descriptionCourse; this.listComments =
		 * listComments;
		 */
	}

	private Corso getFullCourseById() {

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

		return Utils.convertJSONToObject(body, Corso.class);
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();

	}

	@Override
	protected void onPostExecute(Corso course) {
		// TODO Auto-generated method stub
		super.onPostExecute(course);

		// FindHomeCourseActivity.pd.dismiss();

		// loadDataLayout(course);

	}

	protected void loadDataLayout(Corso course) {
		// tvCourseName.setText(course.getNome());
		// ratingAverage.setRating((float)course.getValutazione_media());
		// descriptionCourse.setText(course.getDescrizione());
		//
		// List<Comment> comments = course.getCommenti();
		//
		// ArrayList<FeedbackRowGroup> ratings = new
		// ArrayList<FeedbackRowGroup>();
		//
		// for (int i = 0; i < comments.size(); i++) {
		// FeedbackRowGroup feedb = new FeedbackRowGroup();
		// Author auth = new Author();
		// auth.setName(comments.get(i).getAutore().getNome());
		// feedb.setAuthor(auth);
		// feedb.setRating(comments.get(i).getValutazione());
		// feedb.setComment(comments.get(i).getTesto());
		// ratings.add(feedb);
		// }
		//
		// AdapterFeedbackList mAdapter = new AdapterFeedbackList(context,
		// ratings);
		//
		//
		//
		// listComments.setAdapter(mAdapter);
		// listComments.setGroupIndicator(null);
		// listComments.setOnGroupClickListener(new OnGroupClickListener() {
		//
		// @Override
		// public boolean onGroupClick(ExpandableListView parent, View v,
		// int groupPosition, long id) {
		// // TODO Auto-generated method stub
		// if(parent.isGroupExpanded(groupPosition))
		// parent.collapseGroup(groupPosition);
		// else
		// parent.expandGroup(groupPosition, true);
		// return true;
		// }
		// });

	}

	@Override
	protected Corso doInBackground(Void... params) {
		// TODO Auto-generated method stub
		return getFullCourseById();
	}
}