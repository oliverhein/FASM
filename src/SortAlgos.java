
public class SortAlgos {
	
	public static void selectionSort(int[] feld){
		int i, j, min;
		for( i=feld.length; i>1; i-- ) {
			min = 0;
			for( j=1; j<i; j++ ) {
				if( feld[j] < feld[min] ) min = j;
			}
			tausche( feld, min, i-1);
		}
	}
	
	static void insertionSort( int[] feld ) {
		int i, p, k, zwi;
		for( i=1; i<feld.length; i++ ) {
			p = position( feld[i], feld, i);
			zwi = feld[i];
			for( k=i-1; k>=p; k-- ) tausche( feld, k, k+1);
				feld[p] = zwi;
		}
	}
	
	static void bubbleSort( int[] feld ) {
		for( int j=1; j<feld.length; j++ ) 
			for( int i=feld.length-1; i>=j; i-- ) 
				if( feld[i] > feld[i-1] ) 
					tausche(feld,i,i-1);									
	}
	
	public static void tausche(int[] feld, int i, int j){
		int zwi;
		zwi = feld[i];
		feld[i] = feld[j];
		feld[j] = zwi;
	}
	
	/* Hilfsmethode position
	* suche Position von wert in einem sortierten Feld
	*/
	public static int position(int wert, int[] feld, int laenge) {
		for(int i=laenge-1; i>=0; i-- ) {
			if( feld[i] > wert ) return i+1;
		}
		return 0;
	}
}
