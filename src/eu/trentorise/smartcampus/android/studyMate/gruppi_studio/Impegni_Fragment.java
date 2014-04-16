package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import java.util.ArrayList;

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

import eu.trentorise.smartcampus.android.studyMate.models.Evento;
import eu.trentorise.smartcampus.android.studyMate.models.GruppoDiStudio;
import eu.trentorise.smartcampus.studymate.R;

public class Impegni_Fragment extends SherlockFragment {
	public ArrayList<Evento> lista_impegni;
	GruppoDiStudio gds;

	// protected Object mActionMode;

	public static Impegni_Fragment newInstance(
			ArrayList<Evento> arraylistimpegni, GruppoDiStudio gds) {
		Impegni_Fragment myFragment = new Impegni_Fragment();

		Bundle args = new Bundle();
		args.putSerializable("lista_impegni", arraylistimpegni);
		args.putSerializable("gds", gds);
		myFragment.setArguments(args);

		return myFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.listaimpegni_fragment, container,
				false);
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		lista_impegni = (ArrayList<Evento>) getArguments()
				.getSerializable("serializableobject");
		gds = (GruppoDiStudio) getArguments().getSerializable("gds");
		return;
	}

	@Override
	public void onStart() {
		super.onStart();

		lista_impegni = ((Overview_GDS) getActivity())
				.getContextualListaImpegni();

		// gestione listaimpegni
		ListView impegni_listview = (ListView) getSherlockActivity()
				.findViewById(R.id.lista_impegni);
		AttivitaStudioAdapter adapter = new AttivitaStudioAdapter(
				getSherlockActivity(), R.id.lista_impegni, lista_impegni);
		impegni_listview.setAdapter(adapter);

		impegni_listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				AttivitaStudioAdapter adpt = (AttivitaStudioAdapter) parent
						.getAdapter();
				ArrayList<Evento> entries = adpt.getEntries();
				final Evento selected_impegno = entries.get(position);
				Intent intent = new Intent(getActivity(), ShowImpegnoGDS.class);
				intent.putExtra("contextualAttivitaStudio", selected_impegno);
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
			Intent intent = new Intent(getActivity().getApplicationContext(),
					Add_attivita_studio_activity.class);
			intent.putExtra("gds", gds);
			startActivity(intent);
			return super.onOptionsItemSelected(item);
		}

		default:
			return super.onOptionsItemSelected(item);
		}

	}



}
