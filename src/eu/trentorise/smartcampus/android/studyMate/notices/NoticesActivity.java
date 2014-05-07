package eu.trentorise.smartcampus.android.studyMate.notices;

import it.smartcampuslab.studymate.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuInflater;

import eu.trentorise.smartcampus.android.studyMate.start.MyUniActivity;
import eu.trentorise.smartcampus.android.studyMate.utilities.NotificationHandler;

public class NoticesActivity extends SherlockFragmentActivity {

	private TextView textViewTitleNotices;
	private ListView lvAllNotices;
	private SherlockFragmentActivity activity;
	public static ProgressDialog pd;
	private long fromDate;
	private static final long TIMEFROM = 604800000 * 2;
	private String[] source;
	private Spinner spinner;
	private TextView noNot;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notices);
		ActionBar ab = getSupportActionBar();
		ab.setHomeButtonEnabled(true);
		ab.setDisplayHomeAsUpEnabled(true);
		noNot = (TextView) findViewById(R.id.textViewDescNotices);
		source = getResources().getStringArray(R.array.Source);
		;
		spinner = (Spinner) findViewById(R.id.spinnerNotifiche);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				NoticesActivity.this, android.R.layout.simple_list_item_1,
				source);
		spinner.setAdapter(adapter);
		textViewTitleNotices = (TextView) findViewById(R.id.textViewTitleNotices);
		lvAllNotices = (ListView) findViewById(R.id.listViewNotices);

		new ProgressDialog(NoticesActivity.this);

	}

	@Override
	protected void onResume() {
		super.onResume();

		Date date1Week = new Date(System.currentTimeMillis() - TIMEFROM);
		fromDate = date1Week.getTime();
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				try {

					pd = ProgressDialog.show(NoticesActivity.this,
							getResources()
									.getString(R.string.notification_name),
							getResources().getString(R.string.dialog_loading));
					String type = URLEncoder.encode(source[arg2], "UTF-8");
					new NotificationHandler(getApplicationContext(),
							textViewTitleNotices, lvAllNotices, activity,
							fromDate, type, noNot).execute();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}

		});
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
}