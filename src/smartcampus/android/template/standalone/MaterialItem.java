package smartcampus.android.template.standalone;

public class MaterialItem {
	private String title;
	private String content;
	private int icon;

	public MaterialItem(String title, String content, int icon) {
		super();
		this.title = title;
		this.content = content;
		this.icon = icon;
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

}
