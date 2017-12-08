/**
 * ueberschrift:
 * Beschreibung:
 * Copyright: Copyright (c) 2003
 * Organisation:
 * @author Wang
 * @version 1.0
 *
 * This class contains the parameter of business of agents
 * Investor, NoiseTrader and RandomTrader
 */

package de.marketsim.config;

import javax.swing.*;
import java.io.*;
import java.util.Properties;
import java.util.Vector;
import java.util.Hashtable;
import de.marketsim.SystemConstant;
import de.marketsim.config.XmlHelpTool;

public class ConfData implements java.io.Serializable
{

  public static int  mAktienMarket = 1  ; //"Aktien Market";
  public static int  mMoneyMarket  = 2  ; //"Cash Market";

  public  boolean mTobintaxAgentAktive = true;  // Default mode
  public  int     mMarketMode          = mAktienMarket;  // Default mode

 // online changeable parameter
  public  boolean mStepByStep = false; // Default
  public  SynchronObject mSynchronPoint = new SynchronObject();

  public  boolean mDAXisReady = false;
  public  boolean mNeedNotifyDAX = true;

  public  String mCurrentOrderBookHTMLFile ="";
  public  String mConfigFile = null;

  public  String mDataFormatLanguage = SystemConstant.DataFormatLanguage_German;

  Properties AgentConfiguration = null;

  boolean    mFileReadSuccessful = false;
  public  boolean  mParameterReady = false;

  public  Vector  mDepotFileList = new Vector();
  public  Vector  mExchangeHistoryFileList = new Vector();

  public  String[]  mDistributionList = new String[0];

  public  String  mTimeStamp = "";

  //public  String  mNetworkfilenamelist ="";
  public  String  mNetworkfileCurrentUsed           = null;
  public  String  mNetworkfile_OhnePfad_CurrentUsed = null;

  public  String  mPfadSeperator = "/";

  public  int    mGesamtAgentANzahl = 0;

  public  int    mAnzahlTotalAgents = 0;  //mAnzahlTotalAgents

  public  int    mNodesInNetwork    = 0;   // mNodesInNetwork must be equal to mAnzahlInvestor + mAnzahlNoiseTrader
  public  int    mAnzahlInvestor    = 0;
  public  int    mAnzahlNoiseTrader = 0;
  public  int    mAnzahlBlankoAgent = 0;

  public  int    mAnzahlRandomTrader= 1;  // Default

  //public  boolean mCommonNode2TypeDistributionRandomModeEnabled = false;

  //public  boolean mCurrentNode2TypeDistributionRandomModeEnabled = false;

  //public  boolean mUseCommonNode2TypeDistribution = true;

  /////////////////////////////////////
  // Communikation Parameter Group   //
  /////////////////////////////////////

  public  int  mGewinnStatusExchangeProbability = 10;


  public String mMaxLostNumberMode      = "fixed";  // default
  public int mFixedMaxLostNumber       = 5;   //fixed maximal lost number
  public int mMaxLostNumberSeed        = 5;   //maximal lost number seed

  public int mBaseDeviation4PriceLimit = 10;  //10%
  public int mAbschlagFactor           = 2;
  public int mOrders4AverageLimit      = 10;  // 10 past orders as base of AverageLimit

  /************* Parameter Group fuer Blanko-Agent ****************/

  public int    mBlankoAgentInitCash_Min   =0;
  public int    mBlankoAgentInitCash_Max   =0;

  public int    mBlankoAgentInitAktien_Min  =0;
  public int    mBlankoAgentInitAktien_Max  =0;

  public double mBlankoAgentPlusIndexProcentForActivation_Min =5.0;
  public double mBlankoAgentPlusIndexProcentForActivation_Max =5.0;

  public double mBlankoAgentMinusIndexProcentForDeactivation_Min =5.0;
  public double mBlankoAgentMinusIndexProcentForDeactivation_Max =5.0;

  public  int   mBlankoAgentMindestActiveDays = 5;

  public int    mBlankoAgentDayOfIndexWindow_Min             =10;
  public int    mBlankoAgentDayOfIndexWindow_Max             =10;

  public int    mBlankoAgentInactiveDays_Min                 =14;  // two weeks
  public int    mBlankoAgentInactiveDays_Max                 =14;  // two weeks
  public int    mBlankoAgent_SleepProcent                    =0;  // Default No Sleep.
  public boolean mBlankoAgent_CashAppendAllowed              =true;
  public int mBlankoAgent_AppendCash                     =0;
  public int    mBlankoAgent_DaysOfTotalSell                 =10;  // Default 10 Days

  /************* Parameter Group fuer TobinTax Agent ****************/

  public static String  mCash1_Name = "Euro";   // Default
  public static String  mCash2_Name = "Dollar"; // Default

  public  double    mTobintax_FestSteuer  = 1.0;  // 1.0%
  public  double    mTobintax_ExtraSteuer = 2.0;  // 2.0%
  public  int       mTobintax_Days4AverageKurs = 100;  // 100 Tage
  public  double    mTobintax_Interventionsband =5.0;      // Intervent
  public  double    mTobintax_TradeProzent =10.0;      // von gesamte Depot

  /************* Parameter Group fuer Investor ****************/

  public  int    mInvestorInitCash_Min = 0;
  public  int    mInvestorInitCash_Max = 0;
  public  int    mInvestorInitAktien_Min=0 ;
  public  int    mInvestorInitAktien_Max=0 ;

  public  double  mInvestor_DynamischAbschlageProzent_Min = 0.5;  // Achtung:das ist Prozent
  public  double  mInvestor_DynamischAbschlageProzent_Max = 8.0;  // Achtung:das ist Prozent

  public  double  mInvestor_AbschlagGaussMean      = 2.0;  // das ist absoluter Wert
  public  double  mInvestor_AbschlagGaussDeviation = 0.75; // das ist absoluter Wert

  public  int     mInvestor_AnzahlProzent_LinkBereich = 50 ;   //Achtung:das ist Prozent
  public  int     mInvestor_AnzahlProzent_MittBereich = 35;    //Achtung:das ist Prozent
  public  int     mInvestor_AnzahlProzent_RechtBereich= 15;    //Achtung:das ist Prozent

  public  int     mInvestorSleepProcent     = 30;   // Default
  public  int     mInvestorOrderMengeStufe1 = 1 ; // Default
  public  int     mInvestorOrderMengeStufe2 = 3 ; // Default
  public  int     mInvestorOrderMengeStufe3 = 6 ; // Default
  public  int     mInvestorOrderMengeStufe4 = 10 ; // Default

  public  double  mInvestorKurschangedprocentlimit1 = 1.0 ;
  public  double  mInvestorKurschangedprocentlimit2 = 2.0 ;
  public  double  mInvestorKurschangedprocentlimit3 = 3.0 ;

  public  char    mInvestorStufe1MarketOrderBilligestKauf = ' ' ; // Default
  public  char    mInvestorStufe2MarketOrderBilligestKauf = ' ' ; // Default
  public  char    mInvestorStufe3MarketOrderBilligestKauf = ' ' ; // Default
  public  char    mInvestorStufe4MarketOrderBilligestKauf = ' ' ; // Default

  public  char    mInvestorStufe1MarketOrderBestVerkauf = ' ' ; // Default
  public  char    mInvestorStufe2MarketOrderBestVerkauf = ' ' ; // Default
  public  char    mInvestorStufe3MarketOrderBestVerkauf = ' ' ; // Default
  public  char    mInvestorStufe4MarketOrderBestVerkauf = ' ' ; // Default

  public  double  mInvestor_InnererWertIntervall_Untergrenz = -5.0 ; // in Prozent
  public  double  mInvestor_InnererWertIntervall_Obengrenz  = 5.0  ; // in Prozent
  public  double  mInvestor_KursAnderung_Schwelle           = 4.0  ; // in Prozent
  public  double  mInvestor_AktuellerInnererWert_Potenzial  = 10.0 ; // in Prozent
  public  int     mInvestor_KursAnderung_ReferenzTag        = 5;     // Gegen 5. Vortag

  public  boolean mInvestorAffectedByOtherNode = true;

  /***************** Parameter Group fuer NoiseTrader ************************/

  public  int mNoiseTraderInitCash_Min = 0;
  public  int mNoiseTraderInitCash_Max = 0;
  public  int mNoiseTraderInitAktien_Min = 0 ;
  public  int mNoiseTraderInitAktien_Max = 0 ;

  public  int mNoiseTrader_MinMovingDaysForAveragePrice = 20;   // Default
  public  int mNoiseTrader_MaxMovingDaysForAveragePrice = 50;   // Default

  public  double  mNoiseTrader_MinLimitAdjust = 0.0;
  public  double  mNoiseTrader_MaxLimitAdjust = 0.0;
  //public  double  mNoiseTrader_KurszusazinfobasedLimitAdjust=0.0;

  public  int mNoiseTrader_SleepProcent = 30;   // Default

  /*************************************************************/
  public  double mNoiseTraderKurschangedprocentlimit1 = 1.0 ;
  public  double mNoiseTraderKurschangedprocentlimit2 = 2.0 ;
  public  double mNoiseTraderKurschangedprocentlimit3 = 4.0 ;

  public  int    mNoiseTraderOrderMengeStufe1 = 1 ; // Default
  public  int    mNoiseTraderOrderMengeStufe2 = 3 ; // Default
  public  int    mNoiseTraderOrderMengeStufe3 = 6 ; // Default
  public  int    mNoiseTraderOrderMengeStufe4 = 10 ; // Default

  public  char    mNoiseTraderStufe1MarketOrderBilligestKauf = ' ' ; // Default
  public  char    mNoiseTraderStufe2MarketOrderBilligestKauf = ' ' ; // Default
  public  char    mNoiseTraderStufe3MarketOrderBilligestKauf = ' ' ; // Default
  public  char    mNoiseTraderStufe4MarketOrderBilligestKauf = ' ' ; // Default

  public  char    mNoiseTraderStufe1MarketOrderBestVerkauf = ' ' ; // Default
  public  char    mNoiseTraderStufe2MarketOrderBestVerkauf = ' ' ; // Default
  public  char    mNoiseTraderStufe3MarketOrderBestVerkauf = ' ' ; // Default
  public  char    mNoiseTraderStufe4MarketOrderBestVerkauf = ' ' ; // Default
  public  boolean mNoiseTraderAffectedByOtherNode = true;

  /************* Parameter Group fuer RandomTrader ************/

  public  int    mRandomTraderInitCash=0 ;
  public  int    mRandomTraderInitAktien=0 ;

  public  int    mRandomTraderBuyProbability  = 0;
  public  int    mRandomTraderBuyProbabilityIndexBased  = 50;
  public  int    mRandomTraderBuyProbabilityCheapest  = 50;

  public  int    mRandomTraderSellProbability = 0;
  public  int    mRandomTraderSellProbabilityIndexbased = 50;
  public  int    mRandomTraderSellProbabilityBest = 50;

  public  int    mRandomTraderWaitProbability = 0;

  public  int    mRandomTraderMinMenge=1 ;
  public  int    mRandomTraderMaxMenge=3 ;

  public  int    mRandomTraderSleepSeed          = 10000;
  public  int    mRandomTraderRandomSeed4Decision = 10000;

  /*** Logging Group ***/
  public boolean mLogDailyTradeBook= false ;
  public boolean mLogAgentExchangeHistoy = false ;
  public boolean mLogAgentDailyDepot = false ;

  /**  Parameter Group  General **/

  public  int  mDaysOfOnePeriod = 1500;

  //Default: 1500 Tage -- ein Sinus Kurv

  public  int  mMinInnererWert = 500;
  public  int  mMaxInnererWert = 3000;
  public  int  mBeginInnererWert = 1000;
  public  String  mInnererWertMuster = "";  // define a file name which contains pre-created InnererWert
  public  int  mInit300[];
  // the DataCount depends on the InnererWertFuke
  public  int  mInnererWert[] ;
  public  int  mInnererWertRealDataCountFromModelFile;

  // 1: Aufsteigen, 0: Absteiegn
  public  int  mInnererwertEntwicklungsTrend = 1;
  public  double  mInnererwertMaximalTagAbweichnung= 3.5;  // in %
  public  int  mHandelsday = 300;
  //public  int  mDaysOfContinuedLost =3;

  public  int mRepeatTimes = 1;

  public  int mAnzahlAutoKorrelation = 50;
  public  int mAheadDaysForGewinnCalculation = 50;
  public  double mHillEstimatorProcent = 5;  // 5 means: 5%

  public  Hashtable mAgentInitParameter = new Hashtable();

  //in %  Default ABS(Preis-InnererWert)/InnererWert <= 100%
  public   int mAllowerAbweichungPreis2InnererWert = 100;

  /**********  Parameter Group fuer DataLogger ********************/

  public  String mLogTopDirectory;
  public  String mMainLogDirectory;  // entspricht 2. Level directory

  public  String mAgentAnzahl_CheckFilename ="agentanzahlcheck.txt";

  public  String mResultComparationOfNetworks = "resultcomparation.html";
  public  String mTestCaseName;

  public  long   mTestSerieStarttime;
  public  String mOneConfigStarttime;

  public  String mTestSerienCSVReportFile         = "summary.csv";
  public  String mTestSerienCSVReportFileForSPSS  = "summary-spss.csv";
  public  String mTestSerienHTMLReportFile        = "summary.html";

  public  String mGraphStateHistory               = "graphstatehistory.txt";
  public  String mAgentStateChangeStatisticLogFile="agenttypechangestatistic.csv";
  public  String mLogrenditenLogFilename          = "logrenditen.csv";
  public  String mAutoKorrelationLogFilename      = "autokorrelation.csv";
  public  String mKorrelationLogFilename          = "korrelation.csv";
  // this is the central data file for the statistic 
  public  String mTradeDailyStatisticFilename    = "tradedailystatistic.csv";
  
  public  String mInnererwert300LogFilename       = "innererwert-init300.csv";

  public  String mAgentNumberLogFile              = "agentanzahl.csv";
  public  String mDepotLogFilename                = "depot.csv";
  public  String mDailyOrderBookFile              = "DailyOrderBook.html";
  public  String mPriceStatisticLogFile           ="ergebnisse.txt";
  public  String mHillEstimatorLogFile            = "hill.csv";
  public  String mTobintaxLogFile                 = "tobintax.csv";
  public  String mTobintaxCalculationDetailedLogFile = "tobintaxcalculationdetailed.csv";

  public  String mSimulationCounterInfo = "";
  public  boolean mSummaryCreated = false;

  // File Name for new statistic
  public  String mDepotListFile_TextFormat   = "depotlist_textformat.csv";

  public  String mDepotListSortedByGroup_TextFormat  = "depotlist_sortedbygroup.csv";
  public  String mDepotListSortedByGroup_HTMLFormat  = "depotlist_sortedbygroup.html";

  public  String mAgentGroupFinalStatisticCompararationFileName="agentgroupfinalstatistic.html";
  public String mChartOverviewHTML = "chartoverview.html";
  public String mChartName = "frame.jpg";
  
  public Vector mDailyPerformedAktienList = new Vector(); 
  public Vector mDailyPerformedVolumeList = new Vector(); 
  
  public void setAnzahlTotalAgents (int pAnzahlTotal)
  {
      this.mAnzahlTotalAgents = pAnzahlTotal;
  }

  public String getCurrentMarketMode()
  {
      if ( this.mMarketMode == mAktienMarket )
      {
          return "Stock Market";
      }
      else
      {
          return "Money Market";
      }
  }

  /**
   * This method is used only for internal paramter controll by developer
   */
  public  String[] getAllParameterPairDetailedCommenta( )
  {
     Vector ppp = new Vector();
     ppp.add("###### Parameter begins #######");
     ppp.add("################################################################");
     ppp.add("## General Parameter                                          ##");
     ppp.add("################################################################");

     ppp.add("# Market Mode definition");
     ppp.add("# =1  AktienMarket");
     ppp.add("# =2  CashMarket");

     ppp.add(ParameterNameConst.MarketMode+"=" + mMarketMode);

     ppp.add("# Tobintax Agent active or not");
     ppp.add(ParameterNameConst.TobintaxAgentActive+"=" + this.mTobintaxAgentAktive);

     ppp.add("#Days for simulation");
     ppp.add("#Default: 300 ");
     ppp.add( ParameterNameConst.Handelsday+"=" + mHandelsday );

     ppp.add("#Die maximale Abweichung in % von Innererwert am Tag ");
     ppp.add( ParameterNameConst.MaximalTagAbweichnung+"=" + mInnererwertMaximalTagAbweichnung );

     ppp.add("#Der minimale Innererwert ");
     ppp.add(ParameterNameConst.InnerWert_Min+"=" + mMinInnererWert );

     ppp.add("#Der maximale Innererwert ");
     ppp.add(ParameterNameConst.InnerWert_Max+"=" + mMaxInnererWert );

     ppp.add("#Der StartWert von InnererWert ");
     ppp.add(ParameterNameConst.InnerWert_Begin+"=" + mBeginInnererWert );

     ppp.add("#Der Interval von Tagen fuer Gewinnstatusaustausch zwischen Agenten");
     ppp.add("#Dieser Wert ist wichtig fuer Agent-Kommunikation");
     ppp.add( ParameterNameConst.GewinnstatusaustauschInterval+"=" + mGewinnStatusExchangeProbability );

     ppp.add("# Tage innerhalb einer Sinus Funktion ");
     ppp.add( ParameterNameConst.DaysOfOnePeriod+"=" + mDaysOfOnePeriod  );

     ppp.add("#Die maximal erlaubte Abweichung zwischen Preis und Innererwert ");
     ppp.add("#Dieser Parameter regelt den Ablauf einer Simulation,");
     ppp.add("#Wenn ein Tag die Abweichung diese Grenz ueberschreitet, wird die Simulation abgebrochen.");
     ppp.add( ParameterNameConst.AllowedAbweichungPreis2InnererWert+"=" + this.mAllowerAbweichungPreis2InnererWert );

     ppp.add("# ReferenceTag, gegen den die Gewinn berechnet werden soll. ");
     ppp.add( ParameterNameConst.AheadDaysForGewinnCalculation+"=" + mAheadDaysForGewinnCalculation );

     ////////////////////////////////////////////////////////////////
     ppp.add("# Prozent von HillEstimation Berechnung ");
     ppp.add( ParameterNameConst.HillEstimatorProcent+"=" + mHillEstimatorProcent );

     ppp.add("################################################################");
     ppp.add("##                                                            ##");
     ppp.add("## Parameter for Agent Communication                          ##");
     ppp.add("##                                                            ##");
     ppp.add("################################################################");

     ppp.add( ParameterNameConst.StatusExchangeProbabiliy + "=" + this.mGewinnStatusExchangeProbability );
     ppp.add( ParameterNameConst.MaxLostNumberMode + "=" + this.mMaxLostNumberMode );
     ppp.add( ParameterNameConst.FixedMaxLostNumber + "=" + this.mFixedMaxLostNumber );
     ppp.add( ParameterNameConst.MaxLostNumberSeed + "=" + this.mMaxLostNumberSeed );
     ppp.add( ParameterNameConst.BaseDeviation4PriceLimit + "=" + this.mBaseDeviation4PriceLimit );
     ppp.add( ParameterNameConst.AbschlagFactor + "=" + this.mAbschlagFactor);
     ppp.add( ParameterNameConst.Orders4AverageLimit + "=" + this.mOrders4AverageLimit );

     ppp.add("################################################################");
     ppp.add("##                                                            ##");
     ppp.add("## Agent Network and Nodes to AgentType Mapping               ##");
     ppp.add("##                                                            ##");
     ppp.add("################################################################");

     ppp.add("#Current used Network file");
     ppp.add( ParameterNameConst.NetworkFile+"=" + this.mNetworkfileCurrentUsed);

     //ppp.add("#if Common Node2AgentType-Zuordnung ist verwendet");
     //ppp.add( ParameterNameConst.UseCommonNetworkNode2AgentTypeDistribution+"=" + this.mUseCommonNode2TypeDistribution);

     //ppp.add("# ff Random-Zuordnung der NetzwerkNodes zu AgentType verwendet ist");
     //ppp.add( ParameterNameConst.NetworkNodeDistributionRandomMode +"=" + this.mCurrentNode2TypeDistributionRandomModeEnabled);

     ppp.add("#Nodes in Network");
     ppp.add( "NodesInNetwork=" + this.mNodesInNetwork);

     ppp.add("#Fundamental Agent in Network");
     ppp.add( "InvestorInNetwork=" + this.mAnzahlInvestor);

     ppp.add("#Trend Agent in Network");
     ppp.add( "NoiseTraderInNetwork=" + this.mAnzahlNoiseTrader);

     ppp.add("#Retail Agent in Network");
     ppp.add( "BlankoAgentInNetwork=" + this.mAnzahlBlankoAgent);

     ppp.add("# Absolute Anzahl von RandomTrader ");
     ppp.add( ParameterNameConst.RandomTrader  + "=" + mAnzahlRandomTrader);

     ppp.add("#Repeat Times of Netzwork");
     ppp.add( ParameterNameConst.RepeatTimes+"=" + this.mRepeatTimes);

     ppp.add("# Original InvestorProzent von der Netzwerknodes ");
     ppp.add("# These two parameter make sense                 ");
     ppp.add("# only when 'All networks use common Node Distribution' is not selected ");
     ppp.add("# and while parameter loading phase. ");
     ppp.add("# In dialog mode, we can always change it after loading.");
     ppp.add("# InvestorProzent + NoiseTraderProzent soll 100 sein ");

     // ppp.add( ParameterNameConst.InvestorProcent +"=" + mCommonInvestor_Procent);
     // ppp.add( ParameterNameConst.NoiseTraderProcent + "=" + mCommonNoiseTrader_Procent);

     ppp.add("################################################################");
     ppp.add("##                                                            ##");
     ppp.add("## Fundamental Agent Group Parameter                          ##");
     ppp.add("##                                                            ##");
     ppp.add("################################################################");
     ppp.add("# Minimal Init Cash  bzw.  Cash1");
     ppp.add( ParameterNameConst.Investor_InitCash_Min + "="+mInvestorInitCash_Min );

     ppp.add("# Maximal Init Cash  bzw.  Cash1");
     ppp.add( ParameterNameConst.Investor_InitCash_Max + "="+mInvestorInitCash_Max );

     ppp.add("# Minimal Init Aktien  bzw.  Cash2");
     ppp.add( ParameterNameConst.Investor_InitStock_Min+ "="+mInvestorInitAktien_Min );

     ppp.add("# Maximal Init Aktien  bzw.  Cash2");
     ppp.add( ParameterNameConst.Investor_InitStock_Max+ "="+mInvestorInitAktien_Max );

      ////////////////////////////////////////////////////////////////
     ppp.add("#AbschlagProzent Parameter ");
     ppp.add(ParameterNameConst.Investor_DynamischAbschlageProzent_Min + "=" + this.mInvestor_DynamischAbschlageProzent_Min );
     ppp.add(ParameterNameConst.Investor_DynamischAbschlageProzent_Max + "=" + this.mInvestor_DynamischAbschlageProzent_Max );

     ppp.add("# OrderMenge Stufe1");
     ppp.add( ParameterNameConst.Investor_Order_Stufe1+"=" +mInvestorOrderMengeStufe1);
     ppp.add("# OrderMenge Stufe2");
     ppp.add( ParameterNameConst.Investor_Order_Stufe2 + "=" +mInvestorOrderMengeStufe2);
     ppp.add("# OrderMenge Stufe3");
     ppp.add( ParameterNameConst.Investor_Order_Stufe3 +"=" +mInvestorOrderMengeStufe3);
     ppp.add("# OrderMenge Stufe4");
     ppp.add( ParameterNameConst.Investor_Order_Stufe4 + "=" +mInvestorOrderMengeStufe4);

     ppp.add("# SchlafenProzent: definier wie haeufig Investor kein Buy/Sell-Order stellt");
     ppp.add( ParameterNameConst.Investor_SleepProcent + "=" +this.mInvestorSleepProcent);




     ppp.add("################################################################");
     ppp.add("##                                                            ##");
     ppp.add("##  Trend Agent Group Parameter                               ##");
     ppp.add("##                                                            ##");
     ppp.add("################################################################");

     ppp.add("#Minimal Init Cash  bzw.  Cash1");
     ppp.add( ParameterNameConst.NoiseTrader_InitCash_Min+"=" + mNoiseTraderInitCash_Min );
     ppp.add("#Maximal Init Cash  bzw.  Cash1");
     ppp.add( ParameterNameConst.NoiseTrader_InitCash_Max+"=" + mNoiseTraderInitCash_Max );

     ppp.add("# Minial Init Aktien  bzw.  Cash2");
     ppp.add( ParameterNameConst.NoiseTrader_InitStock_Min+"=" + mNoiseTraderInitAktien_Min );

     ppp.add("# Maximal Init Aktien  bzw.  Cash2");
     ppp.add( ParameterNameConst.NoiseTrader_InitStock_Max+"=" + mNoiseTraderInitAktien_Max );

     ppp.add("#OrderMenge Stufe1");
     ppp.add( ParameterNameConst.NoiseTrader_Order_Stufe1+"=" + mNoiseTraderOrderMengeStufe1  );
     ppp.add("#OrderMenge Stufe2");
     ppp.add( ParameterNameConst.NoiseTrader_Order_Stufe2+"=" + mNoiseTraderOrderMengeStufe2  );
     ppp.add("#OrderMenge Stufe3");
     ppp.add( ParameterNameConst.NoiseTrader_Order_Stufe3+"=" + mNoiseTraderOrderMengeStufe3  );
     ppp.add("# OrderMenge Stufe4");
     ppp.add( ParameterNameConst.NoiseTrader_Order_Stufe4+"=" + mNoiseTraderOrderMengeStufe4  );

     ppp.add("# Parameter fuer MovingAveragePrice-Berechnung");
     ppp.add("# Jeder NoiseTrader bekommt einen zufaellig generierter MovingDays zwischen Min und Max");
     ppp.add( ParameterNameConst.NoiseTrader_MinMovingDays4AveragePrice          +"="+mNoiseTrader_MinMovingDaysForAveragePrice);
     ppp.add( ParameterNameConst.NoiseTrader_MaxMovingDays4AveragePrice          +"="+mNoiseTrader_MaxMovingDaysForAveragePrice);

     ppp.add("# LimitAdjust ");
     ppp.add("# MinLimitAdjust and MaxAdjust are used for Limit-Generating  Limit = InnererWert * ( 1 + or - x %), x is [LimitMinAdjust, LimitMaxAdjust]");
     ppp.add( ParameterNameConst.NoiseTrader_MinLimitAdjust  +"="+mNoiseTrader_MinLimitAdjust);
     ppp.add( ParameterNameConst.NoiseTrader_MaxLimitAdjust  +"="+mNoiseTrader_MaxLimitAdjust);

     ppp.add("# SchlafenProzent: definier wie haeufig NoiseTrader kein Buy/Sell-Order stellt");
     ppp.add( ParameterNameConst.NoiseTrader_SleepProcent +"="+mNoiseTrader_SleepProcent);

     ppp.add("################################################################");
     ppp.add("##                                                            ##");
     ppp.add("##  Retail Agent Group Parameter                              ##");
     ppp.add("##                                                            ##");
     ppp.add("################################################################");

     ppp.add("# Agent Minimal Init Cash");

     ppp.add( ParameterNameConst.BlankoAgent_InitCash_Min+"=" + mBlankoAgentInitCash_Min );
     ppp.add("# Agent Maximal Init Cash");
     ppp.add( ParameterNameConst.BlankoAgent_InitCash_Max+"=" + mBlankoAgentInitCash_Max );

     ppp.add("# Agent Minial Init Stock");
     ppp.add( ParameterNameConst.BlankoAgent_InitStock_Min+"=" + mBlankoAgentInitAktien_Min );

     ppp.add("# Agent Maximal Init Stock");
     ppp.add( ParameterNameConst.BlankoAgent_InitStock_Max+"=" + mBlankoAgentInitAktien_Max );

     ppp.add( ParameterNameConst.BlankoAgentDayOfIndexWindow_Min+"=" + this.mBlankoAgentDayOfIndexWindow_Min );
     ppp.add( ParameterNameConst.BlankoAgentDayOfIndexWindow_Max+"=" + this.mBlankoAgentDayOfIndexWindow_Max );

     ppp.add( ParameterNameConst.BlankoAgentInactiveDays_Min+"=" + this.mBlankoAgentInactiveDays_Min );
     ppp.add( ParameterNameConst.BlankoAgentInactiveDays_Max+"=" + this.mBlankoAgentInactiveDays_Max );

     ppp.add( ParameterNameConst.BlankoAgentPlusIndexProcentForActivation_Min+"=" + this.mBlankoAgentPlusIndexProcentForActivation_Min  );
     ppp.add( ParameterNameConst.BlankoAgentPlusIndexProcentForActivation_Max+"=" + this.mBlankoAgentPlusIndexProcentForActivation_Max  );

     ppp.add( ParameterNameConst.BlankoAgentMinusIndexProcentForDeactivation_Min+"=" + this.mBlankoAgentMinusIndexProcentForDeactivation_Min );
     ppp.add( ParameterNameConst.BlankoAgentMinusIndexProcentForDeactivation_Max+"=" + this.mBlankoAgentMinusIndexProcentForDeactivation_Max );

     ppp.add( ParameterNameConst.BlankoAgent_SleepProcent+"=" + this.mBlankoAgent_SleepProcent );
     ppp.add( ParameterNameConst.BlankoAgent_CashAppendAllowed+"=" + this.mBlankoAgent_CashAppendAllowed );

     ppp.add( ParameterNameConst.BlankoAgent_DaysOfTotalSell+"=" + this.mBlankoAgent_DaysOfTotalSell );

     ppp.add("################################################################");
     ppp.add("##                                                            ##");
     ppp.add("##  RandomTrader Parameter                                    ##");
     ppp.add("##                                                            ##");
     ppp.add("################################################################");

     ppp.add("#RandomTrader Init Cash bzw. Cash1");
     ppp.add( ParameterNameConst.RandomTrader_InitCash+"=" + mRandomTraderInitCash );
     ppp.add("#RandomTrader Init Aktien bzw. Cash2");
     ppp.add( ParameterNameConst.RandomTrader_InitStock+"=" + mRandomTraderInitAktien );

     ppp.add("#RandomTrader Kauf-Wahrscheinlichkeit in Prozent ");
     ppp.add("# Achtung: Kauf-Wahrschlichkeit + Verkauf-Wahrscheinlichkeit muss 100 sein");
     ppp.add( ParameterNameConst.RandomTraderBuyProbability+"=" + mRandomTraderBuyProbability );

     ppp.add("#RandomTrader Kauf-Wahrscheinlichkeit 'Chepest' in Prozent");
     ppp.add("#Dieser Parameter steuert wie hauefig der RandomTrader den Order mit Chepest-Buy zu stellen");
     ppp.add("# Achtung: Chepest + Indexbased muss 100 sein ");
     ppp.add( ParameterNameConst.RandomTraderBuyProbabilityChepest+"=" + mRandomTraderBuyProbabilityCheapest );

     ppp.add("#RandomTrader Kauf-Wahrscheinlichkeit 'Indexbased' in Prozent");
     ppp.add( ParameterNameConst.RandomTraderBuyProbabilityIndexBased+"=" + mRandomTraderBuyProbabilityIndexBased );

     ppp.add("#RandomTrader Verkauf-Wahrscheinlichkeit in Prozent");
     ppp.add( ParameterNameConst.RandomTraderSellProbability+"=" + mRandomTraderSellProbability);
     ppp.add("#RandomTrader Verkauf-Wahrscheinlichkeit 'IndexBased' in Prozent");
     ppp.add("# Achtung: IndexBased + Best muss 100 sein. ");

     ppp.add( ParameterNameConst.RandomTraderSellProbabilityIndexBased+"=" + mRandomTraderSellProbabilityIndexbased );
     ppp.add("#RandomTrader Verkauf-Wahrscheinlichkeit 'Best' in Prozent");
     ppp.add( ParameterNameConst.RandomTraderSellProbabilityBest+"="+mRandomTraderSellProbabilityBest);

     ppp.add("#RandomTrader Schlafen-Wahrscheinlichkeit in Prozent");
     ppp.add( ParameterNameConst.RandomTraderWaitProbability+"=" + mRandomTraderWaitProbability);

     ppp.add("#RandomTrader Minimal und Maximal OrderMenge steuert die Menge in Order");
     ppp.add("#RandomTrader Minimal OrderMenge");
     ppp.add( ParameterNameConst.RandomTraderMinMenge+"=" + mRandomTraderMinMenge );
     ppp.add("#RandomTrader Maximal OrderMenge");
     ppp.add( ParameterNameConst.RandomTraderMaxMenge+"=" + mRandomTraderMaxMenge );

     ppp.add("################################################################");
     ppp.add("##                                                            ##");
     ppp.add("## Tobintax Agent Parameter                                   ##");
     ppp.add("##                                                            ##");
     ppp.add("################################################################");

     ppp.add("#Tobintax Agent: Feste Steuer");
     ppp.add(ParameterNameConst.TobintaxAgentFestSteuer + "=" + this.mTobintax_FestSteuer);

     ppp.add("#Tobintax Agent: Extra Steuer ");
     ppp.add(ParameterNameConst.TobintaxAgentExtraSteuer + "=" + this.mTobintax_ExtraSteuer);

     ppp.add("#Interventionsband in %");
     ppp.add(ParameterNameConst.TobintaxInterventionsband + "=" + this.mTobintax_Interventionsband);

     ppp.add("#Tage fuer Berechnung der Mittelwert der Kurs");
     ppp.add(ParameterNameConst.TobintaxAgentTag4AverageKurs + "=" + this.mTobintax_Days4AverageKurs);

     ppp.add("#Tradeprozent von Tobintax Agent");
     ppp.add("#wenn er kauft, benutzt er     K% von seinem CASH1");
     ppp.add("#wenn er verkauft, verkauft er K% von seinem CASH2");
     ppp.add(ParameterNameConst.TobintaxAgentTradeProzent + "=" + this.mTobintax_TradeProzent);

     ppp.add(ParameterNameConst.Cash1_Name + "=" + this.mCash1_Name);
     ppp.add(ParameterNameConst.Cash2_Name + "=" + this.mCash2_Name);
     ppp.add("###### Parameter is end #######");

     String tt[] = new String[ ppp.size() ];
     for (int i=0; i< ppp.size(); i++ )
     {
        tt[i] = (String)  ppp.elementAt(i);
     }
     return tt;
  }

  public  void setDepotFileList(Vector pDepotList)
  {
      mDepotFileList = pDepotList;
  }

  public  void setExchangeHistoryFileList(Vector pFileList)
  {
      mExchangeHistoryFileList = pFileList;
  }

  // Only getter methodes List begin ****************************//

  /////////////////////////////////////////////////////////////////////////////

  public int getCountOfRequiredRegister()
  {
        if ( this.mMarketMode == mAktienMarket )
        {
             return this.mAnzahlTotalAgents;
        }
        else
        {
          if ( this.mTobintaxAgentAktive )
          {
             return 1 + this.mAnzahlTotalAgents;
          }
          else
          {
              return this.mAnzahlTotalAgents;
          }
    }
  }

  public int getCountOfRequiredOrders()
  {
       return this.mAnzahlTotalAgents;
  }

  public  int getRandomTraderInitCash()
  {
     return mRandomTraderInitCash;
  }

  public  int getRandomTraderInitAktien()
  {
     return mRandomTraderInitAktien;
  }

  public  int  getRandomTraderBuyProbability()
  {
     return  mRandomTraderBuyProbability ;
  }

  public  int  getRandomTraderBuyProbabilityIndexBased()
  {
    return mRandomTraderBuyProbabilityIndexBased;
  }

  public  int  getRandomTraderBuyProbabilityCheapest()
  {
    return mRandomTraderBuyProbabilityCheapest;
  }

  public  int  getRandomTraderSellProbability()
  {
    return mRandomTraderSellProbability;
  }

  public  int  getRandomTraderSellProbabilityIndexbased()
  {
    return mRandomTraderSellProbabilityIndexbased;
  }

  public  int getRandomTraderSellProbabilityBest()
  {
    return mRandomTraderSellProbabilityBest;
  }

  public  int getRandomTraderWaitProbability()
  {
     return mRandomTraderWaitProbability;
  }

  /*******************************************************/
  public  void setRandomTraderInitCash(int pInt)
    {
       mRandomTraderInitCash = pInt;
    }

    public  void setRandomTraderInitAktien(int pInt)
    {
        mRandomTraderInitAktien = pInt;
    }

    public  void  setRandomTraderBuyProbability(int pInt)
    {
        mRandomTraderBuyProbability = pInt;
    }

    public  void  setRandomTraderBuyProbabilityIndexBased(int pInt)
    {
        mRandomTraderBuyProbabilityIndexBased = pInt;
    }

    public  void  setRandomTraderBuyProbabilityCheapest(int pInt)
    {
        mRandomTraderBuyProbabilityCheapest = pInt;
    }

    public  void  setRandomTraderSellProbability(int pInt)
    {
        mRandomTraderSellProbability = pInt;
    }

    public  void setRandomTraderSellProbabilityIndexbased(int pInt)
    {
        mRandomTraderSellProbabilityIndexbased = pInt;
    }

    public  void getRandomTraderSellProbabilityBest(int pInt)
    {
        mRandomTraderSellProbabilityBest = pInt;
    }

    public  void getRandomTraderWaitProbability(int pInt)
    {
        mRandomTraderWaitProbability = pInt;
    }

 ///////////////////////////////////////////////////////////////////////////

  public  boolean getFileReadOK()
  {
     return mFileReadSuccessful;
  }

  public  String getLogrenditenLogFile()
  {
      return LogFileNameMapper( mLogrenditenLogFilename);
  }

  public  String getTradeDailyStatisticFilename()
  {
      return LogFileNameMapper(mTradeDailyStatisticFilename);
  }

  public  String getInnererwert300LogFile()
  {
     return LogFileNameMapper(mInnererwert300LogFilename);
  }

  public  String getDepotLogFile()
  {
     return LogFileNameMapper(mDepotLogFilename) ;
  }

  public  String getAutoKorrelationLogFile()
  {
      return LogFileNameMapper(mAutoKorrelationLogFilename);
  }

  public  int getAnzahlAutoKorrelation()
  {
       return mAnzahlAutoKorrelation;
  };

  public  void setAnzahlAutoKorrelation(int pAnzahl)
  {
     mAnzahlAutoKorrelation = pAnzahl;
  }

  public  String getKorrelationLogFile()
  {
      return LogFileNameMapper(mKorrelationLogFilename);
  }

  public  String getTobintaxLogFile()
  {
      return LogFileNameMapper( this.mTobintaxLogFile);
  }

  public  String getTobintaxCalculationDetailedLogFile()
  {
      return LogFileNameMapper( this.mTobintaxCalculationDetailedLogFile);
  }

  public String getAgentAnzahl_In_ModelIndexChartFilename()
  {
      return LogFileNameMapper("agentzahl_in_modelindex_endstand.txt");
  }

  public String getAgentAnzahl_CheckFilename()
  {
      return LogFileNameMapper(mAgentAnzahl_CheckFilename);
  }

  public  String getHillEstimatorLogFile()
  {
      return LogFileNameMapper(mHillEstimatorLogFile);
  }

  public String getDepotListFile_TextFormat()
  {
      return LogFileNameMapper( this.mDepotListFile_TextFormat);
  }

  public String getDepotListFile_SortedByGroup_HTMLFormat()
  {
      return LogFileNameMapper( this.mDepotListSortedByGroup_HTMLFormat);
  }

  /*
  // Die Prozent von Investor
  public  int getInvestor_Procent()
  {
     return mInvestor_Procent;
  }

  // Die Prozent von NoiseTrader
  public  int getNoiseTrader_Procent()
  {
     return mNoiseTrader_Procent;
  }
  */
  // Die absolute Anzahl von RandomerTrader
  public  int getRandomTrader()
  {
     return mAnzahlRandomTrader;
  }


  public  String getParameter(String ParameterName)
  {
      return AgentConfiguration.getProperty(ParameterName);
  }

  public  String getParameter(String ParameterName, String pDefault)
  {
      return AgentConfiguration.getProperty(ParameterName, pDefault);
  }

// Only getter methodes List end ****************************//

////////////////////////////////////////////////////////////////////////////
//
// Getter and Setter Methodes
//
////////////////////////////////////////////////////////////////////////////

/** GewinnStatusExchangeProbability **/

public  void setGewinnStatusExchangeProbability(int p)
{
    mGewinnStatusExchangeProbability = p;
}

public  int getGewinnStatusExchangeProbability()
{
    return mGewinnStatusExchangeProbability;
}

/** Handelsday **/

public  int getHandelsday()
{
   return mHandelsday;
}

public  void setHandelsday(int p)
{
  mHandelsday = p;
}

public  void setParameterReady(boolean p)
{
     mParameterReady = p;
}

public  boolean isParameterReady()
{
    return mParameterReady;
}

/*
public  boolean isDialogProcessMode()
{
    return this.mProcessMode == this.mDialogBasicProcessMode;
}
*/


public  void setMinInnererWert(int pMin)
 {
      mMinInnererWert = pMin;
 }

 public  int getMinInnererWert()
  {
      return mMinInnererWert;
  }

  /**    **/

  public  int getMaxInnererWert()
  {
    return mMaxInnererWert;
  }

  public  void setMaxInnererWert(int pMax)
   {
        mMaxInnererWert = pMax;
   }

   /**    **/
   public  void setBeginInnererWert(int pBegin)
    {
         mBeginInnererWert = pBegin;
    }
    public  int getBeginInnererWert()
     {
         return mBeginInnererWert;
     }


     /** DaysOfonePeriod **/

     public  int getDaysOfOnePeriod()
     {
        return mDaysOfOnePeriod;
     }

     public  void setDaysOfOnePeriod(int pDays)
     {
         mDaysOfOnePeriod = pDays;
     }

     /** mAheadDaysForGewinnCalculation **/

     public  int getAheadDaysForGewinnCalculation()
     {
        return mAheadDaysForGewinnCalculation;
     }

     public  synchronized void setAheadDaysForGewinnCalculation(int pDays)
     {
         mAheadDaysForGewinnCalculation = pDays;
     }


 /**  InnereWertEntwicklungsTrend **/
  public  int getInnereWertEntwicklungsTrend()
  {
        return mInnererwertEntwicklungsTrend;
  }
  public  void setInnereWertEntwicklungsTrend(int pTrend)
  {
        mInnererwertEntwicklungsTrend = pTrend;
  }


  /**  InnereWertMaximalTagAbweichnung  **/
  public double getInnereWertMaximalTagAbweichnung()
  {
        return mInnererwertMaximalTagAbweichnung;
  }

  public  void setInnereWertMaximalTagAbweichnung(int pMaximalAbweichnung)
  {
        mInnererwertMaximalTagAbweichnung = pMaximalAbweichnung;
  }

 /** HillEstimatorProzent**/
 public  void setHillEstimatorProcent(double pProzent)
 {
    mHillEstimatorProcent = pProzent;
 }

 public  double getHillEstimatorProcent()
 {
    return mHillEstimatorProcent;
 }

 public  long getTestSerieStarttime()
 {
      return mTestSerieStarttime;
 }

 public  void setTestSerieStarttime(long pTestSerieStarttime)
 {
       mTestSerieStarttime = pTestSerieStarttime;
 }

 public  void setOneConfigStarttime(String pOneConfigStarttime)
 {
      mOneConfigStarttime = pOneConfigStarttime;
 }

  public  void setMainLogDirectory(String pMainLogDirectory)
  {
      mMainLogDirectory = pMainLogDirectory;
  }

  public  void setTestCaseName(String pTestCaseName)
  {
      mTestCaseName = pTestCaseName;
  }

 public  String getLogFileDirectory()
 {
     return  mMainLogDirectory;
 }

 public  String getTextReportFileNameofSimulationSerie()
 {
   return  mMainLogDirectory+"/summary.txt";
 }

 public  String getAgentGroupFinalStatisticCompararationFileName()
 {
   // This file is saved to the top-level logging directory:
   //return mMainLogDirectory +"/" + this.mAgentGroupFinalStatisticCompararationFileName;

   return this.mLogTopDirectory +"/" + this.mAgentGroupFinalStatisticCompararationFileName;

 }

 public  String getHtmlReportFileNameofSimulationSerie()
 {
   // Attention:
   // This file is saved to the top-level logging directory:
   return  mMainLogDirectory+"/summary.html";
 }

 public  String LogFileNameMapper(String pFileName)
 {
     return getLogFileDirectory() + mPfadSeperator + pFileName;
 }

 public  String getAgentnumberLogFile()
 {
    return  LogFileNameMapper(mAgentNumberLogFile);
 }

 public  String getPriceStatisticLogFile()
 {
    return LogFileNameMapper(mPriceStatisticLogFile);
 }

  public String[] getXmlOutput()
  {

     Vector ppp = new Vector();

     ppp.add( XmlHelpTool.getXmlComment(  "////////////////////////////////////////////////////////////////") );
     ppp.add( XmlHelpTool.getXmlComment(  "//                                                            //") );
     ppp.add( XmlHelpTool.getXmlComment(  "// General Parameter                                          //") );
     ppp.add( XmlHelpTool.getXmlComment(  "//                                                            //") );
     ppp.add( XmlHelpTool.getXmlComment(  "////////////////////////////////////////////////////////////////") );

     ppp.add( XmlHelpTool.getXmlComment(  "# Market mode definition") );
     ppp.add( XmlHelpTool.getXmlComment(  "# =1  Stock Market") );
     ppp.add( XmlHelpTool.getXmlComment(  "# =2  Money Market") );

     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.MarketMode, mMarketMode) );

     ppp.add( XmlHelpTool.getXmlComment( "# Tobintax Agent active or not") );
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.TobintaxAgentActive, this.mTobintaxAgentAktive) );

     ppp.add( XmlHelpTool.getXmlComment( "#Days of simulation") );
     ppp.add( XmlHelpTool.getXmlComment( "#Default: 300 days ") );
     ppp.add( XmlHelpTool.getXmlTagFormat(  ParameterNameConst.Handelsday, mHandelsday ) );

     ppp.add( XmlHelpTool.getXmlComment( "#parameter InnererWertMuster defines a text file which contains the InnererWert that is created before.") );
     ppp.add( XmlHelpTool.getXmlTagFormat(  ParameterNameConst.InnerWert_Muster, this.mInnererWertMuster ) );

     ppp.add( XmlHelpTool.getXmlComment( "#Die maximale Abweichung in % von Innererwert am Tag ") );
     ppp.add( XmlHelpTool.getXmlTagFormat(  ParameterNameConst.MaximalTagAbweichnung, mInnererwertMaximalTagAbweichnung ) );

     ppp.add( XmlHelpTool.getXmlComment( "#Der minimale Innererwert ") );
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.InnerWert_Min, mMinInnererWert ));

     ppp.add( XmlHelpTool.getXmlComment( "#Der maximale Innererwert ") );
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.InnerWert_Max, mMaxInnererWert ));

     ppp.add( XmlHelpTool.getXmlComment( "#Der StartWert von InnererWert ") );
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.InnerWert_Begin, mBeginInnererWert ) );

     ppp.add( XmlHelpTool.getXmlComment( "#Der Interval von Tagen fuer Gewinnstatusaustausch zwischen Agenten") );
     ppp.add( XmlHelpTool.getXmlComment( "#Dieser Wert ist wichtig fuer Agent-Kommunikation") );
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.GewinnstatusaustauschInterval , mGewinnStatusExchangeProbability ) );

     ppp.add( XmlHelpTool.getXmlComment( "# Tage innerhalb einer Sinus Funktion "));
     ppp.add( ParameterNameConst.DaysOfOnePeriod+"=" + mDaysOfOnePeriod  );

     ppp.add( XmlHelpTool.getXmlComment( "#Die maximal erlaubte Abweichung zwischen Preis und Innererwert "));
     ppp.add( XmlHelpTool.getXmlComment( "#Dieser Parameter regelt den Ablauf einer Simulation,"));
     ppp.add( XmlHelpTool.getXmlComment( "#Wenn ein Tag die Abweichung diese Grenz ueberschreitet, wird die Simulation abgebrochen."));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.AllowedAbweichungPreis2InnererWert, this.mAllowerAbweichungPreis2InnererWert ));

     ppp.add( XmlHelpTool.getXmlComment( "# ReferenceTag, gegen den die Gewinn berechnet werden soll. "));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.AheadDaysForGewinnCalculation, mAheadDaysForGewinnCalculation ));

     ////////////////////////////////////////////////////////////////
     ppp.add( XmlHelpTool.getXmlComment( "# Prozent von HillEstimation Berechnung "));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.HillEstimatorProcent, mHillEstimatorProcent ));

     ppp.add( XmlHelpTool.getXmlComment( "# DataFormat ( float ) of test report summary "));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.DataFormatLanguage, this.mDataFormatLanguage ));

     ppp.add( XmlHelpTool.getXmlComment( "# Auto Correlation in days"));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.AutoCorrelation, this.mAnzahlAutoKorrelation ));

     ppp.add( XmlHelpTool.getXmlComment( "################################################################"));
     ppp.add( XmlHelpTool.getXmlComment( "##                                                            ##"));
     ppp.add( XmlHelpTool.getXmlComment( "## Parameter for Agent Communication                          ##"));
     ppp.add( XmlHelpTool.getXmlComment( "##                                                            ##"));
     ppp.add( XmlHelpTool.getXmlComment( "################################################################"));

     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.StatusExchangeProbabiliy, this.mGewinnStatusExchangeProbability ));

     ppp.add( XmlHelpTool.getXmlComment( "#Anzahl des Verlusts, nach denen der Agent seinen Typ versuchen zu anderen Typ zu wechseln soll"));
     ppp.add( XmlHelpTool.getXmlComment( "#Der Verlust soll nach nacheinander sein."));

     ppp.add( XmlHelpTool.getXmlComment( "#MaxVerlustNumber Mode: fixed or variable, fixed means all agent use the same MaxLostNumber."));
     ppp.add( XmlHelpTool.getXmlComment( "#variable means every agent has own MaxLostNumber which is created using a Random Seed."));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.MaxLostNumberMode , this.mMaxLostNumberMode ));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.FixedMaxLostNumber , this.mFixedMaxLostNumber ));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.MaxLostNumberSeed , this.mMaxLostNumberSeed ));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.BaseDeviation4PriceLimit, this.mBaseDeviation4PriceLimit ));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.AbschlagFactor,this.mAbschlagFactor));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.Orders4AverageLimit,this.mOrders4AverageLimit ));

     ppp.add( XmlHelpTool.getXmlComment( "################################################################"));
     ppp.add( XmlHelpTool.getXmlComment( "##                                                            ##"));
     ppp.add( XmlHelpTool.getXmlComment( "## Fundamental Agent Group Parameter                          ##"));
     ppp.add( XmlHelpTool.getXmlComment( "##                                                            ##"));
     ppp.add( XmlHelpTool.getXmlComment( "################################################################"));
     ppp.add( XmlHelpTool.getXmlComment( "# Minimal Init Cash  bzw.  Cash1"));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.Investor_InitCash_Min,mInvestorInitCash_Min ));

     ppp.add( XmlHelpTool.getXmlComment( "# Maximal Init Cash  bzw.  Cash1"));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.Investor_InitCash_Max,mInvestorInitCash_Max ));

     ppp.add( XmlHelpTool.getXmlComment( "# Minimal Init Aktien  bzw.  Cash2"));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.Investor_InitStock_Min,mInvestorInitAktien_Min ));

     ppp.add( XmlHelpTool.getXmlComment( "# Maximal Init Aktien  bzw.  Cash2"));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.Investor_InitStock_Max,mInvestorInitAktien_Max ));

      ////////////////////////////////////////////////////////////////
     ppp.add( XmlHelpTool.getXmlComment( "#AbschlagProzent Parameter "));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.Investor_DynamischAbschlageProzent_Min,this.mInvestor_DynamischAbschlageProzent_Min ));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.Investor_DynamischAbschlageProzent_Max, this.mInvestor_DynamischAbschlageProzent_Max ));

     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.Investor_AbschlagGaussMean,      this.mInvestor_AbschlagGaussMean ));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.Investor_AbschlagGaussDeviation, this.mInvestor_AbschlagGaussDeviation ));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.Investor_AnzahlProzent_LinkBereich,  this.mInvestor_AnzahlProzent_LinkBereich ));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.Investor_AnzahlProzent_MittBereich,  this.mInvestor_AnzahlProzent_MittBereich ));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.Investor_AnzahlProzent_RechtBereich, this.mInvestor_AnzahlProzent_RechtBereich ));

     ppp.add( XmlHelpTool.getXmlComment( "Folgende Parameter sind relevant fuer Individuelle InnererWert Berechnung fuer Investor"));
     ppp.add( XmlHelpTool.getXmlComment( "Intervall Untergrenz und ObenGrenz, Schwelle, Potenzial sind in Prozent anzugeben"));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.Investor_InnererWertIntervall_Untergrenz, this.mInvestor_InnererWertIntervall_Untergrenz ));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.Investor_InnererWertIntervall_Obengrenz,  this.mInvestor_InnererWertIntervall_Obengrenz ));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.Investor_KursAnderung_Schwelle,           this.mInvestor_KursAnderung_Schwelle ));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.Investor_AktuellerInnererWert_Potenzial,  this.mInvestor_AktuellerInnererWert_Potenzial ));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.Investor_KursAnderung_ReferenzTag,        this.mInvestor_KursAnderung_ReferenzTag ));
     ppp.add( XmlHelpTool.getXmlComment( "#############################################################################"));

     ////////////////////////////////////////////////////////////////////////////////

     ppp.add( XmlHelpTool.getXmlComment( "# KursChangedProcentLimit 1: if KursChangedProcent is greater than Limi1, then ... "));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.Investor_KurschangedprocentLimit1, this.mInvestorKurschangedprocentlimit1  ));
     ppp.add( XmlHelpTool.getXmlComment( "# KursChangedProcentLimit 2: "));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.Investor_KurschangedprocentLimit2, this.mInvestorKurschangedprocentlimit2   ));
     ppp.add( XmlHelpTool.getXmlComment( "# KursChangedProcentLimit 3: "));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.Investor_KurschangedprocentLimit3 , this.mInvestorKurschangedprocentlimit3 ));

     ppp.add( XmlHelpTool.getXmlComment( "# OrderMenge Stufe1"));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.Investor_Order_Stufe1,mInvestorOrderMengeStufe1));
     ppp.add( XmlHelpTool.getXmlComment( "# OrderMenge Stufe2"));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.Investor_Order_Stufe2,mInvestorOrderMengeStufe2));
     ppp.add( XmlHelpTool.getXmlComment( "# OrderMenge Stufe3"));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.Investor_Order_Stufe3 ,mInvestorOrderMengeStufe3));
     ppp.add( XmlHelpTool.getXmlComment( "# OrderMenge Stufe4"));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.Investor_Order_Stufe4 ,mInvestorOrderMengeStufe4));

     ppp.add( XmlHelpTool.getXmlComment( "# Stufe 1 MarketOrder Mark for BilligestKauf , 'B' stands for Billigest"));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.Investor_Stufe1MarketOrderBilligestKauf, ""+this.mInvestorStufe1MarketOrderBilligestKauf ) );

     ppp.add( XmlHelpTool.getXmlComment( "# Stufe 1 MarketOrder Mark for BestVerkauf , 'B' stands for BestVerkauf"));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.Investor_Stufe1MarketOrderBestVerkauf, ""+this.mInvestorStufe1MarketOrderBestVerkauf ) );

     ppp.add( XmlHelpTool.getXmlComment( "# Stufe 2 MarketOrder Mark, 'B' stands for BilligestKauf"));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.Investor_Stufe2MarketOrderBilligestKauf, ""+this.mInvestorStufe2MarketOrderBilligestKauf ) );
     ppp.add( XmlHelpTool.getXmlComment( "# Stufe 2 MarketOrder Mark for BestVerkauf , 'B' stands for BestVerkauf"));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.Investor_Stufe2MarketOrderBestVerkauf, ""+this.mInvestorStufe2MarketOrderBestVerkauf ) );

     ppp.add( XmlHelpTool.getXmlComment( "# Stufe 3 MarketOrder Mark, 'B' stands for BilligestKauf"));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.Investor_Stufe3MarketOrderBilligestKauf, ""+this.mInvestorStufe3MarketOrderBilligestKauf ) );
     ppp.add( XmlHelpTool.getXmlComment( "# Stufe 3 MarketOrder Mark for BestVerkauf , 'B' stands for BestVerkauf"));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.Investor_Stufe3MarketOrderBestVerkauf, ""+this.mInvestorStufe3MarketOrderBestVerkauf ) );

     ppp.add( XmlHelpTool.getXmlComment( "# Stufe 4 MarketOrder Mark, 'B' stands for BilligestKauf"));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.Investor_Stufe4MarketOrderBilligestKauf, ""+this.mInvestorStufe4MarketOrderBilligestKauf ) );
     ppp.add( XmlHelpTool.getXmlComment( "# Stufe 4 MarketOrder Mark for BestVerkauf , 'B' stands for BestVerkauf"));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.Investor_Stufe4MarketOrderBestVerkauf, ""+this.mInvestorStufe4MarketOrderBestVerkauf ) );

     ppp.add( XmlHelpTool.getXmlComment( "# SchlafenProzent: definier wie haeufig Investor kein Buy/Sell-Order stellt"));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.Investor_SleepProcent ,this.mInvestorSleepProcent));

     ppp.add( XmlHelpTool.getXmlComment( "# " + ParameterNameConst.InvestorAffectedByOtherNode +" definiert ob Investor seinen Typ aendern darf.") );
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.InvestorAffectedByOtherNode ,this.mInvestorAffectedByOtherNode));

     ppp.add( XmlHelpTool.getXmlComment( "################################################################"));
     ppp.add( XmlHelpTool.getXmlComment( "##                                                            ##"));
     ppp.add( XmlHelpTool.getXmlComment( "##  Trend Agent Group Parameter                               ##"));
     ppp.add( XmlHelpTool.getXmlComment( "##                                                            ##"));
     ppp.add( XmlHelpTool.getXmlComment( "################################################################"));

     ppp.add( XmlHelpTool.getXmlComment( "# Minimal Init Cash  bzw.  Cash1"));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.NoiseTrader_InitCash_Min,mNoiseTraderInitCash_Min ));
     ppp.add( XmlHelpTool.getXmlComment( "# Maximal Init Cash  bzw.  Cash1"));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.NoiseTrader_InitCash_Max,mNoiseTraderInitCash_Max ));

     ppp.add( XmlHelpTool.getXmlComment( "# Minial Init Aktien  bzw.  Cash2"));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.NoiseTrader_InitStock_Min,mNoiseTraderInitAktien_Min ));

     ppp.add( XmlHelpTool.getXmlComment( "# Maximal Init Aktien  bzw.  Cash2"));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.NoiseTrader_InitStock_Max,mNoiseTraderInitAktien_Max ));

     ppp.add( XmlHelpTool.getXmlComment( "# KursChangedProcentLimit 1: if KursChangedProcent is greater than Limit1, then ... "));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.NoiseTrader_KurschangedprocentLimit1, this.mNoiseTraderKurschangedprocentlimit1  ));
     ppp.add( XmlHelpTool.getXmlComment( "# KursChangedProcentLimit 2: "));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.NoiseTrader_KurschangedprocentLimit2, this.mNoiseTraderKurschangedprocentlimit2  ));
     ppp.add( XmlHelpTool.getXmlComment( "# KursChangedProcentLimit 3: "));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.NoiseTrader_KurschangedprocentLimit3, this.mNoiseTraderKurschangedprocentlimit3  ));

     ppp.add( XmlHelpTool.getXmlComment( "# OrderMenge Stufe1: KursChangedProcent: [0, Limit1]  "));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.NoiseTrader_Order_Stufe1, mNoiseTraderOrderMengeStufe1  ));
     ppp.add( XmlHelpTool.getXmlComment( "# OrderMenge Stufe2:  KursChangedProcent: [Limit, Limit2] "));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.NoiseTrader_Order_Stufe2,mNoiseTraderOrderMengeStufe2  ));

     ppp.add( XmlHelpTool.getXmlComment( "# OrderMenge Stufe3:  KursChangedProcent: [Limit2, Limit3] "));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.NoiseTrader_Order_Stufe3, mNoiseTraderOrderMengeStufe3  ));

     ppp.add( XmlHelpTool.getXmlComment( "# OrderMenge Stufe4:  KursChangedProcent: [Limit3, ~] "));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.NoiseTrader_Order_Stufe4,mNoiseTraderOrderMengeStufe4  ));

     ppp.add( XmlHelpTool.getXmlComment( "# Stufe 1 MarketOrder Mark, 'B' stands for BilligestKauf"));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.NoiseTrader_Stufe1MarketOrderBilligestKauf, ""+this.mNoiseTraderStufe1MarketOrderBilligestKauf ) );
     ppp.add( XmlHelpTool.getXmlComment( "# Stufe 1 MarketOrder Mark, 'B' stands for BestVerkauf"));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.NoiseTrader_Stufe1MarketOrderBestVerkauf, ""+this.mNoiseTraderStufe1MarketOrderBestVerkauf ) );

     ppp.add( XmlHelpTool.getXmlComment( "# Stufe 2 MarketOrder Mark, 'B' stands for BilligestKauf"));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.NoiseTrader_Stufe2MarketOrderBilligestKauf, ""+this.mNoiseTraderStufe2MarketOrderBilligestKauf ) );
     ppp.add( XmlHelpTool.getXmlComment( "# Stufe 2 MarketOrder Mark, 'B' stands for BestVerkauf"));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.NoiseTrader_Stufe2MarketOrderBestVerkauf, ""+this.mNoiseTraderStufe2MarketOrderBestVerkauf ) );

     ppp.add( XmlHelpTool.getXmlComment( "# Stufe 3 MarketOrder Mark, 'B' stands for BilligestKauf"));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.NoiseTrader_Stufe3MarketOrderBilligestKauf, ""+this.mNoiseTraderStufe3MarketOrderBilligestKauf ) );
     ppp.add( XmlHelpTool.getXmlComment( "# Stufe 3 MarketOrder Mark, 'B' stands for BestVerkauf"));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.NoiseTrader_Stufe3MarketOrderBestVerkauf, ""+this.mNoiseTraderStufe3MarketOrderBestVerkauf ) );

     ppp.add( XmlHelpTool.getXmlComment( "# Stufe 4 MarketOrder Mark, 'B' stands for BilligestKauf"));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.NoiseTrader_Stufe4MarketOrderBilligestKauf, ""+this.mNoiseTraderStufe4MarketOrderBilligestKauf ) );
     ppp.add( XmlHelpTool.getXmlComment( "# Stufe 4 MarketOrder Mark, 'B' stands for BestVerkauf"));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.NoiseTrader_Stufe4MarketOrderBestVerkauf, ""+this.mNoiseTraderStufe4MarketOrderBestVerkauf ) );

     ppp.add( XmlHelpTool.getXmlComment  ( "# Parameter fuer MovingAveragePrice-Berechnung"));
     ppp.add( XmlHelpTool.getXmlComment  ( "# Jeder NoiseTrader bekommt einen zufaellig generierter MovingDays zwischen Min und Max"));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.NoiseTrader_MinMovingDays4AveragePrice ,mNoiseTrader_MinMovingDaysForAveragePrice));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.NoiseTrader_MaxMovingDays4AveragePrice ,mNoiseTrader_MaxMovingDaysForAveragePrice));

     ppp.add( XmlHelpTool.getXmlComment  ( "# LimitAdjust "));
     ppp.add( XmlHelpTool.getXmlComment  ( "# MinLimitAdjust and MaxAdjust are used for Limit-Generating  Limit = InnererWert * ( 1 + or - x %), x is [LimitMinAdjust, LimitMaxAdjust]"));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.NoiseTrader_MinLimitAdjust ,mNoiseTrader_MinLimitAdjust));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.NoiseTrader_MaxLimitAdjust ,mNoiseTrader_MaxLimitAdjust));

     ppp.add( XmlHelpTool.getXmlComment  ( "# SchlafenProcent: definiert wie haeufig NoiseTrader kein Buy/Sell-Order stellt"));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.NoiseTrader_SleepProcent,mNoiseTrader_SleepProcent));

     ppp.add( XmlHelpTool.getXmlComment  ( "# " + ParameterNameConst.NoiseTraderAffectedByOtherNode +" definiert ob NoiseTrader seinen Typ aendern darf.") );
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.NoiseTraderAffectedByOtherNode ,this.mNoiseTraderAffectedByOtherNode));

     ppp.add( XmlHelpTool.getXmlComment  ( "################################################################"));
     ppp.add( XmlHelpTool.getXmlComment  ( "##                                                            ##"));
     ppp.add( XmlHelpTool.getXmlComment  ( "##  Retail Agent Group Parameter                              ##"));
     ppp.add( XmlHelpTool.getXmlComment  ( "##                                                            ##"));
     ppp.add( XmlHelpTool.getXmlComment  ( "################################################################"));

     ppp.add( XmlHelpTool.getXmlComment  ( "# Init Cash"));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.BlankoAgent_InitCash_Min, mBlankoAgentInitCash_Min ));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.BlankoAgent_InitCash_Max, mBlankoAgentInitCash_Max ));

     ppp.add( XmlHelpTool.getXmlComment  ( "# Init Stock"));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.BlankoAgent_InitStock_Min, mBlankoAgentInitAktien_Min ));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.BlankoAgent_InitStock_Max, mBlankoAgentInitAktien_Max ));

     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.BlankoAgentDayOfIndexWindow_Min, mBlankoAgentDayOfIndexWindow_Min ));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.BlankoAgentDayOfIndexWindow_Max, mBlankoAgentDayOfIndexWindow_Max ));

     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.BlankoAgentInactiveDays_Min, mBlankoAgentInactiveDays_Min ));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.BlankoAgentInactiveDays_Max, mBlankoAgentInactiveDays_Max ));

     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.BlankoAgentMindestActiveDays, mBlankoAgentMindestActiveDays ));

     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.BlankoAgentPlusIndexProcentForActivation_Min, mBlankoAgentPlusIndexProcentForActivation_Min ));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.BlankoAgentPlusIndexProcentForActivation_Max, mBlankoAgentPlusIndexProcentForActivation_Max ));

     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.BlankoAgentMinusIndexProcentForDeactivation_Min, mBlankoAgentMinusIndexProcentForDeactivation_Min ) );
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.BlankoAgentMinusIndexProcentForDeactivation_Max, mBlankoAgentMinusIndexProcentForDeactivation_Max ) );

     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.BlankoAgent_SleepProcent, mBlankoAgent_SleepProcent ));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.BlankoAgent_CashAppendAllowed, mBlankoAgent_CashAppendAllowed ));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.BlankoAgent_AppendCash, mBlankoAgent_AppendCash ));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.BlankoAgent_DaysOfTotalSell, mBlankoAgent_DaysOfTotalSell ) );

     ppp.add( XmlHelpTool.getXmlComment( "################################################################"));
     ppp.add( XmlHelpTool.getXmlComment( "##                                                            ##"));
     ppp.add( XmlHelpTool.getXmlComment( "##  RandomTrader Parameter                                    ##"));
     ppp.add( XmlHelpTool.getXmlComment( "##                                                            ##"));
     ppp.add( XmlHelpTool.getXmlComment( "################################################################"));

     ppp.add( XmlHelpTool.getXmlComment( "#RandomTrader Init Cash bzw. Cash1"));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.RandomTrader_InitCash, mRandomTraderInitCash ));
     ppp.add( XmlHelpTool.getXmlComment( "#RandomTrader Init Aktien bzw. Cash2"));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.RandomTrader_InitStock, mRandomTraderInitAktien ));

     ppp.add( XmlHelpTool.getXmlComment( "#RandomTrader Kauf-Wahrscheinlichkeit in Prozent "));
     ppp.add( XmlHelpTool.getXmlComment( "# Achtung: Kauf-Wahrschlichkeit + Verkauf-Wahrscheinlichkeit muss 100 sein"));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.RandomTraderBuyProbability, mRandomTraderBuyProbability ));

     ppp.add( XmlHelpTool.getXmlComment( "#RandomTrader Kauf-Wahrscheinlichkeit 'Chepest' in Prozent"));
     ppp.add( XmlHelpTool.getXmlComment( "#Dieser Parameter steuert wie hauefig der RandomTrader den Order mit Chepest-Buy zu stellen"));
     ppp.add( XmlHelpTool.getXmlComment( "# Achtung: Chepest + Indexbased muss 100 sein "));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.RandomTraderBuyProbabilityChepest,mRandomTraderBuyProbabilityCheapest ));

     ppp.add( XmlHelpTool.getXmlComment( "#RandomTrader Kauf-Wahrscheinlichkeit 'Indexbased' in Prozent"));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.RandomTraderBuyProbabilityIndexBased, mRandomTraderBuyProbabilityIndexBased ));

     ppp.add( XmlHelpTool.getXmlComment( "#RandomTrader Verkauf-Wahrscheinlichkeit in Prozent"));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.RandomTraderSellProbability, mRandomTraderSellProbability));
     ppp.add( XmlHelpTool.getXmlComment( "#RandomTrader Verkauf-Wahrscheinlichkeit 'IndexBased' in Prozent"));
     ppp.add( XmlHelpTool.getXmlComment( "# Achtung: IndexBased + Best muss 100 sein. "));

     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.RandomTraderSellProbabilityIndexBased, mRandomTraderSellProbabilityIndexbased ));
     ppp.add( XmlHelpTool.getXmlComment( "#RandomTrader Verkauf-Wahrscheinlichkeit 'Best' in Prozent"));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.RandomTraderSellProbabilityBest,mRandomTraderSellProbabilityBest));

     ppp.add( XmlHelpTool.getXmlComment( "#RandomTrader Schlafen-Wahrscheinlichkeit in Prozent"));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.RandomTraderWaitProbability,mRandomTraderWaitProbability));

     ppp.add( XmlHelpTool.getXmlComment( "#RandomTrader Minimal und Maximal OrderMenge steuert die Menge in Order"));
     ppp.add( XmlHelpTool.getXmlComment( "#RandomTrader Minimal OrderMenge"));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.RandomTraderMinMenge,mRandomTraderMinMenge ));
     ppp.add( XmlHelpTool.getXmlComment( "#RandomTrader Maximal OrderMenge"));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.RandomTraderMaxMenge,mRandomTraderMaxMenge ));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.RandomTraderRandomSeed4Decision,mRandomTraderRandomSeed4Decision ));

     ppp.add( XmlHelpTool.getXmlComment( "################################################################"));
     ppp.add( XmlHelpTool.getXmlComment( "##                                                            ##"));
     ppp.add( XmlHelpTool.getXmlComment( "## Tobintax Agent Parameter                                   ##"));
     ppp.add( XmlHelpTool.getXmlComment( "##                                                            ##"));
     ppp.add( XmlHelpTool.getXmlComment( "################################################################"));

     ppp.add( XmlHelpTool.getXmlComment( "#Interventionsband in %"));
     ppp.add( XmlHelpTool.getXmlTagFormat(  ParameterNameConst.TobintaxInterventionsband ,this.mTobintax_Interventionsband));

     ppp.add( XmlHelpTool.getXmlComment( "#Tage fuer Berechnung der Mittelwert der Kurs"));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.TobintaxAgentTag4AverageKurs , this.mTobintax_Days4AverageKurs));

     ppp.add( XmlHelpTool.getXmlComment( "#Tradeprozent von Tobintax Agent"));
     ppp.add( XmlHelpTool.getXmlComment( "#wenn er kauft, benutzt er     K% von seinem CASH1"));
     ppp.add( XmlHelpTool.getXmlComment( "#wenn er verkauft, verkauft er K% von seinem CASH2"));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.TobintaxAgentTradeProzent, this.mTobintax_TradeProzent));

     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.Cash1_Name , this.mCash1_Name));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.Cash2_Name,  this.mCash2_Name));

     ppp.add( XmlHelpTool.getXmlComment( "################################################################"));
     ppp.add( XmlHelpTool.getXmlComment( "##                                                            ##"));
     ppp.add( XmlHelpTool.getXmlComment( "## Logging control parameter                                  ##"));
     ppp.add( XmlHelpTool.getXmlComment( "##                                                            ##"));
     ppp.add( XmlHelpTool.getXmlComment( "################################################################"));

     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.LogDailyTradeBook , ""+this.mLogDailyTradeBook ) );
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.LogAgentExchangeHistoy , ""+this.mLogAgentExchangeHistoy ) );
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.LogAgentDailyDepot , ""+this.mLogAgentDailyDepot ) );

     ppp.add( XmlHelpTool.getXmlComment( "################################################################"));
     ppp.add( XmlHelpTool.getXmlComment( "##                                                            ##"));
     ppp.add( XmlHelpTool.getXmlComment( "## Common parts of Agent Network Node->AgentType Mapping      ##"));
     ppp.add( XmlHelpTool.getXmlComment( "##                                                            ##"));
     ppp.add( XmlHelpTool.getXmlComment( "################################################################"));

     ppp.add( XmlHelpTool.getXmlComment( "#Repeat Times of every Netzwork"));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.RepeatTimes, this.mRepeatTimes));

     //ppp.add( XmlHelpTool.getXmlComment("# definiere ob alle Netzwerk die gemeinsame Node->Type Distribution verwenden "));
     //ppp.add( XmlHelpTool.getXmlTagFormat(ParameterNameConst.UseCommonNetworkNode2AgentTypeDistribution, this.mUseCommonNode2TypeDistribution) );

     ppp.add( XmlHelpTool.getXmlComment( "# InvestorProzent und NoiseTraderProzent von der Netzwerknodes: %"));
     ppp.add( XmlHelpTool.getXmlComment( "# InvestorProzent + NoiseTraderProzent soll 100 sein "));
     ppp.add( XmlHelpTool.getXmlComment( "# These parameter make sense only when '" +
                                          ParameterNameConst.UseCommonNetworkNode2AgentTypeDistribution+"' is set to true"));

     //ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.InvestorProcent, mCommonInvestor_Procent));
     //ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.NoiseTraderProcent, mCommonNoiseTrader_Procent));

     ppp.add( XmlHelpTool.getXmlComment( "# Absolute Anzahl von RandomTrader "));
     ppp.add( XmlHelpTool.getXmlTagFormat( ParameterNameConst.RandomTrader, mAnzahlRandomTrader));

     //ppp.add( XmlHelpTool.getXmlComment( "# if Random-Zuordnung des Nodes zu AgentType verwendet ist.") );
     //ppp.add( XmlHelpTool.getXmlTagFormat(ParameterNameConst.NetworkNodeDistributionRandomMode, this.mCommonNode2TypeDistributionRandomModeEnabled) );

     String tt[] = new String[ ppp.size() ];
     for (int i=0; i< ppp.size(); i++ )
     {
        tt[i] = (String)  ppp.elementAt(i);
     }
     return tt;
  }

  /**
   * self check
   * @param args
   */
  public static void main( String args[] )
  {
       ConfData mm = new ConfData();

       String ss[] = mm.getXmlOutput();

       for ( int i=0; i< ss.length; i++)
       {
         System.out.println(ss[i]);
       }
  }

}