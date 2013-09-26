package smartcampus.android.studyMate.myAgenda;

import java.util.List;

import smartcampus.android.studyMate.myAgenda.MyAgendaActivity.MenuKind;
import smartcampus.android.template.standalone.R;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

import eu.trentorise.smartcampus.studyMate.models.Evento;
import eu.trentorise.smartcampus.studyMate.utilities.EventsHandler;

public class OverviewFragment extends SherlockFragment {

	public static ProgressDialog pd;
	public ListView listViewEventi;
	public static List<Evento> listaEventi;
	public EventsHandler eventsHandler;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_myagenda_overview,
				container, false);
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	public void onStart() {
		super.onStart();
		MyAgendaActivity parent = (MyAgendaActivity) getActivity();
		parent.setAgendaState(MenuKind.BASE_MENU);
		getSherlockActivity().supportInvalidateOptionsMenu();

		new ProgressDialog(getActivity());
		OverviewFragment.pd = ProgressDialog.show(getActivity(),
				"Lista degli eventi personali", "Caricamento...");

		eventsHandler = new EventsHandler(
				getActivity().getApplicationContext(), getActivity());
		eventsHandler.execute();

	}
	
}
