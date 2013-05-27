package smartcampus.android.template.standalone;

import java.io.IOException;

import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import eu.trentorise.smartcampus.ac.ACService;
import eu.trentorise.smartcampus.ac.AcServiceException;
import eu.trentorise.smartcampus.ac.Constants;
import eu.trentorise.smartcampus.ac.SCAccessProvider;
import eu.trentorise.smartcampus.ac.authenticator.AMSCAccessProvider;
import eu.trentorise.smartcampus.ac.model.UserData;
import eu.trentorise.smartcampus.profileservice.ProfileService;
import eu.trentorise.smartcampus.profileservice.ProfileServiceException;
import eu.trentorise.smartcampus.profileservice.model.BasicProfile;


public class MyUniActivity extends Activity {

	/** Logging tag */
	private static final String TAG = "Main";
	
	private static final String AUTH_URL = "https://vas-dev.smartcampuslab.it/accesstoken-provider/ac";
	
	private static final String AC_SERVICE_ADDR = "https://vas-dev.smartcampuslab.it/acService";
	private static final String PROFILE_SERVICE_ADDR = "https://vas-dev.smartcampuslab.it";
	
	/** Provides access to the authentication mechanism. Used to retrieve the token */ 
	private SCAccessProvider mAccessProvider = null;
	/** Access token for the application user */
	private String mToken = null;
	
	public static ProgressDialog pd;
@Override
protected void onStart() {
	// TODO Auto-generated method stub
	super.onStart();
}
	
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		new ProgressDialog(MyUniActivity.this);
		pd = ProgressDialog.show(MyUniActivity.this, "Accesso account",
				"Accesso in corso...");
		
		setContentView(R.layout.activity_my_uni);
		
		
		if (mToken != null) {
			// access the user data from the AC service remotely
			new LoadUserDataFromACServiceTask().execute(mToken);
			// access the basic user profile data remotely
			new LoadUserDataFromProfileServiceTask().execute(mToken);
		
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
		
		findViewById(R.id.phl_btn).setOnClickListener(
				new OnClickListener() {
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
		
		}
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_uni);
		
		
		try {
			Constants.setAuthUrl(getApplicationContext(), AUTH_URL);
		} catch (NameNotFoundException e1) {
			Log.e(TAG, "problems with configuration.");
			finish();
		}

		// Initialize the access provider
		mAccessProvider = new AMSCAccessProvider();
		// if the authentication is necessary, use the provided operations to
		// retrieve the token: no restriction on the preferred account type
		try {
			mToken = mAccessProvider.getAuthToken(this, null);

		}

		catch (OperationCanceledException e) {
			Log.e(TAG, "Login cancelled.");
			finish();
		} catch (AuthenticatorException e) {
			Log.e(TAG, "Login failed: " + e.getMessage());
			finish();
		} catch (IOException e) {
			Log.e(TAG, "Login ended with error: " + e.getMessage());
			finish();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_uni, menu);
		return true;
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// check the result of the authentication
		if (requestCode == SCAccessProvider.SC_AUTH_ACTIVITY_REQUEST_CODE) {
			// authentication successful
			if (resultCode == RESULT_OK) {
				mToken = data.getExtras().getString(
						AccountManager.KEY_AUTHTOKEN);
				Log.i(TAG, "Authentication successfull");
				new LoadUserDataFromACServiceTask().execute(mToken);
				new LoadUserDataFromProfileServiceTask().execute(mToken);
				// authentication cancelled by user
			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
				Log.i(TAG, "Authentication cancelled");
				// authentication failed
			} else {
				String error = data.getExtras().getString(
						AccountManager.KEY_AUTH_FAILED_MESSAGE);
				Toast.makeText(this, error, Toast.LENGTH_LONG).show();
				Log.i(TAG, "Authentication failed: " + error);
			}
		}
		
		
		super.onActivityResult(requestCode, resultCode, data);
	}

	public class LoadUserDataFromACServiceTask extends
			AsyncTask<String, Void, UserData> {

		@Override
		protected UserData doInBackground(String... params) {
			try {
				return new ACService(AC_SERVICE_ADDR).getUserByToken(params[0]);
			} catch (SecurityException e) {
				Log.e(TAG, "Security Exception: " + e.getMessage());
			} catch (AcServiceException e) {
				Log.e(TAG, "Service Exception: " + e.getMessage());
			}
			return null;
		}
	}

	public class LoadUserDataFromProfileServiceTask extends
			AsyncTask<String, Void, BasicProfile> {

		@Override
		protected BasicProfile doInBackground(String... params) {
			try {
				return new ProfileService(PROFILE_SERVICE_ADDR)
						.getBasicProfile(params[0]);
			} catch (SecurityException e) {
				Log.e(TAG, "Security Exception: " + e.getMessage());
			} catch (ProfileServiceException e) {
				Log.e(TAG, "Profile Service Exception: " + e.getMessage());
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(BasicProfile result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			pd.dismiss();
		}
		
		
	}

}
