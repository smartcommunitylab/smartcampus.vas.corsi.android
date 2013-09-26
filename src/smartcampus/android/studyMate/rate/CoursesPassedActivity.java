package smartcampus.android.studyMate.rate;

import smartcampus.android.studyMate.start.MyUniActivity;
import smartcampus.android.template.standalone.R;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;

import eu.trentorise.smartcampus.studyMate.utilities.CoursesPassedHandler;

public class CoursesPassedActivity extends SherlockFragmentActivity {

	public static ProgressDialog pd;
	private ListView listViewCorsi;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_courses_passed);
		ActionBar ab = getSupportActionBar();

		ab.setHomeButtonEnabled(true);
		ab.setDisplayHomeAsUpEnabled(true);

		listViewCorsi = (ListView) findViewById(R.id.listViewCoursesPassed);

		new CoursesPassedHandler(getApplicationContext(), listViewCorsi, this)
				.execute();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intentHome = new Intent(CoursesPassedActivity.this,
					MyUniActivity.class);
			intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intentHome);
			return true;
		default:
			return super.onOptionsItemSelected(item);

		}
	}

}