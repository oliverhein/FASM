package de.marketsim.agent.trader;

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
import de.marketsim.util.*;
import de.marketsim.message.*;
import de.marketsim.SystemConstant;

public class AgentSimulatorManager extends Agent
{
   /* 08.09.2005 */
  private AgentSimulatorManagerBehavior mBH = null;

  private String mode = null;

  public AgentSimulatorManager()
  {

  }

  /**
   * Setup the agent. Registers with the DF, and adds a behaviour to
   * process incoming messages.
   */
  protected void setup()
  {

    Object[] args = this.getArguments();
    this.mode = (String) args[0];

    try {
          System.out.println( getLocalName() + " setting up");
          System.out.println("getLocalName()= " + this.getLocalName())  ;
          System.out.println("getName= " + this.getName() )  ;
          System.out.println("getAMS= " + this.getAMS() );

          /////////////////////////////////////////////////////////////////////////
          // create the agent descrption of itself
          DFAgentDescription dfd = new DFAgentDescription();
          AID ss = getAID();
          System.out.println( "AID=" + ss );
          dfd.setName( ss );
          DFService.register( this, dfd );

          /////////////////////////////////////////////////////////////////////////
          // send message to the SimulatorRegister
          ACLMessage aclmsg = new ACLMessage( ACLMessage.INFORM );

          MessageWrapper msgwrp = new MessageWrapper();
          msgwrp.mMessageType = SystemConstant.MessageType_Register;
          msgwrp.mMessageContent = getLocalName();
          aclmsg.setContentObject( msgwrp );
          aclmsg.addReceiver( new AID ( "simulatorregister", false ) );
          // Send a register message to simulatorregister
          this.send(aclmsg);
          System.out.println( this.getLocalName() + " has sent registermessage to simulatorregister");
          /////////////////////////////////////////////////////////////////////////

          // No GUI is neccessary
          mBH = new AgentSimulatorManagerBehavior( this );
          mBH.setMode( this.mode );
          addBehaviour( mBH );
    }
    catch (Exception e)
    {
          System.out.println( "See:" + e );
          e.printStackTrace();
    }
  }

}