package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import eu.trentorise.smartcampus.android.studyMate.R;
import eu.trentorise.smartcampus.android.studyMate.models.GruppoDiStudio;

public class Adapter_gds_to_list extends ArrayAdapter<GruppoDiStudio> {

	ArrayList<GruppoDiStudio> entries;
	Context context;

	public Adapter_gds_to_list(Context context, int textViewResourceId,
			ArrayList<GruppoDiStudio> objects) {
		super(context, textViewResourceId, objects);
		this.entries = objects;
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View row = convertView;
		GruppoDiStudio currentGDS = getItem(position);
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.gds_row, null);
		}

		TextView anno_gds = (TextView) row.findViewById(R.id.header_gds);
		TextView nome_gds = (TextView) row.findViewById(R.id.gds_name);
		ImageView logo_gds = (ImageView) row.findViewById(R.id.gds_logo);

		anno_gds.setText(currentGDS.getAnno() + "Anno");
		nome_gds.setText(currentGDS.getNome());
		logo_gds.setImageDrawable(currentGDS.getLogo());

		GruppoDiStudio prev = null;
		if (position > 0)
			prev = getItem(position - 1);

		if (prev == null || prev.getAnno() != currentGDS.getAnno()) {
			anno_gds.setVisibility(View.VISIBLE);
		} else {
			anno_gds.setVisibility(View.GONE);
		}

		return row;
	}

	public ArrayList<GruppoDiStudio> getEntries() {
		return entries;
	}

}
