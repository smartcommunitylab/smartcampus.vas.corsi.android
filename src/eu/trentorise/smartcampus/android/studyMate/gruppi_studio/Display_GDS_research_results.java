package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import java.util.ArrayList;

import smartcampus.android.template.standalone.R;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import eu.trentorise.smartcampus.android.studyMate.models.GruppoDiStudio;

public class Display_GDS_research_results extends SherlockFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display_gds_research_results_activity);
		ArrayList<String> nomi_studenti_filter = new ArrayList<String>();
		ArrayList<GruppoDiStudio> universo_gds = new ArrayList<GruppoDiStudio>();

		// recupero oggetti da contextualCoolection
		if (!MyApplication.getContextualCollection().isEmpty())
			nomi_studenti_filter = (ArrayList<String>) MyApplication
					.getContextualCollection().get(0);
		universo_gds = (ArrayList<GruppoDiStudio>) MyApplication
				.getContextualCollection().get(1);
		MyApplication.getContextualCollection().clear();
		// fine magheggi

		Bundle extras = getIntent().getExtras();
		String materia_filter = extras.getString("Selected_materia");
		String nome_gruppo_filter = extras.getString("Selected_nome_gruppo");

		ArrayList<GruppoDiStudio> gds_ammissibili = gds_ammissibili(
				universo_gds, materia_filter, nome_gruppo_filter,
				nomi_studenti_filter);

		ListView results_list = (ListView) findViewById(R.id.searchresults_gds_list);
		Adapter_gds_to_list adapter = new Adapter_gds_to_list(
				getApplicationContext(), R.id.searchresults_gds_list,
				gds_ammissibili);
		results_list.setAdapter(adapter);
	}

	public ArrayList<GruppoDiStudio> gds_ammissibili(
			ArrayList<GruppoDiStudio> universo_gds, String materia_filter,
			String nome_gruppo_filter, ArrayList<String> nomi_studenti_filter) {
		ArrayList<GruppoDiStudio> retval = new ArrayList<GruppoDiStudio>();

		if (nomi_studenti_filter.isEmpty()) {
			if (nome_gruppo_filter == "Tutti") {
				if (materia_filter == "Tutte") {// (1) caso con qualsiasi
												// materia e
												// qualsiasi nome_gruppo
					for (GruppoDiStudio gds : universo_gds) {
						// controllo consistenza con compagni di corso indicati
						// boolean check = true;
						// for (String nome_studente : nomi_studenti_filter) {
						// check = check
						// && (gds.getMembri().contains(nome_studente));
						// }
						// if (check)
						Toast.makeText(getApplicationContext(), gds.getNome(),
								Toast.LENGTH_SHORT).show();
						retval.add(gds);

					}
					// end(1)
				} else {// (2)caso con materia selezionata ma corso qualsiasi
					for (GruppoDiStudio gds : universo_gds) {

						if (gds.getMateria() == materia_filter) {
							// controllo consistenza con compagni di corso
							// indicati
							// boolean check = true;
							// for (String nome_studente : nomi_studenti_filter)
							// {
							// check = check
							// && (gds.getMembri()
							// .contains(nome_studente));
							// }
							// if (check)
							Toast.makeText(getApplicationContext(),
									gds.getNome(), Toast.LENGTH_SHORT).show();
							retval.add(gds);
						}
					}
					// end(2)
				}
			} else {// (3) caso in cui un particolare nome_gruppo è stato
					// selezionato
				GruppoDiStudio selected_gds = new GruppoDiStudio();

				for (GruppoDiStudio gds : universo_gds) {
					if (gds.getNome() == nome_gruppo_filter)
						selected_gds = gds;
				}
				// controllo consistenza con compagni di corso indicati
				// boolean check = true;
				// for (String nome_studente : nomi_studenti_filter) {
				// check = check
				// && (selected_gds.getMembri()
				// .contains(nome_studente));
				// }
				// if (check)
				Toast.makeText(getApplicationContext(), selected_gds.getNome(),
						Toast.LENGTH_SHORT).show();
				retval.add(selected_gds);
				// end(3)
			}

		}

		for (GruppoDiStudio gds : retval) {
			Toast.makeText(getApplicationContext(), gds.getNome(),
					Toast.LENGTH_SHORT).show();
		}
		return retval;

	}
}