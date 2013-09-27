package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

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
import android.widget.GridView;
import eu.trentorise.smartcampus.android.studyMate.models.GruppoDiStudio;

public class ViewGruppiGrid_Fragment extends Fragment {

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
		View view = inflater.inflate(R.layout.grid_gruppi_studio_4fragment,
				container, false);

		return view;
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		GridView gridview = (GridView) getActivity().findViewById(
				R.id.gridview_gruppi_di_studio);

		Adapter_gds_to_grid adapter = new Adapter_gds_to_grid(getActivity(),
				R.id.gridview_gruppi_di_studio, user_gds_list);
		gridview.setAdapter(adapter);

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {

				/*
				 * Per passare il contextualGDS alla overview_GDS activity,
				 * piazzo il contextualgds nel contextualcollection e poi dalla
				 * overview_gds vado a recuperarlo. Il contextualcollection � un
				 * Arraylist<Object> della classe MyApplication.
				 * contextualcollection � statico e poich� la classe
				 * myapplication � pubblica chiunque pu� accedere al
				 * contextualcollection. Il contextualcollection lo uso come
				 * spazio di memoria condivisa. Come politica di utilizzo mi
				 * prefiggo di piazzare nel contextualcollection un oggetto
				 * prima di cambiare activity, nella nuova activity per prima
				 * cosa recupero tale oggetto e poi pulisco il
				 * contextualcollection
				 */
				GruppoDiStudio contextualGDS = user_gds_list.get(position);
				MyApplication.getContextualCollection().add(contextualGDS);
				Intent intent = new Intent(getActivity(), Overview_GDS.class);
				startActivity(intent);

			}
		});

	}

}
