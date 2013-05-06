package smartcampus.android.template.standalone;


import android.app.ActionBar;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class MyAgendaActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_agenda);

		ActionBar ab = getActionBar();
		ab.setHomeButtonEnabled(true);
		ab.setDisplayHomeAsUpEnabled(true);
		/** TabHost will have Tabs */
		TabHost tabHost = getTabHost();
		String tab1 = getResources().getString(R.string.tab_home);
		String tab2 = getResources().getString(R.string.tab_courses);

		TabSpec ts1 = tabHost.newTabSpec("tab_test1");
		TabSpec ts2 = tabHost.newTabSpec("tab_test2");

		tabHost.addTab(ts1.setIndicator(tab1).setContent(R.id.tab1));
		tabHost.addTab(ts2.setIndicator(tab2).setContent(R.id.tab2));

		tabHost.setCurrentTab(0);

		String[] corsi = getResources().getStringArray(R.array.Corsi);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, corsi);
		ListView listView = (ListView) findViewById(R.id.listViewCorsi);
		listView.setAdapter(adapter);
		
		String[] corsiInt = getResources().getStringArray(R.array.CorsiInteresse);
		
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, corsiInt);
		ListView listView2 = (ListView) findViewById(R.id.listViewCorsiInteresse);
		listView2.setAdapter(adapter2);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.agenda, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intentHome = new Intent(MyAgendaActivity.this, MyUniActivity.class);
			intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intentHome);
			return true;
		case R.id.add_event:
			
			return true;
		default:
			return super.onOptionsItemSelected(item);

		}
	}

}
