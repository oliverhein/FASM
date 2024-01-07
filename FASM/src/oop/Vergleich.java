package test;

public class Vergleich 
extends java.lang.Object 
implements java.util.Comparator <Person> {
	
	public int compare(Person per1, Person per2) {
		Person p1,p2;
		p1 = (Person) per1;
		p2 = (Person) per2;
		if (p1.name.compareTo(p2.name) > 0 ) return 1;
		  else return -1;
	}
}
