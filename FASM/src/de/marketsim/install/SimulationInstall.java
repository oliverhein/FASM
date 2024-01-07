package de.marketsim.install;

import de.marketsim.util.FileTool;

public class SimulationInstall
{

	public void prepareDirectory()
	{
		// change to the target directory
		FileTool.checkDir("etc");
		FileTool.checkDir("config");
		FileTool.checkDir("lib");
	}


	/**
	 * A install program
	 * 
	 * This program is not finished.
	 * 
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{

 	     // 1. Start a GUI

		 // 2. Display Information

		 // 3. Input the Target Directory

		 // 4. create Main Directory

		 // 5. create lib, etc, config directory

		 // 6. reading and installing jar from lib into lib
		 //    display installing process

		 // 7. Ask the IP adress of the machine on which StockStore will run.
		 //

		 // 8. creating  jadebasic.cfg
		 //    install it into etc

		 // 9. copy the demo simulation configuration into cconfig

		 // 10. install startstore.bat
		 // 11. install dologin.bat
		 // 12. Checking Java Version
		 // 13. If java version is too lower, display information
		 //  for install high version java

	}

}
