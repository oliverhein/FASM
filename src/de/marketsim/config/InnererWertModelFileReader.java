package de.marketsim.config;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */
import java.io.*;
import java.util.*;

public class InnererWertModelFileReader
{
  double[]  mInnererWert;
  double[]  mInit300;
  int    mDays;

public InnererWertModelFileReader(String pFile) throws Exception
{
     try
     {
         java.io.BufferedReader br = new BufferedReader( new java.io.FileReader (pFile) );
         Vector dd = new Vector();
         String ss = null;
         do
         {
            ss = br.readLine();
            if ( ss != null )
            {
               if ( ss.length() > 2 )
               {
                 dd.add(ss);
               }
            }
         }
         while ( ss!=null );

         if ( dd.size() < 300 + 50 )
         {
            throw new Exception ("File has too few Data lines (" + dd.size() + ", but at least 350 lines)" );
         }

         this.mInit300 = new double[300];
         this.mInnererWert = new double[ dd.size() - 300 ];

         // Required Format:
         // 1  100
         // 2  200
         for ( int i=0; i<300; i++)
         {
            ss = (String) dd.elementAt(i);
            int j= ss.indexOf(" ");
            if ( j<0)
            {
              throw new Exception( i+ ". Line Data has format error. There is no space found.");
            }
            ss = ss.substring(j+2);
            mInit300[i] = Double.parseDouble(ss);
         }
         for ( int i=300; i<dd.size(); i++)
         {
            int k = i - 300;
            ss = (String) dd.elementAt(i);

            int j= ss.indexOf(" ");
            if ( j<0)
            {
              throw new Exception( i+ ". Line Data has format error. There is no space found.");
            }
            ss = ss.substring(j+2);
            this.mInnererWert[k] = Double.parseDouble(ss);
         }
         this.mDays = dd.size() - 300;
         br.close();

     }
     catch (IOException ex)
     {
       throw new Exception("Can not read file "+ pFile);
     }
     catch (Exception ex)
     {
       throw ex;
     }
}

public int getDays()
{
  return mDays;
}

public double[] getInit300()
{
  return mInit300;
}

public double[] getInnererWert()
{
  return mInnererWert;
}

public int[] getInit300IntValue()
{
  int dd [] = new int[300];
  for ( int i=0; i< 300; i++)
  {
     dd[i] = (int) mInit300[i];
  }
  return dd;
}

public int[] getInnererWertIntValue()
{
  int dd [] = new int[ this.mInnererWert.length ];
  for ( int i=0; i<this.mInnererWert.length; i++)
  {
     dd[i] = (int) this.mInnererWert[i];
  }
  return dd;
}

public Vector getAllIntData()
{
    Vector dd = new Vector();
    for ( int i=0; i<300; i++)
    {
       dd.add ( new Integer ( (int) this.mInit300[i]) );
    }
    for ( int i=0; i< this.mInnererWert.length; i++)
    {
       dd.add ( new Integer ( (int) this.mInnererWert[i] ) );
    }
    return dd;
}

public Vector getAllDoubleData()
{
    Vector dd = new Vector();
    for ( int i=0; i<300; i++)
    {
       dd.add ( new Double ( this.mInit300[i]) );
    }
    for ( int i=0; i< this.mInnererWert.length; i++)
    {
       dd.add ( new Double (  this.mInnererWert[i] ) );
    }
    return dd;
}

  public static void main(String[] args)
  {
    try
    {
       InnererWertModelFileReader ff = new InnererWertModelFileReader("muster1.txt");
       System.out.println( ff.getDays() +" days Data are found" );
    }
    catch (Exception ex)
    {
       ex.printStackTrace();
    }
  }
}