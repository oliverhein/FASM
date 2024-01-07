/**
 * Überschrift:
 * Beschreibung:
 * Copyright:     Copyright (c) 2003
 * Organisation:
 * @author  Xining Wang
 * @version 1.0
 */

package de.marketsim.agent.trader;

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
import de.marketsim.config.*;
import de.marketsim.SystemConstant;

import de.marketsim.agent.AgentCommandlineParameterConst;

public class RandomTrader extends Agent
{
  protected String AgentLocalName = null;
  protected String Hostname = null;

  public RandomTrader()
  {

  }


  protected void setup()
  {
      /*
      Object  args[] = this.getArguments();
      String commandlinestr = "";
      for (int i=0; i<args.length; i++)
      {
        commandlinestr = commandlinestr + args[i] + " ";
      }
      CommandlineParameterParser cpars = new CommandlineParameterParser(commandlinestr);
      */

      DepotRecord myDepotRecord = new DepotRecord();
      myDepotRecord.mAgentInitType = SystemConstant.AgentType_RANDOMTRADER;

      if ( Configurator.istAktienMarket() )
      {
          myDepotRecord.AktienMarket_setInitCash( Configurator.mConfData.mRandomTraderInitCash );
          myDepotRecord.AktienMarket_setInitAktienMenge( Configurator.mConfData.mRandomTraderInitAktien );
      }
      else
      {
          myDepotRecord.MoneyMarket_setInitCash1( Configurator.mConfData.mRandomTraderInitCash );
          myDepotRecord.MoneyMarket_setInitCash2( Configurator.mConfData.mRandomTraderInitAktien );
      }

      try
      {
          // create the agent description of itself
          DFAgentDescription dfd = new DFAgentDescription();
          AID  mAID = getAID();
          AgentLocalName = mAID.getLocalName();
          myDepotRecord.mAgentName     = AgentLocalName;
          myDepotRecord.mAgentInitType     = SystemConstant.AgentType_RANDOMTRADER;
          myDepotRecord.mAgentInitTypeName = SystemConstant.AgentTypeName_RANDOMTRADER;

          dfd.setName( mAID );
          DFService.register( this, dfd );

          // add a Behaviour to handle messages from StockStore
          RandomTraderBehavior  mBH = new RandomTraderBehavior( this );
          mBH.setDepotRecord( myDepotRecord  );
          addBehaviour( mBH );

      }
      catch (Exception e)
      {
          System.out.println( "Exception: " + e );
          e.printStackTrace();
      }
   }

 }