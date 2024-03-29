
package de.marketsim.util;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c)  2008 </p>
 * <p>Organisation: </p>
 * @author  Xining Wang
 * @version 1.0
 */

import java.io.Serializable;

public class DailyOrderStatistic implements Serializable
{
	  /**
	   * 2008.01  Neue Statistic for Daily Order Book
	   */

	  public  double mHighestBuyLimit;
	  public  double mHighestBuyLimit_Aktien;
	  public  double mHighestBuyLimit_Volume;

	  public  double mLowesSellLimit;
	  public  double mLowesSellLimit_Aktien;
	  public  double mLowesSellLimit_Volume;

	  public  double mHighBuyAndLowSell_Average;
	  public  double mHighBuyAndLowSell_Difference;
	  public  double mHighBuyAndLowSell_Difference_Procent;

	  public  double mAverageDeviation_BuySide;
	  public  double mAverageDeviation_SellSide;

          public int mBuyMenge_Of_AllBuyOrder   = 0;
          public int mBuyMenge_With_Limit       = 0;
          public int mCheapestBuyMenge          = 0;

          public int mSellMenge_Of_AllSellOrder = 0;
          public int mSellMenge_With_Limit      = 0;
          public int mBestenSellMenge           = 0;

          //protected int mOpenBuyMenge_Of_AllBuyOrder   = 0;
          //protected int mOpenSellMenge_Of_AllSellOrder = 0;

	  public  double mNotPerformedAktien_BuyWish_Total;        // Total BuyMenge  - Performed Menge
	  public  double mNotPerformedAktien_SellWish_Total;       // Total SellMenge - Performed Menge


          public  double mNotPerformedAktien_BuyWish_Total_ExcludeCheapestBuy;     // Total BuyMenge  - Performed Menge
          public  double mNotPerformedAktien_SellWish_Total_ExcludeBestSell;       // Total SellMenge - Performed Menge

	  public  double mNotPerformedAktien_BuyWish_At_NewPrice;  // Possible BuyMenge  - Performed Menge
	  public  double mNotPerformedAktien_SellWish_At_NewPrice; // Possible SellMenge - Performed Menge


          public static String NORMAL_MODE = "LIMITBUY+LIMITSELL";
          public static String CHEAPESTBUY_BESTSELLMODE = "CHEAPESTBUY_BESTSELLMODE";
          public static String CHEAPESTBUY_LIMITSELL_AND_BESTSELL_MODE = "CHEAPESTBUY+LIMITSELL+BESTSELL";
          public static String LIMITBUYCHEAPESTBUY_BESTSELL_MODE = "NORMALMODE";

          public String mCalculationMode = "undefined";

          public static int mBasicDataBumber = 5;
          public double mHighestBuyLimitList[] = new double[mBasicDataBumber];
          public double mHighestBuyMengeList[] = new double[mBasicDataBumber];
          public double mHighestBuyMengeSumme = 0;


          public double mLowestSellLimitList[] = new double[mBasicDataBumber];
          public double mLowestSellMengeList[] = new double[mBasicDataBumber];
          public double mLowestSellMengeSumme = 0;

          public int    mHighestBuyLimitNumber_Real = 0;
          public int    mLowestSellLimitNumber_Real  = 0;

	  public DailyOrderStatistic()
	  {

	  }


}