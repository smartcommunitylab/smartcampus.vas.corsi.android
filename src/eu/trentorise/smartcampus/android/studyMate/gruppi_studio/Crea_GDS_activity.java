package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
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

public class Crea_GDS_activity extends SherlockActivity {
	AutoCompleteTextView tv_materia;
	AutoCompleteTextView tv_nome_gds;
	AutoCompleteTextView tv_invitati;
	private ProtocolCarrier mProtocolCarrier;
	public String body;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crea__gds_activity);

		ActionBar actionbar = getSupportActionBar();
		actionbar.setTitle("Nuovo Gruppo");
		actionbar.setLogo(R.drawable.gruppistudio_icon_white);
		actionbar.setHomeButtonEnabled(true);
		actionbar.setDisplayHomeAsUpEnabled(true);

		tv_materia = (AutoCompleteTextView) findViewById(R.id.scegli_materia);
		tv_nome_gds = (AutoCompleteTextView) findViewById(R.id.scegli_nome_gruppo);
		tv_invitati = (AutoCompleteTextView) findViewById(R.id.invita_compagni_gds);

	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		MenuInflater inflater = getSherlock().getMenuInflater();
		inflater.inflate(R.menu.crea__gds_activity, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home: {
			Crea_GDS_activity.this.finish();
		}
		case R.id.action_done: {
			// asynctask per aggiungere un gruppo di studio appena creato ai
			// gruppi di studio persoanli
			MyAsyncTask task = new MyAsyncTask(Crea_GDS_activity.this);
			task.execute();
		}
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private class MyAsyncTask extends AsyncTask<Void, Void, Void> {

		Context taskcontext;
		public ProgressDialog pd;

		public MyAsyncTask(Context taskcontext) {
			super();
			this.taskcontext = taskcontext;
		}

		private boolean addGroup(GruppoDiStudio gds_to_add) {
			mProtocolCarrier = new ProtocolCarrier(Crea_GDS_activity.this,
					SmartUniDataWS.TOKEN_NAME);

			MessageRequest request = new MessageRequest(
					SmartUniDataWS.URL_WS_SMARTUNI,
					SmartUniDataWS.POST_ADD_NEW_GDS);
			request.setMethod(Method.POST);

			MessageResponse response;
			try {

				String gds_to_addJSON = Utils.convertToJSON(gds_to_add);

				request.setBody(gds_to_addJSON);
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
					"Sto creando il gruppo di studio", "");
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pd.dismiss();
			Intent intent = new Intent(Crea_GDS_activity.this,
					Lista_GDS_activity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			@SuppressWarnings("unused")
			String materia = tv_materia.getText().toString();
			String nome = tv_nome_gds.getText().toString();
			GruppoDiStudio justCreatedGds = new GruppoDiStudio();
			justCreatedGds.setNome(nome);
			justCreatedGds.setCorso(/* getcorsofromnomemateria */1);
			// salva il gruppo sul web

			if (addGroup(justCreatedGds)) {
				// se tutto va bene
				System.out
						.println("Creazione del gruppo eseguita con successo");
				Crea_GDS_activity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Toast.makeText(MyApplication.getAppContext(), "ok",
								Toast.LENGTH_SHORT).show();
					}
				});
				return null;
			} else {
				System.out
						.println("Creazione del gruppo: PROBLEMA NON RISOLTO");
				Crea_GDS_activity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Toast.makeText(MyApplication.getAppContext(), "WTF",
								Toast.LENGTH_SHORT).show();
					}
				});
				return null;
			}

		}

	}

}
