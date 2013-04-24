/*******************************************************************************
 * Copyright 2012-2013 Trento RISE
 * 
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 * 
 *        http://www.apache.org/licenses/LICENSE-2.0
 * 
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 ******************************************************************************/
package eu.trentorise.smartcampus.template;

import java.io.ByteArrayOutputStream;
import java.util.List;

import smartcampus.android.template.standalone.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import eu.trentorise.smartcampus.ac.SCAccessProvider;
import eu.trentorise.smartcampus.ac.authenticator.AMSCAccessProvider;
import eu.trentorise.smartcampus.storage.Filestorage;
import eu.trentorise.smartcampus.storage.model.AppAccount;
import eu.trentorise.smartcampus.storage.model.Resource;
import eu.trentorise.smartcampus.storage.model.StorageType;
import eu.trentorise.smartcampus.storage.model.UserAccount;

public class FileActivity extends Activity {

	private static final int AUTH_REQUESTCODE = 100;
	private static final int PHOTO_REQUESTCODE = 200;
	private static final String APPNAME = "hackathon";
	private static final String SERVICE = "smartcampus.filestorage";
	private static final String HOST = "https://vas-dev.smartcampuslab.it";
	private static final String APPTOKEN = "test smartcampus";

	/** Logging tag */
	private static final String TAG = "File";

	private UserAccount mUserAccount = null;

	/** Provides access to the authentication mechanism. Used to retrieve the token */ 
	private SCAccessProvider mAccessProvider = new AMSCAccessProvider();
	/** Access token for the application user */
	private String mToken = null;
	/** Filestorage connector reference */
	private Filestorage mFilestorage = null;
	/** ID of the resource stored */
	private String imageResourceId = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mToken = mAccessProvider.readToken(this, null);
		mFilestorage  = new Filestorage(this, APPNAME, APPTOKEN, HOST, SERVICE);
		if (mToken == null) {
			Log.e(TAG, "No auth token");
			finish();
		}

		setContentView(R.layout.file_mgmt);

		Button btn = (Button)findViewById(R.id.photo_btn);
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
		  		intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, PHOTO_REQUESTCODE);
			}
		});
		
		// verify user account: if not present, create one
		if (mUserAccount == null) {
			new AsyncTask<Void, Void, List<AppAccount>>() {
				@Override
				protected List<AppAccount> doInBackground(Void... params) {
					try {
						// read app accounts
						return mFilestorage.getAppAccounts(mToken);
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}
				}
				@Override
				protected void onPostExecute(List<AppAccount> result) {
					// request new account for the required app
					if (result != null && result.size() > 0) {
						AppAccount appAccount = result.get(0);
						mFilestorage.startAuthActivityForResult(FileActivity.this, mToken, appAccount.getAppAccountName(), appAccount.getId(), StorageType.DROPBOX, AUTH_REQUESTCODE);
					} else {
						Toast.makeText(FileActivity.this, "No Accounts!", Toast.LENGTH_LONG).show();
					}
				}
			}.execute();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// user account acquisition complete
		if (requestCode == AUTH_REQUESTCODE) {
			// user account acquired
			if (resultCode == Activity.RESULT_OK) {
				mUserAccount = data.getParcelableExtra(Filestorage.EXTRA_OUTPUT_USERACCOUNT);
				Toast.makeText(this, "User account created and stored: id = "+mUserAccount.getId(), Toast.LENGTH_LONG).show();
			// user account cancelled
			} else if (resultCode == Activity.RESULT_CANCELED) {
				Toast.makeText(this, "CANCELLED", Toast.LENGTH_LONG).show();
			// user account failed
			} else {
				Toast.makeText(this, "ERROR: "+resultCode, Toast.LENGTH_LONG).show();
			}
		}
		// photo selected
		if (requestCode == PHOTO_REQUESTCODE) {
			if (resultCode == Activity.RESULT_OK) {
				Bitmap image = (Bitmap) data.getExtras().get("data");
				// store and re-read remotely
				new StoreFileTask().execute(image);
			}

		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private class StoreFileTask extends AsyncTask<Bitmap, Void, Bitmap> {
		private ProgressDialog progress = null;

		protected Bitmap doInBackground(Bitmap... params) {
			try {
				Bitmap image = params[0];
				ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
				image.compress(Bitmap.CompressFormat.JPEG, 100,
						byteArrayBitmapStream);

				// store file remotely
				String rid = mFilestorage
						.storeResource(
								byteArrayBitmapStream.toByteArray(),
								"image/jpg",
								"image" + System.currentTimeMillis()
										+ ".jpg",
								mToken,
								mUserAccount.getId());

				if (rid != null) {
					// read file remotely
					Resource resource = mFilestorage.getResource(mToken, rid);
					if (resource == null) return null;
					byte[] content = resource.getContent();
					Bitmap bmp = BitmapFactory.decodeByteArray(content, 0, content.length);
					return bmp;
					} else return null;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			if (progress != null) {
				try {
					progress.cancel();
				} catch (Exception e) {
					Log.w(getClass().getName(),"Problem closing progress dialog: "+e.getMessage());
				}
			}
			// image read correctly
			if (result != null) {
				ImageView iv = (ImageView) findViewById(R.id.photo_iv);
				iv.setImageBitmap(result);
			} else {
				Toast.makeText(FileActivity.this, "Failed storing resource!", Toast.LENGTH_LONG).show();
			}
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			progress  = ProgressDialog.show(FileActivity.this, "", "Storing and reading image remotely...", true);
			super.onPreExecute();
		}
	}
	
}
