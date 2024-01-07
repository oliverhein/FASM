package test;

public class Professor extends Person implements Lehrkraft {
	private int SWS = 0;
	public Professor() {}
	public Professor(String name) { super( name ); }
	public void übernehmeVorlesung(String vorlesung, int stunden) {
		System.out.println( "Prof. " + name + " übernimmt " + vorlesung);
		SWS += stunden;
		System.out.println( "SWS: " + SWS);
	}
}