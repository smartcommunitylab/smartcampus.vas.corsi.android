package eu.trentorise.smartcampus.android.studyMate.models;

import android.net.Uri;

public class Allegato {

	Uri uri_allegato;
	String nome_file;

	// dati e metadati vari insomma...

	public Allegato(Uri uri_allegato, String nome_file) {
		super();
		this.uri_allegato = uri_allegato;
		this.nome_file = nome_file;
	}

	public Uri getUri_allegato() {
		return uri_allegato;
	}

	public void setUri_allegato(Uri uri_allegato) {
		this.uri_allegato = uri_allegato;
	}

	public String getNome_file() {
		return nome_file;
	}

	public void setNome_file(String nome_file) {
		this.nome_file = nome_file;
	}

}
