package smartcampus.android.template.standalone;

import java.util.concurrent.ExecutionException;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;

import eu.trentorise.smartcampus.smartuni.models.Course;
import eu.trentorise.smartcampus.smartuni.utilities.CourseCompleteDataHandler;

public class FindHomeCourseActivity extends SherlockFragmentActivity {

	public static ProgressDialog pd;
	public static Course courseInfo;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_find_home_course);

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
						new TabListener<HomeCourseDescriptionFragment>(this, "tab1",
								HomeCourseDescriptionFragment.class));
		ab.addTab(tab1);

		Tab tab2 = ab
				.newTab()
				.setText(tab2_txt)
				.setTabListener(
						new TabListener<FeedbackFragment>(this, "tab2",
								FeedbackFragment.class));
		ab.addTab(tab2);

		Intent intent = getIntent();
		TextView tvCourseName = (TextView) findViewById(R.id.textViewNameCourseHome);
		String courseName = intent.getStringExtra("courseSelectedName");
		//tvCourseName.setText(courseName);
		//TextView descriptionCourse = (TextView) findViewById(R.id.textViewDescriptioonCourse);
		//RatingBar ratingAverage = (RatingBar)findViewById(R.id.ratingBarCourse);
		ExpandableListView listComments = (ExpandableListView)findViewById(R.id.expandableListViewFeedback);

		new ProgressDialog(FindHomeCourseActivity.this);
		pd = ProgressDialog.show(FindHomeCourseActivity.this, "Informazioni del corso di "+courseName, "Caricamento...");

		String idCourse = intent.getStringExtra("courseSelectedId");
		try {
			courseInfo = new CourseCompleteDataHandler(FindHomeCourseActivity.this, idCourse).execute().get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}



	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		com.actionbarsherlock.view.MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.find_home_course, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		int tab = savedInstanceState.getInt("tab");
		getActionBar().setSelectedNavigationItem(tab);
		super.onRestoreInstanceState(savedInstanceState);
	}


	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item) {
			finish();
			return super.onOptionsItemSelected(item);

	}

	public void getDescription() {

	}

	public void getRating() {

	}

}