package eu.trentorise.smartcampus.android.studyMate.utilities;

public final class SmartUniDataWS {

	public static final String URL_WS_SMARTUNI = "http://smartcampusvascorsiweb.app.smartcampuslab.it";
	// public static String TOKEN = MyUniActivity.getAuthToken();
	public static final String TOKEN_NAME = "studymate";

	// Notices
	public static final String GET_WS_NOTIFICATIONS = "/notification/all";

	// Courses ///////////////////////////////////////////////////////////
	public static final String GET_WS_FREQUENTEDCOURSES = "/corso/me";
	public static final String GET_WS_ALLCOURSES = "/corso/all";

	public static final String POST_WS_COURSE_AS_FOLLOW = "/corso/seguo";

	public static String GET_WS_ALLCOURSES_OF_DEPARTMENT(long id_department) {
		return "/corso/dipartimento/" + String.valueOf(id_department);
	}

	public static String GET_WS_ALLCOURSES_OF_DEGREE(String id_degree) {
		return "/corso/corsolaurea/" + id_degree;
	}

	public static final String GET_WS_MY_COURSES_INTEREST = "/corso/interesse/me";

	// Courses complete
	public static final String GET_WS_COURSE_COMPLETE_DATA(String idCourse) {
		return "/corso/" + idCourse;
	}

	// My courses passed
	public static final String GET_WS_MY_COURSES_PASSED = "/corso/superati/me";

	public static final String GET_WS_COURSE_IS_PASSED(String idCourse) {
		return "/corso/superato/" + idCourse;
	}

	// Events /////////////////////////////////////////////////////////////
	public static final String GET_WS_MYEVENTS = "/evento/me";
	public static final String POST_NEW_EVENT = "/evento";

	public static final String GET_WS_EVENTS_OF_COURSE(String idCourse) {
		return "/evento/" + idCourse;
	}

	// Student //////////////////////////////////////////////////////////
	public static final String GET_WS_STUDENT_DATA = "/studente/me";

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
	public static String GET_WS_FEEDBACK_OF_COURSE(long idCourse) {
		return "/corso/" + String.valueOf(idCourse) + "/commento/all";
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
	public static String POST_ATTIVITASTUDIO_DELETE = "/attivitadistudio/delete";

}