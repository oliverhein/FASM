package de.marketsim.util;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 01.2008 </p>
 * <p>Organisation: </p>
 * @author unbekannt
 * @version 1.0
 */

public class DailyOrderbookStatisticNotUsed
{
	public static String NORMAL_MODE = "LIMITBUY+LIMITSELL";
	public static String CHEAPESTBUY_BESTSELLMODE = "CHEAPESTBUY_BESTSELLMODE";
	public static String CHEAPESTBUY_LIMITSELL_AND_BESTSELL_MODE = "CHEAPESTBUY+LIMITSELL+BESTSELL";
	public static String LIMITBUYCHEAPESTBUY_BESTSELL_MODE = "NORMALMODE";

	public String mCalculationMode = "undefined";

        public static int mBasicDataBumber = 5;
	public double mHighestBuyLimitList[] = new double[mBasicDataBumber];
	public double mLowestSellLimitList[] = new double[mBasicDataBumber];
	public int    mHighestBuyLimitNumber_Real = 0;
	public int    mLowestBuyLimitNUmber_Real  = 0;

	public double mHighestBuyLimit = 0;
	public double mLowestSellLimit = 0;
	public double mDifferenceHigh2Low =0;
	public double mDifferenceHigh2LowProcent =0;

	public double mAktienMengeAtHighestBuyLimit =0;
	public double mAktienMengeAtAtLowestSellLimit =0;

	public double mVolumeAtHighestBuyLimit =0;
	public double mVolumeAtLowestSellLimit =0;

	public int    mPerformedAktien      = 0;
	public int    mNotPerformedAktienOfBuyOrder = 0;
	public int    mNotPerformedAktienOfSellOrder = 0;

}