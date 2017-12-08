package de.marketsim.config;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import java.util.*;


public class NetworkConfigManager
{

  private Vector  networklist       = new Vector();
  private int mMaxNetworkNodes      = 0;
  private int mMaxRandomTraderNodes = 0;


  public NetworkConfigManager()
  {
    // Nothing
  }


  public int getMaxRandomTraderNodes()
  {
      return this.mMaxRandomTraderNodes;
  }

  public int getMaxNetworkNodes()
  {
      return this.mMaxNetworkNodes;
  }

  public void addNewNetwork(NetworkConfig pNetworkConfig)
  {
      this.networklist.add( pNetworkConfig );
      if ( pNetworkConfig.mNodesInNetwork > this.mMaxNetworkNodes )
      {
        this.mMaxNetworkNodes = pNetworkConfig.mNodesInNetwork;
      }
      if ( pNetworkConfig.mRandomTraderNumber > this.mMaxRandomTraderNodes )
      {
        this.mMaxRandomTraderNodes = pNetworkConfig.mRandomTraderNumber;
      }
  }

  /**
   * clear all network definition
   * set two Max Variable auf 0.
   */
  public void reset()
  {
       // Vector clear
       this.networklist.clear();

       // Reset these two Max Counter
       this.mMaxNetworkNodes      = 0;

       this.mMaxRandomTraderNodes = 0;
  }

  public NetworkConfig getNetworkConfig(int pIndex)
  {
       return ( NetworkConfig ) this.networklist.elementAt(pIndex);
  }

  public int getSize()
  {
       return this.networklist.size();
  }

  /**
   * For self test in seperate environment.
   * @param args
   */
  public static void main(String[] args)
  {
       NetworkConfigManager networkConfigList1 = new NetworkConfigManager();
  }
}