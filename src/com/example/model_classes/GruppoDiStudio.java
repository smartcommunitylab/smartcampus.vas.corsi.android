package com.example.model_classes;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;

// TODO: Auto-generated Javadoc
/**
 * The Class GruppoDiStudio.
 */
public class GruppoDiStudio {

	/** The materia. */
	String materia;

	/** The nome. */
	String nome;

	/** The membri. */
	ArrayList<Studente> membri;

	/** The servizi_monitorati. */
	ArrayList<Servizio> servizi_monitorati;

	/** The attivita_studio. */
	ArrayList<AttivitaStudio> attivita_studio;

	/** The anno. */
	int anno;

	ArrayList<ChatObject> forum;
	
	Drawable logo;

	/**
	 * Instantiates a new gruppo di studio.
	 * 
	 * @param materia
	 *            the materia
	 * @param nome
	 *            the nome
	 * @param membri
	 *            the membri
	 * @param servizi_monitorati
	 *            the servizi_monitorati
	 * @param attivita_studio
	 *            the attivita_studio
	 * @param anno
	 *            the anno
	 */
	public GruppoDiStudio(String materia, String nome,
			ArrayList<Studente> membri, ArrayList<Servizio> servizi_monitorati,
			ArrayList<AttivitaStudio> attivita_studio, int anno, Drawable logo) {
		super();
		this.materia = materia;
		this.nome = nome;
		this.membri = membri;
		this.servizi_monitorati = servizi_monitorati;
		this.attivita_studio = attivita_studio;
		this.anno = anno;
		this.logo = logo;
	}

	public Drawable getLogo() {
		return logo;
	}

	public void setLogo(Drawable logo) {
		this.logo = logo;
	}

	/**
	 * Gets the anno.
	 * 
	 * @return the anno
	 */
	public int getAnno() {
		return anno;
	}

	/**
	 * Sets the anno.
	 * 
	 * @param anno
	 *            the new anno
	 */
	public void setAnno(int anno) {
		this.anno = anno;
	}

	public ArrayList<ChatObject> getForum() {
		return forum;
	}

	public void setForum(ArrayList<ChatObject> forum) {
		this.forum = forum;
	}

	/**
	 * Gets the nome.
	 * 
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Sets the nome.
	 * 
	 * @param nome
	 *            the new nome
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Gets the materia.
	 * 
	 * @return the materia
	 */
	public String getMateria() {
		return materia;
	}

	/**
	 * Sets the materia.
	 * 
	 * @param materia
	 *            the new materia
	 */
	public void setMateria(String materia) {
		this.materia = materia;
	}

	/**
	 * Gets the membri.
	 * 
	 * @return the membri
	 */
	public ArrayList<Studente> getMembri() {
		return membri;
	}

	/**
	 * Sets the membri.
	 * 
	 * @param membri
	 *            the new membri
	 */
	public void setMembri(ArrayList<Studente> membri) {
		this.membri = membri;
	}

	/**
	 * Gets the servizi_monitorati.
	 * 
	 * @return the servizi_monitorati
	 */
	public ArrayList<Servizio> getServizi_monitorati() {
		return servizi_monitorati;
	}

	/**
	 * Sets the servizi_monitorati.
	 * 
	 * @param servizi_monitorati
	 *            the new servizi_monitorati
	 */
	public void setServizi_monitorati(ArrayList<Servizio> servizi_monitorati) {
		this.servizi_monitorati = servizi_monitorati;
	}

	/**
	 * Gets the attivita_studio.
	 * 
	 * @return the attivita_studio
	 */
	public ArrayList<AttivitaStudio> getAttivita_studio() {
		return attivita_studio;
	}

	/**
	 * Sets the attivita_studio.
	 * 
	 * @param attivita_studio
	 *            the new attivita_studio
	 */
	public void setAttivita_studio(ArrayList<AttivitaStudio> attivita_studio) {
		this.attivita_studio = attivita_studio;
	}

}
