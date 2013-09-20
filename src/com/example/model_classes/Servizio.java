package com.example.model_classes;



public class Servizio {
	
	public enum TipoServizio {PRENOTAZIONE_AULE, MENSA,TUTORING,BIBLIOTECA};
	
	TipoServizio tipo;
	boolean isActive;
	//da vedere un po' come fare il design di sta roba anche in base al backend
	
	public TipoServizio getTipo() {
		return tipo;
	}
	public Servizio(TipoServizio tipo, boolean isActive) {
		super();
		this.tipo = tipo;
		this.isActive = isActive;
	}
	
	public void setTipo(TipoServizio tipo) {
		this.tipo = tipo;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	

}
