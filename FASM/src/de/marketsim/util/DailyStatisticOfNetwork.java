package de.marketsim.util;

/**
 * <p>Überschrift: FASM Frankfurt Articial Simulation Market</p>
 * <p>Beschreibung: Mircomarket Simulator </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

public class DailyStatisticOfNetwork
{

  public  int mInvestor                =0;
  // change against previous day
  // - : weniger geworden, einige Investor wechselt zu NoiseTrader oder zurück zum BlankoAgent
  // + : Einige NoiseTrader or BlankoAgent wechsel zu Investor
  public  int mInvestorChanged         =0;


  public  int mNoiseTrader             =0;
  public  int mNoiseTraderChanged      =0;
  public  int mBlankoAgent             =0;
  public  int mBlankoAgentActivated    =0;
  public  int mBlankoAgentDeactivated  =0;
  public  int mrandomTrader            =0;

  public DailyStatisticOfNetwork()
  {

  }

}



