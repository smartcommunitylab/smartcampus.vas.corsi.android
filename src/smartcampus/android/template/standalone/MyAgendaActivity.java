package smartcampus.android.template.standalone;

import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class MyAgendaActivity extends SherlockFragmentActivity {
	public static Boolean agendaState = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_my_agenda);
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getSupportMenuInflater();
		if (!agendaState) {
			inflater.inflate(R.menu.agenda, menu);
		}
		else{
			inflater.inflate(R.menu.det_event, menu);
			//agendaState = false;
		}
		return super.onCreateOptionsMenu(menu);
	}
@Override
public void onBackPressed() {			
	agendaState = false;
	invalidateOptionsMenu();
	super.onBackPressed();
}
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intentHome = new Intent(MyAgendaActivity.this,
					MyUniActivity.class);
			intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intentHome);
			return true;
		case R.id.add_event:
			Intent intentEvent = new Intent(MyAgendaActivity.this,
					AddEventActivity.class);
			intentEvent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intentEvent);
			return true;
		case R.id.menu_add_note:
			return true;
		case R.id.menu_add_notification:
			return true;
		case R.id.menu_share:
			return true;
		case R.id.menu_modify_event:
			return true;
		case R.id.menu_delete_event:
			return true;
			
				
				
				
		default:
			return super.onOptionsItemSelected(item);

		}
	}

}
