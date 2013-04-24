package smartcampus.android.template.standalone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class MyUniActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_uni);
		
		
		findViewById(R.id.my_agenda_btn).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MyUniActivity.this,
								MyAgendaActivity.class);
						MyUniActivity.this.startActivity(intent);
					}
				});
		
		findViewById(R.id.find_courses_btn).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MyUniActivity.this,
								FindCoursesActivity.class);
						MyUniActivity.this.startActivity(intent);
					}
				});
		
		findViewById(R.id.phl_btn).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MyUniActivity.this,
								PHLActivity.class);
						MyUniActivity.this.startActivity(intent);
					}
				});
		
		findViewById(R.id.notices_btn).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MyUniActivity.this,
								NoticesActivity.class);
						MyUniActivity.this.startActivity(intent);
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_uni, menu);
		return true;
	}

}
