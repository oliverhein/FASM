package de.marketsim.test;

/**
 * <p>Überschrift: Market Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import de.marketsim.test.Parameter;

import java.io.*;

public class abc
{

  public abc()
  {

  try
  {
        //FileOutputStream fos = new FileOutputStream("D:/t.tmp");
        ByteArrayOutputStream bos = new  ByteArrayOutputStream();
	ObjectOutputStream oos = new ObjectOutputStream( bos );

        Parameter pp = new Parameter();
        pp.p3="AAA chen &&10290€€€";
        pp.p4="2000";

	oos.writeObject( pp );
	oos.close();

        //FileInputStream fis = new FileInputStream("D:/t.tmp");

        byte buffer[]= bos.toByteArray();

        ByteArrayInputStream bis = new  ByteArrayInputStream( buffer );

	ObjectInputStream ois = new ObjectInputStream( bis );
	Parameter p = (Parameter) ois.readObject();

  System.out.println( "p3=" + p.p3 );
  System.out.println( "p4=" + p.p4 );

	ois.close();

  }
  catch (Exception ex)
  {
     ex.printStackTrace();
  }

  System.out.println("Object Read/write is over");


  }

  private static String  removeDirectoryName (String pAbsoluteFileName)
{
   int j = pAbsoluteFileName.indexOf("\\");
   String ss = pAbsoluteFileName;
   while ( j>=0 )
   {
      ss = ss.substring( j +1 );
      j = ss.indexOf("\\");
   }
   return ss;

}

  public static void main(String[] args)
  {

       System.out.println ( 123675 + 551*1013 );
       System.out.println ( 123675 + 551*1021 );
       System.out.println ( 123675 + 551*1023 );



  }
}