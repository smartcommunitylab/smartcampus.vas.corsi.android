package eu.trentorise.smartcampus.studyMate.utilities;

import smartcampus.android.template.standalone.R;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;
import eu.trentorise.smartcampus.studyMate.models.CorsoLite;

public class SetCourseAsFollowHandler extends AsyncTask<CorsoLite, Void, Boolean>{

	private ProtocolCarrier mProtocolCarrier;
	public Context context;
	private String body;
	private CorsoLite corso;
	private TextView txtMonitorFollow;
	
	
	public SetCourseAsFollowHandler(Context applicationContext, TextView txtMonitor) {
		// TODO Auto-generated constructor stub
		this.context = applicationContext;
		this.txtMonitorFollow = txtMonitor;
	}

	@Override
	protected Boolean doInBackground(CorsoLite... params) {
		// TODO Auto-generated method stub
		
		// corso a cui mi riferisco
		corso = params[0];
		
		
		mProtocolCarrier = new ProtocolCarrier(context,
				SmartUniDataWS.TOKEN_NAME);

		MessageRequest request = new MessageRequest(
				SmartUniDataWS.URL_WS_SMARTUNI, SmartUniDataWS.POST_WS_COURSE_AS_FOLLOW);
		request.setMethod(Method.POST);

		MessageResponse response;
		try {

			String eventoJSON = Utils.convertToJSON(corso);

			request.setBody(eventoJSON);
			response = mProtocolCarrier.invokeSync(request,
					SmartUniDataWS.TOKEN_NAME, SmartUniDataWS.TOKEN);

			if (response.getHttpStatus() == 200) {
				return true;
			}else{
				return false;
			}
			

		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
		if(result){
			txtMonitorFollow.setText(context.getResources().getText(R.string.label_txtMonitor_on));
			Toast toast = Toast.makeText(context, context.getResources().getText(R.string.toast_switchfollow_success), Toast.LENGTH_LONG);
			toast.show();
		}else{
			Toast toast = Toast.makeText(context, context.getResources().getText(R.string.toast_switchfollow_error), Toast.LENGTH_SHORT);
			toast.show();
			
		}
		
		
	}

}
