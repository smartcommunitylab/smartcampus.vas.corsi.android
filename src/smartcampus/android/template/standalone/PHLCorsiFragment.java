package smartcampus.android.template.standalone;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;

public class PHLCorsiFragment extends SherlockListFragment {
	// @Override
	// public View onCreateView(LayoutInflater inflater, ViewGroup container,
	// Bundle savedInstanceState) {
	// // Inflate the layout for this fragment
	// View view = inflater.inflate(R.layout.fragment_myagenda_corsi, container,
	// false);
	// // do your view initialization here
	// return view;
	// }
	public void onStart() {
		super.onStart();
		String[] corsi = getResources().getStringArray(R.array.Corsi);
		String[] corsiInt = getResources().getStringArray(
				R.array.CorsiInteresse);
		TitledItem[] items = new TitledItem[corsi.length + corsiInt.length];

		int i = 0;
		for (String s : corsi) {
			items[i++] = new TitledItem("Corsi da libretto", s);
		}
		for (String s : corsiInt) {
			items[i++] = new TitledItem("Corsi di interesse", s);
		}

		TitledAdapter adapter = new TitledAdapter(getSherlockActivity(), items);
		// ListView listView = (ListView)
		// getSherlockActivity().findViewById(R.id.listViewCorsi);
		setListAdapter(adapter);

//		
//		ListView listView = (ListView) getSherlockActivity().findViewById(
//				R.id.listViewCorsiPHL);
		ListView listView = getListView();
		listView.setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub String
//				String courseSelectedName = (String) arg0
//						.getItemAtPosition(arg2);
				Intent intent = new Intent();
				intent.setClass(getActivity(), PHL4Courses.class);
				// i.putExtra("courseSelectedName", courseSelectedName);
				// i.putExtra("courseSelectedId",
				// CoursesHandlerLite.getIDCourseSelected(arg2));
				startActivity(intent);
			}

		});

	}
}
