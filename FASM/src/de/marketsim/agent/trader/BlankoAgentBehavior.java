
/**
 * Beschreibung:
 * Behavoir of new Agent type: BlankoAgent
 * Copyright:     Copyright (c) 2007
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

public class BlankoAgentBehavior extends OperatorBaseBehavior
{

  /* new parameter of BlankoAgent */

  Random  rd = new Random();

  boolean mCashAlreadyIncreased_ThisPeriode = false;

  boolean mTraceOrder        = false;

  // boolean mFirstBuyPerformed = false;
  // double  mFirstBuyKurs      = 0.0;

  // This is not used.
  // 2007-10-06 changed.
  // double  mAverageKursOfTimeWindow = 0.0;

  // The Reference Price before N  days

  boolean mBlankoAgentWarSchonDeaktiviert = false;


  double   mReferencePrice              = 0.0;
  int      mAfterActivationCounter      = 0;
  int      mKursNacheinanderFallCounter = 0;
  //int      mForcedSleepCounter          = 0;

  int      mInactiveDaysCounter          = 0;

  /* end of new parameter of BlankoAgent */

  BestOperatorFinder   bopfinder  = null;
  FriendStatus         mBestNachbarofLastExchange = null;
  boolean firstRequest        = true;
  int PriceHistoryNumber      = 0;
  char Reaction               = ' ';

  OrderPerformedStatus    mPerformedStatus = null;

  int  mSchlechterCounter = 0;

  String mBestPartnerType ="";
  String mBestPartnerRule ="";

  private DataFormatter nff =new de.marketsim.util.DataFormatter( Configurator.mConfData.mDataFormatLanguage );

  Vector  mEarlyFriendStatusBufferFromPartner = new Vector();

  public BlankoAgentBehavior(Agent  pAgent)
  {
     super(pAgent);
     int randomseed = HelpTool.getCharIntSumme( this.myAgent.getLocalName() );
     this.mRandom = new Random();

     String ss = System.getProperty("TRACEORDERENABLED","false");
     if ( ss.equalsIgnoreCase("true") )
     {
         mTraceOrder = true;
     }

  }

  private boolean AgentHasAlreadyStrategie()
  {
     if (  this.mGeerbterTyp != SystemConstant.AgentType_BLANKOAGENT )
     {
       return true;
     }
     else
     {
       return false;
     }
  }

  // main work flow
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
                     MessageWrapper msgwrp = null;
                     try
                     {
                         msgwrp = ( MessageWrapper ) aclmsg.getContentObject();
                     }
                     catch (Exception ex)
                     {
                        ex.printStackTrace();
                        System.out.println( "ErrorMsg from :" +  aclmsg.getSender().getLocalName() );
                        System.out.println( "ErrorMsg=" +  aclmsg.getContent() );

                        // up to now
                        // 2007-11-14
                        // sometimes Agent gets an Jade System Error like following
                        /*
                        ErrorMessage from:ams
						( (action ( agent-identifier :name DAX@muf10005:1099/JADE  :addresses (sequence
						IOR:000000000000001149444C3A464950412F4D54533A312E300000000000000001000000000000
							006E000102000000000E3139322E3136382E302E313033000E6000000019AFABCB00000000023AAD
							C78A0000000800000000000000001400000000000002000000010000002000000000000100010000
							0002050100010001002000010109000000010001010000000026000000020002 )) (ACLMessage)
 							) (MTS-error ( agent-identifier :name DataLogger@muf10005:1099/JADE ) "
 							(internal-error "Agent not found: getContainerID() failed to find agent
 							DataLogger@muf10005:1099/JADE")") )java.lang.NullPointerException

                        ==================================================================================
                        It looks like a performance problem.
                        The DataLogger AGent is not rebooted.
                        But Agents think that it has been rebooted.

                        It will be improved late.
                        Some improvement will be defined to avoid this situation.

                        1) When DAX broadcast info to all agents,
                           It will be garanted that the DataLogger has been registered.
                           Even if it has been rebooted
                           But it can happen that the Booting phase is not finished
                           When in this moment, an agent tries to send a DepotInfo
                           to DataLogger agent, it can result in above error.

                        2) Let the Agent do some delay when it tries to send Depot Info
                           to DataLogger agent !!! at first time !!!

                        */

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
          }  // End of while

        if ( SimulationIsBroken )
        {
            this.UnRegisterMe();
            this.StopAgent();
            return;
        }

         this.mBlankoAgent_StateChanged = false;

         // New Logic for BlankoAgent
         // check if it is in INITSTATE
         if ( this.mBlankoAgentCurrentState == this.BLANKOAGENT_INITSTATE  )
         {
             double pricesteigerung ;
             double steigerungprozent;
             if ( this.mDataCC > this.mInitConfig.mBlankoAgentDayOfIndexWindow )
             {
                 //this.mAverageKursOfTimeWindow = PriceContainer.getMovingAveragePrice( this.mInitConfig.mBlankoAgentDayOfIndexWindow );

                 this.mReferencePrice = PriceContainer.getReferencePrice( this.mInitConfig.mBlankoAgentDayOfIndexWindow );
                 double curprice = PriceContainer.getLastPrice();

                 // if ( curprice > this.mAverageKursOfTimeWindow )
                 if ( curprice > this.mReferencePrice )
                 {
                     //pricesteigerung   = curprice - this.mAverageKursOfTimeWindow;
                     pricesteigerung   = curprice - this.mReferencePrice;

                     // steigerungprozent = pricesteigerung * 100.0 / this.mAverageKursOfTimeWindow;
                     steigerungprozent = pricesteigerung * 100.0 / this.mReferencePrice;

                     //if ( steigerungprozent > Configurator.mConfData.mBlankoAgentPlusIndexProcentForActivation )

                     this.mBlankoAgent_Decision_Argument = "CurPrice=" + curprice + " Ref.Price=" + nff.format2str ( this.mReferencePrice ) +
                                            " UpProcent=" +  nff.format2str (steigerungprozent) ;

                     if ( steigerungprozent > this.mInitConfig.mBlankoAgentKursUpProcent4Activation )
                     {

                         int wahrscheinlichkeit = this.mRandom.nextInt(100);
                         if ( wahrscheinlichkeit > Configurator.mConfData.mBlankoAgent_SleepProcent )
                         {
                             this.mBlankoAgentCurrentState = this.BLANKOAGENT_ACTIVESTATE;
                             this.mBlankoAgentOldState     = this.BLANKOAGENT_INITSTATE;
                             this.mBlankoAgent_StateChanged = true;
                             this.mAfterActivationCounter = 1;
                             this.mBlankoAgent_Decision_Argument = "Activated, " + this.mBlankoAgent_Decision_Argument;
                             this.mInactiveDaysCounter = 0;
                         }
                         else
                         {
                             this.mBlankoAgent_Decision_Argument = "Unchanged because Probability not allowed, " + this.mBlankoAgent_Decision_Argument;
                         }
                     }
                     else
                     {
                     }
                 }
                 else
                 {
                   double pricedown   =  this.mReferencePrice - curprice;
                   // steigerungprozent = pricesteigerung * 100.0 / this.mAverageKursOfTimeWindow;
                   double downprozent = pricedown * 100.0 / this.mReferencePrice;
                     this.mBlankoAgent_Decision_Argument = "CurpPrice=" + curprice +
                                     " Ref.Price=" + nff.format2str( this.mReferencePrice ) +
                                     " DownProcent=" +  nff.format2str (downprozent) ;
                 }
             }
             else
             {
                // In First N Tage, keine Aktivierung möglich !!!
            	 this.mBlankoAgent_Decision_Argument = "PriceWindow needs at least " + this.mInitConfig.mBlankoAgentDayOfIndexWindow + " days prices as basis.";
                //public String mBlankoAgent_Change_Reason    = "";
             }
         }
         else
         {
             // BlankoAgent is active
             if ( this.mBlankoAgentCurrentState == this.BLANKOAGENT_ACTIVESTATE )
             {
                 // already active
                 // Nothing to do

                 // Error Report 2007-07-19
                 // BlankoAgent muss aktive sein und eine eigene Strategie haben
                 // dann kann er seine ActiveCounter erhöhen.

                 // Wenn er noch kein eigene Strategie hat, obwohl er schon active ist
                 // aber ActivationCounter darf nicht erhöht werden.

                 if ( this.mGeerbterTyp == SystemConstant.AgentType_BLANKOAGENT )
                 {
                    // nothing to do
                   this.mBlankoAgent_Decision_Argument = "already active but no strategy";
                 }
                 else
                 {
                    // er hat eine Strategie
                    this.mAfterActivationCounter = this.mAfterActivationCounter + 1;
                    this.mBlankoAgent_Decision_Argument = "Active with strategy for " + this.mAfterActivationCounter + " days";
                 }
             }
             else
             {
                // So it is inactive
                this.mInactiveDaysCounter++;

                /* 2007-11-17
                if ( this.mInactiveDaysCounter >= this.mInitConfig.mBlankoAgentInactiveDays )
                {
                    //this.mBlankoAgentCurrentState = this.BLANKOAGENT_ACTIVESTATE;
                    //this.mBlankoAgentOldState     = this.BLANKOAGENT_INACTIVESTATE;

                    this.mBlankoAgent_Decision_Argument="InactiveDays "+ this.mInactiveDaysCounter + ">=BlankoAgentInactiveDays " +
                    this.mInitConfig.mBlankoAgentInactiveDays + " back to InitState, ignoring old policy.";

                    this.mBlankoAgentCurrentState = this.BLANKOAGENT_INITSTATE;
                    this.mBlankoAgentOldState     = this.BLANKOAGENT_INACTIVESTATE;

                    // vergessen alte Strategie
                    // zurück zum InitState

                    this.mGeerbterTyp = SystemConstant.AgentType_BLANKOAGENT;

                    //2007-09-12
                    this.mDepotRecord.mTypeChangeCounter =   this.mDepotRecord.mTypeChangeCounter + 1;

                    this.mBlankoAgent_StateChanged = true;
                    //this.mBlankoAgent_Activated_Reason = "sleeped for " + this.mForcedSleepCounter +" days > allowed InactiveDays=" + this.mInitConfig.mBlankoAgentInactiveDays;

                    //this.mBlankoAgent_Stay_Init_Reason = "sleeped for " + this.mForcedSleepCounter +" days > allowed InactiveDays=" + this.mInitConfig.mBlankoAgentInactiveDays;
                    // then clear the sleepcounter
                    this.mInactiveDaysCounter = 0;
                }
                else
                {
                   // nothing
                   this.mBlankoAgent_Decision_Argument="InactiveDays "+ this.mInactiveDaysCounter +
                   "<= MindesInactiveDays " + Configurator.mConfData.mBlankoAgent_DaysOfTotalSell + " so staying inactive.";
                }
                */

                // 2007-12-03
                // changed back to old rule:
                //
                if ( this.mInactiveDaysCounter >= this.mInitConfig.mBlankoAgentInactiveDays )
                {
                    //this.mBlankoAgentCurrentState = this.BLANKOAGENT_ACTIVESTATE;
                    //this.mBlankoAgentOldState     = this.BLANKOAGENT_INACTIVESTATE;

                    this.mBlankoAgent_Decision_Argument="InactiveDaysCounter "+ this.mInactiveDaysCounter + ">IndividuellInactiveDays " +  this.mInitConfig.mBlankoAgentInactiveDays+ " back to InitState";

                    this.mBlankoAgentCurrentState = this.BLANKOAGENT_INITSTATE;
                    this.mBlankoAgentOldState     = this.BLANKOAGENT_INACTIVESTATE;

                    // vergessen alte Strategie
                    // zurück zum InitState

                    this.mGeerbterTyp = SystemConstant.AgentType_BLANKOAGENT;

                    //2007-09-12
                    this.mDepotRecord.mTypeChangeCounter =   this.mDepotRecord.mTypeChangeCounter + 1;

                    this.mBlankoAgent_StateChanged = true;
                    //this.mBlankoAgent_Activated_Reason = "sleeped for " + this.mForcedSleepCounter +" days > allowed InactiveDays=" + this.mInitConfig.mBlankoAgentInactiveDays;

                    //this.mBlankoAgent_Stay_Init_Reason = "sleeped for " + this.mForcedSleepCounter +" days > allowed InactiveDays=" + this.mInitConfig.mBlankoAgentInactiveDays;
                    // then clear the sleepcounter
                    this.mInactiveDaysCounter = 0;
                    this.mAfterActivationCounter = 0;

                }
                else
                {
                   // there is yet stocks.
                   this.mBlankoAgent_Decision_Argument="InactiveDays "+ this.mInactiveDaysCounter +
                   " There are yet stocks " + this.mDepotRecord.mCurrentAktienMenge +" pieces, so staying inactive.";
                }

             }
        }

         this.mAgent_TypeChanged = false;

         // It is time for status exchanging !!!
         // After mAheadDaysForGewinnCalculation, StatusExchange findet statt jeden Tag
         //if( ( mDataCC != 0 )  && ( mDataCC >= Configurator.mConfData.mAheadDaysForGewinnCalculation ) )
         if( mDataCC > 0  )
         {
            mBestPartnerType = "";
            mBestPartnerRule = "";
            boolean InterruptCommandReceived = this.exchangeGewinnStatus();
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
          sendFeedBack2Store( orderbasic );
      }
      else
      {
           // Simulation is end
           this.sendDepotRecord2DataLogger();

           double wahrschlichkeit = 0.0;
           if ( this.mRandom4Kommunikation_CallTotal > 0 )
           {
              wahrschlichkeit  = this.mRandom4Kommunikation_Ja * 100.0 / this.mRandom4Kommunikation_CallTotal ;
           }

           String WahrscheinlichkeitCheck =
                  this.getDayNoStr()+";" +
                  this.myAgent.getLocalName()+";"+
                  SystemConstant.getOperatorTypeName( this.mGeerbterTyp ) +";" +
                  " Wahrscheinlichkeit in gesamten Lauf : " + nff.format2str(wahrschlichkeit)+"=" + this.mRandom4Kommunikation_Ja + "/" + this.mRandom4Kommunikation_CallTotal +";";

           this.sendTypeChangeDecisionInfo_SingleLine(WahrscheinlichkeitCheck);

           UnRegisterMe();
           StopAgent();
      }
}

// end of action()

private void Back2InitState()
{
  // Now back to InitState
  this.mBlankoAgentCurrentState = this.BLANKOAGENT_INITSTATE;
  this.mBlankoAgentDeactivationDayCounter = 0;

  // Append Cash is allowed
  if (  Configurator.mConfData.mBlankoAgent_CashAppendAllowed )
  {
    // 2008-01-09: Entferne die Voraussetzung, Cash wird immer zugefügt nach Deaktivierung
    // Check if CASH is less than 1/3 of InitCash
    //if (  this.mDepotRecord.AktienMarket_getCurrentCash() < this.mInitConfig.mInitCash / 3 )
    //{
        // this.mDepotRecord.mCurrentCash = this.mDepotRecord.mCurrentCash + this.mInitConfig.mInitCash;
    	this.mDepotRecord.mCurrentCash = this.mDepotRecord.mCurrentCash +
    	                                 Configurator.mConfData.mBlankoAgent_AppendCash;

    //}
  }

}

/**
 * Prepare a new Order for BlankoAgent
 * @return
 */

private OrderBasicData prepareOrder()
{
  char    finaldecision     = 'N';
  int     SleepProzent      = this.getSleepProzent();
  boolean sleepneeded       = false;
  int     RuleBuyMenge      = 0;
  int     PossibleBuyMenge  = 0;
  int     RuleSellMenge     = 0;
  int     PossibleSellMenge = 0;
  int     BuyLimit          = 0 ;
  int     SellLimit         = 0;
  String  LimitReason       = "";
  String  SellLimitReason   = "";
  String  BuyLimitReason    = "";

  /**
   *  1. Check if BlankoAgent is active
   *     if it is not active
   *     then send a wait Order
   *  2. If it is active,
   *
   *     then check if it will sleep,
   *     if it will Sleep
   *     then send a wait order
   *     else create a new order
   *     acoording to the market situation
   *
   *
   * 3. Communication with its neighbour
   *
   *    if it is not active
   *    then it will become active and
   *    accept the Agent Type of the best neighbour
   *
   * 4. check the market situation
   *    if it should become inactive
   */

  // hier we begin to implement the BlankoAgent Buy/Sell Rule:

  // yet in InitState
  if ( this.mBlankoAgentCurrentState == this.BLANKOAGENT_INITSTATE )
  {
     //return new OrderBasicData( finaldecision, PossibleBuyMenge, BuyLimit, PossibleSellMenge, SellLimit, LimitReason );
     return new OrderBasicData( 'N', 0, 0, "BlankoAgent in InitState no wish" );
  }

  // BlankoAgent is in deactivated-status
  if ( this.mBlankoAgentCurrentState == this.BLANKOAGENT_INACTIVESTATE )
  {
          if ( this.mDepotRecord.mCurrentAktienMenge > 0 )
          {
             int newsellmenge = 0;
             if ( this.mDepotRecord.mCurrentAktienMenge > this.mBlankoAgentSellMenge_WhileDeactivation )
             {
                newsellmenge = this.mBlankoAgentSellMenge_WhileDeactivation;
             }
             else
             {
               newsellmenge = (int) this.mDepotRecord.mCurrentAktienMenge;
             }

             this.mBlankoAgentDeactivationDayCounter = this.mBlankoAgentDeactivationDayCounter +1;

             if (  this.mBlankoAgentDeactivationDayCounter >= this.mInitConfig.mBlankoAgentInactiveDays  )
             {
               this.Back2InitState();
             }

             double downprocentzahl_for_limit = this.rd.nextInt(400)/100.0 ;
             int newSellLimit = (int) Math.round ( this.mDepotRecord.mCurrentPrice * ( 1 - downprocentzahl_for_limit / 100.0 ) );

             return new OrderBasicData( 'S', newsellmenge, newSellLimit, "BlankoAgent in deactivated state, try to sell with Limit=YesterDayPrice*( 1 - " +downprocentzahl_for_limit+"%)" );
          }
          else
          {
             this.mBlankoAgentDeactivationDayCounter = this.mBlankoAgentDeactivationDayCounter + 1;
             if (  this.mBlankoAgentDeactivationDayCounter >= this.mInitConfig.mBlankoAgentInactiveDays  )
             {
               // Now back to InitState
               this.Back2InitState();
               return new OrderBasicData( 'N', 0, 0, "BlankoAgent back to InitState, No stock to sell" );
             }
             else
             {
               return new OrderBasicData( 'N', 0, 0, "BlankoAgent has to be inactive for " + this.mInitConfig.mBlankoAgentInactiveDays +", " + this.mBlankoAgentDeactivationDayCounter );
             }
          }
  }

  // Agent is schon active
  // But it does not have own strategy
  if ( ! this.AgentHasAlreadyStrategie() )
  {
    //return new OrderBasicData( finaldecision, PossibleBuyMenge, BuyLimit, PossibleSellMenge, SellLimit, LimitReason );
    return new OrderBasicData( 'N', 0, 0, "BlankoAgent active but no strategy" );
  }

  /** Old Idee
  double  curprice  = PriceContainer.getLastPrice();
  double  pricedown = curprice - this.mFirstBuyKurs;
  if ( pricedown < 0 )

  **/

  double  curprice        = PriceContainer.getLastPrice();

  //this.mAverageKursOfTimeWindow = this.mAverageBuyPriceCalculator.getAcceptableAveragePrice();

  // this.mAverageKursOfTimeWindow = PriceContainer.getMovingAveragePrice( this.mInitConfig.mBlankoAgentDayOfIndexWindow );

  this.mReferencePrice = PriceContainer.getReferencePrice( this.mInitConfig.mBlankoAgentDayOfIndexWindow );

  // if ( curprice < this.mAverageKursOfTimeWindow )
  if ( curprice < this.mReferencePrice )
  {

    // double   pricedown   = this.mAverageKursOfTimeWindow - curprice;

    double   pricedown   = this.mReferencePrice - curprice;

    //double  downprozent  = pricedown * 100.0 / this.mAverageKursOfTimeWindow;
    double  downprozent  = pricedown * 100.0 / this.mReferencePrice;
    this.mBlankoAgent_Decision_Argument = this.mBlankoAgent_Decision_Argument +
     "CurPrice=" + curprice + " Ref.Price=" + nff.format2str( this.mReferencePrice ) +
     " DownProcent=" + nff.format2str( downprozent)  +
     " Threshold=" + nff.format2str ( this.mInitConfig.mBlankoAgentKursDownProcent4Deactivation ) +
     " MindesActiveDays=" + Configurator.mConfData.mBlankoAgentMindestActiveDays;
    // Price ist unter gegangen.
    // if ( steigerungprozent > Configurator.mConfData.mBlankoAgentPlusIndexProcentForActivation )

    // this.mInitConfig.mBlankoAgentKursDownProcent4Deactivation ist  SchwelleWert (Threshold)
    if ( downprozent > this.mInitConfig.mBlankoAgentKursDownProcent4Deactivation )
    {

      // double delta = Math.abs( pricedown );
      // double pricedownprocent = 100 * delta / this.mFirstBuyKurs;
      // if ( pricedownprocent >Math.abs( Configurator.mConfData.mBlankoAgentMinusIndexProcentForDeactivation ) )

      if ( mAfterActivationCounter > Configurator.mConfData.mBlankoAgentMindestActiveDays )
      {
           // BlankoAgent darf inaktive sein, aber nicht unbedient !
           // this.mKursNacheinanderFallCounter = this.mKursNacheinanderFallCounter + 1;

           //if ( this.mKursNacheinanderFallCounter >= Configurator.mConfData.mBlankoAgent_DaysOfTotalSell )
           //{

               this.mAfterActivationCounter      = 0;
               this.mKursNacheinanderFallCounter = 0;
               this.mBlankoAgentCurrentState     = this.BLANKOAGENT_INACTIVESTATE;
               this.mBlankoAgentWarSchonDeaktiviert = true;
               this.mBlankoAgentOldState         = this.BLANKOAGENT_ACTIVESTATE;
               // reset its type also to BLANKOAGENT
               this.mGeerbterTyp                  = SystemConstant.AgentType_BLANKOAGENT;
               this.mBlankoAgent_StateChanged     = true;
               this.mBlankoAgent_Decision_Argument= "Deactivated !!! " + this.mBlankoAgent_Decision_Argument;
               // reset to false
               //this.mFirstBuyPerformed = false;
           //}

           // Decide to sell all Aktien what Agent has.
           // OrderBasicData( char pDecision, int pBuyMenge, int pBuyLimit, int pSellMenge, int pSellLimit, String pLimitCalcBase)

           if ( this.mDepotRecord.AktienMarket_getCurrentAktienMenge() > 0 )
           {

             int VerkaufMenge     = 0;
             String SellMengeDesc = "";

             if ( this.mDepotRecord.AktienMarket_getCurrentAktienMenge() <= Configurator.mConfData.mBlankoAgent_DaysOfTotalSell )
             {
                VerkaufMenge  = 1;  // VerkaufMenge = (int) this.mDepotRecord.AktienMarket_getCurrentAktienMenge();
                SellMengeDesc = " only 1 aktien ";
                this.mBlankoAgentSellMenge_WhileDeactivation = 1;
             }
             else
             {
                SellMengeDesc = " his  1/" + Configurator.mConfData.mBlankoAgent_DaysOfTotalSell + " of rest aktien";
                VerkaufMenge = Math.round( this.mDepotRecord.AktienMarket_getCurrentAktienMenge()/Configurator.mConfData.mBlankoAgent_DaysOfTotalSell );
                this.mBlankoAgentSellMenge_WhileDeactivation = VerkaufMenge;
             }

             // Try 1: Verkaufen 1 / N  seiner Aktien
             // return new OrderBasicData( 'S', 0, 0, (int) this.mDepotRecord.AktienMarket_getCurrentAktienMenge(),  (int) curprice , "BlankoAgent sells all aktien" );
             // Try 2: Best Verkaufen: 1 / N  seiner Aktien

             this.mBlankoAgentDeactivationDayCounter = 1; // this is first time to sell out.

             // 2007-12-09
             // Blanko Agent stellt kein BestSell Order
             // Old: int newSellLimit = -1;  // BestSell
             double downprocentzahl_for_limit = this.rd.nextInt(400)/100.0 ;

             int    newSellLimit = (int) Math.round ( this.mDepotRecord.mCurrentPrice * ( 1 - downprocentzahl_for_limit / 100.0 ) );

             return new OrderBasicData( 'S', VerkaufMenge,  newSellLimit , "BlankoAgent sells "  + SellMengeDesc );

           }
           else
           {
              // Want to sell, but there is not aktien
              return new OrderBasicData( 'N', 0, 0, "Should sell all but no aktien" );
           }
       }
      }
  }
  else
  {
       // Counter auf 0 zurücksetzen.
       this.mKursNacheinanderFallCounter = 0;
  }

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

       if ( this.mGeerbterTyp == SystemConstant.AgentType_INVESTOR )
       {
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
               // Wenn Buy, feedback only the BuyLimitReason
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
       if ( this.mGeerbterTyp == SystemConstant.AgentType_NOISETRADER )
       {
              // make decision at fisrt the Limit directly
              this.mCurrentAveragePrice = PriceContainer.getMovingAveragePrice( this.mDayofAveragePrice );
              double lastpp = PriceContainer.getLastPrice();

              String kurszusatzadjusthistory = "";

              finaldecision = RuleAnalyser.NoiseTraderMarketAnalysewithRule(this.mCurrentAveragePrice);

              double Maxdelta = Configurator.mConfData.mNoiseTrader_MaxLimitAdjust - Configurator.mConfData.mNoiseTrader_MinLimitAdjust;
              double mydelta  = this.mRandom.nextInt( (int) ( Maxdelta*1000 ) ) / 1000.0;
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

                    int vorzeichen = -1;
                    int temp = this.mRandom.nextInt(100);
                    if ( temp < 25 )
                    {
                       vorzeichen = 1;
                    }

                     BuyLimit = (int) ( BuyLimit * ( 1 + vorzeichen * this.mNoiseTraderDailyLimitAdjustProzent / 100.0 ) );
                     LimitReason = "VortagKurs(" + lastpp + ") * ( 1 - "+  HelpTool.DoubleTransfer( this.mNoiseTraderDailyLimitAdjustProzent, 4) + "%) Vortag KursZusatz=" + SystemConstant.TradeResult_Brief;
                     kurszusatzadjusthistory = ";BuyLimit decreased "+ this.mNoiseTraderDailyLimitAdjustProzent +"%;";
                  }
                  else
                  // Geld "G"
                  if ( this.mPerformedStatus.getYesterdayTradeResult() == SystemConstant.TradeResult_Geld )
                  {
                    // New Theorie
                    // Jeden Tag neue Adjust Prozent zu verwenden

                    int vorzeichen = 1;
                    int temp = this.mRandom.nextInt(100);
                    if ( temp < 25 )
                    {
                       vorzeichen = -1;
                    }

                    BuyLimit = (int) ( BuyLimit * ( 1 + vorzeichen * this.mNoiseTraderDailyLimitAdjustProzent/ 100.0 ) );
                    LimitReason = "VortagKurs(" + lastpp + ") * ( 1 + "+  HelpTool.DoubleTransfer( this.mNoiseTraderDailyLimitAdjustProzent,4) + "%) Vortag KursZusatz=" + SystemConstant.TradeResult_Geld;
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
                    // New Theorie 2008-01-09
                    int vorzeichen = -1;
                    int temp = this.mRandom.nextInt(100);
                    if ( temp < 25 )
                    {
                       vorzeichen = 1;
                    }

                    SellLimit = (int) ( SellLimit * ( 1 + vorzeichen * this.mNoiseTraderDailyLimitAdjustProzent / 100.0 ) );
                    LimitReason = "VortagKurs(" + lastpp + ") * ( 1 - "+  HelpTool.DoubleTransfer( this.mNoiseTraderDailyLimitAdjustProzent,4) + "/100) Vortag KursZusatz=" + SystemConstant.TradeResult_Brief;
                    kurszusatzadjusthistory = ";SellLimit decreased "+this.mNoiseTraderDailyLimitAdjustProzent+"%;";

                  }
                  else
                  // Geld "G"
                  if ( this.mPerformedStatus.getYesterdayTradeResult() == SystemConstant.TradeResult_Geld )
                  {

                    int vorzeichen = 1;
                    int temp = this.mRandom.nextInt(100);
                    if ( temp < 25 )
                    {
                       vorzeichen = -1;
                    }
                    SellLimit   = (int) ( SellLimit * ( 1 + vorzeichen * this.mNoiseTraderDailyLimitAdjustProzent / 100.0 ) );
                    LimitReason = "VortagKurs(" + lastpp + ") * ( 1 + "+  HelpTool.DoubleTransfer( this.mNoiseTraderDailyLimitAdjustProzent,4) + "%) Vortag KursZusatz=" + SystemConstant.TradeResult_Geld;
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
              this.detailedcalculationinfo = ";MyAveragePrice="+ nff.format2str(this.mCurrentAveragePrice)+ " YesterdayPrice=" +nff.format2str(lastpp) + kurszusatzadjusthistory;
         }
     }

  	 if ( finaldecision == 'M' )
  	 {

  		SimpleSingleOrder buyorders[]  = new SimpleSingleOrder[1];
  		SimpleSingleOrder sellorders[] = new SimpleSingleOrder[1];
  		buyorders[0]  = new SimpleSingleOrder();

  		buyorders[0].mAktion='B';
  		buyorders[0].mLimit = BuyLimit;
  		buyorders[0].mMenge = PossibleBuyMenge;

  		sellorders[0] = new SimpleSingleOrder();
  		sellorders[0].mAktion = 'S';
  		sellorders[0].mLimit  = SellLimit;
  		sellorders[0].mMenge  = PossibleSellMenge;
  		return new OrderBasicData( buyorders, sellorders, LimitReason );

  	 }
  	 else
     if ( finaldecision == 'B' )
     {
        return new OrderBasicData( finaldecision, PossibleBuyMenge, BuyLimit, LimitReason );
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

/**
 * Es gibt spezielle Handlung für BlankoAgent
 */

public void sendFeedBack2Store( OrderBasicData pOrderBasicData)
{
        // There are 4 cases:
        // 1.  'M' Mixed Order: It contains Buy and Sell Wishes, The Invstor generate such Order
        // 2.  'N' Nothing, only OK confirmation
        // 3.  'B' Buy  Order, NoiseTrader generate such a order
        // 4.  'S' Sell Order, NoiseTrader generate such a order

        char orderwish;
        MessageWrapper msgwrp = null;
        if ( pOrderBasicData.mFinaldecision == 'M' )
        {
          orderwish = SystemConstant.WishType_Mixed;
          msgwrp = MessageFactory.createAktienOrder
             (
                 this.mGeerbterTyp,  /// Für BlankoAgent wir müssen seine geerbte AgentType verwenden. !!!!
                 this.mDepotRecord.mAbsoluteGewinnProzent,
                 this.mDepotRecord.mRelativeGewinnProzent,
                 pOrderBasicData.mLimitCalcBase,
                 this.mAgent_TypeChanged,
                 this.mOriginalOperatorType,
                 pOrderBasicData.mBuyoptions,
                 pOrderBasicData.mSelloptions
             );
        }
        else
        if ( pOrderBasicData.mFinaldecision == 'N' )
        {
          orderwish = SystemConstant.WishType_Wait;
          msgwrp = MessageFactory.createAktienOrder
             (
                 this.mGeerbterTyp,
                 orderwish,
                 0,
                 0,
                 this.mDepotRecord.mAbsoluteGewinnProzent,
                 this.mDepotRecord.mRelativeGewinnProzent,
                 pOrderBasicData.mLimitCalcBase,
                 this.mAgent_TypeChanged,
                 this.mOriginalOperatorType
             );
        }
        else
        if ( pOrderBasicData.mFinaldecision == 'B' )
        {
          orderwish = SystemConstant.WishType_Buy;

          msgwrp = MessageFactory.createAktienOrder
             (
                 this.mGeerbterTyp,
                 pOrderBasicData.mFinaldecision,
                 pOrderBasicData.mMenge,
                 pOrderBasicData.mLimit,
                 this.mDepotRecord.mAbsoluteGewinnProzent,
                 this.mDepotRecord.mRelativeGewinnProzent,
                 pOrderBasicData.mLimitCalcBase,
                 this.mAgent_TypeChanged,
                 this.mOriginalOperatorType
             );
        }
        else
        {

          orderwish = SystemConstant.WishType_Sell;
          msgwrp = MessageFactory.createAktienOrder
             (
                 this.mGeerbterTyp,
                 pOrderBasicData.mFinaldecision,
                 pOrderBasicData.mMenge,
                 pOrderBasicData.mLimit,
                 this.mDepotRecord.mAbsoluteGewinnProzent,
                 this.mDepotRecord.mRelativeGewinnProzent,
                 pOrderBasicData.mLimitCalcBase,
                 this.mAgent_TypeChanged,
                 this.mOriginalOperatorType
             );
        }

        // 2007-05-20
        // Set Statue Change Info into Order for Statistic

        if ( this.mBlankoAgent_StateChanged )
        {
           if ( this.mBlankoAgentCurrentState == this.BLANKOAGENT_ACTIVESTATE )
           {
             msgwrp.setStatusToActivated();
           }
           else
           if ( this.mBlankoAgentCurrentState == this.BLANKOAGENT_INACTIVESTATE )
           {
             msgwrp.setStatusToDeactivated();
           }
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

 private void processAktienOrder( AktienTrade_Order pAktienOrder)
 {
   // save this price into PriceContainer
   PriceContainer.saveTraderPrice( this.mDataCC, pAktienOrder.mFinalPrice );
   String orderprocessedmsg = "";

   this.mLogger.debug(  this.myAgent.getLocalName() +"  "+  pAktienOrder.getOrderPerformedInfo() );

   if ( pAktienOrder.mBuyLimit > 0 )
   {
     // Save BuyLimit into the Container for future use.
     this.mAverageBuyPriceCalculator.addNewPrice( this.mDataCC, pAktienOrder.mBuyLimit );
   }
   else
   if ( pAktienOrder.mBuyLimit == SystemConstant.Limit_CheapestBuy )
   {
     // save the new Kurs als his BuyLimit
     this.mAverageBuyPriceCalculator.addNewPrice( this.mDataCC, pAktienOrder.mFinalPrice );
   }
   // sonst nicht zu speichern.

   if ( pAktienOrder.mBuyPerformed )
   {
     /*
     2007-05-03: This logic is not needed!
     if ( ! this.mFirstBuyPerformed )
     {
         // so Diser Buy ist mein erster Buy
         // der BuyPreis wird gespeichert für Spätere Benutzung
         this.mFirstBuyKurs       = pAktienOrder.mFinalPrice;
         this.mFirstBuyPerformed  = true;
     }
     */

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
        orderprocessedmsg = "No Buy/S Wish. new Price=" + pAktienOrder.mFinalPrice;
      }
  }
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

             System.out.println("****" + Day +"."+ LocalName +" " + SystemConstant.getOperatorTypeName( this.mGeerbterTyp ) +" says Buy " + WishMenge + ", Limit=" + Limit);
            }
             return WishMenge;
          }
          else
          {
               int nextpossiblemenge = (int) ( cash / Limit );
               if ( this.mTraceOrder )
               {
                 System.out.println("****" + Day +"."+ LocalName +" " + SystemConstant.getOperatorTypeName( this.mGeerbterTyp ) +" says Buy " + nextpossiblemenge + ", Limit=" + Limit );
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

             System.out.println("****" + Day +"."+ LocalName + " "+ SystemConstant.getOperatorTypeName( this.mGeerbterTyp ) +" says Sell "+ WishMenge + ", Limit=" + Limit);
            }

             return  WishMenge;
          }
          else
          {
             this.detailedcalculationinfo= this.detailedcalculationinfo + " rule says to sell " + WishMenge +" stk, only " + currentmenge + " sttk available";
             System.out.println("****" + Day +"."+ LocalName + " "+ SystemConstant.getOperatorTypeName( this.mGeerbterTyp ) +"  says Sell "+ currentmenge + ", Limit=" + Limit);
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

     return  true when Interrupt Command is received
             false otherwise
  */

private boolean exchangeGewinnStatus()
{
     String exchange_info_list[];
     // Send its status to its ReceiverPartner
     if ( this.mXPList == null )
     {
         //System.out.println("*****" + this.mDataCC +"." + this.myAgent.getLocalName()+":No Receiver Partner !");
     }
     else
     {
         // send status to its reciever partner
         this.sendStatus2Partner();
     }

     // Wait for the Status Message from SenderPartner that send the status to this Agent
     if ( this.mSenderPartnerNumber == 0 )
     {
        sendExchangeInfo2DataLoggerWhen_No_Parter();
        // No Partner
        return false;
     }

       //this.mLogger.debug(  this.mDataCC +"." + this.myAgent.getLocalName() + ": will get " + this.mSenderPartnerNumber + " reports from Nachbar");

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

       //System.out.println( "BlankAgent: Already received Partner Information: K="  + k);
       //System.out.println( "BlankAgent: to receive "  + this.mSenderPartnerNumber );

       while ( k < this.mSenderPartnerNumber )
       {

           ACLMessage aclmsg =  this.myAgent.blockingReceive(2000);
           if ( aclmsg == null )
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
                 System.out.println("Invalid message from " + aclmsg.getSender().getLocalName() );
                 System.out.println("Invalid message: " + aclmsg.toString() );
              }

              allstatus.add( (FriendStatus) msgwrp.mMessageContent );
              k++;
           }
       }

       // for Debug

       String StatusExhangeHistroy =
              this.getDayNoStr()+";" +
              this.myAgent.getLocalName()+";"+
              SystemConstant.getOperatorTypeName( this.mGeerbterTyp ) +";" ;

       // i am yet in InitState
       if ( this.mBlankoAgentCurrentState == this.BLANKOAGENT_INITSTATE )
       {
           // Nachbar Info sind nicht interessant
    	   // Agent berücksichtigt diese information nicht.
	   StatusExhangeHistroy = StatusExhangeHistroy + "Keine Kommunikation; Ich bin in InitState;";
           this.sendTypeChangeDecisionInfo_SingleLine( StatusExhangeHistroy );
           return false;
       }

       // Now i am inactive.
       if ( this.mBlankoAgentCurrentState == this.BLANKOAGENT_INACTIVESTATE )
       {
         StatusExhangeHistroy = StatusExhangeHistroy + "Keine Kommunikation; Ich bin Inaktive;";
         this.sendTypeChangeDecisionInfo_SingleLine( StatusExhangeHistroy );
         return false;
       }

       // 2007-12-10
       int wahrscheinlichkeit =  this.mRandom4Kommunikation.nextInt(100);
       this.mRandom4Kommunikation_CallTotal = this.mRandom4Kommunikation_CallTotal + 1;

       if ( wahrscheinlichkeit > Configurator.mConfData.mGewinnStatusExchangeProbability )
       {
           // Sleep
           StatusExhangeHistroy = StatusExhangeHistroy + "Keine Kommunikation; Entscheidung aus Wahrscheinlichkeit;";
           this.mBlankoAgent_Decision_Argument ="Wahrscheinlichkeit hat entschieden, Keine Strategie aus Nachbar zu übernehmen";
           this.sendTypeChangeDecisionInfo_SingleLine( StatusExhangeHistroy );
           return false;
       }
       else
       {
           mRandom4Kommunikation_Ja = mRandom4Kommunikation_Ja + 1;
           StatusExhangeHistroy = StatusExhangeHistroy + "Ja, Wahrscheinlichkeit erlaubt";
       }

       // i am active und can change my type if possible.

       String exchange_decision_info;
       String my_basic_info;

       if ( this.mGeerbterTyp == SystemConstant.AgentType_INVESTOR )
       {
           my_basic_info = nff.format2str(this.mUsedAbschlagProzent) +";NA;" ;
       }
       else
       if ( this.mGeerbterTyp == SystemConstant.AgentType_NOISETRADER )
       {
           my_basic_info = "NA;"+this.mDayofAveragePrice +";";
       }
       else
       {
           my_basic_info = "NA;NA;";
       }

       my_basic_info= my_basic_info +
                     this.mDepotRecord.mRelativeGewinn + ";" +
                     nff.format2str(this.mDepotRecord.mRelativeGewinnProzent) + ";" +
                     this.mDepotRecord.mAbsoluteGewinn + ";" +
                     nff.format2str(this.mDepotRecord.mAbsoluteGewinnProzent) ;

       //StatusExhangeHistroy = StatusExhangeHistroy +
       //                       this.getStringOfPartnerGewinnStatus( allstatus );

       // der BestNachbar in diesem Mal aufgrund StatusExchange
       BestOperatorFinder bopfinder = new BestOperatorFinder( allstatus );

       FriendStatus bestop = bopfinder.getBestGewinner();

       if  (  bestop.mType == SystemConstant.AgentType_BLANKOAGENT  )
       {
           // Sein Typ braucht man nicht zu berucksichtigen.
           exchange_decision_info = "BestNachbar ist Blanko, kein Typwechsel";
           exchange_info_list = new String [ allstatus.size() ];

           exchange_info_list[0] = StatusExhangeHistroy + ";" + exchange_decision_info + ";" + my_basic_info + ";";

           FriendStatus fss = ( FriendStatus ) allstatus.elementAt(0);

           // construct 1. line
           exchange_info_list[0]  = exchange_info_list[0]  + fss.mName +";" +
                                    SystemConstant.getOperatorTypeName( fss.mType ) + ";" +
                                    nff.format2str( fss.mAbschlagProzent ) + ";" +
                                    fss.mDayofAveragePrice + ";" +
                                    nff.format2str( fss.mGewinn  ) + ";" +
                                    nff.format2str( fss.mGewinnProzent ) + ";";

           // construct the further lines
           for ( int j=1; j< allstatus.size(); j++)
           {
             fss = ( FriendStatus ) allstatus.elementAt(j);
             //PartnerName;Typ;AbschlagProzent;Movingsdays;RelativeGewinn;RelativeGewinnProzent;" );
             exchange_info_list[ j] = " ; ; ; ; ; ; ; ; ; ; ; " +
                                      fss.mName +";" +
                                      SystemConstant.getOperatorTypeName( fss.mType ) + ";" +
                                      nff.format2str( fss.mAbschlagProzent ) + ";" +
                                      fss.mDayofAveragePrice + ";" +
                                      nff.format2str( fss.mGewinn  ) + ";" +
                                      nff.format2str( fss.mGewinnProzent ) + ";";
           }

           this.sendTypeChangeDecisionInfo( exchange_info_list );
           return false;
       }
       else
       {

       if (  this.mGeerbterTyp == SystemConstant.AgentType_BLANKOAGENT )
       {
           // 2007-05-02
           // BlankoAgent übermimmt die Strategie von BestNachbar wenn Nachbar Gewinn hat.
           // Wenn Nachbar zur Zeit Verlust hat, wird seine Strategie nicht übernommen.

           if (  bestop.mGewinnProzent > 0.0 )
           {

               this.mGeerbterTyp = bestop.mType;
               exchange_decision_info = "";

               if ( bestop.mType == SystemConstant.AgentType_INVESTOR )
               {
                     // change my Type to Investor, Keine Beschränkung
                     this.mGeerbterTyp = SystemConstant.AgentType_INVESTOR;

                     // 2007-09-12
                     this.mDepotRecord.mTypeChangeCounter = this.mDepotRecord.mTypeChangeCounter + 1;

                     // AbschlagProzent braucht neu zu generieren.
                     this.updateNewAbschlagProzent();
                     exchange_decision_info ="Changed to Investor";
               }
               else
               if ( bestop.mType == SystemConstant.AgentType_NOISETRADER )
               {
                     exchange_decision_info ="Changed to NoiseTrader";
                     this.mGeerbterTyp = SystemConstant.AgentType_NOISETRADER;

                     // 2007-09-12
                     this.mDepotRecord.mTypeChangeCounter = this.mDepotRecord.mTypeChangeCounter + 1;

                     this.updateMovingsDay();
               }
               this.mAgent_TypeChanged = true;
               this.mSchlechterCounter = 0;
               String firstline = StatusExhangeHistroy + ";" + exchange_decision_info + ";" + my_basic_info;
               this.sendExchangeInfo2DataLogger_Mit_mehr_PartnerInfo(firstline, allstatus );
           }
       }
       else
       {
                   exchange_decision_info ="";
                   if ( bestop.mGewinnProzent > this.mDepotRecord.mRelativeGewinnProzent )
                   {
                       this.mSchlechterCounter = this.mSchlechterCounter + 1;
                       if ( isSchlechterCounterOverLimit() )
                       {
                             //Agent has to change his Type:

                             exchange_decision_info = "Schlechter_Counter "+ this.mSchlechterCounter + ">= " + this.getSchlechterCounterLimit();

                             if ( bestop.mType == SystemConstant.AgentType_INVESTOR )
                             {
                                     // change my Type to Investor, Keine Beschränkung
                                     this.mGeerbterTyp = SystemConstant.AgentType_INVESTOR;

                                     this.mDepotRecord.mTypeChangeCounter = this.mDepotRecord.mTypeChangeCounter + 1;

                                     // AbschlagProzent braucht neu zu generieren.
                                     this.updateNewAbschlagProzent();
                                     this.mAgent_TypeChanged = true;
                                     this.mSchlechterCounter = 0;
                                     exchange_decision_info = exchange_decision_info + " changed to Investor";
                                     String firstline = StatusExhangeHistroy + ";" + exchange_decision_info + ";" + my_basic_info;
                                     this.sendExchangeInfo2DataLogger_Mit_mehr_PartnerInfo(firstline, allstatus );
                             }
                             else
                             if ( bestop.mType == SystemConstant.AgentType_NOISETRADER )
                             {
                                     exchange_decision_info = exchange_decision_info + " changed to NoiseTrader";
                                     this.mGeerbterTyp = SystemConstant.AgentType_NOISETRADER;

                                     this.mDepotRecord.mTypeChangeCounter = this.mDepotRecord.mTypeChangeCounter + 1;

                                     this.updateMovingsDay();
                                     this.mAgent_TypeChanged = true;
                                     this.mSchlechterCounter = 0;


                              }
                       }
                       else
                       {
                              exchange_decision_info = "Schlechter_Counter "+ this.mSchlechterCounter + "< " + this.getSchlechterCounterLimit();
                       }

                   }
                   else
                   {
                       // Nothing to do !!!
                       this.mSchlechterCounter = 0;
                   }

                   String firstline = StatusExhangeHistroy + ";" + exchange_decision_info + ";" + my_basic_info;
                   this.sendExchangeInfo2DataLogger_Mit_mehr_PartnerInfo(firstline, allstatus );
       }

  }
  return false;
}

private void sendStatus2Partner()
{
        MessageWrapper tt =
            MessageFactory.createFriendStatus
                           (this.myAgent.getLocalName(),
                            this.mGeerbterTyp,
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
        //System.out.println( "*****" + this.mDataCC +"." + this.myAgent.getLocalName()+ ": sent Status" + mystatus );
}

 private void  makeInitWork()
 {
        // step 1
        this.Registerme();
        this.sendBlankoAgentInitParameterToDataLogger();

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

        // This is Bug Stelle
        // 2007-10-01
        // this.mDepotRecord.AktienMarket_setInitPrice( lastprice );

        // A init status, but only lastprice is useful.
        this.mPerformedStatus = new OrderPerformedStatus('N',lastprice,'N', 0, lastprice );
        // Update Init Depot using the lastprice

        // prepare 1. Order
        OrderBasicData orderbasic = this.prepareOrder();
        // Send 1. Order to StockStore
        sendFeedBack2Store( orderbasic);
}

private String FriendstatusFormatter(String ss)
{

  String str= ss;
  str = str.replace(',', ';');
  str = str.replace('.', ',');

  return  str;

}


}