package eu.trentorise.smartcampus.android.studyMate.phl;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;

import eu.trentorise.smartcampus.android.studyMate.R;

public class MaterialiMoodleFragment extends SherlockFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_phl_materiali_moodle,
				container, false);
		// // do your view initialization here
		return view;
	}

	public void onStart() {
		super.onStart();
		// String[] events = getResources().getStringArray(R.array.EventiFuffa);
		// MaterialItem[] items = new MaterialItem[events.length];
		//
		// int i = 0;
		// for (String s : events) {
		// String[] itms = s.split(",");
		// items[i++] = new MaterialItem(itms[0], itms[1],
		// R.drawable.smartuni_logo, 1);
		// }
		//
		// MaterialAdapter adapter = new MaterialAdapter(getSherlockActivity(),
		// items);
		// ListView listView = (ListView)
		// getSherlockActivity().findViewById(R.id.listViewEventi);
		// listView.setAdapter(adapter);

	}
}