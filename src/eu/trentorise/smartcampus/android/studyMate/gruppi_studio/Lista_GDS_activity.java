package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import eu.trentorise.smartcampus.android.studyMate.models.AttivitaDidattica;
import eu.trentorise.smartcampus.android.studyMate.models.CorsoCarriera;
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
import it.smartcampuslab.studymate.R;

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

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
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
		ArrayList<CorsoCarriera> corsicarrierastudente;

		public MyAsyncTask(Context taskcontext) {
			super();
			this.taskcontext = taskcontext;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(taskcontext);
			pd = ProgressDialog.show(taskcontext,
					"Caricamento gruppi di studio personali", "");
		}

		private List<GruppoDiStudio> getMineGDS() {
			ArrayList<GruppoDiStudio> retval = null;
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
					retval = (ArrayList<GruppoDiStudio>) Utils
							.convertJSONToObjects(body, GruppoDiStudio.class);
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
			return retval;
		}

		@Override
		protected List<GruppoDiStudio> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			user_gds_list.clear();
			responselist = getMineGDS();
			if (responselist != null) {
				for (GruppoDiStudio gds : responselist) {
					user_gds_list.add(gds);
				}
			}

			// proviamo a recueprare i dati relativi al corso di laurea
			// di
			// uno studente tramite il codice prensete all'interno di
			// uno
			// dei corsicarriera che ci siamo appena presi
			String jsoncorsicarrierastudente = load("corsiStudente");
			corsicarrierastudente = (ArrayList<CorsoCarriera>) Utils
					.convertJSONToObjects(jsoncorsicarrierastudente,
							CorsoCarriera.class);

			if (corsicarrierastudente != null
					&& !corsicarrierastudente.isEmpty()) {
				long cod = corsicarrierastudente.get(0).getId();
				mProtocolCarrier = new ProtocolCarrier(Lista_GDS_activity.this,
						SmartUniDataWS.TOKEN_NAME);
				MessageResponse response2;
				MessageRequest request2 = new MessageRequest(
						SmartUniDataWS.URL_WS_SMARTUNI,
						SmartUniDataWS.GET_WS_COURSES_DETAILS(cod));
				request2.setMethod(Method.GET);
				try {
					response2 = mProtocolCarrier.invokeSync(request2,
							SmartUniDataWS.TOKEN_NAME,
							MyUniActivity.getAuthToken());
					if (response2.getHttpStatus() == 200) {

						body = response2.getBody();
						String jsonattivitadidattica = body;
						save("attivitaDidatticaStudente", jsonattivitadidattica);
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
					e.printStackTrace();
				}
			}
			return user_gds_list;
		}

		@Override
		protected void onPostExecute(List<GruppoDiStudio> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pd.dismiss();

			// se la user_gds_list è vuota proponiamo all'utente di fare qlcs..
			TextView tv = (TextView) findViewById(R.id.suggerimento_lista_vuota);
			if (user_gds_list.isEmpty() || user_gds_list == null) {
				tv.setText("Non sei ancora iscritto ad alcun gruppo di studio!\nUtilizza il menù in alto a destra per iscriverti ad un gruppo di studio");
			} else {
				tv.setVisibility(View.GONE);
			}

			// per resettare l'optionmenu
			supportInvalidateOptionsMenu();
			// ordinamento dei gruppi di studio
			// Collections.sort(user_gds_list);

			// for each GDS set the correct Attribute (String Materia) to reduce
			// web
			// traffic
			for (GruppoDiStudio gds : user_gds_list) {
				GetRelatedCorsoAS task1 = new GetRelatedCorsoAS(
						Lista_GDS_activity.this, gds);
				task1.execute();

			}

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

		private void save(String key, String jsonTosaveinSharedP) {
			SharedPreferences sharedPreferences = Lista_GDS_activity.this
					.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putString(key, jsonTosaveinSharedP);
			editor.commit();
		}

		public String load(String key) {
			SharedPreferences sharedPreferences = Lista_GDS_activity.this
					.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
			String retvaljson = sharedPreferences.getString(key, null);
			return retvaljson;
		}

	}

	private class GetRelatedCorsoAS extends AsyncTask<Void, Void, Void> {

		Context taskcontext;
		GruppoDiStudio GDS;
		public ProgressDialog pd;

		public GetRelatedCorsoAS(Context taskcontext, GruppoDiStudio GDS) {
			super();
			this.taskcontext = taskcontext;
			this.GDS = GDS;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(taskcontext);
			pd = ProgressDialog.show(taskcontext,
					"Caricamento gruppi di studio personali", "");
		}

		AttivitaDidattica getRelatedCorso() {
			mProtocolCarrier = new ProtocolCarrier(taskcontext,
					SmartUniDataWS.TOKEN_NAME);

			MessageRequest request = new MessageRequest(
					SmartUniDataWS.URL_WS_SMARTUNI,
					SmartUniDataWS.GET_WS_COURSES_DETAILS(GDS.getCorso()));
			request.setMethod(Method.GET);

			MessageResponse response;
			String body = null;
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
			return Utils.convertJSONToObject(body, AttivitaDidattica.class);

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			AttivitaDidattica retval = getRelatedCorso();
			if (retval != null) {
				GDS.setMateria(retval.getDescription());
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pd.dismiss();

		}

	}

}