package smartcampus.android.studyMate.gruppi_studio;

import java.util.ArrayList;

import smartcampus.android.studyMate.start.MyUniActivity;
import smartcampus.android.template.standalone.R;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.example.model_classes.AttivitaStudio;
import com.example.model_classes.GruppoDiStudio;
import com.example.model_classes.Servizio;
import com.example.model_classes.Studente;

public class Lista_GDS_activity extends SherlockFragmentActivity {

	public ArrayList<GruppoDiStudio> user_gds_list = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_gds_activity);

		// ####################################
		// retrieve gruppi
		user_gds_list = /*
						 * getUtente().getFrequentedGDS ()
						 */new ArrayList<GruppoDiStudio>();

		// creazione gruppi fake per popolare grafica
		ArrayList<Studente> membri_gds = new ArrayList<Studente>();
		ArrayList<Servizio> servizi_monitorati_gds = new ArrayList<Servizio>();
		ArrayList<AttivitaStudio> attivita_studio_gds = new ArrayList<AttivitaStudio>();

		GruppoDiStudio gds1 = new GruppoDiStudio("Programmazione 1",
				"LoveRSEBA", membri_gds, servizi_monitorati_gds,
				attivita_studio_gds, 1, getResources().getDrawable(
						R.drawable.prouno_logo));
		GruppoDiStudio gds2 = new GruppoDiStudio("Matematica Discreta 1",
				"Ghiloni docet", membri_gds, servizi_monitorati_gds,
				attivita_studio_gds, 1, getResources().getDrawable(
						R.drawable.discreta_logo));
		GruppoDiStudio gds3 = new GruppoDiStudio("Reti di calcolatori",
				"Renato <3", membri_gds, servizi_monitorati_gds,
				attivita_studio_gds, 2, getResources().getDrawable(
						R.drawable.reti_calcolatori_logo));
		user_gds_list.add(gds1);
		user_gds_list.add(gds2);
		user_gds_list.add(gds3);
		user_gds_list.add(gds1);
		user_gds_list.add(gds2);
		user_gds_list.add(gds3);

		// ####################################

		// Lista_GDS_activity ha la grafica inizializzata a listviewfragment
		FragmentTransaction ft = this.getSupportFragmentManager()
				.beginTransaction();
		Fragment fragment = new ViewGruppiList_Fragment();
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		ft.replace(R.id.list_container, fragment);
		ft.addToBackStack(null);
		ft.commit();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.lista__gds_activity, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_ricerca_avanzata:
			// da implementare
			return true;

		case R.id.action_ricerca_gruppi_personali:
			// da implementare
			return true;
		case R.id.action_cambia_layout:

			Drawable actualicon = item.getIcon();

			if (actualicon.getConstantState().equals(
					getResources().getDrawable(
							R.drawable.collections_view_as_grid)
							.getConstantState())) {
				// azione
				item.setIcon(R.drawable.collections_view_as_list);

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

				this.getSupportFragmentManager().popBackStack();
				FragmentTransaction ft = this.getSupportFragmentManager()
						.beginTransaction();
				Fragment fragment = new ViewGruppiList_Fragment();
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
				ft.replace(R.id.list_container, fragment);
				ft.addToBackStack(null);
				ft.commit();

			}
		case R.id.action_iscriviti_nuovo_gruppo:
			// da implementare
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent goToMyUniActivity = new Intent(getApplicationContext(),
				MyUniActivity.class);
		goToMyUniActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(goToMyUniActivity);

	}

	public ArrayList<GruppoDiStudio> getUser_gds_list() {
		return user_gds_list;
	}

}
