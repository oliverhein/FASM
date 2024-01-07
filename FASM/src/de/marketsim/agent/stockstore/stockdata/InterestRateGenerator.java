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

import de.marketsim.agent.stockstore.stockdata.ProfitGenerator;

public class InterestRateGenerator
{

  ProfitGenerator  ProfitRef = null;
  int Datamaxnumber = 1500;
  static double  datamax = 0.1;   // 0.1 = 10%
  static double  datamin = 0.001;  // 0.0001 = 0.01 %

  double  PI = java.lang.Math.PI;

  double  ForcedAdjust =  ( datamax - datamin ) / 2;  // default
  double  XStart   = 0.0;
  double  XCurrent = XStart;
  double  XEnd     = 360.0;
  int PlusAlready = 0;
  int MinusAlready = 0;
  String  LastTrend = "+";
  int     Currentdataindex = 0;
  Vector  InterestRateData = new Vector();
  java.util.Random RandomTrend = new java.util.Random(1000);
  java.util.Random RandomDelta = new java.util.Random(1000);

  public InterestRateGenerator()
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

  public void setProfitRef( ProfitGenerator pProfit)
  {
    this.ProfitRef = pProfit;
  }

  public void setForcedAdjust(double dd)
  {
     this.ForcedAdjust = dd;
  }

  public void setXStart(double dd)
  {
     this.XStart = dd;
     this.XCurrent = dd;
  }

  public void setXEnd(double dd)
  {
     this.XEnd = dd;
  }

  private double getNextBasicData()
  {
     this.XCurrent = this.XStart +  Currentdataindex * ( XEnd - XStart ) / Datamaxnumber;
     double pp = this.XCurrent * 2 * PI / 360;
     double cos = Math.cos( pp );
     double dd =  ( datamax - datamin ) / 2 * cos;
     return dd ;
  }

  private double validate(double pCurrentZins, double pLastZins)
  {
    double Delta = 0.002;
    double ww ;
    if  ( Math.abs ( pCurrentZins - pLastZins ) > Delta )
    {
      if ( pCurrentZins - pLastZins > 0.0 )
      {
        ww = pLastZins + Delta;
      }
      else
      {
        ww = pLastZins - Delta;
      }
    }
    else
    {
       ww =  pCurrentZins;
    }

    if ( ww > this.datamax )
    {
       return this.datamax;
    }
    else
    if ( ww < this.datamin )
    {
        return this.datamin;
    }
    else
    {
       return ww;
    }
  }

  public double getNextData()
  {
     Currentdataindex++;
     int trend = 0;
     if ( Currentdataindex > 30 )
     {
        // From 31. data, the Trend will be taken from Profit TrendHistory
        Integer TT = this.ProfitRef.getTrendonTop();
        if ( TT != null )
        {
          trend = TT.intValue();
        }
        else
        {
          System.out.println(" Error: Profit has no TrendHistory !!! ");
        }
     }
     else
     {
        // the first 30 Trend will be generated dynamically byself
        trend = createTrend();
     }

     double d0 = this.getNextBasicData() + this.ForcedAdjust + trend * createDelta() ;

     double lastzins = 0.0;
     double d1 = 0.0;
     if ( this.InterestRateData.size() > 0 )
     {
       Double  lastvalue = (Double) this.InterestRateData.lastElement();
       lastzins = lastvalue.doubleValue();
       d1 = this.validate(d0, lastzins);
     }
     else
     {
       d1 = d0;
     }
     this.InterestRateData.add( new Double( d1 ) );
     return d1;
  }

  public Vector getInterestRateData()
  {
    return this.InterestRateData;
  }

  private double createDelta()
  {
     //  Max Delta: 0.006
     //  0.006 = 0.1 ( datamax ) * 6%
     //  0.0006 * ( 1 ~ 10 ) ---> 0.0006 ~ 0.006
     return  0.0006* (1 + RandomDelta.nextInt(10) ) ;
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

}