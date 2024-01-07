package test;

public class Person {
	
	private static double durchschnittsAlter;
	private static int anzahlPersonen;
	
	public String name;  //  Instanzvariablen
	private int alter;
	
	public Person() { }  // Konstruktoren
	public Person(String name) {
		this.name = name;
	}
	
	public static void berechneAltersDurchschnitt() {
		durchschnittsAlter=0;
		for(int i=0;i<=3;i++) 
			durchschnittsAlter += register[i].getAlter();
		durchschnittsAlter = durchschnittsAlter / anzahlPersonen;		
	}
	
	public String getName() {  // Instanzmethode
		return this.name;
	}
	
	public int getAlter() {  // Instanzmethode
		return this.alter;
	}
	
	
}
