package smartcampus.android.studyMate.rate;

import smartcampus.android.studyMate.finder.FindHomeActivity;
import smartcampus.android.studyMate.finder.ResultSearchedActivity;
import smartcampus.android.studyMate.start.MyUniActivity;
import smartcampus.android.template.standalone.R;
import eu.trentorise.smartcampus.studyMate.models.CorsoLaurea;
import eu.trentorise.smartcampus.studyMate.models.Dipartimento;
import eu.trentorise.smartcampus.studyMate.utilities.CoursesHandlerLite;
import eu.trentorise.smartcampus.studyMate.utilities.CoursesPassedHandler;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

public class CoursesPassedActivity extends Activity {
	
	public static ProgressDialog pd;
	private ListView listViewCorsi;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_courses_passed);
		ActionBar ab = getActionBar();
		ab.setHomeButtonEnabled(true);
		ab.setDisplayHomeAsUpEnabled(true);
		
		listViewCorsi = (ListView)findViewById(R.id.listViewCoursesPassed);
		
		new CoursesPassedHandler(getApplicationContext(), listViewCorsi, this).execute();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
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
