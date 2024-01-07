/**
 * Überschrift:
 * Beschreibung:
 * Copyright:     Copyright (c) 2003
 * Organisation:
 * @author
 * @version 1.0
 */

package de.marketsim.util;

import jade.core.AID;

public class SingleOrder
{

  public int      mOriginalAgentType = 0;

  public int      mAgentType  = 0;
  public int      mMenge = 0;
  public double   mLimit = 0;
  public String   mLimitReason = "";

  //public double   mLimit_Money = 0;

  public char     mOrderWish = 0;
  public boolean  mInvolvedExchange = false;

  // if this Order is involved this Exchanging
  public int      mTradeMenge = 0;
  public int      mFinalPrice   = 0;
  public boolean  mCheapestOrBest = false;
  public char     mTradeResult = ' ';
  public AID      mAID = null;

  // wird benutzt bei MoneyMarket
  public double   mInvolvedCash1 = 0.0;  // = mTax_Part1 + mTax_Part2 wenn er CASH2 verkäuft
  public double   mTax_Fixed = 0.0;
  public double   mTax_Extra = 0.0;
  public double   mFinalKurs = 0;

  // Nur für Order of Fundamental Investor
  public boolean  mOrderOfFundamentalInvestor = false;
  public int      mOrderInternalNo = -1; // Default -1, an invalid index

  /*
    Standard Constructor of Order:
    who buys/sells how many pieces in which Limit
    who: AID
    Wish: Sell/Buy
    How many: Menge
    Limit:    Limit
  */
  public SingleOrder(AID pAID, char pWish, int pAgentType, int pMenge, double pLimit, String pLimitReason, int pOriginalAgentType )
  {
     mAID         = pAID;
     mAgentType   = pAgentType;
     mOrderWish   = pWish;
     mMenge       = pMenge;
     mLimit       = pLimit;
     mLimitReason = pLimitReason;
     mOriginalAgentType =pOriginalAgentType;
  }


  /*
  Constructor of Special Order: CheapestBuy or BestSell
  */
  public SingleOrder(AID pAID, int pAgentType, char pWish, int pMenge, int pOriginalAgentType )
  {
     mAID       = pAID;
     mAgentType = pAgentType;
     mOrderWish = pWish;
     mMenge     = pMenge;
     this.mCheapestOrBest = true;
     this.mOriginalAgentType = pOriginalAgentType;
  }

  public String getAgentName()
  {
    return this.mAID.getLocalName();
  }

}