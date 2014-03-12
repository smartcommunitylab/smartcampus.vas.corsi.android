package eu.trentorise.smartcampus.android.studyMate.myAgenda;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

import eu.trentorise.smartcampus.android.studyMate.models.AttivitaDidattica;
import eu.trentorise.smartcampus.android.studyMate.models.CorsoCarriera;
import eu.trentorise.smartcampus.android.studyMate.models.Evento;
import eu.trentorise.smartcampus.android.studyMate.myAgenda.MyAgendaActivity.MenuKind;
import eu.trentorise.smartcampus.android.studyMate.utilities.AdptDetailedEvent;
import eu.trentorise.smartcampus.android.studyMate.utilities.CoursesHandler;
import eu.trentorise.smartcampus.android.studyMate.utilities.EventAdapter;
import eu.trentorise.smartcampus.android.studyMate.utilities.EventItem;
import eu.trentorise.smartcampus.android.studyMate.utilities.EventsHandler;
import eu.trentorise.smartcampus.studymate.R;

public class OverviewFilterFragment extends SherlockFragment {

	//public CorsoCarriera courseSelected;
	public List<Evento> listaEventiFiltrati = null;
	public static String nomeCorsoOW;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_myagenda_overview,
				container, false);
		Bundle b = getArguments();
		nomeCorsoOW= b.getString("NomeCorso");

		return view;
	}

	public void onStart() {
		super.onStart();

		MyAgendaActivity parent = (MyAgendaActivity) getActivity();
		parent.setAgendaState(MenuKind.BASE_MENU);
		listaEventiFiltrati = new ArrayList<Evento>();

		//courseSelected = new CorsoCarriera();
		//courseSelected = (CorsoCarriera) CoursesHandler.corsoSelezionato;
		parent.setTitle(nomeCorsoOW);
		listaEventiFiltrati = filterEventsbyCourse();

		EventItem[] listEvItem = new EventItem[listaEventiFiltrati.size()];
		if (listaEventiFiltrati.size()==0){
			Toast.makeText(getSherlockActivity(), "Non sono disponibli eventi a breve per questo corso", Toast.LENGTH_SHORT).show();
		}
		else{
		int i = 0;
		for (Evento ev : listaEventiFiltrati) {
			AdptDetailedEvent e = new AdptDetailedEvent(ev.getDate(),
					ev.getTitle(), ev.getTeacher(), ev.getStart()
							.toString(), ev.getRoom());
			listEvItem[i++] = new EventItem(e);
		}

		EventAdapter adapter = new EventAdapter(getSherlockActivity(),
				listEvItem);
		ListView listView = (ListView) getSherlockActivity().findViewById(
				R.id.listViewEventi);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				MyAgendaActivity parent = (MyAgendaActivity) getActivity();
				parent.setAgendaState(MenuKind.DETAIL_OF_EVENT_FOR_COURSE);

				getSherlockActivity().supportInvalidateOptionsMenu();
				// Pass Data to other Fragment
				Evento evento = listaEventiFiltrati.get(arg2);

				FragmentTransaction ft = getSherlockActivity()
						.getSupportFragmentManager().beginTransaction();
				Fragment fragment = new DettailOfEventFragment4Courses(evento);
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
				ft.replace(R.id.tabCorsi, fragment);
				ft.addToBackStack(null);
				ft.commit();
			}

		});
		}
	}
@Override
public void onResume() {
	super.onResume();
}
	// filtro gli eventi in base al corso che ho selezionato
	private List<Evento> filterEventsbyCourse() {

		List<Evento> eventiFiltrati = new ArrayList<Evento>();

		for (Evento evento : EventsHandler.listaEventi) {
			if (String.valueOf(evento.getTitle()).compareTo(nomeCorsoOW)==0) {
				eventiFiltrati.add(evento);
			}
		}

		return eventiFiltrati;

	}

}
