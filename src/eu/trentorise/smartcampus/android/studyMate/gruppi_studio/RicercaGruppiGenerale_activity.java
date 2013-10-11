package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
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
import eu.trentorise.smartcampus.android.studyMate.models.Dipartimento;
import eu.trentorise.smartcampus.android.studyMate.models.GruppoDiStudio;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ricerca_gruppi_generale);

		// personalizzazione actionabar
		ActionBar actionbar = getSupportActionBar();
		actionbar.setLogo(R.drawable.gruppistudio_icon_white);
		actionbar.setHomeButtonEnabled(true);
		actionbar.setDisplayHomeAsUpEnabled(true);

		// creazione istanze strutture dati di supporto
		materieset = new TreeSet<String>();
		nomi_gruppi = new ArrayList<String>();
		nomi_membriset = new TreeSet<String>();
		materie = new ArrayList<String>();
		nomi_membri = new ArrayList<String>();
		// recupero delle componenti grafiche dal layout
		spinner_materia = (Spinner) findViewById(R.id.spinner_materie);
		spinner_nome_gruppo = (Spinner) findViewById(R.id.spinner_nomi_gruppi);
		autocomplete_ricercaXmembro = (AutoCompleteTextView) findViewById(R.id.autocomplete_ricerca_per_membro);
		// generazione di gruppi di studio fake e setting della ricerca tra
		// gruppi
		placeholder_gruppidistudio fake = new placeholder_gruppidistudio();
		fake.initialize_some_gds();
		allChoosable_gds = fake.getChoosable_gds();

		// disegno delle risorse in grafica
		inizializzaRisorseSpinner_TextView();
	}

	void inizializzaRisorseSpinner_TextView() {
		// inizializzaGrafiche();
		nomi_gruppi.add("Tutti");
		for (GruppoDiStudio gds : allChoosable_gds) {
			materieset.add(gds.getMateria());
			nomi_gruppi.add(gds.getNome());
			for (Studente studente : gds.getMembri()) {
				nomi_membriset.add(studente.getNome() + " "
						+ studente.getCognome());
			}

		}
		materie.add("Tutte");
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
			return true;
		}

		case R.id.action_ricerca_GO: {
			String materia = ((Spinner) RicercaGruppiGenerale_activity.this
					.findViewById(R.id.spinner_materie)).getSelectedItem()
					.toString();
			String nome_gruppo = ((Spinner) RicercaGruppiGenerale_activity.this
					.findViewById(R.id.spinner_nomi_gruppi)).getSelectedItem()
					.toString();
			// generazione nomi_studenti da passare
			ArrayList<String> nomi_studenti = new ArrayList<String>();
			Studente s1 = new Studente();
			s1.setNome("Albert");
			s1.setCognome("Einstein");
			s1.setFoto_studente(getResources().getDrawable(R.drawable.einstein));
			Studente s2 = new Studente();
			s2.setNome("Enrico");
			s2.setCognome("Fermi");
			s2.setFoto_studente(getResources().getDrawable(R.drawable.fermi));
			// nomi_studenti.add(/* vari eventuali */null);
			// String nome_studente = ((AutoCompleteTextView)
			// RicercaGruppiGenerale_activity.this
			// .findViewById(R.id.autocomplete_ricerca_per_membro))
			// .getText().toString();

			Intent intent = new Intent(RicercaGruppiGenerale_activity.this,
					Display_GDS_research_results.class);
			intent.putExtra("Selected_materia", materia);
			intent.putExtra("Selected_nome_gruppo", nome_gruppo);
			MyApplication.getContextualCollection().add(nomi_studenti);
			MyApplication.getContextualCollection().add(allChoosable_gds);

			startActivity(intent);
			return true;
		}

		default:
			return super.onOptionsItemSelected(item);

		}

	}

	final class placeholder_gruppidistudio {
		ArrayList<GruppoDiStudio> Choosable_gds;

		public placeholder_gruppidistudio() {
			// TODO Auto-generated constructor stub
		}

		public void initialize_some_gds() {
			Choosable_gds = new ArrayList<GruppoDiStudio>();
			ArrayList<Studente> membri_gds = new ArrayList<Studente>();

			ArrayList<AttivitaStudio> attivita_studio_gds = new ArrayList<AttivitaStudio>();
			ArrayList<ChatObject> forum = new ArrayList<ChatObject>();

			// ####################################
			// creazione gruppi fake per popolare grafica, dovrei in realtà
			// recuperare tutto dal web
			Dipartimento dipartimento = new Dipartimento();
			dipartimento.setNome("Scienze dell'Informazione");
			Studente s1 = new Studente();
			s1.setNome("Albert");
			s1.setCognome("Einstein");
			s1.setDipartimento(dipartimento);
			s1.setAnno_corso("2");
			s1.setFoto_studente(getResources().getDrawable(R.drawable.einstein));
			Studente s2 = new Studente();
			s2.setNome("Enrico");
			s2.setCognome("Fermi");
			s2.setFoto_studente(getResources().getDrawable(R.drawable.fermi));
			s2.setDipartimento(dipartimento);
			s2.setAnno_corso("2");

			membri_gds.add(s1);
			membri_gds.add(s2);
			Date data1 = new Date();
			data1.setTime(5000);

			AttivitaStudio impegno1 = new AttivitaStudio("oggetto1", null, 14,
					null, "titolo as1", "Povo", "a203", "02/10/2013",
					"descrizione as", "09:00", false, false, false, false,
					false, false);
			AttivitaStudio impegno2 = new AttivitaStudio("oggetto2", null, 14,
					null, "titolo as2", "Povo", "a203", "02/10/2013",
					"descrizione as", "09:00", false, false, false, false,
					false, false);

			attivita_studio_gds.add(impegno1);
			attivita_studio_gds.add(impegno2);

			GruppoDiStudio gds1 = new GruppoDiStudio("Programmazione 1",
					"LoveRSEBA", membri_gds, null, attivita_studio_gds, 1,
					forum, MyApplication.getAppContext().getResources()
							.getDrawable(R.drawable.prouno_logo));
			GruppoDiStudio gds2 = new GruppoDiStudio("Matematica Discreta 1",
					"Ghiloni docet", membri_gds, null, attivita_studio_gds, 1,
					forum, MyApplication.getAppContext().getResources()
							.getDrawable(R.drawable.discreta_logo));
			GruppoDiStudio gds3 = new GruppoDiStudio("Reti di calcolatori",
					"Renato <3", membri_gds, null, attivita_studio_gds, 2,
					forum, MyApplication.getAppContext().getResources()
							.getDrawable(R.drawable.reti_calcolatori_logo));
			GruppoDiStudio gds4 = new GruppoDiStudio(
					"Algoritmi e strutture dati", "Djikstra4President",
					membri_gds, null, attivita_studio_gds, 2, forum,
					MyApplication.getAppContext().getResources()
							.getDrawable(R.drawable.algoritmi_logo));

			// fine placeholder
			// ############################
			// qui
			// fare
			// un
			// recupero
			// info
			// dal
			// web
			Choosable_gds.add(gds1);
			Choosable_gds.add(gds2);
			Choosable_gds.add(gds3);
			Choosable_gds.add(gds4);

			// ####################################
			/*
			 * bug nel codice commentato sotto il codice intenderebbe non
			 * mostrare in grafica i gruppi di studio che non possono essere
			 * ricercati, rimuovendoli dall'inisieme di tutti i possibili gruppi
			 * di studio
			 */
			// ArrayList<GruppoDiStudio> alreadyOfTheUser =
			// Lista_GDS_activity.user_gds_list;
			// for (GruppoDiStudio gds_already : alreadyOfTheUser) {
			// for (GruppoDiStudio gds_possible : Choosable_gds) {
			// if (gds_already.compareTo(gds_possible) == 0)
			// Choosable_gds.remove(gds_possible);
			// }
			// }
		}

		public ArrayList<GruppoDiStudio> getChoosable_gds() {
			initialize_some_gds();
			return Choosable_gds;
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

	@SuppressWarnings("unchecked")
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		// TODO Auto-generated method stub

		if (parent.getId() == materie.getId()) {// se viene selezionata una
			// materia
			String selected_value = (String) parent.getItemAtPosition(pos);
			nomi_gds_values.clear();

			if (selected_value == "Tutte") {
				nomi_gds_values.add("Tutti");
				for (GruppoDiStudio gds : all_choosable) {
					nomi_gds_values.add(gds.getNome());
				}

			} else {
				for (GruppoDiStudio gds : all_choosable) {
					if (gds.getMateria() == selected_value) {
						nomi_gds_values.add(gds.getNome());
					}
				}
				nomi_gds_values.add("Tutti");
			}
			((ArrayAdapter<String>) nomi_gds.getAdapter())
					.notifyDataSetChanged();
			return;
		} else if (parent.getId() == nomi_gds.getId()) {// se viene selezionato
			// il nome di un gds
			String selected_value = (String) parent.getItemAtPosition(pos);
			materie_values.clear();

			if (selected_value == "Tutti") {
				Set<String> set = new TreeSet<String>();
				for (GruppoDiStudio gds : all_choosable) {
					set.add(gds.getMateria());
				}
				materie_values.add("Tutte");
				for (String string : set) {
					materie_values.add(string);
				}
			} else {
				for (GruppoDiStudio gds : all_choosable) {
					if (gds.getNome() == selected_value)
						materie_values.add(gds.getMateria());
				}
				materie_values.add("Tutte");
			}

			((ArrayAdapter<String>) materie.getAdapter())
					.notifyDataSetChanged();
			return;
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
	}

	public ArrayList<GruppoDiStudio> getAll_choosable() {
		return all_choosable;
	}

}
