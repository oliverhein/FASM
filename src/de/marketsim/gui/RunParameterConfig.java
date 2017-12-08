/**
 * <p>Überschrift: Market Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

package de.marketsim.gui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import de.marketsim.SystemConstant;
import de.marketsim.config.*;
import de.marketsim.config.InnererWertModelFileReader;
import de.marketsim.config.NetworkFileLoader;

import de.marketsim.util.*;
import de.marketsim.agent.stockstore.stockdata.InnererwertRandomWalkGenerator;
import de.marketsim.agent.AgentCommandlineParameterConst;
import de.marketsim.gui.SimpleKurvFrame;
import de.marketsim.gui.graph.*;

import de.marketsim.config.AgentDistributor;

public class RunParameterConfig extends JFrame
{

  public JFrame      mMainFrame = null;
  public BasePanel   mAbschlagProzentPanel  = new BasePanel();
  public BasePanel   mMovingdaysPanel       = new BasePanel();
  public BasePanel   mInnererWertPanel      = new BasePanel();

  /*
  private AgentDistributor  mSierialNetworkNodeDistributor = new AgentDistributor();
  private AgentDistributor  mRandomNetworkNodeDistributor  = new AgentDistributor();
  */

  BasePanel  mChartPanel = new BasePanel();

  private  NetworkFileTableModel  mNetworkFileTableModel = new NetworkFileTableModel();

  private String  mConfigFile = null;
  private boolean mButtonAccept_pressed = false;

  private JPanel jButtonPanel = new JPanel();
  private JTabbedPane jTabbedPane1 = new JTabbedPane();

  private JButton jBAccept = new JButton();
  private JPanel jp_General = new JPanel();
  private JPanel jp_Inverstor = new JPanel();
  private JPanel jp_Noisetrader = new JPanel();
  private JPanel jPBlanko = new JPanel();
  private JPanel jp_Randomtrader = new JPanel();

  private JTextField jTFInnererWertMaximalTagAbweichnung = new JTextField();
  private JTextField jTFBeginInnererwert = new JTextField();
  private JTextField jTFMINInnererwert = new JTextField();
  private JLabel jLabel11 = new JLabel();
  private JTextField jTFMAXInnererwert = new JTextField();
  private JLabel jLabel10 = new JLabel();
  private JTextField jTFPeriod = new JTextField();
  private JLabel jLabel4 = new JLabel();
  private JLabel jLabel3 = new JLabel();
  private JLabel jLabel2 = new JLabel();
  private JLabel jLabel1 = new JLabel();
  private JLabel jLabel12 = new JLabel();
  private JTextField jTFRandomTraderMaxMenge = new JTextField();
  private JTextField jTFRandomTraderMinMenge = new JTextField();
  private JLabel jLabel13 = new JLabel();
  private JTextField jTFGewinnRefernztag = new JTextField();
  private JLabel jLabel16 = new JLabel();
  private JLabel jLabel15 = new JLabel();
  private JLabel jLabel14 = new JLabel();
  private JTextField jTFHillEstimatorProzent = new JTextField();
  private JLabel jLabel27 = new JLabel();
  private ButtonGroup buttonGroup1 = new ButtonGroup();

  private ButtonGroup AktienMoneyMarketSelectGroup = new ButtonGroup();
  private ButtonGroup TobintaxAktiveSelectGroup = new ButtonGroup();

  private JLabel jLabel28 = new JLabel();
  private JTextField jTFAnzahlAutoKorrelation = new JTextField();
  private JLabel jLabel119 = new JLabel();
  private JLabel jLabel1110 = new JLabel();
  private JLabel jLabel1111 = new JLabel();
  private JLabel jLabel1112 = new JLabel();
  private JTextField jTFInvestorOrderMengeStuf1 = new JTextField();
  private JTextField jTFInvestorOrderMengeStuf2 = new JTextField();
  private JTextField jTFInvestorOrderMengeStuf3 = new JTextField();
  private JTextField jTFInvestorOrderMengeStuf4 = new JTextField();
  private JLabel jLabel1113 = new JLabel();
  private JLabel jLabel1114 = new JLabel();
  private JTextField jTFNoiseTraderOrderMengeStuf4 = new JTextField();
  private JLabel jLabel1115 = new JLabel();
  private JTextField jTFNoiseTraderOrderMengeStuf3 = new JTextField();
  private JTextField jTFNoiseTraderOrderMengeStuf2 = new JTextField();
  private JTextField jTFNoiseTraderOrderMengeStuf1 = new JTextField();
  private JLabel jLabel1116 = new JLabel();
  private JLabel jLB_Investor_MINDepot1_Name = new JLabel();
  private JTextField jTFInvestorInitCash_Min = new JTextField();
  private JLabel jLB_Investor_MINDepot2_Name = new JLabel();
  private JTextField jTFInvestorInitAktien_Min = new JTextField();
  private JLabel jLB_Noisetrader_MINDepot2_Name = new JLabel();
  private JLabel jLB_Noisetrader_MINDepot1_Name = new JLabel();
  private JTextField jTFNoiseTraderInitCash_Min = new JTextField();
  private JTextField jTFNoiseTraderInitAktien_Min = new JTextField();
  private JTextField jTFRandomTraderInitAktien = new JTextField();
  private JLabel jLB_Randomtrader_Depot2_Name = new JLabel();
  private JLabel jLB_Randomtrader_Depot1_Name = new JLabel();
  private JTextField jTFRandomTraderInitCash = new JTextField();
  private JTextField jTFRandomTraderSellProb = new JTextField();
  private JLabel jLabel120 = new JLabel();
  private JTextField jTFRandomTraderBuyCheapest = new JTextField();
  private JLabel jLabel121 = new JLabel();
  private JTextField jTFRandomTraderBuyProb = new JTextField();
  private JTextField jTFRandomTraderSellBest = new JTextField();
  private JTextField jTFRandomTraderBuyIndexBased = new JTextField();
  private JLabel jLabel29 = new JLabel();
  private JTextField jTFRandomTraderSellIndexBased = new JTextField();
  private JLabel jLabel210 = new JLabel();
  private JLabel jLabel211 = new JLabel();
  private JLabel jLabel212 = new JLabel();
  private JLabel jLabel122 = new JLabel();
  private JTextField jTFRandomTraderWaitProb = new JTextField();
  private JPanel jp_Network = new JPanel();
  private JTextField jTFStatusExchangeProbability = new JTextField();

  private JTextField jTFFixedMaxLostNumber = new JTextField();
  private JLabel jLabel125 = new JLabel();
  private JTextField jTFTradeDays = new JTextField();
  private JLabel jLabel1117 = new JLabel();


  private JButton jBADDNetworkFile = new JButton();
  private ButtonGroup ButtonGroupMappingMode = new ButtonGroup();

  private JButton jBSaveto = new JButton();
  private JButton jBBacktoMain = new JButton();
  private JLabel jLabel214 = new JLabel();
  private JTextField jTF_Investor_SleepProcent = new JTextField();
  private JLabel jLabel215 = new JLabel();
  private JTextField jTF_NoiseTrader_SleepProcent = new JTextField();
  private ButtonGroup bg_NoiseTrader_R1_AveragePriceMode = new ButtonGroup();
  private ButtonGroup bg_NoiseTrader_R2 = new ButtonGroup();
  private ButtonGroup bg_NoiseTrader_R3 = new ButtonGroup();
  private ButtonGroup buttonGroup3 = new ButtonGroup();
  private JPanel jP_Tobintax = new JPanel();
  private ButtonGroup buttonGroup4 = new ButtonGroup();
  private JLabel jLabel217 = new JLabel();
  private JTextField jTF_Tobintax_Days4AverageKurs = new JTextField();
  private JLabel jLabel46 = new JLabel();
  private JLabel jLabel47 = new JLabel();
  private JTextField jTF_Tobintax_TradeProzentOfDepot = new JTextField();
  JLabel jLabel218 = new JLabel();
  JTextField jTF_Tobintax_Interventionsband = new JTextField();
  JLabel jLabel48 = new JLabel();
  JLabel jLabel49 = new JLabel();
  JLabel jLabel22 = new JLabel();
  JLabel jLabel23 = new JLabel();
  JTextField jTF_CASH1NAME = new JTextField();
  JTextField jTF_CASH2NAME = new JTextField();
  private JTextField jTFPlus = new JTextField();
  private JTextField jTFMinus = new JTextField();
  private JLabel jLabel21 = new JLabel();
  private JLabel jLabel24 = new JLabel();
  private JButton jBInnererWertGeneration = new JButton();

  private  InnererwertRandomWalkGenerator  mRandomWalkgenerator;
  private  SimpleKurvFrame   mKurvFrame = new SimpleKurvFrame("Inner Value", "Inner Value");

  private TextArea mBlackBoard = new TextArea();

  private JLabel jLabel18 = new JLabel();
  private JTextField jTFAllowedAbweichungPreis2InnererWert = new JTextField();
  private JLabel jLabel110 = new JLabel();
  private JTextField jTFInvestor_Abschlag_Min = new JTextField();
  private JTextField jTFInvestor_Abschlag_Max = new JTextField();
  private JLabel jLabel25 = new JLabel();
  private JLabel jLabel35 = new JLabel();
  private JTextField jTF_NoiseTrader_AveragePrice_VaringDays_Min = new JTextField();
  private JTextField jTF_NoiseTrader_AveragePrice_VaringDays_Max = new JTextField();
  private JLabel jLabel316 = new JLabel();
  private JLabel jLabel317 = new JLabel();
  private GridLayout gridLayout1 = new GridLayout();
  private JLabel jLabel6 = new JLabel();
  private JTextField jTFRepeatTimes = new JTextField();
  private JButton jBAbschlag_Generate = new JButton();
  private JButton jB_MovingdaysGenerate = new JButton();
  private JPanel jp_Kommunikation = new JPanel();
  private JLabel jLabel1211 = new JLabel();
  private JLabel jLabel7 = new JLabel();
  private JTextField jTFLostNumberSeed = new JTextField();
  private JLabel jLabel26 = new JLabel();
  private JLabel jLabel30 = new JLabel();
  private JLabel jLabel31 = new JLabel();
  private JTextField jTFBaseDeviation4PriceLimit = new JTextField();
  private JLabel jLabel40 = new JLabel();
  private JTextField jTFAbschlagFactor = new JTextField();
  private JLabel jLabel213 = new JLabel();
  private JLabel jLabel41 = new JLabel();
  private JTextField jTFOrders4AverageLimit = new JTextField();
  private JLabel jLB_Investor_MAXDepot2_Name = new JLabel();
  private JTextField jTFInvestorInitAktien_Max = new JTextField();
  private JLabel jLB_Investor_MAXDepot1_Name = new JLabel();

  private JLabel jLB_Noisetrader_MAXDepot1_Name = new JLabel();
  private JTextField jTFNoiseTraderInitCash_Max = new JTextField();
  private JLabel jLB_Noisetrader_MAXDepot2_Name = new JLabel();
  private JTextField jTFNoiseTraderInitAktien_Max = new JTextField();
  private JTextField jTFInvestorInitCash_Max = new JTextField();
  private GridLayout gridLayout2 = new GridLayout();
  private JLabel jLabel318 = new JLabel();
  private JLabel jLabel319 = new JLabel();
  private JTextField jTFNoiseTrader_MinLimitAdjust = new JTextField();
  private JTextField jTFNoiseTrader_MaxLimitAdjust = new JTextField();
  private JButton jBSpeichernInnererWert = new JButton();
  private JLabel jLabel111 = new JLabel();
  private JTextField jTFInnererWertMuster = new JTextField();
  private JButton jBInnererWertLaden = new JButton();
  private JButton jBLoadInnererWertMuster = new JButton();

  private JLabel jLB_Investor_MAXDepot2_Name1 = new JLabel();
  private JLabel jLB_Investor_MINDepot2_Name1 = new JLabel();
  private JLabel jLB_Investor_MAXDepot1_Name6 = new JLabel();
  private JLabel jLB_Investor_MINDepot1_Name2 = new JLabel();
  private JTextField jTFBlankoAgent_MaxCash = new JTextField();
  private JTextField jTFBlankoAgent_MinCash = new JTextField();
  private JTextField jTFBlankoAgent_MinAktien = new JTextField();
  private JTextField jTFBlankoAgent_MaxAktien = new JTextField();
  private JLabel jLabel5 = new JLabel();
  private JTextField jTFBlankoAgentPlusIndexForActivation_Min = new JTextField();
  private JLabel jLabel8 = new JLabel();
  private JTextField jTFBlankoAgentInactiveDays_Min = new JTextField();
  private JLabel jLabel9 = new JLabel();
  private JLabel jLabel17 = new JLabel();
  private JTextField jTFBlankoAgentMinusIndexForDeactivation_Min = new JTextField();
  private JTextField jTFBlankoAgentDayOfIndexWindow_Min = new JTextField();

  //private JCheckBox jCBCommonNode2TypeRandomDistributionMode = new JCheckBox();

  private JLabel jLabel43 = new JLabel();
  private JLabel jLabel44 = new JLabel();
  private JLabel jLabel112 = new JLabel();
  private JTextField jTFRandomTraderRandomSeed4Decision = new JTextField();
  private JLabel jLabel216 = new JLabel();
  private JLabel jLabel219 = new JLabel();
  private JLabel jLabel2110 = new JLabel();
  private JLabel jLabel113 = new JLabel();
  private JLabel jLabel45 = new JLabel();
  private JLabel jLabel410 = new JLabel();
  private JLabel jLabel411 = new JLabel();
  private JLabel jLabel412 = new JLabel();
  private JLabel jLabel2111 = new JLabel();
  private JLabel jLabel2112 = new JLabel();
  private JLabel jLabel2113 = new JLabel();
  private JLabel jLabel2114 = new JLabel();
  private JLabel jLabel114 = new JLabel();
  private JLabel jLabel115 = new JLabel();
  JPanel jP_Marketmode = new JPanel();
  JRadioButton jRB_AktienMarket = new JRadioButton();
  JRadioButton jRB_MoneyMarket = new JRadioButton();
  JRadioButton jRB_Tobintax_Aktive = new JRadioButton();
  JRadioButton jRB_Tobintax_Inaktive = new JRadioButton();
  JLabel jLabel2115 = new JLabel();
  private JLabel jLabel1119 = new JLabel();
  private JTextField jTFNoiseTraderOrderMengeStuf5 = new JTextField();
  private JLabel jLabel11110 = new JLabel();
  private JLabel jLabel11111 = new JLabel();
  private JLabel jLabel11112 = new JLabel();
  private JTextField jTFNoiseTraderKursChangedProcentLimit1 = new JTextField();
  private JTextField jTFNoiseTraderKursChangedProcentLimit2 = new JTextField();
  private JTextField jTFNoiseTraderKursChangedProcentLimit3 = new JTextField();
  private JLabel jLabel11113 = new JLabel();
  private JLabel jLabel11114 = new JLabel();
  JLabel jLabel413 = new JLabel();
  JLabel jLabel414 = new JLabel();
  JLabel jLabel415 = new JLabel();
  private JTextField jTFNoiseTraderStufe1MarketOrderBilligestKauf = new JTextField();
  private JTextField jTFNoiseTraderStufe2MarketOrderBilligestKauf = new JTextField();
  private JTextField jTFNoiseTraderStufe3MarketOrderBilligestKauf = new JTextField();
  private JTextField jTFNoiseTraderStufe4MarketOrderBilligestKauf = new JTextField();
  private JLabel jLabel1120 = new JLabel();
  private JLabel jLabel1121 = new JLabel();
  private JLabel jLabel1122 = new JLabel();
  private JTextField jTFInvestorKurschangeLimit1 = new JTextField();
  private JLabel jLabel416 = new JLabel();
  private JTextField jTFInvestorKurschangeLimit2 = new JTextField();
  private JLabel jLabel417 = new JLabel();
  private JTextField jTFInvestorKurschangeLimit3 = new JTextField();
  private JLabel jLabel418 = new JLabel();
  private JLabel jLabel11119 = new JLabel();
  private JTextField jTFInvestorStufe1MarketOrderBilligestKauf = new JTextField();
  private JTextField jTFInvestorStufe2MarketOrderBilligestKauf = new JTextField();
  private JTextField jTFInvestorStufe3MarketOrderBilligestKauf = new JTextField();
  private JTextField jTFInvestorStufe4MarketOrderBilligestKauf = new JTextField();
  private JRadioButton jRB_FixedMaxLostNumber = new JRadioButton();
  private JRadioButton jRB_VariableMaxLostNumber = new JRadioButton();
  private ButtonGroup buttonGroupMaxLostMode = new ButtonGroup();
  private JLabel jLabel313 = new JLabel();
  private JLabel jLabel314 = new JLabel();
  private JLabel jLabel315 = new JLabel();
  private JLabel jLabel3112 = new JLabel();
  private JLabel jLabel3113 = new JLabel();
  private JTextField jTFInvestor_GaussMean = new JTextField();
  private JTextField jTFInvestor_GaussDeviation = new JTextField();
  private JTextField jTFInvestor_LinkBereichProzent = new JTextField();
  private JTextField jTFInvestor_MittBereichProzent = new JTextField();
  private JTextField jTFInvestor_RechtBereichProzent = new JTextField();
  private JLabel jLabel419 = new JLabel();
  private JLabel jLabel4110 = new JLabel();
  private JLabel jLabel4111 = new JLabel();
  private JLabel jLabel111111 = new JLabel();
  private JLabel jLabel111112 = new JLabel();
  private JLabel jLabel111110 = new JLabel();
  private JTextField jTFInvestorStufe1MarketOrderBestVerkauf = new JTextField();
  private JTextField jTFInvestorStufe4MarketOrderBestVerkauf = new JTextField();
  private JTextField jTFInvestorStufe3MarketOrderBestVerkauf = new JTextField();
  private JTextField jTFInvestorStufe2MarketOrderBestVerkauf = new JTextField();
  private JLabel jLabel111113 = new JLabel();
  private JLabel jLabel111114 = new JLabel();
  private JLabel jLabel111115 = new JLabel();
  private JLabel jLabel111116 = new JLabel();
  private JTextField jTFNoiseTraderStufe3MarketOrderBestVerkauf = new JTextField();
  private JTextField jTFNoiseTraderStufe2MarketOrderBestVerkauf = new JTextField();
  private JTextField jTFNoiseTraderStufe1MarketOrderBestVerkauf = new JTextField();
  private JTextField jTFNoiseTraderStufe4MarketOrderBestVerkauf = new JTextField();
  private JLabel jLB_Investor_MINDepot1_Name1 = new JLabel();
  private JLabel jLB_Investor_MAXDepot1_Name1 = new JLabel();
  private JLabel jLB_Investor_MAXDepot1_Name2 = new JLabel();
  private JLabel jLB_Investor_MAXDepot1_Name3 = new JLabel();
  private JLabel jLB_Investor_MAXDepot1_Name4 = new JLabel();
  private JLabel jLB_Investor_MAXDepot1_Name5 = new JLabel();
  private JTextField jTFInvestor_InnererWert_AbweichungInterval_Untergrenz = new JTextField();
  private JLabel jLabel4112 = new JLabel();
  private JTextField jTFInvestor_InnererWert_AbweichungInterval_Obengrenz1 = new JTextField();
  private JLabel jLabel4113 = new JLabel();
  private JTextField jTFInvestor_KursAnderung_schwelle = new JTextField();
  private JLabel jLabel4114 = new JLabel();
  private JTextField jTFInvestor_Potenzial_Aktueller_InnererWert = new JTextField();
  private JLabel jLabel4115 = new JLabel();
  private JTextField jTFInvestor_KursAnderung_RefenzTag = new JTextField();
  private ButtonGroup buttonGroupDataSaveFormat = new ButtonGroup();
  private JRadioButton jRBDataSaveFormatGerman = new JRadioButton();
  private JRadioButton jRBDataSaveFormatEnglish = new JRadioButton();
  private JPanel jP_Logging = new JPanel();
  private JCheckBox jCB_LogAgentExchangeHistory = new JCheckBox();
  private JCheckBox jCB_LogDailyTradeBook = new JCheckBox();
  private JCheckBox jCB_LogAgentDailyDepot = new JCheckBox();
  private JButton jB_RemoveNetwork = new JButton();
  private JButton jB_DisplayGraphic = new JButton();
  private JScrollPane jScrollPane_NetworkFiles = new JScrollPane();
  private JTable jTable_Networkfiles = new JTable();
  private JEditorPane jEditorPane1 = new JEditorPane();
  private JLabel jLabel420 = new JLabel();
  private JLabel jLabel421 = new JLabel();
  private JLabel jLabel221 = new JLabel();
  private JLabel jLabel116 = new JLabel();
  private JLabel jLabel19 = new JLabel();
  private JLabel jLabel20 = new JLabel();
  private JLabel jLabel32 = new JLabel();
  private JTextField jTFBlankoAgentInactiveDays_Max = new JTextField();
  private JTextField jTFBlankoAgentDayOfIndexWindow_Max = new JTextField();
  private JTextField jTFBlankoAgent_SleepProcent = new JTextField();
  private JLabel jLabel33 = new JLabel();
  private JLabel jLabel34 = new JLabel();
  private JLabel jLabel36 = new JLabel();
  private JCheckBox jCBBlankoAgent_AppendCashAllowed = new JCheckBox();
  private JLabel jLabel37 = new JLabel();
  private JLabel jLabel38 = new JLabel();
  private JTextField jTFBlankoAgentIMinimalActivationDays = new JTextField();
  private JLabel jLabel39 = new JLabel();
  private JTextField jTFBlankoAgentPlusIndexForActivation_Max = new JTextField();
  private JTextField jTFBlankoAgentMinusIndexForDeactivation_Max = new JTextField();
  private JLabel jLabel117 = new JLabel();
  private JLabel jLabel310 = new JLabel();
  private JLabel jLabel311 = new JLabel();
  private JLabel jLabel1118 = new JLabel();
  private JCheckBox jCB_InvestorAffectedByOtherNodes = new JCheckBox();
  private JCheckBox jCB_NoiseTraderAffectedByOtherNodes = new JCheckBox();
  private JLabel jLabel11115 = new JLabel();
  private JLabel jLabel312 = new JLabel();
  private JTextField jTFBlankoAgent_DaysOfTotalSell = new JTextField();
  private JLabel jLabel320 = new JLabel();
  private JTextField jTFBlankoAgent_AppendCash = new JTextField();
  private JLabel jLabel42 = new JLabel();

  public RunParameterConfig( String title, boolean modal) {


    //super(frame, title, modal);
    super( title );
    try {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }

    this.jTable_Networkfiles.setModel( this.mNetworkFileTableModel );
    this.jTable_Networkfiles.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
    this.jTable_Networkfiles.setSelectionBackground( Color.red );

    parameternameUpdate();
  }

  public void setMainFrame(JFrame pFrame)
  {
    this.mMainFrame = pFrame;
  }

  private void parameternameUpdate()
  {

    if ( Configurator.istAktienMarket() )
    {
       // Aktien Market
       this.jLB_Investor_MINDepot1_Name.setText("Min Cash");
       this.jLB_Investor_MINDepot2_Name.setText("Min Securities");
       this.jLB_Noisetrader_MINDepot1_Name.setText("Min Cash");
       this.jLB_Noisetrader_MINDepot2_Name.setText("Min Securities");
       this.jLB_Investor_MAXDepot1_Name.setText("Max Cash");
       this.jLB_Investor_MAXDepot2_Name.setText("Max Securities");
       this.jLB_Noisetrader_MAXDepot1_Name.setText("Max Cash");
       this.jLB_Noisetrader_MAXDepot2_Name.setText("Max Securities");
       this.jLB_Randomtrader_Depot1_Name.setText("Cash");
       this.jLB_Randomtrader_Depot2_Name.setText("Securities");

    }
    else
    {
       // Cash Market Mode
       this.jLB_Investor_MINDepot1_Name.setText("Min Cash1");
       this.jLB_Investor_MINDepot2_Name.setText("Min Cash2");
       this.jLB_Noisetrader_MINDepot1_Name.setText("Min Cash1");
       this.jLB_Noisetrader_MINDepot2_Name.setText("Min Cash2");
       this.jLB_Investor_MAXDepot1_Name.setText("Max Cash1");
       this.jLB_Investor_MAXDepot2_Name.setText("Max Cash2");
       this.jLB_Noisetrader_MAXDepot1_Name.setText("Max Cash1");
       this.jLB_Noisetrader_MAXDepot2_Name.setText("Max Cash2");
       this.jLB_Randomtrader_Depot1_Name.setText("Cash1");
       this.jLB_Randomtrader_Depot2_Name.setText("Cash2");
    }
  }

  /**
  public RunParameterConfig()
  {
    this(null, "", false);
  }
  */

 public boolean getButtonAccept_pressed()
 {

   return mButtonAccept_pressed;

 }
  private void jbInit() throws Exception
  {

    jP_Tobintax.setLayout(null);
    jLabel217.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel217.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel217.setText("Moving Average");
    jLabel217.setBounds(new Rectangle(47, 147, 150, 33));
    jTF_Tobintax_Days4AverageKurs.setText("20");
    jTF_Tobintax_Days4AverageKurs.setBounds(new Rectangle(208, 152, 79, 25));
    jLabel46.setText("Days");
    jLabel46.setBounds(new Rectangle(298, 153, 59, 24));
    jLabel47.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel47.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel47.setText("Trade Percentage of Wealth");
    jLabel47.setBounds(new Rectangle(35, 181, 164, 32));
    jTF_Tobintax_TradeProzentOfDepot.setText("10");
    jTF_Tobintax_TradeProzentOfDepot.setBounds(new Rectangle(208, 185, 79, 25));
    jLabel218.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel218.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel218.setText("Intervention Band");
    jLabel218.setBounds(new Rectangle(47, 111, 150, 33));
    jTF_Tobintax_Interventionsband.setText("5.0");
    jTF_Tobintax_Interventionsband.setBounds(new Rectangle(208, 115, 79, 25));
    jLabel48.setText("%");
    jLabel48.setBounds(new Rectangle(298, 117, 39, 24));
    jLabel49.setBounds(new Rectangle(298, 181, 39, 24));
    jLabel49.setText("%");
    jLabel22.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel22.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel22.setText("Cash 1 Name:");
    jLabel22.setBounds(new Rectangle(47, 214, 150, 32));
    jLabel23.setBounds(new Rectangle(47, 250, 150, 32));
    jLabel23.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel23.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel23.setText("Cash 2 Name:");
    jTF_CASH1NAME.setText("Euro");
    jTF_CASH1NAME.setBounds(new Rectangle(208, 216, 79, 25));
    jTF_CASH2NAME.setText("US-Dollar");
    jTF_CASH2NAME.setBounds(new Rectangle(208, 249, 79, 25));

    jTFTradeDays.setText("300"); // Default
    jLabel12.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel13.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel122.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel29.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel211.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel120.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel210.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel212.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel121.setHorizontalAlignment(SwingConstants.RIGHT);
    jLB_Randomtrader_Depot2_Name.setHorizontalAlignment(SwingConstants.RIGHT);
    jLB_Randomtrader_Depot1_Name.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel215.setHorizontalAlignment(SwingConstants.LEFT);
    jLB_Noisetrader_MINDepot2_Name.setHorizontalAlignment(SwingConstants.LEFT);
    jLB_Noisetrader_MINDepot1_Name.setHorizontalAlignment(SwingConstants.LEFT);
    jLabel214.setHorizontalAlignment(SwingConstants.LEFT);
    jLB_Investor_MINDepot2_Name.setHorizontalAlignment(SwingConstants.LEFT);
    jLB_Investor_MINDepot1_Name.setHorizontalAlignment(SwingConstants.LEFT);
    jLabel1112.setHorizontalAlignment(SwingConstants.LEFT);
    jLabel1111.setHorizontalAlignment(SwingConstants.LEFT);
    jLabel1110.setHorizontalAlignment(SwingConstants.LEFT);
    jLabel119.setHorizontalAlignment(SwingConstants.LEFT);
    jTFPlus.setBounds(new Rectangle(340, 27, 42, 24));
    jTFMinus.setBounds(new Rectangle(340, 53, 42, 21));
    jLabel21.setBounds(new Rectangle(238, 53, 98, 21));
    jLabel21.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel21.setText("Below Init Value");
    jLabel24.setBounds(new Rectangle(239, 27, 96, 24));
    jLabel24.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel24.setText("Above Init Value");
    jLabel11.setFont(new java.awt.Font("Dialog", 1, 12));
    jp_General.setFont(new java.awt.Font("Dialog", 1, 12));
    jBInnererWertGeneration.setBounds(new Rectangle(26, 211, 81, 24));
    jBInnererWertGeneration.setActionCommand("InnererWertGeneration");
    jBInnererWertGeneration.setMargin(new Insets(2, 2, 2, 2));
    jBInnererWertGeneration.setText("Generate");
    jBInnererWertGeneration.addActionListener(new RunParameterConfig_jBInnererWertGeneration_actionAdapter(this));


    jLabel18.setBounds(new Rectangle(24, 284, 96, 21));
    jLabel18.setText("Max Distortion");
    jLabel18.setFont(new java.awt.Font("Dialog", 1, 12));
    jTFAllowedAbweichungPreis2InnererWert.setText("100");
    jTFAllowedAbweichungPreis2InnererWert.setBounds(new Rectangle(148, 284, 40, 20));
    jLabel110.setText("%");
    jLabel110.setBounds(new Rectangle(189, 285, 20, 17));

    jTFInvestor_Abschlag_Min.setBounds(new Rectangle(153, 243, 52, 19));

    jTFInvestor_Abschlag_Min.setText("0.5");
    jTFInvestor_Abschlag_Max.setText("8.0");

    jTFInvestor_Abschlag_Max.setBounds(new Rectangle(153, 272, 52, 20));
    jLabel25.setHorizontalAlignment(SwingConstants.LEFT);
    jLabel25.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel25.setText("Max Discount");
    jLabel25.setBounds(new Rectangle(12, 269, 88, 20));
    jLabel35.setBounds(new Rectangle(12, 240, 94, 22));
    jLabel35.setText("Min Discount");
    jLabel35.setHorizontalAlignment(SwingConstants.LEFT);
    jLabel35.setFont(new java.awt.Font("Dialog", 1, 12));
    jTF_NoiseTrader_AveragePrice_VaringDays_Min.setText("10");
    jTF_NoiseTrader_AveragePrice_VaringDays_Min.setBounds(new Rectangle(159, 266, 38, 21));
    jTF_NoiseTrader_AveragePrice_VaringDays_Min.setPreferredSize(new Dimension(91, 30));
    jTF_NoiseTrader_AveragePrice_VaringDays_Max.setBounds(new Rectangle(159, 295, 38, 21));
    jTF_NoiseTrader_AveragePrice_VaringDays_Max.setText("30");
    jTF_NoiseTrader_AveragePrice_VaringDays_Max.setPreferredSize(new Dimension(91, 30));
    jLabel316.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel316.setHorizontalAlignment(SwingConstants.LEFT);
    jLabel316.setText("Min Moving Average");
    jLabel316.setBounds(new Rectangle(5, 266, 130, 20));
    jLabel317.setBounds(new Rectangle(5, 295, 131, 21));
    jLabel317.setText("Max Moving Average");
    jLabel317.setHorizontalAlignment(SwingConstants.LEFT);
    jLabel317.setFont(new java.awt.Font("Dialog", 1, 12));


    jLabel6.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel6.setText("Iterations");
    jLabel6.setBounds(new Rectangle(17, 382, 67, 18));
    jTFRepeatTimes.setText("2");
    jTFRepeatTimes.setBounds(new Rectangle(84, 383, 29, 18));
    jBAbschlag_Generate.setBounds(new Rectangle(14, 420, 198, 27));
    jBAbschlag_Generate.setText("Generate Discounts");
    jBAbschlag_Generate.addActionListener(new RunParameterConfig_jBAbschlag_Generate_actionAdapter(this));
    jB_MovingdaysGenerate.setBounds(new Rectangle(6, 399, 146, 27));
    jB_MovingdaysGenerate.setText("Generate Averages");
    jB_MovingdaysGenerate.addActionListener(new RunParameterConfig_jB_MovingdaysGenerate_actionAdapter(this));

    mChartPanel.setBorder(BorderFactory.createLineBorder(Color.black));
    mChartPanel.setBounds(new Rectangle(332, 241, 320, 110));
    mChartPanel.setLayout(gridLayout2);
    mBlackBoard.setBounds(new Rectangle(374, 391, 320, 59));
    mBlackBoard.setEditable(false);

    jp_Kommunikation.setLayout(null);
    jLabel1211.setFont(new java.awt.Font("Dialog", 1, 14));
    jLabel1211.setText("Communication probability:");
    jLabel1211.setBounds(new Rectangle(15, 34, 201, 23));
    jLabel7.setText("%");
    jLabel7.setBounds(new Rectangle(258, 35, 20, 20));
    jTFLostNumberSeed.setText("5");
    jTFLostNumberSeed.setBounds(new Rectangle(212, 100, 33, 20));
    jLabel26.setFont(new java.awt.Font("Dialog", 1, 14));
    jLabel26.setText("Switch back to Fundamental");
    jLabel26.setBounds(new Rectangle(19, 135, 207, 18));
    jLabel30.setText("Distortion");
    jLabel30.setBounds(new Rectangle(56, 157, 74, 20));
    jLabel31.setText("Discount Factor");
    jLabel31.setBounds(new Rectangle(57, 176, 98, 20));
    jTFBaseDeviation4PriceLimit.setText("15");
    jTFBaseDeviation4PriceLimit.setBounds(new Rectangle(156, 156, 37, 22));
    jLabel40.setText("%");
    jLabel40.setBounds(new Rectangle(194, 157, 27, 19));
    jTFAbschlagFactor.setText("2");
    jTFAbschlagFactor.setBounds(new Rectangle(156, 179, 38, 19));
    jLabel213.setBounds(new Rectangle(20, 206, 157, 18));
    jLabel213.setFont(new java.awt.Font("Dialog", 1, 14));
    jLabel213.setToolTipText("");
    jLabel213.setText("Switch back to Trend");
    jLabel41.setText("Orders in past to be considered");
    jLabel41.setBounds(new Rectangle(57, 226, 179, 24));
    jTFOrders4AverageLimit.setText("10");
    jTFOrders4AverageLimit.setBounds(new Rectangle(247, 226, 40, 24));
    jLB_Investor_MAXDepot2_Name.setFont(new java.awt.Font("Dialog", 1, 12));
    jLB_Investor_MAXDepot2_Name.setHorizontalAlignment(SwingConstants.LEFT);
    jLB_Investor_MAXDepot2_Name.setText("Max Securities");
    jLB_Investor_MAXDepot2_Name.setBounds(new Rectangle(26, 87, 100, 18));
    jTFInvestorInitAktien_Max.setText("0");
    jTFInvestorInitAktien_Max.setBounds(new Rectangle(131, 86, 73, 16));
    jLB_Investor_MAXDepot1_Name.setFont(new java.awt.Font("Dialog", 1, 12));
    jLB_Investor_MAXDepot1_Name.setHorizontalAlignment(SwingConstants.LEFT);
    jLB_Investor_MAXDepot1_Name.setText("Max Cash");
    jLB_Investor_MAXDepot1_Name.setBounds(new Rectangle(26, 39, 75, 18));
    jTFInvestorInitCash_Min.setText("0");
    jTFInvestorInitCash_Min.setBounds(new Rectangle(353, 16, 71, 21));
    jLB_Noisetrader_MAXDepot1_Name.setFont(new java.awt.Font("Dialog", 1, 12));
    jLB_Noisetrader_MAXDepot1_Name.setText("Max Cash");
    jLB_Noisetrader_MAXDepot1_Name.setBounds(new Rectangle(242, 18, 76, 20));
    jTFNoiseTraderInitCash_Max.setText("0");
    jTFNoiseTraderInitCash_Max.setBounds(new Rectangle(348, 16, 86, 23));
    jLB_Noisetrader_MAXDepot2_Name.setFont(new java.awt.Font("Dialog", 1, 12));
    jLB_Noisetrader_MAXDepot2_Name.setText("Max Securities ");
    jLB_Noisetrader_MAXDepot2_Name.setBounds(new Rectangle(242, 46, 96, 22));
    jTFNoiseTraderInitAktien_Max.setText("0");
    jTFNoiseTraderInitAktien_Max.setBounds(new Rectangle(348, 45, 86, 23));
    jTFInvestorInitCash_Max.setText("0");
    jTFInvestorInitCash_Max.setBounds(new Rectangle(131, 41, 73, 17));
    jBADDNetworkFile.setBounds(new Rectangle(666, 12, 84, 25));




    mMovingdaysPanel.setLocation( 275,220 );
    mMovingdaysPanel.setSize    ( 430,210 );

    mMovingdaysPanel.setMaxMinUpdatingRequired( true );
    mMovingdaysPanel.setBounds(new Rectangle(275, 220, 446, 210));

    jLabel318.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel318.setHorizontalAlignment(SwingConstants.LEFT);
    jLabel318.setText("Max Daily Limit Change");
    jLabel318.setBounds(new Rectangle(5, 238, 140, 20));
    jLabel319.setBounds(new Rectangle(5, 209, 141, 23));
    jLabel319.setText("Min Daily Limit Change");
    jLabel319.setHorizontalAlignment(SwingConstants.LEFT);
    jLabel319.setFont(new java.awt.Font("Dialog", 1, 12));
    jTFNoiseTrader_MinLimitAdjust.setText("0.0");
    jTFNoiseTrader_MinLimitAdjust.setBounds(new Rectangle(159, 209, 38, 21));
    jTFNoiseTrader_MaxLimitAdjust.setBounds(new Rectangle(159, 238, 38, 21));
    jTFNoiseTrader_MaxLimitAdjust.setText("3.0");
    jBSpeichernInnererWert.setBounds(new Rectangle(119, 211, 81, 24));
    jBSpeichernInnererWert.setMargin(new Insets(2, 2, 2, 2));
    jBSpeichernInnererWert.setText("Save");
    jBSpeichernInnererWert.addActionListener(new RunParameterConfig_jBSpeichernInnererWert_actionAdapter(this));
    jLabel111.setBounds(new Rectangle(26, 182, 114, 21));
    jLabel111.setText("File for Inner Value");
    jLabel111.setFont(new java.awt.Font("Dialog", 1, 12));


    jTFInnererWertMuster.setBounds(new Rectangle(166, 182, 150, 22));
    jBInnererWertLaden.setBounds(new Rectangle(215, 211, 160, 24));
    jBInnererWertLaden.setMargin(new Insets(2, 2, 2, 2));
    jBInnererWertLaden.setText("Choose Inner Value File");
    jBInnererWertLaden.addActionListener(new RunParameterConfig_jBInnererWertLaden_actionAdapter(this));
    jBLoadInnererWertMuster.setBounds(new Rectangle(319, 180, 54, 23));
    jBLoadInnererWertMuster.setMargin(new Insets(2, 2, 2, 2));
    jBLoadInnererWertMuster.setText("load");
    jBLoadInnererWertMuster.addActionListener(new RunParameterConfig_jBLoadInnererWertMuster_actionAdapter(this));

    //jCBCommonNode2TypeRandomDistributionMode.setText("use Random");
    //jCBCommonNode2TypeRandomDistributionMode.setBounds(new Rectangle(195, 349, 127, 20));
    jLabel43.setBounds(new Rectangle(192, 253, 18, 19));
    jLabel43.setText("%");
    jLabel44.setBounds(new Rectangle(193, 280, 17, 18));
    jLabel44.setText("%");
    jLabel112.setBounds(new Rectangle(12, 342, 283, 20));
    jLabel112.setText("Seed of random generation of decision");
    jLabel112.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel112.setHorizontalAlignment(SwingConstants.RIGHT);
    jTFRandomTraderRandomSeed4Decision.setBounds(new Rectangle(302, 344, 76, 22));
    jTFRandomTraderRandomSeed4Decision.setText("10000");
    jLabel216.setFont(new java.awt.Font("Dialog", 1, 14));
    jLabel216.setText("%");
    jLabel216.setBounds(new Rectangle(380, 166, 21, 20));
    jLabel216.setHorizontalAlignment(SwingConstants.LEFT);
    jLabel219.setHorizontalAlignment(SwingConstants.LEFT);
    jLabel219.setBounds(new Rectangle(380, 252, 21, 20));
    jLabel219.setText("%");
    jLabel219.setFont(new java.awt.Font("Dialog", 1, 14));
    jLabel2110.setHorizontalAlignment(SwingConstants.LEFT);
    jLabel2110.setBounds(new Rectangle(378, 80, 29, 20));
    jLabel2110.setText("%");
    jLabel2110.setFont(new java.awt.Font("Dialog", 1, 14));
    jLabel113.setHorizontalAlignment(SwingConstants.LEFT);
    jLabel113.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel113.setText("Different seed will result in different decision series. ");
    jLabel113.setBounds(new Rectangle(66, 370, 397, 25));
    jLabel45.setText("%");
    jLabel45.setBounds(new Rectangle(179, 71, 13, 25));
    jLabel410.setText("%");
    jLabel410.setBounds(new Rectangle(172, 111, 24, 18));
    jLabel411.setBounds(new Rectangle(207, 241, 19, 20));
    jLabel411.setText("%");
    jLabel412.setBounds(new Rectangle(207, 272, 19, 21));
    jLabel412.setText("%");
    jLabel2111.setFont(new java.awt.Font("Dialog", 1, 14));
    jLabel2111.setText("%");
    jLabel2111.setBounds(new Rectangle(377, 108, 29, 20));
    jLabel2111.setHorizontalAlignment(SwingConstants.LEFT);
    jLabel2112.setFont(new java.awt.Font("Dialog", 1, 14));
    jLabel2112.setText("%");
    jLabel2112.setBounds(new Rectangle(378, 137, 29, 20));
    jLabel2112.setHorizontalAlignment(SwingConstants.LEFT);
    jLabel2113.setHorizontalAlignment(SwingConstants.LEFT);
    jLabel2113.setBounds(new Rectangle(377, 197, 21, 20));
    jLabel2113.setText("%");
    jLabel2113.setFont(new java.awt.Font("Dialog", 1, 14));
    jLabel2114.setHorizontalAlignment(SwingConstants.LEFT);
    jLabel2114.setBounds(new Rectangle(379, 220, 21, 20));
    jLabel2114.setText("%");
    jLabel2114.setFont(new java.awt.Font("Dialog", 1, 14));
    jLabel114.setBounds(new Rectangle(394, 109, 397, 25));
    jLabel114.setText("Index based Buy + Cheapest Buy must be 100.");
    jLabel114.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel114.setHorizontalAlignment(SwingConstants.LEFT);
    jLabel115.setHorizontalAlignment(SwingConstants.LEFT);
    jLabel115.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel115.setText("Index based Sell +Best Sell must be 100.");
    jLabel115.setBounds(new Rectangle(398, 193, 244, 25));
    jRB_AktienMarket.setFont(new java.awt.Font("Dialog", 1, 12));
    jRB_AktienMarket.setActionCommand("StockMarket");
    jRB_AktienMarket.setSelected(true);
    jRB_AktienMarket.setText("Stock Market");
    jRB_AktienMarket.setBounds(new Rectangle(108, 50, 101, 25));

    //jRB_AktienMarket.addActionListener(new StartFrame_jRB_AktienMarket_actionAdapter(this));

    jRB_MoneyMarket.setFont(new java.awt.Font("Dialog", 1, 12));
    jRB_MoneyMarket.setActionCommand("MoneyMarket");
    jRB_MoneyMarket.setText("Cash Market");
    jRB_MoneyMarket.setBounds(new Rectangle(108, 78, 105, 25));

    //jRB_MoneyMarket.addActionListener(new StartFrame_jRB_MoneyMarket_actionAdapter(this));

    jP_Marketmode.setLayout(null);
    jRB_Tobintax_Aktive.setFont(new java.awt.Font("Dialog", 1, 12));
    jRB_Tobintax_Aktive.setActionCommand("active");
    jRB_Tobintax_Aktive.setSelected(true);
    jRB_Tobintax_Aktive.setText("active");
    jRB_Tobintax_Aktive.setBounds(new Rectangle(206, 68, 64, 20));
    jRB_Tobintax_Inaktive.setFont(new java.awt.Font("Dialog", 1, 12));
    jRB_Tobintax_Inaktive.setActionCommand("inactive");
    jRB_Tobintax_Inaktive.setText("inactive");
    jRB_Tobintax_Inaktive.setBounds(new Rectangle(280, 67, 85, 22));
    jLabel2115.setBounds(new Rectangle(46, 66, 150, 22));
    jLabel2115.setText("Tobin Tax");
    jLabel2115.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel2115.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel1119.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel1119.setText("Price Deviation Level 1:");
    jLabel1119.setBounds(new Rectangle(5, 127, 144, 19));
    jTFNoiseTraderKursChangedProcentLimit1.setBounds(new Rectangle(153, 101, 55, 22));
    jTFNoiseTraderKursChangedProcentLimit1.setText("1");

    jLabel11110.setBounds(new Rectangle(42, 124, 110, 25));
    jLabel11110.setText("change procent 2:");
    jLabel11110.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel11111.setBounds(new Rectangle(39, 149, 109, 25));
    jLabel11111.setText("Change Procent 3:");
    jLabel11111.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel11112.setBounds(new Rectangle(40, 174, 111, 25));
    jLabel11112.setText("Change Procent 4:");
    jLabel11112.setFont(new java.awt.Font("Dialog", 1, 12));
    jTFNoiseTraderKursChangedProcentLimit1.setText("1.0");
    jTFNoiseTraderKursChangedProcentLimit1.setBounds(new Rectangle(159, 127, 39, 19));
    jTFNoiseTraderKursChangedProcentLimit2.setBounds(new Rectangle(159, 152, 39, 19));
    jTFNoiseTraderKursChangedProcentLimit2.setText("2.0");
    jTFNoiseTraderKursChangedProcentLimit3.setBounds(new Rectangle(159, 179, 39, 19));
    jTFNoiseTraderKursChangedProcentLimit3.setText("3.0");
    jLabel11113.setBounds(new Rectangle(5, 152, 140, 19));
    jLabel11113.setText("Price Deviation Level 2:");
    jLabel11113.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel11114.setBounds(new Rectangle(5, 179, 138, 19));
    jLabel11114.setText("Price Deviation Level 3:");
    jLabel11114.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel413.setBounds(new Rectangle(205, 123, 13, 25));
    jLabel413.setText("%");
    jLabel414.setBounds(new Rectangle(203, 151, 13, 25));
    jLabel414.setText("%");
    jLabel415.setBounds(new Rectangle(203, 177, 13, 25));
    jLabel415.setText("%");
    jTFNoiseTraderStufe1MarketOrderBilligestKauf.setBounds(new Rectangle(428, 105, 16, 19));
    jTFNoiseTraderStufe2MarketOrderBilligestKauf.setBounds(new Rectangle(428, 131, 16, 19));
    jTFNoiseTraderStufe3MarketOrderBilligestKauf.setBounds(new Rectangle(428, 154, 16, 19));
    jTFNoiseTraderStufe3MarketOrderBilligestKauf.setText("B");
    jTFNoiseTraderStufe4MarketOrderBilligestKauf.setBounds(new Rectangle(428, 179, 16, 19));
    jTFNoiseTraderStufe4MarketOrderBilligestKauf.setText("B");
    jLabel1120.setBounds(new Rectangle(12, 165, 133, 21));
    jLabel1120.setText("Price Deviation Level 1:");
    jLabel1120.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel1120.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel1121.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel1121.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel1121.setText("Price Deviation Level 2:");
    jLabel1121.setBounds(new Rectangle(12, 189, 133, 21));
    jLabel1122.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel1122.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel1122.setText("Price Deviation Level 3:");
    jLabel1122.setBounds(new Rectangle(12, 213, 133, 20));
    jTFInvestorKurschangeLimit1.setText("1.0");
    jTFInvestorKurschangeLimit1.setBounds(new Rectangle(153, 167, 47, 21));
    jLabel416.setBounds(new Rectangle(201, 166, 24, 25));
    jLabel416.setText("%");
    jTFInvestorKurschangeLimit2.setBounds(new Rectangle(153, 189, 47, 21));
    jTFInvestorKurschangeLimit2.setText("2.0");
    jLabel417.setText("%");
    jLabel417.setBounds(new Rectangle(201, 190, 24, 25));
    jTFInvestorKurschangeLimit3.setBounds(new Rectangle(153, 212, 47, 21));
    jTFInvestorKurschangeLimit3.setText("4.0");
    jLabel418.setText("%");
    jLabel418.setBounds(new Rectangle(201, 213, 24, 16));
    jLabel11119.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel11119.setText("Market");
    jLabel11119.setBounds(new Rectangle(469, 102, 55, 19));
    jTFInvestorStufe1MarketOrderBilligestKauf.setBounds(new Rectangle(477, 144, 19, 19));
    jTFInvestorStufe2MarketOrderBilligestKauf.setBounds(new Rectangle(477, 171, 19, 19));
    jTFInvestorStufe3MarketOrderBilligestKauf.setBounds(new Rectangle(477, 196, 19, 19));
    jTFInvestorStufe4MarketOrderBilligestKauf.setBounds(new Rectangle(477, 220, 19, 19));
    jRB_FixedMaxLostNumber.setToolTipText("When the Worse Counter reaches this limit, Agent can change its type " +
    "to the type of his best friend. All agent use the same limit.");
    jRB_FixedMaxLostNumber.setActionCommand("fixed");
    jRB_FixedMaxLostNumber.setSelected(true);
    jRB_FixedMaxLostNumber.setText("Fixed Threshold");
    jRB_FixedMaxLostNumber.setBounds(new Rectangle(39, 78, 130, 22));
    jRB_VariableMaxLostNumber.setToolTipText("When the Worse Counter reaches the individual limit, Agent can change " +
    "its type to the type of his best friend.");
    jRB_VariableMaxLostNumber.setActionCommand("variable");
    jRB_VariableMaxLostNumber.setText("Random Threshold Max");
    jRB_VariableMaxLostNumber.setBounds(new Rectangle(39, 101, 166, 20));
    jRB_VariableMaxLostNumber.addActionListener(new RunParameterConfig_jRB_VariableMaxLostNumber_actionAdapter(this));
    jLabel313.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel313.setHorizontalAlignment(SwingConstants.LEFT);
    jLabel313.setText("Gauss Mean");
    jLabel313.setBounds(new Rectangle(12, 294, 87, 22));
    jLabel314.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel314.setHorizontalAlignment(SwingConstants.LEFT);
    jLabel314.setText("Gauss Deviation");
    jLabel314.setBounds(new Rectangle(12, 318, 103, 22));
    jLabel315.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel315.setHorizontalAlignment(SwingConstants.LEFT);
    jLabel315.setText("Percent of Lower Values");
    jLabel315.setBounds(new Rectangle(12, 345, 143, 22));
    jLabel3112.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel3112.setHorizontalAlignment(SwingConstants.LEFT);
    jLabel3112.setText("Percent of Mid Values");
    jLabel3112.setBounds(new Rectangle(12, 368, 136, 22));
    jLabel3113.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel3113.setHorizontalAlignment(SwingConstants.LEFT);
    jLabel3113.setText("Percent of High Values");
    jLabel3113.setBounds(new Rectangle(12, 392, 141, 22));
    jTFInvestor_GaussMean.setBounds(new Rectangle(153, 299, 52, 20));
    jTFInvestor_GaussMean.setText("2.0");
    jTFInvestor_GaussDeviation.setText("0.75");
    jTFInvestor_GaussDeviation.setBounds(new Rectangle(153, 322, 52, 20));
    jTFInvestor_LinkBereichProzent.setToolTipText("Only Int value");
    jTFInvestor_LinkBereichProzent.setText("50");
    jTFInvestor_LinkBereichProzent.setBounds(new Rectangle(154, 347, 52, 20));
    jTFInvestor_MittBereichProzent.setToolTipText("Only Int value");
    jTFInvestor_MittBereichProzent.setText("35");
    jTFInvestor_MittBereichProzent.setBounds(new Rectangle(154, 369, 52, 20));
    jTFInvestor_RechtBereichProzent.setToolTipText("Only int value");
    jTFInvestor_RechtBereichProzent.setText("15");
    jTFInvestor_RechtBereichProzent.setBounds(new Rectangle(155, 393, 52, 20));
    jLabel419.setText("%");
    jLabel419.setBounds(new Rectangle(209, 346, 24, 25));
    jLabel4110.setText("%");
    jLabel4110.setBounds(new Rectangle(210, 369, 24, 25));
    jLabel4111.setText("%");
    jLabel4111.setBounds(new Rectangle(210, 391, 24, 25));
    jLabel111111.setBounds(new Rectangle(470, 117, 42, 19));
    jLabel111111.setText("Buy");
    jLabel111111.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel111112.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel111112.setText("Sell");
    jLabel111112.setBounds(new Rectangle(529, 118, 53, 19));
    jLabel111110.setBounds(new Rectangle(529, 103, 55, 19));
    jLabel111110.setText("Market");
    jLabel111110.setFont(new java.awt.Font("Dialog", 1, 12));
    jTFInvestorStufe1MarketOrderBestVerkauf.setBounds(new Rectangle(531, 142, 19, 19));
    jTFInvestorStufe4MarketOrderBestVerkauf.setBounds(new Rectangle(531, 218, 19, 19));
    jTFInvestorStufe3MarketOrderBestVerkauf.setBounds(new Rectangle(531, 194, 19, 19));
    jTFInvestorStufe2MarketOrderBestVerkauf.setBounds(new Rectangle(531, 169, 19, 19));
    jLabel111113.setBounds(new Rectangle(480, 85, 53, 19));
    jLabel111113.setText("Sell");
    jLabel111113.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel111114.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel111114.setText("Buy");
    jLabel111114.setBounds(new Rectangle(425, 84, 42, 19));
    jLabel111115.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel111115.setText("Market");
    jLabel111115.setBounds(new Rectangle(480, 70, 55, 19));
    jLabel111116.setBounds(new Rectangle(424, 69, 55, 19));
    jLabel111116.setText("Market");
    jLabel111116.setFont(new java.awt.Font("Dialog", 1, 12));
    jTFNoiseTraderStufe3MarketOrderBestVerkauf.setText("B");
    jTFNoiseTraderStufe3MarketOrderBestVerkauf.setBounds(new Rectangle(482, 154, 16, 19));
    jTFNoiseTraderStufe2MarketOrderBestVerkauf.setBounds(new Rectangle(482, 131, 16, 19));
    jTFNoiseTraderStufe1MarketOrderBestVerkauf.setBounds(new Rectangle(482, 105, 16, 19));
    jTFNoiseTraderStufe4MarketOrderBestVerkauf.setText("B");
    jTFNoiseTraderStufe4MarketOrderBestVerkauf.setBounds(new Rectangle(482, 179, 16, 19));
    jLB_Investor_MINDepot1_Name1.setFont(new java.awt.Font("Dialog", 1, 12));
    jLB_Investor_MINDepot1_Name1.setText("Individual inner value");
    jLB_Investor_MINDepot1_Name1.setBounds(new Rectangle(224, 3, 138, 22));
    jLB_Investor_MINDepot1_Name1.setHorizontalAlignment(SwingConstants.LEFT);
    jLB_Investor_MAXDepot1_Name1.setBounds(new Rectangle(224, 25, 108, 18));
    jLB_Investor_MAXDepot1_Name1.setText("Lower Bound");
    jLB_Investor_MAXDepot1_Name1.setFont(new java.awt.Font("Dialog", 1, 12));
    jLB_Investor_MAXDepot1_Name2.setFont(new java.awt.Font("Dialog", 1, 12));
    jLB_Investor_MAXDepot1_Name2.setText("Upper Bound");
    jLB_Investor_MAXDepot1_Name2.setBounds(new Rectangle(224, 46, 84, 18));
    jLB_Investor_MAXDepot1_Name3.setBounds(new Rectangle(224, 69, 127, 18));
    jLB_Investor_MAXDepot1_Name3.setText("Activation Threshold");
    jLB_Investor_MAXDepot1_Name3.setFont(new java.awt.Font("Dialog", 1, 12));
    jLB_Investor_MAXDepot1_Name4.setFont(new java.awt.Font("Dialog", 1, 12));
    jLB_Investor_MAXDepot1_Name4.setText("Percentage of Inner Value");
    jLB_Investor_MAXDepot1_Name4.setBounds(new Rectangle(224, 88, 164, 18));
    jLB_Investor_MAXDepot1_Name5.setFont(new java.awt.Font("Dialog", 1, 12));
    jLB_Investor_MAXDepot1_Name5.setText("Profit Window");
    jLB_Investor_MAXDepot1_Name5.setBounds(new Rectangle(223, 108, 91, 18));
    jTFInvestor_InnererWert_AbweichungInterval_Untergrenz.setBounds(new Rectangle(379, 28, 47, 17));
    jTFInvestor_InnererWert_AbweichungInterval_Untergrenz.setText("-5.0");
    jLabel4112.setBounds(new Rectangle(429, 28, 24, 16));
    jLabel4112.setText("%");
    jTFInvestor_InnererWert_AbweichungInterval_Obengrenz1.setText("+5.0");
    jTFInvestor_InnererWert_AbweichungInterval_Obengrenz1.setBounds(new Rectangle(379, 48, 47, 19));
    jLabel4113.setText("%");
    jLabel4113.setBounds(new Rectangle(429, 51, 24, 16));
    jTFInvestor_KursAnderung_schwelle.setText("4.0");
    jTFInvestor_KursAnderung_schwelle.setBounds(new Rectangle(379, 70, 47, 16));
    jLabel4114.setText("%");
    jLabel4114.setBounds(new Rectangle(429, 71, 24, 16));
    jTFInvestor_Potenzial_Aktueller_InnererWert.setBounds(new Rectangle(379, 91, 47, 16));
    jTFInvestor_Potenzial_Aktueller_InnererWert.setText("10.0");
    jLabel4115.setBounds(new Rectangle(429, 92, 24, 16));
    jLabel4115.setText("%");
    jTFInvestor_KursAnderung_RefenzTag.setText("5");
    jTFInvestor_KursAnderung_RefenzTag.setBounds(new Rectangle(379, 109, 46, 15));
    jRBDataSaveFormatGerman.setSelected(true);
    jRBDataSaveFormatGerman.setText("German");
    jRBDataSaveFormatGerman.setBounds(new Rectangle(289, 376, 79, 22));
    jRBDataSaveFormatEnglish.setBounds(new Rectangle(370, 376, 79, 22));
    jRBDataSaveFormatEnglish.setText("English");

    jBAccept.setText("accept");
    jBAccept.addActionListener(new RunParameterConfig_jBAccept_actionAdapter(this));

    jp_General.setToolTipText("");
    jp_General.setLayout(null);
    jp_Inverstor.setLayout(null);
    jp_Noisetrader.setLayout(null);
    jp_Randomtrader.setLayout(null);

    jTFInnererWertMaximalTagAbweichnung.setText("3.0");
    jTFInnererWertMaximalTagAbweichnung.setBounds(new Rectangle(166, 103, 53, 21));
    jTFBeginInnererwert.setBounds(new Rectangle(166, 26, 53, 21));
    jTFBeginInnererwert.setText("1000");
    jTFMINInnererwert.setText("500");
    jTFMINInnererwert.setBounds(new Rectangle(166, 51, 53, 21));
    jLabel11.setText("%");
    jLabel11.setBounds(new Rectangle(225, 106, 19, 17));
    jTFMAXInnererwert.setBounds(new Rectangle(166, 76, 53, 21));
    jTFMAXInnererwert.setText("2000");
    jLabel10.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel10.setText("Daily Max Change");
    jLabel10.setBounds(new Rectangle(26, 104, 136, 21));
    jTFPeriod.setText("1500");
    jTFPeriod.setBounds(new Rectangle(166, 153, 53, 22));
    jLabel4.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel4.setText("Inner Value Start");
    jLabel4.setBounds(new Rectangle(26, 26, 124, 21));
    jLabel3.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel3.setText("Inner Value Minimum");
    jLabel3.setBounds(new Rectangle(26, 51, 125, 21));
    jLabel2.setBounds(new Rectangle(26, 76, 130, 21));
    jLabel2.setText("Inner Value  Maximum");
    jLabel2.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel1.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel1.setText("Days for full Cycle");
    jLabel1.setBounds(new Rectangle(26, 153, 119, 22));

    jLabel12.setBounds(new Rectangle(161, 284, 137, 20));
    jLabel12.setText("Minimal trade amount");
    jLabel12.setFont(new java.awt.Font("Dialog", 1, 12));

    jTFRandomTraderMaxMenge.setText("20");
    jTFRandomTraderMaxMenge.setBounds(new Rectangle(301, 313, 76, 22));
    jTFRandomTraderMinMenge.setBounds(new Rectangle(301, 282, 76, 22));
    jTFRandomTraderMinMenge.setText("3");

    jLabel13.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel13.setText("Maximal trade amount");
    jLabel13.setBounds(new Rectangle(161, 311, 137, 20));
    jTFGewinnRefernztag.setBounds(new Rectangle(148, 258, 40, 20));
    jTFGewinnRefernztag.setText("50");
    jLabel16.setBounds(new Rectangle(189, 312, 20, 17));
    jLabel16.setText("%");

    jLabel15.setBounds(new Rectangle(192, 258, 32, 20));
    jLabel15.setText("days");
    jLabel15.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel14.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel14.setText("Profit Horizon");
    jLabel14.setBounds(new Rectangle(24, 258, 117, 21));

    jTFHillEstimatorProzent.setText("5");
    jTFHillEstimatorProzent.setBounds(new Rectangle(148, 308, 40, 20));

    jLabel27.setBounds(new Rectangle(24, 308, 107, 21));
    jLabel27.setText("Hill-Estimator Tail");
    jLabel27.setFont(new java.awt.Font("Dialog", 1, 12));

    jLabel28.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel28.setText("Auto correlation");
    jLabel28.setBounds(new Rectangle(24, 332, 110, 21));

    jTFAnzahlAutoKorrelation.setBounds(new Rectangle(148, 332, 40, 21));
    jTFAnzahlAutoKorrelation.setText("50");

    jLabel119.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel119.setText("Order amount level 1:");
    jLabel119.setBounds(new Rectangle(223, 143, 127, 20));
    jLabel1110.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel1110.setText("Order amount level 2:");
    jLabel1110.setBounds(new Rectangle(223, 169, 127, 20));
    jLabel1111.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel1111.setText("Order amount level 3:");
    jLabel1111.setBounds(new Rectangle(223, 196, 127, 18));
    jLabel1112.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel1112.setText("Order amount level 4:");
    jLabel1112.setBounds(new Rectangle(223, 219, 127, 20));

    jTFInvestorOrderMengeStuf1.setBounds(new Rectangle(379, 145, 46, 18));
    jTFInvestorOrderMengeStuf1.setText("1");

    jTFInvestorOrderMengeStuf2.setBounds(new Rectangle(379, 170, 46, 17));
    jTFInvestorOrderMengeStuf2.setText("3");

    jTFInvestorOrderMengeStuf3.setBounds(new Rectangle(379, 194, 46, 17));
    jTFInvestorOrderMengeStuf3.setText("6");

    jTFInvestorOrderMengeStuf4.setBounds(new Rectangle(379, 218, 46, 19));
    jTFInvestorOrderMengeStuf4.setText("10");

    jLabel1113.setBounds(new Rectangle(225, 154, 129, 19));
    jLabel1113.setText("Order amount level 3:");
    jLabel1113.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel1114.setBounds(new Rectangle(225, 131, 129, 19));
    jLabel1114.setText("Order amount level 2:");
    jLabel1114.setFont(new java.awt.Font("Dialog", 1, 12));

    jTFNoiseTraderOrderMengeStuf4.setText("10");
    jTFNoiseTraderOrderMengeStuf4.setBounds(new Rectangle(357, 179, 55, 18));

    jLabel1115.setBounds(new Rectangle(225, 105, 129, 18));
    jLabel1115.setText("Order amount level 1:");
    jLabel1115.setFont(new java.awt.Font("Dialog", 1, 12));

    jTFNoiseTraderOrderMengeStuf3.setText("6");
    jTFNoiseTraderOrderMengeStuf3.setBounds(new Rectangle(357, 154, 55, 18));

    jTFNoiseTraderOrderMengeStuf2.setText("3");
    jTFNoiseTraderOrderMengeStuf2.setBounds(new Rectangle(356, 131, 55, 18));

    jTFNoiseTraderOrderMengeStuf1.setText("1");
    jTFNoiseTraderOrderMengeStuf1.setBounds(new Rectangle(355, 105, 55, 18));

    jLabel1116.setBounds(new Rectangle(225, 179, 129, 18));
    jLabel1116.setText("Order amount level 4:");
    jLabel1116.setFont(new java.awt.Font("Dialog", 1, 12));
    jLB_Investor_MINDepot1_Name.setBounds(new Rectangle(26, 17, 78, 22));
    jLB_Investor_MINDepot1_Name.setText("Min Cash");
    jLB_Investor_MINDepot1_Name.setFont(new java.awt.Font("Dialog", 1, 12));

    jTFInvestorInitCash_Min.setText("0");
    jTFInvestorInitCash_Min.setBounds(new Rectangle(131, 20, 73, 17));

    jLB_Investor_MINDepot2_Name.setFont(new java.awt.Font("Dialog", 1, 12));
    jLB_Investor_MINDepot2_Name.setText("Min Securities");
    jLB_Investor_MINDepot2_Name.setBounds(new Rectangle(26, 60, 97, 21));

    jTFInvestorInitAktien_Min.setBounds(new Rectangle(131, 61, 73, 19));
    jTFInvestorInitAktien_Min.setText("0");

    jLB_Noisetrader_MINDepot2_Name.setBounds(new Rectangle(7, 42, 94, 23));
    jLB_Noisetrader_MINDepot2_Name.setText("Min Securities");
    jLB_Noisetrader_MINDepot2_Name.setFont(new java.awt.Font("Dialog", 1, 12));
    jLB_Noisetrader_MINDepot1_Name.setFont(new java.awt.Font("Dialog", 1, 12));
    jLB_Noisetrader_MINDepot1_Name.setText("Min Cash");
    jLB_Noisetrader_MINDepot1_Name.setBounds(new Rectangle(7, 9, 84, 31));

    jTFNoiseTraderInitCash_Min.setBounds(new Rectangle(131, 16, 67, 22));
    jTFNoiseTraderInitCash_Min.setText("0");

    jTFNoiseTraderInitAktien_Min.setText("0");
    jTFNoiseTraderInitAktien_Min.setBounds(new Rectangle(131, 44, 67, 22));

    jTFRandomTraderInitAktien.setText("0");
    jTFRandomTraderInitAktien.setBounds(new Rectangle(301, 48, 76, 22));

    jLB_Randomtrader_Depot2_Name.setBounds(new Rectangle(179, 50, 116, 20));
    jLB_Randomtrader_Depot2_Name.setText("Securities");
    jLB_Randomtrader_Depot2_Name.setFont(new java.awt.Font("Dialog", 1, 12));
    jLB_Randomtrader_Depot1_Name.setFont(new java.awt.Font("Dialog", 1, 12));
    jLB_Randomtrader_Depot1_Name.setText("Cash");
    jLB_Randomtrader_Depot1_Name.setBounds(new Rectangle(183, 18, 111, 20));
    jTFRandomTraderInitCash.setBounds(new Rectangle(301, 17, 76, 22));
    jTFRandomTraderInitCash.setText("0");
    jTFRandomTraderSellProb.setBounds(new Rectangle(301, 167, 76, 21));
    jTFRandomTraderSellProb.setText("50");
    jLabel120.setBounds(new Rectangle(193, 167, 104, 20));
    jLabel120.setText("Sell probability");
    jLabel120.setFont(new java.awt.Font("Dialog", 1, 12));

    jTFRandomTraderBuyCheapest.setText("50");
    jTFRandomTraderBuyCheapest.setBounds(new Rectangle(301, 139, 76, 19));

    jLabel121.setBounds(new Rectangle(180, 79, 114, 20));
    jLabel121.setText("Buy probability");
    jLabel121.setFont(new java.awt.Font("Dialog", 1, 12));
    jTFRandomTraderBuyProb.setBounds(new Rectangle(301, 79, 76, 21));
    jTFRandomTraderBuyProb.setText("50");
    jTFRandomTraderSellBest.setBounds(new Rectangle(301, 224, 76, 18));
    jTFRandomTraderSellBest.setText("50");
    jTFRandomTraderBuyIndexBased.setBounds(new Rectangle(301, 110, 76, 20));
    jTFRandomTraderBuyIndexBased.setText("50");
    jLabel29.setBounds(new Rectangle(245, 221, 51, 20));
    jLabel29.setText("best");
    jLabel29.setFont(new java.awt.Font("Dialog", 1, 12));
    jTFRandomTraderSellIndexBased.setBounds(new Rectangle(301, 197, 76, 18));
    jTFRandomTraderSellIndexBased.setText("50");

    jLabel210.setText("Cheapest");
    jLabel210.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel210.setBounds(new Rectangle(240, 136, 56, 20));
    jLabel211.setText("Index based");
    jLabel211.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel211.setBounds(new Rectangle(220, 192, 78, 20));
    jLabel212.setBounds(new Rectangle(218, 107, 78, 20));
    jLabel212.setText("Index based");
    jLabel212.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel122.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel122.setText("Wait probability");
    jLabel122.setBounds(new Rectangle(185, 252, 111, 20));
    jTFRandomTraderWaitProb.setText("0");
    jTFRandomTraderWaitProb.setBounds(new Rectangle(301, 251, 76, 21));

    jTFStatusExchangeProbability.setText(""+  Configurator.mConfData.mGewinnStatusExchangeProbability );

    jTFStatusExchangeProbability.setText("10");

    jTFStatusExchangeProbability.setBounds(new Rectangle(219, 36, 33, 20) );

    jTFFixedMaxLostNumber.setText(""+  Configurator.mConfData.mFixedMaxLostNumber );

    jTFFixedMaxLostNumber.setText("2");
    jTFFixedMaxLostNumber.setBounds(new Rectangle(212, 77, 33, 20));

    jLabel125.setBounds(new Rectangle(26, 129, 123, 21));
    jLabel125.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel125.setText("Trading Days");
    jTFTradeDays.setBounds(new Rectangle(166, 129, 53, 21));


    jp_Network.setLayout(null);


    jBADDNetworkFile.setText("New");
    jBADDNetworkFile.addActionListener(new StoreDialogNetworkFile_jBADDNetworkFile_actionAdapter(this));


    jBSaveto.setText("save configuration to");
    jBSaveto.addActionListener(new RunParameterConfig_jBSaveto_actionAdapter(this));

    jBBacktoMain.setText("back to main menu");
    jBBacktoMain.addActionListener(new RunParameterConfig_jBBacktoMain_actionAdapter(this));

    jLabel214.setBounds(new Rectangle(26, 112, 99, 22));
    jLabel214.setText("Sleep Probability");
    jLabel214.setFont(new java.awt.Font("Dialog", 1, 12));
    jTF_Investor_SleepProcent.setText("0");
    jTF_Investor_SleepProcent.setBounds(new Rectangle(131, 113, 39, 19));
    jLabel215.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel215.setText("Sleep Probability");
    jLabel215.setBounds(new Rectangle(7, 67, 112, 31));
    jTF_NoiseTrader_SleepProcent.setBounds(new Rectangle(131, 70, 39, 23));
    jTF_NoiseTrader_SleepProcent.setText("0");

    jP_Logging.setLayout(null);
    jCB_LogAgentExchangeHistory.setText("Log Agent Exchange History");
    jCB_LogAgentExchangeHistory.setBounds(new Rectangle(30, 70, 196, 37));
    jCB_LogDailyTradeBook.setText("Log Daily Trade Book");
    jCB_LogDailyTradeBook.setBounds(new Rectangle(30, 28, 169, 39));
    jCB_LogAgentDailyDepot.setToolTipText("");
    jCB_LogAgentDailyDepot.setText("Log Agent Daily Depot");
    jCB_LogAgentDailyDepot.setBounds(new Rectangle(30, 110, 169, 37));
    jB_RemoveNetwork.setBounds(new Rectangle(666, 40, 84, 25));
    jB_RemoveNetwork.setText("Remove");
    jB_RemoveNetwork.addActionListener(new RunParameterConfig_jB_RemoveNetwork_actionAdapter(this));
    jB_DisplayGraphic.setBounds(new Rectangle(666, 71, 85, 25));
    jB_DisplayGraphic.setText("Graphic");
    jB_DisplayGraphic.addActionListener(new RunParameterConfig_jB_DisplayGraphic_actionAdapter(this));
    jScrollPane_NetworkFiles.setBounds(new Rectangle(15, 6, 639, 337));
    jEditorPane1.setText("jEditorPane1");
    jEditorPane1.setBounds(new Rectangle(0, 0, 6, 23));
    jLabel420.setText("Days");
    jLabel420.setBounds(new Rectangle(200, 269, 47, 18));
    jLabel421.setText("Days");
    jLabel421.setBounds(new Rectangle(201, 297, 39, 18));

    jRBDataSaveFormatGerman.setBounds(new Rectangle(31, 186, 79, 22));
    jRBDataSaveFormatGerman.setText("German");
    jRBDataSaveFormatGerman.setSelected(true);
    jRBDataSaveFormatEnglish.setText("English");
    jRBDataSaveFormatEnglish.setBounds(new Rectangle(126, 187, 79, 22));
    jLabel221.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel221.setText("Language of Summary Data Format ");
    jLabel221.setBounds(new Rectangle(30, 153, 212, 19));
    jLabel116.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel116.setText("days");
    jLabel116.setBounds(new Rectangle(191, 332, 32, 20));
    jButtonPanel.setBounds(new Rectangle(0, 489, 828, 29));
    jTabbedPane1.setBounds(new Rectangle(0, 0, 828, 489));
    jLB_Investor_MAXDepot2_Name1.setText("Max Securities");
    jLB_Investor_MAXDepot2_Name1.setBounds(new Rectangle(40, 100, 85, 17));
    jLB_Investor_MAXDepot2_Name1.setHorizontalAlignment(SwingConstants.LEFT);
    jLB_Investor_MAXDepot2_Name1.setFont(new java.awt.Font("Dialog", 1, 12));
    jLB_Investor_MINDepot2_Name1.setText("Min Securities");
    jLB_Investor_MINDepot2_Name1.setBounds(new Rectangle(39, 80, 81, 17));
    jLB_Investor_MINDepot2_Name1.setFont(new java.awt.Font("Dialog", 1, 12));
    jLB_Investor_MINDepot2_Name1.setHorizontalAlignment(SwingConstants.LEFT);
    jLB_Investor_MAXDepot1_Name6.setText("Min Cash");
    jLB_Investor_MAXDepot1_Name6.setBounds(new Rectangle(42, 35, 56, 17));
    jLB_Investor_MAXDepot1_Name6.setHorizontalAlignment(SwingConstants.LEFT);
    jLB_Investor_MAXDepot1_Name6.setFont(new java.awt.Font("Dialog", 1, 12));
    jLB_Investor_MINDepot1_Name2.setFont(new java.awt.Font("Dialog", 1, 12));
    jLB_Investor_MINDepot1_Name2.setText("Max Cash");
    jLB_Investor_MINDepot1_Name2.setBounds(new Rectangle(40, 55, 57, 17));
    jLB_Investor_MINDepot1_Name2.setHorizontalAlignment(SwingConstants.LEFT);
    jPBlanko.setLayout(null);
    jTFBlankoAgent_MaxCash.setText("0");
    jTFBlankoAgent_MaxCash.setBounds(new Rectangle(238, 54, 112, 20));
    jTFBlankoAgent_MinCash.setText("0");
    jTFBlankoAgent_MinCash.setBounds(new Rectangle(236, 31, 116, 20));
    jTFBlankoAgent_MinAktien.setText("0");
    jTFBlankoAgent_MinAktien.setBounds(new Rectangle(237, 78, 112, 20));
    jTFBlankoAgent_MaxAktien.setText("0");
    jTFBlankoAgent_MaxAktien.setBounds(new Rectangle(238, 100, 112, 20));
    jPBlanko.setToolTipText("");
    jLabel5.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel5.setToolTipText("when the current index is bigger than this PLUS index  + Average " +
    "Index of the Index Window, then BlankoAgent will be active ");
    jLabel5.setText("Min. Kurs-change-procent for activation %");
    jLabel5.setBounds(new Rectangle(4, 122, 246, 17));
    jTFBlankoAgentPlusIndexForActivation_Min.setToolTipText("when the current index is bigger than this PLUS index  + Average " +
    "Index of the Index Window, then BlankoAgent will be active ");
    jTFBlankoAgentPlusIndexForActivation_Min.setText("0");
    jTFBlankoAgentPlusIndexForActivation_Min.setBounds(new Rectangle(272, 123, 68, 20));
    jLabel8.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel8.setToolTipText("");
    jLabel8.setText("Min Sleep Days after deactivation:");
    jLabel8.setBounds(new Rectangle(26, 222, 227, 17));
    jTFBlankoAgentInactiveDays_Min.setText("0");
    jTFBlankoAgentInactiveDays_Min.setBounds(new Rectangle(271, 223, 47, 20));
    jLabel9.setBounds(new Rectangle(24, 308, 175, 17));
    jLabel9.setText("Minimal days of  Index window:");
    jLabel9.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel17.setBounds(new Rectangle(4, 170, 263, 17));
    jLabel17.setText("Min. Kurs-change-procent  for deactivation %");
    jLabel17.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel17.setToolTipText("when the current index is less than Average Index of the Index Window " +
    "- Minus Index, then BlankoAgent will be active ");
    jTFBlankoAgentMinusIndexForDeactivation_Min.setToolTipText("when the current index is less than Average Index of the Index Window " +
    "- Minus Index, then BlankoAgent will be active ");
    jTFBlankoAgentMinusIndexForDeactivation_Min.setText("0");
    jTFBlankoAgentMinusIndexForDeactivation_Min.setBounds(new Rectangle(270, 172, 71, 20));
    jTFBlankoAgentDayOfIndexWindow_Min.setText("0");
    jTFBlankoAgentDayOfIndexWindow_Min.setBounds(new Rectangle(271, 310, 47, 20));
    jLabel19.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel19.setText(" Sleep Procent");
    jLabel19.setBounds(new Rectangle(22, 363, 134, 17));
    jLabel20.setBounds(new Rectangle(26, 250, 229, 17));
    jLabel20.setText("Max Sleep days after deactivation:");
    jLabel20.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel32.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel32.setText("Maximal days of  Index window:");
    jLabel32.setBounds(new Rectangle(23, 335, 209, 17));
    jTFBlankoAgentInactiveDays_Max.setBounds(new Rectangle(271, 252, 47, 20));
    jTFBlankoAgentInactiveDays_Max.setText("0");
    jTFBlankoAgentDayOfIndexWindow_Max.setBounds(new Rectangle(271, 339, 47, 20));
    jTFBlankoAgentDayOfIndexWindow_Max.setText("0");
    jTFBlankoAgent_SleepProcent.setBounds(new Rectangle(271, 367, 47, 20));
    jTFBlankoAgent_SleepProcent.setText("0");
    jLabel33.setText("%");
    jLabel33.setBounds(new Rectangle(323, 368, 31, 19));
    jLabel34.setBounds(new Rectangle(342, 171, 25, 19));
    jLabel34.setText("%");
    jLabel36.setBounds(new Rectangle(343, 125, 25, 19));
    jLabel36.setText("%");
    jCBBlankoAgent_AppendCashAllowed.setBounds(new Rectangle(271, 392, 25, 23));
    jLabel37.setBounds(new Rectangle(25, 392, 177, 18));
    jLabel37.setText("Append cash allowed");
    jLabel37.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel38.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel38.setText("Mindest activa days before deactivation:");
    jLabel38.setBounds(new Rectangle(23, 280, 247, 17));
    jTFBlankoAgentIMinimalActivationDays.setText("0");
    jTFBlankoAgentIMinimalActivationDays.setBounds(new Rectangle(271, 280, 47, 20));
    jLabel39.setBounds(new Rectangle(3, 147, 268, 17));
    jLabel39.setText("Max. Kurs-change-procent for activation %");
    jLabel39.setToolTipText("when the current index is bigger than this PLUS index  + Average " +
    "Index of the Index Window, then BlankoAgent will be active ");
    jLabel39.setFont(new java.awt.Font("Dialog", 1, 12));
    jTFBlankoAgentPlusIndexForActivation_Max.setBounds(new Rectangle(272, 148, 68, 20));
    jTFBlankoAgentPlusIndexForActivation_Max.setText("0");
    jTFBlankoAgentPlusIndexForActivation_Max.setToolTipText("when the current index is bigger than this PLUS index  + Average " +
    "Index of the Index Window, then BlankoAgent will be active ");
    jTFBlankoAgentMinusIndexForDeactivation_Max.setBounds(new Rectangle(270, 197, 72, 20));
    jTFBlankoAgentMinusIndexForDeactivation_Max.setText("0");
    jTFBlankoAgentMinusIndexForDeactivation_Max.setToolTipText("when the current index is less than Average Index of the Index Window " +
    "- Minus Index, then BlankoAgent will be active ");
    jLabel117.setToolTipText("when the current index is less than Average Index of the Index Window " +
    "- Minus Index, then BlankoAgent will be active ");
    jLabel117.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel117.setText("Max. Kurs-change-procent  for deactivation %");
    jLabel117.setBounds(new Rectangle(4, 195, 257, 17));
    jLabel310.setText("%");
    jLabel310.setBounds(new Rectangle(344, 149, 25, 19));
    jLabel311.setText("%");
    jLabel311.setBounds(new Rectangle(344, 197, 25, 19));
    jLabel1118.setBounds(new Rectangle(276, 272, 194, 20));
    jLabel1118.setText("Inter-node affect -------------------");
    jLabel1118.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel1118.setHorizontalAlignment(SwingConstants.LEFT);
    jCB_InvestorAffectedByOtherNodes.setText("Affected by Trend");
    jCB_InvestorAffectedByOtherNodes.setBounds(new Rectangle(301, 293, 189, 27));
    jCB_NoiseTraderAffectedByOtherNodes.setText("Affected by Fundamental");
    jCB_NoiseTraderAffectedByOtherNodes.setBounds(new Rectangle(11, 355, 193, 27));
    jLabel11115.setHorizontalAlignment(SwingConstants.LEFT);
    jLabel11115.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel11115.setText("Inter-node affect -------------------");
    jLabel11115.setBounds(new Rectangle(6, 330, 194, 20));
    jLabel312.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel312.setText("Total Sell can be done in");
    jLabel312.setBounds(new Rectangle(23, 418, 177, 18));
    jTFBlankoAgent_DaysOfTotalSell.setText("10");
    jTFBlankoAgent_DaysOfTotalSell.setBounds(new Rectangle(267, 420, 60, 21));
    jLabel320.setBounds(new Rectangle(339, 421, 47, 19));
    jLabel320.setText("days");
    jTFBlankoAgent_AppendCash.setText("0");
    jTFBlankoAgent_AppendCash.setBounds(new Rectangle(424, 392, 118, 20));
    jLabel42.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel42.setText("Append Cash");
    jLabel42.setBounds(new Rectangle(315, 392, 104, 19));
    jp_General.add(jBSpeichernInnererWert, null);
    jp_General.add(jLabel1117, null);


    jp_General.add(jLabel24, null);
    jp_General.add(jLabel21, null);
    jp_General.add(jTFPlus, null);
    jp_General.add(jTFMinus, null);
    jp_General.add(jBInnererWertLaden, null);
    jp_General.add(jTFGewinnRefernztag, null);
    jp_General.add(jLabel15, null);
    jp_General.add(jTFAllowedAbweichungPreis2InnererWert, null);
    jp_General.add(jTFAnzahlAutoKorrelation, null);
    jp_General.add(jTFHillEstimatorProzent, null);
    jp_General.add(jLabel16, null);
    jp_General.add(jLabel110, null);
    jp_General.add(jTFBeginInnererwert, null);
    jp_General.add(jLabel4, null);
    jp_General.add(jTFMINInnererwert, null);
    jp_General.add(jLabel3, null);
    jp_General.add(jTFMAXInnererwert, null);
    jp_General.add(jTFInnererWertMaximalTagAbweichnung, null);
    jp_General.add(jLabel10, null);
    jp_General.add(jLabel125, null);
    jp_General.add(jTFTradeDays, null);
    jp_General.add(jTFPeriod, null);
    jp_General.add(jLabel1, null);
    jp_General.add(jLabel111, null);
    jp_General.add(jTFInnererWertMuster, null);
    jp_General.add(jBLoadInnererWertMuster, null);
    jp_General.add(jLabel2, null);
    jp_General.add(jLabel11, null);
    jp_General.add(jBInnererWertGeneration, null);
    jp_General.add(jLabel14, null);
    jp_General.add(jLabel18, null);
    jp_General.add(jLabel27, null);
    jp_General.add(jLabel28, null);
    jp_General.add(jLabel116, null);


    jp_Network.add(mBlackBoard, null);

    jp_Randomtrader.add(jTFRandomTraderInitCash, null);
    jp_Randomtrader.add(jLB_Randomtrader_Depot1_Name, null);
    jp_Randomtrader.add(jLabel121, null);
    jp_Randomtrader.add(jLabel13, null);
    jp_Randomtrader.add(jLabel112, null);
    jp_Randomtrader.add(jTFRandomTraderRandomSeed4Decision, null);
    jp_Randomtrader.add(jTFRandomTraderMaxMenge, null);
    jp_Randomtrader.add(jTFRandomTraderMinMenge, null);
    jp_Randomtrader.add(jTFRandomTraderWaitProb, null);
    jp_Randomtrader.add(jTFRandomTraderSellBest, null);
    jp_Randomtrader.add(jTFRandomTraderSellIndexBased, null);
    jp_Randomtrader.add(jTFRandomTraderSellProb, null);
    jp_Randomtrader.add(jTFRandomTraderBuyCheapest, null);
    jp_Randomtrader.add(jTFRandomTraderBuyIndexBased, null);
    jp_Randomtrader.add(jTFRandomTraderBuyProb, null);
    jp_Randomtrader.add(jTFRandomTraderInitAktien, null);
    jp_Randomtrader.add(jLabel2110, null);
    jp_Randomtrader.add(jLabel2112, null);
    jp_Randomtrader.add(jLabel2114, null);
    jp_Randomtrader.add(jLabel2111, null);
    jp_Randomtrader.add(jLB_Randomtrader_Depot2_Name, null);
    jp_Randomtrader.add(jLabel212, null);
    jp_Randomtrader.add(jLabel210, null);
    jp_Randomtrader.add(jLabel120, null);
    jp_Randomtrader.add(jLabel211, null);
    jp_Randomtrader.add(jLabel29, null);
    jp_Randomtrader.add(jLabel12, null);
    jp_Randomtrader.add(jLabel122, null);
    jp_Randomtrader.add(jLabel2113, null);
    jp_Randomtrader.add(jLabel216, null);
    jp_Randomtrader.add(jLabel219, null);
    jp_Randomtrader.add(jLabel113, null);
    jp_Randomtrader.add(jLabel114, null);
    jp_Randomtrader.add(jLabel115, null);

    jP_Tobintax.add(jLabel218, null);
    jP_Tobintax.add(jLabel217, null);
    jP_Tobintax.add(jTF_Tobintax_Days4AverageKurs, null);
    jP_Tobintax.add(jTF_Tobintax_TradeProzentOfDepot, null);
    jP_Tobintax.add(jLabel48, null);
    jP_Tobintax.add(jTF_Tobintax_Interventionsband, null);
    jP_Tobintax.add(jLabel46, null);
    jP_Tobintax.add(jLabel49, null);
    jP_Tobintax.add(jTF_CASH1NAME, null);
    jP_Tobintax.add(jLabel22, null);
    jP_Tobintax.add(jLabel23, null);
    jP_Tobintax.add(jTF_CASH2NAME, null);
    jP_Tobintax.add(jLabel47, null);
    jP_Tobintax.add(jRB_Tobintax_Inaktive, null);
    jP_Tobintax.add(jLabel2115, null);
    jP_Tobintax.add(jRB_Tobintax_Aktive, null);

    jP_Marketmode.add(jRB_AktienMarket, null);
    jP_Marketmode.add(jRB_MoneyMarket, null);

    jp_Inverstor.add(jTFInvestor_Abschlag_Min, null);
    jp_Inverstor.add(jTFInvestor_Abschlag_Max, null);
    jp_Inverstor.add(jLB_Investor_MINDepot1_Name, null);
    jp_Inverstor.add(jTFInvestorInitCash_Min, null);
    jp_Inverstor.add(jLB_Investor_MAXDepot1_Name, null);
    jp_Inverstor.add(jTFInvestorInitCash_Max, null);
    jp_Inverstor.add(jLB_Investor_MINDepot2_Name, null);
    jp_Inverstor.add(jTFInvestorInitAktien_Min, null);
    jp_Inverstor.add(jLabel214, null);
    jp_Inverstor.add(jTF_Investor_SleepProcent, null);
    jp_Inverstor.add(jLabel410, null);
    jp_Inverstor.add(jLB_Investor_MAXDepot2_Name, null);
    jp_Inverstor.add(jTFInvestorInitAktien_Max, null);
    jp_Inverstor.add(jTFInvestor_GaussMean, null);
    jp_Inverstor.add(jTFInvestor_GaussDeviation, null);
    jp_Inverstor.add(jLB_Investor_MAXDepot1_Name4, null);
    jp_Inverstor.add(jLB_Investor_MAXDepot1_Name3, null);
    jp_Inverstor.add(jLB_Investor_MINDepot1_Name1, null);
    jp_Inverstor.add(jLB_Investor_MAXDepot1_Name1, null);
    jp_Inverstor.add(jLB_Investor_MAXDepot1_Name2, null);
    jp_Inverstor.add(jTFInvestor_KursAnderung_schwelle, null);
    jp_Inverstor.add(jTFInvestor_InnererWert_AbweichungInterval_Untergrenz, null);
    jp_Inverstor.add(jTFInvestor_InnererWert_AbweichungInterval_Obengrenz1, null);
    jp_Inverstor.add(jTFInvestor_Potenzial_Aktueller_InnererWert, null);
    jp_Inverstor.add(jTFInvestor_KursAnderung_RefenzTag, null);
    jp_Inverstor.add(jTFInvestorKurschangeLimit1, null);
    jp_Inverstor.add(jLabel1120, null);
    jp_Inverstor.add(jLabel416, null);
    jp_Inverstor.add(jTFInvestorKurschangeLimit3, null);
    jp_Inverstor.add(jLabel418, null);
    jp_Inverstor.add(jLabel417, null);
    jp_Inverstor.add(jLabel4113, null);
    jp_Inverstor.add(jLabel4114, null);
    jp_Inverstor.add(jLabel4112, null);
    jp_Inverstor.add(jLabel4115, null);
    jp_Inverstor.add(jLabel35, null);
    jp_Inverstor.add(jLabel314, null);
    jp_Inverstor.add(jLabel313, null);
    jp_Inverstor.add(jLabel25, null);
    jp_Inverstor.add(jLabel315, null);
    jp_Inverstor.add(jLabel3113, null);
    jp_Inverstor.add(jLabel3112, null);
    jp_Inverstor.add(jTFInvestor_LinkBereichProzent, null);
    jp_Inverstor.add(jTFInvestor_MittBereichProzent, null);
    jp_Inverstor.add(jTFInvestor_RechtBereichProzent, null);
    jp_Inverstor.add(jLabel411, null);
    jp_Inverstor.add(jLabel419, null);
    jp_Inverstor.add(jLabel4110, null);
    jp_Inverstor.add(jLabel4111, null);
    jp_Inverstor.add(jTFInvestorOrderMengeStuf1, null);
    jp_Inverstor.add(jTFInvestorOrderMengeStuf2, null);
    jp_Inverstor.add(jTFInvestorOrderMengeStuf3, null);
    jp_Inverstor.add(jTFInvestorOrderMengeStuf4, null);
    jp_Inverstor.add(jLB_Investor_MAXDepot1_Name5, null);
    jp_Inverstor.add(jLabel119, null);
    jp_Inverstor.add(jLabel1110, null);
    jp_Inverstor.add(jLabel1111, null);
    jp_Inverstor.add(jLabel1112, null);
    jp_Inverstor.add(jLabel11119, null);
    jp_Inverstor.add(jTFInvestorStufe1MarketOrderBilligestKauf, null);
    jp_Inverstor.add(jTFInvestorStufe2MarketOrderBilligestKauf, null);
    jp_Inverstor.add(jTFInvestorStufe3MarketOrderBilligestKauf, null);
    jp_Inverstor.add(jTFInvestorStufe4MarketOrderBilligestKauf, null);
    jp_Inverstor.add(jTFInvestorStufe1MarketOrderBestVerkauf, null);
    jp_Inverstor.add(jTFInvestorStufe2MarketOrderBestVerkauf, null);
    jp_Inverstor.add(jTFInvestorStufe3MarketOrderBestVerkauf, null);
    jp_Inverstor.add(jTFInvestorStufe4MarketOrderBestVerkauf, null);
    jp_Inverstor.add(jLabel111111, null);
    jp_Inverstor.add(jLabel111110, null);
    jp_Inverstor.add(jLabel111112, null);
    jp_Inverstor.add(jEditorPane1, null);
    jp_Inverstor.add(jLabel1122, null);
    jp_Inverstor.add(jTFInvestorKurschangeLimit2, null);
    jp_Inverstor.add(jLabel1121, null);
    jp_Inverstor.add(jLabel412, null);
    jp_Inverstor.add(jBAbschlag_Generate, null);
    jp_Inverstor.add(jLabel1118, null);
    jp_Inverstor.add(jCB_InvestorAffectedByOtherNodes, null);

    jp_General.add( this.mInnererWertPanel );




    mAbschlagProzentPanel.setLocation( 310,260 );
    mAbschlagProzentPanel.setSize    ( 390,180 );

    jp_Inverstor.add( this.mAbschlagProzentPanel );

    mInnererWertPanel.setBounds( 389,5,395,238 );

    jp_Kommunikation.add(jLabel1211, null);
    jp_Kommunikation.add(jLabel26, null);
    jp_Kommunikation.add(jLabel30, null);
    jp_Kommunikation.add(jLabel31, null);
    jp_Kommunikation.add(jTFBaseDeviation4PriceLimit, null);
    jp_Kommunikation.add(jTFAbschlagFactor, null);
    jp_Kommunikation.add(jLabel213, null);
    jp_Kommunikation.add(jLabel41, null);
    jp_Kommunikation.add(jTFOrders4AverageLimit, null);
    jp_Kommunikation.add(jLabel40, null);
    jp_Kommunikation.add(jTFStatusExchangeProbability, null);
    jp_Kommunikation.add(jLabel7, null);
    jp_Kommunikation.add(jTFFixedMaxLostNumber, null);
    jp_Kommunikation.add(jTFLostNumberSeed, null);
    jp_Kommunikation.add(jRB_FixedMaxLostNumber, null);
    jp_Kommunikation.add(jRB_VariableMaxLostNumber, null);
    jp_Noisetrader.add(jTFNoiseTraderInitCash_Max, null);
    jp_Noisetrader.add(jTFNoiseTraderInitAktien_Max, null);

    this.jTabbedPane1.add(jp_General,  "General");
    this.jTabbedPane1.add(jp_Network,         "Network File");
    this.jTabbedPane1.add(jp_Kommunikation,   "Communication");
    this.jTabbedPane1.add(jp_Inverstor,       "Fundamental");
    this.jTabbedPane1.add(jp_Noisetrader,     "Trend");
    this.jTabbedPane1.add(jPBlanko,            "Retail");
    this.jTabbedPane1.add(jp_Randomtrader,    "Liquidity");
    this.jTabbedPane1.add(jP_Logging,         "Logging");
    this.jTabbedPane1.add(jP_Tobintax,        "Tobin Tax");
    this.jTabbedPane1.add(jP_Marketmode,      "Market Mode");
    this.jTabbedPane1.setBorder(BorderFactory.createLineBorder(Color.black));
    this.jTabbedPane1.setPreferredSize(new Dimension(8, 430));

    this.jButtonPanel.setLayout(gridLayout1 );
    this.jButtonPanel.setBorder(BorderFactory.createLineBorder(Color.black));
    this.jButtonPanel.add(jBBacktoMain, null);
    this.jButtonPanel.add(jBAccept, null);
    this.jButtonPanel.add(jBSaveto, null);

    this.getContentPane().setLayout(null );
    this.getContentPane().add(jTabbedPane1, null);
    this.getContentPane().add(jButtonPanel, null);

    this.jTabbedPane1.setSelectedIndex(0);
    AktienMoneyMarketSelectGroup.add(jRB_AktienMarket);
    AktienMoneyMarketSelectGroup.add(jRB_MoneyMarket);
    TobintaxAktiveSelectGroup.add(jRB_Tobintax_Aktive);
    TobintaxAktiveSelectGroup.add(jRB_Tobintax_Inaktive);
    jp_Noisetrader.add(mMovingdaysPanel);
    jp_Noisetrader.add(jLabel11114, null);
    jp_Noisetrader.add(jLabel11113, null);
    jp_Noisetrader.add(jLabel1119, null);
    buttonGroupMaxLostMode.add(jRB_FixedMaxLostNumber);
    buttonGroupMaxLostMode.add(jRB_VariableMaxLostNumber);
    jp_Noisetrader.add(jLabel111116, null);
    jp_Noisetrader.add(jLabel111114, null);
    jp_Noisetrader.add(jLabel111115, null);
    jp_Noisetrader.add(jLabel111113, null);
    jp_Noisetrader.add(jTFNoiseTraderStufe1MarketOrderBilligestKauf, null);
    jp_Noisetrader.add(jTFNoiseTraderStufe2MarketOrderBilligestKauf, null);
    jp_Noisetrader.add(jTFNoiseTraderStufe3MarketOrderBilligestKauf, null);
    jp_Noisetrader.add(jTFNoiseTraderStufe4MarketOrderBilligestKauf, null);
    jp_Noisetrader.add(jTFNoiseTraderStufe1MarketOrderBestVerkauf, null);
    jp_Noisetrader.add(jTFNoiseTraderStufe2MarketOrderBestVerkauf, null);
    jp_Noisetrader.add(jTFNoiseTraderStufe3MarketOrderBestVerkauf, null);
    jp_Noisetrader.add(jTFNoiseTraderStufe4MarketOrderBestVerkauf, null);
    jp_Noisetrader.add(jTFNoiseTraderOrderMengeStuf1, null);
    jp_Noisetrader.add(jTFNoiseTraderOrderMengeStuf2, null);
    jp_Noisetrader.add(jTFNoiseTraderOrderMengeStuf3, null);
    jp_Noisetrader.add(jTFNoiseTraderOrderMengeStuf4, null);
    jp_Noisetrader.add(jTFNoiseTraderKursChangedProcentLimit1, null);
    jp_Noisetrader.add(jLabel1114, null);
    jp_Noisetrader.add(jLabel1113, null);
    jp_Noisetrader.add(jLabel1116, null);
    jp_Noisetrader.add(jLabel413, null);
    jp_Noisetrader.add(jLabel414, null);
    jp_Noisetrader.add(jLabel415, null);
    jp_Noisetrader.add(jTFNoiseTraderInitCash_Min, null);
    jp_Noisetrader.add(jTFNoiseTraderInitAktien_Min, null);
    jp_Noisetrader.add(jTF_NoiseTrader_SleepProcent, null);
    jp_Noisetrader.add(jTFNoiseTraderKursChangedProcentLimit1, null);
    jp_Noisetrader.add(jTFNoiseTraderKursChangedProcentLimit2, null);
    jp_Noisetrader.add(jTFNoiseTraderKursChangedProcentLimit3, null);
    jp_Noisetrader.add(jLB_Noisetrader_MINDepot1_Name, null);
    jp_Noisetrader.add(jLB_Noisetrader_MINDepot2_Name, null);
    jp_Noisetrader.add(jLabel215, null);
    jp_Noisetrader.add(jLabel44, null);
    jp_Noisetrader.add(jLabel43, null);
    jp_Noisetrader.add(jLB_Noisetrader_MAXDepot1_Name, null);
    jp_Noisetrader.add(jLB_Noisetrader_MAXDepot2_Name, null);
    jp_Noisetrader.add(jLabel45, null);
    jp_Noisetrader.add(jB_MovingdaysGenerate, null);
    jp_Noisetrader.add(jLabel1115, null);
    jp_Noisetrader.add(jLabel319, null);
    jp_Noisetrader.add(jLabel317, null);
    jp_Noisetrader.add(jLabel316, null);
    jp_Noisetrader.add(jLabel318, null);
    jp_Noisetrader.add(jTFNoiseTrader_MinLimitAdjust, null);
    jp_Noisetrader.add(jTFNoiseTrader_MaxLimitAdjust, null);
    jp_Noisetrader.add(jTF_NoiseTrader_AveragePrice_VaringDays_Min, null);
    jp_Noisetrader.add(jTF_NoiseTrader_AveragePrice_VaringDays_Max, null);
    jp_Noisetrader.add(jLabel420, null);
    jp_Noisetrader.add(jLabel421, null);
    buttonGroupDataSaveFormat.add(jRBDataSaveFormatGerman);
    buttonGroupDataSaveFormat.add(jRBDataSaveFormatEnglish);
    jP_Logging.add(jCB_LogDailyTradeBook, null);
    jP_Logging.add(jCB_LogAgentExchangeHistory, null);
    jP_Logging.add(jCB_LogAgentDailyDepot, null);
    jP_Logging.add(jLabel221, null);
    jP_Logging.add(jRBDataSaveFormatEnglish, null);
    jP_Logging.add(jRBDataSaveFormatGerman, null);
    jp_Network.add(jBADDNetworkFile, null);
    jp_Network.add(jB_DisplayGraphic, null);
    //jp_Network.add(jCBCommonNode2TypeRandomDistributionMode, null);
    jp_Network.add(jB_RemoveNetwork, null);
    jp_Network.add(jScrollPane_NetworkFiles, null);
    jp_Network.add(jLabel6, null);
    jp_Network.add(jTFRepeatTimes, null);
    jScrollPane_NetworkFiles.getViewport().add(jTable_Networkfiles, null);
    jPBlanko.add(jLB_Investor_MAXDepot1_Name6, null);
    jPBlanko.add(jTFBlankoAgent_MinCash, null);
    jPBlanko.add(jTFBlankoAgent_MaxCash, null);
    jPBlanko.add(jLB_Investor_MINDepot1_Name2, null);
    jPBlanko.add(jTFBlankoAgent_MinAktien, null);
    jPBlanko.add(jLB_Investor_MINDepot2_Name1, null);
    jPBlanko.add(jTFBlankoAgent_MaxAktien, null);
    jPBlanko.add(jLB_Investor_MAXDepot2_Name1, null);
    jPBlanko.add(jLabel5, null);
    jPBlanko.add(jLabel39, null);
    jPBlanko.add(jTFBlankoAgentPlusIndexForActivation_Min, null);
    jPBlanko.add(jTFBlankoAgentPlusIndexForActivation_Max, null);
    jPBlanko.add(jLabel36, null);
    jPBlanko.add(jLabel310, null);
    jPBlanko.add(jLabel17, null);
    jPBlanko.add(jLabel117, null);
    jPBlanko.add(jTFBlankoAgentMinusIndexForDeactivation_Min, null);
    jPBlanko.add(jTFBlankoAgentMinusIndexForDeactivation_Max, null);
    jPBlanko.add(jLabel8, null);
    jPBlanko.add(jLabel20, null);
    jPBlanko.add(jLabel38, null);
    jPBlanko.add(jLabel9, null);
    jPBlanko.add(jLabel32, null);
    jPBlanko.add(jLabel19, null);
    jPBlanko.add(jLabel37, null);
    jPBlanko.add(jTFBlankoAgentInactiveDays_Min, null);
    jPBlanko.add(jTFBlankoAgentInactiveDays_Max, null);
    jPBlanko.add(jTFBlankoAgentIMinimalActivationDays, null);
    jPBlanko.add(jTFBlankoAgentDayOfIndexWindow_Min, null);
    jPBlanko.add(jTFBlankoAgentDayOfIndexWindow_Max, null);
    jPBlanko.add(jTFBlankoAgent_SleepProcent, null);
    jPBlanko.add(jCBBlankoAgent_AppendCashAllowed, null);
    jPBlanko.add(jLabel33, null);
    jPBlanko.add(jLabel311, null);
    jPBlanko.add(jLabel34, null);
    jPBlanko.add(jLabel312, null);
    jPBlanko.add(jTFBlankoAgent_DaysOfTotalSell, null);
    jPBlanko.add(jLabel320, null);
    jPBlanko.add(jTFBlankoAgent_AppendCash, null);
    jPBlanko.add(jLabel42, null);
    jp_Noisetrader.add(jCB_NoiseTraderAffectedByOtherNodes, null);
    jp_Noisetrader.add(jLabel11115, null);

  }

  public void checkTobintaxSetting()
  {
     if ( Configurator.mConfData.mTobintaxAgentAktive )
     {
         //this.jTF_Tobintax_FestSteuer.setEditable( true );
         //this.jTF_Tobintax_ExtraSteuer.setEditable( true );
         this.jTF_Tobintax_Days4AverageKurs.setEditable(true);
         this.jTF_Tobintax_TradeProzentOfDepot.setEditable( true);
     }
     else
     {
         //this.jTF_Tobintax_FestSteuer.setEditable( false );
         //this.jTF_Tobintax_ExtraSteuer.setEditable( false );
         this.jTF_Tobintax_Days4AverageKurs.setEditable(false);
         this.jTF_Tobintax_TradeProzentOfDepot.setEditable(false);
     }
  }

  // load parameter from ConfData into GUI Komponets
  public void loadParameterFromConfData()
  {
        /* Set Parameter Market Mode */
        if ( Configurator.mConfData.mMarketMode == Configurator.mConfData.mAktienMarket )
        {
            this.jRB_AktienMarket.setSelected( true );
            this.jRB_MoneyMarket.setSelected( false );
        }
        else
        {
            this.jRB_AktienMarket.setSelected( false );
            this.jRB_MoneyMarket.setSelected( true );
        }

        /* Set parameter in General Group */
        this.jTFTradeDays.setText("" + Configurator.mConfData.mHandelsday );
        this.jTFStatusExchangeProbability.setText("" + Configurator.mConfData.mGewinnStatusExchangeProbability );
        this.jTFFixedMaxLostNumber.setText("" + Configurator.mConfData.mFixedMaxLostNumber );
        this.jTFPeriod.setText("" + Configurator.mConfData.mDaysOfOnePeriod );
        this.jTFMAXInnererwert.setText("" + Configurator.mConfData.mMaxInnererWert );
        this.jTFMINInnererwert.setText("" +Configurator.mConfData.mMinInnererWert );
        this.jTFBeginInnererwert.setText("" + Configurator.mConfData.mBeginInnererWert );
        this.jTFInnererWertMaximalTagAbweichnung.setText("" + Configurator.mConfData.mInnererwertMaximalTagAbweichnung );

        this.jTFInnererWertMuster.setText( Configurator.mConfData.mInnererWertMuster  );

        this.jTFGewinnRefernztag.setText("" + Configurator.mConfData.mAheadDaysForGewinnCalculation );
        this.jTFHillEstimatorProzent.setText("" + Configurator.mConfData.mHillEstimatorProcent );
        this.jTFAnzahlAutoKorrelation.setText("" + Configurator.mConfData.mAnzahlAutoKorrelation );
        this.jTFGewinnRefernztag.setText( "" + Configurator.mConfData.mAheadDaysForGewinnCalculation);

        /***** Some Radio Button *****/
        this.jTFAllowedAbweichungPreis2InnererWert.setText( ""+Configurator.mConfData.mAllowerAbweichungPreis2InnererWert );

        if ( Configurator.mConfData.mDataFormatLanguage.equalsIgnoreCase( SystemConstant.DataFormatLanguage_German ) )
        {
          this.jRBDataSaveFormatGerman.setSelected( true );
          this.jRBDataSaveFormatEnglish.setSelected( false );
        }
        else
        {
          this.jRBDataSaveFormatGerman.setSelected( true );
          this.jRBDataSaveFormatEnglish.setSelected( false );
        }

        /*** load Parameter in Network and Node Mapping Group ***/

        this.jTFRepeatTimes.setText(  ""+ Configurator.mConfData.mRepeatTimes);

       /**
        * Load Network File into NetworkFile_TableModell
        */
        // remove all old daten from the Model
        this.mNetworkFileTableModel.removeAllOldDaten();

        // add new network files
        for ( int i=0; i<Configurator.mNetworkConfigManager.getSize();i++)
        {
             NetworkConfig onenetworkconfig = Configurator.mNetworkConfigManager.getNetworkConfig(i);
             this.mNetworkFileTableModel
                 .addNewNetworkFile( onenetworkconfig.mNetworkFileName,
                                     onenetworkconfig.mNodesInNetwork,
                                     onenetworkconfig.mInvestorNumber,
                                     onenetworkconfig.mNoiseTraderNumber,
                                     onenetworkconfig.mBlankoAgentNumber,
                                     onenetworkconfig.mRandomTraderNumber,
                                     onenetworkconfig.mNetworkFileLoader
                                    );
        }

        /* Set Parameter in Kommunikation Group */

        this.jTFStatusExchangeProbability.setText(""+ Configurator.mConfData.mGewinnStatusExchangeProbability);

        if (  Configurator.mConfData.mMaxLostNumberMode.equalsIgnoreCase("fixed") )
        {
             this.jRB_FixedMaxLostNumber.setSelected( true);
             this.jRB_VariableMaxLostNumber.setSelected( false );
        }
        else
        {
             this.jRB_FixedMaxLostNumber.setSelected( false );
             this.jRB_VariableMaxLostNumber.setSelected( true );
        }

        this.jTFLostNumberSeed.setText(""+ Configurator.mConfData.mMaxLostNumberSeed);
        this.jTFBaseDeviation4PriceLimit.setText(""+Configurator.mConfData.mBaseDeviation4PriceLimit);
        this.jTFAbschlagFactor.setText(""+Configurator.mConfData.mAbschlagFactor);
        this.jTFOrders4AverageLimit.setText(""+Configurator.mConfData.mOrders4AverageLimit);

        /* Set Parameter in Investor Group */

        this.jTFInvestor_Abschlag_Min.setText( Configurator.mConfData.mInvestor_DynamischAbschlageProzent_Min+"" );
        this.jTFInvestor_Abschlag_Max.setText( Configurator.mConfData.mInvestor_DynamischAbschlageProzent_Max+"" );

        this.jTFInvestor_GaussMean          .setText( Configurator.mConfData.mInvestor_AbschlagGaussMean+"" );
        this.jTFInvestor_GaussDeviation     .setText( Configurator.mConfData.mInvestor_AbschlagGaussDeviation+"" );
        this.jTFInvestor_LinkBereichProzent .setText( Configurator.mConfData.mInvestor_AnzahlProzent_LinkBereich+"" );
        this.jTFInvestor_MittBereichProzent .setText( Configurator.mConfData.mInvestor_AnzahlProzent_MittBereich+"" );
        this.jTFInvestor_RechtBereichProzent.setText( Configurator.mConfData.mInvestor_AnzahlProzent_RechtBereich+"" );

        this.jTFInvestorInitCash_Min.setText("" + Configurator.mConfData.mInvestorInitCash_Min);
        this.jTFInvestorInitCash_Max.setText("" + Configurator.mConfData.mInvestorInitCash_Max);

        this.jTFInvestorInitAktien_Min.setText(""+ Configurator.mConfData.mInvestorInitAktien_Min);
        this.jTFInvestorInitAktien_Max.setText(""+ Configurator.mConfData.mInvestorInitAktien_Max);

        this.jTF_Investor_SleepProcent.setText(""+ Configurator.mConfData.mInvestorSleepProcent);

        this.jTFInvestor_InnererWert_AbweichungInterval_Obengrenz1.setText( Configurator.mConfData.mInvestor_InnererWertIntervall_Obengrenz+"" );
        this.jTFInvestor_InnererWert_AbweichungInterval_Untergrenz.setText( Configurator.mConfData.mInvestor_InnererWertIntervall_Untergrenz+"" );
        this.jTFInvestor_KursAnderung_schwelle.setText( Configurator.mConfData.mInvestor_KursAnderung_Schwelle+"" );
        this.jTFInvestor_Potenzial_Aktueller_InnererWert.setText( Configurator.mConfData.mInvestor_AktuellerInnererWert_Potenzial+"" );
        this.jTFInvestor_KursAnderung_RefenzTag.setText( Configurator.mConfData.mInvestor_KursAnderung_ReferenzTag+"" );

        this.jTFInvestorOrderMengeStuf1.setText(""  + Configurator.mConfData.mInvestorOrderMengeStufe1 );
        this.jTFInvestorOrderMengeStuf2.setText("" + Configurator.mConfData.mInvestorOrderMengeStufe2);
        this.jTFInvestorOrderMengeStuf3.setText("" + Configurator.mConfData.mInvestorOrderMengeStufe3);
        this.jTFInvestorOrderMengeStuf4.setText("" + Configurator.mConfData.mInvestorOrderMengeStufe4);

        this.jTFInvestorKurschangeLimit1.setText( "" + Configurator.mConfData.mInvestorKurschangedprocentlimit1 );
        this.jTFInvestorKurschangeLimit2.setText( "" + Configurator.mConfData.mInvestorKurschangedprocentlimit2 );
        this.jTFInvestorKurschangeLimit3.setText( "" + Configurator.mConfData.mInvestorKurschangedprocentlimit3 );

        this.jTFInvestorStufe1MarketOrderBilligestKauf.setText(""+ Configurator.mConfData.mInvestorStufe1MarketOrderBilligestKauf);
        this.jTFInvestorStufe2MarketOrderBilligestKauf.setText(""+ Configurator.mConfData.mInvestorStufe2MarketOrderBilligestKauf);
        this.jTFInvestorStufe3MarketOrderBilligestKauf.setText(""+ Configurator.mConfData.mInvestorStufe3MarketOrderBilligestKauf);
        this.jTFInvestorStufe4MarketOrderBilligestKauf.setText(""+ Configurator.mConfData.mInvestorStufe4MarketOrderBilligestKauf);

        this.jTFInvestorStufe1MarketOrderBestVerkauf.setText(""+ Configurator.mConfData.mInvestorStufe1MarketOrderBestVerkauf);
        this.jTFInvestorStufe2MarketOrderBestVerkauf.setText(""+ Configurator.mConfData.mInvestorStufe2MarketOrderBestVerkauf);
        this.jTFInvestorStufe3MarketOrderBestVerkauf.setText(""+ Configurator.mConfData.mInvestorStufe3MarketOrderBestVerkauf);
        this.jTFInvestorStufe4MarketOrderBestVerkauf.setText(""+ Configurator.mConfData.mInvestorStufe4MarketOrderBestVerkauf);

        if ( Configurator.mConfData.mInvestorAffectedByOtherNode )
        {
            this.jCB_InvestorAffectedByOtherNodes.setSelected(true);
        }
        else
        {
            this.jCB_InvestorAffectedByOtherNodes.setSelected( false );
        }

        /* load Parameter in NoiseTrader Group ***/
        //this.jTFNoiseTraderMinMenge.setText(""+ Configurator.mConfData.mNoiseTraderMinMenge);
        //this.jTFNoiseTraderMaxMenge.setText(""+ Configurator.mConfData.mNoiseTraderMaxMenge);
        this.jTFNoiseTraderInitCash_Min.setText(""+ Configurator.mConfData.mNoiseTraderInitCash_Min);
        this.jTFNoiseTraderInitCash_Max.setText(""+ Configurator.mConfData.mNoiseTraderInitCash_Max);

        this.jTFNoiseTraderInitAktien_Min.setText(""+Configurator.mConfData.mNoiseTraderInitAktien_Min);
        this.jTFNoiseTraderInitAktien_Max.setText(""+Configurator.mConfData.mNoiseTraderInitAktien_Max);

        this.jTF_NoiseTrader_SleepProcent.setText( "" + Configurator.mConfData.mNoiseTrader_SleepProcent );

        this.jTFNoiseTraderKursChangedProcentLimit1.setText( "" + Configurator.mConfData.mNoiseTraderKurschangedprocentlimit1 );
        this.jTFNoiseTraderKursChangedProcentLimit2.setText( "" + Configurator.mConfData.mNoiseTraderKurschangedprocentlimit2 );
        this.jTFNoiseTraderKursChangedProcentLimit3.setText( "" + Configurator.mConfData.mNoiseTraderKurschangedprocentlimit3 );

        this.jTFNoiseTraderOrderMengeStuf1.setText(""+ Configurator.mConfData.mNoiseTraderOrderMengeStufe1  );
        this.jTFNoiseTraderOrderMengeStuf2.setText(""+ Configurator.mConfData.mNoiseTraderOrderMengeStufe2);
        this.jTFNoiseTraderOrderMengeStuf3.setText(""+ Configurator.mConfData.mNoiseTraderOrderMengeStufe3);
        this.jTFNoiseTraderOrderMengeStuf4.setText(""+ Configurator.mConfData.mNoiseTraderOrderMengeStufe4);

        this.jTFNoiseTraderStufe1MarketOrderBilligestKauf.setText(""+ Configurator.mConfData.mNoiseTraderStufe1MarketOrderBilligestKauf);
        this.jTFNoiseTraderStufe2MarketOrderBilligestKauf.setText(""+ Configurator.mConfData.mNoiseTraderStufe2MarketOrderBilligestKauf);
        this.jTFNoiseTraderStufe3MarketOrderBilligestKauf.setText(""+ Configurator.mConfData.mNoiseTraderStufe3MarketOrderBilligestKauf);
        this.jTFNoiseTraderStufe4MarketOrderBilligestKauf.setText(""+ Configurator.mConfData.mNoiseTraderStufe4MarketOrderBilligestKauf);

        this.jTFNoiseTraderStufe1MarketOrderBestVerkauf.setText(""+ Configurator.mConfData.mNoiseTraderStufe1MarketOrderBestVerkauf);
        this.jTFNoiseTraderStufe2MarketOrderBestVerkauf.setText(""+ Configurator.mConfData.mNoiseTraderStufe2MarketOrderBestVerkauf);
        this.jTFNoiseTraderStufe3MarketOrderBestVerkauf.setText(""+ Configurator.mConfData.mNoiseTraderStufe3MarketOrderBestVerkauf);
        this.jTFNoiseTraderStufe4MarketOrderBestVerkauf.setText(""+ Configurator.mConfData.mNoiseTraderStufe4MarketOrderBestVerkauf);

        this.jTF_NoiseTrader_AveragePrice_VaringDays_Min.setText("" + Configurator.mConfData.mNoiseTrader_MinMovingDaysForAveragePrice );
        this.jTF_NoiseTrader_AveragePrice_VaringDays_Max.setText("" + Configurator.mConfData.mNoiseTrader_MaxMovingDaysForAveragePrice);

        this.jTFNoiseTrader_MinLimitAdjust.setText("" + Configurator.mConfData.mNoiseTrader_MinLimitAdjust );
        this.jTFNoiseTrader_MaxLimitAdjust.setText("" + Configurator.mConfData.mNoiseTrader_MaxLimitAdjust);

        if ( Configurator.mConfData.mNoiseTraderAffectedByOtherNode )
        {
            this.jCB_NoiseTraderAffectedByOtherNodes.setSelected( true );
        }
        else
        {
            this.jCB_NoiseTraderAffectedByOtherNodes.setSelected( false );
        }

        /*** load Parameter in BlankoAgent Group ***/

        this.jTFBlankoAgent_MinCash.setText  ( "" + Configurator.mConfData.mBlankoAgentInitCash_Min );
        this.jTFBlankoAgent_MaxCash.setText  ( "" + Configurator.mConfData.mBlankoAgentInitCash_Max );
        this.jTFBlankoAgent_MinAktien.setText( "" + Configurator.mConfData.mBlankoAgentInitAktien_Min );
        this.jTFBlankoAgent_MaxAktien.setText( "" + Configurator.mConfData.mBlankoAgentInitAktien_Max );

        this.jTFBlankoAgentDayOfIndexWindow_Min.setText( "" + Configurator.mConfData.mBlankoAgentDayOfIndexWindow_Min);
        this.jTFBlankoAgentDayOfIndexWindow_Max.setText( "" + Configurator.mConfData.mBlankoAgentDayOfIndexWindow_Max);

        this.jTFBlankoAgentIMinimalActivationDays.setText("" + Configurator.mConfData.mBlankoAgentMindestActiveDays );

        this.jTFBlankoAgentInactiveDays_Min.setText("" + Configurator.mConfData.mBlankoAgentInactiveDays_Min );
        this.jTFBlankoAgentInactiveDays_Max.setText("" + Configurator.mConfData.mBlankoAgentInactiveDays_Max );
        this.jTFBlankoAgent_SleepProcent   .setText("" + Configurator.mConfData.mBlankoAgent_SleepProcent );

        this.jTFBlankoAgentPlusIndexForActivation_Min.setText("" + Configurator.mConfData.mBlankoAgentPlusIndexProcentForActivation_Min);
        this.jTFBlankoAgentPlusIndexForActivation_Max.setText("" + Configurator.mConfData.mBlankoAgentPlusIndexProcentForActivation_Max);

        this.jTFBlankoAgentMinusIndexForDeactivation_Min.setText( "" + Configurator.mConfData.mBlankoAgentMinusIndexProcentForDeactivation_Min);
        this.jTFBlankoAgentMinusIndexForDeactivation_Max.setText( "" + Configurator.mConfData.mBlankoAgentMinusIndexProcentForDeactivation_Max);

        this.jCBBlankoAgent_AppendCashAllowed.setSelected( Configurator.mConfData.mBlankoAgent_CashAppendAllowed );



        this.jTFBlankoAgent_AppendCash.setText( Configurator.mConfData.mBlankoAgent_AppendCash+"" );


        this.jTFBlankoAgent_DaysOfTotalSell.setText( Configurator.mConfData.mBlankoAgent_DaysOfTotalSell +"" );

        /* load Parameter in RandomTrader Group ***/

        this.jTFRandomTraderInitCash.setText( Configurator.mConfData.mRandomTraderInitCash+""  );
        this.jTFRandomTraderInitAktien.setText( Configurator.mConfData.mRandomTraderInitAktien + "");
        this.jTFRandomTraderBuyProb.setText( Configurator.mConfData.mRandomTraderBuyProbability + "" );
        this.jTFRandomTraderBuyCheapest.setText( Configurator.mConfData.mRandomTraderBuyProbabilityCheapest + "" );
        this.jTFRandomTraderBuyIndexBased.setText( Configurator.mConfData.mRandomTraderBuyProbabilityIndexBased + "");

        this.jTFRandomTraderSellProb.setText( Configurator.mConfData.mRandomTraderSellProbability + "");
        this.jTFRandomTraderSellIndexBased.setText(Configurator.mConfData.mRandomTraderSellProbabilityIndexbased + "");
        this.jTFRandomTraderSellBest.setText( Configurator.mConfData.mRandomTraderSellProbabilityBest + "" );

        this.jTFRandomTraderWaitProb.setText( (100 - Configurator.mConfData.mRandomTraderBuyProbability
                                                   - Configurator.mConfData.mRandomTraderSellProbability ) +"" );
        this.jTFRandomTraderMinMenge.setText( Configurator.mConfData.mRandomTraderMinMenge +"" );
        this.jTFRandomTraderMaxMenge.setText( Configurator.mConfData.mRandomTraderMaxMenge +"");

        this.jTFRandomTraderRandomSeed4Decision.setText( Configurator.mConfData.mRandomTraderRandomSeed4Decision+"" );

        // load parameter  group of TobinTax Agent

        //this.jTF_Tobintax_FestSteuer.setText(""+Configurator.mConfData.mTobintax_FestSteuer);
        //this.jTF_Tobintax_ExtraSteuer.setText("" + Configurator.mConfData.mTobintax_ExtraSteuer);

        if ( Configurator.mConfData.mTobintaxAgentAktive )
        {
           this.jRB_Tobintax_Aktive.setSelected(true);
           this.jRB_Tobintax_Inaktive.setSelected( false);
        }
        else
        {
           this.jRB_Tobintax_Aktive.setSelected( false );
           this.jRB_Tobintax_Inaktive.setSelected( true );
        }

        this.jTF_Tobintax_Days4AverageKurs.setText("" + Configurator.mConfData.mTobintax_Days4AverageKurs) ;
        this.jTF_Tobintax_TradeProzentOfDepot.setText( ""+Configurator.mConfData.mTobintax_TradeProzent) ;
        this.jTF_Tobintax_Interventionsband.setText( "" + Configurator.mConfData.mTobintax_Interventionsband  );
        this.jTF_CASH1NAME.setText( Configurator.mConfData.mCash1_Name );
        this.jTF_CASH2NAME.setText( Configurator.mConfData.mCash2_Name );

        // load parametergroup Logging Control

        this.jCB_LogDailyTradeBook.setSelected      ( Configurator.mConfData.mLogDailyTradeBook );
        this.jCB_LogAgentDailyDepot.setSelected     ( Configurator.mConfData.mLogAgentDailyDepot );
        this.jCB_LogAgentExchangeHistory.setSelected( Configurator.mConfData.mLogAgentExchangeHistoy );

 }

  public boolean saveParameter2ConfData()
  {
          // check AktienMarket or MoneyMarket is selected.
          String ss;
          ButtonModel bm = this.AktienMoneyMarketSelectGroup.getSelection();
          {
              ss = bm.getActionCommand();
              if ( ss.equalsIgnoreCase("StockMarket") )
              {
                Configurator.mConfData.mMarketMode = Configurator.mConfData.mAktienMarket;
                System.out.println("set to StockMarket Mode");
              }
              else
              {
                Configurator.mConfData.mMarketMode = Configurator.mConfData.mMoneyMarket;
                System.out.println("set to MoneyMarket Mode");
              }
          }

          /* save Agent Anzahl into Common Node-->Agent Type Distributor */
          /* get Parameter in RandomTrader Group into Configurator */
          Configurator.mConfData.mRandomTraderInitCash = Integer.parseInt( this.jTFRandomTraderInitCash.getText().trim() );
          Configurator.mConfData.mRandomTraderInitAktien = Integer.parseInt( this.jTFRandomTraderInitAktien.getText().trim() );
          Configurator.mConfData.mRandomTraderBuyProbability = Integer.parseInt( this.jTFRandomTraderBuyProb.getText().trim() );
          Configurator.mConfData.mRandomTraderBuyProbabilityCheapest = Integer.parseInt( this.jTFRandomTraderBuyCheapest.getText().trim() );
          Configurator.mConfData.mRandomTraderBuyProbabilityIndexBased = Integer.parseInt(this.jTFRandomTraderBuyIndexBased.getText().trim() );

          Configurator.mConfData.mRandomTraderSellProbability = Integer.parseInt(this.jTFRandomTraderSellProb.getText().trim() );
          Configurator.mConfData.mRandomTraderSellProbabilityIndexbased = Integer.parseInt(this.jTFRandomTraderSellIndexBased.getText().trim() );
          Configurator.mConfData.mRandomTraderSellProbabilityBest = Integer.parseInt( this.jTFRandomTraderSellBest.getText().trim() );

          Configurator.mConfData.mRandomTraderWaitProbability = Integer.parseInt(this.jTFRandomTraderWaitProb.getText().trim() );
          Configurator.mConfData.mRandomTraderMinMenge = Integer.parseInt(this.jTFRandomTraderMinMenge.getText().trim() );
          Configurator.mConfData.mRandomTraderMaxMenge = Integer.parseInt(this.jTFRandomTraderMaxMenge.getText().trim() );

          Configurator.mConfData.mRandomTraderRandomSeed4Decision = Integer.parseInt( this.jTFRandomTraderRandomSeed4Decision.getText());

          /*** save parameters in Investor Group ***/

          Configurator.mConfData.mInvestor_DynamischAbschlageProzent_Min =
          Double.parseDouble( this.jTFInvestor_Abschlag_Min.getText());
          Configurator.mConfData.mInvestor_DynamischAbschlageProzent_Max =
          Double.parseDouble( this.jTFInvestor_Abschlag_Max.getText());

          Configurator.mConfData.mInvestor_AbschlagGaussMean =
          Double.parseDouble(this.jTFInvestor_GaussMean.getText() );

          Configurator.mConfData.mInvestor_AbschlagGaussDeviation =
          Double.parseDouble(this.jTFInvestor_GaussDeviation.getText() );

          Configurator.mConfData.mInvestor_AnzahlProzent_LinkBereich =
          Integer.parseInt( this.jTFInvestor_LinkBereichProzent.getText() );

          Configurator.mConfData.mInvestor_AnzahlProzent_MittBereich =
          Integer.parseInt( this.jTFInvestor_MittBereichProzent.getText() );

          Configurator.mConfData.mInvestor_AnzahlProzent_RechtBereich =
          Integer.parseInt( this.jTFInvestor_RechtBereichProzent.getText() );

          Configurator.mConfData.mInvestorInitCash_Min   =  Integer.parseInt( this.jTFInvestorInitCash_Min.getText() );
          Configurator.mConfData.mInvestorInitCash_Max   =  Integer.parseInt( this.jTFInvestorInitCash_Max.getText() );
          Configurator.mConfData.mInvestorInitAktien_Min =  Integer.parseInt( this.jTFInvestorInitAktien_Min.getText() );
          Configurator.mConfData.mInvestorInitAktien_Max =  Integer.parseInt( this.jTFInvestorInitAktien_Max.getText() );

          Configurator.mConfData.mInvestorSleepProcent = Integer.parseInt( this.jTF_Investor_SleepProcent.getText());

          Configurator.mConfData.mInvestor_InnererWertIntervall_Obengrenz  = Double.parseDouble( this.jTFInvestor_InnererWert_AbweichungInterval_Obengrenz1.getText());
          Configurator.mConfData.mInvestor_InnererWertIntervall_Untergrenz = Double.parseDouble( this.jTFInvestor_InnererWert_AbweichungInterval_Untergrenz.getText());
          Configurator.mConfData.mInvestor_KursAnderung_Schwelle           = Double.parseDouble( this.jTFInvestor_KursAnderung_schwelle.getText() );

          Configurator.mConfData.mInvestor_KursAnderung_ReferenzTag        = Integer.parseInt(   this.jTFInvestor_KursAnderung_RefenzTag.getText() );
          Configurator.mConfData.mInvestor_AktuellerInnererWert_Potenzial  = Double.parseDouble( this.jTFInvestor_Potenzial_Aktueller_InnererWert.getText() );

          Configurator.mConfData.mInvestorOrderMengeStufe1 = Integer.parseInt(  this.jTFInvestorOrderMengeStuf1.getText() );
          Configurator.mConfData.mInvestorOrderMengeStufe2 = Integer.parseInt( this.jTFInvestorOrderMengeStuf2.getText() );
          Configurator.mConfData.mInvestorOrderMengeStufe3 = Integer.parseInt( this.jTFInvestorOrderMengeStuf3.getText() );
          Configurator.mConfData.mInvestorOrderMengeStufe4 = Integer.parseInt( this.jTFInvestorOrderMengeStuf4.getText() );

          Configurator.mConfData.mInvestorKurschangedprocentlimit1 = Double.parseDouble( this.jTFInvestorKurschangeLimit1.getText() );
          Configurator.mConfData.mInvestorKurschangedprocentlimit2 = Double.parseDouble( this.jTFInvestorKurschangeLimit2.getText() );
          Configurator.mConfData.mInvestorKurschangedprocentlimit3 = Double.parseDouble( this.jTFInvestorKurschangeLimit3.getText() );

          ss = this.jTFInvestorStufe1MarketOrderBilligestKauf.getText();
          ss = HelpTool.TrimString(ss, ' ');
          if ( ss.length() == 0 )
          {
             ss = " ";
          }
          Configurator.mConfData.mInvestorStufe1MarketOrderBilligestKauf = ss.charAt(0);
          ss = this.jTFInvestorStufe1MarketOrderBestVerkauf.getText();
          ss = HelpTool.TrimString(ss, ' ');
          if ( ss.length() == 0 )
          {
             ss = " ";
          }
          Configurator.mConfData.mInvestorStufe1MarketOrderBestVerkauf = ss.charAt(0);

          ss = this.jTFInvestorStufe2MarketOrderBilligestKauf.getText();
          ss = HelpTool.TrimString(ss, ' ');
          if ( ss.length() == 0 )
          {
             ss = " ";
          }
          Configurator.mConfData.mInvestorStufe2MarketOrderBilligestKauf = ss.charAt(0);
          ss = this.jTFInvestorStufe2MarketOrderBestVerkauf.getText();
          ss = HelpTool.TrimString(ss, ' ');
          if ( ss.length() == 0 )
          {
             ss = " ";
          }
          Configurator.mConfData.mInvestorStufe2MarketOrderBestVerkauf = ss.charAt(0);

          ss = this.jTFInvestorStufe3MarketOrderBilligestKauf.getText();
          ss = HelpTool.TrimString(ss, ' ');
          if ( ss.length() == 0 )
          {
             ss = " ";
          }
          Configurator.mConfData.mInvestorStufe3MarketOrderBilligestKauf = ss.charAt(0);
          ss = this.jTFInvestorStufe3MarketOrderBestVerkauf.getText();
          ss = HelpTool.TrimString(ss, ' ');
          if ( ss.length() == 0 )
          {
             ss = " ";
          }
          Configurator.mConfData.mInvestorStufe3MarketOrderBestVerkauf = ss.charAt(0);

          ss = this.jTFInvestorStufe4MarketOrderBilligestKauf.getText();
          ss = HelpTool.TrimString(ss, ' ');
          if ( ss.length() == 0 )
          {
             ss = " ";
          }
          Configurator.mConfData.mInvestorStufe4MarketOrderBilligestKauf = ss.charAt(0);
          ss = this.jTFInvestorStufe4MarketOrderBestVerkauf.getText();
          ss = HelpTool.TrimString(ss, ' ');
          if ( ss.length() == 0 )
          {
             ss = " ";
          }
          Configurator.mConfData.mInvestorStufe4MarketOrderBestVerkauf = ss.charAt(0);

          Configurator.mConfData.setAnzahlTotalAgents( Configurator.mConfData.mAnzahlInvestor +
                                                       Configurator.mConfData.mAnzahlNoiseTrader +
                                                      Configurator.mConfData.mAnzahlRandomTrader );

          if ( this.jCB_InvestorAffectedByOtherNodes.isSelected() )
          {
               Configurator.mConfData.mInvestorAffectedByOtherNode = true;
          }
          else
          {
               Configurator.mConfData.mInvestorAffectedByOtherNode = false;
          }

          /***  save NoiseTrader Group ***/
          Configurator.mConfData.mNoiseTraderInitCash_Min = Integer.parseInt(this.jTFNoiseTraderInitCash_Min.getText() );
          Configurator.mConfData.mNoiseTraderInitCash_Max = Integer.parseInt(this.jTFNoiseTraderInitCash_Max.getText() );

          Configurator.mConfData.mNoiseTraderInitAktien_Min = Integer.parseInt(this.jTFNoiseTraderInitAktien_Min.getText() );
          Configurator.mConfData.mNoiseTraderInitAktien_Max = Integer.parseInt(this.jTFNoiseTraderInitAktien_Max.getText() );

          Configurator.mConfData.mNoiseTrader_SleepProcent =
              Integer.parseInt( this.jTF_NoiseTrader_SleepProcent.getText( ) );

          Configurator.mConfData.mNoiseTrader_MinMovingDaysForAveragePrice =
             Integer.parseInt( this.jTF_NoiseTrader_AveragePrice_VaringDays_Min.getText());
          Configurator.mConfData.mNoiseTrader_MaxMovingDaysForAveragePrice =
             Integer.parseInt( this.jTF_NoiseTrader_AveragePrice_VaringDays_Max.getText());

          Configurator.mConfData.mNoiseTraderKurschangedprocentlimit1 = Double.parseDouble( this.jTFNoiseTraderKursChangedProcentLimit1.getText() );
          Configurator.mConfData.mNoiseTraderKurschangedprocentlimit2 = Double.parseDouble( this.jTFNoiseTraderKursChangedProcentLimit2.getText() );
          Configurator.mConfData.mNoiseTraderKurschangedprocentlimit3 = Double.parseDouble( this.jTFNoiseTraderKursChangedProcentLimit3.getText() );

          Configurator.mConfData.mNoiseTraderOrderMengeStufe1 = Integer.parseInt( this.jTFNoiseTraderOrderMengeStuf1.getText() );
          Configurator.mConfData.mNoiseTraderOrderMengeStufe2 = Integer.parseInt(this.jTFNoiseTraderOrderMengeStuf2.getText() );
          Configurator.mConfData.mNoiseTraderOrderMengeStufe3 = Integer.parseInt(this.jTFNoiseTraderOrderMengeStuf3.getText() );
          Configurator.mConfData.mNoiseTraderOrderMengeStufe4 = Integer.parseInt(this.jTFNoiseTraderOrderMengeStuf4.getText() );

          Configurator.mConfData.mNoiseTrader_MinLimitAdjust =
             Double.parseDouble( this.jTFNoiseTrader_MinLimitAdjust.getText());
          Configurator.mConfData.mNoiseTrader_MaxLimitAdjust =
             Double.parseDouble( this.jTFNoiseTrader_MaxLimitAdjust.getText());

          ss = this.jTFNoiseTraderStufe1MarketOrderBilligestKauf.getText();
          ss = HelpTool.TrimString(ss, ' ');
          if ( ss.length() == 0 )
          {
             ss = " ";
          }
          Configurator.mConfData.mNoiseTraderStufe1MarketOrderBilligestKauf = ss.charAt(0);
          ss = this.jTFNoiseTraderStufe1MarketOrderBestVerkauf.getText();
          ss = HelpTool.TrimString(ss, ' ');
          if ( ss.length() == 0 )
          {
             ss = " ";
          }
          Configurator.mConfData.mNoiseTraderStufe1MarketOrderBestVerkauf = ss.charAt(0);

          ss = this.jTFNoiseTraderStufe2MarketOrderBilligestKauf.getText();
          ss = HelpTool.TrimString(ss, ' ');
          if ( ss.length() == 0 )
          {
             ss = " ";
          }
          Configurator.mConfData.mNoiseTraderStufe2MarketOrderBilligestKauf = ss.charAt(0);
          ss = this.jTFNoiseTraderStufe2MarketOrderBestVerkauf.getText();
          ss = HelpTool.TrimString(ss, ' ');
          if ( ss.length() == 0 )
          {
             ss = " ";
          }
          Configurator.mConfData.mNoiseTraderStufe2MarketOrderBestVerkauf = ss.charAt(0);

          ss = this.jTFNoiseTraderStufe3MarketOrderBilligestKauf.getText();
          ss = HelpTool.TrimString(ss, ' ');
          if ( ss.length() == 0 )
          {
             ss = " ";
          }
          Configurator.mConfData.mNoiseTraderStufe3MarketOrderBilligestKauf = ss.charAt(0);
          ss = this.jTFNoiseTraderStufe3MarketOrderBestVerkauf.getText();
          ss = HelpTool.TrimString(ss, ' ');
          if ( ss.length() == 0 )
          {
             ss = " ";
          }
          Configurator.mConfData.mNoiseTraderStufe3MarketOrderBestVerkauf = ss.charAt(0);

          ss = this.jTFNoiseTraderStufe4MarketOrderBilligestKauf.getText().trim();
          ss = HelpTool.TrimString(ss, ' ');
          if ( ss.length() == 0 )
          {
             ss = " ";
          }
          Configurator.mConfData.mNoiseTraderStufe4MarketOrderBilligestKauf = ss.charAt(0);
          ss = this.jTFNoiseTraderStufe4MarketOrderBestVerkauf.getText();
          ss = HelpTool.TrimString(ss, ' ');
          if ( ss.length() == 0 )
          {
             ss = " ";
          }
          Configurator.mConfData.mNoiseTraderStufe4MarketOrderBestVerkauf = ss.charAt(0);

          if ( this.jCB_NoiseTraderAffectedByOtherNodes.isSelected() )
          {
               Configurator.mConfData.mNoiseTraderAffectedByOtherNode = true;
          }
          else
          {
               Configurator.mConfData.mNoiseTraderAffectedByOtherNode = false;
          }

          /***  save BlankoAgent Group ***/

          Configurator.mConfData.mBlankoAgentInitCash_Min         = Integer.parseInt( this.jTFBlankoAgent_MinCash.getText() );
          Configurator.mConfData.mBlankoAgentInitCash_Max         = Integer.parseInt( this.jTFBlankoAgent_MaxCash.getText() );
          Configurator.mConfData.mBlankoAgentInitAktien_Min       = Integer.parseInt( this.jTFBlankoAgent_MinAktien.getText() );
          Configurator.mConfData.mBlankoAgentInitAktien_Max       = Integer.parseInt( this.jTFBlankoAgent_MaxAktien.getText() );

          Configurator.mConfData.mBlankoAgentDayOfIndexWindow_Min = Integer.parseInt( this.jTFBlankoAgentDayOfIndexWindow_Min.getText() );
          Configurator.mConfData.mBlankoAgentDayOfIndexWindow_Max = Integer.parseInt( this.jTFBlankoAgentDayOfIndexWindow_Max.getText() );

          Configurator.mConfData.mBlankoAgentMindestActiveDays = Integer.parseInt( this.jTFBlankoAgentIMinimalActivationDays.getText()  );

          Configurator.mConfData.mBlankoAgentInactiveDays_Min     = Integer.parseInt( this.jTFBlankoAgentInactiveDays_Min.getText());



          Configurator.mConfData.mBlankoAgentInactiveDays_Max     = Integer.parseInt( this.jTFBlankoAgentInactiveDays_Max.getText());
          Configurator.mConfData.mBlankoAgent_SleepProcent        = Integer.parseInt( this.jTFBlankoAgent_SleepProcent.getText());

          Configurator.mConfData.mBlankoAgentPlusIndexProcentForActivation_Min         = Double.parseDouble( this.jTFBlankoAgentPlusIndexForActivation_Min.getText() );
          Configurator.mConfData.mBlankoAgentPlusIndexProcentForActivation_Max         = Double.parseDouble( this.jTFBlankoAgentPlusIndexForActivation_Max.getText() );

          Configurator.mConfData.mBlankoAgentMinusIndexProcentForDeactivation_Min      = Double.parseDouble( this.jTFBlankoAgentMinusIndexForDeactivation_Min.getText());
          Configurator.mConfData.mBlankoAgentMinusIndexProcentForDeactivation_Max      = Double.parseDouble( this.jTFBlankoAgentMinusIndexForDeactivation_Max.getText());
          Configurator.mConfData.mBlankoAgent_CashAppendAllowed = this.jCBBlankoAgent_AppendCashAllowed.isSelected();
          Configurator.mConfData.mBlankoAgent_AppendCash = Integer.parseInt(this.jTFBlankoAgent_AppendCash.getText());
          Configurator.mConfData.mBlankoAgent_DaysOfTotalSell   = Integer.parseInt( this.jTFBlankoAgent_DaysOfTotalSell.getText() );

          /*** save parameters in Agent communication Group ***/

          Configurator.mConfData.mGewinnStatusExchangeProbability = Integer.parseInt( this.jTFStatusExchangeProbability.getText());

          Configurator.mConfData.mMaxLostNumberMode        = this.buttonGroupMaxLostMode.getSelection().getActionCommand();
          Configurator.mConfData.mFixedMaxLostNumber       = Integer.parseInt( this.jTFFixedMaxLostNumber.getText() );
          Configurator.mConfData.mMaxLostNumberSeed        = Integer.parseInt( this.jTFLostNumberSeed.getText() );
          Configurator.mConfData.mBaseDeviation4PriceLimit = Integer.parseInt( this.jTFBaseDeviation4PriceLimit.getText());
          Configurator.mConfData.mAbschlagFactor           = Integer.parseInt( this.jTFAbschlagFactor.getText());
          Configurator.mConfData.mOrders4AverageLimit      = Integer.parseInt( this.jTFOrders4AverageLimit.getText());

          /* save Parameters in General Group ***/

          Configurator.mConfData.mHandelsday =  Integer.parseInt( this.jTFTradeDays.getText() );
          Configurator.mConfData.mGewinnStatusExchangeProbability =  Integer.parseInt( this.jTFStatusExchangeProbability.getText() );

          Configurator.mConfData.mDaysOfOnePeriod = Integer.parseInt( this.jTFPeriod.getText() );
          Configurator.mConfData.mMaxInnererWert = Integer.parseInt( this.jTFMAXInnererwert.getText() );
          Configurator.mConfData.mMinInnererWert = Integer.parseInt(this.jTFMINInnererwert.getText() );
          Configurator.mConfData.mBeginInnererWert = Integer.parseInt(this.jTFBeginInnererwert.getText() );
          Configurator.mConfData.mInnererwertMaximalTagAbweichnung = Double.parseDouble(this.jTFInnererWertMaximalTagAbweichnung.getText() );
          Configurator.mConfData.mAheadDaysForGewinnCalculation = Integer.parseInt(this.jTFGewinnRefernztag.getText() );
          Configurator.mConfData.mInnererWertMuster             = this.jTFInnererWertMuster.getText();

          Configurator.mConfData.mAllowerAbweichungPreis2InnererWert = Integer.parseInt(this.jTFAllowedAbweichungPreis2InnererWert.getText() );

          this.jTFGewinnRefernztag.setText( "" + Configurator.mConfData.mAheadDaysForGewinnCalculation);

          Configurator.mConfData.mHillEstimatorProcent  = Double.parseDouble(this.jTFHillEstimatorProzent.getText() );
          Configurator.mConfData.mAnzahlAutoKorrelation = Integer.parseInt( this.jTFAnzahlAutoKorrelation.getText() );

          int jj = this.getInnererwertEntwicklungsTrend();
          Configurator.mConfData.mInnererwertEntwicklungsTrend = jj ;

          Configurator.mConfData.mRepeatTimes = Integer.parseInt( this.jTFRepeatTimes.getText() ) ;

          if ( this.jRBDataSaveFormatGerman.isSelected() )
          {
            Configurator.mConfData.mDataFormatLanguage = SystemConstant.DataFormatLanguage_German;
          }
          else
          {
            Configurator.mConfData.mDataFormatLanguage = SystemConstant.DataFormatLanguage_English;
          }

          /* save Tobintax Parameter  */

          // check if TobinTax is aktive
          bm = this.TobintaxAktiveSelectGroup.getSelection();
          ss = bm.getActionCommand();
          if ( ss.equalsIgnoreCase("active") )
          {
            Configurator.mConfData.mTobintaxAgentAktive = true;
          }
          else
          {
            Configurator.mConfData.mTobintaxAgentAktive = false;
          }

          // save parameter group Logging Control

          Configurator.mConfData.mLogDailyTradeBook      = this.jCB_LogDailyTradeBook.isSelected();
          Configurator.mConfData.mLogAgentDailyDepot     = this.jCB_LogAgentDailyDepot.isSelected();
          Configurator.mConfData.mLogAgentExchangeHistoy = this.jCB_LogAgentExchangeHistory.isSelected();

          // 2006-04-16


          Configurator.mConfData.mTobintax_Days4AverageKurs = Integer.parseInt( this.jTF_Tobintax_Days4AverageKurs.getText()  );
          Configurator.mConfData.mTobintax_TradeProzent  = Double.parseDouble( this.jTF_Tobintax_TradeProzentOfDepot.getText() );
          Configurator.mConfData.mTobintax_Interventionsband  = Double.parseDouble( this.jTF_Tobintax_Interventionsband.getText() );
          Configurator.mConfData.mCash1_Name = this.jTF_CASH1NAME.getText().trim();
          Configurator.mConfData.mCash2_Name = this.jTF_CASH2NAME.getText().trim();

          /* save Parameters in Network Group ***/

          // delete all existing networks
          Configurator.mNetworkConfigManager.reset();;

          String[] mAgentSimulatorList = AgentSimulatorManagerContainer.getAgentSimulatorManagerNames();

          // Get network files from NetworkFileTableModel
          NetworkFileTableRow     nft[]            = this.mNetworkFileTableModel.getAllNetworkFiles();
          NetworkFileLoader    fileloaderlist[]    = this.mNetworkFileTableModel.getNetworkFileLoaderList();

          System.out.println(  nft.length + " networks are defined.");
          // save the NetworkList from GUI into Configurator.mNetworkConfigList

          for ( int i=0; i< nft.length; i++)
          {
             NetworkConfig onenetworkconfig              =  new NetworkConfig(  nft[i].mNetworkFile );
             onenetworkconfig.mNetworkfilenameOhnePfad   =  removeDirectoryName ( nft[i].mNetworkFile );
             //System.out.println ("onenetworkconfig.mNetworkfilenameOhnePfad=" + onenetworkconfig.mNetworkfilenameOhnePfad);
             onenetworkconfig.mNetworkFileLoader         =  fileloaderlist[i];
             onenetworkconfig.mNodesInNetwork            =  nft[i].mNodes;
             onenetworkconfig.mInvestorNumber            =  nft[i].mFundamental;
             onenetworkconfig.mNoiseTraderNumber         =  nft[i].mTrend;
             onenetworkconfig.mBlankoAgentNumber         =  nft[i].mBlanko;
             onenetworkconfig.mRandomTraderNumber        =  nft[i].mRandomTrader;
             //onenetworkconfig.mFesteTobinTax           =  Double.parseDouble( onenetworkguiitem.mFestTobinTax.getText() );
             //onenetworkconfig.mExtraTobinTax           =  Double.parseDouble( onenetworkguiitem.mExtraTobinTax.getText() );
             // Umrechnen der Prozent Zahl
             Configurator.mNetworkConfigManager .addNewNetwork( onenetworkconfig );
             //System.out.println ( "one network config is saved into Configurator"  );
          }
          return true;
  }

  private String  removeDirectoryName (String pAbsoluteFileName)
  {
     String tt = pAbsoluteFileName;
     tt = tt.replace('\\', '/');
     int j = tt.indexOf("/");
     //String ss = tt;
     while ( j>=0 )
     {
        tt = tt.substring( j +1 );
        j = tt.indexOf("/");
     }
     return tt;
  }

  public int getAnzahlAutoKorrelation()
  {
     return Integer.parseInt( this.jTFAnzahlAutoKorrelation.getText() );
  }

  public void setAnzahlAutoKorrelation(int pAnzahl)
  {
     this.jTFAnzahlAutoKorrelation.setText( ""+pAnzahl ) ;
  }

  public void setConfigFile(String pConfigFile)
  {
     this.mConfigFile = pConfigFile;
  }

  public void setHandelsday(int pDays)
  {
     jTFTradeDays.setText("" + pDays );
  }

  /**
   * pTrend = 1; Aufsteigen
   *        = 0; Absteigen
   * @param pTrend
   */
 /*
  public void setAuf_Ab_Steigen( int pTrend  )
  {
    //Aufsteigen
    if ( pTrend == 1 )
    {
      buttonGroup1.setSelected( this.jRadioButtonAufsteigen.getModel(), true );
    }
    else
    {
      buttonGroup1.setSelected( this.jRadioButtonAbsteigen.getModel(), true );
    }
  }
*/
  /**
   * pStrategie = 1; NACHVERLUST
   *            = 2; zu Besten Gewinner
   * @param pStrategie
   */

/////////////////////////////////////////////////////////////////////
  /// setter Methode

  public void setDaysOfOnePeriod(int pDays)
  {
     this.jTFPeriod.setText( "" +pDays );
  }

  public void setMaxInnererWert(int pMaxInnererWert)
  {
     this.jTFMAXInnererwert.setText( "" + pMaxInnererWert );
  }

  public void setMinInnererWert(int pMinInnererWert)
  {
     this.jTFMINInnererwert.setText( "" +pMinInnererWert );
  }

  public void setBeginInnererWert(int pBeginInnererWert)
  {
     this.jTFBeginInnererwert.setText( "" +pBeginInnererWert );
  }

  public void setInnererWertMaximalTagAbweichnung(double pMaximalAbweichungProzent)
  {
     this.jTFInnererWertMaximalTagAbweichnung.setText( "" +pMaximalAbweichungProzent );
  }

  public void setMinimalOrderMengeofRandomTrader(int pMinimalMenge)
  {
     this.jTFRandomTraderMinMenge.setText( "" +pMinimalMenge );
  }

  public void setMaximalOrderMengeofRandomTrader(int pMaximalMenge)
  {
     this.jTFRandomTraderMaxMenge.setText( "" +pMaximalMenge );
  }

  public void setGewinnReferenztag(int pReferenztag)
  {
     this.jTFGewinnRefernztag.setText( "" +pReferenztag );
  }

  public void setHillEstimatorProzent(double pProzent)
  {
     this.jTFHillEstimatorProzent.setText( "" +pProzent );
  }

  /////////////////////////////////////////////////////////////////////
  /// Getter Methode

  public int getGewinnReferenztag()
  {
     return Integer.parseInt( this.jTFGewinnRefernztag.getText() );
  }

  public int getTageVonPeriod()
  {
     return Integer.parseInt( this.jTFPeriod.getText() );
  }

  public int getMaxInnererWert()
  {
     return Integer.parseInt( this.jTFMAXInnererwert.getText() );
  }

  public int getMinInnererWert()
  {
     return Integer.parseInt( this.jTFMINInnererwert.getText() );
  }

  public int getBeginInnererWert()
  {
     return Integer.parseInt( this.jTFBeginInnererwert.getText() );
  }

  public double getInnererWertMaximalTagAbweichnung()
  {
     return Double.parseDouble( this.jTFInnererWertMaximalTagAbweichnung.getText() );
  }

  /***************************/
  public int getMinimalOrderMengeofRandomTrader()
  {
     return Integer.parseInt(this.jTFRandomTraderMinMenge.getText());
  }

  public int getMaximalOrderMengeofRandomTrader()
  {
     return Integer.parseInt(this.jTFRandomTraderMaxMenge.getText());
  }

  /***************************/

  public int getInvestorOrderMengeStufe1()
  {
     return Integer.parseInt(this.jTFInvestorOrderMengeStuf1.getText());
  }

  public int getInvestorOrderMengeStufe2()
  {
     return Integer.parseInt(this.jTFInvestorOrderMengeStuf2.getText());
  }

  public int getInvestorOrderMengeStufe3()
  {
     return Integer.parseInt(this.jTFInvestorOrderMengeStuf3.getText());
  }

  public int getInvestorOrderMengeStufe4()
  {
     return Integer.parseInt(this.jTFInvestorOrderMengeStuf4.getText());
  }

  /*************************************/

  public int getNoiseTraderOrderMengeStufe1()
  {
     return Integer.parseInt(this.jTFNoiseTraderOrderMengeStuf1.getText());
  }

  public int getNoiseTraderOrderMengeStufe2()
  {
     return Integer.parseInt(this.jTFNoiseTraderOrderMengeStuf2.getText());
  }

  public int getNoiseTraderOrderMengeStufe3()
  {
     return Integer.parseInt(this.jTFNoiseTraderOrderMengeStuf3.getText());
  }

  public int getNoiseTraderOrderMengeStufe4()
  {
     return Integer.parseInt(this.jTFNoiseTraderOrderMengeStuf4.getText());
  }

  public int getInvestorInitCash()
  {
     return Integer.parseInt(this.jTFInvestorInitCash_Min.getText());
  }

  public int getInvestorInitAktien()
  {
     return Integer.parseInt(this.jTFInvestorInitAktien_Min.getText());
  }

  public int getNoiseTraderInitCash()
  {
     return Integer.parseInt(this.jTFNoiseTraderInitCash_Min.getText());
  }

  public int getNoiseTraderInitAktien()
  {
     return Integer.parseInt(this.jTFNoiseTraderInitAktien_Min.getText());
  }

  public int getRandomTraderInitCash()
  {
     return Integer.parseInt(this.jTFRandomTraderInitCash.getText());
  }

  public int getRandomTraderInitAktien()
  {
     return Integer.parseInt(this.jTFRandomTraderInitAktien.getText());
  }

  public int getRandomTraderBuyProb()
  {
     return Integer.parseInt(this.jTFRandomTraderBuyProb.getText());
  }

  public int getRandomTraderBuyProbIndexbased()
  {
     return Integer.parseInt(this.jTFRandomTraderBuyIndexBased.getText());
  }

  public int getRandomTraderBuyProbCheapest()
  {
     return Integer.parseInt(this.jTFRandomTraderBuyCheapest.getText());
  }

  public int getRandomTraderSellProb()
  {
     return Integer.parseInt(this.jTFRandomTraderSellProb.getText());
  }

  public int getRandomTraderSellProbIndexbased()
  {
     return Integer.parseInt(this.jTFRandomTraderSellIndexBased.getText());
  }

  public int getRandomTraderSellProbBest()
  {
     return Integer.parseInt(this.jTFRandomTraderSellBest.getText());
  }

  public int getRandomTraderWaitProb()
  {
     return Integer.parseInt(this.jTFRandomTraderWaitProb.getText());
  }



  // in Porzent: 5.0 means 5.0%, while using, has to be devided by 100
  public double getHillEstimatorProzent()
  {
     return Double.parseDouble( this.jTFHillEstimatorProzent.getText() );
  }

  public int getInnererwertEntwicklungsTrend()
  {
      ButtonModel bm = this.buttonGroup1.getSelection();
      // When nichts gewälht:
      if ( bm == null )
      {
        // Aufsteigen
        return 1;
      }

      String ss = bm.getActionCommand();
      if ( ss.equals("ABSTEIGEN") )
      {
         return 0;
      }
      else
      {
         return 1;
      }
  }

  public void processEvent( AWTEvent e )
  {
    // 201 is the code of Windows Closing
    if ( e.getID()== 201 )
    {
       e = null;
       // So this Event is killed hier
       // The event will not be forwarded further.
       // The window can not be closed by click on the Close Icon.
    }
    else
    {
       super.processEvent(e);
    }
  }

  private boolean ValidateParameterInGUI()
  {
    int p1,p2,p3,p4;
    p1 = Integer.parseInt( this.jTFBeginInnererwert.getText() );
    p2 = Integer.parseInt( this.jTFMAXInnererwert.getText() );
    p3 = Integer.parseInt( this.jTFMINInnererwert.getText() );
    if ( ( p1 >p2 ) || ( p1<p3 ))
    {
      JOptionPane.showMessageDialog(this,"Init of Inner Value is not valid. Please switch to General Parameter Panel and correct","Error", JOptionPane.ERROR_MESSAGE);
      return false;
    }

    p1 = Integer.parseInt( this.jTFInvestorOrderMengeStuf1.getText() );
    p2 = Integer.parseInt( this.jTFInvestorOrderMengeStuf2.getText() );
    p3 = Integer.parseInt( this.jTFInvestorOrderMengeStuf3.getText() );
    p4 = Integer.parseInt( this.jTFInvestorOrderMengeStuf4.getText() );

    if (  (p1 <= 0) || ( p2<=0)  || ( p3<=0 ) || ( p4<=0 ))
    {
      JOptionPane.showMessageDialog(this,"Fundamental Order Amount contains wrong value. Please switch to Fundamental Panel and correct it.","Error", JOptionPane.ERROR_MESSAGE);
      this.jTabbedPane1.setSelectedIndex(1);
      return false;
    }

    p1 = Integer.parseInt( this.jTFNoiseTraderOrderMengeStuf1.getText() );
    p2 = Integer.parseInt( this.jTFNoiseTraderOrderMengeStuf2.getText() );
    p3 = Integer.parseInt( this.jTFNoiseTraderOrderMengeStuf3.getText() );
    p4 = Integer.parseInt( this.jTFNoiseTraderOrderMengeStuf4.getText() );

    if (  (p1<=0) || (p2<=0)  || ( p3<=0 ) || ( p4<=0 ) )
    {
      JOptionPane.showMessageDialog(this, "Trend Order Amount contains wrong value. Please switch to Trend Panel and correct.","Error", JOptionPane.ERROR_MESSAGE);
      this.jTabbedPane1.setSelectedIndex(2);
      return false;
    }

    // weitere Überprüfung
    int m =  Integer.parseInt( this.jTFStatusExchangeProbability.getText());
    if ( m <= 0 )
    {
      JOptionPane.showMessageDialog(this,"Agent Status Exchange Interval <= 0. Please switch to Communication Panel and correct it.","Error", JOptionPane.ERROR_MESSAGE);
      return false;
    }

    if ( m <= 0 )
    {
      JOptionPane.showMessageDialog(this,"Die Anzahl des nacheinander Verlusts <= 0. Bitte zu Kommunikation Panel wechseln und korrigieren","Error", JOptionPane.ERROR_MESSAGE);
      return false;
    }

    int days = Integer.parseInt( this.jTFTradeDays.getText() );

    //  Get the Data from the NetwrokTableModel
    NetworkFileTableRow[]  allnetworkfiles= this.mNetworkFileTableModel.getAllNetworkFiles();

    // This Check will not be needed
    // Because Agent Number man can not be change.
    /*
    for ( int i=0; i< allnetworkfiles.length ; i++)
    {
      if (  allnetworkfiles[i].mFundamental + allnetworkfiles[i].mTrend != allnetworkfiles[i].mNodes )
      {
          JOptionPane.showMessageDialog(this, "Network: " + allnetworkfiles[i].mNetworkFile + "\r\nFundamental + Trend is not equal to Nodes. Please switch to Network Panel and correct it.","Error", JOptionPane.ERROR_MESSAGE);
          return false;
      }
    }
    */

    double mm1 = Double.parseDouble( this.jTFNoiseTrader_MinLimitAdjust.getText() );
    double mm2 = Double.parseDouble( this.jTFNoiseTrader_MaxLimitAdjust.getText() );
    if ( mm1 <0.0 || mm2<0.0)
    {
      JOptionPane.showMessageDialog(this, "Trend: Min and Max Kurs-Adjust Procent can not be a mius value.","Error", JOptionPane.ERROR_MESSAGE);
      return false;
    }

    if ( mm1 >mm2 )
    {
      JOptionPane.showMessageDialog(this, "Trend: Min Kurs-Adjust Procent can not be greater than Max Kurs-Adjust Procent.","Error", JOptionPane.ERROR_MESSAGE);
      return false;
    }

    return true;

  }

  void jBAccept_actionPerformed(ActionEvent e)
  {

    if ( ! ValidateParameterInGUI() )
    {
      return;
    }

    // save the Networklist from GUI into Configurator
    this.saveParameter2ConfData();
    mButtonAccept_pressed = true;
    this.setVisible(false);
    this.mMainFrame.setTitle( "FASM " + Configurator.mConfData.getCurrentMarketMode() );

  }

  void jBAgentDistributionGenerate_actionPerformed(ActionEvent e)
  {

    String agentsimulatornamelist[] = AgentSimulatorManagerContainer.getAgentSimulatorManagerNames();
    if ( agentsimulatornamelist.length == 0 )
    {
      JOptionPane.showMessageDialog(this,"No AgentSimulator is found, Distribution can not be performed. Please start AgentSimulator at first, then retry","Error", JOptionPane.ERROR_MESSAGE);
      return;
    }



  }

  void jBADDNetworkFile_actionPerformed(ActionEvent e)
  {

    String startdir         = FileTool.getCurrentAbsoluteDirectory() ;

    System.out.println("FASMHOME="+startdir);

    JFrame pp               = new JFrame();
    java.awt.FileDialog  fd = new FileDialog( pp );

     System.out.println(startdir+"\\network\\");

    fd.setDirectory( startdir+"\\network\\" );

    fd.setSize(200,200);
    fd.setVisible(true);
    String filename = fd.getFile();
    String dirname  = fd.getDirectory();

    String ss = dirname.substring(0, dirname.length()-1);

    String completefilename;
    if ( ss.equalsIgnoreCase( startdir ) )
    {
        completefilename = filename;
    }
    else
    {
        int p= dirname.indexOf( startdir );
        if ( p == 0 )
        {
           completefilename = dirname.substring( startdir.length() + 1  ) + filename ;
        }
        else
        {
           completefilename = dirname + filename;
        }
    }

    if ( filename == null )
    {
         // abbrechen, nichts zu tun
    }
    else
    {

        if ( ! FileChecker.FileExist( completefilename ) )
        {
            JOptionPane.showMessageDialog( null,"Error while loading the networkfile "+ completefilename,"Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        completefilename = completefilename.replace('\\','/');

        // System.out.println("after replace: " + completefilename );

        NetworkFileLoader  nfld = new NetworkFileLoader( completefilename );

        try
        {
            nfld.checkAgentNumber();
            nfld.processnetworkfile();
        }
        catch (Exception ex)
        {
          ex.printStackTrace();
          JOptionPane.showMessageDialog( null,"Error while checking networkfile "+ completefilename,"Error", JOptionPane.ERROR_MESSAGE);
          return;
        }


        if ( this.mNetworkFileTableModel.hasNetworkFileAlreadyExisted( completefilename ) )
        {
          JOptionPane.showMessageDialog( null,"networkfile "+ completefilename + " has aready existed in the list.","Error", JOptionPane.ERROR_MESSAGE);
          return;
        }

        this.mNetworkFileTableModel.addNewNetworkFile( completefilename,
                                                       nfld.getAgentTotal() ,
                                                       nfld.getFundamentalInvestor(),
                                                       nfld.getNoiseTrader(),
                                                       nfld.getBlankoAgent(),
                                                       2, // Default immer 2 RandomTrader for every networkfile
                                                       nfld );

    }
  }

  void jBBacktoMain_actionPerformed(ActionEvent e)
  {
      this.hide();
  }

  void jBSaveto_actionPerformed(ActionEvent e)
  {
      if ( ! this.ValidateParameterInGUI() )
      {
        return;
      }

      // Open a FileDialog for saving file
      JFrame pp = new JFrame();
      java.awt.FileDialog  fd = new java.awt.FileDialog( pp ,"Save current configuration to ",  java.awt.FileDialog.SAVE );

      if ( this.mConfigFile != null )
      {
         fd.setFile( this.mConfigFile );
      }
      else
      {
          fd.setDirectory( FileTool.getCurrentAbsoluteDirectory() + "\\config\\" );
          fd.setFile( "*.xml");
      }
      fd.setSize(200,200);
      fd.setVisible(true);

      String filename = fd.getFile();
      String dirname = fd.getDirectory();

      if ( filename == null )
      {
          System.out.println("Dialog is canceled.");
      }
      else
      {
          // save parameter in GUI to ConfData Object
          this.saveParameter2ConfData();

          // save all Parameter from Configurator 2 Xml File
          try
          {
             //Configurator.saveConfig2TextFile(dirname , filename);
             Configurator.saveConfig2XmlFile(dirname , filename);
             JOptionPane.showMessageDialog(this, "Configuration is successfully saved to " + dirname + filename + " .","Info", JOptionPane.INFORMATION_MESSAGE);
          }
          catch (Exception ex)
          {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,"Error while saving configuration to " +dirname + filename,"Error", JOptionPane.ERROR_MESSAGE);
          }
      }
  }

  void jBInnererWertGeneration_actionPerformed(ActionEvent e)
  {

    Configurator.mConfData.mBeginInnererWert = Integer.parseInt( this.jTFBeginInnererwert.getText() );
    Configurator.mConfData.mMaxInnererWert   = Integer.parseInt( this.jTFMAXInnererwert.getText()) ;
    Configurator.mConfData.mMinInnererWert   = Integer.parseInt( this.jTFMINInnererwert.getText()) ;

    Configurator.mConfData.mInnererwertMaximalTagAbweichnung =Double.parseDouble(this.jTFInnererWertMaximalTagAbweichnung.getText());
    Configurator.mConfData.mHandelsday       = Integer.parseInt(this.jTFTradeDays.getText());

    this.mRandomWalkgenerator = new InnererwertRandomWalkGenerator( Configurator.mConfData.mHandelsday,
                                                     Configurator.mConfData.mBeginInnererWert,
                                                     Configurator.mConfData.mMinInnererWert,
                                                     Configurator.mConfData.mMaxInnererWert,
                                                     Configurator.mConfData.mInnererwertMaximalTagAbweichnung,
                                                     2.5 );
    this.mRandomWalkgenerator.prepareInnererWert();

    Configurator.mConfData.mInit300     = this.mRandomWalkgenerator.getInit300();
    Configurator.mConfData.mInnererWert = this.mRandomWalkgenerator.getInnererWert();

    Vector dd = new Vector();

    // get all data including Init300
    double pp[] = this.mRandomWalkgenerator.getAllGeneratedData();
    for ( int i=0; i<pp.length; i++)
    {
      dd.add( new Integer( (int) pp[ i ] ) );
    }

    this.jTFPlus.setText(  this.mRandomWalkgenerator.getCC_OverInitWert() +"");
    this.jTFMinus.setText( this.mRandomWalkgenerator.getCC_UnderInitWert()+"");

    //this.mKurvFrame.setTrennWert( Configurator.mConfData.mBeginInnererWert );
    //this.mKurvFrame.setData( dd  );

    try
    {
      this.mInnererWertPanel.setDataType("Integer");
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    this.mInnererWertPanel.setXScaleNumber( dd.size() );
    this.mInnererWertPanel.setDrawForm(  this.mInnererWertPanel.DrawLine );
    this.mInnererWertPanel.setVisible(true);
    this.mInnererWertPanel.setData(dd );
    this.mInnererWertPanel.setDrawTrennLine( true );
    this.mInnererWertPanel.setYTitel("Inner Value" );
    this.mInnererWertPanel.setYTrennwert( Configurator.mConfData.mBeginInnererWert  );
    this.mInnererWertPanel.showChart();
    JOptionPane.showMessageDialog(this,"Achtung: Chart zeigt auch die ersten 300 InitWerte. Data in Chart sind 300 + Handelsday","Info", JOptionPane.INFORMATION_MESSAGE );
    return;

}

  void jB_MovingdaysGenerate_actionPerformed(ActionEvent e)
  {
    this.GenerateNewMovingdaysForNoiseTrader();
  }

  void jBCommonNode2TypeDistribute_actionPerformed(ActionEvent e)
  {
           // clear blackBoard
           this.mBlackBoard.setText("");

           int definedfiles = this.mNetworkFileTableModel.getRowCount();

           if ( definedfiles == 0 )
           {
              JOptionPane.showMessageDialog(this,"No Network is defined.","Error", JOptionPane.ERROR_MESSAGE);
              return;
           }

           // create Serial Network Node --> Agent Type Distribution
           ButtonModel selectedmode = this.ButtonGroupMappingMode.getSelection();
           String modename = selectedmode.getActionCommand();

  }

  private boolean IsRandomMappingSelected()
  {
      ButtonModel  bm = this.ButtonGroupMappingMode.getSelection();
      String ss = bm.getActionCommand();
      if ( ss.equalsIgnoreCase("random") )
      {
        return true;
      }
      else
      {
        return false;
      }
  }

  void jBAbschlag_Generate_actionPerformed(ActionEvent e)
  {
       this.GenerateNewAbschlagProzentForInvestor();
  }

  public void GenerateNewMovingdaysForNoiseTrader()
  {
           int     movingdays_min = Integer.parseInt( this.jTF_NoiseTrader_AveragePrice_VaringDays_Min.getText() );
           int     movingdays_max = Integer.parseInt( this.jTF_NoiseTrader_AveragePrice_VaringDays_Max.getText() );

           Configurator.mNetworkCommonParameterDatabase.setMovingdayMinMax(movingdays_min, movingdays_max);
           Configurator.mNetworkCommonParameterDatabase.generateNoiseTraderAgentMovingDay();

           try
           {
                this.mMovingdaysPanel.setDataType( "Integer" );
           }
           catch (Exception ex)
           {
             ex.printStackTrace();
           }

           this.mMovingdaysPanel.setDrawForm( this.mMovingdaysPanel.DrawPoint );

           Vector data = Configurator.mNetworkCommonParameterDatabase.getDatabaseMovingDays();

           this.mMovingdaysPanel.setMaxMinUpdatingRequired( false );
           this.mMovingdaysPanel.setDataMin( movingdays_min  );
           this.mMovingdaysPanel.setDataMax( movingdays_max );
           this.mMovingdaysPanel.setXScaleNumber( data.size() );
           this.mMovingdaysPanel.setData( data );
           this.mMovingdaysPanel.setVisible(true);
           this.mMovingdaysPanel.showChart();
  }

  public void GenerateNewAbschlagProzentForInvestor()
  {
        double abschlag_min = Double.parseDouble( this.jTFInvestor_Abschlag_Min.getText() );
        double abschlag_max = Double.parseDouble( this.jTFInvestor_Abschlag_Max.getText() );

        double gaussmean                 = Double.parseDouble( this.jTFInvestor_GaussMean.getText( ) ) ;
        double gaussdeviation            = Double.parseDouble( this.jTFInvestor_GaussDeviation.getText( ) ) ;
        int    anzahlprozent_linkbereich = Integer.parseInt( this.jTFInvestor_LinkBereichProzent.getText() );
        int    anzahlprozent_mittbereich = Integer.parseInt( this.jTFInvestor_MittBereichProzent.getText() );
        int    anzahlprozent_rechtbereich= Integer.parseInt( this.jTFInvestor_RechtBereichProzent.getText() );
        // create AbschlagProzent für Investor using Gauss Distribution

        FasmGaussConfig gsf   = new FasmGaussConfig();
        gsf.mAllowedMin       = abschlag_min;
        gsf.mAllowedMax       = abschlag_max;
        gsf.mMean             = gaussmean;
        gsf.mDeviation        = gaussdeviation;
        gsf.mDataPart1Prozent = anzahlprozent_linkbereich;
        gsf.mDataPart2Prozent = anzahlprozent_mittbereich;
        gsf.mDataPart3Prozent = anzahlprozent_rechtbereich;

        //Configurator.mCommonNode2TypeDistribution.generateInvestorAgentAbschlagProzentGauss( gsf );

        // Old: using random Distrition
        //  Configurator.mCommonNode2TypeDistribution.generateInvestorAgentAbschlagProzent( abschlag_min, abschlag_max );
        //System.out.println("GenerateNewAbschlagProzentForInvestor() is executed");
        try
        {
           this.mAbschlagProzentPanel.setDataType( "Double" );
        }
        catch (Exception ex)
        {
           ex.printStackTrace();
        }

        this.mAbschlagProzentPanel.setDrawForm( this.mAbschlagProzentPanel.DrawPoint );

        Vector data = Configurator.mNetworkCommonParameterDatabase.getDatabaseAbschlagProcent();
        this.mAbschlagProzentPanel.setXScaleNumber( data.size() );
        this.mAbschlagProzentPanel.setMaxMinUpdatingRequired( false );
        this.mAbschlagProzentPanel.setDataMin(  abschlag_min );
        this.mAbschlagProzentPanel.setDataMax(  abschlag_max );
        this.mAbschlagProzentPanel.setData( data );
        this.mAbschlagProzentPanel.setVisible(true);
        this.mAbschlagProzentPanel.showChart();
    }

  void jBSpeichernInnererWert_actionPerformed(ActionEvent e)
  {
    if ( ( this.mRandomWalkgenerator == null ) || ( ! this.mRandomWalkgenerator.mDataReady ) )
    {
      JOptionPane.showMessageDialog(this,"Inner Values are not ready. Please generate it.","Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    JFrame pp = new JFrame();
    java.awt.FileDialog  fd = new FileDialog( pp );
    fd.setMode( fd.SAVE );
    fd.setSize(200,200);
    fd.setVisible(true);
    String filename = fd.getFile();
    String dirname = fd.getDirectory();

    if ( filename == null )
    {
       // abbrechen, nichts zu tun
    }
    else
    {
        // Save generated InnererWert into a text file:
        double dd[] = this.mRandomWalkgenerator.getAllGeneratedData();
        try
        {
            java.io.PrintWriter pr = new  java.io.PrintWriter( new java.io.FileOutputStream(filename));
            for ( int i=0; i<dd.length; i++)
            {
                pr.println( i+"  " +  (int) dd[i] );
            }
            pr.close();
            JOptionPane.showMessageDialog(this,"InnererWert sind gespeichert unter " + filename ,"Info", JOptionPane.INFORMATION_MESSAGE);
            this.jTFInnererWertMuster.setText( filename );
        }
        catch (Exception ex)
        {
          ex.printStackTrace();
          JOptionPane.showMessageDialog(this,"Fehler bei Speichern ","Error", JOptionPane.ERROR_MESSAGE);
        }
    }

  }

  void jBInnererWertLaden_actionPerformed(ActionEvent e)
  {

    JFrame pp = new JFrame();
    java.awt.FileDialog  fd = new FileDialog( pp );
    fd.setSize(200,200);
    fd.setVisible(true);
    String filename = fd.getFile();
    String dirname = fd.getDirectory();

    if ( filename == null )
    {
       // abbrechen, nichts zu tun
    }
    else
    {
           // laden Existierte InnererWert Model File:
           String modelfile = dirname  + filename;
           modelfile     = modelfile.replace('\\','/');

           String curdir =  FileTool.getCurrentAbsoluteDirectory();

           // change the AbsolutPath to a RelativePath.

           // why "+1", because of "/", because getCurrentAbsoluteDirectory() returns
           // the absolute path, but at end of the path name, it does not contain "/"
           modelfile  = modelfile.substring( curdir.length() + 1 );

           //boolean fileisok = this.DisplayInnererWertDatei( modelfile ) ;
           this.jTFInnererWertMuster.setText( modelfile );
    }

  }

  private boolean DisplayInnererWertDatei(String pInnererWertDatei)
  {
    try
    {
        InnererWertModelFileReader reader = new InnererWertModelFileReader( pInnererWertDatei );
        JOptionPane.showMessageDialog(this, (300+reader.getDays() ) + " InnererWert (davon 300 für Init 300 ) gelesen. " ,"Info", JOptionPane.INFORMATION_MESSAGE);

        Configurator.mConfData.mInit300     = reader.getInit300IntValue();
        Configurator.mConfData.mInnererWert = reader.getInnererWertIntValue();

        Vector dd = reader.getAllIntData();

        try
        {
          this.mInnererWertPanel.setDataType("Integer");
        }
        catch (Exception ex)
        {
          ex.printStackTrace();
          return false;
        }

        this.mInnererWertPanel.setXScaleNumber( dd.size() );
        this.mInnererWertPanel.setDrawForm(  this.mInnererWertPanel.DrawLine );
        this.mInnererWertPanel.setVisible(true);
        this.mInnererWertPanel.setData(dd );
        this.mInnererWertPanel.setDrawTrennLine( true );
        this.mInnererWertPanel.setYTitel("Inner Value" );
        this.mInnererWertPanel.setYTrennwert( Configurator.mConfData.mBeginInnererWert  );
        this.mInnererWertPanel.showChart();
        return true;
    }
    catch (Exception ex)
    {
      JOptionPane.showMessageDialog(this,"Fehler beim Lesen von " + pInnererWertDatei, "Error", JOptionPane.ERROR_MESSAGE);
      return false;
    }
  }

  void jBLoadInnererWertMuster_actionPerformed(ActionEvent e)
  {
      this.DisplayInnererWertDatei( this.jTFInnererWertMuster.getText() );
  }

  void jRB_VariableMaxLostNumber_actionPerformed(ActionEvent e) {

  }

  void jB_RemoveNetwork_actionPerformed(ActionEvent e)
  {
      int j = this.jTable_Networkfiles.getSelectedRow();
      if ( j == -1 )
      {
        JOptionPane.showMessageDialog( this, "No netwrok is selected", "Error", JOptionPane.ERROR_MESSAGE, null);
        return;
      }
      // Delete this row
      this.mNetworkFileTableModel.removeRow( j );
  }

  void jB_DisplayGraphic_actionPerformed(ActionEvent e)
  {
    int j = this.jTable_Networkfiles.getSelectedRow();
    if ( j == -1 )
    {
      JOptionPane.showMessageDialog( this, "No network is selected", "Error", JOptionPane.ERROR_MESSAGE, null);
      return;
    }

    /// to change !!!
    String selectednetworkfile   = this.mNetworkFileTableModel.getNetworkFile( j );
    AgentGraphStatusFrame mgraph = new AgentGraphStatusFrame( selectednetworkfile );

    mgraph.setLocation( this.getLocation()  );

    // get Node-->AgentType Mapping  from NetworkFileLoader
    // Save this mapping into GraphDisplay Class
    System.out.println("get network node->Type mapping");
    mgraph.setAgentTypeList(  this.mNetworkFileTableModel.getNetworkFileLoader( j ).getAgentListWithType() );

    mgraph.setVisible(false);
    mgraph.setVisible(true);
    mgraph.toFront();

  }

}

class RunParameterConfig_jBAccept_actionAdapter implements java.awt.event.ActionListener {
  private RunParameterConfig adaptee;

  RunParameterConfig_jBAccept_actionAdapter(RunParameterConfig adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jBAccept_actionPerformed(e);
  }
}

class StoreDialogNetworkFile_jBADDNetworkFile_actionAdapter implements java.awt.event.ActionListener {
  private RunParameterConfig adaptee;

  StoreDialogNetworkFile_jBADDNetworkFile_actionAdapter(RunParameterConfig adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jBADDNetworkFile_actionPerformed(e);
  }
}

class RunParameterConfig_jBBacktoMain_actionAdapter implements java.awt.event.ActionListener {
  private RunParameterConfig adaptee;

  RunParameterConfig_jBBacktoMain_actionAdapter(RunParameterConfig adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jBBacktoMain_actionPerformed(e);
  }
}

class RunParameterConfig_jBSaveto_actionAdapter implements java.awt.event.ActionListener {
  private RunParameterConfig adaptee;

  RunParameterConfig_jBSaveto_actionAdapter(RunParameterConfig adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jBSaveto_actionPerformed(e);
  }
}

class RunParameterConfig_jBInnererWertGeneration_actionAdapter implements java.awt.event.ActionListener {
  private RunParameterConfig adaptee;

  RunParameterConfig_jBInnererWertGeneration_actionAdapter(RunParameterConfig adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jBInnererWertGeneration_actionPerformed(e);
  }
}

class RunParameterConfig_jB_MovingdaysGenerate_actionAdapter implements java.awt.event.ActionListener {
  private RunParameterConfig adaptee;

  RunParameterConfig_jB_MovingdaysGenerate_actionAdapter(RunParameterConfig adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jB_MovingdaysGenerate_actionPerformed(e);
  }
}

class RunParameterConfig_jBAbschlag_Generate_actionAdapter implements java.awt.event.ActionListener {
  private RunParameterConfig adaptee;

  RunParameterConfig_jBAbschlag_Generate_actionAdapter(RunParameterConfig adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jBAbschlag_Generate_actionPerformed(e);
  }
}

class RunParameterConfig_jBSpeichernInnererWert_actionAdapter implements java.awt.event.ActionListener {
  private RunParameterConfig adaptee;

  RunParameterConfig_jBSpeichernInnererWert_actionAdapter(RunParameterConfig adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jBSpeichernInnererWert_actionPerformed(e);
  }
}

class RunParameterConfig_jBInnererWertLaden_actionAdapter implements java.awt.event.ActionListener {
  private RunParameterConfig adaptee;

  RunParameterConfig_jBInnererWertLaden_actionAdapter(RunParameterConfig adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jBInnererWertLaden_actionPerformed(e);
  }
}

class RunParameterConfig_jBLoadInnererWertMuster_actionAdapter implements java.awt.event.ActionListener {
  private RunParameterConfig adaptee;

  RunParameterConfig_jBLoadInnererWertMuster_actionAdapter(RunParameterConfig adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jBLoadInnererWertMuster_actionPerformed(e);
  }
}

class RunParameterConfig_jRB_VariableMaxLostNumber_actionAdapter implements java.awt.event.ActionListener {
  private RunParameterConfig adaptee;

  RunParameterConfig_jRB_VariableMaxLostNumber_actionAdapter(RunParameterConfig adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jRB_VariableMaxLostNumber_actionPerformed(e);
  }
}

class RunParameterConfig_jB_RemoveNetwork_actionAdapter implements java.awt.event.ActionListener {
  private RunParameterConfig adaptee;

  RunParameterConfig_jB_RemoveNetwork_actionAdapter(RunParameterConfig adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jB_RemoveNetwork_actionPerformed(e);
  }
}

class RunParameterConfig_jB_DisplayGraphic_actionAdapter implements java.awt.event.ActionListener {
  private RunParameterConfig adaptee;

  RunParameterConfig_jB_DisplayGraphic_actionAdapter(RunParameterConfig adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jB_DisplayGraphic_actionPerformed(e);
  }
}