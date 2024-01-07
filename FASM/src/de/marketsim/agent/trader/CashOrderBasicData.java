package de.marketsim.agent.trader;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

public class CashOrderBasicData
{

 public char     finaldecision;
 public int      finalmenge;
 public double   BuyLimit;
 public double   SellLimit;

public CashOrderBasicData( char pDecision, int pMenge, double pBuyLimit, double pSellLimit)
 {
    finaldecision = pDecision;
    finalmenge    = pMenge;
    BuyLimit      = pBuyLimit;
    SellLimit     = pSellLimit;
 }

}


