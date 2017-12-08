package de.marketsim.message;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

public class OrderBasicData
{
 // Für NoiseTrader
 public char   mFinaldecision;  // Possible Value: M--> Mixed,B-->Buy,S-->Sell,N-->Wait
 public int    mMenge   = 0;
 public int    mLimit   = 0;
 public String mLimitCalcBase= "";

 // Für Investor
 public boolean mOrderOfInvestor = false;
 public SimpleSingleOrder []  mBuyoptions  =new SimpleSingleOrder[0];
 public SimpleSingleOrder []  mSelloptions =new SimpleSingleOrder[0];

 public OrderBasicData( char pDecision, int pMenge, int pLimit, String pLimitCalcBase)
 {
    mFinaldecision = pDecision;
    mMenge         = pMenge;
    mLimit         = pLimit;
    mLimitCalcBase = pLimitCalcBase;
 }

 public OrderBasicData( SimpleSingleOrder[]  pBuyoptions, SimpleSingleOrder[]  pSelloptions, String pLimitCalcBase)
 {
    mFinaldecision = 'M';
    mOrderOfInvestor = true;
    mBuyoptions  = pBuyoptions;
    mSelloptions = pSelloptions;
    mLimitCalcBase = pLimitCalcBase;
 }

}

