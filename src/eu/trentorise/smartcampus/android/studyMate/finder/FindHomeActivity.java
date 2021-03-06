package eu.trentorise.smartcampus.android.studyMate.finder;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuInflater;

import eu.trentorise.smartcampus.android.studyMate.MyUniActivity;
import eu.trentorise.smartcampus.android.studyMate.R;
import eu.trentorise.smartcampus.android.studyMate.models.CorsoLaurea;
import eu.trentorise.smartcampus.android.studyMate.models.Dipartimento;
import eu.trentorise.smartcampus.android.studyMate.utilities.Constants;
import eu.trentorise.smartcampus.android.studyMate.utilities.FindCoursesDegreeHandler;
import eu.trentorise.smartcampus.android.studyMate.utilities.FindDepartmentsHandler;

public class FindHomeActivity extends SherlockFragmentActivity implements
		TextWatcher {
	private Spinner spinner1;
	private Spinner spinner2;

	public MultiAutoCompleteTextView searchTV;
	public ArrayAdapter<String> adapter;
	public static ProgressDialog pd;

	Dipartimento departSelected;
	CorsoLaurea corsoLaureaSelected;
	public List<Dipartimento> listDep;
	public List<CorsoLaurea> listCorLaurea;

	FindDepartmentsHandler findDepHandler;
	FindCoursesDegreeHandler findDegHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_home);
		ActionBar ab = getSupportActionBar();
		ab.setHomeButtonEnabled(true);
		ab.setDisplayHomeAsUpEnabled(true);

		setTitle(getResources().getString(R.string.title_activity_find_courses));

		spinner1 = (Spinner) findViewById(R.id.spinner1);
		spinner2 = (Spinner) findViewById(R.id.spinner2);

		// setto la lista iniziale negli spinner
		List<String> initialList = new ArrayList<String>();
		initialList.add(new String(getResources().getString(
				R.string.finder_initiallist_dep)));
		List<String> initialListDeg = new ArrayList<String>();
		initialListDeg.add(new String(getResources().getString(
				R.string.finder_initiallist_deg)));
		ArrayAdapter<String> adapterInitialList = new ArrayAdapter<String>(
				FindHomeActivity.this, R.layout.list_studymate_row_list_simple,
				initialList);
		ArrayAdapter<String> adapterInitialListDeg = new ArrayAdapter<String>(
				FindHomeActivity.this, R.layout.list_studymate_row_list_simple,
				initialListDeg);

		spinner1.setAdapter(adapterInitialList);
		spinner2.setAdapter(adapterInitialListDeg);
		spinner2.setClickable(false);

		findDepHandler = (FindDepartmentsHandler) new FindDepartmentsHandler(
				getApplicationContext(), spinner1, spinner2, this).execute();

		searchTV = (MultiAutoCompleteTextView) findViewById(R.id.multiAutoCompleteTextView1);
		searchTV.addTextChangedListener(FindHomeActivity.this);
	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.search_home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intentHome = new Intent(FindHomeActivity.this,
					MyUniActivity.class);
			intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intentHome);
			return true;
		case R.id.itemSearchCourses:
			Intent intentSearch = new Intent(FindHomeActivity.this,
					ResultSearchedActivity.class);
			intentSearch.putExtra(Constants.DEPARTMENT,
					findDepHandler.getDepartSelected());
			intentSearch.putExtra(Constants.COURSE_DEG,
					FindCoursesDegreeHandler.corsoLaureaSelected);
			MultiAutoCompleteTextView textV = (MultiAutoCompleteTextView) findViewById(R.id.multiAutoCompleteTextView1);
			intentSearch.putExtra(Constants.COURSE_DEG_SPEC, textV.getText()
					.toString());
			startActivity(intentSearch);
			return true;
		default:
			return super.onOptionsItemSelected(item);

		}
	}

	@Override
	public void afterTextChanged(Editable arg0) {
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TextView textSearch = (TextView)
		// findViewById(R.id.textViewLabelCerca);
		// TextView textSearching = (TextView)
		// findViewById(R.id.textViewLabelCercando);
		// textSearching.setText(" " + searchTV.getText().toString());
		// textSearch.setText(R.string.find_label_searchingnow);

	}
}
