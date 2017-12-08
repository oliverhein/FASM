package de.marketsim.agent.stockstore;

/**
 * <p>Überschrift: Market Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Organisation: </p>
 * @author Xining Wang
 * @version 1.0
 */

import jade.core.AID;
import jade.core.Agent;
import jade.core.ProfileImpl;
import jade.core.Profile;

import jade.lang.acl.ACLCodec.*;
import jade.wrapper.PlatformController;
import jade.wrapper.AgentController;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;

import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.DFService;
import jade.domain.FIPAException;

import javax.swing.*;
import java.util.*;
import java.text.NumberFormat;
import de.marketsim.gui.*;
import de.marketsim.agent.stockstore.stockdata.*;
import de.marketsim.config.*;
import de.marketsim.util.*;

public class BatchTaskController extends Agent
{

   /* 10.09.2005 */
  private BatchTaskControllerBehavior mBH = null;
  private LoadBatchConfig mLoadBatchConfig = null;
  public BatchTaskController()
  {

  }

  /**
   * Setup the agent. Registers with the DF, and adds a behaviour to
   * process incoming messages.
   */
  protected void setup()
  {

   try {
          System.out.println( getLocalName() + " setting up");
          // create the agent descrption of itself
          DFAgentDescription dfd = new DFAgentDescription();
          AID ss = this.getAID();
          System.out.println( "AID=" + ss );
          dfd.setName( ss );
          DFService.register( this, dfd );
          System.out.println( getLocalName() + " has regsitered on DFService");

          /*
          System.out.println("getLocalName()= " + this.getLocalName())  ;
          System.out.println("getHap()= " + this.getHap())  ;
          System.out.println("getName= " + this.getName() )  ;

          System.out.println("getAMS= " + this.getAMS() );
          System.out.println("QueueSize" + this.getQueueSize() );
          */

          // start the GUI
          setupGUI();

          mBH = new BatchTaskControllerBehavior( this );
          addBehaviour( mBH );
    }
    catch (Exception e)
    {
          System.out.println( "See:" + e );
          e.printStackTrace();
     }
  }

  public void setupGUI()
  {
     mLoadBatchConfig = new LoadBatchConfig( this );
     mLoadBatchConfig.makeVisible();
  }

}