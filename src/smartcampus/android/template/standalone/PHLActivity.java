package smartcampus.android.template.standalone;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PHLActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phl);
		
		String[] corsi = getResources().getStringArray(R.array.Corsi);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, corsi);
		ListView listView = (ListView) findViewById(R.id.listViewCorsiPHL);
		listView.setAdapter(adapter);
		
		String[] corsiInt = getResources().getStringArray(R.array.CorsiInteresse);
		
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, corsiInt);
		ListView listView2 = (ListView) findViewById(R.id.listViewCorsiInteressePHL);
		listView2.setAdapter(adapter2);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.phl, menu);
		return true;
		
	}

}
