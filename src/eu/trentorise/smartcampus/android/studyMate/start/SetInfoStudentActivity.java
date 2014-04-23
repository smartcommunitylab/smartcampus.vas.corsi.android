package eu.trentorise.smartcampus.android.studyMate.start;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.android.studyMate.utilities.AsyncSetSharedCds;
import eu.trentorise.smartcampus.android.studyMate.utilities.AsyncSetSharedDip;
import eu.trentorise.smartcampus.studymate.R;

public class SetInfoStudentActivity extends SherlockFragmentActivity {
	private Spinner spinner1;
	private Spinner spinner2;
	
	private AsyncSetSharedDip setDepHandler;
	private AsyncSetSharedCds findDegHandler;
	
	
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_info_studente);
		
		ActionBar ab = getSupportActionBar();
		ab.setHomeButtonEnabled(true);
		ab.setDisplayHomeAsUpEnabled(true);

		spinner1 = (Spinner) findViewById(R.id.spinnerDip);
		spinner2 = (Spinner) findViewById(R.id.spinnerCds);

		// setto la lista iniziale negli spinner
		List<String> initialList = new ArrayList<String>();
		initialList.add(new String(getResources().getString(
				R.string.finder_initiallist_dep)));
		List<String> initialListDeg = new ArrayList<String>();
		initialList.add(new String(getResources().getString(
				R.string.finder_initiallist_dep)));
		ArrayAdapter<String> adapterInitialList = new ArrayAdapter<String>(
				SetInfoStudentActivity.this, R.layout.list_studymate_row_list_simple,
				initialList);
		ArrayAdapter<String> adapterInitialListDeg = new ArrayAdapter<String>(
				SetInfoStudentActivity.this, R.layout.list_studymate_row_list_simple,
				initialListDeg);

		spinner1.setAdapter(adapterInitialList);
		spinner2.setAdapter(adapterInitialListDeg);

		setDepHandler = (AsyncSetSharedDip) new AsyncSetSharedDip(
				getApplicationContext(), spinner1, spinner2, SetInfoStudentActivity.this).execute();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return super.onCreateOptionsMenu(menu);
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			moveTaskToBack(true);
			return true;
		default:
			break;

		}
		return false;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	        moveTaskToBack(true);
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
//	@Override
//	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		MenuInflater inflater = getSupportMenuInflater();
//		inflater.inflate(R.menu.menu_sharedp_studente, menu);
//		return true;
//	}
	
//	@Override
//	public boolean onOptionsItemSelected(
//			com.actionbarsherlock.view.MenuItem item) {
//		switch (item.getItemId()) {
//		case android.R.id.home:
//			Intent intentHome = new Intent(SetInfoStudentActivity.this,
//					MyUniActivity.class);
//			intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			startActivity(intentHome);
//			return true;
//		case R.id.itemSearchCourses:
//		
//			SetInfoStudentActivity.this.finish();
//			
//			Toast.makeText(SetInfoStudentActivity.this, R.string.internet_connection,
//					Toast.LENGTH_SHORT).show();
//			
//			return true;
//		default:
//			return super.onOptionsItemSelected(item);
//
//		}
//	}
}
