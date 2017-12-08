package de.marketsim.agent.stockstore;

/**
 * <p>Überschrift: </p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Organisation: </p>
 * @author  Xining Wang
 * @version 1.0
 */

import jade.core.AID;
import jade.core.Agent;
import jade.core.ProfileImpl;
import jade.core.Profile;

import jade.wrapper.PlatformController;
import jade.wrapper.AgentController;

import jade.lang.acl.ACLMessage;
//import jade.lang.acl.MessageTemplate;

import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;

import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.DFService;
import jade.domain.FIPAException;

public class DataLoggerAgent extends Agent
{

  public DataLoggerAgent()
  {

  }


  protected void setup()
  {
      //Object  args[] = this.getArguments();
      //System.out.println("Parameter Number =" + args.length );
      try
      {
          // create the agent description of itself
          DFAgentDescription dfd = new DFAgentDescription();
          AID  mAID = this.getAID();

          System.out.println("DataLogger AID=" + mAID );
          dfd.setName( mAID );
          DFService.register( this, dfd );

          DataLoggerBehavior mBH = new DataLoggerBehavior();
          addBehaviour( mBH );
      }
      catch (Exception e)
      {
          System.out.println( "Exception: " + e );
          e.printStackTrace();
      }
   }

}