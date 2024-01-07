package de.marketsim.util;

/**
 * <p>Überschrift: Market Simulator</p>
 * <p>Ein Datenstructur </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

public class SingleBarData
{
  public double mRelativeGewinnProzent = 0.0;
  public String mData1Description  = "";  // Agentname

  public SingleBarData( double pRelativeGewinnProzent, String DS1)
  {
      this.mRelativeGewinnProzent = pRelativeGewinnProzent;
      this.mData1Description = DS1;
  }
}

