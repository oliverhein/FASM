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

import de.marketsim.message.*;
import de.marketsim.SystemConstant;

import de.marketsim.util.*;
import de.marketsim.config.Configurator;
import de.marketsim.config.AgentSimulatorManagerContainer;



public class SimulatorRegisterAgentBehavior extends CyclicBehaviour
{

  private Agent  mAgent = null;

  public SimulatorRegisterAgentBehavior( Agent pAgent)
  {
     mAgent = pAgent;
  }

  // main processing flow
  public void action()
  {
    ACLMessage aclmsg = this.myAgent.blockingReceive(1000);
    if (aclmsg == null)
    {
       if ( Configurator.mConfData.mNeedNotifyDAX )
       {
         this.sendManagerList2DAX();
       }
       return;
    }

    MessageWrapper msgwrp =  null;
    try
    {
      msgwrp = ( MessageWrapper ) aclmsg.getContentObject();
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }

    if ( msgwrp.mMessageType == SystemConstant.MessageType_Register )
    {
       // Format: REGISTERME Command
       System.out.println( (String)msgwrp.mMessageContent + " has registered." );
       // 1. Register Info from the CommunicationManager
       // Save it into a CommunicationManagerList
       AgentSimulatorManagerContainer.registerNewAgentSimulatorManager( (String)msgwrp.mMessageContent, aclmsg.getSender() );
       // send list to DAX
       sendManagerList2DAX();
    }
    else
    if (msgwrp.mMessageType == SystemConstant.MessageType_UnRegister )
    {
       // 2. UnRegister Info from the CommunicationManager
       // remove it from a CommunicationManagerList
       AgentSimulatorManagerContainer.unregisterAgentSimulatorManager( (String)msgwrp.mMessageContent );
       // send list to DAX
       sendManagerList2DAX();
    }
    else
    if (msgwrp.mMessageType == SystemConstant.MessageType_RegistetrCommunicationManagerWithName )
    {
      String newname = AgentSimulatorManagerContainer.getNewName();
      ACLMessage response = new ACLMessage( ACLMessage.INFORM );

      MessageWrapper mm =new MessageWrapper();
      mm.mMessageType = SystemConstant.MessageType_RegistetrCommunicationManagerWithName;
      mm.mMessageContent = newname;
      try
      {
         response.setContentObject( mm );
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
      response.addReceiver( aclmsg.getSender() );
      this.myAgent.send( response );
      System.out.println( "sent Response:" + newname  );
    }

  }

  private void sendManagerList2DAX()
  {
    if ( Configurator.mConfData.mDAXisReady )
    {
      String ss = AgentSimulatorManagerContainer.getAgentSimulatorManagerNamesString();
      ACLMessage aclmsg = new ACLMessage( ACLMessage.INFORM  );

      MessageWrapper msgwrp = new MessageWrapper();
      msgwrp.mMessageType = SystemConstant.MessageType_SIMULATORMANAGERLIST;
      msgwrp.mMessageContent = ss;
      try
      {
         aclmsg.setContentObject( msgwrp );
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
      aclmsg.addReceiver( new AID("DAX", false)  );
      this.myAgent.send( aclmsg );
      Configurator.mConfData.mNeedNotifyDAX = false;
    }
    else
    {
       Configurator.mConfData.mNeedNotifyDAX = true;
    }

  }

  private void CheckCommunicationManager()
  {
    // if a PING message can not be sent to a CommunicationManager
    // it will be removed from the list.
    // Because it doesn't exist.


    // if a CommunicationManager does not response a PING message,
    // it will be removed from the list.


  }

}