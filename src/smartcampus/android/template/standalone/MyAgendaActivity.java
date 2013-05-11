package smartcampus.android.template.standalone;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class MyAgendaActivity extends SherlockFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_my_agenda);

		final ActionBar ab = getSupportActionBar();
		ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		ab.setHomeButtonEnabled(true);
		ab.setDisplayHomeAsUpEnabled(true);

		/** TabHost will have Tabs */
		String tab1_txt = getResources().getString(R.string.tab_home);
		String tab2_txt = getResources().getString(R.string.tab_courses);

		Tab tab1 = ab
				.newTab()
				.setText(tab1_txt)
				.setTabListener(
						new TabListener<OverviewFragment>(this, "tab1",
								OverviewFragment.class));
		ab.addTab(tab1);

		Tab tab2 = ab
				.newTab()
				.setText(tab2_txt)
				.setTabListener(
						new TabListener<CorsiFragment>(this, "tab2",
								CorsiFragment.class));
		ab.addTab(tab2);
	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		// TODO Auto-generated method stub
		com.actionbarsherlock.view.MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.agenda, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intentHome = new Intent(MyAgendaActivity.this,
					MyUniActivity.class);
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
