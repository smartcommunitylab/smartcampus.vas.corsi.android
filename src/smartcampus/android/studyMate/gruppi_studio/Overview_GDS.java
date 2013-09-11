package smartcampus.android.studyMate.gruppi_studio;

import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;

import eu.trentorise.smartcampus.studyMate.utilities.TabListener;

public class Overview_GDS extends SherlockFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final ActionBar ab = getSupportActionBar();
		ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		setTitle("Gruppi di studio");
		// ab.setHomeButtonEnabled(true);
		// ab.setDisplayHomeAsUpEnabled(true);

		/** TabHost will have Tabs */
		String tab1_txt = "Impegni";
		String tab2_txt = "Forum";

		// tab1

		Tab tab1 = ab
				.newTab()
				.setText(tab1_txt)
				.setTabListener(
						new TabListener<Forum_fragment>(this, "tab1",
								Forum_fragment.class));
		ab.addTab(tab1);

		// tab2
		Tab tab2 = ab
				.newTab()
				.setText(tab2_txt)
				.setTabListener(
						new TabListener<Forum_fragment>(this, "tab2",
								Forum_fragment.class));
		ab.addTab(tab2);

	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

}
