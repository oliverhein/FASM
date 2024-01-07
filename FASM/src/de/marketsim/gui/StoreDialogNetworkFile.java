package de.marketsim.gui;

/**
 * <p>Überschrift: Market Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.Vector;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.core.AID;

import org.apache.log4j.*;

import de.marketsim.util.*;
import de.marketsim.message.*;
import de.marketsim.config.*;
import de.marketsim.SystemConstant;
import de.marketsim.gui.graph.AgentGraphStatusFrame;
import de.marketsim.config.XmlConfigLoader;
import javax.swing.table.*;
import de.marketsim.util.DailyStatisticOfNetwork;


public class StoreDialogNetworkFile extends JFrame implements StoreBaseGUI
{
  private DefaultTableModel tm_agentsimulator = new DefaultTableModel();
  private Process mOrderBookIEWindow;
  Logger  mLoggerAblauf = MsgLogger.getMsgLogger("STOCKSTORE.ABLAUF");
  private Agent mMyAgent = null;
  private RunParameterConfig  mopdialog = new RunParameterConfig("", true );;

  private JButton jBStart = new JButton();
  //private JTextField jFSimulatorList = new JTextField();
  private JLabel jLabel4 = new JLabel();
  private JLabel jLabel5 = new JLabel();
  private JTextField jTFInvestor = new JTextField();
  private JTextField jTFNoiseTrader = new JTextField();
  private JLabel jLabel6 = new JLabel();
  private JLabel jLabel7 = new JLabel();
  private JTextField jTFInvestorReg = new JTextField();
  private JTextField jTFNoiseTraderReg = new JTextField();
  private JLabel jLabel10 = new JLabel();
  private JTextField jTFExpectedTotal = new JTextField();
  private JTextField jTFRegisteredTotal = new JTextField();

  private Configurator conf = null;
  private JLabel jLabel13 = new JLabel();
  private JTextField jTF_StartTime = new JTextField();
  private JLabel jLabel14 = new JLabel();
  private JTextField jTFCurrentday = new JTextField();
  private JTextField jTFFinshedtimeofCurrentday = new JTextField();
  private JLabel jLabel16 = new JLabel();
  private JTextField jTFRandomTrader = new JTextField();
  private JTextField jTFRandomTraderReg = new JTextField();

  private JButton jBOption = new JButton();
  private JLabel jLabel3 = new JLabel();
  private JTextField jTFConfigFile = new JTextField();
  private JButton jBConfigFileSelect = new JButton();
  private JButton jBINTERRUPT = new JButton();
  private JButton jBStepByStep = new JButton();
  private JButton jBGOGO = new JButton();

  //private JCheckBox jCB_Tobintax = new JCheckBox();

  private JButton jBMonitorFame = new JButton();
  private boolean  mMonitorFrameEnabled = false;
  private JCheckBox jCB_ShowInnererWert = new JCheckBox();
  private JLabel jLabel1 = new JLabel();
  private JTextField jTFCurrentNetwork = new JTextField();
  private JLabel jLabel12 = new JLabel();
  private JTextField jTFInterrupted = new JTextField();

  private JButton jBSaveGraph = new JButton();
  private JLabel jLabel8 = new JLabel();
  private JButton jBQUIT = new JButton();


  ////////////////////////////////////////////////////////////////////////////
  private  AgentGraphStatusFrame   mAgentGraphStatus = null;
  private  OperatorStatusFrame     mStatusFrame      = null;
  private HistoryFrame mHistoryFrame = null;
  private GewinnProcentFrame mTraderGewinnProzentFrame = null;
  private GewinnProcentFrame mInvestorGewinnProzentFrame = null;
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JTable jTB_AgentSimulatorList = new JTable();
  private JLabel jLabel9 = new JLabel();
  private JTextField jTF_RunCounterDisplay = new JTextField();
  private JLabel jLabel11 = new JLabel();
  private JLabel jLabel2 = new JLabel();
  private JTextField jTFBlanko = new JTextField();
  private JTextField jTFBlankoReg = new JTextField();
  private JTextField jTFStartTime = new JTextField();
  private JTextField jTFRunCounterDisplay = new JTextField();


  public StoreDialogNetworkFile()
  {
    try
    {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public StoreDialogNetworkFile(Agent pAgent)
  {
    this.mMyAgent = pAgent;
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    this.tm_agentsimulator.setColumnCount(1);
    this.tm_agentsimulator.setColumnIdentifiers( new String[] { "Simulator Name"} );
    this.jTB_AgentSimulatorList.setModel( tm_agentsimulator );

  }

  public void EnableAllMonitorFrame( boolean pEnabled )
  {
     this.jBMonitorFame.setEnabled(true);
     if ( pEnabled )
     {
           this.setScreenLayout();
           this.mTraderGewinnProzentFrame.setVisible(true);
           this.mInvestorGewinnProzentFrame.setVisible(true);
           this.mAgentGraphStatus.setWishedVisible(true);
           this.mHistoryFrame.setVisible(true);
     }
     else
     {
           this.mTraderGewinnProzentFrame.setVisible( false);
           this.mInvestorGewinnProzentFrame.setVisible( false );
           this.mHistoryFrame.setVisible( false );
           this.mAgentGraphStatus.setVisible( false );
           this.mAgentGraphStatus.setWishedVisible( false );
     }
  }

  public void setScreenLayout()
  {
    ScreenLayout.checkScreenResolution();
    this.setLocation( ScreenLayout.MainFrame_Location_X,
                      ScreenLayout.MainFrame_Location_Y  );

    this.setSize( ScreenLayout.MainFrame_Width,
                  ScreenLayout.MainFrame_Height );

       this.mTraderGewinnProzentFrame.setLocation(
           ScreenLayout.NoiseTraderGewinnFrame_Location_X,
           ScreenLayout.NoiseTraderGewinnFrame_Location_Y);
       this.mTraderGewinnProzentFrame.setSize(
           ScreenLayout.NoiseTraderGewinnFrame_Width,
           ScreenLayout.NoiseTraderGewinnFrame_Height);
       this.mInvestorGewinnProzentFrame.setLocation(
            ScreenLayout.InvestorGewinnFrame_Location_X,
            ScreenLayout.InvestorGewinnFrame_Location_Y);
       this.mInvestorGewinnProzentFrame.setSize(
           ScreenLayout.InvestorGewinnFrame_Width,
           ScreenLayout.InvestorGewinnFrame_Height);

    if ( this.mStatusFrame != null )
    {
        this.mStatusFrame.setLocation(
            ScreenLayout.TradestatusFrame_Location_X,
            ScreenLayout.TradestatusFrame_Location_Y);

        this.mStatusFrame.setSize(
            ScreenLayout.TradestatusFrame_Width,
            ScreenLayout.TradestatusFrame_Height);
    }
    else
    {
      // Graph Display
      this.mAgentGraphStatus.setLocation(
          ScreenLayout.TradestatusFrame_Location_X,
          ScreenLayout.TradestatusFrame_Location_Y);
      this.mAgentGraphStatus.setSize(
          ScreenLayout.TradestatusFrame_Width,
          ScreenLayout.TradestatusFrame_Height);
    }

    this.mHistoryFrame.setLocation(
        ScreenLayout.HistroyFrame_Location_X,
        ScreenLayout.HistroyFrame_Location_Y);
    this.mHistoryFrame.setSize(
        ScreenLayout.HistroyFrame_Width,
        ScreenLayout.HistroyFrame_Height);
  }

  private void jbInit() throws Exception
  {

    this.getContentPane().setLayout(null);
    jBStart.setBounds(new Rectangle(101, 228, 82, 30));
    jBStart.setEnabled(false);
    jBStart.setMargin(new Insets(2, 1, 2, 1));
    jBStart.setText("Start");
    jBStart.addActionListener(new StoreDialogNetworkFile_jBStart_actionAdapter(this));
    this.setResizable(false);
    this.setTitle("FASM ");
    this.setIconImage(  Toolkit.getDefaultToolkit().getImage("fasm.gif") );

    jLabel4.setText("Fundamental");
    jLabel4.setBounds(new Rectangle(121, 65, 77, 23));
    jLabel5.setText("Trend");
    jLabel5.setBounds(new Rectangle(205, 65, 43, 21));
    jTFInvestor.setText("0");
    jTFInvestor.setBounds(new Rectangle(129, 84, 31, 22));
    jTFNoiseTrader.setText("13");
    jTFNoiseTrader.setBounds(new Rectangle(209, 84, 32, 22));
    jTFNoiseTrader.setText("0");
    jLabel6.setBounds(new Rectangle(8, 87, 59, 17));
    jLabel6.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel6.setText("Expected:");
    jLabel7.setText("Registered:");
    jLabel7.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel7.setBounds(new Rectangle(9, 109, 68, 22));
    jTFInvestorReg.setBounds(new Rectangle(129, 110, 32, 23));
    jTFInvestorReg.setText("0");
    jTFInvestorReg.setEditable(false);
    jTFNoiseTraderReg.setBounds(new Rectangle(209, 110, 30, 23));
    jTFNoiseTraderReg.setText("0");
    jTFNoiseTraderReg.setEditable(false);
    jLabel10.setBounds(new Rectangle(77, 65, 28, 23));
    jLabel10.setText("Total");
    jTFExpectedTotal.setBounds(new Rectangle(77, 84, 35, 22));
    jTFExpectedTotal.setText("0");
    jTFExpectedTotal.setEditable(false);
    jTFRegisteredTotal.setEditable(false);
    jTFRegisteredTotal.setText("0");
    jTFRegisteredTotal.setBounds(new Rectangle(77, 110, 35, 23));

    jLabel13.setBounds(new Rectangle(12, 169, 31, 21));
    jLabel13.setToolTipText("");
    jLabel13.setText("Start");
    jTFStartTime.setEditable(false);
    jTFStartTime.setText("00:00:00");
    jTFStartTime.setBounds(new Rectangle(44, 169, 61, 21));
    jLabel14.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel14.setText("Day");
    jLabel14.setBounds(new Rectangle(406, 197, 39, 21));
    jTFCurrentday.setEditable(false);
    jTFCurrentday.setText("0");
    jTFCurrentday.setBounds(new Rectangle(434, 197, 84, 21));
    jTFFinshedtimeofCurrentday.setEditable(false);
    jTFFinshedtimeofCurrentday.setText("00:00:00");
    jTFFinshedtimeofCurrentday.setBounds(new Rectangle(196, 169, 61, 21));
    jLabel16.setBounds(new Rectangle(249, 65, 55, 21));
    jLabel16.setToolTipText("");
    jLabel16.setText("Liquidity");
    jTFRandomTrader.setText("0");
    jTFRandomTrader.setBounds(new Rectangle(250, 84, 36, 22));
    jTFRandomTrader.setText("0");
    jTFRandomTraderReg.setEditable(false);
    jTFRandomTraderReg.setText("0");
    jTFRandomTraderReg.setBounds(new Rectangle(250, 110, 36, 23));

    jBOption.addActionListener(new StoreDialogNetworkFile_jBOption_actionAdapter(this));
    jBOption.setBounds(new Rectangle(11, 228, 82, 30));
    jBOption.setEnabled(false);
    jBOption.setMargin(new Insets(2, 1, 2, 1));
    jBOption.setText("Parameter");

    jLabel3.setBounds(new Rectangle(7, 35, 65, 20));
    jLabel3.setText("Config File");

    jTFConfigFile.setEditable(false);
    jTFConfigFile.setBounds(new Rectangle(70, 36, 328, 19));
    jBConfigFileSelect.setText("change");
    jBConfigFileSelect.setBounds(new Rectangle(402, 35, 59, 20));
    jBConfigFileSelect.setMargin(new Insets(2, 1, 2, 1));
    jBConfigFileSelect.addActionListener(new StoreDialogNetworkFile_jBConfigFileSelect_actionAdapter(this));


    jBINTERRUPT.setBounds(new Rectangle(191, 228, 82, 30));
    jBINTERRUPT.setMargin(new Insets(2, 1, 2, 1));
    jBINTERRUPT.setText("Interrupt");
    jBINTERRUPT.addActionListener(new StoreDialogNetworkFile_jBINTERRUPT_actionAdapter(this));

    jBStepByStep.setBounds(new Rectangle(152, 4, 124, 24));
    jBStepByStep.setMargin(new Insets(2, 1, 2, 1));

    jBStepByStep.setEnabled(false);

    jBStepByStep.setText("Enable Step by Step");
    jBStepByStep.addActionListener(new StoreDialogNetworkFile_jBStepByStep_actionAdapter(this));
    jBGOGO.setBounds(new Rectangle(92, 4, 57, 24));
    jBGOGO.setEnabled(false);
    jBGOGO.setMargin(new Insets(2, 1, 2, 1));
    jBGOGO.setText("GO ON");
    jBGOGO.addActionListener(new StoreDialogNetworkFile_jBGOGO_actionAdapter(this));

    //jCB_Tobintax.setBounds(new Rectangle(464, 226, 21, 24));

    jBMonitorFame.setBounds(new Rectangle(277, 4, 99, 24));
    jBMonitorFame.setEnabled(false);
    jBMonitorFame.setMargin(new Insets(2, 1, 2, 1));
    jBMonitorFame.setText("Enable Monitor");
    jBMonitorFame.addActionListener(new StoreDialogNetworkFile_jBMonitorFame_actionAdapter(this));
    jCB_ShowInnererWert.setToolTipText("");
    jCB_ShowInnererWert.setMargin(new Insets(2, 1, 2, 1));
    jCB_ShowInnererWert.setSelected(true);
    jCB_ShowInnererWert.setText("Show Inner Value");
    jCB_ShowInnererWert.setBounds(new Rectangle(382, 3, 124, 24));
    jLabel1.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel1.setText("Current Network");
    jLabel1.setBounds(new Rectangle(9, 197, 104, 21));
    jTFCurrentNetwork.setEditable(false);
    jTFCurrentNetwork.setBounds(new Rectangle(117, 197, 186, 21));


    //jLB_BatchModeSimulationAnzeiger.setBounds(new Rectangle(313, 242, 210, 20));
    jLabel12.setText("Interrupted Run");
    jLabel12.setBounds(new Rectangle(338, 167, 94, 21));
    jTFInterrupted.setEditable(false);
    jTFInterrupted.setText("0");
    jTFInterrupted.setBounds(new Rectangle(445, 167, 32, 21));

    jBSaveGraph.setBounds(new Rectangle(6, 4, 85, 24));
    jBSaveGraph.setEnabled(false);
    jBSaveGraph.setMargin(new Insets(2, 1, 2, 1));
    jBSaveGraph.setText("Save Graph");
    jBSaveGraph.addActionListener(new StoreDialogNetworkFile_jBSaveGraph_actionAdapter(this));
    jLabel8.setText("Agents");
    jLabel8.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel8.setBounds(new Rectangle(9, 65, 48, 20));
    jBQUIT.setText("Quit");
    jBQUIT.addActionListener(new StoreDialogNetworkFile_jBQUIT_actionAdapter(this));

    jBQUIT.setMargin(new Insets(2, 1, 2, 1));
    jBQUIT.setBounds(new Rectangle(280, 228, 82, 30));
    jScrollPane1.setBounds(new Rectangle(402, 72, 110, 60));
    jTB_AgentSimulatorList.setEnabled(false);
    jLabel9.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel9.setText("Run");
    jLabel9.setBounds(new Rectangle(309, 197, 32, 21));
    jTFRunCounterDisplay.setEditable(false);
    jTFRunCounterDisplay.setBounds(new Rectangle(339, 197, 56, 21));
    jLabel11.setBounds(new Rectangle(118, 169, 74, 21));
    jLabel11.setText("Current time");
    jLabel2.setText("Retail");
    jLabel2.setBounds(new Rectangle(314, 64, 70, 20));
    jTFBlanko.setText("0");
    jTFBlanko.setBounds(new Rectangle(314, 87, 53, 18));
    jTFBlankoReg.setEditable(false);
    jTFBlankoReg.setText("0");
    jTFBlankoReg.setBounds(new Rectangle(313, 111, 55, 20));
    jTFStartTime.setBounds(new Rectangle(44, 171, 61, 21));
    jTFStartTime.setText("00:00:00");
    jTFStartTime.setEditable(false);
    jTFRunCounterDisplay.setBounds(new Rectangle(344, 197, 32, 21));
    jTFRunCounterDisplay.setText("0");
    jTFRunCounterDisplay.setEditable(false);
    this.getContentPane().add(jBConfigFileSelect, null);
    this.getContentPane().add(jTFConfigFile, null);

    this.getContentPane().add(jBSaveGraph, null);
    this.getContentPane().add(jLabel3, null);
    this.getContentPane().add(jBGOGO, null);
    this.getContentPane().add(jBStepByStep, null);
    this.getContentPane().add(jBMonitorFame, null);


    this.getContentPane().add(jLabel8, null);
    this.getContentPane().add(jLabel6, null);
    this.getContentPane().add(jLabel7, null);
    this.getContentPane().add(jLabel16, null);
    this.getContentPane().add(jTFRandomTrader, null);
    this.getContentPane().add(jTFRandomTraderReg, null);
    this.getContentPane().add(jCB_ShowInnererWert, null);


    this.getContentPane().add(jTF_StartTime, null);
    this.getContentPane().add(jLabel13, null);
    this.getContentPane().add(jLabel11, null);
    this.getContentPane().add(jTFFinshedtimeofCurrentday, null);
    this.getContentPane().add(jTFCurrentNetwork, null);
    this.getContentPane().add(jLabel14, null);
    this.getContentPane().add(jTFCurrentday, null);
    this.getContentPane().add(jTF_RunCounterDisplay, null);
    this.getContentPane().add(jLabel9, null);
    this.getContentPane().add(jLabel1, null);
    this.getContentPane().add(jBOption, null);
    this.getContentPane().add(jBStart, null);
    this.getContentPane().add(jBQUIT, null);
    this.getContentPane().add(jBINTERRUPT, null);
    this.getContentPane().add(jLabel12, null);
    this.getContentPane().add(jTFInterrupted, null);
    this.getContentPane().add(jTFExpectedTotal, null);
    this.getContentPane().add(jTFNoiseTraderReg, null);
    this.getContentPane().add(jTFNoiseTrader, null);
    this.getContentPane().add(jLabel5, null);
    this.getContentPane().add(jLabel4, null);
    this.getContentPane().add(jTFInvestor, null);
    this.getContentPane().add(jTFInvestorReg, null);
    this.getContentPane().add(jTFRegisteredTotal, null);
    this.getContentPane().add(jLabel10, null);
    this.getContentPane().add(jLabel2, null);
    this.getContentPane().add(jTFBlanko, null);
    this.getContentPane().add(jTFBlankoReg, null);
    this.getContentPane().add(jScrollPane1, null);
    this.getContentPane().add(jTFStartTime, null);
    this.getContentPane().add(jTFRunCounterDisplay, null);
    jScrollPane1.getViewport().add(jTB_AgentSimulatorList, null);

  }

  public void setSimulationConfig(String pConfigFile , boolean pEditable )
  {
      this.jTFConfigFile.setText( pConfigFile );
  }

  public void setSimulationCounter(String pSimulationCounterInfo )
  {
     this.jTFRunCounterDisplay.setText( pSimulationCounterInfo );
  }

  void jBNetworkFileSelect_actionPerformed(ActionEvent e)
  {
     java.awt.FileDialog  fd = new FileDialog(this);
     fd.setSize(200,200);
     fd.setVisible(true);

     String filename = fd.getFile();
     String dirname = fd.getDirectory();
  }

  public void setHistoryFrame(HistoryFrame pHistoryFrame)
  {
    this.mHistoryFrame = pHistoryFrame;
  }

  public void setInvestorGewinnProzentFrame( GewinnProcentFrame pInvestorGewinnProzentFrame)
  {
     this.mInvestorGewinnProzentFrame = pInvestorGewinnProzentFrame;
  }

  public void setNoiseTraderGewinnProzentFrame( GewinnProcentFrame pTraderGewinnProzentFrame )
  {
     this.mTraderGewinnProzentFrame = pTraderGewinnProzentFrame;
  }

  public void setStarttimeofFirstday(String pTime)
  {
    this.jTFStartTime.setText(pTime);
  }

  public void setCurrentNetwork(String pNetworkFile)
  {
     this.jTFCurrentNetwork.setText(pNetworkFile);
  }

  public void setCurrentday(int pDay)
  {
    this.jTFCurrentday.setText( ""+ pDay+"/" + Configurator.mConfData.mHandelsday);
  }

  public void setFinishedtimeofCurrentday(String pTime)
  {
    this.jTFFinshedtimeofCurrentday.setText( ""+ pTime);
  }

  public void enableMonitorFrame(boolean pEnabled)
  {
    this.jBMonitorFame.setEnabled( pEnabled );
  }

  void jBStart_actionPerformed(ActionEvent  ex)
  {
      java.sql.Timestamp ts = new java.sql.Timestamp( System.currentTimeMillis() );
      this.setStarttimeofFirstday( ts.toString().substring(11,19) );
      Vector agentsimulatorAIDlist  = AgentSimulatorManagerContainer.getAgentSimulatorManagerAIDs();
      if ( agentsimulatorAIDlist.size() == 0 )
      {
         JOptionPane.showMessageDialog(this,"No AgentSimulator is found. Please start AgentSimulator with dologin.cmd.","Error", JOptionPane.ERROR_MESSAGE);
         return;
      }
      
      this.jTFInterrupted.setText(""+0);
      
      this.dostart();
  }

  public void dostart()
  {

    this.resetAgentCounterGUIElements();

    this.Check_And_PrepareNetworkFileState();

    this.jBOption.setEnabled        ( false );
    this.jBConfigFileSelect.setEnabled(false);
    this.jTFInvestor.setEditable    ( false );
    this.jTFNoiseTrader.setEditable ( false );
    this.jTFRandomTrader.setEditable( false );
    this.jTFBlanko.setEditable      ( false );

    //this.jTFConfigFile.setEditable ( false );

    this.mHistoryFrame.setXScaleNumber( Configurator.mConfData.mHandelsday );

    this.jBStart     .setEnabled(false);
    this.jBStepByStep.setEnabled(true);
    this.jBMonitorFame.setEnabled(true);

    this.SetCurrentNetworkParameterFromNetworkList( Configurator.mNetworkConfigCurrentIndex );

    this.setScreenLayout();

    java.sql.Timestamp ts = new java.sql.Timestamp( System.currentTimeMillis() );
    String ss = ts.toString().substring(0,19);
    ss = ss.replace(' ', '_');
    ss = ss.replace(':', '_');
    ss = ss.replace('.', '_');

    String cfgfilename = this.jTFConfigFile.getText().trim();

    // replace all \ with /
    cfgfilename = cfgfilename.replace('\\', '/');

    System.out.println ("cfgfilename="+cfgfilename);

    if ( cfgfilename.indexOf("/") >=0 )
    {
         for ( int j=cfgfilename.length()-1; j>=0; j--)
         {
             if (  cfgfilename.charAt( j ) == '/' )
             {
                // wir brauchen nur den letzten Teil
                // cfgfilename ="config/test1.xml"
                // so wir brauchen  test1.xml
                cfgfilename =  cfgfilename.substring( j + 1);
                break;
             }
         }
    }

    String topdirectoryname = "reports/" + ss + FileTool.removeDirectoryName( cfgfilename ) ;

    //System.out.println ("Topdir="+topdirectoryname);

    // create 1. Level Directory
    FileTool.createDirectory( topdirectoryname );

    Configurator.mConfData.mLogTopDirectory= topdirectoryname ;

    // create 2. Level Directory
    String secondleveldirectoryname = topdirectoryname +
                                      Configurator.mConfData.mPfadSeperator +
                                      Configurator.mNetworkConfigCurrentIndex +"_" +
                                      Configurator.mConfData.mNetworkfile_OhnePfad_CurrentUsed;

    Configurator.mConfData.setMainLogDirectory( secondleveldirectoryname );
    FileTool.createDirectory( secondleveldirectoryname );

    // 1 --> 01
    Configurator.mConfData.setTestCaseName("RUN-" + HelpTool.covertInt2StringWithPrefixZero(1, 2) );
    // create 3. Level Directory
    this.PrepareLogDirectoryForOneSimulation();
    // start a new Simulation
    this.StartNewSimulation();

  }

  public void PrepareLogDirectoryForOneSimulation()
  {
    // create 3. Level Directory
    String ss = Configurator.mConfData.mLogTopDirectory +
                Configurator.mConfData.mPfadSeperator +
                Configurator.mNetworkConfigCurrentIndex +"_" +
                Configurator.mConfData.mNetworkfile_OhnePfad_CurrentUsed +
                Configurator.mConfData.mPfadSeperator +
                Configurator.mConfData.mTestCaseName;
    FileTool.createDirectory(ss);
    // create directory for saving depot
    FileTool.createDirectory(ss+Configurator.mConfData.mPfadSeperator+"depot");

    // create directory orderbook for saving daily order book html file
    //  This directory will be used by the  IndexCalculator_base.java
    FileTool.createDirectory(ss+Configurator.mConfData.mPfadSeperator+"orderbook");

    // create directory for saving status exchange history of agents
    FileTool.createDirectory(ss+Configurator.mConfData.mPfadSeperator+"statusexchange");
    // These two directoried will be used by the DataLogger Agent
    Configurator.mConfData.setMainLogDirectory( ss );
  }

  public void StartNewSimulation()
  {
    // java.sql.Timestamp ts = new java.sql.Timestamp(System.currentTimeMillis());
    // String starttime = ts.toString().substring(0,19);

    System.out.println("Configurator.mNetworkConfigCurrentIndex="+Configurator.mNetworkConfigCurrentIndex);

    Configurator.mConfData.setTestSerieStarttime( System.currentTimeMillis() );

    // Start DataLogger Agent
    this.startDataLogger();

    // PriceContainer reset
    PriceContainer.Reset( Configurator.mConfData.mHandelsday );

    this.setCurrentNetwork( (Configurator.mNetworkConfigCurrentIndex+1)+"/" + Configurator.mNetworkConfigManager.getSize()+":" + Configurator.mConfData.mNetworkfileCurrentUsed );

    NetworkConfig currentnetwork = Configurator.getNetworkConfig( Configurator.mNetworkConfigCurrentIndex );

    this.setExpectedAgentAnzahl
    (
             Configurator.mConfData.mAnzahlInvestor+
             Configurator.mConfData.mAnzahlNoiseTrader+
             Configurator.mConfData.mAnzahlBlankoAgent+
             Configurator.mConfData.mAnzahlRandomTrader,

             Configurator.mConfData.mAnzahlInvestor,
             Configurator.mConfData.mAnzahlNoiseTrader,
             Configurator.mConfData.mAnzahlBlankoAgent,
             Configurator.mConfData.mAnzahlRandomTrader
    );

    // Start TobinTax Agent if MoneyMarket and TobinTaxAgent is enabled.
    if ( ( ! Configurator.istAktienMarket() ) && Configurator.mConfData.mTobintaxAgentAktive )
    {
        this.startTobinTaxAgent();
    }

    // send Runtime Parameter
    this.SendRuntimeParameter( AgentSimulatorManagerContainer.getAgentSimulatorManagerAIDs() );

    // send Agent Distribution List
    // use the common DistributionList
    this.SendDistributionList( AgentSimulatorManagerContainer.getAgentSimulatorManagerAIDs(),
                               Configurator.mConfData.mDistributionList );

    this.setSimulationCounter( currentnetwork.mCurrentRunningNo + "/"+ Configurator.mConfData.mRepeatTimes );
    this.setScreenLayout();
    this.EnableAllMonitorFrame( this.mMonitorFrameEnabled );

  }

  public void startTobinTaxAgent()
  {
    System.out.println("Starting TobintaxAgent");
    try
    {
        // get a container controller for creating new agents
        jade.wrapper.PlatformController container = this.mMyAgent.getContainerController();
        jade.wrapper.AgentController tobinagent = container.createNewAgent("Tobintax","de.marketsim.agent.trader.TobintaxAgent", null );
        tobinagent.start();
        System.out.println("TobintaxAgent has been started");
    }
    catch (Exception ex)
    {
        System.out.println("TobinTaxAgebnt can not be started."  );
        ex.printStackTrace();
    }
  }

  public void CloseDailyOrderBook()
  {
    if ( this.mOrderBookIEWindow != null )
    {
      this.mOrderBookIEWindow.destroy();
    }
  }

  public void showDailyOrderBook(String pDailyOrderBook)
  {
      // close old if it exist
      CloseDailyOrderBook();
      // show a new one
      displayDailyOrderBook( pDailyOrderBook );
  }

  /**
   * String pDailyOrderBook, the HTML file of daily order book
   */

  private void displayDailyOrderBook(String pDailyOrderBook)
  {
      // create the html file name
      try
      {
      this.mOrderBookIEWindow = Runtime.getRuntime().exec("iexplore.exe " + pDailyOrderBook);
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
      }
  }

  public void startDataLogger()
  {
      this.mLoggerAblauf.info("Starting DataLoggerAgent");
      try
      {
          // get a container controller for creating new agents
          jade.wrapper.PlatformController container = this.mMyAgent.getContainerController();
          jade.wrapper.AgentController datalogger = container.createNewAgent("DataLogger", "de.marketsim.agent.stockstore.DataLoggerAgent", null );
          datalogger.start();
          this.mLoggerAblauf.info("DataLogger Agent has been started");
      }
      catch (Exception ex)
      {
          this.mLoggerAblauf.error("DataLogger Agent can not be started.", ex );
      }
  }

  public void  SetCurrentNetworkParameterFromNetworkList(int pNetworkIndex)
  {

       System.out.println("Loading " + (pNetworkIndex+1) + ". Network into Configurator.mConfData");

       // load Network-Related Paramter
       NetworkConfig  thisNetworkConfig = Configurator.getNetworkConfig( pNetworkIndex );

       thisNetworkConfig.CreateDynamicGeneratedParameter();

       // set NodesInNetwork, InvestorInNetwork, NoiseTraderInNetwork from Network-specific-Parameter

       Configurator.mConfData.mNodesInNetwork     = thisNetworkConfig.mNodesInNetwork;
       Configurator.mConfData.mAnzahlInvestor     = thisNetworkConfig.mInvestorNumber;
       Configurator.mConfData.mAnzahlNoiseTrader  = thisNetworkConfig.mNoiseTraderNumber;
       Configurator.mConfData.mAnzahlBlankoAgent  = thisNetworkConfig.mBlankoAgentNumber;
       Configurator.mConfData.mAnzahlRandomTrader = thisNetworkConfig.mRandomTraderNumber;

       Configurator.mConfData.setAnzahlTotalAgents( thisNetworkConfig.mInvestorNumber +
                                                    thisNetworkConfig.mNoiseTraderNumber +
                                                    thisNetworkConfig.mBlankoAgentNumber +
                                                    thisNetworkConfig.mRandomTraderNumber
                                                    );

       Configurator.mConfData.mDistributionList   = thisNetworkConfig.getAgent2SimulatorMapping();

       for ( int i=0; i<Configurator.mConfData.mDistributionList.length; i++)
       {
           System.out.println( Configurator.mConfData.mDistributionList[i]);
       }

       Configurator.mConfData.mNetworkfileCurrentUsed           =thisNetworkConfig.mNetworkFileName;
       Configurator.mConfData.mNetworkfile_OhnePfad_CurrentUsed =thisNetworkConfig.mNetworkfilenameOhnePfad;

       Configurator.mConfData.mAnzahlTotalAgents  = Configurator.mConfData.mAnzahlInvestor +
                                                    Configurator.mConfData.mAnzahlNoiseTrader +
                                                    Configurator.mConfData.mAnzahlBlankoAgent +
                                                    Configurator.mConfData.mAnzahlRandomTrader;

       Configurator.mConfData.mTobintax_FestSteuer  = thisNetworkConfig.mFesteTobinTax;
       Configurator.mConfData.mTobintax_ExtraSteuer = thisNetworkConfig.mExtraTobinTax;

       Configurator.mConfData.mAgentInitParameter   = thisNetworkConfig.getAgentInitParameter();

    // before showing new one, make sure that the old is closed.
    if (  this.mAgentGraphStatus != null )
    {
      this.mAgentGraphStatus.setWishedVisible( false );
      this.mAgentGraphStatus.dispose();
    }

    this.mAgentGraphStatus =
    new AgentGraphStatusFrame( Configurator.getCurrentNetworkConfig().mNodesInNetwork,
                               Configurator.getCurrentNetworkConfig().mNetworkFileLoader.getCommunicationPairList(),
                               Configurator.getCurrentNetworkConfig().mRandomTraderNumber   );


    System.out.println("After setting network-related parameter ===========");
    System.out.println("Networkfile="+ thisNetworkConfig.mNetworkFileName );
    System.out.println("Nodes in Networkfile="+ Configurator.mConfData.mNodesInNetwork );
    System.out.println("Investor in Network="+ Configurator.mConfData.mAnzahlInvestor );
    System.out.println("NoiseTrader in Network="+ Configurator.mConfData.mAnzahlNoiseTrader );
    System.out.println("RandomTrader="+ Configurator.mConfData.mAnzahlRandomTrader );
    System.out.println("Tobintax: FestSteuer="+ Configurator.mConfData.mTobintax_FestSteuer );
    System.out.println("Tobintax: ExtraSteuer="+ Configurator.mConfData.mTobintax_ExtraSteuer );
    System.out.println("DistributionList.length" +  Configurator.mConfData.mDistributionList.length + " Agents (Random are included)" );
    System.out.println("===================================");

  }

  /**
   * für jede Simulation-Konfiguration:
   * Diese methode wird nur einmal ausgeführt.
   */
  public void Check_And_PrepareNetworkFileState()
  {
          System.out.println("Checking Networkfiles ......");
          System.out.println( Configurator.mNetworkConfigManager.getSize()+ " Networkfiles will be checked");
          // very important !!
          Configurator.setUniqueIDforAllNetworks();

          Configurator.mNetworkCommonParameterDatabase.setMaxNodes(
              Configurator.mNetworkConfigManager.getMaxNetworkNodes(),
              Configurator.mNetworkConfigManager.getMaxRandomTraderNodes() );

          FasmGaussConfig gsf = new FasmGaussConfig();
          gsf.mAllowedMin       = Configurator.mConfData.mInvestor_DynamischAbschlageProzent_Min;
          gsf.mAllowedMax       = Configurator.mConfData.mInvestor_DynamischAbschlageProzent_Max;
          gsf.mMean             = Configurator.mConfData.mInvestor_AbschlagGaussMean;
          gsf.mDeviation        = Configurator.mConfData.mInvestor_AbschlagGaussDeviation;
          gsf.mDataPart1Prozent = Configurator.mConfData.mInvestor_AnzahlProzent_LinkBereich;
          gsf.mDataPart2Prozent = Configurator.mConfData.mInvestor_AnzahlProzent_MittBereich;
          gsf.mDataPart3Prozent = Configurator.mConfData.mInvestor_AnzahlProzent_RechtBereich;

          Configurator.mNetworkCommonParameterDatabase.setInvestorAgentAbschlagProzentGaussConfig( gsf );

          Configurator.mNetworkCommonParameterDatabase.GaussGenerateInvestorAgentAbschlagProzentGauss( );

          Configurator.mNetworkCommonParameterDatabase.setMovingdayMinMax(
              Configurator.mConfData.mNoiseTrader_MinMovingDaysForAveragePrice,
              Configurator.mConfData.mNoiseTrader_MaxMovingDaysForAveragePrice );

          Configurator.mNetworkCommonParameterDatabase.generateNoiseTraderAgentMovingDay();

  }

  public void SendRuntimeParameter(Vector agentsimulatorAIDlist)
  {
    ACLMessage       aclmsg = new ACLMessage( ACLMessage.INFORM );
    MessageWrapper   msgwrp = new MessageWrapper();
    msgwrp.mMessageType     = SystemConstant.MessageType_RUNTIMEPARAMETER;
    msgwrp.mMessageContent  = Configurator.mConfData;

    PriceContainer.Reset(Configurator.mConfData.mHandelsday);
    try
    {
        aclmsg.setContentObject( msgwrp  );
    }
    catch (Exception exp)
    {
       exp.printStackTrace();
       this.mLoggerAblauf.error("can not set msgwrp into ACL Message.",exp);
    }

    for (int i=0; i<agentsimulatorAIDlist.size();i++)
    {
      aclmsg.addReceiver( (AID) agentsimulatorAIDlist.elementAt(i)  );
    }
    this.mMyAgent.send( aclmsg );
    this.mLoggerAblauf.info("Agents Runtime Parameter has been sent to all Simulators");
  }

  public void SendDistributionList(Vector agentsimulatorAIDlist, String[] pDistributionList)
  {

    ACLMessage       aclmsg = new ACLMessage( ACLMessage.INFORM );
    MessageWrapper   msgwrp = new MessageWrapper();
    msgwrp.mMessageType     = SystemConstant.MessageType_AgentList;
    msgwrp.mMessageContent  = new AgentList( pDistributionList );

    try
    {
         aclmsg.setContentObject( msgwrp );
    }
    catch (Exception exp)
    {
      exp.printStackTrace();
    }

    for ( int i=0; i<agentsimulatorAIDlist.size(); i++)
    {
      aclmsg.addReceiver( (AID)agentsimulatorAIDlist.elementAt(i)  );
    }
    this.mMyAgent.send( aclmsg );
    this.mLoggerAblauf.info("Agents Distributionlist has been sent to all Simulators");
  }

  public void disableAllSubFrame()
  {
      this.EnableAllMonitorFrame( false );

      this.mHistoryFrame.dispose();

      if ( this.mStatusFrame != null )
      {
         this.mStatusFrame.dispose();
      }

      try
      {
         this.mAgentGraphStatus.setWishedVisible( false );
         this.mAgentGraphStatus.setVisible(false);
         this.mAgentGraphStatus.dispose();
      }
      catch (Exception ex)
      {

      }

      this.mTraderGewinnProzentFrame.dispose();
      this.mInvestorGewinnProzentFrame.dispose();
      this.setVisible(false);

  }

  public void registerSimulator( String pSimulatorName )
  {
    //this.jFSimulatorList.setText( this.jFSimulatorList.getText() + pSimulatorName + "," );
    String[] newsm = new String[1];
    newsm[0] = pSimulatorName;
    this.tm_agentsimulator.addRow( newsm );

  }

  public void updateSimulatorList(String pNewList)
  {
    // this.jFSimulatorList.setText( pNewList );
    while ( this.tm_agentsimulator.getRowCount()>0 )
    {
      this.tm_agentsimulator.removeRow(0);
    }

    String ss = pNewList;
    while ( ss.length()> 0 )
    {
       int p=ss.indexOf(",");
       String name;
       if ( p>=0)
       {
         name = ss.substring(0,p);
         ss = ss.substring(p+1);
       }
       else
       {
         name = ss;
         ss="";
       }
       String[] newsm = new String[1];
       newsm[0] = name;
       this.tm_agentsimulator.addRow( newsm );
    }

  }

  public void registerInvestorAgent()
  {
          String ss = this.jTFInvestorReg.getText();
          int j1 = Integer.parseInt(ss) + 1;
          this.jTFInvestorReg.setText( ""+j1 );

          ss = this.jTFNoiseTraderReg.getText();
          int j2 = Integer.parseInt(ss);

          ss = this.jTFRandomTraderReg.getText();
          int j3 = Integer.parseInt(ss);

          ss = this.jTFBlankoReg.getText();
          int j4 = Integer.parseInt(ss);

          this.jTFRegisteredTotal.setText("" + ( j1 + j2 + j3 + j4 ));
  }

  public void registerNoiseTraderAgent()
  {
      String ss = this.jTFNoiseTraderReg.getText();
      int j1 = Integer.parseInt(ss) + 1;
      this.jTFNoiseTraderReg.setText( ""+j1 );

      ss = this.jTFInvestorReg.getText();
      int j2 = Integer.parseInt(ss);

      ss = this.jTFRandomTraderReg.getText();
      int j3 = Integer.parseInt(ss);

      ss = this.jTFBlankoReg.getText();
      int j4 = Integer.parseInt(ss);

      this.jTFRegisteredTotal.setText("" + ( j1 + j2 + j3 + j4 ));
  }

  public void registerRandomTraderAgent()
  {
      String ss = this.jTFRandomTraderReg.getText();
      int j1 = Integer.parseInt(ss) + 1;
      this.jTFRandomTraderReg.setText( ""+j1 );

      ss = this.jTFInvestorReg.getText();
      int j2 = Integer.parseInt(ss);

      ss = this.jTFNoiseTraderReg.getText();
      int j3 = Integer.parseInt(ss);

      ss = this.jTFBlankoReg.getText();
      int j4 = Integer.parseInt(ss);

      this.jTFRegisteredTotal.setText("" + ( j1 + j2 + j3 + j4 ));
  }

  public void registerBlankoAgent()
  {
      String ss = this.jTFBlankoReg.getText();
      int j1 = Integer.parseInt(ss) + 1;
      this.jTFBlankoReg.setText( ""+j1 );

      ss = this.jTFInvestorReg.getText();
      int j2 = Integer.parseInt(ss);

      ss = this.jTFNoiseTraderReg.getText();
      int j3 = Integer.parseInt(ss);

      ss = this.jTFRandomTraderReg.getText();
      int j4 = Integer.parseInt(ss) + 1;

      this.jTFRegisteredTotal.setText("" + ( j1 + j2 + j3 + j4 ));
  }

  public void registerTobintaxAgent()
  {
      //this.jCB_Tobintax.setSelected(true);
  }

  public void resetSomeStateFields()
  {
     this.jTFInvestorReg.setText("0");
     this.jTFNoiseTraderReg.setText("0");
     this.jTFRandomTraderReg.setText("0");
     this.jTFRegisteredTotal.setText("0");
     this.jTFCurrentday.setText("");
     this.jTFFinshedtimeofCurrentday.setText("");
     //this.jTF_StartTime.setText("");
     //this.jCB_Tobintax.setSelected(false);
     this.jCB_ShowInnererWert.setSelected(true);
     this.jBMonitorFame.setText("Enable Monitor");
     this.mMonitorFrameEnabled = false;

  }

  public void unregisterInvestorAgent()
  {
      String ss = this.jTFInvestorReg.getText();
      int j = Integer.parseInt(ss) - 1;
      this.jTFInvestorReg.setText( ""+j );
  }

  public void unregisterNoiseTraderAgent()
  {
      String ss = this.jTFNoiseTraderReg.getText();
      int j = Integer.parseInt(ss) - 1;
      this.jTFNoiseTraderReg.setText( ""+j );
  }

  public void unregisterRandomTraderAgent()
  {
      String ss = this.jTFRandomTraderReg.getText();
      int j = Integer.parseInt(ss) - 1;
      this.jTFRandomTraderReg.setText( ""+j );
  }
  public void unregisterBlankoAgent()
   {
       String ss = this.jTFBlankoReg.getText();
       int j = Integer.parseInt(ss) - 1;
       this.jTFBlankoReg.setText( ""+j );
   }


public void displayInvestorGewinnProzent( Vector pInvestorGewinnProzent, int pDay, double pMax, double pMin )
{
   this.mInvestorGewinnProzentFrame.setData(pInvestorGewinnProzent, pDay, pMax, pMin);
}

public void displayNoiserTraderGewinnProzent( Vector pNoiseTraderGewinnProzent, int pDay, double pMax, double pMin)
{
     this.mTraderGewinnProzentFrame.setData(pNoiseTraderGewinnProzent, pDay, pMax, pMin);
}
public boolean getShowInnererWertEnabled()
{

  if (this.jCB_ShowInnererWert.isSelected())
  {
    return true;
  }
  else
  {
    return false;
  }
}

  public static void main(String[] args)
  {
    StoreDialogNetworkFile ff = new StoreDialogNetworkFile();
    ff.setSize(500,300);
    ff.setVisible(true);
  }

  void jBQUIT_actionPerformed(ActionEvent e) {

    OptionPaneBugFix.YesNoButtonTextBugfix();
    int p = JOptionPane.showConfirmDialog(this, "Do you really want to quit?","Choice", JOptionPane.YES_NO_OPTION);

    if ( p == JOptionPane.OK_OPTION )
    {
          this.SendCloseCommand2AgentSimulator();
          System.exit(0);
    }
  }

  public void EnableButtons4newConfiguration()
  {
    // GUI Button werdebn aktiviert again
    this.jBStart.setEnabled(true);
    this.jBConfigFileSelect.setEnabled(true);
    this.jBOption.setEnabled(true);
    this.jBINTERRUPT.setEnabled( true );
  }

  public void jBINTERRUPT_actionPerformed(ActionEvent e)
  {
        OptionPaneBugFix.YesNoButtonTextBugfix();
        int p = JOptionPane.showConfirmDialog(this, "Do you want really to interrupt current simulation?","Choice", JOptionPane.YES_NO_OPTION);
        if ( p == JOptionPane.OK_OPTION )
        {
              // give a BREAK signal to StockStore
              Configurator.mUserRequiredBreak = true;
              // StockStore will send BREAK command to all Simulators.
              EnableButtons4newConfiguration();
        }
  }

  private void SendCloseCommand2AgentSimulator()
  {
      Vector agentsimulatorAIDlist = AgentSimulatorManagerContainer.getAgentSimulatorManagerAIDs();
      if ( agentsimulatorAIDlist.size() > 0 )
      {
          //send quit command to all AgentSimulatorManager
          ACLMessage aclmsg = new ACLMessage( ACLMessage.INFORM );
          for (int i=0; i<agentsimulatorAIDlist.size(); i++)
          {
             aclmsg.addReceiver( (AID) agentsimulatorAIDlist.elementAt(i) );
          }

          try
          {
            aclmsg.setContentObject( MessageFactory.createQuitCommand() );
          }
          catch (Exception ex)
          {
            ex.printStackTrace();
          }
          this.mMyAgent.send( aclmsg );
          mLoggerAblauf.info("Send Quit Command to all AgentSimulators.");
          HelpTool.pause(3000);
      }
  }

  private void OpenHtmlFile(String pHtmlFile)
  {
    try
    {
       Runtime.getRuntime().exec("iexplore.exe " + pHtmlFile);
    }
    catch (Exception ex)
    {
      JOptionPane.showMessageDialog(this,"IE is not in PATH. Please check the PATH setting of your computer.","Error", JOptionPane.ERROR_MESSAGE);
      return;
    }
  }

  public void closebusiness()
  {
      closeall();
  }

  private void closeall()
  {
    String ss = System.getProperty("Batch");
    if ( (ss!=null ) && ss.equalsIgnoreCase("true") )
    {
       System.exit(0);
    }

  }

  public void setExpectedAgentAnzahl(int pTotal, int pInvestor, int pNoiseTrader, int pBlankoAgent, int pRandomTrader)
  {
      this.jTFExpectedTotal.setText( "" + pTotal );
      this.jTFExpectedTotal.setEditable(false);

      this.jTFInvestor.setText( ""+pInvestor  );
      this.jTFInvestor.setEditable(false);

      this.jTFNoiseTrader.setText(""+pNoiseTrader);
      this.jTFNoiseTrader.setEditable(false);

      this.jTFBlanko.setText( ""+pBlankoAgent );
      this.jTFBlanko.setEditable(false);

      this.jTFRandomTrader.setText(""+pRandomTrader);
      this.jTFRandomTrader.setEditable(false);
  }

  public void setSimulatorList(String pNameList[])
  {
    String ss = "";
    for (int i=0;i<pNameList.length;i++)
    {
      ss = ss + pNameList[i]+",";
    }

    //this.jFSimulatorList.setText(ss);
    //this.jFSimulatorList.setEditable(false);
  }

  public void processEvent( AWTEvent e )
  {
    // 201 is the code of Windows Closing
    if ( e.getID()== 201 )
    {
       // Nothing to do
    }
    else
    {
       super.processEvent(e);
    }
  }

  void jBOption_actionPerformed(ActionEvent e)
  {

    mopdialog.setMainFrame( this );
    mopdialog.setTitle("Runtime Parameter Configuration ");
    mopdialog.setSize( 850, 565 );
    mopdialog.setLocation(0,270);
    mopdialog.setVisible( false  );
    mopdialog.setResizable(false);

    // mopdialog.setModal( true  );
    mopdialog.checkTobintaxSetting();

    // perform Distribution at first.
    // but man can always change the parameters after entering the dialog
    // mopdialog.doDistribution();

    mopdialog.show();

    this.setEnabled(true);

    if (this.mopdialog.getButtonAccept_pressed())
    {
      System.out.print("ButtonAccept_pressed= " + this.mopdialog.getButtonAccept_pressed());
      this.jBStart.setEnabled(true);
    }
    this.setTitle("FASM " + Configurator.mConfData.getCurrentMarketMode()  );

  }

  void hidemaindialog()
  {
       this.mopdialog.setSize(600,580);
       this.mopdialog.setVisible(true);
  }

  void jBConfigFileSelect_actionPerformed(ActionEvent e)
  {

        java.awt.FileDialog  fd = new FileDialog(this);

        //fd.setFilenameFilter( new CFGFilter("xml")  );

        fd.setDirectory( FileTool.getCurrentAbsoluteDirectory() + "\\config\\" );
        fd.setFile("*.xml");

        fd.setSize(200,200);
        fd.setVisible(true);

        String filename = fd.getFile();
        String dirname  = fd.getDirectory();
        String absolutefilename = dirname + filename;

        absolutefilename = absolutefilename.replace('\\','/');

        if ( filename != null )
        {
           this.jTFConfigFile.setText( absolutefilename );
        }
        fd.setVisible(false);
        this.loadConfigFile( absolutefilename );

  }

  void jBConfigFileLoad_actionPerformed(ActionEvent e)
  {
       // check the config file
       String configfile = this.jTFConfigFile.getText().trim();
       loadConfigFile(configfile);
  }

  public void loadConfigFile(String pConfigFile)
  {
    // check the config file
    String configfile = pConfigFile;
    if ( ! FileChecker.FileExist( configfile )  )
    {
      JOptionPane.showMessageDialog(this, configfile +" does not exist!","Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    // load the config file into Configurator
    // Configurator.init( configfile );

    XmlConfigLoader loader = new XmlConfigLoader();
    loader.doload( configfile );

    // set the fields in the RunParameterConfig with Parameter in Configurator
    this.mopdialog.loadParameterFromConfData();

    this.mopdialog.setConfigFile( configfile );
    this.jBOption.setEnabled(true);
    this.jBStart.setEnabled(true);
    this.jTFStartTime.setText("00:00:00");
    this.setTitle("FASM " + Configurator.mConfData.getCurrentMarketMode()  );

  }

  void jBStepByStep_actionPerformed(ActionEvent e)
  {
      if (  ! Configurator.mConfData.mStepByStep )
      {
           Configurator.mConfData.mStepByStep = true;
           this.jBGOGO.setEnabled( true );
           this.jBSaveGraph.setEnabled( true );
           jBStepByStep.setText("Disable Step by Step");
      }
      else
      {
           Configurator.mConfData.mStepByStep = false;
           Configurator.letsgo();
           Configurator.letsgo();
           this.jBGOGO.setEnabled( false );
           this.jBSaveGraph.setEnabled( false );
           jBStepByStep.setText("Enable Step by Step");
      }
  }

  void jBGOGO_actionPerformed(ActionEvent e)
  {
     Configurator.letsgo();
  }

  void jBMonitorFame_actionPerformed(ActionEvent e)
  {
      if ( this.mMonitorFrameEnabled )
      {
        this.mMonitorFrameEnabled = false;
        this.jBMonitorFame.setText("Enable Monitor");
      }
      else
      {
        this.mMonitorFrameEnabled = true;
        this.jBMonitorFame.setText("Disable Monitor");
      }
      this.EnableAllMonitorFrame( this.mMonitorFrameEnabled );
  }

  public boolean isAgentStatusInGraph()
  {
      if ( this.mAgentGraphStatus != null )
      {
         return true;
      }
      else
      {
         return false;
      }
  }

  public void addInterrupted()
  {
     int j = Integer.parseInt( this.jTFInterrupted.getText()  ) ;
     this.jTFInterrupted.setText( (j+1)+"" );
  }

  public boolean isAgentStatusNameLoaded()
  {
        return this.mStatusFrame.isNameListLoaded() ;
  }

  public void setNameList2AgentStatusFrame(Vector pNameList)
  {
        this.mStatusFrame.saveNameList( pNameList );
  }

  public void setAgentTradeStatus(Vector pAgentStatus, DailyStatisticOfNetwork pStatistic )
  {
    if ( mStatusFrame != null )
    {
         this.mStatusFrame.DisplayStatus( pAgentStatus );
    }
    else
    {
         this.mAgentGraphStatus.setDailyStatistic(pStatistic );
         this.mAgentGraphStatus.DisplayStatus( pAgentStatus );
    }
  }

  private void resetAgentCounterGUIElements()
  {
      this.jTFExpectedTotal.setText("0");
      this.jTFInvestor.setText("0");
      this.jTFNoiseTrader.setText("0");
      this.jTFBlanko.setText("0");
      this.jTFRandomTrader.setText("0");

      this.jTFRegisteredTotal.setText("0");
      this.jTFInvestorReg.setText("0");
      this.jTFNoiseTraderReg.setText("0");
      this.jTFRandomTraderReg.setText("0");
      this.jTFBlankoReg.setText("0");

  }

  void jBSaveGraph_actionPerformed(ActionEvent e)
  {

    java.awt.FileDialog  fd = new FileDialog( this );
    fd.setMode( FileDialog.SAVE  );
    fd.setSize(200,200);
    fd.setVisible(true);
    String filename = fd.getFile();
    String dirname  = fd.getDirectory();

    if ( filename == null )
    {
       // abbrechen, nichts zu tun
    }
    else
    {
      if ( this.mAgentGraphStatus != null )
      {
        String jpgfile= dirname + "/" + filename;
        int j= jpgfile.indexOf(".");
        if ( j<0)
        {
            jpgfile =  jpgfile+".jpg";
        }

        try
        {
          ScreenImage.createImage( this.mAgentGraphStatus.getGraphPanel().getPureGraphPanel(), jpgfile );
        }
        catch (Exception ex)
        {
          JOptionPane.showMessageDialog(this,"Graph can not be saved to " + jpgfile ,"Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    }

  }

  void jButton1_actionPerformed(ActionEvent e) {
       this.tm_agentsimulator.addRow( new Object[]{"AAAA"});
  }

}

class StoreDialogNetworkFile_jBStart_actionAdapter implements java.awt.event.ActionListener
{
  private StoreDialogNetworkFile adaptee;

    StoreDialogNetworkFile_jBStart_actionAdapter(StoreDialogNetworkFile adaptee)
    {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jBStart_actionPerformed(e);
  }
}

class StoreDialogNetworkFile_jBOption_actionAdapter implements java.awt.event.ActionListener {
  private StoreDialogNetworkFile adaptee;

  StoreDialogNetworkFile_jBOption_actionAdapter(StoreDialogNetworkFile adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e)
  {
    adaptee.jBOption_actionPerformed(e);
  }
}

class StoreDialogNetworkFile_jBConfigFileSelect_actionAdapter implements java.awt.event.ActionListener {
  private StoreDialogNetworkFile adaptee;

  StoreDialogNetworkFile_jBConfigFileSelect_actionAdapter(StoreDialogNetworkFile adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e)
  {
    adaptee.jBConfigFileSelect_actionPerformed(e);
  }
}

class StoreDialogNetworkFile_jBINTERRUPT_actionAdapter implements java.awt.event.ActionListener {
  private StoreDialogNetworkFile adaptee;

  StoreDialogNetworkFile_jBINTERRUPT_actionAdapter(StoreDialogNetworkFile adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e)
  {
    adaptee.jBINTERRUPT_actionPerformed(e);
  }
}

class StoreDialogNetworkFile_jBStepByStep_actionAdapter implements java.awt.event.ActionListener {
  private StoreDialogNetworkFile adaptee;

  StoreDialogNetworkFile_jBStepByStep_actionAdapter(StoreDialogNetworkFile adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jBStepByStep_actionPerformed(e);
  }
}

class StoreDialogNetworkFile_jBGOGO_actionAdapter implements java.awt.event.ActionListener {
  private StoreDialogNetworkFile adaptee;

  StoreDialogNetworkFile_jBGOGO_actionAdapter(StoreDialogNetworkFile adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jBGOGO_actionPerformed(e);
  }
}

class StoreDialogNetworkFile_jBMonitorFame_actionAdapter implements java.awt.event.ActionListener {
  private StoreDialogNetworkFile adaptee;

  StoreDialogNetworkFile_jBMonitorFame_actionAdapter(StoreDialogNetworkFile adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jBMonitorFame_actionPerformed(e);
  }
}

class StoreDialogNetworkFile_jBSaveGraph_actionAdapter implements java.awt.event.ActionListener {
  private StoreDialogNetworkFile adaptee;

  StoreDialogNetworkFile_jBSaveGraph_actionAdapter(StoreDialogNetworkFile adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jBSaveGraph_actionPerformed(e);
  }
}

class StoreDialogNetworkFile_jBQUIT_actionAdapter implements java.awt.event.ActionListener {
  private StoreDialogNetworkFile adaptee;

  StoreDialogNetworkFile_jBQUIT_actionAdapter(StoreDialogNetworkFile adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jBQUIT_actionPerformed(e);
  }
}