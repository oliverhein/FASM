package de.marketsim.config;

/**
 * <p>Überschrift: Market Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

public class ParameterTransferHolder implements java.io.Serializable
{

  String mParameterList[] = new String[0];

  public ParameterTransferHolder()
  {
  }

  public void setParameterList(String pParamList[])
  {
      mParameterList = pParamList;
  }
  public String[] getParameterList()
  {
      return mParameterList;
  }

}