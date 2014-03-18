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
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.android.studyMate.models.CorsoCarriera;
import eu.trentorise.smartcampus.android.studyMate.models.Evento;
import eu.trentorise.smartcampus.android.studyMate.utilities.AdptDetailedEvent;
import eu.trentorise.smartcampus.android.studyMate.utilities.EventAdapter;
import eu.trentorise.smartcampus.android.studyMate.utilities.EventItem;
import eu.trentorise.smartcampus.android.studyMate.utilities.EventsHandler;
import eu.trentorise.smartcampus.studymate.R;

public class OverviewFilterFragment extends SherlockFragment {

	//public CorsoCarriera courseSelected;
	public List<Evento> listaEventiFiltrati = null;
	public static String nomeCorsoOW;
	private CorsoCarriera cc;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_myagenda_overview,
				container, false);
		Bundle b = getArguments();
		nomeCorsoOW= b.getString("NomeCorso");
		//cc = new CorsoCarriera();
		cc = (CorsoCarriera) b.getSerializable("corsoCarrieraSelezionato");
		return view;
	}

	public void onStart() {
		super.onStart();
		setHasOptionsMenu(true);
		
		listaEventiFiltrati = new ArrayList<Evento>();

		//courseSelected = new CorsoCarriera();
		//courseSelected = (CorsoCarriera) CoursesHandler.corsoSelezionato;
		getActivity().setTitle(nomeCorsoOW);
		listaEventiFiltrati = filterEventsbyCourse();

		EventItem[] listEvItem = new EventItem[listaEventiFiltrati.size()];
		if (listaEventiFiltrati.size()==0){
			Toast.makeText(getSherlockActivity(), "Non sono disponibli eventi a breve per questo corso", Toast.LENGTH_SHORT).show();
		}
		else{
		int i = 0;
		for (Evento ev : listaEventiFiltrati) {
			AdptDetailedEvent e = new AdptDetailedEvent(ev.getEventoId()
					.getDate(), ev.getTitle(), ev.getType(), ev
					.getEventoId().getStart().toString(), ev.getRoom());
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
				getSherlockActivity().supportInvalidateOptionsMenu();
				// Pass Data to other Fragment
				Evento evento = listaEventiFiltrati.get(arg2);
				Bundle arguments = new Bundle();
				arguments.putSerializable("eventSelected", evento);
				FragmentTransaction ft = getSherlockActivity()
						.getSupportFragmentManager().beginTransaction();
				Fragment fragment = new DettailOfEventFragment();
				fragment.setArguments(arguments);
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

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		menu.clear();
		inflater.inflate(R.menu.add_event, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {

		case R.id.menu_add_event_4_course:
			Intent intentEventAddEvent = new Intent(getActivity(),
					AddEvent4coursesActivity.class);
			intentEventAddEvent.putExtra("corsoCarrieraS", cc);
			intentEventAddEvent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intentEventAddEvent);
			return true;
		default:
			break;
		}
		return false;// super.onOptionsItemSelected(item);
	}
	
}
