package eu.trentorise.smartcampus.smartuni.utilities;

import java.util.ArrayList;
import java.util.List;

import eu.trentorise.smartcampus.smartuni.models.CorsoLite;

public class FilterSearched {

	ArrayList<CorsoLite> coursesList;

	public FilterSearched() {
		// TODO Auto-generated constructor stub
	}

	// filtro in base a quello che ho cercato controllando se le parole sono
	// contenute nella lista
	public ArrayList<CorsoLite> filterListWithCourseSearched(String searched,
			List<CorsoLite> startList) {

		int length = startList.size();
		coursesList = new ArrayList<CorsoLite>(length);

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
