package smartcampus.android.template.standalone;

import java.text.SimpleDateFormat;

import smartcampus.android.template.standalone.MyAgendaActivity.MenuKind;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

import eu.trentorise.smartcampus.smartuni.models.Evento;

public class DettailOfEventFragment extends SherlockFragment
{

	public Evento	eventSelected	= null;
	public View		view			= null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// Inflate the layout for this fragment
		view = inflater.inflate(R.layout.fragment_detail_of_event, container,
				false);

		eventSelected = (Evento) getArguments()
				.getSerializable("eventSelected");

		return view;
	}

	@Override
	public void onStart()
	{

		MyAgendaActivity parent = (MyAgendaActivity) getActivity();
		parent.setAgendaState(MenuKind.DETAIL_OF_EVENT);
		getActivity().invalidateOptionsMenu();

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
				.findViewById(R.id.textLocation);
		tvLocationEvent.setText(eventSelected.getEvent_location());

		super.onStart();

	}
}
