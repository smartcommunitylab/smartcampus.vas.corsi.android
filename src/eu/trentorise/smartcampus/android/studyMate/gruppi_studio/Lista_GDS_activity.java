package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.android.studyMate.R;
import eu.trentorise.smartcampus.android.studyMate.models.AttivitaStudio;
import eu.trentorise.smartcampus.android.studyMate.models.ChatObject;
import eu.trentorise.smartcampus.android.studyMate.models.Dipartimento;
import eu.trentorise.smartcampus.android.studyMate.models.GruppoDiStudio;
import eu.trentorise.smartcampus.android.studyMate.models.Studente;
import eu.trentorise.smartcampus.android.studyMate.start.MyUniActivity;

public class Lista_GDS_activity extends SherlockFragmentActivity {

	public static ArrayList<GruppoDiStudio> user_gds_list = new ArrayList<GruppoDiStudio>();
	private static boolean isShownAsList = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_gds_activity);
		ActionBar actionbar = getSupportActionBar();
		actionbar.setTitle("I miei gruppi");
		actionbar.setLogo(R.drawable.gruppistudio_icon_white);
		actionbar.setHomeButtonEnabled(true);
		actionbar.setDisplayHomeAsUpEnabled(true);

		// butto dentro due gruppi per non dover sempre fare l'iscirzione
		funzcheaggiungeduegruppi();

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

		// retrieve gruppi
		/*
		 * user_gds_list = getUtente().getFrequentedGDS ()
		 */

		if (!MyApplication.getContextualCollection().isEmpty()) {
			// Lista_GDS_activity può essere lanciata dalla home o al termine di
			// una iscrizione ad un corso

			// questo codice gestisce il caso in cui al termine di una ricerca
			// sia stata effettuata l'iscrzione ad un corso appena selezionato
			for (Object gds : MyApplication.getContextualCollection()) {
				user_gds_list.add((GruppoDiStudio) gds);
				// save this info onserver
			}
			MyApplication.getContextualCollection().clear();
		}

		// se la user_gds_list è vuota proponiamo all'utente di fare qlcs..
		TextView tv = (TextView) findViewById(R.id.suggerimento_lista_vuota);
		if (user_gds_list.isEmpty()) {
			tv.setText("Non sei ancora iscritto ad alcun gruppo di studio!\nUtilizza il menù in alto a destra per iscriverti ad un gruppo di studio");
		} else {
			tv.setVisibility(View.GONE);
		}

		// ordinamento dei gruppi di studio
		Collections.sort(user_gds_list);

		// inizializza la grafica in base allo stato booleano di isShownAsList
		if (isShownAsList) {
			FragmentTransaction ft = this.getSupportFragmentManager()
					.beginTransaction();
			Fragment fragment = new ViewGruppiList_Fragment();
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			ft.replace(R.id.list_container, fragment);
			ft.addToBackStack(null);
			ft.commit();
		} else {
			FragmentTransaction ft = this.getSupportFragmentManager()
					.beginTransaction();
			Fragment fragment = new ViewGruppiGrid_Fragment();
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			ft.replace(R.id.list_container, fragment);
			ft.addToBackStack(null);
			ft.commit();
		}

		/*
		 * sto commentone sarebbe il tour guidato che ho tolto per ora if
		 * (user_gds_list.isEmpty()) { AlertDialog.Builder builder = new
		 * AlertDialog.Builder( Lista_GDS_activity.this);
		 * builder.setTitle("Avvio guidato"); builder.setMessage(
		 * "Scopri le funzionalità dei Gruppi di Studio! Vuoi iscriverti ad un gruppo di studio?"
		 * ); builder.setPositiveButton("Si", new
		 * DialogInterface.OnClickListener() {
		 * 
		 * @Override public void onClick(DialogInterface dialog, int which) { //
		 * shadowdialog = new Dialog( Lista_GDS_activity.this);
		 * 
		 * shadowdialog.getWindow().requestFeature( (int)
		 * Window.FEATURE_NO_TITLE);
		 * 
		 * shadowdialog.getWindow().setFlags(
		 * WindowManager.LayoutParams.FLAG_FULLSCREEN,
		 * WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 * 
		 * // layout to display shadowdialog
		 * .setContentView(R.layout.tutorial_dialog_layout);
		 * 
		 * // set color transpartent
		 * shadowdialog.getWindow().setBackgroundDrawable( new
		 * ColorDrawable(Color.TRANSPARENT));
		 * 
		 * shadowdialog.show(); Button ok = (Button) shadowdialog
		 * .findViewById(R.id.ok_button); ok.setOnClickListener(new
		 * OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { stub shadowdialog.dismiss();
		 * } });
		 * 
		 * } }); builder.setNegativeButton("No", new
		 * DialogInterface.OnClickListener() {
		 * 
		 * @Override public void onClick(DialogInterface dialog, int which) { //
		 * TODO Auto-generated method stub dialog.dismiss(); Intent i = new
		 * Intent(Lista_GDS_activity.this, MyUniActivity.class);
		 * startActivity(i);
		 * 
		 * // poi faremo qlcs di logico } });
		 * 
		 * AlertDialog alert = builder.create(); alert.show(); }
		 */

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.lista__gds_activity, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		if (user_gds_list.isEmpty()) {
			MenuItem item = menu.findItem(R.id.action_cambia_layout);
			item.setEnabled(false);
			item.setVisible(false);
		}
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home: {
			Intent intent = new Intent(Lista_GDS_activity.this,
					MyUniActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return super.onOptionsItemSelected(item);
		}

		case R.id.action_cambia_layout:

			// Drawable actualicon = item.getIcon();

			if (isShownAsList) {
				// azione se
				item.setIcon(R.drawable.collections_view_as_list);
				isShownAsList = false;

				this.getSupportFragmentManager().popBackStack();
				FragmentTransaction ft = this.getSupportFragmentManager()
						.beginTransaction();
				Fragment fragment = new ViewGruppiGrid_Fragment();
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
				ft.replace(R.id.list_container, fragment);
				ft.addToBackStack(null);
				ft.commit();

			} else {
				// azione
				item.setIcon(R.drawable.collections_view_as_grid);
				isShownAsList = true;

				this.getSupportFragmentManager().popBackStack();
				FragmentTransaction ft = this.getSupportFragmentManager()
						.beginTransaction();
				Fragment fragment = new ViewGruppiList_Fragment();
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
				ft.replace(R.id.list_container, fragment);
				ft.addToBackStack(null);
				ft.commit();

			}
			return super.onOptionsItemSelected(item);
		case R.id.action_iscriviti_nuovo_gruppo: {
			Intent intent = new Intent(getApplicationContext(),
					RicercaGruppiGenerale_activity.class);
			startActivity(intent);
			return super.onOptionsItemSelected(item);
		}

		case R.id.action_crea_gruppo: {
			Intent intent = new Intent(getApplicationContext(),
					Crea_GDS_activity.class);
			startActivity(intent);
			return super.onOptionsItemSelected(item);
		}

		default:
			return super.onOptionsItemSelected(item);
		}

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent goToMyUniActivity = new Intent(getApplicationContext(),
				MyUniActivity.class);
		goToMyUniActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(goToMyUniActivity);

	}

	public ArrayList<GruppoDiStudio> getUser_gds_list() {
		return user_gds_list;
	}

	private void funzcheaggiungeduegruppi() {
		// butto dentro due gruppi all'inizio per non dover sempre iscrivermi la
		// prima volta
		ArrayList<Studente> membri_gds = new ArrayList<Studente>();

		ArrayList<AttivitaStudio> attivita_studio_gds = new ArrayList<AttivitaStudio>();
		ArrayList<ChatObject> forum = new ArrayList<ChatObject>();

		// ####################################
		// creazione gruppi fake per popolare grafica, dovrei in realtà
		// recuperare tutto dal web
		Dipartimento dipartimento = new Dipartimento();
		dipartimento.setNome("Scienze dell'Informazione");
		Studente s1 = new Studente();

		s1.setNome("Federico");
		s1.setCognome("Rossi");
		s1.setDipartimento(dipartimento);
		s1.setAnno_corso("2");
		// s1.setFoto_studente(getResources().getDrawable(R.drawable.einstein));

		Studente s2 = new Studente();

		s2.setNome("Gabriele");
		s2.setCognome("Bianchi");

		// s2.setFoto_studente(getResources().getDrawable(R.drawable.fermi));
		s2.setDipartimento(dipartimento);
		s2.setAnno_corso("2");

		membri_gds.add(s1);
		membri_gds.add(s2);
		Date data1 = new Date();
		data1.setTime(5000);

		AttivitaStudio impegno1 = new AttivitaStudio("oggetto1", null, 14,
				null, "titolo as1", "Povo", "a203", "02/10/2013",
				"descrizione as", "09:00", false, false, false, false, false,
				false);
		AttivitaStudio impegno2 = new AttivitaStudio("oggetto2", null, 14,
				null, "titolo as2", "Povo", "a203", "02/10/2013",
				"descrizione as", "09:00", false, false, false, false, false,
				false);

		attivita_studio_gds.add(impegno1);
		attivita_studio_gds.add(impegno2);

		GruppoDiStudio gds2 = new GruppoDiStudio("Matematica Discreta 1",
				"GhiloniDOC", membri_gds, null, attivita_studio_gds, 1, forum,
				MyApplication.getAppContext().getResources()
						.getDrawable(R.drawable.discreta_logo));
		GruppoDiStudio gds3 = new GruppoDiStudio("Reti di calcolatori",
				"Renato++", membri_gds, null, attivita_studio_gds, 2, forum,
				MyApplication.getAppContext().getResources()
						.getDrawable(R.drawable.reti_calcolatori_logo));

		user_gds_list.clear();
		user_gds_list.add(gds2);
		user_gds_list.add(gds3);
	}

}
