package de.marketsim.agent.stockstore.stockdata;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import java.util.Random;

public class InnererwertRandomWalkGenerator
{

  public boolean   mDataReady = false;
  double[]  mInnererWert;
  double[]  mInit300;
  int    mDays;
  double mMaximalAbweichung;
  double mMinInnererWert;
  double mMaxInnererWert;

  double mAllowedRatioGrenz;
  double mInitWert;
  int    mCC_OverInitWert = 0;
  int    mCC_UnderInitWert = 0;

  public InnererwertRandomWalkGenerator(
      int pDay,
      double pInitWert,
      double pMinWert,
      double pMaxWert,
      double pMaximalAbweichung,
      double pAllowedRationGrenz )
  {
     this.mDays = pDay + 2;
     this.mMaximalAbweichung = pMaximalAbweichung;
     this.mAllowedRatioGrenz = pAllowedRationGrenz;
     this.mInitWert = pInitWert;
     this.mMinInnererWert = pMinWert;
     this.mMaxInnererWert = pMaxWert;
  }

  public int[] getInit300()
  {
     int dd[] = new int[301];
     for (int i=0; i<300;i++)
     {
       dd[i] = (int) this.mInit300[i];
     }
     dd[300] = (int) this.mInnererWert[0] ;
     return dd;
  }

  public int[] getInnererWert()
  {
    int dd[] = new  int[ mInnererWert.length ];
    for (int i=0; i<mInnererWert.length;i++)
    {
      dd[i] = (int) this.mInnererWert[i];
    }
    return dd;
  }


  public int getInnererWertAtDay(int pDay)
  {
     return (int) this.mInnererWert[pDay];
  }

  public void prepareInnererWert()
  {
    this.mDataReady = false;

    while ( true )
    {
      System.out.println("preparing Innerwert.....");
      this.createInnererWert();
      int mehrheit   = Math.max( this.mCC_OverInitWert, this.mCC_UnderInitWert );
      int minderheit = Math.min( this.mCC_OverInitWert, this.mCC_UnderInitWert );
      if ( minderheit == 0 )
      {
         // Result is invalid
      }
      else
      {
        if ( mehrheit *1.0 / minderheit > this.mAllowedRatioGrenz )
        {
           // erneut erzeugen
        }
        else
        {
          // OK,
          System.out.println( "CC_OverInitWert=" + this.mCC_OverInitWert + " CC_UnderInitwert=" + this.mCC_UnderInitWert );
          this.mDataReady = true;
          break;
        }
      }
    }
  }

  public int getCC_OverInitWert()
  {
     return this.mCC_OverInitWert;
  }

  public int getCC_UnderInitWert()
  {
    return this.mCC_UnderInitWert;

  }

  private void createInnererWert()
  {

     double[] dd = new double[ this.mDays ];

     this.mInit300 = new double[ 300 ];

     Random rd1 = new Random();
     Random rd2 = new Random();

     // 10 fach vergrößer
     int Abweichung = (int)( this.mMaximalAbweichung*10);
     int dif = 2*Abweichung+1;

     // create 301 Init Werten
     double p1,p2,p3,abweichungsprozent;

     for (int i=299; i>=0; i--)
     {
       abweichungsprozent = ( (-1.0) * Abweichung + rd1.nextInt(dif))*0.1*0.01;
       if ( i==299 )
       {
         this.mInit300[i] = this.mInitWert * ( 1 + abweichungsprozent );
       }
       else
       {
         this.mInit300[i] = this.mInit300[i+1] * ( 1 + abweichungsprozent );
       }

       /*
       if ( this.mInit300[i] >= this.mInitWert )
       {
        this.mCC_OverInitWert ++;
       }
       else
       {
         this.mCC_UnderInitWert ++;
       }
       */
     }

     this.mInnererWert = new double[ this.mDays ];

     this.mCC_OverInitWert  =0;
     this.mCC_UnderInitWert =0;

     dd[0] = this.mInitWert;

     for (int nn = 1; nn<this.mDays; nn++)
     {
             if ( nn < 5 )
             {
                 abweichungsprozent = (-1.0 * Abweichung + rd1.nextInt(dif))*0.1*0.01;
                 p1 = dd[nn-1] * ( 1 + abweichungsprozent );
                 dd[nn] = p1;
                 if ( (dd[nn] > this.mMaxInnererWert) || (dd[nn] < this.mMinInnererWert)  )
                 {
                   p1 = dd[nn-1] * ( 1 - abweichungsprozent );
                 }
             }
             else
             {
                 abweichungsprozent = (-1.0 * Abweichung + rd1.nextInt(dif) )*0.1*0.01;
                 p1 = dd[nn-1]*0.7  * ( 1 + abweichungsprozent );
                 p2 = dd[nn-2]*0.15 * ( 1 + abweichungsprozent );
                 p3 = dd[nn-3]*0.15 * ( 1 + abweichungsprozent );
                 dd[nn] = p1 + p2 + p3;

                 if ( (dd[nn] > this.mMaxInnererWert) || (dd[nn] < this.mMinInnererWert)  )
                 {
                   p1 = dd[nn-1]*0.7  * ( 1 - abweichungsprozent );
                   p2 = dd[nn-2]*0.15 * ( 1 - abweichungsprozent );
                   p3 = dd[nn-3]*0.15 * ( 1 - abweichungsprozent );
                   dd[nn] = p1 + p2 + p3;
                 }
             }

             if ( dd[nn] >= this.mInitWert )
             {
                this.mCC_OverInitWert =this.mCC_OverInitWert+1;
             }
             else
             {
                this.mCC_UnderInitWert = this.mCC_UnderInitWert + 1;
             }
      }

      this.mInnererWert = dd;
  }

  /**
   * 300 + Handelsday Data
   *
   * @return
   */
  public double[] getAllGeneratedData()
  {
     double dd[] = new double[ 300 + this.mDays ];
     for ( int i=0; i<300; i++)
     {
       dd[i] = this.mInit300[i];
     }
     for ( int i=0; i<this.mDays; i++)
     {
       dd[300+i] = this.mInnererWert[i];
     }
     return dd;
  }

}