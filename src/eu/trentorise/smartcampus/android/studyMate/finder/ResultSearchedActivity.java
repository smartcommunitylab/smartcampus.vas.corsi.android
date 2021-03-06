package eu.trentorise.smartcampus.android.studyMate.finder;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;

import eu.trentorise.smartcampus.android.studyMate.R;
import eu.trentorise.smartcampus.android.studyMate.models.AttivitaDidattica;
import eu.trentorise.smartcampus.android.studyMate.models.CorsoLaurea;
import eu.trentorise.smartcampus.android.studyMate.models.Dipartimento;
import eu.trentorise.smartcampus.android.studyMate.utilities.Constants;
import eu.trentorise.smartcampus.android.studyMate.utilities.CoursesHandlerLite;

public class ResultSearchedActivity extends SherlockFragmentActivity {

	public List<AttivitaDidattica> courses;
	public ArrayList<String> coursesFiltered;
	String course = null;
	public static ProgressDialog pd;
	public Dipartimento depSelected;
	public CorsoLaurea courseDegreeSelected;
	List<AttivitaDidattica> listCourses;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result_searched);
		ActionBar ab = getSupportActionBar();
		ab.setHomeButtonEnabled(true);
		ab.setDisplayHomeAsUpEnabled(true);

		Intent i = getIntent();
		depSelected = (Dipartimento) i
				.getSerializableExtra(Constants.DEPARTMENT);
		courseDegreeSelected = (CorsoLaurea) i
				.getSerializableExtra(Constants.COURSE_DEG);
		course = i.getStringExtra(Constants.COURSE_DEG_SPEC).toLowerCase();

		new ProgressDialog(ResultSearchedActivity.this);
		pd = ProgressDialog.show(ResultSearchedActivity.this, getResources()
				.getString(R.string.title_activity_result_searched),
				getResources().getString(R.string.dialog_searching_courses));

		TextView tv = (TextView) findViewById(R.id.textViewDatetimeRow);
		ListView listView = (ListView) findViewById(R.id.lv_partecipanti_gds);

		// get data from web service
		new CoursesHandlerLite(getApplicationContext(), depSelected,
				courseDegreeSelected, course, listView, tv, this).execute();

	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.result_searched, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intentHome = new Intent(ResultSearchedActivity.this,
					FindHomeActivity.class);
			intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intentHome);
			return true;
		default:
			return super.onOptionsItemSelected(item);

		}

	}
}
