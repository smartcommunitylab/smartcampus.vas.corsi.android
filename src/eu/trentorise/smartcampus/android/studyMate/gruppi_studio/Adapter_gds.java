package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import eu.trentorise.smartcampus.android.studyMate.R;
import eu.trentorise.smartcampus.android.studyMate.models.GruppoDiStudio;

public class Adapter_gds extends ArrayAdapter<GruppoDiStudio> {

	ArrayList<GruppoDiStudio> entries;
	Context context;

	public Adapter_gds(Context context, int textViewResourceId,
			ArrayList<GruppoDiStudio> objects) {
		super(context, textViewResourceId, objects);
		this.entries = objects;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		GruppoDiStudio currentGDS = getItem(position);
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.gds_row, null);
		}

		TextView nome_gds = (TextView) row.findViewById(R.id.gds_name);
		// ImageView logo_gds = (ImageView) row.findViewById(R.id.gds_logo);
		TextView nome_corso = (TextView) row
				.findViewById(R.id.gds_name_of_course);
		nome_corso.setTextColor(Color.BLACK);
		TextView type_event = (TextView) row.findViewById(R.id.gds_name);
		type_event.setTextColor(Color.BLACK);
		nome_gds.setText(currentGDS.getNome());
		nome_gds.setTextColor(Color.BLACK);
		nome_corso.setText(currentGDS.getMateria());
		nome_corso.setTextColor(Color.BLACK);
		return row;
	}

	public ArrayList<GruppoDiStudio> getEntries() {
		return entries;
	}

}