package eu.trentorise.smartcampus.android.studyMate.myAgenda;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.android.studyMate.models.Evento;
import eu.trentorise.smartcampus.android.studyMate.utilities.AdptDetailedEvent;
import eu.trentorise.smartcampus.android.studyMate.utilities.Constants;
import eu.trentorise.smartcampus.android.studyMate.utilities.EventAdapter;
import eu.trentorise.smartcampus.android.studyMate.utilities.EventItem;
import eu.trentorise.smartcampus.android.studyMate.utilities.EventsHandler;
import eu.trentorise.smartcampus.android.studyMate.utilities.FirstPageFragmentListener;
import eu.trentorise.smartcampus.android.studyMate.utilities.OnSwipeTouchListener;
import eu.trentorise.smartcampus.studymate.R;

public class OverviewFragment extends SherlockFragment {

	public static ProgressDialog pd;
	public ListView listViewEventi;
	public static List<Evento> listaEventi;
	public EventsHandler eventsHandler;
	public static Evento eventoSelezionato;
	public static OnSwipeTouchListener listenerSwipe;
	static FirstPageFragmentListener firstPageListener;
	
	
	public OverviewFragment(FirstPageFragmentListener firstPListener) {
		firstPageListener = firstPListener;
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_myagenda_overview,
				container, false);
		return view;
	}

	// @Override
	// public void onCreate(Bundle savedInstanceState) {
	// super.onCreate(savedInstanceState);
	// }

	@Override
	public void onStart() {
		super.onStart();
		setHasOptionsMenu(true);
		getSherlockActivity().supportInvalidateOptionsMenu();

		listaEventi = MyAgendaActivity.getListEvents();

		EventItem[] listEvItem = new EventItem[listaEventi.size()];
		if (listaEventi.size() == 0) {
			Toast.makeText(
					getSherlockActivity(),
					getActivity().getResources().getString(
							R.string.dialog_not_events), Toast.LENGTH_SHORT)
					.show();
		} else {
			int i = 0;

			for (Evento ev : listaEventi) {
				AdptDetailedEvent e = new AdptDetailedEvent(ev.getEventoId()
						.getDate(), ev.getTitle(), ev.getType(), ev
						.getEventoId().getStart().toString(), ev.getRoom());
				listEvItem[i++] = new EventItem(e, getActivity());

			}

			EventAdapter adapter = new EventAdapter(getSherlockActivity(),
					listEvItem);
			ListView listView = (ListView) getSherlockActivity().findViewById(
					R.id.listViewEventi);
			if (listView == null) {
				return;
			}
			listView.setAdapter(adapter);

			listView.setOnItemClickListener(new ListView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					getSherlockActivity().supportInvalidateOptionsMenu();

					
					eventoSelezionato = listaEventi.get(arg2);;
					
					Evento evento = listaEventi.get(arg2);

					// Pass Data to other Fragment
					Bundle arguments = new Bundle();
					arguments.putSerializable(Constants.SELECTED_EVENT, evento);
//					FragmentTransaction ft = getSherlockActivity()
//							.getSupportFragmentManager().beginTransaction();
//					Fragment fragment = new DettailOfEventFragment();
//					fragment.setArguments(arguments);
//					ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//					ft.replace(getId(), fragment, getTag());
//					ft.addToBackStack(getTag());
//					ft.commit();
					
					firstPageListener.onSwitchToNextFragment();
				}
			});

		}

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		inflater.inflate(R.menu.agenda, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.add_event:

			FragmentTransaction ft = getSherlockActivity()
					.getSupportFragmentManager().beginTransaction();
			Fragment fragment = new AddEventFragment();
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			ft.replace(getId(), fragment, getTag());
			ft.addToBackStack(getTag());
			ft.commit();

			return true;
		default:
			break;
		}
		return false;
	}




	
}
