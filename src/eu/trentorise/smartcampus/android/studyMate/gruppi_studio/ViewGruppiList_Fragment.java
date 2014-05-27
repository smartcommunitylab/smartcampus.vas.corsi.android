package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import it.smartcampuslab.studymate.R;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

import eu.trentorise.smartcampus.android.studyMate.models.GruppoDiStudio;
import eu.trentorise.smartcampus.android.studyMate.utilities.Constants;

public class ViewGruppiList_Fragment extends SherlockFragment {

	private ArrayList<GruppoDiStudio> user_gds_list;
private View view;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.list_gruppi_studio_4fragment,
				container, false);
		return view;
	}

	
	@Override
	public void onStart() {
		super.onStart();
		user_gds_list = ((Lista_GDS_activity) getActivity()).getUser_gds_list();
		ListView listview = (ListView) view.findViewById(
				R.id.listview_gruppi_di_studio);
		if (user_gds_list != null) {
			TextView tv_errore = (TextView) view.findViewById(
					R.id.stringa_errore_caricamento_lista);
			tv_errore.setVisibility(View.GONE);
			Adapter_gds_to_list adapter = new Adapter_gds_to_list(
					getActivity(), R.id.listview_gruppi_di_studio,
					user_gds_list);
			listview.setAdapter(adapter);
			listview.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View v,
						int position, long id) {
					GruppoDiStudio contextualGDS = user_gds_list.get(position);
					Intent intent = new Intent(getActivity(),
							Overview_GDS.class);
					intent.putExtra(Constants.CONTESTUAL_GDS, contextualGDS);
					startActivity(intent);
				}
			});

		} else {
			TextView tv_errore = (TextView) view.findViewById(
					R.id.stringa_errore_caricamento_lista);
			tv_errore.setVisibility(View.VISIBLE);
		}

	}

	public ArrayList<GruppoDiStudio> getUser_gds_list() {
		return user_gds_list;
	}

	public void setUser_gds_list(ArrayList<GruppoDiStudio> user_gds_list) {
		this.user_gds_list = user_gds_list;
	}

}
