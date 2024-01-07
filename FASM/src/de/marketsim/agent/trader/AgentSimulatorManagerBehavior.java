package de.marketsim.agent.trader;

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

import jade.wrapper.AgentController;
import jade.wrapper.AgentContainer;

import de.marketsim.util.MsgLogger;
import de.marketsim.message.*;
import de.marketsim.SystemConstant;

import de.marketsim.util.*;
import de.marketsim.config.*;
import de.marketsim.agent.AgentCommandlineParameterConst;

public class AgentSimulatorManagerBehavior extends CyclicBehaviour
{

  private Agent                 mAgent  = null;
  private AgentController       mAgentController = null;
  private String                mAgentDistributionList[] = new String[0];
  private String                mMode = null;

  public AgentSimulatorManagerBehavior( Agent pAgent)
  {
      mAgent = pAgent;
      MsgLogger.loadMsgLoggerConfig();
  }

  public void setMode(String pMode)
  {
    this.mMode = pMode;
  }

  // main processing flow
  public void action()
  {
    ACLMessage msg = this.myAgent.blockingReceive(500);
    if (msg == null)
    {
        return;
    }

    MessageWrapper msgwrp = null;
    try
    {
       msgwrp = ( MessageWrapper ) msg.getContentObject();
    }
    catch (Exception ex)
    {
       ex.printStackTrace();
    }

    // Command from StockStore
    // 1.  Agent-Runtime-Parameter
    // Save it into the local variables
    if ( msgwrp.mMessageType == SystemConstant.MessageType_RUNTIMEPARAMETER  )
    {
         System.out.println( "RunTimeParameter is received." );

         System.out.println( "My mode=" + this.mMode  );

         if ( this.mMode.equalsIgnoreCase("remote") )
         {
             Configurator.mConfData = ( ConfData ) msgwrp.mMessageContent;
             //System.out.println( "Configurator.mConfData.mAgentInitParameter=" + Configurator.mConfData.mAgentInitParameter.size() );
             String tt[] = Configurator.getAllParameterPair( true );
             for (int i=0; i< tt.length; i++)
             {
                System.out.println( tt[i] );
             }
         }

        Enumeration en = Configurator.mConfData.mAgentInitParameter.elements();
        while ( en.hasMoreElements() )
        {
          AgentInitParameter oneagent = (AgentInitParameter) en.nextElement();
          System.out.println( oneagent.toString() );
        }
    }

    else

    // 2.  Agentsaufteilungsliste
    // start the Investor/NoiseTrader/RandomTrader according to the AgentList
    if ( msgwrp.mMessageType == SystemConstant.MessageType_AgentList )
    {
          System.out.println( "AgentList is received." );

          this.mAgentDistributionList = ( (AgentList) msgwrp.mMessageContent ).AgentInfo;

          System.out.println( "AgentList contains " + this.mAgentDistributionList.length + " Items");

          for ( int i=0; i<this.mAgentDistributionList.length; i++)
          {
            System.out.println( mAgentDistributionList[i] );
          }

          // before start Agent: the PreisContainer has to be garanted
          // that it is cleaned. It is required for the Simulation mit mehreren KonfigurationsDatei
          PriceContainer.Reset( Configurator.mConfData.mHandelsday );
          startAllAgent();
    }
    else
    // 3.  CloseSimulator Command
    // Close the Simulator, java program is end.
    if ( msgwrp.mMessageType == SystemConstant.MessageType_QUITCOMMAND )
    {
      System.out.println("AgentSimulator receives QUIT Command.");
      System.out.println("AgentSimulator exit.");

      if ( this.mMode.equalsIgnoreCase("remote") )
      {
        System.out.println("AgentSimulator calls System.exit().");
        org.apache.log4j.LogManager.shutdown();
        System.exit(0);
      }
      else
      {

      }
    }
  }

  private void startAllAgent()
  {
      String myname = mAgent.getLocalName();

      java.util.Random rdgen = new Random();

      for ( int i=0; i< this.mAgentDistributionList.length; i++)
      {
           String ss = this.mAgentDistributionList[i];

           System.out.println( ss );

           int j1 = ss.indexOf(";");
           String smname = ss.substring(0,j1);

           if ( smname.equals( myname ) )
           {
               // get type
               int j2 = ss.indexOf(";", j1+1);
               String agenttype = ss.substring( j1+1,j2 );
               int j3 = ss.indexOf(";", j2+1);
               String agentname = ss.substring(j2+1, j3);
               int j4 = ss.indexOf(";", j3+1);
               String senderpartneranzahl = ss.substring(j3+1, j4);
               String receiverlist = ss.substring(j4+1);

               // -SenderPartner 2 -ReceiverPartnerList V1;V3;V15;
               if ( agenttype.equals("INVESTOR") )
               {
                 // start it
                 // Prepare parameter
                 String args[] = new String[4];
                 args[0] = "-" + AgentCommandlineParameterConst.SenderPartnerAnzahl;
                 args[1] = senderpartneranzahl;
                 args[2] = "-" + AgentCommandlineParameterConst.ReceiverPartnerList;
                 args[3] = receiverlist;

                 try
                 {
                   createOneAgent( agentname, "de.marketsim.agent.trader.Investor",args);
                 }
                 catch (Exception ex)
                 {
                   ex.printStackTrace();
                 }
               }
               else
               if ( agenttype.equals("NOISETRADER") )
               {
                 // start it
                 // Prepare parameter

                 String args[] = new String[4];

                 args[0] = "-" + AgentCommandlineParameterConst.SenderPartnerAnzahl;
                 args[1] = senderpartneranzahl;
                 args[2] = "-" + AgentCommandlineParameterConst.ReceiverPartnerList;
                 args[3] = receiverlist;

                 try
                 {
                   createOneAgent( agentname, "de.marketsim.agent.trader.NoiseTrader",args);
                 }
                 catch (Exception ex)
                 {
                   ex.printStackTrace();
                 }
               }
               else
               if ( agenttype.equals("BLANKOAGENT") )
               {
                 // start BlankoAgent
                 String args[] = new String[4];
                 args[0] = "-" + AgentCommandlineParameterConst.SenderPartnerAnzahl;
                 args[1] = senderpartneranzahl;
                 args[2] = "-" + AgentCommandlineParameterConst.ReceiverPartnerList;
                 args[3] = receiverlist;
                 try
                 {
                   createOneAgent( agentname, "de.marketsim.agent.trader.BlankoAgent",args);
                 }
                 catch (Exception ex)
                 {
                   ex.printStackTrace();
                 }
               }
               else
               if ( agenttype.equals("RANDOMTRADER") )
               {
                 // start it
                 String args[] = new String[0];
                 try
                 {
                   createOneAgent( agentname, "de.marketsim.agent.trader.RandomTrader",args);
                 }
                 catch (Exception ex)
                 {
                   ex.printStackTrace();
                 }
               }
           }
      }
  }

  private void createOneAgent(String pNewAgentName, String pNewAgentClassName, String args[]) throws Exception
  {
        // create new jade Agent
        // create new agent on the same container
        AgentContainer container = (AgentContainer) mAgent.getContainerController();
        // get a container controller for creating new agents
        mAgentController = container.createNewAgent(pNewAgentName, pNewAgentClassName, args);
        mAgentController.start();
        System.out.println( pNewAgentName + "Class:" + pNewAgentClassName + " will be created." );
  }

  private void CloseSimulator()
  {


  }

}