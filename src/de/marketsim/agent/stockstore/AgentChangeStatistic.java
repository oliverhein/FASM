package de.marketsim.agent.stockstore;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2006
 * Company:
 * @author
 * @version 1.0
 */

public class AgentChangeStatistic
{

  private int mTotalChanges = 0;
  private int mInvestor2NoiseTrader = 0;
  private int mNoiseTrader2Investor = 0;

  public AgentChangeStatistic()
  {

  }

  public void setChanges(int pInvestor2NoiseTrader, int pNoiseTrader2Investor )
  {
     this.mInvestor2NoiseTrader =pInvestor2NoiseTrader;
     this.mNoiseTrader2Investor =pNoiseTrader2Investor;
     this.mTotalChanges = this.mInvestor2NoiseTrader + this.mNoiseTrader2Investor;
  }

  public int getInvestor2NoiseTrader()
  {
     return this.mInvestor2NoiseTrader;
  }

  public int getNoiseTrader2Investor()
  {
     return this.mNoiseTrader2Investor;
  }

  public int getTotalChange()
  {
     return this.mTotalChanges;
  }

  public String getDesc()
  {
     return "Investor2NoiseTrader=" +  this.mInvestor2NoiseTrader +
            " NoiseTrader2Investor=" +  this.mNoiseTrader2Investor +
            " Total changes=" + this.mTotalChanges;
  }

  public String getInfo()
  {
    return  this.mInvestor2NoiseTrader +";" +
            this.mNoiseTrader2Investor + ";" +
            this.mTotalChanges +";";
  }

}