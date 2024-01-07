package de.marketsim.util;

/**
 * <p>Überschrift: Market Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import java.util.*;

import de.marketsim.config.Configurator;
import de.marketsim.util.HelpTool;

public class PriceContainer
{

  private static Vector     PriceList = new Vector();
  private static boolean    TraderPriceSavedStatus[];

  private static int         mHandelsday = 0;
  //private static boolean     mInitialized = false;
  private static boolean     mFirst300PriceSaved = false;

  public static void Reset(int pHandelsday)
  {
      PriceList = new Vector();
      mFirst300PriceSaved = false;
      mHandelsday = pHandelsday;
      TraderPriceSavedStatus = new boolean[mHandelsday];
      for ( int i=0; i<mHandelsday; i++)
      {
          TraderPriceSavedStatus[i] = false;
      }
  }

  public static synchronized void  saveFirst300Price ( int[] pFirst301Innererwert)
  {
     if ( ! mFirst300PriceSaved )
     {
         for (int i=0; i<300; i++)
         {
            PriceList.add( new Double(pFirst301Innererwert[i]) );
         }
         mFirst300PriceSaved = true;
     }
  }

  public static synchronized void  saveFirst300Kurs ( double[] pFirst301Innererwert)
    {
       if ( ! mFirst300PriceSaved )
       {
           for (int i=0; i<300; i++)
           {
              PriceList.add( new Double(pFirst301Innererwert[i]) );
           }
           mFirst300PriceSaved = true;
       }
  }

  // TraderDay begins with 1.
  public static double getTraderPrice ( int pTraderDay )
  {
       Double jj = (Double) PriceList.elementAt( 300 + pTraderDay );
       return jj.doubleValue();
  }

  // In order to calculate the depot before day 300
  // get one generated price: Index: 1 ~ 300
  public static double getGeneratedOnePrice ( int pDayIndex )
  {
       Double jj = ( Double) PriceList.elementAt(pDayIndex -1 );
       return jj.doubleValue();
  }

  // get the last price
  public static double getLastPrice()
  {
       Double jj = ( Double ) PriceList.lastElement();
       return jj.doubleValue();
  }

  // get all price
  public static Vector getAllPrice()
  {
       return PriceList;
  }

  // Save TraderPrice of one Day
  public static synchronized void saveTraderPrice ( int pTraderDay, double pPrice )
  {
       if ( ! TraderPriceSavedStatus[pTraderDay] )
       {
          PriceList.add( new Double(pPrice) ) ;
          TraderPriceSavedStatus[pTraderDay] = true;
       }
  }

  public static double getReferencePrice(int pLetztN_Day )
  {
       Double jj = ( Double ) PriceList.elementAt(  PriceList.size() - pLetztN_Day );
       return jj.doubleValue();
  }

  public static int getDataNumber()
  {
      return PriceList.size();
  }

  public static double getMovingAveragePrice(int pPastdays)
  {
      double summe = 0.0;
      int j;
      if ( pPastdays > PriceList.size() )
      {
         j = PriceList.size();
      }
      else
      {
         j = pPastdays;
      }

      for (int i=0; i<j; i++)
      {
        summe = summe + ((Double) PriceList.elementAt( PriceList.size() - 1 - i ) ).doubleValue();
      }
      return HelpTool.DoubleTransfer(summe / j, 4);
  }




}