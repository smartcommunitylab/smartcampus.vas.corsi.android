package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import smartcampus.android.template.standalone.R;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import eu.trentorise.smartcampus.android.studyMate.models.AttivitaStudio;

public class ShowImpegnoGDS extends SherlockFragmentActivity {

	private AttivitaStudio contextualAttivitaStudio;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.show_impegno_gds);
		/*
		 * recupero contextualAttivitastudio da contextualcollection
		 */
		if (!MyApplication.getContextualCollection().isEmpty()) {
			contextualAttivitaStudio = (AttivitaStudio) MyApplication
					.getContextualCollection().get(0);
		}

		Toast.makeText(MyApplication.getAppContext(),
				contextualAttivitaStudio.getOggetto(), Toast.LENGTH_SHORT)
				.show();

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
		CheckBox c5 = (CheckBox) findViewById(R.id.CheckBox4_biblioteca);

		if (contextualAttivitaStudio.isPrenotazione_aule()) {
			c1.setChecked(true);
		}
		/*
		 * e così via
		 */
	}

}
