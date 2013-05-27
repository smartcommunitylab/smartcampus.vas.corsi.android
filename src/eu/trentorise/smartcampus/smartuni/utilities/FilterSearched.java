package eu.trentorise.smartcampus.smartuni.utilities;

import java.util.ArrayList;
import java.util.List;

import eu.trentorise.smartcampus.smartuni.models.CourseLite;

public class FilterSearched {

	ArrayList<CourseLite> coursesList;

	public FilterSearched() {
		// TODO Auto-generated constructor stub
	}

	public ArrayList<CourseLite> filterListWithCourseSearched(String searched,
			List<CourseLite> startList) {

		int length = startList.size();
		coursesList = new ArrayList<CourseLite>(length);
		
		if (searched.equals("")) {
			for (int k = 0; k < length; k++) {
				coursesList.add(startList.get(k));
			}
		} else {
			for (int k = 0; k < length; k++) {
				if (startList.get(k).getName().toLowerCase().contains(searched)) {
					coursesList.add(startList.get(k));
				}
			}
		}

		return coursesList;
	}

}
