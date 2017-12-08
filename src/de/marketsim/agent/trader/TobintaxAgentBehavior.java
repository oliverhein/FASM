package de.marketsim.agent.trader;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */
import jade.core.behaviours.*;
import jade.lang.acl.*;
import jade.core.*;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.DFService;
import jade.domain.FIPAException;

import java.util.*;
import java.io.*;
import java.lang.*;

import org.apache.log4j.*;

import de.marketsim.SystemConstant;
import de.marketsim.config.*;

import de.marketsim.message.*;

import de.marketsim.util.HelpTool;

import de.marketsim.util.OperationSynchroner;
import de.marketsim.util.DepotRecord;
import de.marketsim.util.PriceContainer;
import de.marketsim.util.MsgLogger;
import de.marketsim.util.DataFormatter;


public class TobintaxAgentBehavior extends CyclicBehaviour
{

  Logger  mLoger;
  int mOperatorType  =  SystemConstant.AgentType_TobinTax;
  String  mRuleName    = "Tobintax";
  boolean firstRequest = true;
  int    mDataCC       = 0;
  String mHostname     = null;

  DataFormatter nff = new DataFormatter ( Configurator.mConfData.mDataFormatLanguage );
  //java.text.NumberFormat englishnff = HelpTool.getNumberFormat("English");
  //java.text.NumberFormat germannff = HelpTool.getNumberFormat("Germany");

  char  Reaction = ' ';

  String    mLocalAID = null;

  DepotRecord  mDepotRecord = null;

  int     mDayIndexLength = 4; // Default 4
  double  Limit = 0;  // Wished Kurs
  double  mYesterdayKurs = 0;
  DailyTobintax mDailyTobintax = new DailyTobintax(0,0,0,0,0);
  java.util.Random randgen = null;

  public TobintaxAgentBehavior( Agent pAgent )
  {
     super( pAgent );
     this.mLocalAID = this.myAgent.getHap();
     String agentname = this.myAgent.getLocalName().toUpperCase();
     randgen = new Random();
     this.mLoger = MsgLogger.getMsgLogger("TOBINTAX");
     System.out.println("TOBINTAX Loger: isDebugEnabled=" + this.mLoger.isDebugEnabled() );
  }

  public void setDepotRecord(DepotRecord pDepotRecord)
  {
     this.mDepotRecord = pDepotRecord;
  }

  public void action()
  {
           ACLMessage Response = null;
           if ( firstRequest )
           {
                Registerme();
                firstRequest=false;
                return;
           }

           // Wait for Response from StockStore
           boolean AllcommandReceived = false;

           Response  =  this.myAgent.blockingReceive(20000);
           if ( Response != null)
           {
              ///  check the message type
             MessageWrapper msgwrp = null;
             try
             {
                msgwrp = (MessageWrapper) Response.getContentObject();
             }
             catch ( Exception ex )
             {
               ex.printStackTrace();
               System.out.println("Tobintax: ErrorMessage from" + Response.getSender().getLocalName() );
               return;
             }

             if (  msgwrp.mMessageType == SystemConstant.MessageType_InterruptCommand )
             {
               UnRegisterMe();
               StopAgent();
               return;
             }
             else
             if ( msgwrp.mMessageType == SystemConstant.MessageType_DailyTobintax )
             {
               // !!!!!!!!!!!!!!!!!  Important !!!!!!!!!!!!!!!!!
               // StockStore can not change this sending sequence
               // Every day: StockStore sends at first DailyTobin, then CashOrder Results

                // add Tax to its Depot
                DailyTobintax dailytobintax = (DailyTobintax) msgwrp.mMessageContent;

                // save it temporary because sendDepot() needs it late.
                this.mDailyTobintax = dailytobintax;

                this.processDailyTobintax(dailytobintax);

                CashTrade_Order order = new CashTrade_Order(0,'N',0,0,0, "");
                // send Depot to DataLogger
                this.sendDepotStatus( order, this.mDailyTobintax );
                this.mDataCC++;
             }
             /*
             else
             // Cash Order Execution Result
             if ( msgwrp.mMessageType == SystemConstant.MessageType_CashTrade_Order )
             {
               de.marketsim.message.CashTrade_Order order = (de.marketsim.message.CashTrade_Order) msgwrp.mMessageContent;
                // update its Depot
               this.mLoger.debug("Processing CashOrder");
               this.processOrderResult( order );

               // send Depot to DataLogger
               this.sendDepotStatus( order, this.mDailyTobintax );

               this.mDataCC++;
               this.create_and_sendOrder();
             }
             */
             else
             // die ersten 300  Kurs Information
             if ( msgwrp.mMessageType == SystemConstant.MessageType_300InitData )
             {
                // Die ersten 300 Kurs in Kurs-History speichern.
                // und nimmt die 300te Kurs als InitKurs für seine InitDepot.
                processFirst300Kurs( msgwrp );

                // Später wird aktivieren !!!!!!!!!!!!
                //this.create_and_sendOrder();
             }
         }
         //The expected Handelday has been archieved.
         //Stop the Agent
         if ( this.mDataCC == Configurator.mConfData.mHandelsday )
         {
            UnRegisterMe();
            StopAgent();
         }
 }

private void StopAgent()
{
  DFAgentDescription dfd = new DFAgentDescription();
  dfd.setName( myAgent.getAID() );
  try
  {
     DFService.deregister( myAgent, dfd );
  }
  catch (Exception ex)
  {
     ex.printStackTrace();
  }
  this.myAgent.doDelete();
}

private void UnRegisterMe()
{
      ACLMessage msg= new ACLMessage( ACLMessage.INFORM );
      MessageWrapper msgwrp = MessageFactory.createUnRegisterMessage( myAgent.getLocalName(), SystemConstant.AgentType_TobinTax );
      try
      {
          msg.setContentObject( msgwrp );
      }
      catch ( IOException ex )
      {
          ex.printStackTrace();
      }

      msg.addReceiver( new AID( "DAX", false) );
      // Send a request to get PriceHistory
      this.myAgent.send(msg);
      System.out.println("Tobintax: UNREGISTERME has been sent.");
}

private void processOrderResult( CashTrade_Order pCashOrder )
{
      // kurs is saved, it will be used for new order
      this.mYesterdayKurs = pCashOrder.mFinalKurs;
      // save yesterday price into PriceContainer ( HistoryPrice)
      PriceContainer.saveTraderPrice( this.mDataCC,  this.mYesterdayKurs  );

      String orderprocessedmsg = "";

      // Trade beteiligt
      if ( pCashOrder.mTradeCash2 != 0.0 )
      {
             if ( pCashOrder.mOrderWish == SystemConstant.WishType_Buy )
             {
                 //buy
                 this.mLoger.debug( this.mDataCC + ".Day  Wish=Buy  Ausgeben Cash1= " + pCashOrder.mInvolvedCash1 + " bekommt CASH2=" + pCashOrder.mTradeCash2 + " Kurs=" +pCashOrder.mFinalKurs );
                 this.mLoger.debug( this.mDataCC + ".Day  Depotsum=" + this.mDepotRecord.mMoneyMarket_CurrentCashDepot );
                 this.mDepotRecord.updateRobintaxAgentDepot(pCashOrder.mOrderWish,  pCashOrder.mInvolvedCash1, pCashOrder.mTradeCash2, pCashOrder.mFinalKurs);
                 this.mLoger.debug( this.mDataCC + ".Day After updating: Depotsum=" + this.mDepotRecord.mMoneyMarket_CurrentCashDepot );
                 orderprocessedmsg = "Buy Confirmed: Cash1=" + pCashOrder.mInvolvedCash1 + " Cash2 =" + pCashOrder.mTradeCash2 + " Kurs=" + pCashOrder.mFinalKurs;
             }
             else
             {
                 // Sell
                 this.mLoger.debug( this.mDataCC + ".Day  Wish=Sell  bekommt Cash1= " + pCashOrder.mInvolvedCash1 + " sold CASH2=" + pCashOrder.mTradeCash2 + " Kurs=" +pCashOrder.mFinalKurs );
                 this.mLoger.debug( this.mDataCC + ".Day  Depotsum=" + this.mDepotRecord.mMoneyMarket_CurrentCashDepot );
                 this.mDepotRecord.updateRobintaxAgentDepot(pCashOrder.mOrderWish,  pCashOrder.mInvolvedCash1,  pCashOrder.mTradeCash2, pCashOrder.mFinalKurs);
                 this.mLoger.debug( this.mDataCC + ".Day After updating: Depotsum=" + this.mDepotRecord.mMoneyMarket_CurrentCashDepot );
                 orderprocessedmsg =  "Sell Confirmed: Cash1=" + pCashOrder.mInvolvedCash1 + " Cash2 =" + pCashOrder.mTradeCash2 + " Kurs=" + pCashOrder.mFinalKurs;
             }
             System.out.println( "---"+ this.mDataCC+"." + this.myAgent.getLocalName() + " " + orderprocessedmsg );
     }
     else
     {
                // This order is not involved into the Trade today
                // only update the price
                this.mLoger.debug( this.mDataCC + ".Day Wish not Performed/Wait, Original-Wish=" +  pCashOrder.mOrderWish+ "  Neu Kurs="+pCashOrder.mFinalKurs + " Involved CASH1=" + pCashOrder.mInvolvedCash1 + " CASH2=" + pCashOrder.mTradeCash2 );
                this.mDepotRecord.updateRobintaxAgentDepot(pCashOrder.mOrderWish,  pCashOrder.mInvolvedCash1, pCashOrder.mTradeCash2, pCashOrder.mFinalKurs);
                orderprocessedmsg = "No Buy/Sell. new Kurs=" + pCashOrder.mFinalKurs;
                System.out.println( "---"+ this.mDataCC+"." + this.myAgent.getLocalName() + " "+ orderprocessedmsg );
     }

}

public void sendDepotStatus( CashTrade_Order pCashOrder, DailyTobintax pDailyTobintax )
{
        // send this depot to DataLogger
       ACLMessage aclmsg = new ACLMessage( ACLMessage.INFORM );
       aclmsg.addReceiver( new AID ( "DataLogger", false) );

       MessageWrapper msgwrp = new MessageWrapper();
       msgwrp.mMessageType = SystemConstant.MessageType_Depotstate;
       // get the current depot and insert into Message
       // Format:
       // DEPOT,Handelstag;AgentType;RegelName;Depot;Price;Aktienanzhal;Cash;
           String tt = "000000" + this.mDataCC;
           String depotinfo =
           "DEPOT," +
           tt.substring( tt.length() - this.mDayIndexLength ) + ";" +
           SystemConstant.getOperatorTypeName( this.mOperatorType) +";" +
           nff.format2str( this.mDepotRecord.mMoneyMarket_CurrentCash1 )+";"+
           nff.format2str( this.mDepotRecord.mMoneyMarket_CurrentCash2 )+";"+
           nff.format2str( this.mDepotRecord.mMoneyMarket_CurrentCashDepot )+";"+

           nff.format2str( pDailyTobintax.mBasetax_Cash1 + pDailyTobintax.mExtratax_Cash1 )+";"+
           nff.format2str( pDailyTobintax.mBasetax_Cash1 )+";"+
           nff.format2str( pDailyTobintax.mExtratax_Cash1 )+";"+

           nff.format2str( pDailyTobintax.mBasetax_Cash2 + pDailyTobintax.mExtratax_Cash2 )+ ";"+
           nff.format2str( pDailyTobintax.mBasetax_Cash2 )+";"+
           nff.format2str( pDailyTobintax.mExtratax_Cash2 )+";"+

           pCashOrder.mOrderWish+";"+
           pCashOrder.mCash2+";";

       String ss ="";
        if ( pCashOrder.mOrderWish == SystemConstant.WishType_Buy )
        {
          ss = nff.format2str(pCashOrder.mBuyLimit);
        }
        else
        if ( pCashOrder.mOrderWish == SystemConstant.WishType_Sell )
        {
          ss = nff.format2str(pCashOrder.mSellLimit);
        }
        else
        {
          ss = "0";
        }

       depotinfo = depotinfo + ss + ";" +

           nff.format2str( pCashOrder.mFinalKurs ) + ";" +
           pCashOrder.mTradeResult + ";" +
           nff.format2str( pCashOrder.mInvolvedCash1 )+";" +
           nff.format2str( pCashOrder.mTradeCash2 )+";" ;

         msgwrp.mMessageContent =depotinfo;
         try
         {
            aclmsg.setContentObject( msgwrp );
         }
         catch (Exception ex)
         {
           ex.printStackTrace();
         }
         myAgent.send( aclmsg );
}

private void processFirst300Kurs( MessageWrapper pMsgWrp )
{

     First300InitData  initdata = (First300InitData) pMsgWrp.mMessageContent;

     // save to PriceContainer
     PriceContainer.saveFirst300Kurs( initdata.mDoubleData );

     // get the Kurs of last day:
     this.mYesterdayKurs = initdata.mDoubleData[ initdata.mDoubleData.length -1 ] ;

     this.mDepotRecord.mMoneyMarket_InitCashDepot =this.mDepotRecord.mMoneyMarket_InitCash1 +
         this.mDepotRecord.mMoneyMarket_InitCash2 / this.mYesterdayKurs;

     this.mDepotRecord.mMoneyMarket_CurrentCashDepot = this.mDepotRecord.mMoneyMarket_InitCashDepot;

     CashTrade_Order  cashorder = new CashTrade_Order( SystemConstant.AgentType_TobinTax,'N',0,0, "");
     cashorder.mFinalKurs = this.mYesterdayKurs;
     cashorder.mTradeResult = 'T';

     //this.sendDepotStatus(cashorder);
}

private char makeDecision()
{

  double movingaverage = PriceContainer.getMovingAveragePrice( Configurator.mConfData.mTobintax_Days4AverageKurs );
  double Interventionspunkt_obergrenz  = movingaverage * ( 1 + Configurator.mConfData.mTobintax_Interventionsband /100.0 );
  double Interventionspunkt_untergrenz = movingaverage * ( 1 - Configurator.mConfData.mTobintax_Interventionsband /100.0 );

  if ( this.mDepotRecord.mMoneyMarket_CurrentKurs > Interventionspunkt_obergrenz )
  {
    // Buy Cash2
    return 'B';
  }
  else
  if ( this.mDepotRecord.mMoneyMarket_CurrentKurs < Interventionspunkt_untergrenz )
  {
    // Sell Cash2
    return 'S';
  }
  else
  {
    // Wait
    return 'N';
  }
}

private int createWishMenge(char pAction)
{
  if ( pAction == 'B' )
  {
    // Buy Cash2 using  %K from Cash1
    double buycapability = this.mDepotRecord.mMoneyMarket_CurrentCash1 * this.mDepotRecord.mMoneyMarket_CurrentKurs;
    return (int) ( Configurator.mConfData.mTobintax_TradeProzent / 100.0 * buycapability );
  }
  else
  if ( pAction == 'S' )
  {
     // Sell Cash2
     // sell %K of Cash 2
     return (int) ( Configurator.mConfData.mTobintax_TradeProzent / 100.0 * this.mDepotRecord.mMoneyMarket_CurrentCash2 );
  }
  else
  {
     return 0;
  }
}

private void create_and_sendOrder()
{
      char   decision = this.makeDecision();
      // generate a decison according to the procent of buy,sell and wait
      int  WishMenge = this.createWishMenge(decision);

            // There are 3 decisions:
            // 1.  'B' buy a stock
            // 2.  'S' sell a stock
            // 3.  'N' Nothing, only OK confirmation
            ACLMessage aclmsg= new ACLMessage( ACLMessage.INFORM );
            MessageWrapper msgwrp = null;

            // Decision ist gezwungen zu WAIT zu ändern.
            if ( WishMenge == 0 )
            {
               msgwrp = MessageFactory.createCashOrder
                        (
                          SystemConstant.AgentType_TobinTax,
                          SystemConstant.WishType_Wait,
                          0,
                          0.0,
                          this.mDepotRecord.mMoneyMarket_CurrentCash1,
                          this.mDepotRecord.mMoneyMarket_CurrentCash2,
                          ""
                         );
            }
            else
            if ( decision == 'B' )
            {
              // cheapest buy
              msgwrp = MessageFactory
                     .createCashOrder(SystemConstant.AgentType_TobinTax,
                                      SystemConstant.WishType_Buy,
                                      WishMenge,
                                      SystemConstant.Limit_CheapestBuy,
                                      this.mDepotRecord.mMoneyMarket_CurrentCash1,
                                      this.mDepotRecord.mMoneyMarket_CurrentCash2,
                                      ""
                                      );
            }
            else
            if ( decision == 'S' )
            {
              // best sell
              msgwrp = MessageFactory
                     .createCashOrder(SystemConstant.AgentType_TobinTax,
                                      SystemConstant.WishType_Sell,
                                      WishMenge,
                                      SystemConstant.Limit_BestenSell,
                                      this.mDepotRecord.mMoneyMarket_CurrentCash1,
                                      this.mDepotRecord.mMoneyMarket_CurrentCash2,
                                      ""
                                      );
            }

            try
            {
                aclmsg.setContentObject( msgwrp );
            }
            catch ( IOException ex )
            {
                 ex.printStackTrace();
            }

            aclmsg.addReceiver( new AID( "DAX", false ) );
            // Send the new order to StockStore
            this.myAgent.send(aclmsg);
            System.out.println("Tobintax sends a order.");
}

private void Registerme()
{
  ACLMessage msg= new ACLMessage( ACLMessage.INFORM );
  MessageWrapper msgwrapper = MessageFactory.
                              createRegisterMessage(
                              myAgent.getLocalName(),
                              this.mOperatorType );
  try
  {
     msg.setContentObject( msgwrapper );
  }
  catch ( IOException ex )
  {
     ex.printStackTrace();
  }

  AID towhom = new AID( "DAX", false );
  /// !!!!!!!!!!!!!!!!!!!!!!!!!! ?????????
  msg.addReceiver( towhom  );
  // Send a register message to Stockstore then wait for the first 300 Initial Data (PriceHistory, Porfit and InterestRate)
  this.myAgent.send(msg);
  System.out.println("Agent has registered on StockStore.");

}

private void processDailyTobintax( DailyTobintax pDailiyTobintax )
{
  this.mDepotRecord.addDailiyTobintax( pDailiyTobintax.mBasetax_Cash1 + pDailiyTobintax.mExtratax_Cash1,
                                       pDailiyTobintax.mBasetax_Cash2 + pDailiyTobintax.mExtratax_Cash2 );

}

}
