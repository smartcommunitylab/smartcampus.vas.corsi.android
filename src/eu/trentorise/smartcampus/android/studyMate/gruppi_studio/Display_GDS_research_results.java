package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import java.util.ArrayList;

import smartcampus.android.template.standalone.R;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
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

		TextView showactivefilter = (TextView) findViewById(R.id.textView_gdsSearchFilter);
		String text = (String) showactivefilter.getText();
		showactivefilter.setText(text + "\n materia: " + materia_filter
				+ "\n nome gruppo: " + nome_gruppo_filter);

		ArrayList<GruppoDiStudio> gds_ammissibili = find_gds_ammissibili(
				universo_gds, materia_filter, nome_gruppo_filter,
				nomi_studenti_filter);

		ListView results_list = (ListView) findViewById(R.id.searchresults_gds_list);
		Adapter_gds_to_list adapter = new Adapter_gds_to_list(
				getApplicationContext(), R.id.searchresults_gds_list,
				universo_gds);// attenzione! al posto di universo_gds si
								// dovrebbe mettere gds_ammissibili ma finch�
								// non funzia lascio stare
		results_list.setAdapter(adapter);
		results_list.setOnItemClickListener(new OnItemClickListener() {

			// parent The AdapterView where the click happened.
			// view The view within the AdapterView that was clicked (this will
			// be a view provided by the adapter)
			// position The position of the view in the adapter.
			// id The row id of the item that was clicked.
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				Adapter_gds_to_list adpt = (Adapter_gds_to_list) parent
						.getAdapter();
				ArrayList<GruppoDiStudio> entries = adpt.getEntries();
				final GruppoDiStudio selected_gds = entries.get(position);
				AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(
						Display_GDS_research_results.this);
				alertdialogbuilder
						.setTitle("Conferma iscrizione")
						.setMessage(
								"Vuoi iscriverti al gruppo \""
										+ selected_gds.getNome() + "\"?")
						.setPositiveButton("Si",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										dialog.dismiss();
										// occio magheggio deprecato
										MyApplication.getContextualCollection()
												.add(selected_gds);
										Intent intent = new Intent(
												Display_GDS_research_results.this,
												Lista_GDS_activity.class);
										intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
										startActivity(intent);
									}
								})

						.setNegativeButton("No",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										dialog.dismiss();
									}
								});
				AlertDialog alertdialog = alertdialogbuilder.create();
				alertdialog.show();

			}
		});
	}

	public ArrayList<GruppoDiStudio> find_gds_ammissibili(
			ArrayList<GruppoDiStudio> universo_gds, String materia_filter,
			String nome_gruppo_filter, ArrayList<String> nomi_studenti_filter) {
		// andr� fatta questa ricerca nella forma di database con query
		// classiche tipo select * from gruppidistudio where
		// filtro=valore_filtro
		ArrayList<GruppoDiStudio> retval = new ArrayList<GruppoDiStudio>();

		if (!nomi_studenti_filter.isEmpty()) {
			if (nome_gruppo_filter == "Tutti") {
				if (materia_filter == "Tutte") {// (1) caso con qualsiasi
												// materia e
												// qualsiasi nome_gruppo,
												// aggiungi tutti i gds che
												// concordano anche con i nomi
												// studenti selezionati
					for (GruppoDiStudio gds : universo_gds) {
						// controllo consistenza con compagni di corso indicati
						boolean check = true;
						for (String nome_studente : nomi_studenti_filter) {
							check = check
									&& (gds.getMembri().contains(nome_studente));
						}
						if (check)
							retval.add(gds);
					}
					// end(1)
				} else {// (2)caso con materia selezionata ma corso qualsiasi
					for (GruppoDiStudio gds : universo_gds) {

						if (gds.getMateria() == materia_filter) {
							// controllo consistenza con compagni di corso
							// indicati
							boolean check = true;
							for (String nome_studente : nomi_studenti_filter) {
								check = check
										&& (gds.getMembri()
												.contains(nome_studente));
							}
							if (check)
								retval.add(gds);
						}
					}
					// end(2)
				}
			} else {// (3) caso in cui un particolare nome_gruppo � stato
					// selezionato
				GruppoDiStudio selected_gds = null;
				for (GruppoDiStudio gds : universo_gds) {
					if (gds.getNome() == nome_gruppo_filter) {
						selected_gds = gds;
					}
				}
				// controllo consistenza con compagni di corso indicati
				boolean check = true;
				for (String nome_studente : nomi_studenti_filter) {
					check = check
							&& (selected_gds.getMembri()
									.contains(nome_studente));
				}
				if (check)
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