package smartcampus.android.studyMate.gruppi_studio;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeSet;

import smartcampus.android.template.standalone.R;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.example.model_classes.AttivitaStudio;
import com.example.model_classes.ChatObject;
import com.example.model_classes.GruppoDiStudio;
import com.example.model_classes.Servizio;
import com.example.model_classes.Studente;
import com.google.android.gms.maps.model.Tile;

import eu.trentorise.smartcampus.studyMate.models.Corso;

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
		Studente s1 = new Studente("Albert", "Einstein");
		Studente s2 = new Studente("Enrico", "Fermi");
		ArrayList<Studente> membri_gds = new ArrayList<Studente>();
		membri_gds.add(s1);
		membri_gds.add(s2);
		Date data1 = new Date();
		data1.setTime(5000);
		Time time = new Time(45);

		ArrayList<Servizio> servizi_monitorati_gds = new ArrayList<Servizio>();
		AttivitaStudio impegno1 = new AttivitaStudio(12, null,
				"titolo evento1", "luogo evento", "a123", data1,
				"descrizione attività", time, time, false, false, "oggetto",
				null, null);
		AttivitaStudio impegno2 = new AttivitaStudio(12, null,
				"titolo evento2", "luogo evento", "a123", data1,
				"descrizione attività", time, time, false, false, "oggetto",
				null, null);
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

		ArrayAdapter<String> adapter_spinner_materia = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, materie);
		adapter_spinner_materia
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_materia.setAdapter(adapter_spinner_materia);

		ArrayAdapter<String> adapter_spinner_nomigruppo = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, nomi_gruppi);
		adapter_spinner_materia
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_nome_gruppo.setAdapter(adapter_spinner_nomigruppo);

		ArrayAdapter<String> autotext_nomiMembri_adapter = new ArrayAdapter<String>(
				RicercaGruppiGenerale_activity.this,
				android.R.layout.simple_dropdown_item_1line, nomi_membri);
		autocomplete_ricercaXmembro.setAdapter(autotext_nomiMembri_adapter);

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
