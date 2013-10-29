package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewConfiguration;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.android.studyMate.R;
import eu.trentorise.smartcampus.android.studyMate.models.AttivitaStudio;
import eu.trentorise.smartcampus.android.studyMate.models.ChatObject;
import eu.trentorise.smartcampus.android.studyMate.models.GruppoDiStudio;

public class Overview_GDS extends SherlockFragmentActivity {

	private GruppoDiStudio contextualGDS = new GruppoDiStudio();
	private ArrayList<ChatObject> contextualForum = new ArrayList<ChatObject>();
	private ArrayList<AttivitaStudio> contextualListaImpegni = new ArrayList<AttivitaStudio>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.overview_gds_waitingforforum_layout);

		// codice per sistemare l'actionoverflow
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception ex) {
			// Ignore
		}

		/*
		 * Come da politica di utilizzo del contextualcollection ogni volta che
		 * recupero un oggetto mi aspetto di trovarlo in posizione 0, appena
		 * recupreato il tale oggetto mi preoccupo di pulire il
		 * contextualcollection (a overview ecc ci posso arrivare partendo da:
		 * lista dei gruppi di studio, oppure terminando l'aggiunta di un
		 * impegno
		 */
		if (!MyApplication.getContextualCollection().isEmpty()) {
			Object obj = MyApplication.getContextualCollection().get(0);
			if (obj instanceof GruppoDiStudio) {
				contextualGDS = (GruppoDiStudio) obj;
				contextualForum = contextualGDS.getForum();
				contextualListaImpegni = contextualGDS.getAttivita_studio();
			} else if (obj instanceof AttivitaStudio) {
				/*
				 * todo here
				 */

				contextualListaImpegni.add((AttivitaStudio) obj);
			}

			MyApplication.getContextualCollection().clear();

		}

		final ActionBar ab = getSupportActionBar();
		// ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		ab.setTitle(contextualGDS.getNome());
		ab.setLogo(R.drawable.gruppistudio_icon_white);
		ab.setHomeButtonEnabled(true);
		ab.setDisplayHomeAsUpEnabled(true);

		FragmentTransaction ft = this.getSupportFragmentManager()
				.beginTransaction();
		Fragment fragment = new Impegni_Fragment();
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		ft.replace(R.id.impegni_fragment_container, fragment);
		ft.addToBackStack(null);
		ft.commit();

		// /** TabHost will have Tabs */
		// String tab1_txt = "Impegni";
		// String tab2_txt = "Forum";
		//
		// // tab1
		//
		// Tab tab1 = ab
		// .newTab()
		// .setText(tab1_txt)
		// .setTabListener(
		// new TabListener<Impegni_Fragment>(this, "tab1",
		// Impegni_Fragment.class));
		// ab.addTab(tab1);
		//
		// // tab2
		// Tab tab2 = ab
		// .newTab()
		// .setText(tab2_txt)
		// .setTabListener(
		// new TabListener<Forum_fragment>(this, "tab2",
		// Forum_fragment.class));
		// ab.addTab(tab2);

	}

	@Override
	public void onBackPressed() {
		Overview_GDS.this.finish();
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// Bundle args = arg2.getExtras();
		Toast.makeText(
				Overview_GDS.this,
				"resultcode= " + arg1 + " # requestCode= " + arg0
						+ "\n mi hanno ritornato" + " elem", Toast.LENGTH_SHORT)
				.show();
		super.onActivityResult(arg0, arg1, arg2);
	}

	// @Override
	// protected void onActivityResult(int requestCode, int resultCode, Intent
	// data) {
	// if (requestCode == MyApplication.PICK_FILE_FROM_PHONE_MEMORY) {
	// Bundle args = data.getExtras();
	// Toast.makeText(
	// Overview_GDS.this,
	// "resultcode=# " + resultCode + " #" + " mi hanno ritornato"
	// + args.size() + " elem", Toast.LENGTH_SHORT).show();
	// }
	// Bundle args = data.getExtras();
	// Toast.makeText(
	// Overview_GDS.this,
	// "resultcode= " + resultCode + " # requestCode= " + resultCode
	// + "\n mi hanno ritornato" + args.size() + " elem",
	// Toast.LENGTH_SHORT).show();
	// super.onActivityResult(requestCode, resultCode, data);
	// }

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.overview__gd, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home: {
			Overview_GDS.this.finish();
			return true;
		}
		case R.id.action_abbandona_gruppo:
			Toast.makeText(Overview_GDS.this, "abbandona gruppo",
					Toast.LENGTH_SHORT).show();
			return true;
		case R.id.action_modifica_gruppo:
			Toast.makeText(Overview_GDS.this, "modifica dettagli gruppo",
					Toast.LENGTH_SHORT).show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	public GruppoDiStudio getContextualGDS() {
		return contextualGDS;
	}

	public void setContextualGDS(GruppoDiStudio contextualGDS) {
		this.contextualGDS = contextualGDS;
	}

	public ArrayList<ChatObject> getContextualForum() {
		return contextualForum;
	}

	public void setContextualForum(ArrayList<ChatObject> contextualForum) {
		this.contextualForum = contextualForum;
	}

	public ArrayList<AttivitaStudio> getContextualListaImpegni() {
		return contextualListaImpegni;
	}

	public void setContextualListaImpegni(
			ArrayList<AttivitaStudio> contextualListaImpegni) {
		this.contextualListaImpegni = contextualListaImpegni;
	}

}
