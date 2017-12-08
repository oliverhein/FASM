/**
 * Überschrift:
 * Beschreibung:
 * Copyright:     Copyright (c) 2003
 * Organisation:
 * @author
 * @version 1.0
 */

package de.marketsim.agent.stockstore.stockdata;

import java.math.*;
import java.io.*;
import java.util.*;

public class ProfitGenerator
{

  int Datamaxnumber = 1500; // default

  static double  datamax = 200.0;
  static double  datamin = 10.0;

  double  PI = java.lang.Math.PI;

  double  ForcedAdjust = ( datamax + datamin ) / 2.0;
  double  XStart   = 0.0;
  int     mForwardDays = 0;
  double  XCurrent = XStart;

  int PlusAlready = 0;
  int MinusAlready = 0;
  String  LastTrend = "+";
  int     Currentdataindex = 0;

  Vector  ProfitData = new Vector();

  java.util.Random RandomTrend = new java.util.Random(1000);
  java.util.Random RandomDelta = new java.util.Random(1000);

  Vector TrendHistory = new Vector();
  // HistoryNumber defines how many Trend value should be holded
  // These values will be used by the Zins Generator
  int  TrendHistorySize = 30; // Default

  public ProfitGenerator()
  {

  }

  public static double getDataMax()
  {
     return datamax;
  }

  public static double getDataMin()
  {
     return datamin;
  }

  public void setTrendHistorySize(int pSize)
  {
     this.TrendHistorySize = pSize;
  }

  public void setForcedAdjust(double dd)
  {
     this.ForcedAdjust = dd;
  }

  public void setForwardDays(int pForwardDays)
  {
     this.mForwardDays = pForwardDays;
     // XStart is HuDu
     this.XStart     =  (2.0 * Math.PI * this.mForwardDays)/ Datamaxnumber;
     // XCurrent is Hudu
     this.XCurrent   = this.XStart;
  }

  private double validate(double dd)
  {
    if ( dd > this.datamax )
    {
      return this.datamax;
    }
    else
    if ( dd < this.datamin )
    {
      return this.datamin;
    }
    else
    {
      return dd;
    }
  }


  private double getBaseDataNext()
  {
     this.XCurrent = this.XStart  +  (2.0 * Math.PI * Currentdataindex) / Datamaxnumber  ;
     double cos = java.lang.Math.cos( this.XCurrent );
     double dd =  ( this.datamax - this.datamin ) / 2 * cos;
     return dd ;
  }

  public double getNextData()
  {
     int trend = createTrend();
     this.TrendHistory.add( new Integer( trend ) );
     // the TrendHistory contains maximal so many ( HistorySize )  Trends
     // The top value will always be removed
     // if the HistorySize is full and a new is added.
     if ( this.TrendHistory.size() > this.TrendHistorySize )
     {
       this.TrendHistory.removeElementAt(0);
     }
     double d0 = this.getBaseDataNext() ;
     d0 = d0 + this.ForcedAdjust + trend * createDelta(d0) ;

     double d1 = this.validate(d0);

     Currentdataindex++;
     this.ProfitData.add( new Double( d1 )  );
     return d1;
  }

  public Vector getProfitData()
  {
    return this.ProfitData;
  }

  public Integer getTrendonTop()
  {
     if ( this.TrendHistory.size() > 0 )
     {
        return (Integer) this.TrendHistory.elementAt(0);
     }
     else
     {
        return null;
     }
  }

  private double createDelta(double pBaseValue )
  {
     // maximal 0.8% = 0.008
     double dd = (datamax - datamin)*0.0008 *  RandomDelta.nextInt(11) ;

     if ( dd > pBaseValue * 2 )
     {
       dd = dd/3 ;
     }

     return dd;
  }

  private int createTrend()
  {
     int base = 1000;
     int k = RandomTrend.nextInt(base);
     int allowedcontinuechanging = 10;
     if ( k >= base * 6 / 10 )
     {
       this.PlusAlready++;
       if ( PlusAlready > allowedcontinuechanging )
       {
         this.LastTrend = "-";
         this.PlusAlready=0;
         return -1;
       }
       else
       {
         this.LastTrend = "+";
         return +1;
       }
     }
     else
     if (  k >= base * 3 / 10 )
     {
       this.MinusAlready++;
       if ( MinusAlready > allowedcontinuechanging )
       {
         this.LastTrend = "+";
         this.MinusAlready=0;
         return +1;
       }
       else
       {
         this.LastTrend = "-";
         return -1;
       }
     }
     else
     {
         return 0;
     }
  }

  public int getCurrentdataindex()
  {
    return Currentdataindex;
  }

/*
  public static void main(String args[])
  {
      String filename = System.getProperty("filename","G:/abc.csv");
      java.text.DecimalFormat dc = new java.text.DecimalFormat();
      ProfitGenerator  pf = new ProfitGenerator();
      try
      {
        java.io.PrintWriter fw = new java.io.PrintWriter( new java.io.FileOutputStream( filename) );
        for (int i=0; i<300; i++)
        {
           double dd = pf.getNextData();

           fw.println( i +";" + dc.format(dd) );
        }
        fw.checkError();
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
  }
*/
}