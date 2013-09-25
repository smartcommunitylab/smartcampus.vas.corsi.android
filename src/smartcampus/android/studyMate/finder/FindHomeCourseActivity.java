package smartcampus.android.studyMate.finder;

import smartcampus.android.studyMate.myAgenda.AddRateActivity;
import smartcampus.android.studyMate.myAgenda.MyAgendaActivity;
import smartcampus.android.template.standalone.R;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.studyMate.models.Corso;
import eu.trentorise.smartcampus.studyMate.models.CorsoLite;
import eu.trentorise.smartcampus.studyMate.utilities.CoursesHandler;
import eu.trentorise.smartcampus.studyMate.utilities.CoursesPassedHandler;
import eu.trentorise.smartcampus.studyMate.utilities.TabListener;

public class FindHomeCourseActivity extends SherlockFragmentActivity {

	public static Corso courseInfo;
	public static String courseName;
	public static CorsoLite corsoAttuale;

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

		Intent intent = getIntent();

		courseName = intent.getStringExtra("courseSelectedName");
		setTitle(courseName);
		corsoAttuale = new CorsoLite();
		corsoAttuale = (CorsoLite) intent
				.getSerializableExtra("courseSelected");
	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		com.actionbarsherlock.view.MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.find_home_course, menu);
		return super.onCreateOptionsMenu(menu);
	}

//	@Override
//	protected void onSaveInstanceState(Bundle outState) {
//		super.onSaveInstanceState(outState);
//		outState.putInt("tab", getSupportActionBar().getSelectedNavigationIndex());
//	}
//
//	@Override
//	protected void onRestoreInstanceState(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		int tab = savedInstanceState.getInt("tab");
//		getActionBar().setSelectedNavigationItem(tab);
//		super.onRestoreInstanceState(savedInstanceState);
//	}
//
//	public void getDescription() {
//
//	}
//
//	public void getRating() {
//
//	}

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
			Intent intentAddRating = new Intent(FindHomeCourseActivity.this,
					AddRateActivity.class);
//			intentAddRating.putExtra("corso",
//					CoursesHandler.corsoSelezionato.getNome());
			intentAddRating.putExtra("NomeCorso",
					CoursesHandler.corsoSelezionato.getNome());
			intentAddRating.putExtra("IdCorso", CoursesHandler.corsoSelezionato.getId());
			intentAddRating.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			new CoursesPassedHandler(c, listViewCorsi, currentActivity)
			startActivity(intentAddRating);
			return true;
		default:
			return super.onOptionsItemSelected(item);

		}
	}
	
	
}