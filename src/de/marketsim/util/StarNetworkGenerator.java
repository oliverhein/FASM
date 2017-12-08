package de.marketsim.util;

import java.io.*;

public class StarNetworkGenerator {

	/**
	 * @param args
	 */

public static void makesternet(int n){

	String ss = "starnetwork_"+n + ".mat";

	try{

		java.io.PrintWriter  f = new PrintWriter( new java.io.FileWriter (ss) );
		f.println("*Vertices      "+n);

		for (int z=1; z<n+1;z++)
		{
			String s1 = "       "+z;
			s1=s1.substring(s1.length()-8);
			String s2 ='"'+"v"+z+'"'+"                                             ";
			s2=s2.substring(0, 40);
			f.println(s1+" "+s2+"0.0000    0.0000    0.5000");

		}

		f.println("*Matrix");
		/* 1 "v1"                                     0.1000    0.5000    0.5000
	       2 "v2"                                     0.1008    0.4749    0.5000
	       3 "v3"                                     0.1032    0.4499    0.5000
		*/

	  String firstline = "0 ";
	  for (int m =0; m<n-2; m++){

		  firstline= firstline+ "1 ";
	  }
		
	  firstline=firstline + "1 ";
	  f.println(firstline);

	  /**********************************************************************/

	  String secondline = "1 0 1 0 ";
	  for (int i=0; i<n-4; i++){

		  secondline= secondline+ "0 ";
	  }
		
	  f.println( secondline );
	  
	  /**********************************************************************/

	  String thirdline = "1 1 0 1 1 0 ";
	  for (int i=0; i<n-6; i++){

		  thirdline= thirdline+ "0 ";
	  }
		
	  f.println( thirdline );
	  
	  /**********************************************************************/

	  String line= "";
	  for (int i=0; i<n-4; i++){
		
		  // immer 1
		  line = "1 ";
		  // followed by 0
		  for (int j=0; j<i;j++){
			line = line +"0 ";
			}
			// followed by "0 1 0 1 "
			
		  line = line + "0 1 0 1 ";
		  
		  // followed by rest 0
		  for (int y =0; y<n-i-5;y++){
				line = line +"0 ";
		}

		  f.println(line);
	  }

	  /**********************************************************************/

	  String lastline = "1 ";

	  for (int m =0; m<n-3; m++){

		  lastline= lastline+ "0 ";
	  }
		lastline =lastline + "1 0 ";
	  f.println(lastline);
	  f.close();

	}catch(Exception ioe)

	{ioe.printStackTrace();}

}

	public static void main(String[] args)
        {
                System.out.println();
                System.out.println("This tool is used to generate Star Network Matrix");
                System.out.println();
                System.out.println("Usage:");
                System.out.println("java -cp <FASM_INSTALL>/lib/fasm.jar de.marketsim.util.StarNetworkGenerator NodeNumber" );
                System.out.println();
                System.out.println("Example:");
                System.out.println("java -cp C:/FASM/lib/fasm.jar de.marketsim.util.StarNetworkGenerator 500" );
                System.out.println("A Star Network Matrix will be created and the matrix file will be saved to starnetwork_500.mat" );
                System.out.println();
                if ( args.length == 0 )
               {

                 return;
              }

                System.out.println();
                int n = Integer.parseInt( args[0]);

                System.out.println("Now it is creating a Network Matrix with " +  args[0]  + " nodes" );
		makesternet( n );
                System.out.println("Network Matrix is created successfully. "  );
                System.out.println("Please check file " + "starnetwork_" + n + ".mat" );
                System.out.println();
	}

}
