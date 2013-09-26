package smartcampus.android.studyMate.notices;

import smartcampus.android.studyMate.start.MyUniActivity;
import smartcampus.android.template.standalone.R;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuInflater;

import eu.trentorise.smartcampus.studyMate.utilities.NotificationHandler;

public class NoticesActivity extends SherlockFragmentActivity{

	private TextView textViewTitleNotices;
	private ListView lvAllNotices;
	public static ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notices);
		com.actionbarsherlock.app.ActionBar ab = getSherlock().getActionBar();// getActionBar();
		ab.setHomeButtonEnabled(true);
		ab.setDisplayHomeAsUpEnabled(true);

		textViewTitleNotices = (TextView) findViewById(R.id.textViewTitleNotices);
		lvAllNotices = (ListView) findViewById(R.id.listViewNotices);

		new ProgressDialog(NoticesActivity.this);
		pd = ProgressDialog.show(NoticesActivity.this, "Bacheca notifiche",
				"Caricamento...");

		new NotificationHandler(getApplicationContext(), textViewTitleNotices,
				lvAllNotices).execute();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.notices, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intentHome = new Intent(NoticesActivity.this,
					MyUniActivity.class);
			intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intentHome);
			return true;

		default:
			return super.onOptionsItemSelected(item);

		}
	}

}