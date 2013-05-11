package smartcampus.android.template.standalone;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

public class CorsiFragment extends SherlockFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_myagenda_corsi, container, false);
		// do your view initialization here
		
		String[] corsi = getResources().getStringArray(R.array.Corsi);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, corsi);
		ListView listView = (ListView) view.findViewById(R.id.listViewCorsi);
		listView.setAdapter(adapter);
		
		String[] corsiInt = getResources().getStringArray(
				R.array.CorsiInteresse);

		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(view.getContext(),
				android.R.layout.simple_list_item_1, corsiInt);
		ListView listView2 = (ListView) view.findViewById(R.id.listViewCorsiInteresse);
		listView2.setAdapter(adapter2);
		
		return view;
	}
}
