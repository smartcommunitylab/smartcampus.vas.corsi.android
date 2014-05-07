package eu.trentorise.smartcampus.android.studyMate.utilities;

public final class SmartUniDataWS {

	// general
	public static final String URL_WS_SMARTUNI = "http://studymate-test.app.smartcampuslab.it";
	public static final String TOKEN_NAME = "studymate";

	// Notification
	public static final String GET_WS_NOTIFICATIONS = "/notification/all";

	public static final String GET_WS_NOTIFICATIONS(String type, long fromDate) {
		return "/notifiche/type/" + type + "/date/" + String.valueOf(fromDate);
	}

	// corsocarriera
	public static final String GET_WS_FREQUENTEDCOURSES = "/corsocarriera/me";

	public static final String GET_WS_FREQUENTEDCOURSES_SYNC = "/sync/corsocarriera/me";

	public static final String GET_WS_MY_COURSES_PASSED = "/corsocarriera/passed/me";

	public static final String GET_WS_MY_COURSES_NOT_PASSED = "/corsocarriera/notpassed/me";

	public static final String GET_WS_COURSE_IS_PASSED(String adCod) {
		return "/corsocarriera/" + adCod + "/superato";
	}

	// corsointeresse
	public static final String POST_WS_COURSE_AS_FOLLOW = "/corso/seguo";

	public static String POST_WS_COURSE_AS_FOLLOW_NEW(long adId) {
		return "/corsointeresse/" + String.valueOf(adId) + "/seguo";
	}

	public static String GET_WS_IF_FOLLOW(String adId) {
		return "/corsointeresse/" + adId + "/seguito";
	}

	public static String POST_WS_COURSE_UNFOLLOW(String adCod) {
		return "/corsointeresse/" + String.valueOf(adCod) + "/delete";
	}

	public static final String GET_WS_MY_COURSES_INTEREST = "/corso/interesse/me";

	// AttivitaDidattica

	public static final String GET_WS_ALLCOURSES_ATT_DIDATTICA = "/attivitadidattica/all";

	public static String GET_WS_ALLCOURSES_OF_DEPARTMENT(long id_dep) {
		return "/attivitadidattica/dipartimento/" + String.valueOf(id_dep);
	}

	public static String GET_WS_ALLCOURSES_OF_DEGREE(String id_cds) {
		return "/attivitadidattica/corsolaurea/" + id_cds;
	}

	public static String GET_WS_COURSES_DETAILS(long adId) {
		return "/attivitadidattica/" + String.valueOf(adId);
	}

	public static String GET_WS_COURSE_BY_COD(String adCod) {
		return "/attivitadidattica/adcod/" + String.valueOf(adCod);
	}

	public static final String GET_WS_COURSE_COMPLETE_DATA(String idCourse) {
		return "/corso/" + idCourse;
	}

	// CorsoLaurea ///////////////////////////////////////////////////////////

	public static final String GET_WS_ALLCOURSES = "/corsolaurea/all";

	public static String GET_WS_COURSESDEGREE_OF_DEPARTMENT(long idDepartment) {
		return "/corsolaurea/" + String.valueOf(idDepartment);
	}

	// Studente //////////////////////////////////////////////////////////
	public static final String GET_WS_STUDENT_DATA = "/sync/studente/me";

	public static final String GET_WS_STUDENT_DATA_NO_SYNC = "/studente/me";

	public static final String GET_STUDENTE(long id_stud) {
		return "/studente/" + id_stud;
	}

	// Evento
	public static final String GET_WS_MYEVENTS = "/evento/me";

	public static final String POST_NEW_EVENT = "/evento";

	public static final String DELETE_EVENTO = "/evento/delete";

	public static final String GET_WS_EVENTS_OF_COURSE(String idCourse) {
		return "/evento/" + idCourse;
	}

	public static final String POST_WS_CHANGE_PERSONAL_EVENT(long date,
			long from, long to) {
		return "/evento/change/date/" + date + "/from/" + from + "/to/" + to;
	}

	// Dipartimento //////////////////////////////////////////////////////////
	public static final String GET_WS_DEPARTMENTS_ALL = "/dipartimento/all";

	// Commento //////////////////////////////////////////////////////////
	public static String GET_WS_FEEDBACK_OF_COURSE(long adId) {
		return "/corso/" + String.valueOf(adId) + "/commento/all";
	}

	public static String GET_WS_FEEDBACK_OF_STUDENT(long idCourse) {
		return "/commento/" + String.valueOf(idCourse) + "/me";
	}

	public static final String POST_WS_MY_FEEDBACK = "/commento";

	// gds///////////////////////////////////////////////////////////////
	public static String GET_WS_MY_GDS = "/gruppodistudio/me";

	public static String POST_ADD_NEW_GDS = "/gruppodistudio/add";

	// ritorna i gruppi a cui uno studente può iscriversi//////////////////////
	public static String GET_WS_FIND_GDS = "/gruppodistudio/find";

	public static final String GET_WS_FIND_GDS_OF_COURSE(long idad) {
		return "gruppodistudio/find/" + idad;
	}

	public static String POST_ABANDON_GDS = "/gruppodistudio/delete/me";

	public static String GET_WS_ALLGDS = "/gruppodistudio/all";

	public static String GET_WS_GDS_BY_COURSE(long idCourse) {
		return "/gruppodistudio/" + String.valueOf(idCourse);
	}

	public static String GET_WS_GDS_CORSO_ME(long idCourse) {
		return "/gruppodistudio/" + String.valueOf(idCourse) + "/me";
	}

	public static String POST_ACCEPT_GDS = "/gruppodistudio/accept";

	// AttivitaDiStudio
	// //////////////////////////////////////////////////////////
	public static String GET_CONTEXTUAL_ATTIVITASTUDIO(long idGDS) {
		return "/attivitadistudio/" + String.valueOf(idGDS);
	}

	public static String POST_ATTIVITASTUDIO_ADD = "/attivitadistudio/add";

	public static String DELETE_ATTIVITASTUDIO = "/attivitadistudio/delete";

	public static final String POST_WS_CHANGE_ATTIVITASTUDIO(long dateold,
			long fromold, long toold) {
		return "/attivitadistudio/change/date/" + dateold + "/from/" + fromold
				+ "/to/" + toold;
	}

}