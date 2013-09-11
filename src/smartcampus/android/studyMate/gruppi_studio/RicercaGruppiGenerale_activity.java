package smartcampus.android.studyMate.gruppi_studio;

import smartcampus.android.template.standalone.R;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuInflater;

public class RicercaGruppiGenerale_activity extends SherlockActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ricerca_gruppi_generale);

		// customizzazione grafica

		//ActionBar actionbar = getSupportActionBar();
		

		Spinner spinner_materia = (Spinner) findViewById(R.id.spinner_materie);
		Spinner spinner_nome_gruppo = (Spinner) findViewById(R.id.spinner_nomi_gruppi);
		AutoCompleteTextView autocomplete_ricercaXmembro = (AutoCompleteTextView) findViewById(R.id.autocomplete_ricerca_per_membro);

		// String[] materie=getMaterieFromWebSrevice();
		String[] materie = { "Analisi 1", "Programmazione funzionale",
				"Reti di calcolatori", "Algoritmi e strutture dati" };
		ArrayAdapter<String> adapter_spinner_materia = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, materie);
		adapter_spinner_materia
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_materia.setAdapter(adapter_spinner_materia);

		// String[] nomi_gruppi=getNomiGruppiForUser(User u);
		String[] nomi_gruppi = { "Gruppo 1", "Gruppo 2", "Gruppo 3",
				"Gruppo 4", "Gruppo 5" };
		ArrayAdapter<String> adapter_spinner_nomigruppo = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, nomi_gruppi);
		adapter_spinner_materia
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_nome_gruppo.setAdapter(adapter_spinner_nomigruppo);

		// String[] nomi_membri=getGDSmembersNames();
		String[] nomi_membri = {
				"Alberto Angela",
				"Giulio Cesare",
				"Bobo Vieri",
				"Sigmund Freud",
				"Ricardo izecson dos santos leite Kakà",
				"Pablo Diego José Francisco de Paula Juan Nepomuceno María de los Remedios Cipriano de la Santísima Trinidad Ruiz y Picasso" };
		ArrayAdapter<String> autotext_nomiMembri_adapter = new ArrayAdapter<String>(
				RicercaGruppiGenerale_activity.this,
				android.R.layout.simple_dropdown_item_1line, nomi_membri);
		autocomplete_ricercaXmembro.setAdapter(autotext_nomiMembri_adapter);

		// splitactionbar sottostante

	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		// TODO Auto-generated method stub

		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.ricerca_generale_menu, menu);

		return super.onCreateOptionsMenu(menu);
	}

}
