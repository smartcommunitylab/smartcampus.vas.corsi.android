package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.android.studyMate.models.GruppoDiStudio;
import it.smartcampuslab.studymate.R;

public class ShowModifyGDSDetails_activity extends SherlockActivity {
	private GruppoDiStudio contextualGDS;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_modify_gdsdetails_activity);
		ActionBar actionbar = getSupportActionBar();
		actionbar.setTitle("Dettagli Gruppo");
		actionbar.setLogo(R.drawable.gruppistudio_icon_white);
		actionbar.setHomeButtonEnabled(true);
		actionbar.setDisplayHomeAsUpEnabled(true);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			contextualGDS = (GruppoDiStudio) extras
					.getSerializable("contextualGDS");

			// setup degli elementi grafici
			AutoCompleteTextView scegli_nome_gds_tv = (AutoCompleteTextView) findViewById(R.id.scegli_nome_gruppo_modifica_gds);
			scegli_nome_gds_tv.setText(contextualGDS.getNome());
		}

	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getSherlock().getMenuInflater();
		inflater.inflate(R.menu.show_modify_gdsdetails_activity, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home: {
			ShowModifyGDSDetails_activity.this.finish();
			return super.onOptionsItemSelected(item);
		}
		case R.id.action_done: {
			// asynctask per salvare le modifiche apportate ad un gruppo di
			// studio
			// MyAsyncTask task = new MyAsyncTask(Crea_GDS_activity.this);
			// task.execute();
			return super.onOptionsItemSelected(item);
		}
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private class ASModificaDettagliGDS extends AsyncTask<Void, Void, Boolean> {

		Context taskcontext;
		public ProgressDialog pd;
		Boolean allright;

		public ASModificaDettagliGDS(Context taskcontext) {
			super();
			this.taskcontext = taskcontext;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(taskcontext);
			pd = ProgressDialog.show(taskcontext, contextualGDS.getNome()
					+ ": salvataggio modifiche in corso", "...");
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			boolean retval = false;
			retval = salvamodifichegruppo();
			return retval;
		}

		Boolean salvamodifichegruppo() {
			Boolean retval = false;
			/*
			 * fare qulcosa via metodi web per salvare le modifiche ad un gruppo
			 */
			Toast.makeText(
					getApplicationContext(),
					"Manca il metodo al backend per salvare le modifiche ad un gruppo!",
					Toast.LENGTH_LONG).show();
			// return retval;
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}

	}
}
