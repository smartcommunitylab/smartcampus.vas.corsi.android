package eu.trentorise.smartcampus.studyMate.models;

public class FeedbackRowGroup {

	public Author author;
	public float rating;
	public String comment;
	public float rating_contenuti;
	public float rating_cfu;
	public float rating_lezioni;
	public float rating_materiale;
	public float rating_esame;

	public FeedbackRowGroup() {
		// TODO Auto-generated constructor stub
	}

	public Author getAuthor() {
		return author;
	}

	public float getRating() {
		return rating;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public float getRating_contenuti() {
		return rating_contenuti;
	}

	public void setRating_contenuti(float rating_contenuti) {
		this.rating_contenuti = rating_contenuti;
	}

	public float getRating_cfu() {
		return rating_cfu;
	}

	public void setRating_cfu(float rating_cfu) {
		this.rating_cfu = rating_cfu;
	}

	public float getRating_lezioni() {
		return rating_lezioni;
	}

	public void setRating_lezioni(float rating_lezioni) {
		this.rating_lezioni = rating_lezioni;
	}

	public float getRating_materiale() {
		return rating_materiale;
	}

	public void setRating_materiale(float rating_materiale) {
		this.rating_materiale = rating_materiale;
	}

	public float getRating_esame() {
		return rating_esame;
	}

	public void setRating_esame(float rating_esame) {
		this.rating_esame = rating_esame;
	}

}
