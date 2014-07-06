package test;

public class Tutor extends Student implements Lehrkraft {
	float vergütung = 0;
	public Tutor() { }
	public Tutor( String name ) { super( name ); }
	public void übernehmeVorlesung(String vorlesung, int stunden) {
		System.out.println( "Tutor " + name + " übernimmt " + vorlesung);
		vergütung += stunden * 10;
		System.out.println( "Vergütung: " + vergütung + " Euro");
	}
}
