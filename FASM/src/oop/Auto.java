package test;

public class Auto extends Fahrzeug {
	private String typ;
	private int anzahlSitze;
	private int kilometerStand;
	private int anzahlZylinder;
	private int verbrauch;
	
	public void erhoeheKilometer(int plusKilometer) {
		this.kilometerStand += plusKilometer;
	}
}