/**
 * Überschrift:
 * Beschreibung:
 * Copyright:     Copyright (c) 2003
 * Organisation:
 * @author
 * @version 1.0
 **/

package de.marketsim.util;

import java.io.*;
import java.util.*;
import java.text.*;
import de.marketsim.SystemConstant;

public class HelpTool
{

  static int[]  Expten = {1,10,100,1000,10000,100000,1000000,10000000,100000000};

  public static void pause(String pPrompt)
  {
      System.out.println(pPrompt);
      pause();
  }

  public static void pause()
  {
    try
    {
     BufferedReader pp = new BufferedReader( new InputStreamReader ( System.in ));
     pp.readLine();
    }
    catch (Exception ex)
    {
    }
  }

  public static String getCommandLine()
  {
      String ss="OK";
      try
      {
         BufferedReader pp = new BufferedReader( new InputStreamReader ( System.in ));
         ss = pp.readLine();
      }
      catch (Exception ex)
      {
      }
      return ss;
  }

  // time in ms
  public static void pause(int time)
  {
      try
      {
          Thread.sleep(time);
      }
      catch (Exception ex)
      {

      }
  }

  // get the default Number Format
  /*
  public static NumberFormat getNumberFormat(String pLanguage)
  {
      NumberFormat nff = null;
      // Number Formatter
      if ( pLanguage.equals( SystemConstant.DataFormatLanguage_German  ) )
      {
         nff = java.text.NumberFormat.getNumberInstance( java.util.Locale.GERMANY );
      }
      else
      if ( pLanguage.equals( SystemConstant.DataFormatLanguage_English  ) )
      {
        nff = java.text.NumberFormat.getNumberInstance(java.util.Locale.US);
      }
      else
      {
        // German als Default
        nff = java.text.NumberFormat.getNumberInstance( java.util.Locale.US );
      }

      String fractionlen = System.getProperty("Fraction","8");
      int fraction = Integer.parseInt( fractionlen );
      nff.setMaximumFractionDigits( fraction );
      nff.setGroupingUsed(false);
      return nff;
  }
  */


  public static String getTimeStamp()
  {
     java.sql.Timestamp ts = new java.sql.Timestamp( System.currentTimeMillis() );
     return ts.toString().substring(11);
  }

  public static String TrimString(String pOriginalStr, char pChar)
  {
     String ss = pOriginalStr;
     while ( ss.indexOf("" + pChar ) >= 0 )
     {
        int pos = ss.indexOf( "" + pChar );
        ss = ss.substring(0,pos) + ss.substring(pos+1);
     }
     return ss;
  }

  public static double DoubleTransfer(double pData, int pDigitLength)
  {
      long p1 = Math.round ( pData * Expten[pDigitLength] );
      return p1 * 1.0 / Expten[pDigitLength];
  }

  public static List SortVectorString(Vector pp)
  {
      List ll = Arrays.asList( pp.toArray()  );
      Collections.sort(ll);
      return ll;
  }

  public static int getCharIntSumme(String SS)
  {
      int sum = 1000;
      for ( int i=0; i<SS.length(); i++)
      {
          sum = sum + ( int ) SS.charAt(i) * 10;
      }
      String ss = "" + System.currentTimeMillis();
      ss = ss.substring( ss.length() -3  );
      sum = sum + Integer.parseInt(ss);
      return sum;
  }

  public static void main(String args[] )
  {
    String ss = "Hallo W orld !";

    double dd = 122.37;
    /*

    NumberFormat nff = getNumberFormat( "German");
    nff.setMaximumFractionDigits(4);
    nff.setMinimumFractionDigits(4);

    System.out.println ( nff.format(dd) );
    nff.setMaximumFractionDigits(6);
    System.out.println ( nff.format(dd) );

    String ss1 = TrimString( ss, ' ');
    System.out.println (ss1);

    if ( args.length < 2 )
    {
        System.out.println("Usage:");
        System.out.println("java  de.marketsim.tool.HelpTool wait n");
        System.out.println("n the waittime, in second");
        return;
    }
    int n = Integer.parseInt( args[1] );
    pause(n*1000);

    */
  }

  public static String covertInt2StringWithPrefixZero(int pIntvalue, int pExpectedMindestLength  )
  {
	  String ss = "" + pIntvalue;
	  if ( ss.length() >= pExpectedMindestLength )
	  {
		  return ss;
	  }
	  else
	  {
		  ss ="00000000000" +  ss;
		  String res = ss.substring( ss.length() - pExpectedMindestLength );
		  return res;
	  }
  }
  
  public static void beep()
  {
     java.awt.Toolkit.getDefaultToolkit().beep();
  }

}