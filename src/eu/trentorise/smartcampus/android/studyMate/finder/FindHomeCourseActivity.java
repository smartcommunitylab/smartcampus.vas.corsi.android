package eu.trentorise.smartcampus.android.studyMate.finder;

import eu.trentorise.smartcampus.android.studyMate.R;
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
import eu.trentorise.smartcampus.android.studyMate.utilities.Constants;
import eu.trentorise.smartcampus.android.studyMate.utilities.SmartUniDataWS;
import eu.trentorise.smartcampus.android.studyMate.utilities.TabListener;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;

public class FindHomeCourseActivity extends SherlockFragmentActivity {

	public static AttivitaDidattica courseInfo;
	public static String courseName;
	public static AttivitaDidattica corsoAttuale;
	public static long idCorso;
	String corsoName;
	String adCod;

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
		idCorso = intent.getLongExtra(Constants.COURSE_ID, 0);
		corsoName = intent.getStringExtra(Constants.COURSE_NAME);
		adCod = intent.getStringExtra(Constants.AD_COD);
		setTitle(corsoName);
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
		popDialog.setTitle(getResources()
				.getString(R.string.rate_review_course));
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
				.setNegativeButton(
						getResources().getString(R.id.button_annulla),
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
			new IsCousePassedTask().execute(adCod);

			return true;
		default:
			return super.onOptionsItemSelected(item);

		}
	}

	private class IsCousePassedTask extends AsyncTask<String, Void, Boolean> {

		private ProtocolCarrier mProtocolCarrier;
		public String body;

		private String corsoId;

		@Override
		protected Boolean doInBackground(String... params) {
			corsoId = params[0];

			mProtocolCarrier = new ProtocolCarrier(FindHomeCourseActivity.this,
					SmartUniDataWS.TOKEN_NAME);

			MessageRequest request = new MessageRequest(
					SmartUniDataWS.URL_WS_SMARTUNI,
					SmartUniDataWS.GET_WS_COURSE_IS_PASSED(corsoId));
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
				e.printStackTrace();
			}

			return Utils.convertJSONToObject(body, Boolean.class);

		}

		@Override
		protected void onPostExecute(Boolean isPassed) {
			super.onPostExecute(isPassed);

			if (isPassed == null) {
				Toast toast = Toast.makeText(FindHomeCourseActivity.this,
						getResources().getString(R.string.dialog_error),
						Toast.LENGTH_LONG);
				toast.show();
				return;
			}

			if (isPassed) {
				Intent intentAddRating = new Intent(
						FindHomeCourseActivity.this,
						AddRatingFromCoursesPassed.class);
				intentAddRating.putExtra(Constants.COURSE_ID, idCorso);
				intentAddRating.putExtra(Constants.COURSE_NAME, corsoName);
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