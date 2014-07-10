package test;

public class Student extends Person {
	
	int matrikel;
	
	public Student() { }
	
	public Student( String name ) {
		super( name );
	}
	
	public Student( String name, int matrikel ) {
		super( name );
		this.matrikel = matrikel;
	}
}
