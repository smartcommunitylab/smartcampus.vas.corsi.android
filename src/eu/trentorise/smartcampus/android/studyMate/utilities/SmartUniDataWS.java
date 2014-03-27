package eu.trentorise.smartcampus.android.studyMate.utilities;

public final class SmartUniDataWS {

	public static final String URL_WS_SMARTUNI = "https://studymate-web.app.smartcampuslab.it";
	// public static String TOKEN = MyUniActivity.getAuthToken();
	public static final String TOKEN_NAME = "studymate";

	// Notices
	public static final String GET_WS_NOTIFICATIONS = "/notification/all";

	// Courses ///////////////////////////////////////////////////////////
	public static final String GET_WS_FREQUENTEDCOURSES = "/corsocarriera/me";
	public static final String GET_WS_FREQUENTEDCOURSES_SYNC = "/sync/corsocarriera/me";
	public static final String GET_WS_ALLCOURSES = "/corsolaurea/all";
	public static final String GET_WS_ALLCOURSES_ATT_DIDATTICA = "/attivitadidattica/all";
	public static final String POST_WS_COURSE_AS_FOLLOW = "/corso/seguo";
	public static final String DELETE_EVENTO = "/evento/delete";

	public static String POST_WS_COURSE_AS_FOLLOW_NEW(long adId) {
		return "/corsointeresse/" + String.valueOf(adId) + "/seguo";
	}

	public static String GET_WS_IF_FOLLOW(String adId) {
		return "/corsointeresse/" + adId + "/seguito";
	}

	public static String GET_WS_ALLCOURSES_OF_DEPARTMENT(long id_dep) {
		return "/attivitadidattica/dipartimento/" + String.valueOf(id_dep);
	}

	public static String GET_WS_ALLCOURSES_OF_DEGREE(String id_cds) {
		return "/attivitadidattica/corsolaurea/" + id_cds;
	}

	public static String GET_WS_COURSES_DETAILS(long adId) {
		return "/attivitadidattica/" + String.valueOf(adId);
	}

	public static final String GET_WS_MY_COURSES_INTEREST = "/corso/interesse/me";

	// Courses complete
	public static final String GET_WS_COURSE_COMPLETE_DATA(String idCourse) {
		return "/corso/" + idCourse;
	}

	// My courses passed
	public static final String GET_WS_MY_COURSES_PASSED = "/corsocarriera/passed/me";

	// My courses passed
	public static final String GET_WS_MY_COURSES_NOT_PASSED = "/corsocarriera/notpassed/me";

	public static final String GET_WS_COURSE_IS_PASSED(String adCod) {
		return "/corsocarriera/" + adCod + "/superato";
	}

	// Events /////////////////////////////////////////////////////////////
	public static final String GET_WS_MYEVENTS = "/evento/me";// "/evento/me";
	public static final String POST_NEW_EVENT = "/evento";

	public static final String GET_WS_EVENTS_OF_COURSE(String idCourse) {
		return "/evento/" + idCourse;
	}

	public static final String POST_WS_CHANGE_PERSONAL_EVENT(long date,
			long from, long to) {
		return "/evento/change/date/" + date + "/from/" + from + "/to/" + to;
	}

	public static final String GET_WS_NOTIFICATIONS(String type, long fromDate) {
		return "/notifiche/type/" + type + "/date/" + String.valueOf(fromDate);
	}

	// Student //////////////////////////////////////////////////////////
	public static final String GET_WS_STUDENT_DATA = "/sync/studente/me";

	// Student //////////////////////////////////////////////////////////
	public static final String GET_WS_STUDENT_DATA_NO_SYNC = "/studente/me";

	// Departments //////////////////////////////////////////////////////////
	public static final String GET_WS_DEPARTMENTS_ALL = "/dipartimento/all";

	// Courses degree //////////////////////////////////////////////////////
	public static String GET_WS_COURSESDEGREE_OF_DEPARTMENT(long idDepartment) {
		return "/corsolaurea/" + String.valueOf(idDepartment);
	}

	// Material for Courses
	// //////////////////////////////////////////////////////
	public static final String GET_MATERIAL_FOR_COURSE(long idCourse) {
		return "/risorsa/" + idCourse;
	}

	// Feedback //////////////////////////////////////////////////////////
	public static String GET_WS_FEEDBACK_OF_COURSE(long adId) {
		return "/corso/" + String.valueOf(adId) + "/commento/all";
	}

	// Feedback //////////////////////////////////////////////////////////
	public static String GET_WS_FEEDBACK_OF_STUDENT(long idCourse) {
		return "/commento/" + String.valueOf(idCourse) + "/me";
	}

	public static final String POST_WS_MY_FEEDBACK = "/commento";

	// gds///////////////////////////////////////////////////////////////
	public static String GET_WS_MY_GDS = "/gruppodistudio/me";

	// gds///////////////////////////////////////////////////////////////
	public static String POST_ADD_NEW_GDS = "/gruppodistudio/add";

	// gds///////////////////////////////////////////////////////////////
	public static String DELETE_ABANDON_GDS = "/gruppodistudio/delete/me";

	// gds///////////////////////////////////////////////////////////////
	public static String GET_WS_ALLGDS = "/gruppodistudio/all";

	// gds //////////////////////////////////////////////////////////
	public static String GET_WS_GDS_BY_COURSE(long idCourse) {
		return "/gruppodistudio/" + String.valueOf(idCourse);
	}

	// gds //////////////////////////////////////////////////////////
	public static String GET_WS_GDS_CORSO_ME(long idCourse) {
		return "/gruppodistudio/" + String.valueOf(idCourse) + "/me";
	}

	// gds //////////////////////////////////////////////////////////
	public static String POST_ACCEPT_GDS = "/gruppodistudio/accept";

	// gds //////////////////////////////////////////////////////////
	public static String GET_CONTEXTUAL_ATTIVITASTUDIO(long idGDS) {
		return "/attivitadistudio/" + String.valueOf(idGDS);
	}

	// gds //////////////////////////////////////////////////////////
	public static String POST_ATTIVITASTUDIO_ADD = "/attivitadistudio/add";

	// gds //////////////////////////////////////////////////////////
	public static String POST_ATTIVITASTUDIO_MODIFY = "/attivitadistudio/change";

	// gds //////////////////////////////////////////////////////////
	public static String DELETE_ATTIVITASTUDIO = "/attivitadistudio/delete";

}