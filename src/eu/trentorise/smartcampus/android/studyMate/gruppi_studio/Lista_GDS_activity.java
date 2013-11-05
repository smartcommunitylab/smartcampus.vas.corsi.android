package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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

import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.android.studyMate.models.GruppoDiStudio;
import eu.trentorise.smartcampus.android.studyMate.start.MyUniActivity;
import eu.trentorise.smartcampus.android.studyMate.utilities.SmartUniDataWS;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;
import eu.trentorise.smartcampus.studymate.R;

public class Lista_GDS_activity extends SherlockFragmentActivity {

	public static ArrayList<GruppoDiStudio> user_gds_list = new ArrayList<GruppoDiStudio>();
	private static boolean isShownAsList = true;
	private ProtocolCarrier mProtocolCarrier;
	public String body;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// impostazioni grafiche
		setContentView(R.layout.lista_gds_activity);
		ActionBar actionbar = getSupportActionBar();
		actionbar.setTitle("I miei gruppi");
		actionbar.setLogo(R.drawable.gruppistudio_icon_white);
		actionbar.setHomeButtonEnabled(true);
		actionbar.setDisplayHomeAsUpEnabled(true);

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

		// retrieve gruppi with followinf asynctask
		MyAsyncTask task = new MyAsyncTask(Lista_GDS_activity.this);
		task.execute();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.lista__gds_activity, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// per rimuovere il bottone cambia layout nel caso in cui non ci sia
		// alcun gruppo presente nella user_gds_list
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
			if (isShownAsList) {
				// azione se siamo in modalità lista
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
				// azione se siamo in modalità griglia
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

	private class MyAsyncTask extends
			AsyncTask<Void, Void, List<GruppoDiStudio>> {

		Context taskcontext;
		public ProgressDialog pd;
		List<GruppoDiStudio> responselist;

		public MyAsyncTask(Context taskcontext) {
			super();
			this.taskcontext = taskcontext;
		}

		private List<GruppoDiStudio> getMineGDS() {
			mProtocolCarrier = new ProtocolCarrier(Lista_GDS_activity.this,
					SmartUniDataWS.TOKEN_NAME);

			MessageRequest request = new MessageRequest(
					SmartUniDataWS.URL_WS_SMARTUNI,
					SmartUniDataWS.GET_WS_MY_GDS);
			request.setMethod(Method.GET);

			MessageResponse response;
			try {
				response = mProtocolCarrier
						.invokeSync(request, SmartUniDataWS.TOKEN_NAME,
								MyUniActivity.getAuthToken());

				if (response.getHttpStatus() == 200) {

					body = response.getBody();

				} else {
					return null;
				}
			} catch (ConnectionException e) {
				e.printStackTrace();
			} catch (ProtocolException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (AACException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return Utils.convertJSONToObjects(body, GruppoDiStudio.class);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(taskcontext);
			pd = ProgressDialog.show(taskcontext,
					"Caricamento gruppi di studio personali", "");
		}

		@Override
		protected void onPostExecute(List<GruppoDiStudio> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pd.dismiss();

			// se la user_gds_list è vuota proponiamo all'utente di fare qlcs..
			TextView tv = (TextView) findViewById(R.id.suggerimento_lista_vuota);
			if (user_gds_list.isEmpty()) {
				tv.setText("Non sei ancora iscritto ad alcun gruppo di studio!\nUtilizza il menù in alto a destra per iscriverti ad un gruppo di studio");
			} else {
				tv.setVisibility(View.GONE);
			}

			// ordinamento dei gruppi di studio
			// Collections.sort(user_gds_list);

			// inizializza la grafica in base allo stato booleano di
			// isShownAsList
			if (isShownAsList) {
				FragmentTransaction ft = Lista_GDS_activity.this
						.getSupportFragmentManager().beginTransaction();
				Fragment fragment = new ViewGruppiList_Fragment();
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
				ft.replace(R.id.list_container, fragment);
				ft.addToBackStack(null);
				ft.commit();
			} else {
				FragmentTransaction ft = Lista_GDS_activity.this
						.getSupportFragmentManager().beginTransaction();
				Fragment fragment = new ViewGruppiGrid_Fragment();
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
				ft.replace(R.id.list_container, fragment);
				ft.addToBackStack(null);
				ft.commit();
			}

		}

		@Override
		protected List<GruppoDiStudio> doInBackground(Void... params) {
			getMineGDS();
			// TODO Auto-generated method stub
			user_gds_list.clear();
			responselist = getMineGDS();
			for (GruppoDiStudio gds : responselist) {
				user_gds_list.add(gds);
			}

			return user_gds_list;
		}

	}

}