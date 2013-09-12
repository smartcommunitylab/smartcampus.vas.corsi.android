package smartcampus.android.studyMate.gruppi_studio;

import java.util.ArrayList;

import smartcampus.android.template.standalone.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.example.model_classes.AttivitaStudio;

public class Impegni_Fragment extends SherlockFragment {
	ArrayList<AttivitaStudio> lista_impegni;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.listaimpegni_fragment, container,
				false);
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		lista_impegni = ((Overview_GDS) getActivity())
				.getContextualListaImpegni();
		/*
		 * ergo recuperare varie view e piazzare gli adapater per visualizzare
		 * le varie attivitàstudio
		 */

	}
}
