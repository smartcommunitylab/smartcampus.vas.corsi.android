package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;

import eu.trentorise.smartcampus.android.studyMate.models.AttivitaDiStudio;
import eu.trentorise.smartcampus.studymate.R;
import eu.trentorise.smartcampus.studymate.R.id;
import eu.trentorise.smartcampus.studymate.R.layout;

public class ModifiyAttivitaStudio extends SherlockActivity {
	private AttivitaDiStudio attivitaDiStudio;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_attivita_studio_activity);

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

		// customizzazzione spinner: setting up

		Spinner spinner_edificio = (Spinner) findViewById(R.id.spinner_edificio);
		ArrayAdapter<String> adapter_spinner_ed = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, edifici_values);
		spinner_edificio.setAdapter(adapter_spinner_ed);

		String location_actual = attivitaDiStudio.getRoom();
		int spinnerPositionedificio = adapter_spinner_ed
				.getPosition(location_actual);
		spinner_edificio.setSelection(spinnerPositionedificio);

		Spinner spinner_aula = (Spinner) findViewById(R.id.spinner_aula);
		ArrayAdapter<String> adapter_spinner_aule = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, room_values);
		spinner_aula.setAdapter(adapter_spinner_aule);

		String room_actual = attivitaDiStudio.getRoom();
		int spinnerPositionaula = adapter_spinner_aule.getPosition(room_actual);
		spinner_edificio.setSelection(spinnerPositionaula);

		// retrieving & initializing some button
		Button btn_data = (Button) findViewById(R.id.data_button_gds);
		Button btn_time = (Button) findViewById(R.id.ora_button_gds);

		Date data = attivitaDiStudio.getDate();
		SimpleDateFormat formatgiornoanno = new SimpleDateFormat("dd/MM/yyyy");
		btn_data.setText(formatgiornoanno.format(data));

		SimpleDateFormat formatorariogiornata = new SimpleDateFormat("HH:mm");
		btn_time.setText(formatorariogiornata.format(data));

		// retrieving textview_oggetto
		TextView oggetto_tv = (TextView) this
				.findViewById(R.id.editText_oggetto);
	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
		/*
		 * da fare l'optionmenu
		 */
	}

}
