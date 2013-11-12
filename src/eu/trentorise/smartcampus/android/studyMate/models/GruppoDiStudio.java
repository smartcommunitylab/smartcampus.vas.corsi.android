package eu.trentorise.smartcampus.android.studyMate.models;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class GruppoDiStudio.
 */

// già da web
public class GruppoDiStudio {
	// id del gruppo

	private long id;

	// Nome del gruppo

	private String nome;

	// corso di riferimento

	private long corso;

	private String idsStudenti;

	private List<Studente> studentiGruppo;

	private Byte[] logo;

	private boolean visible;

	public GruppoDiStudio() {
		this.idsStudenti = "";
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCorso() {
		return corso;
	}

	public void setCorso(long corso) {
		this.corso = corso;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public long getId() {
		return id;
	}

	public String getIdsStudenti() {
		return idsStudenti;
	}

	public void setIdsStudenti(String idsStudenti) {
		this.idsStudenti = idsStudenti;
	}

	public List<Studente> getStudentiGruppo() {
		return studentiGruppo;
	}

	 public Byte[] getLogo() {
	 return logo;
	 }
	
	
	 public void setLogo(Byte[] logo) {
	 this.logo = logo;
	 }

	public void setStudentiGruppo(List<Studente> studentiGruppo) {
		this.studentiGruppo = studentiGruppo;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public void setIfVisibleFromNumMembers() {
		int numMembers = this.getIdsStudenti().split(",").length;

		if (numMembers >= 2)
			this.setVisible(true);
		else
			this.setVisible(false);

	}

	public void addStudenteGruppo(long idStudenteDaAggiungere) {
		// TODO Auto-generated method stub
		this.setIdsStudenti(this.getIdsStudenti()
				+ String.valueOf(idStudenteDaAggiungere) + ",");
	}

	// chiamata soltanto alla creazione di un nuovo gruppo
	public void initStudenteGruppo(long idStudenteDaAggiungere) {
		// TODO Auto-generated method stub
		this.setIdsStudenti(String.valueOf(idStudenteDaAggiungere) + ",");
	}

	public void removeStudenteGruppo(long id2) {
		// TODO Auto-generated method stub
		String studentiGruppoIds = null;

		if (this.getIdsStudenti() == null)
			return;

		studentiGruppoIds = this.getIdsStudenti();

		String[] listS = studentiGruppoIds.split(",");

		String studentiGruppoAggiornato = "";

		for (String s : listS) {
			if (!s.equals(String.valueOf(id2))) {
				studentiGruppoAggiornato = studentiGruppoAggiornato.concat(s
						.toString() + ",");
			}
		}
		this.setIdsStudenti(studentiGruppoAggiornato);
	}

	public boolean canRemoveGruppoDiStudioIfVoid() {
		String[] listIds = this.getIdsStudenti().split(",");

		if (listIds[0] == "")
			return true;
		else
			return false;
	}

	public boolean isContainsStudente(long idStudente) {
		// TODO Auto-generated method stub
		String studentiGruppoIds = null;
		studentiGruppoIds = this.getIdsStudenti();

		String[] listS = studentiGruppoIds.split(",");

		for (String s : listS) {
			if (s.equals(String.valueOf(idStudente))) {
				return true;
			}
		}

		return false;
	}

	public List<String> getListInvited(long idStudente) {
		return convertIdsInvitedToList(this.getIdsStudenti(), idStudente);
	}

	public List<String> convertIdsInvitedToList(String ids, long idStudente) {
		String[] listIds = null;
		List<String> listIdsInvited = new ArrayList<String>();

		listIds = ids.split(",");

		for (String id : listIds) {

			if (!id.equals(String.valueOf(idStudente))) {
				listIdsInvited.add(id);
			}

		}

		return listIdsInvited;
	}

}
