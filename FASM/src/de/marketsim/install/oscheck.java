package de.marketsim.install;

public class oscheck
{

	public static void checkos()
	{
		System.out.println( "Os.Name=" + System.getProperty("os.name") );
		System.out.println( "Os.Version=" + System.getProperty("os.version") );
		System.out.println( "user.Name=" + System.getProperty("user.name") );
		System.out.println( "user.Home=" + System.getProperty("user.home") );
	}




	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		checkos();
	}

}
