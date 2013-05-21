package eu.trentorise.smartcampus.smartuni.utilities;

public final class SmartUniDataWS {
	

	public static final String URL_WS_SMARTUNI = "http://unicorsi.app.smartcampuslab.it"; 
	public static final String TOKEN = "aee58a92-d42d-42e8-b55e-12e4289586fc";
	public static final String TOKEN_NAME = "test smartcampus";
	
	
	// Notices
	public static final String GET_WS_NOTIFICATIONS = "/notifiche/get"; 
	
	
	// Courses
	public static final String GET_WS_FREQUENTEDCOURSES = "/corsi/frequentati";
	public static final String GET_WS_ALLCOURSES = "/corsi/all";
	public static String GET_WS_ALLCOURSES_OF_DEPARTMENT(String department){
		return "/corsi/"+department;	
	}
	public static String GET_WS_ALLCOURSES_OF_DEGREE(String degree){
		return "/corsi/"+degree;	
	}
	
	// Courses complete
	public static final String GET_WS_COURSE_COMPLETE_DATA(String idCourse){
		return "/corso/"+idCourse;
	}
	
	
	// Events
	public static final String GET_WS_ALLEVENTS = "/events/all";
	
	
	
	
	
}
