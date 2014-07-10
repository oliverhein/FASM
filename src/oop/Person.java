package test;

public class Person {
	
	public String name;  //  Instanzvariablen
	private int alter;
	
	public Person() { }  // Konstruktoren
	public Person(String name) {
		this.name = name;
	}
	
	public String getName() {  // Instanzmethode
		return this.name;
	}
	
	public int getAlter() {  // Instanzmethode
		return this.alter;
	}
}
