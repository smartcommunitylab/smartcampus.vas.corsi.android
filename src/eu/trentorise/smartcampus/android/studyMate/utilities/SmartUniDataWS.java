package eu.trentorise.smartcampus.android.studyMate.utilities;

public final class SmartUniDataWS {

	// general
	public static final String URL_WS_SMARTUNI = "studymate-webapp-test.app.smartcampuslab.it";// "http://studymate-test.app.smartcampuslab.it";
	public static final String TOKEN_NAME = "studymate";

	// Notification
	public static final String GET_WS_NOTIFICATIONS = "/rest/notification/all";

	public static final String GET_WS_NOTIFICATIONS(String type, long fromDate) {
		return "/rest/notifiche/type/" + type + "/date/"
				+ String.valueOf(fromDate);
	}

	// corsocarriera
	public static final String GET_WS_FREQUENTEDCOURSES = "/rest/corsocarriera/me";

	public static final String GET_WS_FREQUENTEDCOURSES_SYNC = "/rest/sync/corsocarriera/me";

	public static final String GET_WS_MY_COURSES_PASSED = "/rest/corsocarriera/passed/me";

	public static final String GET_WS_MY_COURSES_NOT_PASSED = "/rest/corsocarriera/notpassed/me";

	public static final String GET_WS_COURSE_IS_PASSED(String adCod) {
		return "/rest/corsocarriera/" + adCod + "/superato";
	}

	// corsointeresse
	public static final String POST_WS_COURSE_AS_FOLLOW = "/rest/corso/seguo";

	public static String POST_WS_COURSE_AS_FOLLOW_NEW(long adId) {
		return "/rest/corsointeresse/" + String.valueOf(adId) + "/seguo";
	}

	public static String GET_WS_IF_FOLLOW(String adId) {
		return "/rest/corsointeresse/" + adId + "/seguito";
	}

	public static String POST_WS_COURSE_UNFOLLOW(String adCod) {
		return "/rest/corsointeresse/" + String.valueOf(adCod) + "/delete";
	}

	public static final String GET_WS_MY_COURSES_INTEREST = "/rest/corso/interesse/me";

	// AttivitaDidattica

	public static final String GET_WS_ALLCOURSES_ATT_DIDATTICA = "/rest/attivitadidattica/all";

	public static String GET_WS_ALLCOURSES_OF_DEPARTMENT(long id_dep) {
		return "/rest/attivitadidattica/dipartimento/" + String.valueOf(id_dep);
	}

	public static String GET_WS_ALLCOURSES_OF_DEGREE(String id_cds) {
		return "/rest/attivitadidattica/corsolaurea/" + id_cds;
	}

	public static String GET_WS_COURSES_DETAILS(long adId) {
		return "/rest/attivitadidattica/" + String.valueOf(adId);
	}

	public static String GET_WS_COURSE_BY_COD(String adCod) {
		return "/rest/attivitadidattica/adcod/" + String.valueOf(adCod);
	}

	public static final String GET_WS_COURSE_COMPLETE_DATA(String idCourse) {
		return "/rest/corso/" + idCourse;
	}

	// CorsoLaurea ///////////////////////////////////////////////////////////

	public static final String GET_WS_ALLCOURSES = "/rest/corsolaurea/all";

	public static String GET_WS_COURSESDEGREE_OF_DEPARTMENT(long idDepartment) {
		return "/rest/corsolaurea/" + String.valueOf(idDepartment);
	}

	// Studente //////////////////////////////////////////////////////////
	public static final String GET_WS_STUDENT_DATA = "/rest/sync/studente/me";

	public static final String GET_WS_STUDENT_DATA_NO_SYNC = "/rest/studente/me";

	public static final String GET_STUDENTE(long id_stud) {
		return "/rest/studente/" + id_stud;
	}

	// Evento
	public static final String GET_WS_MYEVENTS = "/rest/evento/me";

	public static final String POST_NEW_EVENT = "/rest/evento";

	public static final String DELETE_EVENTO = "/rest/evento/delete";

	public static final String GET_WS_EVENTS_OF_COURSE(String idCourse) {
		return "/rest/evento/" + idCourse;
	}

	public static final String POST_WS_CHANGE_PERSONAL_EVENT(long date,
			long from, long to) {
		return "/rest/evento/change/date/" + date + "/from/" + from + "/to/"
				+ to;
	}

	// Dipartimento //////////////////////////////////////////////////////////
	public static final String GET_WS_DEPARTMENTS_ALL = "/rest/dipartimento/all";

	// Commento //////////////////////////////////////////////////////////
	public static String GET_WS_FEEDBACK_OF_COURSE(long adId) {
		return "/rest/corso/" + String.valueOf(adId) + "/commento/all";
	}

	public static String GET_WS_FEEDBACK_OF_STUDENT(long idCourse) {
		return "/rest/commento/" + String.valueOf(idCourse) + "/me";
	}

	public static final String POST_WS_MY_FEEDBACK = "/rest/commento";

	// gds///////////////////////////////////////////////////////////////
	public static String GET_WS_MY_GDS = "/rest/gruppodistudio/me";

	public static String POST_ADD_NEW_GDS = "/rest/gruppodistudio/add";

	// ritorna i gruppi a cui uno studente può iscriversi//////////////////////
	public static String GET_WS_FIND_GDS = "/rest/gruppodistudio/find";

	public static final String GET_WS_FIND_GDS_OF_COURSE(long idad) {
		return "/rest/gruppodistudio/find/" + idad;
	}

	public static String POST_ABANDON_GDS = "/rest/gruppodistudio/delete/me";

	public static String GET_WS_ALLGDS = "/rest/gruppodistudio/all";

	public static String GET_WS_GDS_BY_COURSE(long idCourse) {
		return "/rest/gruppodistudio/" + String.valueOf(idCourse);
	}

	public static String GET_WS_GDS_CORSO_ME(long idCourse) {
		return "/rest/gruppodistudio/" + String.valueOf(idCourse) + "/me";
	}

	public static String POST_ACCEPT_GDS = "/rest/gruppodistudio/accept";

	// AttivitaDiStudio
	// //////////////////////////////////////////////////////////
	public static String GET_CONTEXTUAL_ATTIVITASTUDIO(long idGDS) {
		return "/rest/attivitadistudio/" + String.valueOf(idGDS);
	}

	public static String POST_ATTIVITASTUDIO_ADD = "/rest/attivitadistudio/add";

	public static String DELETE_ATTIVITASTUDIO = "/rest/attivitadistudio/delete";

	public static final String POST_WS_CHANGE_ATTIVITASTUDIO(long dateold,
			long fromold, long toold) {
		return "/rest/attivitadistudio/change/date/" + dateold + "/from/"
				+ fromold + "/to/" + toold;
	}

}