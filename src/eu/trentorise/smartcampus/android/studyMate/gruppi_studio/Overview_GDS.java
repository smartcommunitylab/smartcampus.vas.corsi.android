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
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.android.studyMate.models.AttivitaDiStudio;
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

public class Overview_GDS extends SherlockFragmentActivity {

	public GruppoDiStudio contextualGDS = null;
	//public ArrayList<ChatObj> contextualForum = new ArrayList<ChatObj>();
	public ArrayList<AttivitaDiStudio> contextualListaImpegni = new ArrayList<AttivitaDiStudio>();
	private ProtocolCarrier mProtocolCarrier;
	public String body;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.overview_gds_waitingforforum_layout);

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

		Bundle myextras = getIntent().getExtras();
		contextualGDS = (GruppoDiStudio) myextras.get("contextualGDS");

		final ActionBar ab = getSupportActionBar();
		// ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		ab.setTitle(contextualGDS.getNome());
		ab.setLogo(R.drawable.gruppistudio_icon_white);
		ab.setHomeButtonEnabled(true);
		ab.setDisplayHomeAsUpEnabled(true);

		// /** TabHost will have Tabs */
		// String tab1_txt = "Impegni";
		// String tab2_txt = "Forum";
		//
		// // tab1
		//
		// Tab tab1 = ab
		// .newTab()
		// .setText(tab1_txt)
		// .setTabListener(
		// new TabListener<Impegni_Fragment>(this, "tab1",
		// Impegni_Fragment.class));
		// ab.addTab(tab1);
		//
		// // tab2
		// Tab tab2 = ab
		// .newTab()
		// .setText(tab2_txt)
		// .setTabListener(
		// new TabListener<Forum_fragment>(this, "tab2",
		// Forum_fragment.class));
		// ab.addTab(tab2);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// retrieving impegni from web
		AsyncTimpegniLoader task = new AsyncTimpegniLoader(Overview_GDS.this);
		task.execute();
	}

	@Override
	public void onBackPressed() {
		Overview_GDS.this.finish();
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// Bundle args = arg2.getExtras();
		Toast.makeText(
				Overview_GDS.this,
				"resultcode= " + arg1 + " # requestCode= " + arg0
						+ "\n mi hanno ritornato" + " elem", Toast.LENGTH_SHORT)
				.show();
		super.onActivityResult(arg0, arg1, arg2);
	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.overview__gd, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home: {
			Overview_GDS.this.finish();
			return true;
		}
		case R.id.action_abbandona_gruppo:
			AsyncTabbandonaGruppo task = new AsyncTabbandonaGruppo(
					Overview_GDS.this, contextualGDS);
			task.execute();
			return super.onOptionsItemSelected(item);
		case R.id.action_modifica_gruppo:

			Intent intent = new Intent(Overview_GDS.this,
					ShowModifyGDSDetails_activity.class);
			intent.putExtra("contextualGDS", contextualGDS);
			startActivity(intent);
			return super.onOptionsItemSelected(item);
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	public GruppoDiStudio getContextualGDS() {
		return contextualGDS;
	}

//	public ArrayList<ChatObj> getContextualForum() {
//		return contextualForum;
//	}

	public void setContextualGDS(GruppoDiStudio contextualGDS) {
		this.contextualGDS = contextualGDS;
	}

	public ArrayList<AttivitaDiStudio> getContextualListaImpegni() {
		return contextualListaImpegni;
	}

	public void setContextualListaImpegni(
			ArrayList<AttivitaDiStudio> contextualListaImpegni) {
		this.contextualListaImpegni = contextualListaImpegni;
	}

	private class AsyncTimpegniLoader extends AsyncTask<Void, Void, Void> {

		Context taskcontext;
		public ProgressDialog pd;

		public AsyncTimpegniLoader(Context taskcontext) {
			super();
			this.taskcontext = taskcontext;
		}

		private List<AttivitaDiStudio> retrievedImpegni() {
			mProtocolCarrier = new ProtocolCarrier(Overview_GDS.this,
					SmartUniDataWS.TOKEN_NAME);

			MessageRequest request = new MessageRequest(
					SmartUniDataWS.URL_WS_SMARTUNI,
					SmartUniDataWS.GET_CONTEXTUAL_ATTIVITASTUDIO(contextualGDS
							.getId()));
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

			return Utils.convertJSONToObjects(body, AttivitaDiStudio.class);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			// ripulisco la lista impegni prima di caricare i nuovi impegni
			contextualListaImpegni.clear();
			pd = new ProgressDialog(taskcontext);
			pd = ProgressDialog.show(taskcontext,
					"Caricamento dettagli del gruppo di studio", "");
		}

		@Override
		protected Void doInBackground(Void... params) {
			// faccio andare il metodo web per recuperare la lista impegni dal
			// web
			contextualListaImpegni = (ArrayList<AttivitaDiStudio>) retrievedImpegni();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			// ora che ho gli impegni pronti faccio partire la transaction che
			// porta alla schermata dove vengono mostrati gli impegni

			// faccio un controllo se non ci sono impegni
			// se la user_gds_list è vuota proponiamo all'utente di fare qlcs..
			TextView tv = (TextView) findViewById(R.id.suggerimento_listaimpegni_vuota);
			if (contextualListaImpegni.isEmpty()) {
				tv.setText("Non sono stati fissati impegni!\nUtilizza il menù in alto a destra per fissare un nuovo impegno");
			} else {
				tv.setVisibility(View.GONE);
			}
			FragmentTransaction ft = Overview_GDS.this
					.getSupportFragmentManager().beginTransaction();
			// Fragment fragment = new Impegni_Fragment();
			Fragment fragment = Impegni_Fragment.newInstance(
					contextualListaImpegni, contextualGDS);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			ft.replace(R.id.impegni_fragment_container, fragment);
			ft.addToBackStack(null);
			ft.commit();
			pd.dismiss();
		}

	}

	private class AsyncTabbandonaGruppo extends
			AsyncTask<GruppoDiStudio, Void, Void> {

		Context taskcontext;
		public ProgressDialog pd;
		GruppoDiStudio toabandonGDS;

		public AsyncTabbandonaGruppo(Context taskcontext,
				GruppoDiStudio toabandonGDSgds) {
			super();
			this.taskcontext = taskcontext;
			this.toabandonGDS = toabandonGDSgds;
		}

		private boolean abandonGDS(GruppoDiStudio gds_to_abandon) {
			mProtocolCarrier = new ProtocolCarrier(Overview_GDS.this,
					SmartUniDataWS.TOKEN_NAME);

			MessageRequest request = new MessageRequest(
					SmartUniDataWS.URL_WS_SMARTUNI,
					SmartUniDataWS.DELETE_ABANDON_GDS);
			request.setMethod(Method.DELETE);

			MessageResponse response;
			try {

				String gds_to_abandonJSON = Utils
						.convertToJSON(gds_to_abandon);

				request.setBody(gds_to_abandonJSON);
				/*
				 * pare ci sia un bug qui, forse perchè la invokesync va fatta
				 * diversamente visto che stiamousando una delete
				 */
				response = mProtocolCarrier
						.invokeSync(request, SmartUniDataWS.TOKEN_NAME,
								MyUniActivity.getAuthToken());

				if (response.getHttpStatus() == 200) {

					return true;
					// body = response.getBody();

				} else {
					return false;
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

			return true;
		}


		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(taskcontext);
			pd = ProgressDialog.show(taskcontext,
					"Stai lasciando "+ toabandonGDS.getNome(), "");
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pd.dismiss();
			Overview_GDS.this.finish();
		}

		@Override
		protected Void doInBackground(GruppoDiStudio... params) {
			// TODO Auto-generated method stub
			abandonGDS(toabandonGDS);
			return null;
		}

	}
}