package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewConfiguration;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.android.studyMate.models.Allegato;
import eu.trentorise.smartcampus.android.studyMate.models.AttivitaDiStudio;
import eu.trentorise.smartcampus.studymate.R;

public class ShowImpegnoGDS extends SherlockFragmentActivity {

	AttivitaDiStudio contextualAttivitaStudio;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.show_impegno_gds);

		// codice per sistemare l'actionoverflow
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception ex) {
			// Ignore
		}

		// personalizzazioje actionabar
		ActionBar actionbar = getSupportActionBar();
		actionbar.setTitle("Dettagli impegno");
		actionbar.setLogo(R.drawable.gruppistudio_icon_white);
		actionbar.setHomeButtonEnabled(true);
		actionbar.setDisplayHomeAsUpEnabled(true);
		/*
		 * recupero contextualAttivitaStudio
		 */
		Bundle myextras = getIntent().getExtras();
		contextualAttivitaStudio = (AttivitaDiStudio) myextras
				.getSerializable("contextualAttivitaStudio");

		TextView tv_oggetto = (TextView) findViewById(R.id.oggetto_showgds);
		tv_oggetto.setText(contextualAttivitaStudio.getTopic());
		TextView tv_data = (TextView) findViewById(R.id.text_data_impegno_showgds);
		Date data = contextualAttivitaStudio.getData();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		tv_data.setText(format.format(data));
		ListView listview_allegati = (ListView) findViewById(R.id.lista_allegati_showgds);
		ArrayList<Allegato> contextualAllegatis = null; /*
														 * contextualAttivitaStudio
														 * .getAllegati();
														 */
		if (contextualAllegatis == null || contextualAllegatis.isEmpty()) {
			Toast.makeText(MyApplication.getAppContext(),
					"non ci sono allegati ne mostro uno per prova",
					Toast.LENGTH_SHORT).show();
			contextualAllegatis = new ArrayList<Allegato>();
			Allegato fake = new Allegato(null, "nome allegato finto.pdf");
			contextualAllegatis.add(fake);
			contextualAllegatis.add(fake);
		}
		Allegati_to_list_arrayadapter adapter = new Allegati_to_list_arrayadapter(
				ShowImpegnoGDS.this, R.id.lista_allegati_showgds,
				contextualAllegatis);
		listview_allegati.setAdapter(adapter);
		/*
		 * manca altra roba
		 */
		// CheckBox c1 = (CheckBox)
		// findViewById(R.id.CheckBox1_prenotazione_aule);
		// CheckBox c2 = (CheckBox) findViewById(R.id.CheckBox2_mensa);
		// CheckBox c3 = (CheckBox) findViewById(R.id.CheckBox3_tutoring);
		// CheckBox c4 = (CheckBox) findViewById(R.id.CheckBox4_biblioteca);

		// if (contextualAttivitaStudio.isPrenotazione_aule()) {
		// c1.setChecked(true);
		// }
		// if (contextualAttivitaStudio.isMensa()) {
		// c2.setChecked(true);
		// }
		// if (contextualAttivitaStudio.isTutoring()) {
		// c3.setChecked(true);
		// }
		// if (contextualAttivitaStudio.isBiblioteca()) {
		// c4.setChecked(true);
		// }

		/*
		 * e così via
		 */
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.menu_showimpegno_gds, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			ShowImpegnoGDS.this.finish();
			return true;
		case R.id.action_modifica_impegno:
			Intent intent = new Intent(ShowImpegnoGDS.this,
					ModifiyAttivitaStudio.class);
			intent.putExtra("impegno_da_modificare", contextualAttivitaStudio);
			startActivity(intent);
			return super.onOptionsItemSelected(item);

		case R.id.action_elimina_impegno:
			Toast.makeText(ShowImpegnoGDS.this, "elimina impegno",
					Toast.LENGTH_SHORT).show();
			return super.onOptionsItemSelected(item);
		default:
			return super.onOptionsItemSelected(item);
		}

	}

}
