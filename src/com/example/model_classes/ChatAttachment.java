package com.example.model_classes;

import android.text.format.Time;

public class ChatAttachment extends ChatObject {

	Allegato allegato;
	// tipo

	public ChatAttachment(Time publicationTime, Allegato allegato) {
		super(publicationTime);
		this.allegato = allegato;
	}

	public Allegato getAllegato() {
		return allegato;
	}

	public void setAllegato(Allegato allegato) {
		this.allegato = allegato;
	}
	
	/*@Override
	public boolean isMine(int param) {
		// TODO Auto-generated method stub
		return super.isMine(param);
	}*/

}
