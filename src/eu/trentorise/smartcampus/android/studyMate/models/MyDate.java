package eu.trentorise.smartcampus.android.studyMate.models;

//quest classe è stata creata per facilitare l'uso dei datepicker android
//dove i mesi sono mappati su numeri da 0 a 11. 
//Utilizzando questa classe per stamapre date viene nascosto 
//al programmatore il problema di incrementare di uno il mese
//ogni volta che si deve presentare una data appena rilevata dal picker

public class MyDate {

	int year;
	int month;
	int day;

	public MyDate() {
		// empty default constructor
	}

	public MyDate(int year, int month, int day) {
		super();
		this.year = year;
		this.month = month;
		this.day = day;
	}

	@Override
	public String toString() {
		return ("" + day + "/" + (month + 1) + "/" + year);
	}

	public static MyDate parseFromString(String phrase_date) {

		String delims = "[/]";
		String[] tokens = phrase_date.split(delims);
		int mDay = Integer.parseInt(tokens[0]);
		int mMonth = Integer.parseInt(tokens[1]);
		int mYear = Integer.parseInt(tokens[2]);
		MyDate date = new MyDate(mYear, mMonth, mDay);
		return date;
	}

	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public int getDay() {
		return day;
	}

}
