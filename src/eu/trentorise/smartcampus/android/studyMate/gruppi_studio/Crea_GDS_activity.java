package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import smartcampus.android.template.standalone.R;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class Crea_GDS_activity extends SherlockActivity {
	AutoCompleteTextView tv_materia;
	AutoCompleteTextView tv_nome_gds;
	AutoCompleteTextView tv_invitati;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crea__gds_activity);

		ActionBar actionbar = getSupportActionBar();
		actionbar.setTitle("Nuovo Gruppo");
		actionbar.setLogo(R.drawable.gruppistudio_icon_white);
		actionbar.setHomeButtonEnabled(true);
		actionbar.setDisplayHomeAsUpEnabled(true);

		tv_materia = (AutoCompleteTextView) findViewById(R.id.scegli_materia);
		tv_nome_gds = (AutoCompleteTextView) findViewById(R.id.scegli_nome_gruppo);
		tv_invitati = (AutoCompleteTextView) findViewById(R.id.invita_compagni_gds);

	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		MenuInflater inflater = getSherlock().getMenuInflater();
		inflater.inflate(R.menu.crea__gds_activity, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home: {
			Crea_GDS_activity.this.finish();
		}
		case R.id.action_done: {
			String materia = tv_materia.getText().toString();
			String nome = tv_nome_gds.getText().toString();

			Toast.makeText(
					getApplicationContext(),
					"Gruppo: " + nome + " della materia " + materia
							+ " creato con successo", Toast.LENGTH_SHORT)
					.show();
			Crea_GDS_activity.this.finish();
		}

		default:
			return super.onOptionsItemSelected(item);
		}

	}

}
