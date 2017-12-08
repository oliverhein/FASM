/**
 * Überschrift:
 * Beschreibung:
 *
 * Redesigned this class
 *
 * Copyright:     Copyright (c) 2006
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

import java.util.*;
import java.io.*;
import java.lang.*;

import de.marketsim.SystemConstant;
import de.marketsim.message.*;

import de.marketsim.gui.*;
import de.marketsim.util.*;
import de.marketsim.config.*;

public class OperatorBehaviorMoneyMarket extends OperatorBaseBehavior
{

  boolean typechanged = false;

  BestOperatorFinder bopfinder  = null;
  FriendStatus    mBestNachbarofLastExchange = null;
  boolean firstRequest        = true;
  int PriceHistoryNumber      = 0;

  char  Reaction = ' ';

  OrderPerformedStatus      mPerformedStatus = null;

  int  mVerlustCounter = 0;

  //private int mTypWechselStrategie = Configurator.mConfData.mTypWechselStrategie_Nachverlust;

  String mBestPartnerType ="";
  String mBestPartnerRule ="";

  Vector  mEarlyFriendStatusBufferFromPartner = new Vector();

  public OperatorBehaviorMoneyMarket(Agent  pAgent)
  {
     super(pAgent);
  }

  // main process flow
  // this action Method will be automatic called from jader.Agent.setup().
  public void action()
  {
        if ( firstRequest )
        {
             this.makeInitWork();
             firstRequest = false;
             return;
        }

         boolean AllMessageFromStockStoreReady = false;
         boolean AgentIsBroken = false;
         while ( ( !AllMessageFromStockStoreReady ) && ( ! AgentIsBroken) )
         {
               ACLMessage aclmsg =  this.myAgent.blockingReceive(100000);
               if ( aclmsg == null)
               {
                  System.out.println( this.myAgent.getAID().getLocalName() + ": waiting for message" );
               }
               else
               {
                     MessageWrapper msgwrp  = null;
                     try
                     {
                         msgwrp = ( MessageWrapper ) aclmsg.getContentObject();
                     }
                     catch (Exception ex)
                     {
                        ex.printStackTrace();
                        System.out.println( "ErrorMsg from :" +  aclmsg.getSender().getLocalName() );
                        System.out.println( "ErrorMsg=" +  aclmsg.getContent() );
                        return;
                     }

                     if (  msgwrp.mMessageType == SystemConstant.MessageType_InterruptCommand )
                     {
                       AgentIsBroken = true;
                     }
                     else
                    if ( msgwrp.mMessageType == SystemConstant.MessageType_Innererwert )
                    {
                      this.mCurrentInnererWert = Double.parseDouble( (String) msgwrp.mMessageContent );
                      AllMessageFromStockStoreReady = true;
                    }
                    else
                    if ( msgwrp.mMessageType == SystemConstant.MessageType_CashTrade_Order )
                    {
                       CashTrade_Order cashorder = ( CashTrade_Order ) msgwrp.mMessageContent;
                       processCashOrder( cashorder);
                    }
                    else
                    if ( msgwrp.mMessageType == SystemConstant.MessageType_FriendStatus )
                    {
                        FriendStatus ff = ( FriendStatus ) msgwrp.mMessageContent;
                        // Early arrived FriendStatusInfo must be saved temperaly.
                        // It will be used by GewinnStatusExchange methode
                        this.mEarlyFriendStatusBufferFromPartner.add( ff  );
                    }
               }
          }

          if ( AgentIsBroken )
          {
            this.UnRegisterMe();
            this.StopAgent();
            return;
          }
         typechanged = false;
         // It is time for status exchanging !!!
         if ( mDataCC >0 )
         {
            mBestPartnerType = "";
            this.mBestPartnerRule = "";
            // Following Action will be performed:
            // 1. Send GewinnStatus to my Partner
            // 2. Wait and  receive the GewinnStatus from my SenderPartner
            // 3. check and try to change its type according to Probabiliy and Sleep and other Rule
            boolean ReceivedInterruptedCommand = exchangeGewinnStatus();

            if ( ReceivedInterruptedCommand )
            {
                return;
            }
         }

      // prepare basic information for next Order
      CashOrderBasicData orderbasic = this.prepareOrder();

      // OperationSynchroner.startsynchron();
      //The expected Handelday has been archieved.
      //Stop the Agent
      mDataCC=mDataCC+1;
      if ( mDataCC < Configurator.mConfData.mHandelsday )
      {
          // Send this Order to StockStore
          sendFeedBack2Store( orderbasic.finaldecision, orderbasic.finalmenge, orderbasic.BuyLimit, orderbasic.SellLimit);
      }
      else
      {
           // Simulation is end
           UnRegisterMe();
           StopAgent();
      }
}
// end of action()

/**
 * prepare a new order:
 * Buy-Limit, Sell-Limit, TradeMenge
 * @return
 */
private CashOrderBasicData prepareOrder()
{
  int  Wishexchangemenge = 0;
  int  finalmenge        = 0;
  char finaldecision     = 'N';
  int  SleepProzent      = this.getSleepProzent();
  boolean sleepneeded    = false;

  double BuyLimit  = 0 ;
  double SellLimit = 0;

  if ( SleepProzent != 0 )
  {
    if (this.mRandom.nextInt(100) < SleepProzent )
    {
      sleepneeded = true;
    }
  }

    this.detailedcalculationinfo = "";
    if ( this.mOperatorType == SystemConstant.AgentType_INVESTOR )
    {
       Wishexchangemenge = RuleAnalyser.makeInvestorOrderMenge( this.mCurrentInnererWert, this.mUsedAbschlagProzent, 'B' );
       BuyLimit  =  this.mCurrentInnererWert * ( 1.0 - this.mUsedAbschlagProzent/100.0 ) ;
       SellLimit =  this.mCurrentInnererWert * ( 1.0 + this.mUsedAbschlagProzent/100.0 ) ;
       finaldecision = 'M';
       finalmenge = this.makeFinalMenge('B', Wishexchangemenge, BuyLimit, mDataCC+1 );

       // eigene Limit muss gespeichert werden.
       // die gespeicherte Limit werden fuer Vregleich benutzt.
       LimitRecord heutelimitrecord = new LimitRecord ( this.mDataCC, BuyLimit, SellLimit ) ;
       this.addOneLimitRecord( heutelimitrecord );

    }
    else
    if ( this.mOperatorType == SystemConstant.AgentType_NOISETRADER )
    {
           // update eigene AveragePrice
           this.mCurrentAveragePrice = PriceContainer.getMovingAveragePrice( this.mDayofAveragePrice );

           double lastpp = PriceContainer.getLastPrice();

           // decide BuyLimit or SellLimit bzw. Buy or Sell
           if (  lastpp >=  this.mCurrentAveragePrice )
           {
              finaldecision = 'B'; // Buy
              BuyLimit  = lastpp * ( 1 + this.mInitConfig.mLimitAdjust / 100.0 );
              // get the WishMenge according to allgemeine NoiseTarder regel
              Wishexchangemenge = (int) RuleAnalyser.makeNoiseTraderOrderMenge( this.mCurrentInnererWert, finaldecision , this.mCurrentAveragePrice);
              finalmenge = this.makeFinalMenge( finaldecision, Wishexchangemenge, BuyLimit, mDataCC+1 );
           }
           else
           {
               finaldecision = 'S'; // Sell
               SellLimit  = lastpp * ( 1 - this.mInitConfig.mLimitAdjust / 100.0) ;
               // get the WishMenge according to allgemeine NoiseTarder regel
               Wishexchangemenge = (int) RuleAnalyser.makeNoiseTraderOrderMenge( this.mCurrentInnererWert, finaldecision , this.mCurrentAveragePrice);
               finalmenge = this.makeFinalMenge( finaldecision, Wishexchangemenge, SellLimit, mDataCC+1 );
           }
           this.detailedcalculationinfo = ";MyAveragePrice="+ nff.format2str(this.mCurrentAveragePrice)+ " yesterdayprice=" +nff.format2str(lastpp);
    }

    if ( finalmenge == 0 )
    {
        finaldecision ='N';
    }

    if ( sleepneeded )
    {
      // create Order with Wish "Sleep"
      finaldecision = 'N';
      finalmenge    = 0;
    }

    if ( this.mOperatorType == SystemConstant.AgentType_NOISETRADER )
    {
      this.mLogger.debug( "NNNN;"+this.myAgent.getLocalName()+";" + this.mDataCC +";"+ PriceContainer.getLastPrice()+ ";"+this.mCurrentAveragePrice + "; Decision=" + finaldecision+";" + sleepneeded+";wishmenge="+Wishexchangemenge+";FinalMenge="+finalmenge) ;
    }

    // formatierte Limit zu  xx.yyyy Format
    int LL = (int) ( BuyLimit*10000 );
    BuyLimit = LL * 1.0 / 10000;

    LL = ( int ) ( SellLimit * 10000 );
    SellLimit = LL * 1.0 / 10000;

    return new CashOrderBasicData( finaldecision, finalmenge, BuyLimit, SellLimit );

}



/*
private OrderBasicData prepareOrder()
{
  int  Wishexchangemenge = 0;
  int  finalmenge = 0;
  char finaldecision ;
  int  SleepProzent = this.getSleepProzent();
  boolean sleepneeded = false;
  double  Limit = 0.0;

  if ( SleepProzent != 0 )
  {
    if (this.mRandom.nextInt(100)<SleepProzent)
    {
      sleepneeded = true;
    }
  }

  if ( sleepneeded )
  {
    // create Order with Wish "Sleep"
    finaldecision = 'N';
    finalmenge    = 0;
    Limit         = 0.0;
    return new OrderBasicData( finaldecision, finalmenge, Limit );
  }

  char decisionofmarkt = 'N';

  this.detailedcalculationinfo = "";
  if ( this.mOperatorType == SystemConstant.AgentType_INVESTOR )
  {
             InvestorRuleSuggestionRecord
             decisionrecord = RuleAnalyser.InvestorMarketAnalysewithRule
                              (
                               this.mCurrentInnererWert,
                               this.mUsedAbschlagProzent
                               );

            decisionofmarkt = decisionrecord.mAction;
            this.detailedcalculationinfo = decisionrecord.mSuggestionReason;
           // decide the Limit directly
           // Last Price: der letzte Price in Vector von PriceContainer
           double yesterdayprice = PriceContainer.getLastPrice();
           Limit = RuleAnalyser.makeInvestorLimit( this.mCurrentInnererWert, this.mUsedAbschlagProzent,  decisionofmarkt);

           if ( decisionofmarkt =='B' || decisionofmarkt =='S' )
           {
              Wishexchangemenge = RuleAnalyser.makeInvestorOrderMenge( this.mCurrentInnererWert,this.mUsedAbschlagProzent,decisionofmarkt);
           }
}
else
{
                this.mCurrentAveragePrice = PriceContainer.getMovingAveragePrice(this.mDayofAveragePrice);
                // make dicision at fisrt the Limit directly
                decisionofmarkt =
                RuleAnalyser.NoiseTraderMarketAnalysewithRule( this.mCurrentAveragePrice);

                // calculate the Limit
                Limit = RuleAnalyser.makeNoiseTraderLimit( this.mCurrentAveragePrice, decisionofmarkt, this.mPerformedStatus );

                if ( decisionofmarkt =='B' || decisionofmarkt =='S' )
                {
                   Wishexchangemenge = RuleAnalyser.
                                       makeNoiseTraderOrderMenge(
                                       this.mCurrentInnererWert,
                                       decisionofmarkt,
                                       this.mCurrentAveragePrice);
                }
}

  finalmenge = this.makeFinalMenge( 1, decisionofmarkt, Wishexchangemenge, Limit, mDataCC+1 );
  finaldecision = decisionofmarkt;
  if ( finalmenge == 0 )
  {
      finaldecision ='N';
  }
  return new OrderBasicData( finaldecision, finalmenge, Limit );
}

*/

private void sendFeedBack2Store(char finaldecision, int finalmenge,  double BuyLimit, double SellLimit)
{
        // There are 4 Orders:
        // 1.  'M' Mixed Order: It contains Buy and Sell Wishes)
        // 2.  'N' Nothing, only OK confirmation
        // 3.  'B' Buy
        // 4.  'S' Sell

        MessageWrapper msgwrp = null;
        if ( finaldecision == 'M' )
        {
          msgwrp = MessageFactory.createCashOrder(
                 this.mOperatorType,
                 SystemConstant.WishType_Mixed,
                 finalmenge,
                 BuyLimit,
                 SellLimit,
                 this.mDepotRecord.mAbsoluteGewinnProzent,
                 this.mDepotRecord.mRelativeGewinnProzent,
                 ""
             );
        }
        else
        if ( finaldecision == 'N' )
        {
          msgwrp = MessageFactory.createCashOrder(
                 this.mOperatorType,
                 SystemConstant.WishType_Wait,
                 finalmenge,
                 0,
                 this.mDepotRecord.mAbsoluteGewinnProzent,
                 this.mDepotRecord.mRelativeGewinnProzent,
                 ""
             );
        }
        else
        if ( finaldecision == 'B' )
        {
          msgwrp = MessageFactory.createCashOrder(
                 this.mOperatorType,
                 SystemConstant.WishType_Buy,
                 finalmenge,
                 BuyLimit,
                 this.mDepotRecord.mAbsoluteGewinnProzent,
                 this.mDepotRecord.mRelativeGewinnProzent,
                 ""
             );
        }
        else
        {
          msgwrp = MessageFactory.createCashOrder(
                 this.mOperatorType,
                 SystemConstant.WishType_Sell,
                 finalmenge,
                 SellLimit,
                 this.mDepotRecord.mAbsoluteGewinnProzent,
                 this.mDepotRecord.mRelativeGewinnProzent,
                 ""
             );
        }

        ACLMessage msg= new ACLMessage( ACLMessage.INFORM );
        try
        {
           msg.setContentObject( msgwrp);
        }
        catch (Exception ex)
        {
          ex.printStackTrace();
        }
        msg.addReceiver( new AID( "DAX", false ) );
        // Send the new order to StockStore
        this.myAgent.send(msg);
}

 private void processCashOrder( CashTrade_Order pCashOrder)
 {
   // save this price into PriceContainer
   PriceContainer.saveTraderPrice( this.mDataCC, pCashOrder.mFinalKurs );
   System.out.println( this.myAgent.getLocalName() + ": Order Result" + pCashOrder.getResult() );
   String orderprocessedmsg = "";

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
   this.sendDepotStatus( pCashOrder );
 }

  private int makeFinalMenge(char DecisonOfMarkt, int WishMenge, double Limit, int Day )
  {
     String LocalName = this.myAgent.getLocalName();
     switch ( DecisonOfMarkt )
     {
      case 'B':
          double cash1 = this.mDepotRecord.mMoneyMarket_CurrentCash1;
          if ( cash1 - 1 * Limit < 0.0 )
          {
            System.out.println("****" + Day +"."+ LocalName +" says Buy, no enough CASH1 available, so wait.");
            this.detailedcalculationinfo = this.detailedcalculationinfo + " CASH1 is not enough for 1 CASH2, so wait" ;
            return 0;
          }
          else
          if ( cash1 - WishMenge * Limit > 0.0 )
          {
             System.out.println("****" + Day +"."+ LocalName +" " + SystemConstant.getOperatorTypeName( this.mOperatorType ) +" " + " says Buy " + WishMenge + ", Limit=" + Limit);
             return WishMenge;
          }
          else
          {
             int possiblemenge = (int) (cash1 / Limit * 0.85 ) ;
             System.out.println("****" + Day +"."+ LocalName +" " + SystemConstant.getOperatorTypeName( this.mOperatorType ) +" says Buy " + possiblemenge + ", Limit=" + Limit);
             this.detailedcalculationinfo = this.detailedcalculationinfo + " rule says to buy " + WishMenge + " std, but CASH1 is enough for " + possiblemenge +" stk." ;
             return possiblemenge;
          }

      case 'S':
          int currentmenge = (int) this.mDepotRecord.mMoneyMarket_CurrentCash2 ;
          if ( currentmenge <= 0 )
          {
            System.out.println("****"  + Day +"."+ LocalName+" says Sell, No enough Cash2, so wait.");
            this.detailedcalculationinfo = this.detailedcalculationinfo + " Decision is Sell But no Cash2 available, so wait" ;
            return 0;
          }
          else
          if ( currentmenge - WishMenge >= 0 )
          {
             System.out.println("****" + Day +"."+ LocalName + " "+ SystemConstant.getOperatorTypeName( this.mOperatorType ) +" says Sell "+ WishMenge + ", Limit=" + Limit);
             return  WishMenge;
          }
          else
          {
             System.out.println("****" + Day +"."+ LocalName + " "+ SystemConstant.getOperatorTypeName( this.mOperatorType ) +" says Sell "+ currentmenge + ", Limit=" + Limit);
             this.detailedcalculationinfo = this.detailedcalculationinfo + "rule says to sell " + WishMenge+ "stk, but only " + currentmenge + " CASH2 available." ;
             return currentmenge;
          }
      default:
          System.out.println("****"  + Day +"."+ LocalName  +" says Wait.");
          return 0;
     }
  }

  /* send Gewinndstatus to its ReceiverPartner.
     the partner NameList is contained in the mXPList.

     Wait for the Gewinndstatus from its SenderPartner.
     Pay attention to:
      * Number of SenderPartner = Number of ReceiverPartner
      or
      *  Number of SenderPartner != Number of ReceiverPartner
  */


  private boolean exchangeGewinnStatus()
  {
       String Dayindex ="0000"+ mDataCC;
       // Send its status to its ReceiverPartner
       if ( this.mXPList == null )
       {
           System.out.println("*****" + this.mDataCC +"." + this.myAgent.getLocalName()+":No Receiver Partner !");
       }
       else
       {
           // send status to its reciever partner
           this.sendStatus2Partner();
       }

       // Wait for the Status Message from SenderPartner that send the status to this Agent
       if ( this.mSenderPartnerNumber == 0 )
       {
           String StatusExchangeInfo =
                 Dayindex.substring( Dayindex.length() - this.mDayIndexLength )+";" +
                 this.myAgent.getLocalName()+";"+
                 SystemConstant.getOperatorTypeName( this.mOperatorType ) +";Kein Nachbar sent Status zu mir." ;

           this.mLogger.debug( StatusExchangeInfo );
           this.sendTypeChangeDecisionInfo( StatusExchangeInfo );
       }
       else
       {
         this.mLogger.debug(  this.mDataCC +"." + this.myAgent.getLocalName() + ": will get " + this.mSenderPartnerNumber + " reports from Nachbar");
         ACLMessage Response = null;
         Vector allstatus = new Vector();
         String Sendernamelist ="";

         int k = this.mEarlyFriendStatusBufferFromPartner.size();

         while ( this.mEarlyFriendStatusBufferFromPartner.size() > 0 )
         {
             FriendStatus ff =( FriendStatus ) this.mEarlyFriendStatusBufferFromPartner.remove(0);
             // Fehler-Ursache
             // allstatus.add( Response );
             // richtige command
             allstatus.add(  ff );
             Sendernamelist=Sendernamelist + ff.mName +";";
         }

         while ( k < this.mSenderPartnerNumber )
         {
             ACLMessage aclmsg =  this.myAgent.blockingReceive(2000);
             if ( aclmsg == null)
             {
                System.out.println("*****" + mDataCC +"." + this.myAgent.getLocalName() + " " +( this.mSenderPartnerNumber - k ) +"/" + this.mSenderPartnerNumber +" to wait, already from " +Sendernamelist);
             }
             else
             {
                Sendernamelist = Sendernamelist + aclmsg.getSender().getLocalName()+";";
                MessageWrapper msgwrp  = null;
                try
                {
                   msgwrp = ( MessageWrapper ) aclmsg.getContentObject();

                   if (  msgwrp.mMessageType == SystemConstant.MessageType_InterruptCommand )
                   {
                     this.UnRegisterMe();
                     this.StopAgent();
                     return true;
                   }




                }
                catch (Exception ex)
                {
                   ex.printStackTrace();
                }

                allstatus.add( (FriendStatus) msgwrp.mMessageContent );
                k++;
             }
         }

         System.out.println( "*****" + this.mDataCC +"." + this.myAgent.getLocalName() + ": " + k + " reports are received from " + Sendernamelist);

         ACLMessage msgx = new ACLMessage( ACLMessage.INFORM );

         msgx.addReceiver( new AID ( "DataLogger", false) );

         // Message Format: This message is used to record the history of Status-Exchange
         // XSTATE,Day;........;
         DataFormatter dataform = new DataFormatter( Configurator.mConfData.mDataFormatLanguage  );
         String StatusExhangeHistroy =
               Dayindex.substring( Dayindex.length() - this.mDayIndexLength )+";" +
               this.myAgent.getLocalName()+";"+
               SystemConstant.getOperatorTypeName( this.mOperatorType ) +";" ;

         if ( this.mOperatorType == SystemConstant.AgentType_INVESTOR )
         {
           StatusExhangeHistroy = StatusExhangeHistroy +
                                  dataform.format2str(this.mUsedAbschlagProzent) +";NA;" ;
         }
         else
         {
           StatusExhangeHistroy = StatusExhangeHistroy +
                                  "NA;"+this.mDayofAveragePrice +";";
         }
         StatusExhangeHistroy = StatusExhangeHistroy +
                                this.mDepotRecord.mRelativeGewinn + ";" +
                                dataform.format2str(this.mDepotRecord.mRelativeGewinnProzent) + ";" +
                                this.mDepotRecord.mAbsoluteGewinn + ";" +
                                dataform.format2str(this.mDepotRecord.mAbsoluteGewinnProzent) + ";";

         StatusExhangeHistroy = StatusExhangeHistroy +
                                this.getStringOfPartnerGewinnStatus( allstatus );


         int kommunikationwahrscheinlichkeit = this.mRandom.nextInt( 100 );

         if ( this.mOperatorType ==  this.mOriginalOperatorType )
         {
               // der BestNachbar in diesem Mal aufgrund StatusExchange
               BestOperatorFinder bopfinder = new BestOperatorFinder( allstatus );
               FriendStatus bestop = bopfinder.getBestGewinner();

               // vergleiche GewinnProzent mit meiner Nachbar
               // Wir berücksichtigen nur den Agent:
               // Sein GewinnProzent > Mein GewinnProzent
               if (  bestop.mGewinnProzent > this.mDepotRecord.mRelativeGewinnProzent  )
               {
                     this.mVerlustCounter++;
               }

               if ( kommunikationwahrscheinlichkeit > Configurator.mConfData.mGewinnStatusExchangeProbability )
               {
                     StatusExhangeHistroy = StatusExhangeHistroy + "ProbabilityDecisionNoChange;";
               }
               else
               {
                     StatusExhangeHistroy = StatusExhangeHistroy + "ProbabilityDecisionCanChange;";
                     if ( this.mVerlustCounter >= this.mInitConfig.mLostNumberLimit )
                     {
                        StatusExhangeHistroy = StatusExhangeHistroy + "Schlechter_Counter "+ this.mVerlustCounter + ">= Limit " + this.mInitConfig.mLostNumberLimit;
                        // i should change
                        if ( this.mOperatorType != bestop.mType )
                        {
                             if ( bestop.mType == SystemConstant.AgentType_INVESTOR )
                             {
                               // change my Type to Investor, Keine Beschränkung
                               this.mOperatorType = bestop.mType;
                                // AbschlagProzent braucht neu zu generieren.
                               this.updateNewAbschlagProzent();
                               StatusExhangeHistroy = StatusExhangeHistroy + "Changed to Investor and updated Abschlag";
                               this.mVerlustCounter = 0;
                             }
                             else
                             {
                               // Überprüfe die Abweichung der Preise von InnererWert
                               // InnererWert * ( 1 + X% + Y * AbschlagProzent )
                               double price_top_limit = this.mCurrentInnererWert * ( 1 + Configurator.mConfData.mBaseDeviation4PriceLimit / 100.0 + Configurator.mConfData.mAbschlagFactor * this.mUsedAbschlagProzent / 100.0 ) ;

                               // InnererWert * ( 1 - X% - Y * AbschlagProzent )
                               double price_bottom_limit = this.mCurrentInnererWert * ( 1 - Configurator.mConfData.mBaseDeviation4PriceLimit / 100.0 - Configurator.mConfData.mAbschlagFactor * this.mUsedAbschlagProzent / 100.0 ) ;

                               double lastprice =  PriceContainer.getLastPrice();

                               if ( ( lastprice > price_top_limit ) ||
                                    ( lastprice < price_bottom_limit ) )
                               {
                                  // in diesem Fall, nicht wechseln
                                  StatusExhangeHistroy = StatusExhangeHistroy + "Can not change to NoiseTrader, big preis deviation";
                               }
                               else
                               {
                                   this.mOperatorType = bestop.mType;
                                   this.updateMovingsDay();
                                   this.mLimitHistory.clear();
                                   StatusExhangeHistroy = StatusExhangeHistroy + "Changed to NoiseTrader and updated Movingday cleared HistoryLimit";
                                   this.mVerlustCounter = 0;
                               }
                             }
                        }
                        else
                        {
                           // Best Nachbar hat dieselen Typ wie ich.
                           // So brauche ich nicht zu wechseln .
                          StatusExhangeHistroy = StatusExhangeHistroy + "NoChange;because BestNachbar has same Type.";
                        }


                     }
                     else
                     {
                         // nichts zu tun
                         StatusExhangeHistroy = StatusExhangeHistroy + "NoChange;because Schlechter_Counter(" + this.mVerlustCounter + ")< Limit "+this.mInitConfig.mLostNumberLimit+";";
                     }
                     // immer Reset auf 0
                     this.mVerlustCounter = 0;

               }
               this.sendTypeChangeDecisionInfo( StatusExhangeHistroy );
         }
         else
         {
            // i have changed my type:
           if ( kommunikationwahrscheinlichkeit > Configurator.mConfData.mGewinnStatusExchangeProbability )
           {
                 StatusExhangeHistroy = StatusExhangeHistroy + "ProbabilityDecisionNoChange;";
           }
           else
           {
                 StatusExhangeHistroy = StatusExhangeHistroy + "ProbabilityDecisionCanChange;";
                 if ( this.mOperatorType == SystemConstant.AgentType_INVESTOR )
                 {
                      if (  this.mLimitHistory.size() >= this.mInitConfig.mDays4AverageLimit )
                      {
                             // Has the enough Records for Average calculation
                             // Zur zeit bin ich Investor
                             LimitRecord averagelimit = this.getAverageLimit();
                             double averageprice = PriceContainer.getMovingAveragePrice( this.mInitConfig.mDays4AverageLimit );

                             if ( ( averagelimit.mBuyLimit > averageprice ) ||
                                  ( averagelimit.mSellLimit < averageprice )  )
                             {
                                 // wechsel zurück zum NoiseTrader
                                 this.mOperatorType = SystemConstant.AgentType_NOISETRADER;
                                 this.updateMovingsDay();
                                 this.mVerlustCounter = 0;
                                 this.typechanged = true;

                                 if ( ( averagelimit.mBuyLimit > averageprice ) )
                                 {
                                   StatusExhangeHistroy = StatusExhangeHistroy +"changed to NoiseTrader; AverageBuyLimit "+ averagelimit.mBuyLimit +" > AveragePrice " +
                                                          this.nff.format2str ( averageprice )+ ";"+this.mInitConfig.mLostNumberLimit+";";
                                 }
                                 else
                                 {
                                   StatusExhangeHistroy = StatusExhangeHistroy +"changed to NoiseTrader; AverageSellLimit "+ averagelimit.mSellLimit +" < AveragePrice " +
                                                          nff.format2str( averageprice )+ ";"+this.mInitConfig.mLostNumberLimit+";";
                                 }
                                 StatusExhangeHistroy  = StatusExhangeHistroy  + " and cleared LimitHistory ";
                                 this.mLimitHistory.clear();
                             }
                             else
                             {
                                 // Mit Rückkehr-Regel nicht möglich
                                 StatusExhangeHistroy = StatusExhangeHistroy +"Mit Rückkehr-Regel, NoChange; AverageBuyLimit=" +
                                                        nff.format2str( averagelimit.mBuyLimit) +" Averageprice="+
                                                        nff.format2str(averageprice)+" AverageSellLimit="+
                                                        this.nff.format2str( averagelimit.mSellLimit )+";";

                                 BestOperatorFinder bopfinder = new BestOperatorFinder( allstatus );
                                 FriendStatus bestop = bopfinder.getBestGewinner();

                                 if (  (bestop.mType == SystemConstant.AgentType_NOISETRADER ) &&
                                       (bestop.mGewinnProzent > this.mDepotRecord.mRelativeGewinnProzent ) )
                                 {
                                   this.mVerlustCounter = this.mVerlustCounter + 1;
                                   if ( this.mVerlustCounter >= this.mInitConfig.mLostNumberLimit )
                                   {
                                       StatusExhangeHistroy = StatusExhangeHistroy+ " NoiseTrader Nachbar hat höher GewinnProzent so wechseln zu NoiseTrader";
                                       this.mOperatorType = SystemConstant.AgentType_NOISETRADER;
                                       this.updateMovingsDay();
                                       this.mVerlustCounter = 0;
                                       this.typechanged = true;
                                   }
                                 }
                                 else
                                 {
                                   // kann nicht wechseln.
                                   StatusExhangeHistroy = StatusExhangeHistroy+ " Hinwechsel-Regel verwendet, aber Kein Wechsel weil  Schlechter_Counter " + this.mVerlustCounter + " < Schlechter_Limit" + this.mInitConfig.mLostNumberLimit;
                                 }
                             }
                      }
                      else
                      {
                        StatusExhangeHistroy = StatusExhangeHistroy +"NoChange; There are only " + this.mLimitHistory.size()+ " Historydata, " + this.mInitConfig.mDays4AverageLimit + " records are required.";
                      }
                 }
                 else
                 {
                   // Zur zeit bin ich NoiseTrader

                   // InnererWert * ( 1 + X% + Y * AbschlagProzent )
                   double price_top_limit = this.mCurrentInnererWert * ( 1 + Configurator.mConfData.mBaseDeviation4PriceLimit / 100.0 + Configurator.mConfData.mAbschlagFactor * this.mUsedAbschlagProzent / 100.0 ) ;

                   // InnererWert * ( 1 - X% - Y * AbschlagProzent )
                   double price_bottom_limit = this.mCurrentInnererWert * ( 1 - Configurator.mConfData.mBaseDeviation4PriceLimit / 100.0 - Configurator.mConfData.mAbschlagFactor * this.mUsedAbschlagProzent / 100.0 ) ;

                   double lastprice =  PriceContainer.getLastPrice();

                   if ( ( lastprice > price_top_limit ) ||
                        ( lastprice < price_bottom_limit ) )
                   {
                       // wechsel zum Investor
                       this.mOperatorType = this.mOriginalOperatorType;
                       this.updateNewAbschlagProzent();
                       this.mVerlustCounter = 0;
                       this.typechanged = true;

                       if ( lastprice > price_top_limit )
                       {
                         StatusExhangeHistroy = StatusExhangeHistroy +"changedTo investor; lastprice" + lastprice +
                                                ">AktuellerPrice + X% + Y*Abschag" + this.nff.format2str(price_top_limit);
                       }
                       else
                       {
                         StatusExhangeHistroy = StatusExhangeHistroy +"changedTo investor; lastprice" + lastprice +
                                                "<AktuellerPrice + X% + Y*Abschag" + this.nff.format2str( price_bottom_limit ) ;
                       }
                   }
                   else
                   {
                       StatusExhangeHistroy = StatusExhangeHistroy +"NoChange unter Rückwechsel-Regel; because PriceTopLimit " +
                                              this.nff.format2str(price_top_limit)+" > LastPirce "+
                                              this.nff.format2str( lastprice ) +" > PriceBottomLimit("+
                                              this.nff.format2str(price_bottom_limit)+";";

                       BestOperatorFinder bopfinder = new BestOperatorFinder( allstatus );
                       FriendStatus bestop = bopfinder.getBestGewinner();

                       if (  (bestop.mType == SystemConstant.AgentType_INVESTOR ) &&
                             (bestop.mGewinnProzent > this.mDepotRecord.mRelativeGewinnProzent ) )
                       {
                         this.mVerlustCounter = this.mVerlustCounter + 1;
                         if ( this.mVerlustCounter >= this.mInitConfig.mLostNumberLimit )
                         {
                             StatusExhangeHistroy = StatusExhangeHistroy+ " Investor Nachbar hat höher GewinnProzent so wechseln zu Investor";
                             this.mOperatorType = SystemConstant.AgentType_INVESTOR;
                             this.updateNewAbschlagProzent();
                             this.mVerlustCounter = 0;
                             this.typechanged = true;
                         }
                       }
                       else
                       {
                         // kann nicht wechseln.
                         StatusExhangeHistroy = StatusExhangeHistroy+ " Hinwechsel-Regel verwendet, aber Kein Wechsel weil  Schlechter_Counter " + this.mVerlustCounter + " < Schlechter_Limit" + this.mInitConfig.mLostNumberLimit;
                       }
                   }
              }
          }
          this.sendTypeChangeDecisionInfo( StatusExhangeHistroy );
       }
    }
    return false;

  }


/*

  private void exchangeGewinnStatus()
  {
      String Dayindex ="0000"+ mDataCC;
       // Send its status to its ReceiverPartner
       if ( this.mXPList == null )
       {
           System.out.println("*****" + this.mDataCC +"." + this.myAgent.getLocalName()+":No Receiver Partner !");
       }
       else
       {
           // send status to its reciever partner
           this.sendStatus2Partner();
       }

       // Wait for the Status Message from SenderPartner that send the status to this Agent
       if ( this.mSenderPartnerNumber == 0 )
       {
           String StatusExchangeInfo =
                 Dayindex.substring( Dayindex.length() - this.mDayIndexLength )+";" +
                 this.myAgent.getLocalName()+";"+
                 SystemConstant.getOperatorTypeName( this.mOperatorType ) +";Kein Nachbar sent Status zu mir." ;

           this.mLogger.debug( StatusExchangeInfo );
           this.sendTypeChangeDecisionInfo( StatusExchangeInfo );
       }
       else
       {
         this.mLogger.debug(  this.mDataCC +"." + this.myAgent.getLocalName() + ": will get " + this.mSenderPartnerNumber + " reports from Nachbar");
         ACLMessage Response = null;

         Vector allstatus = new Vector();
         String Sendernamelist ="";

         int k = this.mEarlyFriendStatusBufferFromPartner.size();

         while ( this.mEarlyFriendStatusBufferFromPartner.size() > 0 )
         {
             FriendStatus ff =( FriendStatus ) this.mEarlyFriendStatusBufferFromPartner.remove(0);
             // Fehler-Ursache
             // allstatus.add( Response );
             // richtige command
             allstatus.add(  ff );
             Sendernamelist=Sendernamelist + ff.mName +";";
         }

         while ( k < this.mSenderPartnerNumber )
         {
             ACLMessage aclmsg =  this.myAgent.blockingReceive(2000);
             if ( aclmsg == null)
             {
                System.out.println("*****" + mDataCC +"." + this.myAgent.getLocalName() + " " +( this.mSenderPartnerNumber - k ) +"/" + this.mSenderPartnerNumber +" to wait, already from " +Sendernamelist);
             }
             else
             {
                Sendernamelist = Sendernamelist + aclmsg.getSender().getLocalName()+";";
                MessageWrapper msgwrp  = null;
                try
                {
                   msgwrp = ( MessageWrapper ) aclmsg.getContentObject();
                }
                catch (Exception ex)
                {
                   ex.printStackTrace();
                }

                allstatus.add( (FriendStatus) msgwrp.mMessageContent );
                k++;
             }
         }

         System.out.println( "*****" + this.mDataCC +"." + this.myAgent.getLocalName() + ": " + k + " reports are received from " + Sendernamelist);

         ACLMessage msgx = new ACLMessage( ACLMessage.INFORM );
         msgx.addReceiver( new AID ( "DataLogger", false) );

         // Message Format: This message is used to record the history of Status-Exchange
         // XSTATE,Day;........;
         DataFormatter dataform = new DataFormatter("Germany");
         String StatusExhangeHistroy =
               Dayindex.substring( Dayindex.length() - this.mDayIndexLength )+";" +
               this.myAgent.getLocalName()+";"+
               SystemConstant.getOperatorTypeName( this.mOperatorType ) +";" ;
         if ( this.mOperatorType == SystemConstant.AgentType_INVESTOR )
         {
           StatusExhangeHistroy = StatusExhangeHistroy +
                                  dataform.format2str(this.mUsedAbschlagProzent) +";NA;" ;
         }
         else
         {
           StatusExhangeHistroy = StatusExhangeHistroy +
                                  "NA;"+this.mDayofAveragePrice +";";
         }
         StatusExhangeHistroy = StatusExhangeHistroy +
                                this.mDepotRecord.mRelativeGewinn + ";" +
                                dataform.format2str(this.mDepotRecord.mRelativeGewinnProzent) + ";" +
                                this.mDepotRecord.mAbsoluteGewinn + ";" +
                                dataform.format2str(this.mDepotRecord.mAbsoluteGewinnProzent) + ";";

         StatusExhangeHistroy = StatusExhangeHistroy +
                                this.getStringOfPartnerGewinnStatus( allstatus );


         int kommunikationwahrscheinlichkeit = this.mRandom.nextInt( 100 );

         if ( kommunikationwahrscheinlichkeit <= Configurator.mConfData.mStatusExchangeProbabiliy )
         {
            // check if the StrategieWechsel ist notwendig !!
            // continue the procedure
           StatusExhangeHistroy = StatusExhangeHistroy + "ProbabilityDecisionCanChange;";
         }
         else
         {
           StatusExhangeHistroy = StatusExhangeHistroy + "ProbabilityDecisionNoChange;";
           this.sendTypeChangeDecisionInfo( StatusExhangeHistroy );
            //only check if i have Lost
           if ( this.mDepotRecord.mRelativeGewinn < 0 )
           {
              this.mVerlustCounter++;
           }
           return;
         }

         if ( this.mDepotRecord.mRelativeGewinn > 0)
         {
                // Once Gewinn, VerlustCounter is cleared to 0
                this.mVerlustCounter = 0;

                // der BestNachbar in diesem Mal aufgrund StatusExchange
                BestOperatorFinder bopfinder = new BestOperatorFinder( allstatus );
                FriendStatus bestop = bopfinder.getBestGewinner();

                // compare the GewinnProzent mit meine GewinnProzent
                // Wir berücksichtigen nur den Agent:
                // Sein GewinnProzent > Mein GewinnProzent
                if (  bestop.mGewinnProzent > this.mDepotRecord.mRelativeGewinnProzent  )
                {
                      // Prüfe: Ob ich einen BestNachbar schon gehabt habe.
                      if ( this.mBestNachbarofLastExchange == null )
                      {
                         // Noch nicht,dann mache nur eine Bemerkung
                         this.mBestNachbarofLastExchange = bestop;
                      }
                      else
                      {
                        // Schon gehabt:
                        // Prüfe: Ob er der BestNachbar von LetztMal ist.
                        // nämlich :
                        // ob er dieselb Nachbar von letzter mal ist.
                        if ( this.mBestNachbarofLastExchange.mName.equals( bestop.mName ) )
                        {
                              if ( this.mOperatorType != bestop.mType )
                              {
                                    StatusExhangeHistroy = StatusExhangeHistroy + "TypeChanged from " + this.mOperatorType + " to " + bestop.mType;
                                    // System.out.println( "I change myself from " + this.mOperatorType + ":" + " to " + bestop.mType );
                                    this.mOperatorType =  bestop.mType;
                                    this.typechanged = true;
                                    if ( bestop.mType == SystemConstant.AgentType_INVESTOR )
                                    {
                                      // create a new AbschlagProzent
                                      this.updateNewAbschlagProzent();
                                    }
                                    else
                                    {
                                       // create a new MovingsDay
                                       this.updateMovingsDay();
                                    }
                                    // Alte Theorie: Nimmt Parameter aus bester Nachbar
                                    //
                                    //this.mUsedAbschlagProzent = bestop.mAbschlagProzent;
                                    //this.mDayofAveragePrice   = bestop.mDayofAveragePrice;
                              }
                              else
                              {
                                 // nicht zu wechseln
                              }
                        }
                        else
                        {
                          // mache nur eine Bemerkung von BestNachbarAgent
                          this.mBestNachbarofLastExchange = bestop;
                          StatusExhangeHistroy = StatusExhangeHistroy + "Make Remark of "+bestop.mName+" " + bestop.mType+";";
                        }
                      }

                }
                else
                {
                      this.mBestNachbarofLastExchange = null;
                      // Es gibt keine NachbarAgent, die besser als ich ist und ich bemerke keine Agent
                      // So ist diese vaiable auf null gesetzt.
                      StatusExhangeHistroy = StatusExhangeHistroy + "NoChange;Because_I_AM_Better;";
                }
                this.sendTypeChangeDecisionInfo( StatusExhangeHistroy );
         }
         else
         if ( this.mDepotRecord.mRelativeGewinn < 0 )
         {
            this.mVerlustCounter++;
            // Zuerst prüfen:ob ich schon meine Verlust-Grenze erreicht habe.
            if ( this.mVerlustCounter < this.mInitConfig.mLostNumberLimit )
            {
               // nichts zu tun
              StatusExhangeHistroy = StatusExhangeHistroy + "NoChange;because VerlustCounter(" + this.mVerlustCounter + ")<"+this.mInitConfig.mLostNumberLimit+";";
            }
            else
            {
                  // die Verlust-Grenze schon erreicht
                  // prüfe ob ich mein AgentTyp schon gewandet habe:

                  if ( this.mOperatorType == this.mOriginalOperatorType )
                  {
                        // noch nicht gewandet
                        // dann ändere meine Regel zur Regeln von BestNachbar,
                        // und er muss besser als ich sein.
                        BestOperatorFinder bopfinder = new BestOperatorFinder( allstatus );
                        FriendStatus bestop = bopfinder.getBestGewinner();

                        // prüfe ob er besser als ich ist
                        if ( bestop.mGewinnProzent > this.mDepotRecord.mRelativeGewinnProzent )
                        {
                              //wechsel zu best Nachbar
                              // endlich ändere ich meine Regel zu seiner original
                              // Wichtig: reset Counter
                              this.mVerlustCounter=0;

                              //this.mUsedAbschlagProzent     =  bestop.mAbschlagProzent;
                              //this.mDayofAveragePrice       =  bestop.mDayofAveragePrice;

                              this.mOperatorType =  bestop.mType;
                              // create a new AbschlagProzent
                              this.updateNewAbschlagProzent();
                              // create a new MovingsDay
                              this.updateMovingsDay();

                              this.typechanged = true;
                              StatusExhangeHistroy = StatusExhangeHistroy +"VerlustCounter(" + this.mVerlustCounter + ")>="+this.mInitConfig.mLostNumberLimit+";TypeChangeTo;"+bestop.mType+";";
                        }
                        else
                        {
                           // nichts machen, weil ich noch besser als andere bin
                           // !!!!!!!!!!!!!!!!!!!!!
                           // VerlustCounter darf nicht auf 0 zurückgesetzt werden !!!
                          StatusExhangeHistroy = StatusExhangeHistroy +"VerlustCounter(" + this.mVerlustCounter + ")>="+this.mInitConfig.mLostNumberLimit+";ButNoChange_I_am_better;";
                        }
                  }
                  else
                  {
                        // ich habe meien  Typ schon geändert,
                        // trotzdem mache ich immer noch Verlust,

                         if ( this.mOperatorType == SystemConstant.AgentType_INVESTOR )
                         {
                               // Zur zeit bin ich Investor
                               LimitRecord averagelimit = this.getAverageLimit();
                               double averageprice = PriceContainer.getMovingAveragePrice( this.mInitConfig.mDays4AverageLimit );
                               if ( ( averagelimit.mBuyLimit > averageprice ) ||
                                    ( averagelimit.mSellLimit < averageprice )  )
                               {
                                   // wechsel zurück zum NoiseTrader
                                   this.mOperatorType = this.mInitConfig.mInitAgentType;
                                   this.mVerlustCounter = 0;
                                   this.typechanged = true;
                                   // create a new AbschlagProzent
                                   this.updateNewAbschlagProzent();
                                   // create a new MovingsDay
                                   this.updateMovingsDay();

                                   if ( ( averagelimit.mBuyLimit > averageprice ) )
                                   {
                                     StatusExhangeHistroy = StatusExhangeHistroy +"VerlustCounter(" + this.mVerlustCounter + ")>="+this.mInitConfig.mLostNumberLimit+";ChangeToNoiseTrader;Because AverageBuyLimit("+ HelpTool.DoubleTransfer( averagelimit.mBuyLimit, 4) +") > averageprice("+HelpTool.DoubleTransfer(averageprice,4)+");";
                                   }
                                   else
                                   {
                                     StatusExhangeHistroy = StatusExhangeHistroy +"VerlustCounter(" + this.mVerlustCounter + ")>="+this.mInitConfig.mLostNumberLimit+";ChangeToNoiseTrader;Because AverageSellLimit("+ HelpTool.DoubleTransfer( averagelimit.mSellLimit, 4) +") < averageprice("+HelpTool.DoubleTransfer(averageprice,4)+");";
                                   }
                               }
                               else
                               {
                                  // bleibt
                                  // not clear VerlustCounter
                                 StatusExhangeHistroy = StatusExhangeHistroy +"VerlustCounter(" + this.mVerlustCounter + ")>="+this.mInitConfig.mLostNumberLimit+";NoChange;Because AverageSellLimit("+ HelpTool.DoubleTransfer( averagelimit.mSellLimit, 4) +") > Averageprice("+HelpTool.DoubleTransfer(averageprice,4)+")>AverageBuyLimit("+ HelpTool.DoubleTransfer( averagelimit.mBuyLimit, 4)+");";
                               }
                         }
                         else
                         {
                              // Zur zeit bin ich NoiseTrader

                              // InnererWert * ( 1 + X% + Y * AbschlagProzent )
                              double price_top_limit = this.mCurrentInnererWert * ( 1 + Configurator.mConfData.mBaseDeviation4PriceLimit / 100.0 + Configurator.mConfData.mAbschlagFactor * this.mUsedAbschlagProzent / 100.0 ) ;

                              // InnererWert * ( 1 - X% - Y * AbschlagProzent )
                              double price_bottom_limit = this.mCurrentInnererWert * ( 1 - Configurator.mConfData.mBaseDeviation4PriceLimit / 100.0 - Configurator.mConfData.mAbschlagFactor * this.mUsedAbschlagProzent / 100.0 ) ;

                              double lastprice =  PriceContainer.getLastPrice();
                              if ( ( lastprice > price_top_limit ) ||
                                   ( lastprice < price_bottom_limit ) )
                              {
                                  // wechsel zum Investor
                                  this.mOperatorType = this.mInitConfig.mInitAgentType;
                                  this.mVerlustCounter = 0;
                                  // create a new AbschlagProzent
                                  this.updateNewAbschlagProzent();
                                  // create a new MovingsDay
                                  this.updateMovingsDay();

                                  this.typechanged = true;
                                  if ( lastprice > price_top_limit )
                                  {
                                    StatusExhangeHistroy = StatusExhangeHistroy +"VerlustCounter(" + this.mVerlustCounter + ")>="+this.mInitConfig.mLostNumberLimit+";ChangeToInvestor;Because LastPirce("+ HelpTool.DoubleTransfer( lastprice, 1) +") > PriceTopLimit("+HelpTool.DoubleTransfer(price_top_limit,1)+");";
                                  }
                                  else
                                  {
                                    StatusExhangeHistroy = StatusExhangeHistroy +"VerlustCounter(" + this.mVerlustCounter + ")>="+this.mInitConfig.mLostNumberLimit+";ChangeToInvestor;Because LastPirce("+ HelpTool.DoubleTransfer( lastprice, 1) +") < PriceBottomLimit("+HelpTool.DoubleTransfer(price_bottom_limit,1)+");";
                                  }
                              }
                              else
                              {
                                  StatusExhangeHistroy = StatusExhangeHistroy +"VerlustCounter(" + this.mVerlustCounter + ")>="+this.mInitConfig.mLostNumberLimit+";ButNoChange; because PriceTopLimit(" + HelpTool.DoubleTransfer(price_top_limit,1)+") > LastPirce("+ HelpTool.DoubleTransfer( lastprice, 1) +") > PriceBottomLimit("+HelpTool.DoubleTransfer(price_bottom_limit,1)+");";
                              }
                         }
                  }
              }
              this.sendTypeChangeDecisionInfo( StatusExhangeHistroy );
          }
          else
          {
             // GewinnProzent == 0
             // nichts machen
            StatusExhangeHistroy = StatusExhangeHistroy  +";NoChanges;Because Profit=0";
            this.sendTypeChangeDecisionInfo( StatusExhangeHistroy );
          }
       }
  }


*/

/*
private void exchangeGewinnStatus()
{
     // Send its status to its ReceiverPartner
     if ( this.mXPList == null )
     {
         System.out.println("*****" + this.mDataCC +"." + this.myAgent.getLocalName()+":No Receiver Partner !");
     }
     else
     {
         // send status to its reciever partner
         this.sendStatus2Partner();
     }

     // Wait for the Status Message from SenderPartner that send the status to this Agent
     if ( this.mSenderPartnerNumber == 0 )
     {
         System.out.println( "*****" + this.mDataCC +"." + this.myAgent.getLocalName()+":No status to wait Senderpartner=0" );
     }
     else
     {
       System.out.println( "*****" + this.mDataCC +"." + this.myAgent.getLocalName() + ": Try to get " + this.mSenderPartnerNumber + " reports from senderPartner");
       ACLMessage Response = null;

       Vector allstatus = new Vector();
       String Sendernamelist ="";

       int k = this.mEarlyFriendStatusBufferFromPartner.size();

       while ( this.mEarlyFriendStatusBufferFromPartner.size() > 0 )
       {
           FriendStatus ff =( FriendStatus ) this.mEarlyFriendStatusBufferFromPartner.remove(0);
           // Fehler-Ursache
           // allstatus.add( Response );
           // richtige command
           allstatus.add(  ff );
           Sendernamelist=Sendernamelist + ff.mName +";";
       }

       while ( k < this.mSenderPartnerNumber )
       {
           ACLMessage aclmsg =  this.myAgent.blockingReceive(2000);
           if ( aclmsg == null)
           {
              System.out.println("*****" + mDataCC +"." + this.myAgent.getLocalName() + " " +( this.mSenderPartnerNumber - k ) +"/" + this.mSenderPartnerNumber +" to wait, already from " +Sendernamelist);
           }
           else
           {
              Sendernamelist = Sendernamelist + aclmsg.getSender().getLocalName()+";";
              MessageWrapper msgwrp  = null;
              try
              {
                 msgwrp = ( MessageWrapper ) aclmsg.getContentObject();
              }
              catch (Exception ex)
              {
                 ex.printStackTrace();
              }
              allstatus.add( (FriendStatus) msgwrp.mMessageContent );
              k++;
           }
       }

       System.out.println( "*****" + this.mDataCC +"." + this.myAgent.getLocalName() + ": " + k + " reports are received from " + Sendernamelist);

       ACLMessage msgx = new ACLMessage( ACLMessage.INFORM );

       msgx.addReceiver( new AID ( "DataLogger", false) );
       String Dayindex ="0000"+ mDataCC;

       // Message Format: This message is used to record the history of Status-Exchange
       // XSTATE,Day;........;
       DataFormatter dataform = new DataFormatter("Germany");
       String StatusExhangeHistroy =
             Dayindex.substring( Dayindex.length() - this.mDayIndexLength )+";" +
             this.myAgent.getLocalName()+";"+
             SystemConstant.getOperatorTypeName( this.mOperatorType ) +";" ;

           if ( this.mOperatorType == SystemConstant.AgentType_INVESTOR )
           {
             StatusExhangeHistroy = StatusExhangeHistroy +
                                    dataform.format2str(this.mUsedAbschlagProzent) +";NA;" ;
           }
           else
           {
             StatusExhangeHistroy = StatusExhangeHistroy +
                                    "NA;"+this.mDayofAveragePrice +";";
           }

           StatusExhangeHistroy = StatusExhangeHistroy +
             this.mDepotRecord.mRelativeGewinn + ";" +
             dataform.format2str(this.mDepotRecord.mRelativeGewinnProzent) + ";" +
             this.mDepotRecord.mAbsoluteGewinn + ";" +
             dataform.format2str(this.mDepotRecord.mAbsoluteGewinnProzent) + ";";

       StatusExhangeHistroy = StatusExhangeHistroy +
                              this.getStringOfPartnerGewinnStatus(allstatus);

       MessageWrapper msgwrp = new MessageWrapper();
       msgwrp.mMessageType = SystemConstant.MessageType_XState ;
       msgwrp.mMessageContent = StatusExhangeHistroy;
       try
       {
           msgx.setContentObject( msgwrp );
       }
       catch (Exception ex)
       {
         ex.printStackTrace();
       }

       myAgent.send( msgx );

       // 2005-04-23:
       //  Verlust wird nur an diesem Tag geprüft und gezählt.
       //
       if ( this.mDepotRecord.mRelativeGewinn > 0)
       {
              // Once Gewinn, VerlustCounter is cleared to 0
              this.mVerlustCounter = 0;

              // der BestNachbar in dies Mal nach StatusExchange
              BestOperatorFinder bopfinder = new BestOperatorFinder( allstatus );
              FriendStatus bestop = bopfinder.getBestGewinner();

              // compare the GewinnProzent mit meine GewinnProzent
              // Wir berücksichtigen nur den Agent:
              // Sein GewinnProzent > Mein GewinnProzent
              if (  bestop.mGewinnProzent > this.mDepotRecord.mRelativeGewinnProzent  )
              {
                    // Prüfe: Ob ich einen BestNachbar schon gehabt habe.
                    if ( this.mBestNachbarofLastExchange == null )
                    {
                       // Noch nicht,dann mache nur eine Bemerkung
                       this.mBestNachbarofLastExchange = bestop;
                    }
                    else
                    {
                      // Schon gehabt:
                      // Prüfe: Ob er der BestNachbar von LetztMal ist.
                      // nämlich :
                      // ob er dieselb Nachbar von letzter mal ist.
                      if ( this.mBestNachbarofLastExchange.mName.equals( bestop.mName ) )
                      {
                                // Ja, wechsel meine Regel zu seine Regel.
                                System.out.println( "I change myself from " +
                                this.mOperatorType + " to " +  bestop.mType );
                                this.mUsedAbschlagProzent     =  bestop.mAbschlagProzent;
                                this.mDayofAveragePrice       = bestop.mDayofAveragePrice;
                                this.mOperatorType =  bestop.mType;
                                this.typechanged = true;
                      }
                      else
                      {
                        // mache nur eine Bemerkung von BestNachbarAgent
                        this.mBestNachbarofLastExchange = bestop;
                      }
                    }
              }
              else
              {
                    this.mBestNachbarofLastExchange = null;
                    // Es gibt keine NachbarAgent, die besser als ich ist und ich bemerke keine Agent
                    // So ist diese vaiable auf null gesetzt.
              }
       }
       else
       if ( this.mDepotRecord.mRelativeGewinn < 0 )
       {
          this.mVerlustCounter++;

          // Zuerst prüfen:ob ich schon meine VerlustGrenze erreicht habe.
          if ( this.mVerlustCounter < this.mInitConfig.mLostNumberLimit )
          {
             // nichts machen
          }
          else
          {
                // schon Nacheinaandere N (zum Beispiel 2 ) mal Verlust
                // prüfe ob ich noch nicht mein Typ zum anderen gewandet habe:

                if ( this.mOperatorType == this.mOriginalOperatorType )
                {
                      // noch nicht gewandet
                      // dann ändere meine Regel zur Regeln von BestNachbar,
                      // und er muss besser als ich sein.
                      BestOperatorFinder bopfinder = new BestOperatorFinder( allstatus );
                      FriendStatus bestop = bopfinder.getBestGewinner();

                      // prüfe ob er besser als ich ist
                      if ( bestop.mGewinnProzent > this.mDepotRecord.mRelativeGewinnProzent )
                      {
                          // endlich ändere ich meine Regel zu seiner original
                          // Wichtig: Counter reset
                          this.mVerlustCounter=0;
                          System.out.println( "I change myself from " +
                              this.mOperatorType +  " to " + bestop.mType  );
                          this.mOperatorType =  bestop.mType;
                          this.typechanged = true;
                      }
                      else
                      {
                         // nichts machen, weil ich noch besser als andere bin
                         // !!!!!!!!!!!!!!!!!!!!!
                         // VerlustCounter darf nicht auf 0 gesetzt werden !!!
                      }
                }
                else
                {
                      // ich habe meien Regel schon geändert,
                      // trotzdem mache ich immer noch verlust,
                  if ( this.mOperatorType == SystemConstant.AgentType_INVESTOR )
                  {
                        // Zur zeit bin ich Investor
                        LimitRecord averagelimit = this.getAverageLimit();
                        double averageprice = PriceContainer.getMovingAveragePrice( this.mInitConfig.mDays4AverageLimit );
                        if ( ( averagelimit.mBuyLimit > averageprice ) ||
                             ( averagelimit.mSellLimit < averageprice )  )
                        {
                            // wechsel zurück zum NoiseTrader
                            this.mOperatorType = this.mInitConfig.mInitAgentType;
                            this.mVerlustCounter = 0;
                            System.out.println( "I change myself to my original type NoiseTrader " );
                            this.typechanged = true;
                        }
                        else
                        {
                           // bleibt
                           // not clear VerlustCounter
                        }
                  }
                  else
                  {
                       // Zur zeit bin ich NoiseTrader


                       // InnererWert * ( 1 + X% + Y * AbschlagProzent )
                       double price_up_limit = this.mCurrentInnererWert * ( 1 + Configurator.mConfData.mBaseDeviation4PriceLimit / 100.0 + Configurator.mConfData.mAbschlagFactor * this.mUsedAbschlagProzent / 100.0 ) ;

                       // InnererWert * ( 1 - X% - Y * AbschlagProzent )
                       double price_down_limit = this.mCurrentInnererWert * ( 1 - Configurator.mConfData.mBaseDeviation4PriceLimit / 100.0 - Configurator.mConfData.mAbschlagFactor * this.mUsedAbschlagProzent / 100.0 ) ;
                       if ( ( PriceContainer.getLastPrice() > price_up_limit ) ||
                            ( PriceContainer.getLastPrice() < price_down_limit ) )
                       {
                           // wechsel zum Investor
                           this.mOperatorType = this.mInitConfig.mInitAgentType;
                           this.mVerlustCounter = 0;
                           System.out.println( "I change myself to my original type Investor " );
                           this.typechanged = true;
                       }
                       else
                       {
                           // bleibt
                       }
                  }
                }
            }
        }
        else
        {
           // GewinnProzent == 0
           // nichts machen
        }
     }
     ////////////////////////////////////////////////////

}

*/

private void sendStatus2Partner()
{
        MessageWrapper tt =
            MessageFactory.createFriendStatus
                           (this.myAgent.getLocalName(),
                            this.mOperatorType,
                            this.mUsedAbschlagProzent,
                            this.mDayofAveragePrice,
                            this.mDepotRecord.mRelativeGewinn,
                            this.mDepotRecord.mRelativeGewinnProzent,
                            this.mOriginalOperatorType
                            );
        FriendStatus exactstate = (FriendStatus) tt.mMessageContent;

        ACLMessage statusmsg = new ACLMessage( ACLMessage.INFORM );
        try
        {
           statusmsg.setContentObject( tt  );
        }
        catch (Exception ex)
        {
          ex.printStackTrace();
        }
        String ss = "";
        for ( int i=0; i<this.mXPList.size(); i++)
        {
            AID oneaid = (AID) this.mXPList.elementAt(i);
            ss = ss + oneaid.getLocalName()+",";
            statusmsg.addReceiver( oneaid );
        }
        myAgent.send( statusmsg  );
        System.out.println( "*****" + this.mDataCC +"." + this.myAgent.getLocalName()+ ": sended Status to " + ss + ": Type=" + exactstate.mType);
}

private void sendTypeChangeDecisionInfo(String  pTypeChangeDecisionInfo)
{
  ACLMessage  msgx = new ACLMessage( ACLMessage.INFORM );
  MessageWrapper msgwrp = new MessageWrapper();
  msgwrp.mMessageType = SystemConstant.MessageType_XState ;
  msgwrp.mMessageContent = pTypeChangeDecisionInfo;
  try
  {
      msgx.setContentObject( msgwrp );
      msgx.addReceiver( new AID ( "DataLogger", false) );
  }
  catch (Exception ex)
  {
      ex.printStackTrace();
  }
  this.mLogger.debug( pTypeChangeDecisionInfo );
  myAgent.send( msgx );
}


 private void  makeInitWork()
 {
        // step 1
        this.Registerme();
        // step 2:
        ACLMessage aclmsg = this.myAgent.blockingReceive();

        MessageWrapper msgwrp =  null;
        try
        {
            msgwrp = ( MessageWrapper ) aclmsg.getContentObject();
        }
        catch (Exception ex)
        {

        }

        // MultiInnererWert
        First300InitData first300data = ( First300InitData ) msgwrp.mMessageContent;
        this.processFirst300InitData( first300data );

        // The price of No. 300 price data
        double lastprice = PriceContainer.getLastPrice() ;
        this.mCurrentInnererWert = first300data.mDoubleData[ first300data.mDoubleData.length -1 ];

        this.mDepotRecord.MoneyMarket_setInitKurs( lastprice );
        // A init status, but only lastprice is useful.
        this.mPerformedStatus = new OrderPerformedStatus('N',lastprice, 'W', 0, lastprice );
        // Update Init Depot using the lastprice
        // prepare 1. Order
        CashOrderBasicData orderbasic = this.prepareOrder();
        // Send 1. Order to StockStore
        sendFeedBack2Store( orderbasic.finaldecision, orderbasic.finalmenge, orderbasic.BuyLimit, orderbasic.SellLimit);
 }

private String FriendstatusFormatter(String ss)
{
  String str= ss;
  str = str.replace(',', ';');
  str = str.replace('.', ',');
  return  str;

}

}
