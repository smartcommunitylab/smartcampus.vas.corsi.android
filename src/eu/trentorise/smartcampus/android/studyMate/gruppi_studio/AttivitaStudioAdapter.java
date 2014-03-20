package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import eu.trentorise.smartcampus.android.studyMate.models.AttivitaDiStudio;
import eu.trentorise.smartcampus.studymate.R;

public class AttivitaStudioAdapter extends ArrayAdapter<AttivitaDiStudio> {

	private ArrayList<AttivitaDiStudio> entries;
	Context context;

	public AttivitaStudioAdapter(Context context, int textViewResourceId,
			ArrayList<AttivitaDiStudio> objects) {
		super(context, textViewResourceId, objects);
		this.entries = objects;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View impegno_view = convertView;
		AttivitaDiStudio currentImpegno = getItem(position);

		if (impegno_view == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			impegno_view = inflater.inflate(R.layout.attivita_studio_layout,
					null);
		}

		// recupera componenti da layout, assegnagli i testi del currentImpegno
		try {
			TextView data_view = (TextView) impegno_view
					.findViewById(R.id.data_attivitastudio_adpt);
			TextView oggetto_view = (TextView) impegno_view
					.findViewById(R.id.oggetto_attivitastudio);
			TextView aula_edificio_view = (TextView) impegno_view
					.findViewById(R.id.aula_attivitastudio_e_edificio);
			TextView orario_view = (TextView) impegno_view
					.findViewById(R.id.orario_attivitastudio);

			if (currentImpegno.getEventoId().getDate() != null) {
				SimpleDateFormat format = new SimpleDateFormat(
						"dd/MM/yyyy HH:mm");
				data_view.setText(format.format(currentImpegno.getEventoId()
						.getDate()));
			}

			oggetto_view.setText(currentImpegno.getTitle());
			aula_edificio_view.setText(currentImpegno.getRoom());
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			orario_view.setText(format.format(currentImpegno.getEventoId()
					.getDate()));

			AttivitaDiStudio prev = null;
			if (position > 0)
				prev = getItem(position - 1);

			if (prev == null
					|| (prev.getEventoId().getDate()
							.equals(currentImpegno.getEventoId().getDate()) == false)) {
				data_view.setVisibility(View.VISIBLE);
			} else {
				data_view.setVisibility(View.GONE);
			}
		} catch (Exception e) {
			// TODO: handle exception
			// merda
			Toast.makeText(MyApplication.getAppContext(), "merda",
					Toast.LENGTH_SHORT).show();
		}

		return impegno_view;
	}

	public ArrayList<AttivitaDiStudio> getEntries() {
		return entries;
	}

}
