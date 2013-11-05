package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import eu.trentorise.smartcampus.android.studyMate.models.GruppoDiStudio;
import eu.trentorise.smartcampus.studymate.R;

public class Adapter_gds_to_grid extends ArrayAdapter<GruppoDiStudio> {
	private Context context;
	@SuppressWarnings("unused")
	private ArrayList<GruppoDiStudio> entries;

	public Adapter_gds_to_grid(Context context, int textViewResourceId,
			ArrayList<GruppoDiStudio> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		this.entries = objects;
	}

	// public int getCount() {
	// return entries.size();
	// }
	//
	// public long getItemId(int position) {
	// return 0;
	// }

	// create a new ImageView for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {

		View littleSquare = convertView;
		GruppoDiStudio currentGDS = (GruppoDiStudio) getItem(position);
		if (littleSquare == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			littleSquare = inflater.inflate(R.layout.gds_square, null);
			// littleSquare.findViewById(R.id.little_square_for_gds)
			// .setLayoutParams(new GridView.LayoutParams(85, 85));
			/*
			 * ((ImageView) littleSquare.findViewById(R.id.gds_square_logo))
			 * .setScaleType(ImageView.ScaleType.CENTER_CROP);
			 * littleSquare.findViewById
			 * (R.id.little_square_for_gds).setPadding(8, 8, 8, 8);
			 */

		}

		LinearLayout container = (LinearLayout) littleSquare
				.findViewById(R.id.little_square_for_gds);
		if (position % 2 == 0) {
			container.setBackgroundResource(R.color.WhiteSmoke);
		} else {
			container.setBackgroundResource(R.color.abs__background_holo_light);
		}

		ImageView logo_gds = (ImageView) littleSquare
				.findViewById(R.id.gds_square_logo);
		TextView nome_gds = (TextView) littleSquare
				.findViewById(R.id.gds_square_nome);

		// da conformare al backend
		// logo_gds.setImageDrawable(currentGDS.getLogo());
		nome_gds.setText(currentGDS.getNome());
		return littleSquare;
	}

}