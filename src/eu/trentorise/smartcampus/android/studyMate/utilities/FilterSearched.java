package eu.trentorise.smartcampus.android.studyMate.utilities;

import java.util.ArrayList;
import java.util.List;

import eu.trentorise.smartcampus.android.studyMate.models.AttivitaDidattica;

public class FilterSearched {

	ArrayList<AttivitaDidattica> coursesList;

	public FilterSearched() {
	}

	// filtro in base a quello che ho cercato controllando se le parole sono
	// contenute nella lista
	public ArrayList<AttivitaDidattica> filterListWithCourseSearched(
			String searched, List<AttivitaDidattica> startList) {

		int length = startList.size();
		coursesList = new ArrayList<AttivitaDidattica>(length);

		if (searched.equals("")) {
			for (int k = 0; k < length; k++) {
				coursesList.add(startList.get(k));
			}
		} else {
			for (int k = 0; k < length; k++) {
				if (startList.get(k).getDescription().toLowerCase()
						.contains(searched)) {
					coursesList.add(startList.get(k));
				}
			}
		}

		return coursesList;
	}

}
