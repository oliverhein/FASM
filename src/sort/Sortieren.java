
public class Sortieren {

	public static void main(String[] args) {
		
		int[] feld = new int[100000]; // 100.000 ganze Zahlen werden sortiert
		long start, finish; // Variablen für Zeitmessung
		
		for( int i=0; i<100000; i++ ) // Zufallszahlen generieren
			feld[i] = (int)(Math.random() * 32000);	
		start = System.currentTimeMillis(); // Zeit messen
		
		SortAlgos.insertionSort(feld); // verwendeter Sortier-Algo.
	
		finish = System.currentTimeMillis();  // Zeit messen
		System.out.println("Zeit: " + (finish - start) + " ms");
	}
}
