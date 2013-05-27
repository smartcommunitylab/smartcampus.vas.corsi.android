package smartcampus.android.template.standalone;

import java.util.ArrayList;
import java.util.List;

import eu.trentorise.smartcampus.smartuni.models.CourseLite;
import eu.trentorise.smartcampus.smartuni.utilities.CoursesHandlerLite;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class ResultSearchedActivity extends Activity {

	public List<CourseLite> courses;
	public ArrayList<String> coursesFiltered;
	String department;
	String degree;
	String course;
	public static ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result_searched);
		ActionBar ab = getActionBar();
		ab.setHomeButtonEnabled(true);
		ab.setDisplayHomeAsUpEnabled(true);

		Intent i = getIntent();
		department = i.getStringExtra("department");
		degree = i.getStringExtra("courseDegree");
		course = i.getStringExtra("course").toLowerCase();

		new ProgressDialog(ResultSearchedActivity.this);
		pd = ProgressDialog.show(
				ResultSearchedActivity.this, "Risultati della ricerca",
				"Caricamento dei corsi...");

		TextView tv = (TextView) findViewById(R.id.textViewDatetimeRow);
		ListView listView = (ListView) findViewById(R.id.listView1);

		// get data from web service
		new CoursesHandlerLite(getApplicationContext(), department, degree,
				course, listView, tv).execute();

		listView.setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) { // TODO Auto-generated method stub String
				String courseSelectedName = (String) arg0
						.getItemAtPosition(arg2);
				Intent i = new Intent(ResultSearchedActivity.this,
						FindHomeCourseActivity.class);

				i.putExtra("courseSelectedName", courseSelectedName);
				i.putExtra("courseSelectedId", CoursesHandlerLite.getIDCourseSelected(arg2));
				startActivity(i);
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result_searched, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
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
