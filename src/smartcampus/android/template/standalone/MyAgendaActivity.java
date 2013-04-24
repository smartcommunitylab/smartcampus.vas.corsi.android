package smartcampus.android.template.standalone;

import android.app.TabActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class MyAgendaActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_agenda);

		/** TabHost will have Tabs */
		TabHost tabHost = getTabHost();
		String tab1 = getResources().getString(R.string.tab_home);
		String tab2 = getResources().getString(R.string.tab_recensioni);

		TabSpec ts1 = tabHost.newTabSpec("tab_test1");
		TabSpec ts2 = tabHost.newTabSpec("tab_test2");

		tabHost.addTab(ts1.setIndicator(tab1).setContent(R.id.tab1));
		tabHost.addTab(ts2.setIndicator(tab2).setContent(R.id.tab2));

		tabHost.setCurrentTab(0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_agenda, menu);
		return true;
	}

}
