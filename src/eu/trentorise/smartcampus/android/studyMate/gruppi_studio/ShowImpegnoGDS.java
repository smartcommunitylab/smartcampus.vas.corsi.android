package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import it.smartcampuslab.studymate.R;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ViewConfiguration;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.android.studyMate.models.Evento;
import eu.trentorise.smartcampus.android.studyMate.models.GruppoDiStudio;
import eu.trentorise.smartcampus.android.studyMate.models.Studente;
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

public class ShowImpegnoGDS extends SherlockFragmentActivity {

	Evento contextualAttivitaStudio;
	GruppoDiStudio contextualGDS; 
	private ProtocolCarrier mProtocolCarrier;


	@Override
	protected void onResume() {
		super.onResume();
		setContentView(R.layout.show_impegno_gds);

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
		contextualAttivitaStudio = (Evento) myextras
				.getSerializable(Constants.CONTEXTUAL_ATT);
		contextualGDS = (GruppoDiStudio) myextras
				.getSerializable(Constants.CONTESTUAL_GDS);
		
		
		
		
		// personalizzazione actionabar
		ActionBar actionbar = getSupportActionBar();
		actionbar.setTitle(contextualAttivitaStudio.getTitle());
		actionbar.setLogo(R.drawable.gruppistudio_icon_white);
		actionbar.setHomeButtonEnabled(true);
		actionbar.setDisplayHomeAsUpEnabled(true);
		/*
		 * recupero contextualAttivitaStudio
		 */

		TextView tv_oggetto = (TextView) findViewById(R.id.oggetto_showgds);
		tv_oggetto.setText(contextualGDS.getMateria());//contextualAttivitaStudio.getTitle());
		TextView tv_data = (TextView) findViewById(R.id.text_data_impegno_showgds);
		Date data = contextualAttivitaStudio.getEventoId().getDate();
		java.sql.Time time = contextualAttivitaStudio.getEventoId().getStart();
		SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
		tv_data.setText(formatDate.format(data)+" "+time.toString());
		
		TextView polo_aula_tv = (TextView) findViewById(R.id.textLocation_impegno_showgds);
		polo_aula_tv.setText(contextualAttivitaStudio.getRoom());
		TextView tv_descrizione = (TextView) findViewById(R.id.textDescription_impegno_showgds);
		tv_descrizione.setText(contextualAttivitaStudio
				.getPersonalDescription());
	}
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.menu_showimpegno_gds, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			ShowImpegnoGDS.this.finish();
			return super.onOptionsItemSelected(item);
		case R.id.action_modifica_impegno:
			SharedPreferences sharedPreferences = ShowImpegnoGDS.this
					.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
			String studenteStr = sharedPreferences.getString(
					"studenteSessioneJSON", null);
			Studente studLogged = Utils.convertJSONToObject(studenteStr,
					Studente.class);
			if (contextualAttivitaStudio.getEventoId().getIdStudente() != studLogged
					.getId()) {
				Toast.makeText(
						ShowImpegnoGDS.this,
						getResources().getString(
								R.string.event_modify_not_allowed),
						Toast.LENGTH_SHORT).show();
			} else {
				Intent intent1 = new Intent(ShowImpegnoGDS.this,
						ModifiyAttivitaStudio.class);
				intent1.putExtra(Constants.IMPEGNO_MOD,
						contextualAttivitaStudio);
				intent1.putExtra(Constants.CONTESTUAL_GDS,
						contextualGDS);
				startActivity(intent1);
			}
			return super.onOptionsItemSelected(item);

		case R.id.action_elimina_impegno:

			SharedPreferences sharedPreferencesDel = ShowImpegnoGDS.this
					.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
			String studenteStrDel = sharedPreferencesDel.getString(
					"studenteSessioneJSON", null);
			Studente studLoggedDel = Utils.convertJSONToObject(studenteStrDel,
					Studente.class);
			if (contextualAttivitaStudio.getEventoId().getIdStudente() != studLoggedDel
					.getId()) {
				Toast.makeText(
						ShowImpegnoGDS.this,
						getResources().getString(
								R.string.event_delete_not_allowed),
						Toast.LENGTH_SHORT).show();
			} else {
				AsyncTabbandonaAttivitaStudio task = new AsyncTabbandonaAttivitaStudio(
						ShowImpegnoGDS.this, contextualAttivitaStudio);
				task.execute();
			}
			return super.onOptionsItemSelected(item);
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	private class AsyncTabbandonaAttivitaStudio extends
			AsyncTask<Evento, Void, Void> {

		@SuppressWarnings("unused")
		Context taskcontext;
		
		Evento toabandonAS;

		public AsyncTabbandonaAttivitaStudio(Context taskcontext,
				Evento toabandonAS) {
			super();
			this.taskcontext = taskcontext;
			this.toabandonAS = toabandonAS;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		private boolean abandonAS(Evento as_to_abandon) {
			mProtocolCarrier = new ProtocolCarrier(ShowImpegnoGDS.this,
					SmartUniDataWS.TOKEN_NAME);

			MessageRequest request = new MessageRequest(
					SmartUniDataWS.URL_WS_SMARTUNI,
					SmartUniDataWS.DELETE_ATTIVITASTUDIO);
			request.setMethod(Method.POST);

			MessageResponse response;
			try {
				String as_to_abandonJSON = Utils.convertToJSON(as_to_abandon);
				request.setBody(as_to_abandonJSON);
				response = mProtocolCarrier
						.invokeSync(request, SmartUniDataWS.TOKEN_NAME,
								MyUniActivity.getAuthToken());

				if (response.getHttpStatus() == 200) {
//					String body = response.getBody();
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
		protected Void doInBackground(Evento... params) {
			abandonAS(this.toabandonAS);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			onBackPressed();
			//ShowImpegnoGDS.this.finish();
			// Intent intent = new Intent(ShowImpegnoGDS.this,
			// Overview_GDS.class);
			// intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			// startActivity(intent);
		}

	}

}
