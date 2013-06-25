package smartcampus.android.template.standalone;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class MyAgendaActivity extends SherlockFragmentActivity {
	public int agendaState;

	// 0 = elenco base
	// 1 = overview filter
	// 2 = detail of event
	// 3 = detail of event for course
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		agendaState = 0;
		AddEvent4coursesActivity.state = 1;
		// setContentView(R.layout.activity_my_agenda);
		final ActionBar ab = getSupportActionBar();
		ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		ab.setHomeButtonEnabled(true);
		ab.setDisplayHomeAsUpEnabled(true);

		/** TabHost will have Tabs */
		String tab1_txt = getResources().getString(R.string.tab_home);
		String tab2_txt = getResources().getString(R.string.tab_courses);

		Tab tab1 = ab
				.newTab()
				.setText(tab1_txt)
				.setTabListener(
						new TabListener<OverviewFragment>(this, "tab1",
								OverviewFragment.class));
		ab.addTab(tab1);

		Tab tab2 = ab
				.newTab()
				.setText(tab2_txt)
				.setTabListener(
						new TabListener<CorsiFragment>(this, "tab2",
								CorsiFragment.class));
		ab.addTab(tab2);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getSupportMenuInflater();
		if (agendaState == 0) {
			if (AddEvent4coursesActivity.state == 0){
				inflater.inflate(R.menu.add_event, menu);
				AddEvent4coursesActivity.state = 1;
			}
			else
			inflater.inflate(R.menu.agenda, menu);
		}
		if (agendaState == 1) {
			inflater.inflate(R.menu.add_event, menu);
			// agendaState = false;
		}
		if ((agendaState == 2) || (agendaState == 3)){
			inflater.inflate(R.menu.det_event, menu);
			// agendaState = false;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onBackPressed() {
		if (agendaState == 3) {
			agendaState = 1;
		}
		if (agendaState == 2) {
			agendaState = 0;
		}
//		if (agendaState == 1) {
//			agendaState = 0;
//
//		}
		invalidateOptionsMenu();
		super.onBackPressed();
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intentHome = new Intent(MyAgendaActivity.this,
					MyUniActivity.class);
			intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intentHome);
			return true;
		case R.id.add_event:
			Intent intentEvent = new Intent(MyAgendaActivity.this,
					AddEventActivity.class);
			intentEvent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intentEvent);
			return true;
		case R.id.menu_add_note:

			AlertDialog.Builder alertNote = new AlertDialog.Builder(
					MyAgendaActivity.this);
			final EditText inputNote = new EditText(MyAgendaActivity.this);
			alertNote.setView(inputNote);
			alertNote.setTitle("Inserisci nota");
			inputNote.setHint("Inserisci la nota qui...");
			alertNote.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							// Editable value = input.getText();
							Toast.makeText(getApplicationContext(), "Nota...",
									Toast.LENGTH_SHORT).show();
							// e.printStackTrace();
						}
					});
			alertNote.show();

			return true;
		case R.id.menu_add_notification:

			AlertDialog.Builder alertnotification = new AlertDialog.Builder(
					MyAgendaActivity.this);
			final EditText inputNotification = new EditText(
					MyAgendaActivity.this);
			alertnotification.setView(inputNotification);
			alertnotification.setTitle("Invia Segnalazione");
			inputNotification.setHint("");
			alertnotification.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							// Editable value = input.getText();
							Toast.makeText(getApplicationContext(),
									"Notifica...", Toast.LENGTH_SHORT).show();
							// e.printStackTrace();
						}
					});
			alertnotification.show();
			return true;
		case R.id.menu_share:
			return true;
		case R.id.menu_modify_event:
			Intent intentEvent2 = new Intent(MyAgendaActivity.this,
					AddEventActivity.class);
			intentEvent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intentEvent2);
			return true;
		case R.id.menu_delete_event:
			return true;
		case R.id.menu_add_event_4_course:
			Intent intentEventAddEvent = new Intent(MyAgendaActivity.this,
					AddEvent4coursesActivity.class);
			intentEventAddEvent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intentEventAddEvent);
			return true;
		case R.id.menu_add_rating:
			
			Intent intentAddRating = new Intent(MyAgendaActivity.this,
					AddRateActivity.class);
			intentAddRating.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intentAddRating);
			return true;
			
			
			
//			AlertDialog.Builder alert = new AlertDialog.Builder(
//					MyAgendaActivity.this);
//			LayoutInflater inflater = getLayoutInflater();
//			View dialoglayout = inflater.inflate(R.layout.dialog_layout,
//					(ViewGroup) getCurrentFocus());
//
//			alert.setView(dialoglayout);
//			alert.setTitle("Esprimi un giudizio");
//			alert.setPositiveButton("OK",
//					new DialogInterface.OnClickListener() {
//						public void onClick(DialogInterface dialog, int which) {
//							// Editable value = input.getText();
//							Toast.makeText(getApplicationContext(), "rating",
//									Toast.LENGTH_SHORT).show();
//							// e.printStackTrace();
//						}
//					});
//			alert.show();
		default:
			return super.onOptionsItemSelected(item);

		}
	}

	public int isAgendaState() {
		return agendaState;
	}

	public void setAgendaState(int agendaState) {
		this.agendaState = agendaState;
	}
	
//	@Override
//	protected void onResume() {
//		// TODO Auto-generated method stub
//		invalidateOptionsMenu();
//		super.onResume();
//	}
//
//	@Override
//	protected void onRestart() {
//		// TODO Auto-generated method stub
//		invalidateOptionsMenu();
//		super.onRestart();
//	}
}
