package smartcampus.android.template.standalone;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import eu.trentorise.smartcampus.smartuni.models.CorsoLaurea;
import eu.trentorise.smartcampus.smartuni.models.Dipartimento;
import eu.trentorise.smartcampus.smartuni.utilities.FindCoursesDegreeHandler;
import eu.trentorise.smartcampus.smartuni.utilities.FindDepartmentsHandler;
import eu.trentorise.smartcampus.smartuni.utilities.NotificationHandler;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class FindHomeActivity extends Activity implements TextWatcher {
	private Spinner spinner1;
	private Spinner spinner2;
	public String departSelectedName;
	public String courseSelected;
	public MultiAutoCompleteTextView searchTV;
	public ArrayAdapter<String> adapter;
	public static ProgressDialog pd;
	public List<Dipartimento> listDep = new ArrayList<Dipartimento>();
	public List<CorsoLaurea> listCorLaurea = new ArrayList<CorsoLaurea>();
	Dipartimento departSelected;
	CorsoLaurea corsoLaureaSelected;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_home);
		ActionBar ab = getActionBar();
		ab.setHomeButtonEnabled(true);
		ab.setDisplayHomeAsUpEnabled(true);

		spinner1 = (Spinner) findViewById(R.id.spinner1);
		spinner2 = (Spinner) findViewById(R.id.spinner2);

		new ProgressDialog(FindHomeActivity.this);
		pd = ProgressDialog.show(FindHomeActivity.this,
				"Lista dei dipartimenti", "Caricamento...");

		// setto la lista iniziale negli spinner
		List<String> initialList = new ArrayList<String>();
		initialList.add(new String("Tutto"));

		ArrayAdapter<String> adapterInitialList = new ArrayAdapter<String>(
				FindHomeActivity.this, R.layout.list_studymate_row_list_simple,
				initialList);

		spinner1.setAdapter(adapterInitialList);
		spinner2.setAdapter(adapterInitialList);

		try {
			listDep = new FindDepartmentsHandler(getApplicationContext(),
					spinner1).execute().get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		pd.dismiss();

		spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {

				
				new ProgressDialog(FindHomeActivity.this);
				pd = ProgressDialog.show(FindHomeActivity.this,
						"Lista dei corsi di laurea", "Caricamento...");

				departSelectedName = parent.getItemAtPosition(pos).toString();

				departSelected = new Dipartimento();

				departSelected = listDep.get(pos);

				// Create an ArrayAdapter using the string array and a default
				// spinner layout

				// do the background process or any work that takes
				// time to see progreaa dialog

				try {
					listCorLaurea = new FindCoursesDegreeHandler(
							getApplicationContext(), spinner2, departSelected)
							.execute().get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				pd.dismiss();

			}

			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		// listener spinner corso laurea
		spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {

				courseSelected = parent.getItemAtPosition(pos).toString();

				corsoLaureaSelected = new CorsoLaurea();

				corsoLaureaSelected = listCorLaurea.get(pos);

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		searchTV = (MultiAutoCompleteTextView) findViewById(R.id.multiAutoCompleteTextView1);
		searchTV.addTextChangedListener(FindHomeActivity.this);
		adapter = new ArrayAdapter<String>(FindHomeActivity.this,
				android.R.layout.simple_dropdown_item_1line,
				R.array.drop_down_spinner_singlecourses_IT);

		searchTV.setAdapter(adapter);

		pd.dismiss();
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
			intentSearch.putExtra("department", /* departSelectedName.toString() */
					departSelected);
			intentSearch.putExtra("courseDegree", /* courseSelected.toString() */
					corsoLaureaSelected);
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
