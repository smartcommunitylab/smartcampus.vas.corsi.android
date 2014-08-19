package eu.trentorise.smartcampus.android.studyMate;

import eu.trentorise.smartcampus.android.studyMate.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.gcm.GCMRegistrar;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import eu.trentorise.smartcampus.ac.AACException;
import eu.trentorise.smartcampus.ac.SCAccessProvider;
import eu.trentorise.smartcampus.android.common.LauncherHelper;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.android.studyMate.R.id;
import eu.trentorise.smartcampus.android.studyMate.R.layout;
import eu.trentorise.smartcampus.android.studyMate.R.menu;
import eu.trentorise.smartcampus.android.studyMate.R.string;
import eu.trentorise.smartcampus.android.studyMate.finder.FindHomeActivity;
import eu.trentorise.smartcampus.android.studyMate.gruppi_studio.Lista_GDS_activity;
import eu.trentorise.smartcampus.android.studyMate.models.CorsoCarriera;
import eu.trentorise.smartcampus.android.studyMate.models.CorsoLaurea;
import eu.trentorise.smartcampus.android.studyMate.models.Dipartimento;
import eu.trentorise.smartcampus.android.studyMate.myAgenda.MyAgendaActivity;
import eu.trentorise.smartcampus.android.studyMate.notices.NoticesActivity;
import eu.trentorise.smartcampus.android.studyMate.rate.CoursesPassedActivity;
import eu.trentorise.smartcampus.android.studyMate.start.SetInfoStudentActivity;
import eu.trentorise.smartcampus.android.studyMate.utilities.Constants;
import eu.trentorise.smartcampus.android.studyMate.utilities.SharedUtils;
import eu.trentorise.smartcampus.android.studyMate.utilities.SmartUniDataWS;
import eu.trentorise.smartcampus.android.studyMate.utilities.TutorialUtils;
import eu.trentorise.smartcampus.communicator.CommunicatorConnector;
import eu.trentorise.smartcampus.communicator.CommunicatorConnectorException;
import eu.trentorise.smartcampus.network.RemoteConnector;
import eu.trentorise.smartcampus.network.RemoteConnector.CLIENT_TYPE;
import eu.trentorise.smartcampus.profileservice.BasicProfileService;
import eu.trentorise.smartcampus.profileservice.model.BasicProfile;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;
import eu.trentorise.smartcampus.pushservice.NotificationCenter;
import eu.trentorise.smartcampus.pushservice.PushServiceConnector;

public class MyUniActivity extends SherlockActivity {

	public static final String APP_ID = "studymate";
	//
	public static final String SERVER_URL = "https://vas-dev.smartcampuslab.it/core.communicator";
	public static final String AUTH_URL = "https://vas-dev.smartcampuslab.it/aac";

	// This is the project id generated from the Google console when
	// you defined a Google APIs project.
	private static final String PROJECT_ID = "674612024423";
	public static final String EXTRA_MESSAGE = "message";
	public static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";
	private String regId = "";
	private String broadcastMessage = "No broadcast message";

	/**
	 * Tag used on log messages.
	 */
	static final String TAG = "GCMDemo";
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	// This intent filter will be set to filter on the string
	// "GCM_RECEIVED_ACTION"
	private IntentFilter gcmFilter;
	private GoogleCloudMessaging gcm;

	private static Context mContext;
	private static SCAccessProvider accessProvider = null;
	public static ProgressDialog pd;
	public Dipartimento studenteDipartimento;
	public CorsoLaurea studenteCds;
	public List<Dipartimento> listDipartimenti;
	public List<CorsoLaurea> listCds;

	/**
	 * Provides access to the authentication mechanism. Used to retrieve the
	 * token
	 */
	public ProtocolCarrier mProtocolCarrier;
	public String body;
	public static String userAuthToken;
	public static BasicProfile bp;
	
	
//	public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {
//	    @Override
//	    public void onReceive(Context context, Intent intent) {
//	        // Explicitly specify that GcmIntentService will handle the intent.
//	        ComponentName comp = new ComponentName(context.getPackageName(),
//	                GcmIntentService.class.getName());
//	        // Start the service, keeping the device awake while it is launching.
//	        startWakefulService(context, (intent.setComponent(comp)));
//	        setResultCode(Activity.RESULT_OK);
//	    }
//	}

	// A BroadcastReceiver must override the onReceive() event.
	private BroadcastReceiver gcmReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d("GCM","Message received!");

//			new NotificationCenter(mContext).publishNotification(intent,
//					1234, MyUniActivity.class);

		}
	};
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getApplicationContext();



		if (LauncherHelper.isLauncherInstalled(this, true)) {
			if (!isUserConnectedToInternet(mContext)) {
				Toast.makeText(mContext, R.string.internet_connection,
						Toast.LENGTH_SHORT).show();
				MyUniActivity.this.finish();
			} else {
				if (!TutorialUtils.isFirstLaunch(mContext)) {
					new LoadUserDataFromACServiceTask().execute();
				} else {
					AlertDialog.Builder builder = new AlertDialog.Builder(this);
					builder// .setTitle(R.string.welcome_title)
					.setView(
							getLayoutInflater().inflate(
									R.layout.disclaimerdialog, null))
							.setOnCancelListener(
									new DialogInterface.OnCancelListener() {

										@Override
										public void onCancel(
												DialogInterface arg0) {
											arg0.dismiss();
											new LoadUserDataFromACServiceTask()
													.execute();
										}
									})
							.setPositiveButton(getString(R.string.ok),
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
											new LoadUserDataFromACServiceTask()
													.execute();
										}
									});
					builder.create().show();
				}
			}
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (!isUserConnectedToInternet(mContext)) {
			Toast.makeText(mContext, R.string.internet_connection,
					Toast.LENGTH_SHORT).show();
			MyUniActivity.this.finish();
		} else {
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

			findViewById(R.id.phl_btn).setOnClickListener(
					new OnClickListener() {
						@Override
						public void onClick(View v) {
							Toast.makeText(
									getApplicationContext(),
									getResources().getString(
											R.string.dialog_coming_soon),
									Toast.LENGTH_SHORT).show();
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

			findViewById(R.id.rate_btn).setOnClickListener(
					new OnClickListener() {
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
							// Toast.makeText(
							// getApplicationContext(),
							// getResources().getString(
							// R.string.dialog_coming_soon),
							// Toast.LENGTH_SHORT).show();
							Intent intent = new Intent(MyUniActivity.this,
									Lista_GDS_activity.class);
							MyUniActivity.this.startActivity(intent);
						}
					});

			studenteDipartimento = SharedUtils
					.getDipartimentoStudente(MyUniActivity.this);
			studenteCds = SharedUtils.getCdsStudente(MyUniActivity.this);

			if (studenteDipartimento == null || studenteCds == null) {
				Intent intent = new Intent(MyUniActivity.this,
						SetInfoStudentActivity.class);
				MyUniActivity.this.startActivity(intent);
			}
		}
		
		// Create our IntentFilter, which will be used in conjunction with a
		// broadcast receiver.
		gcmFilter = new IntentFilter();
		gcmFilter.addAction("GCM_RECEIVED_ACTION");
	}

	public static SCAccessProvider getAccessProvider() {
		if (accessProvider == null)
			accessProvider = SCAccessProvider.getInstance(mContext);
		return accessProvider;
	}

	public static String getAuthToken() throws AACException {
		String mToken;
		mToken = getAccessProvider().readToken(mContext);
		return mToken;
	}

	public class LoadUserDataFromACServiceTask extends
			AsyncTask<Void, Void, BasicProfile> {

		ArrayList<CorsoCarriera> corsicarrierastudente;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pd = ProgressDialog.show(MyUniActivity.this, getResources()
					.getString(R.string.dialog_sync_data), getResources()
					.getString(R.string.dialog_loading));
		}

		@Override
		protected BasicProfile doInBackground(Void... params) {
			try {
				
				String reg_id = getRegistrationId(mContext); /////////////////////////////////////TEST

				RemoteConnector.setClientType(CLIENT_TYPE.CLIENT_ACCEPTALL);
				BasicProfileService service = new BasicProfileService(AUTH_URL);
				bp = service.getBasicProfile(getAuthToken());
				System.out.println(bp.getName());
				System.out.println("USERID: " + bp.getUserId());
				System.out.println("user token: " + getAuthToken());

				boolean isFirstTime = SharedUtils.isFirstTime(mContext);

				// se è il primo avvio dell'app sul dispositivo -> registro
				// regid nel server
				if (isFirstTime) {
					registrationDevice();
				}
				
				

				// init connector
				// PushServiceConnector connector = new PushServiceConnector();
				// try {
				// connector.init(getApplicationContext(), getAuthToken(),
				// APP_ID, SERVER_URL);
				// } catch (CommunicatorConnectorException e) {
				// e.printStackTrace();
				// }
				// proviamo a recuperare i dati studente
 				mProtocolCarrier = new ProtocolCarrier(MyUniActivity.this,
						SmartUniDataWS.TOKEN_NAME);
				MessageResponse response;
				MessageRequest request = new MessageRequest(
						SmartUniDataWS.URL_WS_SMARTUNI,
						SmartUniDataWS.GET_WS_STUDENT_DATA);
				request.setMethod(Method.GET);
				try {
					response = mProtocolCarrier.invokeSync(request,
							SmartUniDataWS.TOKEN_NAME,
							MyUniActivity.getAuthToken());
					if (response.getHttpStatus() == 200) {

						// il body corrisponde al jsonstudente!! allora lo
						// facciamo vedere XD
						body = response.getBody();
						String jsonstudente = body;
						save("studenteSessioneJSON", jsonstudente);
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

				// proviamo a recueprare i dati relativi ai corsi dello
				// studente
				mProtocolCarrier = new ProtocolCarrier(MyUniActivity.this,
						SmartUniDataWS.TOKEN_NAME);
				MessageResponse response1;
				MessageRequest request1 = new MessageRequest(
						SmartUniDataWS.URL_WS_SMARTUNI,
						SmartUniDataWS.GET_WS_FREQUENTEDCOURSES_SYNC);
				request1.setMethod(Method.GET);
				try {
					response1 = mProtocolCarrier.invokeSync(request1,
							SmartUniDataWS.TOKEN_NAME,
							MyUniActivity.getAuthToken());
					if (response1.getHttpStatus() == 200) {

						body = response1.getBody();
						String jsoncorsidellostudente = body;
						save("corsiStudente", jsoncorsidellostudente);
						corsicarrierastudente = (ArrayList<CorsoCarriera>) Utils
								.convertJSONToObjects(jsoncorsidellostudente,
										CorsoCarriera.class);
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

				return bp;
			} catch (Exception e) {
				e.printStackTrace();
				// System.out.println(e.getMessage());
				return null;
			}

		}

		@Override
		protected void onPostExecute(BasicProfile result) {
			super.onPostExecute(result);
			pd.dismiss();
			if (TutorialUtils.isFirstLaunch(mContext)) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						MyUniActivity.this);
				builder.setTitle(R.string.welcome_title)
						.setMessage(R.string.welcome_msg)
						.setOnCancelListener(
								new DialogInterface.OnCancelListener() {

									@Override
									public void onCancel(DialogInterface arg0) {
										arg0.dismiss();
									}
								})

						.setPositiveButton(getString(R.string.begin_tut),
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.dismiss();
										TutorialUtils.getTutorial(
												MyUniActivity.this)
												.showTutorials();

									}
								})
						.setNeutralButton(getString(android.R.string.cancel),
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.dismiss();

									}
								});
				builder.create().show();
				TutorialUtils.disableFirstLanch(MyUniActivity.this);
			} else if (TutorialUtils.isTutorialEnabled(MyUniActivity.this)) {
				TutorialUtils.getTutorial(MyUniActivity.this).showTutorials();
			}
		}

		private void save(String key, String jsonTosaveinSharedP) {
			SharedPreferences sharedPreferences = MyUniActivity.this
					.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putString(key, jsonTosaveinSharedP);
			editor.commit();
		}

	}

	public static boolean isUserConnectedToInternet(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
		if (netInfo != null) {
			return netInfo.isConnected();
		}
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.my_uni, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.preference:
			Intent intent = new Intent(MyUniActivity.this,
					SetInfoStudentActivity.class);
			intent.putExtra(Constants.MY_UNI_STATE, true);
			MyUniActivity.this.startActivity(intent);
			return false;
		case R.id.show_tutorial:
			TutorialUtils.enableTutorial(this);
			TutorialUtils.getTutorial(this).showTutorials();
		default:
			break;

		}
		return false;
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		TutorialUtils.getTutorial(this).onTutorialActivityResult(requestCode,
				resultCode, data);

	}

	private void registrationDevice() {
		final String TAG = "REG_ID";
		String registrationStatus = "";

		try {

			if (gcm == null) {
				gcm = GoogleCloudMessaging.getInstance(this);
			}

			regId = getRegistrationId(mContext);

			if (regId.isEmpty()) {

				String msg = "";

				try {
					if (gcm == null) {
						gcm = GoogleCloudMessaging.getInstance(mContext);
					}
					regId = gcm.register(PROJECT_ID);
					msg = "Device registered, registration ID=" + regId;

					// You should send the registration ID to your server over
					// HTTP,
					// so it can use GCM/HTTP or CCS to send messages to your
					// app.
					// The request to your server should be authenticated if
					// your app
					// is using accounts.
					sendRegistrationToServer(regId);

					// For this demo: we don't need to send it because the
					// device
					// will send upstream messages to a server that echo back
					// the
					// message using the 'from' address in the message.

					// Persist the regID - no need to register again.
					storeRegistrationId(mContext, regId);
				} catch (IOException ex) {
					msg = "Error :" + ex.getMessage();
					// If there is an error, don't just keep trying to register.
					// Require the user to click a button again, or perform
					// exponential back-off.
				}
			}

			// This is actually a dummy function. At this point, one
			// would send the registration id, and other identifying
			// information to your server, which should save the id
			// for use when broadcasting messages.
			sendRegistrationToServer(regId);

		} catch (Exception e) {

			e.printStackTrace();
			registrationStatus = e.getMessage();

		}

		Log.d(TAG, registrationStatus);

		// This is part of our CHEAT. For this demo, you'll need to
		// capture this registration id so it can be used in our demo web
		// service.
		Log.d(TAG, regId);

	}

	private boolean sendRegistrationToServer(String regId) {

		mProtocolCarrier = new ProtocolCarrier(MyUniActivity.this,
				SmartUniDataWS.TOKEN_NAME);
		MessageResponse response;
		MessageRequest request1 = new MessageRequest(
				SmartUniDataWS.URL_WS_SMARTUNI,
				SmartUniDataWS.POST_WS_REGISTRATION_GCM(regId));
		request1.setMethod(Method.POST);
		try {
			response = mProtocolCarrier.invokeSync(request1,
					SmartUniDataWS.TOKEN_NAME, MyUniActivity.getAuthToken());
			if (response.getHttpStatus() == 200) {

				body = response.getBody();
				
				if (body.equals("true")) {
					SharedUtils.setFirstTime(mContext);
					Log.d("REG_ID", "registration to server done!");
					return true;
				} else {
					Log.d("REG_ID", "registration to server failed!");
					return false;
				}

				

			} else {
				Log.d("REG_ID", "registration to server failed!");
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

		return false;

	}


	// If our activity is paused, it is important to UN-register any
	// broadcast receivers.
	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(gcmReceiver);
		
	}

	// When an activity is resumed, be sure to register any
	// broadcast receivers with the appropriate intent
	@Override
	protected void onResume() {
		super.onResume();
		registerReceiver(gcmReceiver, gcmFilter);

	}

	/**
	 * Gets the current registration ID for application on GCM service.
	 * <p>
	 * If result is empty, the app needs to register.
	 * 
	 * @return registration ID, or empty string if there is no existing
	 *         registration ID.
	 */
	private String getRegistrationId(Context context) {
		final SharedPreferences prefs = getGCMPreferences(context);
		String registrationId = prefs.getString(PROPERTY_REG_ID, "");
		if (registrationId.isEmpty()) {
			Log.i(TAG, "Registration not found.");
			return "";
		}
		// Check if app was updated; if so, it must clear the registration ID
		// since the existing regID is not guaranteed to work with the new
		// app version.
		int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION,
				Integer.MIN_VALUE);
		int currentVersion = getAppVersion(context);
		if (registeredVersion != currentVersion) {
			Log.i(TAG, "App version changed.");
			return "";
		}
		return registrationId;
	}

	/**
	 * @return Application's {@code SharedPreferences}.
	 */
	private SharedPreferences getGCMPreferences(Context context) {
		// This sample app persists the registration ID in shared preferences,
		// but
		// how you store the regID in your app is up to you.
		return getSharedPreferences(MyUniActivity.class.getSimpleName(),
				Context.MODE_PRIVATE);
	}

	/**
	 * @return Application's version code from the {@code PackageManager}.
	 */
	private static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// should never happen
			throw new RuntimeException("Could not get package name: " + e);
		}
	}

	/**
	 * Stores the registration ID and app versionCode in the application's
	 * {@code SharedPreferences}.
	 * 
	 * @param context
	 *            application's context.
	 * @param regId
	 *            registration ID
	 */
	private void storeRegistrationId(Context context, String regId) {
		final SharedPreferences prefs = getGCMPreferences(context);
		int appVersion = getAppVersion(context);
		Log.i(TAG, "Saving regId on app version " + appVersion);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(PROPERTY_REG_ID, regId);
		editor.putInt(PROPERTY_APP_VERSION, appVersion);
		editor.commit();
	}
}
