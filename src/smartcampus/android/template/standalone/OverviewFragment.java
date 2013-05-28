package smartcampus.android.template.standalone;

import java.util.Date;

import com.actionbarsherlock.app.SherlockListFragment;

public class OverviewFragment extends SherlockListFragment {
	@SuppressWarnings("deprecation")
	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		// Inflate the layout for this fragment
//		View view = inflater.inflate(R.layout.fragment_myagenda_overview,
//				container, false);
//		return view;
//	}

	public void onStart() {
		super.onStart();
		String[] events = getResources().getStringArray(R.array.NewEventiFuffa);
		EventItem[] items = new EventItem[events.length];

		int i = 0;
		for (String s : events) {
			String[] itms = s.split(",");
			Date d = new Date(Date.parse(itms[0]));
			DetailedEvent e = new DetailedEvent(d, itms[1], itms[2], itms[3]);
			items[i++] = new EventItem(e);
		}

		EventAdapter adapter = new EventAdapter(getSherlockActivity(), items);
		//ListView listView = (ListView) getSherlockActivity().findViewById(R.id.listViewEventi);
		setListAdapter(adapter);

	}
}
