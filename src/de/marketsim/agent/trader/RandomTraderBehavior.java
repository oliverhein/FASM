/**
 * Überschrift:
 * Beschreibung:
 * Copyright:     Copyright (c) 2003
 * Organisation:
 * @author  Xining Wang
 * @version 1.0
 */

package de.marketsim.agent.trader;

import jade.core.behaviours.*;
import jade.lang.acl.*;
import jade.core.*;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.DFService;
import jade.domain.FIPAException;

import org.apache.log4j.Logger;

import java.util.*;
import java.io.*;
import java.lang.*;

import de.marketsim.util.HelpTool;
import de.marketsim.util.MsgLogger;
import de.marketsim.util.OperationSynchroner;
import de.marketsim.util.DepotRecord;
import de.marketsim.util.PriceContainer;
import de.marketsim.util.DataFormatter;

import de.marketsim.message.*;
import de.marketsim.SystemConstant;
import de.marketsim.config.*;

public class RandomTraderBehavior extends CyclicBehaviour
{
  public Logger         mLogger;

  int     mOperatorType          = SystemConstant.AgentType_RANDOMTRADER;
  String  mRuleName           = "RANDOM";
  boolean firstRequest        = true;

  boolean mTraceOrder;
  int     mDayIndexLength     = 4;
  int     mDataCC             = 0;
  String mHostname            = null;
  ACLMessage msg              = new ACLMessage( ACLMessage.INFORM );

  //java.text.NumberFormat nff = java.text.NumberFormat.getNumberInstance( java.util.Locale.GERMANY );

  //java.text.NumberFormat englishnff = HelpTool.getNumberFormat("English");

  //public java.text.NumberFormat germannff  = HelpTool.getNumberFormat("Germany");
  DataFormatter nff = new DataFormatter ( Configurator.mConfData.mDataFormatLanguage );

  char  Reaction = ' ';

  String    mLocalAID = null;

  OrderPerformedStatus      mPerformedStatus = null;

  DepotRecord  mDepotRecord = null;

  //int  Limit = 0;  // PriceWish
  //int     mYesterdayprice_AktienMarket = 0;
  double  mYesterdayprice  = 0.0;

  java.util.Random randomformenge = null;
  java.util.Random randomforsleep = null;
  java.util.Random randomfororder = null;

  public RandomTraderBehavior( Agent pAgent )
  {
     super( pAgent );
     this.mLocalAID = this.myAgent.getHap();
     String agentname = this.myAgent.getLocalName().toUpperCase();

     char cc = agentname.charAt( agentname.length() -1 );

     int randomtradernumber = Integer.parseInt( cc + "");

     randomformenge = new Random( Configurator.mConfData.mRandomTraderSleepSeed + 1000 * randomtradernumber );
     randomforsleep = new Random( Configurator.mConfData.mRandomTraderSleepSeed + 1000 * randomtradernumber);
     randomfororder = new Random( Configurator.mConfData.mRandomTraderRandomSeed4Decision + 1000 * randomtradernumber);

     this.mLogger = MsgLogger.getMsgLogger( agentname.toUpperCase() );

     //System.out.println("Prepare RandomTrader:" + agentname.toUpperCase() );

     this.mLogger.debug("Logger is created");

     String ss = System.getProperty("TRACEORDERENABLED","false");
     if (  ss.equalsIgnoreCase( "true")  )
     {
         mTraceOrder = true;
     }


  }

  public void setDepotRecord(DepotRecord pDepotRecord)
  {
     this.mDepotRecord = pDepotRecord;
     this.mLogger.debug("InitialInfo; InitAktienMenge=" + this.mDepotRecord.AktienMarket_getInitAktienMenge() +
                        ";InitCash=" + this.mDepotRecord.AktienMarket_getInitCash() +";" +
                        ";InitDepot=" + this.mDepotRecord.AktienMarket_getInitDepot() );
  }

  // main process flow
  // This action Method will be automatical called from jader.Agent.setup().

  public void action()
  {
           if ( firstRequest )
           {
             Registerme();
             firstRequest = false;
           }

           ACLMessage aclmsg =  this.myAgent.blockingReceive();
           if ( aclmsg != null)
           {
             MessageWrapper msgwrp  = null;
             try
             {
                msgwrp = ( MessageWrapper ) aclmsg.getContentObject();
             }
             catch (Exception ex)
             {
               ex.printStackTrace();
             }

             if (  msgwrp.mMessageType == SystemConstant.MessageType_InterruptCommand )
             {
               UnRegisterMe();
               StopAgent();
               return;
             }
             else
              if (  msgwrp.mMessageType == SystemConstant.MessageType_300InitData )
              {
                First300InitData first300data = (First300InitData ) msgwrp.mMessageContent;
                this.processFirst300InitData(first300data);
                // Send 1. Order
                sendOrder2StockStore();
                return;
              }
              else
              if (  msgwrp.mMessageType == SystemConstant.MessageType_AktienTrade_Order )
              {
                // dodo
                // AktienMarket
                if ( ! Configurator.istAktienMarket())
                {
                  // Grosser Fehler
                  System.out.println(" Error! Get a Aktien_order Result but System Conf is MoneyMarket   ");
                  return;
                }
                processAktienTrade_Order((AktienTrade_Order)msgwrp.mMessageContent);
                // send Next Order
                sendOrder2StockStore();
              }
              else
              if (  msgwrp.mMessageType == SystemConstant.MessageType_CashTrade_Order )
              {
                // MoneyMarket
                if ( Configurator.istAktienMarket() )
                {
                  // Grosser Fehler
                  System.out.println(" Error! Get a Cash_order Result but System Conf is AktienMarket   ");
                  return;
                }
                processCashTrade_Order( (CashTrade_Order)msgwrp.mMessageContent );

                // send Next Order
                sendOrder2StockStore();
              }
              else
              {
                // other message is droped.
                return;

              }
          }
      mDataCC++;
      //The expected Handelday has been reached.
      //Stop the Agent
      if ( mDataCC == Configurator.mConfData.mHandelsday  )
      {
         sendDepotRecord2DataLogger();
         UnRegisterMe();
         StopAgent();
      }
}

// Send DepotRecord to DataLogger for Group Statistic
public void sendDepotRecord2DataLogger()
{
    MessageWrapper msgwrp = MessageFactory.createDepotRecordMessage( this.mDepotRecord );

    ACLMessage msg= new ACLMessage( ACLMessage.INFORM );
    try
    {
       msg.setContentObject( msgwrp);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    msg.addReceiver( new AID( "DataLogger", false ) );
    // Send the own DepotRecord to DataLogger
    this.myAgent.send(msg);

}



private void sendOrder2StockStore()
{
  char dynamicdecision = this.makeDecision();
  // generate a decison according to the procent of buy,sell and wait
  int  WishMenge;
  if ( Configurator.istAktienMarket() )
  {
     WishMenge = this.createWishMengeForAktienMarket(dynamicdecision, this.mYesterdayprice);
  }
  else
  {
     WishMenge = this.createWishMengeForMoneyMarket(dynamicdecision, this.mYesterdayprice);
  }
  // There are 3 decisions:
  // 1.  'B' buy a stock
  // 2.  'S' sell a stock
  // 3.  'N' Nothing, only OK confirmation
  ACLMessage aclmsg= new ACLMessage( ACLMessage.INFORM );

  MessageWrapper msgwrp = null;
  // Decision ist gezwungen zu WAIT zu ändern.

  if ( Configurator.istAktienMarket() )
  {
     msgwrp = this.createAktienOrder( dynamicdecision, WishMenge );
  }
  else
  {
    msgwrp = this.createCashOrder( dynamicdecision, WishMenge );
  }


  try
  {
     aclmsg.setContentObject( msgwrp );
  }
  catch (Exception ex)
  {
    ex.printStackTrace();
  }

  aclmsg.addReceiver( new AID( "DAX", false ) );
  // Send the new order to StockStore
  this.myAgent.send( aclmsg);

  if ( mTraceOrder )
  {
    System.out.println("Randomtrader: send 1 Order" );
  }

}

private  MessageWrapper createAktienOrder(char dynamicdecision, int WishMenge)
{
  if ( ( WishMenge == 0 ) || ( dynamicdecision == 'N') )
  {
       return MessageFactory.createAktienOrder
       ( SystemConstant.AgentType_RANDOMTRADER,
         SystemConstant.WishType_Wait,
         0,
         0,
         this.mDepotRecord.mAbsoluteGewinnProzent,
         this.mDepotRecord.mRelativeGewinnProzent,
         "",
         false,
         SystemConstant.AgentType_RANDOMTRADER );
  }

  if ( dynamicdecision == 'B' )
  {
        int r =  this.randomfororder.nextInt(100);
        if ( (r <= Configurator.mConfData.mRandomTraderBuyProbabilityIndexBased) )
        {
            int    AktienMarket_newlimit = 0;
            // Case: Gled
            if ( this.mPerformedStatus.getYesterdayTradeResult() == SystemConstant.TradeResult_Geld )
            {
                AktienMarket_newlimit = (int) ( this.mYesterdayprice * ( 1 + 0.01 ) );
            }
            else
            // Case: Brief
            if ( this.mPerformedStatus.getYesterdayTradeResult() == SystemConstant.TradeResult_Brief )
            {
                AktienMarket_newlimit = (int) ( this.mYesterdayprice * ( 1 - 0.01 ) );
            }
            else
            {
                //Case: bezahlt und Taxe
                AktienMarket_newlimit = (int)  this.mYesterdayprice;
            }
             return MessageFactory.createAktienOrder
             ( SystemConstant.AgentType_RANDOMTRADER,
               SystemConstant.WishType_Buy,
               WishMenge,
               AktienMarket_newlimit,
               this.mDepotRecord.mAbsoluteGewinnProzent,
               this.mDepotRecord.mRelativeGewinnProzent,
               "",
               false,
               SystemConstant.AgentType_RANDOMTRADER );
        }
        else
        {
            // cheapest buy
            return MessageFactory.createAktienOrder
            ( SystemConstant.AgentType_RANDOMTRADER,
              SystemConstant.WishType_Buy,
              WishMenge,
              SystemConstant.Limit_CheapestBuy,
              this.mDepotRecord.mAbsoluteGewinnProzent,
              this.mDepotRecord.mRelativeGewinnProzent,
              "Cheapest buy",
              false,
              SystemConstant.AgentType_RANDOMTRADER);
        }
    }
    else
    {
        // Sell
        int r = this.randomfororder.nextInt(100);
        if ( (r <=  Configurator.mConfData.mRandomTraderSellProbabilityIndexbased ) )
        {
                  int AktienMarket_newlimit = 0;
                  String LimitReason = "";
                  // Case Geld
                  if ( this.mPerformedStatus.getYesterdayTradeResult() == SystemConstant.TradeResult_Geld )
                  {
                      AktienMarket_newlimit = (int) ( this.mYesterdayprice * ( 1 + 0.01 ) );
                      LimitReason = "Vortag * (1+0.01) wegen Kurszusatz " + SystemConstant.TradeResult_Geld;
                  }
                  else
                  // Case: Brief
                  if ( this.mPerformedStatus.getYesterdayTradeResult() == SystemConstant.TradeResult_Brief )
                  {
                      AktienMarket_newlimit = (int) ( this.mYesterdayprice * ( 1 - 0.01 ) );
                      LimitReason = "Vortag * (1 - 0.01) wegen Kurszusatz " + SystemConstant.TradeResult_Brief;
                  }
                  else
                  {
                    // Case: bezahlt und Taxe
                    AktienMarket_newlimit = (int)this.mYesterdayprice;
                    LimitReason = "Vortag";

                  }
                  return MessageFactory.createAktienOrder
                  ( SystemConstant.AgentType_RANDOMTRADER,
                    SystemConstant.WishType_Sell,
                    WishMenge,
                    AktienMarket_newlimit,
                    this.mDepotRecord.mAbsoluteGewinnProzent,
                    this.mDepotRecord.mRelativeGewinnProzent,
                    LimitReason,
                    false,
                    SystemConstant.AgentType_RANDOMTRADER);
          }
          else
          {
              return MessageFactory.createAktienOrder
                     ( SystemConstant.AgentType_RANDOMTRADER,
                       SystemConstant.WishType_Sell,
                       WishMenge,
                       SystemConstant.Limit_BestenSell,
                       this.mDepotRecord.mAbsoluteGewinnProzent,
                       this.mDepotRecord.mRelativeGewinnProzent,
                       "",
                       false,
                       SystemConstant.AgentType_RANDOMTRADER);
          }
    }
}

private  MessageWrapper createCashOrder(char dynamicdecision, int WishMenge )
{
    if ( ( WishMenge == 0 ) || ( dynamicdecision == 'N') )
    {
        return MessageFactory.createCashOrder
        ( SystemConstant.AgentType_RANDOMTRADER,
          SystemConstant.WishType_Wait,
          0,
          0,
          this.mDepotRecord.mAbsoluteGewinnProzent,
          this.mDepotRecord.mRelativeGewinnProzent,
          "" );
    }

    if ( dynamicdecision == 'B' )
    {
        int r = this.randomfororder.nextInt(100);
        if ( (r <= Configurator.mConfData.mRandomTraderBuyProbabilityIndexBased) )
        {
            String LimitReason = "";
            double MoneyMarket_newlimit = 0.0;
            // Case: Gled
            if ( this.mPerformedStatus.getYesterdayTradeResult() == SystemConstant.TradeResult_Geld )
            {
                MoneyMarket_newlimit =  this.mYesterdayprice * ( 1 + 0.01 ) ;
                LimitReason = "Vortag * (1+0.01) wegen Kurszusatz " + SystemConstant.TradeResult_Geld;
            }
            else
            // Case: Brief
            if ( this.mPerformedStatus.getYesterdayTradeResult() == SystemConstant.TradeResult_Brief )
            {
                MoneyMarket_newlimit = this.mYesterdayprice * ( 1 - 0.01 ) ;
                LimitReason = "Vortag * (1-0.01) wegen Kurszusatz " + SystemConstant.TradeResult_Brief;            }
            else
            {
                //Case: bezahlt und Taxe
                MoneyMarket_newlimit = this.mYesterdayprice;
                LimitReason = "Vortag";
            }

            // formatierte Limit zu  xx.yyyy Format
            int LL = (int) ( MoneyMarket_newlimit*10000 );
            double MM = LL * 1.0 / 10000;
            return  MessageFactory.createCashOrder
            ( SystemConstant.AgentType_RANDOMTRADER,
              SystemConstant.WishType_Buy,
              WishMenge,
              MM,
              this.mDepotRecord.mAbsoluteGewinnProzent,
              this.mDepotRecord.mRelativeGewinnProzent,
              LimitReason );
        }
        else
        {
              // cheapest buy
              return MessageFactory.createCashOrder
              ( SystemConstant.AgentType_RANDOMTRADER,
                SystemConstant.WishType_Buy,
                WishMenge,
                SystemConstant.Limit_CheapestBuy,
                this.mDepotRecord.mAbsoluteGewinnProzent,
                this.mDepotRecord.mRelativeGewinnProzent,
                "" );
        }
    }
    else
    {
        // Sell
        int r = this.randomfororder.nextInt(100);
        if ( (r <=  Configurator.mConfData.mRandomTraderSellProbabilityIndexbased ) )
        {
             String LimitReason = "";
             // CashMarket
             double MoneyMarket_newlimit = 0.0;
             // Case Geld
             if ( this.mPerformedStatus.getYesterdayTradeResult() == SystemConstant.TradeResult_Geld )
             {
                 MoneyMarket_newlimit = this.mYesterdayprice * ( 1 + 0.01 ) ;
                 LimitReason = "Vortag * (1+0.01) wegen Kurszusatz " + SystemConstant.TradeResult_Geld;
             }
             else
             // Case: Brief
             if ( this.mPerformedStatus.getYesterdayTradeResult() == SystemConstant.TradeResult_Brief )
             {
                 MoneyMarket_newlimit = this.mYesterdayprice * ( 1 - 0.01  );
                 LimitReason = "Vortag * (1 - 0.01) wegen Kurszusatz " + SystemConstant.TradeResult_Brief;
             }
             else
             {
                 // Case: bezahlt und Taxe
                 MoneyMarket_newlimit = this.mYesterdayprice;
                 LimitReason = "Vortag";
             }
             int LL = (int) ( MoneyMarket_newlimit*10000 );
             double MM = LL * 1.0 / 10000;
             return MessageFactory.createCashOrder
             ( SystemConstant.AgentType_RANDOMTRADER,
               SystemConstant.WishType_Sell,
               WishMenge,
               MM,
               this.mDepotRecord.mAbsoluteGewinnProzent,
               this.mDepotRecord.mRelativeGewinnProzent,
              LimitReason );
          }
          else
          {
              return MessageFactory.createCashOrder
              ( SystemConstant.AgentType_RANDOMTRADER,
                SystemConstant.WishType_Sell,
                WishMenge,
                SystemConstant.Limit_BestenSell,
                this.mDepotRecord.mAbsoluteGewinnProzent,
                this.mDepotRecord.mRelativeGewinnProzent,
                "" );
          }
    }
}

private void processAktienTrade_Order(AktienTrade_Order pAktienOrder)
{
            PriceContainer.saveTraderPrice(this.mDataCC, pAktienOrder.mFinalPrice);

            // The price is saved, it will be used for new order

            if (  pAktienOrder.mFinalPrice == 0 )
            {
                System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                System.exit(0);
            }

            this.mYesterdayprice = pAktienOrder.mFinalPrice;
            this.mLogger.debug( this.mDataCC+": FinalPrice=" + pAktienOrder.mFinalPrice );

            String orderprocessedmsg = "";
            if ( pAktienOrder.mBuyPerformed )
            {
               this.mPerformedStatus =new OrderPerformedStatus
               (
                    pAktienOrder.mTradeResult,
                    pAktienOrder.mFinalPrice,
                    SystemConstant.WishType_Buy,
                    pAktienOrder.mTradeMenge,
                    pAktienOrder.mBuyLimit
                );

               this.mDepotRecord.AktienMarket_BuyAktien(  pAktienOrder.mTradeMenge, pAktienOrder.mFinalPrice );
               orderprocessedmsg =  "Buy Confirmed: Menge=" +
                                     pAktienOrder.mTradeMenge +
                                    " Kurs=" + pAktienOrder.mFinalPrice;

             }
             else
             if ( pAktienOrder.mSellPerformed )
             {
                 this.mPerformedStatus =new OrderPerformedStatus
                 (
                      pAktienOrder.mTradeResult,
                      pAktienOrder.mFinalPrice,
                      SystemConstant.WishType_Sell,
                      pAktienOrder.mTradeMenge,
                      pAktienOrder.mSellLimit
                  );

                 this.mDepotRecord.AktienMarket_SellAktien( pAktienOrder.mTradeMenge, pAktienOrder.mFinalPrice );
                 orderprocessedmsg = "Sell Confirmed: Menge=" +
                                   pAktienOrder.mTradeMenge +
                                     " Kurs=" + pAktienOrder.mFinalPrice;

             }
            else
            {
                this.mPerformedStatus =new OrderPerformedStatus
                (
                       pAktienOrder.mTradeResult,
                       pAktienOrder.mFinalPrice,
                       SystemConstant.WishType_Wait,
                       0,
                       0
                );
                // update the price
                this.mDepotRecord .AktienMarket_setCurrentPrice( pAktienOrder.mFinalPrice );

                if ( pAktienOrder.mOrderWish == SystemConstant.WishType_Mixed )
                {
                  orderprocessedmsg = "Wish not fulfilled. new Price=" + pAktienOrder.mFinalPrice;
                }
                else
                {
                  orderprocessedmsg = "No Buy/S Wish. new Price=" + pAktienOrder.mFinalPrice;
                }
            }
             this.mLogger.debug( "---"+mDataCC+"." + this.myAgent.getLocalName() + " "+ orderprocessedmsg );
             //send Depot to DataLogger
             this.sendDepotStatus( pAktienOrder );
 }

  public void sendDepotStatus( AktienTrade_Order pAktienOrder )
  {
        // send this depot to DataLogger
       ACLMessage aclmsg = new ACLMessage( ACLMessage.INFORM );
       aclmsg.addReceiver( new AID ( "DataLogger", false) );

       MessageWrapper msgwrp = new MessageWrapper();
       msgwrp.mMessageType = SystemConstant.MessageType_Depotstate;
       // get the current depot and insert into Message
       String tt = "000000" + this.mDataCC;
       String depotinfo =
       "DEPOT," +
       tt.substring( tt.length() - this.mDayIndexLength ) + ";" +
       SystemConstant.getOperatorTypeName( this.mOperatorType) +";" +
       this.mDepotRecord.AktienMarket_getCurrentAktienMenge()+";"+
       this.mDepotRecord.AktienMarket_getCurrentCash()+";"+
       this.mDepotRecord.AktienMarket_getCurrentDepot() + ";"+
       this.mDepotRecord.mRelativeGewinn+";"+
       nff.format2str( this.mDepotRecord.mRelativeGewinnProzent )+";"+
       this.mDepotRecord.mAbsoluteGewinn+";"+
       nff.format2str( this.mDepotRecord.mAbsoluteGewinnProzent )+";"+
       pAktienOrder.mOrderWish+";";

       String ss ="";
       if ( pAktienOrder.mOrderWish == SystemConstant.WishType_Buy )
       {
         ss = pAktienOrder.mBuyMenge+";" + pAktienOrder.mBuyLimit;
       }
       else
       if ( pAktienOrder.mOrderWish == SystemConstant.WishType_Sell )
       {
         ss = pAktienOrder.mSellMenge+";"+ pAktienOrder.mSellLimit;
       }
       else
       {
         ss = "0;0";
       }
       depotinfo = depotinfo + ss + ";" +
       pAktienOrder.mFinalPrice + ";" +
       pAktienOrder.mTradeResult + ";" +
       pAktienOrder.mTradeMenge+";" ;

       this.mLogger.debug(depotinfo);

       /* simple format */
       msgwrp.mMessageContent = depotinfo;

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

  public void sendDepotStatus( CashTrade_Order pCashOrder )
  {
        // send this depot to DataLogger
       ACLMessage aclmsg = new ACLMessage( ACLMessage.INFORM );
       aclmsg.addReceiver( new AID ( "DataLogger", false) );

       MessageWrapper msgwrp = new MessageWrapper();
       msgwrp.mMessageType = SystemConstant.MessageType_Depotstate;
       // get the current depot and insert into Message
       String tt = "000000" + this.mDataCC;

       String depotinfo =
           "DEPOT," +
           tt.substring( tt.length() - this.mDayIndexLength ) + ";" +
           SystemConstant.getOperatorTypeName( this.mOperatorType) +";" +
           nff.format2str( this.mDepotRecord.mMoneyMarket_CurrentCash1 )+";"+
           nff.format2str( this.mDepotRecord.mMoneyMarket_CurrentCash2 )+";"+
           nff.format2str( this.mDepotRecord.mMoneyMarket_CurrentCashDepot )+";"+
           this.mDepotRecord.mRelativeGewinn+";"+
           nff.format2str( this.mDepotRecord.mRelativeGewinnProzent )+";"+
           this.mDepotRecord.mAbsoluteGewinn+";"+
           nff.format2str(  this.mDepotRecord.mAbsoluteGewinnProzent )+";"+

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



/*
private void sendDepot2DataLogger( AktienTrade_Order pAktienTrade_Order )
{
      ACLMessage msgx = new ACLMessage( ACLMessage.INFORM );
      msgx.addReceiver( new AID ( "DataLogger", false) );
      // Data Format:
      // DEPOT,Handelstag;AgentType;RegelName;Depot;Preis;Aktienanzhal;Cash;LastWish;WishPrice
      // For RandomTrader
      // DEPOT,
      // Handelstag;
      // AgentType;
      // RANDOM;
      // Depot;
      // Preis;
      // Aktienanzhal;
      // Cash;
      // LastWish;
      // WishMenge,
      // WishPrice
      // get the current depot and insert into Message
      String Dayindex ="0000"+DataCC;
      int DayIndexLength = ( Configurator.mConfData.mHandelsday+"" ).length();

      MessageWrapper msgwrp = new MessageWrapper ();
      msgwrp.mMessageType    = SystemConstant.MessageType_Depotstate;
      msgwrp.mMessageContent = "DEPOT," +
                      Dayindex.substring( Dayindex.length() - DayIndexLength )+";" +
                      SystemConstant.getOperatorTypeName( this.mOperatorType ) +";" +
                      this.mRuleName+";" +
                      pAktienTrade_Order.mFinalPrice +";" +
                      pAktienTrade_Order.mTradeResult +";" +
                      this.mDepotRecord.AktienMarket_getCurrentAktienMenge() + ";" +
                      this.mDepotRecord.AktienMarket_getCurrentCash()+";"+
                      pAktienTrade_Order.mOrderWish+";"+
                      pAktienTrade_Order.mMenge+";"+
                      pAktienTrade_Order.mLimit+";"+
                      pAktienTrade_Order.mTradeMenge+";"+
                      this.mDepotRecord.AktienMarket_getCurrentDepot() + ";"+
                      this.mDepotRecord.mRelativeGewinnStatus+";"+
                      this.mDepotRecord.mRelativeGewinn+";"+
                      this.mDepotRecord.mRelativeGewinnProzent+";"+
                      this.mDepotRecord.mAbsoluteGewinnStatus+";"+
                      this.mDepotRecord.mAbsoluteGewinn+";"+
                      this.mDepotRecord.mAbsoluteGewinnProzent+";";
      try
      {
       msgx.setContentObject( msgwrp );
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
      myAgent.send( msgx );
}

private void sendDepot2DataLogger( CashTrade_Order pCashTrade_Order )
{
      ACLMessage msgx = new ACLMessage( ACLMessage.INFORM );
      msgx.addReceiver( new AID ( "DataLogger", false) );
      // Data Format:
      // DEPOT,Handelstag;AgentType;RegelName;Depot;Preis;Aktienanzhal;Cash;LastWish;WishPrice
      // For RandomTrader
      // DEPOT,
      // Handelstag;
      // AgentType;
      // RANDOM;
      // Depot;
      // Preis;
      // Aktienanzhal;
      // Cash;
      // LastWish;
      // WishMenge,
      // WishPrice
      // get the current depot and insert into Message
      String Dayindex ="0000"+DataCC;
      int DayIndexLength = ( Configurator.mConfData.mHandelsday+"" ).length();

      MessageWrapper msgwrp = new MessageWrapper ();
      msgwrp.mMessageType    = SystemConstant.MessageType_Depotstate;
      msgwrp.mMessageContent = "DEPOT," +
                      Dayindex.substring( Dayindex.length() - DayIndexLength )+";" +
                      SystemConstant.getOperatorTypeName( this.mOperatorType ) +";" +
                      this.mRuleName+";" +
                      pCashTrade_Order.mFinalKurs +";" +
                      pCashTrade_Order.mTradeResult +";" +
                      this.mDepotRecord.AktienMarket_getCurrentAktienMenge() + ";" +
                      this.mDepotRecord.AktienMarket_getCurrentCash()+";"+
                      pCashTrade_Order.mOrderWish+";"+
                      pCashTrade_Order.mCash2+";"+
                      pCashTrade_Order.mLimit+";"+
                      pCashTrade_Order.mTradeCash2+";"+
                      this.mDepotRecord.AktienMarket_getCurrentDepot() + ";"+
                      this.mDepotRecord.mRelativeGewinnStatus+";"+
                      this.mDepotRecord.mRelativeGewinn+";"+
                      this.mDepotRecord.mRelativeGewinnProzent+";"+
                      this.mDepotRecord.mAbsoluteGewinnStatus+";"+
                      this.mDepotRecord.mAbsoluteGewinn+";"+
                      this.mDepotRecord.mAbsoluteGewinnProzent+";";
      try
      {
       msgx.setContentObject( msgwrp );
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
      myAgent.send( msgx );
}
*/

private void processCashTrade_Order(CashTrade_Order pCashOrder)
{
       PriceContainer.saveTraderPrice(this.mDataCC, pCashOrder.mFinalKurs);
       // The price is saved, it will be used for new order
       this.mYesterdayprice = pCashOrder.mFinalKurs;
       String orderprocessedmsg = "";
       System.out.println( this.myAgent.getLocalName() + ": Order Result" + pCashOrder.getResult() );

        if ( pCashOrder.mBuyPerformed )
        {
              this.mPerformedStatus =new OrderPerformedStatus
              (
                   pCashOrder.mTradeResult,
                   pCashOrder.mFinalKurs,
                   SystemConstant.WishType_Buy,
                   pCashOrder.mTradeCash2,
                   pCashOrder.mBuyLimit
               );
               this.mDepotRecord.MoneyMarket_BuyCash2(  pCashOrder.mInvolvedCash1, pCashOrder.mTradeCash2, pCashOrder.mFinalKurs );
               orderprocessedmsg =  "Buy Confirmed: Cash1=" +
                                    pCashOrder.mInvolvedCash1 +
                                   " Kurs=" + pCashOrder.mFinalKurs;
            }
            else
            if ( pCashOrder.mSellPerformed )
            {
              this.mPerformedStatus =new OrderPerformedStatus
              (
                   pCashOrder.mTradeResult,
                   pCashOrder.mFinalKurs,
                   SystemConstant.WishType_Sell,
                   pCashOrder.mTradeCash2,
                   pCashOrder.mSellLimit
               );
               this.mDepotRecord.MoneyMarket_SellCash2( pCashOrder.mInvolvedCash1, pCashOrder.mTradeCash2, pCashOrder.mFinalKurs );
               orderprocessedmsg =  "Sell Confirmed: Cash1=" +
                                    pCashOrder.mInvolvedCash1 +
                                   " Kurs=" + pCashOrder.mFinalKurs;
            }
            else
            {
              this.mPerformedStatus =new OrderPerformedStatus
              (
                     pCashOrder.mTradeResult,
                     pCashOrder.mFinalKurs,
                     SystemConstant.WishType_Wait,
                     pCashOrder.mInvolvedCash1,
                     pCashOrder.mBuyLimit
              );
              // update the price
              this.mDepotRecord.MoneyMarket_setCurrentKurs( pCashOrder.mFinalKurs );
              if ( pCashOrder.mOrderWish == SystemConstant.WishType_Mixed )
              {
                orderprocessedmsg = "Wish not fulfilled. new Price=" + pCashOrder.mFinalKurs;
              }
              else
              {
                orderprocessedmsg = "No Buy/S Wish. new Price=" + pCashOrder.mFinalKurs;
              }
            }
           System.out.println( "---"+ mDataCC+"." + this.myAgent.getLocalName() + " "+ orderprocessedmsg );
           this.sendDepotStatus( pCashOrder );
}

private char makeDecision()
{
      int r = this.randomforsleep.nextInt(1000);
      if ( (r >=0) && ( r < Configurator.mConfData.mRandomTraderBuyProbability * 10 ) )
      {
        return 'B';
      }
      else
      if ( (r >= Configurator.mConfData.mRandomTraderBuyProbability * 10)
            && ( r < ( Configurator.mConfData.mRandomTraderBuyProbability + Configurator.mConfData.mRandomTraderSellProbability ) * 10 ) )
      {
          return 'S';
      }
      else
      {
         System.out.println("Random Trader RRR=" +  r);
         return 'N';
      }
  }

 private void processFirst300InitData( First300InitData  pFirst300data)
 {
     // The price of No. 300 price data
     // save this price, it will be used for next order
     if ( Configurator.istAktienMarket() )
     {
         PriceContainer.saveFirst300Price(pFirst300data.mIntData);
         this.mYesterdayprice = pFirst300data.mIntData[ pFirst300data.mIntData.length -1 ];

         // Update Init Depot using the lastprice
         this.mDepotRecord.AktienMarket_setInitPrice( pFirst300data.mIntData[ pFirst300data.mIntData.length -1 ] );
     }
     else
     {
          PriceContainer.saveFirst300Kurs( pFirst300data.mDoubleData );
          this.mYesterdayprice = pFirst300data.mDoubleData[ pFirst300data.mDoubleData.length -1 ];

          // Update Init Depot using the lastprice
          this.mDepotRecord.MoneyMarket_setInitKurs( this.mYesterdayprice );
     }

     // A init status, but only lastprice is useful.
     this.mPerformedStatus = new OrderPerformedStatus( SystemConstant.TradeResult_Taxe ,
                                                       this.mYesterdayprice,
                                                       'N',
                                                       0,
                                                       this.mYesterdayprice );
 }

  private int createWishMengeForAktienMarket(char pDecision, double pLimit)
  {
      // N is in [Min, Max]
      int N = Configurator.mConfData.mRandomTraderMinMenge +
              this.randomformenge.nextInt( Configurator.mConfData.mRandomTraderMaxMenge + 1 -
                               Configurator.mConfData.mRandomTraderMinMenge );

      if ( pDecision == 'S')
      {
        if  ( N <= this.mDepotRecord.AktienMarket_getCurrentAktienMenge() )
        {
          return N;
        }
        else
        if ( this.mDepotRecord.AktienMarket_getCurrentAktienMenge() >=
            Configurator.mConfData.mRandomTraderMinMenge )
        {
             return Configurator.mConfData.mRandomTraderMinMenge;
        }
        else
        {
           return 0;
        }
      }
      else
      if ( pDecision == 'B' )
      {
              long cashallowedbuymeng = this.mDepotRecord.AktienMarket_getCurrentCash() / this.mDepotRecord.AktienMarket_getCurrentPrice();
              if ( cashallowedbuymeng < Configurator.mConfData.mRandomTraderMinMenge )
              {
                  // When es keine Cash oder keine genug Cash für eine Minimal Order-Menge gibt,
                 return 0;
              }
              else
              {
                if ( N <= cashallowedbuymeng )
                {
                    return N;
                }
                else
                {
                    return Configurator.mConfData.mRandomTraderMinMenge;
                }
              }
      }
      else
      {
          return 0;
      }
  }

  private int createWishMengeForMoneyMarket(char pDecision, double pLimit)
  {
      // N is in [Min, Max]
      int N = Configurator.mConfData.mRandomTraderMinMenge +
              this.randomformenge.nextInt( Configurator.mConfData.mRandomTraderMaxMenge + 1 -
                               Configurator.mConfData.mRandomTraderMinMenge );

      if ( pDecision == 'S')
      {
        if  ( N <= this.mDepotRecord.mMoneyMarket_CurrentCash2 )
        {
          return N;
        }
        else
        if ( this.mDepotRecord.mMoneyMarket_CurrentCash2 >=
            Configurator.mConfData.mRandomTraderMinMenge )
        {
             return Configurator.mConfData.mRandomTraderMinMenge;
        }
        else
        {
           return 0;
        }
      }
      else
      if ( pDecision == 'B' )
      {
              int cashallowedbuymeng = (int ) ( this.mDepotRecord.mMoneyMarket_CurrentCash1 * this.mDepotRecord.mMoneyMarket_CurrentKurs );
              if ( cashallowedbuymeng < Configurator.mConfData.mRandomTraderMinMenge )
              {
                  // When es keine Cash oder keine genug Cash für eine Minimal Order-Menge gibt,
                 return 0;
              }
              else
              {
                if ( N <= cashallowedbuymeng )
                {
                    return N;
                }
                else
                {
                    return Configurator.mConfData.mRandomTraderMinMenge;
                }
              }
      }
      else
      {
          return 0;
      }
  }

  private void Registerme()
  {
    ACLMessage msg= new ACLMessage( ACLMessage.INFORM );
    MessageWrapper msgwrapper = MessageFactory.createRegisterMessage
                        ( myAgent.getLocalName(),
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

  private void UnRegisterMe()
  {
    ACLMessage msg= new ACLMessage( ACLMessage.INFORM );
    MessageWrapper msgwrapper = MessageFactory.createUnRegisterMessage
                        ( myAgent.getLocalName(),
                          this.mOperatorType);
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
    System.out.println( this.myAgent.getLocalName() +": UNREGISTERME has been sent.");
    try
    {
       Thread.sleep(100);
    }
    catch (Exception ex)
    {

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
        OperationSynchroner.oneagentlogout();
  }

}