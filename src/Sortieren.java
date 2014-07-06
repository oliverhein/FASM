
public class Sortieren {

	public static void main(String[] args) {
		
		int[] feld = new int[10]; // 100.000 ganze Zahlen werden sortiert
		long start, finish; // Variablen fuer Zeitmessung
		
		for( int i=0; i<10; i++ ) // Zufallszahlen generieren
			feld[i] = (int)(Math.random() * 32000);	
		
		start = System.currentTimeMillis(); // Zeit messen	
		SortAlgos.selectionSort(feld); // verwendeter Sortier-Algo.
		finish = System.currentTimeMillis();  // Zeit messen
		System.out.println("Selection Sort: " + (finish - start) + " ms");
		
		for( int i=0; i<10; i++ ) 
			System.out.println(feld[i]);	
		
		start = System.currentTimeMillis(); // Zeit messen	
		SortAlgos.insertionSort(feld); // verwendeter Sortier-Algo.
		finish = System.currentTimeMillis();  // Zeit messen
		System.out.println("Insertion Sort: " + (finish - start) + " ms");
		
		for( int i=0; i<10; i++ ) 
			System.out.println(feld[i]);	
		
		start = System.currentTimeMillis(); // Zeit messen	
		SortAlgos.bubbleSort(feld); // verwendeter Sortier-Algo.
		finish = System.currentTimeMillis();  // Zeit messen
		System.out.println("Bubble Sort: " + (finish - start) + " ms");
		
		for( int i=0; i<10; i++ ) 
			System.out.println(feld[i]);	
	}
}
