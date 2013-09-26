package com.example.model_classes;

import android.text.format.Time;

public abstract class ChatObject {

	// Autore
	Time publicationTime;

	public ChatObject(Time publicationTime) {
		super();
		this.publicationTime = publicationTime;
	}

	public Time getPublicationTime() {
		return publicationTime;
	}

	public void setPublicationTime(Time publicationTime) {
		this.publicationTime = publicationTime;
	}

	public boolean isMine(int param) {
		return param == 1;
		/*
		 * questo metodo quando saprò come usare il campo autore della classe
		 * chatobject mi dirà se il chatobject corrente è mio oppure no
		 */
	}

}
