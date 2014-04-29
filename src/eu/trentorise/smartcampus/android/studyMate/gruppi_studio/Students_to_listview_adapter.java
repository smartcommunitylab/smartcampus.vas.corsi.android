package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import eu.trentorise.smartcampus.android.studyMate.models.Studente;
import eu.trentorise.smartcampus.android.studyMate.utilities.SharedUtils;
import eu.trentorise.smartcampus.studymate.R;

public class Students_to_listview_adapter extends ArrayAdapter<Studente> {

	ArrayList<Studente> entries;
	Context context;

	public Students_to_listview_adapter(Context context,
			int textViewResourceId, ArrayList<Studente> objects) {
		super(context, textViewResourceId, objects);
		this.entries = objects;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View student_view = convertView;
		Studente currentStudent = getItem(position);
		if (student_view == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			student_view = inflater.inflate(R.layout.students_view, null);
		}

		@SuppressWarnings("unused")
		ImageView foto_studente = (ImageView) student_view
				.findViewById(R.id.iv_foto_studente);
		TextView nomeEcognome_studente = (TextView) student_view
				.findViewById(R.id.tv_nomecognome_studente);
		TextView info_studente = (TextView) student_view
				.findViewById(R.id.tv_info_studente);

		// foto_studente.setImageDrawable(currentStudent.getFoto_studente());
		nomeEcognome_studente.setText(currentStudent.getNome() + " "
				+ currentStudent.getCognome());
		
		info_studente.setText(currentStudent.getAnno_corso() + "° "+context.getResources().getString(R.string.string_year_at)
		 +" "+ entries.get(position).getCds());

		return student_view;
	}

}
