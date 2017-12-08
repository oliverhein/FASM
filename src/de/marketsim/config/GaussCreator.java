package de.marketsim.config;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author unbekannt
 * @version 1.0
 */

import  de.marketsim.config.FasmGaussConfig;
import  de.marketsim.util.HelpTool;

import  java.util.*;


public class GaussCreator
{

  private FasmGaussConfig  mFasmGaussConfig = new FasmGaussConfig();
  private GaussBewertung   mGaussBewertung = new GaussBewertung();
  private int mBasicWerteAnzahl = 1000;
  private int mBasicWerteSeed   = 50000;

  public  GaussCreator( FasmGaussConfig pFasmGaussConfig)
  {
       mFasmGaussConfig = pFasmGaussConfig;
  }

  /**
   *   Generierte Werte werden in Vector gespeichert.
   *
   *   Jedes Object ist ein Double Object
  */

  public  Vector getGaussDistribution()
  {
      double  InitWerte[]       = new double [mBasicWerteAnzahl];
      double  SortedInitWerte[] = new double [mBasicWerteAnzahl];

      Random rd = new Random( this.mBasicWerteSeed );
      int k=0;
      do
      {
        double dd = HelpTool.DoubleTransfer( this.mFasmGaussConfig.mMean + rd.nextGaussian() * this.mFasmGaussConfig.mDeviation, 4 ) ;
        InitWerte[k] = dd;
        k++;

        /*
        if (  (dd < this.mFasmGaussConfig.mAllowedMin ) || (dd > this.mFasmGaussConfig.mAllowedMax ) )
        {
        }
        else
        {
           InitWerte[k] = dd;
           k=k+1;
        }
        */
      }
      while ( k < mBasicWerteAnzahl );

      //this.DisplayDD(InitWerte);

      // Sortierung
      SortedInitWerte = sortData(InitWerte);

      //this.DisplayDD(SortedInitWerte);

      this.mGaussBewertung = this.createGaussBewertung( InitWerte );

      //System.out.println(" Defined Mean      =" + this.mFasmGaussConfig.mMean );
      //System.out.println(" Defined Deviation =" + this.mFasmGaussConfig.mDeviation );
      //displayGaussBewertung(this.mGaussBewertung);

      Vector BaseWert4Part1 = new Vector();
      for ( int i=0; i< this.mFasmGaussConfig.mWertGrenzStelle1; i++)
      {
          BaseWert4Part1.add( new Double ( SortedInitWerte[i])  );
      }

      Vector BaseWert4Part2 = new Vector();
      for ( int i=0; i< this.mFasmGaussConfig.mWertGrenzStelle2; i++)
      {
          BaseWert4Part2.add( new Double ( SortedInitWerte[this.mFasmGaussConfig.mWertGrenzStelle1 +i])  );
      }

      Vector BaseWert4Part3 = new Vector();
      for ( int i=0; i< this.mFasmGaussConfig.mWertGrenzStelle3; i++)
      {
          BaseWert4Part3.add( new Double ( SortedInitWerte[this.mFasmGaussConfig.mWertGrenzStelle1 +
                                                           this.mFasmGaussConfig.mWertGrenzStelle2 +
                                                           i])
                             );
      }

      // Requirted Anzahl von Part1:
      int np1 = (int) ( this.mFasmGaussConfig.mDataNumber * this.mFasmGaussConfig.mDataPart1Prozent / 100.0 );

      // Requirted Anzahl von Part2:
      int np2 = (int) ( this.mFasmGaussConfig.mDataNumber * this.mFasmGaussConfig.mDataPart2Prozent / 100.0 );

      // Requirted Anzahl von Part3:
      int np3 = this.mFasmGaussConfig.mDataNumber - np1 - np2;

      Vector result = new Vector();

      k=0;
      do
      {
           int index = rd.nextInt( BaseWert4Part1.size()  );
           double dd = ( ( Double ) BaseWert4Part1.elementAt( index ) ).doubleValue();
           //System.out.println(" check part1 with " + dd);
           if (  (dd < this.mFasmGaussConfig.mAllowedMin ) || (dd > this.mFasmGaussConfig.mAllowedMax ) )
           {

           }
           else
           {
             result.add( BaseWert4Part1.elementAt( index ) );
             k=k+1;
           }
      }
      while ( k<np1 );

      k=0;
      do
      {
           int index = rd.nextInt( BaseWert4Part2.size()  );
           double dd = ( ( Double ) BaseWert4Part2.elementAt( index ) ).doubleValue();
           //System.out.println(" check part2 with " + dd);
           if (  (dd < this.mFasmGaussConfig.mAllowedMin ) || (dd > this.mFasmGaussConfig.mAllowedMax ) )
           {
           }
           else
           {
             result.add( BaseWert4Part2.elementAt( index ) );
             k++;
           }
      }
      while ( k< np2 );

      k=0;
      do
      {
           int index = rd.nextInt( BaseWert4Part3.size()  );
           double dd = ( ( Double ) BaseWert4Part3.elementAt( index ) ).doubleValue();
           //System.out.println(" check part3 with " + dd);
           if (  (dd < this.mFasmGaussConfig.mAllowedMin ) || (dd > this.mFasmGaussConfig.mAllowedMax ) )
           {
           }
           else
           {
             result.add( BaseWert4Part3.elementAt( index ) );
             k++;
           }
      }
      while ( k< np3 );
      return result;
  }

  /**
   * Sort the original Data, Original data will not be changed.
   * return the Results
   * @param pSrcData
   * @return
   */

  public void  displayGaussBewertung(GaussBewertung dd )
  {
      System.out.println(" Mean =" + dd.mMean);
      System.out.println(" Min  =" + dd.mMin);
      System.out.println(" Max  =" + dd.mMax);
      System.out.println(" Minus=" + dd.mAnzahlMinus);
  }

  public GaussBewertung  createGaussBewertung(double dd[] )
  {
        GaussBewertung mm = new GaussBewertung();
        double sum =0.0;
        mm.mMax = Double.MIN_VALUE;
        mm.mMin = Double.MAX_VALUE;
        for ( int i=0; i< dd.length; i++)
        {
            if ( dd[i] < 0.0 )
            {
               mm.mAnzahlMinus  = mm.mAnzahlMinus + 1;
            }
            sum = sum + dd[i];
            if ( dd[i] > mm.mMax )
            {
              mm.mMax = dd[i];
            }

            if ( dd[i] < mm.mMin )
            {
              mm.mMin = dd[i];
            }
        }
        mm.mMean = sum / dd.length;
        return mm;
  }

  public double[] sortData(double pSrcData[] )
  {
       double result[] = new double [ pSrcData.length ];
       for ( int i=0; i< pSrcData.length ; i++ )
       {
         result[i] = pSrcData[i];
       }

       for ( int i=0; i< result.length-1 ; i++ )
         for ( int j=i+1; j<result.length; j++)
         {
            if ( result[j] < result[i] )
            {
                 double temp = result[j] ;
                 result[j] = result[i];
                 result[i] = temp;
            }
         }
         return result;
  }

  public static void DisplayDD(double dd[])
  {
      for (int i=0; i<dd.length; i++)
      {
         System.out.println( i+ ". Data =" + dd[i] );
      }
  }

  public static void DisplayDD( Vector dd)
  {
      double pp[] = new double[ dd.size() ];
      for (int i=0; i<dd.size(); i++)
      {
         pp[i] = ( ( Double ) dd.elementAt(i) ).doubleValue();
      }
      DisplayDD(pp);
  }

  public static void main(String[] args)
  {
        FasmGaussConfig cfg = new FasmGaussConfig();
        cfg.mDataNumber = 50;
        cfg.mDataPart1Prozent = 60.0;
        cfg.mDataPart2Prozent = 30.0;
        cfg.mDataPart3Prozent = 10.0;
        cfg.mMean      = 2.0;
        cfg.mDeviation = 0.85;

        cfg.mAllowedMin = 0.5;
        cfg.mAllowedMax = 3.5;

        GaussCreator gg =new GaussCreator( cfg );

        Vector dd1 = gg.getGaussDistribution();

        cfg.mMean      = 2.0;
        cfg.mDeviation = 0.4;
        cfg.mAllowedMin = 0.5;
        cfg.mAllowedMax = 3.5;

        Vector dd2 = gg.getGaussDistribution();

        cfg.mMean      = 2.0;
        cfg.mDeviation = 0.6;
        cfg.mAllowedMin = 0.5;
        cfg.mAllowedMax = 3.5;

        Vector dd3 = gg.getGaussDistribution();

        gg.DisplayDD( dd1 );
        gg.DisplayDD( dd2 );
        gg.DisplayDD( dd3 );

  }
}