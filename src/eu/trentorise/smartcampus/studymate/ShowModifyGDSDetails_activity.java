package eu.trentorise.smartcampus.studymate;

import android.os.Bundle;
import android.widget.AutoCompleteTextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.android.studyMate.models.GruppoDiStudio;

public class ShowModifyGDSDetails_activity extends SherlockActivity {
	private GruppoDiStudio contextualGDS;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_modify_gdsdetails_activity);
		ActionBar actionbar = getSupportActionBar();
		actionbar.setTitle("Dettagli Gruppo");
		actionbar.setLogo(R.drawable.gruppistudio_icon_white);
		actionbar.setHomeButtonEnabled(true);
		actionbar.setDisplayHomeAsUpEnabled(true);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			contextualGDS = (GruppoDiStudio) extras
					.getSerializable("contextualGDS");

			//setup degli elementi grafici
			AutoCompleteTextView scegli_nome_gds_tv = (AutoCompleteTextView) findViewById(R.id.scegli_nome_gruppo_modifica_gds);
			scegli_nome_gds_tv.setText(contextualGDS.getNome());
		}

	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getSherlock().getMenuInflater();
		inflater.inflate(R.menu.show_modify_gdsdetails_activity, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home: {
			ShowModifyGDSDetails_activity.this.finish();
			return super.onOptionsItemSelected(item);
		}
		case R.id.action_done: {
			// asynctask per aggiungere un gruppo di studio appena creato ai
			// gruppi di studio persoanli
			// MyAsyncTask task = new MyAsyncTask(Crea_GDS_activity.this);
			// task.execute();
			return super.onOptionsItemSelected(item);
		}
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
