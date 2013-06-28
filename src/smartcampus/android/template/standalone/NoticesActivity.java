package smartcampus.android.template.standalone;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import eu.trentorise.smartcampus.smartuni.utilities.NotificationHandler;

public class NoticesActivity extends Activity
{

	// private List<Notice> notifies;
	// private ArrayList<Notice> notificationsList;
	// private ArrayList<String> descriptionsList;
	// private ArrayList<String> datetimeList;
	// private ArrayList<String> usersList;
	// private String dateString;
	private TextView				textViewTitleNotices;
	private ListView				lvAllNotices;
	public static ProgressDialog	pd;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notices);
		ActionBar ab = getActionBar();
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
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.notices, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
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