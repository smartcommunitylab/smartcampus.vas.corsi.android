package smartcampus.android.template.standalone;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import eu.trentorise.smartcampus.smartuni.models.CorsoLaurea;
import eu.trentorise.smartcampus.smartuni.models.Dipartimento;
import eu.trentorise.smartcampus.smartuni.utilities.FindCoursesDegreeHandler;
import eu.trentorise.smartcampus.smartuni.utilities.FindDepartmentsHandler;

public class FindHomeActivity extends Activity implements TextWatcher {
	private Spinner spinner1;
	private Spinner spinner2;

	public MultiAutoCompleteTextView searchTV;
	public ArrayAdapter<String> adapter;
	public static ProgressDialog pd;

	public String departSelectedName = null;
	public String courseSelected = null;
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
		ActionBar ab = getActionBar();
		ab.setHomeButtonEnabled(true);
		ab.setDisplayHomeAsUpEnabled(true);

		setTitle(getResources().getString(R.string.title_activity_find_courses));
		
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		spinner2 = (Spinner) findViewById(R.id.spinner2);

		// setto la lista iniziale negli spinner
		List<String> initialList = new ArrayList<String>();
		initialList.add(new String("Tutto"));
		ArrayAdapter<String> adapterInitialList = new ArrayAdapter<String>(
				FindHomeActivity.this, R.layout.list_studymate_row_list_simple,
				initialList);

		spinner1.setAdapter(adapterInitialList);
		spinner2.setAdapter(adapterInitialList);

		@SuppressWarnings("unused")
		final Activity currentAct = this;

		findDepHandler = (FindDepartmentsHandler) new FindDepartmentsHandler(
				getApplicationContext(), spinner1, spinner2, this).execute();

		searchTV = (MultiAutoCompleteTextView) findViewById(R.id.multiAutoCompleteTextView1);
		searchTV.addTextChangedListener(FindHomeActivity.this);
		
		/*
		 * adapter = new ArrayAdapter<String>(FindHomeActivity.this,
		 * android.R.layout.simple_dropdown_item_1line,
		 * R.array.drop_down_spinner_singlecourses_IT);
		 * 
		 * searchTV.setAdapter(adapter);
		 */

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
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
			intentSearch.putExtra("department", findDepHandler.getDepartSelected());
			intentSearch.putExtra("courseDegree", FindCoursesDegreeHandler.corsoLaureaSelected);
			MultiAutoCompleteTextView textV = (MultiAutoCompleteTextView) findViewById(R.id.multiAutoCompleteTextView1);
			intentSearch.putExtra("course", textV.getText().toString());
			startActivity(intentSearch);
			return true;
		default:
			return super.onOptionsItemSelected(item);

		}
	}

	@Override
	public void afterTextChanged(Editable arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		TextView textSearch = (TextView) findViewById(R.id.textViewLabelCerca);
		TextView textSearching = (TextView) findViewById(R.id.textViewLabelCercando);
		textSearching.setText(" " + searchTV.getText().toString());
		textSearch.setText(R.string.find_label_searchingnow);

	}
}
