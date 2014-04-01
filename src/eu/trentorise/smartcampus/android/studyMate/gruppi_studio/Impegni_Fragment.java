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

import eu.trentorise.smartcampus.android.studyMate.models.AttivitaDiStudio;
import eu.trentorise.smartcampus.android.studyMate.models.GruppoDiStudio;
import eu.trentorise.smartcampus.studymate.R;

public class Impegni_Fragment extends SherlockFragment {
	public ArrayList<AttivitaDiStudio> lista_impegni;
	GruppoDiStudio gds;

	// protected Object mActionMode;

	public static Impegni_Fragment newInstance(
			ArrayList<AttivitaDiStudio> arraylistimpegni, GruppoDiStudio gds) {
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
		lista_impegni = (ArrayList<AttivitaDiStudio>) getArguments()
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
				ArrayList<AttivitaDiStudio> entries = adpt.getEntries();
				final AttivitaDiStudio selected_impegno = entries.get(position);
				Intent intent = new Intent(getActivity(), ShowImpegnoGDS.class);
				intent.putExtra("contextualAttivitaStudio", selected_impegno);
				startActivity(intent);
			}
		});

		// impegni_listview
		// .setOnItemLongClickListener(new OnItemLongClickListener() {
		// @Override
		// public boolean onItemLongClick(AdapterView<?> parent,
		// View view, int position, long id) {
		// // TODO Auto-generated method stub
		// if (mActionMode != null) {
		// return false;
		// }
		//
		// // Start the CAB using the ActionMode.Callback defined
		// // above
		// mActionMode = Impegni_Fragment.this
		// .getSherlockActivity().startActionMode(
		// mActionModeCallback);
		//
		// // view.setSelected(true);
		//
		// view.setPressed(true);
		//
		// // view.setBackgroundColor(getResources().getColor(
		// // R.color.pressed_theme2_studymate));
		// return true;
		// }
		//
		// });

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

	public ArrayList<AttivitaDiStudio> getLista_impegni() {
		return lista_impegni;
	}

	// private ActionMode.Callback mActionModeCallback = new
	// ActionMode.Callback() {
	//
	// // Called when the action mode is created; startActionMode() was called
	// @Override
	// public boolean onCreateActionMode(ActionMode mode, Menu menu) {
	// // Inflate a menu resource providing context menu items
	// MenuInflater inflater = mode.getMenuInflater();
	// inflater.inflate(R.menu.action_mode_allgds, menu);
	// return true;
	// }
	//
	// // Called each time the action mode is shown. Always called after
	// // onCreateActionMode, but
	// // may be called multiple times if the mode is invalidated.
	// @Override
	// public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
	// return false; // Return false if nothing is done
	// }
	//
	// // Called when the user selects a contextual menu item
	// @Override
	// public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
	// switch (item.getItemId()) {
	// case R.id.ac_elimina:
	// Toast.makeText(MyApplication.getAppContext(), "ole",
	// Toast.LENGTH_SHORT).show();
	// mode.finish(); // Action picked, so close the CAB
	// return true;
	// default:
	// return false;
	// }
	// }
	//
	// // Called when the user exits the action mode
	// @Override
	// public void onDestroyActionMode(ActionMode mode) {
	//
	// mActionMode = null;
	// }
	// };

}
