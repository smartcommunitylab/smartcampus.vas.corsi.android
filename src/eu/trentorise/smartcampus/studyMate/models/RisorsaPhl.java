package eu.trentorise.smartcampus.studyMate.models;

import java.io.Serializable;


public class RisorsaPhl implements Serializable {
	

	private String name;
	private String hash;
	private String date;
	private Boolean read;
	private Boolean write;
	private Boolean rm;
	private Boolean hidden;
	private int size;
	private String mime;
	private String url;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Boolean getRead() {
		return read;
	}
	public void setRead(Boolean read) {
		this.read = read;
	}
	public Boolean getWrite() {
		return write;
	}
	public void setWrite(Boolean write) {
		this.write = write;
	}
	public Boolean getRm() {
		return rm;
	}
	public void setRm(Boolean rm) {
		this.rm = rm;
	}
	public Boolean getHidden() {
		return hidden;
	}
	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getMime() {
		return mime;
	}
	public void setMime(String mime) {
		this.mime = mime;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}


	

}
