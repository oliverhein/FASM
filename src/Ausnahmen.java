
public class Ausnahmen {

	public static void main(String[] args) {
		int i=0;
		int [] feld = new int[5];
		
		try {		
			for (i=0;i<=5;i++)
				feld[i]=i;
		}
		catch (ArrayIndexOutOfBoundsException e){
			System.out.println("Indexfehler! Laufindex=" + i + " nicht im Bereich!");
		}
		finally {
			// nichts zu tun
		}
	}

}
