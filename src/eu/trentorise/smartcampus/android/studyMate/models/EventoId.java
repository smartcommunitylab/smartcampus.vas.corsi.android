package eu.trentorise.smartcampus.android.studyMate.models;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

public class EventoId implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 2002953249356174909L;

	private long idEventAd;

	private Date date;

	private Time start;

	private Time stop;

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
	
	public Time getStart() {
		return start;
	}

	public void setStart(Time start) {
		this.start = start;
	}

	public Time getStop() {
		return stop;
	}

	public void setStop(Time stop) {
		this.stop = stop;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getIdEventAd() {
		return idEventAd;
	}

	public void setIdEventAd(long idEventAd) {
		this.idEventAd = idEventAd;
	}

	
}
