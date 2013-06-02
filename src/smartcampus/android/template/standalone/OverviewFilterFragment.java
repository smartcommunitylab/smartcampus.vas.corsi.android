package smartcampus.android.template.standalone;

import java.util.Date;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

public class OverviewFilterFragment extends SherlockFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_myagenda_overview,
				container, false);
		return view;
	}

	public void onStart() {
		super.onStart();

		String[] events = getResources().getStringArray(R.array.NewEventiFuffa);
		EventItem[] items = new EventItem[events.length];

		int i = 0;
		for (String s : events) {
			String[] itms = s.split(",");
			Date d = new Date(Date.parse(itms[0]));
			AdptDetailedEvent e = new AdptDetailedEvent(d, itms[1], itms[2],
					itms[3]);
			items[i++] = new EventItem(e);
		}

		EventAdapter adapter = new EventAdapter(getSherlockActivity(), items);
		ListView listView = (ListView) getSherlockActivity().findViewById(
				R.id.listViewEventi);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				MyAgendaActivity parent = (MyAgendaActivity) getActivity();
				parent.setAgendaState(true);
				getActivity().invalidateOptionsMenu();
				FragmentTransaction ft = getSherlockActivity()
						.getSupportFragmentManager().beginTransaction();
				Fragment fragment = new DettailOfEventFragment();
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
				ft.replace(R.id.tabOverview, fragment);
				ft.addToBackStack(null);
				ft.commit();
				//getActivity().onCreateOptionsMenu(R.menu.test);
			}

		});

	}
}
