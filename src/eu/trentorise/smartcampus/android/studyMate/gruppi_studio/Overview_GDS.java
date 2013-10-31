package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewConfiguration;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.android.studyMate.R;
import eu.trentorise.smartcampus.android.studyMate.models.AttivitaDiStudio;
import eu.trentorise.smartcampus.android.studyMate.models.ChatObj;
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

public class Overview_GDS extends SherlockFragmentActivity {

	private GruppoDiStudio contextualGDS = null;
	private ArrayList<ChatObj> contextualForum = new ArrayList<ChatObj>();
	private ArrayList<AttivitaDiStudio> contextualListaImpegni = new ArrayList<AttivitaDiStudio>();
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
		/*
		 * Come da politica di utilizzo del contextualcollection ogni volta che
		 * recupero un oggetto mi aspetto di trovarlo in posizione 0, appena
		 * recupreato il tale oggetto mi preoccupo di pulire il
		 * contextualcollection (a overview ecc ci posso arrivare partendo da:
		 * lista dei gruppi di studio e trovando nel contextualcollection il
		 * gruppo che ho cliccato tra quelli della lista
		 */
		if (!MyApplication.getContextualCollection().isEmpty()) {
			Object obj = MyApplication.getContextualCollection().get(0);
			contextualGDS = (GruppoDiStudio) obj;
			MyApplication.getContextualCollection().clear();
		}

		final ActionBar ab = getSupportActionBar();
		// ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		ab.setTitle(contextualGDS.getNome());
		ab.setLogo(R.drawable.gruppistudio_icon_white);
		ab.setHomeButtonEnabled(true);
		ab.setDisplayHomeAsUpEnabled(true);

		// retrieving impegni from web
		AsyncTimpegniLoader task = new AsyncTimpegniLoader(Overview_GDS.this);
		task.execute();
		// contextualForum = contextualGDS.getForum();
		// da fare dentro la asynctask
		// contextualListaImpegni = contextualGDS.getAttivita_studio();

		FragmentTransaction ft = this.getSupportFragmentManager()
				.beginTransaction();
		Fragment fragment = new Impegni_Fragment();
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		ft.replace(R.id.impegni_fragment_container, fragment);
		ft.addToBackStack(null);
		ft.commit();

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
			return true;
		case R.id.action_modifica_gruppo:
			Toast.makeText(Overview_GDS.this, "modifica dettagli gruppo",
					Toast.LENGTH_SHORT).show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	public GruppoDiStudio getContextualGDS() {
		return contextualGDS;
	}

	public ArrayList<ChatObj> getContextualForum() {
		return contextualForum;
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

		public void attendi() {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(taskcontext);
			pd = ProgressDialog.show(taskcontext,
					"Caricamento dettagli del gruppo di studio", "");
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pd.dismiss();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			attendi();
			return null;
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

				String gds_to_abandonJSON = Utils.convertToJSON(gds_to_abandon);

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

		public void attendi() {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(taskcontext);
			pd = ProgressDialog.show(taskcontext,
					"Rimozione del gruppo di studio", "");
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
