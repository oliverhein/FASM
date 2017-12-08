package de.marketsim.install;

import java.util.jar.*;
import java.util.zip.*;
import java.util.*;
import java.io.*;


import de.marketsim.util.FileTool;


public class JarCheck {


	public void ListJarContent(String pJarFileName)
	{
		try
		{
		   JarFile jjf = new JarFile( pJarFileName);
		   Enumeration en =  jjf.entries();

		   String dirname ="jartemp";

		   FileTool.DeleteOldDepotFile( dirname );
		   FileTool.createDirectory(  dirname );

		   while ( en.hasMoreElements() )
		   {
			 ZipEntry hh = (ZipEntry) en.nextElement();
			 System.out.println( "Name =" + hh.getName()+ " size=" + hh.getSize()  );

			 String ss = hh.getName();

			 int p1 = ss.indexOf("/");
			 String target = dirname ;
			 String filename = "";
			 if ( p1 >0 )
			 {
				 target = target + "/" +  ss.substring(0,p1);
				 FileTool.createDirectory( target );

                                 filename = ss.substring( p1 + 1);
			 }
			 else
			 {
				 filename = ss;
			 }

			 if ( ss.indexOf("jar") > 0 )
			 {
  			   InputStream ins = jjf.getInputStream( hh );
			   this.ExtractoneFile( ins, target + "/" + filename );
			 }

		   }
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}


	public void displayonefile ( InputStream pIns )
	{
		try
		{
   	       int kk = pIns.available();
   	       byte buffer[] = new byte[kk];
   	       int jj = pIns.read( buffer );
   	       String ss = new String( buffer );
   	       System.out.println( ss );
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

	}


	public void ExtractoneFile ( InputStream pIns, String pFilename )
	{
		try
		{
   	       int kk = pIns.available();
   	       byte buffer[] = new byte[kk];
   	       int jj = pIns.read( buffer );
   	       FileOutputStream  fos = new FileOutputStream( pFilename );
   	       fos.write( buffer );
   	       fos.close();
   	       System.out.println("Extrac to " + pFilename );
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

	}


	/**
	 * A small test program for Java JAR function 
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		JarCheck pp = new JarCheck();
		pp.ListJarContent( "d:/abc.jar");
	}

}
