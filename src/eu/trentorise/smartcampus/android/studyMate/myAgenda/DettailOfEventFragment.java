package eu.trentorise.smartcampus.android.studyMate.myAgenda;

import java.text.SimpleDateFormat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

import eu.trentorise.smartcampus.android.studyMate.models.Evento;
import eu.trentorise.smartcampus.android.studyMate.myAgenda.MyAgendaActivity.MenuKind;
import eu.trentorise.smartcampus.studymate.R;

public class DettailOfEventFragment extends SherlockFragment {

	public Evento eventSelected = null;
	public View view = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		view = inflater.inflate(R.layout.fragment_detail_of_event, container,
				false);

		eventSelected = (Evento) getArguments()
				.getSerializable("eventSelected");

		return view;
	}

	@Override
	public void onStart() {

		MyAgendaActivity parent = (MyAgendaActivity) getActivity();
		parent.setAgendaState(MenuKind.DETAIL_OF_EVENT);
		getSherlockActivity().supportInvalidateOptionsMenu();

		TextView tvTitleEvent = (TextView) view
				.findViewById(R.id.textTitleEvent);
		tvTitleEvent.setText(eventSelected.getTitolo());

		TextView tvDateEvent = (TextView) view.findViewById(R.id.textDataEvent);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		tvDateEvent.setText(dateFormat.format(eventSelected.getData()));

		TextView tvOraEvent = (TextView) view.findViewById(R.id.textOraEvent);
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
				.findViewById(R.id.textDescriptionEvent);
		tvDescEvent.setText(eventSelected.getDescrizione());

		TextView tvAulaEvent = (TextView) view.findViewById(R.id.textAulaEvent);
		tvAulaEvent.setText(eventSelected.getRoom());

		TextView tvLocationEvent = (TextView) view
				.findViewById(R.id.textLocationEvent);
		tvLocationEvent.setText(eventSelected.getEvent_location());

		super.onStart();

	}
}
