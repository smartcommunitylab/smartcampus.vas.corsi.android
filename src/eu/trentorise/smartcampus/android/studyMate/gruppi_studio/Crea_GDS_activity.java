package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.android.studyMate.R;

public class Crea_GDS_activity extends SherlockActivity {
	AutoCompleteTextView tv_materia;
	AutoCompleteTextView tv_nome_gds;
	AutoCompleteTextView tv_invitati;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crea__gds_activity);

		ActionBar actionbar = getSupportActionBar();
		actionbar.setTitle("Nuovo Gruppo");
		actionbar.setLogo(R.drawable.gruppistudio_icon_white);
		actionbar.setHomeButtonEnabled(true);
		actionbar.setDisplayHomeAsUpEnabled(true);

		tv_materia = (AutoCompleteTextView) findViewById(R.id.scegli_materia);
		tv_nome_gds = (AutoCompleteTextView) findViewById(R.id.scegli_nome_gruppo);
		tv_invitati = (AutoCompleteTextView) findViewById(R.id.invita_compagni_gds);

	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		MenuInflater inflater = getSherlock().getMenuInflater();
		inflater.inflate(R.menu.crea__gds_activity, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home: {
			Crea_GDS_activity.this.finish();
		}
		case R.id.action_done: {
			String materia = tv_materia.getText().toString();
			String nome = tv_nome_gds.getText().toString();

			MyAsyncTask task = new MyAsyncTask(Crea_GDS_activity.this);
			task.execute();

		}

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
			pd.dismiss();
			Intent intent = new Intent(Crea_GDS_activity.this,
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
