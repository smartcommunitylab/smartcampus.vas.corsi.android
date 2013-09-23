package com.example.model_classes;

import android.net.Uri;

public class Allegato {
	
	Uri uri_allegato;
	//dati e metadati vari insomma...

	public Allegato(Uri uri_allegato) {
		super();
		this.uri_allegato = uri_allegato;
	}

	public Uri getUri_allegato() {
		return uri_allegato;
	}

	public void setUri_allegato(Uri uri_allegato) {
		this.uri_allegato = uri_allegato;
	}
	
	

}
