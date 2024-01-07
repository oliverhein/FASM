package de.marketsim.agent.trader;

/**
 * <p>Überschrift: Mircomarket Simulator</p>
 * <p>Beschreibung: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Organisation: </p>
 * @author Xining Wang
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
import de.marketsim.gui.*;
import de.marketsim.util.*;
import de.marketsim.agent.AgentCommandlineParameterConst;
import de.marketsim.config.Configurator;

public class TobintaxAgent extends Agent
{
  protected String AgentLocalName = null;
  protected String Hostname = null;

  public TobintaxAgent()
  {
  }

  protected void setup()
  {
      System.out.println("Tobintax Agent Initializing begins");
      // -InitDepotCash1 0 -InitDepotCash2 0
      PriceContainer.Reset( Configurator.mConfData.mHandelsday );
      DepotRecord myDepotRecord = new DepotRecord();
      String ss1 = System.getProperty("TOBINTAXAGENTINITCASH1", ".0");
      double  dd1 = Double.parseDouble(ss1);
      String ss2 = System.getProperty("TOBINTAXAGENTINITCASH2",".0");
      double  dd2 = Double.parseDouble(ss2);

      myDepotRecord.mMoneyMarket_InitCash1    = dd1;
      myDepotRecord.mMoneyMarket_InitCash2    = dd2;
      myDepotRecord.mMoneyMarket_CurrentCash1 = dd1;
      myDepotRecord.mMoneyMarket_CurrentCash2 = dd2;

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
          // Set Depot and Menge according to the Parameter
          // add a Behaviour to handle messages from StockOperatorBehavior
          TobintaxAgentBehavior  mBH = new TobintaxAgentBehavior( this );
          mBH.setDepotRecord( myDepotRecord );
          addBehaviour( mBH );
          System.out.println("Tobintax Agent Initializing is finished successfully.");
      }
      catch (Exception e)
      {
          System.out.println( "Tobintax Agent starting failed Exception: " + e );
          e.printStackTrace();
      }
   }

   public void setupGUI()
   {
      // nothing to do!!!!!!
   }

 }



