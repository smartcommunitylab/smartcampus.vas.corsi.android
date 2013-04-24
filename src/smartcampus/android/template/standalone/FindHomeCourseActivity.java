package smartcampus.android.template.standalone;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class FindHomeCourseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_home_course);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.find_home_course, menu);
		return true;
	}

}
