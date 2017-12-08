package de.marketsim.util;

/**
 * <p>Überschrift: Market Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import java.util.Vector;
import java.util.Properties;

public class AgentPartnerInfoKeeper
{

  static Properties  RecieverPartnerList = new Properties();
  static Properties  SenderPartnerList   = new Properties();

  public static void saveOnePartnerInfo(String pAgentName, String pSenderPartner, String pRecieverPartners)
  {
      RecieverPartnerList.setProperty( pAgentName,  pRecieverPartners );
      SenderPartnerList.setProperty(pAgentName, pSenderPartner );
  }

  public static int getSenderPartnerNumber(String pAgentName)
  {
     return Integer.parseInt( SenderPartnerList.getProperty(pAgentName) );
  }

  public static String getReciverPartners(String pAgentName)
  {
     return RecieverPartnerList.getProperty( pAgentName);
  }

}