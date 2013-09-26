package smartcampus.android.studyMate.gruppi_studio;

import java.util.ArrayList;

import smartcampus.android.template.standalone.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.model_classes.AttivitaStudio;

public class AttivitaStudioAdapter extends ArrayAdapter<AttivitaStudio> {

	private ArrayList<AttivitaStudio> entries;
	Context context;

	public AttivitaStudioAdapter(Context context, int textViewResourceId,
			ArrayList<AttivitaStudio> objects) {
		super(context, textViewResourceId, objects);
		this.entries = objects;
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View impegno_view = convertView;
		AttivitaStudio currentImpegno = getItem(position);

		if (impegno_view == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			impegno_view = inflater.inflate(R.layout.attivita_studio_layout,
					null);
		}

		// recupera componenti da layout, assegnagli i testi del currentImpegno

		TextView data_view = (TextView) impegno_view
				.findViewById(R.id.data_attivitastudio);
		TextView oggetto_view = (TextView) impegno_view
				.findViewById(R.id.oggetto_attivitastudio);
		TextView aula_view = (TextView) impegno_view
				.findViewById(R.id.aula_attivitastudio);
		TextView orario_view = (TextView) impegno_view
				.findViewById(R.id.orario_attivitastudio);

		data_view.setText(currentImpegno.getData().getDay() + "/"
				+ currentImpegno.getData().getMonth() + "/"
				+ currentImpegno.getData().getYear());
		oggetto_view.setText(currentImpegno.getOggetto());
		aula_view.setText("Aula " + currentImpegno.getRoom());
		orario_view.setText(currentImpegno.getStart().toString() + "-"
				+ currentImpegno.getStop().toString());

		AttivitaStudio prev = null;
		if (position > 0)
			prev = getItem(position - 1);

		if (prev == null
				|| (prev.getData().equals(currentImpegno.getData()) == false)) {
			data_view.setVisibility(View.VISIBLE);
		} else {
			data_view.setVisibility(View.GONE);
		}

		return impegno_view;
	}
}