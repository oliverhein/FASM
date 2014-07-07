
public class TesteAusnahme {

	public static void main(String[] args) {
		try {
			EigeneAusnahme.teste();
	    }
		catch (MyException e) {
			System.out.println(e.getMessage());
		}
		finally {
			// nichts zu tun
		}
	}

}
