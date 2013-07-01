package smartcampus.android.template.standalone;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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

public class OverviewFragment extends SherlockFragment
{

	public static ProgressDialog	pd;
	public ListView					listViewEventi;
	public static List<Evento>		listaEventi;
	public EventsHandler			eventsHandler;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_myagenda_overview,
				container, false);
		return view;
	}

	@Override
	public void onCreate(Bundle arg0)
	{
		// TODO Auto-generated method stub
		super.onCreate(arg0);

		new ProgressDialog(getActivity());
		OverviewFragment.pd = ProgressDialog.show(getActivity(),
				"Lista degli eventi personali", "Caricamento...");

		eventsHandler = new EventsHandler(getActivity().getApplicationContext(), getActivity());
		eventsHandler.execute();

	}

	public void onStart()
	{
		super.onStart();
		MyAgendaActivity parent = (MyAgendaActivity) getActivity();
		parent.setAgendaState(MenuKind.BASE_MENU);
		getActivity().invalidateOptionsMenu();


		
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
		
//		//forzo l'overview ad essere ricaricata
//		eventsHandler = new EventsHandler(getActivity().getApplicationContext(), getActivity());
//		eventsHandler.execute();
		

	}
	
	
	
//	public static Evento[] EventsSortByDate(List<Evento> arrayEventi) {
//		
//		Evento temp = null;
//		Evento[] ev = new Evento[arrayEventi.size()];
//	    for (int a=1; a<ev.length; a++) {
//	        for(int b=0; b<ev.length - a; b++) {
//	        	
//	            if (((ev[b].getData()).compareTo((ev[b].getData()))) > 0){
//	                //swap movies[b] with movies[b+1]
//	                temp = ev[b];
//	            }
//	            ev[b] = ev[b+1];
//	            ev[b+1] = temp;
//	        }
//	    }
//	    
//	    return ev;
//	}
	
	
	
	
}
	

