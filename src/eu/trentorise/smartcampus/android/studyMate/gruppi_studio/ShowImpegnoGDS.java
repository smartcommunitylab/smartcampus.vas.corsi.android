package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

import eu.trentorise.smartcampus.studymate.R;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ViewConfiguration;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.android.studyMate.models.AttivitaDiStudio;
import eu.trentorise.smartcampus.android.studyMate.start.MyUniActivity;
import eu.trentorise.smartcampus.android.studyMate.utilities.SmartUniDataWS;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;

public class ShowImpegnoGDS extends SherlockFragmentActivity {

	AttivitaDiStudio contextualAttivitaStudio;
	private ProtocolCarrier mProtocolCarrier;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
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

		// personalizzazioje actionabar
		ActionBar actionbar = getSupportActionBar();
		actionbar.setTitle("Dettagli impegno");
		actionbar.setLogo(R.drawable.gruppistudio_icon_white);
		actionbar.setHomeButtonEnabled(true);
		actionbar.setDisplayHomeAsUpEnabled(true);
		/*
		 * recupero contextualAttivitaStudio
		 */
		Bundle myextras = getIntent().getExtras();
		contextualAttivitaStudio = (AttivitaDiStudio) myextras
				.getSerializable("contextualAttivitaStudio");

		TextView tv_oggetto = (TextView) findViewById(R.id.oggetto_showgds);
		tv_oggetto.setText(contextualAttivitaStudio.getTitle());
		TextView tv_data = (TextView) findViewById(R.id.text_data_impegno_showgds);
		Date data = contextualAttivitaStudio.getEventoId().getDate();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		tv_data.setText(format.format(data));
		TextView polo_aula_tv = (TextView) findViewById(R.id.textLocation_impegno_showgds);
		polo_aula_tv.setText(contextualAttivitaStudio.getRoom());
		TextView tv_descrizione = (TextView) findViewById(R.id.textDescription_impegno_showgds);
		tv_descrizione.setText(contextualAttivitaStudio.getTopic());

		ListView listview_allegati = (ListView) findViewById(R.id.lista_allegati_showgds);

		// ArrayList<Allegato> contextualAllegatis = null;
		/*
		 * contextualAttivitaStudio .getAllegati();
		 */
		// if (contextualAllegatis == null || contextualAllegatis.isEmpty()) {
		// Toast.makeText(MyApplication.getAppContext(),
		// "non ci sono allegati ne mostro uno per prova",
		// Toast.LENGTH_SHORT).show();
		// contextualAllegatis = new ArrayList<Allegato>();
		// Allegato fake = new Allegato(null, "nome allegato finto.pdf");
		// contextualAllegatis.add(fake);
		// contextualAllegatis.add(fake);
		// }
		// Allegati_to_list_arrayadapter adapter = new
		// Allegati_to_list_arrayadapter(
		// ShowImpegnoGDS.this, R.id.lista_allegati_showgds,
		// contextualAllegatis);
		// listview_allegati.setAdapter(adapter);
		/*
		 * manca altra roba
		 */
		// CheckBox c1 = (CheckBox)
		// findViewById(R.id.CheckBox1_prenotazione_aule);
		// CheckBox c2 = (CheckBox) findViewById(R.id.CheckBox2_mensa);
		// CheckBox c3 = (CheckBox) findViewById(R.id.CheckBox3_tutoring);
		// CheckBox c4 = (CheckBox) findViewById(R.id.CheckBox4_biblioteca);

		// if (contextualAttivitaStudio.isPrenotazione_aule()) {
		// c1.setChecked(true);
		// }
		// if (contextualAttivitaStudio.isMensa()) {
		// c2.setChecked(true);
		// }
		// if (contextualAttivitaStudio.isTutoring()) {
		// c3.setChecked(true);
		// }
		// if (contextualAttivitaStudio.isBiblioteca()) {
		// c4.setChecked(true);
		// }

		/*
		 * e così via
		 */
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
			return true;
		case R.id.action_modifica_impegno:
			Intent intent = new Intent(ShowImpegnoGDS.this,
					ModifiyAttivitaStudio.class);
			intent.putExtra("impegno_da_modificare", contextualAttivitaStudio);
			startActivity(intent);
			return super.onOptionsItemSelected(item);

		case R.id.action_elimina_impegno:
			AsyncTabbandonaAttivitaStudio task = new AsyncTabbandonaAttivitaStudio(
					ShowImpegnoGDS.this, contextualAttivitaStudio);
			task.execute();
			return super.onOptionsItemSelected(item);
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	private class AsyncTabbandonaAttivitaStudio extends
			AsyncTask<AttivitaDiStudio, Void, Void> {

		Context taskcontext;
		public ProgressDialog pd;
		AttivitaDiStudio toabandonAS;

		public AsyncTabbandonaAttivitaStudio(Context taskcontext,
				AttivitaDiStudio toabandonAS) {
			super();
			this.taskcontext = taskcontext;
			this.toabandonAS = toabandonAS;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(taskcontext);
			pd = ProgressDialog.show(taskcontext, "Stai cancellando:  "
					+ toabandonAS.getTopic(), "");
		}

		private boolean abandonAS(AttivitaDiStudio as_to_abandon) {
			mProtocolCarrier = new ProtocolCarrier(ShowImpegnoGDS.this,
					SmartUniDataWS.TOKEN_NAME);

			MessageRequest request = new MessageRequest(
					SmartUniDataWS.URL_WS_SMARTUNI,
					SmartUniDataWS.DELETE_ATTIVITASTUDIO);
			request.setMethod(Method.DELETE);

			MessageResponse response;
			try {
				String as_to_abandonJSON = Utils.convertToJSON(as_to_abandon);
				request.setBody(as_to_abandonJSON);
				response = mProtocolCarrier
						.invokeSync(request, SmartUniDataWS.TOKEN_NAME,
								MyUniActivity.getAuthToken());

				if (response.getHttpStatus() == 200) {
					String body = response.getBody();
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
		protected Void doInBackground(AttivitaDiStudio... params) {
			// TODO Auto-generated method stub
			abandonAS(this.toabandonAS);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pd.dismiss();
			ShowImpegnoGDS.this.finish();
		}

	}

}
