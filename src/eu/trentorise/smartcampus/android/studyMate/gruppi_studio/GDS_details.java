package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import smartcampus.android.template.standalone.R;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;

import eu.trentorise.smartcampus.android.studyMate.models.GruppoDiStudio;

public class GDS_details extends SherlockActivity {
	private GruppoDiStudio contextualGDS;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// retrieving contextualgds from general collector
		if (!MyApplication.getContextualCollection().isEmpty()) {
			contextualGDS = (GruppoDiStudio) MyApplication
					.getContextualCollection().get(0);
			MyApplication.getContextualCollection().clear();
		}

		setContentView(R.layout.gds_detail_activity);
		// customize layout
		setContentView(R.layout.gds_detail_activity);
		ActionBar actionbar = getSupportActionBar();
		actionbar.setTitle(contextualGDS.getNome());
		actionbar.setLogo(R.drawable.gruppistudio_icon_white);
		actionbar.setHomeButtonEnabled(true);
		actionbar.setDisplayHomeAsUpEnabled(true);

		// retrieving graphics from activity_layout
		ImageView logo_gds = (ImageView) findViewById(R.id.iv_logo_detail);
		TextView nome_gds = (TextView) findViewById(R.id.tv_nome_gds_detail);
		TextView materia_gds = (TextView) findViewById(R.id.tv_nome_gds_detail);
		Button btn_subscribe_gds = (Button) findViewById(R.id.btn_subscribe_gds);
		ListView participants_gds = (ListView) findViewById(R.id.lv_partecipanti_gds);

		logo_gds.setImageDrawable(contextualGDS.getLogo());
		nome_gds.setText(contextualGDS.getNome());
		materia_gds.setText(contextualGDS.getMateria());
		Students_to_listview_adapter adapter = new Students_to_listview_adapter(
				GDS_details.this, R.id.lv_partecipanti_gds,
				contextualGDS.getMembri());
		participants_gds.setAdapter(adapter);
		btn_subscribe_gds.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(
						GDS_details.this);
				alertdialogbuilder
						.setTitle("Conferma iscrizione")
						.setMessage(
								"Vuoi iscriverti al gruppo \""
										+ contextualGDS.getNome() + "\"?")
						.setPositiveButton("Si",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										dialog.dismiss();
										// some logic here
										Intent intent = new Intent(
												GDS_details.this,
												Lista_GDS_activity.class);
										MyApplication.getContextualCollection()
												.add(contextualGDS);
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

}
