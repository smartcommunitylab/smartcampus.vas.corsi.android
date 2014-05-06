<<<<<<< HEAD
package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.android.studyMate.models.AttivitaDidattica;
import eu.trentorise.smartcampus.android.studyMate.models.GruppoDiStudio;
import eu.trentorise.smartcampus.android.studyMate.utilities.Constants;
import eu.trentorise.smartcampus.studymate.R;

public class Display_GDS_research_results extends SherlockFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display_gds_research_results_activity);
		ActionBar actionbar = getSupportActionBar();
		actionbar.setLogo(R.drawable.gruppistudio_icon_white);
		actionbar.setTitle(R.string.iscr_group_stud);
		actionbar.setHomeButtonEnabled(true);
		actionbar.setDisplayHomeAsUpEnabled(true);
		Bundle extras = getIntent().getExtras();
		String materia_filter = extras.getString(Constants.SELECTED_MATERIA);
		@SuppressWarnings("unchecked")
		ArrayList<GruppoDiStudio> possibleGDS = (ArrayList<GruppoDiStudio>) extras
				.getSerializable(Constants.POSSIBLE_GDS);
		@SuppressWarnings({ "unchecked", "unused" })
		ArrayList<AttivitaDidattica> PossibleAttivitaDidattiche = (ArrayList<AttivitaDidattica>) extras
				.getSerializable(Constants.POSSIBLE_ATT);

		String nome_gruppo_filter = extras.getString(Constants.NOME_GRUPPO);

		TextView tv_materia = (TextView) findViewById(R.id.tv_filter_materia);
		tv_materia.setText(materia_filter);
		TextView tv_nomeGruppo = (TextView) findViewById(R.id.tv_filter_nomegruppo);
		tv_nomeGruppo.setText(nome_gruppo_filter);

		ListView results_list = (ListView) findViewById(R.id.searchresults_gds_list);

		Adapter_gds_to_list adapter = new Adapter_gds_to_list(
				getApplicationContext(), R.id.searchresults_gds_list,
				possibleGDS);
		results_list.setAdapter(adapter);
		results_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Adapter_gds_to_list adpt = (Adapter_gds_to_list) parent
						.getAdapter();
				ArrayList<GruppoDiStudio> entries = adpt.getEntries();
				GruppoDiStudio selected_gds = entries.get(position);
				Intent intent = new Intent(Display_GDS_research_results.this,
						GDS_Subscription_activity.class);
				intent.putExtra(Constants.GDS_SUBS, selected_gds);

				startActivity(intent);

			}

		});

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Display_GDS_research_results.this.finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}

