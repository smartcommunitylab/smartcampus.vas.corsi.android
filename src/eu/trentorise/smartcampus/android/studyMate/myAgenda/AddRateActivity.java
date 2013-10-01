package eu.trentorise.smartcampus.android.studyMate.myAgenda;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import smartcampus.android.template.standalone.R;
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
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.android.studyMate.models.Commento;
import eu.trentorise.smartcampus.android.studyMate.models.RatingRowGroup;
import eu.trentorise.smartcampus.android.studyMate.models.Studente;
import eu.trentorise.smartcampus.android.studyMate.start.MyUniActivity;
import eu.trentorise.smartcampus.android.studyMate.utilities.AdapterFeedbackList;
import eu.trentorise.smartcampus.android.studyMate.utilities.AdapterRating;
import eu.trentorise.smartcampus.android.studyMate.utilities.AddFeedbackHandler;
import eu.trentorise.smartcampus.android.studyMate.utilities.CoursesHandler;
import eu.trentorise.smartcampus.android.studyMate.utilities.SmartUniDataWS;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;

public class AddRateActivity extends SherlockFragmentActivity {

	AdapterFeedbackList mAdapter;
	ArrayList<RatingRowGroup> ratings;
	List<Float> values;
	Commento commento;
	public static ProgressDialog pd;
	ExpandableListView list = null;
	private RatingRowGroup rrg;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_rating);
		setTitle(CoursesHandler.corsoSelezionato.getNome());
		new ProgressDialog(AddRateActivity.this);
		pd = ProgressDialog.show(AddRateActivity.this,
				"Caricamento dei dati della tua recensione", "Caricamento...");

		new LoaderFeedbackData(AddRateActivity.this).execute();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
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
			// TODO Auto-generated constructor stub
			this.context = applicationContext;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			ratings = new ArrayList<RatingRowGroup>();
			list = (ExpandableListView) findViewById(R.id.expandableListViewRating);
		}

		@Override
		protected void onPostExecute(Commento commento) {
			// TODO Auto-generated method stub
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
					// TODO Auto-generated method stub

					return true;
				}
			});

			findViewById(R.id.button_ok_rate).setOnClickListener(
					new OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent intent = new Intent();
							intent.putExtra("Rating", ratings);
							Commento commento = new Commento();

							// controllo se il commento è presente
							if (commentCourse.getText().toString() != null)
								commento.setTesto(commentCourse.getText()
										.toString());
							else
								commento.setTesto(new String(""));
							
							
							// Aggiorno l'oggetto commento ottenuto dal server e lo aggiorno con i valori appena inseriti dall'utente
							Calendar c = Calendar.getInstance();
							commento.setData_inserimento(c.getTime().toString());

							commento.setCorso(CoursesHandler.corsoSelezionato);

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
							commento.setId_studente(stud);

							new AddFeedbackHandler(getParent().getApplicationContext())
									.execute(commento);
							Toast.makeText(getApplicationContext(),
									"Voto Aggiunto!", Toast.LENGTH_LONG).show();
							finish();
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
					SmartUniDataWS.GET_WS_FEEDBACK_OF_STUDENT(
							Long.parseLong(MyUniActivity.bp.getUserId()),
							CoursesHandler.corsoSelezionato.getId()));
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

			return Utils.convertJSONToObject(body, Commento.class);
		}

	}

}