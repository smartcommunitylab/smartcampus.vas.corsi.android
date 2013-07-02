package eu.trentorise.smartcampus.smartuni.models;

public class Notice {

	public String id;
	public String description;
	public Author author;
	public String title;
	public String updateTime;
	public String timestamp;
	public String user;

	public Notice() {
		// TODO Auto-generated constructor stub
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Author getAuthor() {
		return author;
	}

	public String getTitle() {
		return title;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
}
