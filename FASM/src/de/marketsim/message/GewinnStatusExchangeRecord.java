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

public class GewinnStatusExchangeRecord implements Serializable
{

  public String mInfoList[];

  public GewinnStatusExchangeRecord(String pInfoList[])
  {
       mInfoList = pInfoList;
  }
}