package de.marketsim.message;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import jade.lang.acl.*;
import jade.core.*;
import de.marketsim.SystemConstant;
import de.marketsim.util.DailyOrderStatistic;

public class MessageFactory
{

  public static MessageWrapper createUnRegisterMessage(String pAgentName, int pAgentType)
  {
     MessageWrapper tempmsg  = new MessageWrapper();
     tempmsg.mMessageType    = SystemConstant.MessageType_UnRegister;
     UnRegisterMessage tt    = new UnRegisterMessage();
     tt.mAgentName           = pAgentName;
     tt.mAgentType           = pAgentType;
     tempmsg.mMessageContent = tt;
     return tempmsg;
  }

  public static MessageWrapper createRegisterMessage(String pAgentName, int pAgentType)
  {

     MessageWrapper tempmsg  = new MessageWrapper();
     tempmsg.mMessageType    = SystemConstant.MessageType_Register;
     RegisterMessage tt      = new RegisterMessage();
     tt.mAgentName           = pAgentName;
     tt.mAgentType           = pAgentType;
     tempmsg.mMessageContent = tt;
     return tempmsg;

  }

  public static MessageWrapper createFirst300IntData(int pKurs[])
  {

    MessageWrapper tempmsg  = new MessageWrapper();
    tempmsg.mMessageType    = SystemConstant.MessageType_300InitData;
    First300InitData    tt  = new First300InitData();
    tt.mIntData             = pKurs;
    tempmsg.mMessageContent = tt;
    return tempmsg;

  }

  public static MessageWrapper createFirst300DoubleData(double pKurs[])
  {

    MessageWrapper tempmsg  = new MessageWrapper();
    tempmsg.mMessageType    = SystemConstant.MessageType_300InitData;
    First300InitData    tt  = new First300InitData();
    tt.mDoubleData          = pKurs;
    tempmsg.mMessageContent = tt;
    return tempmsg;

  }

  public static MessageWrapper createCashOrder
  (
     int    pAgentType,
     char   pWish,
     int    pMenge,
     double pLimit,
     double pAbsoluteGewinnProzent,
     double pRelativeGewinnProzent,
     String pLimitCalcBase )
  {

     MessageWrapper tempmsg  = new MessageWrapper();
     tempmsg.mMessageType    = SystemConstant.MessageType_CashTrade_Order;
     CashTrade_Order tt      = new CashTrade_Order(pAgentType,
                                                   pWish,
                                                   pMenge,
                                                   pLimit,
                                                   pLimitCalcBase);
     tt.mAbsoluteGewinnProzent = pAbsoluteGewinnProzent;
     tt.mRelativeGewinnProzent = pRelativeGewinnProzent;

     tempmsg.mMessageContent = tt;
     return tempmsg;
  }

  public static MessageWrapper createCashOrder
  (
     int    pAgentType,
     char   pWish,
     int    pMenge,
     double pBuyLimit,
     double pSellLimit,
     double pAbsoluteGewinnProzent,
     double pRelativeGewinnProzent,
     String pLimitCalcBase )
  {

     MessageWrapper tempmsg  = new MessageWrapper();
     tempmsg.mMessageType    = SystemConstant.MessageType_CashTrade_Order;
     CashTrade_Order tt      = new CashTrade_Order(pAgentType,
                                                   pWish,
                                                   pMenge,
                                                   pBuyLimit,
                                                   pSellLimit,
                                                   pLimitCalcBase
                                                   );
     tt.mAbsoluteGewinnProzent = pAbsoluteGewinnProzent;
     tt.mRelativeGewinnProzent = pRelativeGewinnProzent;
     tempmsg.mMessageContent = tt;
     return tempmsg;
  }

  public static MessageWrapper createAktienOrder
  (
     int    pAgentType,
     double pAbsoluteGewinnProzent,
     double pRelativeGewinnProzent,
     String pLimitCalcBase,
     boolean pTypeChanged,
     int    pOriginalAgentType,
     SimpleSingleOrder[]  pBuyOrders,
     SimpleSingleOrder[]  pSellOrders
    )

  {
     MessageWrapper tempmsg  = new MessageWrapper();
     tempmsg.mMessageType    = SystemConstant.MessageType_AktienTrade_Order;
     AktienTrade_Order tt      =
     new AktienTrade_Order( pAgentType, pBuyOrders, pSellOrders, pLimitCalcBase, pOriginalAgentType );

     tt.mAbsoluteGewinnProzent = pAbsoluteGewinnProzent;
     tt.mRelativeGewinnProzent = pRelativeGewinnProzent;
     tt.mTypeChanged           = pTypeChanged;
     tempmsg.mMessageContent   =  tt;
     return tempmsg;
  }


  public static MessageWrapper createAktienOrder
  (
     int pAgentType,
     char   pWish,
     int    pMenge,
     int    pLimit,
     double pAbsoluteGewinnProzent,
     double pRelativeGewinnProzent,
     String pLimitCalcBase,
     boolean pTypeChanged,
     int    pOriginalAgentType )
  {
     MessageWrapper tempmsg  = new MessageWrapper();
     tempmsg.mMessageType    = SystemConstant.MessageType_AktienTrade_Order;
     AktienTrade_Order tt      =
         new AktienTrade_Order( pAgentType,
                                pWish,
                                pMenge,
                                pLimit,
                                pLimitCalcBase,
                                pOriginalAgentType
                                );
     tt.mAbsoluteGewinnProzent = pAbsoluteGewinnProzent;
     tt.mRelativeGewinnProzent = pRelativeGewinnProzent;
     tt.mTypeChanged           = pTypeChanged;
     tempmsg.mMessageContent   =  tt;
     return tempmsg;
  }

  public static MessageWrapper createAktienOrder
  (
     int    pAgentType,
     char   pWish,
     int    pBuyMenge,
     int    pBuyLimit,
     int    pSellMenge,
     int    pSellLimit,
     double pAbsoluteGewinnProzent,
     double pRelativeGewinnProzent,
     String pLimitCalcBase,
     boolean pTypeChanged,
     int    pOriginalAgentType )
  {
     MessageWrapper tempmsg  = new MessageWrapper();
     tempmsg.mMessageType    = SystemConstant.MessageType_AktienTrade_Order;
     AktienTrade_Order tt      =
         new AktienTrade_Order( pAgentType,
                                pWish,
                                pBuyMenge,
                                pBuyLimit,
                                pSellMenge,
                                pSellLimit,
                                pLimitCalcBase,
                                pOriginalAgentType
                                );
     tt.mAbsoluteGewinnProzent = pAbsoluteGewinnProzent;
     tt.mRelativeGewinnProzent = pRelativeGewinnProzent;
     tt.mTypeChanged           = pTypeChanged;

     tempmsg.mMessageContent   =  tt;
     return tempmsg;
  }

  public static MessageWrapper createFriendStatus
          (String pAgentName,
           int pAgentType,
           double pAbschlagProzent,
           int    pDayOfAveragePrice,
           double pGewinn,
           double pAbsoluteGewinnProzent,
           int    pOriginalType
          )
  {
    MessageWrapper msgwrp = new MessageWrapper();
    msgwrp.mMessageType = SystemConstant.MessageType_FriendStatus;
    msgwrp.mMessageContent = new FriendStatus
                      (pAgentName,
                       pAgentType,
                       pAbschlagProzent,
                       pDayOfAveragePrice,
                       pGewinn,
                       pAbsoluteGewinnProzent,
                       pOriginalType);
    return msgwrp;
  }

  public static MessageWrapper createQuitCommand()
  {
      MessageWrapper msgwrp = new MessageWrapper();
      msgwrp.mMessageType = SystemConstant.MessageType_QUITCOMMAND;
      return msgwrp;
  }

  public static MessageWrapper createAgentList(String pInfoList[])
  {
      MessageWrapper msgwrp = new MessageWrapper();
      msgwrp.mMessageType = SystemConstant.MessageType_AgentList;
      msgwrp.mMessageContent = new AgentList(pInfoList);
      return msgwrp;
  }

  public static MessageWrapper createStoreDailyTradeSummary(double pKurs,
		  		                                            char pTradeStatus, 
		                                                    double pTradeMenge,
		  		                                            double pTradeVolume, 
		  		                                            double pInnererWert,
		  		                                            DailyOrderStatistic pDailyOrderStatistic
		  		                                            )
  {
	  
	  // contains the Daily Trade Summary and new Statistic values
      StoreDailyTradeSummary mm = new StoreDailyTradeSummary(pKurs,  pTradeStatus, pTradeMenge, pTradeVolume, pInnererWert);
      mm.setDailyOrderStatistic(pDailyOrderStatistic);
      
      MessageWrapper msgwrp  = new MessageWrapper();
      msgwrp.mMessageType    = SystemConstant.MessageType_StoreDailyTradeSummary;
      msgwrp.mMessageContent = mm;
      return  msgwrp;
      
  }

  public static MessageWrapper createDailyTobinTax(double pKurs, double pFestTobintax_In_Cash1, double pExtraTobintax_In_Cash1,  double pFestTobintax_In_Cash2, double pExtraTobintax_In_Cash2 )
   {
       DailyTobintax mm = new DailyTobintax(pKurs, pFestTobintax_In_Cash1, pExtraTobintax_In_Cash1, pFestTobintax_In_Cash2, pExtraTobintax_In_Cash2);
       MessageWrapper msgwrp = new MessageWrapper();
       msgwrp.mMessageType = SystemConstant.MessageType_DailyTobintax;
       msgwrp.mMessageContent = mm;
       return  msgwrp;
   }

   public static MessageWrapper createInterruptCommand(String pReason)
     {
         MessageWrapper msgwrp = new MessageWrapper();
         msgwrp.mMessageType = SystemConstant.MessageType_InterruptCommand;
         msgwrp.mMessageContent = new InterruptCommand(pReason);
         return  msgwrp;
     }

   public static MessageWrapper createDepotRecordMessage( Object pDepotRecord)
   {
           MessageWrapper msgwrp = new MessageWrapper();
           msgwrp.mMessageType = SystemConstant.MessageType_DepotRecord;
           msgwrp.mMessageContent = pDepotRecord;
           return  msgwrp;
  }

  public static MessageWrapper createGewinnStatusExchangeList( String  pInfoList[])
  {
          GewinnStatusExchangeRecord pRec = new GewinnStatusExchangeRecord(pInfoList);
          MessageWrapper msgwrp = new MessageWrapper();
          msgwrp.mMessageType = SystemConstant.MessageType_XState;
          msgwrp.mMessageContent = pRec;
          return  msgwrp;
 }

}