package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.android.studyMate.models.GruppoDiStudio;
import eu.trentorise.smartcampus.android.studyMate.models.Studente;
import eu.trentorise.smartcampus.studymate.R;

public class GDS_Subscription_activity extends SherlockActivity {
	private GruppoDiStudio contextualGDS;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle myextras = getIntent().getExtras();
		contextualGDS = (GruppoDiStudio) myextras
				.getSerializable("gds_to_subscribe");

		setContentView(R.layout.gds_detail_activity);
		// customize layout

		ActionBar actionbar = getSupportActionBar();
		actionbar.setTitle("Iscrizione gruppo di studio");
		actionbar.setLogo(R.drawable.gruppistudio_icon_white);
		actionbar.setHomeButtonEnabled(true);
		actionbar.setDisplayHomeAsUpEnabled(true);

		// retrieving graphics from activity_layout
		@SuppressWarnings("unused")
		ImageView logo_gds = (ImageView) findViewById(R.id.iv_logo_detail);
		TextView nome_gds = (TextView) findViewById(R.id.tv_nome_gds_detail);
		TextView materia_gds = (TextView) findViewById(R.id.tv_materia_gds_detail);
		ListView participants_gds = (ListView) findViewById(R.id.lv_partecipanti_gds);

		// da fare quando si potrà col backend
		// logo_gds.setImageDrawable(contextualGDS.getLogo());
		nome_gds.setText(contextualGDS.getNome());
		materia_gds.setText("" + contextualGDS.getCorso());
		// un po' di roba fake
		ArrayList<Studente> membriGDS = new ArrayList<Studente>();
		Studente s1 = new Studente();
		s1.setNome("Pinco");
		s1.setCognome("Pallino");
		s1.setAnno_corso("2");
		Studente s2 = new Studente();
		s2.setNome("Pinco");
		s2.setCognome("Pallino");
		s2.setAnno_corso("2");
		membriGDS.add(s1);
		membriGDS.add(s1);
		// fine roba fake
		Students_to_listview_adapter adapter = new Students_to_listview_adapter(
				GDS_Subscription_activity.this, R.id.lv_partecipanti_gds,
				/* contextualGDS.getMembri() */membriGDS);
		participants_gds.setAdapter(adapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.menu_gds_subscription, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			GDS_Subscription_activity.this.finish();
			return true;
		case R.id.action_subscribe:
			AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(
					GDS_Subscription_activity.this);
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
									dialog.dismiss();
									// some logic here
									MyAsyncTask task = new MyAsyncTask(
											GDS_Subscription_activity.this);
									task.execute();

								}
							})

					.setNegativeButton("No",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
								}
							});
			AlertDialog alertdialog = alertdialogbuilder.create();
			alertdialog.show();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private class MyAsyncTask extends AsyncTask<Void, Void, Void> {

		Context taskcontext;
		public ProgressDialog pd;

		public MyAsyncTask() {
			super();
			// TODO Auto-generated constructor stub
		}

		public MyAsyncTask(Context taskcontext) {
			super();
			this.taskcontext = taskcontext;
		}

		public void attendi() {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(taskcontext);
			pd = ProgressDialog.show(taskcontext, "Primo Progress Dialog",
					"Caricamento...");
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result == null) {

				Toast.makeText(GDS_Subscription_activity.this,
						"Ops! C'è stato un errore...", Toast.LENGTH_SHORT)
						.show();
				GDS_Subscription_activity.this.finish();
			}
			pd.dismiss();
			Intent intent = new Intent(GDS_Subscription_activity.this,
					Lista_GDS_activity.class);

			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			attendi();
			return null;
		}

	}
}
