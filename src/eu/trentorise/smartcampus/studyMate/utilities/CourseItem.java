package eu.trentorise.smartcampus.studyMate.utilities;

import java.text.SimpleDateFormat;

import eu.trentorise.smartcampus.studyMate.models.CourseEvent;

public class CourseItem extends TitledItem {

	private CourseEvent object;

	public CourseItem(CourseEvent obj) {
		super();

		this.object = obj;
	}

	@Override
	public String getTitle() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return dateFormat.format(object.getDate());
	}

	@Override
	public String getContent() {
		return object.getName();
	}

}
