package eu.trentorise.smartcampus.android.studyMate.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GruppoDiStudio implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1179725685319910150L;

	private long id;

	private String nome;

	private long corso;

	private String idsStudenti;

	private String materia;

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

	// public List<Long> getIdStudentiGruppo() {
	// return this.convertIdsAllStudentsToList();
	// }

	public Byte[] getLogo() {
		return logo;
	}

	public void setLogo(Byte[] logo) {
		this.logo = logo;
	}

	public void setStudentiGruppo(List<Studente> studentiGruppo) {
		this.studentiGruppo = studentiGruppo;
	}

	public List<Studente> getStudentiGruppo() {
		return studentiGruppo;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public List<String> getListInvited(long idStudente) {
		return convertIdsInvitedToList(this.getIdsStudenti(), idStudente);
	}

	public void setIfVisibleFromNumMembers() {
		int numMembers = this.getIdsStudenti().split(",").length;

		if (numMembers >= 1)
			this.setVisible(true);
		else
			this.setVisible(false);

	}

	// chiamata soltanto alla creazione di un nuovo gruppo
	public void initStudenteGruppo(long idStudenteDaAggiungere) {
		// TODO Auto-generated method stub
		this.setIdsStudenti(String.valueOf(idStudenteDaAggiungere) + ",");
	}

	public void addStudenteGruppo(long idStudenteDaAggiungere) {
		// TODO Auto-generated method stub
		this.setIdsStudenti(this.getIdsStudenti()
				+ String.valueOf(idStudenteDaAggiungere) + ",");
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

	// public List<Long> convertIdsAllStudentsToList() {
	// String[] listIds = null;
	// List<Long> listIdsInvited = new ArrayList<Long>();
	//
	// listIds = this.idsStudenti.split(",");
	//
	// for (String id : listIds) {
	//
	// listIdsInvited.add(Long.parseLong(id));
	//
	// }
	//
	// return listIdsInvited;
	// }

	public String getMateria() {
		return materia;
	}

	public void setMateria(String materia) {
		this.materia = materia;
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

	public boolean canRemoveGruppoDiStudioIfVoid() {
		String[] listIds = this.getIdsStudenti().split(",");

		if (listIds[0] == "")
			return true;
		else
			return false;
	}

}