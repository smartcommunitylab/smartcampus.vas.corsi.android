//package eu.trentorise.smartcampus.android.studyMate.utilities;
//
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.os.AsyncTask;
//import android.os.PowerManager;
//import android.widget.Toast;
//import eu.trentorise.smartcampus.android.studyMate.models.CwdPHL;
//
//public class DownloadTask extends AsyncTask<String, Integer, String> {
//	private Context context;
//	private ProgressDialog mProgressDialog;
//	private CwdPHL r;
//
//	public DownloadTask(Context context, ProgressDialog mProgressDialog,
//			CwdPHL r) {
//		this.context = context;
//		this.mProgressDialog = mProgressDialog;
//		this.r = r;
//	}
//
//	@SuppressWarnings("resource")
//	@Override
//	protected String doInBackground(String... sUrl) {
//		// take CPU lock to prevent CPU from going off if the user
//		// presses the power button during download
//		PowerManager pm = (PowerManager) context
//				.getSystemService(Context.POWER_SERVICE);
//		PowerManager.WakeLock wl = pm.newWakeLock(
//				PowerManager.PARTIAL_WAKE_LOCK, getClass().getName());
//		wl.acquire();
//
//		try {
//			InputStream input = null;
//			OutputStream output = null;
//			HttpURLConnection connection = null;
//			try {
//				URL url = new URL(sUrl[0]);
//				connection = (HttpURLConnection) url.openConnection();
//				connection.connect();
//
//				// expect HTTP 200 OK, so we don't mistakenly save error report
//				// instead of the file
//				if (connection.getResponseCode() != HttpURLConnection.HTTP_OK)
//					return "Server returned HTTP "
//							+ connection.getResponseCode() + " "
//							+ connection.getResponseMessage();
//
//				// this will be useful to display download percentage
//				// might be -1: server did not report the length
//				int fileLength = connection.getContentLength();
//
//				// download the file
//				input = connection.getInputStream();
//				output = new FileOutputStream("/sdcard/studyMate/"
//						+ r.getName());
//
//				byte data[] = new byte[4096];
//				long total = 0;
//				int count;
//				while ((count = input.read(data)) != -1) {
//					// allow canceling with back button
//					if (isCancelled())
//						return null;
//					total += count;
//					// publishing the progress....
//					if (fileLength > 0) // only if total length is known
//						publishProgress((int) (total * 100 / fileLength));
//					output.write(data, 0, count);
//				}
//			} catch (Exception e) {
//				return e.toString();
//			} finally {
//				try {
//					if (output != null)
//						output.close();
//					if (input != null)
//						input.close();
//				} catch (IOException ignored) {
//				}
//
//				if (connection != null)
//					connection.disconnect();
//			}
//		} finally {
//			wl.release();
//		}
//		return null;
//	}
//
//	@Override
//	protected void onPreExecute() {
//		super.onPreExecute();
//		mProgressDialog.show();
//	}
//
//	@Override
//	protected void onProgressUpdate(Integer... progress) {
//		super.onProgressUpdate(progress);
//		// if we get here, length is known, now set indeterminate to false
//		mProgressDialog.setIndeterminate(false);
//		mProgressDialog.setMax(100);
//		mProgressDialog.setProgress(progress[0]);
//	}
//
//	@Override
//	protected void onPostExecute(String result) {
//		mProgressDialog.dismiss();
//		if (result != null)
//			Toast.makeText(context, "Download error: " + result,
//					Toast.LENGTH_LONG).show();
//		else
//			Toast.makeText(context, "File downloaded", Toast.LENGTH_SHORT)
//					.show();
//	}
//
// }