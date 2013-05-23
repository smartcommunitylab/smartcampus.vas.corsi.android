package eu.trentorise.smartcampus.smartuni.models;

import android.widget.RatingBar;

public class FeedbackRowGroup {

	public Author author;
	public int rating;
	public String comment;

	public FeedbackRowGroup() {
		// TODO Auto-generated constructor stub
	}

	public Author getAuthor() {
		return author;
	}

	public int getRating() {
		return rating;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
