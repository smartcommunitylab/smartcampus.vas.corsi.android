package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.android.studyMate.MyUniActivity;
import eu.trentorise.smartcampus.android.studyMate.R;
import eu.trentorise.smartcampus.android.studyMate.models.CorsoCarriera;
import eu.trentorise.smartcampus.android.studyMate.models.GruppoDiStudio;
import eu.trentorise.smartcampus.android.studyMate.utilities.Constants;
import eu.trentorise.smartcampus.android.studyMate.utilities.SmartUniDataWS;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;

public class RicercaGruppiGenerale_activity extends SherlockFragmentActivity {

	Spinner spinner_materia;
	// Spinner spinner_nome_gruppo;
	public ArrayList<String> listaCorsiString = new ArrayList<String>();
	public List<CorsoCarriera> listaCorsi = new ArrayList<CorsoCarriera>();
	public ArrayList<GruppoDiStudio> listaGDSxMateria = new ArrayList<GruppoDiStudio>();
	private ProtocolCarrier mProtocolCarrier;
	public String body;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ricerca_gruppi_generale);

		// personalizzazione actionabar
		ActionBar actionbar = getSupportActionBar();
		actionbar.setLogo(R.drawable.gruppistudio_icon_white);
		actionbar.setHomeButtonEnabled(true);
		actionbar.setDisplayHomeAsUpEnabled(true);

		// recupero delle componenti grafiche dal layout
		spinner_materia = (Spinner) findViewById(R.id.spinner_materie);
		// spinner_nome_gruppo = (Spinner)
		// findViewById(R.id.spinner_nomi_gruppi);
		LoadSpinnerMaterieAsTask task = new LoadSpinnerMaterieAsTask(
				RicercaGruppiGenerale_activity.this);
		task.execute();
		spinner_materia.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parentView,
					View selectedItemView, int position, long id) {

				CorsoCarriera attivitaDidattica = listaCorsi.get(position);
				LoadGDSofCourse task = new LoadGDSofCourse(
						RicercaGruppiGenerale_activity.this);
				task.execute(attivitaDidattica);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.ricerca_generale_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home: {
			RicercaGruppiGenerale_activity.this.finish();
			return true;
		}
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private class LoadSpinnerMaterieAsTask extends
			AsyncTask<Void, Void, List<CorsoCarriera>> {

		Context taskcontext;
		public ProgressDialog pd;

		public LoadSpinnerMaterieAsTask(Context taskcontext) {
			super();
			this.taskcontext = taskcontext;
		}

		protected ArrayList<CorsoCarriera> webgetCorsiUtente() {
			mProtocolCarrier = new ProtocolCarrier(
					RicercaGruppiGenerale_activity.this,
					SmartUniDataWS.TOKEN_NAME);

			MessageResponse response;
			MessageRequest request = new MessageRequest(
					SmartUniDataWS.URL_WS_SMARTUNI,
					SmartUniDataWS.GET_WS_MY_COURSES_NOT_PASSED);
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
				e.printStackTrace();
			}

			return (ArrayList<CorsoCarriera>) Utils.convertJSONToObjects(body,
					CorsoCarriera.class);

		}

		@Override
		protected List<CorsoCarriera> doInBackground(Void... params) {
			return webgetCorsiUtente();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd = new ProgressDialog(taskcontext);
			pd = ProgressDialog.show(taskcontext,
					getResources().getString(R.string.dialog_list_courses),
					getResources().getString(R.string.dialog_loading));
		}

		@Override
		protected void onPostExecute(List<CorsoCarriera> result) {
			super.onPostExecute(result);
			listaCorsiString.clear();
			listaCorsi.clear();

			if (result != null && !result.isEmpty()) {
				for (CorsoCarriera tempcorso : result) {
					listaCorsiString.add(tempcorso.getName());
				}
				listaCorsi = result;
				ArrayAdapter<String> adapter_spinner_materie = new ArrayAdapter<String>(
						RicercaGruppiGenerale_activity.this,
						android.R.layout.simple_spinner_item, listaCorsiString);
				adapter_spinner_materie
						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinner_materia.setAdapter(adapter_spinner_materie);
				pd.dismiss();
			} else {
				pd.dismiss();
				RicercaGruppiGenerale_activity.this.finish();
				Toast.makeText(
						getApplicationContext(),
						getResources().getString(
								R.string.toast_gds_no_find_to_subscribe),
						Toast.LENGTH_LONG).show();
			}

		}

	}

	private class LoadGDSofCourse extends AsyncTask<CorsoCarriera, Void, Void> {
		Context taskcontext;
		public ProgressDialog pd;
		private CorsoCarriera materiaLookingForGDS;
		private ArrayList<GruppoDiStudio> temp_listaGDS;

		public LoadGDSofCourse(Context taskcontext) {
			super();
			this.taskcontext = taskcontext;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd = new ProgressDialog(taskcontext);
			pd = ProgressDialog.show(
					taskcontext,
					getResources().getString(
							R.string.dialog_gds_associated_loading),
					getResources().getString(R.string.dialog_loading));
		}

		private ArrayList<GruppoDiStudio> getGDSofThatAttivitaDidattica(
				CorsoCarriera ad) {
			MessageResponse response;
			if (ad == null) {
				return null;
			}
			MessageRequest request = new MessageRequest(
					SmartUniDataWS.URL_WS_SMARTUNI,
					SmartUniDataWS.GET_WS_FIND_GDS_OF_COURSE(Long.parseLong(ad
							.getCod())));
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
				e.printStackTrace();
			}
			return (ArrayList<GruppoDiStudio>) Utils.convertJSONToObjects(body,
					GruppoDiStudio.class);

		}

		@Override
		protected Void doInBackground(CorsoCarriera... params) {
			materiaLookingForGDS = params[0];
			temp_listaGDS = getGDSofThatAttivitaDidattica(materiaLookingForGDS);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			ListView results_list = (ListView) findViewById(R.id.searchresults_gds_list);
			// aggiorno la lista di GDS associati alla materia
			listaGDSxMateria.clear();
			listaGDSxMateria = temp_listaGDS;
			if (temp_listaGDS != null && temp_listaGDS.size() != 0) {
				// se ci sono GDS della tale materia...
				// fare qualcosa con gli adapter
				ArrayList<String> nomi_GDS = new ArrayList<String>();
				nomi_GDS.add(getResources().getString(R.string.all_gds));
				for (GruppoDiStudio g : listaGDSxMateria) {
					nomi_GDS.add(g.getNome());
				}

				Adapter_gds adapter = new Adapter_gds(getApplicationContext(),
						R.id.searchresults_gds_list, listaGDSxMateria);
				results_list.setAdapter(adapter);
				results_list.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						Adapter_gds adpt = (Adapter_gds) parent.getAdapter();
						ArrayList<GruppoDiStudio> entries = adpt.getEntries();
						GruppoDiStudio selected_gds = entries.get(position);
						Intent intent = new Intent(
								RicercaGruppiGenerale_activity.this,
								GDS_Subscription_activity.class);
						intent.putExtra(Constants.GDS_SUBS, selected_gds);

						startActivity(intent);
					}
				});
				pd.dismiss();
			} else {
				listaGDSxMateria.clear();
				Adapter_gds adapter = new Adapter_gds(getApplicationContext(),
						R.id.searchresults_gds_list, listaGDSxMateria);
				results_list.setAdapter(adapter);
				pd.dismiss();
				Toast.makeText(
						getApplicationContext(),
						getResources().getString(
								R.string.toast_gds_no_find_to_subscribe),
						Toast.LENGTH_LONG).show();
			}
		}
	}

	public String load(String key) {
		SharedPreferences sharedPreferences = RicercaGruppiGenerale_activity.this
				.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
		String retvaljson = sharedPreferences.getString(key, null);
		return retvaljson;
	}

}
