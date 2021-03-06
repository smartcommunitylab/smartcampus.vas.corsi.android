package eu.trentorise.smartcampus.android.studyMate.rate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.android.studyMate.MyUniActivity;
import eu.trentorise.smartcampus.android.studyMate.R;
import eu.trentorise.smartcampus.android.studyMate.models.Commento;
import eu.trentorise.smartcampus.android.studyMate.models.RatingRowGroup;
import eu.trentorise.smartcampus.android.studyMate.models.Studente;
import eu.trentorise.smartcampus.android.studyMate.utilities.AdapterFeedbackList;
import eu.trentorise.smartcampus.android.studyMate.utilities.AdapterRating;
import eu.trentorise.smartcampus.android.studyMate.utilities.AddFeedbackHandler;
import eu.trentorise.smartcampus.android.studyMate.utilities.Constants;
import eu.trentorise.smartcampus.android.studyMate.utilities.SmartUniDataWS;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;

public class AddRatingFromCoursesPassed extends SherlockFragmentActivity {
	AdapterFeedbackList mAdapter;
	ArrayList<RatingRowGroup> ratings;
	List<Float> values;
	Commento commento;
	public static ProgressDialog pd;
	ExpandableListView list = null;
	private RatingRowGroup rrg;
	public SherlockFragmentActivity act;

	long idCorso;
	String CorsoName;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_rating);
		Intent intent = getIntent();
		idCorso = intent.getLongExtra(Constants.COURSE_ID, 0);
		CorsoName = intent.getStringExtra(Constants.COURSE_NAME);
		setTitle(getResources().getString(R.string.rate_review_course) + " "
				+ CorsoName);
		new ProgressDialog(AddRatingFromCoursesPassed.this);
		pd = ProgressDialog
				.show(AddRatingFromCoursesPassed.this, getResources()
						.getString(R.string.dialog_rate_loading_feedback),
						getResources().getString(R.string.dialog_loading));

		new LoaderFeedbackData(AddRatingFromCoursesPassed.this).execute();
	}

	private void setRatingContexts(Commento commento) {

		if (commento == null) {

			final EditText commentCourse = (EditText) findViewById(R.id.AddCommentRatingCourse);
			commentCourse.setText(new String(""));

			// CONTENUTI
			rrg = new RatingRowGroup();
			rrg.setRating(0);
			rrg.setContext(getResources().getString(
					R.string.rating_context_contenuti));
			rrg.setExplainContext(getResources().getString(
					R.string.rating_contextExplain_contenuti));
			ratings.add(rrg);

			// CARICO DI STUDIO
			rrg = new RatingRowGroup();
			rrg.setRating(0);
			rrg.setContext(getResources().getString(
					R.string.rating_context_carico_studio));
			rrg.setExplainContext(getResources().getString(
					R.string.rating_contextExplain_carico_studio));
			ratings.add(rrg);

			// LEZIONI
			rrg = new RatingRowGroup();
			rrg.setRating(0);
			rrg.setContext(getResources().getString(
					R.string.rating_context_lezioni));
			rrg.setExplainContext(getResources().getString(
					R.string.rating_contextExplain_lezioni));
			ratings.add(rrg);

			// MATERIALI
			rrg = new RatingRowGroup();
			rrg.setRating(0);
			rrg.setContext(getResources().getString(
					R.string.rating_context_materiali));
			rrg.setExplainContext(getResources().getString(
					R.string.rating_contextExplain_materiali));
			ratings.add(rrg);

			// Esame
			rrg = new RatingRowGroup();
			rrg.setRating(0);
			rrg.setContext(getResources().getString(
					R.string.rating_context_esame));
			rrg.setExplainContext(getResources().getString(
					R.string.rating_contextExplain_esame));
			ratings.add(rrg);
		} else {

			final EditText commentCourse = (EditText) findViewById(R.id.AddCommentRatingCourse);
			commentCourse.setText(commento.getTesto());

			// CONTENUTI
			rrg = new RatingRowGroup();
			rrg.setRating(commento.getRating_contenuto());
			rrg.setContext(getResources().getString(
					R.string.rating_context_contenuti));
			rrg.setExplainContext(getResources().getString(
					R.string.rating_contextExplain_contenuti));
			ratings.add(rrg);

			// CARICO DI STUDIO
			rrg = new RatingRowGroup();
			rrg.setRating(commento.getRating_carico_studio());
			rrg.setContext(getResources().getString(
					R.string.rating_context_carico_studio));
			rrg.setExplainContext(getResources().getString(
					R.string.rating_contextExplain_carico_studio));
			ratings.add(rrg);

			// LEZIONI
			rrg = new RatingRowGroup();
			rrg.setRating(commento.getRating_lezioni());
			rrg.setContext(getResources().getString(
					R.string.rating_context_lezioni));
			rrg.setExplainContext(getResources().getString(
					R.string.rating_contextExplain_lezioni));
			ratings.add(rrg);

			// MATERIALI
			rrg = new RatingRowGroup();
			rrg.setRating(commento.getRating_materiali());
			rrg.setContext(getResources().getString(
					R.string.rating_context_materiali));
			rrg.setExplainContext(getResources().getString(
					R.string.rating_contextExplain_materiali));
			ratings.add(rrg);

			// Esame
			rrg = new RatingRowGroup();
			rrg.setRating(commento.getRating_esame());
			rrg.setContext(getResources().getString(
					R.string.rating_context_esame));
			rrg.setExplainContext(getResources().getString(
					R.string.rating_contextExplain_esame));
			ratings.add(rrg);
		}
	}

	public class LoaderFeedbackData extends AsyncTask<Commento, Void, Commento> {

		private ProtocolCarrier mProtocolCarrier;
		public Context context;
		String body;

		public LoaderFeedbackData(Context applicationContext) {
			this.context = applicationContext;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			ratings = new ArrayList<RatingRowGroup>();
			list = (ExpandableListView) findViewById(R.id.expandableListViewRating);
		}

		@Override
		protected void onPostExecute(Commento commento) {
			super.onPostExecute(commento);

			// setto i campi della valutazione
			setRatingContexts(commento);

			final AdapterRating mAdapter = new AdapterRating(context, ratings);

			list.setAdapter(mAdapter);
			list.setGroupIndicator(null);
			list.expandGroup(0);
			list.setItemsCanFocus(true);
			list.setOnGroupClickListener(new OnGroupClickListener() {

				@Override
				public boolean onGroupClick(ExpandableListView parent, View v,
						int groupPosition, long id) {
					int count = mAdapter.getGroupCount();

					if (parent.isGroupExpanded(groupPosition)) {

						for (int i = 0; i < count; i++) {
							View elem = parent.getChildAt(i);
							if (elem != null) {
								elem.setBackgroundColor(getResources()
										.getColor(R.color.white_smartn_theme));
							}
						}

						parent.collapseGroup(groupPosition);

					} else {

						for (int i = 0; i < count; i++) {
							parent.collapseGroup(i);

							if (i != groupPosition) {
								View elem = parent.getChildAt(i);
								if (elem != null) {
									elem.setBackgroundColor(getResources()
											.getColor(
													R.color.white_smartn_theme));
								}
							}
						}

						parent.expandGroup(groupPosition);
						View elem = parent.getChildAt(groupPosition);
						if (elem != null) {
							// do Nothing
						}
					}

					return true;
				}

			});

			final EditText commentCourse = (EditText) findViewById(R.id.AddCommentRatingCourse);

			pd.dismiss();
			list.setOnChildClickListener(new OnChildClickListener() {

				@Override
				public boolean onChildClick(ExpandableListView parent, View v,
						int groupPosition, int childPosition, long id) {
					return true;
				}
			});

			findViewById(R.id.button_ok_rate).setOnClickListener(
					new OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent intent = new Intent();
							intent.putExtra(Constants.RATING, ratings);
							Commento commento = new Commento();

							if (commentCourse.getText().toString() != null)
								commento.setTesto(commentCourse.getText()
										.toString());
							else
								commento.setTesto(new String(""));
							Calendar c = Calendar.getInstance();
							commento.setData_inserimento(c.getTime().toString());

							commento.setCorso(idCorso);
							commento.setRating_contenuto(ratings.get(0)
									.getRating());
							commento.setRating_carico_studio(ratings.get(1)
									.getRating());
							commento.setRating_lezioni(ratings.get(2)
									.getRating());
							commento.setRating_materiali(ratings.get(3)
									.getRating());
							commento.setRating_esame(ratings.get(4).getRating());

							Studente stud = new Studente();
							stud.setId(Long.parseLong(MyUniActivity.bp
									.getUserId()));
							stud.setNome(MyUniActivity.bp.getName());
							commento.setId_studente(stud.getId());
							commento.setNome_studente(stud.getNome());

							new ProgressDialog(AddRatingFromCoursesPassed.this);
							pd = ProgressDialog.show(
									AddRatingFromCoursesPassed.this,
									getResources().getString(
											R.string.dialog_saving_feedback),
									getResources().getString(
											R.string.dialog_loading));
							new AddFeedbackHandler(
									AddRatingFromCoursesPassed.this,
									AddRatingFromCoursesPassed.this)
									.execute(commento);

						}
					});

			findViewById(R.id.button_annulla_rate).setOnClickListener(
					new OnClickListener() {
						@Override
						public void onClick(View v) {
							finish();
						}
					});

		}

		@Override
		protected Commento doInBackground(Commento... params) {
			mProtocolCarrier = new ProtocolCarrier(context,
					SmartUniDataWS.TOKEN_NAME);

			MessageRequest request = new MessageRequest(
					SmartUniDataWS.URL_WS_SMARTUNI,
					SmartUniDataWS.GET_WS_FEEDBACK_OF_STUDENT(idCorso));
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

			return Utils.convertJSONToObject(body, Commento.class);
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
	}
}
