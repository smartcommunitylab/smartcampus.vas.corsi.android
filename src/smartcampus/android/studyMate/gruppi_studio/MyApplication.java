package smartcampus.android.studyMate.gruppi_studio;

import java.util.ArrayList;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
	private static Context context;
	private static ArrayList<Object> contextualCollection = new ArrayList<Object>();

	public void onCreate() {
		super.onCreate();
		MyApplication.context = getApplicationContext();
	}

	public static Context getAppContext() {
		return MyApplication.context;
	}

	public static ArrayList<Object> getContextualCollection() {
		return contextualCollection;
	}

}
