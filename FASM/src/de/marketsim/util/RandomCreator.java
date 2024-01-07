package de.marketsim.util;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2005
 * Company:
 * @author
 * @version 1.0
 */

import java.util.*;

public class RandomCreator {

  private String mMode;
  public RandomCreator(String pMode)
  {
     mMode = pMode;
  }

  public void getRandom(int Mean, int Deviation, int num)
  {
      if ( mMode.equals("Gauss") )
      {
        Gauss( Mean, Deviation, num );
      }
      else
      {
        Random( Mean, Deviation, num );
      }
  }

  public void Gauss(int Mean, int Deviation, int num)
  {
      Random rr = new Random();

      VerteilungStatic gsstatistic = new VerteilungStatic(Mean, Deviation);
      int i=0;
      System.out.println("===========================" );
      while ( i<num )
      {
              int jj = (int) ( rr.nextGaussian()* Deviation + Mean ) ;
              System.out.println(jj);
              gsstatistic.IncreaseOneWert( jj );
              i++;
      }

      System.out.println("===========================" );
      gsstatistic.PrintStatic();
  }





  public void FasmGauss(int Min, int Max, int num)
  {
      Random rr = new Random();
      int Mean      = Min;
      int Deviation = Max - Min;

      VerteilungStatic gsstatistic = new VerteilungStatic(Mean, Deviation);
      int i=0;
      System.out.println("===========================" );
      while ( i<num )
      {
           int jj = (int) ( rr.nextGaussian()* Deviation + Mean ) ;
           if ( (jj < Min ) || ( jj> Max )  )
           {

           }
           else
           {
              System.out.println(jj);
              gsstatistic.IncreaseOneWert( jj );
              i++;
           }
      }

      System.out.println("===========================" );
      gsstatistic.PrintStatic();

  }

  public void Random(int Mean, int Deviation, int num)
  {
      Random rr = new Random();
      VerteilungStatic gsstatistic = new VerteilungStatic(Mean, Deviation);

      for ( int i=0; i<num; i++)
      {
           int jj = (int) ( rr.nextInt( 2*Deviation ) + Mean - Deviation ) ;
           gsstatistic.IncreaseOneWert( jj );
      }
      System.out.println( "===============================" );
      gsstatistic.PrintStatic();

  }

  public static void main(String args[])
  {

    RandomCreator pp = new RandomCreator("");
    int mean      = Integer.parseInt( args[0] );
    int deviation = Integer.parseInt( args[1] );
    int num       = Integer.parseInt( args[2] );
    //pp.Gauss ( mean, deviation, num );
    // pp.FasmGauss ( MIN, MAX, num );
    pp.FasmGauss ( mean, deviation, num );
    //pp.Random( mean, deviation, num );

  }

  class VerteilungStatic
  {
     Hashtable allcounter = new Hashtable();
     int mMean;
     int mDeviation;

     VerteilungStatic(int Mean, int Deviation)
     {
         mMean      = Mean;
         mDeviation = Deviation;
     }

     public void IncreaseOneWert(int pWert)
     {
          Counter pp = (Counter) allcounter.get(""+ pWert);

          if ( pp !=null )
          {
            pp.cc = pp.cc + 1;
          }
          else
          {
            allcounter.put( ""+pWert, new Counter(1) );
          }
     }

     public void PrintStatic()
     {
        Enumeration keys = allcounter.keys();

        int min = mMean;
        int max = mMean;

        while ( keys.hasMoreElements() )
        {
           int jj = Integer.parseInt( (String) keys.nextElement() );
           if ( jj < min )
           {
              min = jj;
           }
           else
           if ( jj > max )
           {
              max = jj;
           }
        }

        for ( int i=min; i<max; i++)
        {
             Counter pp = (Counter) allcounter.get( "" +i );
             if ( pp == null )
             {
               System.out.println( i + ";0" );
             }
             else
             {
               System.out.println( i + ";" + pp.cc );
             }

        }
     }


  }

  class Counter
  {
     public int cc = 0;
     Counter()
     {
     }

     Counter(int initwert)
     {
        cc = initwert;
     }


  }


}