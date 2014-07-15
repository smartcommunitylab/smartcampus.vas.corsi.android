package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import eu.trentorise.smartcampus.android.studyMate.R;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.android.studyMate.models.AttivitaDidattica;
import eu.trentorise.smartcampus.android.studyMate.models.GruppoDiStudio;
import eu.trentorise.smartcampus.android.studyMate.models.Studente;
import eu.trentorise.smartcampus.android.studyMate.start.MyUniActivity;
import eu.trentorise.smartcampus.android.studyMate.utilities.Constants;
import eu.trentorise.smartcampus.android.studyMate.utilities.SmartUniDataWS;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;

public class GDS_Subscription_activity extends SherlockActivity {
	private GruppoDiStudio contextualGDS;
	private ProtocolCarrier mProtocolCarrier;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle myextras = getIntent().getExtras();
		contextualGDS = (GruppoDiStudio) myextras
				.getSerializable(Constants.GDS_SUBS);

		// questo carica la materia nel gruppo
		new GetRelatedCorsoAS(GDS_Subscription_activity.this, contextualGDS)
				.execute();

		setContentView(R.layout.gds_detail_activity);
		// customize layout
		ActionBar actionbar = getSupportActionBar();
		actionbar.setTitle(R.string.iscr_group_stud);
		actionbar.setLogo(R.drawable.gruppistudio_icon_white);
		actionbar.setHomeButtonEnabled(true);
		actionbar.setDisplayHomeAsUpEnabled(true);

		// retrieving graphics from activity_layout
		// ImageView logo_gds = (ImageView) findViewById(R.id.iv_logo_detail);
		TextView nome_gds = (TextView) findViewById(R.id.tv_nome_gds_detail);
		TextView materia_gds = (TextView) findViewById(R.id.tv_materia_gds_detail);
		ListView participants_gds = (ListView) findViewById(R.id.lv_partecipanti_gds);

		nome_gds.setText(contextualGDS.getNome());
		materia_gds.setText(contextualGDS.getMateria());

		if (contextualGDS.getStudentiGruppo() != null
				&& !contextualGDS.getStudentiGruppo().isEmpty()) {
			Students_to_listview_adapter adapter = new Students_to_listview_adapter(
					GDS_Subscription_activity.this, R.id.lv_partecipanti_gds,
					((ArrayList<Studente>) contextualGDS.getStudentiGruppo()));
			participants_gds.setAdapter(adapter);
		}

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
					.setTitle(
							getResources().getString(R.string.sure_group_stud))
					.setMessage(
							getResources().getString(
									R.string.sure_group_stud_message)
									+ " \"" + contextualGDS.getNome() + "\"?")
					.setPositiveButton(R.string.yes,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
									// some logic here
									ASTaskSubscribe task = new ASTaskSubscribe(
											GDS_Subscription_activity.this,
											contextualGDS);
									task.execute();

								}
							})

					.setNegativeButton(R.string.no,
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

	private class ASTaskSubscribe extends AsyncTask<Void, Void, Void> {

		Context taskcontext;
		public ProgressDialog pd;
		private GruppoDiStudio gds_to_subscribe;

		public ASTaskSubscribe(Context taskcontext,
				GruppoDiStudio gds_to_subscribe) {
			super();
			this.taskcontext = taskcontext;
			this.gds_to_subscribe = gds_to_subscribe;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd = new ProgressDialog(taskcontext);
			pd = ProgressDialog.show(taskcontext,
					getResources().getString(R.string.iscr_group_stud_dial)
							+ gds_to_subscribe.getNome(), getResources()
							.getString(R.string.dialog_loading));
		}

		void subscribetogds(GruppoDiStudio gds) {
			mProtocolCarrier = new ProtocolCarrier(taskcontext,
					SmartUniDataWS.TOKEN_NAME);

			MessageRequest request = new MessageRequest(
					SmartUniDataWS.URL_WS_SMARTUNI,
					SmartUniDataWS.POST_ACCEPT_GDS);
			request.setMethod(Method.POST);
			String jsongds = Utils.convertToJSON(gds);
			request.setBody(jsongds);

			MessageResponse response;
			try {
				response = mProtocolCarrier
						.invokeSync(request, SmartUniDataWS.TOKEN_NAME,
								MyUniActivity.getAuthToken());

				if (response.getHttpStatus() == 200) {

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
			return;
		}

		@Override
		protected Void doInBackground(Void... params) {
			subscribetogds(gds_to_subscribe);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			pd.dismiss();
			Intent intent = new Intent(GDS_Subscription_activity.this,
					Lista_GDS_activity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}

	}

	private class GetRelatedCorsoAS extends AsyncTask<Void, Void, Void> {

		Context taskcontext;
		GruppoDiStudio GDS;
		public ProgressDialog pd;

		public GetRelatedCorsoAS(Context taskcontext, GruppoDiStudio GDS) {
			super();
			this.taskcontext = taskcontext;
			this.GDS = GDS;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pd = new ProgressDialog(taskcontext);
			pd = ProgressDialog.show(taskcontext,
					getResources().getString(R.string.loading_gds), "");
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
			TextView materia_gds = (TextView) findViewById(R.id.tv_materia_gds_detail);
			materia_gds.setText(GDS.getMateria());
			pd.dismiss();

		}

	}

}
