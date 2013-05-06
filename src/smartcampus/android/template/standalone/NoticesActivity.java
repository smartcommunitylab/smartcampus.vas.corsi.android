package smartcampus.android.template.standalone;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import eu.trentorise.smartcampus.smartuni.models.Notice;
import eu.trentorise.smartcampus.smartuni.models.Notifications;
import eu.trentorise.smartcampus.smartuni.utilities.AdapterNoticesList;
import eu.trentorise.smartcampus.smartuni.utilities.NotificationHandler;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
public class NoticesActivity extends Activity {

	private Notifications notifies;
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
		textViewTitleNotices = (TextView) findViewById(R.id.textViewTitleNotices);
		lvAllNotices = (ListView) findViewById(R.id.listViewNotices);
			try {
				notifies = (Notifications) new NotificationHandler(
						getApplicationContext()).execute().get();
				
				if (notifies == null) {
					setVoidNotify();
				}else
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
		Iterator<Notice> i = notifies.getListNotifications().iterator();

		datetimeList = new ArrayList<String>();
		usersList = new ArrayList<String>();
		descriptionsList = new ArrayList<String>();
		
		while (i.hasNext()) {
			Notice t = new Notice();
			t = (Notice) i.next();
			
			descriptionsList.add(t.getDescription());
			datetimeList.add(t.getDatetime());
			usersList.add(t.getUser());
		}
		
		AdapterNoticesList adapterNotices = new AdapterNoticesList(NoticesActivity.this, R.id.listViewNotices, notifies.getListNotifications());
		lvAllNotices.setAdapter(adapterNotices);
		
	}

	private void setVoidNotify() {
		textViewTitleNotices.setText(R.string.notices_string_titlelist);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss",
				Locale.ITALY);
		Date date = new Date();
		dateString = dateFormat.format(date);
		
		textViewTitleNotices
		.setText(R.string.notices_string_titlelistExist);
textViewTitleNotices.setText(textViewTitleNotices.getText() + " "
		+ dateString);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.notices, menu);
		return true;
	}

	
}
