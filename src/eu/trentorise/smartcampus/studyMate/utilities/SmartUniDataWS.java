package eu.trentorise.smartcampus.studyMate.utilities;

import smartcampus.android.studyMate.start.MyUniActivity;

public final class SmartUniDataWS {

	public static final String URL_WS_SMARTUNI = "http://smartcampusvascorsiweb.app.smartcampuslab.it";
	// public static final String URL_WS_SMARTUNI =
	// "http://unicorsi.app.smartcampuslab.it";
	// public static String TOKEN = "aee58a92-d42d-42e8-b55e-12e4289586fc";
	public static String TOKEN = MyUniActivity.userAuthToken;
	public static final String TOKEN_NAME = "test smartcampus";

	// Notices
	public static final String GET_WS_NOTIFICATIONS = "/notification/all";

	// Courses ///////////////////////////////////////////////////////////
	public static final String GET_WS_FREQUENTEDCOURSES = "/corso/me";
	public static final String GET_WS_ALLCOURSES = "/corso/all";

	public static String GET_WS_ALLCOURSES_OF_DEPARTMENT(long id_department) {
		return "/corso/dipartimento/" + String.valueOf(id_department);
	}

	public static String GET_WS_ALLCOURSES_OF_DEGREE(long id_degree) {
		return "/corso/corsolaurea/" + String.valueOf(id_degree);
	}

	// Courses complete
	public static final String GET_WS_COURSE_COMPLETE_DATA(String idCourse) {
		return "/corso/" + idCourse;
	}

	// My courses passed
	public static final String GET_WS_MY_COURSES_PASSED = "/corso/superati/me";

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
		// return "/commento/" + String.valueOf(idCourse);
	}

	// Feedback //////////////////////////////////////////////////////////
	public static String GET_WS_FEEDBACK_OF_STUDENT(long idStudent,
			long idCourse) {
		return "/commento/" + String.valueOf(idCourse) + "/"
				+ String.valueOf(idStudent);
	}

	public static final String POST_WS_MY_FEEDBACK = "/commento";

}
