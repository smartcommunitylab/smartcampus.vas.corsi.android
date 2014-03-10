package eu.trentorise.smartcampus.android.studyMate.finder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.android.studyMate.models.AttivitaDidattica;
import eu.trentorise.smartcampus.android.studyMate.rate.AddRatingFromCoursesPassed;
import eu.trentorise.smartcampus.android.studyMate.start.MyUniActivity;
import eu.trentorise.smartcampus.android.studyMate.utilities.CoursesHandler;
import eu.trentorise.smartcampus.android.studyMate.utilities.CoursesHandlerLite;
import eu.trentorise.smartcampus.android.studyMate.utilities.CoursesPassedHandler;
import eu.trentorise.smartcampus.android.studyMate.utilities.SmartUniDataWS;
import eu.trentorise.smartcampus.android.studyMate.utilities.TabListener;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;
import eu.trentorise.smartcampus.studymate.R;

public class FindHomeCourseActivity extends SherlockFragmentActivity {

	public static AttivitaDidattica courseInfo;
	public static String courseName;
	public static AttivitaDidattica corsoAttuale;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final ActionBar ab = getSupportActionBar();
		ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		ab.setHomeButtonEnabled(true);
		ab.setDisplayHomeAsUpEnabled(true);

		/** TabHost will have Tabs */
		String tab1_txt = getResources().getString(R.string.tab_home_course);
		String tab2_txt = getResources().getString(R.string.tab_feedback);

		Intent intent = getIntent();
		courseName = intent.getStringExtra("courseSelectedName");
		setTitle(courseName);
		corsoAttuale = new AttivitaDidattica();
		corsoAttuale = (AttivitaDidattica) intent.getSerializableExtra("courseSelected");

		Tab tab1 = ab
				.newTab()
				.setText(tab1_txt)
				.setTabListener(
						new TabListener<HomeCourseDescriptionFragment>(this,
								"tab1", HomeCourseDescriptionFragment.class));
		ab.addTab(tab1);

		Tab tab2 = ab
				.newTab()
				.setText(tab2_txt)
				.setTabListener(
						new TabListener<FeedbackFragment>(this, "tab2",
								FeedbackFragment.class));
		ab.addTab(tab2);

	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		com.actionbarsherlock.view.MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.find_home_course, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public void ShowDialog() {
		final AlertDialog.Builder popDialog = new AlertDialog.Builder(this);

		final RatingBar rating = new RatingBar(this);
		popDialog.setIcon(android.R.drawable.btn_star_big_on);
		popDialog.setTitle("Vote!! ");
		popDialog.setView(rating);
		popDialog.setView(findViewById(R.layout.dialog_layout));

		// Button OK
		popDialog.setPositiveButton(android.R.string.ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}

				})

		// Button Cancel
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});

		popDialog.create();
		popDialog.show();

	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();

			return true;
		case R.id.itemAddRating:
			new IsCousePassedTask().execute(CoursesHandlerLite.corsoSelezionato
					.getAdId());

			return true;
		default:
			return super.onOptionsItemSelected(item);

		}
	}

	private class IsCousePassedTask extends AsyncTask<Long, Void, Boolean> {

		private ProtocolCarrier mProtocolCarrier;
		public String body;

		private Long corsoId;

		@Override
		protected Boolean doInBackground(Long... params) {
			corsoId = params[0];

			mProtocolCarrier = new ProtocolCarrier(FindHomeCourseActivity.this,
					SmartUniDataWS.TOKEN_NAME);

			MessageRequest request = new MessageRequest(
					SmartUniDataWS.URL_WS_SMARTUNI,
					SmartUniDataWS.GET_WS_COURSE_IS_PASSED(String
							.valueOf(corsoId)));
			request.setMethod(Method.GET);

			MessageResponse response;
			try {
				response = mProtocolCarrier
						.invokeSync(request, SmartUniDataWS.TOKEN_NAME,
								MyUniActivity.getAuthToken());

				if (response.getHttpStatus() == 200) {

					body = response.getBody();

				} else {
					return false;
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

			return Utils.convertJSONToObject(body, Boolean.class);

		}

		@Override
		protected void onPostExecute(Boolean isPassed) {
			super.onPostExecute(isPassed);

			if (isPassed == null) {
				Toast toast = Toast.makeText(FindHomeCourseActivity.this,
						"Ops. C'è stato un errore", Toast.LENGTH_LONG);
				toast.show();
				return;
			}

			if (isPassed) {
				Intent intentAddRating = new Intent(
						FindHomeCourseActivity.this,
						AddRatingFromCoursesPassed.class);
				intentAddRating.putExtra("corso",
						CoursesHandler.corsoSelezionato.getId());
				CoursesPassedHandler.corsoSelezionato = CoursesHandler.corsoSelezionato;
				intentAddRating.putExtra("NomeCorso",
						CoursesHandler.corsoSelezionato.getName() );
				intentAddRating.putExtra("IdCorso",
						CoursesHandler.corsoSelezionato.getId());
				intentAddRating.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intentAddRating);

			} else {
				Toast toast = Toast.makeText(
						FindHomeCourseActivity.this,
						getResources().getText(
								R.string.toast_rate_access_denied),
						Toast.LENGTH_LONG);
				toast.show();
			}
		}

	}

}