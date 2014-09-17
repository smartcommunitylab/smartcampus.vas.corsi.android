package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.studyMate.MyUniActivity;
import eu.trentorise.smartcampus.android.studyMate.R;
import eu.trentorise.smartcampus.android.studyMate.models.Evento;
import eu.trentorise.smartcampus.android.studyMate.models.GruppoDiStudio;
import eu.trentorise.smartcampus.android.studyMate.utilities.Constants;
import eu.trentorise.smartcampus.android.studyMate.utilities.SmartUniDataWS;
import eu.trentorise.smartcampus.android.studyMate.utilities.TabListener;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;

public class Overview_GDS extends SherlockFragmentActivity {

	public static GruppoDiStudio contextualGDS = null;
	public ArrayList<Evento> contextualListaImpegni = new ArrayList<Evento>();
	private ProtocolCarrier mProtocolCarrier;
	public String body;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		Bundle myextras = getIntent().getExtras();
		ActionBar ab = null;
		// se l'activity è chiamata dall'activity precedente

		contextualGDS = (GruppoDiStudio) myextras.get(Constants.CONTESTUAL_GDS);

		ab = getSupportActionBar();
		ab.setTitle(contextualGDS.getNome());
		ab.setLogo(R.drawable.gruppistudio_icon_white);
		ab.setHomeButtonEnabled(true);
		ab.setDisplayHomeAsUpEnabled(true);

		String tab1_txt = getResources().getString(R.string.attivita_string);
		String tab2_txt = getResources().getString(R.string.chat_label);

		ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		Tab tab1 = ab
				.newTab()
				.setText(tab1_txt)
				.setTabListener(
						new TabListener<Impegni_Fragment>(this, "tab1",
								Impegni_Fragment.class));
		ab.addTab(tab1);

		Tab tab2 = ab
				.newTab()
				.setText(tab2_txt)
				.setTabListener(
						new TabListener<Chat_Fragment>(this, "tab2",
								Chat_Fragment.class));
		ab.addTab(tab2);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
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
			return true;
		}
		case R.id.action_mostra_partecipanti: {
			Intent intent = new Intent(getApplicationContext(),
					GDS_members_activity.class);
			intent.putExtra(Constants.GDS_SUBS, contextualGDS);
			startActivity(intent);
			return true;
		}
		case R.id.action_abbandona_gruppo:
			AsyncTabbandonaGruppo task = new AsyncTabbandonaGruppo(
					Overview_GDS.this, contextualGDS);
			task.execute();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	public GruppoDiStudio getContextualGDS() {
		return contextualGDS;
	}

	@SuppressWarnings("static-access")
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
			onBackPressed();

		}

		@Override
		protected Void doInBackground(GruppoDiStudio... params) {
			abandonGDS(toabandonGDS);
			return null;
		}

	}
}
