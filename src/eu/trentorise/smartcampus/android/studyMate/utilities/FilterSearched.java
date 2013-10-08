package eu.trentorise.smartcampus.android.studyMate.utilities;

import java.util.ArrayList;
import java.util.List;

import eu.trentorise.smartcampus.android.studyMate.models.Corso;

public class FilterSearched {

	ArrayList<Corso> coursesList;

	public FilterSearched() {
		// TODO Auto-generated constructor stub
	}

	// filtro in base a quello che ho cercato controllando se le parole sono
	// contenute nella lista
	public ArrayList<Corso> filterListWithCourseSearched(String searched,
			List<Corso> startList) {

		int length = startList.size();
		coursesList = new ArrayList<Corso>(length);

		if (searched.equals("")) {
			for (int k = 0; k < length; k++) {
				coursesList.add(startList.get(k));
			}
		} else {
			for (int k = 0; k < length; k++) {
				if (startList.get(k).getNome().toLowerCase().contains(searched)) {
					coursesList.add(startList.get(k));
				}
			}
		}

		return coursesList;
	}

}
