package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.android.studyMate.MyUniActivity;
import eu.trentorise.smartcampus.android.studyMate.R;
import eu.trentorise.smartcampus.android.studyMate.models.AttivitaDidattica;
import eu.trentorise.smartcampus.android.studyMate.models.GruppoDiStudio;
import eu.trentorise.smartcampus.android.studyMate.models.Studente;
import eu.trentorise.smartcampus.android.studyMate.utilities.Constants;
import eu.trentorise.smartcampus.android.studyMate.utilities.SmartUniDataWS;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;

public class GDS_members_activity extends SherlockActivity {
	private GruppoDiStudio contextualGDS;
	private ProtocolCarrier mProtocolCarrier;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle myextras = getIntent().getExtras();
		contextualGDS = (GruppoDiStudio) myextras
				.getSerializable(Constants.GDS_SUBS);

		// questo carica la materia nel gruppo
		new GetRelatedCorsoAS(GDS_members_activity.this, contextualGDS)
				.execute();

		setContentView(R.layout.gds_members_activity);
		// customize layout
		ActionBar actionbar = getSupportActionBar();
		actionbar.setTitle(getResources().getString(R.string.gds_show_users));
		actionbar.setLogo(R.drawable.gruppistudio_icon_white);
		actionbar.setHomeButtonEnabled(true);
		actionbar.setDisplayHomeAsUpEnabled(true);

		// retrieving graphics from activity_layout
		TextView nome_gds = (TextView) findViewById(R.id.tv_nome_gds_detail_members);
		TextView materia_gds = (TextView) findViewById(R.id.tv_materia_gds_detail_members);
		ListView participants_gds = (ListView) findViewById(R.id.lv_partecipanti_gds_members);

		nome_gds.setText(contextualGDS.getNome());
		materia_gds.setText(contextualGDS.getMateria());

		if (contextualGDS.getStudentiGruppo() != null
				&& !contextualGDS.getStudentiGruppo().isEmpty()) {
			Students_to_listview_adapter adapter = new Students_to_listview_adapter(
					GDS_members_activity.this,
					R.id.lv_partecipanti_gds_members,
					((ArrayList<Studente>) contextualGDS.getStudentiGruppo()));
			participants_gds.setAdapter(adapter);
		}

	}

	@Override
	protected void onStart() {

		findViewById(R.id.button_join_gds_members).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						AsyncTabbandonaGruppo task = new AsyncTabbandonaGruppo(
								GDS_members_activity.this, contextualGDS);
						task.execute();

					}
				});
		super.onStart();
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
			GDS_members_activity.this.finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private class GetRelatedCorsoAS extends AsyncTask<Void, Void, Void> {

		Context taskcontext;
		GruppoDiStudio GDS;

		public GetRelatedCorsoAS(Context taskcontext, GruppoDiStudio GDS) {
			super();
			this.taskcontext = taskcontext;
			this.GDS = GDS;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		AttivitaDidattica getRelatedCorso() {
			mProtocolCarrier = new ProtocolCarrier(taskcontext,
					SmartUniDataWS.TOKEN_NAME);

			MessageRequest request = new MessageRequest(
					SmartUniDataWS.URL_WS_SMARTUNI,
					SmartUniDataWS.GET_WS_COURSES_DETAILS(GDS.getCorso()));
			request.setMethod(Method.GET);

			MessageResponse response;
			String body = null;
			try {
				response = mProtocolCarrier
						.invokeSync(request, SmartUniDataWS.TOKEN_NAME,
								MyUniActivity.getAuthToken());

				if (response.getHttpStatus() == 200) {
					body = response.getBody();
				} else {
					return null;
				}
			} catch (ConnectionException e) {
				e.printStackTrace();
			} catch (ProtocolException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (AACException e) {
				e.printStackTrace();
			}
			return Utils.convertJSONToObject(body, AttivitaDidattica.class);

		}

		@Override
		protected Void doInBackground(Void... params) {
			AttivitaDidattica retval = getRelatedCorso();
			if (retval != null) {
				GDS.setMateria(retval.getDescription());
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			TextView materia_gds = (TextView) findViewById(R.id.tv_materia_gds_detail_members);
			materia_gds.setText(GDS.getMateria());
		}

	}

	private class AsyncTabbandonaGruppo extends
			AsyncTask<GruppoDiStudio, Void, Void> {

		@SuppressWarnings("unused")
		Context taskcontext;
		GruppoDiStudio toabandonGDS;

		public AsyncTabbandonaGruppo(Context taskcontext,
				GruppoDiStudio toabandonGDSgds) {
			super();
			this.taskcontext = taskcontext;
			this.toabandonGDS = toabandonGDSgds;
		}

		private boolean abandonGDS(GruppoDiStudio gds_to_abandon) {
			mProtocolCarrier = new ProtocolCarrier(GDS_members_activity.this,
					SmartUniDataWS.TOKEN_NAME);

			MessageRequest request = new MessageRequest(
					SmartUniDataWS.URL_WS_SMARTUNI,
					SmartUniDataWS.POST_ABANDON_GDS);
			request.setMethod(Method.POST);

			MessageResponse response;
			try {
				request.setBody(gds_to_abandon.getId() + "");
				response = mProtocolCarrier
						.invokeSync(request, SmartUniDataWS.TOKEN_NAME,
								MyUniActivity.getAuthToken());

				if (response.getHttpStatus() == 200) {

					return true;

				} else {
					return false;
				}
			} catch (ConnectionException e) {
				e.printStackTrace();
			} catch (ProtocolException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (AACException e) {
				e.printStackTrace();
			}

			return true;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			GDS_members_activity.this.finish();
			Intent intent = new Intent(GDS_members_activity.this,
					Lista_GDS_activity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			Toast.makeText(getApplicationContext(),
					getResources().getString(R.string.gds_unjoin_done),
					Toast.LENGTH_SHORT).show();
			// GDS_members_activity.this.finish();
			// onBackPressed();

		}

		@Override
		protected Void doInBackground(GruppoDiStudio... params) {
			abandonGDS(toabandonGDS);
			return null;
		}

	}
}
