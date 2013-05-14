package smartcampus.android.template.standalone;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import eu.trentorise.smartcampus.smartuni.models.Notice;
import eu.trentorise.smartcampus.smartuni.models.Notifications;
import eu.trentorise.smartcampus.smartuni.utilities.AdapterNoticesList;
import eu.trentorise.smartcampus.smartuni.utilities.NotificationHandler;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class NoticesActivity extends Activity {

	private List<Notice> notifies;
	private ArrayList<Notice> notificationsList;
	private ArrayList<String> descriptionsList;
	private ArrayList<String> datetimeList;
	private ArrayList<String> usersList;
	private String dateString;
	private TextView textViewTitleNotices;
	private ListView lvAllNotices;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notices);
		ActionBar ab = getActionBar();
		ab.setHomeButtonEnabled(true);
		ab.setDisplayHomeAsUpEnabled(true);

		textViewTitleNotices = (TextView) findViewById(R.id.textViewTitleNotices);
		lvAllNotices = (ListView) findViewById(R.id.listViewNotices);
		try {
			notifies = (List<Notice>) new NotificationHandler(
					getApplicationContext()).execute().get();

			if (notifies == null) {
				setVoidNotify();
			} else
				setListNotifications();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void setListNotifications() {

		textViewTitleNotices.setText(R.string.notices_string_titlelist);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss",
				Locale.ITALY);
		Date date = new Date();
		dateString = dateFormat.format(date);

		textViewTitleNotices.setText(R.string.notices_string_titlelistExist);
		textViewTitleNotices.setText(textViewTitleNotices.getText() + " "
				+ dateString);

		Iterator<Notice> i = notifies.iterator();

		datetimeList = new ArrayList<String>();
		usersList = new ArrayList<String>();
		descriptionsList = new ArrayList<String>();

		while (i.hasNext()) {
			Notice t = new Notice();
			t = (Notice) i.next();

			descriptionsList.add(t.getDescription());
			/*
			 * datetimeList.add(t.getDatetime()); usersList.add(t.getUser());
			 */
		}

		AdapterNoticesList adapterNotices = new AdapterNoticesList(
				NoticesActivity.this, R.id.listViewNotices, notifies);
		lvAllNotices.setAdapter(adapterNotices);

	}

	private void setVoidNotify() {
		textViewTitleNotices.setText(R.string.notices_string_titlelist);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss",
				Locale.ITALY);
		Date date = new Date();
		dateString = dateFormat.format(date);

		textViewTitleNotices.setText(R.string.notices_string_titlelistExist);
		textViewTitleNotices.setText(textViewTitleNotices.getText() + " "
				+ dateString);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.notices, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
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