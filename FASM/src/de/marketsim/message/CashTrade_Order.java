package de.marketsim.message;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */
import jade.core.AID;
import de.marketsim.SystemConstant;

public class CashTrade_Order implements java.io.Serializable
{

  public int      mAgentType  = 0;
  public boolean  mTypeChanged = false; // Default

  public int      mCash2 = 0;
  public double   mBuyLimit = 0.0;  // kurs wished
  public double   mSellLimit = 0.0;  // kurs wished

  public char     mOrderWish = 'W'; // wait ???????
  public double   mNextDayInnerwert=0.0;
  // !!!!!!!!!!!!!!!!
  // these 2 variable are addtinal variable for GwinnProzent Display
  // They are not involved into Exchange
  // These 2 Variable will be reported to StockStore Programm
  // when the Agent sends its Order
  public double   mAbsoluteGewinnProzent = 0.0;
  public double   mRelativeGewinnProzent = 0.0;

  // if this Order is involved this Exchanging
  public boolean  mBuyPerformed  = false;
  public boolean  mSellPerformed = false;

  public double   mInvolvedCash1    = 0;

  public int      mTradeCash2       = 0;
  public double   mFinalKurs        = 0;
  public boolean  mCheapestOrBest   = false;
  public char     mTradeResult = ' ';

  public double   mTax_Fixed;  // Fix Steuer
  public double   mTax_Extra;  // Extra Steuer

  public AID      mAID = null;
  public String   mLimitCalcBase = "";
  /*
    Constructor für RandomTrader und Tobintax
    auch für INvestor und NoiseTrader wenn sie für "Sleep" entscheiden.
    who buys/sells how many pieces in which Limit
    who: AID
    Wish: Sell/Buy
    How many: Menge
    Limit:    Limit
  */
  public CashTrade_Order(int pAgentType,  char pWish, int pCash2, double pLimit, String pLimitCalcBase)
  {
     mAgentType = pAgentType;
     mOrderWish = pWish;
     mCash2     = pCash2;
     if ( mOrderWish == SystemConstant.WishType_Buy )
     {
         mBuyLimit     = pLimit;
     }
     else
     if ( mOrderWish == SystemConstant.WishType_Sell )
     {
           mSellLimit     = pLimit;
     }
     mLimitCalcBase= pLimitCalcBase;
  }

  public CashTrade_Order(int pAgentType,  char pWish, int pCash2, double pBuyLimit, double pSellLimit, String pLimitCalcBase)
  {
     mAgentType = pAgentType;
     mOrderWish = pWish;
     mCash2     = pCash2;
     mBuyLimit  = pBuyLimit;
     mSellLimit = pSellLimit;
     mLimitCalcBase= pLimitCalcBase;
  }

  public String toString()
  {
           String ss = "";
           if ( mOrderWish == SystemConstant.WishType_Buy )
           {
               ss = "Agentype=" + mAgentType+ ";" +this.mOrderWish+";Cash2="+this.mCash2 +";BuyLimit=" + this.mBuyLimit;
           }
           else
           if ( mOrderWish == SystemConstant.WishType_Sell )
           {
             ss = "Agentype=" + mAgentType+ ";" +this.mOrderWish+";Cash2="+this.mCash2 +";SellLimit=" + this.mSellLimit;
           }
           else
           if ( mOrderWish == SystemConstant.WishType_Wait )
           {
             ss = "Agentype=" + mAgentType+ ";" +this.mOrderWish+";Cash2="+this.mCash2 +";Limit=" + this.mSellLimit;
           }
           else
           {
             ss = "Agentype=" + mAgentType+ ";" +this.mOrderWish+";Cash2="+this.mCash2 +";BuyLimit=" + this.mBuyLimit+";SellLimit="+ this.mSellLimit;
           }
    return  ss;
  }

  public String getResult()
  {

    String ss = "";
    if ( mOrderWish == SystemConstant.WishType_Buy )
    {
      ss ="Agentype=" + mAgentType + ";" +this.mOrderWish+";"+
      "Cash2="+this.mCash2 +";BuyLimit=" + this.mBuyLimit + " Performed InvolvedCash1" +
      this.mInvolvedCash1 + "; TradeCash2="+ this.mTradeCash2 +
        " FixTax=" + this.mTax_Fixed +"; ExtraTax=" + this.mTax_Extra ;
    }
    else
    if ( mOrderWish == SystemConstant.WishType_Sell )
    {
      ss = "Agentype=" + mAgentType + ";" +this.mOrderWish+";"+
        "Cash2="+this.mCash2 +";SellLimit=" + this.mSellLimit + " Performed InvolvedCash1" +
        this.mInvolvedCash1 + "; TradeCash2="+ this.mTradeCash2 +
        " FixTax1=" + this.mTax_Fixed +"; ExtraTax=" + this.mTax_Extra ;
    }
    else
    if ( mOrderWish == SystemConstant.WishType_Wait )
    {
      ss = "Agentype=" + mAgentType + ";" +this.mOrderWish+";"+
        "Cash2="+this.mCash2 +";Limit=" + this.mBuyLimit + " Performed InvolvedCash1" +
        this.mInvolvedCash1 + "; TradeCash2="+ this.mTradeCash2 +
        " FixedTax=" + this.mTax_Fixed +"; ExtraTax=" + this.mTax_Extra ;
    }
    else
    {
      ss = "Agentype=" + mAgentType + ";" +this.mOrderWish+";"+
        "Cash2="+this.mCash2 +";BuyLimit=" + this.mBuyLimit + ";SellLimit=" + this.mSellLimit +
        " Performed InvolvedCash1" +
        this.mInvolvedCash1 + "; TradeCash2="+ this.mTradeCash2 +
        " FixedTax1=" + this.mTax_Fixed +"; ExtraTax=" + this.mTax_Extra ;
    }
    return  ss;
  }

  public String getOrderPerformedInfo()
  {
      return "BuyLimit=" + this.mBuyLimit + " SellLimit=" + this.mSellLimit + "BuyPerformed=" +  this.mBuyPerformed + " SellPerformed=" + this.mSellPerformed + " mInvolvedCash1=" + this.mInvolvedCash1 + " mInvolvedCash2=" + this.mTradeCash2 +" mFinalePrice=" + this.mFinalKurs;
  }

}
