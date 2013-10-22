package eu.trentorise.smartcampus.android.studyMate.phl;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;

import eu.trentorise.smartcampus.android.studyMate.R;
import eu.trentorise.smartcampus.android.studyMate.start.MyUniActivity;

public class PHLActivity extends SherlockFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_phl_corsi);
	}

	@Override
	protected void onStart() {
		super.onStart();
		ListView listViewCorsi = (ListView) findViewById(R.id.listViewCorsiPHL);

		Bundle arguments = new Bundle();
		PHLengine handlerPersonalCoursesPHL = new PHLengine(
				this.getApplicationContext(), this, listViewCorsi, this);
		handlerPersonalCoursesPHL.execute(arguments);

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.phl, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intentHome = new Intent(PHLActivity.this,
					MyUniActivity.class);
			intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intentHome);
			return true;
		default:
			return super.onOptionsItemSelected(item);

		}
	}

}
