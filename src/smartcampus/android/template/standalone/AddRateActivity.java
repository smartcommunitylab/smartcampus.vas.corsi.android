package smartcampus.android.template.standalone;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.RatingBar;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.Toast;
import eu.trentorise.smartcampus.ac.authenticator.AMSCAccessProvider;
import eu.trentorise.smartcampus.ac.model.UserData;
import eu.trentorise.smartcampus.profileservice.model.BasicProfile;
import eu.trentorise.smartcampus.smartuni.models.Commento;
import eu.trentorise.smartcampus.smartuni.models.RatingRowGroup;
import eu.trentorise.smartcampus.smartuni.models.Studente;
import eu.trentorise.smartcampus.smartuni.utilities.AdapterFeedbackList;
import eu.trentorise.smartcampus.smartuni.utilities.AddFeedbackHandler;
import eu.trentorise.smartcampus.smartuni.utilities.CoursesHandler;
import eu.trentorise.smartcampus.smartuni.utilities.LoaderFeedbackData;

public class AddRateActivity extends FragmentActivity {

	ExpandableListView list;
	AdapterFeedbackList mAdapter;
	ArrayList<RatingRowGroup> ratings;
	List<Float> values;
	Commento commento;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// View view = findViewById(R.layout.activity_add_rating);
		setContentView(R.layout.activity_add_rating);
		// }
		//
		// @Override
		// public View onCreateView(LayoutInflater inflater, ViewGroup
		// container,
		// Bundle savedInstanceState) {
		// // TODO Auto-generated method stub
		// View view = inflater.inflate(R.layout.activity_add_rating,
		// container, false);
		// TextView titleCourseRating = (TextView)
		// findViewById(R.id.textViewTitleRatingCourse);
		// titleCourseRating.setText(FindHomeCourseActivity.courseInfo.getNome());

		ratings = new ArrayList<RatingRowGroup>();
		final ExpandableListView list = (ExpandableListView) findViewById(R.id.expandableListViewRating);

		try {
			commento = new LoaderFeedbackData(AddRateActivity.this).execute()
					.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// setto i campi della valutazione
		setRatingContexts();

		final AdapterRating mAdapter = new AdapterRating(this, ratings);

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
							elem.setBackgroundColor(getResources().getColor(
									R.color.white_smartn_theme));
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
										.getColor(R.color.white_smartn_theme));
							}
						}
					}

					parent.expandGroup(groupPosition, true);
					View elem = parent.getChildAt(groupPosition);
					if (elem != null) {
						elem.setBackgroundColor(getResources().getColor(
								R.color.pressed_smartn_theme));
					}
				}

				return true;
			}

		});

		final RatingBar rbCont = (RatingBar) findViewById(R.id.ratingBarContextContenuti);
		final RatingBar rbCfu = (RatingBar) findViewById(R.id.ratingBarContextCfu);
		final RatingBar rbLez = (RatingBar) findViewById(R.id.ratingBarContextLezioni);
		final RatingBar rbMat = (RatingBar) findViewById(R.id.ratingBarContextMateriali);
		final RatingBar rbExam = (RatingBar) findViewById(R.id.ratingBarContextEsame);
		final EditText commentCourse = (EditText) findViewById(R.id.AddCommentRatingCourse);
		commentCourse.setText(commento.getTesto());

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
						commento.setCorso(CoursesHandler.corsoSelezionato);
						commento.setRating_contenuto(ratings.get(0).getRating());
						commento.setRating_carico_studio(ratings.get(1)
								.getRating());
						commento.setRating_lezioni(ratings.get(2).getRating());
						commento.setRating_materiali(ratings.get(3).getRating());
						commento.setRating_esame(ratings.get(4).getRating());

						if (commentCourse.getText().toString() != null)
							commento.setTesto(commentCourse.getText()
									.toString());
						else
							commento.setTesto(new String(""));
						Calendar c = Calendar.getInstance();
						commento.setData_inserimento(c.getTime().toString());

						AMSCAccessProvider mAccessProvider = new AMSCAccessProvider();
						UserData profile = mAccessProvider.readUserData(
								AddRateActivity.this, null);
						Studente stud = new Studente();
						stud.setId(Long.parseLong(profile.getUserId()));
						commento.setId_studente(stud);
						new AddFeedbackHandler().execute(commento);
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

	private void setRatingContexts() {

		if (commento == null) {
			// CONTENUTI
			RatingRowGroup rrg = new RatingRowGroup();
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
		}else{
			// CONTENUTI
						RatingRowGroup rrg = new RatingRowGroup();
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

}
