package de.marketsim.message;

/**
 * <p>Überschrift: FASM Frankfurt Articial Simulation Market</p>
 * <p>Beschreibung: Mircomarket Simulator </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import java.io.Serializable;

public class DepotInfoContainer implements Serializable
{
  public String[] myDepotInfo;

  public DepotInfoContainer( String pDepotInfo )
  {
    myDepotInfo = new String[1];
    myDepotInfo[0] = pDepotInfo;
  }

  public DepotInfoContainer( String pDepotInfo[] )
  {
    myDepotInfo = pDepotInfo;
  }


}