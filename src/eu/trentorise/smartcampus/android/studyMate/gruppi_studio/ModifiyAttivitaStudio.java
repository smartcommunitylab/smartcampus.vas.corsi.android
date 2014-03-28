package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
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
import eu.trentorise.smartcampus.studymate.R;

public class ModifiyAttivitaStudio extends SherlockActivity {
	private AttivitaDiStudio attivitaDiStudio;
	private ProtocolCarrier mProtocolCarrier;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_attivita_studio_activity);

		// personalizzazioje actionabar
		ActionBar actionbar = getSupportActionBar();
		actionbar.setTitle("Modifica impegno");
		actionbar.setLogo(R.drawable.gruppistudio_icon_white);
		actionbar.setHomeButtonEnabled(true);
		actionbar.setDisplayHomeAsUpEnabled(true);

		// recupero gds da modificare per impostare i campi di testo ecc da
		// modificare con i valori preesistenti dell'attivitadistudio
		Bundle myextras = getIntent().getExtras();
		attivitaDiStudio = (AttivitaDiStudio) myextras
				.getSerializable("impegno_da_modificare");

		// customizzazione del layout di questa activity. Si vuole mostrare
		// nelle view disponibili i dati contenuti nella attivitadistudio che
		// l'utente vuole modificare

		// customizzazzione spinner: riempimento
		ArrayList<String> edifici_values = new ArrayList<String>();
		edifici_values.add("Povo, polo Ferraris");
		edifici_values.add("Povo, polo 0");
		edifici_values.add("Povo, nuovo polo");

		ArrayList<String> room_values = new ArrayList<String>();
		for (int i = 101; i < 115; i++) {
			room_values.add("a" + i);
		}
		for (int i = 201; i < 215; i++) {
			room_values.add("a" + i);
		}

		// customizzazzione spinner: setting up se non si torna ad avere una
		// event.location non serve a niente fare la ricerca and selection degli
		// spinner

		Spinner spinner_edificio = (Spinner) findViewById(R.id.spinner_edificio);
		ArrayAdapter<String> adapter_spinner_ed = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, edifici_values);
		spinner_edificio.setAdapter(adapter_spinner_ed);

		// String location_actual = attivitaDiStudio.getRoom();
		// int spinnerPositionedificio = adapter_spinner_ed
		// .getPosition(location_actual);
		// spinner_edificio.setSelection(spinnerPositionedificio);

		Spinner spinner_aula = (Spinner) findViewById(R.id.spinner_aula);
		ArrayAdapter<String> adapter_spinner_aule = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, room_values);
		spinner_aula.setAdapter(adapter_spinner_aule);

		// String room_actual = attivitaDiStudio.getRoom();
		// int spinnerPositionaula =
		// adapter_spinner_aule.getPosition(room_actual);
		// spinner_edificio.setSelection(spinnerPositionaula);

		// retrieving & initializing some button
		Button btn_data = (Button) findViewById(R.id.data_button_gds);
		Button btn_time = (Button) findViewById(R.id.ora_button_gds);

		Date data = attivitaDiStudio.getEventoId().getDate();
		SimpleDateFormat formatgiornoanno = new SimpleDateFormat("dd/MM/yyyy");
		btn_data.setText(formatgiornoanno.format(data));

		SimpleDateFormat formatorariogiornata = new SimpleDateFormat("HH:mm");
		btn_time.setText(formatorariogiornata.format(data));

		// retrieving textview_oggetto
		TextView oggetto_tv = (TextView) this
				.findViewById(R.id.editText_oggetto);
		oggetto_tv.setText(attivitaDiStudio.getTitle());

		TextView descrizione_tv = (TextView) this
				.findViewById(R.id.editText_descrizione_impegno);
		descrizione_tv.setText(attivitaDiStudio.getTopic());
	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getSherlock().getMenuInflater();
		inflater.inflate(R.menu.modifiy_attivita_studio, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home: {

			ModifiyAttivitaStudio.this.finish();
			return super.onOptionsItemSelected(item);

		}
		case R.id.action_done: {
			/*
			 * recupero elementi grafici
			 */
			Spinner spinner_edificio = (Spinner) findViewById(R.id.spinner_edificio);
			Spinner spinner_aula = (Spinner) findViewById(R.id.spinner_aula);
			Button btn_data = (Button) findViewById(R.id.data_button_gds);
			Button btn_time = (Button) findViewById(R.id.ora_button_gds);
			TextView oggetto_tv = (TextView) this
					.findViewById(R.id.editText_oggetto);
			TextView descrizione_tv = (TextView) this
					.findViewById(R.id.editText_descrizione_impegno);
			/*
			 * recupero informazioni dagli elementi grafici e aggiornamento di
			 * attivitaDiStudio
			 */
			String edificio = spinner_edificio.getSelectedItem().toString();
			String aula = spinner_aula.getSelectedItem().toString();

			String oggetto = oggetto_tv.getText().toString();
			String descrizione = descrizione_tv.getText().toString();

			String stringdata = btn_data.getText().toString();
			String ora = btn_time.getText().toString();
			stringdata = stringdata + " " + ora;
			Date data = null;
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			try {
				data = format.parse(stringdata);
				System.out.println(data);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			/*
			 * salvataggio modifche in attivitaDiStudio
			 */
			attivitaDiStudio.setTitle(oggetto);
			attivitaDiStudio.setTopic(descrizione);
			attivitaDiStudio.getEventoId().setDate(data);
			attivitaDiStudio.setRoom(edificio + " - " + aula);
			ModifyAS salvamodificheAS = new ModifyAS(ModifiyAttivitaStudio.this);
			salvamodificheAS.execute();
			return super.onOptionsItemSelected(item);
		}
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	private class ModifyAS extends AsyncTask<Void, Void, Boolean> {
		Context taskcontext;
		public ProgressDialog pd;
		Boolean allright;

		public ModifyAS(Context taskcontext) {
			super();
			this.taskcontext = taskcontext;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd = new ProgressDialog(taskcontext);
			pd = ProgressDialog.show(taskcontext,
					"Salvataggio modifiche in corso", "...");
		}

		private boolean modificaAS() {
			mProtocolCarrier = new ProtocolCarrier(ModifiyAttivitaStudio.this,
					SmartUniDataWS.TOKEN_NAME);

			MessageResponse response;

			MessageRequest request = new MessageRequest(
					SmartUniDataWS.URL_WS_SMARTUNI,
					SmartUniDataWS.POST_ATTIVITASTUDIO_MODIFY);
			request.setMethod(Method.POST);

			Boolean resultPost = false;

			try {

				String AttivitaJSON = Utils.convertToJSON(attivitaDiStudio);
				System.out
						.println("Il json dell'attività di studio che sto modificando è: "
								+ AttivitaJSON);
				request.setBody(AttivitaJSON);
				response = mProtocolCarrier
						.invokeSync(request, SmartUniDataWS.TOKEN_NAME,
								MyUniActivity.getAuthToken());

				if (response.getHttpStatus() == 200) {

					String body = response.getBody();
					resultPost = Utils.convertJSONToObject(body, Boolean.class);

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
			} catch (eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return resultPost;
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			allright = modificaAS();
			return null;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pd.dismiss();
			if (allright) {
				ModifiyAttivitaStudio.this.finish();
			} else {
				// merda
				Toast.makeText(ModifiyAttivitaStudio.this,
						"errore nella modifica dell'AS", Toast.LENGTH_SHORT)
						.show();
				ModifiyAttivitaStudio.this.finish();
			}

		}

	}

}
