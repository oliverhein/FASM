package de.marketsim.config;

/**
 * <p>Überschrift: Market Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import java.util.*;

public class SimpleOne2ManyCommunication
{

  public static String INVESTOR    = "INVESTOR";
  public static String NOISETRADER = "NOISETRADER";
  String mMasterName     = null;
  String mMyPartnerList  = "";
  public String mType    = "";

  /*
   mMySenderPartner stores the number of sender partner.
   It means that there are n Partner which will send Status message to this agent
   This Parameter is very important for communication.
   This Agent will have to wait so many Status Messages then continue his decision.
  */
  int    mMySenderPartner= 0;

  public SimpleOne2ManyCommunication(String pMasterName)
  {
    mMasterName = pMasterName;
  }

  public void setAgentType(String pAgentType)
  {
    this.mType = pAgentType; // INVSTOR or NOISETRADER
  }

  public void addOnePartner(String pPartnerName)
  {
    mMyPartnerList = mMyPartnerList + pPartnerName + ";";
  }

  public String getmyPartner()
  {
     return mMyPartnerList;
  }

  public String getMasterName()
  {
     return this.mMasterName;
  }

  /*
   set the number of sender partner.
   n means that there are n Partner which will send Status message to this agent
  */
  public void setMySenderPartner(int n )
  {
    this.mMySenderPartner = n;
  }

  /*
   get the number of sender partner.
  */
  public int getMySenderPartner( )
  {
    return this.mMySenderPartner;
  }


}