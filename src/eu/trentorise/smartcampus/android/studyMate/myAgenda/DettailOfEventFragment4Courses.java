package eu.trentorise.smartcampus.android.studyMate.myAgenda;

import java.text.SimpleDateFormat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

import eu.trentorise.smartcampus.android.studyMate.R;
import eu.trentorise.smartcampus.android.studyMate.models.Evento;
import eu.trentorise.smartcampus.android.studyMate.myAgenda.MyAgendaActivity.MenuKind;

public class DettailOfEventFragment4Courses extends SherlockFragment

{
	public Evento eventSelected = null;
	public View view = null;

	public DettailOfEventFragment4Courses(Evento evento) {
		this.eventSelected = evento;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		view = inflater.inflate(R.layout.fragment_detail_of_event, container,
				false);

		return view;
	}

	@Override
	public void onStart() {
		MyAgendaActivity parent = (MyAgendaActivity) getActivity();
		parent.setAgendaState(MenuKind.DETAIL_OF_EVENT_FOR_COURSE);
		getSherlockActivity().supportInvalidateOptionsMenu();

		TextView tvTitleEvent = (TextView) view
				.findViewById(R.id.oggetto_impegno_gds);
		tvTitleEvent.setText(eventSelected.getTitolo());

		TextView tvDateEvent = (TextView) view
				.findViewById(R.id.text_data_impegno_gds);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		tvDateEvent.setText(dateFormat.format(eventSelected.getData()));

		TextView tvOraEvent = (TextView) view
				.findViewById(R.id.textOra_impegno_gds);
		tvOraEvent
				.setText(eventSelected
						.getStart()
						.toString()
						.subSequence(
								0,
								eventSelected.getStart().toString().length() - 3)
						+ " - "
						+ eventSelected
								.getStop()
								.toString()
								.subSequence(
										0,
										eventSelected.getStart().toString()
												.length() - 3));

		TextView tvDescEvent = (TextView) view
				.findViewById(R.id.textDescription_impegno_gds);
		tvDescEvent.setText(eventSelected.getDescrizione());

		TextView tvAulaEvent = (TextView) view
				.findViewById(R.id.textAula_impegno_gds);
		tvAulaEvent.setText(eventSelected.getRoom());

		TextView tvLocationEvent = (TextView) view
				.findViewById(R.id.textLocation_impegno_gds);
		tvLocationEvent.setText(eventSelected.getEvent_location());
		super.onStart();
	}
}
