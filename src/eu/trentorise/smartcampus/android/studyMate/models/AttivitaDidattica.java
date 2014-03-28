package eu.trentorise.smartcampus.android.studyMate.models;

import java.io.Serializable;

public class AttivitaDidattica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1878554247189449881L;
	private long adId;
	private String adCod;
	private String description;
	private long cds_id;
	private String ordYear;
	private String offYear;
	private Float valutazione_media;
	private Float rating_contenuto;
	private Float rating_carico_studio;
	private Float rating_lezioni;
	private Float rating_materiali;
	private Float rating_esame;
	private String courseDescription;

	public long getAdId() {
		return adId;
	}

	public void setAdId(long adId) {
		this.adId = adId;
	}

	public String getAdCod() {
		return adCod;
	}

	public void setAdCod(String adCod) {
		this.adCod = adCod;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getCds_id() {
		return cds_id;
	}

	public void setCds_id(long cds_id) {
		this.cds_id = cds_id;
	}

	public String getOrdYear() {
		return ordYear;
	}

	public void setOrdYear(String ordYear) {
		this.ordYear = ordYear;
	}

	public String getOffYear() {
		return offYear;
	}

	public void setOffYear(String offYear) {
		this.offYear = offYear;
	}

	public Float getValutazione_media() {
		return valutazione_media;
	}

	public void setValutazione_media(Float valutazione_media) {
		this.valutazione_media = valutazione_media;
	}

	public Float getRating_contenuto() {
		return rating_contenuto;
	}

	public void setRating_contenuto(Float rating_contenuto) {
		this.rating_contenuto = rating_contenuto;
	}

	public Float getRating_carico_studio() {
		return rating_carico_studio;
	}

	public void setRating_carico_studio(Float rating_carico_studio) {
		this.rating_carico_studio = rating_carico_studio;
	}

	public Float getRating_lezioni() {
		return rating_lezioni;
	}

	public void setRating_lezioni(Float rating_lezioni) {
		this.rating_lezioni = rating_lezioni;
	}

	public Float getRating_materiali() {
		return rating_materiali;
	}

	public void setRating_materiali(Float rating_materiali) {
		this.rating_materiali = rating_materiali;
	}

	public Float getRating_esame() {
		return rating_esame;
	}

	public void setRating_esame(Float rating_esame) {
		this.rating_esame = rating_esame;
	}

	public String getCourseDescription() {
		return courseDescription;
	}

	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}

}
