package eu.trentorise.smartcampus.android.studyMate.models;

public class ChatMessage {

	private long id;

	private String nome_studente;
	
	private long id_studente;

	private Long data;
	
	private String testo;
	
	private long gds;
	
	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public ChatMessage() {
	}

	public Long getData() {
		return data;
	}

	public void setData(Long data) {
		this.data = data;
	}

	public String getNome_studente() {
		return nome_studente;
	}

	public void setNome_studente(String nome_studente) {
		this.nome_studente = nome_studente;
	}

	public long getId_studente() {
		return id_studente;
	}

	public void setId_studente(long id_studente) {
		this.id_studente = id_studente;
	}

	public long getGds() {
		return gds;
	}

	public void setGds(long gds) {
		this.gds = gds;
	}


	
}
