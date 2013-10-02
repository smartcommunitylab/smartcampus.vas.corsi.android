package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import smartcampus.android.template.standalone.R;
import android.os.Bundle;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import eu.trentorise.smartcampus.android.studyMate.models.AttivitaStudio;

public class ShowImpegnoGDS extends SherlockFragmentActivity {

	private AttivitaStudio contextualAttivitaStudio;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.show_impegno_gds);
		/*
		 * recupero contextualAttivitastudio da contextualcollection
		 */
		if (!MyApplication.getContextualCollection().isEmpty()) {
			contextualAttivitaStudio = (AttivitaStudio) MyApplication
					.getContextualCollection().get(0);
		}

		Toast.makeText(MyApplication.getAppContext(),
				contextualAttivitaStudio.getOggetto(), Toast.LENGTH_SHORT)
				.show();
	}

}
