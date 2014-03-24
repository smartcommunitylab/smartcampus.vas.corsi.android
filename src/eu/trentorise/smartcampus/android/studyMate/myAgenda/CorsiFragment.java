package eu.trentorise.smartcampus.android.studyMate.myAgenda;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.android.studyMate.utilities.CoursesHandler;
import eu.trentorise.smartcampus.studymate.R;

public class CorsiFragment extends SherlockFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_myagenda_corsi,
				container, false);
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		setHasOptionsMenu(true);
		ListView listViewCorsi = (ListView) getSherlockActivity().findViewById(
				R.id.listViewCorsi);
		getSherlockActivity().supportInvalidateOptionsMenu();

		Bundle arguments = new Bundle();
		CoursesHandler handlerPersonalCourses = new CoursesHandler(
				getActivity().getApplicationContext(), listViewCorsi,
				getActivity(), getSherlockActivity());
		handlerPersonalCourses.execute(arguments);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		menu.clear();
		inflater.inflate(R.menu.agenda, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {

		case R.id.add_event:
			FragmentTransaction ft = getSherlockActivity()
					.getSupportFragmentManager().beginTransaction();
			Fragment fragment = new AddEventActivity();
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			ft.replace(this.getId(), fragment);
			ft.addToBackStack(null);
			ft.commit();
			return true;
		default:
			break;
		}
		return false;
	}

	// public static void clearBackStack(FragmentManager manager){
	// int rootFragment = manager.getBackStackEntryAt(0).getId();
	// manager.popBackStack(rootFragment,
	// FragmentManager.POP_BACK_STACK_INCLUSIVE);
	// }
}
