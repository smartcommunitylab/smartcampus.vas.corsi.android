package eu.trentorise.smartcampus.android.studyMate.utilities;

import java.util.Collections;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.android.studyMate.models.AttivitaDidattica;
import eu.trentorise.smartcampus.android.studyMate.models.Commento;
import eu.trentorise.smartcampus.android.studyMate.models.CorsoInteresse;
import eu.trentorise.smartcampus.android.studyMate.start.MyUniActivity;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;
import it.smartcampuslab.studymate.R;

public class FeedbackHandler extends AsyncTask<Void, Void, List<Commento>> {

	private ProtocolCarrier mProtocolCarrier;
	public Context context;
	String body;
	long idCourse;
	TextView tvCourseName;
	RatingBar ratingAverage;
	ExpandableListView listComments;
	public SherlockFragmentActivity act;
	TextView descriptionCourse;
	Button swichFollow;
	public static ProgressDialog pd;
	TextView txtMonitor;

	public static List<Commento> feedbackInfoList;
	public static AttivitaDidattica corsoInfo;

	public FeedbackHandler(Context applicationContext, long idCourse,
			SherlockFragmentActivity act, RatingBar ratingAverage,
			TextView descriptionCourse, Button sFollow, TextView txtMonitor) {
		this.context = applicationContext;
		this.idCourse = idCourse;
		this.act = act;
		this.ratingAverage = ratingAverage;
		this.descriptionCourse = descriptionCourse;
		this.swichFollow = sFollow;
		this.txtMonitor = txtMonitor;
	}

	private List<Commento> getFullFeedbackById() {

		// Richiedo la lista dei commenti

		mProtocolCarrier = new ProtocolCarrier(context,
				SmartUniDataWS.TOKEN_NAME);

		MessageRequest request = new MessageRequest(
				SmartUniDataWS.URL_WS_SMARTUNI,
				SmartUniDataWS.GET_WS_FEEDBACK_OF_COURSE(idCourse));
		request.setMethod(Method.GET);

		MessageResponse response;
		try {
			response = mProtocolCarrier.invokeSync(request,
					SmartUniDataWS.TOKEN_NAME, MyUniActivity.getAuthToken());

			if (response.getHttpStatus() == 200) {

				body = response.getBody();
				System.out.println(body);
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

		// prendo i dati aggiornati del corso
		request = new MessageRequest(SmartUniDataWS.URL_WS_SMARTUNI,
				SmartUniDataWS.GET_WS_COURSES_DETAILS(idCourse));
		// GET_WS_COURSE_COMPLETE_DATA(String
		// .valueOf(idCourse)));
		request.setMethod(Method.GET);

		try {
			response = mProtocolCarrier.invokeSync(request,
					SmartUniDataWS.TOKEN_NAME, MyUniActivity.getAuthToken());

			if (response.getHttpStatus() == 200) {

				String bodyCorso = response.getBody();
				corsoInfo = Utils.convertJSONToObject(bodyCorso,
						AttivitaDidattica.class);

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

		return Utils.convertJSONToObjects(body, Commento.class);
	}

	@Override
	protected void onPreExecute() {
		new ProgressDialog(act);
		pd = ProgressDialog.show(
				act,
				context.getResources().getString(
						R.string.feedback_course_information), context
						.getResources().getString(R.string.dialog_loading));

		super.onPreExecute();

	}

	@Override
	protected void onPostExecute(final List<Commento> commenti) {
		super.onPostExecute(commenti);

		new FollowTask().execute();
		if (commenti == null) {

			Toast.makeText(context,
					context.getResources().getString(R.string.dialog_error),
					Toast.LENGTH_SHORT).show();

			pd.dismiss();
			act.finish();
		} else {

			Collections.reverse(commenti);
			feedbackInfoList = commenti;
			act.getSupportActionBar().setTitle(corsoInfo.getDescription());
			ratingAverage.setRating((float) corsoInfo.getValutazione_media());
			RatingBar ratingCont = (RatingBar) act
					.findViewById(R.id.ratingBarRowContenuti);
			RatingBar ratingCaricoStudio = (RatingBar) act
					.findViewById(R.id.ratingBarRowCfu);
			RatingBar ratingLezioni = (RatingBar) act
					.findViewById(R.id.ratingBarRowLezioni);
			RatingBar ratingMateriali = (RatingBar) act
					.findViewById(R.id.ratingBarRowMateriali);
			RatingBar ratingEsame = (RatingBar) act
					.findViewById(R.id.ratingBarRowEsame);

			if (corsoInfo.getValutazione_media() == 0) {
				ratingCont.setRating(0);
				ratingCaricoStudio.setRating(0);
				ratingLezioni.setRating(0);
				ratingMateriali.setRating(0);
				ratingEsame.setRating(0);

			} else {
				ratingCont.setRating(corsoInfo.getRating_contenuto());
				ratingCaricoStudio.setRating(corsoInfo
						.getRating_carico_studio());
				ratingLezioni.setRating(corsoInfo.getRating_lezioni());
				ratingMateriali.setRating(corsoInfo.getRating_materiali());
				ratingEsame.setRating(corsoInfo.getRating_esame());

			}
			if (corsoInfo.getCourseDescription() == null) {

			} else {
				descriptionCourse.setText(corsoInfo.getCourseDescription());
			}
			pd.dismiss();

		}

	}

	@Override
	protected List<Commento> doInBackground(Void... params) {
		return getFullFeedbackById();
	}

	private class FollowTask extends AsyncTask<String, Void, CorsoInteresse> {

		private ProtocolCarrier mProtocolCarrier;
		public String body;

		@Override
		protected CorsoInteresse doInBackground(String... params) {

			mProtocolCarrier = new ProtocolCarrier(context,
					SmartUniDataWS.TOKEN_NAME);

			MessageRequest request = new MessageRequest(
					SmartUniDataWS.URL_WS_SMARTUNI,
					SmartUniDataWS.GET_WS_IF_FOLLOW(String.valueOf(corsoInfo
							.getAdId())));
			request.setMethod(Method.GET);

			MessageResponse response;
			try {
				response = mProtocolCarrier
						.invokeSync(request, SmartUniDataWS.TOKEN_NAME,
								MyUniActivity.getAuthToken());

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
				e.printStackTrace();
			}

			return Utils.convertJSONToObject(body, CorsoInteresse.class);

		}

		@Override
		protected void onPostExecute(CorsoInteresse cI) {
			super.onPostExecute(cI);
			if (cI == null) {
				swichFollow.setBackgroundResource(R.drawable.ic_monitor_off);
				txtMonitor.setText(R.string.label_txtMonitor_off);
				swichFollow.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						new SetCourseAsFollowHandler(context, swichFollow,
								txtMonitor).execute(corsoInfo);
					}

				});
				return;
			} else {
				if (cI.isCorsoCarriera()) {
					Toast.makeText(
							context,
							context.getResources().getString(
									R.string.feedback_course_is_career),
							Toast.LENGTH_SHORT).show();
					swichFollow.setBackgroundResource(R.drawable.ic_monitor_on);
				} else {
					swichFollow.setBackgroundResource(R.drawable.ic_monitor_on);
					txtMonitor.setText(R.string.label_txtMonitor_on);
					swichFollow.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {

							new SetCourseAsFollowHandler(context, swichFollow,
									txtMonitor).execute(corsoInfo);
						}

					});
				}
			}
		}

	}

}