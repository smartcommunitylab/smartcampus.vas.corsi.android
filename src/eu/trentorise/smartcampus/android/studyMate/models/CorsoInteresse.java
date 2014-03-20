package eu.trentorise.smartcampus.android.studyMate.models;


public class CorsoInteresse {

		private long id;

		private AttivitaDidattica attivitaDidattica; 
		
		private long studenteId;
		
		private boolean isCorsoCarriera;

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public AttivitaDidattica getAttivitaDidattica() {
			return attivitaDidattica;
		}

		public void setAttivitaDidattica(AttivitaDidattica attivitaDidattica) {
			this.attivitaDidattica = attivitaDidattica;
		}

		public long getStudenteId() {
			return studenteId;
		}

		public void setStudenteId(long studenteId) {
			this.studenteId = studenteId;
		}

		public boolean isCorsoCarriera() {
			return isCorsoCarriera;
		}

		public void setCorsoCarriera(boolean isCorsoCarriera) {
			this.isCorsoCarriera = isCorsoCarriera;
		}
		
		
		

}
