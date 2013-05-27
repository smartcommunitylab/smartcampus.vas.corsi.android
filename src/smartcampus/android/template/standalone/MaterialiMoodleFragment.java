package smartcampus.android.template.standalone;

import java.util.Date;

import com.actionbarsherlock.app.SherlockListFragment;

public class MaterialiMoodleFragment extends SherlockListFragment {

	public void onStart() {
		super.onStart();
		String[] events = getResources().getStringArray(R.array.EventiFuffa);
		TitledItem[] items = new TitledItem[events.length];

		int i = 0;
		for (String s : events) {
			String[] itms = s.split(",");
			@SuppressWarnings("deprecation")
			Date d = new Date(Date.parse(itms[0]));
			CourseEvent e = new CourseEvent(d, itms[1]);
			items[i++] = new EventItem(e);
		}

		TitledAdapter adapter = new TitledAdapter(getSherlockActivity(), items);
		//ListView listView = (ListView) getSherlockActivity().findViewById(R.id.listViewEventi);
		setListAdapter(adapter);

	}
}