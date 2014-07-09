
public class BodyMassIndex {

	public static void main(String[] args) {
		
		double gewicht;
		double groesse;
		
		try {
			gewicht = BmiEingabe.EingabeGewicht();
			groesse = BmiEingabe.EingabeGroesse();
			
			System.out.println("Ihr BMI beträgt: " + gewicht/(groesse*groesse));
		}
		
		catch (GewichtUnplausibel e) {
			System.out.println(e.getMessage());
		}	
		
		catch (GroesseUnplausibel e) {
			System.out.println(e.getMessage());
		}
	}

}
