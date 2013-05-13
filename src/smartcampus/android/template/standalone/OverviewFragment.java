package smartcampus.android.template.standalone;

import java.util.Date;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

public class OverviewFragment extends SherlockFragment {
	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_myagenda_overview, container, false);

		String[] events = getResources().getStringArray(R.array.EventiFuffa);
		TitledItem[] items = new TitledItem[events.length];	
	
		int i = 0;
		for (String s : events) {
			String[] itms = s.split(",");
			Date d = new Date(Date.parse(itms[0]));
			CourseEvent e = new CourseEvent(d, itms[1]);
			items[i++] = new EventItem(e);
		}
		
			
		TitledAdapter adapter = new TitledAdapter(view.getContext(), items);
		ListView listView = (ListView) view.findViewById(R.id.listViewEventi
				);
		listView.setAdapter(adapter);
		
		
		return view;
	}
}
