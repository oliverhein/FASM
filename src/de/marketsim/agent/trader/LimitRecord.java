package de.marketsim.agent.trader;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

public class LimitRecord {

  public int    mDay       = 0;
  public double mSellLimit = 0.0;
  public double mBuyLimit  = 0.0;

  public LimitRecord (int pDay,  double pBuyLimit, double pSellLimit)
  {
     this.mDay       = pDay;
     this.mBuyLimit  = pBuyLimit;
     this.mSellLimit = pSellLimit;
  }

}