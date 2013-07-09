package smartcampus.android.template.standalone;

import java.io.Serializable;

public class MaterialItem implements Serializable{
	private String title;
	private String content;
	private int icon;
	private int level;

	public MaterialItem(String title, String content, int icon, int level) {
		super();
		this.title = title;
		this.content = content;
		this.icon = icon;
		this.level=level;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
