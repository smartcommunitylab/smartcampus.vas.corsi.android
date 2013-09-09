package eu.trentorise.smartcampus.studyMate.utilities;

import java.io.Serializable;

public class MaterialItem implements Serializable {
	private String title;
	private String content;
	private int icon;
	private String hash;

	public MaterialItem(String title, String content, int icon, String hash) {
		super();
		this.title = title;
		this.content = content;
		this.icon = icon;
		this.setHash(hash);
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

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	

}
