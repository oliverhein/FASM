/**
 * Überschrift:
 * Beschreibung:
 * Copyright:     Copyright (c) 2003
 * Organisation:
 * @author  Xining Wang
 * @version 1.0
 */

package de.marketsim.test;

import jade.core.behaviours.*;
import jade.lang.acl.*;
import jade.core.*;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.DFService;
import jade.domain.FIPAException;

import java.util.*;
import java.io.*;
import java.lang.*;

public class SimpleBehavior extends CyclicBehaviour
{
  ACLMessage msg      = new ACLMessage( ACLMessage.INFORM );
  String    mLocalAID = null;
  String    mRuleName = null;

  public SimpleBehavior(Agent pAgent)
  {
     super(pAgent);
     this.mLocalAID = this.myAgent.getHap();
  }

  public void sendMessage(String Who, String msg)
  {
    ACLMessage sendmsg = new ACLMessage( ACLMessage.INFORM );
    sendmsg.setContent( msg );
    AID user = new AID(Who, false);
    System.out.println( user );

    sendmsg.addReceiver( user );
    this.myAgent.send( sendmsg );
  }

  // main process flow
  // this action Method will be automatical called from jader.Agent.setup().
  public void action()
  {
       ACLMessage Response = null;
       Response =  this.myAgent.blockingReceive(1000);
       if ( Response == null)
       {
            System.out.println( "No Messages, waiting next 20 seconds" );
       }
       else
       {
         System.out.println("received Message=" + Response.getContent() );
       }
  }

}