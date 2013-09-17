package smartcampus.android.studyMate.gruppi_studio;

import java.util.ArrayList;

import smartcampus.android.template.standalone.R;
import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.example.model_classes.AttivitaStudio;
import com.example.model_classes.ChatObject;
import com.example.model_classes.GruppoDiStudio;

import eu.trentorise.smartcampus.studyMate.utilities.TabListener;

public class Overview_GDS extends SherlockFragmentActivity {

	private GruppoDiStudio contextualGDS;
	private ArrayList<ChatObject> contextualForum;
	private ArrayList<AttivitaStudio> contextualListaImpegni;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/*
		 * Come da politica di utilizzo del contextualcollection ogni volta che
		 * recupero un oggetto mi aspetto di trovarlo in posizione 0, appena
		 * recupreato il tale oggetto mi preoccupo di pulire il
		 * contextualcollection
		 */
		contextualGDS = (GruppoDiStudio) MyApplication
				.getContextualCollection().get(0);
		MyApplication.getContextualCollection().clear();
		contextualForum = contextualGDS.getForum();
		contextualListaImpegni = contextualGDS.getAttivita_studio();

		final ActionBar ab = getSupportActionBar();
		ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		ab.setTitle(contextualGDS.getNome());
		// ab.setHomeButtonEnabled(true);
		// ab.setDisplayHomeAsUpEnabled(true);

		/** TabHost will have Tabs */
		String tab1_txt = "Impegni";
		String tab2_txt = "Forum";

		// tab1

		Tab tab1 = ab
				.newTab()
				.setText(tab1_txt)
				.setTabListener(
						new TabListener<Impegni_Fragment>(this, "tab1",
								Impegni_Fragment.class));
		ab.addTab(tab1);

		// tab2
		Tab tab2 = ab
				.newTab()
				.setText(tab2_txt)
				.setTabListener(
						new TabListener<Forum_fragment>(this, "tab2",
								Forum_fragment.class));
		ab.addTab(tab2);

	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		// TODO Auto-generated method stub
		
		return super.onCreateOptionsMenu(menu);
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
