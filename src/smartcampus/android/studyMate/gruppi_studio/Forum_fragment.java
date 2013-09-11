package smartcampus.android.studyMate.gruppi_studio;

import smartcampus.android.template.standalone.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

public class Forum_fragment extends SherlockFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.forum_fragment, container, false);
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		//logic to popolate fragmentview here
		
		ListView chat=(ListView) getSherlockActivity().findViewById(R.id.forum_listview);
		TextView chateText=(TextView) getSherlockActivity().findViewById(R.id.forum_edittext);
		
		
	}

}
