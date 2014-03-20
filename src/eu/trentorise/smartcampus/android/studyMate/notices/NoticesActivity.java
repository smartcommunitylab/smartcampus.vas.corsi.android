package eu.trentorise.smartcampus.android.studyMate.notices;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuInflater;

import eu.trentorise.smartcampus.android.studyMate.start.MyUniActivity;
import eu.trentorise.smartcampus.android.studyMate.utilities.NotificationHandler;
import eu.trentorise.smartcampus.studymate.R;

public class NoticesActivity extends SherlockFragmentActivity implements OnNavigationListener {

	private TextView textViewTitleNotices;
	private ListView lvAllNotices;
	private SherlockFragmentActivity activity;
	public static ProgressDialog pd;
	private long fromDate;
	private static final long TIMEFROM = 604800000 * 2;
	private String[] source;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notices);
		ActionBar ab = getSupportActionBar();
		ab.setHomeButtonEnabled(true);
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setDisplayShowTitleEnabled(false);
		ab.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		source = getResources().getStringArray(R.array.Source);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				NoticesActivity.this, android.R.layout.simple_list_item_1,
				source);

		// Set up the dropdown list navigation in the action bar.
		ab.setListNavigationCallbacks(adapter, NoticesActivity.this);

		textViewTitleNotices = (TextView) findViewById(R.id.textViewTitleNotices);
		lvAllNotices = (ListView) findViewById(R.id.listViewNotices);

		new ProgressDialog(NoticesActivity.this);
		pd = ProgressDialog.show(NoticesActivity.this, "Bacheca notifiche",
				"Caricamento...");

	}

	@Override
	protected void onResume() {
		super.onResume();

		Date date1Week = new Date(System.currentTimeMillis() - TIMEFROM);
		fromDate = date1Week.getTime();

		new NotificationHandler(getApplicationContext(), textViewTitleNotices,
				lvAllNotices, activity, fromDate).execute();
	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
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

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		// TODO Auto-generated method stub
		try {
			URLEncoder.encode(source[itemPosition], "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}