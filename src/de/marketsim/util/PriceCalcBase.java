package de.marketsim.util;

/**
 * Überschrift:
 * Beschreibung:
 * Copyright:     Copyright (c) 2003
 * Organisation:
 * @author
 * @version 1.0
 */

public class PriceCalcBase
{

  public double mLimit     =0 ;

  // für Speicherung von Summe von BuyMenge bis dieser Preis
  public int mBuyMenge  =0;
  // für Speicherung von Summe von SellMenge bis dieser Preis
  public int mSellMenge =0;
  public int mCalcMenge =0;
  public int mPossibleTradeMenge  =0;  // PossibleMenge = MATH.MIN(mSellMenge, mBuyMenge)
  public double mPossibleTradeVolume  =0;  // PossibleMenge * Preis

  public int mOriginalBuyMenge  =0;
  public int mOriginalSellMenge =0;

  public PriceCalcBase( double pLimit  )
  {
     this.mLimit = pLimit;
  }

  public double getPossibleTradeVolume()
  {
     this.mCalcMenge = Math.min( this.mBuyMenge, this.mSellMenge);
     this.mPossibleTradeVolume = this.mCalcMenge * this.mLimit;
     return this.mPossibleTradeVolume;
  }

}