package eu.trentorise.smartcampus.android.studyMate.myAgenda;

import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.android.studyMate.utilities.TabListener;
import eu.trentorise.smartcampus.studymate.R;

public class MyAgendaActivity extends SherlockFragmentActivity {

	public enum ChildActivity {
		ADD_EVENT_FOR_COURSES, ADD_RATING, NONE
	}

	
	public static String corsoNameMA;
	String idCorsoMA;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		corsoNameMA = intent.getStringExtra("NomeCorso");

		final ActionBar ab = getSupportActionBar();
		ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		setTitle(getResources().getString(R.string.title_activity_my_agenda));
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
	protected void onResume() {
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
			setTitle("Agenda");
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			return true;
		case R.id.add_event:
			return false;
		case R.id.menu_modify_event:
			return false;
		case R.id.menu_delete_event:
			return false;
		case R.id.menu_add_event_4_course:
			return false;
		default:
			break;

		}
		return false;
	}
}
