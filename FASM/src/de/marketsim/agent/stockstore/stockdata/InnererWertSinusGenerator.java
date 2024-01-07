package de.marketsim.agent.stockstore.stockdata;

import java.util.Vector;
import de.marketsim.config.*;

/**
 * <p>Überschrift: Market Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 02.02.2005</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

public class InnererWertSinusGenerator
{

    int     Datamaxnumber = 1500; // default
    private int mInnererWertMax  = 3000; // default
    private int mInnererWertMin  = 500;  // default
    private int mInnererWertBegin = 1750;
    private int mlastInnererWert  = 0;

    private Vector  mInnererWertHistory = new Vector();

    double  PI = java.lang.Math.PI;
    double  ForcedAdjust = ( mInnererWertMax + mInnererWertMin ) / 2.0;
    // Hudu
    double  XStart       = 0.0;

    int     mForwardDays = 0;

    int PlusAlready = 0;
    int MinusAlready = 0;
    String  LastTrend = "+";
    int     Currentdataindex = 0;

    //java.util.Random RandomTrend = new java.util.Random(1000);
    //java.util.Random RandomDelta = new java.util.Random(1000);

    java.util.Random RandomTrend = new java.util.Random();
    java.util.Random RandomDelta = new java.util.Random();

    Vector TrendHistory = new Vector();

    int  mTrend = 1; // Default: 1: Aufsteigen 0: Absteigen

    // HistoryNumber defines how many Trend value should be holded
    // These values will be used by the Zins Generator
    int  TrendHistorySize = 30; // Default

    public InnererWertSinusGenerator()
    {

       this.mInnererWertMax = Configurator.mConfData.mMaxInnererWert;
       this.mInnererWertMin = Configurator.mConfData.mMinInnererWert;
       this.Datamaxnumber   = Configurator.mConfData.mDaysOfOnePeriod;
       this.ForcedAdjust   = ( mInnererWertMax + mInnererWertMin ) / 2.0;
       this.mInnererWertBegin = Configurator.mConfData.mBeginInnererWert;

       int pp1 = mInnererWertBegin - ( this.mInnererWertMax + this.mInnererWertMin)/ 2 ;
       int pp2 = ( this.mInnererWertMax - this.mInnererWertMin)/ 2 ;
       double sin_x = 1.0* pp1 / pp2;

       // Start HuDU
       this.XStart = Math.asin( sin_x );

       //this.mTrend = Configurator.getInnereWertEntwicklungsTrend();

       this.mTrend = RandomTrend.nextInt(4);

       System.out.println("mTrend=" + mTrend );

       // Ajdust dynamically
       adjustXStart( mTrend );

       System.out.println("MAX InnererWert=" + this.mInnererWertMax );
       System.out.println("Min InnererWert=" + this.mInnererWertMin );
       System.out.println("Begin InnererWert=" + this.mInnererWertBegin );
       System.out.println("Data Period=" + this.Datamaxnumber );
       System.out.println("XStart(Hudu)=" + this.XStart );

    }

    private void adjustXStart( int pTrend)
    {
      if ( this.XStart > 0 )
      {
          // Aufsteigerung
          if ( pTrend >= 2 )
          {
             this.XStart = this.XStart;
          }
          else
          {
             // Absteigerung
             this.XStart = Math.PI - this.XStart;
          };
      }
      else
      {
          // Steigerung
          if ( pTrend >= 2 )
          {
             this.XStart = this.XStart;
          }
          else
          {
            // Absteigerung
            this.XStart = (-1)*Math.PI - this.XStart;
          };
      }
    }

    private double validate(double dd)
    {
      if ( dd > this.mInnererWertMax )
      {
        return this.mInnererWertMax;
      }
      else
      if ( dd < this.mInnererWertMin )
      {
        return this.mInnererWertMin;
      }
      else
      {
        return dd;
      }
    }

    private double getBaseDataNext()
    {
       //  the basic form is:
       //      (MAX+MIN)       (MAX-MIN)
       //  Y =----------   +  ----------  * sin(x)
       //         2              2
       double hudu = this.XStart + (2.0 * Math.PI * this.Currentdataindex ) / this.Datamaxnumber;
       double sinvalue = java.lang.Math.sin( hudu );
       double dd =  ( this.mInnererWertMax - this.mInnererWertMin ) * sinvalue / 2;
       return dd ;
    }

    private double createDelta(double pBaseValue )
     {
        // maximal 10% = ( 0.01 ~ 0.1 )
        double dd = ( this.mInnererWertMax - this.mInnererWertMin) *0.01 * RandomDelta.nextInt(11) ;
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

    public int getNextinnererWert()
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
       double d0 = this.getBaseDataNext();
       d0 = d0 + this.ForcedAdjust ;
       if ( this.Currentdataindex > 0 )
       {
         d0 = d0 + trend * createDelta(d0) ;
       }

       //double d1 = this.validate(d0);
       double d1 = d0;

       if ( this.mInnererWertHistory.size() > 0 )
       {
          Integer LastValue = (Integer) this.mInnererWertHistory.lastElement();
          int tt = LastValue.intValue();
          if ( Math.abs ( d1 - tt )*100 / tt > Configurator.mConfData.mInnererwertMaximalTagAbweichnung )
          {
            if  ( d1 > tt )
            {
              d1 = tt * ( 1 + Configurator.mConfData.mInnererwertMaximalTagAbweichnung/100.0);
            }
            else
            {
              d1 = tt * ( 1 - Configurator.mConfData.mInnererwertMaximalTagAbweichnung/100.0);
            }
          }
       }
       Currentdataindex++;
       this.mInnererWertHistory.add( new Integer( (int)d1 )  );
       return (int)d1;
    }

    public void setTrendHistorySize(int pSize)
    {
       this.TrendHistorySize = pSize;
    }

    public Vector getAllInnererWert()
    {
      return this.mInnererWertHistory;
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

     public int getCurrentdataindex()
     {
       return Currentdataindex;
     }

     public boolean setBeginValue(int pBeginValue)
     {
         if (( pBeginValue > this.mInnererWertMax)  || (this.mInnererWertMax<this.mInnererWertMin))
         {
            System.out.println("BeginValue is out of the allowed value [" + this.mInnererWertMin+ ","+this.mInnererWertMax+"]");
            return false;
         }
         double sin_x = 1.0*( pBeginValue - ( this.mInnererWertMax + this.mInnererWertMin)/ 2 ) /
                      ( this.mInnererWertMax - this.mInnererWertMin)/ 2 ;

         // HuDU
         this.XStart  = Math.asin( sin_x );
         this.adjustXStart( this.mTrend );
         return true;
     }

     public static void main(String args[])
     {
         InnererWertSinusGenerator gen = new InnererWertSinusGenerator();
         for (int i=0; i<600; i++)
         {
            System.out.println( i+";" + gen.getNextinnererWert());
         }
     }
  }


