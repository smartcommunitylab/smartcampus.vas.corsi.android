package smartcampus.android.studyMate.gruppi_studio;

import java.util.ArrayList;

import smartcampus.android.template.standalone.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
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

		ListView impegni_listview = (ListView) getSherlockActivity()
				.findViewById(R.id.lista_impegni);

		AttivitaStudioAdapter adapter = new AttivitaStudioAdapter(
				getSherlockActivity(), R.id.lista_impegni, lista_impegni);

		impegni_listview.setAdapter(adapter);

		// ChatObjectAdapter adapter = new ChatObjectAdapter(
		// getSherlockActivity(), R.id.forum_listview, forum);
		// chat.setAdapter(adapter);

	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		MenuInflater inflater = getSherlockActivity().getSupportMenuInflater();
		inflater.inflate(R.menu.impegni_gds_menu, menu);

		super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.aggiungi_impegno:
			Toast.makeText(MyApplication.getAppContext(),
					"wizard aggiungi impegno da fare..", Toast.LENGTH_SHORT)
					.show();
		default:
			return super.onOptionsItemSelected(item);
		}

	}

}
