package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import eu.trentorise.smartcampus.android.studyMate.models.GruppoDiStudio;
import eu.trentorise.smartcampus.studymate.R;

public class Adapter_gds_to_list extends ArrayAdapter<GruppoDiStudio> {

	ArrayList<GruppoDiStudio> entries;
	Context context;

	public Adapter_gds_to_list(Context context, int textViewResourceId,
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
		ImageView logo_gds = (ImageView) row.findViewById(R.id.gds_logo);
		 	//ImageView logo_gds = (ImageView) row.findViewById(R.id.gds_logo);
		  		TextView nome_corso = (TextView) row
		  				.findViewById(R.id.gds_name_of_course);
		  
		 		// con questo task si caricano i dati dentro alla variabile di classe
		 		// relatedAttivitaDidattica
		 		// così si riesce a stampare la materia relativa al tale GDS
		 
		  		nome_gds.setText(currentGDS.getNome());
		  		// da conformare al backend
		  		// logo_gds.setImageDrawable(currentGDS.getLogo());
		 		// nome_corso.setText(getcorsobyid(currentGDS.getCorso().getname());
		 		nome_corso.setText(currentGDS.getMateria());

		return row;
	}

	public ArrayList<GruppoDiStudio> getEntries() {
		return entries;
	}

}
