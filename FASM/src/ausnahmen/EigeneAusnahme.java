
public class EigeneAusnahme {

	public static void teste() throws MyException {
		try {
			MyException ex = new MyException();
			throw ex;
		}
		catch (MyException e) {
			System.out.println(e.getMessage());
		}
	}

}
