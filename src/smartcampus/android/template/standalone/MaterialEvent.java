package smartcampus.android.template.standalone;

import java.util.Date;


public class MaterialEvent {
	private Date date;
	private String name;
	private int icon;
	
	
	public MaterialEvent(Date date, String name, int icon) {
		super();
		this.date = date;
		this.name = name;
		this.icon = icon;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}
	
	
}
