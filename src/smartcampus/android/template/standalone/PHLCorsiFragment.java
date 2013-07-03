package smartcampus.android.template.standalone;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

public class PHLCorsiFragment extends SherlockFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_phl_corsi,
				container, false);
		// // do your view initialization here
		return view;
	}
	// ListView listViewCorsi = (ListView) getSherlockActivity().findViewById(
	// R.id.listViewCorsi);
	// getActivity().invalidateOptionsMenu();
	// // String[] corsi = getResources().getStringArray(R.array.Corsi);
	// // String[] corsiInt = getResources().getStringArray(
	// // R.array.CorsiInteresse);
	// // TitledItem[] items = new TitledItem[corsi.length + corsiInt.length];
	//
	// Bundle arguments = new Bundle();
	// CoursesHandler handlerPersonalCourses = new CoursesHandler(
	// getActivity().getApplicationContext(), listViewCorsi,
	// getActivity(), getSherlockActivity());
	// handlerPersonalCourses.execute(arguments);

	public void onStart() {
		super.onStart();

		// PHLengine phl = new PHLengine();
		// List<Corso> frequentati = phl.getFrequentedCourses();
		ListView listViewCorsi = (ListView) getSherlockActivity().findViewById(
				R.id.listViewCorsiPHL);

		Bundle arguments = new Bundle();
		PHLengine handlerPersonalCoursesPHL = new PHLengine(getActivity()
				.getApplicationContext(), getActivity(), listViewCorsi,
				getSherlockActivity());
		handlerPersonalCoursesPHL.execute(arguments);

		// String[] corsi = getResources().getStringArray(R.array.Corsi);
		// String[] corsiInt = getResources().getStringArray(
		// R.array.CorsiInteresse);
		//
		// TitledItem[] items = new TitledItem[corsi.length + corsiInt.length];
		// /*
		// * TitledItem[] items = new TitledItem[frequentati.size() +
		// * corsiInt.length];
		// */
		//
		// int i = 0;
		// // for (Corso c : frequentati)
		// // {
		// // items[i++] = new TitledItem("Corsi da libretto", c.getNome());
		// // }
		//
		// for (String s : corsi) {
		// items[i++] = new TitledItem("Corsi da libretto", s);
		// }
		//
		// for (String s : corsiInt) {
		// items[i++] = new TitledItem("Corsi di interesse", s);
		// }
		//
		// TitledAdapter adapter = new TitledAdapter(getSherlockActivity(),
		// items);
		// setListAdapter(adapter);
		// ListView listView = getListView();
		//
		// listView.setOnItemClickListener(new ListView.OnItemClickListener() {
		// @Override
		// public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
		// long arg3) {
		// // TODO Auto-generated method stub String
		// Intent intent = new Intent();
		// intent.setClass(getActivity(), PHL4Courses.class);
		//
		// startActivity(intent);
		// }
		// });
	}
}
