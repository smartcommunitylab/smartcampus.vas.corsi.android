package smartcampus.android.template.standalone;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import smartcampus.android.template.standalone.MyAgendaActivity.MenuKind;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

import eu.trentorise.smartcampus.smartuni.models.Evento;
import eu.trentorise.smartcampus.smartuni.utilities.EventsHandler;

public class OverviewFragment extends SherlockFragment {

	public static ProgressDialog pd;
	public ListView listViewEventi;
	public List<Evento> listaEventi;
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
	public void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);

		new ProgressDialog(getActivity());
		OverviewFragment.pd = ProgressDialog.show(getActivity(), "Lista degli eventi personali",
				"Caricamento...");

		eventsHandler = new EventsHandler(getActivity().getApplicationContext());
		eventsHandler.execute();

		
	}

	public void onStart() {
		super.onStart();
		MyAgendaActivity parent = (MyAgendaActivity) getActivity();
		parent.setAgendaState(MenuKind.BASE_MENU);
		getActivity().invalidateOptionsMenu();

		
		try {
			listaEventi = eventsHandler.get();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// String[] events =
		// getResources().getStringArray(R.array.NewEventiFuffa);
		// EventItem[] items = new EventItem[events.length];
		//
		// int i = 0;
		// for (String s : events) {
		// String[] itms = s.split(",");
		// @SuppressWarnings("deprecation")
		// Date d = new Date(Date.parse(itms[0]));
		// AdptDetailedEvent e = new AdptDetailedEvent(d, itms[1], itms[2],
		// itms[3]);
		// items[i++] = new EventItem(e);
		// }

		EventItem[] listEvItem = new EventItem[listaEventi.size()];
		int i = 0;

		for (Evento ev : listaEventi) {
			AdptDetailedEvent e = new AdptDetailedEvent(ev.getData(),
					ev.getTitolo(), ev.getDescrizione(), ev.getStart()
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
				parent.setAgendaState(MenuKind.DETAIL_OF_EVENT);
				getActivity().invalidateOptionsMenu();
				FragmentTransaction ft = getSherlockActivity()
						.getSupportFragmentManager().beginTransaction();
				Fragment fragment = new DettailOfEventFragment();
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
				ft.replace(R.id.tabOverview, fragment);
				ft.addToBackStack(null);
				ft.commit();
			}
		});

		pd.dismiss();

	}
}
