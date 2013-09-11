package smartcampus.android.studyMate.start;

import smartcampus.android.studyMate.finder.FindHomeActivity;
import smartcampus.android.studyMate.gruppi_studio.Lista_GDS_activity;
import smartcampus.android.studyMate.myAgenda.MyAgendaActivity;
import smartcampus.android.studyMate.notices.NoticesActivity;
import smartcampus.android.studyMate.phl.PHLActivity;
import smartcampus.android.template.standalone.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.ac.SCAccessProvider;
import eu.trentorise.smartcampus.ac.embedded.EmbeddedSCAccessProvider;
import eu.trentorise.smartcampus.communicator.CommunicatorConnectorException;
import eu.trentorise.smartcampus.profileservice.BasicProfileService;
import eu.trentorise.smartcampus.profileservice.model.BasicProfile;
import eu.trentorise.smartcampus.pushservice.PushServiceConnector;

//import eu.trentorise.smartcampus.puschservice.PushServiceConnector;

public class MyUniActivity extends Activity {

	/** Logging tag */
	private static final String TAG = "Main";
	// private static final String AUTH_URL =
	// "https://vas-dev.smartcampuslab.it/accesstoken-provider/ac";
	// private static final String AC_SERVICE_ADDR =
	// "https://vas-dev.smartcampuslab.it/acService";
	// private static final String PROFILE_SERVICE_ADDR =
	// "https://vas-dev.smartcampuslab.it";

	public static final String CLIENT_ID = "b8fcb94d-b4cf-438f-802a-c0a560734c88";

	public static final String CLIENT_SECRET = "536560ac-cb74-4e1b-86a1-ef2c06c3313a";

	public static final String APP_ID = "studymate";

	public static final String SERVER_URL = "https://vas-dev.smartcampuslab.it/core.communicator";

	/**
	 * Provides access to the authentication mechanism. Used to retrieve the
	 * token
	 */
	private SCAccessProvider accessProvider = null;
	public static String userAuthToken;
	public static ProgressDialog pd;
	public static BasicProfile bp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_uni);
		// your code here
		new ProgressDialog(MyUniActivity.this);
		pd = ProgressDialog.show(MyUniActivity.this, "Accesso account",
				"Accesso in corso...");

		new LoadUserDataFromACServiceTask().execute();

		accessProvider = new EmbeddedSCAccessProvider();
		try {
			if (!accessProvider.login(this, CLIENT_ID, CLIENT_SECRET, null)) {
				// user is already registered. Proceed requesting the token
				// and the related steps if needed
			}
		} catch (AACException e) {
			Log.e(TAG, "Failed to login: " + e.getMessage());
			// handle the failure, e.g., notify the user and close the app.
		}

		// PushServiceConnector connector = new PushServiceConnector();
		// // //init connector
		// try {
		// System.out.println("token: " + token);
		// connector.init(getApplicationContext(), token);
		// } catch (CommunicatorConnectorException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// try {
		// new PushServiceConnector().init(getApplicationContext(), token);
		// } catch (CommunicatorConnectorException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		findViewById(R.id.my_agenda_btn).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MyUniActivity.this,
								MyAgendaActivity.class);
						MyUniActivity.this.startActivity(intent);
					}
				});

		findViewById(R.id.find_courses_btn).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MyUniActivity.this,
								FindHomeActivity.class);
						MyUniActivity.this.startActivity(intent);
					}
				});

		findViewById(R.id.phl_btn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MyUniActivity.this,
						PHLActivity.class);
				MyUniActivity.this.startActivity(intent);
			}
		});

		findViewById(R.id.notices_btn).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MyUniActivity.this,
								NoticesActivity.class);
						MyUniActivity.this.startActivity(intent);
					}
				});

		findViewById(R.id.gruppi_studio_btn).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MyUniActivity.this,
								Lista_GDS_activity.class);
						MyUniActivity.this.startActivity(intent);
					}
				});

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		new LoadUserDataFromACServiceTask().execute();
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_uni, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.logout:
			try {
				accessProvider.logout(MyUniActivity.this);
				System.out.println(userAuthToken);
			} catch (AACException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
		return false;

		// return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// check the result of the authentication

		if (requestCode == SCAccessProvider.SC_AUTH_ACTIVITY_REQUEST_CODE) {
			if (resultCode == Activity.RESULT_OK) {
				// A&A successful. Proceed requesting the token
				// and the related steps if needed

			} else if (resultCode == Activity.RESULT_CANCELED) {
				// Cancelled by user
			} else {
				// Operation failed for some reason
			}
		}
		// other code here
		super.onActivityResult(requestCode, resultCode, data);
	}

	public class LoadUserDataFromACServiceTask extends
			AsyncTask<Void, Void, BasicProfile> {

		@Override
		protected BasicProfile doInBackground(Void... params) {
			try {
				userAuthToken = accessProvider.readToken(MyUniActivity.this,
						CLIENT_ID, CLIENT_SECRET);
				System.out.println(userAuthToken);
				BasicProfileService service = new BasicProfileService(
						"https://vas-dev.smartcampuslab.it/aac");
				bp = service.getBasicProfile(userAuthToken);
				System.out.println(bp.getName());
				System.out.println("USERID: " + bp.getUserId());
				PushServiceConnector connector = new PushServiceConnector();
				// //init connector
				try {
					System.out.println("token: " + userAuthToken);
					connector.init(getApplicationContext(), userAuthToken,
							APP_ID, SERVER_URL, bp);
				} catch (CommunicatorConnectorException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					new PushServiceConnector().init(getApplicationContext(),
							userAuthToken, APP_ID, SERVER_URL, bp);
				} catch (CommunicatorConnectorException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return bp;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

		}

		@Override
		protected void onPostExecute(BasicProfile result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			pd.dismiss();
		}

	}

}