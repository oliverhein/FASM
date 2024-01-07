package de.marketsim.agent.trader;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import java.util.Hashtable;
import java.util.Vector;

public class DailyKursChangeContainer {

  private Vector     mDailyKursChangeHistoryData  = new Vector();
  private Hashtable  mDailyKursChangeHistoryMarke = new Hashtable();

  public synchronized void addNewKursChaneg(String pDay, double pChange)
  {
      if ( ! mDailyKursChangeHistoryMarke.containsKey(pDay) )
      {
          Double dd = new Double(pChange);
          mDailyKursChangeHistoryMarke.put(pDay, dd );
          mDailyKursChangeHistoryData.add(dd);
      }
  }

  public double getAverage(int pLastday )
  {
      double sum = 0.0;
      for ( int i=0; i< pLastday; i++)
      {
         sum = sum +  ( ( Double ) mDailyKursChangeHistoryData.elementAt( mDailyKursChangeHistoryData.size() - i ) ).doubleValue();
      }
      return sum / pLastday;
  }

  public void ClearOlddata()
  {
      mDailyKursChangeHistoryMarke.clear();
      mDailyKursChangeHistoryData.clear();
  }

  public static void main(String[] args) {
  }
}