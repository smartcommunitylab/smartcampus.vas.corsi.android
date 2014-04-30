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

import eu.trentorise.smartcampus.android.studyMate.models.Evento;
import eu.trentorise.smartcampus.android.studyMate.models.GruppoDiStudio;
import eu.trentorise.smartcampus.android.studyMate.utilities.AdptDetailedEvent;
import eu.trentorise.smartcampus.android.studyMate.utilities.Constants;
import eu.trentorise.smartcampus.android.studyMate.utilities.EventAdapter;
import eu.trentorise.smartcampus.android.studyMate.utilities.EventItem;
import eu.trentorise.smartcampus.studymate.R;

public class Impegni_Fragment extends SherlockFragment {
	public ArrayList<Evento> lista_impegni;
	GruppoDiStudio gds;

	// protected Object mActionMode;

	public static Impegni_Fragment newInstance(
			ArrayList<Evento> arraylistimpegni, GruppoDiStudio gds) {
		Impegni_Fragment myFragment = new Impegni_Fragment();

		Bundle args = new Bundle();
		args.putSerializable(Constants.IMPEGNI_LIST, arraylistimpegni);
		args.putSerializable(Constants.GDS, gds);
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

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		lista_impegni = (ArrayList<Evento>) getArguments().getSerializable(
				"serializableobject");
		gds = (GruppoDiStudio) getArguments().getSerializable(Constants.GDS);
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
		EventItem[] listEvItem = new EventItem[lista_impegni.size()];
		int i = 0;
		for (Evento ev : lista_impegni) {
			AdptDetailedEvent e = new AdptDetailedEvent(ev.getEventoId()
					.getDate(), ev.getTitle(), ev.getType(), ev.getEventoId()
					.getStart().toString(), ev.getRoom());
			listEvItem[i++] = new EventItem(e, getActivity());

		}
		EventAdapter adapter = new EventAdapter(getSherlockActivity(),
				listEvItem);
		impegni_listview.setAdapter(adapter);

		impegni_listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				EventAdapter adpt = (EventAdapter) parent.getAdapter();
				final Evento selected_impegno = lista_impegni.get(position);
				Intent intent = new Intent(getActivity(), ShowImpegnoGDS.class);
				intent.putExtra(Constants.CONTEXTUAL_ATT, selected_impegno);
				startActivity(intent);
			}
		});

	}

}
