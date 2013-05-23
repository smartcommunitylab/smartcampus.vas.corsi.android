package smartcampus.android.template.standalone;

import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockListFragment;

public class HomeCourseDescriptionFragment extends SherlockFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_home_course_description, container, false);
		
		Intent intent = getActivity().getIntent();
		TextView tvCourseName = (TextView) view.findViewById(R.id.textViewNameCourseHome);
		String courseName = intent.getStringExtra("courseSelectedName");
		tvCourseName.setText(courseName);

		
		return view;
	}
	
	
}
