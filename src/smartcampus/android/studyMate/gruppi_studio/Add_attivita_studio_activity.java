package smartcampus.android.studyMate.gruppi_studio;

import smartcampus.android.template.standalone.R;
import android.os.Bundle;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class Add_attivita_studio_activity extends SherlockFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_attivita_studio_activity);
		ActionBar actionbar = getSupportActionBar();
		actionbar.setTitle("Nuova attivit� di studio");
		actionbar.setLogo(R.drawable.gruppistudio_icon_white);
		actionbar.setHomeButtonEnabled(true);
		actionbar.setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.add_attivita_studio_activity, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_done: {
			Toast.makeText(getApplicationContext(), "attivit� studio creata",
					Toast.LENGTH_SHORT).show();
			Add_attivita_studio_activity.this.finish();
		}
		case android.R.id.home: {
			Add_attivita_studio_activity.this.finish();
		}

		default:
			return super.onOptionsItemSelected(item);
		}

	}
}
