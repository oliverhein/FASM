package de.marketsim.agent.stockstore;

/**
 * <p>Überschrift: Market Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import java.text.*;
import java.util.*;
import java.io.*;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.DFService;
import jade.domain.FIPAException;

import de.marketsim.util.*;
import de.marketsim.config.*;
import de.marketsim.message.*;
import de.marketsim.SystemConstant;

import de.marketsim.agent.AgentCommandlineParameterConst;

// CyclicBehaviour
public class BatchTaskControllerBehavior extends OneShotBehaviour
{

  private Agent  mAgent = null;

  private BatchRunMonitor mMonitor = new BatchRunMonitor();

  public BatchTaskControllerBehavior( Agent pAgent)
  {
     mAgent = pAgent;
  }

  public void CheckConfiguration( String pConfigurationDirectoryName )
  {

  }

  // main processing flow
  public void action()
  {
    // wait this Signal !!!
    while ( StartBatchSignal.mStartSignal < 0 )
    {
      try
      {
        Thread.sleep(100);
      }
      catch (Exception ex)
      {

      }
    }

    System.out.println("Task Controll starts to schedule a configuration for simulation");

    // Check the TaskList
    // Check if all task is finished
    String[] ConfigFiles =ConfigFileCollector.getConfigFiles4Simulation();
    String[] TopLevelHtmlLink = new String [ConfigFiles.length];
    mMonitor.setSize( 440, 300);
    mMonitor.setVisible(true);
    mMonitor.setConfigFileList(  ConfigFiles );

    for ( int i=0; i< ConfigFiles.length; i++)
    {

      String cfgname = ConfigFiles[i];
      System.out.println("ConfigFileName=" + cfgname );
      int JJ = ConfigFileCollector.getSimulationTimesOneConfigFile( cfgname );
      mMonitor.setState( i, "running" );
      StartStockStore(  cfgname, JJ );
      //3. wait the "finish" Command from StockStore
      waitFinishCommand();
      // delay 3 s
      System.out.println("waiting for close of StockStore");
      mMonitor.setState( i, "finished" );

      HelpTool.pause(3000);
    }

    System.out.println("All ConfigFiles are processed."  );

    java.io.PrintStream  pw  = null;

    try
    {
      System.out.println("creating toplevel Html summary file ");
      pw = new java.io.PrintStream( new  java.io.FileOutputStream( "mainreport.html" ) );
    }
    catch (Exception ex)
    {

    }

    HTMLCreator.putHtmlHead(pw);
    HTMLCreator.putHtmlBodyBegin(pw);
    for (int i=0; i<TopLevelHtmlLink.length; i++)
    {
        HTMLCreator.putFileLink(pw, TopLevelHtmlLink[i], TopLevelHtmlLink[i] );
    }
    HTMLCreator.putHtmlBodyEnd(pw);
    HTMLCreator.putHtmlEnd(pw);

    pw.close();

        //send quit command to all AgentSimulator
        Vector agentsimulatorAIDlist = AgentSimulatorManagerContainer.getAgentSimulatorManagerAIDs();

        ACLMessage aclmsg = new ACLMessage( ACLMessage.INFORM );
        for (int i=0; i<agentsimulatorAIDlist.size(); i++)
        {
           aclmsg.addReceiver( (AID) agentsimulatorAIDlist.elementAt(i) );
        }
        try
        {
           aclmsg.setContentObject( MessageFactory.createQuitCommand() );
        }
        catch ( Exception ex)
        {
          ex.printStackTrace();
        }

        this.mAgent.send( aclmsg );

        System.out.println("Quit Command is sent");
        //wait for 500 ms
        try
        {
           Thread.sleep(2000);
        }
        catch (Exception ex)
        {

        }
        System.out.println("Simulation is end."  );
        System.exit(0);

  }

  private void StartStockStore(String configfilename, int pNetworkRepeatTimes)
  {
    // Start StockStore using this configuration
    System.out.println("start StockStore with " + configfilename );
    // create a new agent
    // get the PlatFormController
    jade.wrapper.PlatformController container = mAgent.getContainerController(); // get a container controller for creating new agents
    String args[] = new String[4];
    args[0] = "-" + AgentCommandlineParameterConst.SimulationConfig ;
    args[1] = configfilename;
    args[2] = "-" + AgentCommandlineParameterConst.RepeatTimes;
    args[3] = ""+pNetworkRepeatTimes;

    try
    {
        jade.wrapper.AgentController stockstore = container.createNewAgent("DAX", "de.marketsim.agent.stockstore.Store", args );
        stockstore.start();
        System.out.println("StockStore is started."  );
    }
    catch (Exception ex)
    {
        System.out.println("StockStore can not be started."  );
        ex.printStackTrace();
    }
  }

  public void waitFinishCommand()
  {
    // Wait Signal "Finished" from StockStore
    System.out.println("TaskController: wait for finish-signal ....."  );
    ACLMessage msg = this.myAgent.blockingReceive();
    System.out.println("TaskController: A finish-signal is received from " +  msg.getSender().getLocalName()  );
    // wait 10 seconds so that DataLogger finishes the data processing
    try
    {
      Thread.sleep(3000);
    }
    catch (Exception ex)
    {

    }

  }

}