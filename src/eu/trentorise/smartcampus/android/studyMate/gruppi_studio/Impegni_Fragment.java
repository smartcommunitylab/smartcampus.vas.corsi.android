package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import java.util.ArrayList;

import smartcampus.android.template.standalone.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.android.studyMate.models.AttivitaStudio;

public class Impegni_Fragment extends SherlockFragment {
	public ArrayList<AttivitaStudio> lista_impegni;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.listaimpegni_fragment, container,
				false);
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onStart() {
		super.onStart();
		lista_impegni = ((Overview_GDS) getActivity())
				.getContextualListaImpegni();
		/*
		 * ergo recuperare varie view e piazzare gli adapater per visualizzare
		 * le varie attivit�studio
		 */

		ListView impegni_listview = (ListView) getSherlockActivity()
				.findViewById(R.id.lista_impegni);

		AttivitaStudioAdapter adapter = new AttivitaStudioAdapter(
				getSherlockActivity(), R.id.lista_impegni, lista_impegni);

		impegni_listview.setAdapter(adapter);

		impegni_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				AttivitaStudioAdapter adpt = (AttivitaStudioAdapter) parent
						.getAdapter();
				ArrayList<AttivitaStudio> entries = adpt.getEntries();
				final AttivitaStudio selected_impegno = entries.get(position);
				Intent intent = new Intent(getActivity(), ShowImpegnoGDS.class);
				MyApplication.getContextualCollection().add(selected_impegno);
				startActivity(intent);

			}
		});

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.impegni_gds_menu, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.aggiungi_impegno: {
			Intent intent = new Intent(MyApplication.getAppContext(),
					Add_attivita_studio_activity.class);
			startActivity(intent);
		}

		default:
			return super.onOptionsItemSelected(item);
		}

	}

	public ArrayList<AttivitaStudio> getLista_impegni() {
		return lista_impegni;
	}
	
}
