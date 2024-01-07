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
import org.apache.log4j.Logger;

import de.marketsim.SystemConstant;
import de.marketsim.message.*;

import de.marketsim.gui.*;

//import de.marketsim.stockdata.*;

import de.marketsim.util.*;
import de.marketsim.config.*;

public abstract class OperatorBaseBehavior extends CyclicBehaviour
{

  public static int KursStarkGestiegen = 1;
  public static int KursStarkGefallen  = -1;
  public static int KursChangeNormal   = 0;

  static String MessageName[] = {"Preis","Gewinn","Zinsatz"};

  static String  BLANKOAGENT_INITSTATE_NAME      = "initstate";
  static String  BLANKOAGENT_ACTIVESTATE_NAME    = "active   ";
  static String  BLANKOAGENT_INACTIVESTATE_NAME  = "inactive ";

  static String BlankoAgentStateName[] = {BLANKOAGENT_INITSTATE_NAME,
                                          BLANKOAGENT_ACTIVESTATE_NAME,
                                          BLANKOAGENT_INACTIVESTATE_NAME
                                          };
  public static int  BLANKOAGENT_INITSTATE      = 0;
  public static int  BLANKOAGENT_ACTIVESTATE    = 1;
  public static int  BLANKOAGENT_INACTIVESTATE  = 2;

  //public String mBlankoAgent_Stay_Init_Reason  = "";
  //public String mBlankoAgent_Activated_Reason  = "";
  //public String mBlankoAgent_Deactivated_Reason= "";

  public String mBlankoAgent_Decision_Argument = "";


  public  AverageBuyPriceCalculator  mAverageBuyPriceCalculator = new AverageBuyPriceCalculator();

  public boolean mBlankoAgent_StateChanged = false;

  public int mBlankoAgentCurrentState = BLANKOAGENT_INITSTATE;
  public int mBlankoAgentOldState     = BLANKOAGENT_INITSTATE;
  public int mBlankoAgentDeactivationDayCounter = 0;
  public int mBlankoAgentSellMenge_WhileDeactivation = 0;

  public boolean mAgent_TypeChanged   = false;

  public int  mGeerbterTyp             = SystemConstant.AgentType_BLANKOAGENT;  // Default BlankoAgent


  public Logger         mLogger;
  public List           mNameSortedPartner;

  public String         detailedcalculationinfo = "";
  public int            mDataCC                 = 0;
  public Vector         mXPList                 = null;
  public int            mSenderPartnerNumber    = 0;
  public DepotRecord    mDepotRecord = null;
  public String         mLocalAID = null;
  public int            mOperatorType         = SystemConstant.AgentType_INVESTOR; // Default
  public int            mOriginalOperatorType = SystemConstant.AgentType_INVESTOR; // Default

  public int            mDayIndexLength = 4;
  public int            mYesterdayprice_AktienMarket = 0;
  public double         mYesterdayprice_MoneyMarket  = 0.0;

  double                mCurrentInnererWert = 0.0;

  public double         mUsedAbschlagProzent = 0.0;
  //public double         mOriginalAbschlagProzent = 0.0;

  public int            mDayofAveragePrice = 0;
  //public int            mOriginalDayofAveragePrice = 0;

  public double         mCurrentAveragePrice = 0.0;

  /**  This variable will be used only by Investor **/
  public int            mInnererWertPotenzialVerbrauchenCounter = 0;



  public double            mletztegerechnetPotenzial = 0;

  public int            mInnererWertPotenzialFaktor = 0;

  public double         mKursChangeProzent = 0.0; // gegenüber ReferenzTag

  public int            mLetzteKursChangeCheckState = KursChangeNormal; // Nicht Bekant für Init
                                                        // +1: Stark gestiegen
                                                        // -1: Stark gefallen

  public  int           mTag_der_letzten_Potenzial_Anpassung  = -50000;  // noch nicht eingesetzt
  public  int           mTagCounter_SeitletzteAnpassung = 0;  // noch nicht eingesetzt

  public double         mPotenzialBase = 0.0;

  /**  This variable will be used only by Investor **/
  public double         mInnererWertRandomAbweichung = 0.0;
  /**  This variable will be used only by Investor **/
  public double         mEingesetztePotenzial        = 0.0;

  /**  This variable will be used only by Investor **/
  public double mInvestorVerwendetInnererWert=0.0;

  public Vector         mLimitHistory = new Vector();

  /**  This variable will be used only by NoiseTrader
   *   Kurszusatz-abhängige Adjust Prozent:
  */
  public double         mNoiseTraderDailyLimitAdjustProzent =0.0;

  public AgentInitParameter  mInitConfig = null;

  java.util.Random mRandom = new Random();

  public java.util.Random mRandom4Kommunikation = new Random();

  public int mRandom4Kommunikation_Ja = 0;

  public int mRandom4Kommunikation_CallTotal = 0;


  // public java.text.NumberFormat nff = HelpTool.getNumberFormat();
  // public java.text.NumberFormat englishnff = HelpTool.getNumberFormat("English");
  // public java.text.NumberFormat germannff  = HelpTool.getNumberFormat("Germany");

  protected DataFormatter nff =new de.marketsim.util.DataFormatter( Configurator.mConfData.mDataFormatLanguage );

  public OperatorBaseBehavior(Agent  pAgent)
  {
     super(pAgent);
     this.mLocalAID = this.myAgent.getHap();
     String agentname = this.myAgent.getLocalName().toUpperCase();
     if ( Configurator.mConfData.mHandelsday >999 )
     {
       this.mDayIndexLength = 4;
     }
  }

  public void addOneLimitRecord(LimitRecord pLimitRecord )
  {
      this.mLimitHistory.add(pLimitRecord);
      if ( this.mLimitHistory.size() > this.mInitConfig.mDays4AverageLimit + 2 )
      {
         this.mLimitHistory.removeElementAt(0);
      }
  }

  public void sendExchangeInfo2DataLoggerWhen_No_Parter()
  {
        String StatusExchangeInfo =
        this.getDayNoStr() + ";" +
        this.myAgent.getLocalName()+";"+
        SystemConstant.getOperatorTypeName( this.mGeerbterTyp ) +";Keine Kommunikation; Kein Nachbar send Status zu mir." ;

      String exchange_info_list[] = new String[1];
      exchange_info_list[0] = StatusExchangeInfo;
      this.sendTypeChangeDecisionInfo( exchange_info_list );
  }

  public void sendExchangeInfo2DataLogger_Mit_mehr_PartnerInfo(String pFirstLineBasic, Vector allstatus  )
  {

    String exchange_info_list[] = new String [ allstatus.size() ];

    exchange_info_list[0] = pFirstLineBasic + ";";
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
  }

  public void sendTypeChangeDecisionInfo(String  pTypeChangeDecisionInfoList[])
  {

    if (  ! Configurator.mConfData.mLogAgentExchangeHistoy   )
    {
      return;
    }

    ACLMessage  msgx = new ACLMessage( ACLMessage.INFORM );
    MessageWrapper msgwrp = MessageFactory.createGewinnStatusExchangeList( pTypeChangeDecisionInfoList );

    try
    {
        msgx.setContentObject( msgwrp );
        msgx.addReceiver( new AID ( "DataLogger", false) );
    }
    catch (Exception ex)
    {
        ex.printStackTrace();
    }
    //this.mLogger.debug( pTypeChangeDecisionInfo );
    myAgent.send( msgx );
  }

  public void sendTypeChangeDecisionInfo_SingleLine(String  pTypeChangeDecisionInfo)
  {

    if (  ! Configurator.mConfData.mLogAgentExchangeHistoy   )
    {
      return;
    }

    ACLMessage  msgx = new ACLMessage( ACLMessage.INFORM );

    String info_list[] = new String[1];
    info_list[0] = pTypeChangeDecisionInfo;
    MessageWrapper msgwrp = MessageFactory.createGewinnStatusExchangeList( info_list );

    try
    {
        msgx.setContentObject( msgwrp );
        msgx.addReceiver( new AID ( "DataLogger", false) );
    }
    catch (Exception ex)
    {
        ex.printStackTrace();
    }
    //this.mLogger.debug( pTypeChangeDecisionInfo );
    myAgent.send( msgx );
  }



  public String getDayNoStr()
  {
      String Dayindex ="0000"+ mDataCC;
      Dayindex.substring( Dayindex.length() - this.mDayIndexLength );
      return Dayindex;
  }

  public OrderBasicData PrepareInvestorOrderData()
  {

	  // this.mUsedAbschlagProzent muss immer ein Plus Value sein.
    this.mUsedAbschlagProzent = Math.abs(this.mUsedAbschlagProzent);

    // original: this.mCurrentInnererWert
    double  lastprice = PriceContainer.getLastPrice();
    this.mInvestorVerwendetInnererWert = this.getIndividuellInnererWert( this.mCurrentInnererWert,  lastprice ) ;

    SimpleSingleOrder buyoptions_at_prepare[] = new SimpleSingleOrder[4];

    buyoptions_at_prepare[0] = new SimpleSingleOrder();
    buyoptions_at_prepare[0].mAktion='B';
    buyoptions_at_prepare[0].mIsPossible = true;
    double dd = this.mInvestorVerwendetInnererWert *
                 ( 1 - this.mUsedAbschlagProzent / 100.0 );
    buyoptions_at_prepare[0].mLimit = (int) dd;
    buyoptions_at_prepare[0].mMenge = Configurator.mConfData.mInvestorOrderMengeStufe1;

    buyoptions_at_prepare[1] = new SimpleSingleOrder();
    buyoptions_at_prepare[1].mAktion='B';
    buyoptions_at_prepare[1].mIsPossible = true;
    dd = this.mInvestorVerwendetInnererWert *
                 ( 1 - this.mUsedAbschlagProzent / 100.0 -
                   Configurator.mConfData.mInvestorKurschangedprocentlimit1 /100.0 );
    buyoptions_at_prepare[1].mLimit = (int) dd;
    buyoptions_at_prepare[1].mMenge = Configurator.mConfData.mInvestorOrderMengeStufe2;

    buyoptions_at_prepare[2] = new SimpleSingleOrder();
    buyoptions_at_prepare[2].mAktion='B';
    buyoptions_at_prepare[2].mIsPossible = true;
    dd = this.mInvestorVerwendetInnererWert *
                 ( 1 - this.mUsedAbschlagProzent / 100.0 -
                   Configurator.mConfData.mInvestorKurschangedprocentlimit2 /100.0 );
    buyoptions_at_prepare[2].mLimit = (int) dd;
    buyoptions_at_prepare[2].mMenge = Configurator.mConfData.mInvestorOrderMengeStufe3;

    buyoptions_at_prepare[3] = new SimpleSingleOrder();
    buyoptions_at_prepare[3].mAktion='B';
    buyoptions_at_prepare[3].mIsPossible = true;
    dd = this.mInvestorVerwendetInnererWert *
                 ( 1 - this.mUsedAbschlagProzent / 100.0 -
                   Configurator.mConfData.mInvestorKurschangedprocentlimit3 /100.0 );
    buyoptions_at_prepare[3].mLimit = (int) dd;
    buyoptions_at_prepare[3].mMenge = Configurator.mConfData.mInvestorOrderMengeStufe4;

    SimpleSingleOrder selloptions_at_prepare[] = new SimpleSingleOrder[4];

    selloptions_at_prepare[0] = new SimpleSingleOrder();
    selloptions_at_prepare[0].mAktion='S';
    selloptions_at_prepare[0].mIsPossible = true;
    dd = this.mInvestorVerwendetInnererWert *
                 ( 1 + this.mUsedAbschlagProzent / 100.0 );
    selloptions_at_prepare[0].mLimit = (int) dd;
    selloptions_at_prepare[0].mMenge = Configurator.mConfData.mInvestorOrderMengeStufe1;

    selloptions_at_prepare[1] = new SimpleSingleOrder();
    selloptions_at_prepare[1].mAktion='S';
    selloptions_at_prepare[1].mIsPossible = true;
    dd = this.mInvestorVerwendetInnererWert *
                 ( 1 + this.mUsedAbschlagProzent / 100.0 +
                   Configurator.mConfData.mInvestorKurschangedprocentlimit1 /100.0 );
    selloptions_at_prepare[1].mLimit = (int) dd;
    selloptions_at_prepare[1].mMenge = Configurator.mConfData.mInvestorOrderMengeStufe2;

    selloptions_at_prepare[2] = new SimpleSingleOrder();
    selloptions_at_prepare[2].mAktion='S';
    selloptions_at_prepare[2].mIsPossible = true;
    dd = this.mInvestorVerwendetInnererWert *
                 ( 1 + this.mUsedAbschlagProzent / 100.0  +
                   Configurator.mConfData.mInvestorKurschangedprocentlimit2 /100.0 );
    selloptions_at_prepare[2].mLimit = (int) dd;
    selloptions_at_prepare[2].mMenge = Configurator.mConfData.mInvestorOrderMengeStufe3;

    selloptions_at_prepare[3] = new SimpleSingleOrder();
    selloptions_at_prepare[3].mAktion='S';

    selloptions_at_prepare[3].mIsPossible = true;
    dd = this.mInvestorVerwendetInnererWert *
                 ( 1 + this.mUsedAbschlagProzent / 100.0 +
                   Configurator.mConfData.mInvestorKurschangedprocentlimit3 /100.0 );
    selloptions_at_prepare[3].mLimit = (int) dd;
    selloptions_at_prepare[3].mMenge = Configurator.mConfData.mInvestorOrderMengeStufe4;
    return new OrderBasicData( buyoptions_at_prepare, selloptions_at_prepare, "IndividuellerInnererWert="  + nff.format2str( this.mInvestorVerwendetInnererWert ) + " AbschlagProz=" + nff.format2str(this.mUsedAbschlagProzent) );

  }

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
               this.mOperatorType,
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
                   this.mOperatorType,
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
          {      // B  or S
                 msgwrp = MessageFactory.createAktienOrder
                 (
                     this.mOperatorType,
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


  /**
   * calculate the average Limit from Limit History
   * @return
   */
  public LimitRecord getAverageLimit()
  {
       double sum_buylimit = 0.0;
       double sum_selllimit = 0.0;
       int index = this.mLimitHistory.size() - 1; //letzte data
       for ( int i=0; i<this.mInitConfig.mDays4AverageLimit; i++)
       {
          LimitRecord rec = ( LimitRecord ) this.mLimitHistory.elementAt( index  );
          sum_buylimit = sum_buylimit + rec.mBuyLimit;
          sum_selllimit = sum_selllimit + rec.mSellLimit;
          index = index - 1;
       }
       sum_buylimit = sum_buylimit / this.mLimitHistory.size();
       sum_selllimit = sum_selllimit / this.mLimitHistory.size();
       return new LimitRecord (0, sum_buylimit, sum_selllimit );
  }

  public void setSenderPartnerAnzahl(int pAnzahl)
  {
    this.mSenderPartnerNumber = pAnzahl;
  }

  public void setReceiverPartnerList(String pList )
  {

    Vector vv = new Vector();
    if ( pList == null )
    {
       return;
    }
    // remove the space at the end
    String ss = pList.trim();

    int j =ss.indexOf(";", ss.length()-1 );
    if ( j > 0 )
    {
      // remove it, at the end if it exists.
      ss = ss.substring(0,j);
    }
    while ( ss.length()>0)
    {
      j = ss.indexOf(";");
      if ( j>0 )
      {
         String name = ss.substring(0,j);
         ss = ss.substring(j+1);
         vv.add( new AID( name, false) );
      }
      else
      {
        vv.add( new AID( ss, false) );
        ss="";
      }
    }
     this.setXPList( vv );
  }

  public void setOperatorType(int  pOperatorType)
  {
     this.mOperatorType         = pOperatorType;
     this.mOriginalOperatorType = pOperatorType;
     // Diese Variable ist nur von BlankoAgent verwendet.
     this.mGeerbterTyp          = pOperatorType;
  }

  public void setInitParameter(AgentInitParameter pAgentInitParameter)
  {
     this.mInitConfig          = pAgentInitParameter;
     this.mUsedAbschlagProzent = this.mInitConfig.mAbschlagProzent;
     this.mDayofAveragePrice   = this.mInitConfig.mMovingsday;
     this.mLogger              = MsgLogger.getMsgLogger( this.mInitConfig.mName );
     this.mLogger.debug("Logger is created");

     // 2007-05-03 added
     this.mAverageBuyPriceCalculator.setTimeWindow( this.mInitConfig.mBlankoAgentDayOfIndexWindow );

  }

  public void setDepotRecord( DepotRecord  pDepotRecord)
  {
     this.mDepotRecord = pDepotRecord;
  }

  /**
   *
   */
  public void updateNewAbschlagProzent()
  {
    int diff = (int) ( ( Configurator.mConfData.mInvestor_DynamischAbschlageProzent_Max-
                 Configurator.mConfData.mInvestor_DynamischAbschlageProzent_Min + 1 ) * 100 );

    this.mUsedAbschlagProzent = Configurator.mConfData.mInvestor_DynamischAbschlageProzent_Min + mRandom.nextInt(diff) / 100.0;
    //return Configurator.mConfData.mInvestor_DynamischAbschlageProzent_Min + mRandom.nextInt(diff) / 100.0;

  }

  public void updateMovingsDay()
  {
    int diff = Configurator.mConfData.mNoiseTrader_MaxMovingDaysForAveragePrice-
               Configurator.mConfData.mNoiseTrader_MinMovingDaysForAveragePrice + 1;
    this.mDayofAveragePrice = Configurator.mConfData.mNoiseTrader_MinMovingDaysForAveragePrice + mRandom.nextInt(diff);
  }

  public void StopAgent()
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
        System.out.println( this.myAgent.getLocalName() + " has deregistered from DF Service");
  }

  public void setXPList( Vector pXPList )
  {
       this.mXPList = pXPList;
  }


  public void sendBlankoAgentInitParameterToDataLogger()
  {

// Depot,Tag;Type;
    String depotinfo = "Depot, ;BlankoAgent; Individuelle Parameter: TimeWindowForAverageBuyLimit(Day)=" + this.mInitConfig.mBlankoAgentDayOfIndexWindow + ";" +
                      "ActivationThreshold(%)="   + this.mInitConfig.mBlankoAgentKursUpProcent4Activation+";" +
                      "DeactivationThreshold(%)=" + this.mInitConfig.mBlankoAgentKursDownProcent4Deactivation+";";

    // send this depot to DataLogger
    ACLMessage aclmsg = new ACLMessage( ACLMessage.INFORM );
    aclmsg.addReceiver( new AID ( "DataLogger", false) );
    MessageWrapper msgwrp = new MessageWrapper();
    msgwrp.mMessageType = SystemConstant.MessageType_Depotstate;
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

  /**
   * This is a new feature
   *
   * @param pAktienOrder
   * @param pDatumIndex
   */
  public void sendDepotStatus( AktienTrade_Order pAktienOrder, int pDatumIndex )
  {

       if (  ! Configurator.mConfData.mLogAgentDailyDepot )
       {
          return;
       }

       if ( this.mOriginalOperatorType == SystemConstant.AgentType_INVESTOR )
       {
           DepotInfoContainer depotinfo = null;
           depotinfo = new DepotInfoContainer ( this.getInvestorDepotInfo( pAktienOrder, pDatumIndex ) );
           // send this depot to DataLogger
           ACLMessage aclmsg = new ACLMessage( ACLMessage.INFORM );
           aclmsg.addReceiver( new AID ( "DataLogger", false) );
           MessageWrapper msgwrp = new MessageWrapper();
           msgwrp.mMessageType = SystemConstant.MessageType_InvestorDepotstate;

           //this.detailedcalculationinfo;
           // simple format
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
             return;
       }

       String depot = null;
       if ( this.mOriginalOperatorType == SystemConstant.AgentType_BLANKOAGENT )
       {
           depot = this.getBlankoAgentDepotInfo( pAktienOrder, pDatumIndex );
       }
       else
       if ( this.mOriginalOperatorType == SystemConstant.AgentType_NOISETRADER )
       {
           depot =  this.getNoiseDepotInfo( pAktienOrder, pDatumIndex );
       }

       /**
        * 2007-10-01
        * Debug Code

       if (  this.myAgent.getLocalName().compareToIgnoreCase("V3" ) == 0 )
       {
         depotinfo = depotinfo + "; HistoryRecords=" + this.mDepotRecord.getDepotHistoryItemNumber()+"; Vallues=" + this.mDepotRecord.getDepotHistoryItem();
       }

       */

       // send this depot to DataLogger
       ACLMessage aclmsg = new ACLMessage( ACLMessage.INFORM );
       aclmsg.addReceiver( new AID ( "DataLogger", false) );
       MessageWrapper msgwrp = new MessageWrapper();
       msgwrp.mMessageType = SystemConstant.MessageType_Depotstate;

       //this.detailedcalculationinfo;
       // simple format
       msgwrp.mMessageContent = depot;

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


  private String getNoiseDepotInfo( AktienTrade_Order pAktienOrder, int pDatumIndex )
  {

       DataFormatter nff =new de.marketsim.util.DataFormatter( Configurator.mConfData.mDataFormatLanguage );
       // get the current depot and insert into Message
       String tt = "000000" + pDatumIndex;
       String depotinfo =
       "DEPOT," +
       tt.substring( tt.length() - this.mDayIndexLength ) + ";" +
       SystemConstant.getOperatorTypeName( this.mOperatorType ) +";" +
       this.mDepotRecord.AktienMarket_getCurrentAktienMenge()+";"+
       this.mDepotRecord.AktienMarket_getCurrentCash()+";"+
       this.mDepotRecord.AktienMarket_getCurrentDepot() + ";"+
       this.mDepotRecord.mRelativeGewinn+";"+

       nff.format2str( this.mDepotRecord.mRelativeGewinnProzent )+";"+

       this.mDepotRecord.mAbsoluteGewinn+";"+
       nff.format2str( this.mDepotRecord.mAbsoluteGewinnProzent )+";"+
       pAktienOrder.mOrderWish+";"+
       pAktienOrder.mBuyMenge+";"+
       pAktienOrder.mBuyLimit+";"+
       pAktienOrder.mSellMenge+";"+
       pAktienOrder.mSellLimit+";"+
       pAktienOrder.mFinalPrice + ";" +
       pAktienOrder.mTradeResult + ";" +
       (int)this.mCurrentInnererWert + ";" +
       (int)this.mInvestorVerwendetInnererWert + ";" ;

       String IndividuellInnereWertBerechnendetail = "";

       IndividuellInnereWertBerechnendetail = "0;0;0;0;0;0;0;";

       depotinfo = depotinfo + IndividuellInnereWertBerechnendetail;

       if ( pAktienOrder.mBuyPerformed )
       {
         depotinfo = depotinfo + SystemConstant.WishType_Buy + ";" ;
       }
       else
       if ( pAktienOrder.mSellPerformed )
       {
         depotinfo = depotinfo + SystemConstant.WishType_Sell + ";" ;
       }
       else
       {
         depotinfo = depotinfo + " ;" ;
       }

       depotinfo = depotinfo + pAktienOrder.mTradeMenge  + ";";

       depotinfo= depotinfo + "NA;" + nff.format2str( this.mCurrentAveragePrice )+";" + this.mDayofAveragePrice+";";

       // Früher haben wir definiert:
       // Jeder NoiseTrader hat einen Zufall Wert, der beim Anfang erzeugt wird, danach bleibt dieser Wert unverändert.
       //depotinfo = depotinfo +  germannff.format( this.mInitConfig.mLimitAdjust ) + ";" ;
       // Neue Theoroe:
       // Jeden Tag jeder NoiseTrader bekommt einen zufälligen Werte:

       String NoiseTraderLimitAdjust = "";
       NoiseTraderLimitAdjust =  nff.format2str( this.mNoiseTraderDailyLimitAdjustProzent );
       depotinfo = depotinfo + NoiseTraderLimitAdjust + ";" ;
       depotinfo = depotinfo + this.detailedcalculationinfo;
       return depotinfo;
  }

  private String[] getInvestorDepotInfo( AktienTrade_Order pAktienOrder, int pDatumIndex )
  {
       // get the current depot and insert into Message
       String depotinfo4 = "DEPOT," + this.getDayNoStr() +";" +  SystemConstant.getOperatorTypeName( this.mOperatorType ) +";;;;;;;;" ;
       String depotinfo3 = depotinfo4;
       String depotinfo2 = depotinfo4;

       String depotinfo1 = "DEPOT," + this.getDayNoStr() +";" + SystemConstant.getOperatorTypeName( this.mOperatorType ) +";" +
       this.mDepotRecord.AktienMarket_getCurrentAktienMenge()+";"+
       this.mDepotRecord.AktienMarket_getCurrentCash()+";"+
       this.mDepotRecord.AktienMarket_getCurrentDepot() + ";"+
       this.mDepotRecord.mRelativeGewinn+";"+

       nff.format2str( this.mDepotRecord.mRelativeGewinnProzent )+";"+

       this.mDepotRecord.mAbsoluteGewinn+";"+
       nff.format2str( this.mDepotRecord.mAbsoluteGewinnProzent )+";";

       SimpleSingleOrder[] Buyorders  =  pAktienOrder.getBuyOrders();
       SimpleSingleOrder[] Sellorders =  pAktienOrder.getSellOrders();

       if ( Buyorders.length > 0 )
       {

       depotinfo1 = depotinfo1 + Buyorders[0].mAktion +";"+
       Buyorders[0].mMenge+";"+
       Buyorders[0].mLimit+";"+
       Sellorders[0].mAktion + ";"+
       Sellorders[0].mMenge+";"+
       Sellorders[0].mLimit+";"+
       pAktienOrder.mFinalPrice + ";" +
       pAktienOrder.mTradeResult + ";" +

       (int)this.mCurrentInnererWert + ";" +
       (int)this.mInvestorVerwendetInnererWert + ";" ;

       String IndividuellInnereWertBerechnendetail = "";

       IndividuellInnereWertBerechnendetail =

       nff.format2str( this.mInnererWertRandomAbweichung ) + ";" +
       nff.format2str( this.mKursChangeProzent ) + ";+/-" +
       nff.format2str( Configurator.mConfData.mInvestor_KursAnderung_Schwelle ) +";"+
       nff.format2str( this.mEingesetztePotenzial ) + ";" +
       this.mInnererWertPotenzialVerbrauchenCounter+";" +
       nff.format2str( this.mPotenzialBase ) +";" +
       this.mTagCounter_SeitletzteAnpassung +";";

      depotinfo1 = depotinfo1 + IndividuellInnereWertBerechnendetail;

       if ( pAktienOrder.mBuyPerformed )
       {
         depotinfo1 = depotinfo1 + SystemConstant.WishType_Buy + ";" ;
       }
       else
       if ( pAktienOrder.mSellPerformed )
       {
         depotinfo1 = depotinfo1 + SystemConstant.WishType_Sell + ";" ;
       }
       else
       {
         depotinfo1 = depotinfo1 + " ;" ;
       }

       depotinfo1 = depotinfo1 + pAktienOrder.mTradeMenge  + ";";

       depotinfo1 = depotinfo1 + nff.format2str( this.mUsedAbschlagProzent ) + ";NA;NA;";

       // Früher haben wir definiert:
       // Jeder NoiseTrader hat einen Zufall Wert, der beim Anfang erzeugt wird, danach bleibt dieser Wert unverändert.
       //depotinfo = depotinfo +  germannff.format( this.mInitConfig.mLimitAdjust ) + ";" ;
       // Neue Theoroe:
       // Jeden Tag jeder NoiseTrader bekommt einen zufälligen Werte:

       depotinfo1 = depotinfo1 + "0;" ;
       depotinfo1 = depotinfo1 + this.detailedcalculationinfo;

       if ( Buyorders.length == 4 )
       {

           depotinfo2 = depotinfo2 + Buyorders[1].mAktion +";" +
           Buyorders[1].mMenge+";"+
           Buyorders[1].mLimit+";"+
           Sellorders[1].mAktion + ";"+
           Sellorders[1].mMenge+";"+
           Sellorders[1].mLimit+";";

           depotinfo3 = depotinfo3 + Buyorders[2].mAktion +";"+
           Buyorders[2].mMenge+";"+
           Buyorders[2].mLimit+";"+
           Sellorders[2].mAktion + ";"+
           Sellorders[2].mMenge+";"+
           Sellorders[2].mLimit+";";

           depotinfo4 = depotinfo4 + Buyorders[3].mAktion +";"+
           Buyorders[3].mMenge+";"+
           Buyorders[3].mLimit+";"+
           Sellorders[3].mAktion + ";"+
           Sellorders[3].mMenge+";"+
           Sellorders[3].mLimit+";";
       }

       }

       String infobundel[];

       if ( Buyorders.length == 4 )
       {
    	   infobundel = new String[4];
    	   infobundel[0] = depotinfo1;
    	   infobundel[1] = depotinfo2;
    	   infobundel[2] = depotinfo3;
    	   infobundel[3] = depotinfo4;
       }
       else
       {
    	   infobundel = new String[1];
    	   infobundel[0] = depotinfo1;
       }

       return infobundel;

  }


  private String getBlankoAgentDepotInfo(  AktienTrade_Order pAktienOrder, int pDatumIndex )
  {

           DataFormatter nff =new de.marketsim.util.DataFormatter( Configurator.mConfData.mDataFormatLanguage );
           // get the current depot and insert into Message
           String tt = "000000" + pDatumIndex;
           String depotinfo ="DEPOT," +
           tt.substring( tt.length() - this.mDayIndexLength ) + ";" +
           SystemConstant.getOperatorTypeName( this.mGeerbterTyp ) + ";" +

           this.BlankoAgentStateName[ this.mBlankoAgentCurrentState ] +";" ;

           if ( this.mBlankoAgentCurrentState == this.BLANKOAGENT_INITSTATE )
           {
             depotinfo = depotinfo + this.mBlankoAgent_Decision_Argument + ";";
             depotinfo= depotinfo +
             this.mDepotRecord.AktienMarket_getCurrentAktienMenge()+";"+
             this.mDepotRecord.AktienMarket_getCurrentCash()+";"+
             this.mDepotRecord.AktienMarket_getCurrentDepot() + ";"+
             this.mDepotRecord.mRelativeGewinn+";"+
             nff.format2str( this.mDepotRecord.mRelativeGewinnProzent )+";"+
             this.mDepotRecord.mAbsoluteGewinn+";"+
             nff.format2str( this.mDepotRecord.mAbsoluteGewinnProzent )+";  ;   ;   ;   ;  ;"+
             this.mDepotRecord.mCurrentPrice +";" ;
             return depotinfo;
           }
           else
           {
             depotinfo = depotinfo + this.mBlankoAgent_Decision_Argument + ";";
           }

           depotinfo= depotinfo +
           this.mDepotRecord.AktienMarket_getCurrentAktienMenge()+";"+
           this.mDepotRecord.AktienMarket_getCurrentCash()+";"+
           this.mDepotRecord.AktienMarket_getCurrentDepot() + ";"+
           this.mDepotRecord.mRelativeGewinn+";"+
           nff.format2str( this.mDepotRecord.mRelativeGewinnProzent )+";"+
           this.mDepotRecord.mAbsoluteGewinn+";"+
           nff.format2str( this.mDepotRecord.mAbsoluteGewinnProzent )+";"+
           pAktienOrder.mOrderWish+";"+
           pAktienOrder.mBuyMenge+";"+
           pAktienOrder.mBuyLimit+";"+
           pAktienOrder.mSellMenge+";"+
           pAktienOrder.mSellLimit+";"+
           pAktienOrder.mFinalPrice + ";" +
           pAktienOrder.mTradeResult + ";" +
           nff.format2str(this.mCurrentInnererWert) + ";" +
           nff.format2str(this.mInvestorVerwendetInnererWert) + ";" ;

           String IndividuellInnereWertBerechnendetail = "";
           if ( this.mGeerbterTyp == SystemConstant.AgentType_INVESTOR )
           {
                   IndividuellInnereWertBerechnendetail =
                   nff.format2str( this.mInnererWertRandomAbweichung ) + ";" +
                   nff.format2str( this.mEingesetztePotenzial ) + ";" +
                   this.mInnererWertPotenzialVerbrauchenCounter+";" ;
           }
           else
           {
               IndividuellInnereWertBerechnendetail = "0;0;0;";
           }

          depotinfo = depotinfo + IndividuellInnereWertBerechnendetail;

           if ( pAktienOrder.mBuyPerformed )
           {
             depotinfo = depotinfo + SystemConstant.WishType_Buy + ";" ;
           }
           else
           if ( pAktienOrder.mSellPerformed )
           {
             depotinfo = depotinfo + SystemConstant.WishType_Sell + ";" ;
           }
           else
           {
             depotinfo = depotinfo + " ;" ;
           }

           depotinfo = depotinfo + pAktienOrder.mTradeMenge  + ";";

           if ( this.mGeerbterTyp == SystemConstant.AgentType_INVESTOR )
           {
             depotinfo= depotinfo + nff.format2str( this.mUsedAbschlagProzent ) + ";NA;NA;";
           }
           else
           if ( this.mGeerbterTyp == SystemConstant.AgentType_NOISETRADER )
           {
             depotinfo= depotinfo + "NA;" + nff.format2str( this.mCurrentAveragePrice )+";" +
                        this.mDayofAveragePrice+";";
           }
           else
           {
             // Hat noch kein Strategie, so nichts zu berichten
           }
           // Früher haben wir definiert:
           // Jeder NoiseTrader hat einen Zufall Wert, der beim Anfang erzeugt wird, danach bleibt dieser Wert unverändert.
           //depotinfo = depotinfo +  germannff.format( this.mInitConfig.mLimitAdjust ) + ";" ;

           // Neu Theoroe:
           // Jeden Tag jeder NoiseTrader bekommt einen zufälligen Werte:

           String NoiseTraderLimitAdjust = "";
           if ( this.mGeerbterTyp == SystemConstant.AgentType_INVESTOR )
           {
              NoiseTraderLimitAdjust = "0";
           }
           else
           if ( this.mGeerbterTyp == SystemConstant.AgentType_NOISETRADER )
           {
              NoiseTraderLimitAdjust = nff.format2str( this.mNoiseTraderDailyLimitAdjustProzent );
           }
           else
           {
              NoiseTraderLimitAdjust = "0";
           }
           depotinfo = depotinfo + NoiseTraderLimitAdjust + ";" ;

           depotinfo = depotinfo + this.detailedcalculationinfo;

           return depotinfo;
  }

  public double getIndividuellInnererWert (double pCurrentInnererwert, double pLastPrice )
  {
      double delta = Configurator.mConfData.mInvestor_InnererWertIntervall_Obengrenz -
                     Configurator.mConfData.mInvestor_InnererWertIntervall_Untergrenz;

      double dd = this.mRandom.nextInt( (int) (delta * 1000) ) / 1000.0;

      this.mInnererWertRandomAbweichung = Configurator.mConfData.mInvestor_InnererWertIntervall_Untergrenz + dd;

      double temp_individuell_innererwert = pCurrentInnererwert * ( 1 + this.mInnererWertRandomAbweichung / 100.0 );
      double finalInnererWert = temp_individuell_innererwert;

      double Preis_Of_ReferenzTag = PriceContainer.getTraderPrice( this.mDataCC - Configurator.mConfData.mInvestor_KursAnderung_ReferenzTag );
      this.mKursChangeProzent =  (pLastPrice -  Preis_Of_ReferenzTag) * 100.0 / Preis_Of_ReferenzTag;

      //double Potenzial = temp_individuell_innererwert * Configurator.mConfData.mInvestor_AktuellerInnererWert_Potenzial / 100.0;

      int  Abstand_zum_letzten_Anpassung;

      // Preis ist stark gestiegen und über SchwelleWert
      if ( this.mKursChangeProzent > Configurator.mConfData.mInvestor_KursAnderung_Schwelle )
      {
    	  	 // Calculate Potenzial
    	     if ( this.mInnererWertPotenzialVerbrauchenCounter == 0 )
    	     {
    	    	 this.mPotenzialBase = pCurrentInnererwert * 0.2 * Configurator.mConfData.mInvestor_AktuellerInnererWert_Potenzial / 100.0;
    	     }
    	     else
    	     {
    	    	 // Nothing to do
    	     }

             // Anpassung ist noch erlaubt ?
             if ( this.mInnererWertPotenzialVerbrauchenCounter < 5  )
             {
            	 // Ja
            	 // check if the Abstand is bigger than Windows
            	 Abstand_zum_letzten_Anpassung = this.mDataCC - this.mTag_der_letzten_Potenzial_Anpassung;
                 if ( Abstand_zum_letzten_Anpassung < Configurator.mConfData.mInvestor_KursAnderung_ReferenzTag )
                 {
                   //2007-12-03 16:00
                   // Zeit zur letzten Anpassnung ist zu kurz.
                   // Potenzial darf nicht angepasst werden.
                   // Nur dieser Counter erhöht
                   this.mTagCounter_SeitletzteAnpassung = this.mTagCounter_SeitletzteAnpassung + 1;

                 }
                 else
                 {
            	      // Ja, Anpassung is erlaubt
                      this.mLetzteKursChangeCheckState = this.KursStarkGestiegen;
                      this.mTag_der_letzten_Potenzial_Anpassung = this.mDataCC;
                      this.mInnererWertPotenzialVerbrauchenCounter = this.mInnererWertPotenzialVerbrauchenCounter + 1;
                      this.mEingesetztePotenzial = this.mPotenzialBase * this.mInnererWertPotenzialVerbrauchenCounter;
                      this.mTagCounter_SeitletzteAnpassung = 0;

                 }
                 //this.mEingesetztePotenzial = Potenzial * 0.2 * this.mInnererWertPotenzialVerbrauchenCounter ;
                 finalInnererWert = temp_individuell_innererwert + this.mEingesetztePotenzial;
             }
             else
             {
                 // Nur dieser Counter erhöht
                 this.mTagCounter_SeitletzteAnpassung = this.mTagCounter_SeitletzteAnpassung + 1;
             }
      }
      else
      // Preis ist stark gesturzt
      if ( this.mKursChangeProzent < (-1.0) * Configurator.mConfData.mInvestor_KursAnderung_Schwelle )
      {
 	  	 	// Calculate Potenzial
 	     	if ( this.mInnererWertPotenzialVerbrauchenCounter == 0 )
 	     	{
 	     		this.mPotenzialBase = pCurrentInnererwert * 0.2 * Configurator.mConfData.mInvestor_AktuellerInnererWert_Potenzial / 100.0;
 	     	}
 	     	else
 	     	{
 	     		// Nothing to do
 	     	}

            // Kapazität ist noch erlaubt?
            if ( this.mInnererWertPotenzialVerbrauchenCounter > -5 )
            {
            	Abstand_zum_letzten_Anpassung = this.mDataCC - this.mTag_der_letzten_Potenzial_Anpassung;
                if (  Abstand_zum_letzten_Anpassung > Configurator.mConfData.mInvestor_KursAnderung_ReferenzTag )
                {
                  // Ja, mache eine neue Anpassung
                  // verwende das letzte eingesetzte Potenzial
                  // Brauch nicht neu zu berechnen
               	  this.mInnererWertPotenzialVerbrauchenCounter = this.mInnererWertPotenzialVerbrauchenCounter - 1;
               	  this.mTag_der_letzten_Potenzial_Anpassung = this.mDataCC;
               	  this.mLetzteKursChangeCheckState = this.KursStarkGefallen;
               	  this.mEingesetztePotenzial  = this.mPotenzialBase * this.mInnererWertPotenzialVerbrauchenCounter ;
                  this.mTagCounter_SeitletzteAnpassung = 0;
                }
                else
                {
                	// Nicht mehr
                	// Anpassung findet nicht sttat
                        // Nur dieser Counter erhöht
                       this.mTagCounter_SeitletzteAnpassung = this.mTagCounter_SeitletzteAnpassung + 1;
                }
            }
            else
            {
            	// Nothing to do !!
            	// this.mEingesetztePotenzial wird nicht angepasst
                // Nur dieser Counter erhöht
                this.mTagCounter_SeitletzteAnpassung = this.mTagCounter_SeitletzteAnpassung + 1;
            }
            finalInnererWert = temp_individuell_innererwert + this.mEingesetztePotenzial;
      }
      else
      {
           this.mTagCounter_SeitletzteAnpassung = this.mTagCounter_SeitletzteAnpassung + 1;
           if ( this.mTagCounter_SeitletzteAnpassung >= Configurator.mConfData.mInvestor_KursAnderung_ReferenzTag * 5 )
           {
               // Versuch this.mInnererWertPotenzialVerbrauchenCounter auf 0 zu bringen
               if ( this.mInnererWertPotenzialVerbrauchenCounter > 0 )
               {
                 this.mInnererWertPotenzialVerbrauchenCounter = this.mInnererWertPotenzialVerbrauchenCounter - 1;
                 this.mEingesetztePotenzial  = this.mPotenzialBase * this.mInnererWertPotenzialVerbrauchenCounter ;
                 this.mTagCounter_SeitletzteAnpassung = 0;
               }
               else
               if ( this.mInnererWertPotenzialVerbrauchenCounter < 0 )
               {
                 this.mInnererWertPotenzialVerbrauchenCounter = this.mInnererWertPotenzialVerbrauchenCounter + 1;
                 this.mEingesetztePotenzial  = this.mPotenzialBase * this.mInnererWertPotenzialVerbrauchenCounter ;
                 this.mTagCounter_SeitletzteAnpassung = 0;
               }
           }
           finalInnererWert = temp_individuell_innererwert + this.mEingesetztePotenzial;
      }
      return finalInnererWert;
  }

  private String getBlankoAgentStateChangedReason()
  {
     return this.mBlankoAgent_Decision_Argument;
     /*
     2007-11-07

     if ( ! this.mBlankoAgent_StateChanged )
     {
         // Nichts zu geben
         return "";
     }
     else
     {
         if ( this.mBlankoAgentCurrentState == this.BLANKOAGENT_INITSTATE )
         {
             return this.mBlankoAgent_Stay_Init_Reason;
         }
         else
         if ( this.mBlankoAgentCurrentState == this.BLANKOAGENT_ACTIVESTATE )
         {
             return this.mBlankoAgent_Activated_Reason;
         }
         else
         {
             return this.mBlankoAgent_Deactivated_Reason;
         }
     }
    */
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
           this.mDepotRecord.mRelativeGewinn + ";" +
           nff.format2str( this.mDepotRecord.mRelativeGewinnProzent ) + ";" +
           this.mDepotRecord.mAbsoluteGewinn+";"+
           nff.format2str( this.mDepotRecord.mAbsoluteGewinnProzent ) + ";" +

           pCashOrder.mOrderWish + ";" +
           pCashOrder.mCash2 +";" +
           nff.format2str(pCashOrder.mBuyLimit)+";"+
           nff.format2str(pCashOrder.mSellLimit)+";"+
           nff.format2str(pCashOrder.mFinalKurs) + ";" +

           pCashOrder.mTradeResult + ";" +

           nff.format2str( this.mCurrentInnererWert) + ";" ;

           if ( pCashOrder.mBuyPerformed )
           {
             depotinfo = depotinfo + SystemConstant.WishType_Buy + ";" ;
           }
           else
           if ( pCashOrder.mSellPerformed )
           {
             depotinfo = depotinfo + SystemConstant.WishType_Sell + ";" ;
           }
           else
           {
             depotinfo = depotinfo + "NotPerformed;" ;
           }

         depotinfo = depotinfo + nff.format2str(pCashOrder.mInvolvedCash1)+";" ;
         depotinfo = depotinfo + nff.format2str(pCashOrder.mTax_Fixed)+";" ;
         depotinfo = depotinfo + nff.format2str(pCashOrder.mTax_Extra)+";" ;
         depotinfo = depotinfo + pCashOrder.mTradeCash2+";" ;

         if ( this.mOperatorType == SystemConstant.AgentType_INVESTOR )
         {
            depotinfo= depotinfo + nff.format2str( this.mUsedAbschlagProzent ) + ";NA;NA;";
         }
         else
         {
            depotinfo= depotinfo + "NA;" + nff.format2str( this.mCurrentAveragePrice )+";" + this.mDayofAveragePrice+";";
         }

         depotinfo = depotinfo +  nff.format2str( this.mInitConfig.mLimitAdjust ) + ";" ;
         depotinfo = depotinfo + this.detailedcalculationinfo;

         this.mLogger.debug( "KKK "+this.myAgent.getLocalName()+": DepotInfo " + depotinfo );

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

  public void Registerme()
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
    System.out.println("Agent "  +  this.myAgent.getLocalName() + " has registered on StockStore as "  + SystemConstant.getOperatorTypeName( this.mOperatorType ) );
  }

  public void UnRegisterMe()
  {
    ACLMessage msg= new ACLMessage( ACLMessage.INFORM );
    MessageWrapper msgwrapper = MessageFactory.createUnRegisterMessage
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
    System.out.println("UNREGISTERME has been sent.");
}

public void processFirst300InitData( First300InitData  pFirst300data)
{
    if (Configurator.mConfData.mMarketMode==Configurator.mConfData.mMoneyMarket)
    {
        PriceContainer.saveFirst300Kurs( pFirst300data.mDoubleData );
    }
    else
    {
        PriceContainer.saveFirst300Price(pFirst300data.mIntData);
    }

    if ( Configurator.istAktienMarket() )
    {
       // The price of No. 300 price data
        // save this price, it will be used for next order
        this.mYesterdayprice_AktienMarket = pFirst300data.mIntData[ pFirst300data.mIntData.length -1 ];

        this.mDepotRecord.AktienMarket_setInitPrice( this.mYesterdayprice_AktienMarket );

        AktienTrade_Order aktienorder = new AktienTrade_Order( this.mOperatorType, SystemConstant.WishType_Wait, 0, this.mOriginalOperatorType );

        // 2007-10-01
        aktienorder.mFinalPrice = this.mYesterdayprice_AktienMarket;
        // Init Depot Status
        this.sendDepotStatus( aktienorder, 0 );
    }
    else
    {
        this.mYesterdayprice_MoneyMarket = pFirst300data.mDoubleData[ pFirst300data.mDoubleData.length -1 ];
        this.mDepotRecord.MoneyMarket_setInitKurs( this.mYesterdayprice_MoneyMarket );
        CashTrade_Order cashorder = new CashTrade_Order( this.mOperatorType, SystemConstant.WishType_Wait, 0,0, "" );
        this.sendDepotStatus( cashorder );
    }
}

public  int getSleepProzent()
{

  // Run-Parameter have also to be changed
  if ( this.mOperatorType == SystemConstant.AgentType_INVESTOR )
  {
            return Configurator.mConfData.mInvestorSleepProcent;
  }
  else
  // NoiseTrader
  {
          return Configurator.mConfData.mNoiseTrader_SleepProcent;
  }

}

public String getStringOfPartnerGewinnStatus(Vector pStatusList)
{
    if ( pStatusList.size() == 0 )
    {
      return "";
    }

    Hashtable hst = new Hashtable();
    String []  namelist = new String[pStatusList.size()];
    for ( int i=0;i<pStatusList.size();i++)
    {
       FriendStatus oneStatus = (FriendStatus) pStatusList.elementAt(i);
       hst.put( oneStatus.mName, oneStatus );
       namelist[i] =oneStatus.mName;
    }
    // prepare for sorting. A List Object is required.
    List sortednamelist = Arrays.asList( namelist );
    // do sorting finally
    Collections.sort( sortednamelist );
    String result = "";
    for (int i=0; i< sortednamelist.size(); i++)
    {
      FriendStatus oneStatus = (FriendStatus)  hst.get( sortednamelist.get(i) ) ;
      result = result +  oneStatus.mName+";" +
               SystemConstant.getOperatorTypeName(  oneStatus.mType ) +";" ;
      if (  oneStatus.mType == SystemConstant.AgentType_INVESTOR )
      {
          result = result + this.nff.format2str( oneStatus.mAbschlagProzent )+";NA;" ;
      }
      else
      {
          result = result + "NA;" + oneStatus.mDayofAveragePrice + ";" ;
      }
      result = result + this.nff.format2str( oneStatus.mGewinn ) + ";" +
               this.nff.format2str( oneStatus.mGewinnProzent ) +";";
    }
    return result;
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

}
