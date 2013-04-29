package smartcampus.android.template.standalone;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ResultSearchedActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result_searched);
		ActionBar ab = getActionBar();
		ab.setHomeButtonEnabled(true);
		ab.setDisplayHomeAsUpEnabled(true);
		
		Intent i = getIntent();
		String department = i.getStringExtra("department");
		String degree = i.getStringExtra("courseDegree");
		String course = i.getStringExtra("course").toLowerCase();
		TextView tv = (TextView)findViewById(R.id.textView1);
		if(course.equals("")){
			if(department.equals("Tutto"))
			tv.setText(tv.getText()+"Tutto");
			else
				if(degree.equals("Tutto"))
					tv.setText(tv.getText()+" Dipartimento di "+department.toString());
				else
					tv.setText(tv.getText()+" Dipartimento di "+department.toString()+", corso di laurea in "+degree.toString());	
		}else{
			if(department.equals("Tutto")){
				if(degree.equals("Tutto"))
					tv.setText(tv.getText()+" "+course.toString());	
				else
					tv.setText(tv.getText()+" "+course.toString()+ " del corso di laurea in "+degree.toString());	
			}else{
				if(degree.equals("Tutto"))
					tv.setText(tv.getText()+" "+course.toString()+ " del dipartimento di "+department.toString());	
				else
					tv.setText(tv.getText()+" "+course.toString()+ " del dipartimento di "+department.toString()+", corso di laurea in "+degree.toString());	
			}
				
			
		}
		
		String[] mTempArray = getResources().getStringArray(R.array.drop_down_spinner_singlecourses_IT);
	    
		ListView listView = (ListView)findViewById(R.id.listView1);
		
		int length = mTempArray.length;
		ArrayList<String> arrayCourseSearched = new ArrayList<String>();
		
		for(int k=0;k<length;k++){
			if(mTempArray[k].toLowerCase().contains(course)){
				arrayCourseSearched.add(mTempArray[k]);
			}
		}
		ArrayAdapter adapterCursesList = new ArrayAdapter<String>(ResultSearchedActivity.this, android.R.layout.simple_list_item_1, arrayCourseSearched);
		
	    listView.setAdapter(adapterCursesList);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result_searched, menu);
		return true;
	}

	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intentHome = new Intent(ResultSearchedActivity.this, FindHomeActivity.class);
			intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intentHome);
			return true;
		default:
			return super.onOptionsItemSelected(item);

		}
	}
}
