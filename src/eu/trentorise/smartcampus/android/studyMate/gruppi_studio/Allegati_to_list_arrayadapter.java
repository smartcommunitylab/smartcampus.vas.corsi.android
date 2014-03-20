//package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;
//
//import java.util.ArrayList;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.TextView;
//import eu.trentorise.smartcampus.android.studyMate.models.Allegato;
//import eu.trentorise.smartcampus.studymate.R;
//
//public class Allegati_to_list_arrayadapter extends ArrayAdapter<Allegato> {
//
//	ArrayList<Allegato> entries;
//	Context context;
//
//	public Allegati_to_list_arrayadapter(Context context,
//			int textViewResourceId, ArrayList<Allegato> objects) {
//		super(context, textViewResourceId, objects);
//		this.entries = objects;
//		this.context = context;
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		View row = convertView;
//		Allegato currentAllegato = getItem(position);
//		if (row == null) {
//			LayoutInflater inflater = (LayoutInflater) context
//					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//			row = inflater.inflate(R.layout.allegato_row, null);// cambia layout
//																// per allegato
//		}
//
//		TextView nome_allegato = (TextView) row
//				.findViewById(R.id.nome_allegato);
//		/*
//		 * per ora sono tutti pdf
//		 */
//		nome_allegato.setText(currentAllegato.getNome_file());
//
//		return row;
//	}
// }
