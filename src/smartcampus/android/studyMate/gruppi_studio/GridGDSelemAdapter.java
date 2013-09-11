package smartcampus.android.studyMate.gruppi_studio;

import java.util.ArrayList;

import smartcampus.android.template.standalone.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.model_classes.GruppoDiStudio;

public class GridGDSelemAdapter extends ArrayAdapter<GruppoDiStudio> {
	private Context context;
	private ArrayList<GruppoDiStudio> entries;

	public GridGDSelemAdapter(Context context, int textViewResourceId,
			ArrayList<GruppoDiStudio> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
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
			littleSquare.findViewById(R.id.little_square_for_gds)
					.setLayoutParams(new GridView.LayoutParams(85, 85));
			((ImageView) littleSquare.findViewById(R.id.gds_square_logo))
					.setScaleType(ImageView.ScaleType.CENTER_CROP);
			littleSquare.findViewById(R.id.little_square_for_gds).setPadding(4,
					4, 4, 4);
		}

		ImageView logo_gds = (ImageView) littleSquare
				.findViewById(R.id.gds_square_logo);
		TextView nome_gds = (TextView) littleSquare
				.findViewById(R.id.gds_square_nome);

		logo_gds.setImageDrawable(currentGDS.getLogo());
		nome_gds.setText(currentGDS.getNome());
		return littleSquare;
	}

}