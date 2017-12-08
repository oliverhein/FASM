/**
 * Überschrift:
 * Beschreibung:
 * Copyright:     Copyright (c) 2003
 * Organisation:
 * @author
 * @version 1.0
 */

package de.marketsim.agent.stockstore.stockdata;

import java.util.Vector;


public class PriceHistoryGenerator
{

  private int mDatanumber = 300;
  private int mpricenumber=0;
  private int mPriceMax  = -1;
  private int mPriceMin  = 100000000;
  private int lastprice;

  private Vector  mPriceHistory = new Vector();
  private int high, low;

  //private InnererWertGenerator mInnererWert = new InnererWertGenerator();

  public PriceHistoryGenerator()
  {

  }

  // generate  default 300 random price
  public void generatePriceHistory()
  {
      dogenerate();
  }

  public void generatePriceHistory(int  pNumber )
  {
      mDatanumber = pNumber;
      dogenerate();
  }

  // this Methode generate per time only one Data.
  // the data is random +-3% of the last elemnet of mPriceHistory.
  public int generateonePrice()
  {
    int LastDataPrice= Integer.parseInt((mPriceHistory.lastElement().toString()));
    java.util.Random rd=new java.util.Random();
    int n = 1000;
    int NewPrice=rd.nextInt(n);

    Integer kk ;
    if (NewPrice >= n/2)
    {
      kk = new Integer( (int) (LastDataPrice*(1 + 0.03)) );
    }
    else
    {
      kk= new Integer( (int) (LastDataPrice*(1 - 0.03)) );
    }
    this.lastprice = kk.intValue();
    return kk.intValue();
  }

  // 23.09.2004  New Modell
  public int generateonePrice(double pProfit, double pInterestrate)
  {
     this.lastprice = (int) ( pProfit / pInterestrate );
     return this.lastprice;
  }


  // 23.09.2004  New Modell

  /*
  public Vector generate300Price( Vector  pProfitVector , Vector pInterestRateVector)
  {
    // From Logic it is garanteed that 300 Data Vector will be passed in Parameter
    for (int n = 0; n<pProfitVector.size() ; n++)
    {
      double profit   =  ((Double)pProfitVector.elementAt(n) ).doubleValue();
      double interest =  ((Double)pInterestRateVector.elementAt(n) ).doubleValue();
      this.lastprice = (int)( profit/ interest );
      Integer kk = new Integer ( this.lastprice  );
      mPriceHistory.addElement( kk );
    }
    return mPriceHistory;
  }

*/

  public int getLastPrice()
  {
    return this.lastprice;
  }

  private void updateMaxMin( int tt)
  {
          if ( tt > mPriceMax )
          {
             mPriceMax = tt;
          }
          else
          if ( tt < mPriceMin )
          {
             mPriceMin = tt;
          }
  }

  public void dogenerate()
  {
      mPriceMax =-1;
      mPriceMin = 2000;
      high = 0;
      low = 0;
      mPriceHistory.clear();
      java.util.Random rd=new java.util.Random();

      int base=1000;
      int tt = 0;
      for (int n = 0; n < this.mDatanumber; n++)
      {
        int m = rd.nextInt( this.mDatanumber );
        if (m > this.mDatanumber/2 )
        {
          base = (int) ( base*(1 + 0.03) );
          tt = base;
          high=high + 1;
          Integer newdata = new Integer(tt);
          this.mPriceHistory.addElement( newdata );
          updateMaxMin(tt);
        }
        else
        {
          base=(int) ( base*(1-0.03) );
          tt = base;
          low=low+1;
          Integer newdata=new Integer( tt );
          this.mPriceHistory.addElement( newdata );
          updateMaxMin(tt);
        }
      }

      this.lastprice = tt;
      System.out.println( mPriceMax );
      System.out.println( mPriceMin );
  }

  public Vector getPriceHistory()
  {
    return this.mPriceHistory;
  }

  public int getHistoryDataNumber()
  {
    return this.mDatanumber;
  }

  public int getPriceMax()
  {
    return this.mPriceMax;
  }

  public int getPriceMin()
  {
    return this.mPriceMin;
  }

}