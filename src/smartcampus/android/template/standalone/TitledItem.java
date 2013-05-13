package smartcampus.android.template.standalone;

public class TitledItem {
	private String title;
	private String content;
	
	public TitledItem() {
		this.title = null;
		this.content = null;
	}
	
	public TitledItem(String title, String content) {
		super();
		this.title = title;
		this.content = content;
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
	
}
