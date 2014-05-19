package eu.trentorise.smartcampus.android.studyMate.gruppi_studio;

import it.smartcampuslab.studymate.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;

import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.android.studyMate.models.GruppoDiStudio;
import eu.trentorise.smartcampus.android.studyMate.start.MyUniActivity;
import eu.trentorise.smartcampus.android.studyMate.utilities.SmartUniDataWS;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;

public class Lista_GDS_activity extends SherlockFragmentActivity {

	private ArrayList<GruppoDiStudio> user_gds_list = new ArrayList<GruppoDiStudio>();
	private ProtocolCarrier mProtocolCarrier;
	public String body;

	@Override
	protected void onResume() {
		super.onResume();
		setContentView(R.layout.lista_gds_activity);
		ActionBar actionbar = getSupportActionBar();
		actionbar.setTitle(R.string.my_gds);
		actionbar.setLogo(R.drawable.gruppistudio_icon_white);
		actionbar.setHomeButtonEnabled(true);
		actionbar.setDisplayHomeAsUpEnabled(true);

		// codice per sistemare l'actionoverflow
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception ex) {
			// Ignore
		}
		GetMyCds getCds = new GetMyCds(Lista_GDS_activity.this);
		getCds.execute();

		FragmentTransaction ft = Lista_GDS_activity.this
				.getSupportFragmentManager().beginTransaction();
		Fragment fragment = new ViewGruppiList_Fragment();
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		ft.replace(R.id.list_container, fragment);
		ft.addToBackStack(null);
		ft.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.lista__gds_activity, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent goToMyUniActivity = new Intent(getApplicationContext(),
				MyUniActivity.class);
		goToMyUniActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(goToMyUniActivity);

	}

	@Override
	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home: {
			onBackPressed();
			return super.onOptionsItemSelected(item);
		}
		case R.id.action_iscriviti_nuovo_gruppo: {
			Intent intent = new Intent(getApplicationContext(),
					RicercaGruppiGenerale_activity.class);
			startActivity(intent);
			return super.onOptionsItemSelected(item);
		}

		case R.id.action_crea_gruppo: {
			Intent intent = new Intent(getApplicationContext(),
					Crea_GDS_activity.class);
			startActivity(intent);
			return super.onOptionsItemSelected(item);
		}

		default:
			return super.onOptionsItemSelected(item);
		}

	}

	public ArrayList<GruppoDiStudio> getUser_gds_list() {
		return user_gds_list;
	}

	private class GetMyCds extends AsyncTask<Void, Void, List<GruppoDiStudio>> {
		Context taskcontext;
		public ProgressDialog pd;

		public GetMyCds(Context taskcontext) {
			super();
			this.taskcontext = taskcontext;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd = new ProgressDialog(taskcontext);
			pd = ProgressDialog.show(taskcontext,
					getResources().getString(R.string.loading_gds), "");
		}

		private List<GruppoDiStudio> getMineGDS() {
			ArrayList<GruppoDiStudio> retval = null;
			mProtocolCarrier = new ProtocolCarrier(Lista_GDS_activity.this,
					SmartUniDataWS.TOKEN_NAME);

			MessageRequest request = new MessageRequest(
					SmartUniDataWS.URL_WS_SMARTUNI,
					SmartUniDataWS.GET_WS_MY_GDS);
			request.setMethod(Method.GET);

			MessageResponse response;
			try {
				response = mProtocolCarrier
						.invokeSync(request, SmartUniDataWS.TOKEN_NAME,
								MyUniActivity.getAuthToken());

				if (response.getHttpStatus() == 200) {
					body = response.getBody();
					retval = (ArrayList<GruppoDiStudio>) Utils
							.convertJSONToObjects(body, GruppoDiStudio.class);
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
			return retval;
		}

		@Override
		protected List<GruppoDiStudio> doInBackground(Void... params) {
			// user_gds_list.clear();
			List<GruppoDiStudio> responselist = getMineGDS();
			// if (responselist != null) {
			// for (GruppoDiStudio gds : responselist) {
			// user_gds_list.add(gds);
			// }
			// }
			return responselist;// user_gds_list;
		}

		@Override
		protected void onPostExecute(List<GruppoDiStudio> result) {
			super.onPostExecute(result);
			pd.dismiss();
			if (result == null) {
				Toast.makeText(getApplicationContext(),
						getResources().getString(R.string.dialog_error),
						Toast.LENGTH_SHORT).show();
				onBackPressed();
			} else {
				user_gds_list.clear();
				for (GruppoDiStudio gds : result) {
					user_gds_list.add(gds);
				}
				// se la user_gds_list è vuota proponiamo all'utente di fare
				// qlcs..
				TextView tv = (TextView) findViewById(R.id.suggerimento_lista_vuota);
				if (user_gds_list.isEmpty() || user_gds_list == null) {
					tv.setText(getResources().getString(R.string.no_gds));
				} else {
					tv.setVisibility(View.GONE);
				}
				supportInvalidateOptionsMenu();
			}
		}
	}
}
