package test;

public class Fahrzeug {
	
	private String hersteller;
	private int anzahlUnfaelle = 0;
	private int produktionsJahr;
	private int kaufPreis;
	
	public void setkaufPreis(int preis) {
		this.kaufPreis = preis;
	}
	
	public int getkaufPreis() {
		return this.kaufPreis;
	}
}
