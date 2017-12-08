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

public class AktienTrade_Order implements java.io.Serializable
{

  public int      mOriginalAgentType     = 0;

  public int      mAgentType     = 0;
  public boolean  mTypeChanged   = false; // Default
  public int      mBuyMenge      = 0;
  public int      mSellMenge     = 0;
  public int      mBuyLimit      = 0;
  public int      mSellLimit     = 0;
  public char     mOrderWish     = 0;
  public boolean  mBuyPerformed  = false;
  public boolean  mSellPerformed = false;

  public int      mNextDayInnerwert=0;

  // !!!!!!!!!!!!!!!!
  // these 2 variables are addtinal variable for GwinnProzent Display
  // They are not involved into Exchange
  // These 2 Variable will be reported to StockStore Programm
  // when the Agent sends its Order
  public double   mAbsoluteGewinnProzent = 0.0;
  public double   mRelativeGewinnProzent = 0.0;

  // if this Order is involved into the Trade
  public int      mTradeMenge = 0;
  public int      mFinalPrice   = 0;

  public boolean  mCheapestOrBest = false;
  public char     mTradeResult = ' ';
  public AID      mAID = null;
  public String   mLimitCalcBase = "";

  // Nur für BlankoAgent
  public int      mBlankoStateChange = 0;  // Default, Keine Änderung
  // =1;  Activated
  // =2;  Deactivated;
  // =

  public SimpleSingleOrder[] mBuyOrders  = new SimpleSingleOrder[0];
  public SimpleSingleOrder[] mSellOrders = new SimpleSingleOrder[0];


  private boolean containsExtendedOrder = false;

  public void setBlankoActivated()
  {
      mBlankoStateChange = 1;
  }

  public void setBlankoDeactivated()
  {
      mBlankoStateChange = 2;
  }

  public boolean isBlankoStateChanged()
  {
      return mBlankoStateChange != 0;
  }

  public boolean isBlankoActivated()
  {
      return mBlankoStateChange == 1;
  }

  public boolean isBlankoDeactivated()
  {
      return mBlankoStateChange == 2;
  }

  /*
    Constructor für RandomTrader und TobinTax
    und für Investor und NoiseTrader wenn sie für "Sleep" entscheiden
    who buys/sells how many pieces in which Limit
    who: AID
    Wish: Sell,Buy,Wait,
    How many: Menge
    Limit:    Limit
  */
  public AktienTrade_Order(int pAgentType, char pWish, int pMenge, int pLimit, String pLimitCalcBase, int pOriginalAgentType  )
  {
     mAgentType         = pAgentType;
     mOriginalAgentType = pOriginalAgentType;
     mOrderWish = pWish;

     if ( mOrderWish == SystemConstant.WishType_Buy )
     {
           mBuyLimit     = pLimit;
           mBuyMenge     = pMenge;
     }
     else
     if ( mOrderWish == SystemConstant.WishType_Sell )
     {
           mSellLimit     = pLimit;
           mSellMenge     = pMenge;
     }
      mLimitCalcBase= pLimitCalcBase;
  }

  /**
   *
   * Investor verwenden diesen Constructor
   * @param pAgentType
   * @param pWish
   * @param pMenge
   * @param pBuyLimit
   * @param pSellLimit
   */

  public AktienTrade_Order(int pAgentType, char pWish, int pBuyMenge, int pBuyLimit, int pSellMenge, int pSellLimit, String pLimitCalcBase, int pOriginalAgentType )
  {
     mAgentType = pAgentType;
     mOriginalAgentType = pOriginalAgentType;
     mOrderWish = pWish;
     mBuyMenge  = pBuyMenge;
     mBuyLimit  = pBuyLimit;
     mSellMenge = pSellMenge;
     mSellLimit = pSellLimit;
     mLimitCalcBase= pLimitCalcBase;
  }

  /*
  Constructor of Special Order: CheapestBuy or BestSell
  */
  public AktienTrade_Order(int pAgentType, char pWish, int pMenge, int pOriginalAgentType )
  {
     this.mAgentType      = pAgentType;
     this.mOriginalAgentType = pOriginalAgentType;
     this.mOrderWish      = pWish;

     if ( mOrderWish == SystemConstant.WishType_Buy )
     {
           mBuyMenge     = pMenge;
     }
     else
     if ( mOrderWish == SystemConstant.WishType_Sell )
     {
           mSellMenge     = pMenge;
     }
     this.mCheapestOrBest = true;
  }

  /**
   * New constructor for 8 Orders Concept
   * 2007-11-26
   *
   * @param pAgentType
   * @param pAllOption
   * @param pLimitCalcBase
   * @param pOriginalAgentType
   */

  public AktienTrade_Order(int pAgentType, SimpleSingleOrder[] pBuyOrders, SimpleSingleOrder[] pSellOrders, String pLimitCalcBase, int pOriginalAgentType )
  {
     mAgentType = pAgentType;
     mOriginalAgentType = pOriginalAgentType;
     this.mBuyOrders  = pBuyOrders;
     this.mSellOrders = pSellOrders;
     mLimitCalcBase= pLimitCalcBase;
     this.containsExtendedOrder = true;

  }

  /**
   * Nur für Investor Orders
   */
  public void setPerformedBuyMenge(int pOrderNo, int pPerformedMenge)
  {
      mBuyOrders[pOrderNo].mPerformedMenge = pPerformedMenge;
  }

  /**
   * Nur für Investor Orders
   */
  public void setPerformedSellMenge(int pOrderNo, int pPerformedMenge)
  {
      mSellOrders[pOrderNo].mPerformedMenge = pPerformedMenge;
  }

  public String toString()
  {
    return "Type="+ this.mAgentType+","+
           "Wish="+ this.mOrderWish + "," +
           "BuyMenge=" + this.mBuyMenge + "," +
           "BuyLimit=" + this.mBuyLimit + "," +
           "SellMenge=" + this.mSellMenge + "," +
           "SellLimit=" + this.mSellLimit + "," +
           "OriginalAgentType=" + this.mOriginalAgentType ;
  }

  public String getOrderPerformedInfo()
  {
      return  "BuyLimit=" + this.mBuyLimit + " SellLimit=" + this.mSellLimit + "  BuyPerformed=" +  this.mBuyPerformed + " SellPerformed=" + this.mSellPerformed + " mTradeMenge=" + this.mTradeMenge + " mFinalePrice=" + this.mFinalPrice;
  }

  // 2007-11-24  8 Orders Konzept
  /**
   * @return
   */
  public SimpleSingleOrder[] getBuyOrders()
  {
      return this.mBuyOrders;
  }

  public SimpleSingleOrder[] getSellOrders()
  {
      return this.mSellOrders;
  }

  public boolean containsExtendedOrder()
  {
     return this.containsExtendedOrder;
  }


}