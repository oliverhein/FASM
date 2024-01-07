/**
 * Überschrift:
 * Beschreibung:
 * Copyright:     Copyright (c) 2003
 * Organisation:
 * @author Wang
 * @version 1.0
 *
 * This class contains the parameter of business of agents
 * Investor, NoiseTrader and RandomTrader
 */

package de.marketsim.config;

import java.io.*;
import java.util.Properties;
import java.util.Vector;
import java.text.NumberFormat;

import de.marketsim.SystemConstant;
import de.marketsim.agent.stockstore.stockdata.InnererwertRandomWalkGenerator;
import de.marketsim.util.HelpTool;
import de.marketsim.util.HTMLCreator;
import de.marketsim.config.FasmGaussConfig;

public class Configurator
{
  // public static  AgentDistributor  mAgentDistributor= new AgentDistributor();
  // public static int  mDialogBasicProcessMode = 1;
  // public static int  mBatchProcessMode       = 2;
  // public static int  mProcessMode = mDialogBasicProcessMode;  // Default mode
  public static boolean mUserRequiredBreak = false;
  public static  ConfData mConfData = new ConfData();;

  //public static  Vector   mNetworkConfigList = new Vector();
  public static  NetworkConfigManager mNetworkConfigManager = new NetworkConfigManager();

  public static  int      mNetworkConfigCurrentIndex = 0;
  public static  InnererwertRandomWalkGenerator mInnererwertRandomWalkGenerator=null;

  //public static  CommonNode2TypeDistribution mCommonNode2TypeDistribution= new  CommonNode2TypeDistribution();
  public static  NetworkCommonParameterDatabase mNetworkCommonParameterDatabase = new NetworkCommonParameterDatabase();

  public static  int mTypeChangeIndicator = 0;  // für statistic

  // Check if Graphic is displayed.

  public static boolean isGraphicDisplayed()
  {
       if ( System.getProperty("DisplayGraphic").equalsIgnoreCase("yes" ) )
       {
         return true;
       }
       else
       {
         return false;
       }
  }

  /**
   * !!!! Network File name kann nicht als ID benutzt werden !!!!!!!
   *
   * This methode will be called before Simulation begins.
   *
   */
  public static void setUniqueIDforAllNetworks()
  {
    if ( mNetworkConfigManager.getSize() == 0 )
    {
       return;
    }


    boolean IsOK = true;
    for ( int i=0; i<mNetworkConfigManager.getSize(); i++)
    {
       NetworkConfig thisnetwork = mNetworkConfigManager.getNetworkConfig(i);
       thisnetwork.mUniqueID = "NETWORKID-"+i;
    }
  }

  public static void ResetRunCounterOfNetwork()
  {
    for ( int i=0; i<mNetworkConfigManager.getSize(); i++)
    {
       NetworkConfig thisnetwork = mNetworkConfigManager.getNetworkConfig(i);
       thisnetwork.mCurrentRunningNo = 1;
    }
  }

  /* This methode will not be used.

  public static int checkNetworksHavesameNodes() throws Exception
  {
      if ( mNetworkConfigManager.getSize() == 0 )
      {
         throw new Exception("No network file is defined.");
      }

      NetworkConfig lastnetwork =( NetworkConfig ) mNetworkConfigList.elementAt(0);
      boolean IsOK = true;
      for ( int i=1; i<mNetworkConfigList.size(); i++)
      {
         NetworkConfig thisnetwork = ( NetworkConfig ) mNetworkConfigList.elementAt(i);
         if ( thisnetwork.mNodesInNetwork != lastnetwork.mNodesInNetwork )
         {
            System.out.println( thisnetwork.mNetworkFileName + " has " + thisnetwork.mNodesInNetwork + " nodes");
            System.out.println( lastnetwork.mNetworkFileName + " has " + lastnetwork.mNodesInNetwork + " nodes");
            System.out.println("Nodes in Networks are not same. This is not allowed. Please check the network file defined.");
            IsOK = false;
         }
      }

      if ( ! IsOK )
      {
        throw new Exception("Nodes in Networks are not same. This is not allowed. Please check the network file defined.");
      }
      return lastnetwork.mNodesInNetwork;
  }
   */

  public static boolean istAktienMarket()
  {
     return mConfData.mMarketMode == mConfData.mAktienMarket;
  }

  public static String[] getAllParameterPair(boolean pAddCommenta )
  {
       return mConfData.getAllParameterPairDetailedCommenta();
  }

  /*
  public static String[] getAllParameterPairDetailedCommenta( )
  {
   String[] ttt = mConfData.getAllParameterPairDetailedCommenta();
   // 3 Lines are for Comments
   // Configurator.mNetworkConfigList.size()*2  for network-related parameter
   String[] mmm = new String[ttt.length + mNetworkConfigManager.getSize()*2 + 3 ];
   for ( int i=0; i<ttt.length; i++)
   {
     mmm[i] = ttt[i];
   }

   mmm[ ttt.length    ] ="###################################################################################################################";
   mmm[ ttt.length +1 ] ="# Network - related parameter which are only used for Tobintax Mode, In AktienMode it will be ignored             #";
   mmm[ ttt.length +2 ] ="###################################################################################################################";
   int p = ttt.length + 3;
   for ( int i=0; i<Configurator.mNetworkConfigList.size(); i++)
   {
       NetworkConfig onenetworkconfig = ( NetworkConfig ) Configurator.mNetworkConfigList.elementAt(i);
       mmm[ p + i*2      ] = onenetworkconfig.mNetworkfilenameOhnePfad + ".FESTETOBINTAX=" + onenetworkconfig.mFesteTobinTax;
       mmm[ p + i*2 + 1  ] = onenetworkconfig.mNetworkfilenameOhnePfad + ".EXTRATOBINTAX=" + onenetworkconfig.mExtraTobinTax;
   }
   return mmm;

  }
  */

  public static void setDepotFileList(Vector pDepotList)
  {
      mConfData.mDepotFileList = pDepotList;
  }

  public static NetworkConfig getCurrentNetworkConfig()
  {
     return mNetworkConfigManager.getNetworkConfig( mNetworkConfigCurrentIndex );
  }

  public static void setExchangeHistoryFileList(Vector pFileList)
  {
      mConfData.mExchangeHistoryFileList = pFileList;
  }

 ///////////////////////////////////////////////////////////////////////////

  public static boolean getFileReadOK()
  {
     return mConfData.mFileReadSuccessful;
  }

  public static String getLogrenditenLogFile()
  {
      return mConfData.LogFileNameMapper( mConfData.mLogrenditenLogFilename);
  }


  public static String getTradeDailyStatisticLogFile()
  {
      return mConfData.LogFileNameMapper(mConfData.mTradeDailyStatisticFilename );
  }

  public static String getGraphStateHistoryLogFile()
  {
      return mConfData.LogFileNameMapper(mConfData.mGraphStateHistory);
  }

  public static String getAgentStateChangeStatisticLogFile()
  {
      return mConfData.LogFileNameMapper(mConfData.mAgentStateChangeStatisticLogFile);
  }

  public static String getInnererwert300LogFile()
  {
     return mConfData.LogFileNameMapper(mConfData.mInnererwert300LogFilename);
  }

  public static String getDepotLogFile()
  {
     return mConfData.LogFileNameMapper(mConfData.mDepotLogFilename) ;
  }

  public static String getAutoKorrelationLogFile()
  {
      return LogFileNameMapper(mConfData.mAutoKorrelationLogFilename);
  }

  public static String getKorrelationLogFile()
  {
      return mConfData.LogFileNameMapper(mConfData.mKorrelationLogFilename);

  }

  public static String getHillEstimatorLogFile()
  {
      return mConfData.LogFileNameMapper(mConfData.mHillEstimatorLogFile);
  }

  /*  Die absolute Anzahl of RandomerTrader  */
  public static int getRandomTrader()
  {
     return mConfData.mAnzahlRandomTrader;
  }

  public static String getParameter(String ParameterName)
  {
      return mConfData.AgentConfiguration.getProperty(ParameterName);
  }

  public static String getParameter(String ParameterName, String pDefault)
  {
      return mConfData.AgentConfiguration.getProperty(ParameterName, pDefault);
  }

  /** GewinnStatusExchangeProbability **/
  public static void setParameterReady(boolean p)
  {
     mConfData.mParameterReady = p;
  }

  public static boolean isParameterReady()
  {
     return mConfData.mParameterReady;
  }

 /** mAheadDaysForGewinnCalculation **/

 public static int getAheadDaysForGewinnCalculation()
 {
    return mConfData.mAheadDaysForGewinnCalculation;
 }

 public static synchronized void setAheadDaysForGewinnCalculation( int pDays )
 {
     mConfData.mAheadDaysForGewinnCalculation = pDays;
 }

 public static void setOneConfigStarttime(String pOneConfigStarttime)
 {
      mConfData.mOneConfigStarttime = pOneConfigStarttime;
 }

 public static String getTextReportFileNameofSimulationSerie()
 {
   return  mConfData.mMainLogDirectory+"/summary.csv";
 }

 public static String getHtmlReportFileNameofSimulationSerie()
 {
   return  mConfData.mMainLogDirectory+"/summary.html";
 }

 public static String LogFileNameMapper(String pFileName)
 {
     return mConfData.getLogFileDirectory() + mConfData.mPfadSeperator + pFileName;
 }

 public static String getAgentnumberLogFile()
 {
    return  mConfData.LogFileNameMapper(mConfData.mAgentNumberLogFile);
 }

 public static String getPriceStatisticLogFile()
 {
    return mConfData.LogFileNameMapper(mConfData.mPriceStatisticLogFile);
 }

 public static NetworkConfig getNetworkConfig(int pIndex)
 {
     System.out.print("Configurator.mNetworkConfigManager.getSize()=" + Configurator.mNetworkConfigManager.getSize() + " pIndex= " + pIndex );
     return Configurator.mNetworkConfigManager.getNetworkConfig(pIndex);
 }

public static String  getDepotListFileTotal()
{
   return  LogFileNameMapper( "depotlist-total.csv" );
}

public static String  getDepotListFileGroup()
{
   return  LogFileNameMapper( "depotlist-grouped.csv" );
}

public static String getHtmlProfileName()
{
   return  LogFileNameMapper( "Profile.html" );
}

public static String getHtmlProfilePath()
{
        String ss = mConfData.getLogFileDirectory();
        int i = ss.indexOf( mConfData.mPfadSeperator );
        ss = ss.substring(i+1);
        return  ss+mConfData.mPfadSeperator+"Profile.html";
}




public static void createTestSummaryInHtml()
{
    String InitCashTitle ;
    String InitAktienTitle;
    String PriceName;
    boolean printTobintax;
    String  MarketName = "";

    if ( mConfData.mMarketMode == SystemConstant.MarketMode_AktienMarket )
    {
        InitCashTitle   ="Init Cash";
        InitAktienTitle ="Init Securities";
        PriceName="Price";
        printTobintax= false;
        MarketName = "Stock Market";
    }
    else
    {
        InitCashTitle   ="Init Cash1(" +  mConfData.mCash1_Name +")";
        InitAktienTitle ="Init Cash2("+  mConfData.mCash2_Name +")";
        PriceName="Exchange Rate";
        printTobintax= true;
        MarketName = "Cash Market";
    }

  try
  {
    String profilename = getHtmlProfileName() ;
    java.io.PrintStream  Paramfile= new java.io.PrintStream( new java.io.FileOutputStream( profilename ));
    HTMLCreator.putHtmlHead( Paramfile );
    HTMLCreator.putHtmlBodyBegin( Paramfile );
    String realtimeformat = mConfData.mTimeStamp;
    byte bb[] = realtimeformat.getBytes();
    bb[10]=' ';
    bb[13]=':';
    bb[16]=':';
    realtimeformat = new String( bb );
    HTMLCreator.putHTMLLine(Paramfile,"<H2>The simulation begins at " +  realtimeformat );
    String trennline="--------------------------------------------------------------------------------";
    HTMLCreator.putHTMLLine(Paramfile,trennline);

    HTMLCreator.putHTMLContent(Paramfile,"<Table border='1' >");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD><H3>Market</H3</TD> <TD><H3>"+MarketName+"</H3> </TD> </TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD><H3>Config File</H3</TD> <TD><H3>"+mConfData.mConfigFile+"</H3> </TD> </TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD><H3>Inner Value File </H3</TD> <TD><H3>"+mConfData.mInnererWertMuster+"</H3> </TD> </TR>");

    if ( mConfData.mMarketMode == mConfData.mMoneyMarket )
    {
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD><H3>Cash1 </H3</TD> <TD><H3> " +  mConfData.mCash1_Name +"</H3> </TD> </TR>" );
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD><H3>Cash2 </H3</TD> <TD><H3> " +  mConfData.mCash2_Name +"</H3> </TD> </TR>" );
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD><H3>Exchange Rate </H3</TD> <TD><H3> " + mConfData.mCash1_Name+"/" + mConfData.mCash2_Name +"</H3> </TD> </TR>" );
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD><H3>Is Tobin Tax Agent active? </H3</TD> <TD><H3> " +  mConfData.mTobintaxAgentAktive +"</H3> </TD> </TR>" );
    HTMLCreator.putHTMLLine(Paramfile,"Attention: Depot is always calculated in "+mConfData.mCash1_Name );
    }

    //HTMLCreator.putHTMLContent(Paramfile,"</Table>");
    //HTMLCreator.putHTMLLine(Paramfile,trennline);
    //HTMLCreator.putHTMLLine(Paramfile,"Parameter of Agent-Verteilung");
    //HTMLCreator.putHTMLLine(Paramfile,trennline);

    // HTMLCreator.putHTMLContent(Paramfile,"<Table border='1' >");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD><H2>Parameter for mapping of Network Node</H2></TD></TR>");

    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD><H3>Parameter Name</H3</TD> <TD><H3> Value</H3> </TD> </TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Network</TD><TD>"       + Configurator.getCurrentNetworkConfig().mNetworkFileName  + "</TD> </TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Total Agents </TD><TD>" + mConfData.mAnzahlTotalAgents  +"</TD> </TR>");

    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Agents from Netzwerk </TD><TD>" +  mConfData.mNodesInNetwork +"</TD> </TR>" );
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>&nbsp;&nbsp;&nbsp;Fundamental </TD><TD>"         + mConfData.mAnzahlInvestor +"</TD> </TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>&nbsp;&nbsp;&nbsp;Trend </TD><TD>"               + mConfData.mAnzahlNoiseTrader +"</TD> </TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>&nbsp;&nbsp;&nbsp;Retail </TD><TD>"              + mConfData.mAnzahlBlankoAgent +"</TD> </TR>");

    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Liquidity </TD><TD>"  + mConfData.mAnzahlRandomTrader +"</TD> </TR>");

    //HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Random Mapping </TD><TD>"+ mConfData.mCurrentNode2TypeDistributionRandomModeEnabled +"</TD> </TR>");
    //HTMLCreator.putHTMLContent(Paramfile,"</Table>");

    //HTMLCreator.putHTMLLine(Paramfile,"<H2>");
    //HTMLCreator.putHTMLLine(Paramfile,trennline);
    //HTMLCreator.putHTMLLine(Paramfile,"General Parameter");

    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD><H2>General Parameter</H2></TD></TR>");

    //HTMLCreator.putHTMLLine(Paramfile,trennline);
    //HTMLCreator.putHTMLLine(Paramfile,"<H3>");
    //HTMLCreator.putHTMLContent(Paramfile,"<Table border='1' >");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD><H3>Parameter Name</H3</TD> <TD><H3> Value</H3> </TD> </TR> ");

    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Handelstag dieser Simulation </TD> <TD>" + mConfData.mHandelsday +"</TD> </TR>" );
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>GewinnStatusAustauschProbabiliy </TD> <TD>" + mConfData.mGewinnStatusExchangeProbability + "%" +"</TD> </TR>");

    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>MaxVerlustAnzahl Mode </TD> <TD>"  + mConfData.mMaxLostNumberMode +"</TD> </TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Gemeinsame feste MaxVerlustanzahl <BR> für Strategiewechsel (Fixed Mode) </TD> <TD>"  + mConfData.mFixedMaxLostNumber +"</TD> </TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Random Seed für einzelne MaxVerlustanzahl <BR> of Agenten für Strategiewechsel (variable Mode) </TD> <TD>"  + mConfData.mMaxLostNumberSeed +"</TD> </TR>");

    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>GegenüberTag für Gewinnberechnung </TD> <TD>" + mConfData.mAheadDaysForGewinnCalculation +"</TD> </TR>" );

    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>HillEstimatorProzent </TD> <TD>" + mConfData.mHillEstimatorProcent + "%" +"</TD> </TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Anzahl-AutoKorrelation </TD> <TD>" + mConfData.mAnzahlAutoKorrelation +"</TD> </TR>");
    //HTMLCreator.putHTMLContent(Paramfile,"</Table>");

    //HTMLCreator.putHTMLLine(Paramfile,"<H2>");
    //HTMLCreator.putHTMLLine(Paramfile,trennline);
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD><H2>Parameter fuer Generierung of Innererwert</H2></TD></TR>");
    //HTMLCreator.putHTMLLine(Paramfile,"Parameter fuer Generierung of Innererwert");
    //HTMLCreator.putHTMLLine(Paramfile,trennline);
    //HTMLCreator.putHTMLLine(Paramfile,"<H3>");

    //HTMLCreator.putHTMLContent(Paramfile,"<Table border='1' >");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD><H3>Parameter Name</H3</TD> <TD><H3> Value</H3> </TD> </TR> ");

    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Period of InnererwertKurv (Tage) </TD> <TD>" + mConfData.mDaysOfOnePeriod +"</TD> </TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Handelstage dieser Simulation </TD> <TD>" + mConfData.mHandelsday +"</TD> </TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Minimal-Wert von InnererWert </TD> <TD>" +  mConfData.mMinInnererWert +"</TD> </TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Maximal-Wert von InnererWert </TD> <TD>" + mConfData.mMaxInnererWert +"</TD> </TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Start-Wert von InnererWert </TD> <TD>" + mConfData.mBeginInnererWert +"</TD> </TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Maximale Abweichnung gegen Vortag </TD> <TD>" + mConfData.mInnererwertMaximalTagAbweichnung +  "%" +"</TD> </TR>");
    if (  mConfData.mInnererwertEntwicklungsTrend == 1 )
    {
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>InnererwertEntwicklungsTrend </TD> <TD>Aufsteigen</TD> </TR>");
    }
    else
    {
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>InnererwertEntwicklungsTrend </TD> <TD>Absteigen</TD> </TR>");
    }

    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD><H2>Configuration von Fundamental Investor</H2></TD></TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD><H3>Parameter Name</H3</TD> <TD><H3> Value</H3> </TD> </TR>");

    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>AbschlageProzent Parameter");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Minimal-AbschlageProzent</TD> <TD>" + mConfData.mInvestor_DynamischAbschlageProzent_Min + "%" +"</TD> </TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Maximal-AbschlageProzent</TD> <TD>" + mConfData.mInvestor_DynamischAbschlageProzent_Max + "%"+"</TD> </TR>");

    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>AbschlageProzent-GaussMean</TD> <TD>"      + mConfData.mInvestor_AbschlagGaussMean  +"</TD> </TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>AbschlageProzent-GaussDeviation</TD> <TD>" + mConfData.mInvestor_AbschlagGaussDeviation +"</TD> </TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>AnzahlProzent-LinkBereich</TD> <TD>"       + mConfData.mInvestor_AnzahlProzent_LinkBereich + "%" +"</TD> </TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>AnzahlProzent-MittBereich</TD> <TD>"       + mConfData.mInvestor_AnzahlProzent_MittBereich + "%" +"</TD> </TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>AnzahlProzent-RechtBereich</TD> <TD>"       + mConfData.mInvestor_AnzahlProzent_RechtBereich + "%" +"</TD> </TR>");

    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Investor Minimal " +  InitCashTitle + "</TD> <TD>" + mConfData.mInvestorInitCash_Min +"</TD> </TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Investor Maximal " +  InitCashTitle + "</TD> <TD>" + mConfData.mInvestorInitCash_Max +"</TD> </TR>");

    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Investor Minimal " +  InitAktienTitle + "</TD> <TD>" + mConfData.mInvestorInitAktien_Min +"</TD> </TR>" ) ;
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Investor Maximal " +  InitAktienTitle + "</TD> <TD>" + mConfData.mInvestorInitAktien_Max +"</TD> </TR>" ) ;

    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Investor  SchlafProzent</TD> <TD>" + mConfData.mInvestorSleepProcent  +"</TD> </TR>") ;

    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Investor affected by NoiseTrader</TD> <TD>" + mConfData.mInvestorAffectedByOtherNode  +"</TD> </TR>") ;

    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Individueller InnererWertAbweichung Untergrenz </TD> <TD>" + mConfData.mInvestor_InnererWertIntervall_Untergrenz  +"% </TD> </TR>") ;
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Individueller InnererWertAbweichung Obengrenz </TD> <TD>" + mConfData.mInvestor_InnererWertIntervall_Untergrenz  +"% </TD> </TR>") ;
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>ReferenzTag der Kursaenderung </TD> <TD>" + "Vor " + mConfData.mInvestor_KursAnderung_ReferenzTag  +". Tag</TD> </TR>") ;
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Kursaenderungsschwelle </TD> <TD>" + mConfData.mInvestor_KursAnderung_Schwelle  +"% </TD> </TR>") ;
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Potenz der aktuellen individuellen InnererWert </TD> <TD>" + mConfData.mInvestor_AktuellerInnererWert_Potenzial +"% </TD> </TR>") ;

    HTMLCreator.putHTMLContent(Paramfile,"<TR> <TD> <h4>Investor Stufen Regel </h4></TD> </TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Stufen </TD> <TD>Kursänderung </TD> <TD>Ordermenge </TD> <TD>Billigstkauf </TD> <TD>Bestverkauf </TD>  </TR>");

    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Stufe1</TD> <TD>" + "-Infinitive ~~"+ mConfData.mInvestorKurschangedprocentlimit1
                                            +"%</TD><TD>"+mConfData.mInvestorOrderMengeStufe1
                                            +"</TD><TD> " + getMarketOrderDefinition (  mConfData.mInvestorStufe1MarketOrderBilligestKauf )
                                            +"</TD><TD> " + getMarketOrderDefinition (  mConfData.mInvestorStufe1MarketOrderBestVerkauf  ) + "</TD>  </TR>");

    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Stufe2</TD> <TD>" +  mConfData.mInvestorKurschangedprocentlimit1 +"~~"+mConfData.mInvestorKurschangedprocentlimit2
                                            +"%</TD> <TD>" + mConfData.mInvestorOrderMengeStufe2
                                            +"</TD> <TD>" + getMarketOrderDefinition ( mConfData.mInvestorStufe2MarketOrderBilligestKauf )
                                            +"</TD> <TD>" + getMarketOrderDefinition ( mConfData.mInvestorStufe2MarketOrderBestVerkauf ) + "</TD>  </TR>");


    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Stufe3</TD> <TD>" + mConfData.mInvestorKurschangedprocentlimit2 +" ~~"+ mConfData.mInvestorKurschangedprocentlimit3
                                            +"%</TD><TD>" + mConfData.mInvestorOrderMengeStufe3
                                            +"</TD> <TD>" + getMarketOrderDefinition ( mConfData.mInvestorStufe3MarketOrderBilligestKauf )
                                            +"</TD> <TD>" + getMarketOrderDefinition ( mConfData.mInvestorStufe3MarketOrderBestVerkauf ) + "</TD>  </TR>");


    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Stufe4</TD> <TD>" + mConfData.mInvestorKurschangedprocentlimit3 + " ~~ Infinitive"
                                            +"%</TD><TD>" + mConfData.mInvestorOrderMengeStufe4
                                            +"</TD> <TD>" + getMarketOrderDefinition ( mConfData.mInvestorStufe4MarketOrderBilligestKauf )
                                            +"</TD> <TD>" + getMarketOrderDefinition ( mConfData.mInvestorStufe4MarketOrderBestVerkauf ) + "</TD>  </TR>");





/*
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD> Kurs Änderung Prozent Grenz 1</TD> <TD>" + mConfData.mInvestorKurschangedprocentlimit1 +"%</TD> </TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD> Kurs Änderung Prozent Grenz 2</TD> <TD>" + mConfData.mInvestorKurschangedprocentlimit2 +"%</TD> </TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD> Kurs Änderung Prozent Grenz 3</TD> <TD>" + mConfData.mInvestorKurschangedprocentlimit3 +"%</TD> </TR>");


    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Investor OrderMengeStufe1 </TD> <TD>" + mConfData.mInvestorOrderMengeStufe1 +"</TD>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Investor OrderMengeStufe2 </TD> <TD>" + mConfData.mInvestorOrderMengeStufe2 +"</TD> </TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Investor OrderMengeStufe3 </TD> <TD>" + mConfData.mInvestorOrderMengeStufe3 +"</TD> </TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Investor OrderMengeStufe4 </TD> <TD>" + mConfData.mInvestorOrderMengeStufe4 +"</TD> </TR>");

    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD> Billigenkauf Mark </TD> <TD>" + mConfData.mInvestorStufe1MarketOrderBilligestKauf +"</TD> </TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD> Bestenverkaufkauf Mark </TD> <TD>" + mConfData.mInvestorStufe1MarketOrderBestVerkauf +"</TD> </TR>");
*/
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD><H2>Configuration of Trend Trader</H2></TD></TR>");

    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD><H3>Parameter Name</H3</TD> <TD><H3> Value</H3> </TD> </TR>");

    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Trend Trader Minimal " + InitCashTitle +"</TD> <TD>" + mConfData.mNoiseTraderInitCash_Min +"</TD> </TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Trend Trader Maximal " + InitCashTitle +"</TD> <TD>" + mConfData.mNoiseTraderInitCash_Max +"</TD> </TR>");

    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Trend Trader Minimal " + InitAktienTitle + "</TD> <TD>" +  mConfData.mNoiseTraderInitAktien_Min+"</TD> </TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Trend Trader Maximal " + InitAktienTitle + "</TD> <TD>" +  mConfData.mNoiseTraderInitAktien_Max+"</TD> </TR>");

    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Trend Trader SchlafProzent</TD> <TD>"  +  mConfData.mNoiseTrader_SleepProcent+"</TD> </TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Trend Trader affected by Investor </TD> <TD>"  +  mConfData.mNoiseTraderAffectedByOtherNode+"</TD> </TR>");

    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>MinimalMovingDaysForAveragePrice </TD> <TD>" +mConfData.mNoiseTrader_MinMovingDaysForAveragePrice +"</TD> </TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>MaximalMovingDaysForAveragePrice </TD> <TD>" +mConfData.mNoiseTrader_MaxMovingDaysForAveragePrice+"</TD> </TR>");

    HTMLCreator.putHTMLContent(Paramfile,"<TR> <TD> <h4>Trend Trader Stufen Regel </h4> </TD> </TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Stufen </TD> <TD>Kursänderung </TD> <TD>Ordermenge </TD> <TD>Billigestkauf </TD> <TD>Bestverkauf </TD>  </TR>");

    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Stufe1</TD> <TD>" + "-Infinitive ~~"+ mConfData.mNoiseTraderKurschangedprocentlimit1
                                           +"%</TD><TD>" + mConfData.mNoiseTraderOrderMengeStufe1
                                           +"</TD><TD>" + getMarketOrderDefinition ( mConfData.mNoiseTraderStufe1MarketOrderBilligestKauf )
                                           +"</TD> <TD>" + getMarketOrderDefinition ( mConfData.mNoiseTraderStufe1MarketOrderBestVerkauf )+ "</TD>  </TR>");

    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Stufe2</TD> <TD>" + mConfData.mNoiseTraderKurschangedprocentlimit2
                                           +"%</TD> <TD>" + mConfData.mNoiseTraderOrderMengeStufe2
                                           +"</TD> <TD>" + getMarketOrderDefinition( mConfData.mNoiseTraderStufe2MarketOrderBilligestKauf )
                                           +"</TD> <TD>" + getMarketOrderDefinition ( mConfData.mNoiseTraderStufe2MarketOrderBestVerkauf )+ "</TD>  </TR>");

    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Stufe3</TD> <TD>" + mConfData.mNoiseTraderKurschangedprocentlimit3
                                           +"%</TD> <TD>" + mConfData.mNoiseTraderOrderMengeStufe3
                                           +"</TD>  <TD>" + getMarketOrderDefinition (mConfData.mNoiseTraderStufe3MarketOrderBilligestKauf )
                                           +"</TD>  <TD>" + getMarketOrderDefinition (mConfData.mNoiseTraderStufe3MarketOrderBestVerkauf ) + "</TD>  </TR>");

    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Stufe4</TD> <TD>" + mConfData.mNoiseTraderKurschangedprocentlimit3+"~~Infinitive"
                                           +"%</TD> <TD>" + mConfData.mNoiseTraderOrderMengeStufe4
                                           +"</TD> <TD>" + getMarketOrderDefinition (mConfData.mNoiseTraderStufe4MarketOrderBilligestKauf )
                                           +"</TD> <TD>" + getMarketOrderDefinition (mConfData.mNoiseTraderStufe4MarketOrderBestVerkauf ) + "</TD>  </TR>");

    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD><H2>Configuration of Retail Agent </H2></TD></TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD><H3>Parameter Name</H3</TD> <TD><H3> Value</H3> </TD> </TR>");

    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Retail Agent Minimal " + InitCashTitle +"</TD> <TD>" + mConfData.mBlankoAgentInitCash_Min +"</TD> </TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Retail Agent Maximal " + InitCashTitle +"</TD> <TD>" + mConfData.mBlankoAgentInitCash_Max +"</TD> </TR>");

    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Retail Agent Minimal " + InitAktienTitle + "</TD> <TD>" +  mConfData.mBlankoAgentInitAktien_Min+"</TD> </TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Retail Agent Maximal " + InitAktienTitle + "</TD> <TD>" +  mConfData.mBlankoAgentInitAktien_Max+"</TD> </TR>");

    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Retail Agent Kurs-change-Procent for activation Min" + "</TD> <TD>" +  mConfData.mBlankoAgentPlusIndexProcentForActivation_Min+"</TD> </TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Retail Agent Kurs-change-Procent for activation Max" + "</TD> <TD>" +  mConfData.mBlankoAgentPlusIndexProcentForActivation_Max+"</TD> </TR>");

    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Retail Agent Kurs-Change-Procent for deactivation Min" + "</TD> <TD>" +  mConfData.mBlankoAgentMinusIndexProcentForDeactivation_Min+"</TD> </TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Retail Agent Kurs-Change-Procent for deactivation Max" + "</TD> <TD>" +  mConfData.mBlankoAgentMinusIndexProcentForDeactivation_Max+"</TD> </TR>");

    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Retail Agent Index Window  Minimal days " +  "</TD> <TD>" +  mConfData.mBlankoAgentDayOfIndexWindow_Min+"</TD> </TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Retail Agent Index Window  Maximal days " +  "</TD> <TD>" +  mConfData.mBlankoAgentDayOfIndexWindow_Max+"</TD> </TR>");

    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Retail Agent Minimal sleep days after deactivation " +  "</TD> <TD>" +  mConfData.mBlankoAgentInactiveDays_Min+"</TD> </TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Retail Agent Maximal sleep days after deactivation " +  "</TD> <TD>" +  mConfData.mBlankoAgentInactiveDays_Max+"</TD> </TR>");

    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Retail Agent append cash is allowed? " +  "</TD> <TD>" +  mConfData.mBlankoAgent_CashAppendAllowed+"</TD> </TR>");

    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Retail Agent Total Sell in  " +  "</TD> <TD>" +  mConfData.mBlankoAgent_DaysOfTotalSell+" Days </TD> </TR>");

/*
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>NoiseTraderOrderMengeStufe1 </TD> <TD>" + mConfData.mNoiseTraderOrderMengeStufe1+"</TD> </TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>NoiseTraderOrderMengeStufe2 </TD> <TD>" + mConfData.mNoiseTraderOrderMengeStufe2+"</TD> </TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>NoiseTraderOrderMengeStufe3 </TD> <TD>" + mConfData.mNoiseTraderOrderMengeStufe3+"</TD> </TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>NoiseTraderOrderMengeStufe4 </TD> <TD>" + mConfData.mNoiseTraderOrderMengeStufe4+"</TD> </TR>");

    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD> Kurs Änderung Prozent Grenz 1</TD> <TD>" + mConfData.mNoiseTraderKurschangedprocentlimit1 +"%</TD> </TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD> Kurs Änderung Prozent Grenz 2</TD> <TD>" + mConfData.mNoiseTraderKurschangedprocentlimit2 +"%</TD> </TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD> Kurs Änderung Prozent Grenz 3</TD> <TD>" + mConfData.mNoiseTraderKurschangedprocentlimit3 +"%</TD> </TR>");

*/
    //HTMLCreator.putHTMLContent(Paramfile,"</Table>");

    //HTMLCreator.putHTMLLine(Paramfile,"<H2>");
    //HTMLCreator.putHTMLLine(Paramfile,trennline);
    //HTMLCreator.putHTMLLine(Paramfile,"Configuration von RandomTrader");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD><H2>Configuration von RandomTrader</H2></TD></TR>");

    //HTMLCreator.putHTMLLine(Paramfile,trennline);
    //HTMLCreator.putHTMLLine(Paramfile,"<H3>");

    //HTMLCreator.putHTMLContent(Paramfile,"<Table border='1' >");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD><H3>Parameter Name</H3</TD><TD><H3> Value</H3> </TD> </TR>");

    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>RandomTrader " + InitCashTitle + "</TD><TD>" + mConfData.mRandomTraderInitCash +"</TD> </TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>RandomTrader " + InitAktienTitle + "</TD><TD>" + mConfData.mRandomTraderInitAktien +"</TD> </TR>" ) ;
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Kauf-Wahrscheinlichkeit</TD><TD>"+ mConfData.mRandomTraderBuyProbability+ "%"+"</TD> </TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>        Cheapest-Kauf als Limit in Prozent</TD><TD>"+ mConfData.mRandomTraderBuyProbabilityCheapest+ "%"+"</TD> </TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>        Kurs-basierter Limit in Prozent</TD><TD>"+ mConfData.mRandomTraderBuyProbabilityIndexBased+ "%"+"</TD> </TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Verkauf-Wahrscheinlichkeit</TD><TD>"+ mConfData.mRandomTraderSellProbability+ "%"+"</TD> </TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>        Best-Verkauf als Limit in Prozent</TD><TD>"+ mConfData.mRandomTraderSellProbabilityBest+ "%"+"</TD> </TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>        Kurs-basierter Limit in Prozent</TD><TD>"+ mConfData.mRandomTraderSellProbabilityIndexbased+ "%"+"</TD> </TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Schlaf Prozent</TD><TD>"+ mConfData.mRandomTraderWaitProbability+ "%"+"</TD> </TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>MinimalOrdermengeofRandomTrader </TD><TD>" +  mConfData.mRandomTraderMinMenge+"</TD></TR>");
    HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>MaximalOrdermengeofRandomTrader </TD><TD>" +  mConfData.mRandomTraderMaxMenge+"</TD></TR>");
    //HTMLCreator.putHTMLContent(Paramfile,"</Table>");

    if ( printTobintax )
    {
        //HTMLCreator.putHTMLLine(Paramfile,"<H2>");
        //HTMLCreator.putHTMLLine(Paramfile,trennline);
        //HTMLCreator.putHTMLLine(Paramfile,"Configuration von Tobintax Agent");
        HTMLCreator.putHTMLContent(Paramfile,"<TR><TD><H2>Configuration von Tobintax Agent</H2></TD></TR>");
        //HTMLCreator.putHTMLLine(Paramfile,trennline);

        //HTMLCreator.putHTMLContent(Paramfile,"<Table border='1' >");
        HTMLCreator.putHTMLContent(Paramfile,"<TR><TD><H3>Parameter Name</H3</TD><TD><H3> Value</H3> </TD> </TR>");

        HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Feste Steuer</TD><TD>" + mConfData.mTobintax_FestSteuer + "%"+"</TD></TR>");
        HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>ExtraSteuer</TD><TD>" + mConfData.mTobintax_ExtraSteuer + "%"+"</TD></TR>");
        HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Interventionsband</TD><TD>" + mConfData.mTobintax_Interventionsband + "%"+"</TD></TR>");
        HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>AverageKurs basiert auf letzten </TD><TD>" + mConfData.mTobintax_Days4AverageKurs + "Days"+"</TD></TR>");
        HTMLCreator.putHTMLContent(Paramfile,"<TR><TD>Prozent von Depot wenn TobintaxAgent Order erstellt</TD><TD>" + mConfData.mTobintax_TradeProzent + "%"+"</TD></TR>");
        //HTMLCreator.putHTMLLine(Paramfile,"<H3>");
    }

    HTMLCreator.putHTMLContent(Paramfile,"</Table>");

    HTMLCreator.putHTMLLine(Paramfile,trennline);
    HTMLCreator.putHTMLContent(Paramfile,"<BR><BR>");

    HTMLCreator.putHTMLLine(Paramfile,"Agent Type Information");

    HTMLCreator.putHTMLLine(Paramfile,"<H5>");
    for (int i=0; i<mConfData.mDistributionList.length; i++)
    {
      HTMLCreator.putHTMLLine(Paramfile, mConfData.mDistributionList[i]);
    }

    HTMLCreator.putHTMLLine(Paramfile,trennline);

    HTMLCreator.putHTMLLine(Paramfile,"<H3>");
    HTMLCreator.putHTMLLine(Paramfile,"Die taeglich Status der Agent sind in der Datei unter RUN-x/graphstatehistory.txt gespeichert. ");
    HTMLCreator.putHTMLLine(Paramfile,"Bitte Graph-Review tool mit Kommand startreview.bat starten und die obige Datei oeffnen. ");

    HTMLCreator.putHTMLLine(Paramfile,trennline);

    HTMLCreator.putHTMLLine(Paramfile,"<H2>Kurs-Entwicklung");
    HTMLCreator.putHTMLLine(Paramfile,"<image src=" + "'" + mConfData.mChartName +" '>");

    HTMLCreator.putHTMLLine(Paramfile,"<H2>Links zu den Detailsdaten -----");
    HTMLCreator.putHTMLLine(Paramfile,"<H3>");

    /////////////////////////////////////////////////////////////////////
    HTMLCreator.putFileLink(Paramfile, "Statistik Kurtosis", mConfData.mPriceStatisticLogFile );
    HTMLCreator.putFileLink(Paramfile, "LogRenditen", "logrenditen.csv" );
    HTMLCreator.putFileLink(Paramfile, "LogRenditen-Sorted", "logrenditen-sorted.csv" );
    HTMLCreator.putFileLink(Paramfile, "BasisDaten von Korrelation",  mConfData.mKorrelationLogFilename );
    HTMLCreator.putFileLink(Paramfile, "BasisDaten von AutoKorrelation",  mConfData.mAutoKorrelationLogFilename);
    HTMLCreator.putFileLink(Paramfile, "Hill ", mConfData.mHillEstimatorLogFile  );
    HTMLCreator.putFileLink(Paramfile, "300-Anfangswerten von Innererwert", mConfData.mInnererwert300LogFilename );

    HTMLCreator.putFileLink(Paramfile, "Price history and statistic of daily order book ", mConfData.mTradeDailyStatisticFilename );

    HTMLCreator.putFileLink(Paramfile, "Daily Orderbook", mConfData.mDailyOrderBookFile );


    HTMLCreator.putFileLink(Paramfile, "Agent-Type-Aenderung-Ermittlung",  mConfData.mAgentNumberLogFile );

    if ( printTobintax )
    {
       HTMLCreator.putFileLink(Paramfile, "Taeglich Tobintax-Berechnungsdetails",  "tobintaxcalculationdetailed.csv" );
       HTMLCreator.putFileLink(Paramfile, "TobintaxAgent-Depot",  "depot/tobintax.csv" );
    }

    HTMLCreator.putFileLink(Paramfile, "DepotList of single agents", "depothistory.html" );
    HTMLCreator.putFileLink(Paramfile, "ExchangeHistory aller Agenten", "exchangehistory.html" );

    HTMLCreator.putFileLink(Paramfile, "depot group overview and comparation (HTML Format)",  mConfData.mDepotListSortedByGroup_HTMLFormat );
    HTMLCreator.putFileLink(Paramfile, "depot overview of all agents (text format)",          mConfData.mDepotListFile_TextFormat );

    HTMLCreator.putHtmlBodyEnd( Paramfile );
    HTMLCreator.putHtmlEnd( Paramfile );

    Paramfile.close();
    mConfData.mSummaryCreated = true;

  }
  catch (Exception ex)
  {
      System.out.println(ex.getMessage());
  }
  createDailyOrderBookOverviewHtml();
  createDepotHistoyHtml();
  createExchangeHistoyHtml();
}
/////////////////////////////////////////////////////////////////////////////////////

private static String getMarketOrderDefinition ( char pDef )
{
   if  ( ( pDef == 'B' ) || ( pDef == 'b' ) )
   {
     return "B";
   }
   else
   {
     return "-";
   }
}

private static void createDailyOrderBookOverviewHtml()
{
  try
  {
    String  DailyBookfilename = LogFileNameMapper( "DailyOrderBook.html");
    java.io.PrintStream  htmlfile= new java.io.PrintStream( new java.io.FileOutputStream( DailyBookfilename));

    HTMLCreator.putHtmlBodyBegin( htmlfile  );
    HTMLCreator.putHtmlHead( htmlfile  );

    for ( int i=0; i< mConfData.mHandelsday;i++)
    {

       HTMLCreator.putFileLinkwithNewWindow( htmlfile, "HandelsDay_"+(i+1),"orderbook/"+(i+1)+".html", "newwindow"+i);
    }
    HTMLCreator.putHtmlBodyEnd( htmlfile  );
    HTMLCreator.putHtmlEnd( htmlfile  );
    htmlfile.close();
 }
  catch (Exception ex)
  {
      ex.printStackTrace();
  }

}

///////////////////////////////////////////////////////////////////////////////////////////


private static void createDepotHistoyHtml()
{
  try
  {
    String depothtmlfilename = LogFileNameMapper( "depothistory.html");
    java.io.PrintStream  htmlfile= new java.io.PrintStream( new java.io.FileOutputStream( depothtmlfilename));

    HTMLCreator.putHtmlBodyBegin( htmlfile  );
    HTMLCreator.putHtmlHead( htmlfile  );

    for ( int i=0; i< mConfData.mDepotFileList.size();i++)
    {
        String ss = (String) mConfData.mDepotFileList.elementAt(i);
        HTMLCreator.putFileLinkwithNewWindow( htmlfile, ss, ss , "newwindow"+i );
    }
    HTMLCreator.putHtmlBodyEnd( htmlfile  );
    HTMLCreator.putHtmlEnd( htmlfile  );
    htmlfile.close();
 }
  catch (Exception ex)
  {
      ex.printStackTrace();
  }

}

private static void createExchangeHistoyHtml()
{
  try
  {
    String htmlfilename = LogFileNameMapper( "exchangehistory.html");
    java.io.PrintStream  htmlfile= new java.io.PrintStream( new java.io.FileOutputStream( htmlfilename));
    HTMLCreator.putHtmlBodyBegin( htmlfile  );
    HTMLCreator.putHtmlHead( htmlfile  );
    String windowname ="";
    for ( int i=0; i< mConfData.mExchangeHistoryFileList.size();i++)
    {
        String ss = (String) mConfData.mExchangeHistoryFileList.elementAt(i);
        HTMLCreator.putFileLinkwithNewWindow( htmlfile, ss, ss , "newwindow"+i );
    }
    HTMLCreator.putHtmlBodyEnd( htmlfile  );
    HTMLCreator.putHtmlEnd( htmlfile  );
    htmlfile.close();
 }
  catch (Exception ex)
  {
      ex.printStackTrace();
  }
}

public static void PrintAllParameter()
{
   String ss[] = mConfData.getAllParameterPairDetailedCommenta();
   for ( int i=0; i<ss.length; i++)
   {
      System.out.println(ss[i]);
   }
}

public static void waitforcommand()
{
   mConfData.mSynchronPoint.dowait();
}

public static void waitforcommand(int pWaitTime)
{
   mConfData.mSynchronPoint.dowait(pWaitTime);
}

public static void letsgo()
{
   mConfData.mSynchronPoint.letsgo();
}

public static String getResultComparationOfNetworksHtmlFile()
{
  return "./" +
             Configurator.mConfData.mLogTopDirectory+
             Configurator.mConfData.mPfadSeperator +
             Configurator.mConfData. mResultComparationOfNetworks;
}

/*
public static void createResultComparationOfNetworks()
{
  String comparation_result_htmlfile = Configurator.mConfData.mLogTopDirectory+
              Configurator.mConfData.mPfadSeperator +
              Configurator.mConfData. mResultComparationOfNetworks;

  try
  {
    java.io.PrintStream  htmlfile= new java.io.PrintStream( new java.io.FileOutputStream( comparation_result_htmlfile ));

    putHtmlBodyBegin( htmlfile  );
    putHtmlHead( htmlfile  );

    /*
    <TABLE>
      <TR>
        <TH>Abbreviation</TH>
        <TH>Long Form</TH>
      </TR>
      <TR>
        <TD>AFAIK</TD>
        <TD>As Far As I Know</TD>
      </TR>
      <TR>
        <TD>IMHO</TD>
        <TD>In My Humble Opinion</TD>
      </TR>
      <TR>
        <TD>OTOH</TD>
        <TD>On The Other Hand</TD>
      </TR>
   </TABLE>
    */

    /*
    putHTMLLine(htmlfile,"<H2>Comparation of Simulations with different networks </H2><H4>");

    // Print Titel Line"
    putHTMLLine(htmlfile,
                "<TABLE Border=2 ><TR>"+
                "<TH>Network</TH>"+
                "<TH>Nodes</TH>"+
                "<TH>Complexity</TH>"+
                "<TH>Investor</TH>"+
                "<TH>NoiseTrader</TH>"+
                "<TH>RandomTrader</TH>"+
                "<TH>Kurtosis - 1 Day-Interval  </TH>"+
                "<TH>Kurtosis - 5 Day-Interval  </TH>"+
                "<TH>Kurtosis - 10 Day-Interval </TH>"+
                "<TH>Kurtosis - 25 Day-Interval </TD>"+
                "<TH>Kurtosis - 50 Day-Interval </TH>"+
                "<TH>Details</TH>"+
                "<TR>");

    for ( int i=0; i<mNetworkConfigList.size();i++)
    {
        NetworkConfig onenetwork = (NetworkConfig) mNetworkConfigList.elementAt(i);

        htmlfile.println(
                    "<TR>"+
                    "<TD>" + onenetwork.mNetworkfilenameOhnePfad+"</TD>"+
                    "<TD>" + onenetwork.mNodesNumber+"</TD>"+
                    "<TD>" + "0" +"</TD>"+
                    "<TD>"+  onenetwork.mInvestorNumber+"</TD>"+
                    "<TD>" + onenetwork.mNoiseTraderNumber+"</TD>"+
                    "<TD>"  + onenetwork.mRandomTraderNumber+"</TD>"+
                    "<TD>"  + HelpTool.DoubleTransfer( onenetwork.mSimulationReportData.mData1DayAbstand.Kurtosis  ,4)  + "</TD>"+
                    "<TD>"  + HelpTool.DoubleTransfer( onenetwork.mSimulationReportData.mData5DayAbstand.Kurtosis  ,4)  + "</TD>"+
                    "<TD>"  + HelpTool.DoubleTransfer( onenetwork.mSimulationReportData.mData10DayAbstand.Kurtosis ,4)  + "</TD>"+
                    "<TD>"  + HelpTool.DoubleTransfer( onenetwork.mSimulationReportData.mData25DayAbstand.Kurtosis ,4)  + "</TD>"+
                    "<TD>"  + HelpTool.DoubleTransfer( onenetwork.mSimulationReportData.mData50DayAbstand.Kurtosis, 4)  + "</TD>"+
                    "<TD><a href='" + onenetwork.mNetworkfilenameOhnePfad +
                                  Configurator.mConfData.mPfadSeperator +
                                  "profile.html"+
                                  "'><H3>Details</H3></a></TD>"+
                    "<TR>");
    }
    putHTMLLine(htmlfile,"</Table>");

    putHtmlBodyEnd( htmlfile  );
    putHtmlEnd( htmlfile  );
    htmlfile.close();
 }
  catch (Exception ex)
  {
      ex.printStackTrace();
  }
}
*/

public static void saveConfig2XmlFile(String pDirName, String pXmlFile) throws Exception
{
      java.io.PrintWriter pp = new PrintWriter( new FileOutputStream(pDirName + pXmlFile) );
      pp.println( "<SimulationConfig>"  );
      pp.println( "<BaseConfig>"  );
      String ss[] = mConfData.getXmlOutput();
      for ( int i=0; i<ss.length; i++)
      {
        pp.println(ss[i]);
      }
      pp.println( "</BaseConfig>"  );
      pp.println( ""  );

      for ( int i=0; i< mNetworkConfigManager.getSize(); i++)
      {
        NetworkConfig onenetworkconf = mNetworkConfigManager.getNetworkConfig(i);
        ss = onenetworkconf.getXmlOutput();
        for ( int j=0; j<ss.length; j++)
        {
          pp.println(ss[j]);
        }
        pp.println( ""  );
      }
      pp.println( "</SimulationConfig>"  );
      pp.close();
}

public static void saveConfig2TextFile(String pDirName, String pFilename) throws Exception
{
     // get all Parameter from Configurator Object
     String tt[] = getAllParameterPair( true ) ;
     // save all parameters to a file
     java.io.FileWriter  fw = new java.io.FileWriter( pDirName + pFilename );
     for (int i=0; i < tt.length; i++)
     {
        fw.write( tt[i] + "\r\n" );
     }
     fw.close();
}


public static void main(String args[] )
{
    //saveparameter();
    String tt[] = getAllParameterPair(true);

    for (int i=0; i<tt.length; i++)
    {
       System.out.println(tt[i]);
    }
}

}