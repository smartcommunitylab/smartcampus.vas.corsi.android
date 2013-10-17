package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.android.studyMate.R;
import eu.trentorise.smartcampus.android.studyMate.models.AttivitaStudio;

public class ShowImpegnoGDS extends SherlockFragmentActivity {

	private AttivitaStudio contextualAttivitaStudio;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.show_impegno_gds);

		// personalizzazioje actionabar
		ActionBar actionbar = getSupportActionBar();
		actionbar.setTitle("Dettagli impegno");
		actionbar.setLogo(R.drawable.gruppistudio_icon_white);
		actionbar.setHomeButtonEnabled(true);
		actionbar.setDisplayHomeAsUpEnabled(true);
		/*
		 * recupero contextualAttivitastudio da contextualcollection
		 */
		if (!MyApplication.getContextualCollection().isEmpty()) {
			contextualAttivitaStudio = (AttivitaStudio) MyApplication
					.getContextualCollection().get(0);
		}

		TextView tv_oggetto = (TextView) findViewById(R.id.oggetto_impegno_gds);
		tv_oggetto.setText(contextualAttivitaStudio.getOggetto());
		TextView tv_data = (TextView) findViewById(R.id.text_data_impegno_gds);
		tv_data.setText(contextualAttivitaStudio.getData());
		TextView tv_ora = (TextView) findViewById(R.id.textOra_impegno_gds);
		tv_ora.setText(contextualAttivitaStudio.getStart());
		/*
		 * manca altra roba
		 */
		CheckBox c1 = (CheckBox) findViewById(R.id.CheckBox1_prenotazione_aule);
		CheckBox c2 = (CheckBox) findViewById(R.id.CheckBox2_mensa);
		CheckBox c3 = (CheckBox) findViewById(R.id.CheckBox3_tutoring);
		CheckBox c4 = (CheckBox) findViewById(R.id.CheckBox4_biblioteca);

		if (contextualAttivitaStudio.isPrenotazione_aule()) {
			c1.setChecked(true);
		}
		if (contextualAttivitaStudio.isMensa()) {
			c2.setChecked(true);
		}
		if (contextualAttivitaStudio.isTutoring()) {
			c3.setChecked(true);
		}
		if (contextualAttivitaStudio.isBiblioteca()) {
			c4.setChecked(true);
		}

		/*
		 * e cos� via
		 */
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			ShowImpegnoGDS.this.finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

}
