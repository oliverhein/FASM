package de.marketsim.test;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author unbekannt
 * @version 1.0
 */

import jade.core.AID;
import jade.core.Agent;
import jade.core.ProfileImpl;
import jade.core.Profile;

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

public class TestAgent extends Agent
{

  protected String AgentLocalName = null;
  protected String Hostname = null;


  public TestAgent()
  {
  }

  protected void setup()
  {
      try
      {
          // create the agent description of itself
          DFAgentDescription dfd = new DFAgentDescription();
          AID  mAID = getAID();
          AgentLocalName = mAID.getLocalName();


          dfd.setName( mAID );
          DFService.register( this, dfd );

          // add the GUI
          setupGUI();

          // add a Behaviour to handle messages from StockOperatorBehavior
          System.out.println("Hallo world, my name ist " + AgentLocalName );

      }
      catch (Exception e)
      {
          System.out.println( "Exception: " + e );
          e.printStackTrace();
      }
   }

   public void setupGUI()
   {

   }

 }
