package smartcampus.android.phl;

import smartcampus.android.template.standalone.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

public class MaterialiPhlFragment extends SherlockFragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_phl_materiali,
				container, false);
		// // do your view initialization here
		return view;
	}
	
	public void onStart() {
		super.onStart();
		
		ListView listViewCorsi = (ListView) getSherlockActivity().findViewById(
				R.id.listViewPhlMateriali);

		Bundle arguments = new Bundle();
		//arguments.getSerializable("cartella");
		PHLengine4Course handlerCoursesMaterial = new PHLengine4Course(
				getActivity().getApplicationContext(), 
				getActivity(), listViewCorsi, getSherlockActivity());
		handlerCoursesMaterial.execute(arguments);
	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
//		String[] events = getResources().getStringArray(R.array.EventiFuffa);
//		MaterialItem[] items = new MaterialItem[events.length];
//
//		int i = 0;
//		for (String s : events) {
//			String[] itms = s.split(",");
//			items[i++] = new MaterialItem(itms[0], itms[1],
//					R.drawable.smartuni_logo);
//		}
//
//		MaterialAdapter adapter = new MaterialAdapter(getSherlockActivity(),
//				items);
//		 ListView listView = (ListView)
//		getSherlockActivity().findViewById(R.id.listViewEventi);
//		 listView.setAdapter(adapter);

	}
}