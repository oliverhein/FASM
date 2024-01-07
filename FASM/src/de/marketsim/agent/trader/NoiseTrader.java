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
import de.marketsim.SystemConstant;
import de.marketsim.config.*;

import de.marketsim.agent.AgentCommandlineParameterConst;

public class NoiseTrader extends Agent
{
  protected String AgentLocalName = null;
  protected String Hostname = null;

  public NoiseTrader()
  {
  }

  protected void setup()
  {

      // commandline parameter format
      // -DisplayConsole true/false -InitDepotCash 100000 -InitDepotAktienMenge 100 -Regel R1 -SenderPartner 2 -ReceiverPartnerList V1;V3;V15;

      Object  args[] = this.getArguments();
      String commandlinestr = "";
      for (int i=0; i<args.length; i++)
      {
        commandlinestr = commandlinestr + args[i] + " ";
      }

      CommandlineParameterParser cpars = new CommandlineParameterParser(commandlinestr);
      AgentInitParameter  agentinitconfig = ( AgentInitParameter ) Configurator.mConfData.mAgentInitParameter.get(this.getLocalName());
      DepotRecord myDepotRecord = new DepotRecord();

      myDepotRecord.mAgentInitType = SystemConstant.AgentType_NOISETRADER;

      /**
      if ( Configurator.istAktienMarket() )
      {
         myDepotRecord.AktienMarket_setInitCash( Configurator.mConfData.mNoiseTraderInitCash );
         myDepotRecord.AktienMarket_setInitAktienMenge( Configurator.mConfData.mNoiseTraderInitAktien  );
      }
      else
      {
        //todo ???
        myDepotRecord.MoneyMarket_setInitCash1( Configurator.mConfData.mNoiseTraderInitCash  );
        myDepotRecord.MoneyMarket_setInitCash2( Configurator.mConfData.mNoiseTraderInitAktien  );
      }
      */

     /*** new 2006.04.10 */
     if ( Configurator.istAktienMarket() )
     {
         myDepotRecord.AktienMarket_setInitCash( agentinitconfig.mInitCash );
         myDepotRecord.AktienMarket_setInitAktienMenge( agentinitconfig.mInitAktien);
     }
     else
     {
         myDepotRecord.MoneyMarket_setInitCash1(  agentinitconfig.mInitCash );
         myDepotRecord.MoneyMarket_setInitCash2(  agentinitconfig.mInitAktien );
     }
      int SenderPartnerAnzahl = Integer.parseInt( cpars.getParameter( AgentCommandlineParameterConst.SenderPartnerAnzahl ) );
      String Receiverpartnerlist = cpars.getParameter( AgentCommandlineParameterConst.ReceiverPartnerList  ) ;

      try
      {
          // create the agent description of itself
          DFAgentDescription dfd = new DFAgentDescription();
          AID  mAID = getAID();
          AgentLocalName = mAID.getLocalName();
          myDepotRecord.mAgentName         = AgentLocalName;
          myDepotRecord.mAgentInitType     = SystemConstant.AgentType_NOISETRADER;
          myDepotRecord.mAgentInitTypeName = SystemConstant.AgentTypeName_NOISETRADER;

          //System.out.println( AgentLocalName + " starting up");

          dfd.setName( mAID );
          DFService.register( this, dfd );

          // add the GUI
          setupGUI();

          // add a Behaviour to handle messages from StockOperatorBehavior


          OperatorBaseBehavior  mBH ;

          if ( Configurator.istAktienMarket() )
          {
            mBH= new OperatorBehaviorAktienMarket( this );
          }
          else
          {
            mBH= new OperatorBehaviorMoneyMarket( this );
          }

          mBH.setInitParameter( agentinitconfig );
          mBH.setOperatorType( SystemConstant.AgentType_NOISETRADER);
          mBH.setDepotRecord( myDepotRecord );
          mBH.setSenderPartnerAnzahl( SenderPartnerAnzahl );
          mBH.setReceiverPartnerList( Receiverpartnerlist );
          addBehaviour( mBH );

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