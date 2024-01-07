  package de.marketsim.util;

  /**
   * <p>Überschrift: Market Simulator</p>
   * <p>Ein Datenstructur </p>
   * <p>Copyright: Copyright (c) 2005</p>
   * <p>Organisation: </p>
   * @author Xining Wang
   * @version 1.0
   */

  public class TwoBarBaseData
  {

    public double mAbsoluteGewinnProzent = 0.0;
    public double mRelativeGewinnProzent = 0.0;
    public String mData1Description  = "";  // Agentname
    public String mData2Description  = "";  // AgentType

    public TwoBarBaseData(double pAbsoluteGewinnProzent, double pRelativeGewinnProzent, String DS1, String DS2)
    {
        this.mAbsoluteGewinnProzent = pAbsoluteGewinnProzent;
        this.mRelativeGewinnProzent = pRelativeGewinnProzent;
        this.mData1Description = DS1;
        this.mData2Description = DS2;
    }
 }
