/**
 * Überschrift:
 * Beschreibung:
 *
 *  Redesigned this class
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
//import de.marketsim.stockdata.*;
import de.marketsim.util.*;
import de.marketsim.config.*;
import de.marketsim.agent.trader.RuleAnalyser;

public class OperatorBehaviorAktienMarket extends OperatorBaseBehavior
{

  boolean  mTraceOrder = false;

  BestOperatorFinder   bopfinder  = null;
  FriendStatus         mBestNachbarofLastExchange = null;
  boolean firstRequest        = true;
  int PriceHistoryNumber      = 0;

  char Reaction               = ' ';

  OrderPerformedStatus    mPerformedStatus = null;

  int  mSchlechterCounter = 0;

  //private int mTypWechselStrategie = Configurator.mConfData.mTypWechselStrategie_Nachverlust;

  String mBestPartnerType ="";
  String mBestPartnerRule ="";

  Vector  mEarlyFriendStatusBufferFromPartner = new Vector();

  public OperatorBehaviorAktienMarket(Agent  pAgent)
  {
     super(pAgent);
     int randomseed = HelpTool.getCharIntSumme( this.myAgent.getLocalName() );
     this.mRandom = new Random();

     String ss = System.getProperty("TRACEORDERENABLED","false");
     if (  ss.equalsIgnoreCase( "true")  )
     {
       mTraceOrder = true;
     }

  }

  // main process flow
  // this action Method will be automatical called from jader.Agent.setup().
  public void action()
  {
        if ( firstRequest )
        {
             this.makeInitWork();
             firstRequest = false;
             return;
        }

         boolean AllMessageFromStockStoreReady = false;
         boolean SimulationIsBroken = false;

         while ( ( !AllMessageFromStockStoreReady ) && ( ! SimulationIsBroken) )
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
                     }

                     if (  msgwrp.mMessageType == SystemConstant.MessageType_InterruptCommand )
                     {
                       SimulationIsBroken = true;
                       continue;
                     }
                     else
                    if ( msgwrp.mMessageType == SystemConstant.MessageType_Innererwert )
                    {
                      this.mCurrentInnererWert = Integer.parseInt( (String) msgwrp.mMessageContent );
                      AllMessageFromStockStoreReady = true;
                    }
                    else
                    if ( msgwrp.mMessageType == SystemConstant.MessageType_AktienTrade_Order )
                    {
                       AktienTrade_Order aktienorder = ( AktienTrade_Order ) msgwrp.mMessageContent;
                       processAktienOrder(aktienorder);
                    }
                    else
                    if ( msgwrp.mMessageType == SystemConstant.MessageType_FriendStatus )
                    {
                        FriendStatus ff = ( FriendStatus ) msgwrp.mMessageContent;
                        // Early arrived FriendStatusInfo must be buffered.
                        // It will be used by GewinnStatusExchange methode
                        this.mEarlyFriendStatusBufferFromPartner.add( ff  );
                    }
               }
          }

        if ( SimulationIsBroken )
        {
            this.UnRegisterMe();
            this.StopAgent();
            return;
        }

        this.mAgent_TypeChanged = false;

         // It is time for status exchanging !!!
         // After mAheadDaysForGewinnCalculation, StatusExchange findet statt jeden Tag
         //if( ( mDataCC != 0 )  && ( mDataCC >= Configurator.mConfData.mAheadDaysForGewinnCalculation ) )
         if( mDataCC > 0  )
         {
            mBestPartnerType = "";
            mBestPartnerRule = "";
            boolean InterruptCommandReceived = exchangeGewinnStatus();
            if ( InterruptCommandReceived )
            {
                return;
            }
         }

      // prepare basic information for next Order
      OrderBasicData orderbasic = this.prepareOrder();

      // OperationSynchroner.startsynchron();
      // The expected Handelday has been archieved.
      mDataCC= mDataCC+1;
      //Stop the Agent
      if ( mDataCC < Configurator.mConfData.mHandelsday )
      {
          // Zufall-Generiert zwischen MinMenge und MaxMenge
          // int Wishexchangemenge = this.mMinMenge+this.mRandom.nextInt(mMaxMenge-mMinMenge+1);
          // check Depot und WishMenge anpassen!
          sendFeedBack2Store( orderbasic);
      }
      else
      {
           // Simulation is end
           sendDepotRecord2DataLogger();
           UnRegisterMe();
           StopAgent();
      }
}

// end of action()

// Send DepotRecord to DataLogger for Group Statistic

private OrderBasicData prepareOrder()
{
  char finaldecision = 'N';
  int  SleepProzent = this.getSleepProzent();
  boolean sleepneeded = false;

  int  RuleBuyMenge      = 0;
  int  PossibleBuyMenge  = 0;

  int  RuleSellMenge     = 0;
  int  PossibleSellMenge = 0;

  int  BuyLimit  = 0 ;
  int  SellLimit = 0;
  String  LimitReason = "";
  String  SellLimitReason = "";
  String  BuyLimitReason = "";

  if ( SleepProzent != 0 )
  {
    if (this.mRandom.nextInt(100)<SleepProzent)
    {
      sleepneeded = true;
    }
  }

  if ( sleepneeded )
  {
       finaldecision = 'N';
  }
  else
  {

    this.detailedcalculationinfo = "";

    if ( this.mOperatorType == SystemConstant.AgentType_INVESTOR )
    {
        return PrepareInvestorOrderData();
    }
    else
    if ( this.mOperatorType == SystemConstant.AgentType_NOISETRADER )
    {
           // make decision at fisrt the Limit directly
           this.mCurrentAveragePrice = PriceContainer.getMovingAveragePrice( this.mDayofAveragePrice );
           double lastpp = PriceContainer.getLastPrice();

           String kurszusatzadjusthistory = "";

           finaldecision = RuleAnalyser.NoiseTraderMarketAnalysewithRule(this.mCurrentAveragePrice);

           double Maxdelta =Configurator.mConfData.mNoiseTrader_MaxLimitAdjust - Configurator.mConfData.mNoiseTrader_MinLimitAdjust;
           double mydelta = this.mRandom.nextInt( (int) ( Maxdelta*1000 ) ) / 1000.0;
           this.mNoiseTraderDailyLimitAdjustProzent = Configurator.mConfData.mNoiseTrader_MinLimitAdjust + mydelta;

           // decide to buy
           if ( finaldecision == 'B' )
           {
               // 2006-09-11:
               BuyLimit  = (int) ( lastpp ) ;

               // 2006-08-15: Limit-Anpassung aufgrund Kurszusatzinfo
               // Brief: "B"

               if ( this.mPerformedStatus.getYesterdayTradeResult() == SystemConstant.TradeResult_Brief )
               {
                  // Jeder Agent hat ein konstant AdjustProzent
                  // BuyLimit = (int) ( BuyLimit * ( 1 - this.mInitConfig.mLimitAdjust / 100.0 ) );
                  // LimitReason = "VortagKurs(" + lastpp + ") * ( 1 - "+  HelpTool.DoubleTransfer( this.mInitConfig.mLimitAdjust,4) + "/100) Vortag KursZusag=" + SystemConstant.TradeResult_Brief;
                  // kurszusatzadjusthistory = ";BuyLimit decreased "+ this.mInitConfig.mLimitAdjust +"%;";

                  // 2006-10-23: Sehr wichtige Änderungen
                  // Jetzt jeder Agent hat jeden tag einen neuen Zufall Prozent

                  int factor = -1;
                  int temp = this.mRandom.nextInt(100);
                  if ( temp < 25 )
                  {
                     factor = 1;
                  }

                  BuyLimit = (int) ( BuyLimit * ( 1 + factor * this.mNoiseTraderDailyLimitAdjustProzent / 100.0 ) );

                  LimitReason = "VortagKurs(" + lastpp + ") * ( 1 - "+  HelpTool.DoubleTransfer( this.mNoiseTraderDailyLimitAdjustProzent, 4) + "%) Vortag KursZusag=" + SystemConstant.TradeResult_Brief;
                  kurszusatzadjusthistory = ";BuyLimit decreased "+ this.mNoiseTraderDailyLimitAdjustProzent +"%;";
               }
               else
               // Geld "G"
               if ( this.mPerformedStatus.getYesterdayTradeResult() == SystemConstant.TradeResult_Geld )
               {
                 // Old Theorie
                 // Verwende ein Konstant Adjust Prozent
                 // BuyLimit = (int) ( BuyLimit * ( 1 + this.mInitConfig.mLimitAdjust / 100.0 ) );
                 // LimitReason = "VortagKurs(" + lastpp + ") * ( 1 + "+  HelpTool.DoubleTransfer( this.mInitConfig.mLimitAdjust,4) + "/100) Vortag KursZusag=" + SystemConstant.TradeResult_Geld;
                 // kurszusatzadjusthistory = ";BuyLimit increased "+ this.mInitConfig.mLimitAdjust +"/100;";

                 // New Theorie
                 // Jeden Tag neue Adjust Prozent zu verwenden

                 int factor = 1;
                 int temp = this.mRandom.nextInt(100);
                 if ( temp < 25 )
                 {
                    factor = -1;
                 }

                 BuyLimit = (int) ( BuyLimit * ( 1 + factor * this.mNoiseTraderDailyLimitAdjustProzent/ 100.0 ) );
                 LimitReason = "VortagKurs(" + lastpp + ") * ( 1 + "+  HelpTool.DoubleTransfer( this.mNoiseTraderDailyLimitAdjustProzent,4) + "%) Vortag KursZusag=" + SystemConstant.TradeResult_Geld;
                 kurszusatzadjusthistory = ";BuyLimit increased "+ this.mNoiseTraderDailyLimitAdjustProzent +"%;";
               }
               // Tax "T"
               else
               {
                 // erneut die LimitAdjust erstellen
                 Maxdelta = 2* Configurator.mConfData.mNoiseTrader_MaxLimitAdjust;
                 mydelta = this.mRandom.nextInt( (int) ( Maxdelta*1000 ) ) / 1000.0;
                 this.mNoiseTraderDailyLimitAdjustProzent = (-1) * Configurator.mConfData.mNoiseTrader_MaxLimitAdjust + mydelta;
                 BuyLimit  = (int) ( lastpp +  this.mNoiseTraderDailyLimitAdjustProzent ) ;
                 LimitReason="VortagKurs + ZufallAdjust("+this.mNoiseTraderDailyLimitAdjustProzent+")";
               }

               // get the BuyMenge according to allgemeine NoiseTrader rule
               RuleBuyMenge     = (int) RuleAnalyser.makeNoiseTraderOrderMenge( this.mCurrentInnererWert, finaldecision , this.mCurrentAveragePrice);
               PossibleBuyMenge = this.makeFinalMenge(finaldecision, RuleBuyMenge, BuyLimit, mDataCC+1 );
               if ( PossibleBuyMenge == 0 )
               {
                   finaldecision ='N';
               }
               this.mLogger.debug( "NNNN;"+this.myAgent.getLocalName()+";" + this.mDataCC +";"+
                              PriceContainer.getLastPrice()+ ";"+this.mCurrentAveragePrice +
                              "; Decision=" + finaldecision+";" + sleepneeded+
                              "; wishmenge="+RuleBuyMenge+"; FinalMenge="+PossibleBuyMenge) ;

               // check if Billigest Buy is possible.
               LimitErmittelung le =  RuleAnalyser.checkNoiseTraderLimit( BuyLimit, this.mCurrentAveragePrice, 'B' );
               BuyLimit = (int) le.mLimit;
               LimitReason = LimitReason + " " + le.mReason;
           }
           else
           if ( finaldecision == 'S' )
           {
               finaldecision = 'S'; // Sell
               // 2006-09-11:
               SellLimit  = (int) ( lastpp ) ;
               // 2006-08-15: Limit-Anpassung aufgrund Kurszusatzinfo
               // Brief  "B"
               if ( this.mPerformedStatus.getYesterdayTradeResult() == SystemConstant.TradeResult_Brief )
               {
                 // Old Theorie
                 //SellLimit = (int) ( SellLimit * ( 1 - this.mInitConfig.mLimitAdjust / 100.0 ) );
                 //LimitReason = "VortagKurs(" + lastpp + ") * ( 1 - "+  HelpTool.DoubleTransfer( this.mInitConfig.mLimitAdjust,4) + "/100) Vortag KursZusag=" + SystemConstant.TradeResult_Brief;
                 //kurszusatzadjusthistory = ";SellLimit decreased "+this.mInitConfig.mLimitAdjust+"/100;";

                 // New Theorie 2006-10-31
                 SellLimit = (int) ( SellLimit * ( 1 - this.mNoiseTraderDailyLimitAdjustProzent / 100.0 ) );
                 LimitReason = "VortagKurs(" + lastpp + ") * ( 1 - "+  HelpTool.DoubleTransfer( this.mNoiseTraderDailyLimitAdjustProzent,4) + "/100) Vortag KursZusag=" + SystemConstant.TradeResult_Brief;
                 kurszusatzadjusthistory = ";SellLimit decreased "+this.mNoiseTraderDailyLimitAdjustProzent+"%;";

               }
               else
               // Geld "G"
               if ( this.mPerformedStatus.getYesterdayTradeResult() == SystemConstant.TradeResult_Geld )
               {
                 // Old Theorie
                 //SellLimit = (int) ( SellLimit * ( 1 + this.mInitConfig.mLimitAdjust / 100.0 ) );
                 //LimitReason = "VortagKurs(" + lastpp + ") * ( 1 + "+  HelpTool.DoubleTransfer( this.mInitConfig.mLimitAdjust,4) + "/100) Vortag KursZusag=" + SystemConstant.TradeResult_Geld;
                 //kurszusatzadjusthistory = ";SellLimit increased "+this.mInitConfig.mLimitAdjust+"/100;";

                 // New Theorie: 2006-10-31
                 SellLimit   = (int) ( SellLimit * ( 1 + this.mNoiseTraderDailyLimitAdjustProzent / 100.0 ) );
                 LimitReason = "VortagKurs(" + lastpp + ") * ( 1 + "+  HelpTool.DoubleTransfer( this.mNoiseTraderDailyLimitAdjustProzent,4) + "%) Vortag KursZusag=" + SystemConstant.TradeResult_Geld;
                 kurszusatzadjusthistory = ";SellLimit increased "+ this.mNoiseTraderDailyLimitAdjustProzent +"%;";
               }
               // TAX "T"
               else
               {
                 // erneut die LimitAdjust erstellen
                 Maxdelta = 2* Configurator.mConfData.mNoiseTrader_MaxLimitAdjust;
                 mydelta = this.mRandom.nextInt( (int) ( Maxdelta*1000 ) ) / 1000.0;
                 this.mNoiseTraderDailyLimitAdjustProzent = (-1) * Configurator.mConfData.mNoiseTrader_MaxLimitAdjust + mydelta;
                 SellLimit  = (int) ( lastpp +  this.mNoiseTraderDailyLimitAdjustProzent ) ;
                 LimitReason="VortagKurs + ZufallAdjust("+this.mNoiseTraderDailyLimitAdjustProzent+")";
               }

               // get the SellMenge according to allgemeine NoiseTrader rule
               RuleSellMenge     = (int) RuleAnalyser.makeNoiseTraderOrderMenge( this.mCurrentInnererWert, finaldecision , this.mCurrentAveragePrice);
               PossibleSellMenge = this.makeFinalMenge(finaldecision, RuleSellMenge, SellLimit, mDataCC+1 );

               if ( PossibleSellMenge == 0 )
               {
                   finaldecision ='N';
               }

               // check if Best Sell is possible.
               LimitErmittelung le = RuleAnalyser.checkNoiseTraderLimit( SellLimit, this.mCurrentAveragePrice,'S' );
               SellLimit = (int) le.mLimit;
               LimitReason = LimitReason + " " +le.mReason;

               this.mLogger.debug( "NNNN;"+this.myAgent.getLocalName()+";" + this.mDataCC +";"+
                                   PriceContainer.getLastPrice()+ ";"+this.mCurrentAveragePrice +
                                   "; Decision=" + finaldecision+";" + sleepneeded +
                                   ";wishmenge="+RuleSellMenge+";FinalMenge="+PossibleSellMenge) ;
           }

           this.detailedcalculationinfo = ";MyAveragePrice="+ nff.format2str(this.mCurrentAveragePrice)+ " YesterdayPrice=" +
               nff.format2str(lastpp) + kurszusatzadjusthistory;
    }
  }

  if ( finaldecision == 'B' )
  {
    return new OrderBasicData( finaldecision,  PossibleBuyMenge, BuyLimit, LimitReason );
  }
  else
  if ( finaldecision == 'S' )
  {
    return new OrderBasicData( finaldecision, PossibleSellMenge, SellLimit, LimitReason );
  }
  else
  {
    return new OrderBasicData( finaldecision, 0, 0, LimitReason );
  }
}

/*
private OrderBasicData prepareOrder()
{
  char finaldecision = 'N';
  int  SleepProzent = this.getSleepProzent();
  boolean sleepneeded = false;

  int  RuleBuyMenge      = 0;
  int  PossibleBuyMenge  = 0;

  int  RuleSellMenge     = 0;
  int  PossibleSellMenge = 0;

  int  BuyLimit  = 0 ;
  int  SellLimit = 0;
  String  LimitReason = "";
  String  SellLimitReason = "";
  String  BuyLimitReason = "";

  if ( SleepProzent != 0 )
  {
    if (this.mRandom.nextInt(100)<SleepProzent)
    {
      sleepneeded = true;
    }
  }

  if ( sleepneeded )
  {
       finaldecision = 'N';
  }
  else
  {

    this.detailedcalculationinfo = "";

    if ( this.mOperatorType == SystemConstant.AgentType_INVESTOR )
    {
       // original: this.mCurrentInnererWert
       double  lastprice = PriceContainer.getLastPrice();
       this.mInvestorVerwendetInnererWert = this.getIndividuellInnererWert( this.mCurrentInnererWert,  lastprice ) ;

       RuleBuyMenge  = RuleAnalyser.makeInvestorOrderMenge( this.mInvestorVerwendetInnererWert, this.mUsedAbschlagProzent, 'B' );
       RuleSellMenge = RuleAnalyser.makeInvestorOrderMenge( this.mInvestorVerwendetInnererWert, this.mUsedAbschlagProzent, 'S' );

       double avg = PriceContainer.getMovingAveragePrice( this.mDayofAveragePrice );

       LimitErmittelung le = RuleAnalyser.makeInvestorLimit ( this.mInvestorVerwendetInnererWert, this.mUsedAbschlagProzent, 'S' );

       SellLimit       = ( int ) le.mLimit;
       SellLimitReason = le.mReason;

       // check if "Best Sell" is possible
       le              = RuleAnalyser.checkInvestorLimit( SellLimit, avg, 'S' );
       SellLimit       = ( int ) le.mLimit;
       SellLimitReason = SellLimitReason + " " + le.mReason;

       PossibleSellMenge = this.makeFinalMenge('S', RuleSellMenge, SellLimit, mDataCC+1 );

       le = RuleAnalyser.makeInvestorLimit ( this.mInvestorVerwendetInnererWert, this.mUsedAbschlagProzent, 'B' );
       BuyLimit       = (int) le.mLimit;
       BuyLimitReason = le.mReason;

       PossibleBuyMenge = this.makeFinalMenge('B', RuleBuyMenge,  BuyLimit,  mDataCC+1 );
       // check if "Billigesten Buy" is possible
       le = RuleAnalyser.checkInvestorLimit(  BuyLimit, avg, 'B'  );
       BuyLimit       = ( int ) le.mLimit;
       BuyLimitReason = BuyLimitReason + " " + le.mReason;

       // Default Decision: Mixed Order
       finaldecision = 'M';
       if ( PossibleBuyMenge == 0 )
       {
            finaldecision = 'S';
            // Wenn Sell, feedback only the SellLimitReason
            LimitReason = SellLimitReason + " No cash, only Sell";
       }
       else
       if ( PossibleSellMenge == 0 )
       {
            finaldecision = 'B';
            // Wenn Buy, feedback only the SellLimitReason
            LimitReason = BuyLimitReason + " No stock, only Buy";
       }
       else
       {
             // 2006-09-19 coded
             // "Billigsten Buy" is prior to "Best Sell" when two "B" are possible at the same time.
             if ( BuyLimit == -1 )
             {
               // Decision is adjusted to "Billigsten Buy"
               finaldecision = 'B';
               LimitReason = BuyLimitReason + " Sell is no longer possible.";
             }
             else
             if ( SellLimit == -1 )
             {
               // Decision is adjusted to "Besten Sell"
               finaldecision = 'S';
               LimitReason = SellLimitReason + " Buy is on longer possible.";
             }
             else
             {
                // Mixed order
               LimitReason = "BuyLimit = IndividuellInnWert(" + this.mInvestorVerwendetInnererWert + ")*( 1 -" + this.mUsedAbschlagProzent+"/100)";
               LimitReason = LimitReason + " SellLimit = IndividuellInnWert(" + this.mInvestorVerwendetInnererWert + ")*( 1 +" + this.mUsedAbschlagProzent+"/100)";
             }
       }

       LimitRecord heutelimitrecord = new LimitRecord ( this.mDataCC, BuyLimit, SellLimit ) ;
       this.addOneLimitRecord( heutelimitrecord );

       // create the decision record for debug

       String decisioninfo =(mDataCC+1) + ". IndividuellInnererWert=" + this.mInvestorVerwendetInnererWert ;
       if ( finaldecision == 'B' )
       {
           if ( BuyLimit == -1 )
           {
             decisioninfo = decisioninfo + " BuyLimit=Billigsten Buy  SellLimit=0 " ;
           }
           else
           {
             decisioninfo = decisioninfo + " BuyLimit=" + BuyLimit+ "  SellLimit=0 " ;
           }
       }
       else
       if ( finaldecision == 'S' )
       {
         if ( SellLimit == -1 )
         {
           decisioninfo = decisioninfo + " BuyLimit=0 SellLimit=Besten Sell " ;
         }
         else
         {
           decisioninfo = decisioninfo + " BuyLimit=0 SellLimit=" + SellLimit ;
         }
       }
       else
       {
           decisioninfo = decisioninfo + " BuyLimit=" + BuyLimit + " SellLimit=" + SellLimit ;
       }
       decisioninfo = decisioninfo + " Abschlag=" + this.mUsedAbschlagProzent +
                           " RuleBuyMenge=" + RuleBuyMenge +
                           " RuleSellMenge=" + RuleSellMenge +
                           " PossibleBuyMenge=" + PossibleBuyMenge +
                           " PossibleSellMenge=" + PossibleSellMenge +
                           " FinalDecision=" + finaldecision;
       this.mLogger.debug( decisioninfo );

    }
    else
    if ( this.mOperatorType == SystemConstant.AgentType_NOISETRADER )
    {
           // make decision at fisrt the Limit directly
           this.mCurrentAveragePrice = PriceContainer.getMovingAveragePrice( this.mDayofAveragePrice );
           double lastpp = PriceContainer.getLastPrice();

           String kurszusatzadjusthistory = "";

           finaldecision = RuleAnalyser.NoiseTraderMarketAnalysewithRule(this.mCurrentAveragePrice);

           double Maxdelta =Configurator.mConfData.mNoiseTrader_MaxLimitAdjust - Configurator.mConfData.mNoiseTrader_MinLimitAdjust;
           double mydelta = this.mRandom.nextInt( (int) ( Maxdelta*1000 ) ) / 1000.0;
           this.mNoiseTraderDailyLimitAdjustProzent = Configurator.mConfData.mNoiseTrader_MinLimitAdjust + mydelta;

           // decide to buy
           if ( finaldecision == 'B' )
           {
               // 2006-09-11:
               BuyLimit  = (int) ( lastpp ) ;

               // 2006-08-15: Limit-Anpassung aufgrund Kurszusatzinfo
               // Brief: "B"

               if ( this.mPerformedStatus.getYesterdayTradeResult() == SystemConstant.TradeResult_Brief )
               {
                  // Jeder Agent hat ein konstant AdjustProzent
                  // BuyLimit = (int) ( BuyLimit * ( 1 - this.mInitConfig.mLimitAdjust / 100.0 ) );
                  // LimitReason = "VortagKurs(" + lastpp + ") * ( 1 - "+  HelpTool.DoubleTransfer( this.mInitConfig.mLimitAdjust,4) + "/100) Vortag KursZusag=" + SystemConstant.TradeResult_Brief;
                  // kurszusatzadjusthistory = ";BuyLimit decreased "+ this.mInitConfig.mLimitAdjust +"%;";

                  // 2006-10-23: Seht wichtige Änderungen
                  // Jetzt jeder Agent hat jeden tag einen neuen Zufall Prozent

                  BuyLimit = (int) ( BuyLimit * ( 1 - this.mNoiseTraderDailyLimitAdjustProzent / 100.0 ) );
                  LimitReason = "VortagKurs(" + lastpp + ") * ( 1 - "+  HelpTool.DoubleTransfer( this.mNoiseTraderDailyLimitAdjustProzent, 4) + "%) Vortag KursZusag=" + SystemConstant.TradeResult_Brief;
                  kurszusatzadjusthistory = ";BuyLimit decreased "+ this.mNoiseTraderDailyLimitAdjustProzent +"%;";
               }
               else
               // Geld "G"
               if ( this.mPerformedStatus.getYesterdayTradeResult() == SystemConstant.TradeResult_Geld )
               {
                 // Old Theorie
                 // Verwende ein Konstant Adjust Prozent
                 // BuyLimit = (int) ( BuyLimit * ( 1 + this.mInitConfig.mLimitAdjust / 100.0 ) );
                 // LimitReason = "VortagKurs(" + lastpp + ") * ( 1 + "+  HelpTool.DoubleTransfer( this.mInitConfig.mLimitAdjust,4) + "/100) Vortag KursZusag=" + SystemConstant.TradeResult_Geld;
                 // kurszusatzadjusthistory = ";BuyLimit increased "+ this.mInitConfig.mLimitAdjust +"/100;";

                 // New Theorie
                 // Jeden Tag neue Adjust Prozent zu verwenden
                 BuyLimit = (int) ( BuyLimit * ( 1 +  this.mNoiseTraderDailyLimitAdjustProzent/ 100.0 ) );
                 LimitReason = "VortagKurs(" + lastpp + ") * ( 1 + "+  HelpTool.DoubleTransfer( this.mNoiseTraderDailyLimitAdjustProzent,4) + "%) Vortag KursZusag=" + SystemConstant.TradeResult_Geld;
                 kurszusatzadjusthistory = ";BuyLimit increased "+ this.mNoiseTraderDailyLimitAdjustProzent +"%;";
               }
               // Tax "T"
               else
               {
                 // erneut die LimitAdjust erstellen
                 Maxdelta = 2* Configurator.mConfData.mNoiseTrader_MaxLimitAdjust;
                 mydelta = this.mRandom.nextInt( (int) ( Maxdelta*1000 ) ) / 1000.0;
                 this.mNoiseTraderDailyLimitAdjustProzent = (-1) * Configurator.mConfData.mNoiseTrader_MaxLimitAdjust + mydelta;
                 BuyLimit  = (int) ( lastpp +  this.mNoiseTraderDailyLimitAdjustProzent ) ;
                 LimitReason="VortagKurs + ZufallAdjust("+this.mNoiseTraderDailyLimitAdjustProzent+")";
               }

               // get the BuyMenge according to allgemeine NoiseTrader rule
               RuleBuyMenge     = (int) RuleAnalyser.makeNoiseTraderOrderMenge( this.mCurrentInnererWert, finaldecision , this.mCurrentAveragePrice);
               PossibleBuyMenge = this.makeFinalMenge(finaldecision, RuleBuyMenge, BuyLimit, mDataCC+1 );
               if ( PossibleBuyMenge == 0 )
               {
                   finaldecision ='N';
               }
               this.mLogger.debug( "NNNN;"+this.myAgent.getLocalName()+";" + this.mDataCC +";"+
                              PriceContainer.getLastPrice()+ ";"+this.mCurrentAveragePrice +
                              "; Decision=" + finaldecision+";" + sleepneeded+
                              "; wishmenge="+RuleBuyMenge+"; FinalMenge="+PossibleBuyMenge) ;

               // check if Billigest Buy is possible.
               LimitErmittelung le =  RuleAnalyser.checkNoiseTraderLimit( BuyLimit, this.mCurrentAveragePrice, 'B' );
               BuyLimit = (int) le.mLimit;
               LimitReason = LimitReason + " " + le.mReason;
           }
           else
           if ( finaldecision == 'S' )
           {
               finaldecision = 'S'; // Sell
               // 2006-09-11:
               SellLimit  = (int) ( lastpp ) ;
               // 2006-08-15: Limit-Anpassung aufgrund Kurszusatzinfo
               // Brief  "B"
               if ( this.mPerformedStatus.getYesterdayTradeResult() == SystemConstant.TradeResult_Brief )
               {
                 // Old Theorie
                 //SellLimit = (int) ( SellLimit * ( 1 - this.mInitConfig.mLimitAdjust / 100.0 ) );
                 //LimitReason = "VortagKurs(" + lastpp + ") * ( 1 - "+  HelpTool.DoubleTransfer( this.mInitConfig.mLimitAdjust,4) + "/100) Vortag KursZusag=" + SystemConstant.TradeResult_Brief;
                 //kurszusatzadjusthistory = ";SellLimit decreased "+this.mInitConfig.mLimitAdjust+"/100;";

                 // New Theorie 2006-10-31
                 SellLimit = (int) ( SellLimit * ( 1 - this.mNoiseTraderDailyLimitAdjustProzent / 100.0 ) );
                 LimitReason = "VortagKurs(" + lastpp + ") * ( 1 - "+  HelpTool.DoubleTransfer( this.mNoiseTraderDailyLimitAdjustProzent,4) + "/100) Vortag KursZusag=" + SystemConstant.TradeResult_Brief;
                 kurszusatzadjusthistory = ";SellLimit decreased "+this.mNoiseTraderDailyLimitAdjustProzent+"%;";

               }
               else
               // Geld "G"
               if ( this.mPerformedStatus.getYesterdayTradeResult() == SystemConstant.TradeResult_Geld )
               {
                 // Old Theorie
                 //SellLimit = (int) ( SellLimit * ( 1 + this.mInitConfig.mLimitAdjust / 100.0 ) );
                 //LimitReason = "VortagKurs(" + lastpp + ") * ( 1 + "+  HelpTool.DoubleTransfer( this.mInitConfig.mLimitAdjust,4) + "/100) Vortag KursZusag=" + SystemConstant.TradeResult_Geld;
                 //kurszusatzadjusthistory = ";SellLimit increased "+this.mInitConfig.mLimitAdjust+"/100;";

                 // New Theorie: 2006-10-31
                 SellLimit   = (int) ( SellLimit * ( 1 + this.mNoiseTraderDailyLimitAdjustProzent / 100.0 ) );
                 LimitReason = "VortagKurs(" + lastpp + ") * ( 1 + "+  HelpTool.DoubleTransfer( this.mNoiseTraderDailyLimitAdjustProzent,4) + "%) Vortag KursZusag=" + SystemConstant.TradeResult_Geld;
                 kurszusatzadjusthistory = ";SellLimit increased "+ this.mNoiseTraderDailyLimitAdjustProzent +"%;";
               }
               // TAX "T"
               else
               {
                 // erneut die LimitAdjust erstellen
                 Maxdelta = 2* Configurator.mConfData.mNoiseTrader_MaxLimitAdjust;
                 mydelta = this.mRandom.nextInt( (int) ( Maxdelta*1000 ) ) / 1000.0;
                 this.mNoiseTraderDailyLimitAdjustProzent = (-1) * Configurator.mConfData.mNoiseTrader_MaxLimitAdjust + mydelta;
                 SellLimit  = (int) ( lastpp +  this.mNoiseTraderDailyLimitAdjustProzent ) ;
                 LimitReason="VortagKurs + ZufallAdjust("+this.mNoiseTraderDailyLimitAdjustProzent+")";
               }

               // get the SellMenge according to allgemeine NoiseTrader rule
               RuleSellMenge     = (int) RuleAnalyser.makeNoiseTraderOrderMenge( this.mCurrentInnererWert, finaldecision , this.mCurrentAveragePrice);
               PossibleSellMenge = this.makeFinalMenge(finaldecision, RuleSellMenge, SellLimit, mDataCC+1 );

               if ( PossibleSellMenge == 0 )
               {
                   finaldecision ='N';
               }

               // check if Best Sell is possible.
               LimitErmittelung le = RuleAnalyser.checkNoiseTraderLimit( SellLimit, this.mCurrentAveragePrice,'S' );
               SellLimit = (int) le.mLimit;
               LimitReason = LimitReason + " " +le.mReason;

               this.mLogger.debug( "NNNN;"+this.myAgent.getLocalName()+";" + this.mDataCC +";"+
                                   PriceContainer.getLastPrice()+ ";"+this.mCurrentAveragePrice +
                                   "; Decision=" + finaldecision+";" + sleepneeded +
                                   ";wishmenge="+RuleSellMenge+";FinalMenge="+PossibleSellMenge) ;
           }

           this.detailedcalculationinfo = ";MyAveragePrice="+ nff.format2str(this.mCurrentAveragePrice)+ " YesterdayPrice=" +
               nff.format2str(lastpp) + kurszusatzadjusthistory;
    }
  }
  return new OrderBasicData( finaldecision, PossibleBuyMenge, BuyLimit, PossibleSellMenge, SellLimit, LimitReason );

}

*/



 private void processAktienOrder( AktienTrade_Order pAktienOrder)
 {
   // save this price into PriceContainer
   PriceContainer.saveTraderPrice( this.mDataCC, pAktienOrder.mFinalPrice );
   String orderprocessedmsg = "";

   this.mLogger.debug(  this.myAgent.getLocalName() +"  "+  pAktienOrder.getOrderPerformedInfo() );

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
            pAktienOrder.mBuyLimit
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
             pAktienOrder.mTradeMenge,
             pAktienOrder.mBuyLimit
      );
      // update the price
      this.mDepotRecord .AktienMarket_setCurrentPrice( pAktienOrder.mFinalPrice );
      if ( pAktienOrder.mOrderWish == SystemConstant.WishType_Mixed )
      {
        orderprocessedmsg = "Wish not fulfilled. new Price=" + pAktienOrder.mFinalPrice;
      }
      else
      {
        orderprocessedmsg = "No Buy/Sell Wish. new Price=" + pAktienOrder.mFinalPrice;
      }
  }

  // Important Debug

  // System.out.println( "---"+mDataCC+"." + this.myAgent.getLocalName() + " "+ orderprocessedmsg );

    /**
     * 2005-04-23
     * The Gewinn ist berechnet jeden Tag
     * aber Verlust ist jedoch nicht mehr geprüft und gezählt.
     * Verlust ist nur am Tag von StatusExchange geprüft und gezählt.
     */
    this.sendDepotStatus( pAktienOrder, ( this.mDataCC + 1 ) );
 }

  private int makeFinalMenge( char DecisonOfMarkt, int WishMenge, int Limit, int Day )
  {
     String LocalName = this.myAgent.getLocalName();
     switch ( DecisonOfMarkt )
     {
      case 'B':
          double cash = this.mDepotRecord.AktienMarket_getCurrentCash();
          if ( cash - 1.0 * Limit <= 0.0 )
          {
            // Cash is so littel that man can nat buy any stock
            this.detailedcalculationinfo= this.detailedcalculationinfo + "Cash not enough, BUY not possible, SELL possible.";
            return 0;
          }
          else
          if ( cash - WishMenge * Limit > 0.0 )
          {
             if ( this.mTraceOrder )
             {
             System.out.println("****" + Day +"."+ LocalName +" " + SystemConstant.getOperatorTypeName( this.mOperatorType ) +" says Buy " + WishMenge + ", Limit=" + Limit);
             }
             return WishMenge;
          }
          else
          {
               int nextpossiblemenge = (int) ( cash / Limit );
               if ( this.mTraceOrder )
               {

               System.out.println("****" + Day +"."+ LocalName +" " + SystemConstant.getOperatorTypeName( this.mOperatorType ) +" says Buy " + nextpossiblemenge + ", Limit=" + Limit );
               }

               return nextpossiblemenge;
          }
      case 'S':
          long currentmenge = this.mDepotRecord.AktienMarket_getCurrentAktienMenge() ;

          if ( currentmenge <= 0 )
          {
            if ( this.mTraceOrder )
            {
            System.out.println("****"  + Day +"."+ LocalName +" rule says Sell, no stock available to sell, BUY is possible.");
            }
            this.detailedcalculationinfo= this.detailedcalculationinfo + "Decision is Sell But no stock available SELL is not possible BUY possible.";
            return 0;
          }
          else
          if ( currentmenge - WishMenge >= 0 )
          {
            if ( this.mTraceOrder )
            {
             System.out.println("****" + Day +"."+ LocalName + " "+ SystemConstant.getOperatorTypeName( this.mOperatorType ) +" says Sell "+ WishMenge + ", Limit=" + Limit);
            }
             return  WishMenge;
          }
          else
          {
             this.detailedcalculationinfo= this.detailedcalculationinfo + " rule says to sell " + WishMenge +" stk, only " + currentmenge + " sttk available";
             if ( this.mTraceOrder )
             {

             System.out.println("****" + Day +"."+ LocalName + " "+ SystemConstant.getOperatorTypeName( this.mOperatorType ) +"  says Sell "+ currentmenge + ", Limit=" + Limit);
             }

             return (int) currentmenge;
          }
      case '0':
        if ( this.mTraceOrder )
        {

          System.out.println("****"  + Day +"."+ LocalName +" says Wait.");
        }
          return 0;
      default:
        if ( this.mTraceOrder )
        {

          System.out.println("****"  + Day +"."+ LocalName + ":Error, Wait.");
        }
        return 0;
     }
  }


private boolean isSchlechterCounterOverLimit()
{
  boolean exceededmaxlost = false;
  if ( Configurator.mConfData.mMaxLostNumberMode.equals("fixed") )
  {
     if ( this.mSchlechterCounter >= Configurator.mConfData.mFixedMaxLostNumber )
     {
        exceededmaxlost = true;
     }
  }
  else
  {
      if ( this.mSchlechterCounter >= this.mInitConfig.mLostNumberLimit )
      {
          exceededmaxlost = true;
      }
  }
  return exceededmaxlost;
}

private String getSchlechterCounterLimit()
{
  if ( Configurator.mConfData.mMaxLostNumberMode.equals("fixed") )
  {
     return "Common Limit " + Configurator.mConfData.mFixedMaxLostNumber ;
  }
  else
  {
     return "Individual Limit " +  this.mInitConfig.mLostNumberLimit;
  }
}



  /* send Gewinndstatus to its ReceiverPartner.
     the partner NameList is contained in the mXPList.

     Wait for the Gewinndstatus from its SenderPartner.
     Pay attention to:
      * Number of SenderPartner = Number of ReceiverPartner
      or
      *  Number of SenderPartner != Number of ReceiverPartner

     return:  true  when InterruptCommand is received in this phase
             false   otherwise
  */

private boolean exchangeGewinnStatus()
{
     String exchange_infolist[];
     boolean have_received_interruptcommand = false;

     // Send its status to its RecieverPartner
     if ( this.mXPList == null )
     {
         System.out.println("*****" + this.mDataCC +"." + this.myAgent.getLocalName()+":No Receiver Partner !");
     }
     else
     {
         // send status to its reciever partner
         this.sendStatus2Partner();
     }

     // Wait for the Status Message from SenderPartner that send the status to this Agent.
     if ( this.mSenderPartnerNumber == 0 )
     {
         sendExchangeInfo2DataLoggerWhen_No_Parter();
         // No Partner
         return false;
     }

      // try to receive the GewinnStatus from Partner

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

                   have_received_interruptcommand = true;
                   return have_received_interruptcommand;

                 }

              }
              catch (Exception ex)
              {
                 ex.printStackTrace();
                 System.out.println("Invalid message from " + aclmsg.getSender().getLocalName() );
                 System.out.println("Invalid message: " + aclmsg.toString() );
              }

              allstatus.add( (FriendStatus) msgwrp.mMessageContent );
              k++;
           }
       }

       // System.out.println( "*****" + this.mDataCC +"." + this.myAgent.getLocalName() + ": " + k + " reports are received from " + Sendernamelist);

       // Message Format: This message is used to record the history of Status-Exchange
       // XSTATE,Day;........;

       String StatusExhangeHistroy =
             getDayNoStr()+";" +
             this.myAgent.getLocalName()+";"+
             SystemConstant.getOperatorTypeName( this.mOperatorType ) +";" ;

       if ( ( this.mOperatorType == SystemConstant.AgentType_INVESTOR ) &&
            ( ! Configurator.mConfData.mInvestorAffectedByOtherNode ) )
       {
              // Nachbar hat keinen Einfluss.
              StatusExhangeHistroy = StatusExhangeHistroy + "Keine Kommunikation; so konfiguriert dass er keinen Einfluss von anderen NoiseTrader hat;";
              this.sendTypeChangeDecisionInfo_SingleLine( StatusExhangeHistroy );
              return false;
       }

      if (  ( this.mOperatorType == SystemConstant.AgentType_NOISETRADER ) &&
            ( ! Configurator.mConfData.mNoiseTraderAffectedByOtherNode ) )
      {
              // Nachbar hat keinen Einfluss.
              StatusExhangeHistroy = StatusExhangeHistroy + "Keine Kommunikation;so konfiguriert dass er keinen Einfluss von anderen Investor hat;";
              this.sendTypeChangeDecisionInfo_SingleLine( StatusExhangeHistroy );
              return false;
      }

      // check  wahrscheinlichkeit
      int kommunikationwahrscheinlichkeit = this.mRandom4Kommunikation.nextInt( 100 );
      if ( kommunikationwahrscheinlichkeit > Configurator.mConfData.mGewinnStatusExchangeProbability )
      {
            StatusExhangeHistroy = StatusExhangeHistroy + "Keine Kommunikation; wahrscheinlichkeit erlaubt es nicht;";
            this.sendTypeChangeDecisionInfo_SingleLine( StatusExhangeHistroy );
            return false;
      }

       // dann weiter, TypeChange ist erlaubt:
       StatusExhangeHistroy = StatusExhangeHistroy + "Ja wahrscheinlichkeit erlaubt;";

       String mybasicinfo ="";

       if ( this.mOperatorType == SystemConstant.AgentType_INVESTOR )
       {
         mybasicinfo = nff.format2str(this.mUsedAbschlagProzent) +";NA;" ;
       }
       else
       {
         mybasicinfo = StatusExhangeHistroy + "NA;"+this.mDayofAveragePrice +";";
       }

       mybasicinfo = mybasicinfo +
                     this.mDepotRecord.mRelativeGewinn + ";" +
                     nff.format2str(this.mDepotRecord.mRelativeGewinnProzent) + ";" +
                     this.mDepotRecord.mAbsoluteGewinn + ";" +
                     nff.format2str(this.mDepotRecord.mAbsoluteGewinnProzent) + ";";

       String exchange_decision_reason = "";

       if ( this.mOperatorType == this.mOriginalOperatorType )
       {
                   // der BestNachbar in diesem Mal aufgrund StatusExchange
                   BestOperatorFinder bopfinder = new BestOperatorFinder( allstatus );
                   FriendStatus bestop = bopfinder.getBestGewinner();

                   // vergleiche GewinnProzent mit meiner Nachbar
                   // Wir berücksichtigen nur den Agent:
                   // Sein GewinnProzent > Mein GewinnProzent und
                   if ( (  bestop.mGewinnProzent > this.mDepotRecord.mRelativeGewinnProzent  ) &&
                        (  bestop.mType != this.mOperatorType ) )
                   {

                       this.mSchlechterCounter++;
                       if ( this.isSchlechterCounterOverLimit() )
                       {
                               if (  bestop.mOriginalType == SystemConstant.AgentType_BLANKOAGENT )
                               {
                                   // Wenn BestFriend is a BlankoAgent
                                   // then nothing to do !!!
                                   // A Investor/NoiseTrader will not become back to BlankoAgent
                                   exchange_decision_reason = "Kein Wechseln weil kein Beeinfluss von BlankoAgent;";
                               }
                               else
                               {
                                   //Agent has to change his Type:
                                   exchange_decision_reason = exchange_decision_reason + "Schlechter_Counter "+ this.mSchlechterCounter + ">= " + this.getSchlechterCounterLimit()+";";

                                   if ( bestop.mType == SystemConstant.AgentType_INVESTOR )
                                   {
                                     // change my Type to Investor, Keine Beschränkung
                                     this.mOperatorType = bestop.mType;
                                     // 2007-09-10
                                     this.mDepotRecord.mTypeChangeCounter = this.mDepotRecord.mTypeChangeCounter + 1;

                                     // AbschlagProzent braucht neu zu generieren.
                                     // this.updateNewAbschlagProzent();

                                     exchange_decision_reason = exchange_decision_reason + " Changed to Investor and updated Abschlag;";
                                     this.mSchlechterCounter = 0;

                                     // 2007-11-26
                                     // Reset these two counter when Agent changes back to Investor
                                     this.mInnererWertPotenzialFaktor = 0;
                                     this.mInnererWertPotenzialVerbrauchenCounter = 0;

                                   }
                                   else
                                   {

                                     // Best Operator is ein NosieTrader

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
                                        exchange_decision_reason = exchange_decision_reason + " Can not change to NoiseTrader because of big preis deviation;";
                                     }
                                     else
                                     {
                                         this.mOperatorType = bestop.mType;
                                         // 2007-09-10
                                         this.mDepotRecord.mTypeChangeCounter = this.mDepotRecord.mTypeChangeCounter + 1;

                                         this.updateMovingsDay();
                                         this.mLimitHistory.clear();
                                         exchange_decision_reason = exchange_decision_reason + " Changed to NoiseTrader and updated Movingday cleared HistoryLimit;";
                                         this.mSchlechterCounter = 0;
                                     }
                                  }
                               }
                          }
                          else
                          {

                            exchange_decision_reason = exchange_decision_reason + " NoChange because Schlecter_Counter (" + this.mSchlechterCounter +")<" + this.getSchlechterCounterLimit()+";";

                          }
                   }
                   else
                   {
                       // BestNachbar hat gleichen Typ wie ich oder Sein GewinnProz ist nicht hoeher als meins.
                       // So brauche ich nicht zu wechseln .
                       exchange_decision_reason = exchange_decision_reason + " NoChange because BestNachbar has same Type or his GewinnProz not higher than mine;";
                   }
             String firstline = StatusExhangeHistroy + ";" + exchange_decision_reason + ";" + mybasicinfo;
             this.sendExchangeInfo2DataLogger_Mit_mehr_PartnerInfo( firstline, allstatus );

       }
       else
       {
               //exchange_decision_reason
               if ( this.mOperatorType == SystemConstant.AgentType_INVESTOR )
               {
                    if (  this.mLimitHistory.size() >= this.mInitConfig.mDays4AverageLimit )
                    {
                           // Has the enough Records for calculating of Average
                           // Zur zeit bin ich Investor
                           LimitRecord averagelimit = this.getAverageLimit();
                           double averageprice = PriceContainer.getMovingAveragePrice( this.mInitConfig.mDays4AverageLimit );

                           if ( ( averagelimit.mBuyLimit > averageprice ) ||
                                ( averagelimit.mSellLimit < averageprice )  )
                           {
                               // wechsel zurück zum NoiseTrader
                               this.mOperatorType = SystemConstant.AgentType_NOISETRADER;
                               this.updateMovingsDay();
                               this.mSchlechterCounter = 0;
                               this.mAgent_TypeChanged = true;

                               if ( ( averagelimit.mBuyLimit > averageprice ) )
                               {
                                 exchange_decision_reason = " back to NoiseTrader because AverageBuyLimit "+ averagelimit.mBuyLimit +" > AveragePrice " + nff.format2str ( averageprice )+ "  SchlecterCounterLimit="+this.mInitConfig.mLostNumberLimit+";";
                               }
                               else
                               {
                                 exchange_decision_reason = exchange_decision_reason +" back to NoiseTrader because AverageSellLimit "+ averagelimit.mSellLimit +" < AveragePrice " + nff.format2str( averageprice )+ " SchlecterCounterLimit="+this.mInitConfig.mLostNumberLimit+";";
                               }

                               //StatusExhangeHistroy  = StatusExhangeHistroy  + " and cleared LimitHistory ";

                               this.mLimitHistory.clear();
                           }
                           else
                           {
                               // Mit Rückkehr-Regel nicht möglich
                               exchange_decision_reason = exchange_decision_reason +"Mit Rückkehr-Regel, NoChange AverageBuyLimit=" +
                               nff.format2str( averagelimit.mBuyLimit) +" Averageprice="+
                               nff.format2str(averageprice)+" AverageSellLimit="+
                               nff.format2str( averagelimit.mSellLimit )+";";

                               BestOperatorFinder bopfinder = new BestOperatorFinder( allstatus );
                               FriendStatus bestop = bopfinder.getBestGewinner();

                               if (  (bestop.mType == SystemConstant.AgentType_NOISETRADER ) &&
                                     (bestop.mGewinnProzent > this.mDepotRecord.mRelativeGewinnProzent ) )
                               {
                                 this.mSchlechterCounter = this.mSchlechterCounter + 1;
                                 if ( this.isSchlechterCounterOverLimit() )
                                 {
                                     exchange_decision_reason = exchange_decision_reason+ " BestNachbar(NoiseTrader) hat höher GewinnProzent, wechsel zu NoiseTrader";
                                     this.mOperatorType = SystemConstant.AgentType_NOISETRADER;
                                     // 2007-09-10
                                     this.mDepotRecord.mTypeChangeCounter = this.mDepotRecord.mTypeChangeCounter + 1;

                                     this.updateMovingsDay();
                                     this.mSchlechterCounter = 0;
                                     this.mAgent_TypeChanged = true;
                                 }
                                 else
                                 {
                                     exchange_decision_reason = exchange_decision_reason + " No Change, Schlecter_Counter (" + this.mSchlechterCounter +")<" + this.getSchlechterCounterLimit();
                                 }
                               }
                               else
                               {
                                 // kann nicht wechseln.
                                 exchange_decision_reason = exchange_decision_reason + " Hinwechsel-Regel verwendet, aber Kein Wechsel weil  Schlechter_Counter " + this.mSchlechterCounter + " < Schlechter_Limit" + this.mInitConfig.mLostNumberLimit;
                               }
                           }
                    }
                    else
                    {
                      exchange_decision_reason = exchange_decision_reason +" NoChange Only " + this.mLimitHistory.size()+ " "  +this.mInitConfig.mDays4AverageLimit + " of Limit Info of HistoryLimit are required.";
                    }
               }
               else
               {
                 // Zur zeit bin ich NoiseTrader
                 // InnererWert * ( 1 + X% + Y * AbschlagProzent )
                 // X: BaseDeviation4PriceLimit
                 // Y:AbschlagFactor
                 double price_top_limit = this.mCurrentInnererWert * ( 1 + Configurator.mConfData.mBaseDeviation4PriceLimit / 100.0 + Configurator.mConfData.mAbschlagFactor * this.mUsedAbschlagProzent / 100.0 ) ;
                 // InnererWert * ( 1 - X% - Y * AbschlagProzent )
                 double price_bottom_limit = this.mCurrentInnererWert * ( 1 - Configurator.mConfData.mBaseDeviation4PriceLimit / 100.0 - Configurator.mConfData.mAbschlagFactor * this.mUsedAbschlagProzent / 100.0 ) ;

                 double lastprice =  PriceContainer.getLastPrice();

                 if ( ( lastprice > price_top_limit ) ||
                      ( lastprice < price_bottom_limit ) )
                 {
                     // wechsel zum Investor
                     this.mOperatorType = this.mOriginalOperatorType;
                     // 2007-09-10
                     this.mDepotRecord.mTypeChangeCounter = this.mDepotRecord.mTypeChangeCounter + 1;

                     // this.updateNewAbschlagProzent();
                     this.mSchlechterCounter = 0;
                     this.mAgent_TypeChanged = true;

                     if ( lastprice > price_top_limit )
                     {
                       exchange_decision_reason = exchange_decision_reason +" back to investor because lastprice" + lastprice + ">AktuellerPrice + X% + Y*Abschag" + nff.format2str(price_top_limit);
                     }
                     else
                     {
                       exchange_decision_reason = exchange_decision_reason +" back to investor because lastprice" + lastprice + "<AktuellerPrice + X% + Y*Abschag" + nff.format2str( price_bottom_limit ) ;
                     }
                 }
                 else
                 {
                     exchange_decision_reason = exchange_decision_reason +" ButNoChange under Rückwechsel-Regel because PriceTopLimit " + nff.format2str(price_top_limit)+" > LastPirce "+
                     nff.format2str( lastprice ) +" > PriceBottomLimit("+
                     nff.format2str(price_bottom_limit)+";";

                     BestOperatorFinder bopfinder = new BestOperatorFinder( allstatus );
                     FriendStatus bestop = bopfinder.getBestGewinner();

                     if (  (bestop.mType == SystemConstant.AgentType_INVESTOR ) &&
                           (bestop.mGewinnProzent > this.mDepotRecord.mRelativeGewinnProzent ) )
                     {

                       this.mSchlechterCounter = this.mSchlechterCounter + 1;
                       if ( this.isSchlechterCounterOverLimit() )
                       {
                           exchange_decision_reason = exchange_decision_reason + " Investor Nachbar hat höher GewinnProzent so wechseln zu Investor";
                           this.mOperatorType = SystemConstant.AgentType_INVESTOR;

                           // 2007-09-11
                           this.mDepotRecord.mTypeChangeCounter = this.mDepotRecord.mTypeChangeCounter + 1;
                           //this.updateNewAbschlagProzent();
                           this.mSchlechterCounter = 0;
                           this.mAgent_TypeChanged = true;

                           // 2007-11-26
                           // Reset these two counter when Agent changes back to Investor
                           this.mInnererWertPotenzialFaktor = 0;
                           this.mInnererWertPotenzialVerbrauchenCounter = 0;

                       }
                       else
                       {
                         exchange_decision_reason = exchange_decision_reason+ " Hinwechsel-Regel verwendet, aber Kein Wechsel because Schlechter_Counter( " + mSchlechterCounter + ") <" + this.getSchlechterCounterLimit();
                       }
                     }
                     else
                     {
                       // kann nicht wechseln.
                       exchange_decision_reason = exchange_decision_reason+ " Kein Wechsel, GewinnProz von BestNachbar nicht höcher als eigene";
                     }
                 }
            }
        }
  String firstline = StatusExhangeHistroy +";" + exchange_decision_reason + ";" + mybasicinfo;
  this.sendExchangeInfo2DataLogger_Mit_mehr_PartnerInfo( firstline, allstatus );
  return false;

}

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
        String mystatus = "Absc=" + this.mUsedAbschlagProzent + " MovD=" + this.mDayofAveragePrice
        + "; RelGewinn=" +this.mDepotRecord.mRelativeGewinn+ "; GewinnP=" + this.mDepotRecord.mRelativeGewinnProzent;

        ACLMessage statusmsg = new ACLMessage( ACLMessage.INFORM );
        try
        {
           statusmsg.setContentObject( tt  );
        }
        catch (Exception ex)
        {
          ex.printStackTrace();
        }
        for ( int i=0; i<this.mXPList.size(); i++)
        {
            AID oneaid = (AID) this.mXPList.elementAt(i);
            statusmsg.addReceiver( oneaid );
        }
        myAgent.send( statusmsg  );
        //System.out.println( "*****" + this.mDataCC +"." + this.myAgent.getLocalName()+ ": send Status" + mystatus );
}

 private void makeInitWork()
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
            ex.printStackTrace();
        }

        // MultiInnererWert
        First300InitData first300data = ( First300InitData ) msgwrp.mMessageContent;

        this.processFirst300InitData( first300data );

        // The price of No. 300 price data
        int lastprice = (int)PriceContainer.getLastPrice() ;
        this.mCurrentInnererWert = first300data.mIntData[ first300data.mIntData.length -1 ];

        // Hier ist Bug-Stelle
        // 2007-10-01
        // Doppeled Call of the Methode
        //this.mDepotRecord.AktienMarket_setInitPrice( lastprice );

        // A init status, but only lastprice is useful.
        this.mPerformedStatus = new OrderPerformedStatus('N',lastprice,'N', 0, lastprice );
        // Update Init Depot using the lastprice

        // prepare 1. Order
        OrderBasicData orderbasic = this.prepareOrder();
        // Send 1. Order to StockStore
        sendFeedBack2Store( orderbasic );
 }

private String FriendstatusFormatter(String ss)
{
  String str= ss;
  str = str.replace(',', ';');
  str = str.replace('.', ',');
  return  str;

}


}