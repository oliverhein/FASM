/**
 * Überschrift:
 * Beschreibung:
 * Copyright:     Copyright (c) 2003
 * Organisation:
 * @author
 * @version 1.0
 */

package de.marketsim.agent.trader;

public class OrderPerformedStatus
{
    private char mTradeResult = ' ';
    // O:  OK = order was proceeded. wish menge= eingekaufte or verkaufte Menge
    // G:  Geld = Buy order were not or part of the order proceeded.
    // B:  Brief = Sell order were not or part of the order proceeded.
    // N:  Wait
    private char       mPerformedWish = ' '; // B,S,N
    private double     mYesterdayPrice;
    private double     mTradeMenge;
    private double     mYesterdayWishLimit;

  public OrderPerformedStatus(char pTradeResult, double pYesterdayPrice, char pPerformedWish,  double pTradeMenge, double pYesterdayWishLimit )
  {
    this.mTradeResult          = pTradeResult;
    this.mYesterdayPrice       = pYesterdayPrice;
    this.mPerformedWish        = pPerformedWish;
    this.mTradeMenge           = pTradeMenge;
    this.mYesterdayWishLimit   = pYesterdayWishLimit;
  }

  public char getYesterdayTradeResult()
  {
      return this.mTradeResult;
  }

  public double getYesterdayPrice()
  {
      return this.mYesterdayPrice;
  }

  public double getTradeMenge()
  {
      return this.mTradeMenge;
  }

  public double getYesterdayWishLimit()
  {
      return this.mYesterdayWishLimit;
  }

}
