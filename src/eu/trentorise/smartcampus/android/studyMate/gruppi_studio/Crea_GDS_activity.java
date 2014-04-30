package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import eu.trentorise.smartcampus.studymate.R;

public class Crea_GDS_activity extends SherlockActivity {
	public Spinner spinner_scegli_materia;
	AutoCompleteTextView tv_nome_gds;
	AutoCompleteTextView tv_invitati;
	private ProtocolCarrier mProtocolCarrier;
	public String body;
	public ArrayList<String> listaCorsiString = new ArrayList<String>();
	public List<CorsoCarriera> listaCorsi = new ArrayList<CorsoCarriera>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crea__gds_activity);

		ActionBar actionbar = getSupportActionBar();
		actionbar.setTitle(R.string.new_group_stud);
		actionbar.setLogo(R.drawable.gruppistudio_icon_white);
		actionbar.setHomeButtonEnabled(true);
		actionbar.setDisplayHomeAsUpEnabled(true);

		spinner_scegli_materia = (Spinner) findViewById(R.id.spinner_materia);
		tv_nome_gds = (AutoCompleteTextView) findViewById(R.id.scegli_nome_gruppo);

		LoadSpinnerMaterieAsTask task = new LoadSpinnerMaterieAsTask(
				Crea_GDS_activity.this);
		task.execute();

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
			PostNewGDS task = new PostNewGDS(Crea_GDS_activity.this);
			task.execute();
			return super.onOptionsItemSelected(item);
		}
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private class PostNewGDS extends AsyncTask<Void, Void, Void> {

		Context taskcontext;
		public ProgressDialog pd;

		public PostNewGDS(Context taskcontext) {
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
				e.printStackTrace();
			}

			return true;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd = new ProgressDialog(taskcontext);
			pd = ProgressDialog.show(taskcontext,
					getResources().getString(R.string.dialog_saving_feedback),
					getResources().getString(R.string.dialog_loading));
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			pd.dismiss();
			Intent intent = new Intent(Crea_GDS_activity.this,
					Lista_GDS_activity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}

		@Override
		protected Void doInBackground(Void... params) {
			String materia = spinner_scegli_materia.getSelectedItem()
					.toString();
			int position = spinner_scegli_materia.getSelectedItemPosition();
			CorsoCarriera cc = listaCorsi.get(position);
			String nome = tv_nome_gds.getText().toString();
			GruppoDiStudio justCreatedGds = new GruppoDiStudio();
			justCreatedGds.setNome(nome);
			justCreatedGds.setMateria(materia);
			justCreatedGds.setCorso(Long.parseLong(cc.getCod()));
			addGroup(justCreatedGds);
			return null;

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

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd = new ProgressDialog(taskcontext);
			pd = ProgressDialog.show(taskcontext,
					getResources().getString(R.string.dialog_loading), "");
		}

		protected ArrayList<CorsoCarriera> webgetCorsiUtente() {
			mProtocolCarrier = new ProtocolCarrier(Crea_GDS_activity.this,
					SmartUniDataWS.TOKEN_NAME);
			// alcune preparazioni iniziali
			// recupero dello studente in sessione dalle sharedpreferences

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
		protected void onPostExecute(List<CorsoCarriera> result) {
			super.onPostExecute(result);
			if (result != null) {
				listaCorsi = result;
				for (CorsoCarriera tempcorso : result) {
					listaCorsiString.add(tempcorso.getName());
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
						getResources().getString(R.string.dialog_error),
						Toast.LENGTH_LONG).show();
			}

		}
	}
}
