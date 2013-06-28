package smartcampus.android.template.standalone;

import java.text.SimpleDateFormat;

public class CourseItem extends TitledItem
{

	private CourseEvent	object;

	public CourseItem(CourseEvent obj)
	{
		super();

		this.object = obj;
	}

	@Override
	public String getTitle()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return dateFormat.format(object.getDate());
	}

	@Override
	public String getContent()
	{
		return object.getName();
	}

}
