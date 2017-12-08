package de.marketsim.config;

/**
 * <p>Überschrift: Market Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

public class AgentStartParameter
{

  String  mReceiverPartnerList = "";
  int     mSenderPartnerNumber = 0;
  String  mRegel = "";
  String  mAgentName = "";

  public AgentStartParameter(String pAgentName, String pRegel, String pReceiverPartnerList, int pSenderPartnerNumber)
  {
    this.mAgentName = pAgentName;
    this.mRegel     = pRegel;
    this.mReceiverPartnerList = pReceiverPartnerList;
    this.mSenderPartnerNumber = pSenderPartnerNumber;
  }

  public String getRegel()
  {
     return this.mRegel;
  }

  public String getReceiverPartnerList()
  {
     return this.mReceiverPartnerList;
  }

  public int getSenderPartnerNumber()
  {
     return this.mSenderPartnerNumber;
  }

}