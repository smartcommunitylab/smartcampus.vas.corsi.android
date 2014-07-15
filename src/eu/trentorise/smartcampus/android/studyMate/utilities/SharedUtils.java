package eu.trentorise.smartcampus.android.studyMate.utilities;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import eu.trentorise.smartcampus.android.common.Utils;
import eu.trentorise.smartcampus.android.studyMate.models.CorsoLaurea;
import eu.trentorise.smartcampus.android.studyMate.models.Dipartimento;

public final class SharedUtils {

	private static final String STUDENTE_DIPARTIMENTO_SHARED_PREFERENCES = "studente_dipartimento_shared_preferences";
	private static final String STUDENTE_CDS_SHARED_PREFERENCES = "studente_cds_shared_preferences";
	private static final String STUDENTE_DIPARTIMENTO = "studente_dipartimento";
	private static final String IS_FIRST_TIME = "is_first_time";
	private static final String IS_FIRST_TIME_PREFERENCES = "is_first_time_preferences";
	private static final String STUDENTE_CDS = "studente_cds";

	public static boolean setDipartimentoStudente(Context context,
			Dipartimento dipartimento) throws Exception {
		SharedPreferences pref = context.getSharedPreferences(
				STUDENTE_DIPARTIMENTO_SHARED_PREFERENCES, Context.MODE_PRIVATE);

		Editor editor = pref.edit();
		editor.putString(STUDENTE_DIPARTIMENTO,
				Utils.convertToJSON(dipartimento));

		return editor.commit();
	}

	public static boolean setCdsStudente(Context context, CorsoLaurea cds)
			throws Exception {
		SharedPreferences pref = context.getSharedPreferences(
				STUDENTE_CDS_SHARED_PREFERENCES, Context.MODE_PRIVATE);

		Editor editor = pref.edit();
		editor.putString(STUDENTE_CDS, Utils.convertToJSON(cds));

		return editor.commit();
	}

	public static Dipartimento getDipartimentoStudente(Context context) {
		SharedPreferences pref = context.getSharedPreferences(
				STUDENTE_DIPARTIMENTO_SHARED_PREFERENCES, Context.MODE_PRIVATE);

		String jsonObjectDipartimento = pref.getString(STUDENTE_DIPARTIMENTO,
				null);

		Dipartimento dipartimentoStudente = Utils.convertJSONToObject(
				jsonObjectDipartimento, Dipartimento.class);

		return dipartimentoStudente;
	}

	public static CorsoLaurea getCdsStudente(Context context) {
		SharedPreferences pref = context.getSharedPreferences(
				STUDENTE_CDS_SHARED_PREFERENCES, Context.MODE_PRIVATE);

		String jsonObjectDipartimento = pref.getString(STUDENTE_CDS, null);

		CorsoLaurea cdsStudente = Utils.convertJSONToObject(
				jsonObjectDipartimento, CorsoLaurea.class);

		return cdsStudente;
	}

	public static int getPosListFromShared(ArrayList<String> listString,
			String description) {

		int pos = 0;
		int i = 0;
		for (String string : listString) {

			if (string.equals(description)) {
				pos = i;
				break;
			}
			i++;
		}

		return pos;
	}

	public static boolean isFirstTime(Context context) {
		// TODO Auto-generated method stub
		SharedPreferences pref = context.getSharedPreferences(
				IS_FIRST_TIME_PREFERENCES, Context.MODE_PRIVATE);

		String firstTime = pref.getString(STUDENTE_CDS, null);

		if (firstTime == null){
			return true;
		}else{
			return false;
		}
	}

	public static boolean setFirstTime(Context context) {
		// TODO Auto-generated method stub
		SharedPreferences pref = context.getSharedPreferences(
				IS_FIRST_TIME_PREFERENCES, Context.MODE_PRIVATE);

		Editor editor = pref.edit();
		editor.putString(STUDENTE_CDS, Utils.convertToJSON(IS_FIRST_TIME));

		return editor.commit();
	}

}
