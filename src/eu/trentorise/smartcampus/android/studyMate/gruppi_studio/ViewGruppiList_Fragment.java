package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import java.util.ArrayList;
import java.util.Collections;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.android.studyMate.R;
import eu.trentorise.smartcampus.android.studyMate.models.GruppoDiStudio;

public class ViewGruppiList_Fragment extends SherlockFragment {

	private ArrayList<GruppoDiStudio> user_gds_list;
	protected Object mActionMode;
	private View longclikedview = null;// la view selezionata alla quale
										// cambiare colore finchè l'actionmode è
										// attiva

	@Override
	public void onCreate(Bundle savedInstanceState) {
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
		user_gds_list = ((Lista_GDS_activity) getActivity()).getUser_gds_list();
		ListView listview = (ListView) getActivity().findViewById(
				R.id.listview_gruppi_di_studio);

		// sorting gds before rendering them on screen
		Collections.sort(user_gds_list);

		Adapter_gds_to_list adapter = new Adapter_gds_to_list(getActivity(),
				R.id.listview_gruppi_di_studio, user_gds_list);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {

				/*
				 * Per passare il contextualGDS alla overview_GDS activity,
				 * piazzo il contextualgds nel contextualcollection e poi dalla
				 * overview_gds vado a recuperarlo. Il contextualcollection è un
				 * Arraylist<Object> della classe MyApplication.
				 * contextualcollection è statico e poichè la classe
				 * myapplication è pubblica chiunque può accedere al
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

		listview.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (mActionMode != null) {
					return false;
				}

				// Start the CAB using the ActionMode.Callback defined above
				mActionMode = ViewGruppiList_Fragment.this
						.getSherlockActivity().startActionMode(
								mActionModeCallback);

				// view.setSelected(true);

				view.setPressed(true);
				longclikedview = view;

				// view.setBackgroundColor(getResources().getColor(
				// R.color.pressed_theme2_studymate));
				return true;
			}

		});

	}

	private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

		// Called when the action mode is created; startActionMode() was called
		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			// Inflate a menu resource providing context menu items
			MenuInflater inflater = mode.getMenuInflater();
			inflater.inflate(R.menu.action_mode_allgds, menu);
			return true;
		}

		// Called each time the action mode is shown. Always called after
		// onCreateActionMode, but
		// may be called multiple times if the mode is invalidated.
		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return false; // Return false if nothing is done
		}

		// Called when the user selects a contextual menu item
		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			switch (item.getItemId()) {
			case R.id.ac_elimina:
				Toast.makeText(MyApplication.getAppContext(), "ole",
						Toast.LENGTH_SHORT).show();
				mode.finish(); // Action picked, so close the CAB
				return true;
			default:
				return false;
			}
		}

		// Called when the user exits the action mode
		@Override
		public void onDestroyActionMode(ActionMode mode) {
			longclikedview.setPressed(false);
			mActionMode = null;
		}
	};

	public ArrayList<GruppoDiStudio> getUser_gds_list() {
		return user_gds_list;
	}

	public void setUser_gds_list(ArrayList<GruppoDiStudio> user_gds_list) {
		this.user_gds_list = user_gds_list;
	}

}