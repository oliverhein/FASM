package test;

import java.util.Arrays;

public class test {
	
	public static Person [] register = new Person[4];		
	
	public static void main(String [] args) {	
		
		register[0]=new Person("Weber");
		register[1]=new Person("Maier");
		register[2]=new Person("Schmidt");
		register[3]=new Person("Müller");
		
		Vergleich vrgl = new Vergleich();
		Arrays.sort(register, vrgl);
		
		for (int i=0; i<=3; i++)
			System.out.println(register[i].getName());
		
		Sechs zahl = new Sechs();
		System.out.println(zahl.fuenf_Ausgabe());
	}
	
	
}
