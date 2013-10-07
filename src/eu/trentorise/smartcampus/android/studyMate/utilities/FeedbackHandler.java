package eu.trentorise.smartcampus.android.studyMate.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import smartcampus.android.template.standalone.R;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.android.studyMate.finder.FindHomeCourseActivity;
import eu.trentorise.smartcampus.android.studyMate.models.Commento;
import eu.trentorise.smartcampus.android.studyMate.models.Corso;
import eu.trentorise.smartcampus.android.studyMate.models.CorsoLite;
import eu.trentorise.smartcampus.android.studyMate.models.Studente;
import eu.trentorise.smartcampus.protocolcarrier.ProtocolCarrier;
import eu.trentorise.smartcampus.protocolcarrier.common.Constants.Method;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageRequest;
import eu.trentorise.smartcampus.protocolcarrier.custom.MessageResponse;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ConnectionException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.ProtocolException;
import eu.trentorise.smartcampus.protocolcarrier.exceptions.SecurityException;

public class FeedbackHandler extends AsyncTask<Void, Void, List<Commento>> {

	private ProtocolCarrier mProtocolCarrier;
	public Context context;
	String body;
	long idCourse;
	TextView tvCourseName;
	RatingBar ratingAverage;
	ExpandableListView listComments;
	public static List<Commento> feedbackInfoList;
	public SherlockFragmentActivity act;
	TextView descriptionCourse;
	Button swichFollow;
	public static ProgressDialog pd;
	TextView txtMonitor;
	private Studente studenteUser;

	public FeedbackHandler(Context applicationContext, long idCourse,
			SherlockFragmentActivity act, RatingBar ratingAverage,
			TextView descriptionCourse, Button sFollow, TextView txtMonitor) {
		this.context = applicationContext;
		this.idCourse = idCourse;
		this.act = act;
		this.ratingAverage = ratingAverage;
		this.descriptionCourse = descriptionCourse;
		this.swichFollow = sFollow;
		this.txtMonitor = txtMonitor;
	}

	private List<Commento> getFullFeedbackById() {

		mProtocolCarrier = new ProtocolCarrier(context,
				SmartUniDataWS.TOKEN_NAME);

		MessageRequest request = new MessageRequest(
				SmartUniDataWS.URL_WS_SMARTUNI,
				SmartUniDataWS.GET_WS_FEEDBACK_OF_COURSE(idCourse));
		request.setMethod(Method.GET);

		MessageResponse response;
		try {
			response = mProtocolCarrier.invokeSync(request,
					SmartUniDataWS.TOKEN_NAME, SmartUniDataWS.TOKEN);

			if (response.getHttpStatus() == 200) {

				body = response.getBody();
				System.out.println(body);
			} else {
				return null;
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
		
		
		
		// prendo i dati aggiornati dello studente
		request = new MessageRequest(
				SmartUniDataWS.URL_WS_SMARTUNI,
				SmartUniDataWS.GET_WS_STUDENT_DATA);
		request.setMethod(Method.GET);
		
		try {
			response = mProtocolCarrier.invokeSync(request,
					SmartUniDataWS.TOKEN_NAME, SmartUniDataWS.TOKEN);

			if (response.getHttpStatus() == 200) {

				String bodyStudente = response.getBody();
				studenteUser = Utils.convertJSONToObject(bodyStudente, Studente.class);
				
			} else {
				return null;
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
		
		return Utils.convertJSONToObjects(body, Commento.class);
	}

	@Override
	protected void onPreExecute() {
		new ProgressDialog(act);
		pd = ProgressDialog.show(act, "Informazioni del corso di "
				+ FindHomeCourseActivity.courseName, "Caricamento...");

		super.onPreExecute();

	}

	@Override
	protected void onPostExecute(final List<Commento> commenti) {
		super.onPostExecute(commenti);

		if (commenti == null) {

			Toast.makeText(context, "Ops! C'e' stato un errore...",
					Toast.LENGTH_SHORT).show();

			pd.dismiss();
			act.finish();
		} else {
// prendo studente/me e lo assegno a stud
			// se il corso corrente fa parte dei corsi che seguo lo setto on
			if (isContainsInCorsiInteresse(studenteUser,
					commenti.get(0).getCorso())) {
				swichFollow.setBackgroundResource(R.drawable.ic_monitor_on);
				txtMonitor.setText(R.string.label_txtMonitor_on);
			} else {
				swichFollow.setBackgroundResource(R.drawable.ic_monitor_off);
				txtMonitor.setText(R.string.label_txtMonitor_off);
			}

			swichFollow.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

				
						new SetCourseAsFollowHandler(context, swichFollow,
								txtMonitor).execute(commenti.get(0).getCorso());
				}

			});

			Collections.reverse(commenti);
			feedbackInfoList = commenti;
			act.getSupportActionBar().setTitle(
					feedbackInfoList.get(0).getCorso().getNome());
			ratingAverage.setRating((float) feedbackInfoList.get(0).getCorso()
					.getValutazione_media());

			RatingBar ratingCont = (RatingBar) act
					.findViewById(R.id.ratingBarRowContenuti);
			ratingCont.setRating(feedbackInfoList.get(0).getCorso().getRating_contenuto());

			RatingBar ratingCaricoStudio = (RatingBar) act
					.findViewById(R.id.ratingBarRowCfu);
			ratingCaricoStudio.setRating(feedbackInfoList.get(0).getCorso()
					.getRating_carico_studio());

			RatingBar ratingLezioni = (RatingBar) act
					.findViewById(R.id.ratingBarRowLezioni);
			ratingLezioni
					.setRating(feedbackInfoList.get(0).getCorso().getRating_lezioni());

			RatingBar ratingMateriali = (RatingBar) act
					.findViewById(R.id.ratingBarRowMateriali);
			ratingMateriali.setRating(feedbackInfoList.get(0).getCorso()
					.getRating_materiali());

			RatingBar ratingEsame = (RatingBar) act
					.findViewById(R.id.ratingBarRowEsame);
			ratingEsame.setRating(feedbackInfoList.get(0).getCorso().getRating_esame());

			descriptionCourse.setText(feedbackInfoList.get(0).getCorso()
					.getDescrizione());

			pd.dismiss();

		}

	}

	@Override
	protected List<Commento> doInBackground(Void... params) {
		// TODO Auto-generated method stub
		return getFullFeedbackById();
	}

	
	// metodo che dato lo studente setta la lista dei corsi di interesse dalla
	// stringa degli ids
	private boolean isContainsInCorsiInteresse(Studente stud, Corso corso) {
		// TODO Auto-generated method stub

		if (stud.getIdsCorsiInteresse() == null) {
			return false;
		} else {
			String[] listS = stud.getIdsCorsiInteresse().split(",");
			boolean contenuto = false;

			for (String s : listS) {
				if (s.equals(corso.getId().toString())) {
					contenuto = true;
				}
			}
			return contenuto;
		}

	}

}