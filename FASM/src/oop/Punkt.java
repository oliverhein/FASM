package test;

public class Punkt implements Punktschnittstelle  {
	private int x;
	public int getX() {
		return x;
	}
	public void setX(int i) {
		this.x = i;
	}
	public static void main (String[] args) {
		Punkt p = new Punkt();
		p.setX(3);
		System.out.println("Die Koordinate lautet: ");
		System.out.println(p.getX());
	}
}
