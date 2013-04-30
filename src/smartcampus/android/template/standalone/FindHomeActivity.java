package smartcampus.android.template.standalone;




import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;

public class FindHomeActivity extends Activity implements TextWatcher {
	private Spinner spinner1;
	private Spinner spinner2;
	public String departSelected;
	public String courseSelected;
	public MultiAutoCompleteTextView searchTV;
	public ArrayAdapter<String> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_home);
		ActionBar ab = getActionBar();
		ab.setHomeButtonEnabled(true);
		ab.setDisplayHomeAsUpEnabled(true);
		
		
		spinner1 = (Spinner)findViewById(R.id.spinner1);
		
		
		spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		        departSelected = parent.getItemAtPosition(pos).toString();
		        
		        if(departSelected.toString().equals("Ingegneria e scienza dell informazione")){
		        	Spinner spinner2 = (Spinner)findViewById(R.id.spinner2);
		        	// Create an ArrayAdapter using the string array and a default spinner layout
		        	ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(FindHomeActivity.this,
		        	        R.array.drop_down_spinner_courses_IT, android.R.layout.simple_spinner_item);
		        	// Specify the layout to use when the list of choices appears
		        	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		        	// Apply the adapter to the spinner
		        	spinner2.setAdapter(adapter);
		        }else
		        	if(departSelected.toString().equals("Tutto")){
			        	Spinner spinner2 = (Spinner)findViewById(R.id.spinner2);
			        	// Create an ArrayAdapter using the string array and a default spinner layout
			        	ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(FindHomeActivity.this,
			        	        R.array.drop_down_spinner_courses_all, android.R.layout.simple_spinner_item);
			        	// Specify the layout to use when the list of choices appears
			        	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			        	// Apply the adapter to the spinner
			        	spinner2.setAdapter(adapter);
			        }
		    }
		    public void onNothingSelected(AdapterView<?> parent) {
		    }
		});
	
		spinner2 = (Spinner)findViewById(R.id.spinner2);
		spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		    	courseSelected = parent.getItemAtPosition(pos).toString();
		    }

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		searchTV = (MultiAutoCompleteTextView)findViewById(R.id.multiAutoCompleteTextView1);
		searchTV.addTextChangedListener(FindHomeActivity.this);
		adapter = 
		         new ArrayAdapter<String>(FindHomeActivity.this,android.R.layout.simple_dropdown_item_1line,R.array.drop_down_spinner_singlecourses_IT);
		
		searchTV.setAdapter(adapter);
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_home,menu);
		return true;
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intentHome = new Intent(FindHomeActivity.this, MyUniActivity.class);
			intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intentHome);
			return true;
		case R.id.itemSearchCourses:
			Intent intentSearch = new Intent(FindHomeActivity.this, ResultSearchedActivity.class);
			intentSearch.putExtra("department", departSelected.toString());
			intentSearch.putExtra("courseDegree", courseSelected.toString());
			MultiAutoCompleteTextView textV = (MultiAutoCompleteTextView)findViewById(R.id.multiAutoCompleteTextView1);
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
		TextView textSearch = (TextView)findViewById(R.id.textViewLabelCerca);
		TextView textSearching = (TextView)findViewById(R.id.textViewLabelCercando);
		textSearching.setText(" "+searchTV.getText().toString());
		textSearch.setText(R.string.find_label_searchingnow);
		
	}
}
