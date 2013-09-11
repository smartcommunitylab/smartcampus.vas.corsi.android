package smartcampus.android.studyMate.gruppi_studio;

import java.util.ArrayList;

import smartcampus.android.template.standalone.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.model_classes.GruppoDiStudio;

public class ViewGruppiList_Fragment extends Fragment {

	private ArrayList<GruppoDiStudio> user_gds_list;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		user_gds_list = ((Lista_GDS_activity) getActivity()).getUser_gds_list();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.list_gruppi_studio_4fragment,
				container, false);
		return view;
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		ListView listview = (ListView) getActivity().findViewById(
				R.id.listview_gruppi_di_studio);
		Adapter_gds_to_list adapter = new Adapter_gds_to_list(getActivity(),
				R.id.listview_gruppi_di_studio, user_gds_list);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				/*
				 * Toast.makeText(getActivity(), "" + position,
				 * Toast.LENGTH_SHORT) .show();
				 */
				Intent intent = new Intent(getActivity(), Overview_GDS.class);
				startActivity(intent);

			}
		});

	}

}
