package test;

public class Fahrrad extends Fahrzeug {
	private int anzahlRaeder;
	private String typ;
	private int gewicht;
	
	public void setTyp (String fahrradTyp) {
		this.typ = fahrradTyp;
	}
	
	public String getTyp () {
		return this.typ;
	}
}
