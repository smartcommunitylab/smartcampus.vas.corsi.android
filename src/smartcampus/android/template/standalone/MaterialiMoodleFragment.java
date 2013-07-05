package smartcampus.android.template.standalone;

import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;

public class MaterialiMoodleFragment extends SherlockListFragment {

	public void onStart() {
		super.onStart();
		String[] events = getResources().getStringArray(R.array.EventiFuffa);
		MaterialItem[] items = new MaterialItem[events.length];

		int i = 0;
		for (String s : events) {
			String[] itms = s.split(",");
			items[i++] = new MaterialItem(itms[0], itms[1],
					R.drawable.smartuni_logo);
		}

		MaterialAdapter adapter = new MaterialAdapter(getSherlockActivity(),
				items);
		 ListView listView = (ListView)
		 getSherlockActivity().findViewById(R.id.listViewEventi);
		 listView.setAdapter(adapter);

	}
}