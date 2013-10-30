package eu.trentorise.smartcampus.android.studyMate.models;

//già da web
public class AttivitaDiStudio extends Evento {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// gds di riferimentoì

	private long gruppo;

	// topic

	private String topic;

	public AttivitaDiStudio() {
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public long getGruppo() {
		return gruppo;
	}

	public void setGruppo(long gruppo) {
		this.gruppo = gruppo;
	}

	// public Collection<Allegato> getAllegato() {
	// return allegato;
	// }
	//
	//
	// public void setAllegato(Collection<Allegato> allegato) {
	// this.allegato = allegato;
	// }

	// public Collection<Servizio> getServizio() {
	// return servizio;
	// }
	//
	//
	// public void setServizio(Collection<Servizio> servizio) {
	// this.servizio = servizio;
	// }

}
