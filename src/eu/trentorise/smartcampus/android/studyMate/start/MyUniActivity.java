package eu.trentorise.smartcampus.android.studyMate.start;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;

import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.ac.SCAccessProvider;
import eu.trentorise.smartcampus.ac.embedded.EmbeddedSCAccessProvider;
import eu.trentorise.smartcampus.android.studyMate.R;
import eu.trentorise.smartcampus.android.studyMate.finder.FindHomeActivity;
import eu.trentorise.smartcampus.android.studyMate.gruppi_studio.Lista_GDS_activity;
import eu.trentorise.smartcampus.android.studyMate.myAgenda.MyAgendaActivity;
import eu.trentorise.smartcampus.android.studyMate.notices.NoticesActivity;
import eu.trentorise.smartcampus.android.studyMate.phl.PHLActivity;
import eu.trentorise.smartcampus.android.studyMate.rate.CoursesPassedActivity;
import eu.trentorise.smartcampus.communicator.CommunicatorConnectorException;
import eu.trentorise.smartcampus.network.RemoteConnector;
import eu.trentorise.smartcampus.network.RemoteConnector.CLIENT_TYPE;
import eu.trentorise.smartcampus.profileservice.BasicProfileService;
import eu.trentorise.smartcampus.profileservice.model.BasicProfile;
import eu.trentorise.smartcampus.pushservice.PushServiceConnector;

public class MyUniActivity extends SherlockActivity {

	/** Logging tag */
	private static final String TAG = "Main";

	public static final String APP_ID = "studymate";

	public static final String SERVER_URL = "https://vas-dev.smartcampuslab.it/core.communicator";
	private static Context mContext;
	/**
	 * Provides access to the authentication mechanism. Used to retrieve the
	 * token
	 */
	private static SCAccessProvider accessProvider = null;
	//public static String userAuthToken;
	public static BasicProfile bp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext =  getApplicationContext();
		accessProvider = new EmbeddedSCAccessProvider();
		try {
			if (!accessProvider.login(this, null)) {
				new LoadUserDataFromACServiceTask().execute();
				// user is already registered. Proceed requesting the token
				// and the related steps if needed
			}
		} catch (AACException e) {
			Log.e(TAG, "Failed to login: " + e.getMessage());
			// handle the failure, e.g., notify the user and close the app.
		}

	}

	@Override
	protected void onResume() {
		try {
			if (!accessProvider.login(this, null)) {
				new LoadUserDataFromACServiceTask().execute();
				// user is already registered. Proceed requesting the token
				// and the related steps if needed
			}
		} catch (AACException e) {
			Log.e(TAG, "Failed to login: " + e.getMessage());
			// handle the failure, e.g., notify the user and close the app.
		}
		super.onResume();
	}

	@Override
	protected void onStart() {
		super.onStart();
		setContentView(R.layout.activity_my_uni);

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

		findViewById(R.id.rate_btn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MyUniActivity.this,
						CoursesPassedActivity.class);
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

//	@Override
//	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
//		getSupportMenuInflater().inflate(R.menu.my_uni, menu);
//		return super.onCreateOptionsMenu(menu);
//	}

//	@Override
//	public boolean onOptionsItemSelected(
//			com.actionbarsherlock.view.MenuItem item) {
//		switch (item.getItemId()) {
//		case R.id.logout:
//			new Thread(new Runnable() {
//
//				@Override
//				public void run() {
//					try {
//						accessProvider.logout(MyUniActivity.this);
//
//					} catch (AACException e) {
//						e.printStackTrace();
//					}
//
//				}
//			}).start();
//			Toast.makeText(MyUniActivity.this, "You are logged OFF!",
//					Toast.LENGTH_SHORT).show();
//			finish();
//			return true;
//		}
//		return false;
//	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// check the result of the authentication
		if (requestCode == SCAccessProvider.SC_AUTH_ACTIVITY_REQUEST_CODE) {
			if (resultCode == Activity.RESULT_OK) {
				new LoadUserDataFromACServiceTask().execute();

				// A&A successful. Proceed requesting the token
				// and the related steps if needed

			} else if (resultCode == Activity.RESULT_CANCELED) {
				// Cancelled by user
			} else {
				// Operation failed for some reason
				Toast.makeText(MyUniActivity.this, "Ops! c'è stato un errore!",
						Toast.LENGTH_SHORT).show();
			}
		}
		// other code here
		super.onActivityResult(requestCode, resultCode, data);
	}

	public static String getAuthToken() throws AACException{
		String userAuthToken;
		userAuthToken = MyUniActivity.accessProvider.readToken(mContext);
		return userAuthToken;
	}
	
	
	public class LoadUserDataFromACServiceTask extends
			AsyncTask<Void, Void, BasicProfile> {

		@Override
		protected BasicProfile doInBackground(Void... params) {
			try {
				RemoteConnector.setClientType(CLIENT_TYPE.CLIENT_ACCEPTALL);
				String userAuthToken = getAuthToken();
//				System.out.println(userAuthToken);
				BasicProfileService service = new BasicProfileService(
						"https://vas-dev.smartcampuslab.it/aac");
				bp = service.getBasicProfile(userAuthToken);
				System.out.println(bp.getName());
				System.out.println("USERID: " + bp.getUserId());
				PushServiceConnector connector = new PushServiceConnector();
				// //init connector
				try {
//					System.out.println("token: " + userAuthToken);
					connector.init(getApplicationContext(), userAuthToken,
							APP_ID, SERVER_URL);
				} catch (CommunicatorConnectorException e) {
					e.printStackTrace();
				}

//				try {
//					new PushServiceConnector().init(getApplicationContext(),
//							userAuthToken, APP_ID, SERVER_URL);
//				} catch (CommunicatorConnectorException e) {
//					e.printStackTrace();
//				}

				return bp;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

		}

		@Override
		protected void onPostExecute(BasicProfile result) {
			super.onPostExecute(result);
		}

	}

}