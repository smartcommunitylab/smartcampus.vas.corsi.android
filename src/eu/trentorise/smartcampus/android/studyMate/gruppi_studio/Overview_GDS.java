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
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.android.studyMate.models.Evento;
import eu.trentorise.smartcampus.android.studyMate.models.GruppoDiStudio;
import eu.trentorise.smartcampus.android.studyMate.start.MyUniActivity;
import eu.trentorise.smartcampus.android.studyMate.utilities.Constants;
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
	public ArrayList<Evento> contextualListaImpegni = new ArrayList<Evento>();
	private ProtocolCarrier mProtocolCarrier;
	public String body;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.overview_gds_waitingforforum_layout);
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception ex) {
		}

		Bundle myextras = getIntent().getExtras();
		contextualGDS = (GruppoDiStudio) myextras.get(Constants.CONTESTUAL_GDS);

		final ActionBar ab = getSupportActionBar();
		ab.setTitle(contextualGDS.getNome());
		ab.setLogo(R.drawable.gruppistudio_icon_white);
		ab.setHomeButtonEnabled(true);
		ab.setDisplayHomeAsUpEnabled(true);

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		AsyncTimpegniLoader task = new AsyncTimpegniLoader(Overview_GDS.this);
		task.execute();
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
			onBackPressed();
			return true;
		}
		case R.id.aggiungi_impegno: {
			Intent intent = new Intent(getApplicationContext(),
					Add_attivita_studio_activity.class);
			intent.putExtra(Constants.GDS, contextualGDS);
			startActivity(intent);
			return super.onOptionsItemSelected(item);
		}
		case R.id.action_abbandona_gruppo:
			AsyncTabbandonaGruppo task = new AsyncTabbandonaGruppo(
					Overview_GDS.this, contextualGDS);
			task.execute();
			return super.onOptionsItemSelected(item);
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	@Override
	public void onBackPressed() {
		Overview_GDS.this.finish();
	}

	public GruppoDiStudio getContextualGDS() {
		return contextualGDS;
	}

	public void setContextualGDS(GruppoDiStudio contextualGDS) {
		this.contextualGDS = contextualGDS;
	}

	public ArrayList<Evento> getContextualListaImpegni() {
		return contextualListaImpegni;
	}

	public void setContextualListaImpegni(
			ArrayList<Evento> contextualListaImpegni) {
		this.contextualListaImpegni = contextualListaImpegni;
	}

	private class AsyncTimpegniLoader extends AsyncTask<Void, Void, Void> {

		Context taskcontext;
		public ProgressDialog pd;

		public AsyncTimpegniLoader(Context taskcontext) {
			super();
			this.taskcontext = taskcontext;
		}

		private List<Evento> retrievedImpegni() {
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
				e.printStackTrace();
			}

			return Utils.convertJSONToObjects(body, Evento.class);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// ripulisco la lista impegni prima di caricare i nuovi impegni
			contextualListaImpegni.clear();
			pd = new ProgressDialog(taskcontext);
			pd = ProgressDialog.show(taskcontext,
					getResources().getString(R.string.dialog_loading), "");
		}

		@Override
		protected Void doInBackground(Void... params) {
			// faccio andare il metodo web per recuperare la lista impegni dal
			// web
			contextualListaImpegni = (ArrayList<Evento>) retrievedImpegni();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// ora che ho gli impegni pronti faccio partire la transaction che
			// porta alla schermata dove vengono mostrati gli impegni

			// faccio un controllo se non ci sono impegni
			// se la user_gds_list è vuota proponiamo all'utente di fare qlcs..
			TextView tv = (TextView) findViewById(R.id.suggerimento_listaimpegni_vuota);
			if (contextualListaImpegni.isEmpty()) {
				tv.setText(getResources().getString(R.string.att_message));
			} else {
				tv.setVisibility(View.GONE);
				FragmentTransaction ft = Overview_GDS.this
						.getSupportFragmentManager().beginTransaction();
				Fragment fragment = Impegni_Fragment.newInstance(
						contextualListaImpegni, contextualGDS);
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
				ft.replace(R.id.impegni_fragment_container, fragment);
				ft.addToBackStack(null);
				ft.commit();
			}

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
					SmartUniDataWS.POST_ABANDON_GDS);
			request.setMethod(Method.POST);

			MessageResponse response;
			try {
				request.setBody(gds_to_abandon.getId() + "");
				response = mProtocolCarrier
						.invokeSync(request, SmartUniDataWS.TOKEN_NAME,
								MyUniActivity.getAuthToken());

				if (response.getHttpStatus() == 200) {

					return true;

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
				e.printStackTrace();
			}

			return true;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd = new ProgressDialog(taskcontext);
			pd = ProgressDialog.show(taskcontext,
					getResources().getString(R.string.leave_gds) + " "
							+ toabandonGDS.getNome(), "");
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			pd.dismiss();
			Intent intent = new Intent(Overview_GDS.this,
					Lista_GDS_activity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}

		@Override
		protected Void doInBackground(GruppoDiStudio... params) {
			abandonGDS(toabandonGDS);
			return null;
		}

	}
}