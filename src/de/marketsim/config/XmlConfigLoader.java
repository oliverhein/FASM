package de.marketsim.config;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import java.io.*;
import java.util.Vector;
import java.util.Hashtable;

import org.w3c.dom.*;
import javax.xml.parsers.*;

import de.marketsim.config.ConfData;
import de.marketsim.config.Configurator;
import de.marketsim.config.ParameterNameConst;
import de.marketsim.config.XmlNodeProperty;
import de.marketsim.agent.stockstore.stockdata.InnererwertRandomWalkGenerator;

import de.marketsim.SystemConstant;
import de.marketsim.util.FileChecker;


public class XmlConfigLoader
{

public XmlConfigLoader()
{

}

public void doload(String pXmlConfigFile)
{
      DocumentBuilderFactory docBFac;
      DocumentBuilder docBuild;
      Document doc = null;

      try
      {
         docBFac = DocumentBuilderFactory.newInstance();
         docBuild = docBFac.newDocumentBuilder();
         doc = docBuild.parse( pXmlConfigFile );

         XmlNodeProperty rootelement  = new XmlNodeProperty(doc.getDocumentElement());

         XmlNodeProperty baseconfig   = new XmlNodeProperty( rootelement.getStructure("BaseConfig") );

         // Step 1:
         // Load Basic Config
         this.loadXmlProperty2ConfData( Configurator.mConfData,  baseconfig  );
         Configurator.mConfData.mConfigFile =pXmlConfigFile;

         // Step 2:
         // load Network confgurations
         Node networklist[] = rootelement.getStructureList("Network");

         System.out.println( networklist.length + " networks are found.");

         // remove all old configuration
         Configurator.mNetworkConfigManager.reset();

         int nodes_in_differentnetworkfile[] = new int[networklist.length];

         for ( int i=0; i<networklist.length; i++)
         {
             System.out.println( "Checking " + ( i+1 ) + ". network file ");

             XmlNodeProperty  onexmlnode    = new XmlNodeProperty( networklist[i] );

             NetworkConfig    oneconfig = this.loadXmlNetworkConfig(onexmlnode);

             nodes_in_differentnetworkfile[ i ] = oneconfig.mNodesInNetwork;
             System.out.println( "Networkfile:" + oneconfig.mNetworkFileName + " ; Nodes in Network:" + oneconfig.mNodesInNetwork );

             Configurator.mNetworkConfigManager.addNewNetwork( oneconfig );
         }

      }
      catch (Exception ex)
      {
          ex.printStackTrace();
      }
}

public void loadXmlProperty2ConfData(ConfData mConfData, XmlNodeProperty pXmlNodeProperty )
{

        mConfData.mTradeDailyStatisticFilename  = pXmlNodeProperty.getProperty("Pricelog", "tradedailystatistic.csv");
        mConfData.mDepotLogFilename              = pXmlNodeProperty.getProperty("Depotlog", "depot.csv");

        String ss = null;

        ////////////////////////////////////////////////////////////////////
        //                                                                //
        //  Initialize the General Parameter Group                        //
        //                                                                //
        ////////////////////////////////////////////////////////////////////

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.Handelsday, "300");
        ss = ss.trim();
        if ( ss.length()== 0 )
        {
             mConfData.mHandelsday = 300;
        }
        else
        {
             mConfData.mHandelsday = Integer.parseInt(ss);
        }
        ////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////
        // in Procent
        ss = pXmlNodeProperty.getProperty( ParameterNameConst.MaximalTagAbweichnung,"12" ) ;
        if ( ss.trim().length() == 0 )
        {
          ss="12";
        }
        mConfData.mInnererwertMaximalTagAbweichnung = Double.parseDouble( ss );

        ////////////////////////////////////////////////////////////////

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.InnerWert_Muster ) ;
        if ( ss == null )
        {
          mConfData.mInnererWertMuster = "";
        }
        else
        {
          mConfData.mInnererWertMuster = ss;
        }

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.InnerWert_Max, "3000" ) ;
        if ( ss.trim().length() == 0 )
        {
          ss="3000";
        }
        mConfData.mMaxInnererWert = Integer.parseInt( ss );

        ////////////////////////////////////////////////////////////////
        ss = pXmlNodeProperty.getProperty( ParameterNameConst.InnerWert_Min,"500" ) ;
        if ( ss.trim().length() == 0 )
        {
          ss="500";
        }
        mConfData.mMinInnererWert = Integer.parseInt( ss );

        ////////////////////////////////////////////////////////////////
        ss = pXmlNodeProperty.getProperty( ParameterNameConst.InnerWert_Begin,"1000" ) ;
        if ( ss.trim().length() == 0 )
        {
          ss="1000";
        }
        mConfData.mBeginInnererWert = Integer.parseInt( ss );
        ////////////////////////////////////////////////////////////////
        ss = pXmlNodeProperty.getProperty( ParameterNameConst.GewinnstatusaustauschInterval,"3" ) ;
        if ( ss.trim().length() == 0 )
        {
          ss="3";
        }
        mConfData.mGewinnStatusExchangeProbability = Integer.parseInt( ss );

        ////////////////////////////////////////////////////////////////
        ss = pXmlNodeProperty.getProperty( ParameterNameConst.DaysOfOnePeriod,"1500" ) ;
        if ( ss.trim().length() == 0 )
        {
          ss = "1500";
        }
        mConfData.mDaysOfOnePeriod = Integer.parseInt( ss );

        ////////////////////////////////////////////////////////////////
        ss = pXmlNodeProperty.getProperty( ParameterNameConst.AheadDaysForGewinnCalculation,"50" ) ;
        if ( ss.trim().length() == 0 )
        {
          ss = "50";
        }
        mConfData.mAheadDaysForGewinnCalculation = Integer.parseInt( ss );
        ////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////
        ss = pXmlNodeProperty.getProperty( ParameterNameConst.AllowedAbweichungPreis2InnererWert,"50" ) ;
        if ( ss.trim().length() == 0 )
        {
          ss = "50";
        }
        mConfData.mAllowerAbweichungPreis2InnererWert = Integer.parseInt(ss);

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.HillEstimatorProcent,"5" ) ;
        if ( ss.trim().length() == 0 )
        {
          ss = "5";
        }
        mConfData.mHillEstimatorProcent = Double.parseDouble(ss);


        ss = pXmlNodeProperty.getProperty( ParameterNameConst.AutoCorrelation,"50" ) ;
        if ( ss.trim().length() == 0 )
        {
          ss = "50";
        }
        mConfData.mAnzahlAutoKorrelation = Integer.parseInt( ss );

        ////////////////////////////////////////////////////////////////
        mConfData.mHillEstimatorLogFile = pXmlNodeProperty.getProperty( ParameterNameConst.HillEstimatorLogFile,"hill.csv" ) ;

        String s1 = null;

        s1 = pXmlNodeProperty.getProperty( ParameterNameConst.MarketMode );
        if ( s1 == null )
        {
            mConfData.mMarketMode = mConfData.mAktienMarket;
        }
        else
        {
            mConfData.mMarketMode = Integer.parseInt(s1);
        }

        s1= pXmlNodeProperty.getProperty( ParameterNameConst.TobintaxAgentActive);
        if ( s1== null)
        {
          mConfData.mTobintaxAgentAktive = true;
        }
        else
        if (s1.equalsIgnoreCase("true"))
        {
          mConfData.mTobintaxAgentAktive = true;
        }
        else
        {
          mConfData.mTobintaxAgentAktive = false;
        }

        // Default Language: German
        s1= pXmlNodeProperty.getProperty( ParameterNameConst.DataFormatLanguage,  SystemConstant.DataFormatLanguage_German );
        if ( s1.equalsIgnoreCase( SystemConstant.DataFormatLanguage_German ))
        {
          mConfData.mDataFormatLanguage = SystemConstant.DataFormatLanguage_German;
        }
        else
        if ( s1.equalsIgnoreCase( SystemConstant.DataFormatLanguage_English ))
        {
            mConfData.mDataFormatLanguage = SystemConstant.DataFormatLanguage_English;
        }
        else
        {
           mConfData.mDataFormatLanguage = SystemConstant.DataFormatLanguage_English;
        }

        // 2006-05-26
        // InnererWert Vorbereitungsvorgang

        // check if mInnererWertMuster=""
        if ( ! mConfData.mInnererWertMuster.equals("") )
        {
          // laden InnererWert von Datei
          try
          {
             InnererWertModelFileReader  rd =  new InnererWertModelFileReader (mConfData.mInnererWertMuster);
             mConfData.mInit300     = rd.getInit300IntValue();
             mConfData.mInnererWert = rd.getInnererWertIntValue();
             mConfData.mInnererWertRealDataCountFromModelFile = mConfData.mInnererWert.length;

             if ( mConfData.mHandelsday > mConfData.mInnererWert.length )
             {
                mConfData.mHandelsday = mConfData.mInnererWert.length;
             }

          }
          catch (Exception ex)
          {
             ex.printStackTrace();
             this.createInnererWert();
          }
        }
        else
        {
          this.createInnererWert();
        }

        ////////////////////////////////////////////////////////////////
        // Initialize the parameter of agent communication            //
        ////////////////////////////////////////////////////////////////

        ss =pXmlNodeProperty.getProperty( ParameterNameConst.StatusExchangeProbabiliy,"10" ) ;
        mConfData.mGewinnStatusExchangeProbability = Integer.parseInt(ss);

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.MaxLostNumberMode,"fixed" ) ; // default mode: Fixed
        mConfData.mMaxLostNumberMode = ss;

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.FixedMaxLostNumber,"3" ) ; // default FixedNUmber: 3
        mConfData.mFixedMaxLostNumber = Integer.parseInt(ss);

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.MaxLostNumberSeed,"5" ) ;
        mConfData.mMaxLostNumberSeed = Integer.parseInt(ss);

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.BaseDeviation4PriceLimit,"10" ) ;
        mConfData.mBaseDeviation4PriceLimit = Integer.parseInt(ss);

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.AbschlagFactor,"10" ) ;
        mConfData.mAbschlagFactor = Integer.parseInt(ss);

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.Orders4AverageLimit,"10" ) ;
        mConfData.mOrders4AverageLimit = Integer.parseInt(ss);

        ////////////////////////////////////////////////////////////////
        //                                                            //
        // Initialize the Investor Parameter                          //
        //                                                            //
        ////////////////////////////////////////////////////////////////

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.Investor_InitCash_Min,"100000" ) ;
        mConfData.mInvestorInitCash_Min = Integer.parseInt(ss);

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.Investor_InitCash_Max,"100000" ) ;
        mConfData.mInvestorInitCash_Max = Integer.parseInt(ss);

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.Investor_InitStock_Min,"1000" ) ;
        mConfData.mInvestorInitAktien_Min = Integer.parseInt(ss);

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.Investor_InitStock_Max,"1000" ) ;
        mConfData.mInvestorInitAktien_Max = Integer.parseInt(ss);


        ss = pXmlNodeProperty.getProperty( ParameterNameConst.Investor_DynamischAbschlageProzent_Min, "0" );
        mConfData.mInvestor_DynamischAbschlageProzent_Min = Double.parseDouble(ss);

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.Investor_DynamischAbschlageProzent_Max, "20" );
        mConfData.mInvestor_DynamischAbschlageProzent_Max = Double.parseDouble(ss);

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.Investor_AbschlagGaussMean, "2.0" );
        mConfData.mInvestor_AbschlagGaussMean = Double.parseDouble(ss);

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.Investor_AbschlagGaussDeviation, "0.75" );
        mConfData.mInvestor_AbschlagGaussDeviation = Double.parseDouble(ss);

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.Investor_AnzahlProzent_LinkBereich, "50" );
        mConfData.mInvestor_AnzahlProzent_LinkBereich = Integer.parseInt(ss);

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.Investor_AnzahlProzent_MittBereich, "35" );
        mConfData.mInvestor_AnzahlProzent_MittBereich = Integer.parseInt(ss);

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.Investor_AnzahlProzent_RechtBereich, "15" );
        mConfData.mInvestor_AnzahlProzent_RechtBereich = Integer.parseInt(ss);

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.Investor_SleepProcent,"0" );
        mConfData.mInvestorSleepProcent = Integer.parseInt(ss);

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.Investor_InnererWertIntervall_Untergrenz,"-5.0" );
        mConfData.mInvestor_InnererWertIntervall_Untergrenz = Double.parseDouble(ss);

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.Investor_InnererWertIntervall_Obengrenz,"5.0" );
        mConfData.mInvestor_InnererWertIntervall_Obengrenz = Double.parseDouble(ss);

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.Investor_KursAnderung_Schwelle,"5.0" );
        mConfData.mInvestor_KursAnderung_Schwelle = Double.parseDouble(ss);

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.Investor_AktuellerInnererWert_Potenzial,"10.0" );
        mConfData.mInvestor_AktuellerInnererWert_Potenzial = Double.parseDouble(ss);

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.Investor_KursAnderung_ReferenzTag,"5" );
        mConfData.mInvestor_KursAnderung_ReferenzTag = Integer.parseInt(ss);

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.Investor_KurschangedprocentLimit1,"1.0" ) ;
        mConfData.mInvestorKurschangedprocentlimit1 =Double.parseDouble( ss );

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.Investor_KurschangedprocentLimit2,"2.0" ) ;
        mConfData.mInvestorKurschangedprocentlimit2 =Double.parseDouble( ss );

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.Investor_KurschangedprocentLimit3,"4.0" ) ;
        mConfData.mInvestorKurschangedprocentlimit3 =Double.parseDouble( ss );

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.Investor_Order_Stufe1,"1" ) ;
        mConfData.mInvestorOrderMengeStufe1 =Integer.parseInt( ss );

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.Investor_Order_Stufe2,"3" ) ;
        mConfData.mInvestorOrderMengeStufe2 =Integer.parseInt( ss );

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.Investor_Order_Stufe3,"6" ) ;
        mConfData.mInvestorOrderMengeStufe3 =Integer.parseInt( ss );

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.Investor_Order_Stufe4,"10" ) ;
        mConfData.mInvestorOrderMengeStufe4 =Integer.parseInt( ss );

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.Investor_Stufe1MarketOrderBilligestKauf," " ).toUpperCase() ;
        mConfData.mInvestorStufe1MarketOrderBilligestKauf = ss.charAt(0);
        ss = pXmlNodeProperty.getProperty( ParameterNameConst.Investor_Stufe1MarketOrderBestVerkauf," " ).toUpperCase() ;
        mConfData.mInvestorStufe1MarketOrderBestVerkauf = ss.charAt(0);

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.Investor_Stufe2MarketOrderBilligestKauf," " ).toUpperCase() ;
        mConfData.mInvestorStufe2MarketOrderBilligestKauf = ss.charAt(0);
        ss = pXmlNodeProperty.getProperty( ParameterNameConst.Investor_Stufe2MarketOrderBestVerkauf," " ).toUpperCase() ;
        mConfData.mInvestorStufe2MarketOrderBestVerkauf = ss.charAt(0);

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.Investor_Stufe3MarketOrderBilligestKauf," " ).toUpperCase() ;
        mConfData.mInvestorStufe3MarketOrderBilligestKauf = ss.charAt(0);
        ss = pXmlNodeProperty.getProperty( ParameterNameConst.Investor_Stufe3MarketOrderBestVerkauf," " ).toUpperCase() ;
        mConfData.mInvestorStufe3MarketOrderBestVerkauf = ss.charAt(0);

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.Investor_Stufe4MarketOrderBilligestKauf," " ).toUpperCase() ;
        mConfData.mInvestorStufe4MarketOrderBilligestKauf = ss.charAt(0);
        ss = pXmlNodeProperty.getProperty( ParameterNameConst.Investor_Stufe4MarketOrderBestVerkauf," " ).toUpperCase() ;
        mConfData.mInvestorStufe4MarketOrderBestVerkauf = ss.charAt(0);

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.InvestorAffectedByOtherNode, "true" ).toUpperCase() ;
        mConfData.mInvestorAffectedByOtherNode = Boolean.getBoolean( ss );

        ////////////////////////////////////////////////////////////////
        //                                                            //
        // Initialize the NoiseTrader Parameter                          //
        //                                                            //
        ////////////////////////////////////////////////////////////////

        ss =pXmlNodeProperty.getProperty( ParameterNameConst.NoiseTrader_InitCash_Min,"100000" ) ;
        mConfData.mNoiseTraderInitCash_Min =Integer.parseInt(ss);

        ss =pXmlNodeProperty.getProperty( ParameterNameConst.NoiseTrader_InitCash_Max,"100000" ) ;
        mConfData.mNoiseTraderInitCash_Max =Integer.parseInt(ss);

        ss =pXmlNodeProperty.getProperty( ParameterNameConst.NoiseTrader_InitStock_Min,"1000" ) ;
        mConfData.mNoiseTraderInitAktien_Min = Integer.parseInt(ss); ;

        ss =pXmlNodeProperty.getProperty( ParameterNameConst.NoiseTrader_InitStock_Max,"1000" ) ;
        mConfData.mNoiseTraderInitAktien_Max = Integer.parseInt(ss); ;

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.NoiseTrader_MinMovingDays4AveragePrice,"10" );
        mConfData.mNoiseTrader_MinMovingDaysForAveragePrice = Integer.parseInt(ss);
        ss = pXmlNodeProperty.getProperty( ParameterNameConst.NoiseTrader_MaxMovingDays4AveragePrice,"50" );
        mConfData.mNoiseTrader_MaxMovingDaysForAveragePrice = Integer.parseInt(ss);

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.NoiseTrader_MinLimitAdjust,"0" );
        mConfData.mNoiseTrader_MinLimitAdjust = Double.parseDouble(ss);
        ss = pXmlNodeProperty.getProperty( ParameterNameConst.NoiseTrader_MaxLimitAdjust,"3.0" );
        mConfData.mNoiseTrader_MaxLimitAdjust = Double.parseDouble(ss);

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.NoiseTrader_SleepProcent,"20" );
        mConfData.mNoiseTrader_SleepProcent = Integer.parseInt(ss);

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.NoiseTrader_KurschangedprocentLimit1,"1.0" ) ;
        mConfData.mNoiseTraderKurschangedprocentlimit1 = Double.parseDouble( ss );

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.NoiseTrader_KurschangedprocentLimit2,"2.0" ) ;
        mConfData.mNoiseTraderKurschangedprocentlimit2 = Double.parseDouble( ss );

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.NoiseTrader_KurschangedprocentLimit3,"3.0" ) ;
        mConfData.mNoiseTraderKurschangedprocentlimit3 = Double.parseDouble( ss );

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.NoiseTrader_Order_Stufe1,"1" ) ;
        mConfData.mNoiseTraderOrderMengeStufe1 =Integer.parseInt( ss );

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.NoiseTrader_Order_Stufe2,"3" ) ;
        mConfData.mNoiseTraderOrderMengeStufe2 =Integer.parseInt( ss );

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.NoiseTrader_Order_Stufe3,"6" ) ;
        mConfData.mNoiseTraderOrderMengeStufe3 =Integer.parseInt( ss );

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.NoiseTrader_Order_Stufe4,"10" ) ;
        mConfData.mNoiseTraderOrderMengeStufe4 =Integer.parseInt( ss );

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.NoiseTrader_Stufe1MarketOrderBilligestKauf," " ).toUpperCase() ;
        mConfData.mNoiseTraderStufe1MarketOrderBilligestKauf = ss.charAt(0);
        ss = pXmlNodeProperty.getProperty( ParameterNameConst.NoiseTrader_Stufe1MarketOrderBestVerkauf," " ).toUpperCase() ;
        mConfData.mNoiseTraderStufe1MarketOrderBestVerkauf = ss.charAt(0);


        ss = pXmlNodeProperty.getProperty( ParameterNameConst.NoiseTrader_Stufe2MarketOrderBilligestKauf," " ).toUpperCase() ;
        mConfData.mNoiseTraderStufe2MarketOrderBilligestKauf = ss.charAt(0);
        ss = pXmlNodeProperty.getProperty( ParameterNameConst.NoiseTrader_Stufe2MarketOrderBestVerkauf," " ).toUpperCase() ;
        mConfData.mNoiseTraderStufe2MarketOrderBestVerkauf = ss.charAt(0);

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.NoiseTrader_Stufe3MarketOrderBilligestKauf," " ).toUpperCase() ;
        mConfData.mNoiseTraderStufe3MarketOrderBilligestKauf = ss.charAt(0);
        ss = pXmlNodeProperty.getProperty( ParameterNameConst.NoiseTrader_Stufe3MarketOrderBestVerkauf," " ).toUpperCase() ;
        mConfData.mNoiseTraderStufe3MarketOrderBestVerkauf = ss.charAt(0);

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.NoiseTrader_Stufe4MarketOrderBilligestKauf," " ).toUpperCase() ;
        mConfData.mNoiseTraderStufe4MarketOrderBilligestKauf = ss.charAt(0);
        ss = pXmlNodeProperty.getProperty( ParameterNameConst.NoiseTrader_Stufe4MarketOrderBestVerkauf," " ).toUpperCase() ;
        mConfData.mNoiseTraderStufe4MarketOrderBestVerkauf = ss.charAt(0);

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.NoiseTraderAffectedByOtherNode, "true" ).toUpperCase() ;
        mConfData.mNoiseTraderAffectedByOtherNode = Boolean.getBoolean( ss );

        ////////////////////////////////////////////////////////////////
        //                                                            //
        // Initialize the RandomTrader Parameter                          //
        //                                                            //
        ////////////////////////////////////////////////////////////////

        ss =pXmlNodeProperty.getProperty( ParameterNameConst.RandomTrader_InitCash,"20000000" ) ;
        mConfData.mRandomTraderInitCash = Integer.parseInt(ss);
        ss =pXmlNodeProperty.getProperty( ParameterNameConst.RandomTrader_InitStock,"50000" ) ;
        mConfData.mRandomTraderInitAktien = Integer.parseInt(ss);

        ss =pXmlNodeProperty.getProperty( ParameterNameConst.RandomTraderBuyProbability,"40" ) ;
        mConfData.mRandomTraderBuyProbability =Integer.parseInt(ss);
        ss =pXmlNodeProperty.getProperty( ParameterNameConst.RandomTraderBuyProbabilityChepest,"50" ) ;
        mConfData.mRandomTraderBuyProbabilityCheapest=Integer.parseInt(ss);
        ss =pXmlNodeProperty.getProperty( ParameterNameConst.RandomTraderBuyProbabilityIndexBased,"50" ) ;
        mConfData.mRandomTraderBuyProbabilityIndexBased=Integer.parseInt(ss);

       ss =pXmlNodeProperty.getProperty( ParameterNameConst.RandomTraderSellProbability,"40" ) ;
       mConfData.mRandomTraderSellProbability=Integer.parseInt(ss);

       ss =pXmlNodeProperty.getProperty( ParameterNameConst.RandomTraderSellProbabilityIndexBased,"50" ) ;
       mConfData.mRandomTraderSellProbabilityIndexbased=Integer.parseInt(ss);

       ss =pXmlNodeProperty.getProperty( ParameterNameConst.RandomTraderSellProbabilityBest,"50" ) ;
       mConfData.mRandomTraderSellProbabilityBest=Integer.parseInt(ss);

       ss =pXmlNodeProperty.getProperty( ParameterNameConst.RandomTraderWaitProbability,"20" ) ;
       mConfData.mRandomTraderWaitProbability =Integer.parseInt(ss);

       ss =pXmlNodeProperty.getProperty( ParameterNameConst.RandomTraderMinMenge,"1" ) ;
       mConfData.mRandomTraderMinMenge =Integer.parseInt(ss);

       ss =pXmlNodeProperty.getProperty( ParameterNameConst.RandomTraderMaxMenge,"3" ) ;
       mConfData.mRandomTraderMaxMenge =Integer.parseInt(ss);

       ss =pXmlNodeProperty.getProperty( ParameterNameConst.RandomTraderRandomSeed4Decision,"10000" ) ;
       mConfData.mRandomTraderRandomSeed4Decision =Integer.parseInt(ss);

       ////////////////////////////////////////////////////////////////
       //                                                            //
       // Initialize Blanko Agent Parameter                          //
       //                                                            //
       ////////////////////////////////////////////////////////////////

       ss =pXmlNodeProperty.getProperty( ParameterNameConst.BlankoAgent_InitCash_Min,"100000" ) ;
       mConfData.mBlankoAgentInitCash_Min =Integer.parseInt(ss);

       ss =pXmlNodeProperty.getProperty( ParameterNameConst.BlankoAgent_InitCash_Max,"100000" ) ;
       mConfData.mBlankoAgentInitCash_Max =Integer.parseInt(ss);

       ss =pXmlNodeProperty.getProperty( ParameterNameConst.BlankoAgent_InitStock_Min,"1000" ) ;
       mConfData.mBlankoAgentInitAktien_Min = Integer.parseInt(ss); ;

       ss =pXmlNodeProperty.getProperty( ParameterNameConst.BlankoAgent_InitStock_Max,"1000" ) ;
       mConfData.mBlankoAgentInitAktien_Max = Integer.parseInt(ss); ;

       ss =pXmlNodeProperty.getProperty( ParameterNameConst.BlankoAgentDayOfIndexWindow_Min, "10" ) ;
       mConfData.mBlankoAgentDayOfIndexWindow_Min = Integer.parseInt(ss); ;

       ss =pXmlNodeProperty.getProperty( ParameterNameConst.BlankoAgentDayOfIndexWindow_Max, "10" ) ;
       mConfData.mBlankoAgentDayOfIndexWindow_Max = Integer.parseInt(ss); ;


       ss =pXmlNodeProperty.getProperty( ParameterNameConst.BlankoAgentMindestActiveDays, "5" ) ;
       mConfData.mBlankoAgentMindestActiveDays = Integer.parseInt(ss); ;

       ss =pXmlNodeProperty.getProperty( ParameterNameConst.BlankoAgentInactiveDays_Min, "10" ) ;
       mConfData.mBlankoAgentInactiveDays_Min = Integer.parseInt(ss); ;

       ss =pXmlNodeProperty.getProperty( ParameterNameConst.BlankoAgentInactiveDays_Max, "10" ) ;
       mConfData.mBlankoAgentInactiveDays_Max = Integer.parseInt(ss); ;

       ss =pXmlNodeProperty.getProperty( ParameterNameConst.BlankoAgentPlusIndexProcentForActivation_Min, "5.0" ) ;
       mConfData.mBlankoAgentPlusIndexProcentForActivation_Min = Double.parseDouble(ss); ;

       ss =pXmlNodeProperty.getProperty( ParameterNameConst.BlankoAgentPlusIndexProcentForActivation_Max, "5.0" ) ;
       mConfData.mBlankoAgentPlusIndexProcentForActivation_Max = Double.parseDouble(ss); ;

       ss =pXmlNodeProperty.getProperty( ParameterNameConst.BlankoAgentMinusIndexProcentForDeactivation_Min, "5.0" ) ;
       mConfData.mBlankoAgentMinusIndexProcentForDeactivation_Min = Double.parseDouble(ss); ;

       ss =pXmlNodeProperty.getProperty( ParameterNameConst.BlankoAgentMinusIndexProcentForDeactivation_Max, "5.0" ) ;
       mConfData.mBlankoAgentMinusIndexProcentForDeactivation_Max = Double.parseDouble(ss); ;

       ss =pXmlNodeProperty.getProperty( ParameterNameConst.BlankoAgent_SleepProcent, "0" ) ;
       mConfData.mBlankoAgent_SleepProcent = Integer.parseInt(ss);

       ss =pXmlNodeProperty.getProperty( ParameterNameConst.BlankoAgent_CashAppendAllowed, "true" ) ;

       //System.out.println("BlankAgent AppendCash=" + ss + "AAAA");

       mConfData.mBlankoAgent_CashAppendAllowed = Boolean.valueOf(ss).booleanValue();
       ss =pXmlNodeProperty.getProperty( ParameterNameConst.BlankoAgent_AppendCash, "100000" ) ;
       mConfData.mBlankoAgent_AppendCash = Integer.parseInt(ss);
       //System.out.println("BlankAgent AppendCash=  " + mConfData.mBlankoAgent_CashAppendAllowed );

       // Default: 10 Days
       ss =pXmlNodeProperty.getProperty( ParameterNameConst.BlankoAgent_DaysOfTotalSell, "10" ) ;
       mConfData.mBlankoAgent_DaysOfTotalSell = Integer.parseInt(ss);

       ////////////////////////////////////////////////////////////////
       //                                                            //
       // Initialize the Tobintax Agent Parameter                    //
       //                                                            //
       ////////////////////////////////////////////////////////////////

       // TobinTax FestSteuer  Default 1.0% wenn Parameter nicht definiert ist.
       ss =pXmlNodeProperty.getProperty( ParameterNameConst.TobintaxAgentFestSteuer, "15" ) ;
       mConfData.mTobintax_FestSteuer =Double.parseDouble(ss);

       // TobinTax ExtraSteuer  Default 2.0% wenn Parameter nicht definiert ist.
       ss =pXmlNodeProperty.getProperty( ParameterNameConst.TobintaxAgentExtraSteuer, "50" ) ;
       mConfData.mTobintax_ExtraSteuer =Double.parseDouble(ss);

       // TobinTaxAgent Tage für AverageKurs  Default 100 Tage wenn Parameter nicht definiert ist.
       ss =pXmlNodeProperty.getProperty( ParameterNameConst.TobintaxAgentTag4AverageKurs, "20" ) ;
       mConfData.mTobintax_Days4AverageKurs =Integer.parseInt(ss);

       // TobinTaxAgent TradeProzent von Depot: 20% von jeweils Cash1 oder Cash2 wenn Parameter nicht definiert ist.
       ss =pXmlNodeProperty.getProperty( ParameterNameConst.TobintaxAgentTradeProzent, "20" ) ;
       mConfData.mTobintax_TradeProzent =Double.parseDouble(ss);

       // TobinTaxAgent Interventionsband, beispiel  5%
       ss =pXmlNodeProperty.getProperty( ParameterNameConst.TobintaxInterventionsband, "5" ) ;
       mConfData.mTobintax_Interventionsband = Double.parseDouble(ss);

       // Cash1 Name
       mConfData.mCash1_Name =pXmlNodeProperty.getProperty( ParameterNameConst.Cash1_Name, "Euro" ) ;

       // Cash2 Name
       mConfData.mCash2_Name =pXmlNodeProperty.getProperty( ParameterNameConst.Cash2_Name, "Dollar" ) ;

       ////////////////////////////////////////////////////////////////
       //                                                            //
       // Initialize the Parameter Group: Logging                    //
       //                                                            //
       ////////////////////////////////////////////////////////////////

       ss = pXmlNodeProperty.getProperty( ParameterNameConst.LogAgentDailyDepot,"false");
       mConfData.mLogAgentDailyDepot = Boolean.valueOf(ss).booleanValue();
       ss = pXmlNodeProperty.getProperty( ParameterNameConst.LogAgentExchangeHistoy,"false");
       mConfData.mLogAgentExchangeHistoy = Boolean.valueOf(ss).booleanValue();
       ss = pXmlNodeProperty.getProperty( ParameterNameConst.LogDailyTradeBook,"false");
       mConfData.mLogDailyTradeBook =Boolean.valueOf(ss).booleanValue();

       ////////////////////////////////////////////////////////////////
       //                                                            //
       // Initialize the Parameter Group: NodeDistribution           //
       //                                                            //
       ////////////////////////////////////////////////////////////////

       ss = pXmlNodeProperty.getProperty( ParameterNameConst.RepeatTimes, "2");
       mConfData.mRepeatTimes = Integer.parseInt(ss);


       /*
       This parameter will not be used !!!!
       ss = pXmlNodeProperty.getProperty( ParameterNameConst.UseCommonNetworkNode2AgentTypeDistribution, "true");
       ss = ss.trim();

       if ( ss.equalsIgnoreCase("true") )
       {
         mConfData.mUseCommonNode2TypeDistribution = true;
       }
       else
       {
         mConfData.mUseCommonNode2TypeDistribution = false;
       }
       */

       /*
       This parameter will not be used !!!!

       ss = pXmlNodeProperty.getProperty( ParameterNameConst.NetworkNodeDistributionRandomMode, "false");
       ss = ss.trim();
       if ( ss.equalsIgnoreCase("true") )
       {
         mConfData.mCommonNode2TypeDistributionRandomModeEnabled = true;
       }
       else
       {
         mConfData.mCommonNode2TypeDistributionRandomModeEnabled = false;
       }

       */

}

public NetworkConfig loadXmlNetworkConfig( XmlNodeProperty pXmlNodeProperty )
{
        String  networkfilename = pXmlNodeProperty.getProperty( ParameterNameConst.NetworkFile );

        // Unix-compatible
        networkfilename = networkfilename.replace('\\','/');

        //System.out.println("One Networkfile=" + networkfilename);
        NetworkConfig oneconfig = new NetworkConfig( networkfilename  );

        String ss = pXmlNodeProperty.getProperty( ParameterNameConst.RandomTrader, "2");
        oneconfig.mRandomTraderNumber = Integer.parseInt(ss);
        String fixedtax = pXmlNodeProperty.getProperty( ParameterNameConst.TobintaxAgentFestSteuer, "1.0");
        String extratax = pXmlNodeProperty.getProperty( ParameterNameConst.TobintaxAgentExtraSteuer, "5.0");
        oneconfig.mFesteTobinTax = Double.parseDouble(fixedtax);
        oneconfig.mExtraTobinTax = Double.parseDouble(extratax);

        ss = pXmlNodeProperty.getProperty( ParameterNameConst.RandomTrader, "2");
        oneconfig.mRandomTraderNumber = Integer.parseInt(ss);

        oneconfig.mNetworkFileLoader = new NetworkFileLoader( networkfilename );
        try
        {

          oneconfig.mNodesInNetwork   = oneconfig.mNetworkFileLoader.checkAgentNumber();
          oneconfig.mNetworkFileLoader.processnetworkfile();
          oneconfig.mBlankoAgentNumber = oneconfig.mNetworkFileLoader.getBlankoAgent();
          oneconfig.mInvestorNumber    = oneconfig.mNetworkFileLoader.getFundamentalInvestor();
          oneconfig.mNoiseTraderNumber = oneconfig.mNetworkFileLoader.getNoiseTrader();

        }
        catch (Exception ex)
        {
           ex.printStackTrace();
           System.out.println("Error while loading network file "+ networkfilename );
           String mode = System.getProperty("DIALOGMODE", "true");
           if ( ! mode.equalsIgnoreCase("true") )
           {
             System.out.println("Please check if the network file is installed the corresponding directory."+ networkfilename );
             System.out.println("and then start fasm again." );
             System.out.println("" );
             System.out.println("FASM exits with above error." );
             System.exit(0);
           }
        }
        return oneconfig;
}

public void createInnererWert()
{
  // prepare InnererWert for new configuration
  Configurator.mInnererwertRandomWalkGenerator =
        new InnererwertRandomWalkGenerator(
        Configurator.mConfData.mHandelsday,
        Configurator.mConfData.mBeginInnererWert,
        Configurator.mConfData.mMinInnererWert,
        Configurator.mConfData.mMaxInnererWert,
        Configurator.mConfData.mInnererwertMaximalTagAbweichnung,
        2.5 );
  Configurator.mInnererwertRandomWalkGenerator.prepareInnererWert();

  Configurator.mConfData.mInit300     = Configurator.mInnererwertRandomWalkGenerator.getInit300();
  Configurator.mConfData.mInnererWert = Configurator.mInnererwertRandomWalkGenerator.getInnererWert();
}


public boolean ConfigFileCheck(String pXmlConfigFile )
{

  DocumentBuilderFactory docBFac;
  DocumentBuilder docBuild;
  Document doc = null;

  boolean haserror = false;

  try
  {
     docBFac = DocumentBuilderFactory.newInstance();
     docBuild = docBFac.newDocumentBuilder();
     doc = docBuild.parse( pXmlConfigFile );

     XmlNodeProperty rootelement  = new XmlNodeProperty(doc.getDocumentElement());

     // load Network confgurations
     Node networklist[] = rootelement.getStructureList("Network");

     System.out.println( networklist.length + " networks are found.");

     for ( int i=0; i<networklist.length; i++)
     {
         System.out.println( "Checking " + ( i+1 ) + ". network file ");

         XmlNodeProperty mm = new XmlNodeProperty(networklist[i]);

         String  networkfilename = mm.getProperty( ParameterNameConst.NetworkFile );
         int j = networkfilename.indexOf(".");
         String  networknodetypefilename = networkfilename.substring(0,j) + ".clu";

         if (  FileChecker.FileExist( networkfilename ) )
         {
           haserror = true;
           System.out.println ( "Network file " +networkfilename + " can not be found." );
         }

         if (  FileChecker.FileExist( networknodetypefilename ) )
         {
           haserror = true;
           System.out.println ( "Network node type file " + networknodetypefilename + " can not be found." );
         }

     }
  }
  catch (Exception ex)
  {
    haserror = true;
    ex.printStackTrace();
  }
  return  haserror;

}

/**
 * Standalone self test
 * @param args
 */
public static void main(String[] args)
{
    XmlConfigLoader pp = new XmlConfigLoader ();
    boolean haserror = pp.ConfigFileCheck( args[0]  );
    if ( haserror  )
    {
       System.exit (10);
    }
    else
    {
       System.exit ( 0 );
    }
}

}


