package smartcampus.android.phl;

import smartcampus.android.template.standalone.R;
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
		View view = inflater.inflate(R.layout.fragment_phl_corsi, container,
				false);
		// // do your view initialization here
		return view;
	}

	public void onStart() {
		super.onStart();
		ListView listViewCorsi = (ListView) getSherlockActivity().findViewById(
				R.id.listViewCorsiPHL);

		Bundle arguments = new Bundle();
		PHLengine handlerPersonalCoursesPHL = new PHLengine(getActivity()
				.getApplicationContext(), getActivity(), listViewCorsi,
				getSherlockActivity());
		handlerPersonalCoursesPHL.execute(arguments);

	}
}
