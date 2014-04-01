package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.android.studyMate.models.AttivitaDidattica;
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
	public Spinner spinner_scegli_materia;
	AutoCompleteTextView tv_nome_gds;
	// AutoCompleteTextView tv_invitati;
	private ProtocolCarrier mProtocolCarrier;
	public String body;
	public ArrayList<String> listaCorsiString = new ArrayList<String>();
	public ArrayList<AttivitaDidattica> listaCorsi = new ArrayList<AttivitaDidattica>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crea__gds_activity);

		ActionBar actionbar = getSupportActionBar();
		actionbar.setTitle("Nuovo Gruppo");
		actionbar.setLogo(R.drawable.gruppistudio_icon_white);
		actionbar.setHomeButtonEnabled(true);
		actionbar.setDisplayHomeAsUpEnabled(true);

		// tv_materia = (AutoCompleteTextView)
		// findViewById(R.id.scegli_materia);
		spinner_scegli_materia = (Spinner) findViewById(R.id.spinner_materia);
		tv_nome_gds = (AutoCompleteTextView) findViewById(R.id.scegli_nome_gruppo);
		// tv_invitati = (AutoCompleteTextView)
		// findViewById(R.id.invita_compagni_gds);

		// spinner_edificio.setAdapter(adapter_spinner_ed);
		LoadSpinnerMaterieAsTask task = new LoadSpinnerMaterieAsTask(
				Crea_GDS_activity.this);
		task.execute();
		// a questo punto le materie sono state caricate in listacorsi

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
			return super.onOptionsItemSelected(item);
		}
		case R.id.action_done: {
			// asynctask per aggiungere un gruppo di studio appena creato ai
			// gruppi di studio persoanli
			MyAsyncTask task = new MyAsyncTask(Crea_GDS_activity.this);
			task.execute();
			return super.onOptionsItemSelected(item);
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
			String materia = spinner_scegli_materia.getSelectedItem()
					.toString();
			int position = spinner_scegli_materia.getSelectedItemPosition();
			AttivitaDidattica attivitaDidattica = listaCorsi.get(position);
			String nome = tv_nome_gds.getText().toString();
			GruppoDiStudio justCreatedGds = new GruppoDiStudio();
			justCreatedGds.setNome(nome);
			justCreatedGds.setCorso(attivitaDidattica.getAdId());
			// salva il gruppo sul web

			if (addGroup(justCreatedGds)) {
				// se tutto va bene
				System.out
						.println("Creazione del gruppo eseguita con successo");

				return null;
			} else {
				System.out
						.println("Creazione del gruppo: PROBLEMA NON RISOLTO");

				return null;
			}

		}

	}

	private class LoadSpinnerMaterieAsTask extends AsyncTask<Void, Void, Void> {

		Context taskcontext;
		public ProgressDialog pd;
		private ArrayList<AttivitaDidattica> temp_listacorsiArrayList;

		public LoadSpinnerMaterieAsTask(Context taskcontext) {
			super();
			this.taskcontext = taskcontext;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(taskcontext);
			pd = ProgressDialog.show(taskcontext, "Caricamento materie utente",
					"");
		}

		protected ArrayList<AttivitaDidattica> webgetCorsiUtente() {
			mProtocolCarrier = new ProtocolCarrier(Crea_GDS_activity.this,
					SmartUniDataWS.TOKEN_NAME);
			// alcune preparazioni iniziali
			// recupero dello studente in sessione dalle sharedpreferences
			String jsonattivitadidattica = load("attivitaDidatticaStudente");
			AttivitaDidattica attivitadidatticastud = Utils
					.convertJSONToObject(jsonattivitadidattica,
							AttivitaDidattica.class);
			MessageResponse response;
			if (attivitadidatticastud == null) {
				return null;
			}
			MessageRequest request = new MessageRequest(
					SmartUniDataWS.URL_WS_SMARTUNI,
					SmartUniDataWS.GET_WS_ALLCOURSES_OF_DEGREE(""
							+ attivitadidatticastud.getCds_id()));
			request.setMethod(Method.GET);

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

			return (ArrayList<AttivitaDidattica>) Utils.convertJSONToObjects(
					body, AttivitaDidattica.class);

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			temp_listacorsiArrayList = webgetCorsiUtente();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			listaCorsiString.clear();
			listaCorsi.clear();
			listaCorsi = temp_listacorsiArrayList;
			if (temp_listacorsiArrayList != null) {
				for (AttivitaDidattica tempcorso : temp_listacorsiArrayList) {
					listaCorsiString.add(tempcorso.getDescription());
				}
				ArrayAdapter<String> adapter_spinner_materie = new ArrayAdapter<String>(
						Crea_GDS_activity.this,
						android.R.layout.simple_spinner_item, listaCorsiString);
				adapter_spinner_materie
						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinner_scegli_materia.setAdapter(adapter_spinner_materie);
				pd.dismiss();
			} else {
				pd.dismiss();
				Crea_GDS_activity.this.finish();
				Toast.makeText(getApplicationContext(),
						"Impossibile creare un nuovo gruppo!",
						Toast.LENGTH_LONG).show();
			}

		}

		public String load(String key) {
			SharedPreferences sharedPreferences = Crea_GDS_activity.this
					.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
			String retvaljson = sharedPreferences.getString(key, null);
			return retvaljson;
		}
	}
}
