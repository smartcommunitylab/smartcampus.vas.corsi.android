package smartcampus.android.template.standalone;

import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class PHLActivity extends SherlockFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_phl);
		final ActionBar ab = getSupportActionBar();
		ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		ab.setHomeButtonEnabled(true);
		ab.setDisplayHomeAsUpEnabled(true);

		/** TabHost will have Tabs */
//		String tab1_txt = getResources().getString(R.string.tab_home);
		String tab2_txt = getResources().getString(R.string.my_courses);

//		Tab tab1 = ab
//				.newTab()
//				.setText(tab1_txt)
//				.setTabListener(
//						new TabListener<OverviewFragment>(this, "tab1",
//								OverviewFragment.class));
//		ab.addTab(tab1);

		Tab tab2 = ab
				.newTab()
				.setText(tab2_txt)
				.setTabListener(
						new TabListener<CorsiFragment>(this, "tab2",
								CorsiFragment.class));
		ab.addTab(tab2);
//		String[] corsi = getResources().getStringArray(R.array.Corsi);
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//				android.R.layout.simple_list_item_1, corsi);
//		ListView listView = (ListView) findViewById(R.id.listViewCorsiPHL);
//		listView.setAdapter(adapter);
//		
//		String[] corsiInt = getResources().getStringArray(R.array.CorsiInteresse);
//		
//		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
//				android.R.layout.simple_list_item_1, corsiInt);
//		ListView listView2 = (ListView) findViewById(R.id.listViewCorsiInteressePHL);
//		listView2.setAdapter(adapter2);
	}

	
	
	
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		// TODO Auto-generated method stub
		com.actionbarsherlock.view.MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.phl, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	
	
	@Override
	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item) {
		// TODO Auto-generated method stub
		
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intentHome = new Intent(PHLActivity.this, MyUniActivity.class);
			intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intentHome);
			return true;
		default:
			return super.onOptionsItemSelected(item);

		}
	}

}
