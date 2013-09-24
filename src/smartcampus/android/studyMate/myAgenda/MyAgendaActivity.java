package smartcampus.android.studyMate.myAgenda;

import smartcampus.android.template.standalone.R;
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

import eu.trentorise.smartcampus.studyMate.utilities.CoursesHandler;
import eu.trentorise.smartcampus.studyMate.utilities.TabListener;

public class MyAgendaActivity extends SherlockFragmentActivity {

	public enum MenuKind {
		BASE_MENU, OVERVIEW_FILTERED_BY_COURSE, DETAIL_OF_EVENT, DETAIL_OF_EVENT_FOR_COURSE
	}

	public enum ChildActivity {
		ADD_EVENT_FOR_COURSES, ADD_RATING, NONE
	}

	private MenuKind agendaState;
	private ChildActivity mystate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		agendaState = MenuKind.BASE_MENU;
		mystate = ChildActivity.NONE;
		final ActionBar ab = getSupportActionBar();
		ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		setTitle(getResources().getString(R.string.title_activity_my_agenda));
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
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		if (agendaState == MenuKind.BASE_MENU) {
			setTitle("Agenda");
			if (mystate == ChildActivity.ADD_EVENT_FOR_COURSES) {
				inflater.inflate(R.menu.add_event, menu);
			} else if (mystate == ChildActivity.ADD_RATING) {
				inflater.inflate(R.menu.add_event, menu);
			} else {
				inflater.inflate(R.menu.agenda, menu);
			}
		} else if (agendaState == MenuKind.OVERVIEW_FILTERED_BY_COURSE) {
			inflater.inflate(R.menu.add_event, menu);
		}
		if ((agendaState == MenuKind.DETAIL_OF_EVENT)
				|| (agendaState == MenuKind.DETAIL_OF_EVENT_FOR_COURSE)) {
			inflater.inflate(R.menu.det_event, menu);
		}
		mystate = ChildActivity.NONE;
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onBackPressed() {
		if (agendaState == MenuKind.DETAIL_OF_EVENT_FOR_COURSE) {
			agendaState = MenuKind.OVERVIEW_FILTERED_BY_COURSE;
		}
		if (agendaState == MenuKind.DETAIL_OF_EVENT) {
			agendaState = MenuKind.BASE_MENU;

		}
		supportInvalidateOptionsMenu();
		super.onBackPressed();
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
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
							Toast.makeText(getApplicationContext(), "Nota...",
									Toast.LENGTH_SHORT).show();

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
			Toast.makeText(getApplicationContext(), "Coming soon!",
					Toast.LENGTH_SHORT).show();
			// Intent intentEvent2 = new Intent(MyAgendaActivity.this,
			// AddEventActivity.class);
			// intentEvent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			// startActivity(intentEvent2);

			return true;
		case R.id.menu_delete_event:
			return true;
		case R.id.menu_add_event_4_course:
			mystate = ChildActivity.ADD_EVENT_FOR_COURSES;
			Intent intentEventAddEvent = new Intent(MyAgendaActivity.this,
					AddEvent4coursesActivity.class);
			intentEventAddEvent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intentEventAddEvent);
			return true;
		case R.id.menu_add_rating:
			mystate = ChildActivity.ADD_RATING;
			Intent intentAddRating = new Intent(MyAgendaActivity.this,
					AddRateActivity.class);
			CoursesHandler.corsoSelezionato.getNome();
			intentAddRating.putExtra("corso",
					CoursesHandler.corsoSelezionato.getNome());
			intentAddRating.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intentAddRating);
			return true;
		default:
			return super.onOptionsItemSelected(item);

		}
	}

	public MenuKind isAgendaState() {
		return agendaState;
	}

	public void setAgendaState(MenuKind agendaState) {
		this.agendaState = agendaState;
	}
}
