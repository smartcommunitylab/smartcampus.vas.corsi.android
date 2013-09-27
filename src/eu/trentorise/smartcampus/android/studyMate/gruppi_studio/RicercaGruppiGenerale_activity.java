package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeSet;

import smartcampus.android.template.standalone.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.android.studyMate.models.AttivitaStudio;
import eu.trentorise.smartcampus.android.studyMate.models.ChatObject;
import eu.trentorise.smartcampus.android.studyMate.models.GruppoDiStudio;
import eu.trentorise.smartcampus.android.studyMate.models.Servizio;
import eu.trentorise.smartcampus.android.studyMate.models.Studente;

public class RicercaGruppiGenerale_activity extends SherlockActivity {

	TreeSet<String> materieset;
	ArrayList<String> nomi_gruppi;
	TreeSet<String> nomi_membriset;
	ArrayList<String> materie;
	ArrayList<String> nomi_membri;
	Spinner spinner_materia;
	Spinner spinner_nome_gruppo;
	AutoCompleteTextView autocomplete_ricercaXmembro;
	ArrayList<GruppoDiStudio> allChoosable_gds;

	static int fakecount = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ricerca_gruppi_generale);

		ActionBar actionbar = getSupportActionBar();
		actionbar.setLogo(R.drawable.gruppistudio_icon_white);
		actionbar.setHomeButtonEnabled(true);
		actionbar.setDisplayHomeAsUpEnabled(true);

		materieset = new TreeSet<String>();
		nomi_gruppi = new ArrayList<String>();
		nomi_membriset = new TreeSet<String>();
		materie = new ArrayList<String>();
		nomi_membri = new ArrayList<String>();

		spinner_materia = (Spinner) findViewById(R.id.spinner_materie);
		spinner_nome_gruppo = (Spinner) findViewById(R.id.spinner_nomi_gruppi);
		autocomplete_ricercaXmembro = (AutoCompleteTextView) findViewById(R.id.autocomplete_ricerca_per_membro);

		// ####################################
		// creazione gruppi fake per popolare grafica, dovrei in realtà
		// recuperare tutto dal web
		Studente s1 = new Studente();
		Studente s2 = new Studente();
		ArrayList<Studente> membri_gds = new ArrayList<Studente>();
		membri_gds.add(s1);
		membri_gds.add(s2);
		Date data1 = new Date();
		data1.setTime(5000);
		Time time = new Time(45);

		ArrayList<Servizio> servizi_monitorati_gds = new ArrayList<Servizio>();
		AttivitaStudio impegno1 = new AttivitaStudio("oggetto AS1", null,
				servizi_monitorati_gds, 12, null, "titolo as1", "Povo", "a201",
				data1, "descrizione as1", time, time, false, false);
		AttivitaStudio impegno2 = new AttivitaStudio("oggetto AS2", null,
				servizi_monitorati_gds, 13, null, "titolo as2", "Povo", "a201",
				data1, "descrizione as2", time, time, false, false);

		ArrayList<AttivitaStudio> attivita_studio_gds = new ArrayList<AttivitaStudio>();
		attivita_studio_gds.add(impegno1);
		attivita_studio_gds.add(impegno2);

		ArrayList<ChatObject> forum = new ArrayList<ChatObject>();

		GruppoDiStudio gds1 = new GruppoDiStudio("Programmazione 1",
				"LoveRSEBA", membri_gds, servizi_monitorati_gds,
				attivita_studio_gds, 1, forum, getResources().getDrawable(
						R.drawable.prouno_logo));
		GruppoDiStudio gds2 = new GruppoDiStudio("Matematica Discreta 1",
				"Ghiloni docet", membri_gds, servizi_monitorati_gds,
				attivita_studio_gds, 1, forum, getResources().getDrawable(
						R.drawable.discreta_logo));
		GruppoDiStudio gds3 = new GruppoDiStudio("Reti di calcolatori",
				"Renato <3", membri_gds, servizi_monitorati_gds,
				attivita_studio_gds, 2, forum, getResources().getDrawable(
						R.drawable.reti_calcolatori_logo));
		GruppoDiStudio gds4 = new GruppoDiStudio("Algoritmi e strutture dati",
				"Djikstra4President", membri_gds, servizi_monitorati_gds,
				attivita_studio_gds, 2, forum, getResources().getDrawable(
						R.drawable.algoritmi_logo));

		allChoosable_gds = new ArrayList<GruppoDiStudio>();// qui
		// fare
		// un
		// recupero
		// info
		// dal
		// web
		allChoosable_gds.add(gds1);
		allChoosable_gds.add(gds2);
		allChoosable_gds.add(gds3);
		allChoosable_gds.add(gds4);

		// ####################################

		// inizializzaGrafiche();
		for (GruppoDiStudio gds : allChoosable_gds) {
			materieset.add(gds.getMateria());
			nomi_gruppi.add(gds.getNome());
			for (Studente studente : gds.getMembri()) {
				nomi_membriset.add(studente.getNome() + " "
						+ studente.getCognome());
			}

		}
		for (String string : materieset) {
			materie.add(string);
		}
		for (String string : nomi_membriset) {
			nomi_membri.add(string);
		}

		materie.add("Tutte");
		ArrayAdapter<String> adapter_spinner_materia = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, materie);
		adapter_spinner_materia
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_materia.setAdapter(adapter_spinner_materia);
		int spinnerPosition0 = adapter_spinner_materia.getPosition("Tutte");
		spinner_nome_gruppo.setSelection(spinnerPosition0);

		nomi_gruppi.add("Tutti");
		ArrayAdapter<String> adapter_spinner_nomigruppo = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, nomi_gruppi);
		adapter_spinner_materia
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_nome_gruppo.setAdapter(adapter_spinner_nomigruppo);
		int spinnerPosition1 = adapter_spinner_nomigruppo.getPosition("Tutti");
		spinner_nome_gruppo.setSelection(spinnerPosition1);

		ArrayAdapter<String> autotext_nomiMembri_adapter = new ArrayAdapter<String>(
				RicercaGruppiGenerale_activity.this,
				android.R.layout.simple_dropdown_item_1line, nomi_membri);
		autocomplete_ricercaXmembro.setAdapter(autotext_nomiMembri_adapter);

		// listener sugli spinner
		SpinnerChangeListenerUpdater listener = new SpinnerChangeListenerUpdater(
				spinner_materia, spinner_nome_gruppo, allChoosable_gds,
				materie, nomi_gruppi, null);
		spinner_materia.setOnItemSelectedListener(listener);
		spinner_nome_gruppo.setOnItemSelectedListener(listener);

	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		// TODO Auto-generated method stub

		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.ricerca_generale_menu, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home: {
			RicercaGruppiGenerale_activity.this.finish();
		}

		case R.id.action_ricerca_GO:
			/*
			 * String materia = ((Spinner) RicercaGruppiGenerale_activity.this
			 * .findViewById(R.id.spinner_materie)).getSelectedItem()
			 * .toString(); String nome_gruppo = ((Spinner)
			 * RicercaGruppiGenerale_activity.this
			 * .findViewById(R.id.spinner_nomi_gruppi)).getSelectedItem()
			 * .toString(); String nome_studente = ((AutoCompleteTextView)
			 * RicercaGruppiGenerale_activity.this
			 * .findViewById(R.id.autocomplete_ricerca_per_membro))
			 * .getText().toString();
			 * 
			 * Toast.makeText(RicercaGruppiGenerale_activity.this, materia + " "
			 * + nome_gruppo + ", " + nome_studente, Toast.LENGTH_SHORT).show();
			 * 
			 * Toast.makeText(RicercaGruppiGenerale_activity.this,
			 * "gruppo selezionato..", Toast.LENGTH_SHORT).show();
			 */
			GruppoDiStudio gds_selezionato = allChoosable_gds.get(fakecount);
			++fakecount;
			if (fakecount < 4) {
				// save on server
				MyApplication.getContextualCollection().add(gds_selezionato);
			} else {
				for (GruppoDiStudio gds : allChoosable_gds) {
					// gds.getMembri().add(myself);
					// save on server...
					MyApplication.getContextualCollection().add(gds);
				}
			}

			Intent intent = new Intent(RicercaGruppiGenerale_activity.this,
					Lista_GDS_activity.class);
			startActivity(intent);
			return true;
		default:
			return true;

		}

	}

}

final class SpinnerChangeListenerUpdater implements OnItemSelectedListener {

	Spinner materie;
	Spinner nomi_gds;
	ArrayList<GruppoDiStudio> all_choosable;
	ArrayList<String> materie_values;
	ArrayList<String> nomi_gds_values;
	ArrayList<String> nomi_componenti_values;

	public SpinnerChangeListenerUpdater(Spinner materie, Spinner nomi_gds,
			ArrayList<GruppoDiStudio> all_choosable,
			ArrayList<String> materie_values,
			ArrayList<String> nomi_gds_values,
			ArrayList<String> nomi_componenti_values) {
		super();
		this.materie = materie;
		this.nomi_gds = nomi_gds;
		this.all_choosable = all_choosable;
		this.materie_values = materie_values;
		this.nomi_gds_values = nomi_gds_values;
		this.nomi_componenti_values = nomi_componenti_values;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		// TODO Auto-generated method stub

		if (parent.getId() == materie.getId()) {// se viene selezionata una
			// materia
			String selected_value = (String) parent.getItemAtPosition(pos);
			if (selected_value == "Tutte") {
				return;
			}
			nomi_gds_values.clear();
			for (GruppoDiStudio gds : all_choosable) {
				if (gds.getMateria() == selected_value) {
					nomi_gds_values.add(gds.getNome());
				}
			}

			((ArrayAdapter<String>) nomi_gds.getAdapter())
					.notifyDataSetChanged();

		} else if (parent.getId() == nomi_gds.getId()) {// se viene selezionato
			// il nome di un gds

		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

}