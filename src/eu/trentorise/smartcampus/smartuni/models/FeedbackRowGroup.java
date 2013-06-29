package eu.trentorise.smartcampus.smartuni.models;


public class FeedbackRowGroup {

	public Author author;
	public long rating;
	public String comment;
	public long rating_contenuti;
	public long rating_cfu;
	public long rating_lezioni;
	public long rating_materiale;
	public long rating_esame;
	
	public FeedbackRowGroup() {
		// TODO Auto-generated constructor stub
	}

	public Author getAuthor() {
		return author;
	}

	public long getRating() {
		return rating;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public void setRating(long rating) {
		this.rating = rating;
	}
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

	public long getRating_contenuti() {
		return rating_contenuti;
	}

	public void setRating_contenuti(long rating_contenuti) {
		this.rating_contenuti = rating_contenuti;
	}

	public long getRating_cfu() {
		return rating_cfu;
	}

	public void setRating_cfu(long rating_cfu) {
		this.rating_cfu = rating_cfu;
	}

	public long getRating_lezioni() {
		return rating_lezioni;
	}

	public void setRating_lezioni(long rating_lezioni) {
		this.rating_lezioni = rating_lezioni;
	}

	public long getRating_materiale() {
		return rating_materiale;
	}

	public void setRating_materiale(long rating_materiale) {
		this.rating_materiale = rating_materiale;
	}

	public long getRating_esame() {
		return rating_esame;
	}

	public void setRating_esame(long rating_esame) {
		this.rating_esame = rating_esame;
	}
	
	
	
	
}
