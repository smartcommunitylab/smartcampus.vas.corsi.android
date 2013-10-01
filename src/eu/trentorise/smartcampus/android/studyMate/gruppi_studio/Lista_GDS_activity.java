package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import java.util.ArrayList;

import smartcampus.android.template.standalone.R;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;

import eu.trentorise.smartcampus.android.studyMate.models.GruppoDiStudio;
import eu.trentorise.smartcampus.android.studyMate.start.MyUniActivity;

public class Lista_GDS_activity extends SherlockFragmentActivity {

	public static ArrayList<GruppoDiStudio> user_gds_list = new ArrayList<GruppoDiStudio>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_gds_activity);
		ActionBar actionbar = getSupportActionBar();
		actionbar.setTitle("I miei gruppi");
		actionbar.setLogo(R.drawable.gruppistudio_icon_white);
		actionbar.setHomeButtonEnabled(true);
		actionbar.setDisplayHomeAsUpEnabled(true);

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

		// Lista_GDS_activity ha la grafica inizializzata a listviewfragment
		FragmentTransaction ft = this.getSupportFragmentManager()
				.beginTransaction();
		Fragment fragment = new ViewGruppiList_Fragment();
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		ft.replace(R.id.list_container, fragment);
		ft.addToBackStack(null);
		ft.commit();

		/*
		 * sto commentone sarebbe il tour guidato che ho tolto per ora if
		 * (user_gds_list.isEmpty()) { AlertDialog.Builder builder = new
		 * AlertDialog.Builder( Lista_GDS_activity.this);
		 * builder.setTitle("Avvio guidato"); builder.setMessage(
		 * "Scopri le funzionalitï¿½ dei Gruppi di Studio! Vuoi iscriverti ad un gruppo di studio?"
		 * ); builder.setPositiveButton("Si", new
		 * DialogInterface.OnClickListener() {
		 * 
		 * @Override public void onClick(DialogInterface dialog, int which) { //
		 * TODO Auto-generated method stub dialog.dismiss(); final Dialog
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
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub shadowdialog.dismiss(); } });
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
