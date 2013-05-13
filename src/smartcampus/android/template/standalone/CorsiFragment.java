package smartcampus.android.template.standalone;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;

public class CorsiFragment extends SherlockListFragment {
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		// Inflate the layout for this fragment
//		View view = inflater.inflate(R.layout.fragment_myagenda_corsi, container, false);
//		// do your view initialization here
//		return view;
//	}
	public void onStart(){
		super.onStart();
		String[] corsi = getResources().getStringArray(R.array.Corsi);
		String[] corsiInt = getResources().getStringArray(R.array.CorsiInteresse);
		TitledItem[] items = new TitledItem[corsi.length + corsiInt.length];	
	
		int i = 0;
		for (String s : corsi) {
			items[i++] = new TitledItem("Corsi da libretto", s);
		}
		for (String s : corsiInt) {
			items[i++] = new TitledItem("Corsi di interesse", s);
		}
			
		TitledAdapter adapter = new TitledAdapter(getSherlockActivity(), items);
		//ListView listView = (ListView) getSherlockActivity().findViewById(R.id.listViewCorsi);
		setListAdapter(adapter);
		

//		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(view.getContext(),
//				android.R.layout.simple_list_item_1, corsiInt);
//		ListView listView2 = (ListView) view.findViewById(R.id.listViewCorsiInteresse);
//		listView2.setAdapter(adapter2);
		
	}
}
