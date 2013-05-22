package smartcampus.android.template.standalone;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;

public class PHLCorsiFragment extends SherlockListFragment {

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
		setListAdapter(adapter);
		ListView listView = getListView();
		listView.setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub String
				Intent intent = new Intent();
				intent.setClass(getActivity(), PHL4Courses.class);

				startActivity(intent);
			}

		});

	}
}
