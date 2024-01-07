import java.util.Scanner;

public class goethe {

	public static void main(String[] args) {
		
		String [] woerter		= new String[500];
		int anzahl = 0;
		String [] goethe_faust 	= new String[10];
		
		goethe_faust[0] = "Habe nun, ach! Philosophie,Juristerei und Medicin,";
		goethe_faust[1] = "Und leider auch Theologie!Durchaus studiert, mit heißem Bemühn.";
		goethe_faust[2] = "Da steh ' ich nun, ich armer Thor! Und bin so klug als wie zuvor;";
		goethe_faust[3] = "Heiße Magister, heiße Doctor gar, Und ziehe schon an die zehen Jahr,";
		goethe_faust[4] = "Herauf, herab und quer und krumm, Meine Schüler an der Nase herum";
		goethe_faust[5] = "Und sehe, daß wir nichts wissen können! Das will mir schier das Herz verbrennen.";
		goethe_faust[6] = "Zwar bin ich gescheidter als alle die Laffen, Doctoren, Magister, Schreiber und Pfaffen;";
		goethe_faust[7] = "Mich plagen keine Scrupel noch Zweifel, Fürchte mich weder vor Hölle noch Teufel";
		goethe_faust[8] = "Dafür ist mir auch alle Freud' entrissen, Bilde mir nicht ein was Rechts zu wissen,";
		goethe_faust[9] = "Bilde mir nicht ein ich könnte was lehren Die Menschen zu bessern und zu bekehren.";
	
		
		// Aufgabe 1
		
		int zahl=0;
		for(int i=0;i<=9;i++){
			zahl = zahl + goethe_faust[i].length();
		}
		System.out.println("Anzahl der Zeichen: " + zahl);
		
		// Aufgabe 2
		int index;
		int zaehler=0;
		for(int i=0;i<=9;i++){
			index = 0;
			while (goethe_faust[i].indexOf("ich",index) != -1){
				zaehler++;
				index=goethe_faust[i].indexOf("ich",index)+1;
			}
		}
		System.out.print("Das Wort 'ich' kommt " + zaehler + " mal vor.");
		System.out.println();
		
		// Aufgabe 3
		
		Scanner eingabe = new Scanner(System.in);
		String k1 = new String("");
		String k2 = new String("");
		
		System.out.println("Bitte eine Zeichenkette eingeben: ");
		k1 = eingabe.next();
		
		System.out.println("Bitte eine Zeichenkette eingeben: ");
		k2 = eingabe.next();
		
		if (k2.indexOf(k1) == -1)
			System.out.println("1. Zeichenkette nicht in 2. Zeichenkette enthalten.");
		else {	
			String newstring = k2.replace(k1, "ZZZZ");
			System.out.println("Text mit Ersetzung: " + newstring);
		}
		
		System.out.println();
		
		eingabe.close();
		
		// Aufgabe 4
		
		for(int i=0;i<=9;i++){		
			for (String p: goethe_faust[i].split(" ")){
				woerter[anzahl]=p;
				anzahl++;
			}
		}
		
		for(int i=0;i<=10;i++){
			for(int j=0;j<=15;j++){
				int z=(int) (Math.random()*anzahl);
				System.out.print(woerter[z] + " ");
			}
			System.out.println();
		}
		
	}

}
