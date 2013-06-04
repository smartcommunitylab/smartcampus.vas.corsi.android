package smartcampus.android.template.standalone;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

public class CorsiFragment extends SherlockFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_myagenda_corsi,
				container, false);
		// // do your view initialization here
		return view;
	}

	public void onStart() {
		super.onStart();
		MyAgendaActivity parent = (MyAgendaActivity) getActivity();
		parent.setAgendaState(0);

		getActivity().invalidateOptionsMenu();
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
		ListView listView = (ListView) getSherlockActivity().findViewById(
				R.id.listViewCorsi);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				MyAgendaActivity parent = (MyAgendaActivity) getActivity();
				parent.setAgendaState(1);
				getActivity().invalidateOptionsMenu();
				FragmentTransaction ft = getSherlockActivity()
						.getSupportFragmentManager().beginTransaction();
				Fragment fragment = new OverviewFilterFragment();
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
				ft.replace(R.id.tabCorsi, fragment);
				ft.addToBackStack(null);
				ft.commit();
			}
		});

	}
}
