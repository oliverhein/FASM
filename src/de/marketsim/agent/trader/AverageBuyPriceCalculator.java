package de.marketsim.agent.trader;

/**
 * An intelligent Average Price Calculator
 *
 * 2007-05-03
 *
 */

import java.util.Vector;
import java.util.Hashtable;

public class AverageBuyPriceCalculator {


  private int     mTimeWindow = 10; // Default 10 Tage
  private Vector  mPriceList  = new Vector();

  private int     mOldestPriceDay            = 0;

  public AverageBuyPriceCalculator(int pTimeWindow)
  {

      this.mTimeWindow = pTimeWindow;

  }

  public AverageBuyPriceCalculator()
  {
  }

  public void setTimeWindow(int pWindow)
  {
      this.mTimeWindow = pWindow;
  }

  public void addNewPrice ( int pDay, double pNewPrice )
  {

           //System.out.println("Updating list with "  + pDay + ". Day data" );
       if ( this.mOldestPriceDay == 0  )
       {
         DataItem  dd = new DataItem(pDay, pNewPrice);
         this.mPriceList.add( dd );
         this.mOldestPriceDay = pDay;
       }
       else
       {
           int grenzday =  pDay - mTimeWindow;
           // Back to mTimeWindow Tage
           if ( this.mOldestPriceDay <= grenzday )
           {
              // these daten sind nicht mehr benötigt.
                  do
                  {
                          //System.out.println("deleting " + this.mOldestPriceDay+ ". day");
                      // Delete the TOP element
                          this.mPriceList.remove(0);

                          if ( ! this.mPriceList.isEmpty() )
                          {
                                  DataItem tt = (DataItem) this.mPriceList.elementAt(0);
                                  this.mOldestPriceDay = tt.mDay;
                          }
                          else
                          {
                                  // nothing to delete
                                  this.mOldestPriceDay = 0;
                                  break;
                          }
                  }
                  while ( this.mOldestPriceDay <= grenzday  );
           }
           DataItem  dd = new DataItem(pDay, pNewPrice);
           this.mPriceList.add( dd );

           if ( this.mOldestPriceDay == 0 )
           {
                   this.mOldestPriceDay = pDay;
           }

       }
       //System.out.println("List.size()=" + this.mPriceList.size() );

  }

  public double getAcceptableAveragePrice()
  {
          if ( this.mPriceList.size() == 0 )
          {
                  return 0;
          }

      double ss =0;
      for ( int i=0; i < this.mPriceList.size(); i++ )
      {
          ss = ss + ( (DataItem) this.mPriceList.elementAt(i) ).mPrice;
      }
      return ss /this.mPriceList.size();
  }

  public void clear()
  {
      this.mPriceList.clear();
      this.mOldestPriceDay =0;

  }

  /**
   *
   * self test programm 1
   *
   */
  public void test1 ()
  {
        addNewPrice(1, 10);
        addNewPrice(2, 10);
        addNewPrice(3, 10);
        addNewPrice(4, 10);
        addNewPrice(5, 10);
        addNewPrice(6, 10);
        addNewPrice(7, 10);
        double ss = this.getAcceptableAveragePrice();
        System.out.println("Avg=" + ss);

  }

  /**
   *
   * self test programm 2
   *
   */
  public void test2 ()
  {
        addNewPrice(1,  10);
        addNewPrice(2,  10);
        addNewPrice(10, 8);
        addNewPrice(11, 8);
        addNewPrice(12, 8);
        double ss = this.getAcceptableAveragePrice();
        System.out.println("Avg=" + ss);

        addNewPrice(15, 16);
        addNewPrice(16, 16);
        ss = this.getAcceptableAveragePrice();
        System.out.println("Avg=" + ss);

        addNewPrice(17, 16);
        ss = this.getAcceptableAveragePrice();
        System.out.println("Avg=" + ss);



  }

  public static void main(String[] args)
  {
       AverageBuyPriceCalculator pp = new  AverageBuyPriceCalculator( 5 );
      System.out.println("call test1() ---------------------");
      pp.test1();
      pp.clear();
      System.out.println("call test2() ---------------------");
      pp.test2();

  }

  /**
   * Inner Class for One-Day-Data
   * @author fchen
   *
   */
  class DataItem
  {

        public int mDay;
        public double mPrice;

    public DataItem(int pDay, double pPrice )
    {
            this.mDay   = pDay;
            this.mPrice = pPrice;
    }

  }


}

