package de.marketsim.message;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006,2008 </p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import de.marketsim.util.DailyOrderStatistic;

public class StoreDailyTradeSummary  implements java.io.Serializable
{

  public  double   mKurs;
  public  double   mTradeMenge;  // in Stuck
  public  double   mTradeVolume;
  public  char     mTradeStatus;
  public  double   mInnererWert;
  public  double   mDifferenz;
  public  double   mDifferenzProzent;
  public  DailyOrderStatistic mDailyOrderStatistic;

  public StoreDailyTradeSummary(double pKurs, char pTradeStatus,double pTradeMenge, double pTradeVolume,  double pInnererWert )
  {
    this.mKurs = pKurs;
    this.mTradeMenge  = pTradeMenge;
    this.mTradeVolume = pTradeVolume;
    this.mTradeStatus = pTradeStatus;
    this.mInnererWert = pInnererWert;
    //2007-12-08 changed
    this.mDifferenz        = pKurs - pInnererWert;
    this.mDifferenzProzent = Math.round( this.mDifferenz * 100 / pInnererWert );

  }
  
  public void setDailyOrderStatistic(DailyOrderStatistic pDailyOrderStatistic)
  {
	  
	  mDailyOrderStatistic = pDailyOrderStatistic;
	  
  }
  
  
}