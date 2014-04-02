package eu.trentorise.smartcampus.android.studyMate.models;

import java.io.Serializable;
import java.util.List;

public class Studente implements Serializable {

	private long id;
	private String nome;
	private String cognome;
	private String anno_corso;
	private String enrollmentYear;
	private String nation;
	private String academicYear;
	private String suplementaryYear;
	private String cfu;
	private String cfuTotal;
	private String marksNumber;
	private String marksAverage;
	private String gender;
	private String dateOfBirth;
	private String phone;
	private String mobile;
	private String address;
	private String cds;
	private String email;
	private long userSocialId;
	private String idsGruppiDiStudio;
	private List<GruppoDiStudio> gruppiDiStudio;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getAnno_corso() {
		return anno_corso;
	}

	public void setAnno_corso(String anno_corso) {
		this.anno_corso = anno_corso;
	}

	public String getEnrollmentYear() {
		return enrollmentYear;
	}

	public void setEnrollmentYear(String enrollmentYear) {
		this.enrollmentYear = enrollmentYear;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public String getSuplementaryYear() {
		return suplementaryYear;
	}

	public void setSuplementaryYear(String suplementaryYear) {
		this.suplementaryYear = suplementaryYear;
	}

	public String getCfu() {
		return cfu;
	}

	public void setCfu(String cfu) {
		this.cfu = cfu;
	}

	public String getCfuTotal() {
		return cfuTotal;
	}

	public void setCfuTotal(String cfuTotal) {
		this.cfuTotal = cfuTotal;
	}

	public String getMarksNumber() {
		return marksNumber;
	}

	public void setMarksNumber(String marksNumber) {
		this.marksNumber = marksNumber;
	}

	public String getMarksAverage() {
		return marksAverage;
	}

	public void setMarksAverage(String marksAverage) {
		this.marksAverage = marksAverage;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCds() {
		return cds;
	}

	public void setCds(String cds) {
		this.cds = cds;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getUserSocialId() {
		return userSocialId;
	}

	public void setUserSocialId(long userSocialId) {
		this.userSocialId = userSocialId;
	}

	public String getIdsGruppiDiStudio() {
		return idsGruppiDiStudio;
	}

	public void setIdsGruppiDiStudio(String idsGruppiDiStudio) {
		this.idsGruppiDiStudio = idsGruppiDiStudio;
	}

	public List<GruppoDiStudio> getGruppiDiStudio() {
		return gruppiDiStudio;
	}

	public void setGruppiDiStudio(List<GruppoDiStudio> gruppiDiStudio) {
		this.gruppiDiStudio = gruppiDiStudio;
	}

}