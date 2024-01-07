package de.marketsim.gui;

/**
 * <p>Überschrift: FASM Frankfurt Articial Simulation Market</p>
 * <p>Beschreibung: Mircomarket Simulator </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.core.AID;
import org.apache.log4j.*;
import de.marketsim.util.*;
import de.marketsim.message.*;
import de.marketsim.config.*;
import de.marketsim.SystemConstant;
import java.util.*;
import de.marketsim.util.DailyStatisticOfNetwork;


public class SimulationInitControl implements StoreBaseGUI
{

  Logger  mLoggerAblauf = MsgLogger.getMsgLogger("STOCKSTORE.ABLAUF");

  String  mSimulationConfigFile ;

  private Agent mMyAgent = null;

  public SimulationInitControl( Agent pAgent, String pSimulationConfigFile )
  {
     this.mMyAgent = pAgent;
     this.mSimulationConfigFile = pSimulationConfigFile;
     Console myTextConsole = new Console( this );
     myTextConsole.start();
  }

  public void dostart()
  {

    this.Check_And_PrepareNetworkFileState();

    java.sql.Timestamp ts = new java.sql.Timestamp( System.currentTimeMillis() );
    String ss = ts.toString().substring(0,19);
    ss = ss.replace(' ', '_');
    ss = ss.replace(':', '_');
    ss = ss.replace('.', '_');

    System.out.println ("SIMULATIONCONFIGFILE="+ this.mSimulationConfigFile);

    String topdirectoryname = "reports/" + ss + FileTool.removeDirectoryName( this.mSimulationConfigFile ) ;

    topdirectoryname = topdirectoryname.trim();

    System.out.println ("creating reportdirectory: Topdir="+topdirectoryname);

    // create 1. Level Directory
    FileTool.createDirectory( topdirectoryname );

    // create a mark file for ChartGenerator
    FileTool.CreateEmptyFile( topdirectoryname + "/fasmsimulation.mark" );
    System.out.println ("creating "+topdirectoryname + "/fasmsimulation.mark");

    HelpTool.pause("Press any key to continue ");

    Configurator.mConfData.mLogTopDirectory= topdirectoryname ;

    this.SetCurrentNetworkParameterFromNetworkList( 0 );

    // create 2. Level Directory
    String secondleveldirectoryname = topdirectoryname +
                                      Configurator.mConfData.mPfadSeperator +
                                      Configurator.mNetworkConfigCurrentIndex +"_" +
                                      Configurator.mConfData.mNetworkfile_OhnePfad_CurrentUsed;

    Configurator.mConfData.setMainLogDirectory( secondleveldirectoryname );

    FileTool.createDirectory( secondleveldirectoryname );

    Configurator.mConfData.setTestCaseName("RUN-1");

    // create 3. Level Directory
    this.PrepareLogDirectoryForOneSimulation();

    // start a new Simulation
    this.StartNewSimulation();

  }

  public void StartNewSimulation()
  {

    System.out.println();
    System.out.println("***************  Start a new testcase ****************** ");
    System.out.println("Configurator.mNetworkConfigCurrentIndex="+Configurator.mNetworkConfigCurrentIndex);

    Configurator.mConfData.setTestSerieStarttime( System.currentTimeMillis() );

    // Start DataLogger Agent
    this.startDataLogger();

    // PriceContainer reset
    PriceContainer.Reset( Configurator.mConfData.mHandelsday );

    this.setCurrentNetwork( (Configurator.mNetworkConfigCurrentIndex+1)+"/" + Configurator.mNetworkConfigManager.getSize()+":" + Configurator.mConfData.mNetworkfileCurrentUsed );

    this.SetCurrentNetworkParameterFromNetworkList( Configurator.mNetworkConfigCurrentIndex );

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

    // send Runtime Parameter
    this.SendRuntimeParameter( AgentSimulatorManagerContainer.getAgentSimulatorManagerAIDs() );

    // send Agent Distribution List
    // use the common DistributionList
    this.SendDistributionList( AgentSimulatorManagerContainer.getAgentSimulatorManagerAIDs(),
                               Configurator.mConfData.mDistributionList );

    this.setSimulationCounter( currentnetwork.mCurrentRunningNo + "/"+ Configurator.mConfData.mRepeatTimes );

  }


  public void startDataLogger()
  {
      System.out.println("******** Starting DataLoggerAgent ********");
      this.mLoggerAblauf.info("******** Starting DataLoggerAgent ********");
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

    System.out.println();
    System.out.println("******** Sending Runtime Parameter to " + agentsimulatorAIDlist.size() + "  AgentSimulator ********");

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
    System.out.println();
    System.out.println("******** Sending DistributionList to " + agentsimulatorAIDlist.size() + "  AgentSimulator ********");

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

  public void loadConfigFile(String pConfigFile)
  {
    // check the config file
    String configfile = pConfigFile;
    configfile = configfile.trim();
    if ( ! FileChecker.FileExist( configfile )  )
    {
      System.out.println ("********************************************************");
      System.out.println ("*  Error                                               *");
      System.out.println ("*  The following SIMULATION config file does not exist ! *");
      System.out.println ("*  " + configfile                                    + "*");
      System.out.println ("********************************************************");
      System.exit(1);
    }

    // load the config file into Configurator
    XmlConfigLoader loader = new XmlConfigLoader();
    loader.doload( configfile );
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
    // This directory will be used by the  IndexCalculator_base.java
    FileTool.createDirectory(ss+Configurator.mConfData.mPfadSeperator+"orderbook");

    // create directory for saving status exchange history of agents
    FileTool.createDirectory(ss+Configurator.mConfData.mPfadSeperator+"statusexchange");

    // These two directoried will be used by the DataLogger Agent
    Configurator.mConfData.setMainLogDirectory( ss );

  }

  /**************************************************************
   * Folgende methode sind nicht zu implementieren.             *
   * Wir brauchen nur die Methode Signatur fuer Unix            *
   * Version                                                    *
   **************************************************************/

  public void registerInvestorAgent()
  {
    // Nothing to do
  }

  public void registerNoiseTraderAgent()
  {
    // Nothing to do
  }

  public void registerRandomTraderAgent()
  {
    // Nothing to do
  }

  public void registerBlankoAgent()
  {
     // Nothing to do
  }

  public void registerTobintaxAgent()
  {
    // Nothing to do
  }

  public void setSimulationCounter(String pSimulationCounterInfo )
  {
    // Nothing to do
  }

  public void unregisterInvestorAgent()
  {
    // Nothing to do
  }
  public void unregisterNoiseTraderAgent()
  {
    // Nothing to do
  }
  public void unregisterRandomTraderAgent()
  {
    // Nothing to do
  }
  public void unregisterBlankoAgent()
  {
    // Nothing to do
  }
  public void updateSimulatorList(String pNewList)
  {
    // Nothing to do
  }
  public void closebusiness()
  {
    // Nothing to do
  }
  public void setStarttimeofFirstday(String pTime)
  {
    // Nothing to do
  }
  public void setCurrentNetwork(String pNetworkFile)
  {
    // Nothing to do
  }
  public void setCurrentday(int pDay)
  {
    // Nothing to do
  }
  public void setFinishedtimeofCurrentday(String pTime)
  {
    // Nothing to do
  }
  public void disableAllSubFrame()
  {
    // Nothing to do
  }
  public void EnableAllMonitorFrame( boolean pEnabled )
  {
    // Nothing to do
  }
  public void displayInvestorGewinnProzent( Vector pInvestorGewinnProzent, int pDay, double pMax, double pMin)
  {
    // Nothing to do
  }

  public void displayNoiserTraderGewinnProzent( Vector pNoiseTraderGewinnProzent, int pDay, double pMax, double pMin)
  {
    // Nothing to do
  }

  public void setExpectedAgentAnzahl ( int pTotalAgents, int pInvestorAnzahl, int pNoiseTraderAnzahl, int pBlankoAgentAnzahl,int pRandomTraderAnzahl)
  {
    // Nothing to do
  }

  public boolean getShowInnererWertEnabled()
  {
    // Nothing to do
    return true;
  }

  public void resetSomeStateFields()
  {
    // Nothing to do
  }

  public void addInterrupted()
  {
    // Nothing to do
  }

  public void setScreenLayout()
  {
    // Nothing to do
  }

  public void showDailyOrderBook(String  pDailyOrderBook)
  {
    // Nothing to do
  }

  public void EnableButtons4newConfiguration()
  {

  }

  public boolean isAgentStatusInGraph()
  {
     return false;
  }

  public boolean isAgentStatusNameLoaded()
  {
     return true;
  }

  public void setNameList2AgentStatusFrame(Vector pNameList)
  {

  }

  public void setAgentTradeStatus(Vector pAgentStatus, DailyStatisticOfNetwork pStatistic )
  {

  }

  public void startTobinTaxAgent()
  {

  }

  class Console extends Thread
  {
     SimulationInitControl mSimulationInitControl;
     public Console(SimulationInitControl pSimulationInitControl)
     {
         this.mSimulationInitControl = pSimulationInitControl;
     }

     public void run()
     {

        System.out.println("****************************************************************");
        System.out.println("*                                                              *");
        System.out.println("*    The Simulation Text Console is started                    *");
        System.out.println("*                                                              *");
        System.out.println("****************************************************************");
        try
        {
          // Delay 5 seconds
          Thread.sleep(5000);
        }
        catch ( Exception ex)
        {
            ex.printStackTrace();
        }

        System.out.println("****************************************************************");
        System.out.println("*                                                              *");
        System.out.println("*    The Simulation is ready                                   *");
        System.out.println("*    Input START then press Enter to start the simulation      *");
        System.out.println("*                                                              *");
        System.out.println("****************************************************************");

        boolean goon = false;
        String ss="OK";
        while ( !goon )
        {
            ss = HelpTool.getCommandLine();
            if ( ss.equalsIgnoreCase("START") )
            {
               goon = true;
            }
            else
            {
              System.out.println("*Command is wrong, please Enter START");
            }
        }

        this.mSimulationInitControl.dostart();

     }
  }

}
